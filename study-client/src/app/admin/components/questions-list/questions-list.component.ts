import { Component, ViewChild, OnInit, OnDestroy } from '@angular/core';
import { QuestionService } from '../../services/question.service';
import { Router } from '@angular/router';
// import { MatSnackBar, MatSnackBarConfig } from '@angular/material/snack-bar';
import { ExamSharingService } from '../../services/exam-sharing.service';
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { ToastrService } from 'ngx-toastr';

declare var $: any;
declare var MathJax: any
@Component({
  selector: 'app-questions-list',
  templateUrl: './questions-list.component.html',
  styleUrls: ['./questions-list.component.css']
})
export class QuestionsListComponent implements OnInit, OnDestroy {

  // @ViewChild('dataTable', {static: false}) table;
  dataTable: any;
  constructor(
    private fb: FormBuilder,
    private _questionService: QuestionService,
    private router: Router,
    // private _snackBar: MatSnackBar,
    private _examSharingService: ExamSharingService,
    private toastr: ToastrService
  ) { }

  questions: any = [];
  questionImages = {}
  deleteQuestions = [];
  goToPage:number
  approveString:string = "approve"
  approveClass:string = "btn btn-primary"
  approve:boolean = true

  subjects = []
  chapters = []
  question_types = []
  difficulty_levels = []
  exam_papers = []
  approvals = [{'value': 0,'name':'All'}, {'value': 1,'name':'Approved'}, {'value': 2,'name':' Not Approved'}]
  selected_subject = null;
  selected_chapter = null;
  selected_question_type = null;
  selected_diff_level = null;
  selected_approval = null
  filters: FormGroup;
  filter: {
    subject:null,
    chapter:null,
    questionType:null,
    difficultyLevel:null,
    approval:string
  }
  filterObject = {}
  examPaperName=null
  selectedExamPaper=0

  ngOnInit() {
    $('#filterBadgeHeader').hide()
    this.filters = this.fb.group({
      subject: [''],
      chapter: [''],
      difficulty_level: [''],
      question_type: ['question_type'],
      approval:['']
    })
    this.filter = {
      subject:null,
      chapter:null,
      questionType:null,
      difficultyLevel:null,
      approval:null
    }
    let editObject = this._examSharingService.getEditQuestion();
    console.log('editObject  ', editObject)
    if(editObject){
      this.goToPage = editObject['pageNo']
      this.filterObject = editObject['filterObject']
      // this.setFilterFromHistory();
      // this.applyFilter(false)
      this.getProperty(true);
    } else
        this.getProperty(false)
    setTimeout(() => {
      // $('#spinner').hide()
      this.listAllQuestion();
    }, 3000);
  }

  ngOnDestroy() {
    $("body>#deleteConfirmationModal").remove();
    $("body>#filterConfirmationModal").remove();
  }

  listAllQuestion() {
    this.setFilter()
    this.deleteQuestions = []
    $('#spinner').css('display', 'flex')
    let questionsListComponent = this
    this._questionService.listQuestions(this.filterObject).subscribe((response: []) => {
      console.log(response);
      this.questions = response['questions']
      this.questionImages = response['questionImages']
      var body = $('#maths');
      body.html('');
      for (var i = 0; i < this.questions.length; i++) {
        var question = this.questions[i];
        var tr = $('<tr></tr>');
        tr.attr('data-question-id', question['id'])
        var td0 = $('<td></td>');
        var td1 = $('<td></td>');
        td1.html(i + 1);
        var td2 = $('<td></td>');
        //td2.html(this.getImage(question));
        td2.html(question['question']);
        let editQuestion = this.editQuestion;
        td2.dblclick(function () { editQuestion(this, questionsListComponent) })
        var td3 = $('<td></td>')
        question['questionType']['name'];
        var approvedString = question['approved'] ? "Approved" : "Not Approved"
        if(question['solution'] != null)
          td3.html('<span class="badge badge-pill badge-primary">' + question['subject']['name'] + '</span><br><span class="badge badge-pill badge-success">' + question['chapter']['name'] + '</span><br><span class="badge badge-pill badge-danger">' + question['questionType']['name'] + '</span><br><span class="badge badge-pill badge-warning">' + question['difficultyLevel']['name'] + '</span><br><span class="badge badge-pill badge-info">Answer:' + question['answer'] + '</span><br><span class="badge badge-pill badge-secondary">' + approvedString + '</span><br><span class="badge badge-pill badge-dark"><a style="color:white" href='+question['solution']+' target="_blank">Solution</a></span>')
        else
          td3.html('<span class="badge badge-pill badge-primary">' + question['subject']['name'] + '</span><br><span class="badge badge-pill badge-success">' + question['chapter']['name'] + '</span><br><span class="badge badge-pill badge-danger">' + question['questionType']['name'] + '</span><br><span class="badge badge-pill badge-warning">' + question['difficultyLevel']['name'] + '</span><br><span class="badge badge-pill badge-info">Answer:' + question['answer'] + '</span><br><span class="badge badge-pill badge-secondary">' + approvedString + '</span>');
        tr.append(td0);
        tr.append(td1);
        tr.append(td2);
        tr.append(td3);
        MathJax.Hub.Queue(["Typeset", MathJax.Hub, td2[0]]);
        body.append(tr);
      }
      // this.dataTable = $(this.table.nativeElement);
      var deleteQuestions = this.deleteQuestions
      var openSnackBar = this.openSnackBar;
      // var _snackBar = null
      $('#questionTable tbody tr td:first-child').on('click', function () {
        var questionId = $(this).closest('tr').attr('data-question-id');
        questionId = parseInt(questionId)
        console.log(questionId)
        if (deleteQuestions.indexOf(questionId) > -1) {
          //remove from list
          console.log('removing')
          deleteQuestions.splice(deleteQuestions.indexOf(questionId), 1);
          console.log(deleteQuestions)
        } else {
          // add to list
          console.log('adding')
          deleteQuestions.push(questionId);
          console.log(deleteQuestions)
        }
      })
      this.dataTable = $('#questionTable').DataTable({
        "displayStart": this.goToPage == undefined ? 0 : this.goToPage*10,
        "bLengthChange": false,
        fixedHeader: true,
        columnDefs: [{
          orderable: false,
          className: 'select-checkbox',
          targets: 0
        }],
        select: {
          style: 'multi',
          selector: 'td:first-child'
        },
        order: [[1, 'asc']],
      });

      $('#spinner').hide()
    }, (error) => {
      $('#spinner').hide()
      this.openSnackBar('error','Error', 'Failed to load questions. Please try again in sometime or contact to admin!!!');
      console.log(error);
    });
  }

  getImage(question) {
    if(this.questionImages[question['id']]) {
      var questionStr = question['question']
      var temp = this.questionImages[question['id']];
      jQuery.each(temp, function(key, val) {
        var imageStr = 'src="data:image/'+val['imageFormat']+';base64,'+val['questionImage']+'"'
        questionStr = questionStr.replace(key, imageStr)
      });
      question['question'] = questionStr;
      return questionStr;
    } else {
      return question['question'];
    }
  }

  confirmDelete() {
    if (this.deleteQuestions.length == 0) {
      this.openSnackBar('warn', '', 'No question selected to deleted!!!');
      $('#spinner').hide()
      $('#deleteConfirmationModal').modal('hide');
      $(".modal-backdrop").remove();
      return
    }
    $('#deleteConfirmationModal').modal('show')
    $("#deleteConfirmationModal").appendTo("body");
  }
  confirmApprove(type) {
    this.approveString = type ? "approve" : "disapprove"
    this.approveClass = type ? "btn btn-primary" : "btn btn-danger"
    this.approve = type
    if (this.deleteQuestions.length == 0) {
      this.openSnackBar('warn', '', 'No question selected to '+this.approveString+'!!!');
      $('#spinner').hide()
      $('#approveConfirmationModal').modal('hide');
      $(".modal-backdrop").remove();
      return
    }
    $('#approveConfirmationModal').modal('show')
    $("#approveConfirmationModal").appendTo("body");
  }
  editQuestion(questionNode, questionsListComponent) {
    console.log(questionNode)
    // alert('edit mode  '+ $(questionNode).attr('data-question-id'))
    let questionId = $(questionNode).closest('tr').attr('data-question-id')
    console.log('questionsListComponent.filterObject  ', questionsListComponent.filterObject)
    questionsListComponent._examSharingService.setEditQuestion({
      'questionId': questionId,
      'pageNo': questionsListComponent.dataTable.page(),
      'returnUrl': 'questions-list',
      'filterObject': questionsListComponent.filterObject
    });
    $('#spinner').css('display', 'flex')
    questionsListComponent.router.navigateByUrl('/editor/' + questionId);
  }
  openSnackBar(type, title, message) {
    if(type == "success")
      this.toastr.success(message, title);
    else if(type == "error")
      this.toastr.error(message, title);
    else if(type == "warn")
      this.toastr.warning(message, title);
    else
      this.toastr.info(message, title);
  }

  deleteSelected() {
    $('#spinner').css('display', 'flex')

    console.log('questionList  ', this.deleteQuestions)

    this._questionService.deleteQuestion({ 'list': this.deleteQuestions }).subscribe((response) => {
      console.log(response);
      this.openSnackBar('success', 'Success', 'Questions deleted successfully!!!');
      $('#deleteConfirmationModal').modal('hide');
      $(".modal-backdrop").remove();
      // $("body>#deleteConfirmationModal").remove();
      // this.router.navigateByUrl('questions-list');
      this.dataTable.destroy();
      this.listAllQuestion();
    }, (error) => {
      console.log(error);
      $('#spinner').hide()
      this.openSnackBar('error', 'Error', 'Failed to delete questions. Please try again in sometime or contact to admin!!!');
      $('#deleteConfirmationModal').modal('hide');
      // $("body>#deleteConfirmationModal").remove();
    });
  }

  approveSelected(type) {
    $('#spinner').css('display', 'flex')

    console.log('questionList  ', this.deleteQuestions)
    var approveString = type ? "approve" : "disapprove"
    this._questionService.updateApproveStatus({ 'list': this.deleteQuestions, 'approvalStatus':type }).subscribe((response) => {
      console.log(response);
      this.openSnackBar('success', 'Success',  'Questions '+ approveString +'d successfully!!!');
      $('#approveConfirmationModal').modal('hide');
      $(".modal-backdrop").remove();
      // $("body>#deleteConfirmationModal").remove();
      // this.router.navigateByUrl('questions-list');
      this.dataTable.destroy();
      this.listAllQuestion();
    }, (error) => {
      console.log(error);
      $('#spinner').hide()
      this.openSnackBar('error', 'Error', 'Failed to '+approveString+' questions. Please try again in sometime or contact to admin!!!');
      $('#approveConfirmationModal').modal('hide');
      // $("body>#deleteConfirmationModal").remove();
    });
  }

  // filter section

  getProperty(applyFilter) {
    $('#spinner').css('display', 'flex')
    this._questionService.getProperty().subscribe((response) => {
      console.log(response)
      this.subjects = response['subjects']
      this.question_types = response['questionTypes']
      this.difficulty_levels = response['difficultyLevels']
      this.exam_papers = response['examPapers']
      this.selectedExamPaper = this.exam_papers && this.exam_papers.length > 0 ? this.exam_papers[0]['id'] : 0
      
      if(applyFilter){
        this.setFilterFromHistory();
        this.applyFilter(false)
      }
    }, (error) => {
      // $('#spinner').hide()
      console.log(error);
    });
  }

  setChapters() {
    let selectedSubject = this.filters.value.subject;
    console.log(this.subjects)
    let subject = this.subjects.find((data: any) => data.id == selectedSubject);
    console.log(selectedSubject)
    console.log(subject)
    if(subject)
      this.chapters = subject.chapters 
    else
      this.chapters = []
    this.selected_chapter = null
  }
  confirmFilter() {
    $('#filterConfirmationModal').modal('show')
    $("#filterConfirmationModal").appendTo("body");
  }

  setFilterFromHistory() {
    this.filters.value.question_type = this.filterObject['questionType'] ? this.filterObject['questionType']['id'] : null;
    this.selected_question_type = this.filters.value.question_type
    this.filters.value.subject = this.filterObject['subject'] ? this.filterObject['subject']['id'] : null;
    if(this.filters.value.subject != null && this.filters.value.subject > 0){
      let selectedSubject = this.filters.value.subject;
      this.filterObject['subject'] = this.subjects.find((data: any) => data.id === selectedSubject);  
      this.chapters = this.filterObject['subject']['chapters']
    }
    this.selected_subject = this.filters.value.subject
    
    this.filters.value.chapter = this.filterObject['chapter'] ? this.filterObject['chapter']['id'] : null;
    this.selected_chapter = this.filters.value.chapter

    this.filters.value.difficulty_level = this.filterObject['difficultyLevel'] ? this.filterObject['difficultyLevel']['id'] : null;
    this.selected_diff_level = this.filters.value.difficulty_level

    this.filters.value.approval = this.filterObject['approval'];
    this.selected_approval = this.filters.value.approval
  }

  setFilter() {
    let selectedQuestionType = this.filters.value.question_type;
    this.filterObject['questionType'] = this.question_types.find((data: any) => data.id == selectedQuestionType);
    
    let selectedSubject = this.filters.value.subject;
    this.filterObject['subject'] = this.subjects.find((data: any) => data.id == selectedSubject);

    let selectedChapter = this.filters.value.chapter;
    this.filterObject['chapter'] = this.chapters.find((data: any) => data.id == selectedChapter);

    let selectedDiffLevel = this.filters.value.difficulty_level;
    this.filterObject['difficultyLevel'] = this.difficulty_levels.find((data: any) => data.id == selectedDiffLevel);
    
    let selectedApproval = this.filters.value.approval;
    this.filterObject['approval'] = selectedApproval == null || selectedApproval == "" ? 0 : selectedApproval//this.approvals.find((data: any) => data.value === selectedApproval);
  }
  applyFilter(flag) {
    console.log(this.filters.value)
    this.setFilter();
    let showBadge = false;
    if(this.filterObject['subject']) {
      $('#filterSubjectBadge').show();
      this.filter.subject = this.filterObject['subject'].name
      showBadge = true;
    } else {
      $('#filterSubjectBadge').hide();
    }
    if(this.filterObject['chapter']) {
      $('#filterChapterBadge').show();
      this.filter.chapter = this.filterObject['chapter'].name
      showBadge = true;
    } else {
      $('#filterChapterBadge').hide();
    }
    if(this.filterObject['questionType']) {
      $('#filterQuestionTypeBadge').show();
      this.filter.questionType = this.filterObject['questionType'].name
      showBadge = true;
    } else {
      $('#filterQuestionTypeBadge').hide();
    }
    if(this.filterObject['difficultyLevel']) {
      $('#filterDifficultyLevelBadge').show();
      this.filter.difficultyLevel = this.filterObject['difficultyLevel'].name
      showBadge = true;
    } else {
      $('#filterDifficultyLevelBadge').hide();
    }
    if(this.filterObject['approval']) {
      $('#filterApprovalBadge').show();
      this.filter.approval = this.filterObject['approval'] == 0 || this.filterObject['approval'] == null ? 'All' : this.filterObject['approval'] == 1 ? 'Approved' : 'Not approved'
      showBadge = true;
    } else {
      $('#filterApprovalBadge').hide();
    }
    if(showBadge)
      $('#filterBadgeHeader').show();
    if(flag)
      this.getFilteredRecoreds();
    $('#filterConfirmationModal').modal('hide');
    $(".modal-backdrop").remove();
    
  }

  removeFilter(type) {
    if(type == 'subject') {
      $('#filterSubjectBadge').hide();
      // this.filters.value.subject.setValue(null);
      this.filters.patchValue({'subject':null});
    } else if(type == 'chapter') {
      $('#filterChapterBadge').hide();
      // this.filters.value.chapter.setValue(null);
      this.filters.patchValue({'chapter':null});
    } else if(type == 'questionType') {
      $('#filterQuestionTypeBadge').hide();
      // this.filters.value.question_type.setValue(null);
      this.filters.patchValue({'question_type':null});
    } else if(type == 'difficultyLevel') {
      $('#filterDifficultyLevelBadge').hide();
      this.filters.patchValue({'difficulty_level':null});
    } else if(type == 'approval') {
      $('#filterApprovalBadge').hide();
      this.filters.patchValue({'approval':0});
    }

    if($('#filterBadgeHeader').find('span[data-node-type="filter-badge"]:visible').length > 0) {
      $('#filterBadgeHeader').show();
    } else {
      $('#filterBadgeHeader').hide();
    }
    this.getFilteredRecoreds()
  }

  getFilteredRecoreds() {
    if(!this.filterObject['subject'] && !this.filterObject['chapter'] && !this.filterObject['questionType'] && !this.filterObject['diffLevel'] && !this.filterObject['approval'])
      this.clearFilter()
    else {
      // call filtered list
      this.dataTable.destroy();
      this.listAllQuestion();
    }
  }

  clearFilter() {
    console.log('clearing filter')
    this.filters.patchValue({'subject':null});
    this.filters.patchValue({'chapter':null});
    this.filters.patchValue({'question_type':null});
    this.filters.patchValue({'difficulty_level':null});
    this.filters.patchValue({'approval':null});
    $('#filterBadgeHeader').hide();
    this.dataTable.destroy();
    this.listAllQuestion();
  }


  // Question paper
  confirmCreateExam() {
    if (this.deleteQuestions.length == 0) {
      this.openSnackBar('warn','', 'No question selected to create exam!!!');
      $('#spinner').hide()
      $('#createExamConfirmationModal').modal('hide');
      $(".modal-backdrop").remove();
      return
    }
    $('#createExamConfirmationModal').modal('show')
    $("#createExamConfirmationModal").appendTo("body");
  }

  createExam() {
    $('#createExamConfirmationModal').modal('hide');
    $(".modal-backdrop").remove();
    $('#spinner').css('display', 'flex')
    var data = {
      'examPaper': {
        'name': this.examPaperName ? this.examPaperName.trim() : null
      },
      'questionIds': this.deleteQuestions
    }
    this._questionService.addExamPaperQuestions(data).subscribe((response) => {
      $('#spinner').hide()
      console.log(response);
      this.openSnackBar('success', 'Success', 'Questions added to "'+this.examPaperName+'" successfully!!!');
      this.dataTable.destroy();
      this.listAllQuestion();
      this.getProperty(false);
    }, (error) => {
      console.log(error);
      $('#spinner').hide()
      this.openSnackBar('error', 'Error', 'Failed to add questions to "'+this.examPaperName+'". Please try again in sometime or contact to admin!!!');
    });
  }

  confirmAddToExam() {
    if (this.deleteQuestions.length == 0) {
      this.openSnackBar('error', 'Error', 'No question selected to add!!!');
      $('#spinner').hide()
      $('#addToExamConfirmationModal').modal('hide');
      $(".modal-backdrop").remove();
      return
    }
    $('#addToExamConfirmationModal').modal('show')
    $("#addToExamConfirmationModal").appendTo("body");
  }

  addToExam() {
    console.log(this.selectedExamPaper)
    let examPaper = this.exam_papers.find((data: any) => data.id === parseInt(this.selectedExamPaper+""));
    console.log(examPaper)
    $('#addToExamConfirmationModal').modal('hide');
    $(".modal-backdrop").remove();
    $('#spinner').css('display', 'flex')
    var data = {
      'examPaper': examPaper,
      'questionIds': this.deleteQuestions
    }
    this._questionService.addExamPaperQuestions(data).subscribe((response) => {
      $('#spinner').hide()
      console.log(response);
      this.openSnackBar('success', 'Success', 'Questions added to "'+examPaper['name']+'" successfully!!!');
      this.dataTable.destroy();
      this.listAllQuestion();
      this.getProperty(false);
    }, (error) => {
      console.log(error);
      $('#spinner').hide()
      this.openSnackBar('error', 'Error', 'Failed to add questions to "'+examPaper['name']+'". Please try again in sometime or contact to admin!!!');
    });
  }
}
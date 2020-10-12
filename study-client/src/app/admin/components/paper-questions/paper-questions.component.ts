import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { QuestionService } from '../../services/question.service';
// import { MatSnackBarConfig, MatSnackBar } from '@angular/material';
import { ExamSharingService } from '../../services/exam-sharing.service';
import { ToastrService } from 'ngx-toastr';
declare var $: any;
declare var MathJax: any

@Component({
  selector: 'app-paper-questions',
  templateUrl: './paper-questions.component.html',
  styleUrls: ['./paper-questions.component.css']
})
export class PaperQuestionsComponent implements OnInit {

  constructor(
    private _Activatedroute:ActivatedRoute,
    private _router: Router,
    private _questionService:QuestionService,
    // private _snackBar: MatSnackBar,
    private _examSharingService: ExamSharingService,
    private toastr: ToastrService
  ) { }

  dataTable: any;
  deleteQuestions = [];
  goToPage:number
  questions=[]
  questionImages = {}
  examPaper={}

  ngOnInit() {
    let editObject = this._examSharingService.getEditQuestion();
    if(editObject)
      this.goToPage = editObject['pageNo']
    var paperId = this._Activatedroute.snapshot.paramMap.get("paperId");
    this.loadPaperQuestions(paperId);
  }

  loadPaperQuestions(paperId) {
    var data = data = {
      "examPaper": {'id': paperId},
      "questionTypes": [],
      "difficultyLevels": [],
      "examType": "PAPER"
    }
    $('#spinner').css('display', 'flex')
    var paperQuestionsComponent = this;
    this._questionService.getExamPaper(data).subscribe((response:[]) => {
      $('#spinner').hide()
      console.log(response);
      this.examPaper = response
      this.questions = response['questions']
      this.questionImages = response['questionImages']
      var body = $('#examPaperQuestions');
      body.html('');
      for (var i = 0; i < this.questions.length; i++) {
        var question = this.questions[i];
        var tr = $('<tr></tr>');
        tr.attr('data-question-id', question['id'])
        var td0 = $('<td></td>');
        var td1 = $('<td></td>');
        td1.html(i + 1);
        var td2 = $('<td></td>');
        td2.html(this.getImage(question));
        let editQuestion = this.editQuestion;
        td2.dblclick(function () { editQuestion(this, paperQuestionsComponent) })
        var td3 = $('<td></td>')
        question['questionType']['name'];
        var approvedString = question['approved'] ? "Approved" : "Not Approved"
        if(question['solution'] != null)
          td3.html('<span class="badge badge-pill badge-primary">' + question['subject']['name'] + '</span><br><span class="badge badge-pill badge-success">' + question['chapter']['name'] + '</span><br><span class="badge badge-pill badge-danger">' + question['questionType']['name'] + '</span><br><span class="badge badge-pill badge-warning">' + question['difficultyLevel']['name'] + '</span><br><span class="badge badge-pill badge-info">Answer:' + question['answer'] + '</span><br><span class="badge badge-pill badge-secondary">' + approvedString + '</span><br><span class="badge badge-pill badge-dark"><a style="color:white" href='+question['solution']+' target="_blank">Solution</a></span>')
        else
          td3.html('<span class="badge badge-pill badge-primary">' + question['subject']['name'] + '</span><br><span class="badge badge-pill badge-success">' + question['chapter']['name'] + '</span><br><span class="badge badge-pill badge-danger">' + question['questionType']['name'] + '</span><br><span class="badge badge-pill badge-warning">' + question['difficultyLevel']['name'] + '</span><br><span class="badge badge-pill badge-info">Answer:' + question['answer'] + '</span><br><span class="badge badge-pill badge-secondary">' + approvedString + '</span>');
        // td3.html('<span class="badge badge-pill badge-primary">' + question['subject']['name'] + '</span><br><span class="badge badge-pill badge-success">' + question['chapter']['name'] + '</span><br><span class="badge badge-pill badge-danger">' + question['questionType']['name'] + '</span><br><span class="badge badge-pill badge-warning">' + question['difficultyLevel']['name'] + '</span><br><span class="badge badge-pill badge-info">Answer:' + question['answer'] + '</span><br><span class="badge badge-pill badge-secondary">' + approvedString + '</span>')
        tr.append(td0);
        tr.append(td1);
        tr.append(td2);
        tr.append(td3);
        MathJax.Hub.Queue(["Typeset", MathJax.Hub, td2[0]]);
        body.append(tr);
      }
      var deleteQuestions = this.deleteQuestions
      var openSnackBar = this.openSnackBar;
      var _snackBar = null
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
        order: [[1, 'asc']]
      });

      $('#spinner').hide()
    }, (error) => {
      console.log(error);
      $('#spinner').hide()
      this.openSnackBar('error', 'Error', 'Failed to load questions. Please try again in sometime or contact to admin!!!');
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

  goToPapers() {
    this._router.navigateByUrl('/papers');
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

  editQuestion(questionNode, paperQuestionsComponent) {
    console.log(questionNode)
    // alert('edit mode  '+ $(questionNode).attr('data-question-id'))
    let questionId = $(questionNode).closest('tr').attr('data-question-id')
    paperQuestionsComponent._examSharingService.setEditQuestion({
      'questionId': questionId,
      'pageNo': paperQuestionsComponent.dataTable.page(),
      'returnUrl': '/paper-questions/' + paperQuestionsComponent.examPaper['id']
    });
    $('#spinner').css('display', 'flex')
    paperQuestionsComponent._router.navigateByUrl('/editor/' + questionId);
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

  deleteSelected() {
    $('#spinner').css('display', 'flex')

    console.log('questionList  ', this.deleteQuestions)
    var temp = []
    for(var i = 0;i<this.deleteQuestions.length; i++) {
      temp.push({'id': this.deleteQuestions[i]})
    }
    var data = data = {
      "examPaper": {'id': this.examPaper['id'], 'questions': temp},
      "questionTypes": [],
      "difficultyLevels": [],
      "examType": "PAPER"
    }
    this._questionService.deleteExamPaperQuestions(data).subscribe((response) => {
      console.log(response);
      this.openSnackBar('success', 'Success', 'Questions deleted successfully!!!');
      $('#deleteConfirmationModal').modal('hide');
      $(".modal-backdrop").remove();
      // $("body>#deleteConfirmationModal").remove();
      // this.router.navigateByUrl('questions-list');
      this.dataTable.destroy();
      this.loadPaperQuestions(this.examPaper['id']);
    }, (error) => {
      console.log(error);
      $('#spinner').hide()
      this.openSnackBar('error', 'Error', 'Failed to delete questions. Please try again in sometime or contact to admin!!!');
      $('#deleteConfirmationModal').modal('hide');
      // $("body>#deleteConfirmationModal").remove();
    });
  }
}

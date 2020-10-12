import { Component, OnInit, OnDestroy } from '@angular/core';
import { QuestionService } from '../../services/question.service';
import { Router,ActivatedRoute } from '@angular/router';
import { FormBuilder, FormControl, FormGroup, FormArray, Validators } from "@angular/forms";
// import {MatSnackBar,MatSnackBarConfig, MatSnackBarHorizontalPosition, MatSnackBarVerticalPosition,} from '@angular/material/snack-bar';
import { AlertsComponent } from 'src/app/alerts/alerts.component';
import { ExamSharingService } from '../../services/exam-sharing.service';
import { ToastrService } from 'ngx-toastr';
import { FileUploadService } from '../../services/file-upload.service';

declare var CKEDITOR;
declare var $;

@Component({
  selector: 'app-editor',
  templateUrl: './editor.component.html',
  styleUrls: ['./editor.component.css']
})
export class EditorComponent implements OnInit, OnDestroy {

  questionId:any = 0;
  questionCounter:any = 0;
  question_types:any = [];
  subjects:any = [];
  chapters:any = [];
  difficulty_levels:any = [];
  selected_question_type:any = ''
  selected_diff_level:any = ''
  selected_subject:any = ''
  selected_chapter:any = ''
  selected_answer:any = ''
  remember_question_properties = false
  properties: FormGroup;
  questionSettings:any = {}

  message: string = 'Snack Bar opened.';
  actionButtonLabel: string = 'Retry';
  action: boolean = true;
  setAutoHide: boolean = true;
  autoHide: number = 2000;
  // horizontalPosition: MatSnackBarHorizontalPosition = 'center';
  // verticalPosition: MatSnackBarVerticalPosition = 'bottom';
  addExtraClass: boolean = false;
  historyObject:any
  returnUrl='questions-list'
  solutionFileToUpload: File = null;
  solutionUpdated: boolean = false;
  solutionImage: string = null;

  constructor(
    private _Activatedroute:ActivatedRoute,
    private _questionService : QuestionService,
    private _fileUploadService: FileUploadService,
    private fb: FormBuilder,
    private router: Router,
    // private _snackBar: MatSnackBar,
    private _examSharing:ExamSharingService,
    private toastr: ToastrService
    ) { this.initForm(); }

  ngOnInit() {

    $(".custom-file-input").on("change", function() {
      var fileName = $(this).val().split("\\").pop();
      $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
    });


    this.questionId = this._Activatedroute.snapshot.paramMap.get("questionId");
    console.log('questionId  ', this.questionId);
    this.historyObject = this._examSharing.getEditQuestion();
    console.log('historyObject  ', this.historyObject)
    if(this.historyObject && this.questionId)
      this.returnUrl = this.historyObject.returnUrl
    if(this.questionId == null)
      this.historyObject = null;
    this.listSubjects();
    var OBJECT = this;
    (function() {
      var mathElements = [
        'math',
        'maction',
        'maligngroup',
        'malignmark',
        'menclose',
        'merror',
        'mfenced',
        'mfrac',
        'mglyph',
        'mi',
        'mlabeledtr',
        'mlongdiv',
        'mmultiscripts',
        'mn',
        'mo',
        'mover',
        'mpadded',
        'mphantom',
        'mroot',
        'mrow',
        'ms',
        'mscarries',
        'mscarry',
        'msgroup',
        'msline',
        'mspace',
        'msqrt',
        'msrow',
        'mstack',
        'mstyle',
        'msub',
        'msup',
        'msubsup',
        'mtable',
        'mtd',
        'mtext',
        'mtr',
        'munder',
        'munderover',
        'semantics',
        'annotation',
        'annotation-xml'
      ];
      
      CKEDITOR.plugins.addExternal('ckeditor_wiris', 'https://ckeditor.com/docs/ckeditor4/4.13.0/examples/assets/plugins/ckeditor_wiris/', 'plugin.js');
      CKEDITOR.plugins.addExternal('mathjax', 'https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/MathJax.js?config=TeX-AMS_HTML', 'plugin.js');
      let editor = CKEDITOR.replace('editor', {
                extraPlugins: 'ckeditor_wiris',
                // For now, MathType is incompatible with CKEditor file upload plugins.
                removePlugins: 'uploadimage,uploadwidget,uploadfile,filetools,filebrowser',
                height: 320,
                // Update the ACF configuration with MathML syntax.
                extraAllowedContent: mathElements.join(' ') + '(*)[*]{*};img[data-mathml,data-custom-editor,role](Wirisformula);input[name1]'
              });
      editor.ui.addButton('SuperButton', {
          label: "Radio button",
          command: 'commandToAddBreakLine',
          toolbar: 'forms',
          icon: 'https://img.icons8.com/material/24/000000/unchecked-radio-button--v1.png'
      });
      editor.addCommand('commandToAddBreakLine', { exec: function(editor){
        var value = getNextValue()
        var option = getNextOption()
        var type = getType();
        editor.insertHtml('<span>'+value+'). <input type="'+type+'" name="'+option+'" value="'+value+'"/>   </span>')
      } });
      function getType() {
        var selectedQuestionType = OBJECT.selected_question_type;
        if(selectedQuestionType == 'Single Select')
          return 'radio';
        else if(selectedQuestionType == 'Multiple Select')
          return 'checkbox'
      }
      function getNextValue() {
        var data = CKEDITOR.instances.editor.getData();
        var div = $('<div></div>')
        div.html(data)
        var type = getType();
        return div.find('input[type="'+type+'"]').length + 1;
      }
      function getNextOption() {
        var optionName = OBJECT.questionId != null ? OBJECT.questionId : OBJECT.questionCounter;
        var selectedQuestionType = OBJECT.selected_question_type;
        var questionId = OBJECT.questionId
        var data = CKEDITOR.instances.editor.getData();
        var div = $('<div></div>')
        div.html(data)
        var index = div.find('input[type="radio"]').length + 1
        if(selectedQuestionType == 'Single Select')
          return 'option_'+optionName;
        else if(selectedQuestionType == 'Multiple Select')
          {var value = getNextValue()
          return 'option_'+optionName+'_'+value}
        // $(input).attr('data-question-id', questionId)
      }
    }());
  }

  ngOnDestroy(){
    $("body>#deleteConfirmationModal").remove();
  }

  save(){
    // CKEDITOR.instances.editor.toolbar[3].items[2].click()
    var optionName = this.questionId != null ? this.questionId : this.questionCounter;
    console.log(CKEDITOR.instances.editor)
    var data = CKEDITOR.instances.editor.getData();
    console.log(data);
    // console.log(CKEDITOR.instances.editor.getHTML())
    // data = data.substring(3, data.length - 5)
    if(data == null || data.length == 0){
      this.openSnackBar('warn', '', 'Question is empty!!!');
      return;
    }
    var div = $('<div></div>')
    div.html(data)
    console.log(this.properties.value)
    let question = this.formateQuestionModal(data);
    // return;
    $('#spinner').css('display', 'flex')
    this._questionService.addQuestion(
      question
      ).subscribe((response) => {
      console.log(response);
      // alert('Saved Successfully');
      // if( this.solutionFileToUpload != null ) {
      //   this.uploadFiles();
      // } else {
        
      // }
      this._examSharing.setEditQuestion(this.historyObject)
      $('#spinner').hide()
      if(this.questionId == null) 
        this.openSnackBar('success', 'Success', 'Question added successfully!!!');
      else
        this.openSnackBar('success', 'Success', 'Question updated successfully!!!');
      this.router.navigateByUrl('/editor/'+response['data']['id'])
    }, (error) => {
      $('#spinner').hide()
      console.log(error);
      // alert('Error while saving. Please try again in sometime or contact to admin');
      if(this.questionId == null) 
        this.openSnackBar('error', 'Error', 'Failed to add question. Please try again in sometime or contact to admin!!!');
      else
        this.openSnackBar('error', 'Error', 'Failed to update question. Please try again in sometime or contact to admin!!!');
    });


  }

  cancel() {
      this._examSharing.setEditQuestion(this.historyObject)
      this.router.navigateByUrl(this.returnUrl);
  }

  deleteConfirmation() {
    $('#deleteConfirmationModal').modal('show')
    $("#deleteConfirmationModal").appendTo("body");
  }
  delete() {
    if(this.questionId != null) {
      $('#spinner').css('display', 'flex')
      this._questionService.deleteQuestion({'list': [this.questionId]}).subscribe((response) => {
        console.log(response);
        this.openSnackBar('success', 'Success', 'Question deleted successfully!!!');
        $('#deleteConfirmationModal').modal('hide');
        $(".modal-backdrop").remove();
        // $("body>#deleteConfirmationModal").remove();
        this._examSharing.setEditQuestion(this.historyObject)
        this.router.navigateByUrl(this.returnUrl);
        $('#spinner').hide()
        this.cancel();
      }, (error) => {
        console.log(error);
        $('#spinner').hide()
        this.openSnackBar('error', 'Error', 'Failed to delete question. Please try again in sometime or contact to admin!!!');
        $('#deleteConfirmationModal').modal('hide');
        // $("body>#deleteConfirmationModal").remove();
      });
    } else {
      this.openSnackBar('warn', '', 'No question is selected to delete.!!!');
    }
  }

  formateQuestionModal(data) {
    let selectedQuestionType = this.properties.value.question_type;
    let questionType = this.question_types.find((data: any) => data.name == selectedQuestionType);
    
    let selectedSubject = this.properties.value.subject;
    let subject = this.subjects.find((data: any) => data.name == selectedSubject);

    let selectedChapter = this.properties.value.chapter;
    let chapter = this.chapters.find((data: any) => data.name == selectedChapter);

    let selectedDiffLevel = this.properties.value.difficulty_level;
    let diffLevel = this.difficulty_levels.find((data: any) => data.name == selectedDiffLevel);
    
    let answer = this.properties.value.answer;

    return {
      'question' : data,
      'id':this.questionId,
      'subject': subject,
      'chapter': chapter,
      'questionType': questionType,
      'difficultyLevel': diffLevel,
      'answer': answer,
      'questionSettings': {
        'id':this.questionSettings['id'],
        'rememberQuestionProperties': this.remember_question_properties,
        'subject': subject,
        'chapter': chapter,
        'questionType': questionType,
        'difficultyLeval': diffLevel
      }
    }
  }

  getQuestion(questionId) {
    this._questionService.getQuestion(questionId).subscribe((response) => {
      console.log(response);
      this.selected_question_type = response['questionType']['name']
      this.selected_diff_level = response['difficultyLevel']['name']
      this.selected_subject = response['subject']['name']
      let subject = this.subjects.find((data: any) => data.name === this.selected_subject);
      console.log('subject', subject)
      if(subject)
        this.chapters = subject.chapters 
      else
        this.chapters = []
      this.selected_chapter = response['chapter']['name']
      this.selected_answer = response['answer']
      this.solutionImage = response['solution']
      console.log(this.properties.value)
      CKEDITOR.instances.editor.setData(response['question'])
      $('#spinner').hide()
    }, (error) => {
      console.log(error);
      $('#spinner').hide()
    });
  }

  // getQuestionCounter() {
  //   this._questionService.getQuestionCounter().subscribe((response) => {
  //     console.log(response)
  //     this.questionCounter = response['counter'] + 1
  //   }, (error) => {
  //     console.log(error);
  //   });
  // }

  listSubjects() {
    $('#spinner').css('display', 'flex')
    this._questionService.getProperty().subscribe((response) => {
      console.log(response)
      this.subjects = response['subjects']
      this.question_types = response['questionTypes']
      this.difficulty_levels = response['difficultyLevels']
      this.questionCounter = response['counter']['counter']
      this.questionSettings = response['questionSettings']
      this.remember_question_properties = this.questionSettings['rememberQuestionProperties']
      
      if(this.remember_question_properties) {
        this.selected_subject = this.questionSettings['subject']['name']
        let subject = this.subjects.find((data: any) => data.name === this.selected_subject);
        this.chapters = subject['chapters']
        this.selected_chapter = this.questionSettings['chapter']['name']
        this.selected_question_type = this.questionSettings['questionType']['name']
        this.selected_diff_level = this.questionSettings['difficultyLeval']['name']
      }
      if(this.questionId != null) {
        this.getQuestion(this.questionId)
      } else {
        $('#spinner').hide()
        // set properties
      }
    }, (error) => {
      $('#spinner').hide()
      console.log(error);
    });
  }

  setChapters() {
    let selectedSubject = this.properties.value.subject;
    let subject = this.subjects.find((data: any) => data.name === selectedSubject);
    this.chapters = []
    if(subject)
      this.chapters = subject.chapters
    if( this.chapters.length > 0 )  
      this.selected_chapter = this.chapters[0].name
  }

  initForm() {
    this.properties = this.fb.group({
      answer: ['', [Validators.required]],
      question_type: ['', [Validators.required]],
      subject: ['', [Validators.required]],
      chapter: ['', [Validators.required]],
      difficulty_level: ['', [Validators.required]],
      remember_question_properties:['false']
    })
  }
  
  get subject() { return this.properties.get('subject'); }

  get chapter() { return this.properties.get('chapter'); }

  get question_type() { return this.properties.get('question_type'); }

  get difficulty_level() { return this.properties.get('difficulty_level'); }

  get answer() { return this.properties.get('answer'); }

  handleSolutionFileInput(files: FileList) {
    if(files.length > 0) {
      $('#solutionFileLabel').html(files.item(0).name)
      this.solutionUpdated = true;
    } else {
      $('#solutionFileLabel').html("Add Solution")
      this.solutionUpdated = false;
    }
    this.solutionFileToUpload = files.item(0);
  }

  uploadFiles() {
    let formData = new FormData();
    formData.append('questionSolution', this.solutionFileToUpload, this.solutionFileToUpload.name);
    formData.append("questionId", this.questionId)
    formData.append("fileName", this.solutionFileToUpload.name)
    $('#spinner').show()
    this._fileUploadService.uploadFile(
      formData
      ).subscribe((response) => {
      console.log(response);
      this.solutionImage = response['solution']
      // alert('Saved Successfully');
      this._examSharing.setEditQuestion(this.historyObject)
      $('#spinner').hide()
      this.openSnackBar('success', 'Success', 'Solution added successfully!!!');
      $('#solutionFileLabel').html("Add Solution")
      this.solutionUpdated = false;
      this.router.navigateByUrl('/editor/'+this.questionId)
    }, (error) => {
      $('#spinner').hide()
      console.log(error);
      this.openSnackBar('error', 'Error', 'Failed to add solution. Please try again in sometime or contact to admin!!!');
    });
  }

  deleteSolutionsModal() {
    $('#deleteSolutionConfirmationModal').modal('show')
  }
  deleteSolutions() {
    $('#deleteSolutionConfirmationModal').modal('hide')
    $('#spinner').show()
    this._questionService.deleteQuestionSolution(
      this.questionId
      ).subscribe((response) => {
      console.log(response);
      this.solutionImage = null
      // alert('Saved Successfully');
      this._examSharing.setEditQuestion(this.historyObject)
      $('#spinner').hide()
      this.openSnackBar('success', 'Success', 'Solution deleted successfully!!!');
      $('#solutionFileLabel').html("Add Solution")
      this.solutionUpdated = false;
      this.router.navigateByUrl('/editor/'+this.questionId)
    }, (error) => {
      $('#spinner').hide()
      console.log(error);
      this.openSnackBar('error', 'Error', 'Failed to delete solution. Please try again in sometime or contact to admin!!!');
    });
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
}

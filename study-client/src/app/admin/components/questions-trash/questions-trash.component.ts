import { Component, ViewChild, OnInit, OnDestroy } from '@angular/core';
import { QuestionService } from '../../services/question.service';
import { Router} from '@angular/router';
import { ToastrService } from 'ngx-toastr';
// import {MatSnackBar,MatSnackBarConfig} from '@angular/material/snack-bar';
declare var $ : any;
declare var MathJax : any

@Component({
  selector: 'app-questions-trash',
  templateUrl: './questions-trash.component.html',
  styleUrls: ['./questions-trash.component.css']
})
export class QuestionsTrashComponent implements OnInit {

  dataTable: any;
  constructor(
    private _questionService : QuestionService,
    private router: Router,
    // private _snackBar: MatSnackBar,
    private toastr: ToastrService
    ) { }

  questions : any = [];
  questionImages = {}
  deleteQuestions =  [];

  ngOnInit() {
    this.listAllQuestion();
  }


  ngOnDestroy(){
    $("body>#deleteConfirmationModal").remove();
  }

  listAllQuestion(){
    $('#spinner').css('display', 'flex')
    let questionsListComponent = this
    this._questionService.listQuestionsTrash().subscribe((response:[]) => {
      console.log(response);
      this.questions = response['questions']
      this.questionImages = response['questionImages']
      var body = $('#maths');
      body.html('');
      for(var i = 0 ; i< this.questions.length;i++) {
        var question = this.questions[i];
        var tr = $('<tr></tr>');
        tr.attr('data-question-id', question['id'])
        var td0 = $('<td></td>');
        var td1 = $('<td></td>');
        td1.html(i+1);
        var td2 = $('<td></td>');
        // td2.html(this.getImage(question));
        td2.html(question['question']);
        // let editQuestion= this.editQuestion;
        // td2.dblclick(function(){editQuestion(this, questionsListComponent)})
        tr.append(td0);
        tr.append(td1);
        tr.append(td2);
        MathJax.Hub.Queue(["Typeset",MathJax.Hub,td2[0]]);
        body.append(tr);
      }
      // this.dataTable = $(this.table.nativeElement);
      var deleteQuestions = this.deleteQuestions
      var openSnackBar = this.openSnackBar;
      // var _snackBar = null 
    $('#questionTable tbody tr td:first-child').on('click', function(){
      var questionId = $(this).closest('tr').attr('data-question-id');
      questionId = parseInt(questionId)
      console.log(questionId)
      if(deleteQuestions.indexOf(questionId) > -1) {
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
      let deleteSelected = this.deleteSelected
      this.dataTable = $('#questionTable').DataTable( {
        "fnDrawCallback": function( oSettings ) {
          $('.dt-buttons').addClass('col-md-6')
          $('.dt-button').css('float', 'left')
          $('.dt-buttons').css('padding-left', '0px')
          $('.dt-button').css('margin-bottom', '.5rem')
          // $('.dt-buttons').hide()
        },
        fixedHeader: true,
        columnDefs: [ {
            orderable: false,
            className: 'select-checkbox',
            targets:   0
        } ],
        select: {
            style:    'multi',
            selector: 'td:first-child'
        },
        order: [[ 1, 'asc' ]],
        dom: 'Bfrtip',
        buttons: {
          className:'col-md-6',
          buttons: [
            {
                text: 'Restore Selected Questions',
                className:'btn btn-danger',
                action: function ( e, dt, node, config ) {

                  if(deleteQuestions.length == 0){
                    openSnackBar('No question selected to restore!!!', 'alert-warning', null);
                    $('#spinner').hide()
                    $('#deleteConfirmationModal').modal('hide');
                    $(".modal-backdrop").remove();
                    return
                  }
                  $('#deleteConfirmationModal').modal('show')
                  $("#deleteConfirmationModal").appendTo("body");
                }
            }
        ]
        }
    } );

      $('#spinner').hide()
    }, (error) => {
      $('#spinner').hide()
      this.openSnackBar('error', 'Error', 'Failed to load questions. Please try again in sometime or contact to admin!!!');
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

  editQuestion(questionNode, questionsListComponent) {
    console.log(questionNode)
    // alert('edit mode  '+ $(questionNode).attr('data-question-id'))
    let questionId = $(questionNode).closest('tr').attr('data-question-id')
    $('#spinner').css('display', 'flex')
    questionsListComponent.router.navigateByUrl('/editor/'+questionId);
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
    
    this._questionService.restoreQuestion({'list':this.deleteQuestions}).subscribe((response) => {
      console.log(response);
      this.openSnackBar('success', 'Success', 'Questions restored successfully!!!');
      $('#deleteConfirmationModal').modal('hide');
      $(".modal-backdrop").remove();
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

}

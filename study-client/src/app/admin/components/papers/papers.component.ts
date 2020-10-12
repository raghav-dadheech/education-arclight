import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { QuestionService } from '../../services/question.service';
import { ToastrService } from 'ngx-toastr';
// import { MatSnackBarConfig, MatSnackBar } from '@angular/material';
declare var $: any;

@Component({
  selector: 'app-papers',
  templateUrl: './papers.component.html',
  styleUrls: ['./papers.component.css']
})
export class PapersComponent implements OnInit {

  constructor(
    private _router: Router,
    private _questionService: QuestionService,
    // private _snackBar: MatSnackBar,
    private toastr: ToastrService
  ) { }

  exam_papers = []
  deletePaper={}
  ngOnInit() {
    this.listExamPapers();
  }

  listExamPapers() {
    $('#spinner').css('display', 'flex')
    this._questionService.getExamPapers().subscribe((response:[]) => {
      console.log(response)
      this.exam_papers = response;
      $('#spinner').hide()
    }, (error) => {
      $('#spinner').hide()
      this.openSnackBar('error', 'Error', 'Failed to load questions. Please try again in sometime or contact to admin!!!');
      console.log(error);
    });
  }

  goToPaperQuestions(paperId) {
    this._router.navigateByUrl('/paper-questions/' + paperId);
  }

  confirmDelete(paper) {
    this.deletePaper = paper;
    $('#deleteConfirmationModal').modal('show')
    $("#deleteConfirmationModal").appendTo("body");
  }

  delete() {
    $('#deleteConfirmationModal').modal('hide');
    $(".modal-backdrop").remove();
    $('#spinner').css('display', 'flex')
    this._questionService.deleteExamPaper(this.deletePaper['id']).subscribe((response:[]) => {
      $('#spinner').hide()
      console.log(response);
      this.exam_papers = response;
      this.openSnackBar('success', 'Success', 'Exam paper "'+this.deletePaper['name']+'" deleted successfully!!!');
    }, (error) => {
      console.log(error);
      $('#spinner').hide()
      this.openSnackBar('error', 'Error', 'Failed to delete "'+this.deletePaper['name']+'". Please try again in sometime or contact to admin!!!');
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

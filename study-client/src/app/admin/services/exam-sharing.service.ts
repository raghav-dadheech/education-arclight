import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ExamSharingService {

  constructor() { }

  examProperties:any
  editQuestion:any

  setExamProperties(examProperties) {
    this.examProperties = examProperties
  }

  getExamProperties() {
    return this.examProperties
  }

  setEditQuestion(editQuestion) {
    this.editQuestion = editQuestion
  }

  getEditQuestion() {
    return this.editQuestion;
  }
}

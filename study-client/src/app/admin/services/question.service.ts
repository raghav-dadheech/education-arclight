import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { environment } from "../../../environments/environment";
import { Observable } from "rxjs";
import 'rxjs/Rx';
import { map } from 'rxjs/operators';
import { strictEqual } from 'assert';

@Injectable()
export class QuestionService {

    private baseUrl: string = environment.BASE_URL + 'questionnaire'
    private property_baseUrl: string = environment.BASE_URL + 'question-property'
    private exam_baseUrl: string = environment.BASE_URL + 'exam-paper'
    constructor(private _http: HttpClient) {

    }

    listQuestions(filter) {
        let headers = {
            headers: {
                'Content-Type': 'application/json'
            }
        };
        return this._http.post(this.baseUrl + "/list-questions",JSON.stringify(filter), headers).pipe(
            map(response => {return response}));
    }

    listQuestionsTrash() {
        return this._http.get(this.baseUrl + "/list-questions-trash").pipe(
            map(response => {return response}));
    }

    getProperty() {
        return this._http.get(this.property_baseUrl + "/properties").pipe(
            map(response => {return response}));
    }

    getQuestion(questionId: BigInteger) {
        return this._http.get(this.baseUrl + "/question",{
            params: {
                questionId: questionId.toString()
            }
          }).pipe(
            map(response => {return response}));
    }

    deleteQuestion(questionList: any) {
        let headers = {
            headers: {
                'Content-Type': 'application/json'
            }
        };
        return this._http.post(this.baseUrl + "/delete",JSON.stringify(questionList), headers).pipe(
            map(response => {return response}));
    }

    deleteQuestionSolution(questionId: BigInteger) {
        return this._http.get(this.baseUrl + "/delete-solution",{
            params: {
                questionId: questionId.toString()
            }
          }).pipe(
            map(response => {return response}));
    }

    restoreQuestion(questionList: any) {
        let headers = {
            headers: {
                'Content-Type': 'application/json'
            }
        };
        return this._http.post(this.baseUrl + "/restore",JSON.stringify(questionList), headers).pipe(
            map(response => {return response}));
    }

    getQuestionCounter() {
        return this._http.get(this.baseUrl + "/question-counter").pipe(
            map(response => {return response}));
    }

    addQuestion(question: any) {
        let headers = {
            headers: {
                'Content-Type': 'application/json'
            }
        };
        return this._http.post(this.baseUrl + "/add-question", JSON.stringify(question), headers).pipe(
            map(response => {return response}));
    }

    errorHandler(error: Response) {
        return Observable.throw(error || 'SERVER ERROR');
    }

    updateApproveStatus(questionList: any) {
        let headers = {
            headers: {
                'Content-Type': 'application/json'
            }
        };
        return this._http.post(this.baseUrl + "/update-approval-status",JSON.stringify(questionList), headers).pipe(
            map(response => {return response}));
    }

    // Exam section

    listExamPracticeQuestions(data:any) {
        let headers = {
            headers: {
                'Content-Type': 'application/json'
            }
        };
        return this._http.post(this.exam_baseUrl + "/list-exam-practice-questions",JSON.stringify(data), headers).pipe(
            map(response => {return response}));
    }

    listExamPaperQuestions(data:any) {
        let headers = {
            headers: {
                'Content-Type': 'application/json'
            }
        };
        return this._http.post(this.exam_baseUrl + "/list-exam-paper-questions",JSON.stringify(data), headers).pipe(
            map(response => {return response}));
    }

    addExamPaperQuestions(data: any) {
        let headers = {
            headers: {
                'Content-Type': 'application/json'
            }
        };
        return this._http.post(this.exam_baseUrl + "/add-exam-paper-questions", JSON.stringify(data), headers).pipe(
            map(response => {return response}));
    }

    getExamPapers() {
        return this._http.get(this.exam_baseUrl + "/list-exam-papers").pipe(
            map(response => {return response}));
    }

    deleteExamPaper(paperId: BigInteger) {
        return this._http.get(this.exam_baseUrl + "/delete-exam-paper",{
            params: {
                paperId: paperId.toString()
            }
          }).pipe(
            map(response => {return response}));
    }

    getExamPaper(data: any) {
        let headers = {
            headers: {
                'Content-Type': 'application/json'
            }
        };
        return this._http.post(this.exam_baseUrl + "/get-exam-paper",JSON.stringify(data), headers).pipe(
            map(response => {return response}));
    }

    deleteExamPaperQuestions(data: any) {
        let headers = {
            headers: {
                'Content-Type': 'application/json'
            }
        };
        return this._http.post(this.exam_baseUrl + "/delete-exam-paper-questions",JSON.stringify(data), headers).pipe(
            map(response => {return response}));
    }
}
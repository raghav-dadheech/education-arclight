import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { environment } from "../../environments/environment";
import { Observable } from "rxjs";
import 'rxjs/Rx';
import { map } from 'rxjs/operators';
import { strictEqual } from 'assert';

@Injectable()
export class UserService {

    private baseUrl: string = environment.BASE_URL + 'authenticate'
    private resetPasswordObject = null;
    constructor(private _http: HttpClient) {

    }

    login(data) {
        let headers = {
            headers: {
                'Content-Type': 'application/json'
            }
        };
        return this._http.post(this.baseUrl + "/login",JSON.stringify(data), headers).pipe(
            map(response => {return response}));
    }

    register(data) {
        let headers = {
            headers: {
                'Content-Type': 'application/json'
            }
        };
        return this._http.post(this.baseUrl + "/register",JSON.stringify(data), headers).pipe(
            map(response => {return response}));
    }

    forgetPassword(data) {
        let headers = {
            headers: {
                'Content-Type': 'application/json'
            }
        };
        return this._http.post(this.baseUrl + "/forget-password",JSON.stringify(data), headers).pipe(
            map(response => {return response}));
    }

    resetPassword(data) {
        let headers = {
            headers: {
                'Content-Type': 'application/json'
            }
        };
        return this._http.post(this.baseUrl + "/reset-password",JSON.stringify(data), headers).pipe(
            map(response => {return response}));
    }

    isUserLoggedIn() {
        let user = sessionStorage.getItem('username')
        //console.log(!(user === null))
        return !(user === null)
    }

    logOut() {
        sessionStorage.removeItem('username')
    }

    setResetPassword(resetPasswordObject) {
        this.resetPasswordObject = resetPasswordObject;
    }

    getResetPassword() {
        return this.resetPasswordObject;
    }
    
}
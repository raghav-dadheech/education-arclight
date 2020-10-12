import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class AppConstants {
    public PASSWORD_MIN_LENGTH = 6;
    public PASSWORD_MAX_LENGTH = 12;
    public MAX_OTP_LENGTH = 6;
    public MAX_LENGTH_50 = 50;
    public MAX_LENGTH_10 = 10;
    public MAX_LENGTH_100 = 100;
    public FIELD_EMPTY_ERROR = "* Field can not be empty";
    public PASSWORD_ERROR = 'Minimum length should be '+this.PASSWORD_MIN_LENGTH+' and Maximum length should be ' + this.PASSWORD_MAX_LENGTH;
    public PASSWORD_NOT_MATCHED_ERROR = 'Password not matched';
    public INVALID_EMAIL_ADDRESS_ERROR = 'Invalid email address';
    public INVALID_PHONE_NUMBER_ERROR = 'Invalid phone number';
    public MAX_OTP_LENGTH_ERROR = 'Invalid OTP'
    public MAX_ACCESS_CODE_LENGTH_ERROR = 'Invalid invitatio code'
    public MAX_LENGTH_50_ERROR = 'Length must not be greater than 50'
    public MAX_LENGTH_12_ERROR = 'Length must not be greater than 12'
    public MAX_LENGTH_100_ERROR = 'Length must not be greater than 100'


    public isPhone(text) {
        return /^\d+$/.test(text);
    }

    public getUser() {
        console.log("sessionStorage.getItem('user')  ", sessionStorage.getItem('user'))
        return JSON.parse(sessionStorage.getItem('user'))
    }

    public userFullName() {
        var user = JSON.parse(sessionStorage.getItem('user'))
        if(user != null) {
            return user['firstName'] + " " + user['lastName']
        } else {
            return 'Annonymous'
        }
    }

    public isProfileComple() {
        var user = JSON.parse(sessionStorage.getItem('user'))
        if(user != null) {
            return user['profileComplete'];
        } 
        return false;
    }
}
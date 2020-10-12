import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { UserService } from 'src/app/services/user.service';
import { AppConstants } from 'src/app/services/AppConstants';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit {

  constructor(private router: Router,
    private _userService : UserService,
    private toastr: ToastrService,
    private AppConstants : AppConstants) { }

  password = null;
  retypePassword = null;
  otp = null;

  resetPasswordObject = null;
  emailOrPhone = null;
  ngOnInit() {
    this.resetPasswordObject = this._userService.getResetPassword();
    console.log(this.resetPasswordObject);
    if(this.resetPasswordObject == null) {
      this.router.navigateByUrl('/forget-password');
    } else {
      this.emailOrPhone = this.resetPasswordObject['emailOrPhone']
    }

  }

  resetPassword() {
    if(!this.validate()) {
      return;
    }
    var data = {};
    if(this.resetPasswordObject['emailOrPhoneType'] == "email") {
      data['email'] = this.emailOrPhone;
    } else {
      data['phone'] = this.emailOrPhone;
    }
    data['token'] = this.otp;
    data['password'] = this.password;
    $('#spinner').show()
    this._userService.resetPassword(
      data
      ).subscribe((response) => {
        $('#spinner').hide()
      console.log(response);
      if(response["status"] == "success") {
        this.openSnackBar('success', 'Success', response["message"]);
        this.router.navigateByUrl('/login');
      } else {
        this.openSnackBar('error', 'Error', response["message"]);  
      }
    }, (error) => {
      $('#spinner').hide()
      console.log(error);
      this.openSnackBar('error', 'Error', 'User does not exist');
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

  validate() {
    var valid = true;

    $("#otp").removeAttr("style");
    $('#otpError').addClass("d-none");

    $("#password").removeAttr("style");
    $('#passwordError').addClass("d-none");

    $("#retypePassword").removeAttr("style");
    $('#retypePasswordError').addClass("d-none");

    
    if(this.otp == null || this.otp.trim().length == 0) {
      $("#otp").attr("style", "border: 1px solid #dc3545;border-radius: .25rem;");
      $('#otpError').removeClass("d-none");
      $('#otpError').html(this.AppConstants.FIELD_EMPTY_ERROR); 
      valid = false;
    } else if(this.otp.length != this.AppConstants.MAX_OTP_LENGTH) {
      $("#otp").attr("style", "border: 1px solid #dc3545;border-radius: .25rem;");
      $('#otpError').removeClass("d-none");
      $('#otpError').html(this.AppConstants.MAX_OTP_LENGTH_ERROR); 
      valid = false;
    }

    if(this.password == null || this.password.trim().length == 0 || this.password.trim().length < this.AppConstants.PASSWORD_MIN_LENGTH || this.password.trim().length > this.AppConstants.PASSWORD_MAX_LENGTH) {
      $("#password").attr("style", "border: 1px solid #dc3545;border-radius: .25rem;");
      $('#passwordError').removeClass("d-none");
      if(this.password == null || this.password.trim().length == 0)
        $('#passwordError').html(this.AppConstants.FIELD_EMPTY_ERROR);
      else 
        $('#passwordError').html(this.AppConstants.PASSWORD_ERROR);
      valid = false;
    }

    if(this.retypePassword == null || this.retypePassword.trim().length == 0 || this.retypePassword.trim().length < this.AppConstants.PASSWORD_MIN_LENGTH || this.retypePassword.trim().length > this.AppConstants.PASSWORD_MAX_LENGTH || this.password != this.retypePassword) {
      $("#retypePassword").attr("style", "border: 1px solid #dc3545;border-radius: .25rem;");
      $('#retypePasswordError').removeClass("d-none");
      if(this.retypePassword == null || this.retypePassword.trim().length == 0)
        $('#retypePasswordError').html(this.AppConstants.FIELD_EMPTY_ERROR);
      else if(this.password != this.retypePassword)
      $('#retypePasswordError').html(this.AppConstants.PASSWORD_NOT_MATCHED_ERROR);
      else
        $('#retypePasswordError').html(this.AppConstants.PASSWORD_ERROR);
      valid = false;
    }
    return valid;  
  }
}

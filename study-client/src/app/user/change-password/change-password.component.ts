import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';
import { ToastrService } from 'ngx-toastr';
import { AppConstants } from 'src/app/services/AppConstants';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {

  constructor(private router: Router,
    private _userService : UserService,
    private toastr: ToastrService,
    private AppConstants : AppConstants) { }

  oldPassword = null;
  password = null;
  retypePassword = null;
  emailOrPhone = null;
  ngOnInit() {
    this.emailOrPhone = sessionStorage.getItem("username");
  }


  changePassword() {
    if(!this.validate()) {
      return;
    }
    var data = {};
    
    if(this.AppConstants.isPhone(this.emailOrPhone)) {
      data['phone'] = this.emailOrPhone;
    } else {
      data['email'] = this.emailOrPhone;
    }
    data['password'] = this.password;
    data['oldPassword'] = this.oldPassword;
    $('#spinner').show()
    this._userService.resetPassword(
      data
      ).subscribe((response) => {
        $('#spinner').hide()
      console.log(response);
      if(response["status"] == "success") {
        this.openSnackBar('success', 'Success', response["message"]);
        // this.router.navigateByUrl('/login');
      } else {
        this.openSnackBar('error', 'Error', response["message"]);  
      }
    }, (error) => {
      $('#spinner').hide()
      console.log(error);
      this.openSnackBar('error', 'Error', 'User does not exist');
    });
  }

  validate() {
    var valid = true;

    $("#oldPassword").removeAttr("style");
    $('#oldPasswordError').addClass("d-none");

    $("#password").removeAttr("style");
    $('#passwordError').addClass("d-none");

    $("#retypePassword").removeAttr("style");
    $('#retypePasswordError').addClass("d-none");


    if(this.oldPassword == null || this.oldPassword.trim().length == 0 || this.oldPassword.trim().length < this.AppConstants.PASSWORD_MIN_LENGTH || this.oldPassword.trim().length > this.AppConstants.PASSWORD_MAX_LENGTH) {
      $("#oldPassword").attr("style", "border: 1px solid #dc3545;border-radius: .25rem;");
      $('#oldPasswordError').removeClass("d-none");
      if(this.oldPassword == null || this.oldPassword.trim().length == 0)
        $('#oldPasswordError').html(this.AppConstants.FIELD_EMPTY_ERROR);
      else 
        $('#oldPasswordError').html(this.AppConstants.PASSWORD_ERROR);
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

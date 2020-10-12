import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { UserService } from 'src/app/services/user.service';
import { ToastrService } from 'ngx-toastr';
import { AppConstants } from 'src/app/services/AppConstants';
import { AuthService } from 'src/app/services/auth.service';
import { User } from 'src/app/modals/user';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm;

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private _userService: UserService,
    private toastr: ToastrService,
    private AppConstants : AppConstants,
    private authService : AuthService
    ) {
    this.loginForm = this.formBuilder.group({
      userName: null,
      password: null
    });
  }

  ngOnInit() {
    $('body').attr('class', 'hold-transition login-page body-img')
  }

  login() {
    if(!this.validate()) {
      return;
    }
    $('#spinner').show()
    console.warn('Login successfully', this.loginForm.value);
    this._userService.login(
      this.loginForm.value
      ).subscribe((response) => {
        $('#spinner').hide()
      console.log(response);
      sessionStorage.setItem('username',this.loginForm.get("userName").value);
      let tokenStr= 'Bearer '+response['token'];
      sessionStorage.setItem('token', tokenStr);
      sessionStorage.setItem('user', JSON.stringify(response));
      this.router.navigateByUrl('/dashboard');
    }, (error) => {
      $('#spinner').hide()
      console.log(error);
      this.openSnackBar('error', 'Error', 'Invalid credentials');
    });
  }

  validate() {
    var valid = true;

    $("#password").removeAttr("style");
    $('#passwordError').addClass("d-none");

    $("#userName").removeAttr("style");
    $('#userNameError').addClass("d-none");

    var password = this.loginForm.get('password').value;
    if(password == null || password.trim().length == 0 || password.trim().length < this.AppConstants.PASSWORD_MIN_LENGTH || password.trim().length > this.AppConstants.PASSWORD_MAX_LENGTH) {
      $("#password").attr("style", "border: 1px solid #dc3545;border-radius: .25rem;");
      $('#passwordError').removeClass("d-none");
      if(password == null || password.trim().length == 0)
        $('#passwordError').html(this.AppConstants.FIELD_EMPTY_ERROR);
      else 
        $('#passwordError').html(this.AppConstants.PASSWORD_ERROR);
      valid = false;
    }

    var userName = this.loginForm.get('userName').value;
    if(userName == null || userName.trim().length == 0) {
      $("#userName").attr("style", "border: 1px solid #dc3545;border-radius: .25rem;");
      $('#userNameError').removeClass("d-none");
      $('#userNameError').html(this.AppConstants.FIELD_EMPTY_ERROR);
      valid = false;
    } else if(userName.length > this.AppConstants.MAX_LENGTH_50) {
      $("#userName").attr("style", "border: 1px solid #dc3545;border-radius: .25rem;");
      $('#userNameError').removeClass("d-none");
      $('#userNameError').html(this.AppConstants.MAX_LENGTH_50_ERROR);
      valid = false;
    }
    return valid;  
  }
  validateEmail(email) {
    console.log(email)
    return /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/.test(email.value);
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

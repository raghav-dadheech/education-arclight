import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { AppConstants } from 'src/app/services/AppConstants';

@Component({
  selector: 'app-forget-password',
  templateUrl: './forget-password.component.html',
  styleUrls: ['./forget-password.component.css']
})
export class ForgetPasswordComponent implements OnInit {

  constructor(
    private router: Router,
    private _userService : UserService,
    private toastr: ToastrService,
    private AppConstants : AppConstants
  ) { }

  email = null;
  ngOnInit() {
    $('body').attr('class', 'hold-transition login-page body-img')
  }

  forgetPassword() {
    $("#email").removeAttr("style");
    $('#emailError').addClass("d-none");
    if(this.email == null || this.email.trim().length == 0) {
      $("#email").attr("style", "border: 1px solid #dc3545;border-radius: .25rem;");
      $('#emailError').removeClass("d-none");
      $('#emailError').html(this.AppConstants.FIELD_EMPTY_ERROR); 
      return;
    } else if(this.email.length > this.AppConstants.MAX_LENGTH_50) {
      $("#email").attr("style", "border: 1px solid #dc3545;border-radius: .25rem;");
      $('#emailError').removeClass("d-none");
      $('#emailError').html(this.AppConstants.MAX_LENGTH_50_ERROR); 
      return;
    }
    $('#spinner').show()
      var data = {};
      var type = null;
      if(this.AppConstants.isPhone(this.email)) {
        type = "phone";
        data['phone'] = this.email;
      } else {
        type = "email";
        data['email'] = this.email;
      }
      
      // this.router.navigateByUrl('/reset-password');
      this._userService.forgetPassword(
        data
        ).subscribe((response) => {
          $('#spinner').hide()
        console.log(response);
        if(response["status"] == "success") {
          this.openSnackBar('success', 'Success', response["message"]);
          this._userService.setResetPassword({"emailOrPhoneType" : type, "emailOrPhone" : this.email})
          this.router.navigateByUrl('/reset-password');
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
}

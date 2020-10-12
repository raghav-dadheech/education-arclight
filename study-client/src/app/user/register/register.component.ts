import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { stringToKeyValue } from '@angular/flex-layout/extended/typings/style/style-transforms';
import { UserService } from 'src/app/services/user.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { AppConstants } from 'src/app/services/AppConstants';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  registerForm: FormGroup;

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private _userService : UserService,
    private toastr: ToastrService,
    private AppConstants : AppConstants
  ) { 
    this.registerForm = this.formBuilder.group({
      firstName:['', [Validators.required,Validators.maxLength(this.AppConstants.MAX_LENGTH_50)]],
      lastName:['', [Validators.maxLength(this.AppConstants.MAX_LENGTH_50)]],
      email: ['', [Validators.required,Validators.maxLength(this.AppConstants.MAX_LENGTH_50)]],
      phone: ['', [Validators.required]],
      password: ['', [Validators.required]],
      invitationCodeCheck:[''],
      token:['']
    });
    this.registerForm.valueChanges.subscribe(x => {
      // this.validate();
   })
  }

  disableRegister = false;
  showAccessCode = true;

  ngOnInit() {
    $('body').attr('class', 'hold-transition register-page body-img')
  }


  register() {
    var data = this.registerForm.value;
    console.log(this.registerForm.controls);
    console.log(data)
    if(this.validate()) {
      if(!this.registerForm.get('invitationCodeCheck').value) {
        data['token'] = null;
      }
      $('#spinner').show()
      this._userService.register(
        data
        ).subscribe((response) => {
          $('#spinner').hide()
        console.log(response);
        if(response["status"] == "success") {
          this.openSnackBar('success', 'Success', 'Successfully registered!!');
          this.router.navigateByUrl('/login');
        } else {
          this.openSnackBar('error', 'Error', response["message"]);  
        }
      }, (error) => {
        $('#spinner').hide()
        console.log(error);
        this.openSnackBar('error', 'Error', 'Invalid credentials');
      });
    } else {
      console.log("Validation failed");
    }
  }

  validate() {
    console.log('validating')
    var valid = true;
    if(!this.registerForm.valid) {
      $("#firstName").removeAttr("style");
      $('#firstNameError').addClass("d-none");
  
      $("#lastName").removeAttr("style");
      $('#lastNameError').addClass("d-none");
  
      $("#email").removeAttr("style");
      $('#emailError').addClass("d-none");
  
      $("#phone").removeAttr("style");
      $('#phoneError').addClass("d-none");
  
      $("#password").removeAttr("style");
      $('#passwordError').addClass("d-none");

      $("#accessCode").removeAttr("style");
      $('#accessCodeError').addClass("d-none");

      for(var name in this.registerForm.controls) {
        if(this.registerForm.controls[name].invalid) {
          console.log(name + 'is invalid')
          if(name == 'firstName') {
            $("#firstName").attr("style", "border: 1px solid #dc3545;border-radius: .25rem;");
            $('#firstNameError').removeClass("d-none");
            if(this.registerForm.controls[name].hasError('required'))
              $('#firstNameError').html(this.AppConstants.FIELD_EMPTY_ERROR);
            else
              $('#firstNameError').html(this.AppConstants.MAX_LENGTH_50_ERROR);
            
            valid = false;
          } else if(name == 'lastName') {
            
            $("#lastName").attr("style", "border: 1px solid #dc3545;border-radius: .25rem;");
            $('#lastNameError').removeClass("d-none");
            $('#lastNameError').html(this.AppConstants.MAX_LENGTH_50_ERROR);
            valid = false;
          } else if(name == 'email') {
            
            $("#email").attr("style", "border: 1px solid #dc3545;border-radius: .25rem;");
            $('#emailError').removeClass("d-none");
            if(this.registerForm.controls[name].hasError('required'))
              $('#emailError').html(this.AppConstants.FIELD_EMPTY_ERROR);
            else
              $('#emailError').html(this.AppConstants.MAX_LENGTH_50_ERROR);
            valid = false;
          } else if(name == 'phone') {
            
            $("#phone").attr("style", "border: 1px solid #dc3545;border-radius: .25rem;");
            $('#phoneError').removeClass("d-none");
            $('#phoneError').html(this.AppConstants.FIELD_EMPTY_ERROR);
            valid = false;
          } else if(name == 'password') {
            
            $("#password").attr("style", "border: 1px solid #dc3545;border-radius: .25rem;");
            $('#passwordError').removeClass("d-none");
            $('#passwordError').html(this.AppConstants.FIELD_EMPTY_ERROR);
            valid = false;
          }
        }
      }
    } 
    if(this.registerForm.controls['email'].valid && !this.validateEmail(this.registerForm.get('email'))){
      $("#email").attr("style", "border: 1px solid #dc3545;border-radius: .25rem;");
      $('#emailError').removeClass("d-none");
      $('#emailError').html(this.AppConstants.INVALID_EMAIL_ADDRESS_ERROR);
      valid = false;
    }
    if(this.registerForm.controls['phone'].valid && !this.validatePhone(this.registerForm.get('phone'))){
      $("#phone").attr("style", "border: 1px solid #dc3545;border-radius: .25rem;");
      $('#phoneError').removeClass("d-none");
      $('#phoneError').html(this.AppConstants.INVALID_PHONE_NUMBER_ERROR);
      valid = false;
    }
    if(this.registerForm.controls['password'].valid && !this.validatePasswod(this.registerForm.get('password'))){
      $("#password").attr("style", "border: 1px solid #dc3545;border-radius: .25rem;");
      $('#passwordError').removeClass("d-none");
      $('#passwordError').html(this.AppConstants.PASSWORD_ERROR);
      valid = false;
    }
    if(this.registerForm.get('invitationCodeCheck').value) {
      if(this.registerForm.get('token').value == null ||this.registerForm.get('token').value.length == 0)  {
        $("#accessCode").attr("style", "border: 1px solid #dc3545;border-radius: .25rem;");
        $('#accessCodeError').removeClass("d-none");
        $('#accessCodeError').html(this.AppConstants.FIELD_EMPTY_ERROR);
        valid = false;
      } else if(this.registerForm.get('token').value.length != this.AppConstants.MAX_OTP_LENGTH) {
        $("#accessCode").attr("style", "border: 1px solid #dc3545;border-radius: .25rem;");
        $('#accessCodeError').removeClass("d-none");
        $('#accessCodeError').html(this.AppConstants.MAX_ACCESS_CODE_LENGTH_ERROR);  
        valid = false;
      }
    }
    console.log(this.registerForm.get('invitationCodeCheck').value);
    console.log(this.registerForm.get('token').value);

    
    return valid;
  }

  toggleAccessCode() {
    if(this.registerForm.get('invitationCodeCheck').value)
      this.showAccessCode = false
    else
      this.showAccessCode = true;
  }
  validateEmail(email) {
    console.log(email)
    return /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/.test(email.value);
  }

  validatePhone(phone){
    return phone.value.match(/^\d{10}$/)
  }

  validatePasswod(password) {
    return password.value.length >= this.AppConstants.PASSWORD_MIN_LENGTH && password.value.length <= this.AppConstants.PASSWORD_MAX_LENGTH;
  }

  get firstName() {
    return this.registerForm.get("firstName")
  }

  get lastName() {
    return this.registerForm.get("lastName")
  }

  get email() {
    return this.registerForm.get("email")
  }

  get phone() {
    return this.registerForm.get("phone")
  }

  get password() {
    return this.registerForm.get("password")
  }

  get retyprPassword() {
    return this.registerForm.get("retypePassword")
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

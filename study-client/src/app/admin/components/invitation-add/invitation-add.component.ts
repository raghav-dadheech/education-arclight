import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { CommonService } from '../../services/common.service';
import { AppConstants } from 'src/app/services/AppConstants';

@Component({
  selector: 'app-invitation-add',
  templateUrl: './invitation-add.component.html',
  styleUrls: ['./invitation-add.component.css']
})
export class InvitationAddComponent implements OnInit {

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private toastr: ToastrService,
    private _commonService : CommonService,
    private AppConstants : AppConstants
    ) { this.initForm(); 
  }

  invitationForm: FormGroup;
  selected_organisation:any = ''
  selected_role:any = ''
  selected_username:any = ''
  selected_keyword:any = ''

  organisations:any = []
  roles:any = []

  ngOnInit() {
    this.loadOrganisations();
  }

  loadOrganisations() {
    $('#spinner').css('display', 'flex')
    this._commonService.listOrganisations().subscribe((response) => {
      console.log(response)
      this.organisations = response;
      this.loadUserRoles();
    }, (error) => {
      $('#spinner').hide()
      console.log(error);
    });
  }

  loadUserRoles() {
    $('#spinner').css('display', 'flex')
    this._commonService.listUserRoles().subscribe((response) => {
      console.log(response)
      this.roles = response;
      $('#spinner').hide()
    }, (error) => {
      $('#spinner').hide()
      console.log(error);
    });
  }
  invite() {
    console.log(this.invitationForm.value)
    let organisation = this.organisations.find((data: any) => data.name === this.selected_organisation);
    let role = this.roles.find((data: any) => data.role === this.selected_role);
    console.log(organisation);
    console.log(role);
    var temp = this.invitationForm.get('username').value;
    var data = {}
    if(this.AppConstants.isPhone(temp)) {
      data['phone'] = temp;
    } else {
      data['email'] = temp;
    }
    data['keyword'] = this.invitationForm.get('keyword') .value;
    data['organisation'] = organisation;
    data['role'] = role;
    $('#spinner').css('display', 'flex')
    this._commonService.invite(
      data
      ).subscribe((response) => {
        $('#spinner').hide()
      console.log(response);
      if(response["status"] == "success") {
        this.openSnackBar('success', 'Success', response["message"]);
        this.router.navigateByUrl("/invitations");
      } else {
        this.openSnackBar('error', 'Error', response["message"]);  
      }
    }, (error) => {
      $('#spinner').hide()
      console.log(error);
      this.openSnackBar('error', 'Error', 'User does not exist');
    });
  }

  cancel() {
    this.router.navigateByUrl("/invitations");
  }

  emailOrPhoneValidator(control: FormControl) {
    var value = control.value;
    if(!((/^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/.test(value)))) {
      if(!(value.match(/^\d{10}$/))) {
        return {
          emailOrPhone : {
            message:'Invalid email or phone'
          }
        }
      }
    }
    return null;
  }
  initForm() {
    this.invitationForm = this.fb.group({
      organisation: ['', [Validators.required]],
      role: ['', [Validators.required]],
      username: ['', [Validators.required, Validators.maxLength(50), this.emailOrPhoneValidator]],
      keyword: ['', []]
    })
  }

  get organisation() { return this.invitationForm.get('organisation'); }

  get role() { return this.invitationForm.get('role'); }

  get username() { return this.invitationForm.get('username'); }

  get keyword() { return this.invitationForm.get('keyword'); }

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

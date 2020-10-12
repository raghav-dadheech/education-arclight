import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { AppConstants } from 'src/app/services/AppConstants';
import { CommonService } from '../../services/common.service';
declare var $;
@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  constructor(
    private _Activatedroute:ActivatedRoute,
    private fb: FormBuilder,
    private router: Router,
    private toastr: ToastrService,
    private AppConstants: AppConstants,
    private _commonServive : CommonService
  ) { this.initForm(); }

  profileForm: FormGroup;
  selected_firstName = null;
  selected_lastName = null;
  selected_email = null;
  selected_phone = null;
  selected_dob = null;
  selected_gender = null;
  selected_fatherName = null;
  selected_fatherPhone = null;
  selected_addressLine1 = null;
  selected_addressLine2 = null;
  selected_pincode = null;
  selected_city = null;
  selected_state = null;
  selected_country = null;
  selected_school = null;
  selected_class = null;
  selected_course = null;

  FIELD_EMPTY_ERROR = this.AppConstants.FIELD_EMPTY_ERROR
  MAX_50_LENGTH_ERROR = this.AppConstants.MAX_LENGTH_50_ERROR
  INVALID_PHONE_ERROR = this.AppConstants.INVALID_PHONE_NUMBER_ERROR
  MAX_100_LENGTH_ERROR = this.AppConstants.MAX_LENGTH_100_ERROR
  INVALID_PINCODE_ERROR = this.AppConstants.MAX_OTP_LENGTH_ERROR
  
  countries = null;
  states = null;
  cities = null;
  classes = null;
  courses = null;
  ngOnInit() {
    var component = this;
    var datePicker = $('#inputdob').datepicker({
      format: "yyyy-mm-dd"
    }).on('changeDate', function(e) {
      // `e` here contains the extra attributes
      console.log(e)
      console.log(datePicker.getDate)
      console.log($('#inputdob').val())
      console.log(component)
      component.selected_dob = $('#inputdob').val();

  });
  this.loadCities();
  this.classes = [
    {
      "id": 1,
      "name": "8th"
    },
    {
      "id": 2,
      "name": "9th"
    },
    {
      "id": 3,
      "name": "10th"
    },
    {
      "id": 4,
      "name": "11th"
    },
    {
      "id": 5,
      "name": "12th"
    }
  ]
  this.courses = [
    {
      "id": 1,
      "name": "JEE Mains"
    },
    {
      "id": 1,
      "name": "JEE Advance"
    },
    {
      "id": 1,
      "name": "BITSAT"
    }
  ]
  }

  loadCities() {
    this._commonServive.loadCountriesJson().subscribe((response) => {
      console.log(response);
      this.countries = response;
      this.selected_country = this.countries[0].name
      this.states = this.countries[0].states;
      this.selected_state = this.states[0].name;
      this.cities = this.states[0].cities;
      this.selected_city = this.cities[0]
    }, (error) => {
      console.log(error);
    });
  }
  setStates() {
    console.log(this.selected_country)
    let country = this.countries.find((data: any) => data.name === this.selected_country);
    this.states = country['states'];
    this.selected_state = this.states[0].name;
    this.setCities();
  }

  setCities() {
    let state = this.states.find((data: any) => data.name === this.selected_state);
    this.cities = state['cities'];
    this.selected_city = this.cities[0];
  }
  initForm() {
    this.profileForm = this.fb.group({
      firstName: ['', [Validators.required, Validators.maxLength(this.AppConstants.MAX_LENGTH_50)]],
      lastName: ['', [Validators.maxLength(this.AppConstants.MAX_LENGTH_50)]],
      email: ['', [Validators.required, Validators.maxLength(this.AppConstants.MAX_LENGTH_50)]],
      phone: ['', [Validators.required, Validators.maxLength(this.AppConstants.MAX_LENGTH_10)]],
      dob: ['', [Validators.required]],
      gender: ['', [Validators.required]],
      fatherName: ['', [Validators.required, Validators.maxLength(this.AppConstants.MAX_LENGTH_50)]],
      fatherPhone: ['', [Validators.required, Validators.maxLength(this.AppConstants.MAX_LENGTH_10)]],
      addressLine1: ['', [Validators.required, Validators.maxLength(this.AppConstants.MAX_LENGTH_100)]],
      addressLine2: ['', [ Validators.maxLength(this.AppConstants.MAX_LENGTH_100)]],
      pincode: ['', [Validators.required, Validators.maxLength(this.AppConstants.MAX_OTP_LENGTH)]],
      city: ['', [Validators.required]],
      state: ['', [Validators.required]],
      country: ['', [Validators.required]],
      school: ['', [Validators.required, Validators.maxLength(this.AppConstants.MAX_LENGTH_100)]],
      class: ['', [Validators.required]],
      course: ['', [Validators.required]]
    })
  }

  get firstName() { return this.profileForm.get('firstName'); }

  get lastName() { return this.profileForm.get('lastName'); }

  get email() { return this.profileForm.get('email'); }

  get phone() { return this.profileForm.get('phone'); }

  get dob() { return this.profileForm.get('dob'); }

  get gender() { return this.profileForm.get('gender'); }

  get fatherName() { return this.profileForm.get('fatherName'); }

  get fatherPhone() { return this.profileForm.get('fatherPhone'); }

  get addressLine1() { return this.profileForm.get('addressLine1'); }

  get addressLine2() { return this.profileForm.get('addressLine2'); }

  get pincode() { return this.profileForm.get('pincode'); }

  get country() { return this.profileForm.get('country'); }

  get state() { return this.profileForm.get('state'); }

  get city() { return this.profileForm.get('city'); }

  get school() { return this.profileForm.get('school'); }

  get class() { return this.profileForm.get('class'); }

  get course() { return this.profileForm.get('course'); }
  

}

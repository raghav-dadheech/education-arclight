import { Injectable, OnInit } from '@angular/core';
import { User } from '../modals/user';
import { Role } from '../modals/role';
import { AppConstants } from './AppConstants';

@Injectable({
  providedIn: 'root'
})
export class AuthService{

  constructor(
    private AppConstants : AppConstants
  ) { }

  private user: any;
  
  loadUser(){
    this.user = this.AppConstants.getUser();
  }

  isAuthorized() {
    if(this.user == null)
      this.loadUser();
    return !!this.user;
  }
  hasRole(role: any) {
    if(this.user == null)
      this.loadUser();
    return this.isAuthorized() && this.user.role['role'] === role;
  }

  logout() {
    this.user = null;
  }

}

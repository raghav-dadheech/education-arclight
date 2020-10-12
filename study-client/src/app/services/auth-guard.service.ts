import { Injectable } from '@angular/core';
import { CanActivate, CanLoad, Router, ActivatedRouteSnapshot, Route } from '@angular/router';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate, CanLoad {
  constructor(
      private router: Router,
      private authService: AuthService
  ) { }
  canActivate(route: ActivatedRouteSnapshot): Observable<boolean> | Promise<boolean> | boolean {
      console.log('canActivate')
      if (!this.authService.isAuthorized()) {
        console.log('should go to login 1')
        this.router.navigateByUrl('/login');
          return false;
      }
      const roles = route.data.roles;
      console.log('roles  ', roles)
      if (roles && !roles.some(r => this.authService.hasRole(r))) {
        console.log('not found')
          this.router.navigateByUrl('/dashboard');
          return false;
      }
      return true;
  }
  canLoad(route: Route): Observable<boolean> | Promise<boolean> | boolean {
    console.log('canLoad')
      if (!this.authService.isAuthorized()) {
        console.log('should go to login 2')
          this.router.navigateByUrl('/login');
          return false;
      }
      const roles = route.data && route.data.roles;
      if (roles && !roles.some(r => this.authService.hasRole(r))) {
          return false;
      }
      return true;
  }
}

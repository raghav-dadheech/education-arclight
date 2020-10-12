import {MediaMatcher} from '@angular/cdk/layout';
import {ChangeDetectorRef, Component, OnDestroy, OnInit} from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AppConstants } from 'src/app/services/AppConstants';
import { AuthService } from 'src/app/services/auth.service';
declare var $;
@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnDestroy, OnInit {

  mobileQuery: MediaQueryList;
  private _mobileQueryListener: () => void;

  constructor(
    changeDetectorRef: ChangeDetectorRef, 
    media: MediaMatcher,
    private _router: Router,
    private AppConstants : AppConstants,
    private authService : AuthService
    ) {
    this.mobileQuery = media.matchMedia('(max-width: 600px)');
    this._mobileQueryListener = () => changeDetectorRef.detectChanges();
    this.mobileQuery.addListener(this._mobileQueryListener);
   }

   userFullName = '';
   profileCompleted = false;
   ngOnInit() {
      $('body').attr('class', 'hold-transition sidebar-mini layout-fixed')
      // $("body").css('background', '#e9ecef');
      $("body").removeClass("body-img");
      this.userFullName = this.AppConstants.userFullName();
      this.profileCompleted = this.AppConstants.isProfileComple();
      if(!this.profileCompleted) {
        // $('#studentProfileModal').modal('show');
        this._router.navigateByUrl('/profile');
      }
    }

   ngOnDestroy(): void {
      this.mobileQuery.removeListener(this._mobileQueryListener);
    }

    logOut() {
      sessionStorage.removeItem('username')
      sessionStorage.removeItem('token')
      sessionStorage.removeItem('user')
      this.authService.logout();
      this._router.navigateByUrl("/login")
    }

    changePassword() {
      this._router.navigateByUrl("/change-password")
    }
}

import {MediaMatcher} from '@angular/cdk/layout';
import {ChangeDetectorRef, Component, OnDestroy, OnInit} from '@angular/core';
import { AppConstants } from 'src/app/services/AppConstants';
import { AuthService } from 'src/app/services/auth.service';
import { Role } from 'src/app/modals/role';

declare var jQuery : any
@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnDestroy, OnInit {

  mobileQuery: MediaQueryList;
  private _mobileQueryListener: () => void;

  userFullName = '';
  Role = Role;
  constructor(changeDetectorRef: ChangeDetectorRef, media: MediaMatcher,
    private AppConstants : AppConstants, private authService: AuthService) {
    this.mobileQuery = media.matchMedia('(max-width: 600px)');
    this._mobileQueryListener = () => changeDetectorRef.detectChanges();
    this.mobileQuery.addListener(this._mobileQueryListener);
   }

   ngOnInit() {
    this.userFullName = this.AppConstants.userFullName();
   }

   ngOnDestroy(): void {
    this.mobileQuery.removeListener(this._mobileQueryListener);
  }

  get isAuthorized() {
    return this.authService.isAuthorized();
  }

}

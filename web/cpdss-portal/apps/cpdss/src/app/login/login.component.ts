import { Component, Injector, OnInit } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import { SecurityService } from "../shared/services/security/security.service";
import { IUserProfile } from "../shared/models/user-profile.model";
import { LoginService } from './login.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { PermissionsService } from '../shared/services/permissions/permissions.service';
import { environment } from '../../environments/environment';
import { MessageService } from 'primeng/api';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';

/**
 *  CPDSS-main application login component
 *  Will utilize keycloak-service to fetch keycloak properties on load and sets in in-memory using service called securityService
 */

@Component({
  selector: 'cpdss-portal-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loggedIn = false;
  user: IUserProfile;

  private kycloakService: KeycloakService

  constructor(protected readonly router: Router, private injector: Injector, private loginService: LoginService, private ngxSpinnerService: NgxSpinnerService, private permissionsService: PermissionsService, private messageService: MessageService, private translateService: TranslateService) {
    if (environment.name === 'shore') {
      this.kycloakService = <KeycloakService>this.injector.get(KeycloakService);
    }
  }

  ngOnInit(): void {
    if (environment.name === 'shore') {
      this.loadKeycloakProperties();
    } else {
      this.loadTokenProperties()
    }
  }

  // to fetch authentication token and user-profile from keycloak after login
  private async loadKeycloakProperties() {
    this.ngxSpinnerService.show();
    try {
      // wait for keycloak to set log-in status
      const isLoggedIn = await this.kycloakService.isLoggedIn();
      this.loggedIn = isLoggedIn;

      // wait for keycloak to set auth-token and user-profile if logged-in
      if (this.loggedIn) {
        const token = await this.kycloakService.getToken();
        SecurityService.setAuthToken(token);

        this.user = <IUserProfile>await this.kycloakService.loadUserProfile();

        /* get user details and user permission */
        try{
          const result = await this.loginService.getUserDetails().toPromise();
          this.user = <IUserProfile>{ ...this.user, ...result?.user };
          SecurityService.setUserProfile(this.user);
          this.permissionsService.setPermissions(this.user?.rolePermissions?.resources);
          this.setPropertiesDB(token);
          if(this.user.statusCode === 1){
            this.router.navigate(['business']);
          } else {
            const params = { id: this.user.id, status: this.user.statusCode, count: this.user.rejectionCount };
            this.router.navigate(['access-denied/auth/'+ encodeURIComponent(btoa(JSON.stringify(params)))]);
            localStorage.removeItem('_USER_PERMISSIONS');
          }
        } catch(e){
          if(e.error.errorCode === "ERR-RICO-101" && e.error.status === "400"){
            const params = { id: 0, status: 4, count: 0 };
            this.router.navigate(['access-denied/auth/'+ encodeURIComponent(btoa(JSON.stringify(params)))]);
            localStorage.removeItem('_USER_PERMISSIONS');
          }
        }


      }
    }
    catch (ex) {
      console.log('Exception:loginComponent', ex);
    }
    this.ngxSpinnerService.hide();
  }

  // to fetch authentication token and user-profile in case of token based authentication
  private async loadTokenProperties() {
    this.ngxSpinnerService.show();
    try {
      const daysRemain = Number(localStorage.getItem('daysRemain'));
      if(daysRemain){
        const days = (daysRemain > 1 ? daysRemain + ' days' : '24 hours');
        const translationKeys = await this.translateService.get(['PASSWORD_EXPIRY_REMINDER', 'PASSWORD_EXPIRY_REMINDER_DETAILS'], { days: days }).toPromise();

        this.messageService.add({
          severity: 'warn', summary: translationKeys['PASSWORD_EXPIRY_REMINDER'], detail: translationKeys['PASSWORD_EXPIRY_REMINDER_DETAILS']
        });
        localStorage.removeItem('daysRemain')
      }
      const token = localStorage.getItem('token');
      SecurityService.setAuthToken(token);

      /* get user details and user permission */
      const result = await this.loginService.getUserDetails().toPromise();
      this.user = <IUserProfile>{ ...this.user, ...result?.user };
      SecurityService.setUserProfile(this.user);
      this.permissionsService.setPermissions(this.user?.rolePermissions?.resources);
      this.setPropertiesDB(token);
    }
    catch (ex) {
      console.log('Exception:loginComponent', ex);
    }
    this.ngxSpinnerService.hide();
  }

  /**
   * Set values in properties db
   *
   * @private
   * @param {*} token
   * @memberof LoginComponent
   */
  private async setPropertiesDB(token) {
    SecurityService.initPropertiesDB(token);
  }

}

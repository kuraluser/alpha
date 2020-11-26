import { Component, OnInit } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import { SecurityService } from "../shared/services/security/security.service";
import { IUserProfile } from "../shared/models/user-profile.model";
import { LoginService } from './login.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { PermissionsService } from '../shared/services/permissions/permissions.service';

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

  constructor(private kycloakService: KeycloakService, private loginService: LoginService, private ngxSpinnerService: NgxSpinnerService, private permissionsService: PermissionsService) { }

  ngOnInit(): void {
    this.loadKeycloakProperties();
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
        const result = await this.loginService.getUserDetails().toPromise();
        this.user = <IUserProfile>{ ...this.user, ...result?.user };
        SecurityService.setUserProfile(this.user);
        this.permissionsService.setPermissions(this.user?.rolePermissions?.resources)
      }
    }
    catch (ex) {
      console.log('Exception:loginComponent', ex);
    }
    this.ngxSpinnerService.hide();
  }

}

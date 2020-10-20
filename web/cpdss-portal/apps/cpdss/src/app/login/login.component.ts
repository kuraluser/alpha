import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { KeycloakService } from 'keycloak-angular';
import { SecurityService } from "../shared/services/security/security.service";
import { UserProfile } from "../shared/models/user-profile.model";

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

  loggedIn: boolean = false;

  constructor(private router: Router, private kycloakService: KeycloakService) { }

  ngOnInit(): void {
    this.loadKeycloakProperties();
  }

  // to fetch authentication token and user-profile from keycloak after login
  private async loadKeycloakProperties() {
    try {
      // wait for keycloak to set log-in status
      const isLoggedIn = await this.kycloakService.isLoggedIn();
      this.loggedIn = isLoggedIn;
      
      // wait for keycloak to set auth-token and user-profile if logged-in
      if (this.loggedIn == true) {
        const token = await this.kycloakService.getToken();
        SecurityService.setAuthToken(token);

        const userProfile: UserProfile = <UserProfile>await this.kycloakService.loadUserProfile();
        SecurityService.setUserProfile(userProfile);
      }
    }
    catch (ex) {
      console.log('Exception:loginComponent', ex);
    }
  }

}

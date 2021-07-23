import { Component, OnInit, Optional } from '@angular/core';
import { AppConfigurationService } from './../shared/services/app-configuration/app-configuration.service';
import { SecurityService } from './../shared/services/security/security.service';
import { environment } from './../../environments/environment';
import { KeycloakService } from 'keycloak-angular';

/**
 * Compoent class for logout
 *
 * @export
 * @class LogoutComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.scss']
})
export class LogoutComponent implements OnInit {

  constructor(@Optional() private keycloakService: KeycloakService) {
    try {
      const redirectUrl = window.location.protocol + '//' + window.location.hostname + AppConfigurationService.settings.redirectPath;
      SecurityService.userLogoutAction();
      if (environment.name === 'shore') {
        this.keycloakService.logout(redirectUrl);
      } else {
        window.location.href = redirectUrl;
      }
    }
    catch {
      console.error('Something went wrong');
    }
  }

  ngOnInit(): void {
  }

}

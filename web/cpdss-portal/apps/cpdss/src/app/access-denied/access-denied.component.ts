import { Component, OnInit, Optional } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { AUTH_STATUS } from '../shared/models/common.model';
import { AccessDeniedApiService } from './access-denied-api.service';
import { TranslateService } from '@ngx-translate/core';
import { MessageService } from 'primeng/api';
import { AppConfigurationService } from '../shared/services/app-configuration/app-configuration.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { KeycloakService } from 'keycloak-angular';
import { environment } from '../../../src/environments/environment';
import { SecurityService } from '../shared/services/security/security.service';

/**
 * Compoent class for access denied nabvigation
 *
 * @export
 * @class AccessDeniedComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-access-denied',
  templateUrl: './access-denied.component.html',
  styleUrls: ['./access-denied.component.scss']
})
export class AccessDeniedComponent implements OnInit {

  enableUserAuth = false;
  userInfo: any = null;
  userStatus: AUTH_STATUS = null;
  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private messageService: MessageService,
    private translateService: TranslateService,
    private accessDeniedService: AccessDeniedApiService,
    private ngxSpinnerService: NgxSpinnerService,
    @Optional() private keycloakService: KeycloakService,
  ) {}

  /**
   * Component Lifecycle hook OnInit
   *
   * @memberof AccessDeniedComponent
 */
  ngOnInit(): void {
    this.enableUserAuth = this.activatedRoute.snapshot.params.info ? true : false;
    this.userInfo = this.enableUserAuth ? JSON.parse(atob(decodeURIComponent(this.activatedRoute.snapshot.params.info))) : null;
    this.userStatus = this.userInfo ? this.userInfo.status : null;
  }

  /**
  * back to home functionality
  *
  * @memberof AccessDeniedComponent
 */
  goBackToHome() {
    const logOutUrl = window.location.protocol + '//' + window.location.hostname + AppConfigurationService.settings.redirectPath;
    if(localStorage.getItem('_USER_PERMISSIONS')) {
      this.router.navigate(['/']);
    } else {
      SecurityService.userLogoutAction();
      if(environment.name === 'shore'){
        this.keycloakService.logout(logOutUrl);
      } else {
        window.location.href = logOutUrl;
      }
      
    }
    
  }

  /**
  * back to login functionality
  *
  * @memberof AccessDeniedComponent
 */
  gobackToLogin() {
    const redirectUrl = window.location.protocol + '//' + window.location.hostname + AppConfigurationService.settings.redirectPath;
    SecurityService.userLogoutAction();
    if(environment.name === 'shore'){
      this.keycloakService.logout(redirectUrl);
    } else {
      window.location.href = redirectUrl;
    }
  }

  /**
  * user request for acceess permission
  *
  * @memberof AccessDeniedComponent
 */
  async requestPermission() {
    const translationKeys = await this.translateService.get(['USER_AUTHORIZATION_SUCCESS', 'USER_AUTHORIZATION_REQUEST_SUCCESS']).toPromise();
    this.ngxSpinnerService.show();
    const result = await this.accessDeniedService.getAccess().toPromise();
    this.ngxSpinnerService.hide();
    this.messageService.add({ severity: 'success', summary: translationKeys['USER_AUTHORIZATION_SUCCESS'], detail: translationKeys['USER_AUTHORIZATION_REQUEST_SUCCESS'] });
    this.userStatus = 3;
  }
}

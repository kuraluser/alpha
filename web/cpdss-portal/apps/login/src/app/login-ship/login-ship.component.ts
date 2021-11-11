import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { OwlOptions } from 'ngx-owl-carousel-o';
import { MessageService } from 'primeng/api';
import { LoginShipService } from '../login-ship/services/login-ship.service';
import { IAppConfiguration } from '../shared/services/app-configuration/app-configuration.model';
import { AppConfigurationService } from '../shared/services/app-configuration/app-configuration.service';
import { ILoginShip } from './model/login-ship.model';
/**
 * Component for Ship login
 *
 * @export
 * @class LoginShipComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'app-login',
  templateUrl: './login-ship.component.html',
  styleUrls: ['./login-ship.component.scss']
})
// Login component for ship-module
export class LoginShipComponent implements OnInit {
  loginForm: FormGroup;
  settings: IAppConfiguration;
  logo = '';
  carousels = [];
  favicon = '';
  customOptions: OwlOptions = {
    loop: true,
    margin: 0,
    items: 1,
    nav: false,
    dots: false,
    autoplay: true,
    autoplayTimeout: 5000,
    autoplayHoverPause: false,
    animateOut: 'fadeOut',
    animateIn: 'fadeIn',
    responsive: {
      0: {
        items: 1
      }

    },
  }

  constructor(private fb: FormBuilder,
    private loginShipService: LoginShipService,
    private messageService: MessageService,
    private translateService: TranslateService
  ) { }

  /**
   * Method called on intialization of the component
   *
   * @memberof LoginShipComponent
   */
  ngOnInit(): void {
    this.settings = AppConfigurationService.settings;
    this.logo = localStorage.getItem('logo');
    this.favicon = localStorage.getItem('favicon');
    this.loginForm = this.fb.group({
      userName: ['', [Validators.required]],
      password: ['', [Validators.required, Validators.minLength(7)]]
    });
    this.carousels = localStorage.getItem('carousel') ? JSON.parse(localStorage.getItem('carousel')) : [];
  }

  /**
   * Submit form
   */
  async onSubmit() {
    const loginData: ILoginShip = {
      'username': this.loginForm.controls.userName.value,
      'password': this.loginForm.controls.password.value,
    }
    if (this.loginForm.valid) {
      try {
        const result = await this.loginShipService.validateShipLogin(loginData).toPromise();
        if (result?.responseStatus?.status === '200') {
          const logoUrl = localStorage.getItem('logo');
          const docsUrl = localStorage.getItem('docsUrl');
          const realm = localStorage.getItem('realm');
          const faviconUrl = localStorage.getItem('favicon');
          const token = result.token;
          let redirectUri = window.location.protocol + '//' + window.location.hostname + this.settings.path + '?realm=' + realm + '&logoUrl=' + logoUrl + '&token=' + token + '&faviconUrl=' + faviconUrl + '&docsUrl=' + docsUrl;
          if (typeof result.expiryReminder?.daysRemain !== 'undefined') {
            redirectUri += '&daysRemain=' + result.expiryReminder?.daysRemain
          }
          window.location.href = redirectUri;
        }
      }
      catch (error) {
        if (error.error?.status === '401') {
          const translateKeys = [
            'PASSWORD_EXPIRED',
            'PASSWORD_EXPIRED_DETAILS',
            'INCORRECT_PASSWORD',
            'INCORRECT_PASSWORD_DETAILS'
          ];
          const translatedMsgs = await this.translateService.get(translateKeys).toPromise()
          console.log(translatedMsgs)
          if (error.error?.errorCode === 'ERR-RICO-124') {
            this.messageService.add({ severity: 'error', summary: translatedMsgs[translateKeys[0]], detail: translatedMsgs[translateKeys[1]] });
          } else {
            this.messageService.add({ severity: 'error', summary: translatedMsgs[translateKeys[2]], detail: translatedMsgs[translateKeys[3]] });
          }
        }
      }
    }
    else {
      this.loginForm.markAllAsTouched();
    }
  }

  /**
   * Get form control of newVoyageForm
   */
  get login() {
    return this.loginForm.controls;
  }

}

import { Component, OnInit } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import { IAppConfiguration } from '../shared/services/app-configuration/app-configuration.model';
import { AppConfigurationService } from '../shared/services/app-configuration/app-configuration.service';
import { OwlOptions } from 'ngx-owl-carousel-o';

// Login component for shore-module

@Component({
  selector: 'cpdss-login-shore',
  templateUrl: './login-shore.component.html',
  styleUrls: ['./login-shore.component.scss']
})
export class LoginShoreComponent implements OnInit {
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
  idpList = [];
  settings: IAppConfiguration;
  realm = '';
  logo = '';
  carousels = [];
  favicon = '';

  constructor(private kcService: KeycloakService, private appConfig: AppConfigurationService) { }

  ngOnInit(): void {
    this.settings = AppConfigurationService.settings;
    this.logo = localStorage.getItem('logo');
    this.favicon = localStorage.getItem('favicon');
    this.createIdpInput();
    this.carousels = localStorage.getItem('carousel') ? JSON.parse(localStorage.getItem('carousel')) : [];
  }

  // to bind input buttons dynamically from api response with identity-provider enabled
  createIdpInput() {
    const idpConfig = localStorage.getItem('keycloakIdpConfig').split(',');
    this.realm = localStorage.getItem('realm');
    for (let i = 0; i < idpConfig.length; i++) {
      this.idpList.push(idpConfig[i]);
    }
  }

  // common login function for all identity-providers
  login(idp) {
    const logoUrl = localStorage.getItem('logo');
    const faviconUrl = localStorage.getItem('favicon');
    const docsUrl = localStorage.getItem('docsUrl');
    const simulatorSiteUrl = localStorage.getItem('simulatorSiteUrl');
    this.kcService.login({
      redirectUri: window.location.protocol + '//' + window.location.hostname + this.settings.path + '?realm=' + this.realm + '&logoUrl=' + logoUrl + '&faviconUrl=' + faviconUrl + '&simulatorUrl=' + encodeURIComponent(simulatorSiteUrl) + '&docsUrl=' + docsUrl,
      idpHint: idp
    });
  }

}

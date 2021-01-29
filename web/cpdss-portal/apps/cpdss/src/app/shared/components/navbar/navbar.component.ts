import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { KeycloakService } from 'keycloak-angular';
import { Observable } from 'rxjs';
import { AppConfigurationService } from '../../services/app-configuration/app-configuration.service';
import { SecurityService } from '../../services/security/security.service';
import { ThemeService } from '../../services/theme-service/theme.service';
import { IMenuItem } from './navbar.component.model';

@Component({
  selector: 'cpdss-portal-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
  isToggle = true;
  darkMode$: Observable<boolean>;
  menuList: IMenuItem[];
  isSubMenu: boolean[] = [];
  showUserIconDropdown = false;
  companyLogo = '';

  constructor(private themeService: ThemeService, private keycloakService: KeycloakService,
    private router: Router) { }

  ngOnInit(): void {

    this.companyLogo = localStorage.getItem('companyLogo');
    /**
     * Array for showing nav
     */
    this.menuList = [
      {
        'menu': 'STATUS',
        'menuIcon': 'status',
        'menuLink': 'voyage-status',
        'subMenu': []
      },
      {
        'menu': 'CARGO_PLANNING',
        'menuIcon': 'cargo-planning',
        'menuLink': 'cargo-planning',
        'subMenu': []
      },
      {
        'menu': 'SYNOPTICAL',
        'menuIcon': 'synoptical-table',
        'menuLink': 'synoptical',
        'subMenu': []
      },
      {
        'menu': 'ADMIN',
        'menuIcon': 'voyages',
        'menuLink': 'admin',
        'subMenu': [
          { 'name': 'User Role Permission' , 'subMenuLink': '/business/admin/user-role-listing'}
        ]
      },
      /* {
        'menu': 'OPERATIONS',
        'menuIcon': 'voyages',
        'menuLink': 'operations',
        'subMenu': [
          { 'name': 'LOADING' },
          { 'name': 'DISCHARGING' },
          { 'name': 'BUNKERING' }
        ]
      },
      {
        'menu': 'VOYAGES',
        'menuIcon': 'voyages',
        'menuLink': 'voyages',
        'subMenu': [

        ]
      },
      {
        'menu': 'REPORTERS',
        'menuIcon': 'voyages',
        'menuLink': 'admin',
        'subMenu': []
      },
      {
        'menu': 'ADMIN',
        'menuIcon': 'voyages',
        'menuLink': 'admin',
        'subMenu': []
      },
      {
        'menu': 'FLEET',
        'menuIcon': 'voyages',
        'menuLink': 'admin',
        'subMenu': []
      } */

    ];
  }

  /**
   * Display submenu when clicked
   */
  showSubmenu(list, index) {
    this.isSubMenu = [];
    if (list.length > 0) {
      this.isSubMenu[index] = true;
    }
  }

  /**
   * Hide submenu on focus out
   */
  hide(list, index) {
    this.isSubMenu = [];
    if (list.length > 0) {
      this.isSubMenu[index] = false;
    }
  }
  /**
   * Change theme on button click
   */
  setDarkMode() {
    this.isToggle = !this.isToggle;
    this.themeService.setDarkMode(this.isToggle);
  }

  /**
   * Method for user logout
   *
   * @memberof NavbarComponent
   */
  logout() {
    try {
      const redirectUrl = window.location.protocol + '//' + window.location.hostname + AppConfigurationService.settings.redirectPath;
      SecurityService.userLogoutAction();
      this.keycloakService.logout(redirectUrl);

    }
    catch {
      console.error('Something went wrong');
    }
  }

  /**
   * Handler for User Icon toggle 
   *
   * @memberof NavbarComponent
   */
  onUserIconMenuToggle() {
    this.showUserIconDropdown = !this.showUserIconDropdown;
  }

  /**
   * Handler sub routes in List 
   *
   * @memberof NavbarComponent
   */
  subRoute(subMenu) {
    this.router.navigate([subMenu.subMenuLink]);
  }

}

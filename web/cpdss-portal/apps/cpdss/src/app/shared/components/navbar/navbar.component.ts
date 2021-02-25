import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { KeycloakService } from 'keycloak-angular';
import { Observable } from 'rxjs';
import { AppConfigurationService } from '../../services/app-configuration/app-configuration.service';
import { SecurityService } from '../../services/security/security.service';
import { ThemeService } from '../../services/theme-service/theme.service';
import { IMenuItem , IPermission } from './navbar.component.model';
import { PermissionsService } from '../../../shared/services/permissions/permissions.service';

@Component({
  selector: 'cpdss-portal-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
  isToggle = true;
  darkMode$: Observable<boolean>;
  menuList: IMenuItem[] = [];
  showUserIconDropdown = false;
  companyLogo = '';
  userPermission: any;

  constructor(private themeService: ThemeService, private keycloakService: KeycloakService,
    private router: Router,
    private permissionsService: PermissionsService) { }

  ngOnInit(): void {

    this.companyLogo = localStorage.getItem('companyLogo');
    
    
    /**
     * Array for showing nav
     */
    const menuList = [
      {
        'menu': 'STATUS',
        'menuIcon': 'status',
        'menuLink': 'voyage-status',
        'subMenu': [],
        'isSubMenuOpen': false,
        'permissionMapping': AppConfigurationService.settings.permissionMapping['VoyageStatusComponent']
      },
      {
        'menu': 'CARGO_PLANNING',
        'menuIcon': 'cargo-planning',
        'menuLink': 'cargo-planning',
        'subMenu': [],
        'isSubMenuOpen': false,
        'permissionMapping': AppConfigurationService.settings.permissionMapping['CargoPlanningComponent']
      },
      {
        'menu': 'SYNOPTICAL',
        'menuIcon': 'synoptical-table',
        'menuLink': 'synoptical',
        'subMenu': [],
        'isSubMenuOpen': false,
        'permissionMapping': AppConfigurationService.settings.permissionMapping['SynopticalComponent']
      },
      {
        'menu': 'ADMIN',
        'menuIcon': 'admin',
        'menuLink': 'admin',
        'isSubMenuOpen': false,
        'permissionMapping': AppConfigurationService.settings.permissionMapping['AdminComponent'],
        'subMenu': [
          { 'name': 'User Role Permission' , 'subMenuLink': '/business/admin/user-role-permission' , 'permissionMapping': AppConfigurationService.settings.permissionMapping['UserPermission'], 'isVisible': false}
        ],
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
    let isUserPermissionAvailable = setInterval(() => {
      if(JSON.parse(window.localStorage.getItem('_USER_PERMISSIONS'))) {
        this.userPermission = JSON.parse(window.localStorage.getItem('_USER_PERMISSIONS'));
        this.getPagePermission(menuList);
        clearInterval(isUserPermissionAvailable);
      }
    },50);
  }

    /**
   * Get page permission
   *
   * @memberof NavbarComponent
   */
  getPagePermission(menuList) {
    const list = [...menuList];
    list?.map((menuItem , index) => {
      const permission = this.getPermission(menuItem.permissionMapping);
      if((permission && permission?.view)) {
        this.menuList.push(menuItem)
      }
      if(menuItem.subMenu.length) {
        menuItem.subMenu?.map((subMenu, subMenuIndex) => { 
          const subMenuPermission = this.getPermission(subMenu.permissionMapping);
          if(subMenuPermission && subMenuPermission?.view) {
            this.menuList[index]['subMenu'][subMenuIndex] = subMenu;
          }
        })
      }
    });
  }

    /**
   * Method for fetching permission
   *
   * @param {string} languageKey
   * @returns {IPermission}
   * @memberof PermissionsService
   */
  getPermission(languageKey: string): IPermission {
    const permission = this.userPermission?.find(item => item.languageKey === languageKey)?.permission;
    return permission;
  }

  /**
   * Display submenu when clicked
   */
  showSubmenu(list, index) {
    this.menuList?.map((list: IMenuItem) => list.isSubMenuOpen = false);
    if (list.length > 0) {
      this.menuList[index].isSubMenuOpen = true;
    }
  }

  /**
   * Hide submenu on focus out
   */
  hide(list, index) {
    this.menuList?.map((list: IMenuItem) => list.isSubMenuOpen = false);
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
  subRoute(event: any,subMenu: any,list: any, index: number) {
    this.router.navigate([subMenu.subMenuLink]);
    this.hide(list,index);
    event.stopPropagation();
  }

}

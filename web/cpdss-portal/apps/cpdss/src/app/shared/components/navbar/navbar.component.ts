import { Component, Injector, OnInit, OnDestroy } from '@angular/core';
import { Event as NavigationEvent, Router, ActivatedRoute, NavigationStart, NavigationEnd } from '@angular/router';
import { KeycloakService } from 'keycloak-angular';
import { Observable, Subscription } from 'rxjs';
import { AppConfigurationService } from '../../services/app-configuration/app-configuration.service';
import { SecurityService } from '../../services/security/security.service';
import { ThemeService } from '../../services/theme-service/theme.service';
import { IMenuItem, IPermission, INotificationItem } from './navbar.component.model';
import { PermissionsService } from '../../../shared/services/permissions/permissions.service';
import { environment } from 'apps/cpdss/src/environments/environment';
import { NavbarApiService } from './navbar-api.service';
import { TranslateService } from '@ngx-translate/core';
import { MessageService } from 'primeng/api';
import { NgxSpinnerService } from 'ngx-spinner';
import { NotificationService } from '../../services/notification/notification.service';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'cpdss-portal-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit, OnDestroy {
  isToggle = true;
  darkMode$: Observable<boolean>;
  menuList: IMenuItem[] = [];
  showUserIconDropdown = false;
  companyLogo = '';
  userPermission: any;
  notificationList: INotificationItem[] = [];
  showNotification = false;
  notificationSubscription: Subscription;
  isShore = false;
  navigationEvent$: Subscription;

  private keycloakService: KeycloakService;

  constructor(
    private themeService: ThemeService,
    private injector: Injector,
    private router: Router,
    private permissionsService: PermissionsService,
    private navbarApiService: NavbarApiService,
    private translateService: TranslateService,
    private messageService: MessageService,
    private ngxSpinnerService: NgxSpinnerService,
    private notificationService: NotificationService,
    private activatedRoute: ActivatedRoute
  ) {
    if (environment.name === 'shore') {
      this.keycloakService = <KeycloakService>this.injector.get(KeycloakService);
    }
  }

  ngOnInit(): void {
    this.companyLogo = localStorage.getItem('companyLogo');
    if (environment.name === 'shore') {
      this.isShore = true;
      this.getNotifications();
      this.notificationSubscription = this.notificationService.getNotification.subscribe((value) => {
        if (value) {
          this.getNotifications();
        }
      });
    }
    this.navigationEvent$ = this.router.events.pipe(filter(event => event instanceof NavigationEnd))
      .subscribe(event => {
        this.activeRoute(event['url']);
      });

    /**
     * Array for showing nav
     */
    const menuList = [
      {
        'menu': 'DASHBOARD',
        'menuIcon': 'status',
        'menuLink': 'voyage-status',
        'routerLinkActive': 'voyage-status',
        'subMenu': [],
        'isSubMenuOpen': false,
        'permissionMapping': AppConfigurationService.settings.permissionMapping['VoyageStatusComponent']
      },
      {
        'menu': 'CARGO_PLANNING',
        'menuIcon': 'cargo-planning',
        'menuLink': 'cargo-planning',
        'routerLinkActive': 'cargo-planning',
        'subMenu': [],
        'isSubMenuOpen': false,
        'permissionMapping': AppConfigurationService.settings.permissionMapping['CargoPlanningComponent']
      },
      {
        'menu': 'VOYAGES',
        'menuIcon': 'voyages',
        'menuLink': 'voyage-list',
        'routerLinkActive': 'voyage-list',
        'subMenu': [],
        'isSubMenuOpen': false,
        'permissionMapping': AppConfigurationService.settings.permissionMapping['voyagesComponent']
      },
      {
        'menu': 'SYNOPTICAL',
        'menuIcon': 'synoptical-table',
        'menuLink': 'synoptical',
        'routerLinkActive': 'synoptical',
        'subMenu': [],
        'isSubMenuOpen': false,
        'permissionMapping': AppConfigurationService.settings.permissionMapping['SynopticalComponent'],
        'addVesselId': true,
        'addVoyageId': true,
        'addLoadableStudyId': true,
        'addLoadablePatternId': true,
      },
      {
        'menu': 'ADMIN',
        'menuIcon': 'admin',
        'menuLink': '',
        'routerLinkActive': 'admin',
        'isSubMenuOpen': false,
        'permissionMapping': AppConfigurationService.settings.permissionMapping['AdminComponent'],
        'subMenu': [
          { 'name': 'User Role Permission', 'subMenuLink': '/business/admin/user-role-permission', 'permissionMapping': AppConfigurationService.settings.permissionMapping['UserRoleListing'], 'isVisible': false },
          { 'name': 'User', 'subMenuLink': '/business/admin/user-listing', 'permissionMapping': AppConfigurationService.settings.permissionMapping['UserListingComponent'], 'isVisible': false },
          { 'name': 'Port Master', 'subMenuLink': '/business/admin/port-listing','permissionMapping': AppConfigurationService.settings.permissionMapping['PortListingComponent'],'isVisible': false },          
        ],
      },
      {
        'menu': 'OPERATIONS',
        'menuIcon': '',
        'menuLink': '',
        'routerLinkActive': 'operations',
        'isSubMenuOpen': false,
        'permissionMapping': AppConfigurationService.settings.permissionMapping['OperationsComponent'],
        'subMenu': [
          { 'name': 'Loading', 'subMenuLink': '/business/operations/loading', 'permissionMapping': AppConfigurationService.settings.permissionMapping['LoadingComponent'], 'isVisible': false }
        ],
      },
      {
        'menu': 'FLEET',
        'menuIcon': '',
        'menuLink': 'fleet',
        'routerLinkActive': 'fleet',
        'subMenu': [],
        'isSubMenuOpen': false,
        'permissionMapping': AppConfigurationService.settings.permissionMapping['FleetComponent']
      },
      /*
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
      } */

    ];
    const isUserPermissionAvailable = setInterval(() => {
      if (JSON.parse(window.localStorage.getItem('_USER_PERMISSIONS'))) {
        this.userPermission = JSON.parse(window.localStorage.getItem('_USER_PERMISSIONS'));
        clearInterval(isUserPermissionAvailable);
        this.getPagePermission(menuList);
        this.activeRoute(this.router.url);
      }
    }, 50);
  }

  /**
   * unsubscribe the observable
   *
   * @memberof NavbarComponent
  */
  ngOnDestroy() {
    this.navigationEvent$.unsubscribe();
    if (environment.name === 'shore') {
      this.notificationSubscription.unsubscribe();
    }
  }
  /**
 * Get page permission
 *
 * @memberof NavbarComponent
 */
  getPagePermission(menuList) {
    this.menuList = []
    const list = [...menuList];
    list?.map((menuItem: IMenuItem, index) => {
      const permission = this.getPermission(menuItem.permissionMapping);
      const menuListItem = [];
      if ((permission && permission?.view)) {
        menuListItem.push({
          menu: menuItem.menu,
          menuIcon: menuItem.menuIcon,
          menuLink: menuItem.menuLink,
          subMenu: [],
          isSubMenuOpen: menuItem.isSubMenuOpen,
          permissionMapping: menuItem.permissionMapping,
          addVesselId: menuItem.addVesselId,
          addVoyageId: menuItem.addVoyageId,
          addLoadableStudyId: menuItem.addLoadableStudyId,
          addLoadablePatternId: menuItem.addLoadablePatternId,
          routerLinkActive: menuItem.routerLinkActive,
          isActive: false
        });
        if (menuItem.subMenu.length) {
          menuItem.subMenu?.map((subMenu, subMenuIndex) => {
            const subMenuPermission = this.getPermission(subMenu.permissionMapping);
            if (subMenuPermission && subMenuPermission?.view) {
              menuListItem[0]['subMenu'].push(subMenu);
            }
          })
          if (menuListItem[0]['subMenu']?.length) {
            this.menuList = [...this.menuList, ...menuListItem];
          }
        } else {
          this.menuList = [...this.menuList, ...menuListItem];
        }
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
   * Reroute or Display submenu when clicked
   */
  rerouteOrShowSubmenu(list, index) {
    if (list.menuLink !== '') {
      let link = '/business/' + list.menuLink;
      if (list.addVesselId) {
        const vesselId = localStorage.getItem("vesselId")
        if (vesselId) {
          link += '/' + vesselId
        }
      }
      if (list.addVoyageId) {
        const voyageId = localStorage.getItem("voyageId")
        if (voyageId) {
          link += '/' + voyageId
        }
      }
      if (list.addLoadableStudyId) {
        const loadableStudyId = localStorage.getItem("loadableStudyId")
        if (loadableStudyId) {
          link += '/' + loadableStudyId
        }
      }
      if (list.addLoadablePatternId) {
        const loadablePatternId = localStorage.getItem("loadablePatternId")
        if (loadablePatternId) {
          link += '/' + loadablePatternId
        }
      }
      this.router.navigateByUrl(link)
    }
    this.menuList?.map((menuItem: IMenuItem) => menuItem.isSubMenuOpen = false);
    if (list.subMenu?.length > 0) {
      this.menuList[index].isSubMenuOpen = true;
    }
  }

  /**
   * Hide submenu on focus out
   */
  hide(list, index) {
    this.menuList?.map((menuItem: IMenuItem) => menuItem.isSubMenuOpen = false);
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
  subRoute(event: any, subMenu: any, list: any, index: number) {
    this.router.navigate([subMenu.subMenuLink]);
    this.hide(list, index);
    event.stopPropagation();
  }

  /**
   * Method for getting admin notifications list 
   *
   * @memberof NavbarComponent
   */
  async getNotifications() {
    const results = await this.navbarApiService.getNotification().toPromise();
    this.notificationList = results.notifications ? results.notifications : [];

  }

  /**
   * Handler for notification Icon toggle 
   *
   * @memberof NavbarComponent
   */
  toggleNotification(outside?) {
    this.showNotification = outside ? false : !this.showNotification;
  }

  /**
   * Handler for role list redirection 
   *
   * @memberof NavbarComponent
   */
  gotoRoleLIst(user) {
    this.router.navigate(['/business/admin/user-role-permission/user/' + user.id]);
    this.showNotification = false;
  }

  /**
   * Handler for reject user 
   *
   * @memberof NavbarComponent
   */

  async rejectUser(user) {
    this.showNotification = false;
    this.ngxSpinnerService.show();
    const translationKeys = await this.translateService.get(['USER_AUTHORIZATION_SUCCESS', 'USER_AUTHORIZATION_REJECT_USER_SUCCESS']).toPromise();
    const results = await this.navbarApiService.rejectUser(user.userId, {}).toPromise();
    this.ngxSpinnerService.hide();
    this.messageService.add({ severity: 'success', summary: translationKeys['USER_AUTHORIZATION_SUCCESS'], detail: translationKeys['USER_AUTHORIZATION_REJECT_USER_SUCCESS'] });
    this.getNotifications();

  }

  /**
   * Handler router link active
   *
   * @memberof NavbarComponent
   */
  activeRoute(url: string) {
    this.menuList.findIndex(menu => {
      if (url.includes(menu.routerLinkActive)) {
        menu.isActive = true
      } else {
        menu.isActive = false
      }
    })
  }

}

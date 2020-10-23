import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
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

  constructor(private themeService: ThemeService) { }

  ngOnInit(): void {

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
      // {
      //   'menu': 'REPORTERS',
      //   'menuIcon': 'voyages',
      //   'menuLink': 'admin',
      //   'subMenu': []
      // },
      {
        'menu': 'ADMIN',
        'menuIcon': 'voyages',
        'menuLink': 'admin',
        'subMenu': []
      },
      // {
      //   'menu': 'FLEET',
      //   'menuIcon': 'voyages',
      //   'menuLink': 'admin',
      //   'subMenu': []
      // }

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

}

import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { IPermission, IResource } from '../../models/user-profile.model';

/**
 * Service for managing permissions
 *
 * @export
 * @class PermissionsService
 */
@Injectable({
  providedIn: 'root'
})
export class PermissionsService {

  constructor(private router: Router) { }

  /**
   * Method for fetching permission
   *
   * @param {string} languageKey
   * @param {boolean} [isScreen=true]
   * @returns {IPermission}
   * @memberof PermissionsService
   */
  getPermission(languageKey: string, isScreen = true): IPermission {
    const permission = JSON.parse(window.localStorage.getItem('_USER_PERMISSIONS'))?.find(item => item.languageKey === languageKey)?.permission;
    if (permission && !permission?.view && isScreen) {
      this.router.navigate(['/access-denied']);
    }
    return permission;
  }

  /**
   * Method setting permission
   *
   * @param {IResource[]} permissions
   * @memberof PermissionsService
   */
  setPermissions(permissions: IResource[]): void {
    if(permissions){
      window.localStorage.setItem('_USER_PERMISSIONS', JSON.stringify(permissions));
    }
  }
}

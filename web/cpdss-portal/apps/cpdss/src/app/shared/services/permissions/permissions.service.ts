import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { IPermission, IResource } from '../../models/user-profile.model';

@Injectable({
  providedIn: 'root'
})
export class PermissionsService {

  constructor(private router: Router) { }

  getPermission(languageKey: string, isScreen = true): IPermission {
    const permission = JSON.parse(window.localStorage.getItem('_USER_PERMISSIONS'))?.find(item => item.languageKey === languageKey)?.permission;
    if (permission && !permission?.view && isScreen) {
      this.router.navigate(['/']);
    }
    return permission;
  }

  setPermissions(permissions: IResource[]): void {
    window.localStorage.setItem('_USER_PERMISSIONS', JSON.stringify(permissions));
  }
}

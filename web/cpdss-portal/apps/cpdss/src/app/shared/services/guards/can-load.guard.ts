import { Injectable } from '@angular/core';
import { CanLoad, Route, UrlSegment, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router, ActivatedRoute, Data } from '@angular/router';
import { KeycloakAuthGuard, KeycloakService } from 'keycloak-angular';
import { AppConfigurationService } from '../app-configuration/app-configuration.service';

@Injectable({
  providedIn: 'root'
})
export class CanLoadGuard extends KeycloakAuthGuard implements CanLoad {

  constructor(protected router: Router, private keycloak: KeycloakService, private route: ActivatedRoute) {
    super(router, keycloak);
  }

  async canLoad(route: Route, segments: UrlSegment[]): Promise<boolean> {
    try {
      this.authenticated = await this.keycloakAngular.isLoggedIn();
      this.roles = await this.keycloakAngular.getUserRoles(true);

      const result = await this.checkAccessAllowed(route.data);
      return result;
    } catch (error) {
      console.error(error);
      throw error;
    }
  }

  async isAccessAllowed(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    return this.checkAccessAllowed(route.data)
  }

  async checkAccessAllowed(data: Data): Promise<boolean> {
    // Force the user to log in if currently unauthenticated.
    if (!this.authenticated) {
      window.location.href = window.location.protocol + '//' + window.location.hostname + AppConfigurationService.settings.redirectPath;
    }

    const loggedIn = await this.keycloak.isLoggedIn();
    if (loggedIn) {
      return true;
    }
    else {
      window.location.href = window.location.protocol + '//' + window.location.hostname + AppConfigurationService.settings.redirectPath;
    }

    // Get the roles required from the route.
    const requiredRoles = this.roles;

    // Allow the user to to proceed if no additional roles are required to access the route.
    if (!(requiredRoles instanceof Array) || requiredRoles.length === 0) {
      return true;
    }

    // Allow the user to proceed if all the required roles are present.
    return requiredRoles.every((role) => this.roles.includes(role));
  }

}

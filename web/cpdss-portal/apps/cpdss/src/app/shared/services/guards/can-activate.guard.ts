import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, ActivatedRoute, Router } from '@angular/router';
import { KeycloakAuthGuard, KeycloakService } from 'keycloak-angular';
import { AppConfigurationService } from '../app-configuration/app-configuration.service';

@Injectable({
  providedIn: 'root'
})
export class CanActivateGuard extends KeycloakAuthGuard {

  constructor(protected readonly router: Router, protected readonly keycloak: KeycloakService, private route: ActivatedRoute) {
      super(router, keycloak);
  }

  public async isAccessAllowed(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {

      // Redirect to default route if currently unauthenticated.
      if (!this.authenticated) {
          this.router.navigate(['/']);
      }

      // Get the roles required from the route.
      const requiredRoles = route.data.roles;

      // Allow the user to to proceed if no additional roles are required to access the route.
      if (!(requiredRoles instanceof Array) || requiredRoles.length === 0) {
          return true;
      }

      // Allow the user to proceed if all the required roles are present.
      return requiredRoles.every((role) => this.roles.includes(role));
  }

}
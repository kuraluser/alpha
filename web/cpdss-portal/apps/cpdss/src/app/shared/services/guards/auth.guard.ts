import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot, Router, ActivatedRoute } from '@angular/router';
import { KeycloakAuthGuard, KeycloakService, KeycloakOptions } from 'keycloak-angular';
import { AppConfigurationService } from '../app-configuration/app-configuration.service';

/**
 *  it extends keycloak-auth-guard to guard user on authenticating
 *  will read query-params from url for getting realm and company-logo url
 */

@Injectable({
    providedIn: 'root'
})
export class AuthGuard extends KeycloakAuthGuard {

    constructor(protected readonly router: Router, protected readonly keycloak: KeycloakService, private route: ActivatedRoute) {
        super(router, keycloak);
    }

    public async isAccessAllowed(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {

        // Force the user to log in if currently unauthenticated.
        if (!this.authenticated) {
            window.location.href = window.location.protocol + '//' + window.location.hostname + ':' + AppConfigurationService.settings.redirectPort;
        }

        const loggedIn = await this.keycloak.isLoggedIn();
        if (loggedIn) {
            setTimeout(() => {
                this.router.navigate(['business']);
            }, 500);
        }
        else {
            window.location.href = window.location.protocol + '//' + window.location.hostname + ':' + AppConfigurationService.settings.redirectPort;
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

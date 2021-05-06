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
export class ShoreAuthGuard extends KeycloakAuthGuard {

    constructor(protected readonly router: Router, protected readonly keycloak: KeycloakService, private route: ActivatedRoute) {
        super(router, keycloak);
    }

    public async isAccessAllowed(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {

        // Force the user to log in if currently unauthenticated.
        if (!this.authenticated || this.keycloak.isTokenExpired()) {
            window.location.href = window.location.protocol + '//' + window.location.hostname + AppConfigurationService.settings.redirectPath;
        }

        const loggedIn = await this.keycloak.isLoggedIn();
        if (loggedIn) {
            // *** commented after first time login user task implementation - DSS-1634 ***
            /* setTimeout(() => {
                this.router.navigate(['business']);
            }, 500); */
          return true;
        }
        else {
            window.location.href = window.location.protocol + '//' + window.location.hostname + AppConfigurationService.settings.redirectPath;
        }
        return false;
    }

}

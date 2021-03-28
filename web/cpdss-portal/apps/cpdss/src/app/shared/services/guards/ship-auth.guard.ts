import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot, Router, ActivatedRoute } from '@angular/router';
import { KeycloakAuthGuard, KeycloakService, KeycloakOptions } from 'keycloak-angular';
import { AppConfigurationService } from '../app-configuration/app-configuration.service';

/**
 *  
 *  auth guard for ship 
 */
@Injectable({
    providedIn: 'root'
})
export class ShipAuthGuard {

    constructor(protected readonly router: Router, private route: ActivatedRoute) {
    }

    public async canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        setTimeout(() => {
            this.router.navigate(['business']);
        }, 500);
        return true;
    }

}
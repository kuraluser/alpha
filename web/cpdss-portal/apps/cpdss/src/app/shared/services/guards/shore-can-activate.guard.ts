import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot, ActivatedRoute, Router } from '@angular/router';
import { KeycloakAuthGuard, KeycloakService } from 'keycloak-angular';

@Injectable({
  providedIn: 'root'
})
/*
 Can activate guard to prevent from entering a particular url based on the permissions
*/
export class ShoreCanActivateGuard extends KeycloakAuthGuard {

  constructor(protected readonly router: Router, protected readonly keycloak: KeycloakService, private route: ActivatedRoute) {
    super(router, keycloak);
  }

  public async isAccessAllowed(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {

    // Redirect to default route if currently unauthenticated.
    if (!this.authenticated || this.keycloak.isTokenExpired()) {
      this.router.navigate(['/']);
      return false;
    }
    const hasRole = localStorage.getItem('_USER_PERMISSIONS');
    if(!hasRole){
      this.router.navigate(['/access-denied']);
      return false;
    }
    return true;
  }

}

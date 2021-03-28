import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot, ActivatedRoute, Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
/*
 Can activate guard to prevent from entering a particular url based on the permissions
*/
export class ShipCanActivateGuard {

  constructor(protected readonly router: Router, private route: ActivatedRoute) {
  }

  public async canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    return true;
  }

}
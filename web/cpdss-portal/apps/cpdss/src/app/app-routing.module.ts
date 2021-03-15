import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ShoreAuthGuard } from '../app/shared/services/guards/shore-auth.guard';
import { ShipAuthGuard } from '../app/shared/services/guards/ship-auth.guard';
import { ShipCanActivateGuard } from '../app/shared/services/guards/ship-can-activate.guard';
import { ShoreCanActivateGuard } from '../app/shared/services/guards/shore-can-activate.guard';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { AccessDeniedComponent } from './access-denied/access-denied.component';
import { environment } from '../environments/environment';

let authGuard = [], canActivateGuard=[];
if(environment.name === 'shore'){
  authGuard = [ShoreAuthGuard]
  canActivateGuard = [ShoreCanActivateGuard]
} else {
  authGuard = [ShipAuthGuard]
  canActivateGuard = [ShipCanActivateGuard]
}
const routes: Routes = [
  { path: '', loadChildren: () => import('./login/login.module').then(m => m.LoginModule), canActivate: authGuard },
  { path: 'business', loadChildren: () => import('./business/business.module').then(m => m.BusinessModule), canActivate: canActivateGuard },
  { path: 'access-denied', component: AccessDeniedComponent },
  { path: '**', component: PageNotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

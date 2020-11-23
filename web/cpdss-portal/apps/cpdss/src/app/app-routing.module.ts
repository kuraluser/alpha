import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from '../app/shared/services/guards/auth.guard';
import { CanActivateGuard } from '../app/shared/services/guards/can-activate.guard';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { AccessDeniedComponent } from './access-denied/access-denied.component';

const routes: Routes = [
  { path: '', loadChildren: () => import('./login/login.module').then(m => m.LoginModule), canActivate: [AuthGuard] },
  { path: 'business', loadChildren: () => import('./business/business.module').then(m => m.BusinessModule), canActivate: [CanActivateGuard] },
  { path: 'access-denied', component: AccessDeniedComponent },
  { path: '**', component: PageNotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

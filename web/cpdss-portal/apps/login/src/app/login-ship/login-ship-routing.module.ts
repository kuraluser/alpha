import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginShipComponent } from './login-ship.component';

const routes: Routes = [{ path: '', component: LoginShipComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class LoginShipRoutingModule { }

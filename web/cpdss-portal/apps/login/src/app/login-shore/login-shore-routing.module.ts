import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginShoreComponent } from './login-shore.component';

const routes: Routes = [{ path: '', component: LoginShoreComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class LoginShoreRoutingModule { }

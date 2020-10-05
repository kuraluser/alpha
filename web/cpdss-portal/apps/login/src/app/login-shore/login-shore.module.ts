import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LoginShoreRoutingModule } from './login-shore-routing.module';
import { LoginShoreComponent } from './login-shore.component';


@NgModule({
  declarations: [LoginShoreComponent],
  imports: [
    CommonModule,
    LoginShoreRoutingModule
  ]
})
export class LoginShoreModule { }

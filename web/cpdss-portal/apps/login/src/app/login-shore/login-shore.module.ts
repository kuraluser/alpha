import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LoginShoreRoutingModule } from './login-shore-routing.module';
import { LoginShoreComponent } from './login-shore.component';
import { TranslateModule } from '@ngx-translate/core';

// this module will load all the dependencies of shore-login

@NgModule({
  declarations: [LoginShoreComponent],
  imports: [
    CommonModule,
    LoginShoreRoutingModule,
    TranslateModule
  ]
})
export class LoginShoreModule { }

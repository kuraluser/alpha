import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LogoutRoutingModule } from './logout-routing.module';
import { LogoutComponent } from './logout.component';

/**
 * Module for Logout
 *
 * @export
 * @class LogoutModule
 */
@NgModule({
  declarations: [LogoutComponent],
  imports: [
    CommonModule,
    LogoutRoutingModule
  ]
})
export class LogoutModule { }

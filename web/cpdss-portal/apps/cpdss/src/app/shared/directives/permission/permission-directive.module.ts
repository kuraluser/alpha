import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PermissionDirective } from './permission.directive';


/**
 * Module for permission directive
 *
 * @export
 * @class PermissionDirectiveModule
 */
@NgModule({
  declarations: [PermissionDirective],
  imports: [
    CommonModule
  ],
  exports: [PermissionDirective]
})
export class PermissionDirectiveModule { }

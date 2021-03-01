import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';
import { DatatableModule } from '../../../shared/components/datatable/datatable.module';
import { PermissionDirectiveModule } from '../../../shared/directives/permission/permission-directive.module';

import { UserRoutingModule } from './user-routing.module'
import { UserListingComponent } from './user-listing/user-listing.component'

/**
 * Module class User Module
 *
 * @export
 * @class UserModule
 */
@NgModule({
  declarations: [UserListingComponent],
  imports: [
    CommonModule,
    TranslateModule,
    DatatableModule,
    PermissionDirectiveModule,
    UserRoutingModule
  ]
})
export class UserModule { }

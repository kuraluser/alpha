import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

import { TreeTableModule } from 'primeng/treetable';
import { TableModule } from 'primeng/table';
import { TranslateModule } from '@ngx-translate/core';
import { DialogModule } from 'primeng/dialog';


import { DatatableModule } from '../../../shared/components/datatable/datatable.module';
import { ValidationErrorModule } from '../../../shared/components/validation-error/validation-error.module';

import { UserRolePermissionRoutingModule } from './user-role-permission-routing.module';
import { RolePermissionComponent } from './user-role-permission/role-permission/role-permission.component';
import { UserAllocateComponent } from './user-role-permission/user-allocate/user-allocate.component';
import { UserRoleListingComponent } from './user-role-listing/user-role-listing.component';
import { AddUserRoleComponent } from './add-user-role/add-user-role.component';
import { PermissionDirectiveModule } from '../../../shared/directives/permission/permission-directive.module';

/**
 * Module class User Role Permission Module
 *
 * @export
 * @class UserRolePermissionModule
 */
@NgModule({
  declarations: [RolePermissionComponent, UserAllocateComponent, UserRoleListingComponent , AddUserRoleComponent ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    TranslateModule,
    TreeTableModule,
    DatatableModule,
    DialogModule,
    ValidationErrorModule,
    TableModule,
    PermissionDirectiveModule,
    UserRolePermissionRoutingModule,
  ]
})
export class UserRolePermissionModule { }

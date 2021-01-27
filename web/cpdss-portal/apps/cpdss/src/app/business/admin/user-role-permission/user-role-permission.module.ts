import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TreeTableModule } from 'primeng/treetable';
import { TableModule } from 'primeng/table';
import { TranslateModule } from '@ngx-translate/core';

import { UserRolePermissionRoutingModule } from './user-role-permission-routing.module';
import { RolePermissionComponent } from './user-role-permission/role-permission/role-permission.component';
import { UserAllocateComponent } from './user-role-permission/user-allocate/user-allocate.component';

/**
 * Module class User Role Permission Module
 *
 * @export
 * @class UserRolePermissionModule
 */
@NgModule({
  declarations: [RolePermissionComponent, UserAllocateComponent],
  imports: [
    CommonModule,
    UserRolePermissionRoutingModule,
    TranslateModule,
    TreeTableModule,
    TableModule
  ]
})
export class UserRolePermissionModule { }

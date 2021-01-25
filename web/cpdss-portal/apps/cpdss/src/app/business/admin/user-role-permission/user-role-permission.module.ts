import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {TreeTableModule} from 'primeng/treetable';


import { UserRolePermissionRoutingModule } from './user-role-permission-routing.module';
import { RolePermissionComponent } from './user-role-permission/role-permission/role-permission.component';

/**
 * Module class User Role Permission Module
 *
 * @export
 * @class UserRolePermissionModule
 */
@NgModule({
  declarations: [RolePermissionComponent],
  imports: [
    CommonModule,
    TreeTableModule,
    UserRolePermissionRoutingModule
  ]
})
export class UserRolePermissionModule { }

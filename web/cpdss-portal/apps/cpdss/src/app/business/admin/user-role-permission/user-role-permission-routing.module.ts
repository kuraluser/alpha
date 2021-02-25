import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RolePermissionComponent } from './user-role-permission/role-permission/role-permission.component';
import { UserRoleListingComponent } from './user-role-listing/user-role-listing.component';

const routes: Routes = [
    {
        path: '',
        component: UserRoleListingComponent
    },
    {
        path: ':roleId',
        component: RolePermissionComponent
    }
];

/**
 * Routing Module for User Role Permission Routing Module
 *
 * @export
 * @class UserRolePermissionRoutingModule
 */

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class UserRolePermissionRoutingModule { }

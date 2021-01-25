import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AdminComponent } from './admin.component';

const routes: Routes = [
    {
        path: '',
        component: AdminComponent,
        children: [
            { path: '', redirectTo: 'user-role-permission', pathMatch: 'full' },
            {
                path: 'user-role-permission',
                loadChildren: () => import('./user-role-permission/user-role-permission.module').then(m => m.UserRolePermissionModule)
            }
        ]
    },
];

/**
 * Routing Module for Admin permission screen
 *
 * @export
 * @class AdminRoutingModule
 */
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class AdminRoutingModule { }

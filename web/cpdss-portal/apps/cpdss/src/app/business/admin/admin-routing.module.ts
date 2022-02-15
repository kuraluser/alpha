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
            },
            {
                path: 'user-listing',
                loadChildren: () => import('./user/user.module').then(m => m.UserModule)
            },
            {
                path: 'port-listing',
                loadChildren: () => import('./port-master/port-master.module').then(m => m.PortMasterModule)
            },
            {
                path: 'rules',
                loadChildren: () => import('./rules/rules.module').then(m => m.RulesModule)
            },
            {
              path: 'cargo-master',
              loadChildren: () => import('./cargo-master/cargo-master.module').then(m => m.CargoMasterModule)
            },
            {
              path: 'vessel-information',
              loadChildren: () => import('./vessel-information/vessel-information.module').then(m => m.VesselInformationModule)
            },
            {
              path: 'crew-listing',
              loadChildren: () => import('./crew-details/crew-details.module').then(m => m.CrewDetailsModule)
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

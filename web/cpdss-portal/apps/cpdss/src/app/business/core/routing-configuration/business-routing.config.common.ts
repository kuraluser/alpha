import { Routes } from '@angular/router';

export class BusinessRoutingCommonConfig {
    static getRoutesForEnvironment(): Routes {
        return [
            {
                path: 'voyage-status',
                loadChildren: () => import('../../voyage-status/voyage-status.module').then(m => m.VoyageStatusModule)
            },
            {
                path: 'cargo-planning',
                loadChildren: () => import('../../cargo-planning/cargo-planning.module').then(m => m.CargoPlanningModule)
            },
            { path: 'operations', loadChildren: () => import('../../operations/operations.module').then(m => m.OperationsModule) },
            { path: 'synoptical', loadChildren: () => import('../../synoptical/synoptical.module').then(m => m.SynopticalModule) },
            { path: 'admin', loadChildren: () => import('../../admin/admin.module').then(m => m.AdminModule) },
            { path: 'voyage-list', loadChildren: () => import('../../voyages/voyages.module').then(m => m.VoyagesModule) },
            { path: 'file-repository', loadChildren: () => import('../../file-repository/file-repository.module').then(m => m.FileRepositoryModule) }
        ];
    }
}

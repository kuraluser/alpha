import { Routes } from '@angular/router';
import { VoyageStatusComponent } from '../../voyage-status/voyage-status.component';

export class BusinessRoutingCommonConfig {
    static getRoutesForEnvironment(): Routes {
        return [
            { path: '', redirectTo: 'voyage-status', pathMatch: 'full' },
            {
                path: 'voyage-status',
                loadChildren: () => import('../../voyage-status/voyage-status.module').then(m => m.VoyageStatusModule)
            },
            {
                path: 'cargo-planning',
                loadChildren: () => import('../../cargo-planning/cargo-planning.module').then(m => m.CargoPlanningModule)
            },
            { path: 'operations', loadChildren: () => import('../../operations/operations.module').then(m => m.OperationsModule) },
        ];
    }
}

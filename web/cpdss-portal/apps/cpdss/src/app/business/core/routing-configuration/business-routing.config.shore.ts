import { Routes } from '@angular/router';
import { BusinessRoutingCommonConfig } from './business-routing.config.common';
import { BusinessComponent } from '../../business.component';

export class BusinessRoutingConfig {
    static getRoutesForEnvironment(): Routes {
        return [
            {
                path: '',
                component: BusinessComponent,
                children: [
                    ...BusinessRoutingCommonConfig.getRoutesForEnvironment(),                  
                    { path: 'voyages', loadChildren: () => import('../../voyages/voyages.module').then(m => m.VoyagesModule) }
                ]
            }
        ];
    }
}

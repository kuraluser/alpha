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
                    { path: '', redirectTo: 'voyage-status', pathMatch: 'full' },
                    ...BusinessRoutingCommonConfig.getRoutesForEnvironment(),
                ]
            }
        ];
    }
}

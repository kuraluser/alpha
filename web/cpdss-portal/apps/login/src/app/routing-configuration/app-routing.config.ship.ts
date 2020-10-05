import { Routes } from '@angular/router';
import { AppRoutingCommonConfig } from './app-routing.config.common';

export class AppRoutingConfig {
    static getRoutesForEnvironment(): Routes {
        return [
            ...AppRoutingCommonConfig.getRoutesForEnvironment(),
            { path: 'login', loadChildren: () => import('../login-ship/login-ship.module').then(m => m.LoginShipModule) }
        ];
    }
}

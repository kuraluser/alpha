import { Routes } from '@angular/router';

export class AppRoutingCommonConfig {
    static getRoutesForEnvironment(): Routes {
        return [
            { path: '', redirectTo: '/login', pathMatch: 'full' },
        ];
    }
}

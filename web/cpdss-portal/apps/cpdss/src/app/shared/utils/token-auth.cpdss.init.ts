import { HttpClient } from '@angular/common/http';
import { IAppConfiguration } from '../services/app-configuration/app-configuration.model';
import { AppConfigurationService } from '../services/app-configuration/app-configuration.service';
import { SecurityService } from '../services/security/security.service';

/**
 *  initializer function for main ship application
 */


export function tokenAuthCPDSSInitializer(http: HttpClient, appConfig: AppConfigurationService): () => Promise<any> {
    return async (): Promise<any> => {
        const appSettings: IAppConfiguration = await appConfig.load();
        const logoutUrl = window.location.protocol + '//' + window.location.hostname + appSettings.redirectPath;

        return new Promise<void>(async (resolve, reject) => {
            try {
                if (window.location.search) {
                    const arr = window.location.search.split('&')
                    let realm = arr[0].split('=')[1];
                    if (realm) {
                        localStorage.setItem('realm', realm);
                    } else if (localStorage.getItem('realm') !== undefined && localStorage.getItem('realm') !== 'undefined' && localStorage.getItem('realm') !== '' && localStorage.getItem('realm') !== null) {
                        realm = localStorage.getItem('realm');
                    }
                    let imgUri = arr[1].split('=')[1];
                    if (imgUri) {
                        localStorage.setItem('companyLogo', imgUri);
                    } else if (localStorage.getItem('companyLogo') !== undefined && localStorage.getItem('companyLogo') !== 'undefined' && localStorage.getItem('companyLogo') !== '' && localStorage.getItem('companyLogo') !== null) {
                        imgUri = localStorage.getItem('companyLogo');
                    }
                    let token = arr[2].split('=')[1];
                    if (token) {
                        localStorage.setItem('token', token);
                    } else if (localStorage.getItem('token') !== undefined && localStorage.getItem('token') !== 'undefined' && localStorage.getItem('token') !== '' && localStorage.getItem('token') !== null) {
                        token = localStorage.getItem('token');
                        //TODO: Handling scenario when user manually cleares service worker and indexed db
                        /* const isPropertyExist = await SecurityService.getPropertiesDB('token');
                        if(token && !isPropertyExist) {
                            SecurityService.initPropertiesDB(token);
                        } */
                    }
                    let favicon = arr[3].split('=')[1];
                    if (favicon) {
                        localStorage.setItem('favicon', favicon);
                    } else if (localStorage.getItem('favicon') !== undefined && localStorage.getItem('favicon') !== 'undefined' && localStorage.getItem('favicon') !== '' && localStorage.getItem('favicon') !== null) {
                        favicon = localStorage.getItem('favicon');
                    }
                    
                    if (arr[4]) {
                        let daysRemain = arr[4].split('=')[1];
                        if (daysRemain) {
                            localStorage.setItem('daysRemain', daysRemain);
                        } else if (localStorage.getItem('daysRemain') !== undefined && localStorage.getItem('daysRemain') !== 'undefined' && localStorage.getItem('daysRemain') !== '' && localStorage.getItem('daysRemain') !== null) {
                            daysRemain = localStorage.getItem('daysRemain');
                        }
                    }
                }
                resolve();
            } catch (error) {
                // If error redirect to login app
                window.location.href = logoutUrl;
                reject(error);
            }
        });
    };
}

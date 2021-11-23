import { HttpClient } from '@angular/common/http';
import { IAppConfiguration } from '../services/app-configuration/app-configuration.model';
import { AppConfigurationService } from '../services/app-configuration/app-configuration.service';

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
                    const arr = window.location.search.split('&');

                    let realm = window.location.search.indexOf('realm=') >= 0 ? arr[0].split('=')[1] : null;
                    if (realm) {
                        localStorage.setItem('realm', realm);
                    } else if (localStorage.getItem('realm') !== undefined && localStorage.getItem('realm') !== 'undefined' && localStorage.getItem('realm') !== '' && localStorage.getItem('realm') !== null) {
                        realm = localStorage.getItem('realm');
                    }

                    let imgUri = window.location.search.indexOf('logoUrl=') >= 0 ? arr[1].split('=')[1] : null;
                    if (imgUri) {
                        localStorage.setItem('companyLogo', imgUri);
                    } else if (localStorage.getItem('companyLogo') !== undefined && localStorage.getItem('companyLogo') !== 'undefined' && localStorage.getItem('companyLogo') !== '' && localStorage.getItem('companyLogo') !== null) {
                        imgUri = localStorage.getItem('companyLogo');
                    }

                    let token = window.location.search.indexOf('token=') >= 0 ? arr[2].split('=')[1] : null;
                    if (token) {
                        localStorage.setItem('token', token);
                    } else if (localStorage.getItem('token') !== undefined && localStorage.getItem('token') !== 'undefined' && localStorage.getItem('token') !== '' && localStorage.getItem('token') !== null) {
                        token = localStorage.getItem('token');
                    }

                    let favicon = window.location.search.indexOf('faviconUrl=') >= 0 ? arr[3].split('=')[1] : null;
                    if (favicon) {
                        localStorage.setItem('favicon', favicon);
                    } else if (localStorage.getItem('favicon') !== undefined && localStorage.getItem('favicon') !== 'undefined' && localStorage.getItem('favicon') !== '' && localStorage.getItem('favicon') !== null) {
                        favicon = localStorage.getItem('favicon');
                    }

                    const simulatorUrlIndex =  window.location.search.indexOf('simulatorUrl=') > -1 && arr.findIndex((queryParam) => queryParam.includes('simulatorUrl='));
                    let simulatorSiteUrl = simulatorUrlIndex ? arr[simulatorUrlIndex].split('=')[1] : null;
                    if (simulatorSiteUrl) {
                        localStorage.setItem('simulatorSiteUrl', simulatorSiteUrl);
                    } else if (localStorage.getItem('simulatorSiteUrl') !== undefined && localStorage.getItem('simulatorSiteUrl') !== 'undefined' && localStorage.getItem('simulatorSiteUrl') !== '' && localStorage.getItem('simulatorSiteUrl') !== null) {
                        simulatorSiteUrl = localStorage.getItem('simulatorSiteUrl');
                    }

                    const docsUrlKey = 'docsUrl';
                    const docsUrlIndex = window.location.search.indexOf(docsUrlKey);
                    let docsUri = window.location.search.substring(docsUrlIndex + docsUrlKey.length + 1);
                    if (docsUri) {
                      localStorage.setItem('docsUrl', docsUri);
                    } else if (localStorage.getItem('docsUrl') !== undefined && localStorage.getItem('docsUrl') !== 'undefined' && localStorage.getItem('docsUrl') !== '' && localStorage.getItem('docsUrl') !== null) {
                      docsUri = localStorage.getItem('docsUrl');
                    }

                    if (arr[4]) {
                        let daysRemain = window.location.search.indexOf('daysRemain=') >= 0 ? arr[4].split('=')[1] : null;
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

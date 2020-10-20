import { KeycloakService } from 'keycloak-angular';
import { environment } from '../../../environments/environment';
import { KeycloakConfig } from 'keycloak-js';
import { HttpClient } from '@angular/common/http';
import { IAppConfiguration } from '../services/app-configuration/app-configuration.model';
import { AppConfigurationService } from '../services/app-configuration/app-configuration.service';
import { ActivatedRoute, Router } from '@angular/router';

/**
 *  keycloak initializer function for main shore application
 */

let keycloakConfig: KeycloakConfig = { url: '', realm: '', clientId: '' }

export function keycloakCPDSSInitializer(keycloak: KeycloakService, http: HttpClient, appConfig: AppConfigurationService): () => Promise<any> {
    return (): Promise<any> => {
        return new Promise(async (resolve, reject) => {
            try {
                let imageIndex = window.location.search.indexOf('imgurl');
                let imgUri = window.location.search.substring(imageIndex + 7);
                if (localStorage.getItem('companyLogo') !== undefined && localStorage.getItem('companyLogo') !== 'undefined' && localStorage.getItem('companyLogo') !== '' && localStorage.getItem('companyLogo') !== null) {
                    imgUri = localStorage.getItem('companyLogo');
                }
                else {
                    localStorage.setItem('companyLogo', imgUri);
                }

                let realm;
                if (localStorage.getItem('realm') !== undefined && localStorage.getItem('realm') !== 'undefined' && localStorage.getItem('realm') !== '' && localStorage.getItem('realm') !== null) {
                    realm = localStorage.getItem('realm');
                }
                else {
                    realm = window.location.search.split('&')[0].split('=')[1];
                    localStorage.setItem('realm', realm);
                }

                let hostname = window.location.hostname.split('.')[0];
                let hostUrl: string = '';
                let appSettings: IAppConfiguration = await appConfig.load();
                keycloakConfig = {
                    url: appSettings.keycloakUrl,
                    realm: realm,
                    clientId: 'frontend'
                }
                hostUrl = 'companies/' + hostname + '.cpdss.com/idp-info';

                await keycloak.init({
                    config: keycloakConfig,
                    initOptions: {
                        onLoad: 'check-sso',
                        silentCheckSsoRedirectUri: window.location.origin + '/assets/keycloak/silent-check-sso.html',
                        checkLoginIframe: false
                    },
                    bearerExcludedUrls: ['/assets']
                });

                resolve();
            } catch (error) {
                reject(error);
            }
        });
    };
}

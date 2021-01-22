import { KeycloakService } from 'keycloak-angular';
import { KeycloakConfig } from 'keycloak-js';
import { HttpClient } from '@angular/common/http';
import { IAppConfiguration } from '../services/app-configuration/app-configuration.model';
import { AppConfigurationService } from '../services/app-configuration/app-configuration.service';

/**
 *  keycloak initializer function for main shore application
 */

let keycloakConfig: KeycloakConfig = { url: '', realm: '', clientId: '' }

export function keycloakCPDSSInitializer(keycloak: KeycloakService, http: HttpClient, appConfig: AppConfigurationService): () => Promise<any> {
    return async (): Promise<any> => {
        const appSettings: IAppConfiguration = await appConfig.load();
        const logoutUrl = window.location.protocol + '//' + window.location.hostname + ':' + appSettings.redirectPort;

        return new Promise(async (resolve, reject) => {
            try {
                const logoKey = 'logoUrl';
                const imageIndex = window.location.search.indexOf(logoKey);
                let imgUri = window.location.search.substring(imageIndex + logoKey.length + 1);
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
                    if (realm) {
                        localStorage.setItem('realm', realm);
                    }
                }


                keycloakConfig = {
                    url: appSettings.keycloakUrl,
                    realm: realm,
                    clientId: appSettings.clientId
                }

                const isLoggedIn = await keycloak.init({
                    config: keycloakConfig,
                    initOptions: {
                        onLoad: 'check-sso',
                        silentCheckSsoRedirectUri: window.location.origin + '/assets/keycloak/silent-check-sso.html',
                        checkLoginIframe: false
                    },
                    bearerExcludedUrls: ['/assets']
                });

                const keycloakInstance = keycloak.getKeycloakInstance();

                // If not logged in redirect to login app
                if (!isLoggedIn) {
                    keycloakInstance.login();
                } else {
                    //If token expired
                    keycloakInstance.onTokenExpired = () => {
                        
                        if (keycloakInstance.refreshToken) {
                            const res = keycloakInstance.updateToken(5);
                            if (res) {
                                return keycloakInstance.token;
                            } else {
                                keycloakInstance.login();
                            }
                        } else {
                            keycloakInstance.login();
                        }
                    };
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

import { KeycloakEvent, KeycloakEventType, KeycloakService } from 'keycloak-angular';
import { KeycloakConfig } from 'keycloak-js';
import { HttpClient } from '@angular/common/http';
import { IAppConfiguration } from '../services/app-configuration/app-configuration.model';
import { AppConfigurationService } from '../services/app-configuration/app-configuration.service';
import { SecurityService } from '../services/security/security.service';

/**
 *  keycloak initializer function for main shore application
 */

let keycloakConfig: KeycloakConfig = { url: '', realm: '', clientId: '' }

export function keycloakCPDSSInitializer(keycloak: KeycloakService, http: HttpClient, appConfig: AppConfigurationService): () => Promise<any> {
    return async (): Promise<any> => {
        const appSettings: IAppConfiguration = await appConfig.load();
        const logoutUrl = window.location.protocol + '//' + window.location.hostname + appSettings.redirectPath;

        return new Promise(async (resolve, reject) => {
            try {
                localStorage.setItem('alertForVoyageEnd', 'false');
                const logoKey = 'logoUrl';
                const imageIndex = window.location.search.indexOf(logoKey);
                let imgUri = window.location.search.substring(imageIndex + logoKey.length + 1);
                imgUri = imgUri.split('&')[0];
                if(imgUri) {
                    localStorage.setItem('companyLogo', imgUri);
                } else if(localStorage.getItem('companyLogo') !== undefined && localStorage.getItem('companyLogo') !== 'undefined' && localStorage.getItem('companyLogo') !== '' && localStorage.getItem('companyLogo') !== null) {
                    imgUri = localStorage.getItem('companyLogo');
                }
                const faviconKey = 'faviconUrl';
                const iconIndex = window.location.search.indexOf(faviconKey);
                let iconUri = window.location.search.substring(iconIndex + faviconKey.length + 1);
                if(iconUri) {
                    localStorage.setItem('favicon', iconUri);
                } else if(localStorage.getItem('favicon') !== undefined && localStorage.getItem('favicon') !== 'undefined' && localStorage.getItem('favicon') !== '' && localStorage.getItem('favicon') !== null) {
                    iconUri = localStorage.getItem('favicon');
                }

                let realm = window.location.search.split('&')[0].split('=')[1];
                if (realm) {
                    localStorage.setItem('realm', realm);
                } else if (localStorage.getItem('realm') !== undefined && localStorage.getItem('realm') !== 'undefined' && localStorage.getItem('realm') !== '' && localStorage.getItem('realm') !== null) {
                    realm = localStorage.getItem('realm');
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
                        // TODO: Code commented as per requested by Suhail 
                        //silentCheckSsoRedirectUri: window.location.origin + appSettings?.path + '/assets/keycloak/silent-check-sso.html',
                        checkLoginIframe: false
                    },
                    bearerExcludedUrls: ['/assets']
                });

                const keycloakInstance = keycloak.getKeycloakInstance();

                // If not logged in redirect to login app
                if (!isLoggedIn) {
                    window.location.href = logoutUrl;
                } else {
                    //TODO: Handling scenario when user manually cleares service worker and indexed db
                    /* const token = localStorage.getItem('token');
                    const isPropertyExist = await SecurityService.getPropertiesDB('token');
                    if(token && !isPropertyExist) {
                        SecurityService.initPropertiesDB(token);
                    } */

                    //If token expired
                    keycloak.keycloakEvents$.subscribe(async (response: KeycloakEvent) => {
                        if (response.type === KeycloakEventType.OnTokenExpired) {
                            if (keycloakInstance.refreshToken) {
                                const refreshed = await keycloakInstance.updateToken(300);
                                if (refreshed) {
                                    SecurityService.setAuthToken(keycloakInstance.token);
                                    SecurityService.setPropertiesDB(keycloakInstance.token, 'token');
                                    return keycloakInstance.token;
                                } else {
                                    window.location.href = logoutUrl;
                                }
                            } else {
                                window.location.href = logoutUrl;
                            }
                        } else if (response.type === KeycloakEventType.OnAuthRefreshError) {
                            if (keycloak.isTokenExpired) {
                                window.location.href = logoutUrl;
                            }
                        }
                    });
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

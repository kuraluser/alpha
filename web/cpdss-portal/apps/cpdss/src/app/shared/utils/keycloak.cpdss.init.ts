import { KeycloakEvent, KeycloakEventType, KeycloakService } from 'keycloak-angular';
import { KeycloakConfig } from 'keycloak-js';
import { HttpClient } from '@angular/common/http';
import { IAppConfiguration } from '../services/app-configuration/app-configuration.model';
import { AppConfigurationService } from '../services/app-configuration/app-configuration.service';
import { SecurityService } from '../services/security/security.service';
import { GlobalErrorHandler } from '../../shared/services/error-handlers/global-error-handler';
import { ActivatedRoute } from '@angular/router';
/**
 *  keycloak initializer function for main shore application
 */

let keycloakConfig: KeycloakConfig = { url: '', realm: '', clientId: '' }

export function keycloakCPDSSInitializer(keycloak: KeycloakService, http: HttpClient, appConfig: AppConfigurationService, activatedRoute:ActivatedRoute, globalErrorHandler:GlobalErrorHandler): () => Promise<any> {

    return async (): Promise<any> => {
        const appSettings: IAppConfiguration = await appConfig.load();
        const logoutUrl = window.location.protocol + '//' + window.location.hostname + appSettings.redirectPath;

        return new Promise(async (resolve, reject) => {
            try {
                const queryParamsArr = window.location.search.split('&');

                const imageIndex = window.location.search.indexOf('logoUrl=') > -1 && queryParamsArr.findIndex((queryParam) => queryParam.includes('logoUrl='));
                let imgUri = imageIndex ? queryParamsArr[imageIndex].split('=')[1] : null;
                if (imgUri) {
                    localStorage.setItem('companyLogo', imgUri);
                } else if (localStorage.getItem('companyLogo') !== undefined && localStorage.getItem('companyLogo') !== 'undefined' && localStorage.getItem('companyLogo') !== '' && localStorage.getItem('companyLogo') !== null) {
                    imgUri = localStorage.getItem('companyLogo');
                }

                const iconIndex = window.location.search.indexOf('faviconUrl=') > -1 && queryParamsArr.findIndex((queryParam) => queryParam.includes('faviconUrl='));
                let iconUri = iconIndex ? queryParamsArr[iconIndex].split('=')[1] : null;
                if (iconUri) {
                    localStorage.setItem('favicon', iconUri);
                } else if (localStorage.getItem('favicon') !== undefined && localStorage.getItem('favicon') !== 'undefined' && localStorage.getItem('favicon') !== '' && localStorage.getItem('favicon') !== null) {
                    iconUri = localStorage.getItem('favicon');
                }

                const simulatorUrlIndex =  window.location.search.indexOf('simulatorUrl=') > -1 && queryParamsArr.findIndex((queryParam) => queryParam.includes('simulatorUrl='));
                let simulatorSiteUrl = simulatorUrlIndex ? queryParamsArr[simulatorUrlIndex].split('=')[1] : null;
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

                let realm = window.location.search.indexOf('realm=') >= 0 ? window.location.search.split('&')[0].split('=')[1] : null;
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

                    //If token expired
                    keycloak.keycloakEvents$.subscribe(async (response: KeycloakEvent) => {
                        if (response.type === KeycloakEventType.OnTokenExpired) {
                            if (keycloakInstance.refreshToken) {
                                const refreshed = await keycloakInstance.updateToken(appSettings?.tokenMinValidity ?? 180);
                                if (refreshed) {
                                    SecurityService.setAuthToken(keycloakInstance.token);
                                    SecurityService.initPropertiesDB(keycloakInstance.token);
                                    return keycloakInstance.token;
                                }
                            } else {
                                globalErrorHandler.sessionOutMessage();
                            }
                        } else if (response.type === KeycloakEventType.OnAuthRefreshError) {
                            if (keycloak.isTokenExpired) {
                                globalErrorHandler.sessionOutMessage();
                            }
                        }
                    });
                }

                resolve(true);
            } catch (error) {
                // If error redirect to login app
                window.location.href = logoutUrl;
                reject(error);
            }
        });
    };
}

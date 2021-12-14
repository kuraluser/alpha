import { KeycloakService } from 'keycloak-angular';
import { environment } from '../../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { IAppConfiguration } from '../services/app-configuration/app-configuration.model';
import { AppConfigurationService } from '../services/app-configuration/app-configuration.service';

// initializing keycloak with keycloakService and keycloakConfig
export function keycloakShoreInitializer(keycloak: KeycloakService, http: HttpClient, appConfig: AppConfigurationService): () => Promise<any> {

    return (): Promise<any> => {
        return new Promise(async (resolve, reject) => {
            try {
                const hostname = window.location.hostname.split('.')[0];
                let hostUrl = '';

                hostUrl = environment.name === 'ship' ?  'company/carousals?domain=' + hostname : 'companies/' + hostname + '/idp-info';

                const appSettings: IAppConfiguration = await appConfig.load();

                const httpHeaders = new HttpHeaders().set('Content-Type', 'application/json');
                await http.get(appSettings.apiUrl + environment.uriPath + hostUrl, { headers: httpHeaders }).toPromise().then(async function (response: any) {
                    if (response) {
                        localStorage.setItem('keycloakIdpConfig', response.providers);
                        localStorage.setItem('realm', response.realm);
                        localStorage.setItem('logo', response.logo);
                        localStorage.setItem('docsUrl', response.documentationSiteUrl);
                        localStorage.setItem('carousel', JSON.stringify(response.carousals));
                        localStorage.setItem('favicon', response.favicon);
                        localStorage.setItem('simulatorSiteUrl', response.simulatorSiteUrl);
                        if(environment.name === 'shore'){
                            const keycloakUrl = appSettings.keycloakUrl;
                            const keycloakConfig = {
                                url: keycloakUrl,
                                realm: response.realm,
                                clientId: appSettings.clientId,
                            }

                            const isLoggedIn = await keycloak.init({
                                config: keycloakConfig,
                                initOptions: {
                                    onLoad: 'check-sso',
                                    checkLoginIframe: false
                                },
                                enableBearerInterceptor: false
                            });

                            if(isLoggedIn){
                                location.href = window.location.protocol + '//' + window.location.hostname + appSettings.path;
                            }
                        }
                    }
                });
                resolve(true);
            } catch (error) {
                reject(error);
            }
        });
    };
}

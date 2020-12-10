import { KeycloakService } from 'keycloak-angular';
import { environment } from '../../../environments/environment';
import { KeycloakConfig } from 'keycloak-js';
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
                hostUrl = 'companies/' + hostname + '/idp-info';

                const appSettings: IAppConfiguration = await appConfig.load();

                const httpHeaders = new HttpHeaders().set('Content-Type', 'application/json');
                await http.get(environment.uriPath + hostUrl, { headers: httpHeaders }).toPromise().then(function (response: any) {
                    if (response) {
                        localStorage.setItem('keycloakIdpConfig', response.providers);
                        localStorage.setItem('realm', response.realm);
                        localStorage.setItem('logo', response.logo);
                        const keycloakUrl = appSettings.keycloakUrl;
                        const keycloakConfig = {
                            url: keycloakUrl,
                            realm: response.realm,
                            clientId: appSettings.clientId,
                        }

                        keycloak.init({
                            config: keycloakConfig,
                            enableBearerInterceptor: false
                        });
                    }
                });
                resolve();
            } catch (error) {
                reject(error);
            }
        });
    };
}
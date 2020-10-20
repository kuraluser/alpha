import { KeycloakService } from 'keycloak-angular';
import { environment } from '../../../environments/environment';
import { KeycloakConfig } from 'keycloak-js';
import { HttpClient } from '@angular/common/http';
import { IAppConfiguration } from '../services/app-configuration/app-configuration.model';
import { AppConfigurationService } from '../services/app-configuration/app-configuration.service';

// initializing keycloak with keycloakService and keycloakConfig
export function keycloakShoreInitializer(keycloak: KeycloakService, http: HttpClient, appConfig: AppConfigurationService): () => Promise<any> {

    return (): Promise<any> => {
        return new Promise(async (resolve, reject) => {
            try {
                let hostname = window.location.hostname.split('.')[0];
                let hostUrl: string = '';
                hostUrl = 'companies/' + hostname + '.cpdss.com/idp-info';

                let appSettings: IAppConfiguration = await appConfig.load();

                await http.get(environment.uriPath + hostUrl).toPromise().then(function (response: any) {
                    if (response) {
                        localStorage.setItem('keycloakIdpConfig', response.providers);
                        localStorage.setItem('realm', response.realm);
                        localStorage.setItem('logoUrl', response.logoUrl);
                        const keycloakUrl = appSettings.keycloakUrl;
                        let keycloakConfig = {
                            url: keycloakUrl,
                            realm: response.realm,
                            clientId: response.clientId,
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
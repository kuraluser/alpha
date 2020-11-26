import { IDictionary } from '../../models/common.model';

export interface IAppConfiguration {
    apiUrl: string;
    keycloakUrl: string;
    redirectPort: string;
    clientId: string;
    permissionMapping?: IDictionary<string>;
}


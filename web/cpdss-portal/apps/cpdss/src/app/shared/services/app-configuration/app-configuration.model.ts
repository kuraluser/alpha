import { IDictionary, QUANTITY_UNIT } from '../../models/common.model';

export interface IAppConfiguration {
    apiUrl: string;
    keycloakUrl: string;
    redirectPort: string;
    clientId: string;
    baseUnit: QUANTITY_UNIT;
    volumeBaseUnit: QUANTITY_UNIT;
    permissionMapping?: IDictionary<string>;
}


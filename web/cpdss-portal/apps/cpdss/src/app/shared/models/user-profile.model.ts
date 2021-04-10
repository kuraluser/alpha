import { KeycloakProfile } from 'keycloak-js';
import { IResponse } from './common.model';

/**
 * Interface for User details
 *
 * @export
 * @interface IUserProfile
 * @extends {KeycloakProfile}
 */
export interface IUserProfile extends KeycloakProfile {
    rolePermissions?: IRolePermission;
    statusCode?: number;
    rejectionCount?: number;
}

/**
 * Interface for user role details
 *
 * @export
 * @interface IRolePermission
 */
export interface IRolePermission {
    id: number;
    role: string;
    resources: IResource[];
}

/**
 * Interface for resourse permissions
 *
 * @export
 * @interface IResource
 */
export interface IResource {
    id: number;
    name: string;
    languageKey: string;
    permission: IPermission;
}

/**
 * Interface for permission details
 *
 * @export
 * @interface IPermission
 */
export interface IPermission {
    add: boolean;
    edit: boolean;
    delete: boolean;
    view: boolean;
}

/**
 * Interface for user details api response
 *
 * @export
 * @interface IUserAuthorizationResponse
 * @extends {IResponse}
 */
export interface IUserAuthorizationResponse extends IResponse {
    user: IUserProfile;
}
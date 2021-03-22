import { IResponseStatus } from '../../../shared/models/common.model';


/**
 * Interface for  user  api response
 *
 * @export
 * @interface IUserResponse
 */
export interface IUserResponse {
    responseStatus: IResponseStatus;
    users: IUserDetails[];
    maxUserCount: number;
    roles: IRoleDetails[];
}


/**
 * Interface for  role details
 *
 * @export
 * @interface IRoleDetails
 */
export interface IRoleDetails {
    companyId: number;
    description: string;
    id: number;
    name: string;
}

/**
 * Interface for  user details
 *
 * @export
 * @interface IUserDetails
 */
export interface IUserDetails {
    designation: string;
    firstName: string;
    id: number;
    lastName: string;
    role: string;
    rolePermissions: string;
    username: string;
    roleId: number;
    isLoginSuspended: boolean;
    defaultUser: boolean;
}

/**
 * Interface for user Details object
 *
 * @export
 * @interface IUserDetalisValueObject
 */
export interface IUserDetalisValueObject {
    designation: string;
    firstName: string;
    id: number;
    lastName: string;
    role: string;
    rolePermissions: string;
    username: string;
    isDelete?: boolean;
    isEditable?: boolean;
    isActionsEnabled: boolean;
    isResetPassword: boolean;   
    isDeletable: boolean;
}

/**
 * Interface for new user object
 *
 * @export
 * @interface IUserModel
*/
export interface IUserModel {
    username: string;
    designation: string;
    roleId: string;
}

/**
 * Interface for new user reponse object
 *
 * @export
 * @interface ISaveUserResponse
*/
export interface ISaveUserResponse {
    responseStatus: IResponseStatus;
    id: number;
}

/**
 *  ENUM for User Popup mode
 *
 * @export
 * @enum {number}
 */
export enum USER_POPUP_SELECTIONMODE {
    ADD = 'ADD',
    EDIT = 'EDIT',
    VIEW = 'VIEW'
}

/**
 * Interface for reset password model
 * @export
 * @interface IResetPasswordModel
 */
export interface IResetPasswordModel {
    userId: number;
    password: string;
}

/**
 * Interface for reset password response
 *
 * @export
 * @interface IResetPasswordResponse
*/
export interface IResetPasswordResponse {
    responseStatus: IResponseStatus;
    id: number;
}

/**
 * Interface for delete user response
 * @export
 * @interface IUserDeleteResponse
*/
export interface IUserDeleteResponse {
    responseStatus: IResponseStatus;
}
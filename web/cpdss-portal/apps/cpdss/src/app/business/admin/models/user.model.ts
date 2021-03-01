import { IResponseStatus } from '../../../shared/models/common.model';


/**
 * Interface for  user  api response
 *
 * @export
 * @interface IUserResponse
 */
export interface IUserResponse {
    responseStatus: IResponseStatus;
    users: IUserDetails[],
}

/**
 * Interface for  user details
 *
 * @export
 * @interface IUserDetails
 */
export interface IUserDetails {
    designation: string,
    firstName: string,
    id: number,
    lastName: string,
    role: string,
    rolePermissions: string
    username: string
}

/**
 * Interface for user Details object
 *
 * @export
 * @interface IUserDetalisValueObject
 */
export interface IUserDetalisValueObject {
    designation: string,
    firstName: string,
    id: number,
    lastName: string,
    role: string,
    rolePermissions: string
    username: string
    isDelete?: boolean;
    isEditable?: boolean
    isActionsEnabled: boolean;    
    isResetPassword: boolean;
}
import { IResponseStatus } from '../../../shared/models/common.model';
import { ICargoTank, ILoadableCargo } from '../../core/models/common.model';


/**
 * Interface for  user role permission api response
 *
 * @export
 * @interface IUserRolePermissionResponse
 */
export interface IUserRolePermissionResponse {
    responseStatus: IResponseStatus;
    screens: IScreenNode[]
}

/**
 * Interface for  user role permission inner nodes
 *
 * @export
 * @interface IScreenNode
 */
export interface IScreenNode {
    id: number,
    name: string,
    languageKey: string,
    add: boolean,
    edit: boolean,
    delete: boolean,
    view: boolean,
    moduleId: number,
    childs: IScreenNode[]
}

/**
 * Interface for  user details api response
 *
 * @export
 * @interface IUserDetailsResponse
 */
export interface IUserDetailsResponse {
    responseStatus: IResponseStatus;
    users: IUserDetail[]
}

/**
 * Interface for  user details
 *
 * @export
 * @interface IUserDetail
 */
export interface IUserDetail {
    id: number,
    rolePermissions: null,
    username: string,
    firstName: string,
    lastName: string,
    name?: string
}

/**
 * Interface for  tree node data
 *
 * @export
 * @interface ITreeNodeData
 */
export interface ITreeNodeData {
    name: string, 
    add: boolean, 
    edit: boolean, 
    delete: boolean, 
    view: boolean, 
    id: number, 
    moduleId: number, 
    isChecked: boolean
}
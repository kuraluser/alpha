import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { AdminModule } from '../admin.module';

import { IUserRolePermissionResponse , IDataStateChange , IUserDetailsResponse , IRoleResponse , ISavePermissionResponse , IUserPermissionModel , IRoleDeleteResponse , IUserRoleModel , ISaveUserRoleResponse } from '../models/user-role-permission.model';

import { CommonApiService } from '../../../shared/services/common/common-api.service';

/**
 * Api Service for user role permission
 *
 * @export
 * @class UserRolePermissionApiService
*/
@Injectable({
  providedIn: AdminModule
})
export class UserRolePermissionApiService {

public page = 0;
public pageSize = 10;
/**
* 
* Api Service for User Role
*/
  constructor(private commonApiService: CommonApiService) { }

  /**
  * 
  * @param {number} roleId
  * Get api for get User Role Permission Details
  */
  getUserRolePermission(roleId: number): Observable<IUserRolePermissionResponse> {
    return this.commonApiService.get<IUserRolePermissionResponse>(`screens/role/${roleId}`);
  }

  /**
  * 
  * Get api for get User Details
  * @memberof UserRolePermissionApiService
  */
 getUserDetails(): Observable<IUserDetailsResponse> {
  return this.commonApiService.get<IUserDetailsResponse>(`users`);
 }
  /**
  * 
  * Get api for get Role Details
  * @memberof UserRolePermissionApiService
  */
 getRoleDetails(params: IDataStateChange): Observable<IRoleResponse> {
  const opts = `${params.name ?  'name='+params.name : ''}${params.desc ?  '&description='+params.desc : ''}&orderBy=${params.orderBy ?  params.orderBy : ''}&page=${params.page ?  params.page : this.page}&pageSize=${params.pageSize ?  params.pageSize : this.pageSize}&sortBy=${params.sortBy ?  params.sortBy : ''}`
  return this.commonApiService.get<IRoleResponse>(`roles?${opts}`);
 }

  /**
 *
 * delete role
 * @param {number} roleId
 * @memberof UserRolePermissionApiService
 */
  deleteRole(roleId: number): Observable<IRoleDeleteResponse> {
    return this.commonApiService.delete<IRoleDeleteResponse>(`user/role/${roleId}`)
  }

  /**
 *
 * add new role
 * @param {IUserRoleModel} newUserRoleData
 * @memberof UserRolePermissionApiService
 */
  saveNewRoleData(newUserRoleData: IUserRoleModel): Observable<ISaveUserRoleResponse> {
    return this.commonApiService.post<IUserRoleModel, ISaveUserRoleResponse>(`user/role`, newUserRoleData);
  }

  /**
 *
 * role permission
 * @param {IUserPermissionModel} savePermission
 * @memberof UserRolePermissionApiService
 */
  rolePermission(savePermission: IUserPermissionModel): Observable<ISavePermissionResponse>{
    return this.commonApiService.post<IUserPermissionModel, ISavePermissionResponse>(`user/role/permission`, savePermission);
  }
}

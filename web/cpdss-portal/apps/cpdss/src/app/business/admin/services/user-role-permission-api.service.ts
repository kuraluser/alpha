import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { AdminModule } from '../admin.module';

import { IUserRolePermissionResponse , IUserDetailsResponse , IRoleResponse , IRoleDeleteResponse , IUserRoleModel , ISaveUserRoleResponse } from '../models/user-role-permission.model';

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
 getRoleDetails(): Observable<IRoleResponse> {
  return this.commonApiService.get<IRoleResponse>(`roles`);
 }

  /**
 *
 * delete role
 * @param {roleId} roleId
 * @memberof UserRolePermissionApiService
 */
  deleteRole(roleId: number): Observable<IRoleDeleteResponse> {
    return this.commonApiService.delete<IRoleDeleteResponse>(`user/role/${roleId}`)
  }

  /**
 *
 * add new role
 * @param {IUserRoleModel} IUserRoleModel
 * @memberof UserRolePermissionApiService
 */
  saveNewRoleData(newUserRoleData: IUserRoleModel): Observable<ISaveUserRoleResponse> {
    return this.commonApiService.post<IUserRoleModel, ISaveUserRoleResponse>(`user/role`, newUserRoleData);
  }

}

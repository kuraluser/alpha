import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { AdminModule } from '../admin.module';

import { IUserRolePermissionResponse , IUserDetailsResponse } from '../models/user-role-permission.model';

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
  */
 getUserDetails(): Observable<IUserDetailsResponse> {
  return this.commonApiService.get<IUserDetailsResponse>(`users`);
 }
}

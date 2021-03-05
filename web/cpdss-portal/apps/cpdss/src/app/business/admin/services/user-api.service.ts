import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { AdminModule } from '../admin.module';

import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { IUserDetails, IUserResponse, IUserModel, ISaveUserResponse, IUserDeleteResponse , IResetPasswordModel, IResetPasswordResponse } from '../models/user.model';

/**
 * Api Service for user
 *
 * @export
 * @class UserApiService
*/
@Injectable({
  providedIn: AdminModule
})
export class UserApiService {

  constructor(private commonApiService: CommonApiService) { }

  /**
* 
* Get api for get User Details
*/
  getUserDetails(): Observable<IUserResponse> {
    return this.commonApiService.get<IUserResponse>(`users`);
  }

  /**
  * 
  * Post api to save new user
  * @param {IUserModel} userDetails
  * @param {IUsnumbererModel} userId
  * @returns {ISaveUserResponse}
  * 
  */
  saveUser(userDetails: IUserModel, userId: number): Observable<ISaveUserResponse> {
    return this.commonApiService.post<IUserModel, ISaveUserResponse>(`users/${userId}`, userDetails);
  }

  /**
   * reset password api 
   * @param {IResetPasswordModel} resetPasswordDetails
   * @memberof UserApiService
  */
  resetPassword(resetPasswordDetails: IResetPasswordModel): Observable<IResetPasswordResponse> {
    return this.commonApiService.post<IResetPasswordModel, IResetPasswordResponse>(`users/reset-password`, resetPasswordDetails);
  }

  /**
  *
  * delete role
  * @param {number} roleId
  * @memberof UserApiService
  */
  deleteUser(userId: number): Observable<IUserDeleteResponse> {
    return this.commonApiService.delete<IUserDeleteResponse>(`user/role/${userId}`)
  }

}

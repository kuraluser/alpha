import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { AdminModule } from '../admin.module';

import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { IUserDetails, IUserResponse , IRoleResponse , IUserModel , ISaveUserResponse } from '../models/user.model';

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
  * Get api for get Role Details
  */
  getRoles(): Observable<IRoleResponse> {
    return this.commonApiService.get<IRoleResponse>(`roles`);
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
  return this.commonApiService.post<IUserModel,ISaveUserResponse>(`users/${userId}`,userDetails);
 }
  
}

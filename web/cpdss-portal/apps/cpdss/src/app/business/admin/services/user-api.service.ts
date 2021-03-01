import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { AdminModule } from '../admin.module';

import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { IUserDetails , IUserResponse } from '../models/user.model';

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
}

import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { IUserAuthorizationResponse } from '../shared/models/user-profile.model';
import { CommonApiService } from '../shared/services/common/common-api.service';

@Injectable()
export class LoginService {

  constructor(private commonApiService: CommonApiService) { }

  /**
   * Method for fetching user details
   *
   * @returns {Observable<IUserAuthorizationResponse>}
   * @memberof LoginService
   */
  getUserDetails(): Observable<IUserAuthorizationResponse> {
    return this.commonApiService.get<IUserAuthorizationResponse>(`user-authorizations`);
  }
}

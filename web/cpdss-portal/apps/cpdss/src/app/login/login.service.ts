import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { IUserAuthorizationResponse } from '../shared/models/user-profile.model';
import { CommonApiService } from '../shared/services/common/common-api.service';

@Injectable()
export class LoginService {

  constructor(private commonApiService: CommonApiService) { }

  getUserDetails(): Observable<IUserAuthorizationResponse> {
    return this.commonApiService.get<any>(`user-authorizations`);
  }
}

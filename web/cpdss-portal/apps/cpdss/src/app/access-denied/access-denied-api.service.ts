import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { CommonApiService } from '../shared/services/common/common-api.service';

/**
 * Api Service for access denied
 *
 * @export
 * @class AccessDeniedApiService
*/
@Injectable()
export class AccessDeniedApiService {

  constructor(private commonApiService: CommonApiService) { }

  /**
   * Method for access permission
   *
   * @memberof AccessDeniedApiService
   */
  getAccess(): Observable<any> {
    return this.commonApiService.get<any>(`user/access`);
  }
}

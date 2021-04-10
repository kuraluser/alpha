import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { CommonApiService } from '../../services/common/common-api.service';

/**
 * Api Service for navbar
 *
 * @export
 * @class NavbarApiService
*/
@Injectable()
export class NavbarApiService {

  constructor(private commonApiService: CommonApiService) { }

  /**
   * Method for admin notification list
   *
   * @memberof NavbarApiService
   */
  getNotification(): Observable<any> {
    return this.commonApiService.get<any>(`user/admin/notifications`);
  }

  /**
   * Method for reject user
   *
   * @memberof NavbarApiService
   */

  rejectUser(userInfo, data): Observable<any> {
    return this.commonApiService.post<any, any>(`user/reject/${userInfo}`, data);
  }
}

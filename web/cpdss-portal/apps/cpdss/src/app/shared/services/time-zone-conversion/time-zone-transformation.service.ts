import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { CommonApiService } from '../common/common-api.service';
import { ITimeZone, ITimeZoneResponse } from '../../models/common.model';
import { map } from 'rxjs/operators';

/**
 * Service for listing and converting Time Zone
 *
 * @export
 * @class TimeZoneTransformationService
 */
@Injectable({
  providedIn: 'root'
})
export class TimeZoneTransformationService {

  constructor(
    private commonAPiService: CommonApiService
  ) { }

  /**
   * function to return list time zone standards
   * @return {*}  {ITimeZone[]}
   * @memberof TimeZoneTransformationService
   */
  getTimeZoneList(): Observable<ITimeZone[]> {
    return this.commonAPiService.get<ITimeZoneResponse>('global-timezones').pipe(map((response) => {
      return response?.timezones;
    }));
  }

  /**
   * function to convert date-time to selected time-zone
   * @param {(string|Date)} dateTime
   * @param {string} zone
   * @return {*}  {string}
   * @memberof TimeZoneTransformationService
   */
  convertToZoneBasedTime(dateTime: Date, offsetValue: string): string {
    const utcOffset = parseFloat(offsetValue.replace(':', '.'));
    const tzDifference = utcOffset * 60 + dateTime.getTimezoneOffset();
    const offsetTime = new Date(dateTime.getTime() + tzDifference * 60 * 1000);
    return this.formatDateTime(offsetTime)
  }

  /**
   * function to format date time (dd-mm-yyyy hh:mm)
   *
   * @param {*} date
   * @return {*} 
   * @memberof TimeZoneTransformationService
   */
  formatDateTime(date) {
    let month = date.getMonth() + 1;
    let day = date.getDate();
    let hour = date.getHours();
    let minute = date.getMinutes();

    if (month < 10) {
      month = '0' + month;
    }

    if (day < 10) {
      day = '0' + day;
    }

    if (hour < 10) {
      hour = '0' + hour;
    }

    if (minute < 10) {
      minute = '0' + minute;
    }

    return day + '-' + month + '-' + date.getFullYear() + ' ' + hour + ':' + minute;
  }

}

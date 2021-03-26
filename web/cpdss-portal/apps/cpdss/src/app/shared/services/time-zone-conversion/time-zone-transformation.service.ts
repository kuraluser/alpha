import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { CommonApiService } from '../common/common-api.service';
import { IDateTimeFormatOptions, ITimeZone, ITimeZoneResponse } from '../../models/common.model';

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
    return this.formatDateTime(offsetTime, {isTime: true});
  }

  /**
   * function to format date time (dd-mm-yyyy) default
   * (dd-mm-yyyy HH:mm) is optional using {formatOptions > isTime}
   * (dd-mm-yyyy HH:mm:ss) is optional using {formatOptions > isTime & isTimeSeconds}
   * @param {*} date
   * @param {IDateTimeFormatOptions} [formatOptions]
   * @return {*} 
   * @memberof TimeZoneTransformationService
   */
  formatDateTime(date, formatOptions?: IDateTimeFormatOptions) {
    let month = date.getMonth() + 1;
    let day = date.getDate();
    let hour = date.getHours();
    let minute = date.getMinutes();
    let seconds = date.getSeconds();

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
    if (seconds < 10) {
      seconds = '0' + seconds;
    }

    let formattedDate = day + '-' + month + '-' + date.getFullYear();

    if (formatOptions?.isTime) {
      formattedDate += ' ' + hour + ':' + minute;
    }
    if (formatOptions?.isTime && formatOptions?.isTimeSeconds) {
      formattedDate += ':' + seconds;
    }

    return formattedDate;
  }

}

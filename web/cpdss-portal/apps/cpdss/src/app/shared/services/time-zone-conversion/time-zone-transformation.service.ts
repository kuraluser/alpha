import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { CommonApiService } from '../common/common-api.service';
import { IDateTimeFormatOptions, ITimeZone, ITimeZoneResponse } from '../../models/common.model';
import * as moment from 'moment';

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
   * function to convert date-time to UTC & UTC to particular offset time zone
   * @param {(Date | string)} dateTime
   * @param {boolean} toUtc
   * @param {string} [offsetValue]
   * @return {*}  {Date}
   * @memberof TimeZoneTransformationService
   */
  convertToZoneBasedTime(dateTime: Date | string, toUtc: boolean, offsetValue?: string): Date {
    const _format = 'YYYY/MM/DD HH:mm';
    let convertToTimezone = toUtc ? moment(dateTime).utc().format(_format) : moment(dateTime).add(offsetValue, 'hours').format(_format);
    const convertedDateTime = moment(convertToTimezone).toDate();
    return convertedDateTime;
  }

  /**
   * function to format date-time
   *
   * @param {*} dateTime
   * @param {IDateTimeFormatOptions} [formatOptions]
   * @return {*}  {string}
   * @memberof TimeZoneTransformationService
   */
  formatDateTime(dateTime, formatOptions?: IDateTimeFormatOptions): string {

    let formattedDate: string;
    const initFormat = 'DD-MM-YYYY HH:mm';
    if (formatOptions?.utcFormat) {
      formattedDate = moment(dateTime, initFormat).format('DD/MMM/YYYY HH:mm').toUpperCase() + ' UTC';
    } else if (formatOptions?.portLocalFormat && formatOptions?.portTimeZoneOffset && formatOptions?.portTimeZoneAbbr) {
      formattedDate = this.modifiedDateTime(dateTime, formatOptions?.portTimeZoneOffset, formatOptions?.portTimeZoneAbbr);
    } else if (formatOptions?.customFormat) {
      formattedDate = moment(dateTime, initFormat).format(formatOptions?.customFormat);
    } else {
      formattedDate = moment(dateTime, initFormat).format('DD-MM-YYYY');
    }
    return formattedDate;
  }

  /**
   * function to modify date-time format with port based zone conversion
   *
   * @param {*} dateTime
   * @param {string} offsetValue
   * @param {string} [abbr]
   * @return {*}  {string}
   * @memberof TimeZoneTransformationService
   */
  modifiedDateTime(dateTime, offsetValue: string, abbr?: string): string {

    let _offsetValue: string;
    const initFormat = 'DD-MM-YYYY HH:mm';
    const abbreviation = ' ' + abbr + ' ';
    if (offsetValue.charAt(0) === '-') {
      let unsignedOffset = offsetValue.split('-')[1];
      if (unsignedOffset.includes('.')) {
        let splittedOffset = offsetValue.split('.');
        splittedOffset[0] = splittedOffset[0].length !== 2 ? '0' + splittedOffset[0] : splittedOffset[0];
        splittedOffset[1] = splittedOffset[1] === '5' && '30';
        _offsetValue = '-' + splittedOffset[0] + ':' + splittedOffset[1];
      } else {
        unsignedOffset = unsignedOffset.length !== 2 ? '0' + unsignedOffset : unsignedOffset;
        _offsetValue = '-' + unsignedOffset + ':00';
      }
    } else {
      if (offsetValue.includes('.')) {
        let splittedOffset = offsetValue.split('.');
        splittedOffset[0] = splittedOffset[0].length !== 2 ? '0' + splittedOffset[0] : splittedOffset[0];
        splittedOffset[1] = splittedOffset[1] === '5' && '30';
        _offsetValue = '+' + splittedOffset[0] + ':' + splittedOffset[1];
      } else {
        offsetValue = offsetValue.length !== 2 ? '0' + offsetValue : offsetValue;
        _offsetValue = '+' + offsetValue + ':00';
      }
    }

    const addedPortTZ = moment(dateTime, initFormat).add(_offsetValue, 'hours').format('DD/MMM/YYYY HH:mm').toUpperCase();
    let convertedPortTZ = moment(dateTime, initFormat).utcOffset(_offsetValue).format('DD/MMM/YYYY HH:mm (UTC Z)');

    const dateTimeSplitIndex = convertedPortTZ.indexOf('(UTC');
    const dateTimeWithAbbr = [addedPortTZ, abbreviation, convertedPortTZ.slice(dateTimeSplitIndex)].join('');
    return dateTimeWithAbbr;
  }

}

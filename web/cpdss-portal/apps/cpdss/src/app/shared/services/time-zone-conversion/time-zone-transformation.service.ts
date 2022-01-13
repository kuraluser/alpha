import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';

import { CommonApiService } from '../common/common-api.service';
import { AppConfigurationService } from '../app-configuration/app-configuration.service';
import { ICountriesResponse, ICountry, IDateTimeFormatOptions, IDaysHours, IMonth, ITimeZone, ITimeZoneResponse } from '../../models/common.model';
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

  private _timezonesDetails: ITimeZone[];
  private _countries: ICountry[];
  private momentInputStringFormat = 'DD-MM-YYYY HH:mm';
  private dateTimeFormatArray = [
    {
      dateFormat: 'DD/MMM/YYYY HH:mm',
      calenderFormat: 'dd/M/yy'
    },
    {
      dateFormat: 'DD-MMM-YYYY HH:mm',
      calenderFormat: 'dd-M-yy'
    }
  ];

  constructor(
    private commonAPiService: CommonApiService
  ) { }

  /**
   * function to return list time zone standards
   * @return {*}  {ITimeZone[]}
   * @memberof TimeZoneTransformationService
   */
  getTimeZoneList(): Observable<ITimeZone[]> {
    if (this._timezonesDetails) {
      return of(this._timezonesDetails);
    } else {
      return this.commonAPiService.get<ITimeZoneResponse>('global-timezones').pipe(map((response) => {
        this._timezonesDetails = response?.timezones;
        return this._timezonesDetails;
      }));
    }
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
    const toDateInput = 'YYYY/MM/DD HH:mm';
    let convertToTimezone: string;
    if (typeof dateTime === 'string') {
      convertToTimezone = toUtc ? moment(dateTime, this.momentInputStringFormat).utc().format(toDateInput) : moment(dateTime, this.momentInputStringFormat).add(offsetValue, 'hours').format(toDateInput);
    } else {
      convertToTimezone = toUtc ? moment(dateTime).utc().format(toDateInput) : moment(dateTime).add(offsetValue, 'hours').format(toDateInput);
    }
    const convertedDateTime = moment(convertToTimezone).toDate();
    return convertedDateTime;
  }

  /**
   * function to revert back the port-based time to UTC
   *
   * @param {(Date | string)} dateTime
   * @param {string} offsetValue
   * @return {*}  {string}
   * @memberof TimeZoneTransformationService
   */
  revertZoneTimetoUTC(dateTime: Date | string, offsetValue: string): string {
    return moment(dateTime, this.momentInputStringFormat).subtract(offsetValue, 'hours').format(this.momentInputStringFormat);
  }

  /**
   * function to format date-time
   *
   * @param {*} dateTime
   * @param {IDateTimeFormatOptions} [formatOptions]
   * @return {*}  {string}
   * @memberof TimeZoneTransformationService
   */
  formatDateTime(dateTime, formatOptions?: IDateTimeFormatOptions): any {
    let formattedDate: Date | string;
    if (formatOptions?.utcFormat) {
      formattedDate = moment(dateTime, this.momentInputStringFormat).format(AppConfigurationService.settings?.dateFormat) + ' UTC';
    } else if (formatOptions?.portLocalFormat && formatOptions?.portTimeZoneOffset && formatOptions?.portTimeZoneAbbr) {
      formattedDate = this.modifiedDateTime(dateTime, formatOptions?.portTimeZoneOffset, formatOptions?.portTimeZoneAbbr);
    } else if (formatOptions?.stringToDate) {
      const stringDate = moment(dateTime, this.momentInputStringFormat).format(AppConfigurationService.settings?.dateFormat);
      formattedDate = moment(stringDate).toDate();
    } else if (formatOptions?.customFormat) {
      formattedDate = moment(dateTime, this.momentInputStringFormat).format(formatOptions?.customFormat);
    } else {
      formattedDate = moment(dateTime, this.momentInputStringFormat).format('DD-MM-YYYY');
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
    const abbreviation = ' ' + abbr + ' ';
    if (offsetValue.charAt(0) === '-') {
      let unsignedOffset = offsetValue.split('-')[1];
      if (unsignedOffset.includes('.')) {
        const splittedOffset = offsetValue.split('.');
        splittedOffset[0] = splittedOffset[0].length !== 2 ? '0' + splittedOffset[0] : splittedOffset[0];
        splittedOffset[1] = splittedOffset[1] === '5' && '30';
        _offsetValue = '-' + splittedOffset[0] + ':' + splittedOffset[1];
      } else {
        unsignedOffset = unsignedOffset.length !== 2 ? '0' + unsignedOffset : unsignedOffset;
        _offsetValue = '-' + unsignedOffset + ':00';
      }
    } else {
      if (offsetValue.includes('.')) {
        const splittedOffset = offsetValue.split('.');
        splittedOffset[0] = splittedOffset[0].length !== 2 ? '0' + splittedOffset[0] : splittedOffset[0];
        splittedOffset[1] = splittedOffset[1] === '5' && '30';
        _offsetValue = '+' + splittedOffset[0] + ':' + splittedOffset[1];
      } else {
        offsetValue = offsetValue.length !== 2 ? '0' + offsetValue : offsetValue;
        _offsetValue = '+' + offsetValue + ':00';
      }
    }

    const addedPortTZ = moment(dateTime, this.momentInputStringFormat).add(_offsetValue, 'hours').format(AppConfigurationService.settings?.dateFormat);
    const tzFormat = AppConfigurationService.settings?.dateFormat + ' (UTC Z)';
    const convertedPortTZ = moment(dateTime, this.momentInputStringFormat).utcOffset(_offsetValue).format(tzFormat);

    const dateTimeSplitIndex = convertedPortTZ.indexOf('(UTC');
    const dateTimeWithAbbr = [addedPortTZ, abbreviation, convertedPortTZ.slice(dateTimeSplitIndex)].join('');
    return dateTimeWithAbbr;
  }

  /**
   * Function to map the configuration dateformat with calender format
   *
   * @param {string} dateTimeFormat
   * @return {*}
   * @memberof TimeZoneTransformationService
   */
  getMappedConfigurationDateFormat(dateTimeFormat: string): string {
    const mappedFormat = [...this.dateTimeFormatArray].find(format => (format.dateFormat === dateTimeFormat));
    return mappedFormat.calenderFormat;
  }

  /**
   * Fetch country list
   *
   * @return {*}  {ICountry[]}
   * @memberof TimeZoneTransformationService
   */
  getCountries(): Observable<ICountry[]> {
    if (this._countries) {
      return of(this._countries);
    } else {
      return this.commonAPiService.get<ICountriesResponse>('countries').pipe(map((response) => {
        this._countries = response?.countrys;
        return this._countries;
      }));
    }
  }

  /**
   * Method to return months.
   *
   * @return {*}  {IMonths[]}
   * @memberof TimeZoneTransformationService
   */
  getMonthList(): IMonth[] {
    return [
      {
        id: 1,
        month: 'JANUARY'
      },
      {
        id: 2,
        month: 'FEBRUARY'
      },
      {
        id: 3,
        month: 'MARCH'
      },
      {
        id: 4,
        month: 'APRIL'
      },
      {
        id: 5,
        month: 'MAY'
      },
      {
        id: 6,
        month: 'JUNE'
      },
      {
        id: 7,
        month: 'JULY'
      },
      {
        id: 8,
        month: 'AUGUST'
      },
      {
        id: 9,
        month: 'SEPTEMBER'
      },
      {
        id: 10,
        month: 'OCTOBER'
      },
      {
        id: 11,
        month: 'NOVEMBER'
      },
      {
        id: 12,
        month: 'DECEMBER'
      }
    ]
  }

  /**
  * Convert date time(dd-mm-yyyy hh:mm) to Date object
  *
  * @memberof TimeZoneTransformationService
  */
  convertToDate(value: string): Date {
    if (value) {
      const arr = value.toString().split(' ')
      const dateArr = arr[0]?.split('-');
      if (arr[1]) {
        const timeArr = arr[1].split(':')
        if (dateArr.length > 2 && timeArr.length > 1) {
          return new Date(Number(dateArr[2]), Number(dateArr[1]) - 1, Number(dateArr[0]), Number(timeArr[0]), Number(timeArr[1]));
        }
      } else {
        return new Date(Number(dateArr[2]), Number(dateArr[1]) - 1, Number(dateArr[0]))
      }
    }
    return null
  }

  /**
   * Function to convert hours into days & hours and vice-versa
   *
   * @param {(number | IDaysHours)} value
   * @return {*}
   * @memberof TimeZoneTransformationService
   */
  convertHoursToDaysHours(value: number | IDaysHours): any {
    let convertedTime: number | IDaysHours;
    if (typeof value === 'number') {
      if (value === 0) {
        convertedTime = { days: 0, hours: 0 };
      } else {
        convertedTime = {
          days: Math.floor(value / 24),
          hours: Math.floor(value % 24)
        };
      }
    } else {
      if (value.days === 0 && value.hours === 0) {
        convertedTime = 0;
      } else {
        convertedTime = (value.days * 24) + value.hours;
      }
    }
    return convertedTime;
  }

}

import { Injectable } from '@angular/core';

/**
 * Transformation Service for Loadaing  details
 *
 * @export
 * @class LoadingDischargingDetailsTransformationService
 */

@Injectable()
export class LoadingDischargingDetailsTransformationService {

  constructor() { }


    /**
   *
   * Method to set validation message for loadig details
   * @return {*} 
   * @memberof LoadingDischargingDetailsTransformationService
   */
     setValidationMessageForLoadingDetails() {
      return {
        timeOfSunrise: {
          'required': 'LOADING_DETAILS_SUNRISE_REQUIRED',
          'failedCompare': 'LOADING_DETAILS_SUNRISE_FAILED_COMPARE'
        },
        timeOfSunset: {
          'required': 'LOADING_DETAILS_SUNSET_REQUIRED',
          'failedCompare': 'LOADING_DETAILS_SUNSET_FAILED_COMPARE'
        },
        startTime: {
          'required': 'LOADING_DETAILS_START_TIME_REQUIRED'
        },
        initialTrim: {
          'required': 'LOADING_DETAILS_INITIAL_TRIM_REQUIRED'
        },
        maximumTrim: {
          'required': 'LOADING_DETAILS_MAX_TRIM_REQUIRED'
        },
        finalTrim: {
          'required': 'LOADING_DETAILS_FINAL_TRIM_REQUIRED'
        }
      }
    }
  
}

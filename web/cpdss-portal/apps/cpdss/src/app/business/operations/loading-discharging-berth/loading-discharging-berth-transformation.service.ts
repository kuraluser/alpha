import { Injectable } from '@angular/core';

/**
 * Transformation Service for loading berth module
 *
 * @export
 * @class LoadingDischargingBerthTransformationService
 */
@Injectable()
export class LoadingDischargingBerthTransformationService {

  constructor() { }

  /**
    *  Set validation error message
    */
   setValidationErrorMessage() {
    return {
      berth: {
        'duplicateBerth': 'LOADING_INFORMATION_BERTH_DUPLICATION',
      },
    }
  }
}

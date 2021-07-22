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
      hoseConnections: {
        'maxlength': 'LOADING_DISCHARGING_BERTH_HOSECONNECTION_CHARACTER_LIMIT'
      },
      regulationAndRestriction: {
        'maxlength': 'LOADING_DISCHARGING_BERTH_REGULATION_RESTRICTION_CHARACTER_LIMIT'
      },
      itemsToBeAgreedWith: {
        'maxlength': 'LOADING_DISCHARGING_BERTH_ITEMS_TO_BE_AGREED_WITH_CHARACTER_LIMIT'
      },
      lineDisplacement: {
        'min': 'LOADING_DISCHARGING_BERTH_LINE_DIPLACEMENT_MIN',
        'max': 'LOADING_DISCHARGING_BERTH_LINE_DIPLACEMENT_MAX'
      }
    }
  }
}

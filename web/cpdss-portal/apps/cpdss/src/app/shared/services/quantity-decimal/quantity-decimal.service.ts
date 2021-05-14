import { Injectable } from '@angular/core';

import { QUANTITY_UNIT } from '../../models/common.model';
import { AppConfigurationService } from '../../services/app-configuration/app-configuration.service';

/**
 * Service for quantity unit decimal correction
 *
 * @export
 * @class QuantityDecimalService
 * @implements {QuantityDecimalService}
 */

@Injectable({
  providedIn: 'root'
})
export class QuantityDecimalService {

  constructor() { }
  /**
   * Method for transforming quantity decimal 
   * @param {string} unit
   * @memberof QuantityDecimalService
  */
  quantityDecimal(unit?:string): number{
    const qunatityUnit = unit ? unit : <QUANTITY_UNIT>localStorage.getItem('unit');
    let decimal;
    switch (qunatityUnit) {
      case QUANTITY_UNIT.KL:
        decimal = AppConfigurationService.settings.quantityNumberFormatKL.slice(-1);
        break;

      case QUANTITY_UNIT.BBLS:
        decimal = AppConfigurationService.settings.quantityNumberFormatBBLS.slice(-1);
        break;

      case QUANTITY_UNIT.OBSBBLS:
        decimal = AppConfigurationService.settings.quantityNumberFormatOBSBBLS.slice(-1);
        break;

      case QUANTITY_UNIT.MT:
        decimal = AppConfigurationService.settings.quantityNumberFormatMT.slice(-1);
        break;

      case QUANTITY_UNIT.LT:
        decimal = AppConfigurationService.settings.quantityNumberFormatLT.slice(-1);
        break;

      default:
        decimal = AppConfigurationService.settings.quantityNumberFormatMT.slice(-1);
        break;
    }
    return Number(decimal)
  }
}

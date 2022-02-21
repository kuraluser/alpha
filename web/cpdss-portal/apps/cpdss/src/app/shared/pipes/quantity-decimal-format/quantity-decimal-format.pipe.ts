import { Pipe, PipeTransform } from '@angular/core';
import { DecimalPipe } from'@angular/common';

import { QUANTITY_UNIT } from '../../models/common.model';
import { AppConfigurationService } from '../../services/app-configuration/app-configuration.service';

/**
 * Pipe for quantity unit decimal correction
 *
 * @export
 * @class QuantityDecimalFormatPipe
 * @implements {PipeTransform}
 */

@Pipe({
  name: 'quantityDecimalFormat'
})
export class QuantityDecimalFormatPipe implements PipeTransform {
  constructor(private decimalPipe: DecimalPipe){}
  /**
   * Method for transforming quantity decimal
   *
   * @param {string} value
   * @param {string} unit
   * @memberof QuantityDecimalFormatPipe
   */
  transform(value: string | number, unit?: string): string {
    let decimal;
    const data: any = value;
    const qunatityUnit = unit ? unit : <QUANTITY_UNIT>localStorage.getItem('unit');
    switch (qunatityUnit) {
      case QUANTITY_UNIT.KL:
        decimal = AppConfigurationService.settings.quantityNumberFormatKL;
        break;

      case QUANTITY_UNIT.OBSKL:
        decimal = AppConfigurationService.settings.quantityNumberFormatKL;
        break;

      case QUANTITY_UNIT.BBLS:
        decimal = AppConfigurationService.settings.quantityNumberFormatBBLS;
        break;

      case QUANTITY_UNIT.OBSBBLS:
        decimal = AppConfigurationService.settings.quantityNumberFormatOBSBBLS;
        break;

      case QUANTITY_UNIT.MT:
        decimal = AppConfigurationService.settings.quantityNumberFormatMT;
        break;

      case QUANTITY_UNIT.LT:
        decimal = AppConfigurationService.settings.quantityNumberFormatLT;
        break;

      default:
        decimal = AppConfigurationService.settings.quantityNumberFormatMT;
        break;
    }
    let convertedValue;
    convertedValue = isNaN(data) ?  value : this.decimalPipe.transform(value, decimal);
    return convertedValue;
  }

}

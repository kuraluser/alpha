import { Pipe, PipeTransform } from '@angular/core';
import { QUANTITY_UNIT } from '../../models/common.model';

/**
 * Pipe for quantity unit conversion
 *
 * @export
 * @class QuantityPipe
 * @implements {PipeTransform}
 */
@Pipe({
  name: 'quantity'
})
export class QuantityPipe implements PipeTransform {

  /**
   * Method for transforming quantity from one unit to other
   *
   * @param {string} value
   * @param {string} unitFrom
   * @param {string} unitTo
   * @param {string} api
   * @param {string} [temperature]
   * @returns {number}
   * @memberof QuantityPipe
   */
  transform(value: string, unitFrom: QUANTITY_UNIT, unitTo: QUANTITY_UNIT, api: string, temperature?: string): number {
    if (!value || value === '' || !api || api === '' || !unitFrom || !unitTo) {
      return null;
    }
    const _value = Number(value);
    const _api = Number(api);
    const _temperature = Number(temperature);
    const quantityInBBLS = this.convertToBBLS(_value, unitFrom, _api, _temperature);
    const convertedQuantity = this.convertFromBBLS(quantityInBBLS, unitTo, _api, _temperature);

    return convertedQuantity;
  }

  /**
   * Method to convert from other units to standard unit BBLS
   *
   * @param {number} value
   * @param {QUANTITY_UNIT} unitFrom
   * @param {number} api
   * @param {number} temperature
   * @returns {number}
   * @memberof QuantityPipe
   */
  convertToBBLS(value: number, unitFrom: QUANTITY_UNIT, api: number, temperature: number): number {
    const conversionConstant: number = this.getConversionConstant(unitFrom, api, temperature);
    const _value: number = unitFrom === QUANTITY_UNIT.OBSBBLS ? value * conversionConstant : value / conversionConstant;

    return _value;
  }

  /**
   * Method to convert from standard unit BBLS to other units
   *
   * @param {number} value
   * @param {QUANTITY_UNIT} unitTo
   * @param {number} api
   * @param {number} temperature
   * @returns {number}
   * @memberof QuantityPipe
   */
  convertFromBBLS(value: number, unitTo: QUANTITY_UNIT, api: number, temperature: number): number {
    const conversionConstant: number = this.getConversionConstant(unitTo, api, temperature);
    const _value: number = unitTo === QUANTITY_UNIT.OBSBBLS ? value / conversionConstant : value * conversionConstant;

    return _value;
  }

  /**
   * Method for getting conversion constant
   *
   * @param {QUANTITY_UNIT} unit
   * @param {number} api
   * @param {number} temperature
   * @returns {number}
   * @memberof QuantityPipe
   */
  getConversionConstant(unit: QUANTITY_UNIT, api: number, temperature: number): number {
    let conversionConstant: number;
    switch (unit) {
      case QUANTITY_UNIT.MT:
        conversionConstant = (((535.1911 / (api + 131.5)) - 0.0046189) * 0.42) / 10;
        break;

      case QUANTITY_UNIT.KL:
        conversionConstant = 0.15899;
        break;

      case QUANTITY_UNIT.OBSBBLS:
        conversionConstant = Math.exp(-(341.0957 / Math.pow((141360.198 / (api + 131.5)), 2)) * (temperature - 60) * (1 + (0.8 * (341.0957 / Math.pow((141360.198 / (api + 131.5)), 2)) * (temperature - 60))));
        break;

      case QUANTITY_UNIT.LT:
        conversionConstant = ((589.943 / (api + 131.5)) - 0.0050789) * 0.0375;
        break;

      default:
        conversionConstant = 1;
        break;
    }

    return conversionConstant;
  }
}

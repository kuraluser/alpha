import { Pipe, PipeTransform } from '@angular/core';

import { LENGTH_UNIT } from './../../models/common.model';

/**
 * pipe for lenght unit conversion
 */
@Pipe({
  name: 'length'
})
export class LengthConverterPipe implements PipeTransform {

  /**
   * Function to convert Meter to Feet and vice-versa,
   * Kilometer to Nauticalmile and vice-versa
   * @param {(string|number)} value
   * @param {LENGTH_UNIT} unitFrom
   * @param {LENGTH_UNIT} unitTo
   * @param {number} [decimalPoint]
   * @return {*}  {number}
   * @memberof LengthConverterPipe
   */
  transform(value: string|number, unitFrom: LENGTH_UNIT, unitTo: LENGTH_UNIT, decimalPoint?: number): number {
    if (!value || value === '' || !unitFrom || !unitTo) {
      return null;
    }

    let convertedLength: number;
    if (unitFrom === LENGTH_UNIT.M && unitTo === LENGTH_UNIT.FT) {
      convertedLength = (decimalPoint && decimalPoint > -1) ? Number((Number(value) * 3.2808).toFixed(decimalPoint)): Number(value) * 3.2808;
    } else if (unitFrom === LENGTH_UNIT.FT && unitTo === LENGTH_UNIT.M){
      convertedLength = (decimalPoint && decimalPoint > -1) ? Number((Number(value) / 3.2808).toFixed(decimalPoint)): Number(value) / 3.2808;
    } else if (unitFrom === LENGTH_UNIT.KM && unitTo === LENGTH_UNIT.NM){
      convertedLength = (decimalPoint && decimalPoint > -1) ? Number((Number(value) * 0.539957).toFixed(decimalPoint)): Number(value) * 0.539957;
    } else if (unitFrom === LENGTH_UNIT.NM && unitTo === LENGTH_UNIT.KM){
      convertedLength = (decimalPoint && decimalPoint > -1) ? Number((Number(value) / 0.539957).toFixed(decimalPoint)): Number(value) / 0.539957;
    }
    return convertedLength;
  }

}

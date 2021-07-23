import { Injectable } from '@angular/core';
import { DATATABLE_FIELD_TYPE, IDataTableColumn } from '../../../shared/components/datatable/datatable.model';
import { QUANTITY_UNIT } from '../../../shared/models/common.model';
import { QuantityPipe } from '../../../shared/pipes/quantity/quantity.pipe';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';
import { ICargoQuantities, IShipCargoTank, ITank } from '../../core/models/common.model';

/**
 * Transformation Service for cargo details
 *
 * @export
 * @class LoadingDischargingCargoDetailsTransformationService
 */
@Injectable()
export class LoadingDischargingCargoDetailsTransformationService {

  constructor(private quantityPipe: QuantityPipe) { }

  /**
* Method for setting cargo to be loaded grid columns
*
* @returns {IDataTableColumn[]}
* @memberof LoadingDischargingCargoDetailsTransformationService
*/
  getCargotobeLoadedDatatableColumns(quantityUnit: QUANTITY_UNIT): IDataTableColumn[] {
    const quantityNumberFormat = AppConfigurationService.settings['quantityNumberFormat' + quantityUnit];

    return [
      {
        field: 'grade',
        header: 'LOADING_CARGO_TO_BE_LOADED_KIND_OF_CARGO',
        fieldType: DATATABLE_FIELD_TYPE.BADGE,
        badgeColorField: 'colorCode'
      },
      {
        field: 'estimatedAPI',
        header: 'LOADING_CARGO_TO_BE_LOADED_API',
        numberFormat: '1.2-2'
      },
      {
        field: 'estimatedTemp',
        header: 'LOADING_CARGO_TO_BE_LOADED_TEMP',
        numberFormat: '1.2-2'
      },
      {
        field: 'maxLoadingRate',
        header: 'LOADING_CARGO_TO_BE_LOADED_MAX_LOADING_RATE'
      },
      {
        field: 'orderedQuantity',
        header: 'LOADING_CARGO_TO_BE_LOADED_NOMINATION',
        numberFormat: quantityNumberFormat
      },
      {
        field: 'minMaxTolerance',
        header: 'LOADING_CARGO_TO_BE_LOADED_MIN_MAX_TOLERANCE'
      },
      {
        field: 'loadableMT',
        header: 'LOADING_CARGO_TO_BE_LOADED_SHIP_LOADABLE',
        numberFormat: quantityNumberFormat
      },
      {
        field: 'differencePercentage',
        header: 'LOADING_CARGO_TO_BE_LOADED_DIFFERENCE'
      },
      {
        field: 'timeRequiredForLoading',
        header: 'LOADING_CARGO_TO_BE_LOADED_TIME_REQUIRED'
      },
      {
        field: 'slopQuantity',
        header: 'LOADING_CARGO_TO_BE_LOADED_SLOP_QTY',
        numberFormat: quantityNumberFormat
      }
    ]
  }


  /**
  * Method for formatting ballast tanks data
  *
  * @param {IShipCargoTank} cargoTank
  * @param {ICargoQuantities[]} cargoTankQuantities
  * @returns {IShipCargoTank}
  * @memberof LoadingInformationTransformationService
  */
  formatCargoTanks(cargoTank: IShipCargoTank[][], cargoTankQuantities: ICargoQuantities[], prevUnit: QUANTITY_UNIT, currUnit: QUANTITY_UNIT): IShipCargoTank[][] {
    for (let groupIndex = 0; groupIndex < cargoTank?.length; groupIndex++) {
      for (let tankIndex = 0; tankIndex < cargoTank[groupIndex].length; tankIndex++) {
        for (let index = 0; index < cargoTankQuantities?.length; index++) {
          if (cargoTankQuantities[index]?.tankId === cargoTank[groupIndex][tankIndex]?.id) {
            cargoTank[groupIndex][tankIndex].commodity = cargoTankQuantities[index];
            const plannedWeight = this.quantityPipe.transform(cargoTank[groupIndex][tankIndex].commodity.plannedWeight, prevUnit, currUnit, cargoTankQuantities[index]?.api);
            cargoTank[groupIndex][tankIndex].commodity.plannedWeight = plannedWeight ? Number(plannedWeight) : 0;
            const actualWeight = this.quantityPipe.transform(cargoTank[groupIndex][tankIndex].commodity.actualWeight, prevUnit, currUnit, cargoTankQuantities[index]?.api);
            cargoTank[groupIndex][tankIndex].commodity.actualWeight = actualWeight ? Number(actualWeight) : 0;
            cargoTank[groupIndex][tankIndex].commodity.volume = this.quantityPipe.transform(cargoTank[groupIndex][tankIndex].commodity.actualWeight, currUnit, QUANTITY_UNIT.OBSKL, cargoTank[groupIndex][tankIndex].commodity?.api, cargoTank[groupIndex][tankIndex].commodity?.temperature);
            if (cargoTank[groupIndex][tankIndex].commodity?.isCommingleCargo) {
              cargoTank[groupIndex][tankIndex].commodity.colorCode = AppConfigurationService.settings.commingleColor;
            }
            break;
          }
        }
      }
    }
    return cargoTank;
  }



  /**
  *
  * Get Formated Loadable Quantity Data
  * @returns {decimal converted value us number}
  */
  decimalConvertion(_decimalPipe: any, value: string | number, decimalType: string) {
    return _decimalPipe.transform(value, decimalType);
  }

  /**
   * parse number from formatted string
   * @returns {number}
  */
  convertToNumber(value: string) {
    value = value?.replace(/,/g, '');
    return Number(value)
  }
}

import { Injectable } from '@angular/core';
import { DATATABLE_FIELD_TYPE, IDataTableColumn } from '../../../shared/components/datatable/datatable.model';
import { ICargoQuantities, IShipCargoTank, ITank, ILoadableQuantityCargo, IBallastQuantities, IShipBallastTank, IShipBunkerTank, IBunkerQuantities } from '../../core/models/common.model';
import { QuantityPipe } from '../../../shared/pipes/quantity/quantity.pipe';
import { QUANTITY_UNIT, ICargoConditions } from '../../../shared/models/common.model';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';
/**
 * Transformation Service for topping off tank table
 *
 * @export
 * @class ToppingOffTankTableTransformationService
 */
@Injectable()
export class ToppingOffTankTableTransformationService {

  constructor(
    private quantityPipe: QuantityPipe
  ) { }

  /**
* Method for setting topping off tank table
*
* @returns {IDataTableColumn[]}
* @memberof ToppingOffTankTableTransformationService
*/
  getDatatableColumns(): IDataTableColumn[] {
    return [
      {
        field: 'displayOrder',
        header: 'LOADABLE_PLAN_TOPPING_OFF_TANK_SEQUENCE',
      },
      {
        field: 'shortName',
        header: 'LOADABLE_PLAN_TOPPING_OFF_TANK',
      },
      {
        field: 'cargoAbbreviation',
        header: 'LOADABLE_PLAN_TOPPING_OFF_TANK_TYPE',
        fieldType: DATATABLE_FIELD_TYPE.BADGE,
        badgeColorField: 'colourCode'
      },
      {
        field: 'ullage',
        header: 'LOADABLE_PLAN_TOPPING_OFF_TANK_ULLAGE'
      },
      {
        field: 'quantity',
        header: 'LOADABLE_PLAN_TOPPING_OFF_TANK_QUANTITY',
        numberType: 'quantity'
      },
      {
        field: 'fillingRatio',
        header: 'LOADABLE_PLAN_TOPPING_OFF_TANK_FILLING',
      }
    ];
  }

  /**
   * Method for formatting ballast tanks data
   *
   * @param {IShipCargoTank} bunkerTank
   * @param {ICargoQuantities[]} bunkerTankQuantities
   * @returns {IShipCargoTank}
   * @memberof ToppingOffTankTableTransformationService
   */
  formatCargoTanks(cargoTank: IShipCargoTank[][], cargoTankQuantities: ICargoQuantities[], prevUnit: QUANTITY_UNIT, currUnit: QUANTITY_UNIT): IShipCargoTank[][] {

    for (let groupIndex = 0; groupIndex < cargoTank?.length; groupIndex++) {
      for (let tankIndex = 0; tankIndex < cargoTank[groupIndex].length; tankIndex++) {
        for (let index = 0; index < cargoTankQuantities?.length; index++) {
          if (cargoTankQuantities[index]?.tankId === cargoTank[groupIndex][tankIndex]?.id) {
            cargoTank[groupIndex][tankIndex].commodity = cargoTankQuantities[index];
            const plannedWeight = this.quantityPipe.transform(cargoTank[groupIndex][tankIndex].commodity['quantityMT'], prevUnit, currUnit, cargoTankQuantities[index]?.api);
            cargoTank[groupIndex][tankIndex].commodity.plannedWeight = plannedWeight ? Number(plannedWeight.toFixed(2)) : 0;
            const actualWeight = this.quantityPipe.transform(cargoTank[groupIndex][tankIndex].commodity.actualWeight, prevUnit, currUnit, cargoTankQuantities[index]?.api);
            cargoTank[groupIndex][tankIndex].commodity.actualWeight = actualWeight ? Number(actualWeight.toFixed(2)) : 0;
            cargoTank[groupIndex][tankIndex].commodity.volume = this.quantityPipe.transform(cargoTank[groupIndex][tankIndex].commodity.plannedWeight, currUnit, AppConfigurationService.settings.volumeBaseUnit, cargoTank[groupIndex][tankIndex].commodity?.api);
            cargoTank[groupIndex][tankIndex].commodity.percentageFilled = this.getFillingPercentage(cargoTank[groupIndex][tankIndex])
            break;
          }
        }
      }
    }
    return cargoTank;
  }

  /**
* Method to get percentage filled in tank
*
* @param {ITank} tank
* @returns
* @memberof ToppingOffTankTableTransformationService
*/
  getFillingPercentage(tank: ITank) {
    let fillingratio: any = ((tank?.commodity?.volume / Number(tank?.fullCapacityCubm)) * 100).toFixed(2);
    if (Number(fillingratio) >= 100) {
      fillingratio = 100;
    }
    if (isNaN(fillingratio)) {
      fillingratio = 0;
    }
    return fillingratio;
  }

}



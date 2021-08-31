import { Injectable } from '@angular/core';
import { QUANTITY_UNIT, ICargoConditions } from '../../../shared/models/common.model';
import { OHQ_MODE } from '../../cargo-planning/models/cargo-planning.model';
import { QuantityPipe } from '../../../shared/pipes/quantity/quantity.pipe';
import { ICargoQuantities, IShipCargoTank, ITank, ILoadableQuantityCargo, IBallastQuantities, IShipBallastTank } from '../../core/models/common.model';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';
/**
 * Transformation Service for arrival condition
 *
 * @export
 * @class ArrivalConditionTransformationService
 */
@Injectable()
export class ArrivalConditionTransformationService {

  constructor(
    private quantityPipe: QuantityPipe
  ) { }

  /**
   * Method for formatting ballast tanks data
   *
   * @param {IShipBallastTank} ballastTank
   * @param {IBallastQuantities[]} ballastTankQuantities
   * @returns {IShipBallastTank}
   * @memberof VoyageStatusTransformationService
   */
  formatBallastTanks(ballastTank: IShipBallastTank[][], ballastTankQuantities: IBallastQuantities[], prevUnit: QUANTITY_UNIT, currUnit: QUANTITY_UNIT): IShipBallastTank[][] {
    for (let groupIndex = 0; groupIndex < ballastTank?.length; groupIndex++) {
      for (let tankIndex = 0; tankIndex < ballastTank[groupIndex].length; tankIndex++) {
        for (let index = 0; index < ballastTankQuantities?.length; index++) {
          if (ballastTankQuantities[index]?.tankId === ballastTank[groupIndex][tankIndex]?.id) {
            ballastTank[groupIndex][tankIndex].commodity = ballastTankQuantities[index];
            const plannedWeight = ballastTank[groupIndex][tankIndex].commodity.plannedWeight;
            ballastTank[groupIndex][tankIndex].commodity.plannedWeight = plannedWeight ? Number(plannedWeight.toFixed(2)) : 0;
            const actualWeight = ballastTank[groupIndex][tankIndex].commodity.actualWeight;
            ballastTank[groupIndex][tankIndex].commodity.actualWeight = actualWeight ? Number(actualWeight.toFixed(2)) : 0;
            ballastTank[groupIndex][tankIndex].commodity.volume = ballastTank[groupIndex][tankIndex].density ? Number((ballastTank[groupIndex][tankIndex].commodity.actualWeight / ballastTank[groupIndex][tankIndex].density).toFixed(2)) : 0;
            ballastTank[groupIndex][tankIndex].commodity.percentageFilled = this.getFillingPercentage(ballastTank[groupIndex][tankIndex])
            break;
          }
        }
      }
    }
    return ballastTank;
  }

  /**
   * Method for formatting ballast tanks data
   *
   * @param {IShipCargoTank} bunkerTank
   * @param {ICargoQuantities[]} bunkerTankQuantities
   * @returns {IShipCargoTank}
   * @memberof VoyageStatusTransformationService
   */
  formatCargoTanks(cargoTank: IShipCargoTank[][], cargoTankQuantities: ICargoQuantities[], prevUnit: QUANTITY_UNIT, currUnit: QUANTITY_UNIT): IShipCargoTank[][] {

    for (let groupIndex = 0; groupIndex < cargoTank?.length; groupIndex++) {
      for (let tankIndex = 0; tankIndex < cargoTank[groupIndex].length; tankIndex++) {
        for (let index = 0; index < cargoTankQuantities?.length; index++) {
          if (cargoTankQuantities[index]?.tankId === cargoTank[groupIndex][tankIndex]?.id) {
            cargoTank[groupIndex][tankIndex].commodity = cargoTankQuantities[index];
            const plannedWeight = this.quantityPipe.transform(cargoTank[groupIndex][tankIndex].commodity.plannedWeight, prevUnit, currUnit, cargoTankQuantities[index]?.api);
            cargoTank[groupIndex][tankIndex].commodity.plannedWeight = plannedWeight ? Number(plannedWeight.toFixed(2)) : 0;
            const actualWeight = this.quantityPipe.transform(cargoTank[groupIndex][tankIndex].commodity.actualWeight, prevUnit, currUnit, cargoTankQuantities[index]?.api);
            cargoTank[groupIndex][tankIndex].commodity.actualWeight = actualWeight ? Number(actualWeight.toFixed(2)) : 0;
            cargoTank[groupIndex][tankIndex].commodity.volume = this.quantityPipe.transform(cargoTank[groupIndex][tankIndex].commodity.actualWeight, currUnit, AppConfigurationService.settings.volumeBaseUnit, cargoTank[groupIndex][tankIndex].commodity?.api);
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
* @memberof VoyageStatusTransformationService
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

  /**
   * Format congo condition data
   * @returns {object}
  */
  formatCargoCondition(loadableQuantityCargoDetails: ILoadableQuantityCargo) {
    const data = <ICargoConditions>{};
    data.abbreviation = loadableQuantityCargoDetails.cargoAbbreviation;
    data.actualWeight =  0;
    data.plannedWeight = 0;
    data.colorCode = loadableQuantityCargoDetails.colorCode;
    return data;
  }

  /**
   * Format congo quantity data
   * @returns {object}
  */
  formatCargoQuantities(value: any, loadableQuantityCargoDetails: ILoadableQuantityCargo) {
    const data = <ICargoQuantities> {};
    data.abbreviation = loadableQuantityCargoDetails.cargoAbbreviation;
    data.actualWeight = 0;
    data.api = value.api;
    data.cargoId = loadableQuantityCargoDetails.cargoId;
    data.colorCode = loadableQuantityCargoDetails.colorCode;
    return data;
  }
}

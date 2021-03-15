import { Injectable } from '@angular/core';
import { Subject } from "rxjs";
import { IBallastQuantities, IShipBallastTank, IShipBunkerTank, IBunkerQuantities, IShipCargoTank, ICargoQuantities } from '../models/voyage-status.model';
import { IDataTableColumn } from '../../../shared/components/datatable/datatable.model';
import { OHQ_MODE } from '../../cargo-planning/models/cargo-planning.model';
import { QuantityPipe } from '../../../shared/pipes/quantity/quantity.pipe';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';
import { ITank } from '../../core/models/common.model';
import { QUANTITY_UNIT } from '../../../shared/models/common.model';

/**
 * Transformation Service for Voyage status module
 *
 * @export
 * @class VoyageStatusTransformationService
 */
@Injectable()
export class VoyageStatusTransformationService {
  columns: IDataTableColumn[];

  public portOrderChange = new Subject();
  voyageDistance: number;

  constructor(private quantityPipe: QuantityPipe) { }

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
            const plannedWeight = this.quantityPipe.transform(ballastTank[groupIndex][tankIndex].commodity.plannedWeight, prevUnit, currUnit, ballastTankQuantities[index]?.sg);
            ballastTank[groupIndex][tankIndex].commodity.plannedWeight = plannedWeight ? Number(plannedWeight.toFixed(2)) : 0;
            const actualWeight = this.quantityPipe.transform(ballastTank[groupIndex][tankIndex].commodity.actualWeight, prevUnit, currUnit, ballastTankQuantities[index]?.sg);
            ballastTank[groupIndex][tankIndex].commodity.actualWeight = actualWeight ? Number(actualWeight.toFixed(2)) : 0;
            ballastTank[groupIndex][tankIndex].commodity.volume = this.quantityPipe.transform(ballastTank[groupIndex][tankIndex].commodity.actualWeight, currUnit, AppConfigurationService.settings.volumeBaseUnit, ballastTank[groupIndex][tankIndex].commodity?.sg);
            ballastTank[groupIndex][tankIndex].commodity.percentageFilled = this.getFillingPercentage(ballastTank[groupIndex][tankIndex])
            break;
          }
        }
      }
    }
    return ballastTank;
  }


  /**
   * Set validation Error to form control
   */
  setValidationErrorMessageForPortRotationRibbon() {
    return {
      eta: {
        'required': 'PORT_ROTATION_RIBBON_ETA_REQUIRED',
        'maxError': 'PORT_ROTATION_RIBBON_ETA_FAILED_COMPARE_MAX',
        'minError': 'PORT_ROTATION_RIBBON_ETA_FAILED_COMPARE_MIN',
        'compareDateWithPrevious':  'PORT_ROTATION_RIBBON_ETA_FAILED_COMPARE_ETD_DATE'
      },
      etd: {
        'required': 'PORT_ROTATION_RIBBON_ETD_REQUIRED',
        'maxError': 'PORT_ROTATION_RIBBON_ETD_FAILED_COMPARE_MAX',
        'minError': 'PORT_ROTATION_RIBBON_ETD_FAILED_COMPARE_MIN',
        'compareDateWithPrevious':  'PORT_ROTATION_RIBBON_ETD_FAILED_COMPARE_ETA_DATE'
      },
      etaTime: {
        'required': 'PORT_ROTATION_RIBBON_TIME_REQUIRED',
        'maxError': 'PORT_ROTATION_RIBBON_ETA_FAILED_COMPARE_MAX',
        'minError': 'PORT_ROTATION_RIBBON_ETA_FAILED_COMPARE_MIN'
      },
      etdTime: {
        'required': 'PORT_ROTATION_RIBBON_TIME_REQUIRED',
        'maxError': 'PORT_ROTATION_RIBBON_ETD_FAILED_COMPARE_MAX',
        'minError': 'PORT_ROTATION_RIBBON_ETD_FAILED_COMPARE_MIN'
      },
      distance: {
        'required': 'PORT_ROTATION_RIBBON_DISTANCE_REQUIRED',
      }
    }
  }

  /**
* Method for formatting ballast tanks data
*
* @param {IShipBunkerTank} bunkerTank
* @param {IBunkerQuantities[]} bunkerTankQuantities
* @returns {IShipBunkerTank}
* @memberof VoyageStatusTransformationService
*/
  formatBunkerTanks(bunkerTank: IShipBunkerTank[][], bunkerTankQuantities: IBunkerQuantities[], mode: OHQ_MODE, prevUnit: QUANTITY_UNIT, currUnit: QUANTITY_UNIT): IShipBunkerTank[][] {

    for (let groupIndex = 0; groupIndex < bunkerTank?.length; groupIndex++) {
      for (let tankIndex = 0; tankIndex < bunkerTank[groupIndex].length; tankIndex++) {
        for (let index = 0; index < bunkerTankQuantities.length; index++) {
          if (bunkerTankQuantities[index]?.tankId === bunkerTank[groupIndex][tankIndex]?.id) {
            bunkerTank[groupIndex][tankIndex].commodity = bunkerTankQuantities[index];
            let quantity = mode === OHQ_MODE.ARRIVAL ? bunkerTank[groupIndex][tankIndex]?.commodity?.actualArrivalQuantity : bunkerTank[groupIndex][tankIndex]?.commodity?.actualDepartureQuantity;
            quantity = this.quantityPipe.transform(quantity, prevUnit, currUnit, bunkerTank[groupIndex][tankIndex].commodity?.density);
            bunkerTank[groupIndex][tankIndex].commodity.quantity = quantity ? Number(quantity.toFixed(2)) : 0;
            bunkerTank[groupIndex][tankIndex].commodity.volume = this.quantityPipe.transform(bunkerTank[groupIndex][tankIndex].commodity.quantity, currUnit, AppConfigurationService.settings.volumeBaseUnit, bunkerTank[groupIndex][tankIndex].commodity?.density);
            break;
          }
        }
      }
    }
    return bunkerTank;
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
  * Method for setting cargo details grid
  *
  * @memberof LoadableStudyPatternTransformationService
  */
  getColumnFields() {
    this.columns = [
      { field: 'abbreviation', header: 'VOYAGE_STATUS_CARGO_CONDITION_GRADES' },
      { field: 'plannedWeight', header: 'VOYAGE_STATUS_CARGO_CONDITION_PLANNED' },
      { field: 'actualWeight', header: 'VOYAGE_STATUS_CARGO_CONDITION_Actual' },
      { field: 'difference', header: 'VOYAGE_STATUS_CARGO_CONDITION_DIFFERENCE' }
    ];

    return this.columns
  }

  /**
* Method for setting cargo tank grid
*
* @returns {IDataTableColumn[]}
* @memberof LoadableStudyPatternTransformationService
*/
  getCargoTankDatatableColumns(): IDataTableColumn[] {
    return [
      {
        field: 'tankName',
        header: 'TANK NAME'
      },
      {
        field: 'abbreviation',
        header: 'CARGO NAME'
      },
      {
        field: 'correctedUllage',
        header: 'ULLAGE'
      },
      {
        field: 'plannedWeight',
        header: 'QUANTITY BEFORE LOADING'
      },
      {
        field: 'actualWeight',
        header: 'QUANTITY AFTER LOADING'
      },
      {
        field: 'api',
        header: 'API'
      },
      {
        field: 'percentageFilled',
        header: 'Filling Percentage'
      }
    ]
  }

  /**
* Method for setting ballast tank grid
*
* @returns {IDataTableColumn[]}
* @memberof LoadableStudyPatternTransformationService
*/
  getBallastTankDatatableColumns(): IDataTableColumn[] {
    return [
      {
        field: 'tankName',
        header: 'TANK NAME'
      },
      {
        field: 'sg',
        header: 'SG'
      },
      {
        field: 'correctedUllage',
        header: 'ULLAGE'
      },
      {
        field: 'plannedWeight',
        header: 'QUANTITY BEFORE LOADING'
      },
      {
        field: 'actualWeight',
        header: 'QUANTITY AFTER LOADING'
      },
      {
        field: 'percentageFilled',
        header: 'Filling Percentage'
      }
    ]
  }



  /**
   * Get the draft condition table columns
   *
   * @returns
   * @memberof VoyageStatusTransformationService
   */
  getDraftConditionColumnFields() {
    return [
      { field: 'header', header: '' },
      { field: 'aft', header: 'VOYAGE_STATUS_DRAFT_CONDITION_AFT' },
      { field: 'mid', header: 'VOYAGE_STATUS_DRAFT_CONDITION_MID' },
      { field: 'fore', header: 'VOYAGE_STATUS_DRAFT_CONDITION_FORE' }
    ];
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



}

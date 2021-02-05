import { Injectable } from '@angular/core';
import { Subject } from "rxjs";
import { IBallastQuantities, IShipBallastTank, IShipBunkerTank, IBunkerQuantities, IShipCargoTank, ICargoQuantities } from '../models/voyage-status.model';
import { IDataTableColumn } from '../../../shared/components/datatable/datatable.model';
import { OHQ_MODE } from '../../cargo-planning/models/cargo-planning.model';
import { QuantityPipe } from '../../../shared/pipes/quantity/quantity.pipe';
import { ValueObject } from '../../../shared/models/common.model';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';

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
  formatBallastTanks(ballastTank: IShipBallastTank[][], ballastTankQuantities: IBallastQuantities[]): IShipBallastTank[][] {
    for (let groupIndex = 0; groupIndex < ballastTank?.length; groupIndex++) {
      for (let tankIndex = 0; tankIndex < ballastTank[groupIndex].length; tankIndex++) {
        for (let index = 0; index < ballastTankQuantities?.length; index++) {
          if (ballastTankQuantities[index]?.tankId === ballastTank[groupIndex][tankIndex]?.id) {
            ballastTank[groupIndex][tankIndex].commodity = ballastTankQuantities[index];
            ballastTank[groupIndex][tankIndex].commodity.volume = this.quantityPipe.transform(ballastTank[groupIndex][tankIndex].commodity.actualWeight, AppConfigurationService.settings.baseUnit, AppConfigurationService.settings.volumeBaseUnit, ballastTank[groupIndex][tankIndex].commodity?.sg);
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
      },
      etd: {
        'required': 'PORT_ROTATION_RIBBON_ETD_REQUIRED',
      },
      etaTime: {
        'required': 'PORT_ROTATION_RIBBON_TIME_REQUIRED',
      },
      etdTime: {
        'required': 'PORT_ROTATION_RIBBON_TIME_REQUIRED',
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
  formatBunkerTanks(bunkerTank: IShipBunkerTank[][], bunkerTankQuantities: IBunkerQuantities[], mode: OHQ_MODE): IShipBunkerTank[][] {

    for (let groupIndex = 0; groupIndex < bunkerTank?.length; groupIndex++) {
      for (let tankIndex = 0; tankIndex < bunkerTank[groupIndex].length; tankIndex++) {
        for (let index = 0; index < bunkerTankQuantities.length; index++) {
          if (bunkerTankQuantities[index]?.tankId === bunkerTank[groupIndex][tankIndex]?.id) {
            bunkerTank[groupIndex][tankIndex].commodity = bunkerTankQuantities[index];
            bunkerTank[groupIndex][tankIndex].commodity.quantity = mode === OHQ_MODE.ARRIVAL ? bunkerTank[groupIndex][tankIndex]?.commodity?.arrivalQuantity : bunkerTank[groupIndex][tankIndex]?.commodity?.departureQuantity;
            bunkerTank[groupIndex][tankIndex].commodity.volume = this.quantityPipe.transform(bunkerTank[groupIndex][tankIndex].commodity.quantity, AppConfigurationService.settings.baseUnit, AppConfigurationService.settings.volumeBaseUnit, bunkerTank[groupIndex][tankIndex].commodity?.density);
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
  formatCargoTanks(cargoTank: IShipCargoTank[][], cargoTankQuantities: ICargoQuantities[]): IShipCargoTank[][] {

    for (let groupIndex = 0; groupIndex < cargoTank?.length; groupIndex++) {
      for (let tankIndex = 0; tankIndex < cargoTank[groupIndex].length; tankIndex++) {
        for (let index = 0; index < cargoTankQuantities?.length; index++) {
          if (cargoTankQuantities[index]?.tankId === cargoTank[groupIndex][tankIndex]?.id) {
            cargoTank[groupIndex][tankIndex].commodity = cargoTankQuantities[index];
            cargoTank[groupIndex][tankIndex].commodity.volume = this.quantityPipe.transform(cargoTank[groupIndex][tankIndex].commodity.actualWeight, AppConfigurationService.settings.baseUnit, AppConfigurationService.settings.volumeBaseUnit, cargoTank[groupIndex][tankIndex].commodity?.api);
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


}

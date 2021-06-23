import { Injectable } from '@angular/core';
import { QuantityPipe } from '../../../shared/pipes/quantity/quantity.pipe';
import { IBallastQuantities, IShipBallastTank, IShipBunkerTank, IBunkerQuantities } from '../../voyage-status/models/voyage-status.model';
import { ICargoQuantities, IShipCargoTank, ITank } from '../../core/models/common.model';
import { QUANTITY_UNIT } from '../../../shared/models/common.model';
import { OHQ_MODE } from '../../cargo-planning/models/cargo-planning.model';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';
import { DATATABLE_EDITMODE, DATATABLE_FIELD_TYPE, IDataTableColumn, DATATABLE_FILTER_TYPE, DATATABLE_FILTER_MATCHMODE } from '../../../shared/components/datatable/datatable.model';
import { ICargoDetail , ICargoDetailValueObject } from '../models/ullage-update-popup.model';
import { ValueObject } from '../../../shared/models/common.model';

/**
 * Transformation Service for ullage update popup
 *
 * @export
 * @class UllageUpdatePopupTransformationService
 */
@Injectable()
export class UllageUpdatePopupTransformationService {

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
            const quantity = mode === OHQ_MODE.ARRIVAL ? bunkerTank[groupIndex][tankIndex]?.commodity?.actualArrivalQuantity : bunkerTank[groupIndex][tankIndex]?.commodity?.actualDepartureQuantity;
            bunkerTank[groupIndex][tankIndex].commodity.quantity = quantity ? Number(quantity.toFixed(2)) : 0;
            bunkerTank[groupIndex][tankIndex].commodity.volume = bunkerTank[groupIndex][tankIndex].commodity?.density ? bunkerTank[groupIndex][tankIndex].commodity.quantity / bunkerTank[groupIndex][tankIndex].commodity?.density : 0;
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
   * Method for setting cargo table column
   * @returns {IDataTableColumn}
   * @memberof UllageUpdatePopupTransformationService
   */
  getCargoTableColumn(){

    const columns: IDataTableColumn[] = [
      {
        field: 'tankName',
        header: 'LOADABLE_PLAN_ULLAGE_UPDATE_TANK_NAME',        
      },
      {
        field: 'correctedUllage',
        header: 'LOADABLE_PLAN_ULLAGE_UPDATE_RDG_ULLAGE',
        filter: false,
        filterType: DATATABLE_FILTER_TYPE.NUMBER
      },
      {
        field: 'temp',
        header: 'LOADABLE_PLAN_ULLAGE_UPDATE_TEMP',
        filter: false,
        filterType: DATATABLE_FILTER_TYPE.NUMBER,
        editable: true
      },
      {
        field: 'volume',
        header: 'LOADABLE_PLAN_ULLAGE_UPDATE_QTY',
        filter: false,
        filterType: DATATABLE_FILTER_TYPE.TEXT,
      }

    ];
    return columns;
  }

  /**
   * Method for setting ballast tank table column
   * @returns {IDataTableColumn}
   * @memberof UllageUpdatePopupTransformationService
   */
  getBallastTankColumns(){
    const columns: IDataTableColumn[] = [
      {
        field: 'tankName',
        header: 'LOADABLE_PLAN_BALLAST_TANK_NAME',
        filter: false,
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'tankName',
        filterByServer: true,
        sortable: false,
        sortField: 'tankName'
      },
      {
        field: 'correctedUllage',
        header: 'LOADABLE_PLAN_BALLAST_ULLAGE',
        filter: false,
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'correctedUllage',
        filterByServer: true,
        sortable: false,
        sortField: 'correctedUllage'
      },

      {
        field: 'volume',
        header: 'LOADABLE_PLAN_BALLAST_QTY',
        filter: false,
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'volume',
        filterByServer: true,
        sortable: false,
        sortField: 'volume'
      }

    ];
    return columns;
  }

  /**
   * Method for setting bunker tank table column
   * @returns {IDataTableColumn}
   * @memberof UllageUpdatePopupTransformationService
   */
  getBunkerTankColumns(){
    const columns: IDataTableColumn[] = [
      {
        field: 'shortName',
        header: 'LOADABLE_PLAN_BUNKER_TANK_NAME',
        filter: false,
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'vesselName',
        filterByServer: true,
        sortable: false,
        sortField: 'vesselName'
      },
      {
        field: 'correctedUllage',
        header: 'LOADABLE_PLAN_BUNKER_ULLAGE',
        filter: false,
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'vesselName',
        filterByServer: true,
        sortable: false,
        sortField: 'vesselName'
      },

      {
        field: 'volume',
        header: 'LOADABLE_PLAN_BUNKER_QTY',
        filter: false,
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'vesselName',
        filterByServer: true,
        sortable: false,
        sortField: 'vesselName'
      }

    ];
    return columns;
  }

  /**
   * get B/L Figure columns
   * @returns {IDataTableColumn}
   * @memberof UllageUpdatePopupTransformationService
   */
   getBLFigureColumns():IDataTableColumn[] {
    return [
      {
        field: 'cargoName',
        header: 'LOADABLE_PLAN_BL_FIGURE_CARGO_NAME',
        editable: false
      },
      {
        field: 'blRefNo',
        header: 'LOADABLE_PLAN_BL_REF_NO',
        editable: true,
        fieldPlaceholder: "LOADABLE_PLAN_BL_REF_NO_PLACEHOLDER",
        fieldType: DATATABLE_FIELD_TYPE.TEXT,
        errorMessages: {
          required: 'LOADABLE_PLAN_BL_REF_NO_REQUIRED'
        }
      },
      {
        field: 'bbl',
        header: 'LOADABLE_PLAN_BL_BBL@60F',
        editable: true,
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        fieldPlaceholder: "LOADABLE_PLAN_BL_BBL@60F_PLACEHOLDER",
        errorMessages: {
          required: 'LOADABLE_PLAN_BL_BBL@60F_REQUIRED'
        }
      },
      {
        field: 'lt',
        header: 'LOADABLE_PLAN_BL_LT',
        editable: true,
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        fieldPlaceholder: "LOADABLE_PLAN_BL_LT_PLACEHOLDER",
        errorMessages: {
          required: 'LOADABLE_PLAN_BL_LT_REQUIRED'
        }
      },
      {
        field: 'mt',
        header: 'LOADABLE_PLAN_BL_MT',
        editable: true,
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        fieldPlaceholder: "LOADABLE_PLAN_BL_MT_REQUIRED",
        errorMessages: {
          required: 'LOADABLE_PLAN_BL_MT_REQUIRED'
        }
      },
      {
        field: 'kl',
        header: 'LOADABLE_PLAN_BL_KL',
        editable: true,
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        fieldPlaceholder: "LOADABLE_PLAN_BL_KL_PLACEHOLDER",
        errorMessages: {
          required: 'LOADABLE_PLAN_BL_KL_REQUIRED'
        }
      },
      {
        field: 'api',
        header: 'LOADABLE_PLAN_BL_API',
        editable: true,
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        fieldPlaceholder: "LOADABLE_PLAN_BL_API_PLACEHOLDER",
        errorMessages: {
          required: 'LOADABLE_PLAN_BL_API_REQUIRED'
        }
      },
      {
        field: 'temp',
        header: 'LOADABLE_PLAN_BL_TEMP',
        editable: true,
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        fieldPlaceholder: "LOADABLE_PLAN_BL_TEMP_PLACEHOLDER",
        errorMessages: {
          required: 'LOADABLE_PLAN_BL_TEMP_REQUIRED'
        }
      },
      {
        field: 'actions',
        header: '',
        fieldType: DATATABLE_FIELD_TYPE.ACTION
      }
    ]
  }

    /**
  * Method to convert loadable plan tank details to value object
  *
  * @param {ICargoDetail} cargoTankDetail
  * @param {boolean} [isEditMode=true]
  * @param {boolean} [isAdd=true]
  * @returns {ICargoDetailValueObject}
  * @memberof UllageUpdatePopupTransformationService
  */
  getFormatedCargoDetails(cargoTankDetail: ICargoDetail , isEditMode = true , isAdd = true) {
      const _cargoDetail = <ICargoDetailValueObject>{};
      _cargoDetail.blRefNo = new ValueObject<string>(cargoTankDetail.blRefNo , true , isEditMode);
      _cargoDetail.bbl = new ValueObject<number>(cargoTankDetail.bbl , true , isEditMode);
      _cargoDetail.lt = new ValueObject<number>(cargoTankDetail.lt , true , isEditMode);
      _cargoDetail.mt = new ValueObject<number>(cargoTankDetail.mt , true , isEditMode);
      _cargoDetail.kl = new ValueObject<number>(cargoTankDetail.kl , true , isEditMode);
      _cargoDetail.api = new ValueObject<number>(cargoTankDetail.api , true , isEditMode);
      _cargoDetail.temp = new ValueObject<number>(cargoTankDetail.temp , true , isEditMode);
      _cargoDetail.cargoName = cargoTankDetail.cargoName;
      _cargoDetail.isAdd = isAdd;
      return _cargoDetail;
    }
}



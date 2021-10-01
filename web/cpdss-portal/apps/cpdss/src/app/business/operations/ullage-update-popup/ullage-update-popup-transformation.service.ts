import { Injectable } from '@angular/core';
import { QuantityPipe } from '../../../shared/pipes/quantity/quantity.pipe';
import { ICargoQuantities, IShipCargoTank, ITank, IBallastQuantities, IShipBallastTank, IShipBunkerTank, IBunkerQuantities } from '../../core/models/common.model';
import { QUANTITY_UNIT } from '../../../shared/models/common.model';
import { OHQ_MODE } from '../../cargo-planning/models/cargo-planning.model';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';
import { DATATABLE_FIELD_TYPE, IDataTableColumn, DATATABLE_FILTER_TYPE, DATATABLE_FILTER_MATCHMODE } from '../../../shared/components/datatable/datatable.model';
import { ICargoDetail, ICargoDetailValueObject, ITankDetailsValueObject, ULLAGE_STATUS } from '../models/loading-discharging.model';
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
            ballastTank[groupIndex][tankIndex].commodity.actualWeight = ballastTankQuantities[index]['quantity'];
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
  formatBunkerTanks(bunkerTank: IShipBunkerTank[][], bunkerTankQuantities: IBunkerQuantities[], mode: ULLAGE_STATUS): IShipBunkerTank[][] {

    for (let groupIndex = 0; groupIndex < bunkerTank?.length; groupIndex++) {
      for (let tankIndex = 0; tankIndex < bunkerTank[groupIndex].length; tankIndex++) {
        for (let index = 0; index < bunkerTankQuantities.length; index++) {
          if (bunkerTankQuantities[index]?.tankId === bunkerTank[groupIndex][tankIndex]?.id) {
            bunkerTank[groupIndex][tankIndex].commodity = bunkerTankQuantities[index];
            bunkerTank[groupIndex][tankIndex].commodity.density = bunkerTank[groupIndex][tankIndex].commodity.density ? bunkerTank[groupIndex][tankIndex].commodity.density : bunkerTank[groupIndex][tankIndex].density;
            bunkerTank[groupIndex][tankIndex].commodity.volume = bunkerTank[groupIndex][tankIndex].commodity?.density ? (bunkerTank[groupIndex][tankIndex].commodity.quantity / bunkerTank[groupIndex][tankIndex].commodity?.density) : 0;
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
    cargoTank = JSON.parse(JSON.stringify(cargoTank));
    for (let groupIndex = 0; groupIndex < cargoTank?.length; groupIndex++) {
      for (let tankIndex = 0; tankIndex < cargoTank[groupIndex].length; tankIndex++) {
        for (let index = 0; index < cargoTankQuantities?.length; index++) {
          if (cargoTankQuantities[index]?.tankId === cargoTank[groupIndex][tankIndex]?.id) {
            cargoTank[groupIndex][tankIndex].commodity = JSON.parse(JSON.stringify(cargoTankQuantities[index]));
            const plannedWeight = this.quantityPipe.transform(cargoTank[groupIndex][tankIndex].commodity.plannedWeight, prevUnit, currUnit, cargoTankQuantities[index]?.api);
            cargoTank[groupIndex][tankIndex].commodity.plannedWeight = plannedWeight ? Number(plannedWeight.toFixed(2)) : 0;
            const actualWeight = this.quantityPipe.transform(cargoTank[groupIndex][tankIndex].commodity.actualWeight, prevUnit, currUnit, cargoTankQuantities[index]?.api);
            cargoTank[groupIndex][tankIndex].commodity.actualWeight = cargoTankQuantities[index].quantity;
            cargoTank[groupIndex][tankIndex].commodity.volume = this.quantityPipe.transform(cargoTank[groupIndex][tankIndex].commodity.actualWeight, currUnit, QUANTITY_UNIT.OBSKL, cargoTank[groupIndex][tankIndex].commodity?.api, cargoTank[groupIndex][tankIndex].commodity?.temperature);
            cargoTank[groupIndex][tankIndex].commodity.percentageFilled = this.getFillingPercentage(cargoTank[groupIndex][tankIndex]);
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
  getCargoTableColumn() {

    const columns: IDataTableColumn[] = [
      {
        field: 'tankName',
        header: 'LOADABLE_PLAN_ULLAGE_UPDATE_TANK_NAME',
        fieldType: DATATABLE_FIELD_TYPE.TEXT,
        editable: false,
        fieldPlaceholder: ''
      },
      {
        field: 'ullage',
        header: 'ULLAGE_UPDATE_ULLAGE_LABEL',
        filter: false,
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        editable: true,
        fieldColumnClass: 'pr-25',
        fieldPlaceholder: '',
        errorMessages: {
          maxLimit: 'ULLAGE_UPDATE_FILLING_ERROR',
          required: 'ULLAGE_UPDATE_ULLAGE_REQUIRED',
          invalidNumber: 'ULLAGE_UPDATE_INVALID_ERROR'
        }
      },
      {
        field: 'temperature',
        header: 'ULLAGE_UPDATE_TEMPERATURE',
        filter: false,
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        editable: true,
        fieldColumnClass: 'pr-25',
        fieldPlaceholder: '',
        errorMessages: {
          maxLimit: 'ULLAGE_UPDATE_FILLING_ERROR',
          required: 'ULLAGE_UPDATE_TEMPERATURTE_REQUIRED',
          min: 'ULLAGE_UPDATE_TEMPERATURE_MIN_ERROR',
          max: 'ULLAGE_UPDATE_TEMPERATURE_MAX_ERROR',
          invalidNumber: 'ULLAGE_UPDATE_INVALID_ERROR'
        }
      },
      {
        field: 'api',
        header: 'LOADABLE_PLAN_ULLAGE_UPDATE_API',
        filter: false,
        fieldColumnClass: 'pr-25',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        editable: true,
        fieldPlaceholder: '',
        errorMessages: {
          maxLimit: 'ULLAGE_UPDATE_FILLING_ERROR',
          required: 'ULLAGE_UPDATE_API_REQUIRED',
          min: 'ULLAGE_UPDATE_API_MIN_ERROR',
          max: 'ULLAGE_UPDATE_API_MAX_ERROR',
          invalidNumber: 'ULLAGE_UPDATE_INVALID_ERROR'
        }
      },
      {
        field: 'quantity',
        header: 'ULLAGE_UPDATE_CARGO_TABLE_QUANTITY_LABEL',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        editable: false,
        showTotal: true,
        fieldPlaceholder: '',
        numberFormat: '1.2-2'
      },

    ];
    return columns;
  }

  /**
   * Method for setting ballast tank table column
   * @returns {IDataTableColumn}
   * @memberof UllageUpdatePopupTransformationService
   */
  getBallastTankColumns() {
    const columns: IDataTableColumn[] = [
      {
        field: 'tankName',
        header: 'LOADABLE_PLAN_BALLAST_TANK_NAME',
        fieldType: DATATABLE_FIELD_TYPE.TEXT,
        editable: false
      },
      {
        field: 'sounding',
        header: 'ULLAGE_UPDATE_SOUNDING_LABEL',
        filter: false,
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        fieldPlaceholder: '',
        editable: true,
        fieldColumnClass: 'pr-25',
        errorMessages: {
          maxLimit: 'ULLAGE_UPDATE_BALLAST_FILLING_ERROR',
          required: 'ULLAGE_UPDATE_SOUNDING_REQUIRED',
          invalidNumber: 'ULLAGE_UPDATE_INVALID_ERROR'
        }
      },

      {
        field: 'quantity',
        header: 'ULLAGE_UPDATE_CARGO_TABLE_QUANTITY_LABEL',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        fieldPlaceholder: '',
        numberFormat: '1.2-2',
        showTotal: true,
        editable: false
      }

    ];
    return columns;
  }

  /**
   * Method for setting bunker tank table column
   * @returns {IDataTableColumn}
   * @memberof UllageUpdatePopupTransformationService
   */
  getBunkerTankColumns() {
    const columns: IDataTableColumn[] = [
      {
        field: 'tankName',
        header: 'LOADABLE_PLAN_BUNKER_TANK_NAME',
        fieldType: DATATABLE_FIELD_TYPE.TEXT,
        fieldPlaceholder: '',
        editable: false
      },
      {
        field: 'density',
        header: 'ULLAGE_UPDATE_BUNKER_DENSITY_LABEL',
        fieldType: DATATABLE_FIELD_TYPE.TEXT,
        fieldPlaceholder: '',
        editable: false
      },
      {
        field: 'quantity',
        header: 'ULLAGE_UPDATE_CARGO_TABLE_QUANTITY_LABEL',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        fieldColumnClass: 'pr-25',
        fieldPlaceholder: '',
        editable: true,
        showTotal: true,
        totalFieldClass: 'pr-30',
        numberFormat: '1.2-2',
        errorMessages: {
          required: 'ULLAGE_UPDATE_QUANTITY_REQUIRED',
          min: 'ULLAGE_UPDATE_INVALID_ERROR',
          fillingError: 'ULLAGE_UPDATE_BUNKER_FILLING_ERROR',
          invalidNumber: 'ULLAGE_UPDATE_INVALID_ERROR'
        }
      }

    ];
    return columns;
  }

  /**
   * get B/L Figure columns
   * @returns {IDataTableColumn}
   * @memberof UllageUpdatePopupTransformationService
   */
  getBLFigureColumns(): IDataTableColumn[] {
    return [
      {
        field: 'cargoName',
        header: 'LOADABLE_PLAN_BL_FIGURE_CARGO_NAME',
        editable: false,
        fieldType: DATATABLE_FIELD_TYPE.BADGE,
      },
      {
        field: 'blRefNo',
        header: 'LOADABLE_PLAN_BL_REF_NO',
        editable: true,
        fieldPlaceholder: "LOADABLE_PLAN_BL_REF_NO_PLACEHOLDER",
        fieldType: DATATABLE_FIELD_TYPE.TEXT,
        errorMessages: {
          required: 'LOADABLE_PLAN_BL_REF_NO_REQUIRED',
          pattern: 'ULLAGE_UPDATE_BLREF_PATTERN_ERROR',
          minlength: 'ULLAGE_UPDATE_BLREF_MIN_LENGTH_ERROR',
          maxlength: 'ULLAGE_UPDATE_BLREF_MAX_LENGTH_ERROR',
          invalidNumber: 'ULLAGE_UPDATE_INVALID_ENTRY'
        }
      },
      {
        field: 'bbl',
        header: 'LOADABLE_PLAN_BL_BBL@60F',
        editable: true,
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        fieldPlaceholder: "LOADABLE_PLAN_BL_BBL@60F_PLACEHOLDER",
        numberFormat: '1.0',
        errorMessages: {
          required: 'LOADABLE_PLAN_BL_BBL@60F_REQUIRED',
          rangeError: 'ULLAGE_UPDATE_VALUE_RANGE_ERROR',
          min: 'ULLAGE_UPDATE_QUANTITY_NEGATIVE_ERROR',
          invalidNumber: 'ULLAGE_UPDATE_INVALID_ENTRY'
        }
      },
      {
        field: 'lt',
        header: 'LOADABLE_PLAN_BL_LT',
        editable: true,
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        fieldPlaceholder: "LOADABLE_PLAN_BL_LT_PLACEHOLDER",
        numberFormat: '1.2-2',
        errorMessages: {
          required: 'LOADABLE_PLAN_BL_LT_REQUIRED',
          rangeError: 'ULLAGE_UPDATE_VALUE_RANGE_ERROR',
          min: 'ULLAGE_UPDATE_QUANTITY_NEGATIVE_ERROR',
          invalidNumber: 'ULLAGE_UPDATE_INVALID_ENTRY'
        }
      },
      {
        field: 'mt',
        header: 'LOADABLE_PLAN_BL_MT',
        editable: true,
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        fieldPlaceholder: "LOADABLE_PLAN_BL_MT_REQUIRED",
        numberFormat: '1.2-2',
        errorMessages: {
          required: 'LOADABLE_PLAN_BL_MT_REQUIRED',
          rangeError: 'ULLAGE_UPDATE_VALUE_RANGE_ERROR',
          min: 'ULLAGE_UPDATE_QUANTITY_NEGATIVE_ERROR',
          invalidNumber: 'ULLAGE_UPDATE_INVALID_ENTRY'
        }
      },
      {
        field: 'kl',
        header: 'LOADABLE_PLAN_BL_KL',
        editable: true,
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        fieldPlaceholder: "LOADABLE_PLAN_BL_KL_PLACEHOLDER",
        numberFormat: '1.3-3',
        errorMessages: {
          required: 'LOADABLE_PLAN_BL_KL_REQUIRED',
          rangeError: 'ULLAGE_UPDATE_VALUE_RANGE_ERROR',
          min: 'ULLAGE_UPDATE_QUANTITY_NEGATIVE_ERROR',
          invalidNumber: 'ULLAGE_UPDATE_INVALID_ENTRY'
        }
      },
      {
        field: 'api',
        header: 'LOADABLE_PLAN_BL_API',
        editable: true,
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        fieldPlaceholder: "LOADABLE_PLAN_BL_API_PLACEHOLDER",
        numberFormat: '1.2-2',
        errorMessages: {
          required: 'LOADABLE_PLAN_BL_API_REQUIRED',
          min: 'ULLAGE_UPDATE_API_MIN_ERROR',
          invalidNumber: 'ULLAGE_UPDATE_INVALID_ENTRY',
          max: 'ULLAGE_UPDATE_API_MAX_ERROR'
        }
      },
      {
        field: 'temp',
        header: 'ULLAGE_UPDATE_TEMPERATURE',
        editable: true,
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        fieldPlaceholder: "LOADABLE_PLAN_BL_TEMP_PLACEHOLDER",
        numberFormat: '1.2-2',
        errorMessages: {
          required: 'LOADABLE_PLAN_BL_TEMP_REQUIRED',
          min: 'ULLAGE_UPDATE_TEMPERATURE_MIN_ERROR',
          invalidNumber: 'ULLAGE_UPDATE_INVALID_ENTRY',
          max: 'ULLAGE_UPDATE_TEMPERATURE_MAX_ERROR'
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
  getFormatedCargoDetails(cargoTankDetail: ICargoDetail, cargoRow: any, isEditMode = true, isAdd: boolean) {
    const _cargoDetail = <ICargoDetailValueObject>{};
    _cargoDetail.blRefNo = new ValueObject<string>(cargoTankDetail?.blRefNo, true, isEditMode);
    _cargoDetail.bbl = new ValueObject<number>(cargoTankDetail?.quantityBbls ? cargoTankDetail?.quantityBbls : 0, true, isEditMode);
    _cargoDetail.lt = new ValueObject<number>(cargoTankDetail?.quantityLT ? cargoTankDetail?.quantityLT : 0, true, isEditMode);
    _cargoDetail.mt = new ValueObject<number>(cargoTankDetail?.quantityMt ? cargoTankDetail?.quantityMt : 0, true, isEditMode);
    _cargoDetail.kl = new ValueObject<number>(cargoTankDetail?.quantityKl ? cargoTankDetail?.quantityKl : 0, true, isEditMode);
    _cargoDetail.api = new ValueObject<number>(cargoTankDetail?.api, true, isEditMode);
    _cargoDetail.temp = new ValueObject<number>(cargoTankDetail?.temperature, true, isEditMode);
    _cargoDetail.cargoName = cargoRow?.cargoAbbrevation;
    _cargoDetail.isNewRow = isAdd;
    _cargoDetail.cargoNominationId = cargoRow?.cargoNominationId;
    _cargoDetail.portId = cargoTankDetail?.portId;
    _cargoDetail.cargoId = cargoRow?.cargoId
    _cargoDetail.isAdd = true;
    _cargoDetail.id = cargoTankDetail?.id;
    _cargoDetail.cargoColor = cargoRow.cargoColor;
    return _cargoDetail;
  }

  /**
  * Method to convert cargo tank details to value object
  *
  * @param cargoTankDetail
  * @param {boolean} [isEditMode=true]
  * @param {boolean} [isAdd=true]
  * @returns {ITankDetailsValueObject}
  * @memberof UllageUpdatePopupTransformationService
  */
  getFormatedTankDetailsCargo(cargoTank: any, isEditMode = true, isPlanned: boolean, isAdd = true) {
    const _tankDetails = <ITankDetailsValueObject>{};
    _tankDetails.tankName = new ValueObject<string>(cargoTank?.tankShortName ? cargoTank?.tankShortName : 0, true, false, false, true);
    _tankDetails.ullage = new ValueObject<number>(cargoTank?.ullage && !isPlanned ? Number(cargoTank?.ullage) : 0, true, isEditMode, false, true);
    _tankDetails.temperature = new ValueObject<number>(cargoTank?.temperature && !isPlanned ? Number(cargoTank?.temperature) : 0, true, isEditMode, false, true);
    _tankDetails.api = new ValueObject<number>(cargoTank?.api && !isPlanned ? Number(cargoTank?.api) : 0, true, isEditMode, false, true);
    _tankDetails.quantity = new ValueObject<number>(cargoTank?.quantity && !isPlanned ? cargoTank?.quantity : 0, true, false, false, true);
    _tankDetails.id = cargoTank.id;
    _tankDetails.tankId = cargoTank.tankId;
    _tankDetails.loadablePatternId = cargoTank?.loadablePatternId;
    _tankDetails.dischargePatternId = cargoTank?.dischargePatternId;
    _tankDetails.fillingPercentage = new ValueObject<number>(cargoTank.fillingPercentage ? cargoTank.fillingPercentage : '');
    _tankDetails.cargoNominationId = cargoTank.cargoNominationId;
    _tankDetails.arrivalDeparture = cargoTank.arrivalDeparture
    _tankDetails.isAdd = true;
    _tankDetails.actualPlanned = cargoTank.actualPlanned;
    _tankDetails.correctionFactor = cargoTank.correctionFactor;
    return _tankDetails;
  }

  /**
  * Method to convert ballast tank details to value object
  *
  * @param ballastTank
  * @param {boolean} [isEditMode=true]
  * @param {boolean} [isAdd=true]
  * @returns {ITankDetailsValueObject}
  * @memberof UllageUpdatePopupTransformationService
  */
  getFormatedTankDetailsBallast(ballastTank: any, isEditMode = true, isPlanned: boolean, isAdd = true) {
    const _tankDetails = <ITankDetailsValueObject>{};
    _tankDetails.tankName = new ValueObject<string>(ballastTank?.tankShortName ? ballastTank?.tankShortName : 0, true, false, false, true);
    _tankDetails.quantity = new ValueObject<number | string>(ballastTank?.quantity && !isPlanned ? Number(ballastTank?.quantity) : 0, true, false, false, true);
    _tankDetails.sounding = new ValueObject<number | string>(ballastTank.sounding && !isPlanned ? Number(ballastTank.sounding) : 0, true, isEditMode, false, true);
    _tankDetails.tankId = ballastTank.tankId;
    _tankDetails.loadablePatternId = ballastTank?.loadablePatternId;
    _tankDetails.dischargePatternId = ballastTank?.dischargePatternId;
    _tankDetails.temperature = new ValueObject<number>(ballastTank.temperature ? ballastTank.temperature : '', true, false, false, true);
    _tankDetails.isAdd = true;
    _tankDetails.id = ballastTank?.id;
    _tankDetails.correctedUllage = ballastTank?.correctedUllage;
    _tankDetails.colorCode = ballastTank?.colorCode;
    _tankDetails.fillingPercentage = new ValueObject<number>(ballastTank.fillingPercentage ? ballastTank.fillingPercentage : '', true, false, false, true);
    _tankDetails.arrivalDeparture = ballastTank?.arrivalDeparture;
    _tankDetails.actualPlanned = ballastTank?.actualPlanned;
    _tankDetails.sg = ballastTank?.sg;
    return _tankDetails;
  }

  /**
* Method to convert bunker tank details to value object
*
* @param bunkerTank
* @param {boolean} [isEditMode=true]
* @param {boolean} [isAdd=true]
* @returns {ITankDetailsValueObject}
* @memberof UllageUpdatePopupTransformationService
*/
  getFormatedTankDetailsBunker(bunkerTank: any, isEditMode = true, isPlanned: boolean, isAdd = true) {

    const _tankDetails = <ITankDetailsValueObject>{};
    _tankDetails.tankName = new ValueObject<string>(bunkerTank?.tankShortName ? bunkerTank?.tankShortName : '', true, false, false, true);
    _tankDetails.quantity = new ValueObject<number>(bunkerTank?.quantity && !isPlanned ? Number(bunkerTank?.quantity) : 0, true, isEditMode, false, true);
    _tankDetails.tankId = bunkerTank.tankId;
    _tankDetails.density = new ValueObject<number>(bunkerTank.density && !isPlanned ? bunkerTank.density : 0, true, false, false, true);
    _tankDetails.loadablePatternId = bunkerTank?.loadablePatternId;
    _tankDetails.dischargePatternId = bunkerTank?.dischargePatternId;
    _tankDetails.temperature = bunkerTank?.temperature;
    _tankDetails.colorCode = bunkerTank?.colorCode;
    _tankDetails.actualPlanned = bunkerTank?.actualPlanned;
    _tankDetails.arrivalDeparture = bunkerTank?.arrivalDeparture;
    _tankDetails.isAdd = true;
    return _tankDetails;
  }

  /**
* Method to convert cargo tank quantities data
*
* @param data
* @memberof UllageUpdatePopupTransformationService
*/
  formatCargoQuantity(data) {
    const cargoQuantity: any = {};
    cargoQuantity.cargoAbbrevation = data.cargoAbbrevation;
    cargoQuantity.nominationTotal = this.quantityPipe.transform(data.nominationTotal, QUANTITY_UNIT.MT, QUANTITY_UNIT.BBLS, data.nominationApi) ?? 0;
    cargoQuantity.maxTolerance = data.maxTolerance;
    cargoQuantity.minTolerance = data.minTolerance;
    cargoQuantity.maxQuantity = data.maxQuantity;
    cargoQuantity.minQuantity = data.minQuantity;
    cargoQuantity.api = data.blAvgApi;
    cargoQuantity.cargoColor = data.cargoColor;
    cargoQuantity.cargoNominationId = data.cargoNominationId;
    cargoQuantity.cargoLoaded = data?.cargoLoaded;
    cargoQuantity.cargoToBeLoaded = data?.cargoToBeLoaded;
    cargoQuantity.cargoDischarged = data?.cargoDischarged;
    cargoQuantity.cargoToBeDischarged = data?.cargoToBeDischarged;
    cargoQuantity.plan = {
      bbl: this.quantityPipe.transform(data.plannedQuantityTotal, QUANTITY_UNIT.MT, QUANTITY_UNIT.BBLS, data.nominationApi) ?? 0,
      lt: this.quantityPipe.transform(data.plannedQuantityTotal, QUANTITY_UNIT.MT, QUANTITY_UNIT.LT, data.nominationApi) ?? 0,
      mt: data.plannedQuantityTotal ?? 0,
      kl: this.quantityPipe.transform(data.plannedQuantityTotal, QUANTITY_UNIT.MT, QUANTITY_UNIT.KL, data.nominationApi) ?? 0,
      api: data.nominationApi ? Number(Number(data.nominationApi).toFixed(2)) : 0,
      temp: data.nominationTemp ? Number(Number(data.nominationTemp).toFixed(2)) : 0
    };
    cargoQuantity.actual = {
      bbl: this.quantityPipe.transform(data.actualQuantityTotal, QUANTITY_UNIT.MT, QUANTITY_UNIT.BBLS, data.actualAvgApi),
      lt: this.quantityPipe.transform(data.actualQuantityTotal, QUANTITY_UNIT.MT, QUANTITY_UNIT.LT, data.actualAvgApi),
      mt: data.actualQuantityTotal,
      kl: this.quantityPipe.transform(data.actualQuantityTotal, QUANTITY_UNIT.MT, QUANTITY_UNIT.KL, data.actualAvgApi),
      api: Number(data.actualAvgApi),
      temp: data.actualAvgTemp
    };
    cargoQuantity.blFigure = {
      bbl: this.quantityPipe.transform(data.blQuantityTotal, QUANTITY_UNIT.MT, QUANTITY_UNIT.BBLS, data.blAvgApi),
      lt: this.quantityPipe.transform(data.blQuantityTotal, QUANTITY_UNIT.MT, QUANTITY_UNIT.LT, data.blAvgApi),
      mt: data.blQuantityTotal,
      kl: this.quantityPipe.transform(data.blQuantityTotal, QUANTITY_UNIT.MT, QUANTITY_UNIT.KL, data.blAvgApi),
      api: Number(data?.blAvgApi),
      temp: data.blAvgTemp
    };
    cargoQuantity.difference = {
      bbl: this.quantityPipe.transform(data.difference, QUANTITY_UNIT.MT, QUANTITY_UNIT.BBLS, data.blAvgApi),
      lt: this.quantityPipe.transform(data.difference, QUANTITY_UNIT.MT, QUANTITY_UNIT.LT, data.blAvgApi),
      mt: data.difference,
      kl: this.quantityPipe.transform(data.difference, QUANTITY_UNIT.MT, QUANTITY_UNIT.KL, data.blAvgApi),

    };
    cargoQuantity.diffPercentage = {
      bbl: this.quantityPipe.transform(data.difference, QUANTITY_UNIT.MT, QUANTITY_UNIT.BBLS, data.blAvgApi),
      lt: this.quantityPipe.transform(data.difference, QUANTITY_UNIT.MT, QUANTITY_UNIT.LT, data.blAvgApi),
      mt: data.difference,
      kl: this.quantityPipe.transform(data.difference, QUANTITY_UNIT.MT, QUANTITY_UNIT.KL, data.blAvgApi),
    };
    return cargoQuantity;
  }
}



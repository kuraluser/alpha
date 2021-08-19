import { Injectable } from '@angular/core';
import { QuantityPipe } from '../../../shared/pipes/quantity/quantity.pipe';
import { IBallastQuantities, IShipBallastTank, IShipBunkerTank, IBunkerQuantities } from '../../voyage-status/models/voyage-status.model';
import { ICargoQuantities, IShipCargoTank, ITank } from '../../core/models/common.model';
import { QUANTITY_UNIT } from '../../../shared/models/common.model';
import { OHQ_MODE } from '../../cargo-planning/models/cargo-planning.model';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';
import { DATATABLE_FIELD_TYPE, IDataTableColumn, DATATABLE_FILTER_TYPE, DATATABLE_FILTER_MATCHMODE } from '../../../shared/components/datatable/datatable.model';
import { ICargoDetail, ICargoDetailValueObject, ITankDetailsValueObject } from '../models/ullage-update-popup.model';
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
  formatBunkerTanks(bunkerTank: IShipBunkerTank[][], bunkerTankQuantities: IBunkerQuantities[], mode: OHQ_MODE): IShipBunkerTank[][] {

    for (let groupIndex = 0; groupIndex < bunkerTank?.length; groupIndex++) {
      for (let tankIndex = 0; tankIndex < bunkerTank[groupIndex].length; tankIndex++) {
        for (let index = 0; index < bunkerTankQuantities.length; index++) {
          if (bunkerTankQuantities[index]?.tankId === bunkerTank[groupIndex][tankIndex]?.id) {
            bunkerTank[groupIndex][tankIndex].commodity = bunkerTankQuantities[index];
            // const quantity = mode === OHQ_MODE.ARRIVAL ? bunkerTank[groupIndex][tankIndex]?.commodity?.actualArrivalQuantity : bunkerTank[groupIndex][tankIndex]?.commodity?.actualDepartureQuantity;
            // bunkerTank[groupIndex][tankIndex].commodity.quantity = quantity ? Number(quantity.toFixed(2)) : 0;
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
    cargoTank = JSON.parse(JSON.stringify(cargoTank));
    for (let groupIndex = 0; groupIndex < cargoTank?.length; groupIndex++) {
      for (let tankIndex = 0; tankIndex < cargoTank[groupIndex].length; tankIndex++) {
        for (let index = 0; index < cargoTankQuantities?.length; index++) {
          if (cargoTankQuantities[index]?.tankId === cargoTank[groupIndex][tankIndex]?.id) {
            cargoTank[groupIndex][tankIndex].commodity = cargoTankQuantities[index];
            const plannedWeight = this.quantityPipe.transform(cargoTank[groupIndex][tankIndex].commodity.plannedWeight, prevUnit, currUnit, cargoTankQuantities[index]?.api);
            cargoTank[groupIndex][tankIndex].commodity.plannedWeight = plannedWeight ? Number(plannedWeight.toFixed(2)) : 0;
            const actualWeight = this.quantityPipe.transform(cargoTank[groupIndex][tankIndex].commodity.actualWeight, prevUnit, currUnit, cargoTankQuantities[index]?.api);
            cargoTank[groupIndex][tankIndex].commodity.actualWeight = cargoTankQuantities[index].quantity;
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
  getCargoTableColumn() {

    const columns: IDataTableColumn[] = [
      {
        field: 'tankName',
        header: 'LOADABLE_PLAN_ULLAGE_UPDATE_TANK_NAME',
        fieldType: DATATABLE_FIELD_TYPE.TEXT,
        editable: true
      },
      {
        field: 'ullage',
        header: 'LOADABLE_PLAN_ULLAGE_UPDATE_RDG_ULLAGE',
        filter: false,
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        editable: true
      },
      {
        field: 'temperature',
        header: 'LOADABLE_PLAN_ULLAGE_UPDATE_TEMP',
        filter: false,
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        editable: true
      },
      {
        field: 'api',
        header: 'LOADABLE_PLAN_ULLAGE_UPDATE_API',
        filter: false,
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        editable: true
      }

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
        editable: true
      },
      {
        field: 'ullage',
        header: 'LOADABLE_PLAN_BALLAST_ULLAGE',
        filter: false,
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        editable: true
      },

      {
        field: 'quantity',
        header: 'LOADABLE_PLAN_BALLAST_QTY',
        filter: false,
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        editable: true
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
        filter: false,
        fieldType: DATATABLE_FIELD_TYPE.TEXT,
        editable: true
      },
      {
        field: 'quantity',
        header: 'LOADABLE_PLAN_BUNKER_QTY',
        filter: false,
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        editable: true
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
  getFormatedCargoDetails(cargoTankDetail: ICargoDetail, cargoRow: any, isEditMode = true, isAdd = true) {
    const _cargoDetail = <ICargoDetailValueObject>{};
    _cargoDetail.blRefNo = new ValueObject<string>(cargoTankDetail?.blRefNo, true, isEditMode);
    _cargoDetail.bbl = new ValueObject<number>(cargoTankDetail?.quantityBbls, true, isEditMode);
    _cargoDetail.lt = new ValueObject<number>(cargoTankDetail?.quantityLT, true, isEditMode);
    _cargoDetail.mt = new ValueObject<number>(cargoTankDetail?.quantityMt, true, isEditMode);
    _cargoDetail.kl = new ValueObject<number>(cargoTankDetail?.quantityKl, true, isEditMode);
    _cargoDetail.api = new ValueObject<number>(cargoTankDetail?.api, true, isEditMode);
    _cargoDetail.temp = new ValueObject<number>(cargoTankDetail?.temperature, true, isEditMode);
    _cargoDetail.cargoName = cargoRow.cargoAbbrevation;
    _cargoDetail.isAdd = isAdd;
    _cargoDetail.cargoNominationId = cargoRow.cargoNominationId;
    _cargoDetail.portId = cargoTankDetail.portId;
    _cargoDetail.cargoId = cargoRow.cargoId
    return _cargoDetail;
  }


  getFormatedTankDetailsCargo(cagroTank:any, isEditMode = true, isAdd = true){
    const _tankDetails = <ITankDetailsValueObject>{};
    _tankDetails.tankName = new ValueObject<string>(cagroTank?.tankname ? cagroTank?.tankname : '', true, false, false, true);
    _tankDetails.ullage = new ValueObject<number>(cagroTank?.ullage ? cagroTank?.ullage : '', true, isEditMode, false, true );
    _tankDetails.temperature = new ValueObject<number>(cagroTank?.temperature ? cagroTank?.temperature : '', true, isEditMode, false, true);
    _tankDetails.api = new ValueObject<number>(cagroTank?.api ? cagroTank?.api : '', true, isEditMode, false, true);
    _tankDetails.quantity = new ValueObject<number>(cagroTank?.quantity ? cagroTank?.quantity : '', true, isEditMode, false, true);
    return _tankDetails;
  }

  getFormatedTankDetailsBallast(ballastTank:any, isEditMode = true, isAdd = true){
    const _tankDetails = <ITankDetailsValueObject>{};
    _tankDetails.tankName = new ValueObject<string>(ballastTank?.tankname ? ballastTank?.tankname : '', true, false, false, true);
    _tankDetails.ullage = new ValueObject<number>(ballastTank?.correctedUllage ? ballastTank?.correctedUllage : '', true, isEditMode, false, true );
    _tankDetails.quantity = new ValueObject<number>(ballastTank?.quantity ? ballastTank?.quantity : '', true, isEditMode, false, true);
    return _tankDetails;
  }

  getFormatedTankDetailsBunker(bunkerTank:any, isEditMode = true, isAdd = true){
    const _tankDetails = <ITankDetailsValueObject>{};
    _tankDetails.tankName = new ValueObject<string>(bunkerTank?.tankname ? bunkerTank?.tankname : '', true, false, false, true);
    _tankDetails.quantity = new ValueObject<number>(bunkerTank?.quantity ? bunkerTank?.quantity : '', true, isEditMode, false, true);
    return _tankDetails;
  }

  formatCargoQuantity(data) {
    const cargoQuantity: any = {};
    cargoQuantity.cargoAbbrevation = data.cargoAbbrevation;
    cargoQuantity.nominationTotal = data.nominationTotal;
    cargoQuantity.maxTolerance = data.maxTolerance;
    cargoQuantity.minTolerance = data.minTolerance;
    cargoQuantity.maxQuantity = data.maxQuantity;
    cargoQuantity.minQuantity = data.minQuantity;
    cargoQuantity.api = data.blAvgApi;
    cargoQuantity.cargoNominationId = data.cargoNominationId;
    cargoQuantity.plan = {
      bbl: this.quantityPipe.transform(data.plannedQuantityTotal, QUANTITY_UNIT.MT, QUANTITY_UNIT.BBLS, data.blAvgApi),
      lt: this.quantityPipe.transform(data.plannedQuantityTotal, QUANTITY_UNIT.MT, QUANTITY_UNIT.LT, data.blAvgApi),
      mt: data.plannedQuantityTotal,
      kl: this.quantityPipe.transform(data.plannedQuantityTotal, QUANTITY_UNIT.MT, QUANTITY_UNIT.KL, data.blAvgApi),
      api: data.blAvgApi,
      temp: data.blAvgTemp
    };
    cargoQuantity.actual = {
      bbl: this.quantityPipe.transform(data.actualQuantityTotal, QUANTITY_UNIT.MT, QUANTITY_UNIT.BBLS, data.blAvgApi),
      lt: this.quantityPipe.transform(data.actualQuantityTotal, QUANTITY_UNIT.MT, QUANTITY_UNIT.LT, data.blAvgApi),
      mt: data.actualQuantityTotal,
      kl: this.quantityPipe.transform(data.actualQuantityTotal, QUANTITY_UNIT.MT, QUANTITY_UNIT.KL, data.blAvgApi),
      api: data.blAvgApi,
      temp: data.blAvgTemp
    };
    cargoQuantity.blFigure = {
      bbl: this.quantityPipe.transform(data.blQuantityTotal, QUANTITY_UNIT.MT, QUANTITY_UNIT.BBLS, data.blAvgApi),
      lt: this.quantityPipe.transform(data.blQuantityTotal, QUANTITY_UNIT.MT, QUANTITY_UNIT.LT, data.blAvgApi),
      mt: data.blQuantityTotal,
      kl: this.quantityPipe.transform(data.blQuantityTotal, QUANTITY_UNIT.MT, QUANTITY_UNIT.KL, data.blAvgApi),
      api: data.blAvgApi,
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

  getResponseData() {
    const data = {
      "responseStatus": {
          "status": "200"
      },
      "billOfLaddingList": [
          {
              "cargoId": 33,
              "cargoColor": "#f6ef0e",
              "cargoName": "",
              "cargoAbbrevation": "ARM",
              "cargoNominationId": 17375,
              "billOfLaddings": [
                  {
                      "id": 0,
                      "portId": 359,
                      "quantityBbls": 20.0000,
                      "quantityMt": 22.0000,
                      "quantityKl": 23.0000,
                      "api": 24.0000,
                      "temperature": 25.0000,
                      "blRefNo": "A112"
                  }
              ]
          },
          {
              "cargoId": 32,
              "cargoColor": "#f91010",
              "cargoName": "",
              "cargoAbbrevation": "ARL",
              "cargoNominationId": 17376,
              "billOfLaddings": [
                  {
                      "id": 0,
                      "portId": 2065,
                      "cargoNominationId": 17376,
                      "quantityBbls": 20.0000,
                      "quantityMt": 22.0000,
                      "quantityKl": 23.0000,
                      "api": 24.0000,
                      "temperature": 25.0000,
                      "blRefNo": "B332"
                  }
              ]
          }
      ],
      "portLoadablePlanStowageDetails": [
          {
              "abbreviation": "",
              "api": "40.2000",
              "cargoNominationId": 17375,
              "cargoId": 0,
              "colorCode": "",
              "correctedUllage": "",
              "correctionFactor": "",
              "fillingPercentage": "",
              "id": 290,
              "loadablePatternId": 4886,
              "observedBarrels": null,
              "observedBarrelsAt60": null,
              "observedM3": null,
              "rdgUllage": "",
              "tankId": 25596,
              "tankname": null,
              "temperature": "110.0000",
              "weight": "",
              "quantity": "3233.0000",
              "actualPlanned": "1",
              "arrivalDeparture": "2",
              "ullage": "1.3500",
              "active": false
          },
          {
              "abbreviation": "",
              "api": "40.2000",
              "cargoNominationId": 17375,
              "cargoId": 0,
              "colorCode": "",
              "correctedUllage": "",
              "correctionFactor": "",
              "fillingPercentage": "",
              "id": 291,
              "loadablePatternId": 4886,
              "observedBarrels": null,
              "observedBarrelsAt60": null,
              "observedM3": null,
              "rdgUllage": "",
              "tankId": 25595,
              "tankname": null,
              "temperature": "110.0000",
              "weight": "",
              "quantity": "3234.0000",
              "actualPlanned": "1",
              "arrivalDeparture": "2",
              "ullage": "1.3500",
              "active": false
          },
          {
              "abbreviation": "",
              "api": "40.2000",
              "cargoNominationId": 17375,
              "cargoId": 0,
              "colorCode": "",
              "correctedUllage": "",
              "correctionFactor": "",
              "fillingPercentage": "",
              "id": 292,
              "loadablePatternId": 4886,
              "observedBarrels": null,
              "observedBarrelsAt60": null,
              "observedM3": null,
              "rdgUllage": "",
              "tankId": 25594,
              "tankname": null,
              "temperature": "110.0000",
              "weight": "",
              "quantity": "13569.0000",
              "actualPlanned": "1",
              "arrivalDeparture": "2",
              "ullage": "1.4500",
              "active": false
          },
          {
              "abbreviation": "",
              "api": "40.2000",
              "cargoNominationId": 17375,
              "cargoId": 0,
              "colorCode": "",
              "correctedUllage": "",
              "correctionFactor": "",
              "fillingPercentage": "",
              "id": 293,
              "loadablePatternId": 4886,
              "observedBarrels": null,
              "observedBarrelsAt60": null,
              "observedM3": null,
              "rdgUllage": "",
              "tankId": 25593,
              "tankname": null,
              "temperature": "110.0000",
              "weight": "",
              "quantity": "13569.0000",
              "actualPlanned": "1",
              "arrivalDeparture": "2",
              "ullage": "1.4500",
              "active": false
          },
          {
              "abbreviation": "",
              "api": "40.2000",
              "cargoNominationId": 17375,
              "cargoId": 0,
              "colorCode": "",
              "correctedUllage": "",
              "correctionFactor": "",
              "fillingPercentage": "",
              "id": 294,
              "loadablePatternId": 4886,
              "observedBarrels": null,
              "observedBarrelsAt60": null,
              "observedM3": null,
              "rdgUllage": "",
              "tankId": 25592,
              "tankname": null,
              "temperature": "110.0000",
              "weight": "",
              "quantity": "15936.0000",
              "actualPlanned": "1",
              "arrivalDeparture": "2",
              "ullage": "1.3900",
              "active": false
          },
          {
              "abbreviation": "",
              "api": "40.2000",
              "cargoNominationId": 17375,
              "cargoId": 0,
              "colorCode": "",
              "correctedUllage": "",
              "correctionFactor": "",
              "fillingPercentage": "",
              "id": 295,
              "loadablePatternId": 4886,
              "observedBarrels": null,
              "observedBarrelsAt60": null,
              "observedM3": null,
              "rdgUllage": "",
              "tankId": 25591,
              "tankname": null,
              "temperature": "110.0000",
              "weight": "",
              "quantity": "15936.0000",
              "actualPlanned": "1",
              "arrivalDeparture": "2",
              "ullage": "1.3900",
              "active": false
          },
          {
              "abbreviation": "",
              "api": "40.2000",
              "cargoNominationId": 17375,
              "cargoId": 0,
              "colorCode": "",
              "correctedUllage": "",
              "correctionFactor": "",
              "fillingPercentage": "",
              "id": 296,
              "loadablePatternId": 4886,
              "observedBarrels": null,
              "observedBarrelsAt60": null,
              "observedM3": null,
              "rdgUllage": "",
              "tankId": 25590,
              "tankname": null,
              "temperature": "110.0000",
              "weight": "",
              "quantity": "15936.0000",
              "actualPlanned": "1",
              "arrivalDeparture": "2",
              "ullage": "1.3800",
              "active": false
          }
      ],
      "portLoadablePlanBallastDetails": [
          {
              "cargoId": 0,
              "colorCode": "",
              "correctedUllage": "",
              "correctionFactor": "",
              "fillingPercentage": "",
              "id": 517,
              "loadablePatternId": 4886,
              "rdgUllage": null,
              "tankId": 25597,
              "tankname": null,
              "temperature": "",
              "quantity": "2240.0000",
              "actualPlanned": "2",
              "arrivalDeparture": "2",
              "sounding": "7.6300",
              "active": false
          }
      ],
      "portLoadablePlanRobDetails": [
          {
              "id": 85,
              "loadablePatternId": 4886,
              "tankId": 25637,
              "tankname": null,
              "quantity": "200.0000",
              "actualPlanned": "2",
              "arrivalDeparture": "2",
              "active": false
          },
          {
              "id": 86,
              "loadablePatternId": 4886,
              "tankId": 25636,
              "tankname": null,
              "quantity": "200.0000",
              "actualPlanned": "2",
              "arrivalDeparture": "2",
              "active": false
          },
          {
              "id": 87,
              "loadablePatternId": 4886,
              "tankId": 25615,
              "tankname": null,
              "quantity": "1200.0000",
              "actualPlanned": "2",
              "arrivalDeparture": "2",
              "active": false
          },
          {
              "id": 88,
              "loadablePatternId": 4886,
              "tankId": 25614,
              "tankname": null,
              "quantity": "1200.0000",
              "actualPlanned": "2",
              "arrivalDeparture": "2",
              "active": false
          },
          {
              "id": 89,
              "loadablePatternId": 4886,
              "tankId": 25613,
              "tankname": null,
              "quantity": "1400.0000",
              "actualPlanned": "2",
              "arrivalDeparture": "2",
              "active": false
          },
          {
              "id": 90,
              "loadablePatternId": 4886,
              "tankId": 25612,
              "tankname": null,
              "quantity": "1400.0000",
              "actualPlanned": "2",
              "arrivalDeparture": "2",
              "active": false
          }
      ],
      "cargoQuantityDetails": [
          {
              "cargoId": 33,
              "cargoColor": "#f6ef0e",
              "cargoName": "",
              "cargoAbbrevation": "ARM",
              "cargoNominationId": 17375,
              "nominationTotal": 118729.4567,
              "maxTolerance": 10.0,
              "minTolerance": -10.0,
              "maxQuantity": 130602.40237,
              "minQuantity": 106856.51103,
              "plannedQuantityTotal": 0.0,
              "actualQuantityTotal": 81413.0,
              "blQuantityTotal": 22.0,
              "difference": 81391.0,
              "nominationApi": "29.2000",
              "nominationTemp": "105.0000",
              "actualAvgApi": "40.2",
              "actualAvgTemp": "110.0",
              "blAvgApi": "24.0",
              "blAvgTemp": "25.0"
          },
          {
              "cargoId": 32,
              "cargoColor": "#f91010",
              "cargoName": "",
              "cargoAbbrevation": "ARL",
              "cargoNominationId": 17376,
              "nominationTotal": 88692.8525,
              "maxTolerance": 10.0,
              "minTolerance": -10.0,
              "maxQuantity": 97562.13774999998,
              "minQuantity": 79823.56725,
              "plannedQuantityTotal": 0.0,
              "actualQuantityTotal": 0.0,
              "blQuantityTotal": 22.0,
              "difference": -22.0,
              "nominationApi": "33.0000",
              "nominationTemp": "120.0000",
              "actualAvgApi": "0.0",
              "actualAvgTemp": "0.0",
              "blAvgApi": "24.0",
              "blAvgTemp": "25.0"
          }
      ],
      "bunkerRearTanks": [
          [
              {
                  "id": 25636,
                  "categoryId": 3,
                  "categoryName": "Fresh Water Tank",
                  "name": "DRINKING WATER TANK",
                  "frameNumberFrom": "5",
                  "frameNumberTo": "13",
                  "shortName": "DWP",
                  "fullCapacityCubm": "369.4000",
                  "density": 1.0000,
                  "group": 1,
                  "order": 1,
                  "slopTank": false
              },
              {
                  "id": 25638,
                  "categoryId": 3,
                  "categoryName": "Fresh Water Tank",
                  "name": "DIST.WATER TANK",
                  "frameNumberFrom": "13",
                  "frameNumberTo": "15",
                  "shortName": "DSWP",
                  "fullCapacityCubm": "117.0000",
                  "density": 1.0000,
                  "group": 1,
                  "order": 2,
                  "slopTank": false
              },
              {
                  "id": 26003,
                  "categoryId": 23,
                  "categoryName": "Fresh Tank Void",
                  "name": "APT",
                  "frameNumberFrom": "0",
                  "frameNumberTo": "15",
                  "shortName": "APT1",
                  "group": 1,
                  "order": 3,
                  "slopTank": false
              },
              {
                  "id": 25640,
                  "categoryId": 3,
                  "categoryName": "Fresh Water Tank",
                  "name": "COOLING WATER TANK",
                  "frameNumberFrom": "10",
                  "frameNumberTo": "15",
                  "shortName": "FWC",
                  "fullCapacityCubm": "29.5000",
                  "density": 1.0000,
                  "group": 1,
                  "order": 3,
                  "slopTank": false
              },
              {
                  "id": 25639,
                  "categoryId": 3,
                  "categoryName": "Fresh Water Tank",
                  "name": "DIST.WATER TANK",
                  "frameNumberFrom": "13",
                  "frameNumberTo": "15",
                  "shortName": "DSWS",
                  "fullCapacityCubm": "117.0000",
                  "density": 1.0000,
                  "group": 1,
                  "order": 4,
                  "slopTank": false
              },
              {
                  "id": 25637,
                  "categoryId": 3,
                  "categoryName": "Fresh Water Tank",
                  "name": "FRESH WATER TANK",
                  "frameNumberFrom": "5",
                  "frameNumberTo": "13",
                  "shortName": "FWS",
                  "fullCapacityCubm": "369.4000",
                  "density": 1.0000,
                  "group": 1,
                  "order": 5,
                  "slopTank": false
              }
          ]
      ],
      "bunkerTanks": [
          [
              {
                  "id": 25614,
                  "categoryId": 5,
                  "categoryName": "Fuel Oil Tank",
                  "name": "NO.2  FUEL OIL TANK",
                  "frameNumberFrom": "15",
                  "frameNumberTo": "39",
                  "shortName": "FO2P",
                  "fullCapacityCubm": "1852.0000",
                  "density": 0.9800,
                  "group": 2,
                  "order": 1,
                  "slopTank": false
              },
              {
                  "id": 25612,
                  "categoryId": 5,
                  "categoryName": "Fuel Oil Tank",
                  "name": "NO.1  FUEL OIL TANK",
                  "frameNumberFrom": "39",
                  "frameNumberTo": "49",
                  "shortName": "FO1P",
                  "fullCapacityCubm": "2490.5000",
                  "density": 0.9800,
                  "group": 2,
                  "order": 2,
                  "slopTank": false
              },
              {
                  "id": 26004,
                  "categoryId": 22,
                  "categoryName": "Fuel Void",
                  "name": "FUEL VOID 1",
                  "frameNumberFrom": "15",
                  "frameNumberTo": "49",
                  "shortName": "VOID1",
                  "group": 2,
                  "order": 3,
                  "slopTank": false
              },
              {
                  "id": 26005,
                  "categoryId": 22,
                  "categoryName": "Fuel Void",
                  "name": "FUEL VOID 2",
                  "frameNumberFrom": "15",
                  "frameNumberTo": "43",
                  "shortName": "VOID2",
                  "group": 2,
                  "order": 4,
                  "slopTank": false
              },
              {
                  "id": 25616,
                  "categoryId": 5,
                  "categoryName": "Fuel Oil Tank",
                  "name": "H.F.O.SERV. TANK",
                  "frameNumberFrom": "43",
                  "frameNumberTo": "47",
                  "shortName": "FOSV",
                  "fullCapacityCubm": "66.8000",
                  "density": 0.9800,
                  "group": 2,
                  "order": 5,
                  "slopTank": false
              },
              {
                  "id": 25625,
                  "categoryId": 6,
                  "categoryName": "Diesel Oil Tank",
                  "name": "NO.2 D.O.SERV.TANK",
                  "frameNumberFrom": "22",
                  "frameNumberTo": "24",
                  "shortName": "DOSV2",
                  "fullCapacityCubm": "27.4000",
                  "density": 0.8500,
                  "group": 2,
                  "order": 6,
                  "slopTank": false
              },
              {
                  "id": 25624,
                  "categoryId": 6,
                  "categoryName": "Diesel Oil Tank",
                  "name": "NO.1 D.O. SERV.TANK",
                  "frameNumberFrom": "24",
                  "frameNumberTo": "26",
                  "shortName": "DOSV1",
                  "fullCapacityCubm": "32.4000",
                  "density": 0.8500,
                  "group": 2,
                  "order": 7,
                  "slopTank": false
              },
              {
                  "id": 25617,
                  "categoryId": 5,
                  "categoryName": "Fuel Oil Tank",
                  "name": "H.F.O.SETT. TANK",
                  "frameNumberFrom": "39",
                  "frameNumberTo": "43",
                  "shortName": "FOST",
                  "fullCapacityCubm": "64.1000",
                  "density": 0.9800,
                  "group": 2,
                  "order": 8,
                  "slopTank": false
              },
              {
                  "id": 25620,
                  "categoryId": 5,
                  "categoryName": "Fuel Oil Tank",
                  "name": "F.O.SLUDGE TANK",
                  "frameNumberFrom": "38",
                  "frameNumberTo": "42",
                  "shortName": "FOS",
                  "fullCapacityCubm": "5.5000",
                  "density": 0.9800,
                  "group": 2,
                  "order": 9,
                  "slopTank": false
              },
              {
                  "id": 25623,
                  "categoryId": 6,
                  "categoryName": "Diesel Oil Tank",
                  "name": "NO.2 DIESEL OIL TANK",
                  "frameNumberFrom": "15",
                  "frameNumberTo": "22",
                  "shortName": "DO2S",
                  "fullCapacityCubm": "278.1000",
                  "density": 0.8500,
                  "group": 2,
                  "order": 9,
                  "slopTank": false
              },
              {
                  "id": 25622,
                  "categoryId": 6,
                  "categoryName": "Diesel Oil Tank",
                  "name": "NO.1 DIESEL OIL TANK",
                  "frameNumberFrom": "22",
                  "frameNumberTo": "26",
                  "shortName": "DO1S",
                  "fullCapacityCubm": "175.2000",
                  "density": 0.8500,
                  "group": 2,
                  "order": 10,
                  "slopTank": false
              },
              {
                  "id": 25619,
                  "categoryId": 5,
                  "categoryName": "Fuel Oil Tank",
                  "name": "BOILER F.O.SERV.TANK",
                  "frameNumberFrom": "34",
                  "frameNumberTo": "39",
                  "shortName": "BFOSV",
                  "fullCapacityCubm": "67.2000",
                  "density": 0.9800,
                  "group": 2,
                  "order": 11,
                  "slopTank": false
              },
              {
                  "id": 25615,
                  "categoryId": 5,
                  "categoryName": "Fuel Oil Tank",
                  "name": "NO.2  FUEL OIL TANK",
                  "frameNumberFrom": "26",
                  "frameNumberTo": "39",
                  "shortName": "FO2S",
                  "fullCapacityCubm": "1259.2000",
                  "density": 0.9800,
                  "group": 2,
                  "order": 12,
                  "slopTank": false
              },
              {
                  "id": 25613,
                  "categoryId": 5,
                  "categoryName": "Fuel Oil Tank",
                  "name": "NO.1  FUEL OIL TANK",
                  "frameNumberFrom": "39",
                  "frameNumberTo": "49",
                  "shortName": "FO1S",
                  "fullCapacityCubm": "2154.3000",
                  "density": 0.9800,
                  "group": 2,
                  "order": 13,
                  "slopTank": false
              }
          ]
      ],
      "ballastFrontTanks": [
          [
              {
                  "id": 25611,
                  "categoryId": 2,
                  "categoryName": "Water Ballast Tank",
                  "name": "FORE PEAK TANK(PORT USE)",
                  "frameNumberFrom": "103",
                  "frameNumberTo": "FE",
                  "shortName": "UFPT",
                  "fullCapacityCubm": "5156.5000",
                  "density": 1.0250,
                  "group": 0,
                  "order": 1,
                  "slopTank": false
              },
              {
                  "id": 25597,
                  "categoryId": 2,
                  "categoryName": "Water Ballast Tank",
                  "name": "FORE PEAK TANK",
                  "frameNumberFrom": "103",
                  "frameNumberTo": "FE",
                  "shortName": "LFPT",
                  "fullCapacityCubm": "5444.2000",
                  "density": 1.0250,
                  "group": 0,
                  "order": 2,
                  "slopTank": false
              }
          ]
      ],
      "ballastCenterTanks": [
          [
              {
                  "id": 25608,
                  "categoryId": 2,
                  "categoryName": "Water Ballast Tank",
                  "name": "AFT WATER BALLAST TANK",
                  "frameNumberFrom": "15",
                  "frameNumberTo": "39",
                  "shortName": "AWBP",
                  "fullCapacityCubm": "1024.9000",
                  "density": 1.0250,
                  "group": 0,
                  "order": 1,
                  "slopTank": false
              },
              {
                  "id": 26006,
                  "categoryId": 16,
                  "categoryName": "Ballast Void",
                  "name": "VOID",
                  "frameNumberFrom": "39",
                  "frameNumberTo": "49",
                  "shortName": "VOID3",
                  "group": 0,
                  "order": 2,
                  "slopTank": false
              },
              {
                  "id": 25606,
                  "categoryId": 2,
                  "categoryName": "Water Ballast Tank",
                  "name": "NO.5 WATER BALLAST TANK",
                  "frameNumberFrom": "49",
                  "frameNumberTo": "61",
                  "shortName": "WB5P",
                  "fullCapacityCubm": "8561.9000",
                  "density": 1.0250,
                  "group": 0,
                  "order": 3,
                  "slopTank": false
              },
              {
                  "id": 25604,
                  "categoryId": 2,
                  "categoryName": "Water Ballast Tank",
                  "name": "NO.4 WATER BALLAST TANK",
                  "frameNumberFrom": "61",
                  "frameNumberTo": "71",
                  "shortName": "WB4P",
                  "fullCapacityCubm": "8743.7000",
                  "density": 1.0250,
                  "group": 0,
                  "order": 4,
                  "slopTank": false
              },
              {
                  "id": 25602,
                  "categoryId": 2,
                  "categoryName": "Water Ballast Tank",
                  "name": "NO.3 WATER BALLAST TANK",
                  "frameNumberFrom": "71",
                  "frameNumberTo": "81",
                  "shortName": "WB3P",
                  "fullCapacityCubm": "8875.8000",
                  "density": 1.0250,
                  "group": 0,
                  "order": 5,
                  "slopTank": false
              },
              {
                  "id": 25600,
                  "categoryId": 2,
                  "categoryName": "Water Ballast Tank",
                  "name": "NO.2 WATER BALLAST TANK",
                  "frameNumberFrom": "81",
                  "frameNumberTo": "91",
                  "shortName": "WB2P",
                  "fullCapacityCubm": "8873.6000",
                  "density": 1.0250,
                  "group": 0,
                  "order": 6,
                  "slopTank": false
              },
              {
                  "id": 25598,
                  "categoryId": 2,
                  "categoryName": "Water Ballast Tank",
                  "name": "NO.1 WATER BALLAST TANK",
                  "frameNumberFrom": "91",
                  "frameNumberTo": "103",
                  "shortName": "WB1P",
                  "fullCapacityCubm": "8906.5000",
                  "density": 1.0250,
                  "group": 0,
                  "order": 7,
                  "slopTank": false
              },
              {
                  "id": 25609,
                  "categoryId": 2,
                  "categoryName": "Water Ballast Tank",
                  "name": "AFT WATER BALLAST TANK",
                  "frameNumberFrom": "15",
                  "frameNumberTo": "39",
                  "shortName": "AWBS",
                  "fullCapacityCubm": "1024.9000",
                  "density": 1.0250,
                  "group": 0,
                  "order": 8,
                  "slopTank": false
              },
              {
                  "id": 26007,
                  "categoryId": 16,
                  "categoryName": "Ballast Void",
                  "name": "VOID",
                  "frameNumberFrom": "39",
                  "frameNumberTo": "49",
                  "shortName": "VOID4",
                  "group": 0,
                  "order": 9,
                  "slopTank": false
              },
              {
                  "id": 25607,
                  "categoryId": 2,
                  "categoryName": "Water Ballast Tank",
                  "name": "NO.5 WATER BALLAST TANK",
                  "frameNumberFrom": "49",
                  "frameNumberTo": "61",
                  "shortName": "WB5S",
                  "fullCapacityCubm": "8560.4000",
                  "density": 1.0250,
                  "group": 0,
                  "order": 10,
                  "slopTank": false
              },
              {
                  "id": 25605,
                  "categoryId": 2,
                  "categoryName": "Water Ballast Tank",
                  "name": "NO.4 WATER BALLAST TANK",
                  "frameNumberFrom": "61",
                  "frameNumberTo": "71",
                  "shortName": "WB4S",
                  "fullCapacityCubm": "8741.7000",
                  "density": 1.0250,
                  "group": 0,
                  "order": 11,
                  "slopTank": false
              },
              {
                  "id": 25603,
                  "categoryId": 2,
                  "categoryName": "Water Ballast Tank",
                  "name": "NO.3 WATER BALLAST TANK",
                  "frameNumberFrom": "71",
                  "frameNumberTo": "81",
                  "shortName": "WB3S",
                  "fullCapacityCubm": "8873.8000",
                  "density": 1.0250,
                  "group": 0,
                  "order": 12,
                  "slopTank": false
              },
              {
                  "id": 25601,
                  "categoryId": 2,
                  "categoryName": "Water Ballast Tank",
                  "name": "NO.2 WATER BALLAST TANK",
                  "frameNumberFrom": "81",
                  "frameNumberTo": "91",
                  "shortName": "WB2S",
                  "fullCapacityCubm": "8871.5000",
                  "density": 1.0250,
                  "group": 0,
                  "order": 13,
                  "slopTank": false
              },
              {
                  "id": 25599,
                  "categoryId": 2,
                  "categoryName": "Water Ballast Tank",
                  "name": "NO.1 WATER BALLAST TANK",
                  "frameNumberFrom": "91",
                  "frameNumberTo": "103",
                  "shortName": "WB1S",
                  "fullCapacityCubm": "8904.4000",
                  "density": 1.0250,
                  "group": 0,
                  "order": 14,
                  "slopTank": false
              }
          ]
      ],
      "ballastRearTanks": [
          [
              {
                  "id": 25610,
                  "categoryId": 2,
                  "categoryName": "Water Ballast Tank",
                  "name": "AFT PEAK TANK",
                  "frameNumberFrom": "AE",
                  "frameNumberTo": "15",
                  "shortName": "APT",
                  "fullCapacityCubm": "2574.4000",
                  "density": 1.0250,
                  "group": 0,
                  "order": 1,
                  "slopTank": false
              }
          ]
      ],
      "cargoTanks": [
          [
              {
                  "id": 25595,
                  "categoryId": 1,
                  "categoryName": "Cargo Tank",
                  "name": "SLOP TANK",
                  "frameNumberFrom": "49",
                  "frameNumberTo": "52",
                  "shortName": "SLP",
                  "fullCapacityCubm": "4117.3000",
                  "density": 1.3000,
                  "group": 1,
                  "order": 1,
                  "slopTank": false
              },
              {
                  "id": 25593,
                  "categoryId": 1,
                  "categoryName": "Cargo Tank",
                  "name": "NO.5 WING CARGO OIL TANK",
                  "frameNumberFrom": "52",
                  "frameNumberTo": "61",
                  "shortName": "5P",
                  "fullCapacityCubm": "17277.4000",
                  "density": 1.3000,
                  "group": 1,
                  "order": 2,
                  "slopTank": false
              },
              {
                  "id": 25584,
                  "categoryId": 1,
                  "categoryName": "Cargo Tank",
                  "name": "NO.5 CENTER CARGO OIL TANK",
                  "frameNumberFrom": "49",
                  "frameNumberTo": "61",
                  "shortName": "5C",
                  "fullCapacityCubm": "33725.1000",
                  "density": 1.3000,
                  "group": 1,
                  "order": 3,
                  "slopTank": false
              },
              {
                  "id": 25596,
                  "categoryId": 1,
                  "categoryName": "Cargo Tank",
                  "name": "SLOP TANK",
                  "frameNumberFrom": "49",
                  "frameNumberTo": "52",
                  "shortName": "SLS",
                  "fullCapacityCubm": "4117.3000",
                  "density": 1.3000,
                  "group": 1,
                  "order": 4,
                  "slopTank": false
              },
              {
                  "id": 25594,
                  "categoryId": 1,
                  "categoryName": "Cargo Tank",
                  "name": "NO.5 WING CARGO OIL TANK",
                  "frameNumberFrom": "52",
                  "frameNumberTo": "61",
                  "shortName": "5S",
                  "fullCapacityCubm": "17277.4000",
                  "density": 1.3000,
                  "group": 1,
                  "order": 5,
                  "slopTank": false
              }
          ],
          [
              {
                  "id": 25591,
                  "categoryId": 1,
                  "categoryName": "Cargo Tank",
                  "name": "NO.4 WING CARGO OIL TANK",
                  "frameNumberFrom": "61",
                  "frameNumberTo": "71",
                  "shortName": "4P",
                  "fullCapacityCubm": "20290.8000",
                  "density": 1.3000,
                  "group": 2,
                  "order": 1,
                  "slopTank": false
              },
              {
                  "id": 25583,
                  "categoryId": 1,
                  "categoryName": "Cargo Tank",
                  "name": "NO.4 CENTER CARGO OIL TANK",
                  "frameNumberFrom": "61",
                  "frameNumberTo": "71",
                  "shortName": "4C",
                  "fullCapacityCubm": "28201.6000",
                  "density": 1.3000,
                  "group": 2,
                  "order": 2,
                  "slopTank": false
              },
              {
                  "id": 25592,
                  "categoryId": 1,
                  "categoryName": "Cargo Tank",
                  "name": "NO.4 WING CARGO OIL TANK",
                  "frameNumberFrom": "61",
                  "frameNumberTo": "71",
                  "shortName": "4S",
                  "fullCapacityCubm": "20290.8000",
                  "density": 1.3000,
                  "group": 2,
                  "order": 3,
                  "slopTank": false
              }
          ],
          [
              {
                  "id": 25589,
                  "categoryId": 1,
                  "categoryName": "Cargo Tank",
                  "name": "NO.3 WING CARGO OIL TANK",
                  "frameNumberFrom": "71",
                  "frameNumberTo": "81",
                  "shortName": "3P",
                  "fullCapacityCubm": "20290.8000",
                  "density": 1.3000,
                  "group": 3,
                  "order": 1,
                  "slopTank": false
              },
              {
                  "id": 25582,
                  "categoryId": 1,
                  "categoryName": "Cargo Tank",
                  "name": "NO.3 CENTER CARGO OIL TANK",
                  "frameNumberFrom": "71",
                  "frameNumberTo": "81",
                  "shortName": "3C",
                  "fullCapacityCubm": "28201.6000",
                  "density": 1.3000,
                  "group": 3,
                  "order": 2,
                  "slopTank": false
              },
              {
                  "id": 25590,
                  "categoryId": 1,
                  "categoryName": "Cargo Tank",
                  "name": "NO.3 WING CARGO OIL TANK",
                  "frameNumberFrom": "71",
                  "frameNumberTo": "81",
                  "shortName": "3S",
                  "fullCapacityCubm": "20290.8000",
                  "density": 1.3000,
                  "group": 3,
                  "order": 3,
                  "slopTank": false
              }
          ],
          [
              {
                  "id": 25587,
                  "categoryId": 1,
                  "categoryName": "Cargo Tank",
                  "name": "NO.2 WING CARGO OIL TANK",
                  "frameNumberFrom": "81",
                  "frameNumberTo": "91",
                  "shortName": "2P",
                  "fullCapacityCubm": "20290.8000",
                  "density": 1.3000,
                  "group": 4,
                  "order": 1,
                  "slopTank": false
              },
              {
                  "id": 25581,
                  "categoryId": 1,
                  "categoryName": "Cargo Tank",
                  "name": "NO.2 CENTER CARGO OIL TANK",
                  "frameNumberFrom": "81",
                  "frameNumberTo": "91",
                  "shortName": "2C",
                  "fullCapacityCubm": "28201.6000",
                  "density": 1.3000,
                  "group": 4,
                  "order": 2,
                  "slopTank": false
              },
              {
                  "id": 25588,
                  "categoryId": 1,
                  "categoryName": "Cargo Tank",
                  "name": "NO.2 WING CARGO OIL TANK",
                  "frameNumberFrom": "81",
                  "frameNumberTo": "91",
                  "shortName": "2S",
                  "fullCapacityCubm": "20290.8000",
                  "density": 1.3000,
                  "group": 4,
                  "order": 3,
                  "slopTank": false
              }
          ],
          [
              {
                  "id": 25585,
                  "categoryId": 1,
                  "categoryName": "Cargo Tank",
                  "name": "NO.1  WING CARGO OIL TANK",
                  "frameNumberFrom": "91",
                  "frameNumberTo": "103",
                  "shortName": "1P",
                  "fullCapacityCubm": "20797.7000",
                  "density": 1.3000,
                  "group": 5,
                  "order": 1,
                  "slopTank": false
              },
              {
                  "id": 25580,
                  "categoryId": 1,
                  "categoryName": "Cargo Tank",
                  "name": "NO.1 CENTER CARGO OIL TANK",
                  "frameNumberFrom": "91",
                  "frameNumberTo": "103",
                  "shortName": "1C",
                  "fullCapacityCubm": "30229.5000",
                  "density": 1.3000,
                  "group": 5,
                  "order": 2,
                  "slopTank": false
              },
              {
                  "id": 25586,
                  "categoryId": 1,
                  "categoryName": "Cargo Tank",
                  "name": "NO.1  WING CARGO OIL TANK",
                  "frameNumberFrom": "91",
                  "frameNumberTo": "103",
                  "shortName": "1S",
                  "fullCapacityCubm": "20797.7000",
                  "density": 1.3000,
                  "group": 5,
                  "order": 3,
                  "slopTank": false
              }
          ]
      ]
  }
    return data;
  }
}



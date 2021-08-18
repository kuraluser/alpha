import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { DATATABLE_BUTTON, DATATABLE_FIELD_TYPE, IDataTableColumn } from '../../../shared/components/datatable/datatable.model';
import { QUANTITY_UNIT, ValueObject } from '../../../shared/models/common.model';
import { QuantityPipe } from '../../../shared/pipes/quantity/quantity.pipe';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';
import { ICargoQuantities, ILoadableQuantityCargo, IProtested, IShipCargoTank, OPERATIONS } from '../../core/models/common.model';
import { IPumpData, IPump, ILoadingRate, ISequenceData, ICargoStage } from '../loading-discharging-sequence-chart/loading-discharging-sequence-chart.model';
import { ICOWDetails, IDischargeOperationListData, IDischargingInformation, IDischargingInformationResponse, ILoadedCargo, ILoadingDischargingDelays, ILoadingSequenceDropdownData, ILoadingDischargingSequenceValueObject, IReasonForDelays } from '../models/loading-discharging.model';

/**
 * Transformation Service for Loading  and Discharging
 *
 * @export
 * @class LoadingDischargingTransformationService
 */
@Injectable()
export class LoadingDischargingTransformationService {
  private _loadingInformationSource: Subject<boolean> = new Subject();
  private _dischargingInformationSource: Subject<boolean> = new Subject();
  private _unitChangeSource: Subject<boolean> = new Subject();
  private _loadingInstructionSource: Subject<boolean> = new Subject();

  loadingInformationValidity$ = this._loadingInformationSource.asObservable();
  dischargingInformationValidity$ = this._dischargingInformationSource.asObservable();
  unitChange$ = this._unitChangeSource.asObservable();
  loadingInstructionValidity$ = this._loadingInstructionSource.asObservable();
  constructor(private quantityPipe: QuantityPipe) { }

  /** Set loading information complete status */
  setLoadingInformationValidity(isValid: boolean) {
    this._loadingInformationSource.next(isValid);
  }

  /**
   * Set discharging information complete status
   *
   * @param {boolean} isValid
   * @memberof LoadingDischargingTransformationService
   */
  setDischargingInformationValidity(isValid: boolean) {
    this._dischargingInformationSource.next(isValid);
  }

  /** Set unit changed */
  setUnitChanged(unitChanged: boolean) {
    this._unitChangeSource.next(unitChanged);
  }

  /** Set loading instruction complete status */
  setLoadingInstructionValidity(value: boolean) {
    this._loadingInstructionSource.next(value);
  }


  /**
  *  Set validation error message
  */
  setBerthValidationErrorMessage() {
    return {
      berth: {
        'duplicateBerth': 'LOADING_INFORMATION_BERTH_DUPLICATION',
        'berthRequired': 'LOADING_INFORMATION_BERTH_REQUIRED'
      },
      hoseConnections: {
        'maxlength': 'LOADING_DISCHARGING_BERTH_HOSECONNECTION_CHARACTER_LIMIT',
        'textError':"LOADING_BETH_HOSE_CONNECTION_ERROR"      
      },
      regulationAndRestriction: {
        'maxlength': 'LOADING_DISCHARGING_BERTH_REGULATION_RESTRICTION_CHARACTER_LIMIT'
      },
      itemsToBeAgreedWith: {
        'maxlength': 'LOADING_DISCHARGING_BERTH_ITEMS_TO_BE_AGREED_WITH_CHARACTER_LIMIT'
      },
      lineDisplacement: {
        'required': 'LOADING_DISCHARGING_BERTH_LINE_DIPLACEMENT_MIN',
        'min': 'LOADING_DISCHARGING_BERTH_LINE_DIPLACEMENT_MIN',
        'max': 'LOADING_DISCHARGING_BERTH_LINE_DIPLACEMENT_MAX'
      }
    }
  }


  /**
  *
  * Method to set validation message for loading rate
  * @return {*}
  * @memberof LoadingDischargingTransformationService
  */
  setValidationMessageForLoadingRate(unit: string = '') {
    return {
      maxLoadingRate: {
        'required': 'LOADING_RATE_REQUIRED',
        'failedCompare': 'MAX_LOADING_COMPARE',
        'min': `MIN_LOADING_RATE_MAX_${unit}`,
        'max': `MAX_LOADING_RATE_MAX_${unit}`
      },
      shoreLoadingRate: {
        'required': `MIN_SHORE_LOADING_RATE_${unit}`,
        'min': `MIN_SHORE_LOADING_RATE_${unit}`,
        'max': `MAX_SHORE_LOADING_RATE_${unit}`
      },
      minLoadingRate: {
        'required': 'LOADING_RATE_REQUIRED',
        'failedCompare': 'MIN_LOADING_COMPARE',
        'min': `MIN_LOADING_RATE_MIN_${unit}`,
        'max': `MAX_LOADING_RATE_MIN_${unit}`
      },
      minDeBallastingRate: {
        'required': 'LOADING_RATE_REQUIRED',
        'min': "MIN_DEBALLAST_MINIMUM",
        'max': "MIN_DEBALLAST_MAXIMUM"
      },
      maxDeBallastingRate: {
        'required': 'LOADING_RATE_REQUIRED',
        'min': "MAX_DEBALLAST_MINIMUM",
        'max': "MAX_DEBALLAST_MAXIMUM"
      },
      noticeTimeRateReduction: {
        'required': 'LOADING_RATE_REQUIRED',
        'min': 'LOADING_RATE_MIN_NOTICE_TIME_RATE_REDUCTION',
        'max': 'LOADING_RATE_MAX_NOTICE_TIME_RATE_REDUCTION'
      },
      noticeTimeStopLoading: {
        'required': 'LOADING_RATE_REQUIRED',
        'min': 'LOADING_RATE_MIN_NOTICE_TIME_RATE_STOP',
        'max': 'LOADING_RATE_MAX_NOTICE_TIME_RATE_STOP'
      }
    }
  }

  /**
* Method for setting loading manage grid columns
*
* @returns {IDataTableColumn[]}
* @memberof LoadingDischargingTransformationService
*/
  getLoadingDischargingDelayDatatableColumns(): IDataTableColumn[] {
    return [
      {
        field: 'slNo',
        header: 'SL',
        fieldType: DATATABLE_FIELD_TYPE.SLNO,
        fieldHeaderClass: 'column-sl',
        fieldClass: 'sl'
      },
      {
        field: 'cargo',
        header: 'Cargo',
        listName: 'loadableQuantityCargo',
        fieldType: DATATABLE_FIELD_TYPE.SELECT,
        fieldOptionLabel: 'grade',
        fieldPlaceholder: 'LOADING_MANAGE_SEQUENCE_SELCT_CARGO',
        errorMessages: {
          'required': 'LOADING_MANAGE_SEQUENCE_REQUIRED',
          'quantityExceeds': 'LOADING_MANAGE_SEQUENCE_QUANTITY_EXCEEDS',
          'duplicateCargo': 'LOADING_MANAGE_SEQUENCE_CARGO_DUPLICATE'
        }
      },
      {
        field: 'colorCode',
        header: '',
        fieldHeaderClass: 'column-cargo-sequence-color',
        fieldClass: 'manage-sequence-cargo-color',
        fieldType: DATATABLE_FIELD_TYPE.COLOR,

      },
      {
        field: 'quantity',
        header: 'QUANTITY'
      },
      {
        field: 'reasonForDelay',
        header: 'REASON FOR DELAY',
        listName: 'reasonForDelays',
        fieldType: DATATABLE_FIELD_TYPE.MULTISELECT,
        fieldOptionLabel: 'reason',
        fieldPlaceholder: 'LOADING_MANAGE_SEQUENCE_SELECT_REASON',
        errorMessages: {
          'required': 'LOADING_MANAGE_SEQUENCE_REQUIRED'
        }
      },
      {
        field: 'duration',
        header: 'DURATION',
        fieldType: DATATABLE_FIELD_TYPE.MASK,
        fieldPlaceholder: 'LOADING_MANAGE_SEQUENCE_ENTER_DURATION',
        maskFormat: '99:99',
        errorMessages: {
          'required': 'LOADING_MANAGE_SEQUENCE_REQUIRED',
          'invalidDuration': 'LOADING_MANAGE_SEQUENCE_DURATION_MAX'
        }
      },
      {
        field: 'buttons',
        header: '',
        fieldType: DATATABLE_FIELD_TYPE.BUTTON,
        fieldHeaderClass: 'column-actions',
        buttons: [
          { type: DATATABLE_BUTTON.DELETE_BUTTON, field: 'isDelete', icons: '', class: 'delete-icon', label: '', tooltip: 'Delete', tooltipPosition: "top" }
        ]
      }
    ]
  }

  /**
  * Method for converting loading sequence data to value object model
  *
  * @param {ILoadingDischargingDelays} loadingDischargingDelay
  * @param {boolean} [isNewValue=true]
  * @param {ILoadingSequenceDropdownData} listData
  * @returns {ILoadingDischargingSequenceValueObject}
  * @memberof LoadingDischargingTransformationService
  */
  getLoadingDischargingDelayAsValueObject(loadingDischargingDelay: ILoadingDischargingDelays, isNewValue = true, isEditable = true, listData: ILoadingSequenceDropdownData, prevUnit: QUANTITY_UNIT, currUnit: QUANTITY_UNIT): ILoadingDischargingSequenceValueObject {
    const _loadingDischargingDelay = <ILoadingDischargingSequenceValueObject>{};
    const reasonDelayObj: IReasonForDelays[] = listData?.reasonForDelays?.filter(reason => loadingDischargingDelay?.reasonForDelayIds?.includes(reason.id));
    const cargoObj: ILoadableQuantityCargo = listData?.loadableQuantityCargo?.find(loadable => loadable.cargoId === loadingDischargingDelay.cargoId);
    _loadingDischargingDelay.id = loadingDischargingDelay.id;
    let hourDuration = (loadingDischargingDelay.duration / 60).toString();
    hourDuration = hourDuration.includes('.') ? hourDuration.split('.')[0] : hourDuration;
    hourDuration = Number(hourDuration) < 10 ? ('0' + hourDuration) : hourDuration
    const minute = loadingDischargingDelay.duration % 60;
    const minuteDuration = minute <= 0 ?  '0' + minute : minute;
    _loadingDischargingDelay.duration = new ValueObject<string>(hourDuration + ':' + minuteDuration, true, isNewValue, false, true);
    _loadingDischargingDelay.quantity = this.quantityPipe.transform(loadingDischargingDelay.quantity, prevUnit, currUnit, cargoObj?.estimatedAPI);
    _loadingDischargingDelay.colorCode = cargoObj?.colorCode;
    _loadingDischargingDelay.cargo = new ValueObject<ILoadableQuantityCargo>(cargoObj, true, isEditable ? isNewValue : false, false, isEditable);
    _loadingDischargingDelay.reasonForDelay = new ValueObject<IReasonForDelays[]>(reasonDelayObj, true, isNewValue, false, true);
    _loadingDischargingDelay.isAdd = isNewValue;
    return _loadingDischargingDelay;
  }


  /**
  * Method for unit conversion in manage sequence table
  *
  * @param {ILoadingDischargingDelays} loadingDischargingDelay
  * @returns {string}
  * @memberof LoadingDischargingTransformationService
  */
  manageSequenceUnitConversion(value: number, loadingDischargingDelay: ILoadingDischargingSequenceValueObject, listData: ILoadingSequenceDropdownData, prevUnit: QUANTITY_UNIT, currUnit: QUANTITY_UNIT){
    const cargoObj: ILoadableQuantityCargo = listData?.loadableQuantityCargo?.find(loadable => loadable.cargoId === loadingDischargingDelay?.cargo?.value?.cargoId);
    return this.quantityPipe.transform(value, prevUnit, currUnit, cargoObj?.estimatedAPI);
  }


  /**
   * Method for converting from loading delay value object model
   *
   * @param {ILoadingDischargingSequenceValueObject} loadingDischargingDelayValueObject
   * @returns {ILoadingDischargingDelays}
   * @memberof LoadingDischargingTransformationService
   */
  getLoadingDischargingDelayAsValue(loadingDischargingDelayValueObject: ILoadingDischargingSequenceValueObject[], infoId: number, operation: OPERATIONS): ILoadingDischargingDelays[] {
    const loadingDischargingDelays: ILoadingDischargingDelays[] = [];
    loadingDischargingDelayValueObject.forEach((loadingValueObject) => {
      const _loadingDischargingDelays = <ILoadingDischargingDelays>{};
      _loadingDischargingDelays.id = loadingValueObject?.id;
      if (operation === OPERATIONS.LOADING) {
        _loadingDischargingDelays.loadingInfoId = infoId;
      } else {
        _loadingDischargingDelays.dischargingInfoId = infoId;
      }
      _loadingDischargingDelays.cargoId = loadingValueObject?.cargo?.value?.cargoId;
      _loadingDischargingDelays.reasonForDelayIds = loadingValueObject?.reasonForDelay?.value?.map(a => a.id) ?? [];
      _loadingDischargingDelays.quantity = loadingValueObject?.quantity;
      const minuteDuration = loadingValueObject?.duration?.value.split(':');
      _loadingDischargingDelays.duration = (Number(minuteDuration[0]) * 60) + Number(minuteDuration[1]);
      _loadingDischargingDelays.cargoNominationId = loadingValueObject?.cargo?.value?.cargoNominationId;
      loadingDischargingDelays.push(_loadingDischargingDelays);
    })
    return loadingDischargingDelays;
  }


  /**
   *
   * Method to set validation message for loadig details
   * @return {*}
   * @memberof LoadingDischargingTransformationService
  */
  setValidationMessageForLoadingDetails() {
    return {
      timeOfSunrise: {
        'required': 'LOADING_DETAILS_SUNRISE_REQUIRED',
        'failedCompare': 'LOADING_DETAILS_SUNRISE_FAILED_COMPARE'
      },
      timeOfSunset: {
        'required': 'LOADING_DETAILS_SUNSET_REQUIRED',
        'failedCompare': 'LOADING_DETAILS_SUNSET_FAILED_COMPARE'
      },
      startTime: {
        'required': 'LOADING_DETAILS_START_TIME_REQUIRED'
      },
      initialTrim: {
        'required': 'LOADING_DETAILS_INITIAL_TRIM_REQUIRED',
        'max': 'LOADING_DETAILS_INITIAL_TRIM_MAX'
      },
      maximumTrim: {
        'required': 'LOADING_DETAILS_MAX_TRIM_REQUIRED',
        'min': 'LOADING_DETAILS_MAX_TRIM_MIN',
        'max': 'LOADING_DETAILS_MAX_TRIM_MAX'
      },
      finalTrim: {
        'required': 'LOADING_DETAILS_FINAL_TRIM_REQUIRED',
        'max': 'LOADING_DETAILS_FINAL_TRIM_MAX'
      }
    }
  }


  /**
   * Method for setting cargo to be loaded grid columns
   *
   * @returns {IDataTableColumn[]}
   * @memberof LoadingDischargingTransformationService
   */
  getCargoToBeLoadedDatatableColumns(quantityUnit: QUANTITY_UNIT): IDataTableColumn[] {
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
       
      },
      {
        field: 'minMaxTolerance',
        header: 'LOADING_CARGO_TO_BE_LOADED_MIN_MAX_TOLERANCE'
      },
      {
        field: 'loadableMT',
        header: 'LOADING_CARGO_TO_BE_LOADED_SHIP_LOADABLE',
      
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
   * Method for setting cargo to be discharged grid columns
   *
   * @returns {IDataTableColumn[]}
   * @memberof LoadingDischargingTransformationService
   */
  getCargoToBeDischargedDatatableColumns(quantityUnit: QUANTITY_UNIT): IDataTableColumn[] {
    const quantityNumberFormat = AppConfigurationService.settings['quantityNumberFormat' + quantityUnit];

    return [
      {
        field: 'grade',
        header: 'DISCHARGING_CARGO_TO_BE_DISCHARGED_GRADE',
        fieldType: DATATABLE_FIELD_TYPE.BADGE,
        badgeColorField: 'colorCode'
      },
      {
        field: 'estimatedAPI',
        header: 'DISCHARGING_CARGO_TO_BE_DISCHARGED_API',
        numberFormat: '1.2-2'
      },
      {
        field: 'estimatedTemp',
        header: 'DISCHARGING_CARGO_TO_BE_DISCHARGED_TEMP',
        numberFormat: '1.2-2'
      },
      {
        field: 'loadingPortsLabel',
        header: 'DISCHARGING_CARGO_TO_BE_DISCHARGED_LOADING_PORT'
      },
      {
        field: 'maxDischargingRate',
        header: 'DISCHARGING_CARGO_TO_BE_DISCHARGED_MAX_DISCHARGE_RATE'
      },
      {
        field: 'blFigure',
        header: 'DISCHARGING_CARGO_TO_BE_DISCHARGED_BL_FIGURE'
      },
      {
        field: 'shipFigure',
        header: 'DISCHARGING_CARGO_TO_BE_DISCHARGED_SHIP_FIGURE'
      },
      {
        field: 'timeRequiredForDischarging',
        header: 'DISCHARGING_CARGO_TO_BE_DISCHARGED_DISCHARGE_TIME'
      },
      {
        field: 'protested',
        header: 'DISCHARGING_CARGO_TO_BE_DISCHARGED_IF_PROTESTED',
        fieldType: DATATABLE_FIELD_TYPE.SELECT,
        listName: 'protestedOptions',
        filterField: 'protested.value.name',
        fieldPlaceholder: 'DISCHARGING_SELECT_PROTESTED',
        fieldOptionLabel: 'name'
      },
      {
        field: 'slopQuantity',
        header: 'DISCHARGING_CARGO_TO_BE_DISCHARGED_SLOP_QUANTITY',
        numberFormat: quantityNumberFormat
      },
      {
        field: 'isCommingled',
        header: 'DISCHARGING_CARGO_TO_BE_DISCHARGED_COMMINGLED',
        fieldType: DATATABLE_FIELD_TYPE.CHECKBOX,
        filterField: 'protested.value'
      },
    ]
  }

  /**
  * Method for formatting ballast tanks data
  *
  * @param {IShipCargoTank} cargoTank
  * @param {ICargoQuantities[]} cargoTankQuantities
  * @returns {IShipCargoTank}
  * @memberof LoadingDischargingTransformationService
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

  /**
  * Method for setting cargo details grid
  *
  * @memberof LoadingDischargingTransformationService
  */
  getLoadingDischargingCargoDetailTableColumn() {
    const columns = [
      { field: 'abbreviation', header: 'LOADABLE_PLAN_CONDITION_GRADES' },
      { field: 'plannedWeight', header: 'LOADABLE_PLAN_CONDITION_PLANNED', fieldType: DATATABLE_FIELD_TYPE.NUMBER },
      { field: 'actualWeight', header: 'LOADABLE_PLAN_CONDITION_Actual', fieldType: DATATABLE_FIELD_TYPE.NUMBER },
      { field: 'difference', header: 'LOADABLE_PLAN_CARGO_CONDITION_DIFFERENCE', fieldType: DATATABLE_FIELD_TYPE.NUMBER }
    ];

    return columns;
  }

  /**
   * Set ballast pumbp gravity operation
   *
   * @param {IPumpData[]} ballastPumps
   * @param {IPumpData} gravity
   * @param {IPump[]} ballastPumpCategories
   * @return {*}
   * @memberof LoadingDischargingTransformationService
   */
  setBallastPumpGravity(ballastPumps: IPumpData[], gravity: IPumpData, ballastPumpCategories: IPump[]) {
    ballastPumpCategories.forEach(pump => {
      const data = {
        "pumpId": pump.id,
        "start": gravity.start,
        "end": gravity.end,
        "quantityM3": gravity.quantityM3,
        "rate": gravity.rate,
        "id": "gravity"
      }
      ballastPumps.push(data);
    });

    return ballastPumps;
  }

  /**
   * Set cargo loading rate data
   *
   * @param {*} stageTickPositions
   * @param {*} cargoLoadingRates
   * @return {*}
   * @memberof LoadingDischargingTransformationService
   */
  setCargoLoadingRate(stageTickPositions, cargoLoadingRates: ILoadingRate[]) {
    const _cargoLoadingRates = []
    for (let index = 0; index < stageTickPositions.length - 1; index++) {
      const start = stageTickPositions[index];
      const end = stageTickPositions[index + 1];
      const rate = new Set();
      cargoLoadingRates.forEach(element => {
        if ((element.startTime >= start && element.endTime <= end) || (start >= element.startTime && start < element.endTime) || (end > element.startTime && end <= element.endTime)) {
          element.loadingRates.forEach(rate.add, rate);
        }
      });

      _cargoLoadingRates.push(Array.from(rate));
    }
    return _cargoLoadingRates;
  }

  /**
   * Set tickpositions
   *
   * @param {*} minXAxisValue
   * @param {*} maxXAxisValue
   * @return {*}
   * @memberof LoadingDischargingTransformationService
   */
  setTickPositions(minXAxisValue, maxXAxisValue) {
    const lastMinute = (maxXAxisValue - minXAxisValue) / (60 * 1000);

    const totalHours = Math.trunc(lastMinute / 60) + 1;
    return [...Array(totalHours).keys()].map((item) => minXAxisValue + (item * 60 * 60 * 1000));
  }

  /**
   * Set plotline data for each stage
   *
   * @param {*} minXAxisValue
   * @param {*} maxXAxisValue
   * @param {*} stageInterval
   * @return {*}  {Highcharts.XAxisPlotLinesOptions[]}
   * @memberof LoadingDischargingTransformationService
   */
  setPlotLines(stageTickPositions: number[]): Highcharts.XAxisPlotLinesOptions[] {
    return stageTickPositions.map((item) => {
      return {
        value: item,
        color: '#000d20',
        width: 1,
        dashStyle: 'Dash',
        zIndex: 1
      }
    });
  }

  /**
   * Set cargo stage tickposition
   *
   * @param {ICargoStage[]} cargoStages
   * @return {*}
   * @memberof LoadingDischargingTransformationService
   */
  setCargoTickPositions(cargoStages: ICargoStage[]) {
    return [cargoStages[0].start, ...cargoStages.map((item) => item.end)];
  }

  /**
   * Transform sequence data
   *
   * @return {*}
   * @memberof LoadingDischargingTransformationService
   */
  transformSequenceData(sequenceData: ISequenceData) {
    sequenceData.cargoStageTickPositions = this.setCargoTickPositions(sequenceData.cargoStages);
    sequenceData.ballastPumps = this.setBallastPumpGravity(sequenceData.ballastPumps, sequenceData.gravity, sequenceData.ballastPumpCategories);
    sequenceData.stabilityParams = sequenceData.stabilityParams?.map(param => {
      param.data = param?.data?.map((value) => [value[0], Number(value[1])]);
      return param;
    });
    sequenceData.stagePlotLines = this.setPlotLines(sequenceData.stageTickPositions);
    sequenceData.tickPositions = this.setTickPositions(sequenceData.minXAxisValue, sequenceData.maxXAxisValue);
    sequenceData.cargoLoadingRates = this.setCargoLoadingRate(sequenceData.stageTickPositions, sequenceData.cargoLoadingRates);

    return sequenceData;
  }

  /**
   * Method for transforming discharge information data
   *
   * @param {IDischargingInformationResponse} dischargingInformationResponse
   * @param {IDischargeOperationListData} listData
   * @return {*}  {IDischargingInformation}
   * @memberof LoadingDischargingTransformationService
   */
  transformDischargingInformation(dischargingInformationResponse: IDischargingInformationResponse, listData: IDischargeOperationListData): IDischargingInformation {
    const dischargingInformation = <IDischargingInformation>{};
    dischargingInformation.dischargingInfoId = dischargingInformationResponse?.dischargingInfoId;
    dischargingInformation.synopticalTableId = dischargingInformationResponse?.synopticTableId;
    dischargingInformation.dischargingDetails = dischargingInformationResponse?.dischargingDetails;
    dischargingInformation.dischargingRates = dischargingInformationResponse?.dischargingRates;
    dischargingInformation.berthDetails = dischargingInformationResponse?.berthDetails;
    dischargingInformation.dischargingSequences = dischargingInformationResponse?.dischargingSequences;
    dischargingInformation.isDischargingInfoComplete = dischargingInformationResponse?.isDischargingInfoComplete;
    dischargingInformation.postDischargeStageTime = dischargingInformationResponse?.postDischargeStageTime;
    dischargingInformation.loadedCargos = dischargingInformationResponse?.loadedCargos;
    dischargingInformation.machineryInUses = dischargingInformationResponse?.machineryInUses;
    dischargingInformation.dischargingSequences.loadingDischargingDelays = dischargingInformationResponse.dischargingSequences.dischargingDelays;

    //Update tank list
    const cargoTanks = [];
    dischargingInformationResponse?.cargoVesselTankDetails?.cargoTanks.forEach(tanks => {
      tanks.forEach(tank => {
        cargoTanks.push(tank);
      });
    });
    dischargingInformation.cargoTanks = cargoTanks;

    //Update cow details
    const cowDetails = <ICOWDetails>{};
    cowDetails.cowOption = listData.cowOptions.find(option => option.id === dischargingInformationResponse?.cowDetails?.cowOption);
    cowDetails.cowPercentage = listData.cowPercentages.find(percentage => percentage.value === dischargingInformationResponse?.cowDetails?.cowPercentage);
    cowDetails.allCOWTanks = dischargingInformationResponse?.cowDetails?.allCOWTanks?.map(tank => dischargingInformation?.cargoTanks?.find(cargoTank => cargoTank.id === tank.id));
    cowDetails.topCOWTanks = dischargingInformationResponse?.cowDetails?.topCOWTanks?.map(tank => dischargingInformation?.cargoTanks?.find(cargoTank => cargoTank.id === tank.id));
    cowDetails.bottomCOWTanks = dischargingInformationResponse?.cowDetails?.bottomCOWTanks?.map(tank => dischargingInformation?.cargoTanks?.find(cargoTank => cargoTank.id === tank.id));
    cowDetails.cowEnd = dischargingInformationResponse?.cowDetails?.cowEnd;
    cowDetails.cowStart = dischargingInformationResponse?.cowDetails?.cowStart;
    cowDetails.cowDuration = dischargingInformationResponse?.cowDetails?.cowDuration;
    cowDetails.cowTrimMax = dischargingInformationResponse?.cowDetails?.cowTrimMax;
    cowDetails.cowTrimMin = dischargingInformationResponse?.cowDetails?.cowTrimMin;
    cowDetails.needFlushingOil = dischargingInformationResponse?.cowDetails?.needFlushingOil;
    cowDetails.needFreshCrudeStorage = dischargingInformationResponse?.cowDetails?.needFreshCrudeStorage;
    cowDetails.tanksWashingWithDifferentCargo = dischargingInformationResponse?.cowDetails?.tanksWashingWithDifferentCargo.map(cargoDetails => {
      cargoDetails.washingCargo = dischargingInformation.loadedCargos.find(cargo => cargo.id === cargoDetails?.washingCargo.id);
      return cargoDetails;
    });
    dischargingInformation.cowDetails = cowDetails;

    //Update cargon details
    const loadableQuantityCargoDetails: ILoadedCargo[] = dischargingInformationResponse?.cargoVesselTankDetails?.loadableQuantityCargoDetails.map(cargo => {
      const _cargo = <ILoadedCargo>{};
      _cargo.isAdd = true;
      for (const key in cargo) {
        if (Object.prototype.hasOwnProperty.call(cargo, key)) {
          if (key === 'protested') {
            const _protested = cargo.protested ? listData?.protestedOptions[0] : listData?.protestedOptions[1];
            _cargo.protested = new ValueObject<IProtested>(_protested, true, true, false);
          } else if (key === 'isCommingled') {
            const _isCommingled = cargo.isCommingled ?? false;
            _cargo.isCommingled = new ValueObject<boolean>(_isCommingled, true, true, false);
          } else {
            _cargo[key] = cargo[key];
          }
        }
      }
      return _cargo;
    });
    dischargingInformation.cargoVesselTankDetails = {
      cargoConditions: dischargingInformationResponse?.cargoVesselTankDetails?.cargoConditions,
      cargoQuantities: dischargingInformationResponse?.cargoVesselTankDetails?.cargoQuantities,
      cargoTanks: dischargingInformationResponse?.cargoVesselTankDetails?.cargoTanks,
      loadableQuantityCargoDetails
    }

    //Update stage details
    const stageDuration = dischargingInformationResponse?.dischargingStages.stageDurationList?.find(duration => duration.id === dischargingInformationResponse?.dischargingStages?.stageDuration);
    const stageOffset = dischargingInformationResponse?.dischargingStages.stageOffsetList?.find(offset => offset.id === dischargingInformationResponse?.dischargingStages?.stageOffset);
    dischargingInformation.dischargingStages = {
      trackStartEndStage: dischargingInformationResponse?.dischargingStages?.trackGradeSwitch,
      trackGradeSwitch: dischargingInformationResponse?.dischargingStages?.trackGradeSwitch,
      stageDuration,
      stageOffset
    };

    return dischargingInformation;
  }
}

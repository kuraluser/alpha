import { Injectable } from '@angular/core';
import { BehaviorSubject, Subject } from 'rxjs';
import * as moment from 'moment';
import { DATATABLE_BUTTON, DATATABLE_FIELD_TYPE, IDataTableColumn } from '../../../shared/components/datatable/datatable.model';
import { QUANTITY_UNIT, RATE_UNIT, ValueObject } from '../../../shared/models/common.model';
import { QuantityPipe } from '../../../shared/pipes/quantity/quantity.pipe';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';
import { ICargoQuantities, ILoadableQuantityCargo, IProtested, IShipCargoTank, OPERATIONS } from '../../core/models/common.model';
import { IPumpData, IPump, ILoadingRate, ISequenceData, ICargoStage } from '../loading-discharging-sequence-chart/loading-discharging-sequence-chart.model';
import { ICOWDetails, IDischargeOperationListData, IDischargingInformation, IDischargingInformationResponse, ILoadedCargo, ILoadingDischargingDelays, ILoadingSequenceDropdownData, ILoadingDischargingSequenceValueObject, IReasonForDelays } from '../models/loading-discharging.model';
import { QuantityDecimalFormatPipe } from '../../../shared/pipes/quantity-decimal-format/quantity-decimal-format.pipe';
import { OPERATION_TAB } from '../models/operations.model';
import { IValidationErrorMessagesSet } from '../../../shared/components/validation-error/validation-error.model';

/**
 * Transformation Service for Loading  and Discharging
 *
 * @export
 * @class LoadingDischargingTransformationService
 */
@Injectable()
export class LoadingDischargingTransformationService {
  public _loadingInformationSource: Subject<boolean> = new Subject();
  private _dischargingInformationSource: Subject<boolean> = new Subject();
  private _isDischargeStarted: BehaviorSubject<boolean> = new BehaviorSubject(false);
  private _unitChangeSource: Subject<boolean> = new Subject();
  public _loadingInstructionSource: Subject<boolean> = new Subject();
  public disableSaveButton: BehaviorSubject<boolean> = new BehaviorSubject(false);
  private _rateUnitChangeSource: Subject<boolean> = new Subject();
  private _tabChangeSource: Subject<OPERATION_TAB> = new Subject();
  private _validateUllageData: Subject<any> = new Subject();
  private _setUllageArrivalBtnStatus: Subject<any> = new Subject();
  private _setUllageDepartureBtnStatus: Subject<any> = new Subject();
  private _showUllageErrorPopup: Subject<boolean> = new Subject();
  public isLoadingInfoComplete: BehaviorSubject<boolean> = new BehaviorSubject(false);
  public isLoadingInstructionsComplete: BehaviorSubject<boolean> = new BehaviorSubject(false);
  public isLoadingPlanGenerated: BehaviorSubject<boolean> = new BehaviorSubject(false);
  public isLoadingSequenceGenerated: BehaviorSubject<boolean> = new BehaviorSubject(false);
  public inProcessing: BehaviorSubject<boolean> = new BehaviorSubject(false);
  public generateLoadingPlanButton: BehaviorSubject<boolean> = new BehaviorSubject(false);
  public disableViewErrorButton: BehaviorSubject<boolean> = new BehaviorSubject(false);
  

  loadingInformationValidity$ = this._loadingInformationSource.asObservable();
  dischargingInformationValidity$ = this._dischargingInformationSource.asObservable();
  unitChange$ = this._unitChangeSource.asObservable();
  loadingInstructionValidity$ = this._loadingInstructionSource.asObservable();
  rateUnitChange$ = this._rateUnitChangeSource.asObservable();
  tabChange$ = this._tabChangeSource.asObservable();
  validateUllageData$ = this._validateUllageData.asObservable();
  setUllageArrivalBtnStatus$ = this._setUllageArrivalBtnStatus.asObservable();
  setUllageDepartureBtnStatus$ = this._setUllageDepartureBtnStatus.asObservable();
  showUllageErrorPopup$ = this._showUllageErrorPopup.asObservable();
  isDischargeStarted$ = this._isDischargeStarted.asObservable();

  constructor(
    private quantityPipe: QuantityPipe,
    private quantityDecimalFormatPipe: QuantityDecimalFormatPipe) { }

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

  /**
   * Method to set discharge started status
   *
   * @param {boolean} status
   * @memberof LoadingDischargingTransformationService
   */
  isDischargeStarted(status: boolean) {
    this._isDischargeStarted.next(status);
  }

  /**
   * Set rate unit changed
   *
   * @param {boolean} rateUnitChanged
   * @memberof LoadingDischargingTransformationService
   */
  setRateUnitChanged(rateUnitChanged: boolean) {
    this._rateUnitChangeSource.next(rateUnitChanged);
  }

  /**
   * Set tab change event
   *
   * @param {OPERATION_TAB} tab
   * @memberof LoadingDischargingTransformationService
   */
  setTabChange(tab: OPERATION_TAB) {
    this._tabChangeSource.next(tab);
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
        'berthRequired': 'LOADING_INFORMATION_BERTH_REQUIRED',
        'invalidData': 'LOADING_INFO_INVALID_DATA'
      },
      maxShipDepth: {
        'required': 'LOADING_DISCHARGING_BERTH_REQUIRED',
        'min': 'LOADING_DISCHARGING_BERTH_DEPTH_MIN_ERROR',
        'max': 'LOADING_DISCHARGING_BERTH_DEPTH_MAX_ERROR',
      },
      maxManifoldHeight: {
        'required': 'LOADING_DISCHARGING_BERTH_REQUIRED',
        'min': 'LOADING_DISCHARGING_BERTH_MANIFOLD_HEIGHT_MIN_ERROR',
        'max': 'LOADING_DISCHARGING_BERTH_MANIFOLD_HEIGHT_MAX_ERROR'
      },
      maxManifoldPressure: {
        'required': 'LOADING_DISCHARGING_BERTH_REQUIRED',
        'min': 'LOADING_DISCHARGING_BERTH_MANIFOLD_HEIGHT_MIN_ERROR',
        'max': 'LOADING_DISCHARGING_BERTH_MANIFOLD_HEIGHT_AX_ERROR'
      },
      seaDraftLimitation: {
        'required': 'LOADING_DISCHARGING_BERTH_REQUIRED',
        'min': 'LOADING_DISCHARGING_BERTH_SEA_DRAFT_MIN_ERROR',
        'max': 'LOADING_DISCHARGING_BERTH_SEA_DRAFT_MAX_ERROR',
      },
      airDraftLimitation: {
        'required': 'LOADING_DISCHARGING_BERTH_REQUIRED',
        'min': 'LOADING_DISCHARGING_BERTH_AIR_DRAFT_MIN_ERROR',
        'max': 'LOADING_DISCHARGING_BERTH_AIR_DRAFT_MAX_ERROR',
      },
      hoseConnections: {
        'maxlength': 'LOADING_DISCHARGING_BERTH_HOSECONNECTION_CHARACTER_LIMIT',
        'textError': "LOADING_BETH_HOSE_CONNECTION_ERROR"
      },
      regulationAndRestriction: {
        'maxlength': 'LOADING_DISCHARGING_BERTH_REGULATION_RESTRICTION_CHARACTER_LIMIT',
        'pattern': 'LOADING_DISCHARGING_BERTH_REGULATION_RESTRICTION_CHARACTER_INVALID'
      },
      itemsToBeAgreedWith: {
        'maxlength': 'LOADING_DISCHARGING_BERTH_ITEMS_TO_BE_AGREED_WITH_CHARACTER_LIMIT',
        'pattern': 'LOADING_DISCHARGING_BERTH_ITEMS_TO_BE_AGREED_WITH_CHARACTER_INVALID',
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
   * Method to set validation messages for discharging rate fields
   *
   * @param {string} [unit='']
   * @return {*}
   * @memberof LoadingDischargingTransformationService
   */
  setValidationMessageForDischargingRate(unit: string = '') {
    return {
      maxDischargingRate: {
        'required': 'DISCHARGING_RATE_REQUIRED',
        'invalidNumber': 'DISCHARGING_RATE_INVALID',
        'failedCompare': 'MAX_DISCHARGING_RATE_COMPARE',
        'min': `MAX_DISCHARGING_RATE_MIN_VAL_${unit}`,
        'max': `MAX_DISCHARGING_RATE_MAX_VAL_${unit}`
      },
      initialDischargingRate: {
        'required': 'DISCHARGING_RATE_REQUIRED',
        'invalidNumber': 'DISCHARGING_RATE_INVALID',
        'failedCompare': 'INITIAL_DISCHARGING_RATE_COMPARE',
        'min': `INITIAL_DISCHARGING_RATE_MIN_${unit}`,
        'max': `INITIAL_DISCHARGING_RATE_MAX_${unit}`
      },
      minBallastingRate: {
        'required': 'DISCHARGING_RATE_REQUIRED',
        'invalidNumber': 'DISCHARGING_RATE_INVALID',
        'min': `MIN_BALLAST_MINIMUM_${unit}`,
        'max': `MIN_BALLAST_MAXIMUM_${unit}`
      },
      maxBallastingRate: {
        'required': 'DISCHARGING_RATE_REQUIRED',
        'invalidNumber': 'DISCHARGING_RATE_INVALID',
        'min': `MAX_DEBALLAST_MINIMUM_${unit}`,
        'max': `MAX_DEBALLAST_MAXIMUM_${unit}`
      }
    }
  }

  /**
* Method for setting loading manage grid columns
*
* @returns {IDataTableColumn[]}
* @memberof LoadingDischargingTransformationService
*/
  getLoadingDischargingDelayDatatableColumns(operation: OPERATIONS): IDataTableColumn[] {
    const columns: IDataTableColumn[] = [
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
        header: 'LOADING_MANAGE_SEQUENCE_QUANTITY',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        fieldPlaceholder: 'LOADING_MANAGE_SEQUENCE_ENTER_QUANTITY',
        numberType: 'quantity',
        errorMessages: {
          'required': 'LOADING_MANAGE_SEQUENCE_REQUIRED',
          'quantityExceeds': 'LOADING_MANAGE_SEQUENCE_QUANTITY_EXCEEDS',
          'min': 'LOADING_MANAGE_SEQUENCE_QUANTITY_MIN',
          'max': 'LOADING_MANAGE_SEQUENCE_QUANTITY_MAX',
          'invalidNumber': 'LOADING_MANAGE_SEQUENCE_INVALID'
        }
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
    ];

    if (operation === OPERATIONS.DISCHARGING) {
      const column: IDataTableColumn = {
        field: 'sequenceNo',
        header: 'DISCHARGING_MANAGE_SEQUENCE_SEQUENCE_NO',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        fieldPlaceholder: 'DISCHARGING_MANAGE_SEQUENCE_ENTER_SEQUENCE_NO',
        numberFormat: '1.0-0',
        errorMessages: {
          'required': 'DISCHARGING_MANAGE_SEQUENCE_REQUIRED',
          'invalidNumber': 'DISCHARGING_MANAGE_SEQUENCE_SEQUENCE_NO_INVALID',
          'invalidSequenceNumber': 'DISCHARGING_MANAGE_SEQUENCE_SEQUENCE_NO_INVALID_VALUE'
        }

      };
      columns.splice(1, 0, column);
    }

    return columns;
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
  getLoadingDischargingDelayAsValueObject(loadingDischargingDelay: ILoadingDischargingDelays, isNewValue = true, isEditable = true, listData: ILoadingSequenceDropdownData, prevUnit: QUANTITY_UNIT, currUnit: QUANTITY_UNIT, operation: OPERATIONS): ILoadingDischargingSequenceValueObject {
    const _loadingDischargingDelay = <ILoadingDischargingSequenceValueObject>{};
    const reasonDelayObj: IReasonForDelays[] = listData?.reasonForDelays?.filter(reason => loadingDischargingDelay?.reasonForDelayIds?.includes(reason.id));
    const cargoObj: ILoadableQuantityCargo = listData?.loadableQuantityCargo?.find(loadable => loadable.cargoId === loadingDischargingDelay.cargoId);
    _loadingDischargingDelay.id = loadingDischargingDelay.id;
    let hourDuration = (loadingDischargingDelay.duration / 60).toString();
    hourDuration = hourDuration.includes('.') ? hourDuration.split('.')[0] : hourDuration;
    hourDuration = Number(hourDuration) < 10 ? ('0' + hourDuration) : hourDuration
    const minute = loadingDischargingDelay.duration % 60;
    const minuteDuration = minute <= 0 ? '0' + minute : minute;
    _loadingDischargingDelay.duration = new ValueObject<string>(hourDuration + ':' + minuteDuration, true, isNewValue, false, true);

    if (loadingDischargingDelay.cargoId) {

      const loadableMT = this.quantityPipe.transform(cargoObj.loadableMT, QUANTITY_UNIT.MT, currUnit, cargoObj?.estimatedAPI, cargoObj?.estimatedTemp, -1);
      _loadingDischargingDelay.quantity = new ValueObject<number>(Number(loadableMT), true, operation === OPERATIONS.DISCHARGING && isNewValue && !loadingDischargingDelay?.isInitialDelay, false, operation === OPERATIONS.DISCHARGING && !loadingDischargingDelay?.isInitialDelay);
    } else {
      _loadingDischargingDelay.quantity = new ValueObject<number>(loadingDischargingDelay?.quantity, true, operation === OPERATIONS.DISCHARGING && isNewValue && !loadingDischargingDelay?.isInitialDelay, false, operation === OPERATIONS.DISCHARGING && !loadingDischargingDelay?.isInitialDelay);
    }
    _loadingDischargingDelay.colorCode = cargoObj?.colorCode;
    _loadingDischargingDelay.cargo = new ValueObject<ILoadableQuantityCargo>(cargoObj, true, isEditable && !loadingDischargingDelay?.isInitialDelay && isNewValue, false, isEditable && !loadingDischargingDelay?.isInitialDelay);
    _loadingDischargingDelay.reasonForDelay = new ValueObject<IReasonForDelays[]>(reasonDelayObj, true, isNewValue, false, true);
    _loadingDischargingDelay.isAdd = isNewValue;

    if (operation === OPERATIONS.DISCHARGING) {
      _loadingDischargingDelay.sequenceNo = new ValueObject<number>(loadingDischargingDelay?.isInitialDelay ? 1 : loadingDischargingDelay?.sequenceNo, true, !loadingDischargingDelay?.isInitialDelay && isNewValue, false, !loadingDischargingDelay?.isInitialDelay);
    }

    return _loadingDischargingDelay;
  }


  /**
  * Method for unit conversion in manage sequence table
  *
  * @param {ILoadingDischargingDelays} loadingDischargingDelay
  * @returns {string}
  * @memberof LoadingDischargingTransformationService
  */
  manageSequenceUnitConversion(value: number, loadingDischargingDelay: ILoadingDischargingSequenceValueObject, listData: ILoadingSequenceDropdownData, prevUnit: QUANTITY_UNIT, currUnit: QUANTITY_UNIT) {
    const cargoObj: ILoadableQuantityCargo = listData?.loadableQuantityCargo?.find(loadable => loadable.cargoId === loadingDischargingDelay?.cargo?.value?.cargoId);
    const loadableMT = this.quantityPipe.transform(cargoObj.loadableMT, QUANTITY_UNIT.MT, currUnit, cargoObj?.estimatedAPI, cargoObj?.estimatedTemp, -1);
    return loadableMT;
  }


  /**
   * Method for converting from loading delay value object model
   *
   * @param {ILoadingDischargingSequenceValueObject} loadingDischargingDelayValueObject
   * @returns {ILoadingDischargingDelays}
   * @memberof LoadingDischargingTransformationService
   */
  getLoadingDischargingDelayAsValue(loadingDischargingDelayValueObject: ILoadingDischargingSequenceValueObject[], infoId: number, operation: OPERATIONS, listData: ILoadingSequenceDropdownData): ILoadingDischargingDelays[] {
    const loadingDischargingDelays: ILoadingDischargingDelays[] = [];
    loadingDischargingDelayValueObject.forEach((loadingValueObject) => {
      const _loadingDischargingDelays = <ILoadingDischargingDelays>{};
      _loadingDischargingDelays.id = loadingValueObject?.id;
      if (operation === OPERATIONS.LOADING) {
        _loadingDischargingDelays.loadingInfoId = infoId;
      } else {
        _loadingDischargingDelays.dischargeInfoId = infoId;
      }
      _loadingDischargingDelays.cargoId = loadingValueObject?.cargo?.value?.cargoId;
      _loadingDischargingDelays.reasonForDelayIds = loadingValueObject?.reasonForDelay?.value?.map(a => a.id) ?? [];
      if (_loadingDischargingDelays.cargoId) {
        const cargoObj: ILoadableQuantityCargo = listData?.loadableQuantityCargo?.find(loadable => loadable.cargoId === _loadingDischargingDelays.cargoId);
        _loadingDischargingDelays.quantity = Number(cargoObj.loadableMT);
      } else {
        _loadingDischargingDelays.quantity = loadingValueObject?.quantity?.value;
      }
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
      },
      topOffTrim: {
        'required': 'LOADING_DETAILS_TOP_OFF_TRIM_REQUIRED',
        'max': 'LOADING_DETAILS_TOP_OFF_TRIM_MAX'
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
        numberFormat: '1.2-2',
        fieldColumnClass: 'text-right',
        fieldClass: 'text-right no-ediable-field'
      },
      {
        field: 'estimatedTemp',
        header: 'LOADING_CARGO_TO_BE_LOADED_TEMP',
        numberFormat: '1.2-2',
        fieldColumnClass: 'text-right',
        fieldClass: 'text-right no-ediable-field'
      },
      {
        field: 'maxLoadingRate',
        header: 'LOADING_CARGO_TO_BE_LOADED_MAX_LOADING_RATE',
        fieldColumnClass: 'text-right',
        fieldClass: 'text-right no-ediable-field'
      },
      {
        field: 'convertedOrderedQuantity',
        header: 'LOADING_CARGO_TO_BE_LOADED_NOMINATION',
        numberType: 'quantity',
        fieldColumnClass: 'text-right',
        fieldClass: 'text-right no-ediable-field'
      },
      {
        field: 'minMaxTolerance',
        header: 'LOADING_CARGO_TO_BE_LOADED_MIN_MAX_TOLERANCE',
        fieldColumnClass: 'text-right',
        fieldClass: 'text-right no-ediable-field'
      },
      {
        field: 'shipFigure',
        header: 'LOADING_CARGO_TO_BE_LOADED_SHIP_LOADABLE',
        numberType: 'quantity',
        fieldColumnClass: 'text-right',
        fieldClass: 'text-right no-ediable-field'

      },
      {
        field: 'differencePercentage',
        header: 'LOADING_CARGO_TO_BE_LOADED_DIFFERENCE',
        fieldColumnClass: 'text-right',
        fieldClass: 'text-right no-ediable-field'
      },
      {
        field: 'timeRequiredForLoading',
        header: 'LOADING_CARGO_TO_BE_LOADED_TIME_REQUIRED',
        fieldColumnClass: 'text-right',
        fieldClass: 'text-right no-ediable-field'
      },
      {
        field: 'convertedSlopQuantity',
        header: 'LOADING_CARGO_TO_BE_LOADED_SLOP_QTY',
        numberType: 'quantity',
        fieldColumnClass: 'text-right',
        fieldClass: 'text-right no-ediable-field'
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
        field: 'loadingPortsLabels',
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
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        numberType: 'quantity',
        numberFormat: quantityNumberFormat,
        errorMessages: {
          'required': 'DISCHARGING_CARGO_TO_BE_DISCHARGED_REQUIRED',
          'invalidNumber': 'DISCHARGING_CARGO_TO_BE_DISCHARGED_INVALID'
        }
      },
      {
        field: 'isCommingledDischarge',
        header: 'DISCHARGING_CARGO_TO_BE_DISCHARGED_COMMINGLED',
        fieldType: DATATABLE_FIELD_TYPE.CHECKBOX,
        filterField: 'isCommingledDischarge.value'
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
            const plannedWeight = this.quantityPipe.transform(cargoTank[groupIndex][tankIndex].commodity.plannedWeight, prevUnit, currUnit, cargoTankQuantities[index]?.api, cargoTankQuantities[index]?.temperature, -1);
            cargoTank[groupIndex][tankIndex].commodity.plannedWeight = plannedWeight ? Number(plannedWeight) : 0;
            const actualWeight = this.quantityPipe.transform(cargoTank[groupIndex][tankIndex].commodity.actualWeight, prevUnit, currUnit, cargoTankQuantities[index]?.api);
            cargoTank[groupIndex][tankIndex].commodity.actualWeight = actualWeight ? Number(actualWeight) : 0;
            cargoTank[groupIndex][tankIndex].commodity.volume = this.quantityPipe.transform(cargoTank[groupIndex][tankIndex].commodity.actualWeight, currUnit, QUANTITY_UNIT.OBSKL, cargoTank[groupIndex][tankIndex].commodity?.api, cargoTank[groupIndex][tankIndex].commodity?.temperature);
            cargoTank[groupIndex][tankIndex].commodity.fillingRatio = Number(cargoTank[groupIndex][tankIndex].commodity.fillingRatio);
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
    ballastPumpCategories?.forEach(pump => {
      const data = {
        "pumpId": pump.id,
        "start": gravity.start,
        "end": gravity.end,
        "quantityM3": gravity.quantityM3 ?? 0,
        "rate": gravity.rate ?? 0,
        "rateM3PerHr": gravity.rate ?? 0,
        "id": "gravity" + pump.pumpName // NB:- id must be unique
      }
      ballastPumps?.push(data);
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
    const _cargoLoadingRates = <ILoadingRate[]>[];
    for (let index = 0; index < stageTickPositions.length - 1; index++) {
      const start = stageTickPositions[index];
      const end = stageTickPositions[index + 1];
      const rate = new Set<number>();
      cargoLoadingRates.forEach(element => {
        if ((element.startTime >= start && element.endTime <= end) || (start >= element.startTime && start < element.endTime) || (end > element.startTime && end < element.endTime)) {
          element.loadingRates.forEach(rate.add, rate);
        }
      });
      const rateArr = Array.from(rate);
      _cargoLoadingRates.push({ startTime: start, endTime: end, loadingRatesM3PerHr: rateArr, loadingRates: rateArr });
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
    return cargoStages ? [cargoStages[0]?.start, ...cargoStages?.map((item) => item.end)] : [];
  }

  /**
   * Transform sequence data
   *
   * @return {*}
   * @memberof LoadingDischargingTransformationService
   */
  transformSequenceData(sequenceData: ISequenceData): ISequenceData {
    sequenceData.cargos = sequenceData?.cargos?.map(cargo => {
      cargo.quantityMT = cargo.quantity;
      return cargo;
    });
    sequenceData.cargoStages = sequenceData?.cargoStages?.map(stage => {
      stage.cargos = stage.cargos.map(cargo => {
        cargo.quantityMT = cargo.quantity;
        return cargo;
      });
      return stage;
    });
    sequenceData.cargoTankCategories = sequenceData?.cargoTankCategories?.map(tank => {
      tank.quantityMT = tank.quantity;
      return tank;
    })
    sequenceData.cargoStageTickPositions = this.setCargoTickPositions(sequenceData?.cargoStages);
    const ballastPumps = this.setBallastPumpGravity(sequenceData?.ballastPumps, sequenceData?.gravity, sequenceData?.ballastPumpCategories);
    sequenceData.ballastPumps = ballastPumps?.map(pump => {
      pump.rateM3PerHr = pump.rate;
      return pump;
    });
    sequenceData.cargoPumps = sequenceData?.cargoPumps?.map(pump => {
      pump.rateM3PerHr = pump.rate;
      return pump;
    });
    sequenceData.stabilityParams = sequenceData?.stabilityParams?.map(param => {
      param.data = param?.data?.map((value) => [value[0], Number(value[1])]);
      return param;
    });
    const flowRates = sequenceData?.flowRates?.map(tankFlowRate => {
      tankFlowRate.dataM3PerHr = [...tankFlowRate.data];

      return tankFlowRate;
    });
    sequenceData.flowRates = [...flowRates];
    sequenceData.stagePlotLines = this.setPlotLines(sequenceData?.stageTickPositions);
    sequenceData.tickPositions = this.setTickPositions(sequenceData?.minXAxisValue, sequenceData?.maxXAxisValue);
    sequenceData.cargoLoadingRates = this.setCargoLoadingRate(sequenceData?.stageTickPositions, sequenceData?.cargoLoadingRates);

    return { ...sequenceData };
  }

  /**
   * Method for transforming data to seleted unit in sequence charts
   *
   * @param {ISequenceData} sequenceData
   * @param {QUANTITY_UNIT} quantityUnitTo
   * @param {RATE_UNIT} [rateUnitTo=null]
   * @return {*}  {ISequenceData}
   * @memberof LoadingDischargingTransformationService
   */
  transformSequenceDataToSelectedUnit(sequenceData: ISequenceData, quantityUnitTo: QUANTITY_UNIT, rateUnitTo: RATE_UNIT = null): ISequenceData {
    sequenceData.cargos = sequenceData?.cargos?.map(cargo => {
      cargo.quantity = this.quantityPipe.transform(cargo.quantityMT, QUANTITY_UNIT.MT, quantityUnitTo, cargo?.api);
      return cargo;
    });
    sequenceData.cargoStages = sequenceData?.cargoStages?.map(stage => {
      stage.cargos = stage?.cargos?.map(cargo => {
        cargo.quantity = this.quantityPipe.transform(cargo?.quantityMT, QUANTITY_UNIT.MT, quantityUnitTo, cargo?.api);
        return cargo;
      });
      return stage;
    });
    sequenceData.cargoTankCategories = sequenceData?.cargoTankCategories?.map(tank => {
      tank.quantity = sequenceData?.cargos?.reduce((total, cargo) => total = tank.id !== cargo.tankId || total > cargo?.quantity ? total : cargo?.quantity, 0);
      return tank;
    });
    sequenceData.cargoLoadingRates = sequenceData?.cargoLoadingRates?.map(stage => {
      if (rateUnitTo === RATE_UNIT.BBLS_PER_HR) {
        stage.loadingRates = stage?.loadingRatesM3PerHr?.map(rate => {
          return rate * AppConfigurationService.settings.unitConversionConstant;
        });
      } else {
        stage.loadingRates = [...stage?.loadingRatesM3PerHr];
      }

      return stage;
    });

    const ballastPumps = sequenceData?.ballastPumps?.map(pump => {
      pump.rate = rateUnitTo === RATE_UNIT.BBLS_PER_HR ? pump.rateM3PerHr * AppConfigurationService.settings.unitConversionConstant : pump.rateM3PerHr;
      return pump;
    });
    sequenceData.ballastPumps = ballastPumps;

    sequenceData.cargoPumps = sequenceData?.cargoPumps?.map(pump => {
      pump.rate = rateUnitTo === RATE_UNIT.BBLS_PER_HR ? pump.rateM3PerHr * AppConfigurationService.settings.unitConversionConstant : pump.rateM3PerHr;
      return pump;
    });
    const flowRates = sequenceData?.flowRates?.map(tankFlowRate => {
      if (rateUnitTo === RATE_UNIT.BBLS_PER_HR) {
        tankFlowRate.data = tankFlowRate?.dataM3PerHr?.map(data => {
          return [data[0], Number((data[1] * AppConfigurationService.settings.unitConversionConstant).toFixed())];
        });
      } else {
        tankFlowRate.data = [...tankFlowRate.dataM3PerHr];
      }

      return tankFlowRate;
    });
    sequenceData.flowRates = flowRates;

    return { ...sequenceData };
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
    dischargingInformation.dischargeInfoId = dischargingInformationResponse?.dischargeInfoId;
    dischargingInformation.dischargeStudyName = dischargingInformationResponse?.dischargeStudyName;
    dischargingInformation.dischargeSlopTanksFirst = dischargingInformationResponse?.dischargeSlopTanksFirst;
    dischargingInformation.dischargeCommingledCargoSeperately = dischargingInformationResponse?.dischargeCommingledCargoSeperately;
    dischargingInformation.synopticalTableId = dischargingInformationResponse?.synopticTableId;
    dischargingInformation.dischargeDetails = dischargingInformationResponse?.dischargeDetails;
    dischargingInformation.dischargeRates = dischargingInformationResponse?.dischargeRates;
    dischargingInformation.berthDetails = dischargingInformationResponse?.berthDetails;
    dischargingInformation.dischargeSequences = dischargingInformationResponse?.dischargeSequences;
    dischargingInformation.isDischargeInfoComplete = dischargingInformationResponse?.isDischargeInfoComplete;
    dischargingInformation.isDischargeInstructionsComplete = dischargingInformationResponse?.isDischargeInstructionsComplete;
    dischargingInformation.isDischargeSequenceGenerated = dischargingInformationResponse?.isDischargeSequenceGenerated;
    dischargingInformation.isDischargePlanGenerated = dischargingInformationResponse?.isDischargePlanGenerated;
    dischargingInformation.postDischargeStageTime = dischargingInformationResponse?.postDischargeStageTime;
    dischargingInformation.loadedCargos = dischargingInformationResponse?.loadedCargos;
    dischargingInformation.dischargeSequences.loadingDischargingDelays = dischargingInformationResponse.dischargeSequences.dischargingDelays;

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
    cowDetails.totalDuration = dischargingInformationResponse?.cowDetails?.totalDuration;
    const totalDurationInMinutes = this.convertTimeStringToMinutes(dischargingInformationResponse?.cowDetails?.totalDuration);
    const startTimeInMinutes = this.convertTimeStringToMinutes(dischargingInformationResponse?.cowDetails?.cowStart);
    const endTimeInMinutes = this.convertTimeStringToMinutes(dischargingInformationResponse?.cowDetails?.cowEnd);
    const _duration = totalDurationInMinutes - startTimeInMinutes - endTimeInMinutes;
    cowDetails.cowDuration = moment.utc(_duration * 60 * 1000).format("HH:mm");
    cowDetails.cowTrimMax = dischargingInformationResponse?.cowDetails?.cowTrimMax;
    cowDetails.cowTrimMin = dischargingInformationResponse?.cowDetails?.cowTrimMin;
    cowDetails.needFlushingOil = dischargingInformationResponse?.cowDetails?.needFlushingOil;
    cowDetails.needFreshCrudeStorage = dischargingInformationResponse?.cowDetails?.needFreshCrudeStorage;
    cowDetails.washTanksWithDifferentCargo = dischargingInformationResponse?.cowDetails?.washTanksWithDifferentCargo;
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
          } else if (key === 'isCommingledDischarge') {
            const _isCommingled = cargo.isCommingledDischarge ?? false;
            _cargo.isCommingledDischarge = new ValueObject<boolean>(_isCommingled, true, true, false);
          } else if (key === 'slopQuantity') {
            const _slopQuantity = Number(cargo.slopQuantity) ?? 0;
            _cargo.slopQuantity = new ValueObject<number>(_slopQuantity, true, true, false);
          } else if(key === 'shipFigure') {
            _cargo.loadableMT = cargo.shipFigure;
          } else if (key === 'loadingPorts') {
            _cargo.loadingPortsLabels = cargo?.loadingPorts?.join(',');
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
    const stageDuration = dischargingInformationResponse?.dischargeStages.stageDurationList?.find(duration => duration.id === dischargingInformationResponse?.dischargeStages?.stageDuration);
    const stageOffset = dischargingInformationResponse?.dischargeStages.stageOffsetList?.find(offset => offset.id === dischargingInformationResponse?.dischargeStages?.stageOffset);
    dischargingInformation.dischargeStages = {
      trackStartEndStage: dischargingInformationResponse?.dischargeStages?.trackGradeSwitch,
      trackGradeSwitch: dischargingInformationResponse?.dischargeStages?.trackGradeSwitch,
      stageDuration,
      stageOffset,
      stageDurationList: dischargingInformationResponse?.dischargeStages?.stageDurationList,
      stageOffsetList: dischargingInformationResponse?.dischargeStages?.stageOffsetList
    };

    //Update machinery in use
    dischargingInformation.machineryInUses = {
      pumpTypes: dischargingInformationResponse?.machineryInUses?.pumpTypes,
      vesselPumps: dischargingInformationResponse?.machineryInUses?.vesselPumps,
      vesselManifold: dischargingInformationResponse?.machineryInUses?.vesselManifold,
      vesselBottomLine: dischargingInformationResponse?.machineryInUses?.vesselBottomLine,
      loadingDischargingMachinesInUses: dischargingInformationResponse?.machineryInUses?.dischargingMachinesInUses,
      machineTypes: dischargingInformationResponse?.machineryInUses?.machineTypes,
      tankTypes: dischargingInformationResponse?.machineryInUses?.tankTypes
    };

    return dischargingInformation;
  }

  /**
   * Cow validation messages
   *
   * @return {*}
   * @memberof LoadingDischargingTransformationService
   */
  setCOWValidationErrorMessage(): IValidationErrorMessagesSet {
    return {
      topCOWTanks: {
        'duplicateTanks': 'DISCHARGING_COW_TANK_PREFERENCE_DUPLICATION'
      },
      bottomCOWTanks: {
        'duplicateTanks': 'DISCHARGING_COW_TANK_PREFERENCE_DUPLICATION'
      },
      allCOWTanks: {
        'duplicateTanks': 'DISCHARGING_COW_TANK_PREFERENCE_DUPLICATION'
      },
      cowTrimMin: {
        'required': 'DISCHARGING_COW_REQUIRED',
        'min': 'Min',
        'max': 'Max'
      },
      cowTrimMax: {
        'required': 'DISCHARGING_COW_REQUIRED',
        'max': 'Max',
        'min': 'Min'
      },
      cowStart: {
        'required': 'DISCHARGING_COW_REQUIRED',
        'invalidDuration': 'DISCHARGING_COW_DURATION_MAX_ERROR'
      },
      cowEnd: {
        'required': 'DISCHARGING_COW_REQUIRED',
        'invalidDuration': 'DISCHARGING_COW_DURATION_MAX_ERROR'
      }
    }
  }

  /**
   * Set validationmessage for post discharge section
   *
   * @return {*}  {IValidationErrorMessagesSet}
   * @memberof LoadingDischargingTransformationService
   */
  setPostDischargeValidationErrorMessage(): IValidationErrorMessagesSet {
    return {
      dryCheckTime: {
        'invalidDuration': 'DISCHARGING_POST_DURATION_MAX'
      },
      slopDischargingTime: {
        'invalidDuration': 'DISCHARGING_POST_DURATION_MAX'
      },
      finalStrippingTime: {
        'invalidDuration': 'DISCHARGING_POST_DURATION_MAX'
      },
      freshOilWashingTime: {
        'invalidDuration': 'DISCHARGING_POST_DURATION_MAX'
      }
    }
  }

  /**
   * Converting time string to minutes
   *
   * @param {string} time
   * @return {*}
   * @memberof LoadingDischargingTransformationService
   */
  convertTimeStringToMinutes(time: string) {
    const timeArr = time.split(':');
    return Number(timeArr[0]) * 60 + Number(timeArr[1]);
  }

  /**
   * Method for validate ullage
   *
   * @memberof LoadingDischargingTransformationService
   */
  validateUllage(data){
    this._validateUllageData.next(data);
  }

  /**
   * Method for updating button status
   *
   * @memberof LoadingDischargingTransformationService
   */
  setUllageArrivalBtnStatus(value){
    this._setUllageArrivalBtnStatus.next(value);
  }

  /**
   * Method for updating button status
   *
   * @memberof LoadingDischargingTransformationService
   */
   setUllageDepartureBtnStatus(value){
    this._setUllageDepartureBtnStatus.next(value);
  }

  /**
   * Method for showing errors
   *
   * @memberof LoadingDischargingTransformationService
   */
  showUllageError(value){
    this._showUllageErrorPopup.next(value)
  }
}

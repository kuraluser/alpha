import { Injectable } from '@angular/core';
import { BehaviorSubject, Subject } from 'rxjs';
import * as moment from 'moment';
import { DATATABLE_BUTTON, DATATABLE_FIELD_TYPE, IDataTableColumn } from '../../../shared/components/datatable/datatable.model';
import { QUANTITY_UNIT, RATE_UNIT, ValueObject } from '../../../shared/models/common.model';
import { QuantityPipe } from '../../../shared/pipes/quantity/quantity.pipe';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';
import { ICargoQuantities, ILoadableQuantityCargo, IProtested, IShipCargoTank, ITank, OPERATIONS } from '../../core/models/common.model';
import { IPumpData, IPump, ILoadingRate, ISequenceData, ICargoStage, IEduction, ITankData, ITank as ISequenceTank, IDischargingRate, ICOW } from '../loading-discharging-sequence-chart/loading-discharging-sequence-chart.model';
import { ICOWDetails, IDischargeOperationListData, IDischargingInformation, IDischargingInformationResponse, ILoadedCargo, ILoadingDischargingDelays, ILoadingSequenceDropdownData, ILoadingDischargingSequenceValueObject, IReasonForDelays, ITanksWashingWithDifferentCargo, ITanksWashingWithDifferentCargoResponse, ILoadedCargoResponse } from '../models/loading-discharging.model';
import { QuantityDecimalFormatPipe } from '../../../shared/pipes/quantity-decimal-format/quantity-decimal-format.pipe';
import { OPERATION_TAB } from '../models/operations.model';
import { IValidationErrorMessagesSet } from '../../../shared/components/validation-error/validation-error.model';
import { TranslateService } from '@ngx-translate/core';

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
  private _dischargingInstructionSource: Subject<boolean> = new Subject();
  private _dischargingSequenceSource: Subject<boolean> = new Subject();
  private _dischargingPlanSource: Subject<boolean> = new Subject();
  private _disableDischargeInfoSave: Subject<boolean> = new Subject();
  private _disableDischargeGenerateButton: Subject<boolean> = new Subject();
  private _disableDischargePlanViewError: Subject<boolean> = new Subject();
  private _isDischargeStarted: BehaviorSubject<boolean> = new BehaviorSubject(false);
  private _unitChangeSource: Subject<boolean> = new Subject();
  public _loadingInstructionSource: Subject<boolean> = new Subject();
  public disableInfoInstructionRuleSave: BehaviorSubject<boolean> = new BehaviorSubject(false);
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
  dischargingInstructionValidity$ = this._dischargingInstructionSource.asObservable();
  dischargingSequenceValidity$ = this._dischargingSequenceSource.asObservable();
  dischargingPlanValidity$ = this._dischargingPlanSource.asObservable();
  disableDischargeInfoSave$ = this._disableDischargeInfoSave.asObservable();
  disableDischargePlanGenerate$ = this._disableDischargeGenerateButton.asObservable();
  disableDischargePlanViewError$ = this._disableDischargePlanViewError.asObservable();
  unitChange$ = this._unitChangeSource.asObservable();
  loadingInstructionValidity$ = this._loadingInstructionSource.asObservable();
  rateUnitChange$ = this._rateUnitChangeSource.asObservable();
  tabChange$ = this._tabChangeSource.asObservable();
  validateUllageData$ = this._validateUllageData.asObservable();
  setUllageArrivalBtnStatus$ = this._setUllageArrivalBtnStatus.asObservable();
  setUllageDepartureBtnStatus$ = this._setUllageDepartureBtnStatus.asObservable();
  showUllageErrorPopup$ = this._showUllageErrorPopup.asObservable();
  isDischargeStarted$ = this._isDischargeStarted.asObservable();

  portRotationId: number;
  isMachineryValid: boolean;
  isCargoAdded: boolean;

  constructor(
    private quantityPipe: QuantityPipe,
    private quantityDecimalFormatPipe: QuantityDecimalFormatPipe
  ) { }

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

  /**
   * Set discharging instruction complete status
   *
   * @param {boolean} isValid
   * @memberof LoadingDischargingTransformationService
   */
  setDischargingInstructionValidity(isValid: boolean) {
    this._dischargingInstructionSource.next(isValid);
  }

  /**
   * Set discharging sequence complete status
   *
   * @param {boolean} isValid
   * @memberof LoadingDischargingTransformationService
   */
  setDischargingSequenceValidity(isValid: boolean) {
    this._dischargingSequenceSource.next(isValid);
  }

  /**
   * Set discharging plan complete status
   *
   * @param {boolean} isValid
   * @memberof LoadingDischargingTransformationService
   */
  setDischargingPlanValidity(isValid: boolean) {
    this._dischargingPlanSource.next(isValid);
  }

  /**
   * Disable / enable discharge information save button
   *
   * @param {boolean} isDisable
   * @memberof LoadingDischargingTransformationService
   */
  disableDischargeInfoSave(isDisable: boolean) {
    this._disableDischargeInfoSave.next(isDisable);
  }

  /**
   * Disable / enable discharge plan generate button
   *
   * @param {boolean} isDisable
   * @memberof LoadingDischargingTransformationService
   */
  disableGenerateDischargePlanBtn(isDisable: boolean) {
    this._disableDischargeGenerateButton.next(isDisable);
  }

  /**
   * Disable / enable discharge plan View error button
   *
   * @param {boolean} isDisable
   * @memberof LoadingDischargingTransformationService
   */
  disableDischargePlanViewErrorBtn(isDisable: boolean) {
    this._disableDischargePlanViewError.next(isDisable);
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
        'min': 'LOADING_DISCHARGING_BERTH_MANIFOLD_PRESSURE_MIN_ERROR',
        'max': 'LOADING_DISCHARGING_BERTH_MANIFOLD_PRESSURE_MAX_ERROR'
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
        'min': `MIN_DEBALLAST_MINIMUM_${unit}`,
        'max': `MIN_DEBALLAST_MAXIMUM_${unit}`
      },
      maxDeBallastingRate: {
        'required': 'LOADING_RATE_REQUIRED',
        'min': `MAX_DEBALLAST_MINIMUM_${unit}`,
        'max': `MAX_DEBALLAST_MAXIMUM_${unit}`
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
      minBallastRate: {
        'required': 'DISCHARGING_RATE_REQUIRED',
        'invalidNumber': 'DISCHARGING_RATE_INVALID',
        'failedCompare': 'MIN_BALLAST_RATE_COMPARE',
        'min': `MIN_BALLAST_MINIMUM_${unit}`,
        'max': `MIN_BALLAST_MAXIMUM_${unit}`
      },
      maxBallastRate: {
        'required': 'DISCHARGING_RATE_REQUIRED',
        'invalidNumber': 'DISCHARGING_RATE_INVALID',
        'failedCompare': 'MAX_BALLAST_RATE_COMPARE',
        'min': `MAX_BALLAST_MINIMUM_${unit}`,
        'max': `MAX_BALLAST_MAXIMUM_${unit}`
      }
    }
  }

  /**
* Method for setting loading manage grid columns
*
* @returns {IDataTableColumn[]}
* @memberof LoadingDischargingTransformationService
*/
  getLoadingDischargingDelayDatatableColumns(operation: OPERATIONS, totalDuration: number, translateService: TranslateService): IDataTableColumn[] {


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
        fieldOptionLabel: 'cargoAbbreviation',
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
        maxSelectedLabels: 2,
        multiSelectShowTooltip: true,
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
          'invalidDuration': operation === OPERATIONS.LOADING ? 'LOADING_MANAGE_SEQUENCE_DURATION_MAX' : `${translateService.instant('DISCHARGING_MANAGE_SEQUENCE_DURATION_MAX')} ${totalDuration}Hrs`
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
    let cargoObj: ILoadableQuantityCargo;
    cargoObj = listData?.loadableQuantityCargo?.find(loadable => loadable.cargoNominationId === loadingDischargingDelay.cargoNominationId);
    _loadingDischargingDelay.id = loadingDischargingDelay.id;
    let hourDuration = (loadingDischargingDelay.duration / 60).toString();
    hourDuration = hourDuration.includes('.') ? hourDuration.split('.')[0] : hourDuration;
    hourDuration = Number(hourDuration) < 10 ? ('0' + hourDuration) : hourDuration
    const minute = loadingDischargingDelay.duration % 60;
    const minuteDuration = minute <= 0 ? '0' + minute : minute;
    _loadingDischargingDelay.duration = new ValueObject<string>(hourDuration + ':' + minuteDuration, true, isNewValue, false, true);

    if (loadingDischargingDelay.cargoId) {
      _loadingDischargingDelay.quantityMT = operation === OPERATIONS.DISCHARGING ? loadingDischargingDelay?.quantity : cargoObj.loadableMT;
      const loadableMT = this.quantityPipe.transform(operation === OPERATIONS.DISCHARGING ? loadingDischargingDelay?.quantity : cargoObj.loadableMT, QUANTITY_UNIT.MT, currUnit, cargoObj?.estimatedAPI, cargoObj?.estimatedTemp, operation === OPERATIONS.LOADING ? -1 : null);
      _loadingDischargingDelay.quantity = new ValueObject<number>(Number(loadableMT), true, operation === OPERATIONS.DISCHARGING && isNewValue && !loadingDischargingDelay?.isInitialDelay, false, operation === OPERATIONS.DISCHARGING && !loadingDischargingDelay?.isInitialDelay);
    } else {
      _loadingDischargingDelay.quantityMT = loadingDischargingDelay?.quantity;
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
    const cargoObj: ILoadableQuantityCargo = listData?.loadableQuantityCargo?.find(loadable => loadable.cargoNominationId === loadingDischargingDelay?.cargo?.value?.cargoNominationId);
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
        _loadingDischargingDelays.sequenceNo = Number(loadingValueObject?.sequenceNo.value);
      }
      _loadingDischargingDelays.cargoId = loadingValueObject?.cargo?.value?.cargoId;
      _loadingDischargingDelays.reasonForDelayIds = loadingValueObject?.reasonForDelay?.value?.map(a => a.id) ?? [];
      if (_loadingDischargingDelays.cargoId) {
        const cargoObj: ILoadableQuantityCargo = listData?.loadableQuantityCargo?.find(loadable => loadable.cargoNominationId === loadingValueObject?.cargo?.value?.cargoNominationId);
        _loadingDischargingDelays.quantity = Number(cargoObj.loadableMT);
      } else {
        _loadingDischargingDelays.quantity = loadingValueObject?.quantity?.value;
      }
      if (operation === OPERATIONS.DISCHARGING) {
        _loadingDischargingDelays.quantity = Number(loadingValueObject?.quantityMT);
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
      commonDate: {
        'required': 'LOADING_DETAILS_DATE_REQUIRED_ERROR',
        'notInRange': 'LOADING_DETAILS_DATE_NOT_IN_RANGE'
      },
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
        'max': 'LOADING_DETAILS_INITIAL_TRIM_MAX',
        'min': 'LOADING_DETAILS_INITIAL_TRIM_MIN',
      },
      maximumTrim: {
        'required': 'LOADING_DETAILS_MAX_TRIM_REQUIRED',
        'min': 'LOADING_DETAILS_MAX_TRIM_MIN',
        'max': 'LOADING_DETAILS_MAX_TRIM_MAX'
      },
      finalTrim: {
        'required': 'LOADING_DETAILS_FINAL_TRIM_REQUIRED',
        'max': 'LOADING_DETAILS_FINAL_TRIM_MAX',
        'min': 'LOADING_DETAILS_FINAL_TRIM_MIN',
      },
      strippingTrim: {
        'required': 'LOADING_DETAILS_TOP_OFF_TRIM_REQUIRED',
        'max': 'LOADING_DETAILS_TOP_OFF_TRIM_MAX',
        'min': 'LOADING_DETAILS_TOP_OFF_TRIM_MIN',
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
        field: 'cargoAbbreviation',
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
        numberFormat: '1.0-0',
        fieldClass: 'text-right no-ediable-field'
      },
      {
        field: 'convertedOrderedQuantity',
        header: 'LOADING_CARGO_TO_BE_LOADED_NOMINATION',
        numberFormat: quantityNumberFormat,
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
        numberFormat: quantityNumberFormat,
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
        numberFormat: quantityNumberFormat,
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
        field: 'cargoAbbreviation',
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
        numberFormat: '1.0-0',
        header: 'DISCHARGING_CARGO_TO_BE_DISCHARGED_MAX_DISCHARGE_RATE'
      },
      {
        field: 'blFigure',
        header: 'DISCHARGING_CARGO_TO_BE_DISCHARGED_BL_FIGURE',
        numberFormat: quantityNumberFormat,
        fieldColumnClass: 'text-right',
        fieldClass: 'text-right no-ediable-field'
      },
      {
        field: 'shipFigure',
        header: 'DISCHARGING_CARGO_TO_BE_DISCHARGED_SHIP_FIGURE',
        numberFormat: quantityNumberFormat,
        fieldColumnClass: 'text-right',
        fieldClass: 'text-right no-ediable-field'
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
        numberFormat: quantityNumberFormat,
        errorMessages: {
          'required': 'DISCHARGING_CARGO_TO_BE_DISCHARGED_REQUIRED',
          'min': 'DISCHARGING_CARGO_TO_BE_DISCHARGED_SLOP_QUANTITY_MIN_ERROR',
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
    return typeof value === 'string' ? Number(value?.replace(/,/g, '')) : value;
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
   * Set ballast/cargo eduction operation
   *
   * @param {ITankData[]} tankData
   * @param {IEduction[]} eduction
   * @param {ISequenceTank[]} tankCategories
   * @param {string} type
   * @return {*}
   * @memberof LoadingDischargingTransformationService
   */
  setEduction(tankData: ITankData[], eduction: IEduction[], tankCategories: ISequenceTank[], type: string) {
    eduction?.forEach(item => {
      item?.tanks?.forEach(tankId => {
        const _tank = tankCategories.find(tank => tank?.id === tankId);
        const data: ITankData = {
          tankId: tankId,
          start: item.timeStart,
          end: item.timeEnd,
          quantity: 0,
          quantityMT: 0,
          sounding: 0,
          ullage: 0,
          id: `${type}-stripping-${_tank.tankName}` // NB:- id must be unique
        }
        tankData?.push(data);
      });
    });

    return tankData;
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
        "id": "gravity-" + pump.pumpName // NB:- id must be unique
      }
      ballastPumps?.push(data);
    });

    return ballastPumps;
  }

  /**
   * Set COW tanks in cargo sequence
   *
   * @param {ITankData[]} cargos
   * @param {ICOW} cleaningTanks
   * @return {*}  {ITankData[]}
   * @memberof LoadingDischargingTransformationService
   */
  setCargoCOW(cargos: ITankData[], cleaningTanks: ICOW): ITankData[] {
    for (const key in cleaningTanks) {
      if (Object.prototype.hasOwnProperty.call(cleaningTanks, key)) {
        const tanks: ITankData[] = cleaningTanks[key];
        let className;
        switch (key) {
          case 'bottomTanks':
            className = 'bottom-wash';
            break;

          case 'fullTanks':
            className = 'full-wash';
            break;

          case 'topTanks':
            className = 'top-wash';
            break;

          default:
            break;
        }
        if (className) {
          tanks?.forEach(tank => {
            const data = {
              "tankId": tank?.tankId,
              "start": tank?.start,
              "end": tank?.end,
              "className": className,
              "color": 'transparent',
              "id": "cow-" + tank?.tankName // NB:- id must be unique
            }
            cargos?.push(data);
          });
        }

      }
    }

    return cargos;
  }

  /**
   * Set cargo loading rate data
   *
   * @param {*} stageTickPositions
   * @param {*} cargoRates
   * @return {*}
   * @memberof LoadingDischargingTransformationService
   */
  setCargoRate(stageTickPositions, cargoRates: ILoadingRate[] | IDischargingRate[], type: OPERATIONS) {
    const _cargoLoadingRates: ILoadingRate[] = [];
    const _cargoDischargingRates: IDischargingRate[] = [];
    for (let index = 0; index < stageTickPositions.length - 1; index++) {
      const start = stageTickPositions[index];
      const end = stageTickPositions[index + 1];
      const rate = new Set<number>();
      cargoRates?.forEach(element => {
        if ((element.startTime >= start && element.endTime <= end) || (start >= element.startTime && start < element.endTime) || (end > element.startTime && end <= element.endTime)) {
          if (type === OPERATIONS.LOADING) {
            element.loadingRates.forEach(rate.add, rate);
          } else {
            element.dischargingRates.forEach(rate.add, rate);
          }
        }
      });
      const rateArr = Array.from(rate);
      if (rateArr.length > 2) {
        rateArr.splice(1, rateArr?.length / 2);
      }
      if (type === OPERATIONS.LOADING) {
        _cargoLoadingRates.push({ startTime: start, endTime: end, loadingRatesM3PerHr: rateArr, loadingRates: rateArr });
      } else {
        _cargoDischargingRates.push({ startTime: start, endTime: end, dischargingRatesM3PerHr: rateArr, dischargingRates: rateArr });
      }

    }
    return type === OPERATIONS.LOADING ? _cargoLoadingRates : _cargoDischargingRates;
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
    sequenceData.cargos = this.setEduction(sequenceData?.cargos, sequenceData?.cargoEduction, sequenceData.cargoTankCategories, 'cargo');
    sequenceData.cargos = this.setCargoCOW(sequenceData?.cargos, sequenceData?.cleaningTanks);

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
    sequenceData.cargoLoadingRates = <ILoadingRate[]>this.setCargoRate(sequenceData?.stageTickPositions, sequenceData?.cargoLoadingRates, OPERATIONS.LOADING);
    sequenceData.cargoDischargingRates = <IDischargingRate[]>this.setCargoRate(sequenceData?.stageTickPositions, sequenceData?.cargoDischargingRates, OPERATIONS.DISCHARGING);
    sequenceData.ballasts = this.setEduction(sequenceData?.ballasts, sequenceData?.ballastEduction, sequenceData.ballastTankCategories, 'ballast');
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
      cargo.quantity = this.quantityPipe.transform(cargo.quantityMT, QUANTITY_UNIT.MT, quantityUnitTo, cargo?.api, -1);
      return cargo;
    });
    sequenceData.cargoStages = sequenceData?.cargoStages?.map(stage => {
      stage.cargos = stage?.cargos?.map(cargo => {
        cargo.quantity = this.quantityPipe.transform(cargo?.quantityMT, QUANTITY_UNIT.MT, quantityUnitTo, cargo?.api) ?? 0;
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

    sequenceData.cargoDischargingRates = sequenceData?.cargoDischargingRates?.map(stage => {
      if (rateUnitTo === RATE_UNIT.BBLS_PER_HR) {
        stage.dischargingRates = stage?.dischargingRatesM3PerHr?.map(rate => {
          return rate * AppConfigurationService.settings.unitConversionConstant;
        });
      } else {
        stage.dischargingRates = [...stage?.dischargingRatesM3PerHr];
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
        tankFlowRate.data = tankFlowRate?.dataM3PerHr?.map(data => {
          return [data[0], Number((data[1]).toFixed())];
        });
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
    dischargingInformation.dischargeInfoStatusId = dischargingInformationResponse?.dischargeInfoStatusId;
    dischargingInformation.dischargeStudyName = dischargingInformationResponse?.dischargeStudyName;
    dischargingInformation.dischargeSlopTanksFirst = dischargingInformationResponse?.dischargeSlopTanksFirst;
    dischargingInformation.dischargeCommingledCargoSeparately = dischargingInformationResponse?.dischargeCommingledCargoSeparately;
    dischargingInformation.synopticalTableId = dischargingInformationResponse?.synopticTableId;
    dischargingInformation.dischargeRates = dischargingInformationResponse?.dischargeRates;
    dischargingInformation.berthDetails = dischargingInformationResponse?.berthDetails;
    dischargingInformation.dischargeSequences = dischargingInformationResponse?.dischargeSequences;
    dischargingInformation.isDischargeInfoComplete = dischargingInformationResponse?.isDischargeInfoComplete;
    dischargingInformation.isDischargeInstructionsComplete = dischargingInformationResponse?.isDischargeInstructionsComplete;
    dischargingInformation.isDischargeSequenceGenerated = dischargingInformationResponse?.isDischargeSequenceGenerated;
    dischargingInformation.isDischargePlanGenerated = dischargingInformationResponse?.isDischargePlanGenerated;
    dischargingInformation.dischargeSequences.loadingDischargingDelays = dischargingInformationResponse.dischargeSequences.dischargingDelays;

    // Updating post discharge time
    dischargingInformation.postDischargeStageTime = {
      dryCheckTime: this.convertMinutesToHHMM(Number(dischargingInformationResponse?.postDischargeStageTime?.dryCheckTime)) ?? null,
      slopDischargingTime: this.convertMinutesToHHMM(Number(dischargingInformationResponse?.postDischargeStageTime?.slopDischargingTime)) ?? null,
      finalStrippingTime: this.convertMinutesToHHMM(Number(dischargingInformationResponse?.postDischargeStageTime?.finalStrippingTime)) ?? null,
      freshOilWashingTime: this.convertMinutesToHHMM(Number(dischargingInformationResponse?.postDischargeStageTime?.freshOilWashingTime)) ?? null
    };

    // Update discharging details

    dischargingInformation.dischargeDetails = dischargingInformationResponse?.dischargeDetails;
    dischargingInformation.dischargeDetails.trimAllowed.initialTrim = dischargingInformationResponse?.dischargeDetails?.trimAllowed?.initialTrim ? dischargingInformationResponse?.dischargeDetails?.trimAllowed?.initialTrim : 0;
    dischargingInformation.dischargeDetails.trimAllowed.strippingTrim = dischargingInformationResponse?.dischargeDetails?.trimAllowed?.finalTrim;

    //Update tank list
    const cargoTanks = [];
    dischargingInformationResponse?.cargoVesselTankDetails?.cargoTanks.forEach(tanks => {
      tanks.forEach(tank => {
        cargoTanks.push(tank);
      });
    });
    dischargingInformation.cargoTanks = cargoTanks;
    const dischargeQuantityCargoDetails = [];
    dischargingInformationResponse?.cargoVesselTankDetails?.dischargeQuantityCargoDetails?.map(item => {
      if (item.shipFigure) {
        dischargeQuantityCargoDetails.push(item);
      }
    });

    //Update cargo to be discharged details
    const loadableQuantityCargoDetails: ILoadedCargo[] = this.getCargoToBeDischargedAsValueObject(dischargeQuantityCargoDetails, listData);

    dischargingInformation.cargoVesselTankDetails = {
      cargoConditions: dischargingInformationResponse?.cargoVesselTankDetails?.cargoConditions,
      cargoQuantities: dischargingInformationResponse?.cargoVesselTankDetails?.cargoQuantities,
      cargoTanks: dischargingInformationResponse?.cargoVesselTankDetails?.cargoTanks,
      loadableQuantityCargoDetails
    }

    // Update all cargos now available in the vessel in  selected port
    dischargingInformation.loadedCargos = [...new Map(dischargingInformationResponse?.cargoVesselTankDetails?.cargoQuantities?.map(item => [item['abbreviation'], { id: item?.cargoId, cargoNominationId: item.dischargeCargoNominationId, colorCode: item?.colorCode, abbreviation: item?.abbreviation }])).values()].filter(item => item?.abbreviation);

    //Update cow details
    dischargingInformation.cowDetails = this.setCowDetails(dischargingInformationResponse, listData, dischargingInformation);

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
      loadingDischargingMachinesInUses: dischargingInformationResponse?.machineryInUses?.dischargeMachinesInUses,
      machineTypes: dischargingInformationResponse?.machineryInUses?.machineTypes,
      tankTypes: dischargingInformationResponse?.machineryInUses?.tankTypes
    };

    return dischargingInformation;
  }

  /**
   * Convert cargo to be discharge details value object to value
   *
   * @param {ILoadedCargo[]} dischargeQuantityCargoDetails
   * @param {IDischargeOperationListData} listData
   * @return {*}  {ILoadedCargoResponse[]}
   * @memberof LoadingDischargingTransformationService
   */
  getCargoToBeDischargedAsValue(dischargeQuantityCargoDetails: ILoadedCargo[], listData: IDischargeOperationListData, currentQuantitySelectedUnit: QUANTITY_UNIT): ILoadedCargoResponse[] {
    const _dischargeQuantityCargoDetails: ILoadedCargoResponse[] = dischargeQuantityCargoDetails?.map(cargo => {
      const _cargo = <ILoadedCargoResponse>{};
      for (const key in cargo) {
        if (Object.prototype.hasOwnProperty.call(cargo, key)) {
          if (key === 'protested') {
            _cargo.protested = listData.protestedOptions.findIndex(item => item.id === cargo[key].value.id) === 0 ? true : false;
          } else if (key === 'isCommingledDischarge') {
            _cargo.isCommingledDischarge = cargo[key].value;
          } else if (key === 'slopQuantity') {
            _cargo.slopQuantity = cargo?.slopQuantity ? this.quantityPipe.transform((<ValueObject>cargo[key]).value, currentQuantitySelectedUnit, QUANTITY_UNIT.MT, cargo?.estimatedAPI, cargo?.estimatedTemp, -1) : 0;
          } else {
            _cargo[key] = cargo[key];
          }
        }
      }
      return _cargo;
    });
    return _dischargeQuantityCargoDetails;
  }

  /**
   * Convert cargo to be discharged details to value object
   *
   * @param {ILoadedCargoResponse[]} dischargeQuantityCargoDetails
   * @param {IDischargeOperationListData} listData
   * @return {*}  {ILoadedCargo[]}
   * @memberof LoadingDischargingTransformationService
   */
  getCargoToBeDischargedAsValueObject(dischargeQuantityCargoDetails: ILoadedCargoResponse[], listData: IDischargeOperationListData): ILoadedCargo[] {
    const loadableQuantityCargoDetails: ILoadedCargo[] = dischargeQuantityCargoDetails?.map(cargo => {
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
            _cargo.slopQuantityMT = _slopQuantity.toString();
            _cargo.slopQuantity = new ValueObject<number>(_slopQuantity, true, true, false);
          } else if (key === 'shipFigure') {
            _cargo.loadableMT = cargo.shipFigure;
          } else if (key === 'loadingPorts') {
            _cargo.loadingPortsLabels = cargo?.loadingPorts?.join(',');
          } else if (key === 'dischargeCargoNominationId') {
            _cargo.cargoNominationId = cargo?.dischargeCargoNominationId;
          } else {
            _cargo[key] = cargo[key];
          }
        }
      }
      return _cargo;
    });
    return loadableQuantityCargoDetails;
  }

  /**
   * Set cow plan details in discharge information
   *
   * @param {IDischargingInformationResponse} dischargingInformationResponse
   * @param {IDischargeOperationListData} listData
   * @param {IDischargingInformation} dischargingInformation
   * @return {*}  {ICOWDetails}
   * @memberof LoadingDischargingTransformationService
   */
  setCowDetails(dischargingInformationResponse: IDischargingInformationResponse, listData: IDischargeOperationListData, dischargingInformation: IDischargingInformation): ICOWDetails {
    const cowDetails = <ICOWDetails>{};
    cowDetails.cowOption = listData.cowOptions.find(option => option.id === dischargingInformationResponse?.cowPlan?.cowOption);
    cowDetails.cowPercentage = listData.cowPercentages.find(percentage => percentage.value === Number(dischargingInformationResponse?.cowPlan?.cowPercentage));
    cowDetails.enableDayLightRestriction = dischargingInformationResponse?.cowPlan?.enableDayLightRestriction;

    cowDetails.allCOWTanks = dischargingInformationResponse?.cowPlan?.allCow?.map(tankId => dischargingInformation?.cargoTanks?.find(cargoTank => cargoTank.id === tankId));
    cowDetails.topCOWTanks = dischargingInformationResponse?.cowPlan?.topCow?.map(tankId => dischargingInformation?.cargoTanks?.find(cargoTank => cargoTank.id === tankId));
    cowDetails.bottomCOWTanks = dischargingInformationResponse?.cowPlan?.bottomCow?.map(tankId => dischargingInformation?.cargoTanks?.find(cargoTank => cargoTank.id === tankId));

    const totalDurationInMinutes = dischargingInformation?.cargoVesselTankDetails?.loadableQuantityCargoDetails?.reduce((total, cargo) => total += Number(cargo?.timeRequiredForDischarging), 0) * 60;
    cowDetails.totalDuration = this.convertMinutesToHHMM(totalDurationInMinutes) ?? '00:00';
    cowDetails.cowEnd = this.convertMinutesToHHMM(Number(dischargingInformationResponse?.cowPlan?.cowEnd)) ?? null;
    cowDetails.cowStart = this.convertMinutesToHHMM(Number(dischargingInformationResponse?.cowPlan?.cowStart)) ?? null;
    const startTimeInMinutes = Number(dischargingInformationResponse?.cowPlan?.cowStart);
    const endTimeInMinutes = Number(dischargingInformationResponse?.cowPlan?.cowEnd);
    const _duration = totalDurationInMinutes ? totalDurationInMinutes - startTimeInMinutes - endTimeInMinutes : null;

    cowDetails.cowDuration = this.convertMinutesToHHMM(Number(Number(dischargingInformationResponse?.cowPlan?.cowDuration))) ?? null;

    cowDetails.cowTrimMax = dischargingInformationResponse?.cowPlan?.cowTrimMax;
    cowDetails.cowTrimMin = dischargingInformationResponse?.cowPlan?.cowTrimMin;

    cowDetails.needFlushingOilAndCrudeStorage = dischargingInformationResponse?.cowPlan?.needFlushingOilAndCrudeStorage;

    cowDetails.washTanksWithDifferentCargo = dischargingInformationResponse?.cowPlan?.washTanksWithDifferentCargo;
    const tanksWashingWithDifferentCargo: ITanksWashingWithDifferentCargo[] = dischargingInformation?.cargoVesselTankDetails?.loadableQuantityCargoDetails?.map(cargo => {
      const tanks: ITank[] = [];
      dischargingInformation?.cargoVesselTankDetails?.cargoQuantities?.forEach(tank => {
        if (tank?.dischargeCargoNominationId === cargo?.cargoNominationId) {
          tanks.push(dischargingInformation?.cargoTanks.find(tankObj => tankObj.id === tank?.tankId));
        }
      });
      return {
        cargo: { ...cargo, abbreviation: cargo?.cargoAbbreviation },
        washingCargo: null,
        tanks: null,
        selectedTanks: tanks
      };
    });

    const emptyTanks: ITank[] = [];
    dischargingInformation?.cargoVesselTankDetails?.cargoQuantities?.forEach(tank => {
      if (tank?.plannedWeight === 0) {
        emptyTanks.push(dischargingInformation?.cargoTanks.find(tankObj => tankObj.id === tank?.tankId));
      }
    });

    if (emptyTanks?.length) {
      tanksWashingWithDifferentCargo.push({
        cargo: { id: 0, abbreviation: 'NIL', cargoNominationId: 0 },
        washingCargo: null,
        tanks: null,
        selectedTanks: emptyTanks
      });
    }

    cowDetails.tanksWashingWithDifferentCargo = tanksWashingWithDifferentCargo?.map(cargoDetails => {
      const _cargoDetails: ITanksWashingWithDifferentCargoResponse = dischargingInformationResponse?.cowPlan?.cargoCow?.find(cargoObj => cargoObj?.cargoNominationId === cargoDetails?.cargo?.cargoNominationId);
      cargoDetails.washingCargo = dischargingInformation.loadedCargos.find(cargo => cargo.cargoNominationId === _cargoDetails?.washingCargoNominationId);
      cargoDetails.tanks = cargoDetails.selectedTanks?.filter(tank => _cargoDetails?.tankIds?.includes(tank?.id));
      return cargoDetails;
    });

    return cowDetails;
  }

  /**
   * Cow validation messages
   *
   * @return {*}
   * @memberof LoadingDischargingTransformationService
   */
  setCOWValidationErrorMessage(): IValidationErrorMessagesSet {
    return {
      cowOption: {
        'required': 'DISCHARGING_COW_REQUIRED',
      },
      cowPercentage: {
        'required': 'DISCHARGING_COW_REQUIRED',
      },
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
        'min': 'DISCHARGING_COW_TRIM_MIN_START',
        'max': 'DISCHARGING_COW_TRIM_MIN_END',
        'invalidNumber': 'DISCHARGING_COW_TRIM_INVALID'
      },
      cowTrimMax: {
        'required': 'DISCHARGING_COW_REQUIRED',
        'min': 'DISCHARGING_COW_TRIM_MAX_START',
        'max': 'DISCHARGING_COW_TRIM_MAX_END',
        'invalidNumber': 'DISCHARGING_COW_TRIM_INVALID'
      },
      cowStart: {
        'required': 'DISCHARGING_COW_REQUIRED',
        'invalidDuration': 'DISCHARGING_COW_DURATION_MAX_ERROR',
        'invalidTime': 'DISCHARGING_COW_START_DURATION_TIME'
      },
      cowEnd: {
        'required': 'DISCHARGING_COW_REQUIRED',
        'invalidDuration': 'DISCHARGING_COW_DURATION_MAX_ERROR',
        'invalidTime': 'DISCHARGING_COW_END_DURATION_TIME'
      },
      washingTank: {
        'required': 'DISCHARGING_WASHING_TANK_REQUIRED',
      },
      washingCargo: {
        'required': 'DISCHARGING_WASHING_CARGO_REQUIRED',
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
    const timeArr = time?.split(':');
    return timeArr?.length === 2 ? Number(timeArr[0].replace(/_/g, '')) * 60 + Number(timeArr[1].replace(/_/g, '')) : null;
  }

  /**
   * Method for validate ullage
   *
   * @memberof LoadingDischargingTransformationService
   */
  validateUllage(data) {
    this._validateUllageData.next(data);
  }

  /**
   * Method for updating button status
   *
   * @memberof LoadingDischargingTransformationService
   */
  setUllageArrivalBtnStatus(value) {
    this._setUllageArrivalBtnStatus.next(value);
  }

  /**
   * Method for updating button status
   *
   * @memberof LoadingDischargingTransformationService
   */
  setUllageDepartureBtnStatus(value) {
    this._setUllageDepartureBtnStatus.next(value);
  }

  /**
   * Method for showing errors
   *
   * @memberof LoadingDischargingTransformationService
   */
  showUllageError(value) {
    this._showUllageErrorPopup.next(value)
  }

  /**
   * Convert minutes to HH:MM format
   *
   * @param {number} minutes
   * @return {*}  {string}
   * @memberof LoadingDischargingTransformationService
   */
  convertMinutesToHHMM(minutes: number): string {
    return `0${Math.floor(minutes / 60)}`.slice(-2) + ':' + ('0' + minutes % 60).slice(-2)
  }

  /**
* Method for setting commingle details grid columns
*
* @returns {IDataTableColumn[]}
* @memberof LoadingDischargingTransformationService
*/
  getCommingleDetailsDatatableColumns(): IDataTableColumn[] {
    return [
      {
        field: 'abbreviation',
        header: 'LOADABLE_PATTERN_GRADE'
      },
      {
        field: 'tankName',
        header: 'LOADABLE_PATTERN_TANK'
      },
      {
        field: 'quantity',
        header: 'LOADABLE_PATTERN_TOTAL_QUANTITY'
      },
      {
        field: 'api',
        header: 'LOADABLE_PATTERN_API'
      },
      {
        field: 'temperature',
        header: 'LOADABLE_PATTERN_TEMP'
      },
      {
        field: '',
        header: 'LOADABLE_PATTERN_COMPOSITION_BREAKDOWN',
        fieldColumnClass: 'commingle-composition colspan-header',
        columns: [
          {
            field: 'cargoPercentage',
            header: 'LOADABLE_PATTERN_PERCENTAGE',
            fieldClass: 'commingle-composition-percentage'
          },
          {
            field: 'cargoQuantity',
            header: 'LOADABLE_PATTERN_QUANTITY',
            fieldClass: 'commingle-composition-quantity'
          }
        ]
      }
    ]
  }

}

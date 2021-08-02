import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { DATATABLE_BUTTON, DATATABLE_FIELD_TYPE, IDataTableColumn } from '../../../shared/components/datatable/datatable.model';
import { QUANTITY_UNIT, ValueObject } from '../../../shared/models/common.model';
import { QuantityPipe } from '../../../shared/pipes/quantity/quantity.pipe';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';
import { ICargoQuantities, ILoadableQuantityCargo, IShipCargoTank } from '../../core/models/common.model';
import { ILoadingDelays, ILoadingSequenceDropdownData, ILoadingSequenceValueObject, IReasonForDelays } from '../models/loading-discharging.model';

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
      },
      hoseConnections: {
        'maxlength': 'LOADING_DISCHARGING_BERTH_HOSECONNECTION_CHARACTER_LIMIT'
      },
      regulationAndRestriction: {
        'maxlength': 'LOADING_DISCHARGING_BERTH_REGULATION_RESTRICTION_CHARACTER_LIMIT'
      },
      itemsToBeAgreedWith: {
        'maxlength': 'LOADING_DISCHARGING_BERTH_ITEMS_TO_BE_AGREED_WITH_CHARACTER_LIMIT'
      },
      lineDisplacement: {
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
  setValidationMessageForLoadingRate() {
    return {
      maxLoadingRate: {
        'required': 'LOADING_RATE_REQUIRED',
        'failedCompare': 'MAX_LOADING_COMPARE',
        'min': 'MAX_LOADING_MIN',
        'max': 'MAX_LOADING_MAX'
      },
      shoreLoadingRate: {
        'required': 'LOADING_RATE_REQUIRED',
        'min': 'MAX_LOADING_MIN',
        'max': 'MAX_LOADING_MAX'
      },
      minLoadingRate: {
        'required': 'LOADING_RATE_REQUIRED',
        'failedCompare': 'MIN_LOADING_COMPARE',
        'min': 'MIN_LOADING_MIN',
        'max': 'MIN_LOADING_MAX'
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
        'required': 'LOADING_RATE_REQUIRED'
      },
      noticeTimeStopLoading: {
        'required': 'LOADING_RATE_REQUIRED'
      }
    }
  }

  /**
* Method for setting loading manage grid columns
*
* @returns {IDataTableColumn[]}
* @memberof LoadingDischargingTransformationService
*/
  getLoadingDelayDatatableColumns(): IDataTableColumn[] {
    return [
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
        header: 'QUANTITY',
        numberFormat: '1.2-2'
      },
      {
        field: 'reasonForDelay',
        header: 'REASON FOR DELAY',
        listName: 'reasonForDelays',
        fieldType: DATATABLE_FIELD_TYPE.SELECT,
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
  * @param {ILoadingDelays} loadingDelay
  * @param {boolean} [isNewValue=true]
  * @param {ILoadingSequenceDropdownData} listData
  * @returns {ILoadingSequenceValueObject}
  * @memberof LoadingDischargingTransformationService
  */
  getLoadingDelayAsValueObject(loadingDelay: ILoadingDelays, isNewValue = true, isEditable = true, listData: ILoadingSequenceDropdownData): ILoadingSequenceValueObject {
    const _loadingDelay = <ILoadingSequenceValueObject>{};
    const reasonDelayObj: IReasonForDelays = listData?.reasonForDelays?.find(reason => reason.id === loadingDelay.reasonForDelayId);
    const cargoObj: ILoadableQuantityCargo = listData?.loadableQuantityCargo?.find(loadable => loadable.cargoId === loadingDelay.cargoId);
    _loadingDelay.id = loadingDelay.id;
    let hourDuration = (loadingDelay.duration / 60).toString();
    hourDuration = hourDuration.includes('.') ? hourDuration.split('.')[0] : hourDuration;
    hourDuration = Number(hourDuration) < 10 ? ('0' + hourDuration) : hourDuration
    const minuteDuration = loadingDelay.duration % 60;
    _loadingDelay.duration = new ValueObject<string>(hourDuration + ':' + minuteDuration, true, isNewValue, false, true);
    _loadingDelay.quantity = loadingDelay.quantity;
    _loadingDelay.colorCode = cargoObj?.colorCode;
    _loadingDelay.cargo = new ValueObject<ILoadableQuantityCargo>(cargoObj, true, isEditable ? isNewValue : false, false, isEditable);
    _loadingDelay.reasonForDelay = new ValueObject<IReasonForDelays>(reasonDelayObj, true, isNewValue, false, true);
    _loadingDelay.isAdd = isNewValue;
    return _loadingDelay;
  }


  /**
   * Method for converting from loading delay value object model
   *
   * @param {ILoadingSequenceValueObject} loadingDelayValueObject
   * @returns {ILoadingDelays}
   * @memberof LoadingDischargingTransformationService
   */
  getLoadingDelayAsValue(loadingDelayValueObject: ILoadingSequenceValueObject[], loadingInfoId: number): ILoadingDelays[] {
    let loadingDelays: ILoadingDelays[] = [];
    loadingDelayValueObject.forEach((loadingValueObject) => {
      let _loadingDelays = <ILoadingDelays>{};
      _loadingDelays.id = loadingValueObject?.id;
      _loadingDelays.loadingInfoId = loadingInfoId;
      _loadingDelays.cargoId = loadingValueObject?.cargo?.value?.cargoId;
      _loadingDelays.reasonForDelayId = loadingValueObject?.reasonForDelay?.value?.id;
      _loadingDelays.quantity = loadingValueObject?.quantity;
      const minuteDuration = loadingValueObject?.duration?.value.split(':');
      _loadingDelays.duration = (Number(minuteDuration[0]) * 60) + Number(minuteDuration[1]);
      loadingDelays.push(_loadingDelays);
    })
    return loadingDelays;
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
        'required': 'LOADING_DETAILS_INITIAL_TRIM_REQUIRED'
      },
      maximumTrim: {
        'required': 'LOADING_DETAILS_MAX_TRIM_REQUIRED'
      },
      finalTrim: {
        'required': 'LOADING_DETAILS_FINAL_TRIM_REQUIRED'
      }
    }
  }


  /**
* Method for setting cargo to be loaded grid columns
*
* @returns {IDataTableColumn[]}
* @memberof LoadingDischargingTransformationService
*/
  getCargotobeLoadedDatatableColumns(quantityUnit: QUANTITY_UNIT): IDataTableColumn[] {
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
        numberFormat: quantityNumberFormat
      },
      {
        field: 'minMaxTolerance',
        header: 'LOADING_CARGO_TO_BE_LOADED_MIN_MAX_TOLERANCE'
      },
      {
        field: 'loadableMT',
        header: 'LOADING_CARGO_TO_BE_LOADED_SHIP_LOADABLE',
        numberFormat: quantityNumberFormat
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
}

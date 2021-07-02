import { Injectable } from '@angular/core';
import { ValueObject } from 'apps/cpdss/src/app/shared/models/common.model';
import { DATATABLE_BUTTON, DATATABLE_FIELD_TYPE, IDataTableColumn } from '../../../../shared/components/datatable/datatable.model';
import { ILoadableQuantityCargo } from '../../../core/models/common.model';
import { ILoadingDelays, IReasonForDelays } from '../../models/loading-information.model';
import { ILoadingSequenceListData, ILoadingSequenceValueObject } from '../models/loading-discharging-manage-sequence.model';

/**
 * Transformation Service for Loadaing  Discharging manage sequence
 *
 * @export
 * @class LoadingDischargingManageSequenceTransformationService
 */

@Injectable()
export class LoadingDischargingManageSequenceTransformationService {

  constructor() { }

  /**
* Method for setting loading manage grid columns
*
* @returns {IDataTableColumn[]}
* @memberof LoadingDischargingManageSequenceTransformationService
*/
  getDatatableColumns(): IDataTableColumn[] {
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
          'quantityExceeds': 'LOADING_MANAGE_SEQUENCE_QUANTITY_EXCEEDS'
        }
      },
      {
        field: 'quantity',
        header: 'QUANTITY',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        fieldPlaceholder: 'LOADING_MANAGE_SEQUENCE_ENTER_QUANTITY',
        errorMessages: {
          'required': 'LOADING_MANAGE_SEQUENCE_REQUIRED',
          'quantityExceeds': 'LOADING_MANAGE_SEQUENCE_QUANTITY_EXCEEDS'
        }
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
        fieldHeaderClass:'column-actions',
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
 * @param {ILoadingSequenceListData} listData
 * @returns {ILoadingSequenceValueObject}
 * @memberof LoadingDischargingManageSequenceTransformationService
 */
  getLoadingDelayAsValueObject(loadingDelay: ILoadingDelays, isNewValue = true, isEditable = true, listData: ILoadingSequenceListData): ILoadingSequenceValueObject {
    const _loadingDelay = <ILoadingSequenceValueObject>{};
    const reasonDelayObj: IReasonForDelays = listData?.reasonForDelays?.find(reason => reason.id === loadingDelay.reasonForDelayId);
    const cargoObj: ILoadableQuantityCargo = listData?.loadableQuantityCargo?.find(loadable => loadable.cargoId === loadingDelay.cargoId);
    _loadingDelay.id = loadingDelay.id;
    let hourDuration = (loadingDelay.duration / 60).toString();
    hourDuration = hourDuration.includes('.') ? hourDuration.split('.')[0] : hourDuration;
    hourDuration = Number(hourDuration) < 10 ? ('0' + hourDuration) : hourDuration
    const minuteDuration = loadingDelay.duration % 60;
    _loadingDelay.duration = new ValueObject<string>(hourDuration + ':' + minuteDuration, true, isNewValue, false, true);
    _loadingDelay.quantity = new ValueObject<number>(loadingDelay.quantity, true, isEditable ? isNewValue : false, false, isEditable);
    _loadingDelay.cargo = new ValueObject<ILoadableQuantityCargo>(cargoObj, true, isEditable ? isNewValue : false, false, isEditable);
    _loadingDelay.reasonForDelay = new ValueObject<IReasonForDelays>(reasonDelayObj, true, isNewValue, false, true);
    _loadingDelay.isAdd = isNewValue;
    return _loadingDelay;
  }


  /**
   * Method for converting from cargonomination value object model
   *
   * @param {ICargoNominationValueObject} cargoNomination
   * @returns {ICargoNomination}
   * @memberof LoadableStudyDetailsTransformationService
   */
   getLoadingDelayAsValue(loadingDelayValueObject: ILoadingSequenceValueObject[], loadingInfoId: number): ILoadingDelays[] {
    let loadingDelays: ILoadingDelays[] = [];
    loadingDelayValueObject.forEach((loadingValueObject) => {
      let _loadingDelays = <ILoadingDelays>{};
      _loadingDelays.id = loadingValueObject?.id;
      _loadingDelays.loadingInfoId = loadingInfoId;
      _loadingDelays.cargoId = loadingValueObject?.cargo?.value?.cargoId;
      _loadingDelays.reasonForDelayId = loadingValueObject?.reasonForDelay?.value?.id;
      _loadingDelays.quantity = loadingValueObject?.quantity?.value;
      const minuteDuration = loadingValueObject?.duration?.value.split(':');
      _loadingDelays.duration = (Number(minuteDuration[0]) * 60) + Number(minuteDuration[1]);
      loadingDelays.push(_loadingDelays);
    })
    return loadingDelays;
  }
}

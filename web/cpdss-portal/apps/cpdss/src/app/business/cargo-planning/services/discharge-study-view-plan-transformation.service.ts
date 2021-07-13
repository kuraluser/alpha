import { Injectable } from '@angular/core';

import { ValueObject } from '../../../shared/models/common.model';

import { DATATABLE_ACTION, DATATABLE_FIELD_TYPE, DATATABLE_BUTTON, DATATABLE_FILTER_MATCHMODE, DATATABLE_FILTER_TYPE, IDataTableColumn } from '../../../shared/components/datatable/datatable.model';
import { IPermissionContext, PERMISSION_ACTION, QUANTITY_UNIT, ISubTotal , IMode , ICargo } from '../../../shared/models/common.model';
import { IPortDetailValueObject , IBackLoadingDetails , IPortCargo , IDischargeStudyDropdownData } from '../models/discharge-study-view-plan.model';

@Injectable()

/**
 * Transformation service for Discharge study Details module
 *
 * @export
 * @class DischargeStudyViewPlanTransformationService
*/

export class DischargeStudyViewPlanTransformationService {

  constructor() { }

  /**
* Method for setting commingle details grid columns
*
* @returns {IDataTableColumn[]}
* @memberof DischargeStudyViewPlanTransformationService
*/
  getDischargeStudyCargoDatatableColumns(): IDataTableColumn[] {
    return [
      {
        field: 'slNo',
        header: 'DISCHARGE_STUDY_VIEW_PLAN_DISCHARGE_SL',
        fieldType: DATATABLE_FIELD_TYPE.SLNO,
      },
      {
        field: 'color',
        header: 'DISCHARGE_STUDY_VIEW_PLAN_COLOR',
        fieldType: DATATABLE_FIELD_TYPE.COLORPICKER,
        editable: false
      },
      {
        field: 'cargo',
        header: 'DISCHARGE_STUDY_VIEW_PLAN_CARGO_NAME',
        fieldType: DATATABLE_FIELD_TYPE.SELECT,
        fieldPlaceholder: 'SEARCH_CARGO',
        listFilter: true,
        listName: 'cargoList',
        fieldOptionLabel: 'name',
        editable: false
      },
      {
        field: 'abbreviation',
        header: 'ABBREVIATION',
        editable: false,
        fieldType: DATATABLE_FIELD_TYPE.TEXT,
        fieldPlaceholder: 'ENTER_ABBREVIATION'
      },
      {
        field: 'bbls',
        header: 'DISCHARGE_STUDY_VIEW_PLAN_BBLS',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        editable: false,
        unit: QUANTITY_UNIT.BBLS,
        numberType: 'quantity'
      },
      {
        field: 'mt',
        header: 'DISCHARGE_STUDY_VIEW_PLAN_MT',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        unit: QUANTITY_UNIT.MT,
        editable: false,
        numberType: 'quantity'
      },
      {
        field: 'kl',
        header: 'DISCHARGE_STUDY_VIEW_PLAN_KL',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        unit: QUANTITY_UNIT.KL,
        numberType: 'quantity'
      },
      {
        field: 'time',
        header: 'DISCHARGE_STUDY_VIEW_PLAN_TIME'
      }
    ]
  }

  /**
* Method for setting commingle details grid columns
*
* @returns {IDataTableColumn[]}
* @memberof DischargeStudyViewPlanTransformationService
*/
  getDischargeStudyBackLoadingDatatableColumns(): IDataTableColumn[] {
    const columns: IDataTableColumn[] = [
      {
        field: 'slNo',
        header: 'DISCHARGE_STUDY_VIEW_PLAN_SL_BACK_LOADING',
        fieldType: DATATABLE_FIELD_TYPE.SLNO,
      },
      {
        field: 'color',
        header: 'DISCHARGE_STUDY_VIEW_PLAN_COLOR_BACK_LOADING',
        fieldType: DATATABLE_FIELD_TYPE.COLORPICKER
      },
      {
        field: 'cargo',
        header: 'DISCHARGE_STUDY_VIEW_PLAN_CARGO_NAME_BACK_LOADING',
        fieldType: DATATABLE_FIELD_TYPE.SELECT,
        fieldPlaceholder: 'SEARCH_CARGO',
        listFilter: true,
        listName: 'cargoList',
        fieldOptionLabel: 'name'
      },
      {
        field: 'abbreviation',
        header: 'ABBREVIATION',
        fieldType: DATATABLE_FIELD_TYPE.TEXT
      },
      {
        field: 'bbls',
        header: 'DISCHARGE_STUDY_VIEW_PLAN_BBLS_BACK_LOADING',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        unit: QUANTITY_UNIT.BBLS,
        numberType: 'quantity',
        fieldPlaceholder: 'DISCHARGE_STUDY_BACK_LOADING_BBLS',
        showTotal: true
      },
      {
        field: 'mt',
        header: 'DISCHARGE_STUDY_VIEW_PLAN_MT_BACK_LOADING',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        unit: QUANTITY_UNIT.MT,
        showTotal: true,
        numberType: 'quantity'
      },
      {
        field: 'kl',
        header: 'DISCHARGE_STUDY_VIEW_PLAN_KL_BACK_LOADING',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        unit: QUANTITY_UNIT.KL,
        showTotal: true,
        numberType: 'quantity'
      },
      {
        field: 'api',
        header: 'DISCHARGE_STUDY_VIEW_PLAN_API_BACK_LOADING',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER
      },
      {
        field: 'temp',
        header: 'DISCHARGE_STUDY_VIEW_PLAN_TEMP_BACK_LOADING',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER
      }
    ];
    return columns;
  }


  /**
 * Method to convert loadable plan tank details to value object
 *
 * @param {boolean} isLastIndex
 * @param {*} portDetail
 * @param {ITankDetails} tankDetails
 * @param {boolean} [isNewValue=true]
 * @returns {IPortDetailValueObject}
 * @memberof DischargeStudyViewPlanTransformationService
 */
  getPortDetailAsValueObject(portDetail: any, listData: IDischargeStudyDropdownData, isLastIndex: boolean, isNewValue = true): IPortDetailValueObject {
    const _portDetail = <IPortDetailValueObject>{};
    _portDetail.portName = portDetail.portName;
    _portDetail.instruction = portDetail.instruction ? portDetail.instruction : listData.instructions[0];
    _portDetail.draftRestriction = portDetail.draftRestriction;
    _portDetail.cargoDetail = portDetail.cargo.map((cargoDetail) => {
      return this.getCargoDetailsAsValueObject(cargoDetail, listData);
    })
    const cow: IMode = listData.mode.find(modeDetails => modeDetails.id === portDetail.cow);
    _portDetail.cow = cow;
    _portDetail.dischargeRate = portDetail.dischargeRate;
    _portDetail.backLoadingDetails = portDetail.backLoadingDetails.map((backLoadingDetail) => {
      return this.getBackLoadingDetailAsValueObject(backLoadingDetail, listData, isNewValue);
    });
    return _portDetail;
  }

  /**
  * Method to convert loadable plan tank details to value object
  *
  * @param {*} portDetail
  * @param {ITankDetails} tankDetails
  * @param {boolean} [isNewValue=true]
  * @returns {IPortDetailValueObject}
  * @memberof DischargeStudyViewPlanTransformationService
  */
  getCargoDetailsAsValueObject(cargoDetail: any, listData: IDischargeStudyDropdownData, isNewValue = true) {
    const _cargoDetailValuObject = <IPortCargo>{};
    const mode = listData.mode.find(modeDetails => modeDetails.id === cargoDetail.mode);
    const cargoObj = listData.cargoList.find(cargo => cargo.id === cargoDetail.cargoId);
    const isKlEditable = mode?.id === 2 ? true : false;
    _cargoDetailValuObject.color = new ValueObject<string>(cargoDetail.color, true, false);
    _cargoDetailValuObject.bbls = new ValueObject<string>(isKlEditable ? cargoDetail.bbls : '-', true, false);
    _cargoDetailValuObject.cargo = new ValueObject<ICargo>(cargoObj, true, false);
    _cargoDetailValuObject.kl = new ValueObject<string>(isKlEditable ? cargoDetail.kl : '-', true, false, false, isKlEditable);
    _cargoDetailValuObject.maxKl = new ValueObject<number>(cargoDetail.maxKl, false, false);
    _cargoDetailValuObject.mt = new ValueObject<string>(isKlEditable ? cargoDetail.mt : '-', true, false);
    _cargoDetailValuObject.time = cargoDetail.time;
    _cargoDetailValuObject.abbreviation = new ValueObject<string>(cargoDetail.abbreviation, true, false);
    _cargoDetailValuObject.api = new ValueObject<number>(cargoDetail.api);
    _cargoDetailValuObject.temp = new ValueObject<number>(cargoDetail.temp);
    return _cargoDetailValuObject;
  }


  /**
  * Method to convert loadable plan tank details to value object
  *
  * @param {*} portDetail
  * @param {ITankDetails} tankDetails
  * @param {boolean} [isNewValue=true]
  * @returns {IPortDetailValueObject}
  * @memberof DischargeStudyViewPlanTransformationService
  */
  getBackLoadingDetailAsValueObject(backLoadingDetail: any, listData: IDischargeStudyDropdownData, isNewValue = true): IBackLoadingDetails {
    const _backLoadingDetailDetail = <IBackLoadingDetails>{};
    const cargoObj: ICargo = backLoadingDetail.cargoId ? listData.cargoList.find(cargo => cargo.id === backLoadingDetail.cargoId) : null;
    _backLoadingDetailDetail.color = new ValueObject<string>(backLoadingDetail.color, true, isNewValue);
    _backLoadingDetailDetail.bbls = new ValueObject<number>(backLoadingDetail.bbls, true, false);
    _backLoadingDetailDetail.cargo = new ValueObject<ICargo>(cargoObj, true, isNewValue);
    _backLoadingDetailDetail.kl = new ValueObject<number>(backLoadingDetail.kl, true, isNewValue);
    _backLoadingDetailDetail.mt = new ValueObject<number>(backLoadingDetail.mt, true, false);
    _backLoadingDetailDetail.api = new ValueObject<string>(backLoadingDetail.api, true, isNewValue);
    _backLoadingDetailDetail.temp = new ValueObject<string>(backLoadingDetail.temp, true, isNewValue);
    _backLoadingDetailDetail.isDelete = true;
    _backLoadingDetailDetail.isAdd = isNewValue;
    _backLoadingDetailDetail.abbreviation = new ValueObject<string>(backLoadingDetail.abbreviation);
    _backLoadingDetailDetail.cargoAbbreviation = Math.random().toString();
    return _backLoadingDetailDetail;
  }
}

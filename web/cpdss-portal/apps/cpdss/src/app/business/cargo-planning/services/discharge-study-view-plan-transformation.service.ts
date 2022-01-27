import { Injectable } from '@angular/core';

import { ValueObject } from '../../../shared/models/common.model';

import { QuantityPipe } from '../../../shared/pipes/quantity/quantity.pipe';

import { DATATABLE_ACTION, DATATABLE_FIELD_TYPE, DATATABLE_BUTTON, DATATABLE_FILTER_MATCHMODE, DATATABLE_FILTER_TYPE, IDataTableColumn } from '../../../shared/components/datatable/datatable.model';
import { IPermissionContext, PERMISSION_ACTION, QUANTITY_UNIT, ISubTotal , IMode } from '../../../shared/models/common.model';
import { IPortDetailValueObject , IBackLoadingDetails , IPortCargo , IDischargeStudyDropdownData , IDischargeStudyBackLoadingDetails , IDischargeStudyPortListDetails , IDischargeStudyCargoNominationList} from '../models/discharge-study-view-plan.model';
import { ICargo } from '../../core/models/common.model';

/**
 * Transformation service for Discharge Plan Details module
 *
 * @export
 * @class DischargeStudyViewPlanTransformationService
*/
@Injectable()

export class DischargeStudyViewPlanTransformationService {

  private quantityPipe: QuantityPipe = new QuantityPipe();

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
        field: 'sequenceNo',
        header: 'DISCHARGE_STUDY_VIEW_PLAN_DISCHARGE_SEQUENCE_NO',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER
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
        field: 'dischargeRate',
        header: 'DISCHARGE_STUDY_VIEW_DISCHARGE_RATE',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        numberType: 'quantity'
      },
      {
        field: 'dischargeTime',
        fieldColumnClass: 'text-right',
        fieldClass: 'text-right',
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
 * @param {IDischargeStudyPortListDetails} portDetail
 * @param {ITankDetails} tankDetails
 * @param {boolean} [isNewValue=true]
 * @returns {IPortDetailValueObject}
 * @memberof DischargeStudyViewPlanTransformationService
 */
  getPortDetailAsValueObject(portDetail: IDischargeStudyPortListDetails, listData: IDischargeStudyDropdownData, isLastIndex: boolean, isNewValue = true): IPortDetailValueObject {
    const _portDetail = <IPortDetailValueObject>{};
    const port = listData.portList.find((portItem) =>{
      if(portItem.id === portDetail.portId) {
        return portItem;
      }
    })
    _portDetail.portName = port.name;
    _portDetail.isBackLoadingEnabled = portDetail.isBackLoadingEnabled;
    if(portDetail.instructionId?.length) {
      _portDetail.instruction = listData.instructions.find((instruction) => {
        return portDetail.instructionId.some((instructionId) => {
          return instructionId === instruction.id;
        })
      })
    }
    _portDetail.draftRestriction = portDetail.maxDraft;
    _portDetail.cargoDetail = portDetail.cargoNominationList ? portDetail.cargoNominationList?.map((cargoDetail) => {
      return this.getCargoDetailsAsValueObject(cargoDetail, listData);
    }): []
    const cow: IMode = listData.mode.find(modeDetails => modeDetails.id === portDetail.cowId);
    _portDetail.cow = cow;
    _portDetail.dischargeRate = portDetail.dischargeRate;
    _portDetail.backLoadingDetails = portDetail.backLoading ? portDetail.backLoading?.map((backLoadingDetail) => {
      return this.getBackLoadingDetailAsValueObject(backLoadingDetail, listData, isNewValue);
    }) : [];
    _portDetail.tank = portDetail?.tanks;
    _portDetail.stabilityParams = portDetail?.stabilityParams;
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
  getCargoDetailsAsValueObject(cargoDetail: IDischargeStudyCargoNominationList, listData: IDischargeStudyDropdownData, isNewValue = true) {
    const _cargoDetailValuObject = <IPortCargo>{};

    const cargoObj = listData.cargoList.find(cargo => cargo.id === cargoDetail.cargoId);

    const unitConversion = {
      kl: this.quantityPipe.transform(cargoDetail.quantity, QUANTITY_UNIT.MT, QUANTITY_UNIT.KL, cargoDetail.api, cargoDetail.temperature),
      bbls: this.quantityPipe.transform(cargoDetail.quantity, QUANTITY_UNIT.MT, QUANTITY_UNIT.BBLS, cargoDetail.api, cargoDetail.temperature)
    }
    _cargoDetailValuObject.color = new ValueObject<string>(cargoDetail.color, true, false , false);
    _cargoDetailValuObject.bbls = new ValueObject<string>(unitConversion.bbls ? unitConversion.bbls?.toString() : '0', true, false);
    _cargoDetailValuObject.cargo = new ValueObject<ICargo>(cargoObj, true, false);
    _cargoDetailValuObject.kl = new ValueObject<string>(unitConversion.kl ? unitConversion.kl?.toString() : '0', true, false, false, false);

    _cargoDetailValuObject.mt = new ValueObject<string>(cargoDetail.quantity?.toString(), true, false);
    _cargoDetailValuObject.dischargeTime = cargoDetail.dischargeTime;
    _cargoDetailValuObject.abbreviation = new ValueObject<string>(cargoDetail.abbreviation, true, false);
    _cargoDetailValuObject.api = new ValueObject<number>(cargoDetail.api);
    _cargoDetailValuObject.temp = new ValueObject<number>(cargoDetail.temperature);
    _cargoDetailValuObject.sequenceNo = new ValueObject<number>(cargoDetail.sequenceNo);
    _cargoDetailValuObject.dischargeRate = new ValueObject<number>(cargoDetail.dischargeRate);
    return _cargoDetailValuObject;
  }


   /**
   * Method to convert loadable plan tank details to value object
   *
   * @param {IDischargeStudyBackLoadingDetails} backLoadingDetail
   * @param {IDischargeStudyDropdownData} listData
   * @param {boolean} [isNewValue=true]
   * @returns {IPortDetailValueObject}
   * @memberof DischargeStudyDetailsTransformationService
   */
    getBackLoadingDetailAsValueObject(backLoadingDetail: IDischargeStudyBackLoadingDetails, listData:IDischargeStudyDropdownData ,isNewValue = true): IBackLoadingDetails {
      const _backLoadingDetailDetail = <IBackLoadingDetails>{};
      const unitConversion = {
        kl: this.quantityPipe.transform(backLoadingDetail.quantity, QUANTITY_UNIT.MT, QUANTITY_UNIT.KL, backLoadingDetail.api, backLoadingDetail.temperature),
        bbls: this.quantityPipe.transform(backLoadingDetail.quantity, QUANTITY_UNIT.KL, QUANTITY_UNIT.BBLS, backLoadingDetail.api, backLoadingDetail.temperature),
      }
      const cargoObj: ICargo = backLoadingDetail.cargoId ? listData.cargoList.find(cargo => cargo.id === backLoadingDetail.cargoId) : null;
      _backLoadingDetailDetail.color = new ValueObject<string>(backLoadingDetail.color , true , isNewValue);
      _backLoadingDetailDetail.bbls = new ValueObject<number>(unitConversion.bbls ? unitConversion.bbls : 0, true , false);
      _backLoadingDetailDetail.cargo = new ValueObject<ICargo>(cargoObj, true , isNewValue);
      _backLoadingDetailDetail.kl = new ValueObject<number>(unitConversion.kl ? unitConversion.kl : 0 , true , isNewValue);
      _backLoadingDetailDetail.mt = new ValueObject<number>(backLoadingDetail.quantity  , true , false);
      _backLoadingDetailDetail.api = new ValueObject<string>(backLoadingDetail.api?.toString() , true , isNewValue);
      _backLoadingDetailDetail.temp = new ValueObject<string>(backLoadingDetail.temperature?.toString() , true , isNewValue);

      _backLoadingDetailDetail.abbreviation = new ValueObject<string>(backLoadingDetail.abbreviation, true , isNewValue);
      return _backLoadingDetailDetail;
    }
}

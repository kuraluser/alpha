import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import * as moment from 'moment';
import { v4 as uuid4 } from 'uuid';

import { QuantityPipe } from '../../../shared/pipes/quantity/quantity.pipe';

import { ValueObject } from '../../../shared/models/common.model';
import { IPermission } from '../../../shared/models/user-profile.model';
import {  DischargeStudyDetailsModule } from '../discharge-study-details/discharge-study-details.module';

import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';
import { QuantityDecimalFormatPipe } from '../../../shared/pipes/quantity-decimal-format/quantity-decimal-format.pipe'; 
import { TimeZoneTransformationService } from '../../../shared/services/time-zone-conversion/time-zone-transformation.service';

import { DATATABLE_ACTION, DATATABLE_FIELD_TYPE, DATATABLE_BUTTON, DATATABLE_FILTER_MATCHMODE, DATATABLE_FILTER_TYPE, IDataTableColumn } from '../../../shared/components/datatable/datatable.model';
import { ICargoNomination,  ICargoNominationAllDropdownData, ICargoNominationValueObject, ILoadingPort, ILoadingPortValueObject, IMonths, IOHQPort, IPortAllDropdownData, IPortOBQListData, IPortOBQTankDetail, IPortOBQTankDetailValueObject, IPortOHQTankDetail, IPortOHQTankDetailValueObject, IPortsValueObject, ISegregation, OPERATIONS } from '../models/cargo-planning.model';
import { IPermissionContext, PERMISSION_ACTION, QUANTITY_UNIT, ISubTotal } from '../../../shared/models/common.model';
import { IDischargeOHQStatus, IDischargeStudyDropdownData , IBackLoadingDetails , ICargo , IBillingOfLaddings } from '../models/discharge-study-list.model';
import { IOperations, IPort, IPortList, DISCHARGE_STUDY_STATUS , VOYAGE_STATUS } from '../../core/models/common.model';
import { ILoadableQuantityCommingleCargo , ICommingleCargoDispaly , IBillingFigValueObject , IMode ,  IPortDetailValueObject , IPortCargo , ITankDetails
} from '../models/discharge-study-list.model';


/**
 * Transformation service for Details module
 *
 * @export
 * @class DischargeStudyDetailsTransformationService
*/
@Injectable()
export class DischargeStudyDetailsTransformationService {

  constructor(
    private quantityDecimalFormatPipe: QuantityDecimalFormatPipe,
    private timeZoneTransformationService: TimeZoneTransformationService
  ) { }

  private quantityPipe: QuantityPipe = new QuantityPipe();
  private _portValiditySource: Subject<boolean> = new Subject();
  private _ohqValiditySource: Subject<boolean> = new Subject();
  private _dischargeStudyValiditySource: Subject<boolean> = new Subject();
  private _addPortSource = new Subject();
  private _portUpdate: Subject<any> = new Subject();

  public baseUnit = AppConfigurationService.settings.baseUnit;
  portValidity$ = this._portValiditySource.asObservable();
  ohqValidity$ = this._ohqValiditySource.asObservable();
  portUpdate$ = this._portUpdate.asObservable();
  dischargeStudyValiditySource$ = this._dischargeStudyValiditySource.asObservable();
  addPort$ = this._addPortSource.asObservable();
  ohqPortsValidity: { id: number; isPortRotationOhqComplete: boolean; }[];
   
    /**
  *
  * Get Billing Figure table header
  * @returns {IDataTableColumn[]}
  * @memberof DischargeStudyDetailsTransformationService
  */
  public getBFTableColumns(): IDataTableColumn[] {
    return [
      { field: '', header: 'DISCHARGE_STUDY_CARGO_NOMINATION_BL_FIGURE_SL' , fieldType: DATATABLE_FIELD_TYPE.SLNO},
      {
        field: 'cargoColor',
        header: 'DISCHARGE_STUDY_DISCHARGE_COLOR_BACK_LOADING',
        fieldType: DATATABLE_FIELD_TYPE.COLORPICKER,
        editable: false
      },
      { field: 'cargoName', header: 'DISCHARGE_STUDY_CARGO_NOMINATION_BL_FIGURE_CARGO_NAME' },
      { field: 'cargoAbbrevation', header: 'ABBREVIATION' },
      { field: 'port', header: 'DISCHARGE_STUDY_CARGO_NOMINATION_BL_FIGURE_LOADING_PORT' },
      { field: 'quantityBbls', header: 'DISCHARGE_STUDY_CARGO_NOMINATION_BL_FIGURE_BBLS' , numberType: 'quantity' , unit: QUANTITY_UNIT.BBLS , showTotal: true , fieldType: DATATABLE_FIELD_TYPE.NUMBER},
      { field: 'quantityMt', header: 'DISCHARGE_STUDY_CARGO_NOMINATION_BL_FIGURE_MT' , numberType: 'quantity' , unit: QUANTITY_UNIT.MT, showTotal: true, fieldType: DATATABLE_FIELD_TYPE.NUMBER},
      { field: 'quantityKl', header: 'DISCHARGE_STUDY_CARGO_NOMINATION_BL_FIGURE_KL' , numberType: 'quantity' , unit: QUANTITY_UNIT.KL, showTotal: true, fieldType: DATATABLE_FIELD_TYPE.NUMBER},
      { field: 'api', header: 'DISCHARGE_STUDY_CARGO_NOMINATION_BL_FIGURE_API' , numberFormat: '1.2-2' , fieldType: DATATABLE_FIELD_TYPE.NUMBER},
      { field: 'temperature', header: 'DISCHARGE_STUDY_CARGO_NOMINATION_BL_FIGURE_TEMP', numberFormat: '1.2-2', fieldType: DATATABLE_FIELD_TYPE.NUMBER }
    ]
  }

  /**
  * Method to convert Billinf fig details to value object
  *
  * @param {*} cargoTankDetail
  * @param {boolean} [isEditMode=true]
  * @param {boolean} [isAdd=true]
  * @returns {IBillingFigValueObject}
  * @memberof DischargeStudyDetailsTransformationService
  */
  getFormatedBillingDetails(billingFigDetail: IBillingOfLaddings , listData: any , isEditMode = true , isAdd = true) {
    const _cargoDetail = <IBillingFigValueObject>{};
    const selectedPort = [];
    billingFigDetail.loadingPort.map((portId) => {
      const port = listData.portsList.find((portDetails) => {
        if(portDetails.id === portId) {
         return portDetails;
        }
      })
      selectedPort.push(port.name);
    })
    _cargoDetail.port = selectedPort.toString();
    _cargoDetail.quantityBbls = new ValueObject<number>(billingFigDetail.quantityBbls , true , isEditMode);
    _cargoDetail.quantityMt = new ValueObject<number>(billingFigDetail.quantityMt , true , isEditMode);
    _cargoDetail.quantityKl = new ValueObject<number>(billingFigDetail.quantityKl , true , isEditMode);
    _cargoDetail.api = new ValueObject<number>(billingFigDetail.api , true , isEditMode);
    _cargoDetail.temperature = new ValueObject<number>(billingFigDetail.temperature , true , isEditMode);
    _cargoDetail.cargoName = billingFigDetail.cargoName;
    _cargoDetail.cargoColor = new ValueObject<string>(billingFigDetail.cargoColor, true , isEditMode);
    _cargoDetail.cargoAbbrevation = billingFigDetail.cargoAbbrevation;
    return _cargoDetail;
  }

/**
* Method for setting commingle details grid columns
*
* @returns {IDataTableColumn[]}
* @memberof DischargeStudyDetailsTransformationService
*/
  getCommingleDetailsDatatableColumns(): IDataTableColumn[] {
    return [
      {
        field: 'grade',
        header: 'DISCHARGE_STUDY_GRADE',
        rowspan: 2
      },
      {
        field: 'tankName',
        header: 'DISCHARGE_STUDY_TANK',
        rowspan: 2
      },
      {
        field: 'quantity',
        header: 'DISCHARGE_STUDY_TOTAL_QUANTITY',
        rowspan: 2
      },
      {
        field: 'api',
        header: 'DISCHARGE_STUDY_API',
        rowspan: 2
      },
      {
        field: 'temp',
        header: 'DISCHARGE_STUDY_TEMP',
        rowspan: 2
      },
      {
        field: '',
        header: 'DISCHARGE_STUDY_COMPOSITION_BREAKDOWN',
        fieldColumnClass: "th-border",
        colspan: 6,
        subColumns: [
          {
            field: 'cargoPercentage',
            header: 'DISCHARGE_STUDY_PERCENTAGE',
            fieldClass: 'commingle-composition-percentage'
          },
          {
            field: 'cargoBblsdbs',
            header: 'DISCHARGE_STUDY_OBS_BBLS',
            fieldClass: 'commingle-composition-quantity'
          },
          {
            field: 'cargoBbls60f',
            header: 'DISCHARGE_STUDY_BBLS',
            fieldClass: 'commingle-composition-quantity'
          },
          {
            field: 'cargoLT',
            header: 'DISCHARGE_STUDY_LT',
            fieldClass: 'commingle-composition-quantity'
          },
          {
            field: 'cargoMT',
            header: 'DISCHARGE_STUDY_MT',
            fieldClass: 'commingle-composition-quantity'
          },
          {
            field: 'cargoKL',
            header: 'DISCHARGE_STUDY_KL',
            fieldClass: 'commingle-composition-quantity'
          }
        ]
      }

    ]
  }

    /**
  *
  * Get Formated Loadable Quantity Data
  * @returns { ICommingleCargoDispaly }
  * @param loadablePlanCommingleCargoDetails
  */
  public getFormatedLoadableCommingleCargo(_decimalPipe: any, loadablePlanCommingleCargoDetails: ILoadableQuantityCommingleCargo): ICommingleCargoDispaly {
    const _loadablePlanCommingleCargoDetails = <ICommingleCargoDispaly>{};
    _loadablePlanCommingleCargoDetails.api = this.decimalConvertion(_decimalPipe, loadablePlanCommingleCargoDetails.api, '1.2-2');
    _loadablePlanCommingleCargoDetails.tankName = loadablePlanCommingleCargoDetails.tankShortName;
    _loadablePlanCommingleCargoDetails.grade = loadablePlanCommingleCargoDetails.grade;
    _loadablePlanCommingleCargoDetails.quantityBLS = this.decimalConvertion(_decimalPipe, this.convertQuantityCommingle(loadablePlanCommingleCargoDetails, QUANTITY_UNIT.BBLS, 'quantity'), '1.2-2');
    _loadablePlanCommingleCargoDetails.quantity = this.quantityDecimalFormatPipe.transform(loadablePlanCommingleCargoDetails.quantity, QUANTITY_UNIT.MT);
    _loadablePlanCommingleCargoDetails.temp = this.decimalConvertion(_decimalPipe, loadablePlanCommingleCargoDetails.temp, '1.2-2');
    loadablePlanCommingleCargoDetails.cargo1Bbls60f = this.convertQuantityCommingle(loadablePlanCommingleCargoDetails, QUANTITY_UNIT.BBLS, 'cargo1MT')?.toString();
    loadablePlanCommingleCargoDetails.cargo2Bbls60f = this.convertQuantityCommingle(loadablePlanCommingleCargoDetails, QUANTITY_UNIT.BBLS, 'cargo2MT')?.toString();
    loadablePlanCommingleCargoDetails.cargo1Bblsdbs = this.convertQuantityCommingle(loadablePlanCommingleCargoDetails, QUANTITY_UNIT.OBSBBLS, 'cargo1MT')?.toString();
    loadablePlanCommingleCargoDetails.cargo2Bblsdbs = this.convertQuantityCommingle(loadablePlanCommingleCargoDetails, QUANTITY_UNIT.OBSBBLS, 'cargo2MT')?.toString();
    _loadablePlanCommingleCargoDetails.cargoPercentage = loadablePlanCommingleCargoDetails.cargo1Abbreviation + ' - ' + loadablePlanCommingleCargoDetails.cargo1Percentage + '%' + '<br>' + loadablePlanCommingleCargoDetails.cargo2Abbreviation + ' - ' + loadablePlanCommingleCargoDetails.cargo2Percentage + '%';
    _loadablePlanCommingleCargoDetails.cargoBblsdbs =  this.quantityDecimalFormatPipe.transform(loadablePlanCommingleCargoDetails.cargo1Bblsdbs, QUANTITY_UNIT.OBSBBLS) + '<br>' +  this.quantityDecimalFormatPipe.transform(loadablePlanCommingleCargoDetails.cargo2Bblsdbs, QUANTITY_UNIT.OBSBBLS);
    _loadablePlanCommingleCargoDetails.cargoBbls60f =  this.quantityDecimalFormatPipe.transform(loadablePlanCommingleCargoDetails.cargo1Bbls60f, QUANTITY_UNIT.BBLS) + '<br>' +  this.quantityDecimalFormatPipe.transform(loadablePlanCommingleCargoDetails.cargo2Bbls60f, QUANTITY_UNIT.BBLS);
    _loadablePlanCommingleCargoDetails.cargo1LT = this.convertQuantityCommingle(loadablePlanCommingleCargoDetails, QUANTITY_UNIT.LT, 'cargo1MT');
    _loadablePlanCommingleCargoDetails.cargo2LT = this.convertQuantityCommingle(loadablePlanCommingleCargoDetails, QUANTITY_UNIT.LT, 'cargo2MT');
    _loadablePlanCommingleCargoDetails.cargo1KL = this.convertQuantityCommingle(loadablePlanCommingleCargoDetails, QUANTITY_UNIT.KL, 'cargo1MT');
    _loadablePlanCommingleCargoDetails.cargo2KL = this.convertQuantityCommingle(loadablePlanCommingleCargoDetails, QUANTITY_UNIT.KL, 'cargo2MT');
    _loadablePlanCommingleCargoDetails.cargoMT = this.quantityDecimalFormatPipe.transform(loadablePlanCommingleCargoDetails.cargo1MT, QUANTITY_UNIT.MT) + '<br>' + this.quantityDecimalFormatPipe.transform(loadablePlanCommingleCargoDetails.cargo2MT, QUANTITY_UNIT.MT);
    _loadablePlanCommingleCargoDetails.cargoLT = this.quantityDecimalFormatPipe.transform(_loadablePlanCommingleCargoDetails.cargo1LT, QUANTITY_UNIT.LT) + '<br>' + this.quantityDecimalFormatPipe.transform( _loadablePlanCommingleCargoDetails.cargo2LT, QUANTITY_UNIT.LT);
    _loadablePlanCommingleCargoDetails.cargoKL = this.quantityDecimalFormatPipe.transform(_loadablePlanCommingleCargoDetails.cargo1KL, QUANTITY_UNIT.KL) + '<br>' + this.quantityDecimalFormatPipe.transform( _loadablePlanCommingleCargoDetails.cargo2KL, QUANTITY_UNIT.KL);
    return _loadablePlanCommingleCargoDetails;
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
  *
  * Convert Commingle Quantity to other units
  * @returns {number}
  * @param { ILoadableQuantityCargo } _loadableQuantityDetails
  * @param { QUANTITY_UNIT } unitTo
  * @param { string } key
  */
  convertQuantityCommingle(loadablePlanCommingleCargoDetails: ILoadableQuantityCommingleCargo, unitTo: QUANTITY_UNIT, key: string) {
    return this.quantityPipe.transform(loadablePlanCommingleCargoDetails[key], this.baseUnit, unitTo, loadablePlanCommingleCargoDetails.api, loadablePlanCommingleCargoDetails.temp)
  }

    /**
 * Method for setting ports grid columns
 *
 * @returns {IDataTableColumn[]}
 * @memberof DischargeStudyDetailsTransformationService
 */
  getPortDatatableColumns(permission: IPermission, portEtaEtdPermission: IPermission, dischargeStudyStatusId: any, voyageStatusId: VOYAGE_STATUS): IDataTableColumn[] {
    const minDate = new Date();
    let columns: IDataTableColumn[] = [
      {
        field: 'slNo',
        header: 'PORT_ORDER',
        fieldType: DATATABLE_FIELD_TYPE.SLNO,
        sortable: false,
        fieldHeaderClass: 'column-portOrder',
        fieldClass: 'sl'
      },
      {
        field: 'port',
        header: 'DISCHARGE_STUDY_PORT_ORDER',
        fieldType: DATATABLE_FIELD_TYPE.SELECT,
        filter: true,
        filterPlaceholder: 'SEARCH_PORT',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        listName: 'portList',
        listFilter: true,
        sortable: false,
        sortField: 'port.value.name',
        filterField: 'port.value.name',
        fieldOptionLabel: 'name',
        fieldPlaceholder: 'SELECT_PORT',
        virtualScroll: true,
        errorMessages: {
          'required': 'PORT_FIELD_REQUIRED_ERROR',
          'duplicate': 'PORT_FIELD_DUPLICATE_ERROR',
          'transitDuplicate': 'PORT_TRANSIT_DUPLICATE_ERROR'
        }
      },
      {
        field: 'portcode',
        header: 'DISCHARGE_STUDY_PORT_CODE',
        fieldType: DATATABLE_FIELD_TYPE.TEXT,
        filter: true,
        filterPlaceholder: 'SEARCH_PORT_CODE',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'portcode.value',
        fieldPlaceholder: 'PORT_CODE',
        errorMessages: {
          'required': 'PORT_CODE_REQUIRED_ERROR'
        }
      },
      {
        field: 'operation',
        header: 'DISCHARGE_STUDY_OPERATIONS',
        fieldType: DATATABLE_FIELD_TYPE.SELECT,
        filter: true,
        filterPlaceholder: 'SEARCH_PORT_OPERATIONS',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        listName: 'operationList',
        filterField: 'operation.value.operationName',
        fieldOptionLabel: 'operationName',
        fieldPlaceholder: 'SELECT_OPERATION',
        errorMessages: {
          'required': 'PORT_OPERATIONS_REQUIRED_ERROR',
          'duplicate': 'PORT_FIELD_DUPLICATE_ERROR',
          'transitDuplicate': 'PORT_TRANSIT_DUPLICATE_ERROR'
        }
      },
      {
        field: 'seaWaterDensity',
        header: 'DISCHARGE_STUDY_WATER_DENSITY',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        numberFormat: AppConfigurationService.settings?.sgNumberFormat,
        filter: true,
        filterPlaceholder: 'SEARCH_PORT_WATER_DENSITY',
        filterType: DATATABLE_FILTER_TYPE.NUMBER,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'seaWaterDensity.value',
        fieldPlaceholder: 'ENTER_WATER_DENSITY',
        errorMessages: {
          'required': 'PORT_WATER_DENSITY_REQUIRED_ERROR',
          'min': 'PORT_WATER_DENSITY_MIN_ERROR'
        }
      },
      {
        field: 'maxDraft',
        header: 'DISCHARGE_STUDY_MAX_DRAFT',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        filter: true,
        filterPlaceholder: 'SEARCH_PORT_MAX_DRAFT',
        filterType: DATATABLE_FILTER_TYPE.NUMBER,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'maxDraft.value',
        fieldPlaceholder: 'ENTER_MAX_DRAFT',
        numberFormat: '1.2-2',
        errorMessages: {
          'required': 'PORT_MAX_DRAFT_REQUIRED_ERROR',
          'min': 'PORT_MAX_DRAFT_MIN_ERROR'
        }
      },
      {
        field: 'maxAirDraft',
        header: 'DISCHARGE_STUDY_MAX_AIR_DRAFT',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        filter: true,
        filterPlaceholder: 'SEARCH_PORT_MAX_AIR_DRAFT',
        filterType: DATATABLE_FILTER_TYPE.NUMBER,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'maxAirDraft.value',
        fieldPlaceholder: 'ENTER_MAX_AIR_DRAFT',
        numberFormat: '1.2-2',
        errorMessages: {
          'required': 'PORT_MAX_AIR_DRAFT_REQUIRED_ERROR',
          'min': 'PORT_MAX_AIR_DRAFT_MIN_ERROR'
        }
      },

    ];

    if (portEtaEtdPermission?.view || portEtaEtdPermission?.view === undefined) {
      const etaEtd = [
        {
          field: 'eta',
          header: 'DISCHARGE_STUDY_ETA',
          fieldHeaderClass: 'column-eta',
          fieldType: DATATABLE_FIELD_TYPE.DATETIME,
          filter: true,
          filterPlaceholder: 'SEARCH_PORT_ETA',
          filterType: DATATABLE_FILTER_TYPE.DATE,
          filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
          filterField: 'eta.value',
          fieldPlaceholder: 'CHOOSE_ETA',
          dateFormat: this.timeZoneTransformationService.getMappedConfigurationDateFormat(AppConfigurationService.settings?.dateFormat),
          minDate: minDate,
          fieldClass: 'eta',
          fieldHeaderTooltipIcon: 'pi-info-circle',
          fieldHeaderTooltipText: 'PORT_TIME_ZONE_NOTIFICATION',
          errorMessages: {
            'required': 'PORT_ETA_REQUIRED_ERROR',
            'notInRange': 'PORT_ETA_NOT_IN_DATE_RANGE',
            'failedCompare': 'PORT_ETA_COMPARE_ERROR',
            'etaFailed': 'PORT_ETA_COMAPRE_WITH_ETD_ERROR'
          }
        },
        {
          field: 'etd',
          header: 'DISCHARGE_STUDY_ETD',
          fieldHeaderClass: 'column-etd',
          fieldType: DATATABLE_FIELD_TYPE.DATETIME,
          filter: true,
          filterPlaceholder: 'SEARCH_PORT_ETD',
          filterType: DATATABLE_FILTER_TYPE.DATE,
          filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
          filterField: 'etd.value',
          fieldPlaceholder: 'CHOOSE_ETD',
          minDate: minDate,
          dateFormat: this.timeZoneTransformationService.getMappedConfigurationDateFormat(AppConfigurationService.settings?.dateFormat),
          fieldClass: 'etd',
          fieldHeaderTooltipIcon: 'pi-info-circle',
          fieldHeaderTooltipText: 'PORT_TIME_ZONE_NOTIFICATION',
          errorMessages: {
            'required': 'PORT_ETD_REQUIRED_ERROR',
            'notInRange': 'PORT_ETD_NOT_IN_DATE_RANGE',
            'failedCompare': 'PORT_ETD_COMPARE_ERROR',
            'etdFailed': 'PORT_ETD_COMAPRE_WITH_ETA_ERROR'
          }
        }
      ]
      columns = [...columns, ...etaEtd];
    }

    if (permission &&  [DISCHARGE_STUDY_STATUS.PLAN_PENDING, DISCHARGE_STUDY_STATUS.PLAN_NO_SOLUTION, DISCHARGE_STUDY_STATUS.PLAN_ERROR].includes(dischargeStudyStatusId) && ![VOYAGE_STATUS.CLOSE].includes(voyageStatusId)) {
      const actions: DATATABLE_ACTION[] = [];
      if (permission?.add) {
        actions.push(DATATABLE_ACTION.SAVE);
      }
      if (permission?.delete) {
        actions.push(DATATABLE_ACTION.DELETE);
      }
      const action: IDataTableColumn = {
        field: 'actions',
        header: '',
        fieldHeaderClass: 'column-actions',
        fieldType: DATATABLE_FIELD_TYPE.ACTION,
        actions: actions
      };
      columns = [...columns, action];
    }
    return columns;
  }
  
    /**
 * Method for formating cargo nomination data
 *
 * @param {IPortsValueObject} ports
 * @returns {IPortsValueObject}
 * @memberof DischargeStudyDetailsTransformationService
 */
  formatPorts(ports: IPortsValueObject): IPortsValueObject {
    ports.storeKey = ports.storeKey ?? uuid4();
    return ports;
  }

    /**
  * Method for converting from port value object model
  *
  * @param {IPortsValueObject} cargoNomination
  * @returns {IPortList}
  * @memberof DischargeStudyDetailsTransformationService
  */
  getPortAsValue(port: IPortsValueObject): IPortList {
    const _ports = <IPortList>{};
    for (const key in port) {
      if (Object.prototype.hasOwnProperty.call(port, key)) {
        if (key === 'port') {
          _ports.portId = port[key].value?.id;
        } else if (key === 'operation') {
          _ports.operationId = port[key].value?.id;
        }  else if (key === 'eta') {
          const newEta = moment(port.eta?.value?.slice(0, 17)).format('DD-MM-YYYY HH:mm');
          port.eta?.value ? _ports.eta = this.timeZoneTransformationService.revertZoneTimetoUTC(newEta, port.port?.value?.timezoneOffsetVal) : _ports.eta = "";
        } else if (key === 'etd') {
          const newEtd = moment(port.etd?.value?.slice(0, 17)).format('DD-MM-YYYY HH:mm');
          port.etd?.value ? _ports.etd = this.timeZoneTransformationService.revertZoneTimetoUTC(newEtd, port.port?.value?.timezoneOffsetVal) : _ports.etd = "";
        }
        else {
          if (key !== 'layCanFrom' && key !== 'layCanTo') {
            _ports[key] = port[key] && typeof port[key] === 'object' ? port[key].value : port[key];
          }
        }
      }
    }
    return _ports;
  }

  /** Set port grid complete status */
  setPortValidity(isValid: boolean) {
    this._portValiditySource.next(isValid);
  }

  /**
  * Method for initializing ports row
  *
  * @private
  * @param {boolean} isAdd
  * @returns {boolean}
  * @memberof DischargeStudyDetailsTransformationService
  */
  isEtaEtdViewable(portEtaEtdPermission: IPermission, isAdd: boolean) {
    return (portEtaEtdPermission?.view || portEtaEtdPermission?.view === undefined) &&
      ((isAdd && (portEtaEtdPermission?.add || portEtaEtdPermission?.add === undefined)) ||
        (!isAdd && (portEtaEtdPermission?.edit || portEtaEtdPermission?.edit === undefined))
      ) ? true : false;
  }
  
    /**
   * Method for converting ports data to value object model
   *
   * @param {IPortList} port
   * @param {boolean} [isNewValue=true]
   * @param {IPortAllDropdownData} listData
   * @returns {IPortsValueObject}
   * @memberof DischargeStudyDetailsTransformationService
   */
     getPortAsValueObject(port: IPortList, isNewValue = true, isEditable = true, listData: IPortAllDropdownData, portEtaEtdPermission: IPermission): IPortsValueObject {
      const _port = <IPortsValueObject>{};
      const isEtaEtadEdtitable = this.isEtaEtdViewable(portEtaEtdPermission, isNewValue);
      const portObj: IPort = listData.portList.find(portData => portData.id === port.portId);
      const operationObj: IOperations = listData.operationListComplete.find(operation => operation.id === port.operationId);
      const isEdit = operationObj ? !(operationObj.id === OPERATIONS.LOADING || operationObj.id === OPERATIONS.DISCHARGING) : true;
      
      _port.id = port.id;
      _port.portOrder = port.portOrder;
      _port.portTimezoneId = port.portTimezoneId;
      _port.portcode = new ValueObject<string>(portObj?.code, true, false, false, false);
      _port.port = new ValueObject<IPort>(portObj, true, isNewValue, false, isEdit && isEditable);
      _port.operation = new ValueObject<IOperations>(operationObj, true, isNewValue, false, isEdit && isEditable);
      _port.seaWaterDensity = new ValueObject<number>(port.seaWaterDensity, true, isNewValue, false, isEditable);
      _port.maxDraft = new ValueObject<number>(port.maxDraft, true, isNewValue, false, isEditable);
      _port.maxAirDraft = new ValueObject<number>(port.maxAirDraft, true, isNewValue, false, isEditable);
      _port.eta = new ValueObject<string>(port.eta, true, isNewValue, false, isEtaEtadEdtitable);
      _port.etd = new ValueObject<string>(port.etd, true, isNewValue, false, isEtaEtadEdtitable);
      _port.isAdd = isNewValue;
      _port.isActionsEnabled = isEdit;
      _port.isDelete = false;
      return _port;
    }

 /**
 * Method for emitting observable for update port table
 *
 * @memberof DischargeStudyDetailsTransformationService
 */
  portUpdated() {
    this._portUpdate.next();
  }

  /**
 * Method for emitting observable for add port
 *
 * @memberof DischargeStudyDetailsTransformationService
 */
  addPort() {
    this._addPortSource.next();
  }


    /**
   * Method to get OHG grid colums
   *
   * @returns {IDataTableColumn[]}
   * @memberof DischargeStudyDetailsTransformationService
   */
     getOHQDatatableColumns(): IDataTableColumn[] {
      return [
        {
          field: 'slNo',
          header: 'DISCHARGE_OHQ_SL',
          fieldType: DATATABLE_FIELD_TYPE.SLNO,
          sortable: false,
          fieldHeaderClass: 'column-sl',
          fieldClass: 'sl'
        },
        {
          field: 'fuelTypeName',
          header: 'DISCHARGE_OHQ_FUEL_TYPE',
          editable: false,
          filter: true,
          filterField: 'fuelTypeId',
          filterListName: 'fuelTypes',
          filterMatchMode: DATATABLE_FILTER_MATCHMODE.EQUALS,
          filterType: DATATABLE_FILTER_TYPE.SELECT,
          filterPlaceholder: 'DISCHARGE_OHQ_SEARCH_FUEL_TYPE',
          fieldHeaderClass: 'column-fuel-type'
        },
        {
          field: 'tankName',
          header: 'DISCHARGE_OHQ_TANK',
          editable: false,
          filter: true,
          filterField: 'tankName',
          filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
          filterType: DATATABLE_FILTER_TYPE.TEXT,
          filterPlaceholder: 'DISCHARGE_OHQ_SEARCH_TANK'
        },
        {
          field: 'density',
          header: 'DISCHARGE_OHQ_DENSITY',
          fieldType: DATATABLE_FIELD_TYPE.NUMBER,
          fieldPlaceholder: 'OHQ_PLACEHOLDER_DENSITY',
          filter: true,
          filterField: 'density.value',
          filterMatchMode: DATATABLE_FILTER_MATCHMODE.STARTSWITH,
          filterType: DATATABLE_FILTER_TYPE.NUMBER,
          filterPlaceholder: 'DISCHARGE_OHQ_SEARCH_DENSITY',
          fieldHeaderClass: 'column-density',
          errorMessages: {
            'required': 'OHQ_VALUE_REQUIRED',
            'min': 'OHQ_MIN_VALUE',
            'groupTotal': 'OHQ_GROUP_TOTAL',
            'pattern': 'OHQ_PATTERN_ERROR'
          }
        },
        {
          field: 'arrivalQuantity',
          header: 'DISCHARGE_OHQ_ARRIVAL_QUANTITY',
          fieldType: DATATABLE_FIELD_TYPE.NUMBER,
          fieldPlaceholder: 'DISCHARGE_OHQ_SEARCH_QUANTITY',
          filter: true,
          filterField: 'arrivalQuantity.value',
          filterMatchMode: DATATABLE_FILTER_MATCHMODE.STARTSWITH,
          filterType: DATATABLE_FILTER_TYPE.NUMBER,
          filterPlaceholder: 'OHQ_SEARCH_QUANTITY',
          fieldHeaderClass: 'column-weight',
          errorMessages: {
            'required': 'OHQ_VALUE_REQUIRED',
            'min': 'OHQ_MIN_VALUE',
            'groupTotal': 'OHQ_GROUP_TOTAL',
            'pattern': 'OHQ_PATTERN_ERROR',
            'max': "OHQ_VOLUME_LOADED_EXCEED_FULLCAPACITY"
          }
        },
        {
          field: 'departureQuantity',
          header: 'DISCHARGE_OHQ_DEPARTURE_QUANTITY',
          fieldType: DATATABLE_FIELD_TYPE.NUMBER,
          fieldPlaceholder: 'DISCHARGE_OHQ_SEARCH_QUANTITY',
          filter: true,
          filterField: 'departureQuantity.value',
          filterMatchMode: DATATABLE_FILTER_MATCHMODE.STARTSWITH,
          filterType: DATATABLE_FILTER_TYPE.NUMBER,
          fieldHeaderClass: 'column-weight',
          filterPlaceholder: 'OHQ_SEARCH_QUANTITY',
          errorMessages: {
            'required': 'OHQ_VALUE_REQUIRED',
            'min': 'OHQ_MIN_VALUE',
            'groupTotal': 'OHQ_GROUP_TOTAL',
            'pattern': 'OHQ_PATTERN_ERROR',
            'max': "OHQ_VOLUME_LOADED_EXCEED_FULLCAPACITY",
          }
        }
      ]
    }
  
  /**
   * Method to convert ohq tank details to value object
   *
   * @param {IPortOHQTankDetail} ohqTankDetail
   * @param {boolean} [isNewValue=true]
   * @returns {IPortOHQTankDetailValueObject}
   * @memberof DischargeStudyDetailsTransformationService
   */
   getOHQTankDetailsAsValueObject(ohqTankDetail: IPortOHQTankDetail, isNewValue = true): IPortOHQTankDetailValueObject {
    const _ohqTankDetail = <IPortOHQTankDetailValueObject>{};
    _ohqTankDetail.id = ohqTankDetail?.id;
    _ohqTankDetail.portRotationId = ohqTankDetail?.portRotationId;
    _ohqTankDetail.fuelTypeName = ohqTankDetail?.fuelTypeName;
    _ohqTankDetail.fuelTypeId = ohqTankDetail?.fuelTypeId;
    _ohqTankDetail.tankId = ohqTankDetail?.tankId;
    _ohqTankDetail.tankName = ohqTankDetail?.tankName;
    _ohqTankDetail.colorCode = ohqTankDetail?.colorCode;
    _ohqTankDetail.fullCapacityCubm = ohqTankDetail?.fullCapacityCubm;
    _ohqTankDetail.density = new ValueObject<number>(ohqTankDetail.density, true, isNewValue);
    _ohqTankDetail.arrivalQuantity = new ValueObject<number>(ohqTankDetail.arrivalQuantity, true, isNewValue);
    _ohqTankDetail.departureQuantity = new ValueObject<number>(ohqTankDetail.departureQuantity, true, isNewValue);
    _ohqTankDetail.arrivalVolume = ohqTankDetail?.arrivalVolume;
    _ohqTankDetail.departureVolume = ohqTankDetail?.departureVolume;

    return _ohqTankDetail;
  }

  /**
   * Method for formating ohq tank details
   *
   * @param {IPortOHQTankDetailValueObject} ohqTankDetail
   * @returns {IPortOHQTankDetailValueObject}
   * @memberof DischargeStudyDetailsTransformationService
   */
  formatOHQTankDetail(ohqTankDetail: IPortOHQTankDetailValueObject): IPortOHQTankDetailValueObject {
    ohqTankDetail.storeKey = ohqTankDetail.storeKey ?? uuid4();
    return ohqTankDetail;
  }

  /**
   * Method for converting ohq data as value
   *
   * @param {IPortOHQTankDetailValueObject} ohqTankDetail
   * @returns {IPortOHQTankDetail}
   * @memberof DischargeStudyDetailsTransformationService
   */
  getOHQTankDetailAsValue(ohqTankDetail: IPortOHQTankDetailValueObject): IPortOHQTankDetail {
    const _ohqTankDetail: IPortOHQTankDetail = <IPortOHQTankDetail>{};
    for (const key in ohqTankDetail) {
      if (Object.prototype.hasOwnProperty.call(ohqTankDetail, key)) {
        _ohqTankDetail[key] = ohqTankDetail[key]?.value ?? ohqTankDetail[key];
      }
    }

    return _ohqTankDetail;
  }

  /**
   * Set ohq grid complete status
   *
   * @param {boolean} isValid
   * @memberof DischargeStudyDetailsTransformationService
   */
  setOHQValidity(ohqPorts: IDischargeOHQStatus[]) {
    this.ohqPortsValidity = ohqPorts;
    if (!ohqPorts.length) {
      this._ohqValiditySource.next(false);
      return
    }
    for (let i = 0; i < ohqPorts.length; i++) {
      if (!ohqPorts[i].isPortRotationOhqComplete) {
        this._ohqValiditySource.next(false);
        return
      }
    }
    this._ohqValiditySource.next(true);
  }

  /**
   * Set ohq port complete status
   *
   * @param {boolean} isValid
   * @memberof DischargeStudyDetailsTransformationService
   */
  setOHQPortValidity(id: number, isPortRotationOhqComplete: boolean) {
    if (typeof isPortRotationOhqComplete !== 'undefined') {
      const i = this.ohqPortsValidity.findIndex(port => port.id === id);
      if (i >= 0) {
        this.ohqPortsValidity[i].isPortRotationOhqComplete = isPortRotationOhqComplete;
        this.setOHQValidity(this.ohqPortsValidity)
      }
    }
  }

  /**
   * Get ohq port complete status
   *
   * @param {boolean} isValid
   * @memberof DischargeStudyDetailsTransformationService
   */
  getOHQPortValidity(id: number): boolean {
    const i = this.ohqPortsValidity.findIndex(port => port.id === id);
    if (i >= 0) {
      return this.ohqPortsValidity[i].isPortRotationOhqComplete;
    } else {
      return false;
    }
  }

  /**
  * Add missing ports from the provided array
  *
  * @param {boolean} isValid
  * @memberof LoadableStudyDetailsTransformationService
  */
  addMissingOhqPorts(ohqPorts: IPort[]) {
    ohqPorts.forEach(ohqPort => {
      const i = this.ohqPortsValidity.findIndex(port => port.id === ohqPort.id);
      if (i < 0) {
        this.ohqPortsValidity.push({ id: ohqPort.id, isPortRotationOhqComplete: false })
        this._ohqValiditySource.next(false);
      }
    })
  }

  /**
* Method for setting commingle details grid columns
*
* @returns {IDataTableColumn[]}
* @memberof DischargeStudyDetailsTransformationService
*/
getDischargeStudyCargoDatatableColumns(): IDataTableColumn[] {
  return [
    {
      field: 'slNo',
      header: 'DISCHARGE_STUDY_DISCHARGE_SL',
      fieldType: DATATABLE_FIELD_TYPE.SLNO,
    },
    {
      field: 'color',
      header: 'DISCHARGE_STUDY_DISCHARGE_COLOR',
      fieldType: DATATABLE_FIELD_TYPE.COLORPICKER,
      editable: false
    },
    {
      field: 'cargo',
      header: 'DISCHARGE_STUDY_DISCHARGE_CARGO_NAME',
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
      header: 'DISCHARGE_STUDY_DISCHARGE_BBLS',
      fieldType: DATATABLE_FIELD_TYPE.NUMBER,
      editable: false,
      unit: QUANTITY_UNIT.BBLS,
      numberType: 'quantity'
    },
    {
      field: 'mt',
      header: 'DISCHARGE_STUDY_DISCHARGE_MT',
      fieldType: DATATABLE_FIELD_TYPE.NUMBER,
      unit: QUANTITY_UNIT.MT,
      editable: false,
      numberType: 'quantity'
    },
    {
      field: 'kl',
      header: 'DISCHARGE_STUDY_DISCHARGE_KL',
      fieldType: DATATABLE_FIELD_TYPE.NUMBER,
      unit: QUANTITY_UNIT.KL,
      numberType: 'quantity',
      fieldPlaceholder: 'DISCHARGE_STUDY_BACK_LOADING_KL',
      editable: true,
      errorMessages: {
        'required': 'DISCHARGE_STUDY_FIELD_REQUIRED_ERROR',
        'greaterThanTotalQuantity': 'SUM_OF_QUANTITY_GREATER_THAN_TOTAL_QUANTITY',
        'quantityNotEqual': 'QUANTITY_NOT_EQUAL'
      }
    },
    {
      field: 'mode',
      header: 'DISCHARGE_STUDY_DISCHARGE_MODE',
      fieldType: DATATABLE_FIELD_TYPE.SELECT,
      editable: true,
      listName: 'mode',
      fieldOptionLabel: 'name'
    }
  ]
}

  /**
* Method for setting commingle details grid columns
*
* @returns {IDataTableColumn[]}
* @memberof DischargeStudyDetailsTransformationService
*/
getDischargeStudyBackLoadingDatatableColumns(): IDataTableColumn[] {
  let columns:IDataTableColumn[] = [
    {
      field: 'slNo',
      header: 'DISCHARGE_STUDY_DISCHARGE_SL_BACK_LOADING',
      fieldType: DATATABLE_FIELD_TYPE.SLNO,
    },
    {
      field: 'color',
      header: 'DISCHARGE_STUDY_DISCHARGE_COLOR_BACK_LOADING',
      fieldType: DATATABLE_FIELD_TYPE.COLORPICKER,
      errorMessages: {
        'required': 'DISCHARGE_STUDY_FIELD_REQUIRED_ERROR',
        'duplicateColor': 'CARGO_NOMINATION_COLOR_ALREADY_USED_ERROR'
      }
    },
    {
      field: 'cargo',
      header: 'DISCHARGE_STUDY_DISCHARGE_CARGO_NAME_BACK_LOADING',
      fieldType: DATATABLE_FIELD_TYPE.SELECT,
      fieldPlaceholder: 'SEARCH_CARGO',
      listFilter: true,
      listName: 'cargoList',
      fieldOptionLabel: 'name',
      errorMessages: {
        'required': 'DISCHARGE_STUDY_FIELD_REQUIRED_ERROR'
      }
    },
    {
      field: 'abbreviation',
      header: 'ABBREVIATION',
      fieldType: DATATABLE_FIELD_TYPE.TEXT,
      fieldPlaceholder: 'ENTER_ABBREVIATION',
      errorMessages: {
        'required': 'DISCHARGE_STUDY_FIELD_REQUIRED_ERROR',
        'duplicateAbbrevation': 'DISCHARGE_STUDY_ABBREVIATION_ALREADY_USED_ERROR',
        'alphabetsOnly': 'CARGO_NOMINATION_FIELD_ALPHABETS_ONLY_ERROR',
        'maxlength': 'CARGO_NOMINATION_FIELD_ABBREVIATION_MAX_LENGTH_ERROR'
      }
    },
    {
      field: 'bbls',
      header: 'DISCHARGE_STUDY_DISCHARGE_BBLS_BACK_LOADING',
      fieldType: DATATABLE_FIELD_TYPE.NUMBER,
      unit: QUANTITY_UNIT.BBLS,
      numberType: 'quantity',
      fieldPlaceholder: 'DISCHARGE_STUDY_BACK_LOADING_BBLS',
      showTotal: true,
      editable: false,
      errorMessages: {
        'required': 'DISCHARGE_STUDY_FIELD_REQUIRED_ERROR'
      }
    },
    {
      field: 'mt',
      header: 'DISCHARGE_STUDY_DISCHARGE_MT_BACK_LOADING',
      fieldType: DATATABLE_FIELD_TYPE.NUMBER,
      unit: QUANTITY_UNIT.MT,
      showTotal: true,
      numberType: 'quantity',
      fieldPlaceholder: 'DISCHARGE_STUDY_BACK_LOADING_MT',
      editable: false,
      errorMessages: {
        'required': 'DISCHARGE_STUDY_FIELD_REQUIRED_ERROR'
      }
    },
    {
      field: 'kl',
      header: 'DISCHARGE_STUDY_DISCHARGE_KL_BACK_LOADING',
      fieldType: DATATABLE_FIELD_TYPE.NUMBER,
      unit: QUANTITY_UNIT.KL,
      showTotal: true,
      numberType: 'quantity',
      fieldPlaceholder: 'DISCHARGE_STUDY_BACK_LOADING_KL',
      errorMessages: {
        'required': 'DISCHARGE_STUDY_FIELD_REQUIRED_ERROR'
      }
    },
    {
      field: 'api',
      header: 'DISCHARGE_STUDY_DISCHARGE_API_BACK_LOADING',
      fieldType: DATATABLE_FIELD_TYPE.NUMBER,
      fieldPlaceholder: 'DISCHARGE_STUDY_BACK_LOADING_API',
      errorMessages: {
        'required': 'DISCHARGE_STUDY_FIELD_REQUIRED_ERROR'
      }
    },
    // {
    //   field: 'temp',
    //   header: 'DISCHARGE_STUDY_DISCHARGE_TEMP_BACK_LOADING',
    //   fieldType: DATATABLE_FIELD_TYPE.NUMBER,
    //   fieldPlaceholder: 'DISCHARGE_STUDY_BACK_LOADING_TEMP',
    //   errorMessages: {
    //     'required': 'DISCHARGE_STUDY_FIELD_REQUIRED_ERROR'
    //   }
    // }
  ]
  const actions: DATATABLE_ACTION[] = [];
  actions.push(DATATABLE_ACTION.DELETE);
  actions.push(DATATABLE_ACTION.SAVE);
  const action: IDataTableColumn = {
    field: 'actions',
    header: '',
    fieldHeaderClass: 'column-actions',
    fieldType: DATATABLE_FIELD_TYPE.ACTION,
    actions: actions
  };
  columns = [...columns, action];
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
   * @memberof DischargeStudyDetailsTransformationService
   */
    getPortDetailAsValueObject(portDetail: any, listData:IDischargeStudyDropdownData , isLastIndex : boolean, isNewValue = true): IPortDetailValueObject {
      const _portDetail = <IPortDetailValueObject>{};
      _portDetail.portName = portDetail.portName;
      _portDetail.instruction = portDetail.instruction ? portDetail.instruction : listData.instructions[0];
      _portDetail.draftRestriction = portDetail.draftRestriction;
      _portDetail.cargoDetail  = portDetail.cargo.map((cargoDetail) => {
        return this.getCargoDetailsAsValueObject(cargoDetail,listData);
      })
      const cow:IMode = listData.mode.find(modeDetails => modeDetails.id === portDetail.cow);
      _portDetail.cow = cow;
      _portDetail.percentage = portDetail.percentage;
      _portDetail.tank = listData?.tank.find((tankDetail) => {
        return tankDetail.id === portDetail.tankId
      })
      _portDetail.enableBackToLoading = portDetail.enableBackToLoading !== '' && !isLastIndex ? true : false;
      _portDetail.backLoadingDetails =  portDetail.backLoadingDetails.map((backLoadingDetail) => {
        return this.getBackLoadingDetailAsValueObject(backLoadingDetail, listData , isNewValue);
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
   * @memberof DischargeStudyDetailsTransformationService
   */
    getCargoDetailsAsValueObject(cargoDetail: any, listData:IDischargeStudyDropdownData,isNewValue = true) {
      const _cargoDetailValuObject = <IPortCargo>{};
      const mode = listData.mode.find(modeDetails => modeDetails.id === cargoDetail.mode);
      const cargoObj = listData.cargoList.find(cargo => cargo.id === cargoDetail.cargoId);
      const isKlEditable = mode?.id === 2 ? true : false;
      _cargoDetailValuObject.color = new ValueObject<string>(cargoDetail.color , true , false);
      _cargoDetailValuObject.bbls = new ValueObject<string>(isKlEditable ? cargoDetail.bbls : '-', true , false);
      _cargoDetailValuObject.cargo = new ValueObject<ICargo>(cargoObj,true , false);
      _cargoDetailValuObject.kl = new ValueObject<string>(isKlEditable ? cargoDetail.kl : '-', true , false , false , isKlEditable);
      _cargoDetailValuObject.maxKl = new ValueObject<number>(cargoDetail.maxKl, false , false);
      _cargoDetailValuObject.mt = new ValueObject<string>(isKlEditable ? cargoDetail.mt : '-', true , false);
      _cargoDetailValuObject.mode = new ValueObject<IMode>(mode , true , false);
      _cargoDetailValuObject.abbreviation = new ValueObject<string>(cargoDetail.abbreviation, true , false);
      _cargoDetailValuObject.api = new ValueObject<number>(cargoDetail.api);
      _cargoDetailValuObject.temp = new ValueObject<number>(cargoDetail.temp);
      return _cargoDetailValuObject;
    }

   /**
   * Method to convert backloading details to cargo value of object
   *
   * @param {*} backLoadingDetails
   * @param {IMode} mode
   * @memberof DischargeStudyDetailsTransformationService
   */
    setNewCargoInPortAsValuObject(backLoadingDetails: any , mode: IMode) {
      return { 
        maxKl: new ValueObject<string>(backLoadingDetails?.maxKl?.value ? backLoadingDetails.maxKl.value : 0, true , false),
        abbreviation: new ValueObject<string>(backLoadingDetails.abbreviation.value, true , false),
        color: new ValueObject<string>(backLoadingDetails.color.value, true , false),
        bbls: new ValueObject<number>(backLoadingDetails.bbls.value, true , false), 
        kl: new ValueObject<number>(backLoadingDetails.kl.value, true , false),
        mt: new ValueObject<number>(backLoadingDetails.mt.value, true , false), 
        cargo: new ValueObject<ICargo>(backLoadingDetails.cargo.value, true , false),
        mode: new ValueObject<IMode>(mode , true , false),
        api: new ValueObject<number>(backLoadingDetails.api.value, true , false),
        temp: new ValueObject<number>(backLoadingDetails.temp.value, true , false),
      }
    }
  
   /**
   * Method to convert loadable plan tank details to value object
   *
   * @param {*} portDetail
   * @param {ITankDetails} tankDetails
   * @param {boolean} [isNewValue=true]
   * @returns {IPortDetailValueObject}
   * @memberof DischargeStudyDetailsTransformationService
   */
    getBackLoadingDetailAsValueObject(backLoadingDetail: any, listData:IDischargeStudyDropdownData ,isNewValue = true): IBackLoadingDetails {
      const _backLoadingDetailDetail = <IBackLoadingDetails>{};
      const cargoObj: ICargo = backLoadingDetail.cargoId ? listData.cargoList.find(cargo => cargo.id === backLoadingDetail.cargoId) : null;
      _backLoadingDetailDetail.color = new ValueObject<string>(backLoadingDetail.color , true , isNewValue);
      _backLoadingDetailDetail.bbls = new ValueObject<number>(backLoadingDetail.bbls , true , false);
      _backLoadingDetailDetail.cargo = new ValueObject<ICargo>(cargoObj, true , isNewValue);
      _backLoadingDetailDetail.kl = new ValueObject<number>(backLoadingDetail.kl , true , isNewValue);
      _backLoadingDetailDetail.mt = new ValueObject<number>(backLoadingDetail.mt , true , false);
      _backLoadingDetailDetail.api = new ValueObject<string>(backLoadingDetail.api , true , isNewValue);
      _backLoadingDetailDetail.temp = new ValueObject<string>(backLoadingDetail.temp , true , isNewValue);
      _backLoadingDetailDetail.isDelete = true;
      _backLoadingDetailDetail.isAdd = isNewValue;
      _backLoadingDetailDetail.abbreviation = new ValueObject<string>(backLoadingDetail.abbreviation);
      _backLoadingDetailDetail.cargoAbbreviation = Math.random().toString();
      return _backLoadingDetailDetail;
    }

  /**
   *
   * Method to set validation message for add berth page.
   * @return {*} 
   * @memberof DischargeStudyDetailsTransformationService
   */

  setValidationMessageForDischargeStudy() {
    return {
      draftRestriction: {
        'whitespace': 'DISCHARGE_STUDY_FIELD_REQUIRED_ERROR'
      }
    }
  }

}

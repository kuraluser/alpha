import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { DATATABLE_ACTION, DATATABLE_FIELD_TYPE, DATATABLE_BUTTON , DATATABLE_FILTER_MATCHMODE, DATATABLE_FILTER_TYPE, IDataTableColumn } from '../../../shared/components/datatable/datatable.model';
import { ValueObject , ISubTotal } from '../../../shared/models/common.model';
import { CargoPlanningModule } from '../cargo-planning.module';
import { ICargo, ICargoNomination, ICargoNominationAllDropdownData, ICargoNominationValueObject, ILoadingPort, ILoadingPortValueObject, IMonths, IOHQPort, IPortAllDropdownData, IPortOBQListData, IPortOBQTankDetail, IPortOBQTankDetailValueObject, IPortOHQTankDetail, IPortOHQTankDetailValueObject, IPortsValueObject, ISegregation, OPERATIONS } from '../models/cargo-planning.model';
import { v4 as uuid4 } from 'uuid';
import { IPermission } from '../../../shared/models/user-profile.model';
import { ICargoGroup, ICommingleManual, ICommingleResponseModel, ICommingleValueObject, IPercentage } from '../models/commingle.model';
import { IOperations, IPort, IPortList, LOADABLE_STUDY_STATUS, VOYAGE_STATUS } from '../../core/models/common.model';
import { ILoadableOHQStatus } from '../models/loadable-study-list.model';
import * as moment from 'moment';
import { QUANTITY_UNIT } from '../../../shared/models/common.model';
import { TimeZoneTransformationService } from '../../../shared/services/time-zone-conversion/time-zone-transformation.service';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';


/**
 * Transformation Service for Lodable Study details module
 *
 * @export
 * @class LoadableStudyDetailsTransformationService
 */
@Injectable({
  providedIn: CargoPlanningModule
})
export class LoadableStudyDetailsTransformationService {
  // private fields
  private _addCargoNominationSource = new Subject();
  private _totalQuantityCargoNominationSource: Subject<number> = new Subject();
  private _cargoNominationValiditySource: Subject<boolean> = new Subject();
  private _addPortSource = new Subject();
  private _portValiditySource: Subject<boolean> = new Subject();
  private _ohqValiditySource: Subject<boolean> = new Subject();
  private _obqValiditySource: Subject<boolean> = new Subject();
  private _ohqUpdate: Subject<any> = new Subject();
  private OPERATIONS: OPERATIONS;

  // public fields
  addCargoNomination$ = this._addCargoNominationSource.asObservable();
  totalQuantityCargoNomination$ = this._totalQuantityCargoNominationSource.asObservable();
  cargoNominationValidity$ = this._cargoNominationValiditySource.asObservable();
  addPort$ = this._addPortSource.asObservable();
  portValidity$ = this._portValiditySource.asObservable();
  ohqValidity$ = this._ohqValiditySource.asObservable();
  obqValidity$ = this._obqValiditySource.asObservable();
  ohqUpdate$ = this._ohqUpdate.asObservable();
  ohqPortsValidity: { id: number; isPortRotationOhqComplete: boolean; }[];

  constructor(private timeZoneTransformationService: TimeZoneTransformationService) { }

  /**
   * Method for formating cargo nomination data
   *
   * @param {ICargoNominationValueObject} cargoNomination
   * @returns {ICargoNominationValueObject}
   * @memberof LoadableStudyDetailsTransformationService
   */
  formatCargoNomination(cargoNomination: ICargoNominationValueObject): ICargoNominationValueObject {
    cargoNomination.storeKey = cargoNomination.storeKey ?? uuid4();
    const unit = <QUANTITY_UNIT>localStorage.getItem('unit');
    if (cargoNomination.loadingPorts.value?.length) {
      cargoNomination.quantity.value = 0;
      cargoNomination.loadingPorts.value.map(port => {
        cargoNomination.quantity.value += Number(port.quantity);
      });
      cargoNomination.quantity.value = Number(cargoNomination.quantity.value);
      cargoNomination.loadingPortsNameArray = cargoNomination.loadingPorts.value.map(lport => lport.name);
      const portLength = cargoNomination.loadingPorts.value.length;
      if (portLength > 1) {
        cargoNomination.loadingPortsLabel = cargoNomination.loadingPorts.value[0].name + ' + ' + (cargoNomination.loadingPorts.value.length - 1);
      } else {
        cargoNomination.loadingPortsLabel = cargoNomination.loadingPorts.value[0].name;
      }
    } else {
      cargoNomination.loadingPortsLabel = "";
    }

    return cargoNomination;
  }

  /**
   * Method for converting cargonomination data to value object model
   *
   * @param {ICargoNomination} cargoNomination
   * @param {boolean} [isNewValue=true]
   * @param {ICargoNominationAllDropdownData} listData
   * @returns {ICargoNominationValueObject}
   * @memberof LoadableStudyDetailsTransformationService
   */
  getCargoNominationAsValueObject(cargoNomination: ICargoNomination, isNewValue = true, listData: ICargoNominationAllDropdownData): ICargoNominationValueObject {
    const _cargoNomination = <ICargoNominationValueObject>{};
    const cargoObj: ICargo = listData.cargoList.find(cargo => cargo.id === cargoNomination.cargoId);
    const segregationObj: ISegregation = listData.segregationList.find(segregation => segregation.id === cargoNomination.segregationId);
    const loadingPort: ILoadingPort[] = cargoNomination?.loadingPorts?.map(lport => {
      const portObj = listData.ports.find(port => port.id === lport.id);
      return { ...lport, name: portObj.name };
    });
    cargoNomination.quantity = 0;
    cargoNomination?.loadingPorts?.map(port => {
      cargoNomination.quantity += Number(port.quantity);
    });
    _cargoNomination.id = cargoNomination.id;
    _cargoNomination.priority = new ValueObject<number>(cargoNomination.priority, true, isNewValue, false);
    _cargoNomination.cargo = new ValueObject<ICargo>(cargoObj, true, isNewValue, false);
    _cargoNomination.color = new ValueObject<string>(cargoNomination.color, true, isNewValue, false);
    _cargoNomination.abbreviation = new ValueObject<string>(cargoNomination.abbreviation, true, isNewValue, false);
    _cargoNomination.loadingPorts = new ValueObject<ILoadingPort[]>(loadingPort, true, isNewValue, false);
    _cargoNomination.quantity = new ValueObject<number>(cargoNomination.quantity, true, isNewValue, false);
    _cargoNomination.api = new ValueObject<number>(cargoNomination.api, true, isNewValue, false);
    _cargoNomination.temperature = new ValueObject<number>(cargoNomination.temperature, true, isNewValue, false);
    _cargoNomination.minTolerance = new ValueObject<number>(cargoNomination.minTolerance, true, isNewValue, false);
    _cargoNomination.maxTolerance = new ValueObject<number>(cargoNomination.maxTolerance, true, isNewValue, false);
    _cargoNomination.segregation = new ValueObject<ISegregation>(segregationObj, true, isNewValue, false);
    _cargoNomination.isAdd = isNewValue;

    return _cargoNomination;
  }

  /**
   * Method for converting from cargonomination value object model
   *
   * @param {ICargoNominationValueObject} cargoNomination
   * @returns {ICargoNomination}
   * @memberof LoadableStudyDetailsTransformationService
   */
  getCargoNominationAsValue(cargoNomination: ICargoNominationValueObject): ICargoNomination {
    const _cargoNomination = <ICargoNomination>{};
    for (const key in cargoNomination) {
      if (Object.prototype.hasOwnProperty.call(cargoNomination, key)) {
        if (key === 'cargo') {
          _cargoNomination.cargoId = cargoNomination[key].value?.id;
        } else if (key === 'segregation') {
          _cargoNomination.segregationId = cargoNomination[key].value?.id;
        } else if (key === 'loadingPorts') {
          _cargoNomination.loadingPorts = cargoNomination[key]?.value;
        } else {
          _cargoNomination[key] = cargoNomination[key]?.hasOwnProperty('_isEditMode') ? cargoNomination[key]?.value : cargoNomination[key];
        }
      }
    }
    return _cargoNomination;
  }

  /**
   * Method for setting cargonomination grid columns
   *
   * @returns {IDataTableColumn[]}
   * @memberof LoadableStudyDetailsTransformationService
   */
  getCargoNominationDatatableColumns(permission: IPermission, loadableStudyStatusId: LOADABLE_STUDY_STATUS, voyageStatusId: VOYAGE_STATUS): IDataTableColumn[] {
    let columns: IDataTableColumn[] = [
      {
        field: 'slNo',
        header: 'SL',
        fieldType: DATATABLE_FIELD_TYPE.SLNO,
        sortable: false,
        fieldHeaderClass: 'column-sl',
        fieldClass: 'sl'
      },
      {
        field: 'priority',
        header: 'PRIORITY',
        fieldType: DATATABLE_FIELD_TYPE.SELECT,
        filter: true,
        filterPlaceholder: 'SEARCH_PRIORITY',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.EQUALS,
        listName: 'priorityList',
        filterField: 'priority.value',
        fieldPlaceholder: 'SELECT_PRIORITY',
        fieldHeaderClass: 'column-priority',
        errorMessages: {
          'required': 'CARGO_NOMINATION_FIELD_REQUIRED_ERROR'
        }
      },
      {
        field: 'color',
        header: 'COLOR',
        fieldType: DATATABLE_FIELD_TYPE.COLORPICKER,
        fieldHeaderClass: 'column-color',
        fieldClass: 'color',
        errorMessages: {
          'required': 'CARGO_NOMINATION_FIELD_REQUIRED_ERROR',
          'duplicateColor': 'CARGO_NOMINATION_COLOR_ALREADY_USED_ERROR'
        }
      },
      {
        field: 'cargo',
        header: 'CARGO',
        fieldType: DATATABLE_FIELD_TYPE.SELECT,
        filter: true,
        filterPlaceholder: 'SEARCH_CARGO',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        listName: 'cargoList',
        listFilter: true,
        filterField: 'cargo.value.name',
        fieldPlaceholder: 'SELECT_CARGO',
        fieldOptionLabel: 'name',
        errorMessages: {
          'required': 'CARGO_NOMINATION_FIELD_REQUIRED_ERROR'
        }
      },
      {
        field: 'abbreviation',
        header: 'ABBREVIATION',
        fieldType: DATATABLE_FIELD_TYPE.TEXT,
        filter: true,
        filterPlaceholder: 'SEARCH_ABBREVIATION',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'abbreviation.value',
        fieldPlaceholder: 'ENTER_ABBREVIATION',
        errorMessages: {
          'required': 'CARGO_NOMINATION_FIELD_REQUIRED_ERROR',
          'alphabetsOnly': 'CARGO_NOMINATION_FIELD_ALPHABETS_ONLY_ERROR',
          'maxlength': 'CARGO_NOMINATION_FIELD_ABBREVIATION_MAX_LENGTH_ERROR'
        }
      },
      {
        field: 'loadingPorts',
        header: 'LOADINGPORT',
        editable: false,
        fieldType: DATATABLE_FIELD_TYPE.ARRAY,
        filter: true,
        filterPlaceholder: 'SEARCH_PORT',
        filterType: DATATABLE_FILTER_TYPE.ARRAY,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        arrayLabelField: 'loadingPortsLabel',
        arrayFilterField: 'loadingPortsNameArray',
        fieldPlaceholder: 'SELECT_PORT',
        errorMessages: {
          'required': 'CARGO_NOMINATION_FIELD_REQUIRED_ERROR',
          'loadinPortCargoNotSelected': 'CARGO_NOT_SELECTED'
        }
      },
      {
        field: 'quantity',
        header: 'CARGO_QUANTITY',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        editable: false,
        filter: true,
        filterPlaceholder: 'SEARCH_QUANTITY',
        filterType: DATATABLE_FILTER_TYPE.NUMBER,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.STARTSWITH,
        filterField: 'quantity.value',
        fieldPlaceholder: 'ENTER_QUANTITY',
        showTotal: true,
        numberType: 'quantity',
        fieldHeaderClass: 'column-qty',
        errorMessages: {
          'required': 'CARGO_NOMINATION_FIELD_REQUIRED_ERROR'
        }
      },
      {
        field: 'maxTolerance',
        header: 'MAX_TOLERANCE',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        filter: true,
        filterPlaceholder: 'SEARCH_MAX_TOL',
        filterType: DATATABLE_FILTER_TYPE.NUMBER,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.STARTSWITH,
        fieldSuffix: '%',
        filterField: 'maxTolerance.value',
        fieldPlaceholder: 'ENTER_MAXTOLERANCE',
        fieldHeaderClass: 'column-max-tol',
        errorMessages: {
          'required': 'CARGO_NOMINATION_FIELD_REQUIRED_ERROR',
          'max': 'CARGO_NOMINATION_MAX_TOLERANCE_MAX_ERROR',
          'min': 'CARGO_NOMINATION_MAX_TOLERANCE_MIN_ERROR'
        }
      },
      {
        field: 'minTolerance',
        header: 'MIN_TOLERANCE',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        filter: true,
        filterPlaceholder: 'SEARCH_MIN_TOL',
        filterType: DATATABLE_FILTER_TYPE.NUMBER,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.STARTSWITH,
        fieldSuffix: '%',
        filterField: 'minTolerance.value',
        fieldPlaceholder: 'ENTER_MINTOLERANCE',
        fieldHeaderClass: 'column-min-tol',
        errorMessages: {
          'required': 'CARGO_NOMINATION_FIELD_REQUIRED_ERROR',
          'max': 'CARGO_NOMINATION_MIN_TOLERANCE_MAX_ERROR',
          'min': 'CARGO_NOMINATION_MIN_TOLERANCE_MIN_ERROR'
        }
      },
      {
        field: 'api',
        header: 'API_EST',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        filter: true,
        filterPlaceholder: 'SEARCH_API_EST',
        filterType: DATATABLE_FILTER_TYPE.NUMBER,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.STARTSWITH,
        fieldValueIcon: 'icon-api-temp',
        filterField: 'api.value',
        fieldPlaceholder: 'ENTER_API_EST',
        fieldClass: 'api',
        fieldHeaderClass: 'column-api',
        numberFormat: '1.2-2',
        errorMessages: {
          'required': 'CARGO_NOMINATION_FIELD_REQUIRED_ERROR',
          'min': 'CARGO_NOMINATION_API_MIN_ERROR'
        }
      },
      {
        field: 'temperature',
        header: 'TEMP_EST',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        filter: true,
        filterPlaceholder: 'SEARCH_TEMP_EST',
        filterType: DATATABLE_FILTER_TYPE.NUMBER,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.STARTSWITH,
        fieldValueIcon: 'icon-api-temp',
        filterField: 'temperature.value',
        fieldPlaceholder: 'ENTER_TEMP_EST',
        fieldClass: 'temperature',
        fieldHeaderClass: 'column-temp',
        numberFormat: '1.2-2',
        errorMessages: {
          'required': 'CARGO_NOMINATION_FIELD_REQUIRED_ERROR'
        }
      },
      {
        field: 'segregation',
        header: 'SEGREGATION',
        fieldType: DATATABLE_FIELD_TYPE.SELECT,
        filter: true,
        filterPlaceholder: 'SEARCH_SEGREGATION',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.STARTSWITH,
        listName: 'segregationList',
        filterField: 'segregation.value.name',
        fieldPlaceholder: 'SELECT_SEGREGATION',
        fieldOptionLabel: 'name',
        fieldHeaderClass: 'column-segregation',
        errorMessages: {
          'required': 'CARGO_NOMINATION_FIELD_REQUIRED_ERROR'
        }
      }
    ];
    if (permission && [LOADABLE_STUDY_STATUS.PLAN_PENDING, LOADABLE_STUDY_STATUS.PLAN_NO_SOLUTION, LOADABLE_STUDY_STATUS.PLAN_ERROR].includes(loadableStudyStatusId) && ![VOYAGE_STATUS.CLOSE].includes(voyageStatusId)) {
      const actions: DATATABLE_ACTION[] = [];
      
      if (permission?.add) {
        const buttons = {
          field: 'buttons',
          fieldHeaderClass: 'column-save',
          header: '',
          fieldClass: 'text-center',
          fieldColumnClass: 'text-center',
          fieldType: DATATABLE_FIELD_TYPE.BUTTON,
          buttons: [
            {type: DATATABLE_BUTTON.SAVE_BUTTON , field: 'isAdd' , icons: '' , class: 'pi pi-save' , label: '' , tooltip: 'Save' , tooltipPosition: "top"}
          ]
        }
        columns = [...columns, buttons];
        actions.push(DATATABLE_ACTION.DUPLICATE);
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
   * Method for emitting observable for add cargonomination 
   *
   * @memberof LoadableStudyDetailsTransformationService
   */
  addCargoNomination() {
    this._addCargoNominationSource.next();
  }

  /**
   * Method for emitting observable for update ohq table
   *
   * @memberof LoadableStudyDetailsTransformationService
   */
  ohqUpdated(event) {
    this._ohqUpdate.next(event);
  }

  /**
   * Method for setting total quantity in cargo nomination page
   *
   * @param {number} totalQuantity
   * @memberof LoadableStudyDetailsTransformationService
   */
  setTotalQuantityCargoNomination(totalQuantity: number) {
    this._totalQuantityCargoNominationSource.next(totalQuantity);
  }

  /**
   * Method for setting loading ports grid column
   *
   * @returns {IDataTableColumn[]}
   * @memberof LoadableStudyDetailsTransformationService
   */
  getCargoNominationLoadingPortDatatableColumns(permission: IPermission, loadableStudyStatusId: LOADABLE_STUDY_STATUS, voyageStatusId: VOYAGE_STATUS): IDataTableColumn[] {
    let columns: IDataTableColumn[] = [
      {
        field: 'name',
        header: 'PORT',
        fieldType: DATATABLE_FIELD_TYPE.TEXT,
        editable: false
      },
      {
        field: 'quantity',
        header: 'QUANTITY',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        fieldPlaceholder: 'LOADING_PORT_QUANTITY_PLACEHOLDER',
        errorMessages: {
          'required': 'CARGO_NOMINATION_FIELD_REQUIRED_ERROR',
          'min': 'CARGO_NOMINATION_LOADING_PORT_MIN_ERROR',
          'pattern': 'CARGO_NOMINATION_LOADING_PORT_PATTERN_ERROR'
        }
      }
    ]

    if (permission && [LOADABLE_STUDY_STATUS.PLAN_PENDING, LOADABLE_STUDY_STATUS.PLAN_NO_SOLUTION, LOADABLE_STUDY_STATUS.PLAN_ERROR].includes(loadableStudyStatusId) && ![VOYAGE_STATUS.CLOSE].includes(voyageStatusId)) {
      const actions: DATATABLE_ACTION[] = [];
      if (permission?.delete) {
        actions.push(DATATABLE_ACTION.DELETE);
      }
      const action: IDataTableColumn = {
        field: 'actions',
        header: '',
        fieldType: DATATABLE_FIELD_TYPE.ACTION,
        actions: actions
      };
      columns = [...columns, action];
    }

    return columns;
  }

  /**
   * Method for api & Temp history grid
   *
   * @return {*}  {IDataTableColumn[]}
   * @memberof LoadableStudyDetailsTransformationService
   */
  getCargoNominationApiTempHistoryColumns(): IDataTableColumn[] {
    return [
      {
        field: 'loadingPortName',
        header: 'API_TEMP_HISTORY_POPUP_PAST_5_DETAILS_TABLE_PORT',
      },
      {
        field: 'loadedDate',
        header: 'API_TEMP_HISTORY_POPUP_PAST_5_DETAILS_TABLE_DATE',
      },
      {
        field: 'api',
        header: 'API_TEMP_HISTORY_POPUP_PAST_5_DETAILS_TABLE_API',
        numberFormat: '1.2-2'
      },
      {
        field: 'temperature',
        header: 'API_TEMP_HISTORY_POPUP_PAST_5_DETAILS_TABLE_TEMP',
        numberFormat: '1.2-2'
      }
    ]
  }

  /**
   * Method to return months.
   *
   * @return {*}  {IMonths[]}
   * @memberof LoadableStudyDetailsTransformationService
   */
  getMonthList(): IMonths[] {
    return [
      {
        id: 1,
        month: 'JANUARY'
      },
      {
        id: 2,
        month: 'FEBRUARY'
      },
      {
        id: 3,
        month: 'MARCH'
      },
      {
        id: 4,
        month: 'APRIL'
      },
      {
        id: 5,
        month: 'MAY'
      },
      {
        id: 6,
        month: 'JUNE'
      },
      {
        id: 7,
        month: 'JULY'
      },
      {
        id: 8,
        month: 'AUGUST'
      },
      {
        id: 9,
        month: 'SEPTEMBER'
      },
      {
        id: 10,
        month: 'OCTOBER'
      },
      {
        id: 11,
        month: 'NOVEMBER'
      },
      {
        id: 12,
        month: 'DECEMBER'
      }
    ]
  }

  /**
   * Method for converting loading port data as value object
   *
   * @param {ILoadingPort} loadingPort
   * @param {boolean} [isNewValue=true]
   * @returns {ILoadingPortValueObject}
   * @memberof LoadableStudyDetailsTransformationService
   */
  getCargoNominationLoadingPortAsValueObject(loadingPort: ILoadingPort, isNewValue = true): ILoadingPortValueObject {
    const _loadingPort = <ILoadingPortValueObject>{};
    _loadingPort.id = loadingPort?.id;
    _loadingPort.name = new ValueObject<string>(loadingPort.name, true, false, false);
    _loadingPort.quantity = new ValueObject<number>(loadingPort.quantity, true, isNewValue, false);
    _loadingPort.isAdd = isNewValue;

    return _loadingPort;
  }

  /**
   * Method for converting loading port data as value
   *
   * @param {ILoadingPortValueObject} loadingPort
   * @returns {ILoadingPort}
   * @memberof LoadableStudyDetailsTransformationService
   */
  getCargoNominationLoadingPortAsValue(loadingPort: ILoadingPortValueObject): ILoadingPort {
    const _loadingPorts: ILoadingPort = <ILoadingPort>{};
    for (const key in loadingPort) {
      if (Object.prototype.hasOwnProperty.call(loadingPort, key)) {
        _loadingPorts[key] = loadingPort[key]?.value ?? loadingPort[key];
      }
    }

    return _loadingPorts;
  }


  /** Set cargonomination grid complete status */
  setCargoNominationValidity(isValid: boolean) {
    this._cargoNominationValiditySource.next(isValid);
  }

  /**
   * Get sidepanel loadable studygrid column
   *
   * @returns
   * @memberof LoadableStudyDetailsTransformationService
   */
  getLoadableStudyGridColumns(permission: IPermission, voyageStatusId: VOYAGE_STATUS): IDataTableColumn[] {
    let columns: IDataTableColumn[] = [
      {
        field: 'name',
        header: 'LOADABLE_STUDY_DETAILS_LODABLE_STUDY_COLUMN_NAME',
        sortable: true,
        filter: true,
        filterPlaceholder: 'LOADABLE_STUDY_DETAILS_LODABLE_STUDY_FILTER_NAME',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'name',
      },
      {
        field: 'statusId',
        header: 'LOADABLE_STUDY_DETAILS_LODABLE_STUDY_COLUMN_STATUS',
        fieldType: DATATABLE_FIELD_TYPE.ICON,
        fieldValueIcon: 'icon-status',
        showTooltip: true,
        fieldValue: 'status'
      }
    ];
    if (permission && ![VOYAGE_STATUS.CLOSE].includes(voyageStatusId)) {
      const actions: DATATABLE_ACTION[] = [];
      if (permission?.delete) {
        actions.push(DATATABLE_ACTION.DELETE);
      }

      if (permission?.edit) {
        actions.push(DATATABLE_ACTION.EDIT);
      }

      if (permission?.add) {
        actions.push(DATATABLE_ACTION.DUPLICATE);
      }

      const action: IDataTableColumn = {
        field: 'actions',
        header: '',
        fieldType: DATATABLE_FIELD_TYPE.ACTION,
        actions: actions
      };
      columns = [...columns, action];
    }

    return columns;
  }

  /**
   * Method for converting ports data to value object model
   *
   * @param {IPortList} port
   * @param {boolean} [isNewValue=true]
   * @param {IPortAllDropdownData} listData
   * @returns {IPortsValueObject}
   * @memberof LoadableStudyDetailsTransformationService
   */
  getPortAsValueObject(port: IPortList, isNewValue = true, isEditable = true, listData: IPortAllDropdownData , portEtaEtdPermission: IPermission): IPortsValueObject {
    const _port = <IPortsValueObject>{};
    const isEtaEtadEdtitable = this.isEtaEtdViewable(portEtaEtdPermission,isNewValue);
    const portObj: IPort = listData.portList.find(portData => portData.id === port.portId);
    const operationObj: IOperations = listData.operationListComplete.find(operation => operation.id === port.operationId);
    const isEdit = operationObj ? !(operationObj.id === OPERATIONS.LOADING || operationObj.id === OPERATIONS.DISCHARGING) : true;
    const layCan = (port.layCanFrom && port.layCanTo) ? (port.layCanFrom + ' to ' + port.layCanTo) : '';
    _port.id = port.id;
    _port.portOrder = port.portOrder;
    _port.portTimezoneId = port.portTimezoneId;
    _port.portcode = new ValueObject<string>(portObj?.code, true, false, false, false);
    _port.port = new ValueObject<IPort>(portObj, true, isNewValue, false, isEdit && isEditable);
    _port.operation = new ValueObject<IOperations>(operationObj, true, isNewValue, false, isEdit && isEditable);
    _port.seaWaterDensity = new ValueObject<number>(port.seaWaterDensity, true, isNewValue, false, isEditable);
    _port.layCan = new ValueObject<any>(layCan, true, isNewValue, false, isEditable);
    _port.layCanFrom = new ValueObject<string>(port.layCanFrom?.trim(), true, isNewValue, false, isEditable);
    _port.layCanTo = new ValueObject<string>(port.layCanTo?.trim(), true, isNewValue, false, isEditable);
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
  * Method for initializing ports row
  *
  * @private
  * @param {boolean} isAdd
  * @returns {boolean}
  * @memberof LoadableStudyDetailsTransformationService
  */
  isEtaEtdViewable(portEtaEtdPermission: IPermission ,isAdd: boolean) {
    return (portEtaEtdPermission?.view || portEtaEtdPermission?.view === undefined) && 
    ( (isAdd && (portEtaEtdPermission?.add || portEtaEtdPermission?.add === undefined)) || 
      (!isAdd  && (portEtaEtdPermission?.edit || portEtaEtdPermission?.edit === undefined))
    ) ? true : false;
  }


  /**
 * Method for setting ports grid columns
 *
 * @returns {IDataTableColumn[]}
 * @memberof LoadableStudyDetailsTransformationService
 */
  getPortDatatableColumns(permission: IPermission, portEtaEtdPermission:IPermission , loadableStudyStatusId: LOADABLE_STUDY_STATUS, voyageStatusId: VOYAGE_STATUS): IDataTableColumn[] {
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
        header: 'PORT',
        fieldType: DATATABLE_FIELD_TYPE.SELECT,
        filter: true,
        filterPlaceholder: 'SEARCH_PORT',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        listName: 'portList',
        listFilter: true,
        sortable: true,
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
        header: 'PORT CODE',
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
        header: 'OPERATIONS',
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
        header: 'WATER DENSITY (T/M3)',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        numberFormat: '1.4-4' ,
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
        field: 'layCan',
        header: 'LAY-CAN',
        fieldHeaderClass: 'column-laycan',
        fieldType: DATATABLE_FIELD_TYPE.DATERANGE,
        filter: false,
        minDate: minDate,
        fieldPlaceholder: 'CHOOSE_LAY_CAN',
        fieldClass: 'lay-can',
        dateFormat: this.timeZoneTransformationService.getMappedConfigurationDateFormat(AppConfigurationService.settings?.dateFormat),
        fieldHeaderTooltipIcon: 'pi-info-circle',
        fieldHeaderTooltipText: 'PORT_TIME_ZONE_NOTIFICATION',
        errorMessages: {
          'required': 'PORT_LAY_CAN_REQUIRED_ERROR',
          'toDate': 'PORT_LAY_CAN_TO_DATE_ERROR',
          'datesEqual': 'PORT_LAY_CAN_DATE_EQUAL'
        }
      },
      {
        field: 'maxDraft',
        header: 'MAX-DRAFT (M)',
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
        header: 'MAX AIR DRAFT (M)',
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

    if(portEtaEtdPermission?.view || portEtaEtdPermission?.view === undefined) {
      const etaEtd = [
        {
          field: 'eta',
          header: 'ETA',
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
          header: 'ETD',
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

    if (permission && [LOADABLE_STUDY_STATUS.PLAN_PENDING, LOADABLE_STUDY_STATUS.PLAN_NO_SOLUTION, LOADABLE_STUDY_STATUS.PLAN_ERROR].includes(loadableStudyStatusId) && ![VOYAGE_STATUS.CLOSE].includes(voyageStatusId)) {
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
   * Method for emitting observable for add port
   *
   * @memberof LoadableStudyDetailsTransformationService
   */
  addPort() {
    this._addPortSource.next();
  }

  /** Set port grid complete status */
  setPortValidity(isValid: boolean) {
    this._portValiditySource.next(isValid);
  }

  /**
  * Method for converting from port value object model
  *
  * @param {IPortsValueObject} cargoNomination
  * @returns {IPortList}
  * @memberof LoadableStudyDetailsTransformationService
  */
  getPortAsValue(port: IPortsValueObject): IPortList {
    const _ports = <IPortList>{};
    for (const key in port) {
      if (Object.prototype.hasOwnProperty.call(port, key)) {
        if (key === 'port') {
          _ports.portId = port[key].value?.id;
        } else if (key === 'operation') {
          _ports.operationId = port[key].value?.id;
        } else if (key === 'layCan') {
          if (port[key].value) {
            const layCanFrom = moment(port[key].value.split('to')[0].trim(), AppConfigurationService.settings?.dateFormat.split(' ')[0]).endOf('d').format('DD-MM-YYYY HH:mm');
            _ports.layCanFrom = this.timeZoneTransformationService.revertZoneTimetoUTC(layCanFrom, port.port?.value?.timezoneOffsetVal)?.slice(0, 10);
            const layCanTo = moment(port[key].value.split('to')[1].trim(), AppConfigurationService.settings?.dateFormat.split(' ')[0]).startOf('d').format('DD-MM-YYYY HH:mm');
            _ports.layCanTo = this.timeZoneTransformationService.revertZoneTimetoUTC(layCanTo, port.port?.value?.timezoneOffsetVal)?.slice(0, 10) ;
          } else {
            _ports.layCanFrom = "";
            _ports.layCanTo = "";
          }
        } else if (key === 'eta') {
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

  /**
 * Method for formating cargo nomination data
 *
 * @param {IPortsValueObject} ports
 * @returns {IPortsValueObject}
 * @memberof LoadableStudyDetailsTransformationService
 */
  formatPorts(ports: IPortsValueObject): IPortsValueObject {
    ports.storeKey = ports.storeKey ?? uuid4();
    return ports;
  }

  /**
   * Method to get OHG grid colums
   *
   * @returns {IDataTableColumn[]}
   * @memberof LoadableStudyDetailsTransformationService
   */
  getOHQDatatableColumns(): IDataTableColumn[] {
    return [
      {
        field: 'slNo',
        header: 'OHQ_SL',
        fieldType: DATATABLE_FIELD_TYPE.SLNO,
        sortable: false,
        fieldHeaderClass: 'column-sl',
        fieldClass: 'sl'
      },
      {
        field: 'fuelTypeName',
        header: 'OHQ_FUEL_TYPE',
        editable: false,
        filter: true,
        filterField: 'fuelTypeId',
        filterListName: 'fuelTypes',
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.EQUALS,
        filterType: DATATABLE_FILTER_TYPE.SELECT,
        filterPlaceholder: 'OHQ_SEARCH_FUEL_TYPE',
        fieldHeaderClass: 'column-fuel-type'
      },
      {
        field: 'tankName',
        header: 'OHQ_TANK',
        editable: false,
        filter: true,
        filterField: 'tankName',
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterPlaceholder: 'OHQ_SEARCH_TANK'
      },
      {
        field: 'density',
        header: 'OHQ_DENSITY',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        fieldPlaceholder: 'OHQ_PLACEHOLDER_DENSITY',
        filter: true,
        filterField: 'density.value',
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.STARTSWITH,
        filterType: DATATABLE_FILTER_TYPE.NUMBER,
        filterPlaceholder: 'OHQ_SEARCH_DENSITY',
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
        header: 'OHQ_ARRIVAL_QUANTITY',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        fieldPlaceholder: 'OHQ_PLACEHOLDER_QUANTITY',
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
        header: 'OHQ_DEPARTURE_QUANTITY',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        fieldPlaceholder: 'OHQ_PLACEHOLDER_QUANTITY',
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
   * @memberof LoadableStudyDetailsTransformationService
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
   * @memberof LoadableStudyDetailsTransformationService
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
   * @memberof LoadableStudyDetailsTransformationService
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
   * @memberof LoadableStudyDetailsTransformationService
   */
  setOHQValidity(ohqPorts: ILoadableOHQStatus[]) {
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
   * @memberof LoadableStudyDetailsTransformationService
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
   * @memberof LoadableStudyDetailsTransformationService
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
   * Method to get Manual Commingle grid colums
   */
  getManualCommingleDatatableColumns(permission: IPermission, loadableStudyStatusId: LOADABLE_STUDY_STATUS, voyageStatusId: VOYAGE_STATUS): IDataTableColumn[] {
    let columns: IDataTableColumn[] = [
      {
        field: 'cargo1',
        header: 'COMMINGLE_CARGO1',
        fieldType: DATATABLE_FIELD_TYPE.SELECT,
        fieldComponentClass: 'index-9',
        listName: 'cargoNominationsCargo1',
        fieldOptionLabel: 'name',
        fieldPlaceholder: 'COMMINGLE_CARGO_1_DROP_DOWN_PLACE_HOLDER',
        fieldHeaderClass: 'column-cargo1',
        fieldClass: 'commingle-cargo1',
        showTemplate: true,
        errorMessages: {
          'required': 'COMMINGLE_CARGO_SELECT_ERROR',
          'duplicate': 'COMMINGLE_MANUAL_SAME_CARGO_VALIDATION'
        }
      },
      {
        field: 'cargo1Color',
        header: '',
        fieldHeaderClass: 'column-cargo1-color',
        fieldClass: 'commingle-cargo1-color',
        fieldType: DATATABLE_FIELD_TYPE.COLOR,

      },
      {
        field: 'cargo1IdPct',
        header: 'COMMINGLE_CARGO_PERCENTAGE',
        fieldType: DATATABLE_FIELD_TYPE.SELECT,
        fieldComponentClass: 'index-9',
        listName: 'percentage',
        fieldOptionLabel: 'name',
        fieldPlaceholder: 'COMMINGLE_PERCENTAGE_PLACEHOLDER',
        fieldHeaderClass: 'column-cargo1-pct',
        fieldClass: 'commingle-cargo1-pct',
        errorMessages: {
          'required': 'COMMINGLE_PERCENTAGE_SELECT_ERROR',
          'percentageTotal': 'COMMINGLE_MANUAL_PERCENTAGE_VALIDATION'
        }
      },
      {
        field: 'cargo2',
        header: 'COMMINGLE_CARGO2',
        fieldType: DATATABLE_FIELD_TYPE.SELECT,
        fieldComponentClass: 'index-9',
        listName: 'cargoNominationsCargo2',
        fieldOptionLabel: 'name',
        fieldPlaceholder: 'COMMINGLE_CARGO_2_DROP_DOWN_PLACE_HOLDER',
        fieldHeaderClass: 'column-cargo2',
        fieldClass: 'commingle-cargo2',
        showTemplate: true,
        errorMessages: {
          'required': 'COMMINGLE_CARGO_SELECT_ERROR',
          'duplicate': 'COMMINGLE_MANUAL_SAME_CARGO_VALIDATION'
        }
      },
      {
        field: 'cargo2Color',
        header: '',
        fieldHeaderClass: 'column-cargo2-color',
        fieldClass: 'commingle-cargo2-color',
        fieldType: DATATABLE_FIELD_TYPE.COLOR
      },
      {
        field: 'cargo2IdPct',
        header: 'COMMINGLE_CARGO_PERCENTAGE',
        fieldType: DATATABLE_FIELD_TYPE.SELECT,
        fieldComponentClass: 'index-9',
        listName: 'percentage',
        fieldOptionLabel: 'name',
        fieldPlaceholder: 'COMMINGLE_PERCENTAGE_PLACEHOLDER',
        fieldHeaderClass: 'column-cargo2-pct',
        fieldClass: 'commingle-cargo2-pct',
        errorMessages: {
          'required': 'COMMINGLE_PERCENTAGE_SELECT_ERROR',
          'percentageTotal': 'COMMINGLE_MANUAL_PERCENTAGE_VALIDATION'
        }
      },
      {
        field: 'quantity',
        header: 'COMMINGLE_QTY',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        fieldPlaceholder: 'COMMINGLE_QTY_PLACEHOLDER',
        fieldHeaderClass: 'column-quantity',
        fieldClass: 'commingle-quantity',
        errorMessages: {
          'required': 'COMMINGLE_CARGO_SELECT_ERROR',
          'max': 'COMMINGLE_QUANTITY_MAX_LIMIT',
          'min': 'COMMINGLE_MANUAL_QUANTITY_MIN_VALUE',
          'isMaxQuantity': 'COMMINGLE_QUANTITY_MAX_LIMIT'
        },
        numberType:'quantity'
      }
    ];

    if (permission && [LOADABLE_STUDY_STATUS.PLAN_PENDING, LOADABLE_STUDY_STATUS.PLAN_NO_SOLUTION, LOADABLE_STUDY_STATUS.PLAN_ERROR].includes(loadableStudyStatusId) && ![VOYAGE_STATUS.CLOSE].includes(voyageStatusId)) {
      const actions: DATATABLE_ACTION[] = [];
      if (permission?.delete) {
        actions.push(DATATABLE_ACTION.DELETE);
      }
      const action: IDataTableColumn = {
        field: 'actions',
        header: '',
        fieldType: DATATABLE_FIELD_TYPE.ACTION,
        actions: actions
      };
      columns = [...columns, action];
    }

    return columns;
  }
  /**
   * Method for converting commingle  data as value
   * @param commingle 
   */
  getCommingleAsValue(commingle: ICommingleValueObject): ICargoGroup {
    const _cargoList = <ICargoGroup>{};
    for (const key in commingle) {
      if (Object.prototype.hasOwnProperty.call(commingle, key)) {
        if (key === 'cargo1') {
          _cargoList.cargo1Id = commingle[key].value?.cargoId;
          _cargoList.cargoNomination1Id = commingle[key].value?.id
        } else if (key === 'cargo1pct') {
          _cargoList.cargo1pct = commingle[key].value;
        } else if (key === 'cargo2') {
          _cargoList.cargo2Id = commingle[key].value?.cargoId;
          _cargoList.cargoNomination2Id = commingle[key].value?.id
        } else if (key === 'cargo2pct') {
          _cargoList.cargo2pct = commingle[key].value;
        } else if (key === 'quantity') {
          _cargoList.quantity = commingle[key].value;
        }
      }
    }
    return _cargoList;
  }
  /**
   * Method to convert commingle data to value object model
   * @param commingleManual 
   * @param isNewValue 
   * @param isEditable 
   * @param listData 
   */
  getCommingleValueObject(commingleManual: ICargoGroup, isNewValue = true, isEditable = true, listData: ICommingleManual): ICommingleValueObject {
    const _commingleManual = <ICommingleValueObject>{};
    const cargo1Obj: ICargoNomination = listData.cargoNominationsCargo1.find(cargo1Data => cargo1Data.id === commingleManual.cargoNomination1Id);
    const cargo2Obj: ICargoNomination = listData.cargoNominationsCargo2.find(cargo2Data => cargo2Data.id === commingleManual.cargoNomination2Id);
    const cargo1IdPctObj: IPercentage = listData.percentage.find(percent1 => percent1.id === commingleManual.cargo1pct);
    const cargo2IdPctObj: IPercentage = listData.percentage.find(percent2 => percent2.id === commingleManual.cargo2pct);
    _commingleManual.cargo1 = new ValueObject<ICargoNomination>(cargo1Obj, true, isNewValue, false, isEditable);
    _commingleManual.cargo2 = new ValueObject<ICargoNomination>(cargo2Obj, true, isNewValue, false, isEditable);
    _commingleManual.cargo1Id = new ValueObject<number>(cargo1Obj?.cargoId, true, isNewValue, false, false);
    _commingleManual.cargo2Id = new ValueObject<number>(cargo2Obj?.cargoId, true, isNewValue, false, false);
    _commingleManual.cargo1Color = new ValueObject<string>(cargo1Obj?.color, true, isNewValue, false, isEditable);
    _commingleManual.cargo2Color = new ValueObject<string>(cargo2Obj?.color, true, isNewValue, false, isEditable);
    _commingleManual.cargo1pct = new ValueObject<number>(cargo1IdPctObj?.id, true, isNewValue, false, isEditable);
    _commingleManual.cargo2pct = new ValueObject<number>(cargo2IdPctObj?.id, true, isNewValue, false, isEditable);
    _commingleManual.cargo1IdPct = new ValueObject<IPercentage>(cargo1IdPctObj, true, isNewValue, false, isEditable);
    _commingleManual.cargo2IdPct = new ValueObject<IPercentage>(cargo2IdPctObj, true, isNewValue, false, isEditable);
    _commingleManual.quantity = new ValueObject<number>(commingleManual?.quantity, true, isNewValue, false, isEditable);
    return _commingleManual;
  }

  /**
  * Method to get OBQ grid colums
  *
  * @returns {IDataTableColumn[]}
  * @memberof LoadableStudyDetailsTransformationService
  */
  getOBQDatatableColumns(): IDataTableColumn[] {
    return [
      {
        field: 'slNo',
        header: 'OBQ_SL',
        fieldType: DATATABLE_FIELD_TYPE.SLNO,
        sortable: false,
        fieldHeaderClass: 'column-sl',
        fieldClass: 'sl'
      },
      {
        field: 'tankName',
        header: 'OBQ_TANK',
        editable: false,
        filter: true,
        filterField: 'tankName',
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterPlaceholder: 'OBQ_SEARCH_TANK'
      },
      {
        field: 'cargo',
        header: 'CARGO',
        editable: false,
        fieldType: DATATABLE_FIELD_TYPE.SELECT,
        filter: true,
        filterPlaceholder: 'SEARCH_CARGO',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        listName: 'cargoList',
        listFilter: true,
        filterField: 'cargo.value.name',
        fieldPlaceholder: 'SELECT_CARGO',
        fieldOptionLabel: 'name',
        errorMessages: {
          'required': 'OBQ_VALUE_REQUIRED'
        }
      },
      {
        field: 'api',
        header: 'OBQ_API',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        fieldPlaceholder: 'OBQ_PLACEHOLDER_API',
        filter: true,
        filterField: 'api.value',
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.STARTSWITH,
        filterType: DATATABLE_FILTER_TYPE.NUMBER,
        filterPlaceholder: 'OBQ_SEARCH_API',
        errorMessages: {
          'required': 'OBQ_VALUE_REQUIRED',
          'min': 'OBQ_MIN_VALUE',
          'groupTotal': 'OBQ_GROUP_TOTAL'
        }

      },
      {
        field: 'quantity',
        header: 'OBQ_QUANTITY',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        fieldPlaceholder: 'OBQ_PLACEHOLDER_QUANTITY',
        filter: true,
        filterField: 'quantity.value',
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.STARTSWITH,
        filterType: DATATABLE_FILTER_TYPE.NUMBER,
        filterPlaceholder: 'OBQ_SEARCH_QUANTITY',
        errorMessages: {
          'required': 'OBQ_VALUE_REQUIRED',
          'min': 'OBQ_MIN_VALUE',
          'groupTotal': 'OBQ_GROUP_TOTAL',
          'max': "OBQ_VOLUME_LOADED_EXCEED_FULLCAPACITY"
        },
        numberType: 'quantity'
      }
    ]
  }

  /**
 * Method to convert obq tank details to value object
 *
 * @param {IPortOBQTankDetail} obqTankDetail
 * @param {boolean} [isNewValue=true]
 * @returns {IPortOBQTankDetailValueObject}
 * @memberof LoadableStudyDetailsTransformationService
 */
  getOBQTankDetailsAsValueObject(obqTankDetail: IPortOBQTankDetail, isNewValue = true, listData: IPortOBQListData, isEditable = true): IPortOBQTankDetailValueObject {
    const _obqTankDetail = <IPortOBQTankDetailValueObject>{};
    _obqTankDetail.id = obqTankDetail.id;
    _obqTankDetail.portId = obqTankDetail.portId;
    _obqTankDetail.tankId = obqTankDetail.tankId;
    _obqTankDetail.tankName = obqTankDetail.tankName;
    const cargoObj: ICargo = listData.cargoList.find(cargo => cargo.id === obqTankDetail.cargoId);
    _obqTankDetail.cargo = new ValueObject<ICargo>(cargoObj, true, isNewValue, false);
    _obqTankDetail.fullCapacityCubm = obqTankDetail?.fullCapacityCubm;
    _obqTankDetail.api = new ValueObject<number>(obqTankDetail.api, true, isNewValue, isEditable);
    _obqTankDetail.quantity = new ValueObject<number>(obqTankDetail.quantity, true, isNewValue, isEditable);
    _obqTankDetail.colorCode = obqTankDetail.colorCode;
    _obqTankDetail.abbreviation = obqTankDetail.abbreviation;
    _obqTankDetail.volume = obqTankDetail.volume;

    return _obqTankDetail;
  }

  /**
 * Method for formating obq tank details
 *
 * @param {IPortOBQTankDetailValueObject} obqTankDetail
 * @returns {IPortOBQTankDetailValueObject}
 * @memberof LoadableStudyDetailsTransformationService
 */
  formatOBQTankDetail(obqTankDetail: IPortOBQTankDetailValueObject): IPortOBQTankDetailValueObject {
    obqTankDetail.storeKey = obqTankDetail.storeKey ?? uuid4();
    return obqTankDetail;
  }

  /**
 * Method for converting obq data as value
 *
 * @param {IPortOBQTankDetailValueObject} obqTankDetail
 * @returns {IPortOBQTankDetail}
 * @memberof LoadableStudyDetailsTransformationService
 */
  getOBQTankDetailAsValue(obqTankDetail: IPortOBQTankDetailValueObject): IPortOBQTankDetail {
    const _obqTankDetail: IPortOBQTankDetail = <IPortOBQTankDetail>{};
    for (const key in obqTankDetail) {
      if (Object.prototype.hasOwnProperty.call(obqTankDetail, key)) {
        _obqTankDetail[key] = obqTankDetail[key]?.value ?? obqTankDetail[key];
      }
    }
    _obqTankDetail.cargoId = obqTankDetail.cargo?.value?.id;

    return _obqTankDetail;
  }

  /** Set port grid complete status */
  setObqValidity(isValid: boolean) {
    this._obqValiditySource.next(isValid);
  }

  /**
   *  Set validation error message
   */
  setValidationErrorMessage() {
    return {
      dischargePort: {
        'required': 'CARGONOMINATION_DISCHARGE_PORT_ERROR_DETAILS',
      },
    }
  }

  /**
  * Set validation Error to form control
  */
  setValidationErrorMessageForLoadableQuantity() {
    return {

      portName: {
        'required': 'LOADABLE_QUANTITY_PORTNAME_REQUIRED',
      },
      estSeaDensity: {
        'required': 'LOADABLE_QUANTITY_SEA_WATER_DENSITTY_REQUIRED',
        'min': 'LOADABLE_QUANTITY_ERROR'
      },
      sgCorrection: {
        'required': 'LOADABLE_QUANTITY_SG_CORRECTION_REQUIRED'
      },
      estimateSag: {
        'required': 'LOADABLE_QUANTITY_ESTIMATE_SAG_REQUIRED',
        'min': 'LOADABLE_QUANTITY_ERROR'
      },
      safCorrection: {
        'required': 'LOADABLE_QUANTITY_SAF_CORRECTION_REQUIRED'
      },
      foOnboard: {
        'required': 'LOADABLE_QUANTITY_FO_ONBOARD_REQUIRED',
        'min': 'LOADABLE_QUANTITY_ERROR'
      },
      doOnboard: {
        'required': 'LOADABLE_QUANTITY_DO_ONBOARD_REQUIRED',
        'min': 'LOADABLE_QUANTITY_ERROR'
      },
      freshWaterOnboard: {
        'required': 'LOADABLE_QUANTITY_FRESH_WATER_REQUIRED',
        'min': 'LOADABLE_QUANTITY_ERROR'
      },
      boilerWaterOnboard: {
        'required': 'LOADABLE_QUANTITY_BOILER_WATER_ONBOARD_REQUIRED',
        'pattern': 'LOADABLE_QUANTITY_ERROR'
      },
      ballast: {
        'required': 'LOADABLE_QUANTITY_BALLAST_REQUIRED',
        'min': 'LOADABLE_QUANTITY_ERROR'
      },
      others: {
        'required': 'LOADABLE_QUANTITY_OTHERS_REQUIRED',
        'min': 'LOADABLE_QUANTITY_ERROR'
      },
      distanceInSummerzone: {
        'required': 'LOADABLE_QUANTITY_DISATANCE_REQUIRED',
        'min': 'LOADABLE_QUANTITY_ERROR'
      },
      speedInSz: {
        'required': 'LOADABLE_QUANTITY_SPEED_REQUIRED',
        'min': 'LOADABLE_QUANTITY_ERROR'
      },
      runningHours: {
        'required': 'LOADABLE_QUANTITY_RUNNING_HOURS_REQUIRED',
        'min': 'LOADABLE_QUANTITY_ERROR'
      },
      runningDays: {
        'required': 'LOADABLE_QUANTITY_RUNNING_DAYS_REQUIRED',
        'min': 'LOADABLE_QUANTITY_ERROR'
      },
      foConday: {
        'required': 'LOADABLE_QUANTITY_FO_CONS_PER_DAY_REQUIRED',
        'min': 'LOADABLE_QUANTITY_ERROR'
      },
      foConsInSz: {
        'required': 'LOADABLE_QUANTITY_FO_CONS_IN_SZ_REQUIRED',
        'min': 'LOADABLE_QUANTITY_ERROR'
      },
      subTotal: {
        'required': 'LOADABLE_QUANTITY_SUB_TOTAL_REQUIRED',
      },
      totalQuantity: {
        'required': 'LOADABLE_QUANTITY_TOTAL_QUANTITY_REQUIRED"'
      },
      arrivalMaxDraft: {
        'required': 'LOADABLE_QUANTITY_ARRIVAL_MAX_DRAFT_REQUIRED'
      }
    }
  }

  /**
 * Method for calculating  subtotal
 *
 * @param {ISubTotal} data
 * @returns {number}
 * @memberof LoadableStudyDetailsTransformationService
 */
  getSubTotal(data: ISubTotal): Number {
    const subTotal = Number(data.dwt) - Number(data.sagCorrection) + Number(data.sgCorrection ? data.sgCorrection : 0) - Number(data.foOnboard)
      - Number(data.doOnboard) - Number(data.freshWaterOnboard) - Number(data.boilerWaterOnboard) - Number(data.ballast)
      - Number(data.constant) - Number(data.others);
    return Number(subTotal);
  }
}

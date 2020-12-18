import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { DATATABLE_ACTION, DATATABLE_FIELD_TYPE, DATATABLE_FILTER_MATCHMODE, DATATABLE_FILTER_TYPE, IDataTableColumn } from '../../../shared/components/datatable/datatable.model';
import { ValueObject } from '../../../shared/models/common.model';
import { CargoPlanningModule } from '../cargo-planning.module';
import { ICargo, ICargoNomination, ICargoNominationAllDropdownData, ICargoNominationValueObject, ILoadingPort, ILoadingPortValueObject, IOHQPort, IOperations, IPort, IPortAllDropdownData, IPortList, IPortOBQListData, IPortOBQTankDetail, IPortOBQTankDetailValueObject, IPortOHQTankDetail, IPortOHQTankDetailValueObject, IPortsValueObject, ISegregation, OPERATIONS } from '../models/cargo-planning.model';
import { v4 as uuid4 } from 'uuid';
import { IPermission } from '../../../shared/models/user-profile.model';
import { ICargoGroup, ICommingleManual, ICommingleResponseModel, ICommingleValueObject, IPercentage } from '../models/commingle.model';

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
  private OPERATIONS: OPERATIONS;

  // public fields
  addCargoNomination$ = this._addCargoNominationSource.asObservable();
  totalQuantityCargoNomination$ = this._totalQuantityCargoNominationSource.asObservable();
  cargoNominationValidity$ = this._cargoNominationValiditySource.asObservable();
  addPort$ = this._addPortSource.asObservable();
  portValidity$ = this._portValiditySource.asObservable();

  constructor() { }

  /**
   * Method for formating cargo nomination data
   *
   * @param {ICargoNominationValueObject} cargoNomination
   * @returns {ICargoNominationValueObject}
   * @memberof LoadableStudyDetailsTransformationService
   */
  formatCargoNomination(cargoNomination: ICargoNominationValueObject): ICargoNominationValueObject {
    cargoNomination.storeKey = cargoNomination.storeKey ?? uuid4();
    if (cargoNomination.loadingPorts.value?.length) {
      cargoNomination.quantity.value = 0;
      cargoNomination.loadingPorts.value.map(port => {
        cargoNomination.quantity.value += port.quantity;
      })
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
      cargoNomination.quantity += port.quantity;
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
        } else if (key === 'loadinPorts') {
          _cargoNomination.segregationId = cargoNomination[key].value?.map(port => port.value);
        } else {
          _cargoNomination[key] = cargoNomination[key]?.value ?? cargoNomination[key];
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
  getCargoNominationDatatableColumns(): IDataTableColumn[] {
    return [
      {
        field: 'slNo',
        header: 'SL',
        fieldType: DATATABLE_FIELD_TYPE.SLNO,
        sortable: true,
        fieldHeaderClass: 'column-sl'
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
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.STARTSWITH,
        filterField: 'abbreviation.value',
        fieldPlaceholder: 'ENTER_ABBREVIATION',
        errorMessages: {
          'required': 'CARGO_NOMINATION_FIELD_REQUIRED_ERROR',
          'alphabetsOnly': 'CARGO_NOMINATION_FIELD_ALPHABETS_ONLY_ERROR'
        }
      },
      {
        field: 'loadingPorts',
        header: 'LOADINGPORT',
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
        filter: true,
        filterPlaceholder: 'SEARCH_QUANTITY',
        filterType: DATATABLE_FILTER_TYPE.NUMBER,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.STARTSWITH,
        filterField: 'quantity.value',
        fieldPlaceholder: 'ENTER_QUANTITY',
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
        errorMessages: {
          'required': 'CARGO_NOMINATION_FIELD_REQUIRED_ERROR'
        }
      },
      {
        field: 'actions',
        header: '',
        fieldHeaderClass: 'column-actions',
        fieldType: DATATABLE_FIELD_TYPE.ACTION,
        actions: [DATATABLE_ACTION.SAVE, DATATABLE_ACTION.DELETE, DATATABLE_ACTION.DUPLICATE]
      }
    ]
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
  getCargoNominationLoadingPortDatatableColumns(): IDataTableColumn[] {
    return [
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
          'required': 'CARGO_NOMINATION_FIELD_REQUIRED_ERROR'
        }
      },
      {
        field: 'actions',
        header: '',
        fieldType: DATATABLE_FIELD_TYPE.ACTION,
        actions: [DATATABLE_ACTION.DELETE]
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
  getLoadableStudyGridColumns(): IDataTableColumn[] {
    return [
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
        fieldValueIcon: 'icon-status'
      },
      {
        field: 'actions',
        header: '',
        fieldType: DATATABLE_FIELD_TYPE.ACTION,
        actions: [DATATABLE_ACTION.DELETE, DATATABLE_ACTION.DUPLICATE]
      }
    ];
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
  getPortAsValueObject(port: IPortList, isNewValue = true, isEditable = true, listData: IPortAllDropdownData): IPortsValueObject {
    const _port = <IPortsValueObject>{};
    const portObj: IPort = listData.portList.find(portData => portData.id === port.portId);
    const operationObj: IOperations = listData.operationListComplete.find(operation => operation.id === port.operationId);
    const isEdit = operationObj ? !(operationObj.id === OPERATIONS.LOADING) : true;
    const layCan = (port.layCanFrom && port.layCanTo) ? (port.layCanFrom + ' to ' + port.layCanTo) : '';
    _port.id = port.id;
    _port.portOrder = port.portOrder;
    _port.portcode = new ValueObject<string>(portObj?.code, true, false, false, false);
    _port.port = new ValueObject<IPort>(portObj, true, isNewValue, false, isEditable);
    _port.operation = new ValueObject<IOperations>(operationObj, true, isNewValue, false, isEdit && isEditable);
    _port.seaWaterDensity = new ValueObject<number>(port.seaWaterDensity, true, isNewValue, false, isEditable);
    _port.layCan = new ValueObject<any>(layCan, true, isNewValue, false, isEditable);
    _port.layCanFrom = new ValueObject<string>(port.layCanFrom?.trim(), true, isNewValue, false, isEditable);
    _port.layCanTo = new ValueObject<string>(port.layCanTo?.trim(), true, isNewValue, false, isEditable);
    _port.maxDraft = new ValueObject<number>(port.maxDraft, true, isNewValue, false, isEditable);
    _port.maxAirDraft = new ValueObject<number>(port.maxAirDraft, true, isNewValue, false, isEditable);
    _port.eta = new ValueObject<string>(port.eta, true, isNewValue, false, isEditable);
    _port.etd = new ValueObject<string>(port.etd, true, isNewValue, false, isEditable);
    _port.isAdd = isNewValue;
    _port.isLoadable = !isEdit;
    _port.isDelete = false;
    return _port;
  }


  /**
 * Method for setting ports grid columns
 *
 * @returns {IDataTableColumn[]}
 * @memberof LoadableStudyDetailsTransformationService
 */
  getPortDatatableColumns(permissions: IPermission): IDataTableColumn[] {
    const minDate = new Date();
    return [
      {
        field: 'slNo',
        header: 'SL',
        fieldType: DATATABLE_FIELD_TYPE.SLNO,
        sortable: true,
        fieldHeaderClass: 'column-sl'
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
        filterField: 'port.value.name',
        fieldOptionLabel: 'name',
        fieldPlaceholder: 'SELECT_PORT',
        errorMessages: {
          'required': 'PORT_FIELD_REQUIRED_ERROR',
          'duplicate': 'PORT_FIELD_DUPLICATE_ERROR'
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
        filterPlaceholder: 'SEARCH_PORT',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        listName: 'operationList',
        filterField: 'operation.value.operationName',
        fieldOptionLabel: 'operationName',
        fieldPlaceholder: 'SELECT_OPERATION',
        errorMessages: {
          'required': 'PORT_OPERATIONS_REQUIRED_ERROR',
          'duplicate': 'PORT_FIELD_DUPLICATE_ERROR'
        }
      },
      {
        field: 'seaWaterDensity',
        header: 'WATER DENSITY',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        filter: true,
        filterPlaceholder: 'SEARCH_PORT_WATER_DENSITY',
        filterType: DATATABLE_FILTER_TYPE.NUMBER,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'seaWaterDensity.value',
        fieldPlaceholder: 'ENTER_WATER_DENSITY',
        errorMessages: {
          'required': 'PORT_WATER_DENSITY_REQUIRED_ERROR'
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
        dateFormat: 'dd-mm-yy',
        errorMessages: {
          'required': 'PORT_LAY_CAN_REQUIRED_ERROR'
        }
      },
      {
        field: 'maxDraft',
        header: 'MAX-DRAFT',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        filter: true,
        filterPlaceholder: 'SEARCH_PORT_MAX_DRAFT',
        filterType: DATATABLE_FILTER_TYPE.NUMBER,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'maxDraft.value',
        fieldPlaceholder: 'ENTER_MAX_DRAFT',
        errorMessages: {
          'required': 'PORT_MAX_DRAFT_REQUIRED_ERROR'
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
        errorMessages: {
          'required': 'PORT_MAX_AIR_DRAFT_REQUIRED_ERROR'
        }
      },
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
        dateFormat: 'dd-mm-yy',
        minDate: minDate,
        fieldClass: 'eta',
        errorMessages: {
          'required': 'PORT_ETA_REQUIRED_ERROR',
          'notInRange': 'PORT_ETA_NOT_IN_DATE_RANGE',
          'failedCompare': 'PORT_ETA_COMPARE_ERROR'
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
        dateFormat: 'dd-mm-yy',
        fieldClass: 'etd',
        errorMessages: {
          'required': 'PORT_ETD_REQUIRED_ERROR',
          'failedCompare': 'PORT_ETD_COMPARE_ERROR'
        }
      },
      {
        ...(permissions ? {
          field: 'actions',
          header: '',
          fieldHeaderClass: 'column-actions',
          fieldType: DATATABLE_FIELD_TYPE.ACTION,
          actions: [(permissions?.add ? DATATABLE_ACTION.SAVE : []),
          (permissions?.delete ? DATATABLE_ACTION.DELETE : [])]
        } : {}),
        field: 'actions',
        header: '',
        fieldHeaderClass: 'column-actions',
        fieldType: DATATABLE_FIELD_TYPE.ACTION,
        actions: [DATATABLE_ACTION.SAVE, DATATABLE_ACTION.DELETE]
      },

    ]
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
            _ports.layCanFrom = port[key].value.split('to')[0].trim();
            _ports.layCanTo = port[key].value.split('to')[1].trim();
          } else {
            _ports.layCanFrom = "";
            _ports.layCanTo = "";
          }
        }
        else {
          if (key !== 'layCanFrom' && key !== 'layCanTo') {
            _ports[key] = port[key]?.value ?? port[key];
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
        sortable: true,
        fieldHeaderClass: 'column-sl'
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
        filterPlaceholder: 'OHQ_SEARCH_FUEL_TYPE'
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
        field: '',
        header: 'OHQ_ARRIVAL',
        columns: [
          {
            field: 'arrivalVolume',
            header: 'OHQ_VOL',
            fieldType: DATATABLE_FIELD_TYPE.NUMBER,
            fieldPlaceholder: 'OHQ_PLACEHOLDER_VOL',
            filter: true,
            filterField: 'arrivalVolume.value',
            filterMatchMode: DATATABLE_FILTER_MATCHMODE.STARTSWITH,
            filterType: DATATABLE_FILTER_TYPE.NUMBER,
            filterPlaceholder: 'OHQ_SEARCH_VOL',
            errorMessages: {
              'required': 'OHQ_VALUE_REQUIRED',
              'min': 'OHQ_MIN_VALUE',
              'groupTotal': 'OHQ_GROUP_TOTAL'
            }
          },
          {
            field: 'arrivalQuantity',
            header: 'OHQ_WEIGHT',
            fieldType: DATATABLE_FIELD_TYPE.NUMBER,
            fieldPlaceholder: 'OHQ_PLACEHOLDER_WEIGHT',
            filter: true,
            filterField: 'arrivalQuantity.value',
            filterMatchMode: DATATABLE_FILTER_MATCHMODE.STARTSWITH,
            filterType: DATATABLE_FILTER_TYPE.NUMBER,
            filterPlaceholder: 'OHQ_SEARCH_WEIGHT',
            fieldHeaderClass: 'column-weight',
            errorMessages: {
              'required': 'OHQ_VALUE_REQUIRED',
              'min': 'OHQ_MIN_VALUE',
              'groupTotal': 'OHQ_GROUP_TOTAL'
            }
          }
        ]
      },
      {
        field: '',
        header: 'OHQ_DEPARTURE',
        columns: [
          {
            field: 'departureVolume',
            header: 'OHQ_VOL',
            fieldType: DATATABLE_FIELD_TYPE.NUMBER,
            fieldPlaceholder: 'OHQ_PLACEHOLDER_VOL',
            filter: true,
            filterField: 'departureVolume.value',
            filterMatchMode: DATATABLE_FILTER_MATCHMODE.STARTSWITH,
            filterType: DATATABLE_FILTER_TYPE.NUMBER,
            filterPlaceholder: 'OHQ_SEARCH_VOL',
            errorMessages: {
              'required': 'OHQ_VALUE_REQUIRED',
              'min': 'OHQ_MIN_VALUE',
              'groupTotal': 'OHQ_GROUP_TOTAL'
            }
          },
          {
            field: 'departureQuantity',
            header: 'OHQ_WEIGHT',
            fieldType: DATATABLE_FIELD_TYPE.NUMBER,
            fieldPlaceholder: 'OHQ_PLACEHOLDER_WEIGHT',
            filter: true,
            filterField: 'departureQuantity.value',
            filterMatchMode: DATATABLE_FILTER_MATCHMODE.STARTSWITH,
            filterType: DATATABLE_FILTER_TYPE.NUMBER,
            fieldHeaderClass: 'column-weight',
            filterPlaceholder: 'OHQ_SEARCH_WEIGHT',
            errorMessages: {
              'required': 'OHQ_VALUE_REQUIRED',
              'min': 'OHQ_MIN_VALUE',
              'groupTotal': 'OHQ_GROUP_TOTAL'
            }
          }
        ]
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
    _ohqTankDetail.portId = ohqTankDetail?.portId;
    _ohqTankDetail.fuelTypeName = ohqTankDetail?.fuelTypeName;
    _ohqTankDetail.fuelTypeId = ohqTankDetail?.fuelTypeId;
    _ohqTankDetail.tankId = ohqTankDetail?.tankId;
    _ohqTankDetail.tankName = ohqTankDetail?.tankName;
    _ohqTankDetail.colorCode = ohqTankDetail?.colorCode;
    _ohqTankDetail.arrivalVolume = new ValueObject<number>(ohqTankDetail.arrivalVolume, true, isNewValue);
    _ohqTankDetail.arrivalQuantity = new ValueObject<number>(ohqTankDetail.arrivalQuantity, true, isNewValue);
    _ohqTankDetail.departureVolume = new ValueObject<number>(ohqTankDetail.departureVolume, true, isNewValue);
    _ohqTankDetail.departureQuantity = new ValueObject<number>(ohqTankDetail.departureQuantity, true, isNewValue);

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
   * Method to get Manual Commingle grid colums
   */
  getManualCommingleDatatableColumns(): IDataTableColumn[] {
    return [
      {
        field: 'cargo1',
        header: 'COMMINGLE_CARGO1',
        fieldType: DATATABLE_FIELD_TYPE.SELECT,
        listName: 'cargoNominationsCargo1',
        fieldOptionLabel: 'name',
        fieldPlaceholder: 'COMMINGLE_CARGO_1_DROP_DOWN_PLACE_HOLDER',
        fieldHeaderClass:'column-cargo1',
        fieldClass: 'commingle-cargo1',
        errorMessages: {
          'required': 'COMMINGLE_CARGO_SELECT_ERROR'
        }
      },
      {
        field: 'cargo1Color',
        header: '',
        fieldHeaderClass:'column-cargo1-color',
        fieldClass: 'commingle-cargo1-color',
        fieldType: DATATABLE_FIELD_TYPE.COLOR,

      },
      {
        field: 'cargo1IdPct',
        header: 'COMMINGLE_CARGO_PERCENTAGE',
        fieldType: DATATABLE_FIELD_TYPE.SELECT,
        listName: 'percentage',
        fieldOptionLabel: 'name',
        fieldPlaceholder: 'COMMINGLE_PERCENTAGE_PLACEHOLDER',
        fieldHeaderClass:'column-cargo1-pct',
        fieldClass: 'commingle-cargo1-pct',
        errorMessages: {
          'required': 'COMMINGLE_PERCENTAGE_SELECT_ERROR'
        }
      },
      {
        field: 'cargo2',
        header: 'COMMINGLE_CARGO2',
        fieldType: DATATABLE_FIELD_TYPE.SELECT,
        listName: 'cargoNominationsCargo2',
        fieldOptionLabel: 'name',
        fieldPlaceholder: 'COMMINGLE_CARGO_2_DROP_DOWN_PLACE_HOLDER',
        fieldHeaderClass:'column-cargo2',
        fieldClass: 'commingle-cargo2',
        errorMessages: {
          'required': 'COMMINGLE_CARGO_SELECT_ERROR'
        }
      },
      {
        field: 'cargo2Color',
        header: '',
        fieldHeaderClass:'column-cargo2-color',
        fieldClass: 'commingle-cargo2-color',
        fieldType: DATATABLE_FIELD_TYPE.COLOR
      },
      {
        field: 'cargo2IdPct',
        header: 'COMMINGLE_CARGO_PERCENTAGE',
        fieldType: DATATABLE_FIELD_TYPE.SELECT,
        listName: 'percentage',
        fieldOptionLabel: 'name',
        fieldPlaceholder: 'COMMINGLE_PERCENTAGE_PLACEHOLDER',
        fieldHeaderClass:'column-cargo2-pct',
        fieldClass: 'commingle-cargo2-pct',
        errorMessages: {
          'required': 'COMMINGLE_PERCENTAGE_SELECT_ERROR'
        }
      },
      {
        field: 'quantity',
        header: 'COMMINGLE_QTY',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        fieldPlaceholder: 'COMMINGLE_QTY_PLACEHOLDER',
        fieldHeaderClass:'column-quantity',
        fieldClass: 'commingle-quantity',
        errorMessages: {
          'required': 'COMMINGLE_CARGO_SELECT_ERROR',
          'max': 'COMMINGLE_QUANTITY_MAX_LIMIT'
        }
      },
      {
        field: 'actions',
        header: '',
        fieldType: DATATABLE_FIELD_TYPE.ACTION,
        actions: [DATATABLE_ACTION.DELETE,]
      }
    ]
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
        } else if (key === 'cargo1pct') {
          _cargoList.cargo1pct = commingle[key].value;
        } else if (key === 'cargo2') {
          _cargoList.cargo2Id = commingle[key].value?.cargoId;
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
    const cargo1Obj: ICargoNomination = listData.cargoNominationsCargo1.find(cargo1Data => cargo1Data.cargoId === commingleManual.cargo1Id);
    const cargo2Obj: ICargoNomination = listData.cargoNominationsCargo2.find(cargo2Data => cargo2Data.cargoId === commingleManual.cargo2Id);
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
    _commingleManual.quantity = new ValueObject<number>(commingleManual.quantity, true, isNewValue, false, isEditable);
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
        sortable: true,
        fieldHeaderClass: 'column-sl'
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
        field: 'sounding',
        header: 'OBQ_SOUNDING',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        fieldPlaceholder: 'OBQ_PLACEHOLDER_SOUNDING',
        filter: true,
        filterField: 'sounding.value',
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.STARTSWITH,
        filterType: DATATABLE_FILTER_TYPE.NUMBER,
        filterPlaceholder: 'OBQ_SEARCH_SOUNDING',
        errorMessages: {
          'required': 'OBQ_VALUE_REQUIRED',
          'min': 'OBQ_MIN_VALUE',
          'groupTotal': 'OBQ_GROUP_TOTAL'
        }

      },
      {
        field: 'weight',
        header: 'OBQ_WEIGHT',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        fieldPlaceholder: 'OBQ_PLACEHOLDER_WEIGHT',
        filter: true,
        filterField: 'weight.value',
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.STARTSWITH,
        filterType: DATATABLE_FILTER_TYPE.NUMBER,
        filterPlaceholder: 'OBQ_SEARCH_WEIGHT',
        errorMessages: {
          'required': 'OBQ_VALUE_REQUIRED',
          'min': 'OBQ_MIN_VALUE',
          'groupTotal': 'OBQ_GROUP_TOTAL'
        }
      },
      {
        field: 'volume',
        header: 'OBQ_VOL',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        fieldPlaceholder: 'OBQ_PLACEHOLDER_VOL',
        filter: true,
        filterField: 'volume.value',
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.STARTSWITH,
        filterType: DATATABLE_FILTER_TYPE.NUMBER,
        filterPlaceholder: 'OBQ_SEARCH_VOL',
        errorMessages: {
          'required': 'OBQ_VALUE_REQUIRED',
          'min': 'OBQ_MIN_VALUE',
          'groupTotal': 'OBQ_GROUP_TOTAL'
        }

      },
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
  getOBQTankDetailsAsValueObject(obqTankDetail: IPortOBQTankDetail, isNewValue = true, listData: IPortOBQListData): IPortOBQTankDetailValueObject {
    const _obqTankDetail = <IPortOBQTankDetailValueObject>{};
    _obqTankDetail.id = obqTankDetail.id;
    _obqTankDetail.portId = obqTankDetail.portId;
    _obqTankDetail.tankId = obqTankDetail.tankId;
    _obqTankDetail.tankName = obqTankDetail.tankName;
    const cargoObj: ICargo = listData.cargoList.find(cargo => cargo.id === obqTankDetail.cargoId);
    _obqTankDetail.cargo = new ValueObject<ICargo>(cargoObj, true, isNewValue, false);
    _obqTankDetail.sounding = new ValueObject<number>(obqTankDetail.sounding, true, isNewValue);
    _obqTankDetail.weight = new ValueObject<number>(obqTankDetail.weight, true, isNewValue);
    _obqTankDetail.volume = new ValueObject<number>(obqTankDetail.volume, true, isNewValue);
    _obqTankDetail.colorCode = obqTankDetail.colorCode;

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

}

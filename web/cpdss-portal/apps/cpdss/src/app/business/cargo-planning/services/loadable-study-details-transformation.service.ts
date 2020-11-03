import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { DATATABLE_ACTION, DATATABLE_FIELD_TYPE, DATATABLE_FILTER_MATCHMODE, DATATABLE_FILTER_TYPE, IDataTableColumn } from '../../../shared/components/datatable/datatable.model';
import { ValueObject } from '../../../shared/models/common.model';
import { CargoPlanningModule } from '../cargo-planning.module';
import { ICargo, ICargoNomination, ICargoNominationAllDropdownData, ICargoNominationValueObject, ILoadingPort, ILoadingPortValueObject, ISegregation } from '../models/cargo-planning.model';
import { v4 as uuid4 } from 'uuid';

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

  // public fields
  addCargoNomination$ = this._addCargoNominationSource.asObservable();
  totalQuantityCargoNomination$ = this._totalQuantityCargoNominationSource.asObservable();
  cargoNominationValidity$ = this._cargoNominationValiditySource.asObservable();

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
        fieldClass: 'column-sl'
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
        fieldClass: 'column-color',
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
        fieldClass: 'column-actions',
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
    _loadingPort.name = new ValueObject<string>(loadingPort.name, true, false, false);
    _loadingPort.quantity = new ValueObject<number>(loadingPort.quantity, true, isNewValue, false);
    _loadingPort.isAdd = isNewValue;

    return _loadingPort;
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
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.STARTSWITH,
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
}

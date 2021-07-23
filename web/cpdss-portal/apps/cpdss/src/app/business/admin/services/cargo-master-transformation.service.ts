import { Injectable } from '@angular/core';
import { DATATABLE_ACTION, DATATABLE_FIELD_TYPE, DATATABLE_FILTER_MATCHMODE, DATATABLE_FILTER_TYPE, IDataTableColumn } from '../../../shared/components/datatable/datatable.model';
import { IPermission } from '../../../shared/models/user-profile.model';
import { ICargoDetails } from '../models/cargo.model';
import { AdminModule } from '../admin.module';

/**
 * Service for transformation of cargo master data
 *
 * @export
 * @class CargoMasterTransformationService
 */
@Injectable({
  providedIn: AdminModule
})
export class CargoMasterTransformationService {

  constructor() { }

  /**
   * Method for getting Cargo master table column data
   *
   * @param {IPermission} permission
   * @return {*}  {IDataTableColumn[]}
   * @memberof CargoMasterTransformationService
   */
  getCargosDatatableColumns(permission: IPermission): IDataTableColumn[] {
    let columns: IDataTableColumn[] = [
      {
        field: 'slNo',
        header: 'SL',
        fieldType: DATATABLE_FIELD_TYPE.SLNO,
        fieldHeaderClass: 'column-sl',
        fieldClass: 'sl'
      },
      {
        field: 'name',
        header: 'CARGO_LIST_NAME',
        filter: true,
        filterPlaceholder: 'SEARCH_CARGO_NAME',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'name',
        sortable: true,
        sortField: 'name',
        filterByServer: true
      },
      {
        field: 'type',
        header: 'CARGO_LIST_TYPE',
        filter: true,
        filterPlaceholder: 'SEARCH_CARGO_TYPE',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'type',
        sortable: true,
        sortField: 'type',
        filterByServer: true
      },
      {
        field: 'abbreviation',
        header: 'CARGO_LIST_ABBREVIATION',
        filter: true,
        filterPlaceholder: 'SEARCH_CARGO_ABBREVIATION',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'abbreviation',
        sortable: true,
        sortField: 'abbreviation',
        filterByServer: true
      },
      {
        field: 'assay_date',
        header: 'CARGO_LIST_ASSAY_DATE',
        filter: true,
        filterPlaceholder: 'SEARCH_CARGO_ASSAY_DATE',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'assay_date',
        sortable: true,
        sortField: 'assay_date',
        filterByServer: true
      },
      {
        field: 'api',
        header: 'CARGO_LIST_API',
        filter: true,
        filterPlaceholder: 'SEARCH_CARGO_API',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'api',
        sortable: true,
        sortField: 'api',
        filterByServer: true
      },
      {
        field: 'temp',
        header: 'CARGO_LIST_TEMPERATURE',
        filter: true,
        filterPlaceholder: 'SEARCH_CARGO_TEMPERATURE',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'temp',
        sortable: true,
        sortField: 'temp',
        filterByServer: true
      },
      {
        field: 'countries',
        header: 'CARGO_LIST_COUNTRY',
        filter: true,
        filterPlaceholder: 'SEARCH_CARGO_COUNTRY',
        fieldType: DATATABLE_FIELD_TYPE.ARRAY,
        filterType: DATATABLE_FILTER_TYPE.ARRAY,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        arrayLabelField: 'countriesLabel',
        arrayFilterField: 'countriesNameArray',
        filterByServer: true
      },
      {
        field: 'ports',
        header: 'CARGO_LIST_PORTS',
        filter: true,
        filterPlaceholder: 'SEARCH_CARGO_PORT',
        fieldType: DATATABLE_FIELD_TYPE.ARRAY,
        filterType: DATATABLE_FILTER_TYPE.ARRAY,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        arrayLabelField: 'portsLabel',
        arrayFilterField: 'portsNameArray',
        filterByServer: true
      },
    ];

    if (permission) {
      const actions: DATATABLE_ACTION[] = [];
      if (permission?.edit) {
        actions.push(DATATABLE_ACTION.EDIT);
      }
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
   * Method for formating cargo data
   *
   * @param {ICargoDetails} cargo
   * @return {*}
   * @memberof CargoMasterTransformationService
   */
  formatCargo(cargo: ICargoDetails) {
    cargo.portsNameArray = cargo.ports.map(lport => lport.name);
    cargo.portsLabel = cargo.portsNameArray.join();
    cargo.countriesNameArray = cargo.countries.map(country => country.name);
    cargo.countriesLabel = cargo.countriesNameArray.join();

    return cargo;
  }

  /**
   * Method for api & Temp history grid
   *
   * @return {*}  {IDataTableColumn[]}
   * @memberof CargoMasterTransformationService
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
}

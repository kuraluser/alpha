import { Injectable } from '@angular/core';
import { CargoPlanningModule } from '../cargo-planning.module';
import { IPermission } from '../../../shared/models/user-profile.model';
import { DATATABLE_FIELD_TYPE, DATATABLE_FILTER_MATCHMODE, DATATABLE_FILTER_TYPE, IDataTableColumn } from './../../../shared/components/datatable/datatable.model';

/**
 * Transformation service for Cargo history module
 *
 * @export
 * @class CargoHistoryTransformationService
 */
@Injectable({
  providedIn: CargoPlanningModule
})
export class CargoHistoryTransformationService {

  constructor() { }

  /**
   * Method for Cargo-history grid
   *
   * @return {*}  {IDataTableColumn}
   * @memberof CargoHistoryTransformationService
   */
  getCargoHistoryGridColumns(permission: IPermission): IDataTableColumn[] {
    if (permission?.view) {
      return [
        {
          field: 'slNo',
          header: 'SL No',
          fieldType: DATATABLE_FIELD_TYPE.SLNO,
          fieldHeaderClass: 'column-sl',
          fieldClass: 'sl'
        },
        {
          field: 'vesselName',
          header: 'CARGO_HISTORY_TABLE_VESSEL_NAME',
          filter: true,
          filterPlaceholder: 'CARGO_HISTORY_TABLE_VESSEL_NAME_SEARCH_PLACEHOLDER',
          filterType: DATATABLE_FILTER_TYPE.TEXT,
          filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
          filterField: 'vesselName',
          filterByServer: true,
          sortable: true,
          sortField: 'vesselName'
        },
        {
          field: 'loadingPortName',
          header: 'CARGO_HISTORY_TABLE_LOADING_PORT',
          filter: true,
          filterPlaceholder: 'CARGO_HISTORY_TABLE_LOADING_PORT_SEARCH_PLACEHOLDER',
          filterType: DATATABLE_FILTER_TYPE.TEXT,
          filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
          filterField: 'loadingPortName',
          filterByServer: true,
          sortable: true,
          sortField: 'loadingPortName'
        },
        {
          field: 'grade',
          header: 'CARGO_HISTORY_TABLE_GRADE',
          filter: true,
          filterPlaceholder: 'CARGO_HISTORY_TABLE_GRADE_SEARCH_PLACEHOLDER',
          filterType: DATATABLE_FILTER_TYPE.TEXT,
          filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
          filterField: 'grade',
          filterByServer: true,
          sortable: true,
          sortField: 'grade'
        },
        {
          field: 'loadedYear',
          header: 'CARGO_HISTORY_TABLE_YEAR',
          filter: true,
          filterPlaceholder: 'CARGO_HISTORY_TABLE_YEAR_SEARCH_PLACEHOLDER',
          filterType: DATATABLE_FILTER_TYPE.TEXT,
          filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
          filterField: 'loadedYear',
          filterByServer: true,
          sortable: true,
          sortField: 'loadedYear'
        },
        {
          field: 'loadedMonth',
          header: 'CARGO_HISTORY_TABLE_MONTH',
          filter: true,
          filterPlaceholder: 'CARGO_HISTORY_TABLE_MONTH_SEARCH_PLACEHOLDER',
          filterType: DATATABLE_FILTER_TYPE.TEXT,
          filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
          filterField: 'loadedMonth',
          filterByServer: true
        },
        {
          field: 'loadedDay',
          header: 'CARGO_HISTORY_TABLE_DATE',
          filter: true,
          filterPlaceholder: 'CARGO_HISTORY_TABLE_DATE_SEARCH_PLACEHOLDER',
          filterType: DATATABLE_FILTER_TYPE.TEXT,
          filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
          filterField: 'loadedDay',
          filterByServer: true
        },
        {
          field: 'api',
          header: 'CARGO_HISTORY_TABLE_API',
          filter: true,
          filterPlaceholder: 'CARGO_HISTORY_TABLE_API_SEARCH_PLACEHOLDER',
          filterType: DATATABLE_FILTER_TYPE.TEXT,
          filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
          filterField: 'api',
          filterByServer: true
        },
        {
          field: 'temperature',
          header: 'CARGO_HISTORY_TABLE_TEMP',
          filter: true,
          filterPlaceholder: 'CARGO_HISTORY_TABLE_TEMP_SEARCH_PLACEHOLDER',
          filterType: DATATABLE_FILTER_TYPE.TEXT,
          filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
          filterField: 'temperature',
          filterByServer: true
        }
      ]
    }
  }

}

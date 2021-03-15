import { Injectable } from '@angular/core';
import { IPermission } from '../../../shared/models/user-profile.model';
import { IDataTableColumn } from './../../../shared/components/datatable/datatable.model';

/**
 * Transformation service for Cargo history module
 *
 * @export
 * @class CargoHistoryTransformationService
 */
@Injectable({
  providedIn: 'root'
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
          field: 'vesselName',
          header: 'CARGO_HISTORY_TABLE_VESSEL_NAME'
        },
        {
          field: 'loadingPort',
          header: 'CARGO_HISTORY_TABLE_LOADING_PORT'
        },
        {
          field: 'grade',
          header: 'CARGO_HISTORY_TABLE_GRADE'
        },
        {
          field: 'year',
          header: 'CARGO_HISTORY_TABLE_YEAR'
        },
        {
          field: 'month',
          header: 'CARGO_HISTORY_TABLE_MONTH'
        },
        {
          field: 'date',
          header: 'CARGO_HISTORY_TABLE_DATE'
        },
        {
          field: 'api',
          header: 'CARGO_HISTORY_TABLE_API'
        },
        {
          field: 'temperature',
          header: 'CARGO_HISTORY_TABLE_TEMP'
        }
      ]
    }
  }

}

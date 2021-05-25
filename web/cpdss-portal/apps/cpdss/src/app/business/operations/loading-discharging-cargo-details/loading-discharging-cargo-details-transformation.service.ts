import { Injectable } from '@angular/core';
import { DATATABLE_EDITMODE, DATATABLE_FIELD_TYPE, IDataTableColumn, DATATABLE_FILTER_TYPE, DATATABLE_FILTER_MATCHMODE } from '../../../shared/components/datatable/datatable.model';

/**
 * Transformation Service for cargo details
 *
 * @export
 * @class LoadingDischargingCargoDetailsTransformationService
 */
@Injectable()
export class LoadingDischargingCargoDetailsTransformationService {

  constructor() { }

  /**
   * Method for formatting departure details column
   * @returns {IDataTableColumn}
   * @memberof LoadingDischargingCargoDetailsTransformationService
   */

  departureDetailsColumns(){
    const columns: IDataTableColumn[] = [
      { 
        field: 'cargoName',
        header: 'KIND OF CARGO',
      },
      { 
        field: 'api',
        header: 'API',
      },
      { 
        field: 'temp',
        header: 'TEMP',
      },
      { 
        field: 'maxLoadingRate',
        header: 'MAX LOADING RATE',
      },
      { 
        field: 'nomination',
        header: 'NOMINATION',
      },
      { 
        field: 'shipLoadable',
        header: 'SHIP LOADABLE',
      },
      { 
        field: 'tolerance',
        header: 'TOLERANCE +/-%',
      },
      { 
        field: 'difference',
        header: 'DIFFERENCE +/-%',
      },
      { 
        field: 'loadTime',
        header: 'LOAD TIME (HRS)',
      },
      { 
        field: 'slopQty',
        header: 'SLOP QTY',
      },
      { 
        field: 'cargoSequence',
        header: 'CARGO SEQUENCE',
        fieldType: DATATABLE_FIELD_TYPE.SELECT,
        listName: 'sequence',
        fieldOptionLabel: 'name',
      },

    ];
    return columns;
  }
}

import { Injectable } from '@angular/core';
import { DATATABLE_ACTION, DATATABLE_BUTTON, DATATABLE_FIELD_TYPE, DATATABLE_FILTER_MATCHMODE, DATATABLE_FILTER_TYPE, IDataTableColumn } from '../../../shared/components/datatable/datatable.model';
import { IPermission } from '../../../shared/models/user-profile.model';

/**
 * Transformation Service for voyage module
 *
 * @export
 * @class VoyageListTransformationService
 */
@Injectable()
export class VoyageListTransformationService {

  constructor() { }


  /**
 * Method for setting cargonomination grid columns
 *
 * @returns {IDataTableColumn[]}
 * @memberof VoyageListTransformationService
 */
  getVoyageListDatatableColumns(permissionStart: IPermission, permissionStop: IPermission): IDataTableColumn[] {
    return [
      {
        field: 'slNo',
        header: 'SL',
        fieldType: DATATABLE_FIELD_TYPE.SLNO,
        sortable: false,
        fieldHeaderClass: 'column-sl',
        fieldClass: 'sl'
      },
      {
        field: 'voyageNo',
        header: 'VOYAGE NO',
        fieldHeaderClass: 'column-voyage-no',
        filter: true,
        filterPlaceholder: 'SEARCH_VOYAGE',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'voyageNo',
        sortable: true,
        sortField: 'voyageNo',
        filterByServer: true
      },
      {
        field: 'charterer',
        header: 'CHARTER',
        fieldHeaderClass: 'column-charterer',
        filter: true,
        filterPlaceholder: 'SEARCH_CHARTER',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'charterer',
        sortable: true,
        sortField: 'charterer',
        filterByServer: true
      },
      {
        field: 'plannedStartDate',
        header: 'PLANNED START DATE',
        fieldHeaderClass: 'column-planned-start-date',
        filter: true,
        filterPlaceholder: 'SEARCH_DATE',
        filterType: DATATABLE_FILTER_TYPE.DATE,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.EQUALS,
        filterField: 'plannedStartDate',
        sortable: true,
        sortField: 'plannedStartDate',
        filterByServer: true
      },
      {
        field: 'plannedEndDate',
        header: 'PLANNED END DATE',
        fieldHeaderClass: 'column-planned-end-date',
        filter: true,
        filterPlaceholder: 'SEARCH_DATE',
        filterType: DATATABLE_FILTER_TYPE.DATE,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.EQUALS,
        filterField: 'plannedEndDate',
        sortable: true,
        sortField: 'plannedEndDate',
        filterByServer: true
      },
      {
        field: 'actualStartDate',
        header: 'ACTUAL START DATE',
        fieldHeaderClass: 'column-actual-start-date',
        filter: true,
        filterPlaceholder: 'SEARCH_DATE',
        filterType: DATATABLE_FILTER_TYPE.DATE,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.EQUALS,
        filterField: 'actualStartDate',
        sortable: true,
        sortField: 'actualStartDate',
        filterByServer: true
      },
      {
        field: 'actualEndDate',
        header: 'ACTUAL END DATE',
        fieldHeaderClass: 'column-actual-end-date',
        filter: true,
        filterPlaceholder: 'SEARCH_DATE',
        filterType: DATATABLE_FILTER_TYPE.DATE,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.EQUALS,
        filterField: 'actualEndDate',
        sortable: true,
        sortField: 'actualEndDate',
        filterByServer: true
      },
      {
        field: 'loading',
        header: 'LOADING PORTS',
        fieldHeaderClass: 'column-loading',
        filter: true,
        filterPlaceholder: 'SEARCH_LOADING_PORT',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'loadingPorts',
        filterByServer: true
      },
      {
        field: 'discharging',
        header: 'DISCHARGE PORTS',
        fieldHeaderClass: 'column-discharging',
        filter: true,
        filterPlaceholder: 'SEARCH_DISCHARGE_PORT',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'dischargingPorts',
        filterByServer: true
      },
      {
        field: 'cargo',
        header: 'CARGO LOADED',
        fieldHeaderClass: 'column-cargo',
        filter: true,
        filterPlaceholder: 'SEARCH_CARGO_LOADED',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'cargos',
        filterByServer: true
      },
      {
        field: 'status',
        header: 'STATUS',
        fieldHeaderClass: 'column-status',
        filter: true,
        filterPlaceholder: 'SEARCH_STATUS',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'status',
        sortable: true,
        sortField: 'status',
        filterByServer: true
      },
      {
        field: 'buttons',
        header: '',
        fieldType: DATATABLE_FIELD_TYPE.BUTTON,
        buttons: [
          ...(permissionStart && permissionStart.view ? [{type: DATATABLE_BUTTON.START_VOYAGE , field: 'isStart' , icons: 'voyage-start', label: 'Start'}] : []),
          ...(permissionStop && permissionStop.view ? [{type: DATATABLE_BUTTON.STOP_VOYAGE , field: 'isStop' ,  icons: 'voyage-end', label: 'Stop'}] : []),
        ]
      }
    ]
  }

  /**
  * Format date(dd-mm-yyyy)
  * Format date time(dd-mm-yyyy hh:mm)
  */
  formatDateTime(date, isTime = false) {
    if (!date)
      return null;
    let month = date.getMonth() + 1;
    let day = date.getDate();
    let hour = date.getHours();
    let minute = date.getMinutes();

    if (month < 10) {
      month = '0' + month;
    }

    if (day < 10) {
      day = '0' + day;
    }

    if (hour < 10) {
      hour = '0' + hour;
    }

    if (minute < 10) {
      minute = '0' + minute;
    }

    if (isTime) {
      return day + '-' + month + '-' + date.getFullYear() + ' ' + hour + ':' + minute;
    } else {
      return day + '-' + month + '-' + date.getFullYear();
    }
  }
}

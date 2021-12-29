import { Injectable } from '@angular/core';
import { DATATABLE_ACTION, DATATABLE_FIELD_TYPE, DATATABLE_FILTER_MATCHMODE, DATATABLE_FILTER_TYPE, IDataTableColumn } from '../../../shared/components/datatable/datatable.model';
import { IPermission } from '../../../shared/models/user-profile.model';
import { ICargoDetails } from '../models/cargo.model';
import { AdminModule } from '../admin.module';
import { IValidationErrorMessagesSet } from '../../../shared/components/validation-error/validation-error.model';
import { TimeZoneTransformationService } from '../../../shared/services/time-zone-conversion/time-zone-transformation.service';
import { IDateTimeFormatOptions } from '../../../shared/models/common.model';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';

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

  constructor(private timeZoneTransformationService: TimeZoneTransformationService) { }

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
        field: 'assayDate',
        header: 'CARGO_LIST_ASSAY_DATE',
        filter: true,
        filterPlaceholder: 'SEARCH_CARGO_ASSAY_DATE',
        filterType: DATATABLE_FILTER_TYPE.DATE,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'assayDate',
        sortable: true,
        sortField: 'assayDate',
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
        field: 'loadingInformation',
        header: 'CARGO_LIST_PORTS',
        filter: true,
        filterPlaceholder: 'SEARCH_CARGO_PORT',
        fieldType: DATATABLE_FIELD_TYPE.ARRAY,
        filterType: DATATABLE_FILTER_TYPE.ARRAY,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        arrayLabelField: 'portsLabel',
        arrayFilterField: 'port',
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
    cargo.port = cargo.loadingInformation?.map(info => info?.port?.name);
    const formatOptions: IDateTimeFormatOptions = { customFormat: AppConfigurationService.settings?.dateFormat.split(' ')[0] };
    cargo.assayDate = this.timeZoneTransformationService.formatDateTime(cargo.assayDate, formatOptions);
    cargo.portsLabel = cargo.port?.join();
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

  /**
   * Set validation error messages for cargo details
   *
   * @return {*}  {IValidationErrorMessagesSet}
   * @memberof CargoMasterTransformationService
   */
  setValidationErrorMessage(): IValidationErrorMessagesSet {
    return {
      name: {
        'required': 'CARGO_DETAILS_REQUIRED',
        'specialCharacter': 'CARGO_DETAILS_NAME_INVALID_PATTERN',
        'maxlength': 'CARGO_DETAILS_NAME_MAX_LENGTH'
      },
      abbreviation: {
        'required': 'CARGO_DETAILS_REQUIRED',
        'maxlength': 'CARGO_DETAILS_ABBREVIATION_MAX_LENGTH'
      },
      api: {
        'required': 'CARGO_DETAILS_REQUIRED',
        'invalidNumber': 'CARGO_DETAILS_INVALID'
      },
      reidVapourPressure: {
        'invalidNumber': 'CARGO_DETAILS_INVALID'
      },
      gas: {
        'invalidNumber': 'CARGO_DETAILS_INVALID'
      },
      totalWax: {
        'invalidNumber': 'CARGO_DETAILS_INVALID'
      },
      pourPoint: {
        'invalidNumber': 'CARGO_DETAILS_INVALID'
      },
      cloudPoint: {
        'invalidNumber': 'CARGO_DETAILS_INVALID'
      },
      viscosity: {
        'invalidNumber': 'CARGO_DETAILS_INVALID'
      },
      temp: {
        'invalidNumber': 'CARGO_DETAILS_INVALID'
      },
      cowCodes: {
        'invalidNumber': 'CARGO_DETAILS_INVALID'
      },
      hydrogenSulfideOil: {
        'invalidNumber': 'CARGO_DETAILS_INVALID'
      },
      hydrogenSulfideVapour: {
        'invalidNumber': 'CARGO_DETAILS_INVALID'
      },
      benzene: {
        'invalidNumber': 'CARGO_DETAILS_INVALID'
      },
      port: {
        'unique': 'CARGO_DETAILS_PORT_EXIST'
      }
    }
  }
}

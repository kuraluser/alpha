import { Injectable } from '@angular/core';
import { AdminModule } from '../admin.module';

import { IDataTableColumn, DATATABLE_FILTER_TYPE, DATATABLE_FILTER_MATCHMODE, DATATABLE_FIELD_TYPE, DATATABLE_ACTION } from '../../../shared/components/datatable/datatable.model';
import { IPermission } from '../../../shared/models/user-profile.model';
import { IBerthInfo, IBerthValueObject } from '../models/port.model';
import { ValueObject } from '../../../shared/models/common.model';


/**
 * Service for transformation of port list data
 * @export
 * @class PortMasterTransformationService
 */
@Injectable({
  providedIn: AdminModule
})
export class PortMasterTransformationService {

  berthFomDetails: any;

  constructor() {
  }

  /**
   * Method to set validation message for columns
   * @return {*}
   * @memberof PortMasterTransformationService
   */
  setValidationErrorMessage() {
    return {
      portName: {
        'required': 'PORT_MASTER_PORT_NAME_REQUIRED',
        'maxlength': 'PORT_MASTER_PORT_NAME_MAX_LENGTH'
      },
      portCode: {
        'required': "PORT_MASTER_PORT_CODE_REQUIRED",
        'maxlength': 'PORT_MASTER_PORT_CODE_MAX_LENGTH'
      },
      timeZone: {
        'required': "PORT_MASTER_TIMEZONE_REQUIRED",
      },
      country: {
        'required': "PORT_MASTER_COUNTRY_REQUIRED",
      },
      tideHeightHigh: {
        'required': "PORT_MASTER_TIDE_HEIGHT_REQUIRRED",
        'invalidNumber': "PORT_MASTER_TIDE_HEIGHT_INVALID"
      },
      tideHeightLow: {
        'required': "PORT_MASTER_LOW_TIDE_HEIGHT_REQUIRED",
        'invalidNumber': "PORT_MASTER_LOW_TIDE_HEIGHT_INVALID"
      },
      maxPermissibleDraft: {
        'required': "PORT_MASTER_MAX_PERMISSIBLE_SHIPS",
        'invalidNumber': "PORT_MASTER_MAX_PERMISSIBLE_SHIPS_INVALID"
      },
      densityOfWater: {
        'required': "PORT_MASTER_DENSITY_OF_WATER_REQUIRED",
        'invalidNumber': "PORT_MASTER_DENSITY_OF_WATER_INVALID"
      },
      ambientTemperature: {
        'required': "PORT_MASTER_AMBIENT_TEMPERATURE_REQUIRED",
        'invalidNumber': "PORT_MASTER_AMBIENT_TEMPERATURE_INVALID"
      },

    }
  }

  /**
   * Method for setting Port List grid column
   * @return {IDataTableColumn[]}
   * @memberof PortMasterTransformationService
   */
  getPortListDatatableColumns(permission: IPermission): IDataTableColumn[] {
    const columns: IDataTableColumn[] = [
      {
        field: 'slNo',
        header: 'SL',
        fieldType: DATATABLE_FIELD_TYPE.SLNO,
        fieldHeaderClass: 'column-sl',
        fieldClass: 'sl'
      },
      {
        field: 'name',
        header: 'PORT_MASTER_PORT_NAME',
        filter: true,
        filterPlaceholder: 'PORT_MASTER_SEARCH_PORT_NAME',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'name',
        filterByServer: true,
        sortable: true,
        sortField: 'name'
      },
      {
        field: 'code',
        header: 'PORT_MASTER_PORT_CODE',
        filter: true,
        filterPlaceholder: 'PORT_MASTER_SEARCH_PORT_CODE',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'code',
        filterByServer: true,
        sortable: true,
        sortField: 'code'
      },
      {
        field: 'countryName',
        header: 'PORT_MASTER_COUNTRY',
        filter: true,
        filterPlaceholder: 'PORT_MASTER_SEARCH_COUNTRY',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'countryName',
        filterByServer: true,
        sortable: true,
        sortField: 'countryName'
      },
      {
        field: 'timezone',
        header: 'PORT_MASTER_TIMEZONE',
        filter: true,
        filterPlaceholder: 'PORT_MASTER_SEARCH TIMEZONE',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'timezone',
        filterByServer: true,
        sortable: true,
        sortField: 'timezone'
      },
      {
        field: 'waterDensity',
        header: 'PORTMASTER_DENSITY_OF_WATER',
        filter: true,
        filterPlaceholder: 'PORT_MASTER_SEARCH_DENSITY_OF_WATER',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'waterDensity',
        filterByServer: true,
        sortable: true,
        sortField: 'waterDensity'
      },
      // {
      //   field: 'temperature',
      //   header: 'PORT_MASTER_TEMPERATURE',                            //This will be required when more fields are added to the api.
      //   filter: true,
      //   filterPlaceholder: 'PORT_MASTER_SEARCH_TEMPERATURE',
      //   filterType: DATATABLE_FILTER_TYPE.TEXT,
      //   filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
      //   filterField: 'temperature',
      //   sortable: true,
      //   sortField: 'temperature'
      // },
      // {
      //   field: 'maximumDraft',
      //   header: 'PORT_MASTER_MAXIMUM DRAFT',                        //This will be required when more fields are added to the api.
      //   filter: true,
      //   filterPlaceholder: 'PORT_MASTER_SEARCH_MAXIMUM_DRAFT',
      //   filterType: DATATABLE_FILTER_TYPE.TEXT,
      //   filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
      //   filterField: 'maximumDraft',
      //   sortable: true,
      //   sortField: 'maximumDraft'
      // }
    ]
    return columns;
  }

  /**
   * Method to set  berth form details.
   *
   * @param {*} berthForm
   * @memberof PortMasterTransformationService
   */
  setBerthFormDetails(berthForm: any) {
    this.berthFomDetails = berthForm;
  }

  /**
   *Method to get berth form details.
   *
   * @return {*}
   * @memberof PortMasterTransformationService
   */
  getBerthFormDetails() {
    return this.berthFomDetails;
  }

  /**
   * Method to get berth grid columns list.
   *
   * @memberof PortMasterTransformationService
   */
  getBerthGridColumns(): IDataTableColumn[] {
    let columns: IDataTableColumn[] = [
      {
        field: 'slNo',
        header: 'SL',
        fieldType: DATATABLE_FIELD_TYPE.SLNO,
        fieldHeaderClass: 'column-sl',
        fieldClass: 'sl'
      },
      {
        field: 'berthName',
        header: 'PORTMASTER_BERTH_NAME',
        fieldType: DATATABLE_FIELD_TYPE.TEXT,
        fieldPlaceholder: 'PORTMASTER_BERTH_NAME_PLACEHOLDER',
        errorMessages: {
          'maxlength': 'PORTMASTER_BERTH_NAME_MAX_LENGTH'
        }
      },
      {
        field: 'maxDraft',
        header: 'PORTMASTER_BERTH_MAX_DRAFT',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        fieldPlaceholder: 'PORTMASTER_BERTH_MAX_PLACEHOLDER',
        errorMessages: {
          'required': 'PORTMASTER_BERTH_MAX_DRAFT_REQUIRED'
        }
      },
      {
        field: 'depthInDatum',
        header: 'PORTMASTER_BERTH_DEPTH',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        fieldPlaceholder: 'PORTMASTER_BERTH_DEPTH_PLACEHOLDER'
      },
      {
        field: 'maxLoa',
        header: 'PORTMASTER_BERTH_MAXIMUM_LOA',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        fieldPlaceholder: 'PORTMASTER_BERTH_MAXIMUM_LOA_PLACEHOLDER',
        errorMessages: {
          'required': 'PORTMASTER_BERTH_MAXIMUM_LOA_REQUIRED'
        }
      },
      {
        field: 'maxDwt',
        header: 'PORTMASTER_BERTH_MAXIMUM_DWT',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        fieldPlaceholder: 'PORTMASTER_BERTH_MAXIMUM_DWT_PLACEHOLDER',
        errorMessages: {
          'required': 'PORTMASTER_BERTH_MAXIMUM_DWT_REQUIRED'
        }
      },
      {
        field: 'maxShipDepth',
        header: 'PORTMASTER_BERTH_MAX_PERMISSIBLE_SHIPS',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        fieldPlaceholder: 'PORTMASTER_BERTH_MAX_PERMISSIBLE_SHIPS_PLACEHOLDER',
        errorMessages: {
          'required': 'PORTMASTER_BERTH_MANIFOLD_HEIGHT_REQUIRED'
        }
      },
      {
        field: 'maxManifoldHeight',
        header: 'PORTMASTER_BERTH_MANIFOLD_HEIGHT',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        fieldPlaceholder: 'PORTMASTER_BERTH_MANIFOLD_HEIGHT_PLACEHOLDER',
        errorMessages: {
          'required': 'PORTMASTER_BERTH_MANIFOLD_HEIGHT_REQUIRED'
        }
      },
      {
        field: 'minUKC',
        header: 'PORTMASTER_BERTH_UKC_DETAILS',
        fieldType: DATATABLE_FIELD_TYPE.TEXT,
        fieldPlaceholder: 'PORTMASTER_BERTH_UKC_DETAILS_PLACEHOLDER',
        errorMessages: {
          'required': 'PORTMASTER_BERTH_MINIMUM_UKC_REQUIRED'
        }
      },
      {
        field: 'regulationAndRestriction',
        header: 'PORTMASTER_BERTH_RESTRICTIONS',
        fieldType: DATATABLE_FIELD_TYPE.TEXT,
        fieldPlaceholder: 'PORTMASTER_BERTH_RESTRICTIONS_PLACEHOLDER',
        errorMessages: {
          'maxlength': 'PORTMASTER_BERTH_RESTRICTIONS_MAX_LENGTH'
        }
      }
    ];

    const actions: DATATABLE_ACTION[] = [];
    actions.push(DATATABLE_ACTION.DELETE);
    const action: IDataTableColumn = {
      field: 'actions',
      header: '',
      fieldType: DATATABLE_FIELD_TYPE.ACTION,
      actions: actions
    };
    columns = [...columns, action];
    return columns;
  }

  /**
   * Method to convert berth-details as valueObject
   *
   * @param {IBerthInfo} berthList
   * @param {boolean} [isVisible=true]
   * @param {boolean} [isEditMode=true]
   * @param {boolean} [isModified=true]
   * @param {boolean} [isEditable=true]
   * @param {boolean} [isNewValue=false]
   * @return {*}  {IBerthValueObject}
   * @memberof PortMasterTransformationService
   */
  getBerthListAsValueObject(berthList: IBerthInfo, isVisible = true, isEditMode = true, isModified = true, isEditable = true, isNewValue = true): IBerthValueObject {
    const _berth = <IBerthValueObject>{};
    _berth.berthId = berthList.berthId;
    _berth.portId = berthList.portId;
    _berth.berthName = new ValueObject<string>(berthList.berthName, isVisible, isEditMode, isModified, isEditable);
    _berth.maxDraft = new ValueObject<number>(berthList.maxDraft, isVisible, isEditMode, isModified, isEditable);
    _berth.depthInDatum = new ValueObject<number>(berthList.depthInDatum, isVisible, isEditMode, isModified, isEditable);
    _berth.maxLoa = new ValueObject<number>(berthList.maxLoa, isVisible, isEditMode, isModified, isEditable);
    _berth.maxDwt = new ValueObject<number>(berthList.maxDwt, isVisible, isEditMode, isModified, isEditable);
    _berth.maxShipDepth = new ValueObject<number>(berthList.maxShipDepth, isVisible, isEditMode, isModified, isEditable);
    _berth.maxManifoldHeight = new ValueObject<number>(berthList.maxManifoldHeight, isVisible, isEditMode, isModified, isEditable);
    _berth.minUKC = new ValueObject<string>(berthList.minUKC, isVisible, isEditMode, isModified, isEditable);
    _berth.regulationAndRestriction = new ValueObject<string>(berthList.regulationAndRestriction, isVisible, isEditMode, isModified, isEditable);
    _berth.isActionsEnabled = isNewValue;
    _berth.isAdd = isNewValue;
    return _berth;
  }

}

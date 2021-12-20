import { Injectable } from '@angular/core';
import { IDataTableColumn, DATATABLE_FILTER_TYPE, DATATABLE_FILTER_MATCHMODE, DATATABLE_FIELD_TYPE, DATATABLE_ACTION } from '../../../shared/components/datatable/datatable.model';
import { AdminModule } from '../admin.module';


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
  selectedPortLocation: { lat: number, lon: number }
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
        'required': 'PORT_MASTER_PORT_NAME_REQUIRED'
      },
      portCode: {
        'required': "PORT_MASTER_PORT_CODE_REQUIRED",
      },
      portTimeZone: {
        'required': "PORT_MASTER_TIMEZONE_REQUIRED",
      },
      country: {
        'required': "PORT_MASTER_COUNTRY_REQUIRED",
      },
      highTide: {
        'required': "PORT_MASTER_TIDE_HEIGHT_REQUIRRED",
        'invalidNumber': "PORT_MASTER_TIDE_HEIGHT_INVALID"
      },
      lowTide: {
        'required': "PORT_MASTER_LOW_TIDE_HEIGHT_REQUIRED",
        'invalidNumber': "PORT_MASTER_LOW_TIDE_HEIGHT_INVALID"
      },
      permissibleShipsAtDraft: {
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
  getPortListDatatableColumns(): IDataTableColumn[] {
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
      }
      // {
      //   field: 'country',                                   //This will be needed later when more fields are added to the api.
      //   header: 'PORT_MASTER_COUNTRY',
      //   filter: true,
      //   filterPlaceholder: 'PORT_MASTER_SEARCH_COUNTRY',
      //   filterType: DATATABLE_FILTER_TYPE.TEXT,
      //   filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
      //   filterField: 'country',
      //   sortable: true,
      //   sortField: 'country'
      // },
      , {
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
      }
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
  setBerthFomDetails(berthForm: any) {
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
   *
   * Method to set validation message for add berth page.
   * @return {*}
   * @memberof PortMasterTransformationService
   */

  setValidationMessageForAddBerth() {
    return {
      berthName: {
        'required': 'PORTMASTER_BERTH_NAME'
      },
      maxShipDepth: {
        'required': 'PORTMASTER_BERTH_DEPTH_REQUIRED'
      },
      maxLoa: {
        'required': 'PORTMASTER_BERTH_MAXIMUM_LOA_REQUIRED',
        'invalidNumber': 'PORTMASTER_BERTH_MAXIMUM_LOA_INVALID'
      },
      maxDwt: {
        'required': 'PORTMASTER_BERTH_MAXIMUM_DWT_REQUIRED',
        'invalidNumber': 'PORTMASTER_BERTH_MAXIMUM_DWT_INVALID'
      },
      maxManifoldHeight: {
        'required': 'PORTMASTER_BERTH_MANIFOLD_HEIGHT_REQUIRED',
        'invalidNumber': 'PORTMASTER_BERTH_MANIFOLD_HEIGHT_INVALID'
      },
      minUkc: {
        'required': 'PORTMASTER_BERTH_MINIMUM_UKC_REQUIRED',
        'invalidNumber': 'PORTMASTER_BERTH_MINIMUM_UKC_INVALID'
      }
    }
  }


  /**
   * Method to get previously selected port location
   *
   * @memberof PortMasterTransformationService
   */
   getSelectedPortLocation() {
    return this.selectedPortLocation;
  }

  /**
   * Method to set current location
   *
   * @memberof PortMasterTransformationService
   */
  setSelectedLocation(selectedPortLocation: any) {
    if (selectedPortLocation) {
      this.selectedPortLocation = {
        lat: selectedPortLocation[0],
        lon: selectedPortLocation[1],
      }
    }
  }


  /**
   * Method to get berth grid columns list.
   *
   * @memberof PortMasterTransformationService
   */
  getBerthGridColumns()  {
    let columns :IDataTableColumn[] =
    [
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
        filter: true,
        filterPlaceholder: '',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        fieldType: DATATABLE_FIELD_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'berthName',
        sortable: true,
        sortField: 'berthName',
        editable: true,

      },
      {
        field: 'maxShipDepth',
        header: 'PORTMASTER_BERTH_DEPTH',
        filter: true,
        filterPlaceholder: '',
        fieldType: DATATABLE_FIELD_TYPE.TEXT,
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'maxShipDepth',
        sortable: true,
        sortField: 'maxShipDepth',
        editable: true
      },
      {
        field: 'maxLoa',
        header: 'PORTMASTER_BERTH_MAXIMUM_LOA',
        filter: true,
        filterPlaceholder: '',

        fieldType: DATATABLE_FIELD_TYPE.TEXT,
        filterField: 'maxLoa',
        sortable: true,
        sortField: 'maxLoa',
        editable: true
      },
      {
        field: 'maxDwt',
        header: 'PORTMASTER_BERTH_MAXIMUM_DWT',
        filter: true,
        filterPlaceholder: '',
        fieldType: DATATABLE_FIELD_TYPE.TEXT,
        filterField: 'maxDwt',
        sortable: true,
        sortField: 'maxDwt',
        editable: true
      },
      {
        field: 'maxManifoldHeight',
        header: 'PORTMASTER_BERTH_MANIFOLD_HEIGHT',
        filter: true,
        filterPlaceholder: '',
        fieldType: DATATABLE_FIELD_TYPE.TEXT,
        filterField: 'maxManifoldHeight',
        sortable: true,
        sortField: 'maxManifoldHeight',
        editable: true
      },
      {
        field: 'minUkc',
        header: 'PORTMASTER_BERTH_MINIMUM_UKC',
        filter: true,
        filterPlaceholder: '',
        fieldType: DATATABLE_FIELD_TYPE.TEXT,
        filterField: 'minUkc',
        sortable: true,
        sortField: 'minUkc',
        editable: true
      }
    ];
    const actions: DATATABLE_ACTION[] = [];
      const action: IDataTableColumn = {
        field: 'actions',
        header: '',
        fieldType: DATATABLE_FIELD_TYPE.ACTION,
        fieldValueIcon: '##',
        actions: actions
      };
      columns = [...columns, action];
    return columns;
  }
}

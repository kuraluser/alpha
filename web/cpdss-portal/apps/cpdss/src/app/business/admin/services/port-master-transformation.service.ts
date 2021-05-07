import { Injectable } from '@angular/core';
import { IDataTableColumn, DATATABLE_FILTER_TYPE, DATATABLE_FILTER_MATCHMODE } from '../../../shared/components/datatable/datatable.model';
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
      },
      lowTide: {
        'required': "PORT_MASTER_LOW_TIDE_HEIGHT_REQUIRED",
      },
      permissibleShipsAtDraft: {
        'required': "PORT_MASTER_MAX_PERMISSIBLE_SHIPS",
      },
      densityOfWater: {
        'required': "PORT_MASTER_DENSITY_OF_WATER_REQUIRED",
      },
      ambientTemperature: {
        'required': "PORT_MASTER_AMBIENT_TEMPERATURE_REQUIRED",
      },

    }
  }

  /**
   * Method for setting Port List grid column
   * @return {IDataTableColumn[]}
   * @memberof PortMasterTransformationService
   */
  getPortListDatatableColumns(): IDataTableColumn[] {
    return [
      {
        field: 'portName',
        header: 'PORT_MASTER_PORT_NAME',
        filter: true,
        filterPlaceholder: 'PORT_MASTER_SEARCH_PORT_NAME',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'portName',
        sortable: true,
        sortField: 'portName'
      },
      {
        field: 'portCode',
        header: 'PORT_MASTER_PORT_CODE',
        filter: true,
        filterPlaceholder: 'PORT_MASTER_SEARCH_PORT_CODE',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'portCode',
        sortable: true,
        sortField: 'portCode'
      },
      {
        field: 'country',
        header: 'PORT_MASTER_COUNTRY',
        filter: true,
        filterPlaceholder: 'PORT_MASTER_SEARCH_COUNTRY',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'country',
        sortable: true,
        sortField: 'country'
      },
      {
        field: 'timeZone',
        header: 'PORT_MASTER_TIMEZONE',
        filter: true,
        filterPlaceholder: 'PORT_MASTER_SEARCH TIMEZONE',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'timeZone',
        sortable: true,
        sortField: 'timeZone'
      },
      {
        field: 'densityOfWater',
        header: 'PORTMASTER_DENSITY_OF_WATER',
        filter: true,
        filterPlaceholder: 'PORT_MASTER_SEARCH_DENSITY_OF_WATER',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'densityOfWater',
        sortable: true,
        sortField: 'densityOfWater'
      },
      {
        field: 'temperature',
        header: 'PORT_MASTER_TEMPERATURE',
        filter: true,
        filterPlaceholder: 'PORT_MASTER_SEARCH_TEMPERATURE',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'temperature',
        sortable: true,
        sortField: 'temperature'
      },
      {
        field: 'maximumDraft',
        header: 'PORT_MASTER_MAXIMUM DRAFT',
        filter: true,
        filterPlaceholder: 'PORT_MASTER_SEARCH_MAXIMUM_DRAFT',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'maximumDraft',
        sortable: true,
        sortField: 'maximumDraft'
      }
    ]
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
      berthDepth: {
        'required': 'PORTMASTER_BERTH_DEPTH_REQUIRED'
      },
      maxLoa: {
        'required': 'PORTMASTER_BERTH_MAXIMUM_LOA_REQUIRED'
      },
      maxDwt: {
        'required': 'PORTMASTER_BERTH_MAXIMUM_DWT_REQUIRED'
      },
      maxManifoldHeight: {
        'required': 'PORTMASTER_BERTH_MANIFOLD_HEIGHT_REQUIRED'
      },
      minUkc: {
        'required': 'PORTMASTER_BERTH_MINIMUM_UKC_REQUIRED'
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
}

import { ICrewDetails, ICrewMasterList } from './../models/crew.model';
import { AdminModule } from './../admin.module';
import { Injectable } from '@angular/core';
import { IDataTableColumn, DATATABLE_FILTER_TYPE, DATATABLE_FILTER_MATCHMODE, DATATABLE_FIELD_TYPE, DATATABLE_ACTION } from '../../../shared/components/datatable/datatable.model';
import { IPermission } from '../../../shared/models/user-profile.model';
import { ValueObject } from '../../../shared/models/common.model';

/**
 * Service for transformation of crew data
 * @export
 * @class CrewMasterTransformationService
 */
@Injectable({
  providedIn: AdminModule
})
export class CrewMasterTransformationService {

  constructor() { }

  /**
   * Method to set validation message for columns
   * @return {*}
   * @memberof CrewMasterTransformationService
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
      ambientTemperature: {
        'required': "PORT_MASTER_AMBIENT_TEMPERATURE_REQUIRED",
        'invalidNumber': "PORT_MASTER_AMBIENT_TEMPERATURE_INVALID"
      },

    }
  }
  /**
   * Method for setting Crew List grid column
   * @return {IDataTableColumn[]}
   * @memberof CrewMasterTransformationService
   */
  getCrewListDatatableColumns(permission: IPermission): IDataTableColumn[] {
    const columns: IDataTableColumn[] = [
      {
        field: 'slNo',
        header: 'SL',
        fieldType: DATATABLE_FIELD_TYPE.SLNO,
        fieldHeaderClass: 'column-sl',
        fieldClass: 'sl'
      },
      {
        field: 'crewName',
        header: 'CREW_LIST_NAME',
        filter: true,
        filterPlaceholder: 'SEARCH_CREW_NAME',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'crewName',
        sortable: true,
        sortField: 'crewName',
        filterByServer: true
      },
      {
        field: 'crewRank',
        header: 'CREW_LIST_RANK',
        filter: true,
        filterPlaceholder: 'SEARCH_CREW_RANK',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'crewRank',
        sortable: true,
        sortField: 'crewRank',
        filterByServer: true
      },
      {
        field: 'vesselInformation',
        header: 'CREW_LIST_VESSEL',
        filter: true,
        filterPlaceholder: 'SEARCH_CREW_VESSEL',
        fieldType: DATATABLE_FIELD_TYPE.ARRAY,
        filterType: DATATABLE_FILTER_TYPE.ARRAY,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        arrayLabelField: 'vesselLabel',
        arrayFilterField: 'vesselName',
        filterByServer: true
      }
    ]
    return columns;
  }

  /**
     * Method for formating crew data
     *
     * @param {ICrewMasterList} crewDetails
     * @return {*}
     * @memberof CrewMasterTransformationService
     */
  formatCrew(crewDetails: ICrewMasterList) {
    crewDetails.vesselName = crewDetails.vesselInformation?.map(info => info?.vessel?.name);
    crewDetails.vesselLabel = crewDetails.vesselName?.join();
    return crewDetails;
  }

}

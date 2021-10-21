import { Injectable } from '@angular/core';

import { AdminModule } from '../admin.module';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';
import { TimeZoneTransformationService } from '../../../shared/services/time-zone-conversion/time-zone-transformation.service';

import { DATATABLE_FIELD_TYPE, DATATABLE_FILTER_MATCHMODE, DATATABLE_FILTER_TYPE } from '../../../shared/components/datatable/datatable.model';

/**
 * Service for transformation of Vessel information data
 * @export
 * @class VesselInformationTransformationService
 */
@Injectable({
  providedIn: AdminModule
})
export class VesselInformationTransformationService {

  constructor(
    private timeZoneTransformationService: TimeZoneTransformationService,
  ) {}

  /**
   * function to return datatable columns for Vessel listing
   *
   * @return {*}  {*}
   * @memberof VesselInformationTransformationService
   */
  getVesselInfoTableColumns(): any {
    let columns: any[] = [
      {
        field: 'slNo',
        header: 'SL',
        fieldType: DATATABLE_FIELD_TYPE.SLNO,
        fieldHeaderClass: 'column-sl',
        fieldClass: 'sl'
      },
      {
        field: 'name',
        header: 'VESSEL_MASTER_INFORMATION_VESSEL_NAME',
        filter: true,
        filterPlaceholder: 'VESSEL_MASTER_INFORMATION_VESSEL_NAME_PLACEHOLDER',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'name',
        filterByServer: true,
        sortable: true,
        sortField: 'name'
      },
      // { TODO: may be use in future
      //   field: 'owner',
      //   header: 'VESSEL_MASTER_INFORMATION_OWNER',
      //   filter: true,
      //   filterPlaceholder: 'VESSEL_MASTER_INFORMATION_OWNER_PLACEHOLDER',
      //   filterType: DATATABLE_FILTER_TYPE.TEXT,
      //   filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
      //   filterField: 'owner',
      //   filterByServer: true,
      //   sortable: true,
      //   sortField: 'owner'
      // },
      {
        field: 'typeOfShip',
        header: 'VESSEL_MASTER_INFORMATION_VESSEL_TYPE',
        filter: true,
        filterPlaceholder: 'VESSEL_MASTER_INFORMATION_VESSEL_TYPE_PLACEHOLDER',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'typeOfShip',
        filterByServer: true,
        sortable: true,
        sortField: 'typeOfShip'
      },
      {
        field: 'builder',
        header: 'VESSEL_MASTER_INFORMATION_BUILDER',
        filter: true,
        filterPlaceholder: 'VESSEL_MASTER_INFORMATION_BUILDER_PLACEHOLDER',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'builder',
        filterByServer: true,
        sortable: true,
        sortField: 'builder'
      },
      {
        field: 'dateOfLaunching',
        header: 'VESSEL_MASTER_INFORMATION_DATE_OF_LAUNCH',
        filter: true,
        filterPlaceholder: 'CARGO_HISTORY_TABLE_VESSEL_NAME_SEARCH_PLACEHOLDER',
        filterType: DATATABLE_FILTER_TYPE.DATE,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'dateOfLaunching',
        dateFormat: this.timeZoneTransformationService.getMappedConfigurationDateFormat(AppConfigurationService.settings?.dateFormat),
        filterByServer: true,
        sortable: true,
        sortField: 'dateOfLaunching'
      }
    ];
    return columns;
  }
}

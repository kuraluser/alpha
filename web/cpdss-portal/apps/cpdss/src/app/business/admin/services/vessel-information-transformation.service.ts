import { Injectable } from '@angular/core';
import { AdminModule } from '../admin.module';

import { DATATABLE_FIELD_TYPE } from '../../../shared/components/datatable/datatable.model';

/**
 * Service for transformation of Vessel information data
 * @export
 * @class VesselInformationTransformationService
 */
@Injectable({
  providedIn: AdminModule
})
export class VesselInformationTransformationService {

  constructor() { }

  getVesselInfoTableColumns(): any{
    let columns: any[] = [
      {
        field: 'slNo',
        header: 'SL',
        fieldType: DATATABLE_FIELD_TYPE.SLNO,
        fieldHeaderClass: 'column-sl',
        fieldClass: 'sl'
      },
      {
        field: 'vesselName',
        header: 'VESSEL_MASTER_INFORMATION_VESSEL_NAME',
      },
      {
        field: 'owner',
        header: 'VESSEL_MASTER_INFORMATION_OWNER',
      },
      {
        field: 'vesselType',
        header: 'VESSEL_MASTER_INFORMATION_VESSEL_TYPE',
      },
      {
        field: 'builder',
        header: 'VESSEL_MASTER_INFORMATION_BUILDER',
      },
      {
        field: 'dateOfLaunch',
        header: 'VESSEL_MASTER_INFORMATION_DATE_OF_LAUNCH',
      }
    ];
    return columns;
  }
}

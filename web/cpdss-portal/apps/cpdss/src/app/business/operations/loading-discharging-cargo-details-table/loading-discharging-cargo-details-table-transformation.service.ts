import { Injectable } from '@angular/core';
import { DATATABLE_FIELD_TYPE } from '../../../shared/components/datatable/datatable.model';

/**
 * Transformation Service for cargo details table
 *
 * @export
 * @class LoadingDischargingCargoDetailsTableTransformationService
 */
@Injectable()
export class LoadingDischargingCargoDetailsTableTransformationService {

  constructor() { }

  /**
  * Method for setting cargo details grid
  *
  * @memberof LoadableStudyPatternTransformationService
  */
 getColumnFields() {
  const columns = [
    { field: 'abbreviation', header: 'VOYAGE_STATUS_CARGO_CONDITION_GRADES' },
    { field: 'plannedWeight', header: 'VOYAGE_STATUS_CARGO_CONDITION_PLANNED' , fieldType:  DATATABLE_FIELD_TYPE.NUMBER},
    { field: 'actualWeight', header: 'VOYAGE_STATUS_CARGO_CONDITION_Actual' , fieldType:  DATATABLE_FIELD_TYPE.NUMBER },
    { field: 'difference', header: 'VOYAGE_STATUS_CARGO_CONDITION_DIFFERENCE' , fieldType:  DATATABLE_FIELD_TYPE.NUMBER}
  ];

  return columns;
}
}

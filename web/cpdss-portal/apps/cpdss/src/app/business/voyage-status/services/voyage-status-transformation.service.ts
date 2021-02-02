import { Injectable } from "@angular/core";
import { Subject } from "rxjs";
import { IDataTableColumn } from "../../../shared/components/datatable/datatable.model";
/**
 * Service for voyage-status tranformation
 *
 * @export
 * @class VoyageStatusTransformationService
*/
@Injectable()
export class VoyageStatusTransformationService {
    columns: IDataTableColumn[];

    public portOrderChange = new Subject();
    voyageDistance: number;

    constructor() { }

    /**
      * Set validation Error to form control
      */
    setValidationErrorMessageForPortRotationRibbon() {
        return {

            eta: {
                'required': 'PORT_ROTATION_RIBBON_ETA_REQUIRED',
            },
            etd: {
                'required': 'PORT_ROTATION_RIBBON_ETD_REQUIRED',
            },
            etaTime: {
                'required': 'PORT_ROTATION_RIBBON_TIME_REQUIRED',
            },
            etdTime: {
                'required': 'PORT_ROTATION_RIBBON_TIME_REQUIRED',
            },
            distance: {
                'required': 'PORT_ROTATION_RIBBON_DISTANCE_REQUIRED',
            }
        }
    }

   getColumnFields(){
       this.columns = [
        { field: 'abbreviation', header: 'VOYAGE_STATUS_CARGO_CONDITION_GRADES' },
        { field: 'plannedWeight', header: 'VOYAGE_STATUS_CARGO_CONDITION_PLANNED' },
        { field: 'actualWeight', header: 'VOYAGE_STATUS_CARGO_CONDITION_Actual' },
        { field: 'difference', header: 'VOYAGE_STATUS_CARGO_CONDITION_DIFFERENCE' }
    ];

    return this.columns
   }
}
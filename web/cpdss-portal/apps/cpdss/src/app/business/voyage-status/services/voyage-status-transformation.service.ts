import { Injectable } from "@angular/core";
import { Subject } from "rxjs";
/**
 * Service for voyage-status tranformation
 *
 * @export
 * @class VoyageStatusTransformationService
*/
@Injectable()
export class VoyageStatusTransformationService {
    public portOrderChange = new Subject();

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
}
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { LoadableQuantityModel, LoadableQuantityResponseModel, LodadableQuantity } from '../models/loadable-quantity.model';

@Injectable()
export class LoadableQuantityApiService {

  /**
   * 
   * Api Service for Loadable Quantity
   */
  constructor(private commonApiService: CommonApiService) { }

  /**
   * 
   * @param vesselId 
   * @param voyageId 
   * @param loadableStudyId 
   * Get api for loadable quantity
   */
  getLoadableQuantity(vesselId: number, voyageId: number, loadableStudyId: number): Observable<LoadableQuantityModel> {
    return this.commonApiService.get<LoadableQuantityModel>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies/${loadableStudyId}/loadable-quantity`);
  }

  /**
   * 
   * @param vesselId 
   * @param voyageId 
   * @param loadableStudyId 
   * @param loadableQuantity 
   * Save loadable quantity
   */
  saveLoadableQuantity(vesselId: number, voyageId: number, loadableStudyId: number, loadableQuantity: LodadableQuantity): Observable<LoadableQuantityResponseModel> {
    return this.commonApiService.post<LodadableQuantity, LoadableQuantityResponseModel>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies/${loadableStudyId}/loadable-quantity`, loadableQuantity);

  }
  /**
   * Set validation Error to form control
   */
  setValidationErrorMessage() {
    return {

      portName: {
        'required': 'LOADABLE_QUANTITY_PORTNAME_REQUIRED',
      },
      displacement: {
        'required': 'LOADABLE_QUANTITY_DISPLACEMENT_REQUIREDD',
      },
      arrivalMaxDraft: {
        'required': 'LOADABLE_QUANTITY_ARRIVAL_MAX_DRAFT_REQUIRED',
      },
      dwt: {
        'required': 'LOADABLE_QUANTITY_DWT_REQUIRED',
      },
      lwt: {
        'required': 'LOADABLE_QUANTITY_LWT_REQUIRED',
      },
      tpc: {
        'required': 'LOADABLE_QUANTITY_TPC_REQUIRED'
      },
      estSeaDensity: {
        'required': 'LOADABLE_QUANTITY_SEA_WATER_DENSITTY_REQUIRED'
      },
      sgCorrection: {
        'required': 'LOADABLE_QUANTITY_SG_CORRECTION_REQUIRED'
      },
      estimateSag: {
        'required': 'LOADABLE_QUANTITY_ESTIMATE_SAG_REQUIRED'
      },
      safCorrection: {
        'required': 'LOADABLE_QUANTITY_SAF_CORRECTION_REQUIRED'
      },
      foOnboard: {
        'required': 'LOADABLE_QUANTITY_FO_ONBOARD_REQUIRED'
      },
      doOnboard: {
        'required': 'LOADABLE_QUANTITY_DO_ONBOARD_REQUIRED'
      },
      freshWaterOnboard: {
        'required': 'LOADABLE_QUANTITY_FRESH_WATER_REQUIRED'
      },
      boilerWaterOnboard: {
        'required': 'LOADABLE_QUANTITY_BOILER_WATER_ONBOARD_REQUIRED'
      },
      ballast: {
        'required': 'LOADABLE_QUANTITY_BALLAST_REQUIRED'
      },
      constant: {
        'required': 'LOADABLE_QUANTITY_CONSTANT_REQUIRED'
      },
      others: {
        'required': 'LOADABLE_QUANTITY_OTHERS_REQUIRED'
      },
      distanceInSummerzone: {
        'required': 'LOADABLE_QUANTITY_DISATANCE_IN_SUMMERZONE_REQUIRED'
      },
      speedInSz: {
        'required': 'LOADABLE_QUANTITY_SPEED_IN_SZ_REQUIRED'
      },
      runningHours: {
        'required': 'LOADABLE_QUANTITY_RUNNING_HOURS_REQUIRED'
      },
      runningDays: {
        'required': 'LOADABLE_QUANTITY_RUNNING_DAYS_REQUIRED'
      },
      foConday: {
        'required': 'LOADABLE_QUANTITY_FO_CONS_PER_DAY_REQUIRED'
      },
      foConsInSz: {
        'required': 'LOADABLE_QUANTITY_FO_CONS_IN_SZ_REQUIRED'
      },
      subTotal: {
        'required': 'LOADABLE_QUANTITY_SUB_TOTAL_REQUIRED'
      },
      totalQuantity: {
        'required': 'LOADABLE_QUANTITY_TOTAL_QUANTITY_REQUIRED"'
      }
    }





  }
}

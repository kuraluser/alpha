import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { IDischargingPlanDetailsResponse } from '../models/loading-discharging.model';

/**
 * API service for Discharging-plan module
 */
@Injectable()
export class DischargingPlanApiService {

  constructor(
    private commonApiService: CommonApiService
  ) {}

  /**
   * API to get Discharging plan details
   *
   * @param {number} vesselId
   * @param {number} voyageId
   * @param {number} dischargeInfoId
   * @param {number} portRotationId
   * @return {*}  {Observable<any>}
   * @memberof DischargingPlanApiService
   */
  getDischargingPlanDetails(vesselId: number, voyageId: number, dischargeInfoId: number, portRotationId: number): Observable<IDischargingPlanDetailsResponse> {
    return this.commonApiService.get<IDischargingPlanDetailsResponse>(`vessels/${vesselId}/voyages/${voyageId}/discharging-info/${dischargeInfoId}/discharging-plan/${portRotationId}`);
  }

  /**
 * Method to get Discharging plan template.
 *
 * @param {number} vesselId
 * @param {number} voyageId
 * @param {number} dischargeInfoId
 * @param {number} portRotationId
 * @param {*} data
 * @return {*}  {Observable<any>}
 * @memberof DischargingPlanApiService
 */
 getDischargePlanTemplate(vesselId: number, voyageId: number, dischargeInfoId: number, portRotationId: number,data):Observable<any>{
  return this.commonApiService.postFile<IDischargingPlanDetailsResponse,any>(`vessels/${vesselId}/voyages/${voyageId}/discharging-info/${dischargeInfoId}/port-rotation/${portRotationId}/report`,data, { responseType: 'blob' as 'json' });

}
}

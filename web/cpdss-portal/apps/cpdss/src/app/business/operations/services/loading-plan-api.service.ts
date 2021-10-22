import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { ILoadingPlanDetails } from '../models/loading-discharging.model';

/**
 * Api Service for loading plan module
 *
 * @export
 * @class LoadingPlanApiService
 */
@Injectable()
export class LoadingPlanApiService {

  constructor(
    private commonApiService: CommonApiService
  ) { }

  /**
   * Method for getting loadable plan details
   *
   * @param {number} vesselId
   * @param {number} voyageId
   * @param {number} loadingInfoId
   * @param {number} portRotationId
   * @memberof LoadingPlanApiService
   */
  getLoadingPlanDetails(vesselId: number, voyageId: number, loadingInfoId: number, portRotationId: number){
    return this.commonApiService.get<ILoadingPlanDetails>(`vessels/${vesselId}/voyages/${voyageId}/loading-info/${loadingInfoId}/loading-plan/${portRotationId}`);
  }

/**
 * Method to get loading plan template.
 *
 * @param {number} vesselId
 * @param {number} voyageId
 * @param {number} loadingInfoId
 * @param {number} portRotationId
 * @param {*} data
 * @return {*}  {Observable<any>}
 * @memberof LoadingPlanApiService
 */
 downloadLoadingPlanTemplate(vesselId: number, voyageId: number, loadingInfoId: number, portRotationId: number,data):Observable<any>{
    return this.commonApiService.post<ILoadingPlanDetails,any>(`vessels/${vesselId}/voyages/${voyageId}/loading-info/${loadingInfoId}/port-rotation/${portRotationId}/report`,data, { responseType: 'blob' as 'json' });

  }
}
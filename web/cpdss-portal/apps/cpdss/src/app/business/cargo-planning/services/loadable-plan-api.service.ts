import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { CargoPlanningModule } from '../cargo-planning.module';
import { ILoadablePlanResponse, ISaveComment } from '../models/loadable-plan.model';
import { ICargoResponseModel } from '../../../shared/models/common.model';
import { IResponse , IConfirmStatusResponse } from '../../../shared/models/common.model';

/**
 * Api Service for loadable plan
 * @export
 * @class LoadablePlanApiService
*/

@Injectable({
  providedIn: CargoPlanningModule
})

export class LoadablePlanApiService {

  /**
  * 
  * Api Service for Loadable Plan
  */
  constructor(private commonApiService: CommonApiService) { }

  /**
  * 
  * @param {number} vesselId 
  * @param {number} voyageId 
  * @param {number} loadableStudyId 
  * @param {number} loadablePatternId
  * Get api for loadable plan details
  */
  getLoadablePlanDetails(vesselId: number, voyageId: number, loadableStudyId: number, loadablePatternId: number): Observable<ILoadablePlanResponse> {
    return this.commonApiService.get<ILoadablePlanResponse>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies/${loadableStudyId}/loadable-pattern-details/${loadablePatternId}`);
  }

  /**
  * 
  * Get api for cargo details
  */
  getCargos(): Observable<ICargoResponseModel> {
    return this.commonApiService.get<ICargoResponseModel>(`cargos`);
  }

  /**
 * 
 * @param {number} vesselId 
 * @param {number} voyageId 
 * @param {number} loadableStudyId 
 * @param {ISaveComment} loadableQuantity 
 * @param {number} loadablePatternId
 * Save Comments
 */
  saveComments(vesselId: number, voyageId: number, loadableStudyId: number, loadablePatternId: number, loadableQuantity: ISaveComment): Observable<IResponse> {
    return this.commonApiService.post<ISaveComment, IResponse>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies/${loadableStudyId}/loadable-patten/${loadablePatternId}/comment`, loadableQuantity);
  }

  /**
  * Api for get confirm status
  *
  * @param {number} vesselId
  * @param {number} voyageId
  * @param {number} loadableStudyId
  * @param {number} loadablePatternId
  * @returns {Observable<ICommingleCargoDetailsResponse>}
  * @memberof LoadablePlanApiService
  */
  getConfirmStatus(vesselId: number, voyageId: number, loadableStudyId: number, loadablePatternId: number): Observable<IConfirmStatusResponse> {
    return this.commonApiService.get<IConfirmStatusResponse>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies/${loadableStudyId}/confirm-plan-status/${loadablePatternId}`);
  }

  /**
  * Api for get confirm status
  *
  * @param {number} vesselId
  * @param {number} voyageId
  * @param {number} loadableStudyId
  * @param {number} loadablePatternId
  * @returns {Observable<ICommingleCargoDetailsResponse>}
  * @memberof LoadablePlanApiService
  */
  confirm(vesselId: number, voyageId: number, loadableStudyId: number, loadablePatternId: number): Observable<IResponse> {
    return this.commonApiService.post<any, IResponse>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies/${loadableStudyId}/confirm-plan/${loadablePatternId}`, {});
  }
}


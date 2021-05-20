import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { CargoPlanningModule } from '../cargo-planning.module';
import { ILoadablePlanResponse, ISaveComment, IUpdateUllageModel , IUpdatedUllageResponse  , IAlgoResponse , IValidateAndSaveStowage , IUpdatedRdgLevelResponse , ICommentResponse } from '../models/loadable-plan.model';
import { ICargoResponseModel , IValidateAndSaveResponse } from '../../../shared/models/common.model';
import { IResponse, IConfirmStatusResponse , LoadableQuantityModel  } from '../../../shared/models/common.model';

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
  saveComments(vesselId: number, voyageId: number, loadableStudyId: number, loadablePatternId: number, loadableQuantity: ISaveComment): Observable<ICommentResponse> {
    return this.commonApiService.post<ISaveComment, ICommentResponse>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies/${loadableStudyId}/loadable-patten/${loadablePatternId}/comment`, loadableQuantity);
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

  /**
  * Api for update ullage 
  *
  * @param {number} vesselId
  * @param {number} voyageId
  * @param {number} loadableStudyId
  * @param {number} loadablePatternId
  * @returns {Observable<IUpdatedUllageResponse>}
  * @memberof LoadablePlanApiService
  */
  updateUllage(vesselId: number, voyageId: number, loadableStudyId: number, loadablePatternId: number, data: IUpdateUllageModel): Observable<IUpdatedUllageResponse> {
    return this.commonApiService.post<IUpdateUllageModel, IUpdatedUllageResponse>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies/${loadableStudyId}/loadable-patterns/${loadablePatternId}/update-ullage`, data);
  }


  /**
  * Api for validate and save for edi stowage
  *
  * @param {number} vesselId
  * @param {number} voyageId
  * @param {number} loadableStudyId
  * @param {number} loadablePatternId
  * @returns {Observable<IUpdatedUllageResponse>}
  * @memberof LoadablePlanApiService
  */
  validateAndSave(vesselId: number, voyageId: number, loadableStudyId: number, loadablePatternId: number, data: any): Observable<IValidateAndSaveResponse> {
    return this.commonApiService.post<IValidateAndSaveStowage, IValidateAndSaveResponse>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies/${loadableStudyId}/loadable-patterns/${loadablePatternId}/validate-loadable-plan`, data);
  }

    /**
  * 
  * @param {number} vesselId 
  * @param {number} voyageId 
  * @param {number} loadableStudyId 
  * @param {number} loadablePatternId
  * Get api for algo error response
  */
  getAlgoErrorDetails(vesselId: number, voyageId: number, loadableStudyId: number, loadablePatternId: number): Observable<IAlgoResponse> {
    return this.commonApiService.get<IAlgoResponse>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies/${loadableStudyId}/loadable-pattern/${loadablePatternId}/algo-errors`);
  }

    /**
  * 
  * @param {number} vesselId 
  * @param {number} voyageId 
  * @param {number} loadableStudyId 
  * @param {number} loadablePatternId
  * Export data
  */
  export(vesselId: number, voyageId: number, loadableStudyId: number, loadablePatternId: number): Observable<any> {
    return this.commonApiService.get<any>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies/${loadableStudyId}/loadable-patten/${loadablePatternId}/report`, {responseType: 'blob' as 'json'});
  }

    /**
   * 
   * @param vesselId 
   * @param voyageId 
   * @param loadableStudyId 
   * @param portRotationId
   * Get api for loadable quantity
   */
    getLoadableQuantity(vesselId: number, voyageId: number, loadableStudyId: number, portRotationId: number): Observable<LoadableQuantityModel> {
      return this.commonApiService.get<LoadableQuantityModel>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies/${loadableStudyId}/loadable-quantity?portRotationId=${portRotationId}`);
    }
}


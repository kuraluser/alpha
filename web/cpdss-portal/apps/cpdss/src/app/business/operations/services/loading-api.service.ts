import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { IAlgoResponse } from '../../core/models/common.model';

/**
 * Api Service for Loadaing  module
 *
 * @export
 * @class LoadingApiService
 */
@Injectable()
export class LoadingApiService {

  constructor(private commonApiService: CommonApiService) { }


  /**
   * Post api call for generate loading plan
   *
   * @param {number} vesselId
   * @param {number} voyageId
   * @param {number} loadingInfoId
   * @return {*} 
   * @memberof LoadingApiService
   */
  generateLoadingPlan(vesselId: number, voyageId: number, loadingInfoId: number) {
    return this.commonApiService.post<any, any>(`vessels/${vesselId}/voyages/${voyageId}/loading-info/${loadingInfoId}/generate-loading-plan`, null);

  }


  /**
   * Get api call for algo errors.
   *
   * @param {number} vesselId
   * @param {number} voyageId
   * @param {number} dischargeStudyId
   * @return {*}  {Observable<IAlgoResponse>}
   * @memberof LoadingApiService
   */
  getAlgoErrorDetails(vesselId: number, voyageId: number, loadingInfoId: number, type = null): Observable<IAlgoResponse> {
    return this.commonApiService.get<IAlgoResponse>(`vessels/${vesselId}/voyages/${voyageId}/loading-info/${loadingInfoId}/algo-errors${type ? '/' + type : ''}`);
  }
}



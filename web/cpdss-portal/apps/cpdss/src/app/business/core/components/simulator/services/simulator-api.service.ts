import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { CommonApiService } from '../../../../../shared/services/common/common-api.service';

import { ISimulatorLoadingSequenceResponse, ISimulatorStowageResponse } from '../simulator.model';

/**
 * API service for simulator module
 */
@Injectable()
export class SimulatorApiService {

  constructor(private commonApiService: CommonApiService) {}

  /**
   * API for get stowage-plan JSON data
   * @param {number} vesselId
   * @param {number} loadableStudyId
   * @param {number} caseNo
   * @return {*}  {Observable<any>}
   * @memberof LoadablePlanApiService
   */
  getStowagePlanJsonData(vesselId: number, loadableStudyId:number, caseNo: string): Observable<ISimulatorStowageResponse> {
    return this.commonApiService.get<ISimulatorStowageResponse>(`simulator-json/vessels/${vesselId}/loadableStudyId/${loadableStudyId}/caseNumber/${caseNo}`);
  }

  /**
   * API for loading sequence JSON data
   * @param {number} vesselId
   * @param {number} loadingInfoId
   * @return {*}  {Observable<ISimulatorLoadingSequenceResponse>}
   * @memberof SimulatorApiService
   */
  getLoadingSequencePlanJson(vesselId: number, loadingInfoId:number): Observable<ISimulatorLoadingSequenceResponse> {
    return this.commonApiService.get<ISimulatorLoadingSequenceResponse>(`simulator-json/vessels/${vesselId}/loading-info/${loadingInfoId}`);
  }

}

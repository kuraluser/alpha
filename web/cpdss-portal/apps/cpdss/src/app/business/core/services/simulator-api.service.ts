import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CommonApiService } from '../../../shared/services/common/common-api.service';

import { ISimulatorResponse } from '../models/common.model';

@Injectable()
export class SimulatorApiService {

  constructor(private commonApiService: CommonApiService) {}

  /**
   * Api for get simulator JSON data
   * @param {number} vesselId
   * @param {number} loadableStudyId
   * @param {number} caseNo
   * @return {*}  {Observable<any>}
   * @memberof LoadablePlanApiService
   */
  getSimulatorJsonData(vesselId: number, loadableStudyId:number, caseNo: string): Observable<ISimulatorResponse> {
    return this.commonApiService.get<ISimulatorResponse>(`simulator-json/vessels/${vesselId}/loadableStudyId/${loadableStudyId}/caseNumber/${caseNo}`);
  }
}

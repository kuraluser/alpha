import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { ILoadablePlanResponse } from '../models/loadable-plan.model';

@Injectable({
  providedIn: 'root'
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
  * Get api for loadable quantity
  */
  getLoadablePlanDetails(vesselId: number, voyageId: number, loadableStudyId: number, loadablePatternId: number): Observable<ILoadablePlanResponse> {
    return this.commonApiService.get<ILoadablePlanResponse>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies/${'500'}/loadable-pattern-details/${'4'}`);
  }
}

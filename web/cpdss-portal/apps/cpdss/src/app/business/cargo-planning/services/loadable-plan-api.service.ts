import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { CommonApiService } from '../../../shared/services/common/common-api.service';

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
* @param vesselId 
* @param voyageId 
* @param loadableStudyId 
* @param loadablePatternId
* Get api for loadable quantity
*/
  getLoadablePlanEtaEtdDetails(vesselId: number, voyageId: number, loadableStudyId: number, loadablePatternId: number): Observable<any> {
    return this.commonApiService.get<any>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies/${loadableStudyId}/loadable-pattern-details/${loadablePatternId}`);
  }
}

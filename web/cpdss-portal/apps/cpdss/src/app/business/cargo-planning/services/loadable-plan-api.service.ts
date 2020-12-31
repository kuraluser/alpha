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
* @param {number} vesselId 
* @param {number} voyageId 
* @param {number} loadableStudyId 
* @param {number} loadablePatternId
* Get api for loadable plan Eta & Etd details
*/
  getLoadablePlanEtaEtdDetails(vesselId: number, voyageId: number, loadableStudyId: number, loadablePatternId: number): Observable<any> {
    return this.commonApiService.get<any>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies/${loadableStudyId}/loadable-pattern-details/${loadablePatternId}`);
  }


  /**
* 
* @param {number} vesselId 
* @param {number} voyageId 
* @param {number} loadableStudyId 
* @param {number} loadablePatternId
* Get api for commingled cargo details
*/
  getCommingledCargoDetails(vesselId: number, voyageId: number, loadableStudyId: number, loadablePatternId: number): Observable<any> {
    return this.commonApiService.get<any>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies/${loadableStudyId}/loadable-pattern-details/${loadablePatternId}`);
  }
}

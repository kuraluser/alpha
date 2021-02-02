import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { ICommingleCargoDetailsResponse } from '../models/cargo-planning.model';
import { ILoadablePatternResponse } from '../models/loadable-pattern.model';

/**
 * Api Service for Loadable Pattern History module
 *
 * @export
 * @class LoadablePatternHistoryApiService
 */
@Injectable({
  providedIn: 'root'
})
export class LoadablePatternHistoryApiService {

  constructor(private commonApiService: CommonApiService) { }

    /**
   * Set loadable study api
   *
   * @param {number} vesselId
   * @param {number} voyageId
   * @param {number} loadableStudyId
   * @returns {Observable<ILoadablePatternResponse>}
   * @memberof LoadablePatternHistoryApiService
   */
  getLoadablePatterns(vesselId: number, voyageId: number, loadableStudyId: number): Observable<ILoadablePatternResponse> {
    return this.commonApiService.get<ILoadablePatternResponse>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies/1330/loadable-patterns`);
  }


  /**
   * Api for get commingle cargo details
   *
   * @param {number} vesselId
   * @param {number} voyageId
   * @param {number} loadableStudyId
   * @param {number} loadablePatternId
   * @param {number} loadablePatternCommingleDetailsId
   * @returns {Observable<ICommingleCargoDetailsResponse>}
   * @memberof LoadablePatternHistoryApiService
   */
  getCommingleCargoDetails(vesselId: number, voyageId: number, loadableStudyId: number, loadablePatternId: number, loadablePatternCommingleDetailsId: number): Observable<ICommingleCargoDetailsResponse> {
    return this.commonApiService.get<ICommingleCargoDetailsResponse>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies/${loadableStudyId}/loadable-patterns/${loadablePatternId}/loadable-pattern-commingle-details/${loadablePatternCommingleDetailsId}`);
  }

}

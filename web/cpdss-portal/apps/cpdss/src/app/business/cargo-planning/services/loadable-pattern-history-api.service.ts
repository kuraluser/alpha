import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CommonApiService } from '../../../shared/services/common/common-api.service';
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
    return this.commonApiService.get<ILoadablePatternResponse>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies/${loadableStudyId}/loadable-patterns`);
  }

}

import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IResponse } from '../../../shared/models/common.model';
import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { ICargoResponseModel, IConfirmStatusResponse } from '../../core/models/common.model';
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
    return this.commonApiService.get<ILoadablePatternResponse>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies/${loadableStudyId}/loadable-patterns`);
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

  /**
 * Api for get confirm status
 *
 * @param {number} vesselId
 * @param {number} voyageId
 * @param {number} loadableStudyId
 * @param {number} loadablePatternId
 * @returns {Observable<ICommingleCargoDetailsResponse>}
 * @memberof LoadablePatternHistoryApiService
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
* @memberof LoadablePatternHistoryApiService
*/
  confirm(vesselId: number, voyageId: number, loadableStudyId: number, loadablePatternId: number): Observable<IResponse> {
    return this.commonApiService.post<any, IResponse>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies/${loadableStudyId}/confirm-plan/${loadablePatternId}`, {});
  }

   /**
  *
  * Get api for cargo details
  */
    getCargos(): Observable<ICargoResponseModel> {
      return this.commonApiService.get<ICargoResponseModel>(`cargos`);
    }

}

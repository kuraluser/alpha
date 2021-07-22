import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ICargoResponseModel } from '../../../shared/models/common.model';
import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { ILoadingInformation, ILoadingInformationResponse, ILoadingInformationSaveResponse } from '../models/loading-information.model';

/**
 * Api Service for Loadaing  module
 *
 * @export
 * @class LoadingInformationApiService
 */

@Injectable()
export class LoadingInformationApiService {
  constructor(
    private commonApiService: CommonApiService) {
  }


  /**
   * Get loadable study list
    * @param {number} vesselId
     * @param {number} voyageId
      * @param {number} portRotationId
   */
  getLoadingInformation(vesselId: number, voyageId: number, portRotationId: number): Observable<ILoadingInformationResponse> {
    return this.commonApiService.get<ILoadingInformationResponse>(`vessels/${vesselId}/voyages/${voyageId}/loading-info/0/port-rotation/${portRotationId}`);
  }

  /**
 * Get loadable study list
  * @param {number} vesselId
   * @param {number} voyageId
    * @param {ILoadingInformation} loadingInformation
 */
  saveLoadingInformation(vesselId: number, voyageId: Number, loadingInformation: ILoadingInformation): Observable<ILoadingInformationSaveResponse> {
    return this.commonApiService.post<ILoadingInformation, ILoadingInformationSaveResponse>(`vessels/${vesselId}/voyages/${voyageId}/loading-info`, loadingInformation);
  }



}

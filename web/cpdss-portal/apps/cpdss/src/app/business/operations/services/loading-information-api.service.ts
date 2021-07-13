import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ICargoResponseModel } from '../../../shared/models/common.model';
import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { LoadingModule } from '../loading/loading.module';
import { ILoadingInformationList, ILoadingInformationResponse, LoadingInformationDB } from '../models/loading-information.model';

/**
 * Api Service for Loadaing  module
 *
 * @export
 * @class LoadingInformationApiService
 */

@Injectable()
export class LoadingInformationApiService {
  private _loadingInformationDb: LoadingInformationDB;
  constructor(
    private commonApiService: CommonApiService) { 
      this._loadingInformationDb = new LoadingInformationDB();
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
* 
* Get api for cargo details
*/
  getCargos(): Observable<ICargoResponseModel> {
    return this.commonApiService.get<ICargoResponseModel>(`cargos`);
  }

  /**
     * Method to set cargonomination
     *
     * @param {ICargoNomination} cargoNomination
     * @param {number} vesselId
     * @param {number} voyageId
     * @param {number} loadableStudyId
     * @returns {Promise<number>}
     * @memberof LoadableStudyDetailsApiService
     */
   setLoadingInformation(loadingInformation: ILoadingInformationList, vesselId: number, voyageId: number): Promise<number> {
    loadingInformation.vesselId = vesselId;
    loadingInformation.voyageId = voyageId;
    return this._loadingInformationDb.loadingInformations.add(loadingInformation);
}
}

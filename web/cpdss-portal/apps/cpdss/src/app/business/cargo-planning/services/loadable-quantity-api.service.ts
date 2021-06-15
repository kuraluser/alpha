import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { LoadableQuantityModel, LoadableQuantityResponseModel, LodadableQuantity } from '../models/loadable-quantity.model';


@Injectable()
export class LoadableQuantityApiService {

  /**
   * 
   * Api Service for Loadable Quantity
   */
  constructor(private commonApiService: CommonApiService) { }

  /**
   * 
   * @param vesselId 
   * @param voyageId 
   * @param loadableStudyId 
   * @param portRotationId
   * Get api for loadable quantity
   */
  getLoadableQuantity(vesselId: number, voyageId: number, loadableStudyId: number, portRotationId: number): Observable<LoadableQuantityModel> {
    return this.commonApiService.get<LoadableQuantityModel>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies/${loadableStudyId}/loadable-quantity?portRotationId=${portRotationId ? portRotationId : 0}`);
  }

  /**
   * 
   * @param vesselId 
   * @param voyageId 
   * @param loadableStudyId 
   * @param loadableQuantity
   * Save loadable quantity
   */
  saveLoadableQuantity(vesselId: number, voyageId: number, loadableStudyId: number, loadableQuantity: LodadableQuantity): Observable<LoadableQuantityResponseModel> {
    return this.commonApiService.post<LodadableQuantity, LoadableQuantityResponseModel>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies/${loadableStudyId}/loadable-quantity`, loadableQuantity);

  }
}

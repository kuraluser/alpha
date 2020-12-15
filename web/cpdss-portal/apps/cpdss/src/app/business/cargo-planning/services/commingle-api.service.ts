import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { ICargoResponseModel, ICommingleResponseModel } from '../models/commingle.model';


/**
 * Api Service for commingle pop up
 *
 * @export
 * @class CommingleApiService
 */
@Injectable()
export class CommingleApiService {

  constructor(private commonApiService: CommonApiService) { }

  /**
   * Set loadable study api
   *
   * @memberof CommingleApiService
   */
  getCargos(): Observable<ICargoResponseModel> {
    return this.commonApiService.get<ICargoResponseModel>(`cargos`);
  }

  /**
   * Set loadable study api
   *
   * @param {number} vesselId
   * @param {number} voyageId
   * @param {LoadableStudy} loadableStudyId
   * @returns {Observable<ICommingleResponseModel>}
   * @memberof CommingleApiService
   */
  getCommingle(vesselId: number, voyageId: number, loadableStudyId: number): Observable<ICommingleResponseModel> {
    return this.commonApiService.get<ICommingleResponseModel>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies/${loadableStudyId}/commingle-cargo`);
  }

  /**
   * Set loadable study api
   *
   * @param {number} vesselId
   * @param {number} voyageId
   * @param {LoadableStudy} loadableStudyId
   * @returns {Observable<ICommingleResponseModel>}
   * @memberof CommingleApiService
   */
  saveVolMaxCommingle(vesselId: number, voyageId: number, loadableStudyId: number, payload): Observable<ICommingleResponseModel> {
    return this.commonApiService.postFormData<ICommingleResponseModel>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies/${loadableStudyId}/commingle-cargo`, payload);
  }

  /**
   * Set validation Error to form control
   */
  setValidationErrorMessage() {
    return {

      preferredTanks: {
        'required': 'COMMINGLE_PREFERRED_TANK_REQUIRED',
      }
    }
  }    
}

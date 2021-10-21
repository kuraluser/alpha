import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { IResponse } from './../../../shared/models/common.model';
import { OPERATIONS } from '../../core/models/common.model';

import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { IDischargingInformationPostData, IDischargingInformationResponse, IDischargingInformationSaveResponse, ILoadingInformation, ILoadingInformationResponse, ILoadingInformationSaveResponse } from '../models/loading-discharging.model';

/**
 * Api Service for Loading & Discharging information tab
 *
 * @export
 * @class LoadingDischargingInformationApiService
 */

@Injectable()
export class LoadingDischargingInformationApiService {
  constructor(
    private commonApiService: CommonApiService) {
  }

  /**
   * Methid to fetch loading information
   *
   * @param {number} vesselId
   * @param {number} voyageId
   * @param {number} portRotationId
   * @return {*}  {Observable<ILoadingInformationResponse>}
   * @memberof LoadingDischargingInformationApiService
   */
  getLoadingInformation(vesselId: number, voyageId: number, portRotationId: number): Observable<ILoadingInformationResponse> {
    return this.commonApiService.get<ILoadingInformationResponse>(`vessels/${vesselId}/voyages/${voyageId}/loading-info/0/port-rotation/${portRotationId}`);
  }

  /**
   * Method for saving loading information
   *
   * @param {number} vesselId
   * @param {Number} voyageId
   * @param {ILoadingInformation} loadingInformation
   * @return {*}  {Observable<ILoadingInformationSaveResponse>}
   * @memberof LoadingDischargingInformationApiService
   */
  saveLoadingInformation(vesselId: number, voyageId: Number, loadingInformation: ILoadingInformation): Observable<ILoadingInformationSaveResponse> {
    return this.commonApiService.post<ILoadingInformation, ILoadingInformationSaveResponse>(`vessels/${vesselId}/voyages/${voyageId}/loading-info`, loadingInformation);
  }

  /**
   * Method for Update discharging information
   *
   * @param {number} vesselId
   * @param {Number} voyageId
   * @param {IDischargingInformationPostData} dischargingInformation
   * @return {*}  {Observable<IDischargingInformationSaveResponse>}
   * @memberof LoadingDischargingInformationApiService
   */
  saveDischargingInformation(vesselId: number, voyageId: Number, dischargingInformation: IDischargingInformationPostData): Observable<IDischargingInformationSaveResponse> {
    return this.commonApiService.post<IDischargingInformationPostData, IDischargingInformationSaveResponse>(`vessels/${vesselId}/voyages/${voyageId}/discharging-info`, dischargingInformation);
  }

  /**
   * Method for fetching discharging information
   *
   * @param {number} vesselId
   * @param {number} voyageId
   * @param {number} portRotationId
   * @return {*}  {Observable<IDischargingInformationResponse>}
   * @memberof LoadingDischargingInformationApiService
   */
  getDischargingInformation(vesselId: number, voyageId: number, portRotationId: number): Observable<IDischargingInformationResponse> {
    return this.commonApiService.get<IDischargingInformationResponse>(`vessels/${vesselId}/voyages/${voyageId}/discharge-info/0/port-rotation/${portRotationId}`);
  }

  /**
   * Method to download template
   *
   * @param {number} id
   * @return {*}  {Observable<any>}
   * @memberof LoadingDischargingInformationApiService
 */
  downloadTemplate(id: number, operation: OPERATIONS): Observable<any> {
    if (operation === OPERATIONS.LOADING) {
      return this.commonApiService.get<any>(`loading/download/port-tide-template?loadingId=${id}`, { responseType: 'blob' as 'json' });
    } else {
      return this.commonApiService.get<any>(`discharging/download/port-tide-template?dischargingId=${id}`, { responseType: 'blob' as 'json' });
    }
  }

  /**
  * Method to upload template
  *
  * @param {number} id
  * @param {*} file
  * @return {*}  {Observable<IResponse>}
  * @memberof LoadingDischargingInformationApiService
  */
  uploadTemplate(id: number, file: any, operation: OPERATIONS): Observable<IResponse> {
    const formData: FormData = new FormData();
    formData.append('file', file);
    formData.append('portName', localStorage.getItem('selectedPortName') ?? '');
    formData.append('portId', localStorage.getItem('selectedPortId') ?? '');
    if (operation === OPERATIONS.LOADING) {
      return this.commonApiService.postFormData<any>(`loading/${id}/upload/port-tide-details`, formData);
    } else {
      return this.commonApiService.postFormData<any>(`discharging/${id}/upload/port-tide-details`, formData);
    }
  }
}

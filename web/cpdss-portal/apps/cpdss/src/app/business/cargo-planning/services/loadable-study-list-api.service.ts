import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { IResponse } from '../../../shared/models/common.model';
import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { INewLoadableStudy } from '../../core/components/new-loadable-study-popup/new-loadable-study-popup.model';
import { ILoadablePatternsResponse, ILoadableStudiesResponse, ILoadableStudyResponse, LoadableStudy } from '../models/loadable-study-list.model'
import { saveAs } from 'file-saver';
@Injectable()
export class LoadableStudyListApiService {
  loadableStudyList: LoadableStudy[];

  constructor(
    private commonApiService: CommonApiService,
    private http: HttpClient) { }


  /**
   * Get loadable study list
   */
  getLoadableStudies(vesselId: number, voyageId: number): Observable<ILoadableStudiesResponse> {
    return this.commonApiService.get<ILoadableStudiesResponse>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies
    `);

  }

  /**
 * Get loadable pattern list
 */
  getLoadablePatterns(vesselId: number, voyageId: number, loadableStudyId: number): Observable<ILoadablePatternsResponse> {
    return this.commonApiService.get<ILoadablePatternsResponse>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies/${loadableStudyId}/patterns
    `);
  }

  /**
   * Set loadable study api
   *
   * @param {number} vesselId
   * @param {number} voyageId
   * @param {LoadableStudy} loadableStudy
   * @returns {Observable<ILoadableStudyResponse>}
   * @memberof LoadableStudyListApiService
   */
  setLodableStudy(vesselId: number, voyageId: number, loadableStudy: INewLoadableStudy): Observable<ILoadableStudyResponse> {
    const formData: FormData = new FormData();
    formData.append('charterer', loadableStudy.charterer);
    formData.append('createdFromId', loadableStudy.createdFromId ? loadableStudy.createdFromId?.toString() : "");
    formData.append('detail', loadableStudy.detail);
    formData.append('draftMark', loadableStudy.draftMark ? loadableStudy.draftMark.toString() : "");
    formData.append('draftRestriction', loadableStudy.draftRestriction ? loadableStudy.draftRestriction?.toString() : "");
    formData.append('loadLineXId', loadableStudy.draftMark ? loadableStudy.loadLineXId.toString() : "");
    formData.append('name', loadableStudy.name);
    formData.append('subCharterer', loadableStudy.subCharterer);
    formData.append('maxAirTemperature', loadableStudy.maxAirTempExpected ? loadableStudy.maxAirTempExpected?.toString() : "");
    formData.append('maxWaterTemperature', loadableStudy.maxWaterTempExpected ? loadableStudy.maxWaterTempExpected?.toString() : "");
    formData.append('deletedAttachments', loadableStudy.deletedAttachments);
    for (let i = 0; i < loadableStudy.attachMail.length; i++) {
      if (loadableStudy.attachMail[i]?.size) {
        formData.append('files', loadableStudy.attachMail[i]);
      }

    }
    
    return this.commonApiService.postFormData<ILoadableStudyResponse>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies/${loadableStudy.id}`, formData);
  }

  /**
   *
   *
   * @param {number} vesselId
   * @param {number} voyageId
   * @param {number} loadableStudy
   * @returns {Observable<any>}
   * @memberof LoadableStudyListApiService
   */
  deleteLodableStudy(vesselId: number, voyageId: number, loadableStudyId: number): Observable<IResponse> {
    return this.commonApiService.delete<IResponse>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies/${loadableStudyId}`)
  }

  /**
  *
  *
  * @param {number} vesselId
  * @param {number} voyageId
  * @param {number} loadableStudy
  * @param {number} attachmentId
  * @memberof LoadableStudyListApiService
  */
  downloadAttachment(vesselId: number, voyageId: number, loadableStudyId: number, attachmentId: number) : Observable<any>{
    return this.commonApiService.get<any>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies/${loadableStudyId}/attachments/${attachmentId}`, {responseType: 'blob' as 'json'});
  }
}  

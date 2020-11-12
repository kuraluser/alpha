import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { INewLoadableStudy } from '../../core/components/new-loadable-study-popup/new-loadable-study-popup.model';
import { ILoadableStudiesResponse, ILoadableStudyResponse, LoadableStudy } from '../models/loadable-study-list.model'

@Injectable()
export class LoadableStudyListApiService {
  loadableStudyList: LoadableStudy[];

  constructor(private commonApiService: CommonApiService) { }


  /**
   * Get loadable study list
   */
  getLoadableStudies(vesselId: number, voyageId: number): Observable<ILoadableStudiesResponse> {
    return this.commonApiService.get<ILoadableStudiesResponse>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies
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
    formData.append('createdFromId', loadableStudy.createdFromId.toString());
    formData.append('detail', loadableStudy.detail);
    formData.append('draftMark', loadableStudy.draftMark !== undefined ? loadableStudy.draftMark.toString() : "");
    formData.append('draftRestriction', loadableStudy.draftRestriction?.toString());
    formData.append('loadLineXId', loadableStudy.draftMark !== undefined ? loadableStudy.loadLineXId.toString() : "");
    formData.append('name', loadableStudy.name);
    formData.append('subCharterer', loadableStudy.subCharterer);
    formData.append('maxAirTemperature', loadableStudy.maxAirTempExpected?.toString());
    formData.append('maxWaterTemperature', loadableStudy.maxWaterTempExpected?.toString());
    for (let i = 0; i < loadableStudy.attachMail.length; i++) {
      formData.append('files', loadableStudy.attachMail[i]);

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
  deleteLodableStudy(vesselId: number, voyageId: number, loadableStudyId: number): Observable<any> {
    return this.commonApiService.delete(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies/${loadableStudyId}`)
  }
}  

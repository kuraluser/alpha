import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { OPERATIONS } from '../../core/models/common.model';
import { ILoadingRate, IPump, IPumpData, ISequenceData, ISequenceDataResponse, ITank } from '../loading-discharging-sequence-chart/loading-discharging-sequence-chart.model';
import { OperationsModule } from '../operations.module';

/**
 * Service for loading discharging sequence chart
 *
 * @export
 * @class LoadingDischargingSequenceApiService
 */
@Injectable({
  providedIn: OperationsModule
})
export class LoadingDischargingSequenceApiService {

  constructor(private commonApiService: CommonApiService) {
  }

  /**
   * Fetch sequence data
   *
   * @param {number} vesselId
   * @param {number} voyageId
   * @param {number} infoId
   * @return {*}  {Observable<ISequenceDataResponse>}
   * @memberof LoadingDischargingSequenceApiService
   */
  getSequenceData(vesselId: number, voyageId: number, infoId: number, operation: OPERATIONS): Observable<ISequenceDataResponse> {
    let apiUrl = `vessels/${vesselId}/voyages/${voyageId}/`;
    apiUrl += operation === OPERATIONS.LOADING ? `loading-info/${infoId}/loading-sequence` : `discharging-info/${infoId}/discharging-sequence`;
    return this.commonApiService.get<ISequenceDataResponse>(apiUrl);
  }


}

import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CommonApiService } from '../../../shared/services/common/common-api.service';
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
   * @param {number} loaadingInfo
   * @return {*}  {Observable<ISequenceDataResponse>}
   * @memberof LoadingDischargingSequenceApiService
   */
  getSequenceData(vesselId: number, voyageId: number, loaadingInfo: number): Observable<ISequenceDataResponse> {
    return this.commonApiService.get<ISequenceDataResponse>(`vessels/1/voyages/2227/loading-info/125/loading-sequence`);
  }


}

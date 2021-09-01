import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { IFleetVessel, IFleetVesselResponse } from '../models/fleet-map.model';

/**
 * API service for Fleet module
 * @export
 * @class FleetApiService
 */
@Injectable({
  providedIn: 'root'
})
export class FleetApiService {

  constructor(private commonApiService: CommonApiService) { }

  /**
   * function to get all vessel details
   *
   * @return {*}  {Observable<any>}
   * @memberof FleetApiService
   */
  getVesselDaetails(): Observable<IFleetVesselResponse> {
    return this.commonApiService.get<IFleetVesselResponse>('loadble-study/shore-detail');
  }
}

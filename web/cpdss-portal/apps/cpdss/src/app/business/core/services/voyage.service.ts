import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { IVoyageResponse, Voyage } from '../models/common.models';

@Injectable()
export class VoyageService {
  private _voyages: Voyage[];

  constructor(private commonApiService: CommonApiService) { }

  /**
   * Vessel details api result mock
   */
  getVoyagesByVesselId(vesselId: number): Observable<Voyage[]> {
    return this.commonApiService.get<IVoyageResponse>(`vessels/${vesselId}/voyages`).pipe(map((response) => {
      this._voyages = response.voyages;
      return this._voyages;
    }));

  }
}

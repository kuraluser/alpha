import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { environment } from '../../../../environments/environment';
import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { IVesselsResponse, IVessel } from '../../core/models/vessel-details.model';

/**
 * Service for vessels api
 */
@Injectable({
  providedIn: 'root'
})

export class VesselsApiService {
  private _vesselDetails: IVessel[];

  constructor(private commonApiService: CommonApiService) { }

  /**
   * Vessel details api result mock
   */
  getVesselsInfo(allVessel = false): Observable<IVessel[]> {
    if (this._vesselDetails && this._vesselDetails[0]?.id === Number(localStorage.getItem("vesselId"))) {
      return of(this._vesselDetails);
    } else {
      return this.commonApiService.get<IVesselsResponse>('vessels').pipe(map((response) => {
        if (allVessel) {
          this._vesselDetails = response.vessels;
        } else {
          if (environment.name === 'shore') {
            this._vesselDetails = response.vessels.length ? response.vessels.filter(vessel => (vessel.id === Number(localStorage.getItem("vesselId")))) : <IVessel[]>[];
          } else {
            this._vesselDetails = response.vessels;
          }
        }
        return this._vesselDetails;
      }));
    }

  }
}

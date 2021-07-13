import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { IToppingoffUllageRequest, IToppingoffUllageResponse } from './topping-off-tank-table.model';

/**
 * Api Service for topping off tank tab;e
 *
 * @export
 * @class LoadingInformationApiService
 */
@Injectable()
export class ToppingOffTankTableApiService {

  constructor(private commonApiService: CommonApiService) { }

    /**
* 
* Api for update ullage
*/
updateUllage(vesselId: number, voyageId: number, portRotationId: number, ullageData: IToppingoffUllageRequest): Observable<IToppingoffUllageResponse> {
  return this.commonApiService.post<IToppingoffUllageRequest, IToppingoffUllageResponse>(`vessels/${vesselId}/voyages/${voyageId}/loading-info/0/update-ullage/${portRotationId}`, ullageData);
}
}

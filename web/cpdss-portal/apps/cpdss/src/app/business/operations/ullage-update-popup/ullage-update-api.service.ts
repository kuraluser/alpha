import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CommonApiService } from '../../../shared/services/common/common-api.service';

/**
 * Api Service for ullage update popup
 *
 * @export
 * @class UllageUpdateApiService
 */
@Injectable()
export class UllageUpdateApiService {

  constructor(
    private commonApiService: CommonApiService
  ) { }

  /**
   * Method for getting ullage details
   *
   * @param {number} vesselId
   * @param {number} patternId
   * @param {number} portRotationId
   * @memberof VoyageStatusTransformationService
   */
  getUllageDetails(vesselId:number, patternId: number, portRotationId:  number) {
    return this.commonApiService.get<any>(`vessels/${vesselId}/pattern/${patternId}/port/${portRotationId}/update-ullage`);
  }
}

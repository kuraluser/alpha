import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { IUllageUpdateDetails, IUllageSaveDetails, IUllageQuantityRequest, IUllageQuantityResponse } from './../models/loading-discharging.model';
import { IResponse } from '../../../shared/models/common.model';

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
   * @param {string} status
   * @memberof UllageUpdateApiService
   */
  getUllageDetails(vesselId: number, patternId: number, portRotationId: number, status: string) {
    return this.commonApiService.get<IUllageUpdateDetails>(`vessels/${vesselId}/pattern/${patternId}/port/${portRotationId}/update-ullage/${status}`);
  }

  /**
   * Method for getting ullage quantity
   * @param {IUllageQuantityRequest}
   * @returns {IUllageQuantityResponse}
   * @memberof UllageUpdateApiService
   */
  getUllageQuantity(data, patternId) {
    return this.commonApiService.post<IUllageQuantityRequest, IUllageQuantityResponse>(`vessels/getUllageDetailsAlgo/${patternId}`, data);
  }

  /**
   * Method for save ullage data
   * @param {IUllageSaveDetails}
   * @returns {IResponse}
   * @memberof UllageUpdateApiService
   */
  updateUllage(data: IUllageSaveDetails) {
    return this.commonApiService.post<IUllageSaveDetails, IResponse>(`loading/ullage-bill-update`, data);
  }
}

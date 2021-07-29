import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { IInstructionTabDetails } from './../models/loading-instruction.model';

/**
 * Api Service for loading instruction module
 *
 * @export
 * @class LoadingInstructionApiService
 */
@Injectable()
export class LoadingInstructionApiService {

  constructor(
    private commonApiService: CommonApiService
  ) { }
  /**
   * Get instruction list
    * @param {number} vesselId
     * @param {number} voyageId
      * @param {number} portRotationId
   */
  getLoadingInstructionData(vesselId: number, loadingInfoId: number, portRotationId: number): Observable<IInstructionTabDetails> {
    return this.commonApiService.get<IInstructionTabDetails>(`vessels/${vesselId}/loading-info/${loadingInfoId}/port-rotation/${portRotationId}`);
  }
}

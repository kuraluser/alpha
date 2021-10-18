import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CommonApiService } from '../../../../shared/services/common/common-api.service';
import { IInstructionDetails, IDeleteData, ISaveStatusData } from './../models/instruction-check-list.model';
import { IResponse } from "../../../../shared/models/common.model";
import { OPERATIONS } from '../../../core/models/common.model';

/**
 * Api Service for loading instruction check list
 *
 * @export
 * @class InstructionCheckListApiService
 */
@Injectable()
export class InstructionCheckListApiService {

  constructor(
    private commonApiService: CommonApiService
  ) { }

  /**
   * Get instruction list
    * @param {number} vesselId
    * @param {number} voyageId
    * @param {number} portRotationId
    * @param {IInstructionDetails} data
   */
  saveInstruction(vesselId: number, loadingDischargingInfoId: number, portRotationId: number, data: IInstructionDetails, operation: OPERATIONS): Observable<IResponse> {
    return this.commonApiService.post<IInstructionDetails, IResponse>(`new-instruction/vessels/${vesselId}/${operation === OPERATIONS.LOADING ? 'loading-info' : 'discharging-info'}/${loadingDischargingInfoId}/port-rotation/${portRotationId}`, data);
  }

  /**
   * Delete instruction
    * @param {number} vesselId
    * @param {number} voyageId
    * @param {number} portRotationId
    * @param {IDeleteData} data
   */
  deleteInstruction(vesselId: number, loadingDischargingInfoId: number, portRotationId: number, data: IDeleteData, operation: OPERATIONS): Observable<IResponse> {
    return this.commonApiService.post<IDeleteData, IResponse>(`delete-instruction/vessels/${vesselId}/${operation === OPERATIONS.LOADING ? 'loading-info' : 'discharging-info'}/${loadingDischargingInfoId}/port-rotation/${portRotationId}`, data);
  }

  /**
   * update check list status
    * @param {number} vesselId
    * @param {number} voyageId
    * @param {number} portRotationId
    * @param {ISaveStatusData} data
   */
  updateCheckListStatus(vesselId: number, loadingDischargingInfoId: number, portRotationId: number, data: ISaveStatusData, operation: OPERATIONS): Observable<IResponse> {
    return this.commonApiService.post<ISaveStatusData, IResponse>(`update-instruction/vessels/${vesselId}/${operation === OPERATIONS.LOADING ? 'loading-info' : 'discharging-info'}/${loadingDischargingInfoId}/port-rotation/${portRotationId}`, data);
  }

  /**
   * inline update instruction
    * @param {number} vesselId
    * @param {number} voyageId
    * @param {number} portRotationId
    * @param {IInstructionDetails} data
   */
  updateInstruction(vesselId: number, loadingDischargingInfoId: number, portRotationId: number, data: IInstructionDetails, operation: OPERATIONS): Observable<IResponse> {
    return this.commonApiService.post<IInstructionDetails, IResponse>(`edit-instruction/vessels/${vesselId}/${operation === OPERATIONS.LOADING ? 'loading-info' : 'discharging-info'}/${loadingDischargingInfoId}/port-rotation/${portRotationId}`, data);
  }
}

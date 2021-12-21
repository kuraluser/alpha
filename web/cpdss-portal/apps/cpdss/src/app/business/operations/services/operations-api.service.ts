import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { CommonApiService } from '../../../shared/services/common/common-api.service';

import { IAlgoResponse, ICargoResponseModel, OPERATIONS } from '../../core/models/common.model';
import { IGenerateDischargePlanResponse } from '../models/loading-discharging.model';

/**
 * Service class for operations module
 *
 * @export
 * @class OperationsApiService
 */
@Injectable()
export class OperationsApiService {

  constructor(private commonApiService: CommonApiService) { }

  /**
   * Method for fetching all cargos from db
   *
   * @return {*}  {Observable<ICargoResponseModel>}
   * @memberof OperationsApiService
   */
  getCargos(): Observable<ICargoResponseModel> {
    return this.commonApiService.get<ICargoResponseModel>(`cargos`);
  }


  /**
   * Post api call for generate loading plan
   *
   * @param {number} vesselId
   * @param {number} voyageId
   * @param {number} loadingInfoId
   * @return {*}
   * @memberof OperationsApiService
   */
  generateLoadingPlan(vesselId: number, voyageId: number, loadingInfoId: number) {
    return this.commonApiService.post<any, any>(`vessels/${vesselId}/voyages/${voyageId}/loading-info/${loadingInfoId}/generate-loading-plan`, null);

  }


  /**
   * Get api call for algo errors.
   *
   * @param {number} vesselId
   * @param {number} voyageId
   * @param {number} dischargeStudyId
   * @return {*}  {Observable<IAlgoResponse>}
   * @memberof OperationsApiService
   */
  getAlgoErrorDetails(vesselId: number, voyageId: number, loadingInfoId: number, type = 0): Observable<IAlgoResponse> {
    return this.commonApiService.get<IAlgoResponse>(`vessels/${vesselId}/voyages/${voyageId}/loading-info/${loadingInfoId}/algo-errors/${type}`);
  }


  /**
   * API to genarate discharging plan
   *
   * @param {number} vesselId
   * @param {number} voyageId
   * @param {number} infoId
   * @return {*}  {Observable<any>}
   * @memberof OperationsApiService
   */
  generateDischargePlan(vesselId: number, voyageId: number, infoId: number): Observable<IGenerateDischargePlanResponse> {
    return this.commonApiService.post<any, IGenerateDischargePlanResponse>(`vessels/${vesselId}/voyages/${voyageId}/discharging-info/${infoId}/generate-discharging-plan`, null);
  }


  /**
   * API to get Algo error details
   *
   * @param {number} vesselId
   * @param {number} voyageId
   * @param {number} infoId
   * @param {number} conditionType
   * @return {*}  {Observable<IAlgoResponse>}
   * @memberof OperationsApiService
   */
  getDischargePlanAlgoError(vesselId: number, voyageId: number, infoId: number, conditionType: number): Observable<IAlgoResponse> {
    return this.commonApiService.get<IAlgoResponse>(`vessels/${vesselId}/voyages/${voyageId}/discharge-info/${infoId}/algo-errors/${conditionType}`);
  }

  /**
 * Method to get loading plan template.
 *
 * @param {number} vesselId
 * @param {number} voyageId
 * @param {number} infoId
 * @param {number} portRotationId
 * @param {*} data
 * @return {*}  {Observable<any>}
 * @memberof OperationsApiService
 */
  downloadPlanTemplate(vesselId: number, voyageId: number, infoId: number, portRotationId: number, operation: OPERATIONS, data): Observable<any> {
    return this.commonApiService.postFile<any, any>(`vessels/${vesselId}/voyages/${voyageId}/${operation === OPERATIONS.DISCHARGING ? 'discharge' : 'loading'}-info/${infoId}/port-rotation/${portRotationId}/report`, data, { responseType: 'blob' as 'json' });

  }

}

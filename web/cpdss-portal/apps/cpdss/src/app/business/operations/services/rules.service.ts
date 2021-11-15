import { Injectable } from '@angular/core';
import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { BehaviorSubject, Subject } from 'rxjs';
import { IDischargingInformation, ILoadingInformationResponse } from '../models/loading-discharging.model';
import { OPERATIONS } from '../../core/models/common.model';

/**
 * Service class for rules
 *
 * @export
 * @class RulesApiService
 */
 @Injectable()
export class RulesService {

  save = new Subject();
  loadingDischargingInfo = new BehaviorSubject<IDischargingInformation | ILoadingInformationResponse>(null);
  readonly OPERATIONS = OPERATIONS;
  constructor(
    private commonApiService: CommonApiService
  ) { }

  /**
   * Method to get rules.
   *
   * @param {*} vesselId
   * @param {*} loadingInfoId
   * @return {*}
   * @memberof RulesApiService
   */

  getRules(vesselId,voyageId,infoId: number,operation: OPERATIONS) {
    if(operation === OPERATIONS.LOADING) {
      return this.commonApiService.get<any>(`vessels/${vesselId}/voyages/${voyageId}/loading-info/${infoId}/rules`);
    } else {
      return this.commonApiService.get<any>(`vessels/${vesselId}/voyages/${voyageId}/discharging-info/${infoId}/rules`);
    }
  }

  /**
   * Method to post data.
   *
   * @param {*} postData
   * @return {*}
   * @memberof RulesService
   */
   postRules(postData: any, vesselId: number, voyageId: number,infoId:number,operation: OPERATIONS) {
    if(operation === OPERATIONS.LOADING) {
      return this.commonApiService.post<any, any>(`vessels/${vesselId}/voyages/${voyageId}/loading-info/${infoId}/rules`, postData);
    } else {
      return this.commonApiService.post<any, any>(`vessels/${vesselId}/voyages/${voyageId}/discharging-info/${infoId}/rules`, postData);
    }
  }

}

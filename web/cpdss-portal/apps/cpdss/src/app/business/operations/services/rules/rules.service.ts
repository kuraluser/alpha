import { Injectable } from '@angular/core';
import { CommonApiService } from 'apps/cpdss/src/app/shared/services/common/common-api.service';
import { BehaviorSubject, Subject } from 'rxjs';
import { IVessel } from '../../../core/models/vessel-details.model';
import { VesselsApiService } from '../../../core/services/vessels-api.service';
import { OperationsModule } from '../../operations.module';
import { OPERATIONS } from '../../../core/models/common.model';

/**
 * Service class for rules
 *
 * @export
 * @class RulesApiService
 */
 @Injectable()
export class RulesService {

  vessels: IVessel[];
  save = new Subject();
  infoId = new BehaviorSubject(null);
  readonly OPERATIONS = OPERATIONS;
  constructor(
    private vesselsApiService: VesselsApiService, private commonApiService: CommonApiService
  ) { }

  /**
  * Method to initialize variables in the service
  */
  async init() {
    this.vessels = await this.vesselsApiService.getVesselsInfo(true).toPromise();
  }

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

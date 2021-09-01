import { Injectable } from '@angular/core';
import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { BehaviorSubject, Subject } from 'rxjs';
import { IVessel } from '../models/vessel-details.model';
import { VesselsApiService } from '../services/vessels-api.service';

/**
 * Service Class for Rules
 *
 * @export
 * @class RulesService
 */
@Injectable({
  providedIn: 'root'
})
export class RulesService {

  vessels: IVessel[];
  save = new Subject();
  selectedTab$ = new BehaviorSubject(null);

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
   * @param {*} ruleTypeId
   * @return {*} 
   * @memberof RulesService
   */
  getRules(vesselId: any, ruleTypeId: any) {
    return this.commonApiService.get<any>(`vessel-rule/vessels/${vesselId}/ruleMasterSectionId/${ruleTypeId}`);
  }


  /**
   * Method to post data.
   *
   * @param {*} postData
   * @return {*} 
   * @memberof RulesService
   */
  postRules(postData: any, vesselId: number, ruleTypeId: number) {
    return this.commonApiService.post<any, any>(`vessel-rule/vessels/${vesselId}/ruleMasterSectionId/${ruleTypeId}`, postData);
  }
}

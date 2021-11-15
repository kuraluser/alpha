import { Injectable } from '@angular/core';
import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { BehaviorSubject, Subject } from 'rxjs';
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
  save = new Subject();
  selectedTab$ = new BehaviorSubject(null);

  constructor(
    private commonApiService: CommonApiService
  ) { }


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

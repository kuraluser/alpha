import { Injectable } from '@angular/core';
import { Subject, BehaviorSubject } from 'rxjs';
import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { IVessel } from '../../core/models/vessel-details.model';
import { VesselsApiService } from '../../core/services/vessels-api.service';

/**
 * Service class for rules.
 *
 * @export
 * @class RuleService
 */
@Injectable({
  providedIn: 'root'
})

export class RuleService {

  vessels: IVessel[];
  save = new Subject();
  selectedTab$ = new BehaviorSubject(null);

  constructor(
    private vesselsApiService: VesselsApiService,private commonApiService: CommonApiService
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
 getRules(vesselId:any,ruleTypeId:number,selectedLoadableStudyId:number)
  {
    return  this.commonApiService.get<any>(`loadble-study-rule/vessels/${vesselId}/ruleMasterSectionId/${ruleTypeId}/loadableStudyId/${selectedLoadableStudyId}`);
  }
  
  
  /**
   * Method to post data.
   *
   * @param {*} postData
   * @return {*} 
   * @memberof RulesService
   */
  postRules(postData:any,vesselId:number,ruleTypeId:number,selectedLoadableStudyId)
  { 
   return this.commonApiService.post<any,any>(`loadble-study-rule/vessels/${vesselId}/ruleMasterSectionId/${ruleTypeId}/loadableStudyId/${selectedLoadableStudyId}`,postData);
  }
}


import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { AdminModule } from '../admin.module';
import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { ICrewMasterList, ICrewMasterListResponse, ICrewMasterListStateChange, ICrewrankListResponse, ICrewsDetailsResponse, IPostCrewDetails } from './../models/crew.model';
import { IVesselsResponse } from './../../core/models/vessel-details.model';

/**
 * Api service for crew master
 * Provided under Admin module
 * @export
 * @class CrewMasterApiService
 */
@Injectable({
  providedIn: AdminModule
})
export class CrewMasterApiService {
  private _crews: ICrewMasterList;
  private _page = 0;
  private _pageSize = 10;

  constructor(private commonApiService: CommonApiService) { }

  /**
   * API to get crew master list
   * @param {ICrewMasterListStateChange} stateParams
   * @return {*}  {Observable<ICrewMasterListResponse>}
   * @memberof CrewMasterApiService
   */
   getCrewList(stateParams: ICrewMasterListStateChange): Observable<ICrewMasterListResponse> {
    const filterString = stateParams?.filter ? Object.keys(stateParams?.filter).map(function (key) {
      if (stateParams?.filter[key]) {
        return "&" + key + "=" + stateParams?.filter[key];
      }
    }).toString() : '';
    const filterParams = filterString.replace(/\,/g, '');
    const requestParams = `pageSize=${stateParams.pageSize ? stateParams.pageSize : this._pageSize}&page=${stateParams.page ? stateParams.page : this._page}${stateParams.sortBy ? `&sortBy=${stateParams.sortBy}` : ''}${stateParams.orderBy ? `&orderBy=${stateParams.orderBy}` : ''}${filterParams}`;
    return this.commonApiService.get<ICrewMasterListResponse>(`master/crewlisting?${requestParams}`);
  }

  /**
   * API to list all the Crew Ranks
   * @returns ICrewrankListResponse
   */
  getRanks(){
    return this.commonApiService.get<ICrewrankListResponse>(`crewranks`);
  }

  /**
   * API to list all the vessel
   * @returns ICrewrankListResponse
   */
   getAllvessels(){
    return this.commonApiService.get<IVesselsResponse>('vessels')
  }

  /**
   * To save crew details
   * @param crewData
   * @param crewId
   * @returns
   */
  saveCrew(crewData: IPostCrewDetails, crewId: number){
    return this.commonApiService.post<IPostCrewDetails, ICrewsDetailsResponse>(`/master/crew/${crewId}`, crewData);
  }

}

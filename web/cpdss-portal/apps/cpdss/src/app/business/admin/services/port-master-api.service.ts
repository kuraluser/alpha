import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';

import { AdminModule } from '../admin.module';
import { CommonApiService } from '../../../shared/services/common/common-api.service';

import { IPort, IPortsResponse } from '../../core/models/common.model';
import { IPortMasterListResponse, IPortMasterListStateChange, IPortsDetailsResponse } from '../models/port.model';

/**
 * Api service for port master
 * @export
 * @class PortMasterApiService
 */
@Injectable({
  providedIn: AdminModule,
})
export class PortMasterApiService {

  selectedPortLocation: any;
  private _ports: IPort[];
  private _page = 0;
  private _pageSize = 10;

  constructor(private commonApiService: CommonApiService) { }

  /**
   * Method to get ports list
   *
   * @return {*}  {Observable<IPort[]>}
   * @memberof PortMasterApiService
   */
  getPorts(): Observable<IPort[]> {
    if (this._ports) {
      return of(this._ports);
    } else {
      return this.commonApiService.get<IPortsResponse>('ports').pipe(
        map((response) => {
          this._ports = response?.ports;
          return this._ports;
        })
      );
    }
  }

  /**
   * Method to get country list
   * @return {*}
   * @memberof PortMasterApiService
   */
  async getCountryList() {
    return await [
      { name: 'India' }, //TODO-has to be replaced with actual api call later
      { name: 'Australia' },
    ];
  }

  /**
   * API to get port master list
   * @param {IPortMasterListStateChange} stateParams
   * @return {*}  {Observable<IPortMasterListResponse>}
   * @memberof PortMasterApiService
   */
  getPortsList(stateParams: IPortMasterListStateChange): Observable<IPortMasterListResponse> {
    const filterString = stateParams?.filter ? Object.keys(stateParams?.filter).map(function (key) {
      if (stateParams?.filter[key]) {
        return "&" + key + "=" + stateParams?.filter[key];
      }
    }) : '';
    const requestParams = `pageSize=${stateParams.pageSize ? stateParams.pageSize : this._pageSize}&page=${stateParams.page ? stateParams.page : this._page}${stateParams.sortBy ? `&sortBy=${stateParams.sortBy}` : ''}${stateParams.orderBy ? `&orderBy=${stateParams.orderBy}` : ''}${filterString}`;
    return this.commonApiService.get<IPortMasterListResponse>(`master/ports?${requestParams}`);
  }

  /**
   * Method to get port details.
   *
   * @memberof PortMasterApiService
   */
  getPortDetailsById(portId: number) {
    return this.commonApiService.get<IPortsDetailsResponse>(`portInfo/${portId}`);
  }
}

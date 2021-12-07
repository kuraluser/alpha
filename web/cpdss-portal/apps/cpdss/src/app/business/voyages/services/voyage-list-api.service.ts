import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IDataStateChange } from '../../../shared/components/datatable/datatable.model';
import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { IVoyageListResponse, IVoyageStatusResponse } from '../models/voyage-list.model';

/**
 * Api Service for voyage module
 *
 * @export
 * @class VoyageListApiService
 */
@Injectable()
export class VoyageListApiService {
  public page = 0;
  public pageSize = 10;
  constructor(private commonApiService: CommonApiService) { }


  /**
   *
   * @param {number} vesselId
   * @param {IDataStateChange} options
   * @param {string} fromStartDate
   * @param {string} toStartDate
   * Get api for voyage list
   */
  getVoyageList(vesselId: number, options: IDataStateChange, fromStartDate?: string, toStartDate?: string): Observable<IVoyageListResponse> {
    const filterString = options?.filter ? Object.keys(options?.filter).map(function (key) {
      if(options?.filter[key]){
        return "" + key + "=" + options?.filter[key]; // line break for wrapping only
      }
    }).join("&") : '';
    const params = `${fromStartDate && toStartDate ? `fromStartDate=${fromStartDate}&toStartDate=${toStartDate}` : ''}&pageSize=${options.pageSize ? options.pageSize : this.pageSize}&pageNo=${options.page ? options.page : this.page}${options.sortBy ? `&sortBy=${options.sortBy}` : ''}${options.orderBy ? `&orderBy=${options.orderBy}` : ''}${filterString ? '&'+filterString : ''}`
    return this.commonApiService.get<IVoyageListResponse>(`vessels/${vesselId}/voyagelist?${params}`);
  }

  /**
  *
  * @param {number} vesselId
  * @param {number} voyageId
  * @param {string} startDate
  * start voyage api
  */
  startVoyage(vesselId: number, voyageId: number, startDate: string): Observable<IVoyageStatusResponse> {
    const body = {
      status: "START",
      actualStartDate: startDate ? startDate : ''
    }
    return this.commonApiService.post<any, IVoyageStatusResponse>(`vessels/${vesselId}/voyages/${voyageId}`, body);
  }

  /**
  *
  * @param {number} vesselId
  * @param {number} voyageId
  * @param {string} endDate
  * stop voyage api
  */
  endVoyage(vesselId: number, voyageId: number, endDate: string): Observable<IVoyageStatusResponse> {
    const body = {
      status: "STOP",
      actualEndDate: endDate ? endDate : ''
    }
    return this.commonApiService.post<any, IVoyageStatusResponse>(`vessels/${vesselId}/voyages/${voyageId}`, body);
  }
}

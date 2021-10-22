import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';

import { AdminModule } from '../admin.module';
import { CommonApiService } from '../../../shared/services/common/common-api.service';

import { IVesselDetailsResponse, IVesselInfoDataStateChange, IVesselListResponse } from '../models/vessel-info.model';

/**
 * Service for Vessel information module
 *
 * @export
 * @class VesselInformationApiService
 */
@Injectable({
  providedIn: AdminModule
})
export class VesselInformationApiService {

  public pageNo: number = 0;
  public pageSize: number = 10;

  constructor(
    private commonApiService: CommonApiService
  ) { }

  /**
   * API to get Vessel list
   *
   * @param {IVesselInfoDataStateChange} params
   * @return {*}  {Observable<IVesselListResponse>}
   * @memberof VesselInformationApiService
   */
  getVesselList(params: IVesselInfoDataStateChange): Observable<IVesselListResponse> {
    const requestParams = `pageNo=${params.pageNo ? params.pageNo : this.pageNo}&pageSize=${params.pageSize ? params.pageSize : this.pageSize}${params.orderBy ? '&orderBy=' + params.orderBy : ''}${params.sortBy ? '&sortBy=' + params.sortBy : ''}${params.name ? '&name=' + params.name : ''}${params.typeOfShip ? '&typeOfShip=' + params.typeOfShip : ''}${params.builder ? '&builder=' + params.builder : ''}${params.dateOfLaunching ? '&dateOfLaunching=' + params.dateOfLaunching : ''}`;
    return this.commonApiService.get<IVesselListResponse>(`all-vessels-info?${requestParams}`);
  }

  /**
   * API to get particular Vessel's details
   *
   * @param {number} vesselId
   * @return {*}  {Observable<IVesselDetailsResponse>}
   * @memberof VesselInformationApiService
   */
  getVesselDetails(vesselId: number): Observable<IVesselDetailsResponse> {
    return this.commonApiService.get<IVesselDetailsResponse>(`vessel-information/${vesselId}`);
  }

}

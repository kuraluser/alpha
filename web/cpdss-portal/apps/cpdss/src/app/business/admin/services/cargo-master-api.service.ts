import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { IDataStateChange } from '../../../shared/components/datatable/datatable.model';
import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { IPort, IPortsResponse } from '../../core/models/common.model';
import { AdminModule } from '../admin.module';
import { IAPITempHistoryRequest, ICargoAPITempHistoryResponse, ICargosResponse, ICargoResponse, ICargoDetails, ISaveCargoResponse, IDeleteCargoResponse } from '../models/cargo.model';

/**
 * Service for handling api calls in cargo master
 *
 * @export
 * @class CargoMasterApiService
 */
@Injectable({
  providedIn: AdminModule
})
export class CargoMasterApiService {
  private _ports: IPort[];
  private _page = 0;
  private _pageSize = 10;

  constructor(private commonApiService: CommonApiService) { }

  /**
   * Get cargos api
   *
   * @param {IDataStateChange} options
   * @return {*}  {Observable<ICargosResponse>}
   * @memberof CargoMasterApiService
   */
  getCargos(options: IDataStateChange): Observable<ICargosResponse> {
    const filterString = options?.filter ? Object.keys(options?.filter).map(function (key) {
      if (options?.filter[key]) {
        return "&" + key + "=" + options?.filter[key];
      }
    }) : '';
    const params = `pageSize=${options.pageSize ? options.pageSize : this._pageSize}&pageNo=${options.page ? options.page : this._page}${options.sortBy ? `&sortBy=${options.sortBy}` : ''}${options.orderBy ? `&orderBy=${options.orderBy}` : ''}${filterString}`;
    return this.commonApiService.get<ICargosResponse>(`master/cargos?${params}`);
  }

  /**
   * Get cargo details api
   *
   * @param {number} cargoId
   * @return {*}  {Observable<ICargoResponse>}
   * @memberof CargoMasterApiService
   */
  getCargo(cargoId: number): Observable<ICargoResponse> {
    return this.commonApiService.get<ICargoResponse>(`master/cargos/${cargoId}`);
  }

  /**
     * Method to get all ports in port master
     *
     * @returns {Observable<IPort[]>}
     * @memberof LoadableStudyDetailsApiService
     */
  getPorts(): Observable<IPort[]> {
    if (this._ports) {
      return of(this._ports);
    } else {
      return this.commonApiService.get<IPortsResponse>('ports').pipe(map((response) => {
        this._ports = response?.ports;
        return this._ports;
      }));
    }
  }

  /**
   * Post api for saving cargo
   *
   * @param {ICargoDetails} cargo
   * @param {number} cargoId
   * @return {*}  {Observable<ISaveCargoResponse>}
   * @memberof CargoMasterApiService
   */
  saveCargo(cargo: ICargoDetails, cargoId: number): Observable<ISaveCargoResponse> {
    return this.commonApiService.post<ICargoDetails, ISaveCargoResponse>(`master/cargos/${cargoId}`, cargo);
  }

  /**
   * Delete api for deleting cargo
   *
   * @param {number} cargoId
   * @return {*}  {Observable<IDeleteCargoResponse>}
   * @memberof CargoMasterApiService
   */
  deleteCargo(cargoId: number): Observable<IDeleteCargoResponse> {
    return this.commonApiService.delete<IDeleteCargoResponse>(`master/cargos/${cargoId}`)
  }

  /**
   * Fetching cargo history
   *
   * @param {IAPITempHistoryRequest} cargoAPITempHistoryParams
   * @return {*}  {Observable<ICargoAPITempHistoryResponse>}
   * @memberof CargoMasterApiService
   */
  getCargoApiTemperatureHistoryDetails(cargoAPITempHistoryParams: IAPITempHistoryRequest): Observable<ICargoAPITempHistoryResponse> {
    return of({
      "responseStatus": {
        "status": "200"
      },
      "portHistory": [
        {
          "loadingPortId": 359,
          "loadedDate": "13-07-2021 10:10",
          "loadedMonth": 7,
          "api": 29.2000,
          "temperature": 94.9000
        },
        {
          "loadingPortId": 359,
          "loadedDate": "13-07-2021 10:10",
          "loadedMonth": 7,
          "api": 29.2000,
          "temperature": 94.9000
        },
        {
          "loadingPortId": 359,
          "loadedDate": "01-07-2021 06:18",
          "loadedMonth": 7,
          "api": 29.2000,
          "temperature": 94.9000
        },
        {
          "loadingPortId": 359,
          "loadedDate": "01-07-2021 06:18",
          "loadedMonth": 7,
          "api": 29.2000,
          "temperature": 94.9000
        },
        {
          "loadingPortId": 359,
          "loadedDate": "30-06-2021 05:21",
          "loadedMonth": 6,
          "api": 29.2000,
          "temperature": 94.9000
        }
      ],
      "monthlyHistory": [
        {
          "loadingPortId": 359,
          "loadedYear": 2021,
          "loadedMonth": 5,
          "loadedDay": 26,
          "api": 29.2000,
          "temperature": 94.9000
        },
        {
          "loadingPortId": 359,
          "loadedYear": 2021,
          "loadedMonth": 6,
          "loadedDay": 30,
          "api": 29.2000,
          "temperature": 94.9000
        },
        {
          "loadingPortId": 359,
          "loadedYear": 2021,
          "loadedMonth": 7,
          "loadedDay": 13,
          "api": 29.2000,
          "temperature": 94.9000
        },
        {
          "loadingPortId": 359,
          "loadedYear": 2020,
          "loadedMonth": 4,
          "loadedDay": 8,
          "api": 29.2000,
          "temperature": 94.9000
        }
      ],
      "totalElements": 0
    })
  }
}

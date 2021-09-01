import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { IPort, IPortsResponse } from '../../core/models/common.model';
import { AdminModule } from '../admin.module';
import { IAPITempHistoryRequest, ICargoAPITempHistoryResponse } from '../models/cargo.model';

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

  constructor(private commonApiService: CommonApiService) { }

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

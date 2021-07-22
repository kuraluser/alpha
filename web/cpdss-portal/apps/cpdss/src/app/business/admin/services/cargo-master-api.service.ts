import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { IPort, IPortsResponse } from '../../core/models/common.model';
import { AdminModule } from '../admin.module';

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
}

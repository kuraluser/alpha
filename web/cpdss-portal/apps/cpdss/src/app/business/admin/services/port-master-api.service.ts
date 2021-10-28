import { Injectable } from '@angular/core';
import { AdminModule } from '../admin.module';
import { Observable, of} from 'rxjs';
import { map } from 'rxjs/operators';
import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { IPort, IPortsResponse } from '../../core/models/common.model';
import { IPortsDetailsResponse } from '../models/port.model';



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

  constructor(private commonApiService: CommonApiService) {}

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
   * Method to get port details.
   *
   * @memberof PortMasterApiService
   */
  getPortDetailsById(portId: number) {
    return this.commonApiService.get<IPortsDetailsResponse>(`portInfo/${portId}`);
  }
}

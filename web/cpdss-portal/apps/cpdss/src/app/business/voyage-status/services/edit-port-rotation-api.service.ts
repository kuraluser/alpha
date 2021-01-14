import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { IPort, IPortsDetailsResponse, IPortsResponse } from '../../../shared/models/common.model';
import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { IEditPortRotationModel, IPortResponseModel } from '../models/edit-port-rotation.model';

/**
 * Service for edit port rotation
 */
@Injectable()
export class EditPortRotationApiService {

  constructor(private commonApiService: CommonApiService) { }
  /**
   * Save edit port rotation popup 
   * @param vesselId
   * @param voyageId
   * @param loadableStudyId
   * @param ports
   */
  saveEditPortRotation(vesselId: number, voyageId: number, loadableStudyId: number, ports: IEditPortRotationModel): Observable<IPortResponseModel> {
    return this.commonApiService.post<IEditPortRotationModel, IPortResponseModel>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies/${loadableStudyId}/ports`, ports);
  }

  /**
 * Method to get all ports in port master
 *
 * @returns {Observable<IPort[]>}
 * @memberof EditPortRotationApiService
 */
  getPorts(): Observable<IPort[]> {
    return this.commonApiService.get<IPortsResponse>('ports').pipe(map((response) => {
      return response?.ports;
    }));
  }
  /**
* Method to get all port details
*
* @returns {Observable<IPortsDetailsResponse>}
* @memberof EditPortRotationApiService
*/
  getPortsDetails(vesselId: number, voyageId: number, loadableStudyId: number): Observable<IPortsDetailsResponse> {
    return this.commonApiService.get<IPortsDetailsResponse>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies/${loadableStudyId}/ports`);
  }
}
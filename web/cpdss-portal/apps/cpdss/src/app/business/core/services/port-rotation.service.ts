import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { map } from 'rxjs/operators';
import { IEditPortRotation, IEditPortRotationModel, IPort, IPortList, IPortResponseModel, IPortsDetailsResponse, IPortsResponse } from '../../core/models/common.model';
import { CommonApiService } from '../../../shared/services/common/common-api.service';

/**
 * Service for edit port rotation
 */
@Injectable()
export class PortRotationService {

  public portOrderChange = new Subject();
  private _voyageDistance = new BehaviorSubject<number>(0);

  voyageDistance$ = this._voyageDistance.asObservable();

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
 * @memberof PortRotationService
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
* @memberof PortRotationService
*/
  getPortsDetails(vesselId: number, voyageId: number, loadableStudyId: number): Observable<IPortsDetailsResponse> {
    return this.commonApiService.get<IPortsDetailsResponse>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies/${loadableStudyId}/ports`);
  }

  /**
   * Delete Port
   */
  deletePort(port: IPortList, vesselId, voyageId: number, loadableStudyId: number): Observable<IPortResponseModel>{
    return this.commonApiService.delete< IPortResponseModel>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies/${loadableStudyId}/ports/${port.id}`);
  }

  /**
   * Save port rotation
   */
  savePortRotationRibbon(vesselId: number, voyageId: number, loadableStudyId: number, ports: IEditPortRotation): Observable<IPortResponseModel>{
    return this.commonApiService.post<IEditPortRotation, IPortResponseModel>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies/${loadableStudyId}/ports/${ports.id}`, ports);
  }

  /**
   * Update voyage distance
   *
   * @param {number} voyageDistance
   * @memberof PortRotationService
   */
  updateVoyageDistance(voyageDistance: number) {
    this._voyageDistance.next(voyageDistance);
  }
}

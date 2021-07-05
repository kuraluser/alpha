import { Injectable } from '@angular/core';

import { from, Observable, of, Subject } from 'rxjs';
import { map } from 'rxjs/operators';

import { IPort, IDischargeStudyPortList , IDischargePortsDetailsResponse , IPortsResponse , IAlgoResponse } from '../../core/models/common.model';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';
import { CargoNominationDB, ICargoNomination, ICargoPortsResponse, DischargePortsDB , IOHQPortRotationResponse, IPortOHQResponse, IPortOHQTankDetail, DischargeOHQDB , IPortOBQResponse, IPortOBQTankDetail, OBQDB, ICargoNominationValueObject, ILoadOnTop, IGeneratePatternResponse, ICargoApiTempHistoryResponse, IApiTempHistoryRequest } from '../models/cargo-planning.model';

import { ICargoNominationDetailsResponse } from '../models/discharge-study-list.model'
import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { DischargeStudyDetailsModule } from '../discharge-study-details/discharge-study-details.module';

@Injectable()

/**
 * Api Service for Discharge Study Details module
 *
 * @export
 * @class DischargeStudyDetailsApiService
*/

export class DischargeStudyDetailsApiService {
  private _ports: IPort[];
  private _ohqDb: DischargeOHQDB;
  private _portsDb: DischargePortsDB;
  
  constructor(
    private commonApiService: CommonApiService,
  ) { 
    this._portsDb = new DischargePortsDB();
    this._ohqDb = new DischargeOHQDB();
  }

    /**
   * Method to get all ports in port master
   *
   * @returns {Observable<IPort[]>}
   * @memberof DischargeStudyDetailsApiService
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
   * Method to get all port details
   *
   * @returns {Observable<IDischargePortsDetailsResponse>}
   * @memberof DischargeStudyDetailsApiService
   */
    getPortsDetails(vesselId: number, voyageId: number, dischargeStudyId: number): Observable<IDischargePortsDetailsResponse> {
      return this.commonApiService.get<IDischargePortsDetailsResponse>(`vessels/${vesselId}/voyages/${voyageId}/discharge-studies/${dischargeStudyId}/ports`);
    }

    /**
     * Get count of pending updates in ports db
     *
     * @param {number} vesselId
     * @param {number} voyageId
     * @param {number} dischargeStudyId
     * @returns {Promise<number>}
     * @memberof DischargeStudyDetailsApiService
     */
     getPortPendingUpdatesCount(vesselId: number, voyageId: number, dischargeStudyId: number): Promise<number> {
        return this._portsDb.dischargePorts.where({ 'vesselId': vesselId, 'voyageId': voyageId, 'loadableStudyId': dischargeStudyId }).count();
     }

         /**
     * Method to set Ports
     *
     * @returns {Observable<IPortsDetailsResponse>}
     * @memberof DischargeStudyDetailsApiService
     */
    setPort(ports: IDischargeStudyPortList, vesselId: number, voyageId: number, dischargeStudyId: number, isPortsComplete: boolean): Promise<number> {
      ports.vesselId = vesselId;
      ports.voyageId = voyageId;
      ports.dischargeStudyId = dischargeStudyId;
      ports.isPortsComplete = isPortsComplete;
      return this._portsDb.dischargePorts.add(ports);
    }

        /**
     * Method for fetching port rotation for ohq
     *
     * @param {number} vesselId
     * @param {number} voyageId
     * @param {number} dischargeStudyId
     * @returns
     * @memberof DischargeStudyDetailsApiService
     */
      getOHQPortRotation(vesselId: number, voyageId: number, dischargeStudyId: number): Observable<IOHQPortRotationResponse> {
        return this.commonApiService.get<IOHQPortRotationResponse>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies/${dischargeStudyId}/port-rotation`);
      }
  
      /**
       * Method for fetching port specific ohq
       *
       * @param {number} vesselId
       * @param {number} voyageId
       * @param {number} dischargeStudyId
       * @param {number} portId
       * @returns {Observable<IPortOHQResponse>}
       * @memberof DischargeStudyDetailsApiService
       */
      getPortOHQDetails(vesselId: number, voyageId: number, dischargeStudyId: number, portId: number): Observable<IPortOHQResponse> {
          return this.commonApiService.get<IPortOHQResponse>(`vessels/${vesselId}/voyages/${voyageId}/discharge-studies/${dischargeStudyId}/port-rotation/${portId}/on-hand-quantities`);
      }
  
      /**
       * Method to set ohq
       *
       * @param {IPortOHQTankDetail} ohqTankDetails
       * @param {number} vesselId
       * @param {number} voyageId
       * @param {number} dischargeStudyId
       * @returns {Promise<number>}
       * @memberof DischargeStudyDetailsApiService
       */
      setOHQTankDetails(ohqTankDetails: IPortOHQTankDetail, vesselId: number, voyageId: number, dischargeStudyId: number, isPortRotationOhqComplete: boolean): Promise<number> {
          ohqTankDetails.vesselId = vesselId;
          ohqTankDetails.voyageId = voyageId;
          // ohqTankDetails.dischargeStudyId = dischargeStudyId;
          ohqTankDetails.isPortRotationOhqComplete = isPortRotationOhqComplete;
          return this._ohqDb.dischargeOhq.add(ohqTankDetails);
      }
  
      /**
       * Get count of pending updates in ohq db
       *
       * @param {number} vesselId
       * @param {number} voyageId
       * @param {number} dischargeStudyId
       * @returns {Promise<number>}
       * @memberof LoadableStudyDetailsApiService
       */
      getOHQTankDetailsPendingUpdatesCount(vesselId: number, voyageId: number, dischargeStudyId: number): Promise<number> {
          return this._ohqDb.dischargeOhq.where({ 'vesselId': vesselId, 'voyageId': voyageId, 'dischargeStudyId': dischargeStudyId }).count();
      }
      /**
       * Get count of pending updates in ohq db
       *
       * @param {number} vesselId
       * @param {number} voyageId
       * @param {number} dischargeStudyId
       * @returns {Promise<number>}
       * @memberof LoadableStudyDetailsApiService
       */
      getCargoNominationDetails(vesselId: number, voyageId: number, loadableStudyId: number): Observable<ICargoNominationDetailsResponse> {
        return this.commonApiService.get<ICargoNominationDetailsResponse>(`vessels/${vesselId}/voyages/${voyageId}/discharge-studies/${loadableStudyId}/cargo-nomination`);
      }
}

import { Injectable } from '@angular/core';

import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';

import { IPort, IDischargeStudyPortList , IDischargePortsDetailsResponse , IPortsResponse , IInstructionResponse , ITankResponse  } from '../../core/models/common.model';

import { DischargePortsDB , IOHQPortRotationResponse, IDischargeStudyPortOHQResponse, IDischargeStudyPortOHQTankDetail , DischargeOHQDB  } from '../models/cargo-planning.model';
import { ICargoResponseModel } from '../../../shared/models/common.model';

import { ICargoNominationDetailsResponse , IDischargeStudyDetailsResponse , IPortCargoResponse , ICargoHistoryDetails } from '../models/discharge-study-list.model'
import { CommonApiService } from '../../../shared/services/common/common-api.service';


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
       * @returns {Observable<IDischargeStudyPortOHQResponse>}
       * @memberof DischargeStudyDetailsApiService
       */
      getPortOHQDetails(vesselId: number, voyageId: number, dischargeStudyId: number, portId: number): Observable<IDischargeStudyPortOHQResponse> {
          return this.commonApiService.get<IDischargeStudyPortOHQResponse>(`vessels/${vesselId}/voyages/${voyageId}/discharge-studies/${dischargeStudyId}/port-rotation/${portId}/on-hand-quantities`);
      }
  
      /**
       * Method to set ohq
       *
       * @param {IDischargeStudyPortOHQTankDetail} ohqTankDetails
       * @param {number} vesselId
       * @param {number} voyageId
       * @param {number} dischargeStudyId
       * @returns {Promise<number>}
       * @memberof DischargeStudyDetailsApiService
       */
      setOHQTankDetails(ohqTankDetails: IDischargeStudyPortOHQTankDetail, vesselId: number, voyageId: number, dischargeStudyId: number, isPortRotationOhqComplete: boolean): Promise<number> {
          ohqTankDetails.vesselId = vesselId;
          ohqTankDetails.voyageId = voyageId;
          ohqTankDetails.dischargeStudyId = dischargeStudyId;
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
       * @memberof DischargeStudyDetailsApiService
       */
      getOHQTankDetailsPendingUpdatesCount(vesselId: number, voyageId: number, dischargeStudyId: number): Promise<number> {
          return this._ohqDb.dischargeOhq.where({ 'vesselId': vesselId, 'voyageId': voyageId, 'dischargeStudyId': dischargeStudyId }).count();
      }
      /**
       * Get cargonomination details 
       *
       * @param {number} vesselId
       * @param {number} voyageId
       * @param {number} dischargeStudyId
       * @returns {Promise<number>}
       * @memberof DischargeStudyDetailsApiService
       */
      getCargoNominationDetails(vesselId: number, voyageId: number, dischargeStudyId: number): Observable<ICargoNominationDetailsResponse> {
        return this.commonApiService.get<ICargoNominationDetailsResponse>(`vessels/${vesselId}/voyages/${voyageId}/discharge-studies/${dischargeStudyId}/cargo-nomination`);
      }

      /**
       * Get discharge study details
       *
       * @param {number} vesselId
       * @param {number} voyageId
       * @param {number} dischargeStudyId
       * @returns {Promise<number>}
       * @memberof DischargeStudyDetailsApiService
      */
       getDischargeStudyDetails(vesselId: number, voyageId: number, dischargeStudyId: number): Observable<IDischargeStudyDetailsResponse> {
        return this.commonApiService.get<IDischargeStudyDetailsResponse>(`vessels/${vesselId}/voyages/${voyageId}/discharge-studies/${dischargeStudyId}/cargoByPort`);
      }

      /**
       * Get instruction list
       *
       * @returns {Promise<IInstructionResponse>}
       * @memberof DischargeStudyDetailsApiService
      */
       getInstructionDetails(): Observable<IInstructionResponse> {
        return this.commonApiService.get<IInstructionResponse>(`port-instructions`);
      }

       /**
       * Get tank List
       * @param {number} vesselId
       * @returns {Promise<ITankResponse>}
       * @memberof DischargeStudyDetailsApiService
      */
        getTankDetails(vesselId: number): Observable<ITankResponse> {
          return this.commonApiService.get<ITankResponse>(`vessel/${vesselId}/cargo-tanks`);
        }

      /**
       * Get port cargo list
       * @param {number} vesselId
       * @param {number} dischargeStudyId
       * @returns {Promise<IPortCargoResponse>}
       * @memberof DischargeStudyDetailsApiService
      */
      getPortCargoDetails(dischargeStudyId: number): Observable<IPortCargoResponse> {
        return this.commonApiService.get<IPortCargoResponse>(`discharge-studies/${dischargeStudyId}/port-cargos`);
      }

      /**
       * Get cargo details
       * @returns {Promise<ICargoResponse>}
       * @memberof DischargeStudyDetailsApiService
      */
      getCargoDetails(): Observable<ICargoResponseModel> {
        return this.commonApiService.get<ICargoResponseModel>(`cargos`);
      }

      
      /**
       * Get cargo history details
       *
       * @returns {Promise<IInstructionResponse>}
       * @memberof DischargeStudyDetailsApiService
      */
       getCargoHistoryDetails(vesselId: number , portId: number , cargoId: number): Observable<ICargoHistoryDetails> {
        return this.commonApiService.get<ICargoHistoryDetails>(`vessels/${vesselId}/ports/${portId}/cargos/${cargoId}/cargo-history`);
      }

      /**
       * Method to save discharge study
       *
       * @returns {Promise<IInstructionResponse>}
       * @memberof DischargeStudyDetailsApiService
      */
       saveDischargeStudy(dischargeStudy: any): Observable<IOHQPortRotationResponse> {
        return this.commonApiService.get<IOHQPortRotationResponse>(`cargo-history`);
      }
}

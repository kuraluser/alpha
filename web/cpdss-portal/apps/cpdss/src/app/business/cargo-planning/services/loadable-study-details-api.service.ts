import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { IResponse } from '../../../shared/models/common.model';
import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { CargoPlanningModule } from '../cargo-planning.module';
import { CargoNominationDB, ICargoNominationDetailsResponse, ICargoNomination, ICargoPortsResponse, IPortsResponse, IPort, IPortsDetailsResponse, IPortList, PortsDB, IOHQPortRotationResponse, IPortOHQResponse, IPortOHQTankDetail, OHQDB } from '../models/cargo-planning.model';
import { IDischargingPortIds } from '../models/loadable-study-list.model';

/**
 * Api Service for Loadable Study Details module
 *
 * @export
 * @class LoadableStudyDetailsApiService
 */
@Injectable({
  providedIn: CargoPlanningModule
})
export class LoadableStudyDetailsApiService {
  private _ports: IPort[];
  private _cargoNominationDb: CargoNominationDB;
  private _portsDb: PortsDB;
  private _ohqDb: OHQDB;

  constructor(private commonApiService: CommonApiService) {
    this._cargoNominationDb = new CargoNominationDB();
    this._portsDb = new PortsDB();
    this._ohqDb = new OHQDB();
  }

  /**
   * Method to get all cargonomination details
   *
   * @returns {Observable<ICargoNominationDetailsResponse>}
   * @memberof LoadableStudyDetailsApiService
   */
  getCargoNominationDetails(vesselId: number, voyageId: number, loadableStudyId: number): Observable<ICargoNominationDetailsResponse> {
    return this.commonApiService.get<ICargoNominationDetailsResponse>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies/${loadableStudyId}/cargo-nominations`);
  }

  /**
   * Method to set cargonomination
   *
   * @param {ICargoNomination} cargoNomination
   * @param {number} vesselId
   * @param {number} voyageId
   * @param {number} loadableStudyId
   * @returns {Promise<number>}
   * @memberof LoadableStudyDetailsApiService
   */
  setCargoNomination(cargoNomination: ICargoNomination, vesselId: number, voyageId: number, loadableStudyId: number): Promise<number> {
    cargoNomination.vesselId = vesselId;
    cargoNomination.voyageId = voyageId;
    cargoNomination.loadableStudyId = loadableStudyId;
    return this._cargoNominationDb.cargoNominations.add(cargoNomination);
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
   * Method to get all port specific to cargo
   *
   * @param {number} cargoId
   * @returns {Observable<IPort[]>}
   * @memberof LoadableStudyDetailsApiService
   */
  getAllCargoPorts(cargoId: number): Observable<ICargoPortsResponse> {
    return this.commonApiService.get<ICargoPortsResponse>(`cargos/${cargoId}/ports`);
  }

  /**
   * Set discharging ports for loadable study
   *
   * @param {number} vesselId
   * @param {number} voyageId
   * @param {LoadableStudies} loadableStudy
   * @returns {Observable<IResponse>}
   * @memberof LoadableStudyDetailsApiService
   */
  setLoadableStudyDischargingPorts(vesselId: number, voyageId: number, loadableStudyId: number, dischargingPortIds: IDischargingPortIds): Observable<IResponse> {
    return this.commonApiService.post<IDischargingPortIds, IResponse>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies/${loadableStudyId}/discharging-ports`, dischargingPortIds);
  }

  /**
 * Method to get all port details
 *
 * @returns {Observable<IPortsDetailsResponse>}
 * @memberof LoadableStudyDetailsApiService
 */
  getPortsDetails(vesselId: number, voyageId: number, loadableStudyId: number): Observable<IPortsDetailsResponse> {
    return this.commonApiService.get<IPortsDetailsResponse>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies/${loadableStudyId}/ports`);
  }

  /**
 * Method to set Ports
 *
 * @returns {Observable<IPortsDetailsResponse>}
 * @memberof LoadableStudyDetailsApiService
 */
  setPort(ports: IPortList, vesselId: number, voyageId: number, loadableStudyId: number): Promise<number> {
    ports.vesselId = vesselId;
    ports.voyageId = voyageId;
    ports.loadableStudyId = loadableStudyId;
    return this._portsDb.ports.add(ports);
  }

  /**
   * Method for fetching port rotation for ohq
   *
   * @param {number} vesselId
   * @param {number} voyageId
   * @param {number} loadableStudyId
   * @returns
   * @memberof LoadableStudyDetailsApiService
   */
  getOHQPortRotation(vesselId: number, voyageId: number, loadableStudyId: number): Observable<IOHQPortRotationResponse> {
    return this.commonApiService.get<IOHQPortRotationResponse>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies/${loadableStudyId}/port-rotation`);
  }

  /**
   * Method for fetching port specific ohq
   *
   * @param {number} vesselId
   * @param {number} voyageId
   * @param {number} loadableStudyId
   * @param {number} portId
   * @returns {Observable<IPortOHQResponse>}
   * @memberof LoadableStudyDetailsApiService
   */
  getPortOHQDetails(vesselId: number, voyageId: number, loadableStudyId: number, portId: number): Observable<IPortOHQResponse> {
    return this.commonApiService.get<IPortOHQResponse>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies/${loadableStudyId}/ports/${portId}/on-hand-quantities`);
  }

  /**
   * Method to set ohq
   *
   * @param {IPortOHQTankDetail} ohqTankDetails
   * @param {number} vesselId
   * @param {number} voyageId
   * @param {number} loadableStudyId
   * @returns {Promise<number>}
   * @memberof LoadableStudyDetailsApiService
   */
  setOHQTankDetails(ohqTankDetails: IPortOHQTankDetail, vesselId: number, voyageId: number, loadableStudyId: number): Promise<number> {
    ohqTankDetails.vesselId = vesselId;
    ohqTankDetails.voyageId = voyageId;
    ohqTankDetails.loadableStudyId = loadableStudyId;
    return this._ohqDb.ohq.add(ohqTankDetails);
  }
}

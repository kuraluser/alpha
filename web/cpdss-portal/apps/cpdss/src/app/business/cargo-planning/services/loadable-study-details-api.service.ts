import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { IResponse } from '../../../shared/models/common.model';
import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { CargoPlanningModule } from '../cargo-planning.module';
import { CargoNominationDB, ICargoNominationDetailsResponse, ICargoNomination, ICargoPortsResponse, IPortsResponse, IPort } from '../models/cargo-planning.model';
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

  constructor(private commonApiService: CommonApiService) {
    this._cargoNominationDb = new CargoNominationDB();
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
   * @returns {Observable<ICargoNominationDetailsResponse>}
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
}

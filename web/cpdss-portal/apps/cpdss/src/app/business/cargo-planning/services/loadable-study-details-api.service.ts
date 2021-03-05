import { Injectable } from '@angular/core';
import { from, Observable, of, Subject } from 'rxjs';
import { map } from 'rxjs/operators';
import { IResponse, QUANTITY_UNIT } from '../../../shared/models/common.model';
import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { CargoPlanningModule } from '../cargo-planning.module';
import { CargoNominationDB, ICargoNominationDetailsResponse, ICargoNomination, ICargoPortsResponse, PortsDB, IOHQPortRotationResponse, IPortOHQResponse, IPortOHQTankDetail, OHQDB, IPortOBQResponse, IPortOBQTankDetail, OBQDB, ICargoNominationValueObject, ILoadOnTop, IGeneratePatternResponse, ICargoApiTempHistoryResponse } from '../models/cargo-planning.model';
import { IDischargingPortIds } from '../models/loadable-study-list.model';
import { IPort, IPortList, IPortsDetailsResponse, IPortsResponse } from '../../core/models/common.model';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';
import { QuantityPipe } from '../../../shared/pipes/quantity/quantity.pipe';
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
    private _obqDb: OBQDB;
    private _cargoNominations: ICargoNominationValueObject[];
    public unitChange = new Subject();
    public unitChangeBlocked = new Subject();
    baseUnit = <QUANTITY_UNIT>AppConfigurationService.settings.baseUnit;
    currentUnit = <QUANTITY_UNIT>AppConfigurationService.settings.baseUnit;
    quantityPipe: QuantityPipe = new QuantityPipe()
    disableUnitChange = false;

    public cargoNominationChange = new Subject();
    get cargoNominations() {
        return this._cargoNominations;
    }
    set cargoNominations(cargoNominations: ICargoNominationValueObject[]) {
        this._cargoNominations = cargoNominations;
        this.cargoNominationChange.next(true);
    }
    constructor(private commonApiService: CommonApiService) {
        this._cargoNominationDb = new CargoNominationDB();
        this._portsDb = new PortsDB();
        this._ohqDb = new OHQDB();
        this._obqDb = new OBQDB();
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
     * Get count of pending updates in cargo nomination db
     * @param vesselId 
     * @param voyageId 
     * @param loadableStudyId 
     */
    getCargoNominationPendingUpdatesCount(vesselId: number, voyageId: number, loadableStudyId: number): Promise<number> {
        return this._cargoNominationDb.cargoNominations.where({ 'vesselId': vesselId, 'voyageId': voyageId, 'loadableStudyId': loadableStudyId }).count();
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
     * Get count of pending updates in ports db
     *
     * @param {number} vesselId
     * @param {number} voyageId
     * @param {number} loadableStudyId
     * @returns {Promise<number>}
     * @memberof LoadableStudyDetailsApiService
     */
    getPortPendingUpdatesCount(vesselId: number, voyageId: number, loadableStudyId: number): Promise<number> {
        return this._portsDb.ports.where({ 'vesselId': vesselId, 'voyageId': voyageId, 'loadableStudyId': loadableStudyId }).count();
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

    /**
     * Get count of pending updates in ohq db
     *
     * @param {number} vesselId
     * @param {number} voyageId
     * @param {number} loadableStudyId
     * @returns {Promise<number>}
     * @memberof LoadableStudyDetailsApiService
     */
    getOHQTankDetailsPendingUpdatesCount(vesselId: number, voyageId: number, loadableStudyId: number): Promise<number> {
        return this._ohqDb.ohq.where({ 'vesselId': vesselId, 'voyageId': voyageId, 'loadableStudyId': loadableStudyId }).count();
    }

    /**
    * Method for fetching port specific obq
    *
    * @param {number} vesselId
    * @param {number} voyageId
    * @param {number} loadableStudyId
    * @param {number} portId
    * @returns {Observable<IPortOBQResponse>}
    * @memberof LoadableStudyDetailsApiService
    */
    getPortOBQDetails(vesselId: number, voyageId: number, loadableStudyId: number, portId: number): Observable<IPortOBQResponse> {
        return this.commonApiService.get<IPortOBQResponse>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies/${loadableStudyId}/ports/${portId}/on-board-quantities`);
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
    setOBQTankDetails(obqTankDetails: IPortOBQTankDetail, vesselId: number, voyageId: number, loadableStudyId: number): Promise<number> {
        obqTankDetails.vesselId = vesselId;
        obqTankDetails.voyageId = voyageId;
        obqTankDetails.loadableStudyId = loadableStudyId;
        return this._obqDb.obq.add(obqTankDetails);
    }


    /**
 * Method for fetching port specific obq
 *
 * @param {number} vesselId
 * @param {number} voyageId
 * @param {number} loadableStudyId
 * @returns {Observable<IResponse>}
 * @memberof LoadableStudyDetailsApiService
 */
    generateLoadablePattern(vesselId: number, voyageId: number, loadableStudyId: number): Observable<IGeneratePatternResponse> {
        return this.commonApiService.post<any, IGeneratePatternResponse>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies/${loadableStudyId}/generate-loadable-patterns`,{});
    }
    /**
     * Get count of pending updates in obq db
     *
     * @param {number} vesselId
     * @param {number} voyageId
     * @param {number} loadableStudyId
     * @returns {Promise<number>}
     * @memberof LoadableStudyDetailsApiService
     */
    getOBQTankDetailsPendingUpdatesCount(vesselId: number, voyageId: number, loadableStudyId: number): Promise<number> {
        return this._obqDb.obq.where({ 'vesselId': vesselId, 'voyageId': voyageId, 'loadableStudyId': loadableStudyId }).count();
    }

    /**
     * Converts the quantity from one unit to another
     *
     * @param  currentValue
     * @param  api
     * @param  temp
     * @param  unitFrom
     * @param  Quick
     * @returns {number}
     * @memberof LoadableStudyDetailsApiService
     */
    updateQuantityByUnit(currentValue, unitFrom, unitTo, api, temp?) {
        if (!api || api === '') {
            api = 1;
        }
        let newValue;
        if (temp) {
            newValue = this.quantityPipe.transform(currentValue.toString(), unitFrom, unitTo, api.toString(), temp.toString());
        } else {
            newValue = this.quantityPipe.transform(currentValue.toString(), unitFrom, unitTo, api.toString());
        }
        return Number(newValue.toFixed(2));
    }

    /* Save load on top is enable or not for loadable study
    *
    * @param {number} vesselId
    * @param {number} voyageId
    * @param {number} loadableStudyId
    * @param {ILoadOnTop} loadOnTop
    * @returns {Observable<IResponse>}
    * @memberof LoadableStudyDetailsApiService
    */
    saveLoadableStudyLoadOnTop(vesselId: number, voyageId: number, loadableStudyId: number, loadOnTop: ILoadOnTop): Observable<IResponse> {
        return this.commonApiService.post<ILoadOnTop, IResponse>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies/${loadableStudyId}/load-on-top`, loadOnTop);
    }

    getCargoApiTemperatureHistoryDetails(cargoID: number, loadingPortIDs: number[]): Observable<ICargoApiTempHistoryResponse> {
        /**
         * actual API will integrate with below code
         */
        return this.commonApiService.get<ICargoApiTempHistoryResponse>(`cargoId/${cargoID}/loadingPortIds/${loadingPortIDs}`);
    }

}

import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { IResponse } from '../../../shared/models/common.model';
import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { CargoPlanningModule } from '../cargo-planning.module';
import { CargoNominationDB, ICargoNominationDetailsResponse, ICargoNomination, ICargoPortsResponse, IPortsResponse, IPort, IPortsDetailsResponse, IPortList, PortsDB, IOHQPortRotationResponse, IPortOHQResponse, IPortOHQTankDetail, OHQDB, IPortOBQResponse } from '../models/cargo-planning.model';
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
  getPortOBQDetails(vesselId: number, voyageId: number, loadableStudyId: number, portId: number): Observable<any> {
    // return this.commonApiService.get<IPortOBQResponse>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies/${loadableStudyId}/ports/${portId}/on-board-quantities`);
    return of(
      {
        "onBoardQuantities": [
            {
                "id": 0,
                "tankId": 24355,
                "tankName": "TANK-24355",
                "cargoId": 18,
                "sounding": 4871,
                "weight": 12177,
                "volume": 4059,
                "colorCode": "#24355"
            },
            {
                "id": 0,
                "tankId": 24356,
                "tankName": "TANK-24356",
                "cargoId": 19,
                "sounding": 4871,
                "weight": 12178,
                "volume": 4059,
                "colorCode": "#24356"
            },
            {
                "id": 0,
                "tankId": 24357,
                "tankName": "TANK-24357",
                "cargoId": 19,
                "sounding": 4871,
                "weight": 12178,
                "volume": 4059,
                "colorCode": "#24357"
            },
            {
                "id": 0,
                "tankId": 24358,
                "tankName": "TANK-24358",
                "cargoId": 19,
                "sounding": 4871,
                "weight": 12179,
                "volume": 4059,
                "colorCode": "#24358"
            },
            {
                "id": 0,
                "tankId": 24359,
                "tankName": "TANK-24359",
                "cargoId": 19,
                "sounding": 4871,
                "weight": 12179,
                "volume": 4059,
                "colorCode": "#24359"
            },
            {
                "id": 0,
                "tankId": 24360,
                "tankName": "TANK-24360",
                "cargoId": 20,
                "sounding": 4872,
                "weight": 12180,
                "volume": 4060,
                "colorCode": "#24360"
            },
            {
                "id": 0,
                "tankId": 24361,
                "tankName": "TANK-24361",
                "cargoId": 20,
                "sounding": 4872,
                "weight": 12180,
                "volume": 4060,
                "colorCode": "#24361"
            },
            {
                "id": 0,
                "tankId": 24362,
                "tankName": "TANK-24362",
                "cargoId": 20,
                "sounding": 4872,
                "weight": 12181,
                "volume": 4060,
                "colorCode": "#24362"
            },
            {
                "id": 0,
                "tankId": 24363,
                "tankName": "TANK-24363",
                "cargoId": 20,
                "sounding": 4872,
                "weight": 12181,
                "volume": 4060,
                "colorCode": "#24363"
            },
            {
                "id": 0,
                "tankId": 24364,
                "tankName": "TANK-24364",
                "cargoId": 21,
                "sounding": 4872,
                "weight": 12182,
                "volume": 4060,
                "colorCode": "#24364"
            },
            {
                "id": 0,
                "tankId": 24365,
                "tankName": "TANK-24365",
                "cargoId": 21,
                "sounding": 4873,
                "weight": 12182,
                "volume": 4060,
                "colorCode": "#24365"
            },
            {
                "id": 0,
                "tankId": 24366,
                "tankName": "TANK-24366",
                "cargoId": 21,
                "sounding": 4873,
                "weight": 12183,
                "volume": 4061,
                "colorCode": "#24366"
            },
            {
                "id": 0,
                "tankId": 24367,
                "tankName": "TANK-24367",
                "cargoId": 21,
                "sounding": 4873,
                "weight": 12183,
                "volume": 4061,
                "colorCode": "#24367"
            },
            {
                "id": 0,
                "tankId": 24368,
                "tankName": "TANK-24368",
                "cargoId": 22,
                "sounding": 4873,
                "weight": 12184,
                "volume": 4061,
                "colorCode": "#24368"
            },
            {
                "id": 0,
                "tankId": 24369,
                "tankName": "TANK-24369",
                "cargoId": 22,
                "sounding": 4873,
                "weight": 12184,
                "volume": 4061,
                "colorCode": "#24369"
            },
            {
                "id": 0,
                "tankId": 24370,
                "tankName": "TANK-24370",
                "cargoId": 22,
                "sounding": 4874,
                "weight": 12185,
                "volume": 4061,
                "colorCode": "#24370"
            },
            {
                "id": 0,
                "tankId": 24371,
                "tankName": "TANK-24371",
                "cargoId": 22,
                "sounding": 4874,
                "weight": 12185,
                "volume": 4061,
                "colorCode": "#24371"
            },
            {
                "id": 0,
                "tankId": 24372,
                "tankName": "TANK-24372",
                "cargoId": 23,
                "sounding": 4874,
                "weight": 12186,
                "volume": 4062,
                "colorCode": "#24372"
            },
            {
                "id": 0,
                "tankId": 24373,
                "tankName": "TANK-24373",
                "cargoId": 23,
                "sounding": 4874,
                "weight": 12186,
                "volume": 4062,
                "colorCode": "#24373"
            },
            {
                "id": 0,
                "tankId": 24374,
                "tankName": "TANK-24374",
                "cargoId": 23,
                "sounding": 4874,
                "weight": 12187,
                "volume": 4062,
                "colorCode": "#24374"
            },
            {
                "id": 0,
                "tankId": 24375,
                "tankName": "TANK-24375",
                "cargoId": 23,
                "sounding": 4875,
                "weight": 12187,
                "volume": 4062,
                "colorCode": "#24375"
            },
            {
                "id": 0,
                "tankId": 24376,
                "tankName": "TANK-24376",
                "cargoId": 24,
                "sounding": 4875,
                "weight": 12188,
                "volume": 4062,
                "colorCode": "#24376"
            },
            {
                "id": 0,
                "tankId": 24377,
                "tankName": "TANK-24377",
                "cargoId": 24,
                "sounding": 4875,
                "weight": 12188,
                "volume": 4062,
                "colorCode": "#24377"
            },
            {
                "id": 0,
                "tankId": 24378,
                "tankName": "TANK-24378",
                "cargoId": 24,
                "sounding": 4875,
                "weight": 12189,
                "volume": 4063,
                "colorCode": "#24378"
            }
        ],
        "tanks": [
            [
                {
                    "id": 25595,
                    "categoryId": 1,
                    "categoryName": "Cargo Tank",
                    "name": "SLOP TANK",
                    "frameNumberFrom": "49",
                    "frameNumberTo": "52",
                    "shortName": "SLP",
                    "fillCapcityCubm": 4117.0000,
                    "density": 1.3000,
                    "group": 1,
                    "order": 1,
                    "slopTank": false
                },
                {
                    "id": 25593,
                    "categoryId": 1,
                    "categoryName": "Cargo Tank",
                    "name": "NO.5 WING CARGO OIL TANK",
                    "frameNumberFrom": "52",
                    "frameNumberTo": "61",
                    "shortName": "5P",
                    "fillCapcityCubm": 17277.0000,
                    "density": 1.3000,
                    "group": 1,
                    "order": 2,
                    "slopTank": false
                },
                {
                    "id": 25584,
                    "categoryId": 1,
                    "categoryName": "Cargo Tank",
                    "name": "NO.5 CENTER CARGO OIL TANK",
                    "frameNumberFrom": "49",
                    "frameNumberTo": "61",
                    "shortName": "5C",
                    "fillCapcityCubm": 20798.0000,
                    "density": 1.3000,
                    "group": 1,
                    "order": 3,
                    "slopTank": false
                },
                {
                    "id": 25596,
                    "categoryId": 1,
                    "categoryName": "Cargo Tank",
                    "name": "SLOP TANK",
                    "frameNumberFrom": "49",
                    "frameNumberTo": "52",
                    "shortName": "SLS",
                    "fillCapcityCubm": 4117.0000,
                    "density": 1.3000,
                    "group": 1,
                    "order": 4,
                    "slopTank": false
                },
                {
                    "id": 25594,
                    "categoryId": 1,
                    "categoryName": "Cargo Tank",
                    "name": "NO.5 WING CARGO OIL TANK",
                    "frameNumberFrom": "52",
                    "frameNumberTo": "61",
                    "shortName": "5S",
                    "fillCapcityCubm": 17277.0000,
                    "density": 1.3000,
                    "group": 1,
                    "order": 5,
                    "slopTank": false
                }
            ],
            [
                {
                    "id": 25591,
                    "categoryId": 1,
                    "categoryName": "Cargo Tank",
                    "name": "NO.4 WING CARGO OIL TANK",
                    "frameNumberFrom": "61",
                    "frameNumberTo": "71",
                    "shortName": "4P",
                    "fillCapcityCubm": 20281.0000,
                    "density": 1.3000,
                    "group": 2,
                    "order": 1,
                    "slopTank": false
                },
                {
                    "id": 25583,
                    "categoryId": 1,
                    "categoryName": "Cargo Tank",
                    "name": "NO.4 CENTER CARGO OIL TANK",
                    "frameNumberFrom": "61",
                    "frameNumberTo": "71",
                    "shortName": "4C",
                    "fillCapcityCubm": 33725.0000,
                    "density": 1.3000,
                    "group": 2,
                    "order": 2,
                    "slopTank": false
                },
                {
                    "id": 25592,
                    "categoryId": 1,
                    "categoryName": "Cargo Tank",
                    "name": "NO.4 WING CARGO OIL TANK",
                    "frameNumberFrom": "61",
                    "frameNumberTo": "71",
                    "shortName": "4S",
                    "fillCapcityCubm": 20281.0000,
                    "density": 1.3000,
                    "group": 2,
                    "order": 3,
                    "slopTank": false
                }
            ],
            [
                {
                    "id": 25589,
                    "categoryId": 1,
                    "categoryName": "Cargo Tank",
                    "name": "NO.3 WING CARGO OIL TANK",
                    "frameNumberFrom": "71",
                    "frameNumberTo": "81",
                    "shortName": "3P",
                    "fillCapcityCubm": 20281.0000,
                    "density": 1.3000,
                    "group": 3,
                    "order": 1,
                    "slopTank": false
                },
                {
                    "id": 25582,
                    "categoryId": 1,
                    "categoryName": "Cargo Tank",
                    "name": "NO.3 CENTER CARGO OIL TANK",
                    "frameNumberFrom": "71",
                    "frameNumberTo": "81",
                    "shortName": "3C",
                    "fillCapcityCubm": 28202.0000,
                    "density": 1.3000,
                    "group": 3,
                    "order": 2,
                    "slopTank": false
                },
                {
                    "id": 25590,
                    "categoryId": 1,
                    "categoryName": "Cargo Tank",
                    "name": "NO.3 WING CARGO OIL TANK",
                    "frameNumberFrom": "71",
                    "frameNumberTo": "81",
                    "shortName": "3S",
                    "fillCapcityCubm": 20281.0000,
                    "density": 1.3000,
                    "group": 3,
                    "order": 3,
                    "slopTank": false
                }
            ],
            [
                {
                    "id": 25587,
                    "categoryId": 1,
                    "categoryName": "Cargo Tank",
                    "name": "NO.2 WING CARGO OIL TANK",
                    "frameNumberFrom": "81",
                    "frameNumberTo": "91",
                    "shortName": "2P",
                    "fillCapcityCubm": 20281.0000,
                    "density": 1.3000,
                    "group": 4,
                    "order": 1,
                    "slopTank": false
                },
                {
                    "id": 25581,
                    "categoryId": 1,
                    "categoryName": "Cargo Tank",
                    "name": "NO.2 CENTER CARGO OIL TANK",
                    "frameNumberFrom": "81",
                    "frameNumberTo": "91",
                    "shortName": "2C",
                    "fillCapcityCubm": 28202.0000,
                    "density": 1.3000,
                    "group": 4,
                    "order": 2,
                    "slopTank": false
                },
                {
                    "id": 25588,
                    "categoryId": 1,
                    "categoryName": "Cargo Tank",
                    "name": "NO.2 WING CARGO OIL TANK",
                    "frameNumberFrom": "81",
                    "frameNumberTo": "91",
                    "shortName": "2S",
                    "fillCapcityCubm": 20281.0000,
                    "density": 1.3000,
                    "group": 4,
                    "order": 3,
                    "slopTank": false
                }
            ],
            [
                {
                    "id": 25585,
                    "categoryId": 1,
                    "categoryName": "Cargo Tank",
                    "name": "NO.1  WING CARGO OIL TANK",
                    "frameNumberFrom": "91",
                    "frameNumberTo": "103",
                    "shortName": "1P",
                    "fillCapcityCubm": 20798.0000,
                    "density": 1.3000,
                    "group": 5,
                    "order": 1,
                    "slopTank": false
                },
                {
                    "id": 25580,
                    "categoryId": 1,
                    "categoryName": "Cargo Tank",
                    "name": "NO.1 CENTER CARGO OIL TANK",
                    "frameNumberFrom": "91",
                    "frameNumberTo": "103",
                    "shortName": "1C",
                    "fillCapcityCubm": 30230.0000,
                    "density": 1.3000,
                    "group": 5,
                    "order": 2,
                    "slopTank": false
                },
                {
                    "id": 25586,
                    "categoryId": 1,
                    "categoryName": "Cargo Tank",
                    "name": "NO.1  WING CARGO OIL TANK",
                    "frameNumberFrom": "91",
                    "frameNumberTo": "103",
                    "shortName": "1S",
                    "fillCapcityCubm": 20281.0000,
                    "density": 1.3000,
                    "group": 5,
                    "order": 3,
                    "slopTank": false
                }
            ]
        ]
    }
    )
  }
}

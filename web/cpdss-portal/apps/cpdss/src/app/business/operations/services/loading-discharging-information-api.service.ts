import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { IResponse } from './../../../shared/models/common.model';
import { OPERATIONS } from '../../core/models/common.model';

import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { IDischargingInformationResponse, ILoadingInformation, ILoadingInformationResponse, ILoadingInformationSaveResponse } from '../models/loading-discharging.model';

/**
 * Api Service for Loading & Discharging information tab
 *
 * @export
 * @class LoadingDischargingInformationApiService
 */

@Injectable()
export class LoadingDischargingInformationApiService {
  constructor(
    private commonApiService: CommonApiService) {
  }

  /**
   * Methid to fetch loading information
   *
   * @param {number} vesselId
   * @param {number} voyageId
   * @param {number} portRotationId
   * @return {*}  {Observable<ILoadingInformationResponse>}
   * @memberof LoadingDischargingInformationApiService
   */
  getLoadingInformation(vesselId: number, voyageId: number, portRotationId: number): Observable<ILoadingInformationResponse> {
    return this.commonApiService.get<ILoadingInformationResponse>(`vessels/${vesselId}/voyages/${voyageId}/loading-info/0/port-rotation/${portRotationId}`);
  }

  /**
   * Method for saving loading information
   *
   * @param {number} vesselId
   * @param {Number} voyageId
   * @param {ILoadingInformation} loadingInformation
   * @return {*}  {Observable<ILoadingInformationSaveResponse>}
   * @memberof LoadingDischargingInformationApiService
   */
  saveLoadingInformation(vesselId: number, voyageId: Number, loadingInformation: ILoadingInformation): Observable<ILoadingInformationSaveResponse> {
    return this.commonApiService.post<ILoadingInformation, ILoadingInformationSaveResponse>(`vessels/${vesselId}/voyages/${voyageId}/loading-info`, loadingInformation);
  }

  /**
   * Method for fetching discharging information
   *
   * @param {number} vesselId
   * @param {number} voyageId
   * @param {number} portRotationId
   * @return {*}  {Observable<IDischargingInformationResponse>}
   * @memberof LoadingDischargingInformationApiService
   */
  getDischargingInformation(vesselId: number, voyageId: number, portRotationId: number): Observable<IDischargingInformationResponse> {
    const response: IDischargingInformationResponse = {
      "responseStatus": {
        "status": "200"
      },
      "dischargeInfoId": 136,
      "dischargeStudyName": "DS1",
      "synopticTableId": 89426,
      "isDischargeInfoComplete": false,
      "dischargeDetails": {
        "timeOfSunrise": "05:39",
        "timeOfSunset": "06:40",
        "startTime": "20:13",
        "trimAllowed": {
          "initialTrim": 1.0000,
          "maximumTrim": 1.0000,
          "topOffTrim": 1.0000
        }
      },
      "dischargeRates": {
        "maxDischargingRate": 20500.0000,
        "minBallastingRate": 2500.0000,
        "maxBallastingRate": 6000.0000,
        "initialDischargingRate": 1000.0000,
        "id": 136
      },
      "berthDetails": {
        "availableBerths": [
          {
            "berthId": 20879,
            "portId": 504,
            "dischargeInfoId": 136,
            "maxShpChannel": 18.0000,
            "berthName": "Oil Jetty",
            "dischargingBerthId": null,
            "maxShipDepth": 0,
            "hoseConnections": "hoseConnections",
            "seaDraftLimitation": 0,
            "airDraftLimitation": 0,
            "maxManifoldHeight": 0,
            "regulationAndRestriction": "regulationAndRestriction",
            "maxLoa": "250.0000",
            "maxDraft": null,
            "itemsToBeAgreedWith": "itemsToBeAgreedWith",
            "lineDisplacement": "",
            "cargoCirculation": true,
            "airPurge": true,
            "maxManifoldPressure": 99
          }
        ],
        "selectedBerths": [
          {
            "berthId": 20879,
            "portId": null,
            "dischargeInfoId": 136,
            "maxShpChannel": null,
            "berthName": null,
            "dischargingBerthId": 89,
            "maxShipDepth": 11.0000,
            "hoseConnections": "hoseConnections",
            "seaDraftLimitation": 11.0000,
            "airDraftLimitation": 11.0000,
            "maxManifoldHeight": 11.0000,
            "regulationAndRestriction": "regulationAndRestriction",
            "maxLoa": null,
            "maxDraft": null,
            "itemsToBeAgreedWith": "itemsToBeAgreedWith",
            "lineDisplacement": "1000.0000",
            "cargoCirculation": true,
            "airPurge": true,
            "maxManifoldPressure": 99
          }
        ]
      },
      "machineryInUses": {
        "pumpTypes": [
          {
            "id": 1,
            "name": "Cargo Pump"
          },
          {
            "id": 2,
            "name": "Ballast Pump"
          },
          {
            "id": 3,
            "name": "GS Pump"
          },
          {
            "id": 4,
            "name": "IG Pump"
          }
        ],
        "vesselPumps": [
          {
            "id": 1,
            "vesselId": 1,
            "pumpTypeId": 1,
            "pumpName": "COP1",
            "pumpCode": "COP1",
            "pumpCapacity": null,
            "machineType": 1
          },
          {
            "id": 2,
            "vesselId": 1,
            "pumpTypeId": 1,
            "pumpName": "COP2",
            "pumpCode": "COP2",
            "pumpCapacity": null,
            "machineType": 1
          },
          {
            "id": 3,
            "vesselId": 1,
            "pumpTypeId": 1,
            "pumpName": "COP3",
            "pumpCode": "COP3",
            "pumpCapacity": null,
            "machineType": 1
          }
        ],
        "vesselManifold": [
          {
            "id": 1,
            "vesselId": 1,
            "componentName": "Manifold 1",
            "componentCode": "MFP1",
            "componentType": 1,
            "machineTypeId": 2
          },
          {
            "id": 2,
            "vesselId": 1,
            "componentName": "Manifold 2",
            "componentCode": "MFP2",
            "componentType": 1,
            "machineTypeId": 2
          },
          {
            "id": 3,
            "vesselId": 1,
            "componentName": "Manifold 3",
            "componentCode": "MFP3",
            "componentType": 1,
            "machineTypeId": 2
          },
          {
            "id": 4,
            "vesselId": 1,
            "componentName": "Manifold 4",
            "componentCode": "MFP4",
            "componentType": 1,
            "machineTypeId": 2
          },
          {
            "id": 5,
            "vesselId": 1,
            "componentName": "Manifold 1",
            "componentCode": "MFS1",
            "componentType": 3,
            "machineTypeId": 2
          },
          {
            "id": 6,
            "vesselId": 1,
            "componentName": "Manifold 2",
            "componentCode": "MFS2",
            "componentType": 3,
            "machineTypeId": 2
          },
          {
            "id": 7,
            "vesselId": 1,
            "componentName": "Manifold 3",
            "componentCode": "MFS3",
            "componentType": 3,
            "machineTypeId": 2
          },
          {
            "id": 8,
            "vesselId": 1,
            "componentName": "Manifold 4",
            "componentCode": "MFS4",
            "componentType": 3,
            "machineTypeId": 2
          }
        ],
        "vesselBottomLine": [
          {
            "id": 1,
            "vesselId": 1,
            "componentName": "Bottom Line 1",
            "componentCode": "BL1",
            "componentType": -1,
            "machineTypeId": 3
          },
          {
            "id": 2,
            "vesselId": 1,
            "componentName": "Bottom Line 2",
            "componentCode": "BL2",
            "componentType": -1,
            "machineTypeId": 3
          },
          {
            "id": 3,
            "vesselId": 1,
            "componentName": "Bottom Line 3",
            "componentCode": "BL3",
            "componentType": -1,
            "machineTypeId": 3
          }
        ],
        "dischargingMachinesInUses": [
          {
            "id": 89,
            "dischargeInfoId": 136,
            "machineId": 2,
            "capacity": 0.0000,
            "isUsing": true,
            "machineTypeId": 1
          },
          {
            "id": 90,
            "dischargeInfoId": 136,
            "machineId": 3,
            "capacity": 0.0000,
            "isUsing": true,
            "machineTypeId": 1
          }
        ],
        "machineTypes": {
          "EMPTY": 0,
          "VESSEL_PUMP": 1,
          "BOTTOM_LINE": 3,
          "MANIFOLD": 2
        },
        "tankTypes": [
          {
            "id": 1,
            "name": "Port"
          },
          {
            "id": 2,
            "name": "Centre"
          },
          {
            "id": 3,
            "name": "Stbd"
          },
          {
            "id": 4,
            "name": "WP"
          },
          {
            "id": 5,
            "name": "WS"
          }
        ]
      },
      "dischargeStages": {
        "id": 136,
        "trackStartEndStage": true,
        "trackGradeSwitch": true,
        "stageOffset": 4,
        "stageDuration": 4,
        "stageOffsetList": [
          {
            "id": 1,
            "stageOffsetVal": 1
          },
          {
            "id": 2,
            "stageOffsetVal": 2
          },
          {
            "id": 3,
            "stageOffsetVal": 3
          },
          {
            "id": 4,
            "stageOffsetVal": 4
          },
          {
            "id": 5,
            "stageOffsetVal": 5
          },
          {
            "id": 6,
            "stageOffsetVal": 6
          }
        ],
        "stageDurationList": [
          {
            "id": 1,
            "duration": 1
          },
          {
            "id": 2,
            "duration": 2
          },
          {
            "id": 3,
            "duration": 3
          },
          {
            "id": 4,
            "duration": 4
          },
          {
            "id": 5,
            "duration": 5
          },
          {
            "id": 6,
            "duration": 6
          },
          {
            "id": 7,
            "duration": 7
          },
          {
            "id": 8,
            "duration": 8
          },
          {
            "id": 9,
            "duration": 9
          }
        ]
      },
      "dischargeSequences": {
        "reasonForDelays": [
          { "id": 2, "reason": "Reason 2" },
          { "id": 1, "reason": "Tank Switching" },
          { "id": 3, "reason": "Reason 3" }
        ],
        "dischargingDelays": [
          { "id": 197, "dischargeInfoId": 141, "reasonForDelayIds": [2], "duration": 0, "cargoId": 0, "cargoNominationId": 0, "quantity": null },
          { "id": 199, "dischargeInfoId": 141, "reasonForDelayIds": [2], "duration": 60, "cargoId": 32, "cargoNominationId": 17760, "quantity": 577108, "sequenceNo": 1 },
          { "id": 198, "dischargeInfoId": 141, "reasonForDelayIds": [2, 1], "duration": 0, "cargoId": 33, "cargoNominationId": 17759, "quantity": 1070147, "sequenceNo": 2 }
        ]
      },
      "cargoVesselTankDetails": {
        "cargoTanks": [
          [
            {
              "id": 25595,
              "categoryId": 1,
              "categoryName": "Cargo Tank",
              "name": "SLOP TANK",
              "frameNumberFrom": 49,
              "frameNumberTo": 52,
              "shortName": "SLP",
              "fullCapacityCubm": "4117.3000",
              "density": 1.3,
              "group": 1,
              "order": 1,
              "slopTank": false
            },
            {
              "id": 25593,
              "categoryId": 1,
              "categoryName": "Cargo Tank",
              "name": "NO.5 WING CARGO OIL TANK",
              "frameNumberFrom": 52,
              "frameNumberTo": 61,
              "shortName": "5P",
              "fullCapacityCubm": "17277.4000",
              "density": 1.3,
              "group": 1,
              "order": 2,
              "slopTank": false
            },
            {
              "id": 25584,
              "categoryId": 1,
              "categoryName": "Cargo Tank",
              "name": "NO.5 CENTER CARGO OIL TANK",
              "frameNumberFrom": 49,
              "frameNumberTo": 61,
              "shortName": "5C",
              "fullCapacityCubm": "33725.1000",
              "density": 1.3,
              "group": 1,
              "order": 3,
              "slopTank": false
            },
            {
              "id": 25596,
              "categoryId": 1,
              "categoryName": "Cargo Tank",
              "name": "SLOP TANK",
              "frameNumberFrom": 49,
              "frameNumberTo": 52,
              "shortName": "SLS",
              "fullCapacityCubm": "4117.3000",
              "density": 1.3,
              "group": 1,
              "order": 4,
              "slopTank": false
            },
            {
              "id": 25594,
              "categoryId": 1,
              "categoryName": "Cargo Tank",
              "name": "NO.5 WING CARGO OIL TANK",
              "frameNumberFrom": 52,
              "frameNumberTo": 61,
              "shortName": "5S",
              "fullCapacityCubm": "17277.4000",
              "density": 1.3,
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
              "frameNumberFrom": 61,
              "frameNumberTo": 71,
              "shortName": "4P",
              "fullCapacityCubm": "20290.8000",
              "density": 1.3,
              "group": 2,
              "order": 1,
              "slopTank": false
            },
            {
              "id": 25583,
              "categoryId": 1,
              "categoryName": "Cargo Tank",
              "name": "NO.4 CENTER CARGO OIL TANK",
              "frameNumberFrom": 61,
              "frameNumberTo": 71,
              "shortName": "4C",
              "fullCapacityCubm": "28201.6000",
              "density": 1.3,
              "group": 2,
              "order": 2,
              "slopTank": false
            },
            {
              "id": 25592,
              "categoryId": 1,
              "categoryName": "Cargo Tank",
              "name": "NO.4 WING CARGO OIL TANK",
              "frameNumberFrom": 61,
              "frameNumberTo": 71,
              "shortName": "4S",
              "fullCapacityCubm": "20290.8000",
              "density": 1.3,
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
              "frameNumberFrom": 71,
              "frameNumberTo": 81,
              "shortName": "3P",
              "fullCapacityCubm": "20290.8000",
              "density": 1.3,
              "group": 3,
              "order": 1,
              "slopTank": false
            },
            {
              "id": 25582,
              "categoryId": 1,
              "categoryName": "Cargo Tank",
              "name": "NO.3 CENTER CARGO OIL TANK",
              "frameNumberFrom": 71,
              "frameNumberTo": 81,
              "shortName": "3C",
              "fullCapacityCubm": "28201.6000",
              "density": 1.3,
              "group": 3,
              "order": 2,
              "slopTank": false
            },
            {
              "id": 25590,
              "categoryId": 1,
              "categoryName": "Cargo Tank",
              "name": "NO.3 WING CARGO OIL TANK",
              "frameNumberFrom": 71,
              "frameNumberTo": 81,
              "shortName": "3S",
              "fullCapacityCubm": "20290.8000",
              "density": 1.3,
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
              "frameNumberFrom": 81,
              "frameNumberTo": 91,
              "shortName": "2P",
              "fullCapacityCubm": "20290.8000",
              "density": 1.3,
              "group": 4,
              "order": 1,
              "slopTank": false
            },
            {
              "id": 25581,
              "categoryId": 1,
              "categoryName": "Cargo Tank",
              "name": "NO.2 CENTER CARGO OIL TANK",
              "frameNumberFrom": 81,
              "frameNumberTo": 91,
              "shortName": "2C",
              "fullCapacityCubm": "28201.6000",
              "density": 1.3,
              "group": 4,
              "order": 2,
              "slopTank": false
            },
            {
              "id": 25588,
              "categoryId": 1,
              "categoryName": "Cargo Tank",
              "name": "NO.2 WING CARGO OIL TANK",
              "frameNumberFrom": 81,
              "frameNumberTo": 91,
              "shortName": "2S",
              "fullCapacityCubm": "20290.8000",
              "density": 1.3,
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
              "frameNumberFrom": 91,
              "frameNumberTo": 103,
              "shortName": "1P",
              "fullCapacityCubm": "20797.7000",
              "density": 1.3,
              "group": 5,
              "order": 1,
              "slopTank": false
            },
            {
              "id": 25580,
              "categoryId": 1,
              "categoryName": "Cargo Tank",
              "name": "NO.1 CENTER CARGO OIL TANK",
              "frameNumberFrom": 91,
              "frameNumberTo": 103,
              "shortName": "1C",
              "fullCapacityCubm": "30229.5000",
              "density": 1.3,
              "group": 5,
              "order": 2,
              "slopTank": false
            },
            {
              "id": 25586,
              "categoryId": 1,
              "categoryName": "Cargo Tank",
              "name": "NO.1  WING CARGO OIL TANK",
              "frameNumberFrom": 91,
              "frameNumberTo": 103,
              "shortName": "1S",
              "fullCapacityCubm": "20797.7000",
              "density": 1.3,
              "group": 5,
              "order": 3,
              "slopTank": false
            }
          ]
        ],
        "cargoQuantities": [
          {
            "tankId": 25580,
            "tankName": "1C",
            "actualWeight": 0,
            "plannedWeight": 8657.5,
            "capacity": 30229.5,
            "abbreviation": "ARL",
            "cargoId": 0,
            "colorCode": "#f91010",
            "correctedUllage": 18.6093,
            "api": 33,
            "isCommingleCargo": false,
            "temperature": 120,
            "fillingRatio": "34.3"
          },
          {
            "tankId": 25581,
            "tankName": "2C",
            "actualWeight": 0,
            "plannedWeight": 20158.7,
            "capacity": 28201.6,
            "abbreviation": "ARM",
            "cargoId": 0,
            "colorCode": "#f6ef0e",
            "correctedUllage": 5.3314,
            "api": 29.2,
            "isCommingleCargo": false,
            "temperature": 105,
            "fillingRatio": "83.0"
          },
          {
            "tankId": 25582,
            "tankName": "3C",
            "actualWeight": 0,
            "plannedWeight": 23547,
            "capacity": 28201.6,
            "abbreviation": "ARM",
            "cargoId": 0,
            "colorCode": "#f6ef0e",
            "correctedUllage": 1.4886,
            "api": 29.2,
            "isCommingleCargo": false,
            "temperature": 105,
            "fillingRatio": "97.0"
          },
          {
            "tankId": 25583,
            "tankName": "4C",
            "actualWeight": 0,
            "plannedWeight": 2779.9,
            "capacity": 28201.6,
            "abbreviation": "ARL",
            "cargoId": 0,
            "colorCode": "#f91010",
            "correctedUllage": 24.7622,
            "api": 33,
            "isCommingleCargo": false,
            "temperature": 120,
            "fillingRatio": "11.8"
          },
          {
            "tankId": 25584,
            "tankName": "5C",
            "actualWeight": 0,
            "plannedWeight": 16003.7,
            "capacity": 33725.1,
            "abbreviation": "ARM",
            "cargoId": 0,
            "colorCode": "#f6ef0e",
            "correctedUllage": 12.6301,
            "api": 29.2,
            "isCommingleCargo": false,
            "temperature": 105,
            "fillingRatio": "55.1"
          },
          {
            "tankId": 25585,
            "tankName": "1P",
            "actualWeight": 0,
            "plannedWeight": 16534.3,
            "capacity": 20797.7,
            "abbreviation": "ARM",
            "cargoId": 0,
            "colorCode": "#f6ef0e",
            "correctedUllage": 2.7628,
            "api": 29.2,
            "isCommingleCargo": false,
            "temperature": 105,
            "fillingRatio": "92.3"
          },
          {
            "tankId": 25586,
            "tankName": "1S",
            "actualWeight": 0,
            "plannedWeight": 15008.9,
            "capacity": 20797.7,
            "abbreviation": "ARM",
            "cargoId": 0,
            "colorCode": "#f6ef0e",
            "correctedUllage": 4.9445,
            "api": 29.2,
            "isCommingleCargo": false,
            "temperature": 105,
            "fillingRatio": "83.8"
          },
          {
            "tankId": 25587,
            "tankName": "2P",
            "actualWeight": 0,
            "plannedWeight": 15050.3,
            "capacity": 20290.8,
            "abbreviation": "ARL",
            "cargoId": 0,
            "colorCode": "#f91010",
            "correctedUllage": 3.7257,
            "api": 33,
            "isCommingleCargo": false,
            "temperature": 120,
            "fillingRatio": "88.9"
          },
          {
            "tankId": 25588,
            "tankName": "2S",
            "actualWeight": 0,
            "plannedWeight": 15353.8,
            "capacity": 20290.8,
            "abbreviation": "ARL",
            "cargoId": 0,
            "colorCode": "#f91010",
            "correctedUllage": 3.263,
            "api": 33,
            "isCommingleCargo": false,
            "temperature": 120,
            "fillingRatio": "90.7"
          },
          {
            "tankId": 25589,
            "tankName": "3P",
            "actualWeight": 0,
            "plannedWeight": 11353.3,
            "capacity": 20290.8,
            "abbreviation": "ARL",
            "cargoId": 0,
            "colorCode": "#f91010",
            "correctedUllage": 9.3612,
            "api": 33,
            "isCommingleCargo": false,
            "temperature": 120,
            "fillingRatio": "67.1"
          },
          {
            "tankId": 25590,
            "tankName": "3S",
            "actualWeight": 0,
            "plannedWeight": 12651,
            "capacity": 20290.8,
            "abbreviation": "ARL",
            "cargoId": 0,
            "colorCode": "#f91010",
            "correctedUllage": 7.3827,
            "api": 33,
            "isCommingleCargo": false,
            "temperature": 120,
            "fillingRatio": "74.7"
          },
          {
            "tankId": 25591,
            "tankName": "4P",
            "actualWeight": 0,
            "plannedWeight": 17120.9,
            "capacity": 20290.8,
            "abbreviation": "ARM",
            "cargoId": 0,
            "colorCode": "#f6ef0e",
            "correctedUllage": 1.3863,
            "api": 29.2,
            "isCommingleCargo": false,
            "temperature": 105,
            "fillingRatio": "98.0"
          },
          {
            "tankId": 25592,
            "tankName": "4S",
            "actualWeight": 0,
            "plannedWeight": 17120.9,
            "capacity": 20290.8,
            "abbreviation": "ARM",
            "cargoId": 0,
            "colorCode": "#f6ef0e",
            "correctedUllage": 1.3863,
            "api": 29.2,
            "isCommingleCargo": false,
            "temperature": 105,
            "fillingRatio": "98.0"
          },
          {
            "tankId": 25593,
            "tankName": "5P",
            "actualWeight": 0,
            "plannedWeight": 14126.2,
            "capacity": 17277.4,
            "abbreviation": "ARL",
            "cargoId": 0,
            "colorCode": "#f91010",
            "correctedUllage": 1.4487,
            "api": 33,
            "isCommingleCargo": false,
            "temperature": 120,
            "fillingRatio": "98.0"
          },
          {
            "tankId": 25594,
            "tankName": "5S",
            "actualWeight": 0,
            "plannedWeight": 13671.6,
            "capacity": 17277.4,
            "abbreviation": "ARL",
            "cargoId": 0,
            "colorCode": "#f91010",
            "correctedUllage": 2.221,
            "api": 33,
            "isCommingleCargo": false,
            "temperature": 120,
            "fillingRatio": "94.8"
          },
          {
            "tankId": 25595,
            "tankName": "SLP",
            "actualWeight": 0,
            "plannedWeight": 1308.7,
            "capacity": 4117.3,
            "abbreviation": "ARL",
            "cargoId": 0,
            "colorCode": "#f91010",
            "correctedUllage": 12.7306,
            "api": 33,
            "isCommingleCargo": false,
            "temperature": 120,
            "fillingRatio": "38.1"
          },
          {
            "tankId": 25596,
            "tankName": "SLS",
            "actualWeight": 0,
            "plannedWeight": 1614.3,
            "capacity": 4117.3,
            "abbreviation": "ARM",
            "cargoId": 0,
            "colorCode": "#f6ef0e",
            "correctedUllage": 11.3051,
            "api": 29.2,
            "isCommingleCargo": false,
            "temperature": 105,
            "fillingRatio": "45.5"
          }
        ],
        "loadableQuantityCargoDetails": [
          {
            "id": 9252,
            "grade": "",
            "estimatedAPI": "29.2000",
            "estimatedTemp": "105.0000",
            "cargoId": 33,
            "cargoAbbreviation": "ARM",
            "colorCode": "#f6ef0e",
            "cargoNominationId": 17375,
            "shipFigure": "127108.7",
            "timeRequiredForDischarging": "1.00",
            "blFigure": "118729.5000",
            "maxDischargingRate": "20500.0000",
            "protested": true,
            "isCommingledDischarge": true,
            "slopQuantity": 3301
          },
          {
            "id": 9253,
            "grade": "",
            "estimatedAPI": "33.0000",
            "estimatedTemp": "120.0000",
            "cargoId": 32,
            "cargoAbbreviation": "ARL",
            "colorCode": "#f91010",
            "cargoNominationId": 17376,
            "slopQuantity": 1308.7,
            "shipFigure": "94952.3",
            "timeRequiredForDischarging": "7.05",
            "blFigure": "88692.9000",
            "maxDischargingRate": "20500.0000",
            "protested": true,
            "isCommingledDischarge": false,
            "isCommingledCargo": true
          }
        ],
        "cargoConditions": [
          {
            "id": 0,
            "abbreviation": "ARL",
            "plannedWeight": 94952.3,
            "actualWeight": 0
          },
          {
            "id": 0,
            "abbreviation": "ARM",
            "plannedWeight": 127108.7,
            "actualWeight": 0
          }
        ]
      },
      "cowDetails": {
        "cowOption": 1,
        "cowPercentage": 25,
        "washTanksWithDifferentCargo": true,
        "topCOWTanks": [
          {
            "id": 25595,
            "categoryId": 1,
            "categoryName": "Cargo Tank",
            "name": "SLOP TANK",
            "frameNumberFrom": 49,
            "frameNumberTo": 52,
            "shortName": "SLP",
            "fullCapacityCubm": "4117.3000",
            "density": 1.3,
            "group": 1,
            "order": 1,
            "slopTank": false
          },
          {
            "id": 25593,
            "categoryId": 1,
            "categoryName": "Cargo Tank",
            "name": "NO.5 WING CARGO OIL TANK",
            "frameNumberFrom": 52,
            "frameNumberTo": 61,
            "shortName": "5P",
            "fullCapacityCubm": "17277.4000",
            "density": 1.3,
            "group": 1,
            "order": 2,
            "slopTank": false
          },
          {
            "id": 25584,
            "categoryId": 1,
            "categoryName": "Cargo Tank",
            "name": "NO.5 CENTER CARGO OIL TANK",
            "frameNumberFrom": 49,
            "frameNumberTo": 61,
            "shortName": "5C",
            "fullCapacityCubm": "33725.1000",
            "density": 1.3,
            "group": 1,
            "order": 3,
            "slopTank": false
          },
          {
            "id": 25596,
            "categoryId": 1,
            "categoryName": "Cargo Tank",
            "name": "SLOP TANK",
            "frameNumberFrom": 49,
            "frameNumberTo": 52,
            "shortName": "SLS",
            "fullCapacityCubm": "4117.3000",
            "density": 1.3,
            "group": 1,
            "order": 4,
            "slopTank": false
          },
          {
            "id": 25594,
            "categoryId": 1,
            "categoryName": "Cargo Tank",
            "name": "NO.5 WING CARGO OIL TANK",
            "frameNumberFrom": 52,
            "frameNumberTo": 61,
            "shortName": "5S",
            "fullCapacityCubm": "17277.4000",
            "density": 1.3,
            "group": 1,
            "order": 5,
            "slopTank": false
          }
        ],
        "bottomCOWTanks": [
          {
            "id": 25595,
            "categoryId": 1,
            "categoryName": "Cargo Tank",
            "name": "SLOP TANK",
            "frameNumberFrom": 49,
            "frameNumberTo": 52,
            "shortName": "SLP",
            "fullCapacityCubm": "4117.3000",
            "density": 1.3,
            "group": 1,
            "order": 1,
            "slopTank": false
          },
          {
            "id": 25593,
            "categoryId": 1,
            "categoryName": "Cargo Tank",
            "name": "NO.5 WING CARGO OIL TANK",
            "frameNumberFrom": 52,
            "frameNumberTo": 61,
            "shortName": "5P",
            "fullCapacityCubm": "17277.4000",
            "density": 1.3,
            "group": 1,
            "order": 2,
            "slopTank": false
          },
          {
            "id": 25584,
            "categoryId": 1,
            "categoryName": "Cargo Tank",
            "name": "NO.5 CENTER CARGO OIL TANK",
            "frameNumberFrom": 49,
            "frameNumberTo": 61,
            "shortName": "5C",
            "fullCapacityCubm": "33725.1000",
            "density": 1.3,
            "group": 1,
            "order": 3,
            "slopTank": false
          },
          {
            "id": 25596,
            "categoryId": 1,
            "categoryName": "Cargo Tank",
            "name": "SLOP TANK",
            "frameNumberFrom": 49,
            "frameNumberTo": 52,
            "shortName": "SLS",
            "fullCapacityCubm": "4117.3000",
            "density": 1.3,
            "group": 1,
            "order": 4,
            "slopTank": false
          },
          {
            "id": 25594,
            "categoryId": 1,
            "categoryName": "Cargo Tank",
            "name": "NO.5 WING CARGO OIL TANK",
            "frameNumberFrom": 52,
            "frameNumberTo": 61,
            "shortName": "5S",
            "fullCapacityCubm": "17277.4000",
            "density": 1.3,
            "group": 1,
            "order": 5,
            "slopTank": false
          }
        ],
        "allCOWTanks": [
          {
            "id": 25595,
            "categoryId": 1,
            "categoryName": "Cargo Tank",
            "name": "SLOP TANK",
            "frameNumberFrom": 49,
            "frameNumberTo": 52,
            "shortName": "SLP",
            "fullCapacityCubm": "4117.3000",
            "density": 1.3,
            "group": 1,
            "order": 1,
            "slopTank": false
          },
          {
            "id": 25593,
            "categoryId": 1,
            "categoryName": "Cargo Tank",
            "name": "NO.5 WING CARGO OIL TANK",
            "frameNumberFrom": 52,
            "frameNumberTo": 61,
            "shortName": "5P",
            "fullCapacityCubm": "17277.4000",
            "density": 1.3,
            "group": 1,
            "order": 2,
            "slopTank": false
          },
          {
            "id": 25584,
            "categoryId": 1,
            "categoryName": "Cargo Tank",
            "name": "NO.5 CENTER CARGO OIL TANK",
            "frameNumberFrom": 49,
            "frameNumberTo": 61,
            "shortName": "5C",
            "fullCapacityCubm": "33725.1000",
            "density": 1.3,
            "group": 1,
            "order": 3,
            "slopTank": false
          },
          {
            "id": 25596,
            "categoryId": 1,
            "categoryName": "Cargo Tank",
            "name": "SLOP TANK",
            "frameNumberFrom": 49,
            "frameNumberTo": 52,
            "shortName": "SLS",
            "fullCapacityCubm": "4117.3000",
            "density": 1.3,
            "group": 1,
            "order": 4,
            "slopTank": false
          },
          {
            "id": 25594,
            "categoryId": 1,
            "categoryName": "Cargo Tank",
            "name": "NO.5 WING CARGO OIL TANK",
            "frameNumberFrom": 52,
            "frameNumberTo": 61,
            "shortName": "5S",
            "fullCapacityCubm": "17277.4000",
            "density": 1.3,
            "group": 1,
            "order": 5,
            "slopTank": false
          }
        ],
        "tanksWashingWithDifferentCargo": [
          {
            "cargo": {
              "id": 17375,
              "abbreviation": "ARL",
              "colorCode": "#f91010"
            },
            "washingCargo": {
              "id": 17376,
              "abbreviation": "ARM",
              "colorCode": "#f6ef0e",
            },
            "tanks": [
              {
                "id": 25595,
                "categoryId": 1,
                "categoryName": "Cargo Tank",
                "name": "SLOP TANK",
                "frameNumberFrom": 49,
                "frameNumberTo": 52,
                "shortName": "SLP",
                "fullCapacityCubm": "4117.3000",
                "density": 1.3,
                "group": 1,
                "order": 1,
                "slopTank": false
              },
              {
                "id": 25593,
                "categoryId": 1,
                "categoryName": "Cargo Tank",
                "name": "NO.5 WING CARGO OIL TANK",
                "frameNumberFrom": 52,
                "frameNumberTo": 61,
                "shortName": "5P",
                "fullCapacityCubm": "17277.4000",
                "density": 1.3,
                "group": 1,
                "order": 2,
                "slopTank": false
              },
              {
                "id": 25584,
                "categoryId": 1,
                "categoryName": "Cargo Tank",
                "name": "NO.5 CENTER CARGO OIL TANK",
                "frameNumberFrom": 49,
                "frameNumberTo": 61,
                "shortName": "5C",
                "fullCapacityCubm": "33725.1000",
                "density": 1.3,
                "group": 1,
                "order": 3,
                "slopTank": false
              },
              {
                "id": 25596,
                "categoryId": 1,
                "categoryName": "Cargo Tank",
                "name": "SLOP TANK",
                "frameNumberFrom": 49,
                "frameNumberTo": 52,
                "shortName": "SLS",
                "fullCapacityCubm": "4117.3000",
                "density": 1.3,
                "group": 1,
                "order": 4,
                "slopTank": false
              },
              {
                "id": 25594,
                "categoryId": 1,
                "categoryName": "Cargo Tank",
                "name": "NO.5 WING CARGO OIL TANK",
                "frameNumberFrom": 52,
                "frameNumberTo": 61,
                "shortName": "5S",
                "fullCapacityCubm": "17277.4000",
                "density": 1.3,
                "group": 1,
                "order": 5,
                "slopTank": false
              }
            ],
            "selectedTanks": [
              {
                "id": 25595,
                "categoryId": 1,
                "categoryName": "Cargo Tank",
                "name": "SLOP TANK",
                "frameNumberFrom": 49,
                "frameNumberTo": 52,
                "shortName": "SLP",
                "fullCapacityCubm": "4117.3000",
                "density": 1.3,
                "group": 1,
                "order": 1,
                "slopTank": false
              },
              {
                "id": 25593,
                "categoryId": 1,
                "categoryName": "Cargo Tank",
                "name": "NO.5 WING CARGO OIL TANK",
                "frameNumberFrom": 52,
                "frameNumberTo": 61,
                "shortName": "5P",
                "fullCapacityCubm": "17277.4000",
                "density": 1.3,
                "group": 1,
                "order": 2,
                "slopTank": false
              },
              {
                "id": 25584,
                "categoryId": 1,
                "categoryName": "Cargo Tank",
                "name": "NO.5 CENTER CARGO OIL TANK",
                "frameNumberFrom": 49,
                "frameNumberTo": 61,
                "shortName": "5C",
                "fullCapacityCubm": "33725.1000",
                "density": 1.3,
                "group": 1,
                "order": 3,
                "slopTank": false
              },
              {
                "id": 25596,
                "categoryId": 1,
                "categoryName": "Cargo Tank",
                "name": "SLOP TANK",
                "frameNumberFrom": 49,
                "frameNumberTo": 52,
                "shortName": "SLS",
                "fullCapacityCubm": "4117.3000",
                "density": 1.3,
                "group": 1,
                "order": 4,
                "slopTank": false
              },
              {
                "id": 25594,
                "categoryId": 1,
                "categoryName": "Cargo Tank",
                "name": "NO.5 WING CARGO OIL TANK",
                "frameNumberFrom": 52,
                "frameNumberTo": 61,
                "shortName": "5S",
                "fullCapacityCubm": "17277.4000",
                "density": 1.3,
                "group": 1,
                "order": 5,
                "slopTank": false
              }
            ]
          },
          {
            "cargo": {
              "id": 17376,
              "abbreviation": "ARM",
              "colorCode": "#f6ef0e",
            },
            "washingCargo": {
              "id": 17375,
              "abbreviation": "ARL",
              "colorCode": "#f91010"
            },
            "tanks": [
              {
                "id": 25595,
                "categoryId": 1,
                "categoryName": "Cargo Tank",
                "name": "SLOP TANK",
                "frameNumberFrom": 49,
                "frameNumberTo": 52,
                "shortName": "SLP",
                "fullCapacityCubm": "4117.3000",
                "density": 1.3,
                "group": 1,
                "order": 1,
                "slopTank": false
              },
              {
                "id": 25593,
                "categoryId": 1,
                "categoryName": "Cargo Tank",
                "name": "NO.5 WING CARGO OIL TANK",
                "frameNumberFrom": 52,
                "frameNumberTo": 61,
                "shortName": "5P",
                "fullCapacityCubm": "17277.4000",
                "density": 1.3,
                "group": 1,
                "order": 2,
                "slopTank": false
              },
              {
                "id": 25584,
                "categoryId": 1,
                "categoryName": "Cargo Tank",
                "name": "NO.5 CENTER CARGO OIL TANK",
                "frameNumberFrom": 49,
                "frameNumberTo": 61,
                "shortName": "5C",
                "fullCapacityCubm": "33725.1000",
                "density": 1.3,
                "group": 1,
                "order": 3,
                "slopTank": false
              },
              {
                "id": 25596,
                "categoryId": 1,
                "categoryName": "Cargo Tank",
                "name": "SLOP TANK",
                "frameNumberFrom": 49,
                "frameNumberTo": 52,
                "shortName": "SLS",
                "fullCapacityCubm": "4117.3000",
                "density": 1.3,
                "group": 1,
                "order": 4,
                "slopTank": false
              },
              {
                "id": 25594,
                "categoryId": 1,
                "categoryName": "Cargo Tank",
                "name": "NO.5 WING CARGO OIL TANK",
                "frameNumberFrom": 52,
                "frameNumberTo": 61,
                "shortName": "5S",
                "fullCapacityCubm": "17277.4000",
                "density": 1.3,
                "group": 1,
                "order": 5,
                "slopTank": false
              }
            ],
            "selectedTanks": [
              {
                "id": 25595,
                "categoryId": 1,
                "categoryName": "Cargo Tank",
                "name": "SLOP TANK",
                "frameNumberFrom": 49,
                "frameNumberTo": 52,
                "shortName": "SLP",
                "fullCapacityCubm": "4117.3000",
                "density": 1.3,
                "group": 1,
                "order": 1,
                "slopTank": false
              },
              {
                "id": 25593,
                "categoryId": 1,
                "categoryName": "Cargo Tank",
                "name": "NO.5 WING CARGO OIL TANK",
                "frameNumberFrom": 52,
                "frameNumberTo": 61,
                "shortName": "5P",
                "fullCapacityCubm": "17277.4000",
                "density": 1.3,
                "group": 1,
                "order": 2,
                "slopTank": false
              },
              {
                "id": 25584,
                "categoryId": 1,
                "categoryName": "Cargo Tank",
                "name": "NO.5 CENTER CARGO OIL TANK",
                "frameNumberFrom": 49,
                "frameNumberTo": 61,
                "shortName": "5C",
                "fullCapacityCubm": "33725.1000",
                "density": 1.3,
                "group": 1,
                "order": 3,
                "slopTank": false
              },
              {
                "id": 25596,
                "categoryId": 1,
                "categoryName": "Cargo Tank",
                "name": "SLOP TANK",
                "frameNumberFrom": 49,
                "frameNumberTo": 52,
                "shortName": "SLS",
                "fullCapacityCubm": "4117.3000",
                "density": 1.3,
                "group": 1,
                "order": 4,
                "slopTank": false
              },
              {
                "id": 25594,
                "categoryId": 1,
                "categoryName": "Cargo Tank",
                "name": "NO.5 WING CARGO OIL TANK",
                "frameNumberFrom": 52,
                "frameNumberTo": 61,
                "shortName": "5S",
                "fullCapacityCubm": "17277.4000",
                "density": 1.3,
                "group": 1,
                "order": 5,
                "slopTank": false
              }
            ]
          }
        ],
        "cowStart": "01:00",
        "cowEnd": "01:00",
        "cowDuration": "12:00",
        "totalDuration": "8:00",
        "cowTrimMin": 1,
        "cowTrimMax": 1,
        "needFreshCrudeStorage": true,
        "needFlushingOil": true,
      },
      "postDischargeStageTime": {
        "dryCheckTime": "00:00",
        "slopDischargingTime": "00:00",
        "finalStrippingTime": "00:00",
        "freshOilWashingTime": "00:00",
      },
      "loadedCargos": [
        {
          "id": 17376,
          "abbreviation": "ARM",
          "colorCode": "#f6ef0e",
        },
        {
          "id": 17375,
          "abbreviation": "ARL",
          "colorCode": "#f91010"
        }
      ],
      "isDischargeInstructionsComplete": null,
      "isDischargeSequenceGenerated": null,
      "isDischargePlanGenerated": null
    };
    return of(response);
  }

  /**
   * Method to download template
   *
   * @param {number} id
   * @return {*}  {Observable<any>}
   * @memberof LoadingDischargingInformationApiService
 */
  downloadTemplate(id: number, operation: OPERATIONS): Observable<any> {
    if (operation === OPERATIONS.LOADING) {
      return this.commonApiService.get<any>(`loading/download/port-tide-template?loadingId=${id}`, { responseType: 'blob' as 'json' });
    }
  }

  /**
  * Method to upload template
  *
  * @param {number} loadingId
  * @param {*} file
  * @return {*}  {Observable<IResponse>}
  * @memberof LoadingDischargingInformationApiService
  */
  uploadTemplate(loadingId: number, file: any, operation: OPERATIONS): Observable<IResponse> {
    const formData: FormData = new FormData();
    formData.append('file', file);
    if (operation === OPERATIONS.LOADING) {
      return this.commonApiService.postFormData<any>(`loading/${loadingId}/upload/port-tide-details`, formData);
    }
  }
}

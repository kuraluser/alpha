import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
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
      "dischargingInfoId": 136,
      "synopticTableId": 89426,
      "isDischargingInfoComplete": false,
      "dischargingDetails": {
        "timeOfSunrise": "05:39",
        "timeOfSunset": "06:40",
        "startTime": "20:13",
        "trimAllowed": {
          "initialTrim": 1.0000,
          "maximumTrim": 1.0000,
          "finalTrim": 1.0000
        }
      },
      "dischargingRates": {
        "maxDischargingRate": 20500.0000,
        "minDeBallastingRate": 2500.0000,
        "maxDeBallastingRate": 6000.0000,
        "minDischargingRate": 1000.0000,
        "id": 136
      },
      "berthDetails": {
        "availableBerths": [
          {
            "berthId": 20879,
            "portId": 504,
            "dischargingInfoId": null,
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
            "lineDisplacement": ""
          }
        ],
        "selectedBerths": [
          {
            "berthId": 20879,
            "portId": null,
            "dischargingInfoId": 136,
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
            "lineDisplacement": "1000.0000"
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
            "dischargingInfoId": 136,
            "machineId": 2,
            "capacity": 0.0000,
            "isUsing": true,
            "machineTypeId": 1
          },
          {
            "id": 90,
            "dischargingInfoId": 136,
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
      "dischargingStages": {
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
      "dischargingSequences": {
        "reasonForDelays": [
          {
            "id": 1,
            "reason": "test"
          }
        ],
        "dischargingDelays": [
          {
            "id": 101,
            "dischargingInfoId": 136,
            "reasonForDelayId": 1,
            "duration": 671.0000,
            "cargoId": 0,
            "quantity": null
          },
          {
            "id": 102,
            "dischargingInfoId": 136,
            "reasonForDelayId": 1,
            "duration": 671.0000,
            "cargoId": 252,
            "quantity": 2103234.3544
          }
        ]
      },
      "toppingOffSequence": [
        {
          "id": 770,
          "dischargingInfoId": 136,
          "orderNumber": 1,
          "tankId": 25596,
          "cargoId": 252,
          "shortName": null,
          "cargoName": "",
          "cargoAbbreviation": "",
          "colourCode": "",
          "remark": "",
          "ullage": 1.3490,
          "quantity": 3240.0000,
          "fillingRatio": 98.0000,
          "api": 40.1600,
          "temperature": 106.5000,
          "displayOrder": 1
        },
        {
          "id": 771,
          "dischargingInfoId": 136,
          "orderNumber": 8,
          "tankId": 25581,
          "cargoId": 252,
          "shortName": null,
          "cargoName": "",
          "cargoAbbreviation": "",
          "colourCode": "",
          "remark": "",
          "ullage": 1.2447,
          "quantity": 22192.9000,
          "fillingRatio": 98.0000,
          "api": 40.1600,
          "temperature": 106.5000,
          "displayOrder": 2
        },
        {
          "id": 772,
          "dischargingInfoId": 136,
          "orderNumber": 9,
          "tankId": 25585,
          "cargoId": 252,
          "shortName": null,
          "cargoName": "",
          "cargoAbbreviation": "",
          "colourCode": "",
          "remark": "",
          "ullage": 3.9319,
          "quantity": 14658.1000,
          "fillingRatio": 87.8000,
          "api": 40.1600,
          "temperature": 106.5000,
          "displayOrder": 3
        },
        {
          "id": 773,
          "dischargingInfoId": 136,
          "orderNumber": 9,
          "tankId": 25586,
          "cargoId": 252,
          "shortName": null,
          "cargoName": "",
          "cargoAbbreviation": "",
          "colourCode": "",
          "remark": "",
          "ullage": 3.3938,
          "quantity": 15008.9000,
          "fillingRatio": 89.9000,
          "api": 40.1600,
          "temperature": 106.5000,
          "displayOrder": 4
        },
        {
          "id": 774,
          "dischargingInfoId": 136,
          "orderNumber": 7,
          "tankId": 25587,
          "cargoId": 252,
          "shortName": null,
          "cargoName": "",
          "cargoAbbreviation": "",
          "colourCode": "",
          "remark": "",
          "ullage": 1.7727,
          "quantity": 15719.8000,
          "fillingRatio": 96.5000,
          "api": 40.1600,
          "temperature": 106.5000,
          "displayOrder": 5
        },
        {
          "id": 775,
          "dischargingInfoId": 136,
          "orderNumber": 7,
          "tankId": 25588,
          "cargoId": 252,
          "shortName": null,
          "cargoName": "",
          "cargoAbbreviation": "",
          "colourCode": "",
          "remark": "",
          "ullage": 2.3524,
          "quantity": 15353.8000,
          "fillingRatio": 94.2000,
          "api": 40.1600,
          "temperature": 106.5000,
          "displayOrder": 6
        },
        {
          "id": 776,
          "dischargingInfoId": 136,
          "orderNumber": 5,
          "tankId": 25591,
          "cargoId": 252,
          "shortName": null,
          "cargoName": "",
          "cargoAbbreviation": "",
          "colourCode": "",
          "remark": "",
          "ullage": 1.3861,
          "quantity": 15967.6000,
          "fillingRatio": 98.0000,
          "api": 40.1600,
          "temperature": 106.5000,
          "displayOrder": 7
        },
        {
          "id": 777,
          "dischargingInfoId": 136,
          "orderNumber": 5,
          "tankId": 25592,
          "cargoId": 252,
          "shortName": null,
          "cargoName": "",
          "cargoAbbreviation": "",
          "colourCode": "",
          "remark": "",
          "ullage": 1.3861,
          "quantity": 15967.6000,
          "fillingRatio": 98.0000,
          "api": 40.1600,
          "temperature": 106.5000,
          "displayOrder": 8
        },
        {
          "id": 778,
          "dischargingInfoId": 136,
          "orderNumber": 3,
          "tankId": 25593,
          "cargoId": 252,
          "shortName": null,
          "cargoName": "",
          "cargoAbbreviation": "",
          "colourCode": "",
          "remark": "",
          "ullage": 1.4505,
          "quantity": 13596.2000,
          "fillingRatio": 98.0000,
          "api": 40.1600,
          "temperature": 106.5000,
          "displayOrder": 9
        },
        {
          "id": 779,
          "dischargingInfoId": 136,
          "orderNumber": 10,
          "tankId": 25580,
          "cargoId": 252,
          "shortName": null,
          "cargoName": "",
          "cargoAbbreviation": "",
          "colourCode": "",
          "remark": "",
          "ullage": 1.2358,
          "quantity": 23788.8000,
          "fillingRatio": 98.0000,
          "api": 40.1600,
          "temperature": 106.5000,
          "displayOrder": 10
        },
        {
          "id": 780,
          "dischargingInfoId": 136,
          "orderNumber": 12,
          "tankId": 25582,
          "cargoId": 252,
          "shortName": null,
          "cargoName": "",
          "cargoAbbreviation": "",
          "colourCode": "",
          "remark": "",
          "ullage": 1.2088,
          "quantity": 22192.9000,
          "fillingRatio": 98.0000,
          "api": 40.1600,
          "temperature": 106.5000,
          "displayOrder": 11
        },
        {
          "id": 781,
          "dischargingInfoId": 136,
          "orderNumber": 6,
          "tankId": 25583,
          "cargoId": 252,
          "shortName": null,
          "cargoName": "",
          "cargoAbbreviation": "",
          "colourCode": "",
          "remark": "",
          "ullage": 1.2500,
          "quantity": 22192.7000,
          "fillingRatio": 98.0000,
          "api": 40.1600,
          "temperature": 106.5000,
          "displayOrder": 12
        },
        {
          "id": 782,
          "dischargingInfoId": 136,
          "orderNumber": 4,
          "tankId": 25584,
          "cargoId": 252,
          "shortName": null,
          "cargoName": "",
          "cargoAbbreviation": "",
          "colourCode": "",
          "remark": "",
          "ullage": 1.2659,
          "quantity": 26403.6000,
          "fillingRatio": 97.5000,
          "api": 40.1600,
          "temperature": 106.5000,
          "displayOrder": 13
        },
        {
          "id": 783,
          "dischargingInfoId": 136,
          "orderNumber": 11,
          "tankId": 25589,
          "cargoId": 252,
          "shortName": null,
          "cargoName": "",
          "cargoAbbreviation": "",
          "colourCode": "",
          "remark": "",
          "ullage": 1.4650,
          "quantity": 15913.4000,
          "fillingRatio": 97.7000,
          "api": 40.1600,
          "temperature": 106.5000,
          "displayOrder": 14
        },
        {
          "id": 784,
          "dischargingInfoId": 136,
          "orderNumber": 11,
          "tankId": 25590,
          "cargoId": 252,
          "shortName": null,
          "cargoName": "",
          "cargoAbbreviation": "",
          "colourCode": "",
          "remark": "",
          "ullage": 1.3792,
          "quantity": 15967.6000,
          "fillingRatio": 98.0000,
          "api": 40.1600,
          "temperature": 106.5000,
          "displayOrder": 15
        },
        {
          "id": 785,
          "dischargingInfoId": 136,
          "orderNumber": 3,
          "tankId": 25594,
          "cargoId": 252,
          "shortName": null,
          "cargoName": "",
          "cargoAbbreviation": "",
          "colourCode": "",
          "remark": "",
          "ullage": 1.4505,
          "quantity": 13596.2000,
          "fillingRatio": 98.0000,
          "api": 40.1600,
          "temperature": 106.5000,
          "displayOrder": 16
        },
        {
          "id": 786,
          "dischargingInfoId": 136,
          "orderNumber": 2,
          "tankId": 25595,
          "cargoId": 252,
          "shortName": null,
          "cargoName": "",
          "cargoAbbreviation": "",
          "colourCode": "",
          "remark": "",
          "ullage": 1.3490,
          "quantity": 3240.0000,
          "fillingRatio": 98.0000,
          "api": 40.1600,
          "temperature": 106.5000,
          "displayOrder": 17
        }
      ],
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
            "orderBblsdbs": "",
            "orderBbls60f": "",
            "minTolerence": "-10.0",
            "maxTolerence": "10.0",
            "loadableBblsdbs": "",
            "loadableBbls60f": "",
            "loadableLT": "",
            "loadableMT": "127108.7",
            "loadableKL": "",
            "differencePercentage": "7.06",
            "differenceColor": "",
            "cargoId": 33,
            "orderedQuantity": "",
            "cargoAbbreviation": "ARM",
            "colorCode": "#f6ef0e",
            "cargoNominationId": 17375,
            "timeRequiredForLoading": "8.7",
            "orderQuantity": "118729.5000",
            "maxLoadingRate": "20500.0000"
          },
          {
            "id": 9253,
            "grade": "",
            "estimatedAPI": "33.0000",
            "estimatedTemp": "120.0000",
            "orderBblsdbs": "",
            "orderBbls60f": "",
            "minTolerence": "-10.0",
            "maxTolerence": "10.0",
            "loadableBblsdbs": "",
            "loadableBbls60f": "",
            "loadableLT": "",
            "loadableMT": "94952.3",
            "loadableKL": "",
            "differencePercentage": "7.06",
            "differenceColor": "",
            "cargoId": 32,
            "orderedQuantity": "",
            "cargoAbbreviation": "ARL",
            "colorCode": "#f91010",
            "cargoNominationId": 17376,
            "slopQuantity": 1308.7,
            "timeRequiredForLoading": "7.05",
            "orderQuantity": "88692.9000",
            "maxLoadingRate": "20500.0000"
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
      }
    };
    return of(response);
  }
}

import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';

import { AdminModule } from '../admin.module';
import { CommonApiService } from '../../../shared/services/common/common-api.service';

import { IVesselListResponse } from '../models/vessel-info.model';

/**
 * Service for Vessel information module
 *
 * @export
 * @class VesselInformationApiService
 */
@Injectable({
  providedIn: AdminModule
})
export class VesselInformationApiService {

  constructor(
    private commonApiService: CommonApiService
  ) {}

  /**
   * API to get Vessel list
   *
   * @return {*}  {Observable<any>}
   * @memberof VesselInformationApiService
   */
  getVesselList(): Observable<IVesselListResponse> {

    // mock json will replace once actual API available
    const vesselList: IVesselListResponse = {
      "responseStatus": {
        "status": "200"
      },
      "vesselList": [
        {
          "vesselId": 1,
          "vesselName": "Kazusa",
          "vesselType": "Cargo oil tanker",
          "builder": "Mitsui engineering & shipbuilding",
          "dateOfLaunch": "02-07-2021"
        },
        {
          "vesselId": 2,
          "vesselName": "Atlantic Pioneer",
          "vesselType": "Cargo oil tanker",
          "builder": "Mitsui engineering & shipbuilding",
          "dateOfLaunch": "10-07-2021"
        },
        {
          "vesselId": 3,
          "vesselName": "Shizukisan",
          "vesselType": "Cargo oil tanker",
          "builder": "Mitsui engineering & shipbuilding",
          "dateOfLaunch": "12-07-2021"
        },
        {
          "vesselId": 4,
          "vesselName": "Mitsui",
          "vesselType": "Cargo oil tanker",
          "builder": "Mitsui engineering & shipbuilding",
          "dateOfLaunch": "15-07-2021"
        }
      ]
    };

    return of(vesselList);
  }
  
  getVesselDetails(vesselId: number): Observable<any> {

    // mock json will replace once actual API available
    const vesselDetails = {
      "responseStatus": {
        "status": "200"
      },
      "vesselDetails": {
        "vesselId": 1,
        "vesselName": "kazusa",
        "vesselImageUrl": "assets/images/vessels/kazusa/kazusa.jpg",
        "countryFlagUrl": "assets/images/flags/japan.png",
        "imoNumber": 9339997,
        "generalInfo": {
          "vesselType": "Cargo oil tanker",
          "builder": "Mitsui engineering & shipbuilding",
          "officialNumber": 14008,
          "signalLetter": "7JFB",
          "dateOfKeelLaid": "27-12-2014",
          "dateOfLaunch": "07-02-2019",
          "dateOfDelivery": "10-07-2019",
          "navigationArea": "Ocean going",
          "class": "NS*MNS*"
        },
        "vesselDimesnsions": {
          "registerLength": 333,
          "lengthOverall": 333,
          "draftFullLoad": 11.610,
          "breadthMoulded": 80,
          "lengthBetweenPerpendiculars": 324,
          "depthMoulded": 28,
          "designedLoadDraft": 10.020
        },
        "draftDisplacementDeadweight": {
          "depthMoulded": 20,
          "thicknessOfUpperDeck": 0.015,
          "thicknessOfKeelPlate": 0.023,
          "totalDepth": 14.24
        }
      },
      "bunkerRearTanks": [
        [
          {
            "id": 25636,
            "categoryId": 3,
            "categoryName": "Fresh Water Tank",
            "name": "DRINKING WATER TANK",
            "frameNumberFrom": "5",
            "frameNumberTo": "13",
            "shortName": "DWP",
            "fullCapacityCubm": "369.4000",
            "density": 1.0000,
            "group": 1,
            "order": 1,
            "slopTank": false
          },
          {
            "id": 25638,
            "categoryId": 3,
            "categoryName": "Fresh Water Tank",
            "name": "DIST.WATER TANK",
            "frameNumberFrom": "13",
            "frameNumberTo": "15",
            "shortName": "DSWP",
            "fullCapacityCubm": "117.0000",
            "density": 1.0000,
            "group": 1,
            "order": 2,
            "slopTank": false
          },
          {
            "id": 26003,
            "categoryId": 23,
            "categoryName": "Fresh Tank Void",
            "name": "APT",
            "frameNumberFrom": "0",
            "frameNumberTo": "15",
            "shortName": "APT1",
            "group": 1,
            "order": 3,
            "slopTank": false
          },
          {
            "id": 25640,
            "categoryId": 3,
            "categoryName": "Fresh Water Tank",
            "name": "COOLING WATER TANK",
            "frameNumberFrom": "10",
            "frameNumberTo": "15",
            "shortName": "FWC",
            "fullCapacityCubm": "29.5000",
            "density": 1.0000,
            "group": 1,
            "order": 3,
            "slopTank": false
          },
          {
            "id": 25639,
            "categoryId": 3,
            "categoryName": "Fresh Water Tank",
            "name": "DIST.WATER TANK",
            "frameNumberFrom": "13",
            "frameNumberTo": "15",
            "shortName": "DSWS",
            "fullCapacityCubm": "117.0000",
            "density": 1.0000,
            "group": 1,
            "order": 4,
            "slopTank": false
          },
          {
            "id": 25637,
            "categoryId": 3,
            "categoryName": "Fresh Water Tank",
            "name": "FRESH WATER TANK",
            "frameNumberFrom": "5",
            "frameNumberTo": "13",
            "shortName": "FWS",
            "fullCapacityCubm": "369.4000",
            "density": 1.0000,
            "group": 1,
            "order": 5,
            "slopTank": false
          }
        ]
      ],
      "bunkerTanks": [
        [
          {
            "id": 25614,
            "categoryId": 5,
            "categoryName": "Fuel Oil Tank",
            "name": "NO.2  FUEL OIL TANK",
            "frameNumberFrom": "15",
            "frameNumberTo": "39",
            "shortName": "FO2P",
            "fullCapacityCubm": "1852.0000",
            "density": 0.9800,
            "group": 2,
            "order": 1,
            "slopTank": false
          },
          {
            "id": 25612,
            "categoryId": 5,
            "categoryName": "Fuel Oil Tank",
            "name": "NO.1  FUEL OIL TANK",
            "frameNumberFrom": "39",
            "frameNumberTo": "49",
            "shortName": "FO1P",
            "fullCapacityCubm": "2490.5000",
            "density": 0.9800,
            "group": 2,
            "order": 2,
            "slopTank": false
          },
          {
            "id": 26004,
            "categoryId": 22,
            "categoryName": "Fuel Void",
            "name": "FUEL VOID 1",
            "frameNumberFrom": "15",
            "frameNumberTo": "49",
            "shortName": "VOID1",
            "group": 2,
            "order": 3,
            "slopTank": false
          },
          {
            "id": 26005,
            "categoryId": 22,
            "categoryName": "Fuel Void",
            "name": "FUEL VOID 2",
            "frameNumberFrom": "15",
            "frameNumberTo": "43",
            "shortName": "VOID2",
            "group": 2,
            "order": 4,
            "slopTank": false
          },
          {
            "id": 25616,
            "categoryId": 5,
            "categoryName": "Fuel Oil Tank",
            "name": "H.F.O.SERV. TANK",
            "frameNumberFrom": "43",
            "frameNumberTo": "47",
            "shortName": "FOSV",
            "fullCapacityCubm": "66.8000",
            "density": 0.9800,
            "group": 2,
            "order": 5,
            "slopTank": false
          },
          {
            "id": 25625,
            "categoryId": 6,
            "categoryName": "Diesel Oil Tank",
            "name": "NO.2 D.O.SERV.TANK",
            "frameNumberFrom": "22",
            "frameNumberTo": "24",
            "shortName": "DOSV2",
            "fullCapacityCubm": "27.4000",
            "density": 0.8500,
            "group": 2,
            "order": 6,
            "slopTank": false
          },
          {
            "id": 25624,
            "categoryId": 6,
            "categoryName": "Diesel Oil Tank",
            "name": "NO.1 D.O. SERV.TANK",
            "frameNumberFrom": "24",
            "frameNumberTo": "26",
            "shortName": "DOSV1",
            "fullCapacityCubm": "32.4000",
            "density": 0.8500,
            "group": 2,
            "order": 7,
            "slopTank": false
          },
          {
            "id": 25617,
            "categoryId": 5,
            "categoryName": "Fuel Oil Tank",
            "name": "H.F.O.SETT. TANK",
            "frameNumberFrom": "39",
            "frameNumberTo": "43",
            "shortName": "FOST",
            "fullCapacityCubm": "64.1000",
            "density": 0.9800,
            "group": 2,
            "order": 8,
            "slopTank": false
          },
          {
            "id": 25620,
            "categoryId": 5,
            "categoryName": "Fuel Oil Tank",
            "name": "F.O.SLUDGE TANK",
            "frameNumberFrom": "38",
            "frameNumberTo": "42",
            "shortName": "FOS",
            "fullCapacityCubm": "5.5000",
            "density": 0.9800,
            "group": 2,
            "order": 9,
            "slopTank": false
          },
          {
            "id": 25623,
            "categoryId": 6,
            "categoryName": "Diesel Oil Tank",
            "name": "NO.2 DIESEL OIL TANK",
            "frameNumberFrom": "15",
            "frameNumberTo": "22",
            "shortName": "DO2S",
            "fullCapacityCubm": "278.1000",
            "density": 0.8500,
            "group": 2,
            "order": 9,
            "slopTank": false
          },
          {
            "id": 25622,
            "categoryId": 6,
            "categoryName": "Diesel Oil Tank",
            "name": "NO.1 DIESEL OIL TANK",
            "frameNumberFrom": "22",
            "frameNumberTo": "26",
            "shortName": "DO1S",
            "fullCapacityCubm": "175.2000",
            "density": 0.8500,
            "group": 2,
            "order": 10,
            "slopTank": false
          },
          {
            "id": 25619,
            "categoryId": 5,
            "categoryName": "Fuel Oil Tank",
            "name": "BOILER F.O.SERV.TANK",
            "frameNumberFrom": "34",
            "frameNumberTo": "39",
            "shortName": "BFOSV",
            "fullCapacityCubm": "67.2000",
            "density": 0.9800,
            "group": 2,
            "order": 11,
            "slopTank": false
          },
          {
            "id": 25615,
            "categoryId": 5,
            "categoryName": "Fuel Oil Tank",
            "name": "NO.2  FUEL OIL TANK",
            "frameNumberFrom": "26",
            "frameNumberTo": "39",
            "shortName": "FO2S",
            "fullCapacityCubm": "1259.2000",
            "density": 0.9800,
            "group": 2,
            "order": 12,
            "slopTank": false
          },
          {
            "id": 25613,
            "categoryId": 5,
            "categoryName": "Fuel Oil Tank",
            "name": "NO.1  FUEL OIL TANK",
            "frameNumberFrom": "39",
            "frameNumberTo": "49",
            "shortName": "FO1S",
            "fullCapacityCubm": "2154.3000",
            "density": 0.9800,
            "group": 2,
            "order": 13,
            "slopTank": false
          }
        ]
      ],
      "ballastFrontTanks": [
        [
          {
            "id": 25611,
            "categoryId": 2,
            "categoryName": "Water Ballast Tank",
            "name": "FORE PEAK TANK(PORT USE)",
            "frameNumberFrom": "103",
            "frameNumberTo": "FE",
            "shortName": "UFPT",
            "fullCapacityCubm": "5156.5000",
            "density": 1.0250,
            "group": 0,
            "order": 1,
            "slopTank": false
          },
          {
            "id": 25597,
            "categoryId": 2,
            "categoryName": "Water Ballast Tank",
            "name": "FORE PEAK TANK",
            "frameNumberFrom": "103",
            "frameNumberTo": "FE",
            "shortName": "LFPT",
            "fullCapacityCubm": "5444.2000",
            "density": 1.0250,
            "group": 0,
            "order": 2,
            "slopTank": false
          }
        ]
      ],
      "ballastCenterTanks": [
        [
          {
            "id": 25608,
            "categoryId": 2,
            "categoryName": "Water Ballast Tank",
            "name": "AFT WATER BALLAST TANK",
            "frameNumberFrom": "15",
            "frameNumberTo": "39",
            "shortName": "AWBP",
            "fullCapacityCubm": "1024.9000",
            "density": 1.0250,
            "group": 0,
            "order": 1,
            "slopTank": false
          },
          {
            "id": 26006,
            "categoryId": 16,
            "categoryName": "Ballast Void",
            "name": "VOID",
            "frameNumberFrom": "39",
            "frameNumberTo": "49",
            "shortName": "VOID3",
            "group": 0,
            "order": 2,
            "slopTank": false
          },
          {
            "id": 25606,
            "categoryId": 2,
            "categoryName": "Water Ballast Tank",
            "name": "NO.5 WATER BALLAST TANK",
            "frameNumberFrom": "49",
            "frameNumberTo": "61",
            "shortName": "WB5P",
            "fullCapacityCubm": "8561.9000",
            "density": 1.0250,
            "group": 0,
            "order": 3,
            "slopTank": false
          },
          {
            "id": 25604,
            "categoryId": 2,
            "categoryName": "Water Ballast Tank",
            "name": "NO.4 WATER BALLAST TANK",
            "frameNumberFrom": "61",
            "frameNumberTo": "71",
            "shortName": "WB4P",
            "fullCapacityCubm": "8743.7000",
            "density": 1.0250,
            "group": 0,
            "order": 4,
            "slopTank": false
          },
          {
            "id": 25602,
            "categoryId": 2,
            "categoryName": "Water Ballast Tank",
            "name": "NO.3 WATER BALLAST TANK",
            "frameNumberFrom": "71",
            "frameNumberTo": "81",
            "shortName": "WB3P",
            "fullCapacityCubm": "8875.8000",
            "density": 1.0250,
            "group": 0,
            "order": 5,
            "slopTank": false
          },
          {
            "id": 25600,
            "categoryId": 2,
            "categoryName": "Water Ballast Tank",
            "name": "NO.2 WATER BALLAST TANK",
            "frameNumberFrom": "81",
            "frameNumberTo": "91",
            "shortName": "WB2P",
            "fullCapacityCubm": "8873.6000",
            "density": 1.0250,
            "group": 0,
            "order": 6,
            "slopTank": false
          },
          {
            "id": 25598,
            "categoryId": 2,
            "categoryName": "Water Ballast Tank",
            "name": "NO.1 WATER BALLAST TANK",
            "frameNumberFrom": "91",
            "frameNumberTo": "103",
            "shortName": "WB1P",
            "fullCapacityCubm": "8906.5000",
            "density": 1.0250,
            "group": 0,
            "order": 7,
            "slopTank": false
          },
          {
            "id": 25609,
            "categoryId": 2,
            "categoryName": "Water Ballast Tank",
            "name": "AFT WATER BALLAST TANK",
            "frameNumberFrom": "15",
            "frameNumberTo": "39",
            "shortName": "AWBS",
            "fullCapacityCubm": "1024.9000",
            "density": 1.0250,
            "group": 0,
            "order": 8,
            "slopTank": false
          },
          {
            "id": 26007,
            "categoryId": 16,
            "categoryName": "Ballast Void",
            "name": "VOID",
            "frameNumberFrom": "39",
            "frameNumberTo": "49",
            "shortName": "VOID4",
            "group": 0,
            "order": 9,
            "slopTank": false
          },
          {
            "id": 25607,
            "categoryId": 2,
            "categoryName": "Water Ballast Tank",
            "name": "NO.5 WATER BALLAST TANK",
            "frameNumberFrom": "49",
            "frameNumberTo": "61",
            "shortName": "WB5S",
            "fullCapacityCubm": "8560.4000",
            "density": 1.0250,
            "group": 0,
            "order": 10,
            "slopTank": false
          },
          {
            "id": 25605,
            "categoryId": 2,
            "categoryName": "Water Ballast Tank",
            "name": "NO.4 WATER BALLAST TANK",
            "frameNumberFrom": "61",
            "frameNumberTo": "71",
            "shortName": "WB4S",
            "fullCapacityCubm": "8741.7000",
            "density": 1.0250,
            "group": 0,
            "order": 11,
            "slopTank": false
          },
          {
            "id": 25603,
            "categoryId": 2,
            "categoryName": "Water Ballast Tank",
            "name": "NO.3 WATER BALLAST TANK",
            "frameNumberFrom": "71",
            "frameNumberTo": "81",
            "shortName": "WB3S",
            "fullCapacityCubm": "8873.8000",
            "density": 1.0250,
            "group": 0,
            "order": 12,
            "slopTank": false
          },
          {
            "id": 25601,
            "categoryId": 2,
            "categoryName": "Water Ballast Tank",
            "name": "NO.2 WATER BALLAST TANK",
            "frameNumberFrom": "81",
            "frameNumberTo": "91",
            "shortName": "WB2S",
            "fullCapacityCubm": "8871.5000",
            "density": 1.0250,
            "group": 0,
            "order": 13,
            "slopTank": false
          },
          {
            "id": 25599,
            "categoryId": 2,
            "categoryName": "Water Ballast Tank",
            "name": "NO.1 WATER BALLAST TANK",
            "frameNumberFrom": "91",
            "frameNumberTo": "103",
            "shortName": "WB1S",
            "fullCapacityCubm": "8904.4000",
            "density": 1.0250,
            "group": 0,
            "order": 14,
            "slopTank": false
          }
        ]
      ],
      "ballastRearTanks": [
        [
          {
            "id": 25610,
            "categoryId": 2,
            "categoryName": "Water Ballast Tank",
            "name": "AFT PEAK TANK",
            "frameNumberFrom": "AE",
            "frameNumberTo": "15",
            "shortName": "APT",
            "fullCapacityCubm": "2574.4000",
            "density": 1.0250,
            "group": 0,
            "order": 1,
            "slopTank": false
          }
        ]
      ],
      "cargoTanks": [
        [
          {
            "id": 25595,
            "categoryId": 1,
            "categoryName": "Cargo Tank",
            "name": "SLOP TANK",
            "frameNumberFrom": "49",
            "frameNumberTo": "52",
            "shortName": "SLP",
            "fullCapacityCubm": "4117.3000",
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
            "fullCapacityCubm": "17277.4000",
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
            "fullCapacityCubm": "33725.1000",
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
            "fullCapacityCubm": "4117.3000",
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
            "fullCapacityCubm": "17277.4000",
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
            "fullCapacityCubm": "20290.8000",
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
            "fullCapacityCubm": "28201.6000",
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
            "fullCapacityCubm": "20290.8000",
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
            "fullCapacityCubm": "20290.8000",
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
            "fullCapacityCubm": "28201.6000",
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
            "fullCapacityCubm": "20290.8000",
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
            "fullCapacityCubm": "20290.8000",
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
            "fullCapacityCubm": "28201.6000",
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
            "fullCapacityCubm": "20290.8000",
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
            "fullCapacityCubm": "20797.7000",
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
            "fullCapacityCubm": "30229.5000",
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
            "fullCapacityCubm": "20797.7000",
            "density": 1.3000,
            "group": 5,
            "order": 3,
            "slopTank": false
          }
        ]
      ]
    };
    return of(vesselDetails);
  }
}

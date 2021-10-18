import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { TranslateService } from '@ngx-translate/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';

import { AppConfigurationService } from '../../../../../shared/services/app-configuration/app-configuration.service';
import { LoadingDischargingTransformationService } from '../../../services/loading-discharging-transformation.service';
import { DischargingPlanApiService } from './../../../services/discharging-plan-api.service';

import { IAlgoError, IAlgoResponse, ICargo, ILoadableQuantityCargo, OPERATIONS } from '../../../../core/models/common.model';
import { QUANTITY_UNIT } from '../../../../../shared/models/common.model';
import { IDischargeOperationListData, IDischargingInformation, IDischargingPlanDetailsResponse, ULLAGE_STATUS_VALUE } from '../../../models/loading-discharging.model';
import { LoadingPlanApiService } from '../../../services/loading-plan-api.service';
import { LoadingApiService } from '../../../services/loading-api.service';

/**
 * below dummy data will remove once the actual API implemented
 */
const mockJson = {
  "responseStatus": {
    "status": "200"
  },
  "currentPortCargos": [
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
      "slopQuantity": 3301,
      "timeRequiredForDischarging": "1.00",
      "blFigure": "118729.5000",
      "maxDischargingRate": "20500.0000",
      "protested": true,
      "isCommingledDischarge": true,
      "isCommingledCargo": false
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
  "planStowageDetails": [
    {
      "tankId": 25580,
      "tankName": null,
      "cargoNominationId": 17759,
      "quantityMT": "20.0000",
      "quantityM3": "20.0000",
      "api": "",
      "temperature": "12.0000",
      "ullage": "1.0000",
      "conditionType": 1,
      "valueType": 1,
      "colorCode": null,
      "abbreviation": null,
      "cargoId": null
    }
  ],
  "planBallastDetails": [
    {
      "tankId": 25580,
      "tankName": null,
      "quantityMT": "20.0000",
      "quantityM3": "",
      "sounding": "",
      "conditionType": 1,
      "valueType": 0,
      "colorCode": null,
      "sg": null
    }
  ],
  "planRobDetails": [
    {
      "tankId": 25580,
      "tankName": null,
      "quantityMT": "10.0000",
      "quantityM3": "10.0000",
      "conditionType": 1,
      "valueType": 1,
      "colorCode": null,
      "density": null
    }
  ],
  "planStabilityParams": [
    {
      "foreDraft": "1.0000",
      "meanDraft": "1.0000",
      "aftDraft": "1.0000",
      "trim": "1.0000",
      "bm": "1.0000",
      "sf": "1.0000",
      "conditionType": 1,
      "valueType": 1
    }
  ],
  "dischargingInformation": {
    "dischargeInfoId": 136,
    "dischargeStudyName": "DS1",
    "dischargeStudyId": null,
    "synopticTableId": 89426,
    "dischargeDetails": {
      "timeOfSunrise": "12:23",
      "timeOfSunset": "12:23",
      "startTime": "",
      "trimAllowed": {
        "initialTrim": null,
        "maximumTrim": null,
        "finalTrim": null
      }
    },
    "dischargeRates": {
      "maxDischargingRate": 16200.0000,
      "minBallastingRate": 5100.0000,
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
        },
        {
          "id": 5,
          "name": "Stripping Pump"
        },
        {
          "id": 6,
          "name": "Strip Eductor"
        },
        {
          "id": 7,
          "name": "COW Pump"
        },
        {
          "id": 8,
          "name": "Ballast Eductor"
        },
        {
          "id": 9,
          "name": "Tank Cleaning Pump"
        }
      ],
      "vesselPumps": [
        {
          "id": 4,
          "vesselId": 1,
          "pumpTypeId": 2,
          "pumpName": "BP1",
          "pumpCode": "BP1",
          "pumpCapacity": 3500,
          "machineType": 1
        },
        {
          "id": 5,
          "vesselId": 1,
          "pumpTypeId": 2,
          "pumpName": "BP2",
          "pumpCode": "BP2",
          "pumpCapacity": 3500,
          "machineType": 1
        },
        {
          "id": 10,
          "vesselId": 1,
          "pumpTypeId": 8,
          "pumpName": "Ballast Eductor 1",
          "pumpCode": "BED1",
          "pumpCapacity": 400,
          "machineType": 1
        },
        {
          "id": 11,
          "vesselId": 1,
          "pumpTypeId": 8,
          "pumpName": "Ballast Eductor 2",
          "pumpCode": "BED2",
          "pumpCapacity": 400,
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
      "dischargeMachinesInUses": [
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
        {
          "id": 2,
          "reason": "Reason 2"
        },
        {
          "id": 1,
          "reason": "Tank Switching"
        },
        {
          "id": 3,
          "reason": "Reason 3"
        }
      ],
      "dischargingDelays": [
        {
          "id": 197,
          "dischargeInfoId": 136,
          "reasonForDelayIds": [
            2
          ],
          "duration": 0,
          "cargoId": 0,
          "cargoNominationId": 0,
          "quantity": null
        },
        {
          "id": 199,
          "dischargeInfoId": 136,
          "reasonForDelayIds": [
            2
          ],
          "duration": 60,
          "cargoId": 32,
          "cargoNominationId": 17760,
          "quantity": 577108,
          "sequenceNo": 1
        },
        {
          "id": 198,
          "dischargeInfoId": 136,
          "reasonForDelayIds": [
            2,
            1
          ],
          "duration": 0,
          "cargoId": 33,
          "cargoNominationId": 17759,
          "quantity": 1070147,
          "sequenceNo": 2
        }
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
            "frameNumberFrom": "49",
            "frameNumberTo": "52",
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
            "frameNumberFrom": "52",
            "frameNumberTo": "61",
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
            "frameNumberFrom": "49",
            "frameNumberTo": "61",
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
            "frameNumberFrom": "49",
            "frameNumberTo": "52",
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
            "frameNumberFrom": "52",
            "frameNumberTo": "61",
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
            "frameNumberFrom": "61",
            "frameNumberTo": "71",
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
            "frameNumberFrom": "61",
            "frameNumberTo": "71",
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
            "frameNumberFrom": "61",
            "frameNumberTo": "71",
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
            "frameNumberFrom": "71",
            "frameNumberTo": "81",
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
            "frameNumberFrom": "71",
            "frameNumberTo": "81",
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
            "frameNumberFrom": "71",
            "frameNumberTo": "81",
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
            "frameNumberFrom": "81",
            "frameNumberTo": "91",
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
            "frameNumberFrom": "81",
            "frameNumberTo": "91",
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
            "frameNumberFrom": "81",
            "frameNumberTo": "91",
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
            "frameNumberFrom": "91",
            "frameNumberTo": "103",
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
            "frameNumberFrom": "91",
            "frameNumberTo": "103",
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
            "frameNumberFrom": "91",
            "frameNumberTo": "103",
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
          "lpCargoDetailId": 183497,
          "cargoNominationId": 17760,
          "tankId": 25580,
          "tankName": "1C",
          "actualWeight": 0,
          "plannedWeight": 24666.1,
          "capacity": 30229.5,
          "abbreviation": "ARL",
          "cargoId": 0,
          "colorCode": "#f21818",
          "correctedUllage": 1.2347,
          "api": 33.4,
          "sg": null,
          "isCommingleCargo": false,
          "grade": null,
          "temperature": 118.8,
          "fillingRatio": "98.0"
        },
        {
          "lpCargoDetailId": 183490,
          "cargoNominationId": 17759,
          "tankId": 25581,
          "tankName": "2C",
          "actualWeight": 0,
          "plannedWeight": 23548.1,
          "capacity": 28201.6,
          "abbreviation": "ARM",
          "cargoId": 0,
          "colorCode": "#f3eb12",
          "correctedUllage": 1.2435,
          "api": 30.8,
          "sg": null,
          "isCommingleCargo": false,
          "grade": null,
          "temperature": 105.4,
          "fillingRatio": "98.0"
        },
        {
          "lpCargoDetailId": 183498,
          "cargoNominationId": 17760,
          "tankId": 25582,
          "tankName": "3C",
          "actualWeight": 0,
          "plannedWeight": 23011.4,
          "capacity": 28201.6,
          "abbreviation": "ARL",
          "cargoId": 0,
          "colorCode": "#f21818",
          "correctedUllage": 1.2076,
          "api": 33.4,
          "sg": null,
          "isCommingleCargo": false,
          "grade": null,
          "temperature": 118.8,
          "fillingRatio": "98.0"
        },
        {
          "lpCargoDetailId": 183493,
          "cargoNominationId": 17759,
          "tankId": 25583,
          "tankName": "4C",
          "actualWeight": 0,
          "plannedWeight": 23548.1,
          "capacity": 28201.6,
          "abbreviation": "ARM",
          "cargoId": 0,
          "colorCode": "#f3eb12",
          "correctedUllage": 1.2486,
          "api": 30.8,
          "sg": null,
          "isCommingleCargo": false,
          "grade": null,
          "temperature": 105.4,
          "fillingRatio": "98.0"
        },
        {
          "lpCargoDetailId": 183499,
          "cargoNominationId": 17760,
          "tankId": 25584,
          "tankName": "5C",
          "actualWeight": 0,
          "plannedWeight": 27518.4,
          "capacity": 33725.1,
          "abbreviation": "ARL",
          "cargoId": 0,
          "colorCode": "#f21818",
          "correctedUllage": 1.1301,
          "api": 33.4,
          "sg": null,
          "isCommingleCargo": false,
          "grade": null,
          "temperature": 118.8,
          "fillingRatio": "98.0"
        },
        {
          "lpCargoDetailId": 183488,
          "cargoNominationId": 17759,
          "tankId": 25585,
          "tankName": "1P",
          "actualWeight": 0,
          "plannedWeight": 17365.9,
          "capacity": 20797.7,
          "abbreviation": "ARM",
          "cargoId": 0,
          "colorCode": "#f3eb12",
          "correctedUllage": 1.3109,
          "api": 30.8,
          "sg": null,
          "isCommingleCargo": false,
          "grade": null,
          "temperature": 105.4,
          "fillingRatio": "98.0"
        },
        {
          "lpCargoDetailId": 183489,
          "cargoNominationId": 17759,
          "tankId": 25586,
          "tankName": "1S",
          "actualWeight": 0,
          "plannedWeight": 17365.9,
          "capacity": 20797.7,
          "abbreviation": "ARM",
          "cargoId": 0,
          "colorCode": "#f3eb12",
          "correctedUllage": 1.3109,
          "api": 30.8,
          "sg": null,
          "isCommingleCargo": false,
          "grade": null,
          "temperature": 105.4,
          "fillingRatio": "98.0"
        },
        {
          "lpCargoDetailId": 100000030,
          "cargoNominationId": 0,
          "tankId": 25587,
          "tankName": "2P",
          "actualWeight": 0,
          "plannedWeight": 0,
          "capacity": 20290.8,
          "abbreviation": "",
          "cargoId": 0,
          "colorCode": "",
          "correctedUllage": 0,
          "api": 0,
          "sg": null,
          "isCommingleCargo": false,
          "grade": null,
          "temperature": 0,
          "fillingRatio": ""
        },
        {
          "lpCargoDetailId": 100000031,
          "cargoNominationId": 0,
          "tankId": 25588,
          "tankName": "2S",
          "actualWeight": 0,
          "plannedWeight": 0,
          "capacity": 20290.8,
          "abbreviation": "",
          "cargoId": 0,
          "colorCode": "",
          "correctedUllage": 0,
          "api": 0,
          "sg": null,
          "isCommingleCargo": false,
          "grade": null,
          "temperature": 0,
          "fillingRatio": ""
        },
        {
          "lpCargoDetailId": 183491,
          "cargoNominationId": 17759,
          "tankId": 25589,
          "tankName": "3P",
          "actualWeight": 0,
          "plannedWeight": 16942.7,
          "capacity": 20290.8,
          "abbreviation": "ARM",
          "cargoId": 0,
          "colorCode": "#f3eb12",
          "correctedUllage": 1.378,
          "api": 30.8,
          "sg": null,
          "isCommingleCargo": false,
          "grade": null,
          "temperature": 105.4,
          "fillingRatio": "98.0"
        },
        {
          "lpCargoDetailId": 183492,
          "cargoNominationId": 17759,
          "tankId": 25590,
          "tankName": "3S",
          "actualWeight": 0,
          "plannedWeight": 16942.7,
          "capacity": 20290.8,
          "abbreviation": "ARM",
          "cargoId": 0,
          "colorCode": "#f3eb12",
          "correctedUllage": 1.378,
          "api": 30.8,
          "sg": null,
          "isCommingleCargo": false,
          "grade": null,
          "temperature": 105.4,
          "fillingRatio": "98.0"
        },
        {
          "lpCargoDetailId": 100000032,
          "cargoNominationId": 0,
          "tankId": 25591,
          "tankName": "4P",
          "actualWeight": 0,
          "plannedWeight": 0,
          "capacity": 20290.8,
          "abbreviation": "",
          "cargoId": 0,
          "colorCode": "",
          "correctedUllage": 0,
          "api": 0,
          "sg": null,
          "isCommingleCargo": false,
          "grade": null,
          "temperature": 0,
          "fillingRatio": ""
        },
        {
          "lpCargoDetailId": 100000033,
          "cargoNominationId": 0,
          "tankId": 25592,
          "tankName": "4S",
          "actualWeight": 0,
          "plannedWeight": 0,
          "capacity": 20290.8,
          "abbreviation": "",
          "cargoId": 0,
          "colorCode": "",
          "correctedUllage": 0,
          "api": 0,
          "sg": null,
          "isCommingleCargo": false,
          "grade": null,
          "temperature": 0,
          "fillingRatio": ""
        },
        {
          "lpCargoDetailId": 183494,
          "cargoNominationId": 17759,
          "tankId": 25593,
          "tankName": "5P",
          "actualWeight": 0,
          "plannedWeight": 14426.5,
          "capacity": 17277.4,
          "abbreviation": "ARM",
          "cargoId": 0,
          "colorCode": "#f3eb12",
          "correctedUllage": 1.4494,
          "api": 30.8,
          "sg": null,
          "isCommingleCargo": false,
          "grade": null,
          "temperature": 105.4,
          "fillingRatio": "98.0"
        },
        {
          "lpCargoDetailId": 183495,
          "cargoNominationId": 17759,
          "tankId": 25594,
          "tankName": "5S",
          "actualWeight": 0,
          "plannedWeight": 14426.5,
          "capacity": 17277.4,
          "abbreviation": "ARM",
          "cargoId": 0,
          "colorCode": "#f3eb12",
          "correctedUllage": 1.4494,
          "api": 30.8,
          "sg": null,
          "isCommingleCargo": false,
          "grade": null,
          "temperature": 105.4,
          "fillingRatio": "98.0"
        },
        {
          "lpCargoDetailId": 183496,
          "cargoNominationId": 17759,
          "tankId": 25595,
          "tankName": "SLP",
          "actualWeight": 0,
          "plannedWeight": 3437.9,
          "capacity": 4117.3,
          "abbreviation": "ARM",
          "cargoId": 0,
          "colorCode": "#f3eb12",
          "correctedUllage": 1.3479,
          "api": 30.8,
          "sg": null,
          "isCommingleCargo": false,
          "grade": null,
          "temperature": 105.4,
          "fillingRatio": "98.0"
        },
        {
          "lpCargoDetailId": 183500,
          "cargoNominationId": 17760,
          "tankId": 25596,
          "tankName": "SLS",
          "actualWeight": 0,
          "plannedWeight": 3359.5,
          "capacity": 4117.3,
          "abbreviation": "ARL",
          "cargoId": 0,
          "colorCode": "#f21818",
          "correctedUllage": 1.3481,
          "api": 33.4,
          "sg": null,
          "isCommingleCargo": false,
          "grade": null,
          "temperature": 118.8,
          "fillingRatio": "98.0"
        }
      ],
      "dischargeQuantityCargoDetails": [
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
          "slopQuantity": 3301,
          "timeRequiredForDischarging": "1.00",
          "blFigure": "118729.5000",
          "maxDischargingRate": "20500.0000",
          "protested": true,
          "isCommingledDischarge": true,
          "isCommingledCargo": false
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
          "name": null,
          "abbreviation": "ARL",
          "api": "33.4000",
          "temp": "118.8000",
          "companyId": null,
          "plannedWeight": 78555.4,
          "actualWeight": 0,
          "dischargeTime": null
        },
        {
          "id": 0,
          "name": null,
          "abbreviation": "ARM",
          "api": "30.8000",
          "temp": "105.4000",
          "companyId": null,
          "plannedWeight": 148004.3,
          "actualWeight": 0,
          "dischargeTime": null
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
            "colorCode": "#f6ef0e"
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
            "colorCode": "#f6ef0e"
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
      "needFlushingOil": true
    },
    "postDischargeStageTime": {
      "dryCheckTime": "00:00",
      "slopDischargingTime": "00:00",
      "finalStrippingTime": "00:00",
      "freshOilWashingTime": "00:00"
    },
    "dischargePlanArrStatusId": 5,
    "dischargePlanDepStatusId": 5,
    "dischargePatternId": 4887,
    "isDischargeInfoComplete": true,
    "isDischargeInstructionsComplete": true,
    "isDischargeSequenceGenerated": true,
    "isDischargePlanGenerated": true
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
@Component({
  selector: 'cpdss-portal-discharging-plan',
  templateUrl: './discharging-plan.component.html',
  styleUrls: ['./discharging-plan.component.scss']
})
export class DischargingPlanComponent implements OnInit, OnDestroy {

  @Input() vesselId: number;
  @Input() voyageId: number;
  @Input() dischargeInfoId: number;
  @Input() get cargos(): ICargo[] {
    return this._cargos;
  }
  set cargos(cargos: ICargo[]) {
    this._cargos = cargos;
  }
  @Input() get portRotationId(): number {
    return this._portRotationId;
  }
  set portRotationId(portRotationId: number) {
    this._portRotationId = portRotationId;
    this.getDischargingPlanDetails();
  }

  private _cargos: ICargo[];
  private _portRotationId: number;
  private ngUnsubscribe: Subject<any> = new Subject();

  dischargingPlanDetails: IDischargingPlanDetailsResponse;
  dischargingInformation: IDischargingInformation;
  currentQuantitySelectedUnit = <QUANTITY_UNIT>localStorage.getItem('unit');
  dischargeQuantityCargoDetails: ILoadableQuantityCargo[];
  loadedCargos: ICargo[];
  readonly OPERATIONS = OPERATIONS;
  prevQuantitySelectedUnit: QUANTITY_UNIT;
  dischargingPlanForm: FormGroup;
  errorMessage: IAlgoError[];
  errorPopUp = false;
  listData: IDischargeOperationListData = {
    protestedOptions: [{ name: 'Yes', id: 1 }, { name: 'No', id: 2 }],
    cowOptions: [{ name: 'Auto', id: 1 }, { name: 'Manual', id: 2 }],
    cowPercentages: [
      { value: 25, name: '25%' },
      { value: 50, name: '50%' },
      { value: 75, name: '75%' },
      { value: 100, name: '100%' },
    ]
  };

  constructor(
    private dischargingPlanApiService: DischargingPlanApiService,
    private loadingDischargingTransformationService: LoadingDischargingTransformationService,
    private ngxSpinnerService: NgxSpinnerService,
    private messageService: MessageService,
    private translateService: TranslateService,
    private fb: FormBuilder
  ) { }

  ngOnInit(): void {
    // TODO : will remove below id once actual API data available.
    this.dischargeInfoId = 136;

    this.initSubscriptions();
  }

  ngOnDestroy() {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  /**
  * Initialization for all subscriptions
  *
  * @private
  * @memberof LoadingInformationComponent
  */
  private async initSubscriptions() {
    this.loadingDischargingTransformationService.unitChange$.pipe(takeUntil(this.ngUnsubscribe)).subscribe((res) => {
      this.prevQuantitySelectedUnit = this.currentQuantitySelectedUnit ?? AppConfigurationService.settings.baseUnit
      this.currentQuantitySelectedUnit = <QUANTITY_UNIT>localStorage.getItem('unit');
    });

    this.loadingDischargingTransformationService.showUllageErrorPopup$.subscribe((res) => {
      this.getAlgoErrorMessage(res);
    });

    this.loadingDischargingTransformationService.setUllageArrivalBtnStatus$.subscribe((value) => {
      if (value === ULLAGE_STATUS_VALUE.SUCCESS) {
        this.getDischargingPlanDetails();
      }
    });
    this.loadingDischargingTransformationService.setUllageDepartureBtnStatus$.subscribe((value) => {
      if (value === ULLAGE_STATUS_VALUE.SUCCESS) {
        this.getDischargingPlanDetails();
      }
    });
  }

  /**
   * function to get discharging-plan data
   *
   * @memberof DischargingPlanComponent
   */
  async getDischargingPlanDetails() {
    this.ngxSpinnerService.show();
    /**
     * commented code will use once actual API available
     */
    // const dischargePlanResponse: IDischargingPlanDetailsResponse = await this.dischargingPlanApiService.getDischargingPlanDetails(this.vesselId, this.voyageId, this.dischargeInfoId, this.portRotationId).toPromise();
    const dischargePlanResponse: IDischargingPlanDetailsResponse = JSON.parse(JSON.stringify(mockJson));
    if (dischargePlanResponse.responseStatus.status === "200") {
      this.dischargingPlanDetails = dischargePlanResponse;
      this.dischargingPlanDetails.dischargingInformation.cargoVesselTankDetails.loadableQuantityCargoDetails = dischargePlanResponse.dischargingInformation.cargoVesselTankDetails.dischargeQuantityCargoDetails;
      this.dischargeQuantityCargoDetails = this.dischargingPlanDetails?.dischargingInformation?.cargoVesselTankDetails?.loadableQuantityCargoDetails;
      this.dischargingPlanDetails.dischargingInformation.loadedCargos = [...this.dischargeQuantityCargoDetails].map(cargo => {
        const loadedCargo = { 'id': cargo?.cargoNominationId, 'abbreviation': cargo?.cargoAbbreviation, 'colorCode': cargo?.colorCode };
        return loadedCargo;
      });
      this.dischargingInformation = this.loadingDischargingTransformationService.transformDischargingInformation(this.dischargingPlanDetails.dischargingInformation, this.listData);
      this.dischargingPlanForm = this.fb.group({
        cowDetails: this.fb.group({}),
        postDischargeStageTime: this.fb.group({})
      });
    }
    this.ngxSpinnerService.hide();
  }


  async getAlgoErrorMessage(status) {
    const translationKeys = await this.translateService.get(['DSICHARGING_PLAN_ALGO_ERROR', 'DSICHARGING_PLAN_ALGO_NO_PLAN']).toPromise();

    // TODO: replace discharge plan generation's get algo error details API with below
    // const algoError: IAlgoResponse = await this.loadingApiService.getAlgoErrorDetails(this.vesselId, this.voyageId, this.dischargeInfoId, status.status).toPromise();
    const algoError: IAlgoResponse = <IAlgoResponse>{};

    if (algoError.responseStatus.status === 'SUCCESS') {
      this.errorMessage = algoError.algoErrors;
      this.errorPopUp = status.value;
    }
    this.messageService.add({ severity: 'error', summary: translationKeys['DSICHARGING_PLAN_ALGO_ERROR'], detail: translationKeys["DSICHARGING_PLAN_ALGO_NO_PLAN"] });
  }

  /**
   * function to display ALGO error popup
   *
   * @param {*} status
   * @memberof DischargingPlanComponent
   */
  viewError(status) {
    this.errorPopUp = status;
  }

}

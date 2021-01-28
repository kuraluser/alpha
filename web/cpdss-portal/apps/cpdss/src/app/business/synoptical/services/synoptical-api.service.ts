import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { ISynopticalResponse } from '../models/synoptical-table.model';
/**
 * 
 * Api Service for Synoptical Table
 */

@Injectable()
export class SynopticalApiService {
    mockJson = {
        "responseStatus": {
            "status": "200"
        },
        "synopticalRecords": [
            {
                "id": 460,
                "operationType": "ARR",
                "distance": 122.0000,
                "speed": 25.6000,
                "runningHours": 2.5000,
                "inPortHours": 2.0000,
                "etaEtdPlanned": "18-12-2020 15:30",
                "etaEtdActual": "19-12-2020 15:30",
                "timeOfSunrise": "06:15",
                "timeOfSunset": "18:30",
                "hwTideFrom": 1.0000,
                "hwTideTo": 2.0000,
                "hwTideTimeFrom": "09:30",
                "hwTideTimeTo": "10:00",
                "lwTideFrom": 3.0000,
                "lwTideTo": 4.0000,
                "lwTideTimeFrom": "15:30",
                "lwTideTimeTo": "16:00",
                "specificGravity": 1.2500,
                "portId": 2,
                "portName": "ABADAN",
                "cargos": [
                    {
                        "tankId": 25580,
                        "tankName": "1C",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25581,
                        "tankName": "2C",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25582,
                        "tankName": "3C",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25583,
                        "tankName": "4C",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25584,
                        "tankName": "5C",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25585,
                        "tankName": "1P",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25586,
                        "tankName": "1S",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25587,
                        "tankName": "2P",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25588,
                        "tankName": "2S",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25589,
                        "tankName": "3P",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25590,
                        "tankName": "3S",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25591,
                        "tankName": "4P",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25592,
                        "tankName": "4S",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25593,
                        "tankName": "5P",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25594,
                        "tankName": "5S",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25595,
                        "tankName": "SLP",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25596,
                        "tankName": "SLS",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    }
                ],
                "cargoPlannedTotal": 85.00,
                "cargoActualTotal": 102.00,
                "foList": [
                    {
                        "tankId": 25614,
                        "tankName": "FO2P",
                        "fuelTypeId": 5,
                        "fuelType": "FOT",
                        "plannedWeight": 10.00,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25615,
                        "tankName": "FO2S",
                        "fuelTypeId": 5,
                        "fuelType": "FOT",
                        "plannedWeight": 11.00,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25612,
                        "tankName": "FO1P",
                        "fuelTypeId": 5,
                        "fuelType": "FOT",
                        "plannedWeight": 0,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25616,
                        "tankName": "HFOSRV",
                        "fuelTypeId": 5,
                        "fuelType": "FOT",
                        "plannedWeight": 0,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25613,
                        "tankName": "FO1S",
                        "fuelTypeId": 5,
                        "fuelType": "FOT",
                        "plannedWeight": 0,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25617,
                        "tankName": "HFOSET",
                        "fuelTypeId": 5,
                        "fuelType": "FOT",
                        "plannedWeight": 0,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25619,
                        "tankName": "BFOSRV",
                        "fuelTypeId": 5,
                        "fuelType": "FOT",
                        "plannedWeight": 0,
                        "actualWeight": 0
                    }
                ],
                "doList": [
                    {
                        "tankId": 25622,
                        "tankName": "DO1S",
                        "fuelTypeId": 6,
                        "fuelType": "DOT",
                        "plannedWeight": 12.00,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25625,
                        "tankName": "DOSRV2",
                        "fuelTypeId": 6,
                        "fuelType": "DOT",
                        "plannedWeight": 34.00,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25623,
                        "tankName": "DO2S",
                        "fuelTypeId": 6,
                        "fuelType": "DOT",
                        "plannedWeight": 0,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25624,
                        "tankName": "DOSRV1",
                        "fuelTypeId": 6,
                        "fuelType": "DOT",
                        "plannedWeight": 0,
                        "actualWeight": 0
                    }
                ],
                "fwList": [
                    {
                        "tankId": 25638,
                        "tankName": "DSWTP",
                        "fuelTypeId": 3,
                        "fuelType": "FWT",
                        "plannedWeight": 0,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25636,
                        "tankName": "DRWT",
                        "fuelTypeId": 3,
                        "fuelType": "FWT",
                        "plannedWeight": 0,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25637,
                        "tankName": "FRWT",
                        "fuelTypeId": 3,
                        "fuelType": "FWT",
                        "plannedWeight": 1.00,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25639,
                        "tankName": "DSWTS",
                        "fuelTypeId": 3,
                        "fuelType": "FWT",
                        "plannedWeight": 12.00,
                        "actualWeight": 0
                    }
                ],
                "lubeList": [
                    {
                        "tankId": 25626,
                        "tankName": "LOSUMC",
                        "fuelTypeId": 19,
                        "fuelType": "",
                        "plannedWeight": 12.00,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25632,
                        "tankName": "MELOSETTTANK",
                        "fuelTypeId": 19,
                        "fuelType": "",
                        "plannedWeight": 12.00,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25633,
                        "tankName": "MELOPURIFLOTANK",
                        "fuelTypeId": 19,
                        "fuelType": "",
                        "plannedWeight": 56.00,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25634,
                        "tankName": "NO1CYLOSTORETANK",
                        "fuelTypeId": 19,
                        "fuelType": "",
                        "plannedWeight": 1.00,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25635,
                        "tankName": "NO2CYLOSTORETANK",
                        "fuelTypeId": 19,
                        "fuelType": "",
                        "plannedWeight": 5.00,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25630,
                        "tankName": "NO1MELOSTORTANK",
                        "fuelTypeId": 19,
                        "fuelType": "",
                        "plannedWeight": 2.00,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25631,
                        "tankName": "NO2MELOSTORTANK",
                        "fuelTypeId": 19,
                        "fuelType": "",
                        "plannedWeight": 45.00,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25627,
                        "tankName": "LOST",
                        "fuelTypeId": 19,
                        "fuelType": "",
                        "plannedWeight": 0,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25628,
                        "tankName": "GELOSTORTANK",
                        "fuelTypeId": 19,
                        "fuelType": "",
                        "plannedWeight": 0,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25629,
                        "tankName": "GELOSETTTANK",
                        "fuelTypeId": 19,
                        "fuelType": "",
                        "plannedWeight": 0,
                        "actualWeight": 0
                    }
                ],
                "plannedFOTotal": 21.00,
                "actualFOTotal": 0,
                "plannedDOTotal": 46.00,
                "actualDOTotal": 0,
                "plannedLubeTotal": 133.00,
                "actualLubeTotal": 0,
                "plannedFWTotal": 13.00,
                "actualFWTotal": 0,
                "othersPlanned": 0,
                "othersActual": 0,
                "constantPlanned": 593.0000,
                "constantActual": 0,
                "totalDwtPlanned": 310406.0000,
                "totalDwtActual": 0,
                "displacementPlanned": 351414.0000,
                "displacementActual": 0,
                "ballastPlanned": 250.0000,
                "ballastActual": 0,
                "hogSag": "10.0000",
                "finalDraftFwd": 50.0000,
                "finalDraftAft": 51.0000,
                "finalDraftMid": 52.0000,
                "calculatedDraftFwdPlanned": 5.0000,
                "calculatedDraftFwdActual": 50.0000,
                "calculatedDraftAftPlanned": 22.0000,
                "calculatedDraftAftActual": 51.0000,
                "calculatedDraftMidPlanned": 234.0000,
                "calculatedDraftMidActual": 52.0000,
                "calculatedTrimPlanned": 34.0000,
                "calculatedTrimActual": 53.0000,
                "blindSector": 54.0000
            },
            {
                "id": 459,
                "operationType": "DEP",
                "distance": 122.0000,
                "speed": 25.6000,
                "runningHours": 2.5000,
                "inPortHours": 2.0000,
                "etaEtdPlanned": "18-12-2020 15:30",
                "etaEtdActual": "19-12-2020 15:30",
                "timeOfSunrise": "06:15",
                "timeOfSunset": "18:30",
                "hwTideFrom": 1.0000,
                "hwTideTo": 2.0000,
                "hwTideTimeFrom": "09:30",
                "hwTideTimeTo": "10:00",
                "lwTideFrom": 3.0000,
                "lwTideTo": 4.0000,
                "lwTideTimeFrom": "15:30",
                "lwTideTimeTo": "16:00",
                "specificGravity": 1.2500,
                "portId": 2,
                "portName": "ABADAN",
                "cargos": [
                    {
                        "tankId": 25580,
                        "tankName": "1C",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25581,
                        "tankName": "2C",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25582,
                        "tankName": "3C",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25583,
                        "tankName": "4C",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25584,
                        "tankName": "5C",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25585,
                        "tankName": "1P",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25586,
                        "tankName": "1S",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25587,
                        "tankName": "2P",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25588,
                        "tankName": "2S",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25589,
                        "tankName": "3P",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25590,
                        "tankName": "3S",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25591,
                        "tankName": "4P",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25592,
                        "tankName": "4S",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25593,
                        "tankName": "5P",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25594,
                        "tankName": "5S",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25595,
                        "tankName": "SLP",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25596,
                        "tankName": "SLS",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    }
                ],
                "cargoPlannedTotal": 85.00,
                "cargoActualTotal": 102.00,
                "foList": [
                    {
                        "tankId": 25614,
                        "tankName": "FO2P",
                        "fuelTypeId": 5,
                        "fuelType": "FOT",
                        "plannedWeight": 10.00,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25615,
                        "tankName": "FO2S",
                        "fuelTypeId": 5,
                        "fuelType": "FOT",
                        "plannedWeight": 11.00,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25612,
                        "tankName": "FO1P",
                        "fuelTypeId": 5,
                        "fuelType": "FOT",
                        "plannedWeight": 0,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25616,
                        "tankName": "HFOSRV",
                        "fuelTypeId": 5,
                        "fuelType": "FOT",
                        "plannedWeight": 0,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25613,
                        "tankName": "FO1S",
                        "fuelTypeId": 5,
                        "fuelType": "FOT",
                        "plannedWeight": 0,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25617,
                        "tankName": "HFOSET",
                        "fuelTypeId": 5,
                        "fuelType": "FOT",
                        "plannedWeight": 0,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25619,
                        "tankName": "BFOSRV",
                        "fuelTypeId": 5,
                        "fuelType": "FOT",
                        "plannedWeight": 0,
                        "actualWeight": 0
                    }
                ],
                "doList": [
                    {
                        "tankId": 25622,
                        "tankName": "DO1S",
                        "fuelTypeId": 6,
                        "fuelType": "DOT",
                        "plannedWeight": 1.00,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25625,
                        "tankName": "DOSRV2",
                        "fuelTypeId": 6,
                        "fuelType": "DOT",
                        "plannedWeight": 1.00,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25623,
                        "tankName": "DO2S",
                        "fuelTypeId": 6,
                        "fuelType": "DOT",
                        "plannedWeight": 0,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25624,
                        "tankName": "DOSRV1",
                        "fuelTypeId": 6,
                        "fuelType": "DOT",
                        "plannedWeight": 0,
                        "actualWeight": 0
                    }
                ],
                "fwList": [
                    {
                        "tankId": 25638,
                        "tankName": "DSWTP",
                        "fuelTypeId": 3,
                        "fuelType": "FWT",
                        "plannedWeight": 0,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25636,
                        "tankName": "DRWT",
                        "fuelTypeId": 3,
                        "fuelType": "FWT",
                        "plannedWeight": 0,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25637,
                        "tankName": "FRWT",
                        "fuelTypeId": 3,
                        "fuelType": "FWT",
                        "plannedWeight": 1.00,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25639,
                        "tankName": "DSWTS",
                        "fuelTypeId": 3,
                        "fuelType": "FWT",
                        "plannedWeight": 2.00,
                        "actualWeight": 0
                    }
                ],
                "lubeList": [
                    {
                        "tankId": 25626,
                        "tankName": "LOSUMC",
                        "fuelTypeId": 19,
                        "fuelType": "",
                        "plannedWeight": 12.00,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25632,
                        "tankName": "MELOSETTTANK",
                        "fuelTypeId": 19,
                        "fuelType": "",
                        "plannedWeight": 0.00,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25633,
                        "tankName": "MELOPURIFLOTANK",
                        "fuelTypeId": 19,
                        "fuelType": "",
                        "plannedWeight": 0.00,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25634,
                        "tankName": "NO1CYLOSTORETANK",
                        "fuelTypeId": 19,
                        "fuelType": "",
                        "plannedWeight": 8.00,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25635,
                        "tankName": "NO2CYLOSTORETANK",
                        "fuelTypeId": 19,
                        "fuelType": "",
                        "plannedWeight": 1.00,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25630,
                        "tankName": "NO1MELOSTORTANK",
                        "fuelTypeId": 19,
                        "fuelType": "",
                        "plannedWeight": 0.00,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25631,
                        "tankName": "NO2MELOSTORTANK",
                        "fuelTypeId": 19,
                        "fuelType": "",
                        "plannedWeight": 0.00,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25627,
                        "tankName": "LOST",
                        "fuelTypeId": 19,
                        "fuelType": "",
                        "plannedWeight": 0,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25628,
                        "tankName": "GELOSTORTANK",
                        "fuelTypeId": 19,
                        "fuelType": "",
                        "plannedWeight": 0,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25629,
                        "tankName": "GELOSETTTANK",
                        "fuelTypeId": 19,
                        "fuelType": "",
                        "plannedWeight": 0,
                        "actualWeight": 0
                    }
                ],
                "plannedFOTotal": 21.00,
                "actualFOTotal": 0,
                "plannedDOTotal": 2.00,
                "actualDOTotal": 0,
                "plannedLubeTotal": 21.00,
                "actualLubeTotal": 0,
                "plannedFWTotal": 3.00,
                "actualFWTotal": 0,
                "othersPlanned": 0,
                "othersActual": 0,
                "constantPlanned": 593.0000,
                "constantActual": 0,
                "totalDwtPlanned": 310406.0000,
                "totalDwtActual": 0,
                "displacementPlanned": 351414.0000,
                "displacementActual": 0,
                "ballastPlanned": 200.0000,
                "ballastActual": 23333.0000,
                "hogSag": "10.0000",
                "finalDraftFwd": 50.0000,
                "finalDraftAft": 51.0000,
                "finalDraftMid": 52.0000,
                "calculatedDraftFwdPlanned": 5.0000,
                "calculatedDraftFwdActual": 50.0000,
                "calculatedDraftAftPlanned": 22.0000,
                "calculatedDraftAftActual": 51.0000,
                "calculatedDraftMidPlanned": 234.0000,
                "calculatedDraftMidActual": 52.0000,
                "calculatedTrimPlanned": 34.0000,
                "calculatedTrimActual": 53.0000,
                "blindSector": 54.0000
            },
            {
                "id": 460,
                "operationType": "ARR",
                "distance": 122.0000,
                "speed": 25.6000,
                "runningHours": 2.5000,
                "inPortHours": 2.0000,
                "etaEtdPlanned": "18-12-2020 15:30",
                "etaEtdActual": "19-12-2020 15:30",
                "timeOfSunrise": "06:15",
                "timeOfSunset": "18:30",
                "hwTideFrom": 1.0000,
                "hwTideTo": 2.0000,
                "hwTideTimeFrom": "09:30",
                "hwTideTimeTo": "10:00",
                "lwTideFrom": 3.0000,
                "lwTideTo": 4.0000,
                "lwTideTimeFrom": "15:30",
                "lwTideTimeTo": "16:00",
                "specificGravity": 1.2500,
                "portId": 2,
                "portName": "ABADAN",
                "cargos": [
                    {
                        "tankId": 25580,
                        "tankName": "1C",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25581,
                        "tankName": "2C",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25582,
                        "tankName": "3C",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25583,
                        "tankName": "4C",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25584,
                        "tankName": "5C",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25585,
                        "tankName": "1P",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25586,
                        "tankName": "1S",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25587,
                        "tankName": "2P",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25588,
                        "tankName": "2S",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25589,
                        "tankName": "3P",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25590,
                        "tankName": "3S",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25591,
                        "tankName": "4P",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25592,
                        "tankName": "4S",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25593,
                        "tankName": "5P",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25594,
                        "tankName": "5S",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25595,
                        "tankName": "SLP",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25596,
                        "tankName": "SLS",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    }
                ],
                "cargoPlannedTotal": 85.00,
                "cargoActualTotal": 102.00,
                "foList": [
                    {
                        "tankId": 25614,
                        "tankName": "FO2P",
                        "fuelTypeId": 5,
                        "fuelType": "FOT",
                        "plannedWeight": 10.00,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25615,
                        "tankName": "FO2S",
                        "fuelTypeId": 5,
                        "fuelType": "FOT",
                        "plannedWeight": 11.00,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25612,
                        "tankName": "FO1P",
                        "fuelTypeId": 5,
                        "fuelType": "FOT",
                        "plannedWeight": 0,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25616,
                        "tankName": "HFOSRV",
                        "fuelTypeId": 5,
                        "fuelType": "FOT",
                        "plannedWeight": 0,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25613,
                        "tankName": "FO1S",
                        "fuelTypeId": 5,
                        "fuelType": "FOT",
                        "plannedWeight": 0,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25617,
                        "tankName": "HFOSET",
                        "fuelTypeId": 5,
                        "fuelType": "FOT",
                        "plannedWeight": 0,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25619,
                        "tankName": "BFOSRV",
                        "fuelTypeId": 5,
                        "fuelType": "FOT",
                        "plannedWeight": 0,
                        "actualWeight": 0
                    }
                ],
                "doList": [
                    {
                        "tankId": 25622,
                        "tankName": "DO1S",
                        "fuelTypeId": 6,
                        "fuelType": "DOT",
                        "plannedWeight": 12.00,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25625,
                        "tankName": "DOSRV2",
                        "fuelTypeId": 6,
                        "fuelType": "DOT",
                        "plannedWeight": 34.00,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25623,
                        "tankName": "DO2S",
                        "fuelTypeId": 6,
                        "fuelType": "DOT",
                        "plannedWeight": 0,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25624,
                        "tankName": "DOSRV1",
                        "fuelTypeId": 6,
                        "fuelType": "DOT",
                        "plannedWeight": 0,
                        "actualWeight": 0
                    }
                ],
                "fwList": [
                    {
                        "tankId": 25638,
                        "tankName": "DSWTP",
                        "fuelTypeId": 3,
                        "fuelType": "FWT",
                        "plannedWeight": 0,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25636,
                        "tankName": "DRWT",
                        "fuelTypeId": 3,
                        "fuelType": "FWT",
                        "plannedWeight": 0,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25637,
                        "tankName": "FRWT",
                        "fuelTypeId": 3,
                        "fuelType": "FWT",
                        "plannedWeight": 1.00,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25639,
                        "tankName": "DSWTS",
                        "fuelTypeId": 3,
                        "fuelType": "FWT",
                        "plannedWeight": 12.00,
                        "actualWeight": 0
                    }
                ],
                "lubeList": [
                    {
                        "tankId": 25626,
                        "tankName": "LOSUMC",
                        "fuelTypeId": 19,
                        "fuelType": "",
                        "plannedWeight": 12.00,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25632,
                        "tankName": "MELOSETTTANK",
                        "fuelTypeId": 19,
                        "fuelType": "",
                        "plannedWeight": 12.00,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25633,
                        "tankName": "MELOPURIFLOTANK",
                        "fuelTypeId": 19,
                        "fuelType": "",
                        "plannedWeight": 56.00,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25634,
                        "tankName": "NO1CYLOSTORETANK",
                        "fuelTypeId": 19,
                        "fuelType": "",
                        "plannedWeight": 1.00,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25635,
                        "tankName": "NO2CYLOSTORETANK",
                        "fuelTypeId": 19,
                        "fuelType": "",
                        "plannedWeight": 5.00,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25630,
                        "tankName": "NO1MELOSTORTANK",
                        "fuelTypeId": 19,
                        "fuelType": "",
                        "plannedWeight": 2.00,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25631,
                        "tankName": "NO2MELOSTORTANK",
                        "fuelTypeId": 19,
                        "fuelType": "",
                        "plannedWeight": 45.00,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25627,
                        "tankName": "LOST",
                        "fuelTypeId": 19,
                        "fuelType": "",
                        "plannedWeight": 0,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25628,
                        "tankName": "GELOSTORTANK",
                        "fuelTypeId": 19,
                        "fuelType": "",
                        "plannedWeight": 0,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25629,
                        "tankName": "GELOSETTTANK",
                        "fuelTypeId": 19,
                        "fuelType": "",
                        "plannedWeight": 0,
                        "actualWeight": 0
                    }
                ],
                "plannedFOTotal": 21.00,
                "actualFOTotal": 0,
                "plannedDOTotal": 46.00,
                "actualDOTotal": 0,
                "plannedLubeTotal": 133.00,
                "actualLubeTotal": 0,
                "plannedFWTotal": 13.00,
                "actualFWTotal": 0,
                "othersPlanned": 0,
                "othersActual": 0,
                "constantPlanned": 593.0000,
                "constantActual": 0,
                "totalDwtPlanned": 310406.0000,
                "totalDwtActual": 0,
                "displacementPlanned": 351414.0000,
                "displacementActual": 0,
                "ballastPlanned": 250.0000,
                "ballastActual": 0,
                "hogSag": "10.0000",
                "finalDraftFwd": 50.0000,
                "finalDraftAft": 51.0000,
                "finalDraftMid": 52.0000,
                "calculatedDraftFwdPlanned": 5.0000,
                "calculatedDraftFwdActual": 50.0000,
                "calculatedDraftAftPlanned": 22.0000,
                "calculatedDraftAftActual": 51.0000,
                "calculatedDraftMidPlanned": 234.0000,
                "calculatedDraftMidActual": 52.0000,
                "calculatedTrimPlanned": 34.0000,
                "calculatedTrimActual": 53.0000,
                "blindSector": 54.0000
            },
            {
                "id": 459,
                "operationType": "DEP",
                "distance": 122.0000,
                "speed": 25.6000,
                "runningHours": 2.5000,
                "inPortHours": 2.0000,
                "etaEtdPlanned": "18-12-2020 15:30",
                "etaEtdActual": "19-12-2020 15:30",
                "timeOfSunrise": "06:15",
                "timeOfSunset": "18:30",
                "hwTideFrom": 1.0000,
                "hwTideTo": 2.0000,
                "hwTideTimeFrom": "09:30",
                "hwTideTimeTo": "10:00",
                "lwTideFrom": 3.0000,
                "lwTideTo": 4.0000,
                "lwTideTimeFrom": "15:30",
                "lwTideTimeTo": "16:00",
                "specificGravity": 1.2500,
                "portId": 2,
                "portName": "ABADAN",
                "cargos": [
                    {
                        "tankId": 25580,
                        "tankName": "1C",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25581,
                        "tankName": "2C",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25582,
                        "tankName": "3C",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25583,
                        "tankName": "4C",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25584,
                        "tankName": "5C",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25585,
                        "tankName": "1P",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25586,
                        "tankName": "1S",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25587,
                        "tankName": "2P",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25588,
                        "tankName": "2S",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25589,
                        "tankName": "3P",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25590,
                        "tankName": "3S",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25591,
                        "tankName": "4P",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25592,
                        "tankName": "4S",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25593,
                        "tankName": "5P",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25594,
                        "tankName": "5S",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25595,
                        "tankName": "SLP",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    },
                    {
                        "tankId": 25596,
                        "tankName": "SLS",
                        "actualWeight": 6.00,
                        "plannedWeight": 5.00
                    }
                ],
                "cargoPlannedTotal": 85.00,
                "cargoActualTotal": 102.00,
                "foList": [
                    {
                        "tankId": 25614,
                        "tankName": "FO2P",
                        "fuelTypeId": 5,
                        "fuelType": "FOT",
                        "plannedWeight": 10.00,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25615,
                        "tankName": "FO2S",
                        "fuelTypeId": 5,
                        "fuelType": "FOT",
                        "plannedWeight": 11.00,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25612,
                        "tankName": "FO1P",
                        "fuelTypeId": 5,
                        "fuelType": "FOT",
                        "plannedWeight": 0,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25616,
                        "tankName": "HFOSRV",
                        "fuelTypeId": 5,
                        "fuelType": "FOT",
                        "plannedWeight": 0,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25613,
                        "tankName": "FO1S",
                        "fuelTypeId": 5,
                        "fuelType": "FOT",
                        "plannedWeight": 0,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25617,
                        "tankName": "HFOSET",
                        "fuelTypeId": 5,
                        "fuelType": "FOT",
                        "plannedWeight": 0,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25619,
                        "tankName": "BFOSRV",
                        "fuelTypeId": 5,
                        "fuelType": "FOT",
                        "plannedWeight": 0,
                        "actualWeight": 0
                    }
                ],
                "doList": [
                    {
                        "tankId": 25622,
                        "tankName": "DO1S",
                        "fuelTypeId": 6,
                        "fuelType": "DOT",
                        "plannedWeight": 1.00,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25625,
                        "tankName": "DOSRV2",
                        "fuelTypeId": 6,
                        "fuelType": "DOT",
                        "plannedWeight": 1.00,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25623,
                        "tankName": "DO2S",
                        "fuelTypeId": 6,
                        "fuelType": "DOT",
                        "plannedWeight": 0,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25624,
                        "tankName": "DOSRV1",
                        "fuelTypeId": 6,
                        "fuelType": "DOT",
                        "plannedWeight": 0,
                        "actualWeight": 0
                    }
                ],
                "fwList": [
                    {
                        "tankId": 25638,
                        "tankName": "DSWTP",
                        "fuelTypeId": 3,
                        "fuelType": "FWT",
                        "plannedWeight": 0,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25636,
                        "tankName": "DRWT",
                        "fuelTypeId": 3,
                        "fuelType": "FWT",
                        "plannedWeight": 0,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25637,
                        "tankName": "FRWT",
                        "fuelTypeId": 3,
                        "fuelType": "FWT",
                        "plannedWeight": 1.00,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25639,
                        "tankName": "DSWTS",
                        "fuelTypeId": 3,
                        "fuelType": "FWT",
                        "plannedWeight": 2.00,
                        "actualWeight": 0
                    }
                ],
                "lubeList": [
                    {
                        "tankId": 25626,
                        "tankName": "LOSUMC",
                        "fuelTypeId": 19,
                        "fuelType": "",
                        "plannedWeight": 12.00,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25632,
                        "tankName": "MELOSETTTANK",
                        "fuelTypeId": 19,
                        "fuelType": "",
                        "plannedWeight": 0.00,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25633,
                        "tankName": "MELOPURIFLOTANK",
                        "fuelTypeId": 19,
                        "fuelType": "",
                        "plannedWeight": 0.00,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25634,
                        "tankName": "NO1CYLOSTORETANK",
                        "fuelTypeId": 19,
                        "fuelType": "",
                        "plannedWeight": 8.00,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25635,
                        "tankName": "NO2CYLOSTORETANK",
                        "fuelTypeId": 19,
                        "fuelType": "",
                        "plannedWeight": 1.00,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25630,
                        "tankName": "NO1MELOSTORTANK",
                        "fuelTypeId": 19,
                        "fuelType": "",
                        "plannedWeight": 0.00,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25631,
                        "tankName": "NO2MELOSTORTANK",
                        "fuelTypeId": 19,
                        "fuelType": "",
                        "plannedWeight": 0.00,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25627,
                        "tankName": "LOST",
                        "fuelTypeId": 19,
                        "fuelType": "",
                        "plannedWeight": 0,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25628,
                        "tankName": "GELOSTORTANK",
                        "fuelTypeId": 19,
                        "fuelType": "",
                        "plannedWeight": 0,
                        "actualWeight": 0
                    },
                    {
                        "tankId": 25629,
                        "tankName": "GELOSETTTANK",
                        "fuelTypeId": 19,
                        "fuelType": "",
                        "plannedWeight": 0,
                        "actualWeight": 0
                    }
                ],
                "plannedFOTotal": 21.00,
                "actualFOTotal": 0,
                "plannedDOTotal": 2.00,
                "actualDOTotal": 0,
                "plannedLubeTotal": 21.00,
                "actualLubeTotal": 0,
                "plannedFWTotal": 3.00,
                "actualFWTotal": 0,
                "othersPlanned": 0,
                "othersActual": 0,
                "constantPlanned": 593.0000,
                "constantActual": 0,
                "totalDwtPlanned": 310406.0000,
                "totalDwtActual": 0,
                "displacementPlanned": 351414.0000,
                "displacementActual": 0,
                "ballastPlanned": 200.0000,
                "ballastActual": 23333.0000,
                "hogSag": "10.0000",
                "finalDraftFwd": 50.0000,
                "finalDraftAft": 51.0000,
                "finalDraftMid": 52.0000,
                "calculatedDraftFwdPlanned": 5.0000,
                "calculatedDraftFwdActual": 50.0000,
                "calculatedDraftAftPlanned": 22.0000,
                "calculatedDraftAftActual": 51.0000,
                "calculatedDraftMidPlanned": 234.0000,
                "calculatedDraftMidActual": 52.0000,
                "calculatedTrimPlanned": 34.0000,
                "calculatedTrimActual": 53.0000,
                "blindSector": 54.0000
            }
        ]
    }

    constructor(private commonApiService: CommonApiService) { }

    /**
     * Get synoptical table records
     * @param vesselId 
     * @param voyageId 
     * @param loadableStudyId 
     */

    getSynopticalTable(vesselId: number, voyageId: number, loadableStudyId: number, loadablePatternId: number): Observable<any> {
        if (!loadablePatternId) {
            loadablePatternId = 0;
        }
        return this.commonApiService.get<ISynopticalResponse>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies/${loadableStudyId}/loadable-pattern/${loadablePatternId}/synoptical-table`);
    }

}

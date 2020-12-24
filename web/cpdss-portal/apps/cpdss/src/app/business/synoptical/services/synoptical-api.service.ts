import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { ISynopticalResponse } from '../models/synoptical-table.model';
  /**
   * 
   * Api Service for Synoptical Table
   */

@Injectable({
  providedIn: 'root'
})
export class SynopticalApiService {
  mockJson = {
    "responseStatus": {
        "status": "200"
    },
    "synopticalRecords": [
        {
            "id": 12,
            "operationType": "ARR",
            "distance": 100.0000,
            "speed": 25.6000,
            "runningHours": 2.5000,
            "inPortHours": 2.0000,
            "etaPlanned": "17-11-2020 20:58",
            "etaActual": "17-11-2020 21:58",
            "etdPlanned": "20-11-2020 20:58",
            "etdActual": "20-11-2020 21:58",
            "timeOfSunrise": "6.15",
            "timeOfSunset": "18.30",
            "hwTideFrom": 1.0000,
            "hwTideTo": 2.0000,
            "hwTideTimeFrom": "18-12-2020 09:30",
            "hwTideTimeTo": "18-12-2020 10:00",
            "lwTideFrom": 3.0000,
            "lwTideTo": 4.0000,
            "lwTideTimeFrom": "18-12-2020 15:30",
            "lwTideTimeTo": "2020-12-18T16:00",
            "specificGravity": 1.2500,
            "portId": 1,
            "portName": "ACCRA",
            "cargos": [
                {
                    "tankId": 25596,
                    "tankName": "SLS",
                    "actualWeight": 2324.00,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25581,
                    "tankName": "2C",
                    "actualWeight": 2423.00,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25585,
                    "tankName": "1P",
                    "actualWeight": 2232.00,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25586,
                    "tankName": "1S",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25587,
                    "tankName": "2P",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25588,
                    "tankName": "2S",
                    "actualWeight": 22323.00,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25584,
                    "tankName": "5C",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25580,
                    "tankName": "1C",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25582,
                    "tankName": "3C",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25583,
                    "tankName": "4C",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25589,
                    "tankName": "3P",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25590,
                    "tankName": "3S",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25591,
                    "tankName": "4P",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25592,
                    "tankName": "4S",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25593,
                    "tankName": "5P",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25594,
                    "tankName": "5S",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25595,
                    "tankName": "SLP",
                    "actualWeight": 0,
                    "plannedWeight": 0
                }
            ],
            "cargoPlannedTotal": 29302.00,
            "cargoActualTotal": 0
        },
        {
            "id": 15,
            "operationType": "DEP",
            "distance": 100.0000,
            "speed": 25.6000,
            "runningHours": 2.5000,
            "inPortHours": 2.0000,
            "etaPlanned": "17-11-2020 20:58",
            "etaActual": "17-11-2020 21:58",
            "etdPlanned": "20-11-2020 20:58",
            "etdActual": "20-11-2020 21:58",
            "timeOfSunrise": "6.15",
            "timeOfSunset": "18.30",
            "hwTideFrom": 1.0000,
            "hwTideTo": 2.0000,
            "hwTideTimeFrom": "18-12-2020 09:30",
            "hwTideTimeTo": "18-12-2020 10:00",
            "lwTideFrom": 3.0000,
            "lwTideTo": 4.0000,
            "lwTideTimeFrom": "18-12-2020 15:30",
            "lwTideTimeTo": "2020-12-18T16:00",
            "specificGravity": 1.2500,
            "portId": 1,
            "portName": "ACCRA",
            "cargos": [
                {
                    "tankId": 25596,
                    "tankName": "SLS",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25581,
                    "tankName": "2C",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25585,
                    "tankName": "1P",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25586,
                    "tankName": "1S",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25587,
                    "tankName": "2P",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25588,
                    "tankName": "2S",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25584,
                    "tankName": "5C",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25580,
                    "tankName": "1C",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25582,
                    "tankName": "3C",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25583,
                    "tankName": "4C",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25589,
                    "tankName": "3P",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25590,
                    "tankName": "3S",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25591,
                    "tankName": "4P",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25592,
                    "tankName": "4S",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25593,
                    "tankName": "5P",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25594,
                    "tankName": "5S",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25595,
                    "tankName": "SLP",
                    "actualWeight": 0,
                    "plannedWeight": 0
                }
            ],
            "cargoPlannedTotal": 0,
            "cargoActualTotal": 0
        },
        {
            "id": 13,
            "operationType": "ARR",
            "distance": 100.0000,
            "speed": 25.6000,
            "runningHours": 2.5000,
            "inPortHours": 2.0000,
            "etaPlanned": "",
            "etaActual": "",
            "etdPlanned": "",
            "etdActual": "",
            "timeOfSunrise": "6.15",
            "timeOfSunset": "18.30",
            "hwTideFrom": 1.0000,
            "hwTideTo": 2.0000,
            "hwTideTimeFrom": "18-12-2020 09:30",
            "hwTideTimeTo": "18-12-2020 10:00",
            "lwTideFrom": 3.0000,
            "lwTideTo": 4.0000,
            "lwTideTimeFrom": "18-12-2020 15:30",
            "lwTideTimeTo": "2020-12-18T16:00",
            "specificGravity": 1.2500,
            "portId": 2,
            "portName": "ACCRA",
            "cargos": [
                {
                    "tankId": 25596,
                    "tankName": "SLS",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25581,
                    "tankName": "2C",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25585,
                    "tankName": "1P",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25586,
                    "tankName": "1S",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25587,
                    "tankName": "2P",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25588,
                    "tankName": "2S",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25584,
                    "tankName": "5C",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25580,
                    "tankName": "1C",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25582,
                    "tankName": "3C",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25583,
                    "tankName": "4C",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25589,
                    "tankName": "3P",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25590,
                    "tankName": "3S",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25591,
                    "tankName": "4P",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25592,
                    "tankName": "4S",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25593,
                    "tankName": "5P",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25594,
                    "tankName": "5S",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25595,
                    "tankName": "SLP",
                    "actualWeight": 0,
                    "plannedWeight": 0
                }
            ],
            "cargoPlannedTotal": 0,
            "cargoActualTotal": 0
        },
        {
            "id": 16,
            "operationType": "DEP",
            "distance": 100.0000,
            "speed": 25.6000,
            "runningHours": 2.5000,
            "inPortHours": 2.0000,
            "etaPlanned": "",
            "etaActual": "",
            "etdPlanned": "",
            "etdActual": "",
            "timeOfSunrise": "6.15",
            "timeOfSunset": "18.30",
            "hwTideFrom": 1.0000,
            "hwTideTo": 2.0000,
            "hwTideTimeFrom": "18-12-2020 09:30",
            "hwTideTimeTo": "18-12-2020 10:00",
            "lwTideFrom": 3.0000,
            "lwTideTo": 4.0000,
            "lwTideTimeFrom": "18-12-2020 15:30",
            "lwTideTimeTo": "2020-12-18T16:00",
            "specificGravity": 1.2500,
            "portId": 2,
            "portName": "ACCRA",
            "cargos": [
                {
                    "tankId": 25596,
                    "tankName": "SLS",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25581,
                    "tankName": "2C",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25585,
                    "tankName": "1P",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25586,
                    "tankName": "1S",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25587,
                    "tankName": "2P",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25588,
                    "tankName": "2S",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25584,
                    "tankName": "5C",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25580,
                    "tankName": "1C",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25582,
                    "tankName": "3C",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25583,
                    "tankName": "4C",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25589,
                    "tankName": "3P",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25590,
                    "tankName": "3S",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25591,
                    "tankName": "4P",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25592,
                    "tankName": "4S",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25593,
                    "tankName": "5P",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25594,
                    "tankName": "5S",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25595,
                    "tankName": "SLP",
                    "actualWeight": 0,
                    "plannedWeight": 0
                }
            ],
            "cargoPlannedTotal": 0,
            "cargoActualTotal": 0
        },
        {
            "id": 14,
            "operationType": "ARR",
            "distance": 100.0000,
            "speed": 25.6000,
            "runningHours": 2.5000,
            "inPortHours": 2.0000,
            "etaPlanned": "27-11-2020 14:55",
            "etaActual": "",
            "etdPlanned": "30-11-2020 14:55",
            "etdActual": "",
            "timeOfSunrise": "6.15",
            "timeOfSunset": "18.30",
            "hwTideFrom": 1.0000,
            "hwTideTo": 2.0000,
            "hwTideTimeFrom": "18-12-2020 09:30",
            "hwTideTimeTo": "18-12-2020 10:00",
            "lwTideFrom": 3.0000,
            "lwTideTo": 4.0000,
            "lwTideTimeFrom": "18-12-2020 15:30",
            "lwTideTimeTo": "2020-12-18T16:00",
            "specificGravity": 1.2500,
            "portId": 4,
            "portName": "ACCRA",
            "cargos": [
                {
                    "tankId": 25596,
                    "tankName": "SLS",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25581,
                    "tankName": "2C",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25585,
                    "tankName": "1P",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25586,
                    "tankName": "1S",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25587,
                    "tankName": "2P",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25588,
                    "tankName": "2S",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25584,
                    "tankName": "5C",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25580,
                    "tankName": "1C",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25582,
                    "tankName": "3C",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25583,
                    "tankName": "4C",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25589,
                    "tankName": "3P",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25590,
                    "tankName": "3S",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25591,
                    "tankName": "4P",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25592,
                    "tankName": "4S",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25593,
                    "tankName": "5P",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25594,
                    "tankName": "5S",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25595,
                    "tankName": "SLP",
                    "actualWeight": 0,
                    "plannedWeight": 0
                }
            ],
            "cargoPlannedTotal": 0,
            "cargoActualTotal": 0
        },
        {
            "id": 17,
            "operationType": "DEP",
            "distance": 100.0000,
            "speed": 25.6000,
            "runningHours": 2.5000,
            "inPortHours": 2.0000,
            "etaPlanned": "27-11-2020 14:55",
            "etaActual": "",
            "etdPlanned": "30-11-2020 14:55",
            "etdActual": "",
            "timeOfSunrise": "6.15",
            "timeOfSunset": "18.30",
            "hwTideFrom": 1.0000,
            "hwTideTo": 2.0000,
            "hwTideTimeFrom": "18-12-2020 09:30",
            "hwTideTimeTo": "18-12-2020 10:00",
            "lwTideFrom": 3.0000,
            "lwTideTo": 4.0000,
            "lwTideTimeFrom": "18-12-2020 15:30",
            "lwTideTimeTo": "2020-12-18T16:00",
            "specificGravity": 1.2500,
            "portId": 4,
            "portName": "ACCRA",
            "cargos": [
                {
                    "tankId": 25596,
                    "tankName": "SLS",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25581,
                    "tankName": "2C",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25585,
                    "tankName": "1P",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25586,
                    "tankName": "1S",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25587,
                    "tankName": "2P",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25588,
                    "tankName": "2S",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25584,
                    "tankName": "5C",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25580,
                    "tankName": "1C",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25582,
                    "tankName": "3C",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25583,
                    "tankName": "4C",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25589,
                    "tankName": "3P",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25590,
                    "tankName": "3S",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25591,
                    "tankName": "4P",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25592,
                    "tankName": "4S",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25593,
                    "tankName": "5P",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25594,
                    "tankName": "5S",
                    "actualWeight": 0,
                    "plannedWeight": 0
                },
                {
                    "tankId": 25595,
                    "tankName": "SLP",
                    "actualWeight": 0,
                    "plannedWeight": 0
                }
            ],
            "cargoPlannedTotal": 0,
            "cargoActualTotal": 0
        }
    ],
    "cargoTanks": [
        {
            "id": 25596,
            "shortName": "SLS",
            "slopTank": false
        },
        {
            "id": 25581,
            "shortName": "2C",
            "slopTank": false
        },
        {
            "id": 25585,
            "shortName": "1P",
            "slopTank": false
        },
        {
            "id": 25586,
            "shortName": "1S",
            "slopTank": false
        },
        {
            "id": 25587,
            "shortName": "2P",
            "slopTank": false
        },
        {
            "id": 25588,
            "shortName": "2S",
            "slopTank": false
        },
        {
            "id": 25584,
            "shortName": "5C",
            "slopTank": false
        },
        {
            "id": 25580,
            "shortName": "1C",
            "slopTank": false
        },
        {
            "id": 25582,
            "shortName": "3C",
            "slopTank": false
        },
        {
            "id": 25583,
            "shortName": "4C",
            "slopTank": false
        },
        {
            "id": 25589,
            "shortName": "3P",
            "slopTank": false
        },
        {
            "id": 25590,
            "shortName": "3S",
            "slopTank": false
        },
        {
            "id": 25591,
            "shortName": "4P",
            "slopTank": false
        },
        {
            "id": 25592,
            "shortName": "4S",
            "slopTank": false
        },
        {
            "id": 25593,
            "shortName": "5P",
            "slopTank": false
        },
        {
            "id": 25594,
            "shortName": "5S",
            "slopTank": false
        },
        {
            "id": 25595,
            "shortName": "SLP",
            "slopTank": false
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
  
  getSynopticalTable(vesselId: number, voyageId:number, loadableStudyId: number): Observable<any>{
    return this.commonApiService.get<ISynopticalResponse>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies/${loadableStudyId}/synoptical-table`);
    // return of(this.mockJson)
  }

   

}

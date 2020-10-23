import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { VesselDetailsModel } from '../model/vessel-details.model'

/**
 * Service for vessels api
 */
@Injectable()
export class VesselsApiService {
  public vesselDetails: VesselDetailsModel[];

  constructor(private http: HttpClient) { }

  /**
   * Vessel details api result mock
   */
  getVesselsInfo() {
    this.vesselDetails = [
      {
        "id": 1,
        "name": "KAZUSA",
        "captainId": 10,
        "captainName": "KAZUSA Captain Name",
        "chiefOfficerId": 20,
        "chiefOfficerName": "KAZUSA Chief Officer Name",
        "loadlines": [{
          "id": 1,
          "name": "Summer",
          "draftMarks": ["21.855", "21.42", "21.3760", "20.9430", "20.8080"]
        }, {
          "id": 2,
          "name": "Tropical",
          "draftMarks": ["21.855"]
        },
        {
          "id": 3,
          "name": "Tropical Fresh",
          "draftMarks": ["21.855", "21.42", "21.3760", "20.9430", "20.8080"]
        }]

      },
      {
        "id": 2,
        "name": "SHIZUKISAN",
        "captainId": 30,
        "captainName": "SHIZUKISAN Captain Name",
        "chiefOfficerId": 40,
        "chiefOfficerName": "SHIZUKISAN Chief Officer Name",
        "loadlines": [{
          "id": 1,
          "name": "Summer",
          "draftMarks": ["21.855", "21.42", "21.3760", "20.9430", "20.8080"]
        }, {
          "id": 2,
          "name": "Tropical",
          "draftMarks": ["21.855"]
        },
        {
          "id": 3,
          "name": "Tropical Fresh",
          "draftMarks": ["21.855", "21.42", "21.3760", "20.9430", "20.8080"]
        }
        ]
      }
    ];

    return this.vesselDetails;

  }

  /**
   * Vessel details api 
   */
  getVesselDetails(domainId: number): VesselDetailsModel[] {
    // return this.http.get<VesselDetailsModel[]>('api/cloud/companies/{domain-id}/vessels');
    return this.getVesselsInfo();
  }
}

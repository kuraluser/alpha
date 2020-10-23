import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { NewVoyageModel } from '../models/new-voyage.model';
import { NewVoyageResponseModel } from '../models/new-voyage.model'

/**
 * Service for voyage api
 */
@Injectable()
export class VoyageApiService {

  constructor(private http: HttpClient) { }

  /**
   * Save new-voyage api
   */
  saveNewVoyageData(newVoyage: NewVoyageModel, vessel_id: number, company_id: number): Observable<NewVoyageResponseModel> { 
    return this.http.post<NewVoyageResponseModel>('api/cloud/companies/company-id/vessels/vessel-id/voyages/TC-10', newVoyage);
  }
}

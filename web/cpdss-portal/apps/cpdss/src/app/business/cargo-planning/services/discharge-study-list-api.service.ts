import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { IDischargeStudiesResponse } from '../models/discharge-study-list.model';

@Injectable({
  providedIn: 'root'
})
export class DischargeStudyListApiService {

  constructor(
    private commonApiService: CommonApiService,
    private http: HttpClient) { }

  /**
   * Get discharge study list
   */
   getDischargeStudies(vesselId: number, voyageId: number): Observable<IDischargeStudiesResponse> { 
     let  planType = 2  //pass this to indicate it is discharge study.
     return this.commonApiService.get<IDischargeStudiesResponse>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies?planType=${planType}
    `);

  }
}

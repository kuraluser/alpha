import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { CommonApiService } from '../../../shared/services/common/common-api.service';

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
   getDischargeStudies(vesselId: number, voyageId: number): Observable<any> {  //TODO - create model instead of any type when actual api is available.,This is the dummy api.
    return this.commonApiService.get<any>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies 
    `);

  }
}

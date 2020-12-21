import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
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

  constructor(private commonApiService: CommonApiService) { }

 /**
  * Get synoptical table records
  * @param vesselId 
  * @param voyageId 
  * @param loadableStudyId 
  */ 
  
  getSynopticalTable(vesselId: number, voyageId:number, loadableStudyId: number): Observable<ISynopticalResponse>{
    return this.commonApiService.get<ISynopticalResponse>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies/${loadableStudyId}/synoptical-table`);
  }

   

}

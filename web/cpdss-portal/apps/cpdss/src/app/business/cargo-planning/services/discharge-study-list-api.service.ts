import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { IDischargeStudiesResponse, IDischargeStudy, IDischargeStudyResponse } from '../models/discharge-study-list.model';

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
     const  planningType = 2  //pass this to indicate it is discharge study.
     return this.commonApiService.get<IDischargeStudiesResponse>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies?planningType=${planningType}
    `);
  }


 /**
  * Method to save/update discharge study.
  *
  * @memberof DischargeStudyListApiService
  */

 saveOrUpdateDischargeStudy(vesselId?: number, voyageId?: number, dischargeStudy?: IDischargeStudy,dischargeStudyId?:number)
    {
      const formData: FormData = new FormData();
      formData.append('name',dischargeStudy.name)
      formData.append('enquiryDetails',dischargeStudy.detail);
      if(!dischargeStudyId)        
      {
        return this.commonApiService.postFormData<IDischargeStudyResponse>(`vessels/${vesselId}/voyages/${voyageId}/discharge-study`, formData);
      }
      else{
       return this.commonApiService.putFormData<IDischargeStudyResponse>(`discharge-studies/${dischargeStudyId}`,formData)
      }
    }

}

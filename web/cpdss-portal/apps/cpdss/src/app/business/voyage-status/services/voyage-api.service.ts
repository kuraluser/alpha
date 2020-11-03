import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { NewVoyageModel } from '../models/new-voyage.model';
import { NewVoyageResponseModel } from '../models/new-voyage.model'

/**
 * Service for voyage api
 */
@Injectable()
export class VoyageApiService {

  constructor(private commonApiService: CommonApiService) { }

  /**
   * Save new-voyage api
   */
  saveNewVoyageData(newVoyage: NewVoyageModel, vesselId: number): Observable<NewVoyageResponseModel> {
    return this.commonApiService.post<NewVoyageModel, NewVoyageResponseModel>(`vessels/${vesselId}/voyages`, newVoyage);
  }
}

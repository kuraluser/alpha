import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { IVoyageStatus } from '../models/voyage-status.model';
import { IVoyagePortDetails } from '../../core/models/common.model';

/**
 * Service for voyage api
 */
@Injectable()
export class VoyageApiService {

  constructor(private commonApiService: CommonApiService) { }

  /**
   * Api to get voyage status
   */
  getVoyageDetails(vesselId: number, voyageId: number, loadableStudyId: number, voyageDetails: IVoyagePortDetails): Observable<IVoyageStatus> {
    const portId = voyageDetails.portId;
    return this.commonApiService.post<IVoyagePortDetails, IVoyageStatus>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies/${loadableStudyId}/ports/${portId}/voyage-status`, voyageDetails);
  }
}

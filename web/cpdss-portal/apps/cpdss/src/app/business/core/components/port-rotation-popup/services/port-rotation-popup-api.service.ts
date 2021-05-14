import { Injectable } from '@angular/core';
import { CommonApiService } from 'apps/cpdss/src/app/shared/services/common/common-api.service';
import { Observable } from 'rxjs';
import { IEditPortRotationModel, IPortResponseModel } from '../../../models/common.model';


/**
 * Api Service for Port rotation pop up module
 *
 * @export
 * @class PortRotationPopupApiService
 */
@Injectable()
export class PortRotationPopupApiService {

  constructor(private commonApiService: CommonApiService) { }

   /**
   * Save  port rotation popup 
   * @param vesselId
   * @param voyageId
   * @param loadableStudyId
   * @param ports
   */
    saveEditPortRotation(vesselId: number, voyageId: number, loadableStudyId: number, ports: IEditPortRotationModel): Observable<IPortResponseModel> {
      return this.commonApiService.post<IEditPortRotationModel, IPortResponseModel>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies/${loadableStudyId}/ports`, ports);
    }
}

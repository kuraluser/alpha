import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { ICargoResponseModel } from '../../core/models/common.model';

/**
 * Api Service for Loadaing  module
 *
 * @export
 * @class LoadingApiService
 */
@Injectable()
export class LoadingApiService {

  constructor(private commonApiService: CommonApiService) { }

    /**
*
* Get api for cargo details
*/
getCargos(): Observable<ICargoResponseModel> {
  return this.commonApiService.get<ICargoResponseModel>(`cargos`);
}
}

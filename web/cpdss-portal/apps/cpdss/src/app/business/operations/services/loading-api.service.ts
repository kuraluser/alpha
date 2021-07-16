import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ICargoResponseModel } from '../../../shared/models/common.model';
import { CommonApiService } from '../../../shared/services/common/common-api.service';

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

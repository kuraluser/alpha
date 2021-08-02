import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { ICargoResponseModel } from '../../core/models/common.model';

/**
 * Service class for operations module
 *
 * @export
 * @class OperationsApiService
 */
@Injectable()
export class OperationsApiService {

  constructor(private commonApiService: CommonApiService) { }


  /**
   * Method for fetching all cargos from db
   *
   * @return {*}  {Observable<ICargoResponseModel>}
   * @memberof OperationsApiService
   */
  getCargos(): Observable<ICargoResponseModel> {
    return this.commonApiService.get<ICargoResponseModel>(`cargos`);
  }
}

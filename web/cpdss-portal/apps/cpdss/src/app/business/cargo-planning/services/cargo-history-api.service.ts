import { Injectable } from '@angular/core';
import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { CargoPlanningModule } from '../cargo-planning.module';
import { Observable } from 'rxjs';
import { ICargoHistoryDataStateChange, ICargoHistoryResponse } from '../models/cargo-planning.model';

/**
 * API service for Cargo history module
 *
 * @export
 * @class CargoHistoryApiService
 */
@Injectable({
  providedIn: CargoPlanningModule
})
export class CargoHistoryApiService {
  private page = 0;
  private pageSize = 10;

  constructor(private commonApiService: CommonApiService) { }

  /**
   * method to get cargo history details
   * @param {*} params
   * @return {*}  {Observable<ICargoHistoryResponse>}
   * @memberof CargoHistoryApiService
   */
  getCargoHistoryData(params: ICargoHistoryDataStateChange): Observable<ICargoHistoryResponse> {
    const requestParams = `page=${params.page ? params.page : this.page}&pageSize=${params.pageSize ? params.pageSize : this.pageSize}&orderBy=${params.orderBy ? params.orderBy : ''}&sortBy=${params.sortBy ? params.sortBy : ''}${params.vesselName ? '&vesselName=' + params.vesselName : ''}${params.grade ? '&grade=' + params.grade : ''}${params.loadingPortName ? '&loadingPortName=' + params.loadingPortName : ''}${params.loadedYear ? '&loadedYear=' + params.loadedYear : ''}${params.loadedMonth ? '&loadedMonth=' + params.loadedMonth : ''}${params.loadedDay ? '&loadedDay=' + params.loadedDay : ''}${params.api ? '&api=' + params.api : ''}${params.temperature ? '&temperature=' + params.temperature : ''}${params.startDate ? '&startDate=' + params.startDate : ''}${params.endDate ? '&endDate=' + params.endDate : ''}`;
    return this.commonApiService.get<ICargoHistoryResponse>(`cargo-history?${requestParams}`);
  }
}

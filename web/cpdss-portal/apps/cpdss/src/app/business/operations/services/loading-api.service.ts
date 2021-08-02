import { Injectable } from '@angular/core';
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

}

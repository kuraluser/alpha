import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { IVessel } from '../../../core/models/vessel-details.model';
import { VesselsApiService } from '../../../core/services/vessels-api.service';

/**
 * Service Class for Rules
 *
 * @export
 * @class RulesService
 */
@Injectable({
  providedIn: 'root'
})
export class RulesService {

  vessels: IVessel[];
  selectedVessel: IVessel;
  save = new Subject();
  constructor(
    private vesselsApiService: VesselsApiService
  ) { }

 /**
 * Method to initialize variables in the service
 */
  async init() {
    this.vessels = await this.vesselsApiService.getVesselsInfo(true).toPromise();
  }

  /**
  * Method to save changes
  */
  saveChanges() {
    this.save.next()
  }
}

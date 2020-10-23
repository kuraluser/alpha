import { Component, OnInit } from '@angular/core';
import { VesselDetailsModel } from './model/vessel-details.model';
import { VesselsApiService } from './services/vessels-api.service';
import { map } from 'rxjs/operators';

@Component({
  selector: 'cpdss-portal-business',
  templateUrl: './business.component.html',
  styleUrls: ['./business.component.scss']
})
export class BusinessComponent implements OnInit {
  vesselsDetails: VesselDetailsModel[];

  constructor(private vesselsInfoApi: VesselsApiService) { }

  ngOnInit(): void {
    /**
     * Get vessel details
     */
    this.vesselsInfoApi.getVesselsInfo();

  }

     /**
     * Return vessels details
     */
  async getVesselsInfo(): Promise<VesselDetailsModel[]> {
    this.vesselsDetails = await this.vesselsInfoApi.getVesselDetails(1234);
    // return result.map(item => this.vesselsDetails = item);
    return this.vesselsDetails;

  }

}

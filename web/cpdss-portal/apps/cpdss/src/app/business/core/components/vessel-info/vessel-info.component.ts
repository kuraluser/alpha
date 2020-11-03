import { Component, OnInit } from '@angular/core';
import { VesselDetailsModel } from '../../../model/vessel-details.model';
import { VesselsApiService } from '../../services/vessels-api.service';

/**
 * Componnet for shareble Vessel Icon widget
 *
 * @export
 * @class VesselInfoComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-vessel-info',
  templateUrl: './vessel-info.component.html',
  styleUrls: ['./vessel-info.component.scss']
})
export class VesselInfoComponent implements OnInit {
  vesselDetails: VesselDetailsModel[];
  vesselName: string;
  imoNumber: number;
  constructor(private vesselApiService: VesselsApiService) { }

  ngOnInit(): void {
    this.getVesselDetails();
  }

  /**
     * Get vessel details
     */
  async getVesselDetails() {
    this.vesselDetails = await this.vesselApiService.getVesselsInfo().toPromise();
    this.vesselName = this.vesselDetails[0].name;
    this.imoNumber = Number(this.vesselDetails[0].imoNumber);
  }

}

import { Component, Input, OnInit } from '@angular/core';
import { VesselDetailsModel } from '../../../model/vessel-details.model';

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
  @Input() vesselDetails: VesselDetailsModel;
  vesselName: string;
  imoNumber: number;
  constructor() { }

  ngOnInit(): void {
    this.getVesselDetails();
  }

  /**
     * Get vessel details
     */
  async getVesselDetails() {
    this.vesselName = this.vesselDetails.name;
    this.imoNumber = Number(this.vesselDetails.imoNumber);
  }

}

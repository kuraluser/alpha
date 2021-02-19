import { Component, Input, OnInit } from '@angular/core';
import { IVessel } from '../../models/vessel-details.model';

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
  @Input() vesselDetails: IVessel;
  vesselName: string;
  imoNumber: number;
  flagPath: string;
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
    this.flagPath = this.vesselDetails.flagPath;
  }

}

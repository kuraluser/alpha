import { Component, OnInit } from '@angular/core';
import { VesselDetailsModel } from '../../../model/vessel-details.model';
import { VesselsApiService } from '../../../services/vessels-api.service';

/**
 * Component for vessel information
 */
@Component({
  selector: 'cpdss-portal-vessel-info',
  templateUrl: './vessel-info.component.html',
  styleUrls: ['./vessel-info.component.scss']
})
export class VesselInfoComponent implements OnInit {
  vesselDetails: VesselDetailsModel[];
  vesselName: string;

  constructor(private vesselApiService: VesselsApiService) { }

  ngOnInit(): void {
    /**
     * Get vessel details
     */
    this.vesselDetails = this.vesselApiService.vesselDetails;
    this.vesselName = this.vesselDetails[0].name;
  }

}

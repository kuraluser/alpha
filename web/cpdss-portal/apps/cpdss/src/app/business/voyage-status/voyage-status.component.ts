import { Component, OnInit } from '@angular/core';
import { VesselsApiService } from '../core/services/vessels-api.service';
import { VesselDetailsModel } from '../model/vessel-details.model';

@Component({
  selector: 'cpdss-portal-voyage-status',
  templateUrl: './voyage-status.component.html',
  styleUrls: ['./voyage-status.component.scss']
})
export class VoyageStatusComponent implements OnInit {
  display: boolean;
  showData: boolean;
  vesselInfo: VesselDetailsModel;

  constructor(private vesselsApiService: VesselsApiService) { }

  ngOnInit(): void {
    this.display = false;
    this.showData = false;
    this.getVesselInfo();
  }

  /**
   * Get vessel details
   *
   * @memberof VoyageStatusComponent
   */
  async getVesselInfo() {
    const res = await this.vesselsApiService.getVesselsInfo().toPromise();
    this.vesselInfo = res[0] ?? <VesselDetailsModel>{};
  }

  /**
   * Show new-voyage popup
   */
  showDialog() {
    this.display = true;
  }

  /**
   * Value from new-voyage
   */
  displayPopUpTab(displayNew_: boolean) {
    this.display = displayNew_;
  }

}

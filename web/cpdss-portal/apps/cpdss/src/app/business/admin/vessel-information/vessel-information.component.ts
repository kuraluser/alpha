import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';

import { VesselInformationTransformationService } from './../services/vessel-information-transformation.service';
import { TimeZoneTransformationService } from '../../../shared/services/time-zone-conversion/time-zone-transformation.service';
import { VesselInformationApiService } from './../services/vessel-information-api.service';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';

import { IDateTimeFormatOptions } from '../../../shared/models/common.model';
import { IVesselList, IVesselListResponse } from '../models/vessel-info.model';

/**
 * Component for vessel information page
 *
 * @export
 * @class VesselInformationComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-vessel-information',
  templateUrl: './vessel-information.component.html',
  styleUrls: ['./vessel-information.component.scss']
})
export class VesselInformationComponent implements OnInit {

  columns: any[];
  loading: boolean;
  totalRecords: number;
  currentPage: number;
  first: number;
  vesselList: IVesselList[];

  constructor(
    private vesselInformationTransformationService: VesselInformationTransformationService,
    private timeZoneTransformationService: TimeZoneTransformationService,
    private vesselInformationApiService: VesselInformationApiService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private ngxSpinnerService: NgxSpinnerService
  ) { }

  ngOnInit(): void {
    this.columns = this.vesselInformationTransformationService.getVesselInfoTableColumns();
    this.getVesselMasterInformation();
  }

  /**
   * function to get Vessel master details
   * @memberof VesselInformationComponent
   */
  async getVesselMasterInformation() {
    this.first = 0;
    this.currentPage = 0;
    this.ngxSpinnerService.show();
    const formatOptions: IDateTimeFormatOptions = { customFormat: AppConfigurationService.settings?.dateFormat.split(' ')[0] };
    const vesselListDetails: IVesselListResponse = await this.vesselInformationApiService.getVesselList().toPromise();
    if (vesselListDetails.responseStatus.status === '200') {
      this.vesselList = [...vesselListDetails.vesselList].map(vessel => {
        vessel.dateOfLaunch = vessel?.dateOfLaunch && this.timeZoneTransformationService.formatDateTime(vessel?.dateOfLaunch, formatOptions);
        return vessel;
      });
      this.totalRecords = this.vesselList.length;
      this.loading = false;
    }
    this.ngxSpinnerService.hide();
  }

  /**
   * Function to navigate to selected Vessel
   * @param {*} event
   * @memberof VesselInformationComponent
   */
  onRowClick(event): void {
    this.router.navigate(['vessel', event?.data?.vesselId], { relativeTo: this.activatedRoute });
  }

  /**
   * function to get values on state change like search, sort, pagination
   * @param {*} event
   * @memberof CargoHistoryComponent
   */
  onDataStateChange(event: any): void {
    // this.loading = true;
  }

}

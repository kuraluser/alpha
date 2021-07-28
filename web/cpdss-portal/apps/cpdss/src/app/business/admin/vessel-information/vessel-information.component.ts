import { Component, OnInit } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';

import { VesselInformationTransformationService } from './../services/vessel-information-transformation.service';
import { TimeZoneTransformationService } from '../../../shared/services/time-zone-conversion/time-zone-transformation.service';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';

import { IVesselMasterInfo } from './../models/vessel-info.model';
import { IDateTimeFormatOptions } from '../../../shared/models/common.model';

// code will remove once actual data available
const tempVesselInfoData: IVesselMasterInfo[] = [
  {
    vesselId: 1,
    vesselName: 'Kazusa',
    owner: 'Mitsui O.S.K Lines, LTD',
    vesselType: 'Cargo oil tanker',
    builder: 'Mitsui engineering & shipbuilding',
    dateOfLaunch: '02-07-2020'
  },
  {
    vesselId: 2,
    vesselName: 'Shizukisan',
    owner: 'Mitsui O.S.K Lines, LTD',
    vesselType: 'Cargo oil tanker',
    builder: 'Mitsui engineering & shipbuilding',
    dateOfLaunch: '10-07-2020'
  },
  {
    vesselId: 3,
    vesselName: 'Kazusa',
    owner: 'Mitsui O.S.K Lines, LTD',
    vesselType: 'Cargo oil tanker',
    builder: 'Mitsui engineering & shipbuilding',
    dateOfLaunch: '12-07-2020'
  },
  {
    vesselId: 4,
    vesselName: 'Shizukisan',
    owner: 'Mitsui O.S.K Lines, LTD',
    vesselType: 'Cargo oil tanker',
    builder: 'Mitsui engineering & shipbuilding',
    dateOfLaunch: '15-07-2020'
  },
  {
    vesselId: 5,
    vesselName: 'Kazusa',
    owner: 'Mitsui O.S.K Lines, LTD',
    vesselType: 'Cargo oil tanker',
    builder: 'Mitsui engineering & shipbuilding',
    dateOfLaunch: '29-07-2020'
  }
];

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
  vesselInfoData: IVesselMasterInfo[];

  constructor(
    private vesselInformationTransformationService: VesselInformationTransformationService,
    private timeZoneTransformationService: TimeZoneTransformationService,
    private ngxSpinnerService: NgxSpinnerService
  ) { }

  ngOnInit(): void {
    this.ngxSpinnerService.show();
    this.columns = this.vesselInformationTransformationService.getVesselInfoTableColumns();
    this.getVesselMasterInformation();
  }

  /**
   * function to get Vessel master details
   * @memberof VesselInformationComponent
   */
  getVesselMasterInformation(): void {
    const formatOptions: IDateTimeFormatOptions = { customFormat: AppConfigurationService.settings?.dateFormat.split(' ')[0] };
    this.first = 0;
    this.currentPage = 0;
    this.vesselInfoData = [...tempVesselInfoData].map(vessel => {
      vessel.dateOfLaunch = this.timeZoneTransformationService.formatDateTime(vessel.dateOfLaunch, formatOptions);
      return vessel;
    });
    this.totalRecords = tempVesselInfoData.length;
    this.loading = false;
    this.ngxSpinnerService.hide();
  }

}

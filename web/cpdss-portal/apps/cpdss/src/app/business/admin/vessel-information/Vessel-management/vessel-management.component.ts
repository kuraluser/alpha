import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

import { AppConfigurationService } from '../../../../shared/services/app-configuration/app-configuration.service';
import { VesselInformationApiService } from '../../services/vessel-information-api.service';
import { TimeZoneTransformationService } from './../../../../shared/services/time-zone-conversion/time-zone-transformation.service';

import { IVesselDetailsResponse } from '../../models/vessel-info.model';
import { IShipBallastTank, IShipBunkerTank, IShipCargoTank, ITankOptions, TANKTYPE } from '../../../core/models/common.model';
import { IDateTimeFormatOptions } from '../../../../shared/models/common.model';

/**
 * Component for Vessel details management
 *
 * @export
 * @class VesselManagementComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-vessel-management',
  templateUrl: './vessel-management.component.html',
  styleUrls: ['./vessel-management.component.scss']
})
export class VesselManagementComponent implements OnInit, OnDestroy {

  vesselId: number;
  vesselDetails: IVesselDetailsResponse;
  cargoTanks: IShipCargoTank[][];
  rearBallastTanks: IShipBallastTank[][];
  frontBallastTanks: IShipBallastTank[][];
  centerBallastTanks: IShipBallastTank[][];
  bunkerTanks: IShipBunkerTank[][];
  rearBunkerTanks: IShipBunkerTank[][];
  selectedTab = TANKTYPE.CARGO;
  readonly tankType = TANKTYPE;
  readonly noImageUrl = AppConfigurationService.settings?.vesselNoImageUrl;
  private ngUnsubscribe: Subject<any> = new Subject();

  tankOptions: ITankOptions = {
    showTooltip: true,
    isFullyFilled: false,
    showFillingPercentage: false,
    showVolume: false,
    showWeight: false,
    showUllage: false,
    showCommodityName: false,
    showDensity: false,
    isSelectable: false,
    showSounding: false
  };

  constructor(
    private vesselInformationApiService: VesselInformationApiService,
    private timeZoneTransformationService: TimeZoneTransformationService,
    private activatedRoute: ActivatedRoute,
    private ngxSpinnerService: NgxSpinnerService
  ) { }

  ngOnInit(): void {
    this.activatedRoute.paramMap.pipe(takeUntil(this.ngUnsubscribe)).subscribe(params => {
      this.vesselId = Number(params.get('vesselId'));
    });
    this.getVesselManagementDetails();
  }

  ngOnDestroy() {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  /**
   * Function to get Vessel information in detail
   *
   * @memberof VesselManagementComponent
   */
  async getVesselManagementDetails() {
    this.ngxSpinnerService.show();
    const formatOptions: IDateTimeFormatOptions = { customFormat: AppConfigurationService.settings?.dateFormat.split(' ')[0] };
    const vesselDetailsResponse: IVesselDetailsResponse = await this.vesselInformationApiService.getVesselDetails(this.vesselId).toPromise();
    if (vesselDetailsResponse.responseStatus.status === "200") {
      this.vesselDetails = { ...vesselDetailsResponse };
      this.vesselDetails.generalInfo.dateOfKeelLaid = this.vesselDetails?.generalInfo?.dateOfKeelLaid && this.timeZoneTransformationService.formatDateTime(this.vesselDetails?.generalInfo?.dateOfKeelLaid, formatOptions);
      this.vesselDetails.generalInfo.dateOfLaunch = this.vesselDetails?.generalInfo?.dateOfLaunch && this.timeZoneTransformationService.formatDateTime(this.vesselDetails?.generalInfo?.dateOfLaunch, formatOptions);
      this.vesselDetails.generalInfo.dateOfDelivery = this.vesselDetails?.generalInfo?.dateOfDelivery && this.timeZoneTransformationService.formatDateTime(this.vesselDetails?.generalInfo?.dateOfDelivery, formatOptions);

      this.cargoTanks = vesselDetailsResponse.cargoTanks;
      this.rearBallastTanks = vesselDetailsResponse.ballastRearTanks;
      this.frontBallastTanks = vesselDetailsResponse.ballastFrontTanks;
      this.centerBallastTanks = vesselDetailsResponse.ballastCenterTanks;
      this.bunkerTanks = vesselDetailsResponse.bunkerTanks;
      this.rearBunkerTanks = vesselDetailsResponse.bunkerRearTanks;
    }
    this.ngxSpinnerService.hide();
  }

  /**
   * Function to swtch tank layout tab
   *
   * @param {TANKTYPE} selectedTab
   * @memberof VesselManagementComponent
   */
  onTabClick(selectedTab: TANKTYPE) {
    this.selectedTab = selectedTab;
  }

}

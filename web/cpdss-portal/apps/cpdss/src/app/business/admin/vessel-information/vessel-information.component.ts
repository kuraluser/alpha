import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { Subject } from 'rxjs';

import { VesselInformationTransformationService } from './../services/vessel-information-transformation.service';
import { TimeZoneTransformationService } from '../../../shared/services/time-zone-conversion/time-zone-transformation.service';
import { VesselInformationApiService } from './../services/vessel-information-api.service';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';

import { IDateTimeFormatOptions } from '../../../shared/models/common.model';
import { IVesselInfoDataStateChange, IVesselList, IVesselListResponse } from '../models/vessel-info.model';
import { debounceTime, switchMap } from 'rxjs/operators';

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
export class VesselInformationComponent implements OnInit, OnDestroy {

  columns: any[];
  loading: boolean;
  totalRecords: number;
  currentPage: number;
  first: number;
  vesselListPageState: IVesselInfoDataStateChange;
  vesselList: IVesselList[];

  private getVesselMasterList$ = new Subject<IVesselInfoDataStateChange>();

  constructor(
    private vesselInformationTransformationService: VesselInformationTransformationService,
    private timeZoneTransformationService: TimeZoneTransformationService,
    private vesselInformationApiService: VesselInformationApiService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private ngxSpinnerService: NgxSpinnerService
  ) { }

  ngOnInit(): void {
    this.getVesselMasterInformation();
    this.columns = this.vesselInformationTransformationService.getVesselInfoTableColumns();
  }

  /**
   * function to get Vessel master details
   * @memberof VesselInformationComponent
   */
  getVesselMasterInformation(): void {
    this.ngxSpinnerService.show();
    this.first = 0;
    this.currentPage = 0;
    const formatOptions: IDateTimeFormatOptions = { customFormat: AppConfigurationService.settings?.dateFormat.split(' ')[0] };
    this.getVesselMasterList$.pipe(
      debounceTime(1000),
      switchMap(() => {
        return this.vesselInformationApiService.getVesselList(this.vesselListPageState);
      })
    ).subscribe((vesselListDetails: IVesselListResponse) => {
      this.ngxSpinnerService.hide();
      if (vesselListDetails?.responseStatus.status === '200') {
        const vesselInfo = vesselListDetails?.vesselsInfo ? [...vesselListDetails?.vesselsInfo] : [];
        this.totalRecords = vesselListDetails?.totalElements;
        if (this.totalRecords && !vesselInfo?.length) {
          this.currentPage -= 1;
          this.vesselListPageState['pageNo'] = this.currentPage;
          this.getVesselMasterList$.next();
        }
        this.vesselList = vesselInfo?.map(vessel => {
          vessel.dateOfLaunching = vessel?.dateOfLaunching && this.timeZoneTransformationService.formatDateTime(vessel?.dateOfLaunching, formatOptions);
          return vessel;
        });
        this.loading = false;
      }
    });
    this.vesselListPageState = <IVesselInfoDataStateChange>{};
    this.getVesselMasterList$.next();
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
   *
   * @param {*} event
   * @memberof VesselInformationComponent
   */
  onDataStateChange(event: any): void {
    this.vesselListPageState = {
      pageNo: event.paginator.currentPage,
      pageSize: event.paginator.rows,
      sortBy: event.sort.sortField,
      orderBy: event.sort.sortOrder.toUpperCase(),
      name: event.filter?.name,
      typeOfShip: event.filter?.typeOfShip,
      builder: event.filter?.builder,
      dateOfLaunching: event.filter?.dateOfLaunching
    };
    this.loading = true;
    this.getVesselMasterList$.next();
  }

  /**
   * function to stop subscription
   *
   * @memberof VesselInformationComponent
   */
  ngOnDestroy() {
    this.getVesselMasterList$.unsubscribe();
  }

}

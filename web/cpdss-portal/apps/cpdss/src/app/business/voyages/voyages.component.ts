import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { IDataTableColumn } from '../../shared/components/datatable/datatable.model';
import { VoyageListTransformationService } from './services/voyage-list-transformation.service';
import { IVoyageList, IVoyageListResponse } from './models/voyage-list.model'
import { VoyageListApiService } from './services/voyage-list-api.service';
import { IDataStateChange } from '../admin/models/user-role-permission.model';
import { Subject } from 'rxjs';
import { debounceTime, switchMap } from 'rxjs/operators';
import { IVessel } from '../core/models/vessel-details.model';
import { VesselsApiService } from '../core/services/vessels-api.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { AppConfigurationService } from '../../shared/services/app-configuration/app-configuration.service';
import { IPermission } from '../../shared/models/user-profile.model';
import { PermissionsService } from '../../shared/services/permissions/permissions.service';
import { IPermissionContext, PERMISSION_ACTION, QUANTITY_UNIT } from '../../shared/models/common.model';
import * as moment from 'moment';

/**
 * Component class for voyages compoent
 *
 * @export
 * @class VoyagesComponent
 * @implements {OnInit}
 */

@Component({
  selector: 'cpdss-portal-voyages',
  templateUrl: './voyages.component.html',
  styleUrls: ['./voyages.component.scss']
})
export class VoyagesComponent implements OnInit, OnDestroy {
  @ViewChild('dateRangeFilter') dateRangeFilter: any;
  @ViewChild('voyageListTable') voyageListTableBody: any;

  columns: IDataTableColumn[];
  voyageList: IVoyageList[];
  vesselDetails: IVessel;
  vesselId: number;
  filterDates: Date[];
  showDatePopup = false;
  selectedVoyageId: number;
  selectedLoadablePlanId: number;
  permissionStart: IPermission;
  permissionStop: IPermission;
  defaultDate: Date;
  isStart: boolean;
  display: boolean;
  newVoyagePermissionContext: IPermissionContext;

  filterDateError = null;
  errorMessages: any;
  public loading: boolean;
  public totalRecords: number;
  public currentPage: number;
  public first: number;
  public resetDataTable: boolean;
  public pageState: IDataStateChange;

  //private field
  private getVoyageLists$ = new Subject<IDataStateChange>();

  constructor(private voyageListTransformationService: VoyageListTransformationService,
    private voyageListApiService: VoyageListApiService,
    private vesselsApiService: VesselsApiService,
    private ngxSpinnerService: NgxSpinnerService,
    private permissionsService: PermissionsService) { }

  async ngOnInit(): Promise<void> {
    this.errorMessages = this.voyageListTransformationService.setValidationErrorMessage();
    this.ngxSpinnerService.show();
    this.getPagePermission();
    this.permissionStart = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['voyageStart'], false);
    this.permissionStop = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['voyageStop'], false);
    this.first = 0;
    this.currentPage = 0;
    const res = await this.vesselsApiService.getVesselsInfo().toPromise();
    this.vesselDetails = res[0] ?? <IVessel>{};
    this.vesselId = this.vesselDetails?.id;
    this.columns = this.voyageListTransformationService.getVoyageListDatatableColumns(this.permissionStart, this.permissionStop);
    this.getVoyageLists$.pipe(
      debounceTime(1000), // By setting this we can avoid multiple api calls
      switchMap(() => {
        return this.voyageListApiService.getVoyageList(this.vesselId, this.pageState, this.voyageListTransformationService.formatDateTime(this.filterDates && this.filterDates[0]), this.voyageListTransformationService.formatDateTime(this.filterDates && this.filterDates[1]))
      })
    ).subscribe((voyageLIstResponse: IVoyageListResponse) => {
      this.getVoyageList(voyageLIstResponse);
      this.ngxSpinnerService.hide();
      this.loading = false;
    });
    this.pageState = <IDataStateChange>{};
    this.getVoyageLists$.next();
  }


  /**
   * Get page permission
   *
   * @memberof VoyagesComponent
   */
  getPagePermission() {
    this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['voyagesComponent']);
    this.newVoyagePermissionContext = { key: AppConfigurationService.settings.permissionMapping['VoyageHistoryNewVoyage'], actions: [PERMISSION_ACTION.VIEW] };
  }

  /**
   * unsubscribe the observable
   *
   * @memberof VoyagesComponent
  */
  ngOnDestroy() {
    this.getVoyageLists$.unsubscribe();
  }

  /**
* method for get voyage list response
*
* @param {IVoyageListResponse} voyageLIstResponse
* @memberof VoyagesComponent
*/
  async getVoyageList(voyageLIstResponse: IVoyageListResponse) {
    if (voyageLIstResponse.responseStatus.status === '200') {
      const voyageList = voyageLIstResponse.voyages;
      this.totalRecords = voyageLIstResponse.totalElements;
      if (this.totalRecords && !voyageList?.length) {
        this.currentPage = this.currentPage ? this.currentPage - 1 : 0;
        this.pageState['page'] = this.currentPage;
        this.getVoyageLists$.next();
      }
      if (voyageList?.length) {
        this.voyageList = voyageList?.map(voyage => {
          if (voyage?.loadingPorts?.length) {
            voyage.loading = voyage.loadingPorts.map(e => e.name).join(", ");
          }
          if (voyage?.dischargingPorts?.length) {
            voyage.discharging = voyage.dischargingPorts.map(e => e.name).join(", ");
          }
          if (voyage?.cargos?.length) {
            voyage.cargo = voyage.cargos.map(e => e.name).join(", ");
          }
          voyage.plannedStartDate = this.dateStringToDate(voyage?.plannedStartDate);
          voyage.plannedEndDate = this.dateStringToDate(voyage?.plannedEndDate);
          voyage.actualStartDate = this.dateStringToDate(voyage?.actualStartDate);
          voyage.actualEndDate = this.dateStringToDate(voyage?.actualEndDate);
          voyage.isStart = voyage.status === 'Active' ? false : (voyage.status === 'Closed' ? false : true);
          voyage.isStop = voyage.status === 'Active' ? true : false;
          return voyage;
        });
      } else {
        this.voyageList = [];
      }
    }

  }

  /**
  * state of pagination , filter , sort
  * @param {*} event
  * @memberof VoyagesComponent
 */
  onDataStateChange(event: any) {
    this.pageState = {
      filter: event.filter,
      name: event.filter?.name,
      desc: event.filter?.description,
      pageSize: event.paginator.rows,
      page: event.paginator.currentPage,
      sortBy: event.sort.sortField,
      orderBy: event.sort.sortOrder,
    };
    this.reloadVoyageHistory();
    if (event.action === 'paginator') {
      const tableBody = this.voyageListTableBody.nativeElement.querySelector('.p-datatable-scrollable-body');
      if (tableBody) {
        tableBody.scrollTop = 0;
        tableBody.scrollLeft = 0;
      }
    }
  }


  /**
 * method for select global date range filter
 *
 * @param {*} event
 * @memberof VoyagesComponent
 */
  onDateRangeSelect(event) {
    if (this.filterDates[0] && this.filterDates[1]) {
      this.filterDateError = null;
      this.dateRangeFilter.hideOverlay();
      this.reloadVoyageHistory();
    }
    else if (this.filterDates[0] && !this.filterDates[1]) {
      this.filterDateError = { 'toDate': true };
    }
  }

  /**
  * method for reset global date range filter
  *
  * @param {*} event
  * @memberof VoyagesComponent
  */
  resetDateFilter(event) {
    this.filterDateError = null;
    this.filterDates = null;
    this.reloadVoyageHistory();
  }

  /**
 * set visibility of popup (show/hide)
 *
 * @param {*} event
 * @memberof VoyagesComponent
 */
  setPopupVisibility(emittedValue) {
    this.showDatePopup = emittedValue?.showPopup;
    if (emittedValue?.refresh) {
      this.reloadVoyageHistory();
    }
  }

  /**
  * on start/stop voyage
  *
  * @param {*} event
  * @memberof VoyagesComponent
  */
  async onButtonClick(event) {
    let dateValue: string;
    if (event.field === 'isStart') {
      this.isStart = true;
      dateValue = event?.data?.actualStartDate ? event?.data?.actualStartDate : event?.data?.plannedStartDate;
    } else if (event.field === 'isStop') {
      this.isStart = false;
      dateValue = event?.data?.actualEndDate ? event?.data?.actualEndDate : event?.data?.plannedEndDate;
    }
    this.defaultDate = dateValue !== undefined ? moment(dateValue).toDate() : new Date();
    this.selectedVoyageId = event?.data?.id;
    this.selectedLoadablePlanId = event?.data?.confirmedLoadableStudyId;
    this.showDatePopup = true;
  }

  /**
  * reloade voyage history data
  *
  * @param {*} event
  * @memberof VoyagesComponent
  */
  reloadVoyageHistory() {
    this.loading = true;
    this.getVoyageLists$.next();
  }

  /**
  * Value from new-voyage
  */
  displayPopUpTab(displayNew_: boolean) {
    this.display = displayNew_;
  }

  /**
   * Show new-voyage popup
   */
  showDialog() {
    this.display = true;
  }


  /**
   * function to convert string to Date object
   *
   * @param {string} dateTime
   * @return {*}  {Date}
   * @memberof PortsComponent
   */
  dateStringToDate(date: string): string {
    if (date) {
      const _dateTime = moment(date, 'DD-MM-YYYY').format(AppConfigurationService.settings?.dateFormat.split(' ')[0]);
      return _dateTime;
    }
  }
}

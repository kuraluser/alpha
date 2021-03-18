import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { IDataTableColumn } from '../../shared/components/datatable/datatable.model';
import { VoyageListTransformationService } from './services/voyage-list-transformation.service';
import { IVoyageList, IVoyageListResponse } from './models/voyage-list.model'
import { VoyageListApiService } from './services/voyage-list-api.service';
import { IDataStateChange } from '../admin/models/user-role-permission.model';
import { Subject } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { IVessel } from '../core/models/vessel-details.model';
import { VesselsApiService } from '../core/services/vessels-api.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { ConfirmationAlertService } from '../../shared/components/confirmation-alert/confirmation-alert.service';
import { MessageService } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';
import { first } from 'rxjs/operators';
import { AppConfigurationService } from '../../shared/services/app-configuration/app-configuration.service';
import { IPermission } from '../../shared/models/user-profile.model';
import { PermissionsService } from '../../shared/services/permissions/permissions.service';

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

  columns: IDataTableColumn[];
  voyageList: IVoyageList[];
  vesselDetails: IVessel;
  vesselId: number;
  filterDates: Date[];
  isStartVoyage = false;
  startVoyageId: number;
  permission: IPermission;

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
    private confirmationAlertService: ConfirmationAlertService,
    private messageService: MessageService,
    private translateService: TranslateService,
    private permissionsService: PermissionsService) { }

  async ngOnInit(): Promise<void> {
    this.ngxSpinnerService.show();
    this.permission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['voyagesStartStop'], false);
    this.first = 0;
    this.currentPage = 0;
    const res = await this.vesselsApiService.getVesselsInfo().toPromise();
    this.vesselDetails = res[0] ?? <IVessel>{};
    this.vesselId = this.vesselDetails?.id;
    this.columns = this.voyageListTransformationService.getVoyageListDatatableColumns(this.permission);
    this.getVoyageLists$.pipe(
      switchMap(() => {
        return this.voyageListApiService.getVoyageList(this.vesselId, this.pageState, this.voyageListTransformationService.formatDateTime(this.filterDates && this.filterDates[0]), this.voyageListTransformationService.formatDateTime(this.filterDates && this.filterDates[1]))
      })
    ).subscribe((voyageLIstResponse: IVoyageListResponse) => {
      this.getVoyageList(voyageLIstResponse);
      this.loading = false;
    });
    this.pageState = <IDataStateChange>{};
    this.getVoyageLists$.next();
    this.ngxSpinnerService.hide();
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
        this.currentPage -= 1;
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
          voyage.isStart = voyage.status === 'Active' ? false : true;
          voyage.isStop = voyage.status === 'Active' ? true : false;
          return voyage;
        });
      }else{
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
    this.loading = true;
    this.getVoyageLists$.next();
  }


  /**
 * method for select global date range filter
 *
 * @param {*} event
 * @memberof VoyagesComponent
 */
  onDateRangeSelect(event) {
    if (this.filterDates[0] && this.filterDates[1]) {
      this.dateRangeFilter.hideOverlay();
      this.loading = true;
      this.getVoyageLists$.next();
    }
  }

  /**
 * set visibility of popup (show/hide)
 *
 * @param {*} event
 * @memberof VoyagesComponent
 */
  setPopupVisibility(emittedValue) {
    this.isStartVoyage = emittedValue;
  }

  /**
  * on start/stop voyage
  *
  * @param {*} event
  * @memberof VoyagesComponent
  */
  async onButtonClick(event) {
    if (event.field === 'isStart') {
      this.startVoyageId = event?.data?.id;
      this.isStartVoyage = true;
    } else if (event.field === 'isStop') {
      const today = new Date();
      const translationKeys = await this.translateService.get(['VOYAGE_LIST_ACTIVE_VOYAGE_SUCCESSFULLY_STOP', 'VOYAGE_LIST_ACTIVE_VOYAGE_SUCCESS']).toPromise();
      this.confirmationAlertService.add({ key: 'confirmation-alert', sticky: true, severity: 'warn', summary: 'VOYAGE_LIST_ACTIVE_VOYAGE_STOP_SUMMARY', detail: 'VOYAGE_LIST_ACTIVE_VOYAGE_STOP_DETAILS', data: { confirmLabel: 'VOYAGE_LIST_ACTIVE_VOYAGE_STOP_CONFIRM_LABEL', rejectLabel: 'VOYAGE_LIST_ACTIVE_VOYAGE_STOP_REJECTION_LABEL' } });
      this.confirmationAlertService.confirmAlert$.pipe(first()).subscribe(async (response) => {
        if (response) {

          this.ngxSpinnerService.show();
          try {
            const result = await this.voyageListApiService.endVoyage(this.vesselId, event?.data?.id, this.voyageListTransformationService.formatDateTime(today, true)).toPromise();
            if (result.responseStatus.status === '200') {
              this.messageService.add({ severity: 'success', summary: translationKeys['VOYAGE_LIST_ACTIVE_VOYAGE_SUCCESS'], detail: translationKeys['VOYAGE_LIST_ACTIVE_VOYAGE_SUCCESSFULLY_STOP'] });
            }
          } catch (error) {
          }
          this.ngxSpinnerService.hide();
        }
      });

    }

  }

}

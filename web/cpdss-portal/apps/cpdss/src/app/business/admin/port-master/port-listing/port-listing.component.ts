import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BehaviorSubject } from 'rxjs';
import { debounceTime, switchMap } from 'rxjs/operators';
import { NgxSpinnerService } from 'ngx-spinner';

import { PermissionsService } from '../../../../shared/services/permissions/permissions.service';
import { AppConfigurationService } from '../../../../shared/services/app-configuration/app-configuration.service';
import { PortMasterTransformationService } from '../../services/port-master-transformation.service';
import { PortMasterApiService } from '../../services/port-master-api.service';

import { IPermission } from '../../../../shared/models/user-profile.model';
import { IPermissionContext, PERMISSION_ACTION } from '../../../../shared/models/common.model';
import { IDataTableColumn, IDataTablePageChangeEvent } from '../../../../shared/components/datatable/datatable.model';
import { IPortMasterList, IPortMasterListResponse, IPortMasterListStateChange } from '../../models/port.model';

/**
 * Component class of port lising component
 * @export
 * @class PortListingComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-port-listing',
  templateUrl: './port-listing.component.html',
  styleUrls: ['./port-listing.component.scss']
})
export class PortListingComponent implements OnInit, OnDestroy {

  private getPortMasterListState$ = new BehaviorSubject<IPortMasterListStateChange>(<IPortMasterListStateChange>{});

  public columns: IDataTableColumn[];
  public permission: IPermission;
  public addPortBtnPermissionContext: IPermissionContext;
  public loading: boolean;
  public totalRecords: number;
  public currentPage: number;
  public first: number;
  public portList: IPortMasterList[];
  public portListPageState: IPortMasterListStateChange;

  constructor(
    private permissionsService: PermissionsService,
    private portMasterTransformationService: PortMasterTransformationService,
    private portMasterApiService: PortMasterApiService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private ngxSpinnerService: NgxSpinnerService
  ) { }

  async ngOnInit() {
    this.ngxSpinnerService.show();
    this.first = 0;
    this.currentPage = 0;
    this.portListPageState = <IPortMasterListStateChange>{};
    this.getPortMasterListState$.pipe(
      debounceTime(1000),
      switchMap(() => {
        return this.portMasterApiService.getPortsList(this.portListPageState).toPromise();
      })
    ).subscribe((response: IPortMasterListResponse) => {
      try {
        if (response.responseStatus.status === '200') {
          this.showCurrentPageDetails(response);
        }
      } catch (error) {
        this.ngxSpinnerService.hide();
      }
      this.loading = false;
    });
    this.addPortBtnPermissionContext = { key: AppConfigurationService.settings.permissionMapping['PortListingComponent'], actions: [PERMISSION_ACTION.VIEW, PERMISSION_ACTION.ADD] };
    this.permission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['PortListingComponent']);
    this.columns = this.portMasterTransformationService.getPortListDatatableColumns(this.permission);
  }

  ngOnDestroy() {
    this.getPortMasterListState$.unsubscribe();
  }

  /**
   * Method to bind ports to datatable
   * @param {IPortMasterListResponse} portMaster
   * @memberof PortListingComponent
   */
  showCurrentPageDetails(portMaster: IPortMasterListResponse): void {
    this.ngxSpinnerService.hide();
    this.totalRecords = portMaster.totalElements;
    if (this.totalRecords && !portMaster?.ports?.length) {
      this.loading = true;
      this.currentPage = this.currentPage ? this.currentPage - 1 : 0;
      this.portListPageState['page'] = this.currentPage;
      this.getPortMasterListState$.next(this.portListPageState);
    }
    this.portList = this.totalRecords !== 0 ? portMaster.ports : [];
  }

  /**
   * Method to fetch page state change, filter & sorting values
   * @param {*} event
   * @memberof PortListingComponent
   */
  onDataStateChange(event: IDataTablePageChangeEvent) {
    this.portListPageState = {
      filter: event.filter,
      pageSize: event.paginator.rows,
      page: event.paginator.currentPage,
      sortBy: event.sort.sortField,
      orderBy: event.sort.sortOrder,
    };
    this.loading = true;
    this.getPortMasterListState$.next(this.portListPageState);
  }

  /**
   * Method to navigate to add port component on row selection to edit
   * @param {selectedPort}
   * @memberof PortListingComponent
   */
  onRowSelect(selectedPort) {
    if (this.permission?.edit) {
      this.router.navigate([selectedPort?.data?.id], {relativeTo: this.activatedRoute});
    }
  }

  /**
   * Method to navigate to Add port component
   *
   * @memberof PortListingComponent
   */
  addPort(): void {
    if (this.permission?.add) {
      this.router.navigate([0], {relativeTo: this.activatedRoute});
    }
  }

}

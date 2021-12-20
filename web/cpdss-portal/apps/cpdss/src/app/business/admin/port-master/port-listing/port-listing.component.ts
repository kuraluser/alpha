import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject } from 'rxjs';
import { debounceTime, switchMap } from 'rxjs/operators';
import { NgxSpinnerService } from 'ngx-spinner';

import { PortMasterTransformationService } from '../../services/port-master-transformation.service';
import { PortMasterApiService } from '../../services/port-master-api.service';

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

  public columns: IDataTableColumn[];
  public loading: boolean;
  public totalRecords: number;
  public currentPage: number;
  public first: number;
  public portList: IPortMasterList[];
  public portListPageState: IPortMasterListStateChange;

  private getPortMasterListState$ = new BehaviorSubject<IPortMasterListStateChange>(<IPortMasterListStateChange>{});

  constructor(
    private portMasterTransformationService: PortMasterTransformationService,
    private portMasterApiService: PortMasterApiService,
    private router: Router,
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
    this.columns = this.portMasterTransformationService.getPortListDatatableColumns();
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
   * Method to navigate to add port component on row selection
   * @param {selectedPort}
   * @memberof PortListingComponent
   */
  onRowSelect(selectedPort) {
    this.router.navigate([`/business/admin/port-listing/add-port/${selectedPort?.data?.id}`]);
  }

}

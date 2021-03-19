import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { DatePipe } from '@angular/common';
import { NgxSpinnerService } from 'ngx-spinner';

import { VesselsApiService } from '../../core/services/vessels-api.service';
import { PermissionsService } from '../../../shared/services/permissions/permissions.service';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';
import { CargoHistoryApiService } from './../services/cargo-history-api.service';
import { CargoHistoryTransformationService } from './../services/cargo-history-transformation.service';

import { IVessel } from '../../core/models/vessel-details.model';
import { IPermission } from './../../../shared/models/user-profile.model';
import { IDataTableColumn } from '../../../shared/components/datatable/datatable.model';
import { ICargoHistoryDataStateChange, ICargoHistoryDetails, ICargoHistoryResponse } from '../models/cargo-planning.model';

/**
 * Component to show Cargo history
 *
 * @export
 * @class CargoHistoryComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-cargo-history',
  templateUrl: './cargo-history.component.html',
  styleUrls: ['./cargo-history.component.scss']
})
export class CargoHistoryComponent implements OnInit, OnDestroy {

  vesselInfo: IVessel;
  userPermission: IPermission;
  cargoHistoryGridColumns: IDataTableColumn[];
  cargoHistoryGridData: ICargoHistoryDetails[];

  today = new Date();
  filteredDates: Date[];
  loading: boolean;
  totalRecords: number;
  currentPage: number;
  first: number;
  cargoHistoryPageState: ICargoHistoryDataStateChange;

  private getCargoHistoryDetails$ = new Subject<ICargoHistoryDataStateChange>();

  constructor(
    private vesselsApiService: VesselsApiService,
    private userPermissionService: PermissionsService,
    private cargoHistoryApiService: CargoHistoryApiService,
    private dateFormat: DatePipe,
    private cargoHistoryTransformationService: CargoHistoryTransformationService,
    private ngxSpinnerService: NgxSpinnerService
  ) { }

  async ngOnInit(): Promise<void> {
    this.ngxSpinnerService.show();
    this.first = 0;
    this.currentPage = 0;
    const res = await this.vesselsApiService.getVesselsInfo().toPromise();
    this.vesselInfo = res[0] ?? <IVessel>{};
    this.userPermission = this.userPermissionService.getPermission(AppConfigurationService.settings.permissionMapping['CargoHistoryComponent'], false);
    this.getCargoHistoryDetails$.pipe(
      switchMap(() => {
        return this.cargoHistoryApiService.getCargoHistoryData(this.cargoHistoryPageState);
      })
    ).subscribe((cargoHistorResponse: ICargoHistoryResponse) => {
      this.getCargoHistoryDetais(cargoHistorResponse);
      this.loading = false;
    });
    this.cargoHistoryPageState = <ICargoHistoryDataStateChange>{};
    this.getCargoHistoryDetails$.next();
    this.cargoHistoryGridColumns = this.cargoHistoryTransformationService.getCargoHistoryGridColumns(this.userPermission);
  }

  /**
   * function to bind cargo history details to table
   * @param {ICargoHistoryResponse} cargoHistoryResponse
   * @memberof CargoHistoryComponent
   */
  getCargoHistoryDetais(cargoHistoryResponse: ICargoHistoryResponse): void {
    this.ngxSpinnerService.hide();
    if (cargoHistoryResponse.responseStatus.status === '200') {
      const cargoHistory = cargoHistoryResponse.cargoHistory;
      this.totalRecords = cargoHistoryResponse.totalElements;
      if (this.totalRecords && !cargoHistory?.length) {
        this.currentPage -= 1;
        this.cargoHistoryPageState['page'] = this.currentPage;
        this.getCargoHistoryDetails$.next();
      }
      this.cargoHistoryGridData = cargoHistory;
    }
  }

  /**
   * function to get values on state change like search, sort, pagination
   * @param {*} event
   * @memberof CargoHistoryComponent
   */
  onDataStateChange(event: any): void {
    this.cargoHistoryPageState['page'] = event.paginator.currentPage;
    this.cargoHistoryPageState['pageSize'] = event.paginator.rows;
    this.cargoHistoryPageState['sortBy'] = event.sort.sortField;
    this.cargoHistoryPageState['orderBy'] = event.sort.sortOrder;
    this.cargoHistoryPageState['vesselName'] = event.filter?.vesselName;
    this.cargoHistoryPageState['loadingPortName'] = event.filter?.loadingPortName;
    this.cargoHistoryPageState['grade'] = event.filter?.grade;
    this.cargoHistoryPageState['loadedYear'] = event.filter?.loadedYear;
    this.cargoHistoryPageState['loadedMonth'] = event.filter?.loadedMonth;
    this.cargoHistoryPageState['loadedDay'] = event.filter?.loadedDay;
    this.cargoHistoryPageState['api'] = event.filter?.api;
    this.cargoHistoryPageState['temperature'] = event.filter?.temperature;
    this.loading = true;
    this.getCargoHistoryDetails$.next();
  }

  /**
   * function to get cargo history by date-range
   * @param {*} event
   * @memberof CargoHistoryComponent
   */
  onDateRangeSelect(): void {
    if (this.filteredDates[0] && this.filteredDates[1]) {
      this.cargoHistoryPageState['startDate'] = this.dateFormat.transform(this.filteredDates[0], 'dd-MM-yyyy HH:mm');
      this.cargoHistoryPageState['endDate'] = this.dateFormat.transform(this.filteredDates[1], 'dd-MM-yyyy HH:mm');
      this.loading = true;
      this.getCargoHistoryDetails$.next();
    }
  }

  /**
   * unsubscribing cargo history API observable
   * @memberof CargoHistoryComponent
   */
  ngOnDestroy() {
    this.getCargoHistoryDetails$.unsubscribe();
  }

}

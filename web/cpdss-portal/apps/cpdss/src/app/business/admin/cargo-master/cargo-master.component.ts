import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { IDataStateChange, IDataTableColumn, IDataTableEvent } from '../../../shared/components/datatable/datatable.model';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';
import { PermissionsService } from '../../../shared/services/permissions/permissions.service';
import { ICargoDetails, ICargosResponse } from '../models/cargo.model';
import { IPermissionContext, PERMISSION_ACTION } from '../../../shared/models/common.model';
import { CargoMasterTransformationService } from '../services/cargo-master-transformation.service';
import { CargoMasterApiService } from '../services/cargo-master-api.service';
import { BehaviorSubject, Subject } from 'rxjs';
import { debounceTime, switchMap } from 'rxjs/operators';
import { NgxSpinnerService } from 'ngx-spinner';

/**
 * Component class for cargo master
 *
 * @export
 * @class CargoMasterComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-cargo-master',
  templateUrl: './cargo-master.component.html',
  styleUrls: ['./cargo-master.component.scss']
})
export class CargoMasterComponent implements OnInit, OnDestroy {
  get cargos(): ICargoDetails[] {
    return this._cargos;
  }

  set cargos(value: ICargoDetails[]) {
    this._cargos = value.map(cargo => this.cargoMasterTransformationService.formatCargo(cargo));
  }

  columns: IDataTableColumn[];
  loading: boolean;
  totalRecords: number;
  currentPage: number;
  first: number;
  pageState: IDataStateChange;
  addCargoBtnPermissionContext: IPermissionContext;

  private _cargos: ICargoDetails[];
  private state$ = new BehaviorSubject<IDataStateChange>(<IDataStateChange>{});

  constructor(private permissionsService: PermissionsService,
    private cargoMasterTransformationService: CargoMasterTransformationService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private cargoMasterApiService: CargoMasterApiService,
    private ngxSpinnerService: NgxSpinnerService
  ) { }

  async ngOnInit(): Promise<void> {
    this.ngxSpinnerService.show();
    this.first = 0;
    this.currentPage = 0;
    this.pageState = <IDataStateChange>{};
    this.state$.pipe(
      debounceTime(1000), // By setting this we can avoid multiple api calls
      switchMap(() => {
        return this.cargoMasterApiService.getCargos(this.pageState);
      })
    ).subscribe((response: ICargosResponse) => {
      this.setCurrentPageDetails(response);
      this.loading = false;
    });

    this.addCargoBtnPermissionContext = { key: AppConfigurationService.settings.permissionMapping['CargoMasterComponent'], actions: [PERMISSION_ACTION.VIEW, PERMISSION_ACTION.ADD] };
    const permission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['CargoMasterComponent']);
    this.columns = this.cargoMasterTransformationService.getCargosDatatableColumns(permission);
  }

  ngOnDestroy() {
    this.state$.unsubscribe();
  }

  /**
   * Handler for add cargo button click
   *
   * @memberof CargoMasterComponent
   */
  addCargo() {
    this.router.navigate([0], { relativeTo: this.activatedRoute });
  }

  /**
   * Handler for edit row event
   *
   * @param {IDataTableEvent} event
   * @memberof CargoMasterComponent
   */
  ediCargo(event: IDataTableEvent) {
    this.router.navigate([event?.data?.id], { relativeTo: this.activatedRoute });
  }

  /**
  * Set current page details User Details
  * @memberof CargoMasterComponent
  */
  async setCurrentPageDetails(response: ICargosResponse) {
    this.ngxSpinnerService.hide();
    if (response.responseStatus.status === '200') {
      this.totalRecords = response?.totalElements;
      const cargos = response?.cargos;
      if (this.totalRecords && !cargos?.length) {
        this.loading = true;
        this.currentPage = this.currentPage ? this.currentPage - 1 : 0;
        this.pageState['page'] = this.currentPage;
        this.state$.next(this.pageState);
      }
      this.cargos = cargos;
    }
  }

}

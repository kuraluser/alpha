import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { IDataStateChange, IDataTableColumn, IDataTableEvent, IDataTablePageChangeEvent } from '../../../shared/components/datatable/datatable.model';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';
import { PermissionsService } from '../../../shared/services/permissions/permissions.service';
import { ICargoDetails, ICargosResponse, IDeleteCargoResponse } from '../models/cargo.model';
import { IPermissionContext, PERMISSION_ACTION } from '../../../shared/models/common.model';
import { CargoMasterTransformationService } from '../services/cargo-master-transformation.service';
import { CargoMasterApiService } from '../services/cargo-master-api.service';
import { BehaviorSubject } from 'rxjs';
import { debounceTime, switchMap } from 'rxjs/operators';
import { NgxSpinnerService } from 'ngx-spinner';
import { TranslateService } from '@ngx-translate/core';
import { ConfirmationService, MessageService } from 'primeng/api';

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
    this._cargos = value?.map(cargo => this.cargoMasterTransformationService.formatCargo(cargo));
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
    private ngxSpinnerService: NgxSpinnerService,
    private translateService: TranslateService,
    private confirmationService: ConfirmationService,
    private messageService: MessageService
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

  /**
   * Event handler for datatable change
   *
   * @param {IDataTablePageChangeEvent} event
   * @memberof CargoMasterComponent
   */
  onDataStateChange(event: IDataTablePageChangeEvent) {
    this.pageState = {
      filter: event.filter,
      name: event.filter?.name,
      pageSize: event.paginator.rows,
      page: event.paginator.currentPage,
      sortBy: event.sort.sortField,
      orderBy: event.sort.sortOrder,
    };
    this.loading = true;
    this.state$.next(this.pageState);
  }

  /**
   * Event handel for column click
   *
   * @param {IDataTableEvent} event
   * @memberof CargoMasterComponent
   */
  onColumnClick(event: IDataTableEvent) {
    if (event?.field !== 'actions') {
      this.ediCargo(event);
    }
  }

  /**
   * Event handler for delete action
   *
   * @param {IDataTableEvent} event
   * @memberof CargoMasterComponent
   */
  onDeleteRow(event: IDataTableEvent) {
    const cargoId = event.data?.id;
    const translationKeys = this.translateService.instant(['CARGO_DELETE_SUMMARY', 'CARGO_DELETE_DETAILS', 'CARGO_DELETE_CONFIRM_LABEL', 'CARGO_DELETE_REJECT_LABEL', 'CARGO_DELETE_SUCCESSFULLY', 'CARGO_DELETED_SUCCESS', 'CARGO_DELETED_ERROR', 'CARGO_DELETED_ERROR_CARGO_MAPPED']);

    this.confirmationService.confirm({
      key: 'confirmation-alert',
      header: translationKeys['CARGO_DELETE_SUMMARY'],
      message: translationKeys['CARGO_DELETE_DETAILS'],
      icon: 'pi pi-exclamation-triangle',
      acceptLabel: translationKeys['CARGO_DELETE_CONFIRM_LABEL'],
      acceptIcon: 'pi',
      acceptButtonStyleClass: 'btn btn-main mr-5',
      rejectVisible: true,
      rejectLabel: translationKeys['CARGO_DELETE_REJECT_LABEL'],
      rejectIcon: 'pi',
      rejectButtonStyleClass: 'btn btn-main',
      accept: async () => {
        this.ngxSpinnerService.show();
        try {
          const cargoDeleteRes: IDeleteCargoResponse = await this.cargoMasterApiService.deleteCargo(cargoId).toPromise();
          this.ngxSpinnerService.hide();
          if (cargoDeleteRes.responseStatus.status === '200') {
            this.state$.next(this.pageState);
            this.messageService.add({ severity: 'success', summary: translationKeys['CARGO_DELETED_SUCCESS'], detail: translationKeys['CARGO_DELETE_SUCCESSFULLY'] });
          }
          this.ngxSpinnerService.hide();
        }
        catch (error) {
          if (error?.error?.errorCode === 'ERR-RICO-327') {
            this.messageService.add({ severity: 'error', summary: translationKeys['CARGO_DELETED_ERROR'], detail: translationKeys['CARGO_DELETED_ERROR_CARGO_MAPPED'] });
          }
          this.ngxSpinnerService.hide();
        }
      }
    });
  }
}

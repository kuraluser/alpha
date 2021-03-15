import { Component, OnInit , OnDestroy } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';
import { first } from 'rxjs/operators';

import { TranslateService } from '@ngx-translate/core';
import { MessageService } from 'primeng/api';
import { NgxSpinnerService } from 'ngx-spinner';

import { IRoleDetails, IRoleResponse, IRoleDeleteResponse, IDataStateChange } from '../../models/user-role-permission.model';
import { IDataTableColumn, DATATABLE_ACTION } from '../../../../shared/components/datatable/datatable.model';

import { UserRolePermissionApiService } from '../../services/user-role-permission-api.service';
import { UserRolePermissionTransformationService } from '../../services/user-role-permission-transformation.service';
import { ConfirmationAlertService } from '../../../../shared/components/confirmation-alert/confirmation-alert.service';
import { AppConfigurationService } from '../../../../shared/services/app-configuration/app-configuration.service';
import { PermissionsService } from '../../../../shared/services/permissions/permissions.service';
import { IPermissionContext, PERMISSION_ACTION } from '../../../../shared/models/common.model';
import { Subject } from 'rxjs';
import { switchMap } from 'rxjs/operators';

/**
 * Component class of user allocation
 *
 * @export
 * @class UserRoleListingComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-user-role-listing',
  templateUrl: './user-role-listing.component.html',
  styleUrls: ['./user-role-listing.component.scss']
})
export class UserRoleListingComponent implements OnInit , OnDestroy {

  public columns: IDataTableColumn[];
  public roleList: IRoleDetails[];
  public editMode: false;
  public userRoleForm: FormGroup;
  public addUserRole: boolean;
  public addRoleBtnPermissionContext: IPermissionContext;
  public filterData = new Subject();
  public loading: boolean;
  public totalRecords: number;
  public currentPage: number;
  public first: number;
  public resetDataTable: boolean;
  public pageState:IDataStateChange;

  private getUserDetails$ = new Subject<IDataStateChange>();

  // public method
  constructor(
    private fb: FormBuilder,
    private activatedRoute: ActivatedRoute,
    private messageService: MessageService,
    private router: Router,
    private translateService: TranslateService,
    private ngxSpinnerService: NgxSpinnerService,
    private userRolePermissionApiService: UserRolePermissionApiService,
    private confirmationAlertService: ConfirmationAlertService,
    private permissionsService: PermissionsService,
    private userRolePermissionTransformationService: UserRolePermissionTransformationService
  ) { }

  /**
 * Component lifecycle ngOnit
 *
 * @returns {void}
 * @memberof UserRoleListingComponent
 */
  ngOnInit(): void {
    this.first = 0;
    this.currentPage = 0;
    this.columns = this.userRolePermissionTransformationService.getRoleListDatatableColumns();
    this.getPagePermission();
    this.getUserDetails$.pipe(
      switchMap(() => {
        return this.userRolePermissionApiService.getRoleDetails(this.pageState);
      })
    ).subscribe((roleDetailsRes: IRoleResponse) => {
      this.getUserDetails(roleDetailsRes);
      this.loading = false;
    });
    this.pageState = <IDataStateChange>{};
    this.getUserDetails$.next();
    this.addRoleBtnPermissionContext = { key: AppConfigurationService.settings.permissionMapping['UserRoleListing'], actions: [PERMISSION_ACTION.VIEW, PERMISSION_ACTION.ADD] };
  }

  /**
   * unsubscribe the observable
   *
   * @memberof UserRoleListingComponent
  */
 ngOnDestroy() {
  this.getUserDetails$.unsubscribe();
  }

  /**
   * Get page permission
   *
   * @memberof UserRoleListingComponent
   */
  getPagePermission() {
    const permission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['UserRoleListing']);
    if (permission.edit) {
      this.columns[this.columns.length - 1]['actions'].push(DATATABLE_ACTION.EDIT);
    }
    if (permission.delete) {
      this.columns[this.columns.length - 1]['actions'].push(DATATABLE_ACTION.DELETE);
    }
  }

  /**
  * get User Details
  * @memberof UserRoleListingComponent
  */
  async getUserDetails(roleDetailsRes:IRoleResponse) {
    this.ngxSpinnerService.hide();
    if (roleDetailsRes.responseStatus.status === '200') {
      this.totalRecords = roleDetailsRes.totalElements;
      const roles = roleDetailsRes.roles;
      if(this.totalRecords &&  !roles?.length) {
        this.currentPage -= 1; 
        this.pageState['page'] =  this.currentPage;
        this.getUserDetails$.next();
      }
      this.roleList = roles;
    }
  }

  /**
   * delete user role details
   * @param event 
   * @memberof UserRoleListingComponent
   */
  async onDeleteRow(event) {
    const roleId = event.data?.id;
    this.confirmationAlertService.add({ key: 'confirmation-alert', sticky: true, severity: 'warn', summary: 'USER_ROLE_DELETE_SUMMARY', detail: 'USER_ROLE_DELETE_DETAILS', data: { confirmLabel: 'USER_ROLE_DELETE_CONFIRM_LABEL', rejectLabel: 'USER_ROLE_DELETE_REJECT_LABEL' } });
    this.confirmationAlertService.confirmAlert$.pipe(first()).subscribe(async (response) => {
      if (response) {
        const translationKeys = await this.translateService.get(['ROLE_DELETE_SUCCESSFULLY', 'ROLE_DELETED_SUCCESS']).toPromise();
        this.ngxSpinnerService.show();
        const roleDeleteRes: IRoleDeleteResponse = await this.userRolePermissionApiService.deleteRole(roleId).toPromise();
        this.ngxSpinnerService.hide();
        if (roleDeleteRes.responseStatus.status === '200') {
          this.getUserDetails$.next();
          this.messageService.add({ severity: 'success', summary: translationKeys['ROLE_DELETED_SUCCESS'], detail: translationKeys['ROLE_DELETE_SUCCESSFULLY'] });
        }
      }
    });
  }

  /**
   * route to edit role details page while clicking cloumn
   * @param event 
   * @memberof UserRoleListingComponent
   */
  columnClick(event) {
    if (event.field === 'actions') {
      return;
    }
    const permission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['UserRoleListing']);
    if (permission.edit) {
      this.router.navigate([event.data.id], { relativeTo: this.activatedRoute.parent });
    }
  }

  /**
   * route to edit role details page
   * @param event 
   * @memberof UserRoleListingComponent
   */
  editRow(event) {
    this.router.navigate([event.data.id], { relativeTo: this.activatedRoute.parent });
  }

  /**
   * Add Role
   * @param {boolean} status
   * @memberof UserRoleListingComponent
   */
  addRolePopUp(status: boolean) {
    this.addUserRole = status;
  }

  /**
   * state of pagination , filter , sort
   * @param {*} event
   * @memberof UserRoleListingComponent
  */
  onDataStateChange(event: any) {
    this.pageState = {
      name: event.filter?.name,
      desc: event.filter?.description,
      pageSize: event.paginator.rows,
      page: event.paginator.currentPage,
      sortBy: event.sort.sortField,
      orderBy: event.sort.sortOrder,
    };
    this.loading = true;
    this.getUserDetails$.next();
  }

  /**
   * navigation to first page
   * @memberof UserRoleListingComponent
  */
  roleSaved() {
    this.first = 0;
    this.resetDataTable = true;
    this.pageState = <IDataStateChange>{};
    this.getUserDetails$.next();
  }
}

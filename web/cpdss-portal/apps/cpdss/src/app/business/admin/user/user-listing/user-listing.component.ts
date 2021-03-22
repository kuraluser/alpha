import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { first } from 'rxjs/operators';

import { NgxSpinnerService } from 'ngx-spinner';
import { TranslateService } from '@ngx-translate/core';
import { MessageService } from 'primeng/api';

import { IDataTableColumn, DATATABLE_ACTION , DATATABLE_FIELD_TYPE , DATATABLE_BUTTON } from '../../../../shared/components/datatable/datatable.model';
import { IPermissionContext, PERMISSION_ACTION } from '../../../../shared/models/common.model';
import { IUserResponse, IUserDetails , IRoleDetails , IUserDetalisValueObject , IUserDeleteResponse } from '../../models/user.model';


import { PermissionsService } from '../../../../shared/services/permissions/permissions.service';
import { AppConfigurationService } from '../../../../shared/services/app-configuration/app-configuration.service';
import { ConfirmationAlertService } from '../../../../shared/components/confirmation-alert/confirmation-alert.service';
import { UserTransformationService } from '../../services/user-transformation.service';
import { UserApiService } from '../../services/user-api.service';

/**
 * Component class of add user
 *
 * @export
 * @class UserListingComponent
 * @implements {OnInit}
 */
 @Component({
  selector: 'cpdss-portal-user-listing',
  templateUrl: './user-listing.component.html',
  styleUrls: ['./user-listing.component.scss']
})
export class UserListingComponent implements OnInit {

  public columns: IDataTableColumn[];
  public userList: IUserDetalisValueObject[];
  public editMode: boolean;
  public roles: IRoleDetails[]; 
  public addUser: boolean;
  public resetPasswordPopup: boolean;
  public addUserBtnPermissionContext: IPermissionContext;
  public userForm: FormGroup;
  public popupStatus: string;
  public userDetails: IUserDetails;
  public maxUserCount: number;
  public userDetail: any;

  constructor(
    private fb: FormBuilder,
    private ngxSpinnerService: NgxSpinnerService,
    private translateService: TranslateService,
    private messageService: MessageService,
    private userApiService: UserApiService,
    private userTransformationService: UserTransformationService,
    private permissionsService: PermissionsService,
    private confirmationAlertService: ConfirmationAlertService,
  ) { }

  /**
 * Component lifecycle ngOnit
 *
 * @returns {void}
 * @memberof UserListingComponent
 */
  ngOnInit(): void {
    this.columns = this.userTransformationService.getRoleListDatatableColumns();
    this.getPagePermission();
    this.getUserDetails();
    this.addUserBtnPermissionContext = { key: AppConfigurationService.settings.permissionMapping['UserListingComponent'], actions: [PERMISSION_ACTION.VIEW, PERMISSION_ACTION.ADD] };
  }

  /**
  * get user details
  * @memberof UserListingComponent
  */
  async getUserDetails() {
    this.ngxSpinnerService.show();
    const userRes: IUserResponse = await this.userApiService.getUserDetails().toPromise();
    this.userDetail =  JSON.parse(localStorage.getItem('userDetails'));
    this.ngxSpinnerService.hide();
    if(userRes.responseStatus.status === '200') {
      const userList = userRes?.users;
      this.maxUserCount = userRes.maxUserCount;
      this.roles = userRes.roles;
      const _userList = userList?.map((user) => {
        const userData = this.userTransformationService.getPortAsValueObject(user, this.userDetail.id);
        return userData;
      });
      this.userList = _userList ? _userList : [];
    }
    
  }

  /**
  * Get page permission
  *
  * @memberof UserListingComponent
  */
  getPagePermission() {
    const permission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['UserListingComponent']);
    const permissionResetPassword = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['UserResetPassword'], false);
    if(permissionResetPassword?.view) {
      this.columns.push({
        field: 'buttons',
        header: 'RESET_PASSWORD_HEADING',
        fieldClass: 'text-center',
        fieldColumnClass: 'text-center',
        fieldType: DATATABLE_FIELD_TYPE.BUTTON,
        buttons: [
          {type: DATATABLE_BUTTON.RESETPASSWORD , field: 'isResetPassword' , icons: '' , class: 'reset-btn'}
        ]
      })
    }
    const actions = [];
    if (permission.edit) {
      actions.push(DATATABLE_ACTION.EDIT);
    }
    if (permission.delete) {
      actions.push(DATATABLE_ACTION.DELETE);
    }
    if(actions.length) {
      this.columns.push( {
        field: 'actions',
        header: '',
        fieldType: DATATABLE_FIELD_TYPE.ACTION,
        actions: actions
      })
    }
  }

  /**
  * reset password 
  * @param {any} data
  * @memberof UserListingComponent
  */
  public resetPassword(data: any) {
    this.userDetails = data.data;
    this.resetPopupStatus(true);
  }

  /**
 * Add Role
 * @param {boolean} viewPopup
 * @param {string} status
 * @memberof UserListingComponent
 */
  async addUserPopUp(viewPopup: boolean , status: string) {
    if (this.maxUserCount > this.userList?.length) {
      this.addUser = viewPopup;
      this.popupStatus = status;
    } else {
      const translationKeys = await this.translateService.get([ 'USER_CREATE_ERROR', 'MAXIMUM_USER_EXCEED']).toPromise();
      this.messageService.add({ severity: 'error', summary: translationKeys['USER_CREATE_ERROR'], detail: translationKeys['MAXIMUM_USER_EXCEED'] });
    }
    
  }

  /**
 * view user details
 * @param {string} status
 * @param {*} data
 * @memberof UserListingComponent
 */
  viewUserDetails(data: any , status: string) {
    if(data.field === 'actions' || data.field === 'buttons') {
      return;
    }
    this.userDetails = data.data;
    this.popupStatus = status;
    this.addUser = true;
  }
  
    /**
 * reset popup show/hide status
 * @param {boolean} status
 * @memberof UserListingComponent
 */
  resetPopupStatus(status: boolean) {
    this.resetPasswordPopup = status;
  }

    /**
   * route to edit role details page while clicking cloumn
   * @param {any} event 
   * @param {string} status
   * @memberof UserListingComponent
   */
  editUser(event: any, status: string) {
    if (event.field === 'buttons') {
      return;
    }
    this.userDetails = event.data;
    this.popupStatus = status;
    this.addUser = true;
  }
  
  
    /**
   * delete user 
   * @param event 
   * @memberof ResetPasswordComponent
   */
  async onDeleteRow(event) {
    const userId = event.data?.id;
    this.confirmationAlertService.add({ key: 'confirmation-alert', sticky: true, severity: 'warn', summary: 'USER_DELETE_SUMMARY', detail: 'USER_DELETE_DETAILS', data: { confirmLabel: 'USER_DELETE_CONFIRM_LABEL', rejectLabel: 'USER_DELETE_REJECT_LABEL' } });
    this.confirmationAlertService.confirmAlert$.pipe(first()).subscribe(async (response) => {
      if (response) {
        const translationKeys = await this.translateService.get(['USER_DELETE_SUCCESSFULLY', 'USER_DELETED_SUCCESS']).toPromise();
        this.ngxSpinnerService.show();
        const roleDeleteRes: IUserDeleteResponse = await this.userApiService.deleteUser(userId).toPromise();
        this.ngxSpinnerService.hide();
        if (roleDeleteRes.responseStatus.status === '200') {
          this.getUserDetails();
          this.messageService.add({ severity: 'success', summary: translationKeys['USER_DELETED_SUCCESS'], detail: translationKeys['USER_DELETE_SUCCESSFULLY'] });
        }
      }
    });
  }

}

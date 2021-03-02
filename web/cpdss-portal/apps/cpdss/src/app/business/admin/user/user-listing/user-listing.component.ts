import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { NgxSpinnerService } from 'ngx-spinner';
import { TranslateService } from '@ngx-translate/core';
import { MessageService } from 'primeng/api';

import { IDataTableColumn, DATATABLE_ACTION } from '../../../../shared/components/datatable/datatable.model';
import { IPermissionContext, PERMISSION_ACTION } from '../../../../shared/models/common.model';
import { IUserResponse, IUserDetails , IRoleDetails , IUserDetalisValueObject } from '../../models/user.model';


import { PermissionsService } from '../../../../shared/services/permissions/permissions.service';
import { AppConfigurationService } from '../../../../shared/services/app-configuration/app-configuration.service';

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
  public addUserBtnPermissionContext: IPermissionContext;
  public userForm: FormGroup;
  public popupStatus: string;
  public userDetails: IUserDetails;
  public maxUserCount: number;

  constructor(
    private fb: FormBuilder,
    private ngxSpinnerService: NgxSpinnerService,
    private translateService: TranslateService,
    private messageService: MessageService,
    private userApiService: UserApiService,
    private userTransformationService: UserTransformationService,
    private permissionsService: PermissionsService,
  ) { }

  /**
 * Component lifecycle ngOnit
 *
 * @returns {void}
 * @memberof UserListingComponent
 */
  ngOnInit(): void {
    this.getPagePermission();
    this.columns = this.userTransformationService.getRoleListDatatableColumns();
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
    this.ngxSpinnerService.hide();
    if(userRes.responseStatus.status === '200') {
      const userList = userRes?.users;
      this.maxUserCount = userRes.maxUserCount;
      this.roles = userRes.roles;
      const _userList = userList?.map((user) => {
        const userData = this.userTransformationService.getPortAsValueObject(user);
        return userData;
      });
      this.userList = _userList;
    }
    
  }

  /**
  * Get page permission
  *
  * @memberof UserListingComponent
  */
  getPagePermission() {
    const permission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['UserListingComponent']);
  }

  /**
  * reset password 
  * @memberof UserListingComponent
  */
  public resetPassword($event) {
    const event = $event;
  }

  /**
 * Add Role
 * @param {boolean} viewPopup
 * @param {string} status
 * @memberof UserListingComponent
 */
  async addUserPopUp(viewPopup: boolean , status: string) {
    if (this.maxUserCount >= this.userList?.length) {
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


}

import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

import { IDataTableColumn, DATATABLE_ACTION } from '../../../../shared/components/datatable/datatable.model';
import { IPermissionContext, PERMISSION_ACTION } from '../../../../shared/models/common.model';
import { IUserResponse, IUserDetails , USER_POPUP_SELECTIONMODE } from '../../models/user.model';


import { PermissionsService } from '../../../../shared/services/permissions/permissions.service';
import { AppConfigurationService } from '../../../../shared/services/app-configuration/app-configuration.service';

import { UserTransformationService } from '../../services/user-transformation.service';
import { UserApiService } from '../../services/user-api.service';

@Component({
  selector: 'cpdss-portal-user-listing',
  templateUrl: './user-listing.component.html',
  styleUrls: ['./user-listing.component.scss']
})
export class UserListingComponent implements OnInit {

  public columns: IDataTableColumn[];
  public userList: IUserDetails[];
  public editMode: boolean;
  public addUser: boolean;
  public addUserBtnPermissionContext: IPermissionContext;
  public userForm: FormGroup;
  public popupStatus: string;
  public userDetails: IUserDetails;

  constructor(
    private fb: FormBuilder,
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
    const userRes: IUserResponse = await this.userApiService.getUserDetails().toPromise();
    const userList = userRes?.users;
    const _userList = userList?.map((user) => {
      const userData = this.userTransformationService.getPortAsValueObject(user);
      return userData;
    });
    this.userList = _userList;
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
  addUserPopUp(viewPopup: boolean , status: string) {
    this.addUser = viewPopup;
    this.popupStatus = status;
  }

  /**
 * view user details
 * @param {string} status
 * @param {*} data
 * @memberof UserListingComponent
 */
  viewUserDetails(data: any , status: string) {
    this.userDetails = data.data;
    this.popupStatus = status;
    this.addUser = true;
  }


}

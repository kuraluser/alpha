import { Component, OnInit , Output , EventEmitter } from '@angular/core';

import { IUserDetail, IUserDetailsResponse } from '../../../models/user-role-permission.model';
import { UserRolePermissionApiService } from '../../../services/user-role-permission-api.service';

/**
 * Component class of user allocation
 *
 * @export
 * @class UserAllocateComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-user-allocate',
  templateUrl: './user-allocate.component.html',
  styleUrls: ['./user-allocate.component.scss']
})
export class UserAllocateComponent implements OnInit {

  userDetails: IUserDetail[] = [];
  selectedUser: IUserDetail[] = [];
  totalColSpan: number = 3;
  
  @Output() selectedUserDetails = new EventEmitter<IUserDetail[]>();

  // public method
  constructor(
    private userRolePermissionApiService: UserRolePermissionApiService
  ) { }

  /**
 * Component lifecycle ngOnit
 *
 * @returns {void}
 * @memberof UserAllocateComponent
 */
  ngOnInit(): void {
    this.getUserDetails();
  }

  /**
  * get User Details
  * @memberof UserAllocateComponent
  */
  async getUserDetails() {
    const userDetailsRes: IUserDetailsResponse = await this.userRolePermissionApiService.getUserDetails().toPromise();
    if (userDetailsRes.responseStatus.status === '200') {
      let userDetails = userDetailsRes.users;
      userDetails?.map((userDetail) => {
        this.userDetails.push({...userDetail , 'name': userDetail.firstName + ' ' + userDetail.lastName})
      });
    }
  }

  /**
  * emit selected user details
  * @memberof UserAllocateComponent
  */
  onRowSelect() {
    this.selectedUserDetails.emit(this.selectedUser);
  }
}

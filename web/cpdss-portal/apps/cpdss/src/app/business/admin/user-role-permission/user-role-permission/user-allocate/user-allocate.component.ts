import { Component, OnInit , Output , EventEmitter, Input } from '@angular/core';

import { IUserDetail, IUserDetailsResponse } from '../../../models/user-role-permission.model';
import { UserRolePermissionApiService } from '../../../services/user-role-permission-api.service';

@Component({
  selector: 'cpdss-portal-user-allocate',
  templateUrl: './user-allocate.component.html',
  styleUrls: ['./user-allocate.component.scss']
})

/**
 * Component class of user allocation
 *
 * @export
 * @class UserAllocateComponent
 * @implements {OnInit}
 */
export class UserAllocateComponent implements OnInit {

  _userDetails: IUserDetail[] = [];
  _selectedUser: IUserDetail[] = [];
  totalColSpan: number = 3;
  
  @Output() selectedUserDetails = new EventEmitter<IUserDetail[]>();
  @Input()
  set selectedUser(selectedUser: IUserDetail[]) {
    this._selectedUser = selectedUser;
  }
  get selectedUser() {
    return [...this._selectedUser];
  }

  @Input()
  set userDetails(userDetails: IUserDetail[]) {
    this._userDetails = userDetails;
  }
  get userDetails() {
    return this._userDetails;
  }

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
  }

  /**
  * emit selected user details
  * @memberof UserAllocateComponent
  */
  onRowSelect() {
    this.selectedUserDetails.emit(this.selectedUser);
  }
}

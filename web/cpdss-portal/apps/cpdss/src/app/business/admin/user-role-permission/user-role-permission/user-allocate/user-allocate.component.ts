import { Component, OnInit , Output , EventEmitter, Input, ViewChild } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

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
  totalColSpan: number;
  userInfo: any;
  
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
    this.scrollIntoUser();
    return this._userDetails;
  }

  @ViewChild('datatable') userTable;

  // public method
  constructor(
    private userRolePermissionApiService: UserRolePermissionApiService,
    private activatedRoute: ActivatedRoute
  ) { }

  /**
 * Component lifecycle ngOnit
 *
 * @returns {void}
 * @memberof UserAllocateComponent
 */
  ngOnInit(): void {
    this.userInfo = this.activatedRoute.snapshot.params.userId ? this.activatedRoute.snapshot.params.userId : null;
    this.totalColSpan = 3;
  }

   /**
 * scroll into user
 *
 * @returns {void}
 * @memberof UserAllocateComponent
 */
  scrollIntoUser(): void{ 
    if(this.userTable && this._userDetails && this._userDetails.length && this.userInfo){
      const rowEl = this.userTable.el.nativeElement.querySelector('[id="'+this.userInfo+'"]');
      if(rowEl){
        rowEl.scrollIntoView({behavior: "smooth", inline: "start", block: "start"});
      }
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

import { Injectable } from '@angular/core';

import { AdminModule } from '../admin.module';

import { DATATABLE_ACTION, DATATABLE_FIELD_TYPE, DATATABLE_FILTER_MATCHMODE, DATATABLE_FILTER_TYPE, IDataTableColumn , DATATABLE_BUTTON } from '../../../shared/components/datatable/datatable.model';
import { IUserDetails , IUserResponse , IUserDetalisValueObject } from '../models/user.model';

/**
 * Api Service for user  tranformation of data
 *
 * @export
 * @class UserTransformationService
*/
@Injectable({
  providedIn: AdminModule
})
export class UserTransformationService {

  
  constructor() { }

   /**
   * Method for setting User List grid column
   *
   * @returns {IDataTableColumn[]}
   * @memberof UserTransformationService
  */
  getRoleListDatatableColumns(): IDataTableColumn[] {
    return [
      {
        field: 'slNo',
        header: 'OBQ_SL',
        fieldType: DATATABLE_FIELD_TYPE.SLNO,
        fieldHeaderClass: 'column-sl',
        fieldClass: 'sl'
      },
      {
        field: 'username',
        header: 'USER NAME',
        filter: true,
        filterPlaceholder: 'SEARCH_USER',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'username',
        sortable: true,
        sortField: 'username'
      },
      {
        field: 'designation',
        header: 'DESIGNATION',
        filter: true,
        filterPlaceholder: 'SEARCH_DESGNATION',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'description'
      },
      {
        field: 'role',
        header: 'ROLE',
        filter: true,
        filterPlaceholder: 'SEARCH_ROLE',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'role',
        sortable: true,
        sortField: 'role'
      },
      {
        field: 'buttons',
        header: '',
        fieldType: DATATABLE_FIELD_TYPE.BUTTON,
        buttons: [
          {type: DATATABLE_BUTTON.RESETPASSWORD , field: 'isResetPassword' , icons: '' , class: '' , label: ''}
        ]
      },
      {
        field: 'actions',
        header: '',
        fieldType: DATATABLE_FIELD_TYPE.ACTION,
        actions: [DATATABLE_ACTION.DELETE, DATATABLE_ACTION.EDIT]
      }
    ]
  }

    /**
   * Method for converting User details data to value object model
   *
   * @param {IUserDetails} userDetails
   * @returns {IUserDetalisValueObject}
   * @memberof UserTransformationService
   */
  getPortAsValueObject(userDetails: IUserDetails): IUserDetalisValueObject {
    const _user = <IUserDetalisValueObject>{};
    _user.id = userDetails.id;
    _user.username = userDetails.username;
    _user.designation = userDetails.designation;
    _user.firstName = userDetails.firstName;
    _user.lastName = userDetails.lastName;
    _user.role = userDetails.role;
    _user.isActionsEnabled = false;
    _user.isResetPassword = true;
    return _user;
  }
}

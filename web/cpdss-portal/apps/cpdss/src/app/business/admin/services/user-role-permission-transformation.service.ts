import { Injectable } from '@angular/core';

import { AdminModule } from '../admin.module';

import { DATATABLE_ACTION, DATATABLE_FIELD_TYPE, DATATABLE_FILTER_MATCHMODE, DATATABLE_FILTER_TYPE, IDataTableColumn } from '../../../shared/components/datatable/datatable.model';

/**
 * Api Service for user role tranformation of data
 *
 * @export
 * @class UserRolePermissionTransformationService
*/

@Injectable({
  providedIn: AdminModule
})

export class UserRolePermissionTransformationService {

  constructor() { }

    /**
   * Method for setting Role List grid column
   *
   * @returns {IDataTableColumn[]}
   * @memberof UserRolePermissionTransformationService
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
        field: 'name',
        header: 'ROLE NAME',
        filter: true,
        filterPlaceholder: 'SEARCH_ROLE_NAME',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'name',
        sortable: true,
        sortField: 'name',
      },
      {
        field: 'description',
        header: 'DESCRIPTION',
        filter: true,
        filterPlaceholder: 'SEARCH_ROLE_DESCRIPTION',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'description',
      },
      {
        field: 'actions',
        header: '',
        fieldType: DATATABLE_FIELD_TYPE.ACTION,
        actions: []
      }
    ]
  }

  /**
   * Set validation Error to form control
   * @memberof UserRolePermissionTransformationService
  */
  setValidationErrorMessage() {
    return {
      roleName: {
        'required': 'ADD_ROLE_POPUP_ROLE_REQUIRED_ERROR',
        'pattern':'ROLE_NAME_VALIDATION_ERROR',
        'maxlength': 'ROLE_NAME_MAX_LENGTH'
      },
      roleDescription: {
        'required': 'ADD_ROLE_POPUP_DESCRIPTION_REQUIRED_ERROR'
      }
    }
  }
}

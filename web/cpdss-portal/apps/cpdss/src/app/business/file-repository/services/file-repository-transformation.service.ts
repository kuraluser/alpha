import { Injectable } from '@angular/core';
import { IDataTableColumn, DATATABLE_FILTER_TYPE, DATATABLE_FILTER_MATCHMODE, DATATABLE_FIELD_TYPE, DATATABLE_BUTTON, DATATABLE_ACTION } from './../../../shared/components/datatable/datatable.model'

/**
 * Transformation Service for file repository
 *
 * @export
 * @class FileRepositoryTransformationService
 */

@Injectable()
export class FileRepositoryTransformationService {

  sectionList = [
    { id: 1, label: 'Any' },
    { id: 2, label: 'Bunkering' },
    { id: 3, label: 'Discharging' },
    { id: 4, label: 'Discharge Study' },
    { id: 5, label: 'Loadable Study' },
    { id: 6, label: 'Loading' },
  ];

  category = [
    { id: 1, label: 'Any' },
    { id: 2, label: 'Cargo' },
    { id: 3, label: 'Port' },
    { id: 4, label: 'Process' },
    { id: 5, label: 'Vessel' },
  ]
  constructor() { }

  /**
   * Method for getting table columns
   * @memberof FileRepositoryTransformationService
   */
  repositoryTableColumn(permission) {
    let columns: IDataTableColumn[] = [
      {
        header: 'FILE_REPOSITORY_SL',
        fieldType: DATATABLE_FIELD_TYPE.SLNO
      },
      {
        field: 'voyageNumber',
        header: 'FILE_REPOSITORY_VOY_NO',
        filter: true,
        filterPlaceholder: 'FILE_REPOSITORY_SEARCH_VOY',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'voyageNumber',
        filterByServer: true,
        sortable: true,
        sortField: 'voyageNumber'
      },
      {
        field: 'fileName',
        header: 'FILE_REPOSITORY_FILE_NAME',
        fieldClass: 'break-all',
        filter: true,
        filterPlaceholder: 'FILE_REPOSITORY_SEARCH_FILE',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'fileName',
        filterByServer: true,
        sortable: true,
        sortField: 'fileName'
      },
      {
        field: 'fileType',
        header: 'FILE_REPOSITORY_FILE_TYPE',
        fieldType: DATATABLE_FIELD_TYPE.FILEICONS,
        iconField: 'fileIcon',
        fieldValue: 'fileType',
        filter: true,
        showTooltip: true,
        filterPlaceholder: 'FILE_REPOSITORY_SEARCH_TYPE',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'fileType',
        filterByServer: true,
        sortable: true,
        sortField: 'fileType'
      },
      {
        field: 'section',
        header: 'FILE_REPOSITORY_FILE_SECTION',
        filter: true,
        filterPlaceholder: 'FILE_REPOSITORY_SEARCH_SECTION',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'section',
        filterByServer: true,
        sortable: true,
        sortField: 'section'
      },
      {
        field: 'category',
        header: 'FILE_REPOSITORY_FILE_CATEGORY',
        filter: true,
        filterPlaceholder: 'FILE_REPOSITORY_SEARCH_CATEGORY',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'category',
        filterByServer: true,
        sortable: true,
        sortField: 'category'
      },
      {
        field: 'uploadType',
        header: 'FILE_REPOSITORY_UPLOAD_TYPE',
        sortable: true,
        sortField: 'isSystemGenerated'
      },
      {
        field: 'createdDate',
        header: 'FILE_REPOSITORY_FILE_DATE',
        filter: true,
        filterPlaceholder: 'FILE_REPOSITORY_SEARCH_DATE',
        filterType: DATATABLE_FILTER_TYPE.DATE,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'createdDate',
        filterByServer: true,
        sortable: true,
        sortField: 'createdDate'
      }
    ];
    if (permission) {
      const actions: DATATABLE_ACTION[] = [];
      if (permission?.edit) {
        actions.push(DATATABLE_ACTION.EDIT);
      }
      if (permission?.delete) {
        actions.push(DATATABLE_ACTION.DELETE);
      }
      if (permission?.view) {
        actions.push(DATATABLE_ACTION.VIEW);
      }
      const action: IDataTableColumn = {
        field: 'actions',
        header: '',
        fieldType: DATATABLE_FIELD_TYPE.ACTION,
        actions: actions
      };
      columns = [...columns, action];
    }
    return columns;
  }

  /**
   * Method for getting validation messages
   * @memberof FileRepositoryTransformationService
   */
  setValidationErrorMessage() {
    return {
      voyageNo: {
        'required': 'FILE_REPOSITORY_VOYAGE_REQUIRED',
      },
      section: {
        'required': 'FILE_REPOSITORY_SECTION_REQUIRED',
      },
      category: {
        'required': 'FILE_REPOSITORY_CATEGORY_REQUIRED',
      },
      fileName: {
        'required': 'FILE_REPOSITORY_FILE_REQUIRED',
      },

    }
  }
}

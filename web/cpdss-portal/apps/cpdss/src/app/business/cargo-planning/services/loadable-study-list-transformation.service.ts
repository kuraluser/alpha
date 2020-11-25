import { Injectable } from '@angular/core';
import { DATATABLE_ACTION, DATATABLE_FIELD_TYPE, DATATABLE_FILTER_MATCHMODE, DATATABLE_FILTER_TYPE, IDataTableColumn } from '../../../shared/components/datatable/datatable.model';

@Injectable({
  providedIn: 'root'
})
export class LoadableStudyListTransformationService {

  constructor() { }

  /**
*  loadable study details transformation service
*/
  getLoadableStudyListDatatableColumns(): IDataTableColumn[] {
    return [
      {
        field: 'name',
        header: 'LOADABLE_STUDY_LIST_GRID_LOADABLE_STUDY_NAME_LABEL',
        sortable: false,
        filter: true,
        editable: false,
        filterPlaceholder: 'LOADABLE_STUDY_LIST_SEARCH_BY_NAME',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,

      },
      {
        field: 'detail',
        header: 'LOADABLE_STUDY_LIST_GRID_ENQUIRY_DETAILS_LABEL',
        sortable: false,
        filter: true,
        editable: false,
        filterPlaceholder: 'LOADABLE_STUDY_LIST_SEARCH_BY_DETAILS',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'detail',
      },
      {
        field: 'status',
        header: 'LOADABLE_STUDY_LIST_GRID_STATUS_LABEL',
        filter: true,
        sortable: false,
        editable: false,
        filterPlaceholder: 'LOADABLE_STUDY_LIST_SEARCH_BY_STATUS',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'status',
      },
      {
        field: 'createdDate',
        header: 'LOADABLE_STUDY_LIST_GRID_DATE_LABEL',
        sortable: false,
        filter: true,
        editable: false,
        filterPlaceholder: 'LOADABLE_STUDY_LIST_SEARCH_BY_DATE',
        filterType: DATATABLE_FILTER_TYPE.DATE,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.EQUALS,
        filterField: 'createdDate',
        filterFieldMaxvalue: new Date()
      },
      {
        field: '',
        header: '',
        fieldType: DATATABLE_FIELD_TYPE.ACTION,
        fieldValueIcon: '##',
        actions: [DATATABLE_ACTION.VIEW]
      }
    ]
  }
}

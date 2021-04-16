import { Injectable } from '@angular/core';
import { DATATABLE_ACTION, DATATABLE_FIELD_TYPE, DATATABLE_FILTER_MATCHMODE, DATATABLE_FILTER_TYPE, IDataTableColumn } from '../../../shared/components/datatable/datatable.model';
import { IPermission } from '../../../shared/models/user-profile.model';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';
import { TimeZoneTransformationService } from '../../../shared/services/time-zone-conversion/time-zone-transformation.service';
import { VOYAGE_STATUS } from '../../core/models/common.model';

@Injectable({
  providedIn: 'root'
})
export class LoadableStudyListTransformationService {

  constructor(private timeZoneTransformationService: TimeZoneTransformationService) { }

  /**
*  loadable study details transformation service
*/
  getLoadableStudyListDatatableColumns(permission: IPermission, voyageStatusId: VOYAGE_STATUS): IDataTableColumn[] {
    let columns: IDataTableColumn[] = [
      {
        field: 'slNo',
        header: 'SL',
        fieldType: DATATABLE_FIELD_TYPE.SLNO,
        fieldHeaderClass: 'column-sl',
        fieldClass: 'sl'
      },
      {
        field: 'name',
        header: 'LOADABLE_STUDY_LIST_GRID_LOADABLE_STUDY_NAME_LABEL',
        sortable: true,
        filter: true,
        editable: false,
        filterPlaceholder: 'LOADABLE_STUDY_LIST_SEARCH_BY_NAME',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,

      },
      {
        field: 'detail',
        header: 'LOADABLE_STUDY_LIST_GRID_ENQUIRY_DETAILS_LABEL',
        sortable: true,
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
        sortable: true,
        editable: false,
        filterPlaceholder: 'LOADABLE_STUDY_LIST_SEARCH_BY_STATUS',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'status',
      },
      {
        field: 'createdDate',
        header: 'LOADABLE_STUDY_LIST_GRID_DATE_LABEL',
        sortable: true,
        filter: true,
        editable: false,
        filterPlaceholder: 'LOADABLE_STUDY_LIST_SEARCH_BY_DATE',
        filterType: DATATABLE_FILTER_TYPE.DATE,
        dateFormat: this.timeZoneTransformationService.getMappedConfigurationDateFormat(AppConfigurationService.settings?.dateFormat),
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'createdDate',
        filterFieldMaxvalue: new Date()
      },
      {
        field: 'lastEdited',
        header: 'LOADABLE_STUDY_LIST_GRID_DATE_LAST_EDITED_LABEL',
        sortable: true,
        filter: true,
        editable: false,
        filterPlaceholder: 'LOADABLE_STUDY_LIST_SEARCH_BY_DATE',
        filterType: DATATABLE_FILTER_TYPE.DATE,
        dateFormat: this.timeZoneTransformationService.getMappedConfigurationDateFormat(AppConfigurationService.settings?.dateFormat),
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'lastEdited',
        filterFieldMaxvalue: new Date()
      }
    ];
    if(permission && ![VOYAGE_STATUS.CLOSE].includes(voyageStatusId)) {
      const actions: DATATABLE_ACTION[] = [];
      if(permission?.edit) {
        actions.push(DATATABLE_ACTION.EDIT);
      }
      const action: IDataTableColumn = {
        field: 'actions',
        header: '',
        fieldType: DATATABLE_FIELD_TYPE.ACTION,
        fieldValueIcon: '##',
        actions: actions
      };
      columns = [...columns, action];
    }

    return columns;
  }
}

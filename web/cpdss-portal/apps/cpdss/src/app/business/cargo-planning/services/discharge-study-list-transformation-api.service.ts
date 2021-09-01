import { Injectable } from '@angular/core';
import { DATATABLE_ACTION, DATATABLE_FIELD_TYPE, DATATABLE_FILTER_MATCHMODE, DATATABLE_FILTER_TYPE, IDataTableColumn } from '../../../shared/components/datatable/datatable.model';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';
import { TimeZoneTransformationService } from '../../../shared/services/time-zone-conversion/time-zone-transformation.service';
/**
 *
 *
 * @export
 * @class DischargeStudyListTransformationApiService
 */
@Injectable({
  providedIn: 'root'
})
export class DischargeStudyListTransformationApiService {

  constructor(private timeZoneTransformationService: TimeZoneTransformationService) { }

  /**
   * Method to fetch discharge study table columns.
   *
   * @memberof DischargeStudyListTransformationApiService
   */

  getDischargeStudyTableColumns() {
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
        header: 'DISCHARGE_STUDY_LIST_GRID_LOADABLE_STUDY_NAME_LABEL',
        sortable: true,
        filter: true,
        editable: false,
        filterPlaceholder: 'DISCHARGE_STUDY_LIST_SEARCH_BY_NAME',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,

      },
      {
        field: 'detail',
        header: 'DISCHARGE_STUDY_LIST_GRID_ENQUIRY_DETAILS_LABEL',
        sortable: true,
        filter: true,
        editable: false,
        filterPlaceholder: 'DISCHARGE_STUDY_LIST_SEARCH_BY_DETAILS',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'detail',
      },
      {
        field: 'status',
        header: 'DISCHARGE_STUDY_LIST_GRID_STATUS_LABEL',
        filter: true,
        sortable: true,
        editable: false,
        filterPlaceholder: 'DISCHARGE_STUDY_LIST_SEARCH_BY_STATUS',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'status',
      },
      {
        field: 'createdDate',
        header: 'DISCHARGE_STUDY_LIST_GRID_DATE_LABEL',
        sortable: true,
        filter: true,
        editable: false,
        filterPlaceholder: 'DISCHARGE_STUDY_LIST_SEARCH_BY_DATE',
        filterType: DATATABLE_FILTER_TYPE.DATE,
        dateFormat: this.timeZoneTransformationService.getMappedConfigurationDateFormat(AppConfigurationService.settings?.dateFormat),
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'createdDate',
        filterFieldMaxvalue: new Date()
      },
      {
        field: 'lastEdited',
        header: 'DISCHARGE_STUDY_LIST_GRID_DATE_LAST_EDITED_LABEL',
        sortable: true,
        filter: true,
        editable: false,
        filterPlaceholder: 'DISCHARGE_STUDY_LIST_SEARCH_BY_DATE',
        filterType: DATATABLE_FILTER_TYPE.DATE,
        dateFormat: this.timeZoneTransformationService.getMappedConfigurationDateFormat(AppConfigurationService.settings?.dateFormat),
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'lastEdited',
        filterFieldMaxvalue: new Date()
      }
    ];

      const actions: DATATABLE_ACTION[] = [];     
      const action: IDataTableColumn = {
        field: 'actions',
        header: '',
        fieldType: DATATABLE_FIELD_TYPE.ACTION,
        fieldValueIcon: '##',
        actions: actions
      };
      columns = [...columns, action];
    return columns;

  }

/**
 * Method to convert date into time.
 *
 * @param {*} value
 * @return {*}  {Date}
 * @memberof DischargeStudyListTransformationApiService
 */
convertToDate(value): Date {
    if (value) {
      const arr = value.toString().split(' ')
      const dateArr = arr[0]?.split('-');
      if (arr[1]) {
        const timeArr = arr[1].split(':')
        if (dateArr.length > 2 && timeArr.length > 1) {
          return new Date(Number(dateArr[2]), Number(dateArr[1]) - 1, Number(dateArr[0]), Number(timeArr[0]), Number(timeArr[1]));
        }
      } else {
        return new Date(Number(dateArr[2]), Number(dateArr[1]) - 1, Number(dateArr[0]))
      }
    }
    return null
  }
  

  /**
   * Method to set validation messages for discharge study pop up.
   *
   * @return {*} 
   * @memberof DischargeStudyListTransformationApiService
   */
  setValidationErrorMessage() {
    return {    
      newDischargeStudyName: {
        'required': 'DISCHARGE_STUDY_POPUP_REQUIRED_ERROR',       
        'patternMatch': 'DISCHARGE_NAME_INVALID_PATTERN'
      },
      enquiryDetails: {
        'maxlength': 'DISCHARGE_STUDY_MAX_LENGTH'
      }
    }
  }
}

import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, ValidationErrors } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { MenuItem, SortEvent , LazyLoadEvent } from 'primeng/api';
import { Table } from 'primeng/table';
import { ObjectUtils } from 'primeng/utils';
import { DATATABLE_ACTION, DATATABLE_EDITMODE, DATATABLE_FIELD_TYPE, DATATABLE_FILTER_MATCHMODE, DATATABLE_FILTER_TYPE, DATATABLE_SELECTIONMODE, IDataTableColumn, IDataTableEvent, IDataTableFilterEvent, IDataTableSortEvent , IDataTablePageChangeEvent } from './datatable.model';
import { Paginator } from 'primeng/paginator';

/**
 * Compoent for Datatable
 *
 * @export
 * @class DatatableComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-datatable',
  templateUrl: './datatable.component.html',
  styleUrls: ['./datatable.component.scss']
})
export class DatatableComponent implements OnInit {

  @ViewChild('datatable') datatable: Table;
  @ViewChild('paginator') paginatorRef: Paginator

  // properties
  @Input()
  get columns(): IDataTableColumn[] {
    return this._columns;
  }
  set columns(columns: IDataTableColumn[]) {
    this._columns = columns;
    const colsWithSubCol = columns.filter(col => col?.columns?.length);
    this.totalColSpan = columns.length - colsWithSubCol?.length;
    colsWithSubCol.forEach(col => {
      this.totalColSpan += + col?.columns.length;
    });
    this.setActions(columns);
  }

  @Input()
  get value(): Array<any> {
    return this._value;
  }
  set value(value: Array<any>) {
    if (value)
      this._value = value;
  }

  @Input()
  get form(): FormGroup {
    return this._form;
  }
  set form(form: FormGroup) {
    if (form) {
      this._form = form;
    }
  }

  @Input() selection: Object;

  @Input() selectionMode: DATATABLE_SELECTIONMODE;

  @Input() progress = false;

  @Input()
  get editMode(): DATATABLE_EDITMODE {
    return this._editMode;
  }

  set editMode(editMode: DATATABLE_EDITMODE) {
    this.customSort = editMode ? true : false;
    this._editMode = editMode;
  }

  @Input() filterable: boolean;

  @Input() listData: Object;

  @Input() sortMode: boolean;

  @Input() tableRowReOrder = false;

  @Input() tableId: string;

  @Input() showTotal = false;


  @Input()
  set loading(loading: boolean) {
    this._loading = loading;
  }

  get loading() : boolean {
    return this._loading;
  }

  @Input() paginator: boolean;

  @Input() 
  set rowsPerPage(rows: number[]) {
    this._rowsPerPage = rows;
  }

  get rowsPerPage() : Array<number>  {
    return this._rowsPerPage;
  }
  
  @Input()
  set rows(rows: number) {
    this._rows = rows;
  }

  get rows() : number {
    return this._rows;
  }

  @Input()
  set totalRecords(totalRecords: number) {
    this._totalRecords = totalRecords;
  }

  get totalRecords() : number {
    return this._totalRecords;
  }

  @Input()
  set currentPageReportTemplate(totalRecords: string) {
    this._currentPageReportTemplate = totalRecords;
  }

  get currentPageReportTemplate() : string {
    return this._currentPageReportTemplate;
  }

  @Input() 
  set lazy(lazy: boolean) {
    this._lazy = lazy;
  }

  get lazy(): boolean{
    return this._lazy;
  }

  @Input() 
  set currentPage(currentPage: number) {
    if(currentPage !== this.paginatorRef.paginatorState.page) {
      this.updateCurrentPage(currentPage)
    }
  }

  @Input() 
  set reset(resetDataTable: boolean) {
    if(resetDataTable) {
      this.datatable.reset();
      this.filterObject = {};
      this.resetChange.emit(false);
    }
  }

  get dataTable() {
    return this.form.get('dataTable') as FormArray;
  }

  // output event
  @Output() cellValueClick = new EventEmitter<IDataTableEvent>();
  @Output() editComplete = new EventEmitter<IDataTableEvent>();
  @Output() duplicateRow = new EventEmitter<IDataTableEvent>();
  @Output() deleteRow = new EventEmitter<IDataTableEvent>();
  @Output() viewRow = new EventEmitter<IDataTableEvent>();
  @Output() saveRow = new EventEmitter<IDataTableEvent>();
  @Output() rowSelection = new EventEmitter<IDataTableEvent>();
  @Output() columnClick = new EventEmitter<IDataTableEvent>();
  @Output() rowReorder = new EventEmitter<IDataTableEvent>();
  @Output() filter = new EventEmitter<IDataTableFilterEvent>();
  @Output() sort = new EventEmitter<IDataTableSortEvent>();
  @Output() editRow = new EventEmitter<IDataTableEvent>();
  @Output() onDataStateChange = new EventEmitter<IDataTablePageChangeEvent>();
  @Output() currentPageChange = new EventEmitter<number>();
  @Output() resetChange = new EventEmitter<boolean>();
  
  // public fields
  readonly fieldType = DATATABLE_FIELD_TYPE;
  readonly filterType = DATATABLE_FILTER_TYPE;
  readonly filterMatchMode = DATATABLE_FILTER_MATCHMODE;
  moreOptions: MenuItem[];
  selectedRowEvent: IDataTableEvent;
  totalColSpan: number;
  customSort: boolean;
  filterObject: object = {};


  // private fields
  private _columns: IDataTableColumn[];
  private _value: Array<any>;
  private _form: FormGroup;
  private _editMode: DATATABLE_EDITMODE;
  private _lazy: boolean;
  private _totalRecords: number;
  private _rows: number;
  private _rowsPerPage: number[];
  private _currentPageReportTemplate: string;
  private _loading: boolean;


  // public methods
  constructor(private translateService: TranslateService, private fb: FormBuilder) {
  }

  ngOnInit(): void {
    this._rowsPerPage = [10,50,100];
    this._currentPageReportTemplate = "{currentPage} of {totalPages}";
    this._loading = false;
    if (!this.form) {
      this.form = this.fb.group({
        dataTable: this.fb.array([...this.value])
      });
    }
  }

  /**
   * Handler for on edit complete event
   *
   * @param {IDataTableEvent} event
   * @memberof DatatableComponent
   */
  onEditComplete(event: IDataTableEvent): void {
    let colEditable, col;
    for (let index = 0; index < this.columns.length; index++) {
      if (this.columns[index].field === event?.field) {
        col = this.columns[index];
        break;
      } else {
        col = this.columns[index]?.columns?.find(column => column?.field === event?.field);
        if (col !== undefined) {
          break;
        }
      }
    }
    colEditable = col?.editable
    if (this.editMode && (colEditable === undefined || colEditable) && event?.data[event.field]?.isEditable && !event.data?.isAdd && event.field !== 'actions') {
      const control = this.field(event.index, event.field);
      if (col?.fieldType !== this.fieldType.DATETIME && col?.fieldType !== this.fieldType.DATERANGE) {
        event.data[event.field].isEditMode = control?.invalid;
      }
      if (control?.dirty && control?.valid) {
        if (col?.fieldType === DATATABLE_FIELD_TYPE.COLORPICKER) {
          event.data[event.field].value = control.value;
          this.editComplete.emit(event);
        }
      }
    }
  }

  /**
   * Hadler for table cell edit change event
   *
   * @param event
   * @param {any} rowData
   * @param {number} rowIndex
   * @param {IDataTableColumn} col
   * @memberof DatatableComponent
   */
  onChange(event, rowData: any, rowIndex: number, col: IDataTableColumn) {
    rowData[col.field].value = this.field(rowIndex, col.field).value;
    if (!event?.originalEvent?.target?.className?.includes('p-colorpicker')) {
      this.editComplete.emit({ originalEvent: event, data: rowData, index: rowIndex, field: col.field });
    }
  }

  /**
   * Hadler for table cell value click event
   *
   * @param {MouseEvent} event
   * @param {any} rowData
   * @param {number} rowIndex
   * @param {IDataTableColumn} col
   * @memberof DatatableComponent
   */
  onCellValueClick(event: MouseEvent, rowData: any, rowIndex: number, col: IDataTableColumn) {
    event.stopPropagation(); // Please dont remove this line
    const control = this.field(rowIndex, col.field);
    control.markAsTouched();
    control.updateValueAndValidity();
    this.cellValueClick.emit({ originalEvent: event, data: rowData, index: rowIndex, field: col.field });
  }

  /**
   * Handler for api new row save event
   *
   * @param {MouseEvent} event
   * @param {Object} rowData
   * @param {number} rowIndex
   * @memberof DatatableComponent
   */
  onRowSave() {
    this.saveRow.emit(this.selectedRowEvent);
  }

  /**
   * Handler for cell tab on focus event
   * @param event 
   * @param rowData 
   * @param rowIndex 
   * @param col 
   * @param colIndex 
   */
  onFocus(event, rowData: any, col: IDataTableColumn, colIndex: number) {
    const code = (event.keyCode ? event.keyCode : event.which);
    if (code === 9 && col.fieldType !== this.fieldType.ACTION && (col.editable === undefined || col.editable) && rowData[col.field]?.isEditable && !rowData?.isAdd) {
      const prevField = this.columns[colIndex - 1].field;
      if (prevField && rowData[prevField]) {
        rowData[prevField].isEditMode = false
      }
      rowData[col.field].isEditMode = true;
    }
  }

  /**
  * Handler for cell on click event
  * @param event 
  * @param rowData 
  * @param rowIndex 
  * @param col 
  * @param colIndex 
  */
  onClick(event, rowData, rowIndex, col: IDataTableColumn) {
    if (rowData[col.field]?.isEditable && this.row(rowIndex) && this.editMode && (col.editable === undefined || col.editable) && col.fieldType !== this.fieldType.ACTION) {
      rowData[col.field].isEditMode = true;
    }
    this.columnClick.emit({ originalEvent: event, data: rowData, index: rowIndex, field: col.field });
  }

  /**
   * Handler for filter event
   *
   * @param {IDataTableFilterEvent} event
   * @memberof DatatableComponent
   */
  onFilter(event: IDataTableFilterEvent) {
    this.filter.emit(event);
  }

  /**
   * Get form control of form 
   *
   * @param {number} formGroupIndex
   * @param {string} formControlName
   * @returns {FormControl}
   * @memberof DatatableComponent
   */
  field(formGroupIndex: number, formControlName: string): FormControl {
    const formControl = <FormControl>(<FormArray>this.form.get('dataTable')).at(formGroupIndex).get(formControlName);
    return formControl;
  }

  /**
   * Get field errors
   *
   * @param {number} formGroupIndex
   * @param {string} formControlName
   * @returns {ValidationErrors}
   * @memberof DatatableComponent
   */
  fieldError(formGroupIndex: number, formControlName: string): ValidationErrors {
    const formControl = this.field(formGroupIndex, formControlName);
    return formControl.invalid && (formControl.dirty || formControl.touched) ? formControl.errors : null;
  }

  /**
   * Get form row group
   *
   * @param {number} formGroupIndex
   * @returns {FormGroup}
   * @memberof DatatableComponent
   */
  row(formGroupIndex: number): FormGroup {
    const formGroup = <FormGroup>(<FormArray>this.form.get('dataTable')).at(formGroupIndex);
    return formGroup;
  }

  /**
   * Handler for actions dropdown button
   *
   * @param {MouseEvent} event
   * @param {any} rowData
   * @param {number} rowIndex
   * @param {IDataTableColumn} col
   * @memberof DatatableComponent
   */
  onDropdownClick(event: MouseEvent, rowData: any, rowIndex: number, col: IDataTableColumn) {
    this.selectedRowEvent = { originalEvent: event, data: rowData, index: rowIndex, field: col.field };
  }

  /**
   * Handler for row delete event
   *
   * @memberof DatatableComponent
   */
  onDelete() {
    this.selectedRowEvent.data.isDelete = true;
    this.deleteRow.emit(this.selectedRowEvent);
    // this.editComplete.emit(this.selectedRowEvent);
  }

  /**
   * Handle for row duplicate event
   *
   * @memberof DatatableComponent
   */
  onDuplicate() {
    this.duplicateRow.emit(this.selectedRowEvent);
  }

  /**
   * Handler for view action button click
   *
   * @memberof DatatableComponent
   */
  onView(): void {
    this.viewRow.emit(this.selectedRowEvent);
  }

  /**
   * Handler for option click
   *
   * @param {MouseEvent} event
   * @param {any} rowData
   * @param {number} rowIndex
   * @param {IDataTableColumn} col
   * @param {MenuItem} option
   * @memberof DatatableComponent
   */
  onOptionClick(event: MouseEvent, rowData: any, rowIndex: number, col: IDataTableColumn, option: MenuItem) {
    this.selectedRowEvent = { originalEvent: event, data: rowData, index: rowIndex, field: col.field };
    option.command();
  }

  /**
   * Handler for row selection
   *
   * @param {IDataTableEvent} event
   * @memberof DatatableComponent
   */
  onRowSelect(event: IDataTableEvent) {
    this.rowSelection.emit(event);
  }

  /**
   * Method for getting all more options
   *
   * @param {*} rowData
   * @returns
   * @memberof DatatableComponent
   */
  getMoreOptions(rowData: any) {
    return this.moreOptions.map(option => {
      if (option.id === DATATABLE_ACTION.SAVE) {
        option.visible = rowData?.isAdd;
      }
      if (option.id === DATATABLE_ACTION.EDIT) {
        option.visible = rowData?.isEditable === undefined || rowData?.isEditable;
      }
      if (option.id === DATATABLE_ACTION.DELETE) {
        option.visible = rowData?.isDeletable === undefined || rowData?.isDeletable;
      }
      if (option.id === DATATABLE_ACTION.DUPLICATE) {
        option.visible = rowData?.isDuplicate === undefined || rowData?.isDuplicate;
      }
      return option;
    });    
  }

  // private methods
  /**
   * Method for setting actions for data table
   *
   * @private
   * @param {IDataTableColumn[]} columns
   * @memberof DatatableComponent
   */
  private setActions(columns: IDataTableColumn[]) {
    const actionsCol = columns?.find(col => col.fieldType === this.fieldType.ACTION);
    const moreOptions: DATATABLE_ACTION[] = actionsCol?.actions ?? [];
    this.moreOptions = moreOptions.map(option => {
      let label: string;
      this.translateService.get(option).subscribe(response => {
        label = response
      });
      let _option;
      switch (option) {
        case DATATABLE_ACTION.SAVE:
          _option = {
            id: DATATABLE_ACTION.SAVE,
            label: label,
            icon: 'save-icon',
            command: () => {
              this.onRowSave();
            }
          }
          break;

        case DATATABLE_ACTION.DUPLICATE:
          _option = {
            id: DATATABLE_ACTION.DUPLICATE,
            label: label,
            icon: 'duplicate-icon',
            command: () => {
              this.onDuplicate();
            }
          }
          break;

        case DATATABLE_ACTION.DELETE:
          _option = {
            id: DATATABLE_ACTION.DELETE,
            label: label,
            icon: 'delete-icon',
            command: () => {
              this.onDelete();
            }
          }
          break;

        case DATATABLE_ACTION.VIEW:
          _option = {
            id: DATATABLE_ACTION.VIEW,
            label: label,
            icon: 'view-icon',
            command: () => {
              this.onView();
            }
          }
          break;

        case DATATABLE_ACTION.EDIT:
          _option = {
            id: DATATABLE_ACTION.EDIT,
            label: label,
            icon: 'pencil-icon',
            command: () => {
              this.onEdit();
            }
          }
          break;
        default:
          break;
      }
      return _option;
    });

  }



  /**
  * Handler for api row edit event
  *
  * @param {MouseEvent} event
  * @param {Object} rowData
  * @param {number} rowIndex
  * @memberof DatatableComponent
  */
  onEdit() {
    this.editRow.emit(this.selectedRowEvent);
  }

  /**
  * Filter date
  */
  onDateSelect(value, field, filterMatchMode) {
    this.datatable.filter(this.formatDateTime(value), field, filterMatchMode);
  }

  /**
   * Method for filtering date on enter key press
   * @param value 
   */
  onDateFilter(value, field) {
    this.datatable.filter(value, field, 'equals');
  }



  /**
  * Format date(dd-mm-yyyy)
  * Format date time(dd-mm-yyyy hh:mm)
  */
  formatDateTime(date, isTime = false) {
    if (!date)
      return ""
    let month = date.getMonth() + 1;
    let day = date.getDate();
    let hour = date.getHours();
    let minute = date.getMinutes();

    if (month < 10) {
      month = '0' + month;
    }

    if (day < 10) {
      day = '0' + day;
    }

    if (hour < 10) {
      hour = '0' + hour;
    }

    if (minute < 10) {
      minute = '0' + minute;
    }

    if (isTime) {
      return day + '-' + month + '-' + date.getFullYear() + ' ' + hour + ':' + minute;
    } else {
      return day + '-' + month + '-' + date.getFullYear();
    }
  }

  /**
  * Range date selected
  */
  onDateRangeSelect(event, formGroupIndex: number, formControlName: string, rowData: Object) {
    const formControl = this.field(formGroupIndex, formControlName);
    if(formControl?.value[0] && formControl?.value[1]){
      rowData[formControlName].value = this.formatDateTime(formControl?.value[0]) + ' to ' + this.formatDateTime(formControl?.value[1]);
      rowData[formControlName].isEditMode = false;
        this.editComplete.emit({ originalEvent: event, data: rowData, index: formGroupIndex, field: formControlName });
    }
  }


  /**
  * Range date cleared
  */
  onClearDateRange(event, formGroupIndex: number, formControlName: string, rowData: Object) {
    const formControl = this.field(formGroupIndex, formControlName);
    rowData[formControlName].value = "";
    formControl.setErrors({ 'required': true });
  }

  /**
  * On closing Date panel 
  */
  onDatePanelClosed(event, formGroupIndex: number, formControlName: string, rowData: any) {
    const formControl = this.field(formGroupIndex, formControlName);
    rowData[formControlName].value = this.formatDateTime(formControl.value, true);
    rowData[formControlName].isEditMode = formControl?.invalid;
    this.editComplete.emit({ originalEvent: event, data: rowData, index: formGroupIndex, field: formControlName });
  }

  /**
  * Handler for row re order
  *
  * @memberof DatatableComponent
  */
  onRowReorder(event) {
    this.rowReorder.emit(event)
  }

  /**
   * Custom sort function
   *
   * @param {SortEvent} event
   * @memberof DatatableComponent
   */
  sortFunction(event: SortEvent) {
    event.data.sort((data1, data2) => {
      const value1 = ObjectUtils.resolveFieldData(data1, event?.field);
      const value2 = ObjectUtils.resolveFieldData(data2, event?.field);
      let result = null;
      if (value1 == null && value2 != null)
        result = -1;
      else if (value1 != null && value2 == null)
        result = 1;
      else if (value1 == null && value2 == null)
        result = 0;
      else if (typeof value1 === 'string' && typeof value2 === 'string')
        result = value1.localeCompare(value2);
      else
        result = (value1 < value2) ? -1 : (value1 > value2) ? 1 : 0;
      return (event?.order * result);
    });
    this.sort.emit(event);
  }
  
  /**
   * lazy loading for sorting
   * @param {LazyLoadEvent}  event
   */
  loadDetails(event: LazyLoadEvent) {
    if(event.sortField) {
      this.updateCurrentPage(0);
    }
  }

  /**
   * Checks field disabled or not
   * @param formGroupIndex 
   * @param formControlName 
   */
  disabledField(formGroupIndex: number, formControlName: string) {
    const formControl = this.field(formGroupIndex, formControlName);
    return formControl.disabled;

  }

  /**
 * Get the total value of a column
 * @param col 
 * @param index 
 */
  getTotal(col: IDataTableColumn, index: number) {
    if (!index)
      return "Total"
    else if (col.showTotal && col.fieldType === this.fieldType.NUMBER) {
      let total = 0;
      this.value.forEach(row => {
        if (row[col.field]) {
          const value = row[col.field].value ?? 0;
          total += value
        }
      })
      return total.toFixed(2);
    }
    return ""
  }

  /**
   * Method to get rowspan
   *
   * @param {IDataTableColumn[]} columns
   * @param {*} filterable
   * @returns
   * @memberof DatatableComponent
   */
  getRowSpan(columns: IDataTableColumn[], filterable) {
    let rowSpan = filterable ? 1 : 0;
    columns.forEach(col => {
      rowSpan = rowSpan < col?.columns?.length ? col?.columns?.length : rowSpan;
    });

    return rowSpan;
  }

  /**
   * Method called on PageChange
   *
   * @returns
   * @memberof DatatableComponent
   */
  onPageChange(event) {
    const data = {
      paginator: {
        currentPage: event?.page ? event.page : 0,
        rows: event.rows ? event.rows : 0
      },
      filter: this.filterObject,
      action: 'paginator',
      sort: {
        sortField: this.datatable._sortField,
        sortOrder: this.datatable._sortField ? this.datatable._sortOrder === 1 ? 'asc' : 'desc' : '',
      }
    }
    this.currentPageChange.emit(event?.page ? event.page : 0)
    this.onDataStateChange.emit(data);
  }

  /**
   * Method called on filter the data
   *
   * @returns
   * @memberof DatatableComponent
   */
  filterData($event , col) {
    if(col?.filterByServer) {
      this.updateCurrentPage(0);
      this.filterObject[col.filterField] = ($event.target.value).trim();
    } else {
      this.datatable.filter(($event.target.value).trim(), col?.filterField ? col?.filterField : col.field, col.filterMatchMode)
    }
  }

  /**
   * set state value for server side sorting
   *
   * @param {string} action
   * @memberof DatatableComponent
   */
  setStateValue(action: string) {
    return {
      paginator: {
        currentPage: this.paginatorRef.paginatorState.page ? this.paginatorRef.paginatorState.page : 0,
        rows: this.paginatorRef.paginatorState.rows
      },
      filter: this.filterObject,
      action: action,
      sort: {
        sortField: this.datatable._sortField,
        sortOrder: this.datatable._sortField ? this.datatable._sortOrder === 1 ? 'asc' : 'desc' : '',
      }
    }
  }
  /**
   * set state value for server side sorting
   *
   * @param {number} index
   * @memberof DatatableComponent
   */
  getSlNo(index: number) {
    return (this.paginatorRef?.paginatorState?.page*this.paginatorRef?.paginatorState?.rows) + (index +1);
  }

  /**
   * update current page
   *
   * @param {number} currentPage
   * @memberof DatatableComponent
   */
  private updateCurrentPage(currentPage: number): void {
    setTimeout(() => {this.paginatorRef.changePage(currentPage) , this.currentPageChange.emit(currentPage)});
  }

}


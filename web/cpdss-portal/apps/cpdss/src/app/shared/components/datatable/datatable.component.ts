import { Component, EventEmitter, Input, OnInit, Output, QueryList, ViewChild, ViewChildren } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, ValidationErrors } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { MenuItem } from 'primeng/api';
import { Table } from 'primeng/table';
import { DATATABLE_ACTION, DATATABLE_EDITMODE, DATATABLE_FIELD_TYPE, DATATABLE_FILTER_MATCHMODE, DATATABLE_FILTER_TYPE, DATATABLE_SELECTIONMODE, IDataTableColumn, IDataTableEvent } from './datatable.model';

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

  // properties
  @Input()
  get columns(): IDataTableColumn[] {
    return this._columns;
  }
  set columns(columns: IDataTableColumn[]) {
    this._columns = columns;
    this.setActions(columns);
  }

  @Input()
  get value(): [] {
    return this._value;
  }
  set value(value: []) {
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

  @Input() editMode: DATATABLE_EDITMODE;

  @Input() filterable: boolean;

  @Input() listData: Object;

  @Input() sortMode: boolean;

  @Input() tableRowReOrder = false;

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

  // public fields
  readonly fieldType = DATATABLE_FIELD_TYPE;
  readonly filterType = DATATABLE_FILTER_TYPE;
  readonly filterMatchMode = DATATABLE_FILTER_MATCHMODE;
  moreOptions: MenuItem[];
  selectedRowEvent: IDataTableEvent;
  dateRange = '';
  dateTime = new Date();

  // private fields
  private _columns: IDataTableColumn[];
  private _value: [];
  private _form: FormGroup;



  // public methods
  constructor(private translateService: TranslateService, private fb: FormBuilder) {
  }

  ngOnInit(): void {
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
    if (this.editMode && (!event.data.isAdd || !this.columns.some(col => col.fieldType === this.fieldType.ACTION)) && event.field !== 'actions') {
      const control = this.field(event.index, event.field);
      if (control?.dirty && control?.valid) {
        event.data[event.field].value = control.value;
        this.editComplete.emit(event);
      }
      event.data[event.field].isEditMode = control?.invalid;
    }
  }

  /**
   * Hadler for table cell edit change event
   *
   * @param event
   * @param {Object} rowData
   * @param {number} rowIndex
   * @param {IDataTableColumn} col
   * @memberof DatatableComponent
   */
  onChange(event, rowData: Object, rowIndex: number, col: IDataTableColumn) {
    rowData[col.field].value = this.field(rowIndex, col.field).value;
    if (!event?.originalEvent?.target?.className?.includes('p-colorpicker')) {
      this.editComplete.emit({ originalEvent: event, data: rowData, index: rowIndex, field: col.field });
    }
  }

  /**
   * Hadler for table cell value click event
   *
   * @param {MouseEvent} event
   * @param {Object} rowData
   * @param {number} rowIndex
   * @param {IDataTableColumn} col
   * @memberof DatatableComponent
   */
  onCellValueClick(event: MouseEvent, rowData: Object, rowIndex: number, col: IDataTableColumn) {
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
  onFocus(event, rowData: Object, rowIndex: number, col: IDataTableColumn, colIndex: number) {
    const code = (event.keyCode ? event.keyCode : event.which);
    if (code === 9 && col.fieldType !== this.fieldType.ACTION && (col.editable === undefined || col.editable) && rowData[col.field]?.isEditable) {
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
   * @param {object} rowData
   * @param {number} rowIndex
   * @param {IDataTableColumn} col
   * @memberof DatatableComponent
   */
  onDropdownClick(event: MouseEvent, rowData: object, rowIndex: number, col: IDataTableColumn) {
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
   * @param {object} rowData
   * @param {number} rowIndex
   * @param {IDataTableColumn} col
   * @param {MenuItem} option
   * @memberof DatatableComponent
   */
  onOptionClick(event: MouseEvent, rowData: object, rowIndex: number, col: IDataTableColumn, option: MenuItem) {
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
    if (rowData?.isAdd) {
      return this.moreOptions.map(option => {
        if (option.id === DATATABLE_ACTION.SAVE) {
          option.visible = true;
        }
        return option;
      });
    } else {
      return this.moreOptions.map(option => {
        if (option.id === DATATABLE_ACTION.SAVE) {
          option.visible = false;
        }
        return option;
      });
    }
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
        default:
          break;
      }
      return _option;
    });

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
  * Range date select
  */
  onDateRangeSelect(value) {
    if (Array.isArray(value)) {
      this.dateRange = this.formatDateTime(value[0]) + ' to ' + this.formatDateTime(value[1])
    } else {
      if (this.dateRange === '' || this.dateRange.includes("to")) {
        this.dateRange = this.formatDateTime(value)
      }
      else {
        this.dateRange = this.dateRange + ' to ' + this.formatDateTime(value)
      }
    }
  }

  /**
  * Range date selected
  */
  onDateRangeSelected(event, formGroupIndex: number, formControlName: string, rowData: Object) {
    const formControl = this.field(formGroupIndex, formControlName);
    formControl.markAsTouched();
    if (this.dateRange.includes("to")) {
      formControl.setValue(this.dateRange)
      rowData[formControlName].value = formControl.value;
      this.editComplete.emit({ originalEvent: event, data: rowData, index: formGroupIndex, field: formControlName });
    } else {
      formControl.setErrors({ 'required': true });
    }
  }

  /**
  * Range date cleared
  */
  onClearDateRange(value) {
    this.dateRange = '';
  }

  /**
  * Date and time select
  */
  onDateTimeSelect(value, formGroupIndex: number, formControlName: string, rowData: Object) {
    const formControl = this.field(formGroupIndex, formControlName);
    formControl.markAsTouched();
    formControl.setValue(this.formatDateTime(value, true))
    rowData[formControlName].value = formControl.value;
    this.editComplete.emit({ originalEvent: value, data: rowData, index: formGroupIndex, field: formControlName });
  }

  /**
  * Not selcting Date and time 
  */
  onDateTimeNotSelected(value, formGroupIndex: number, formControlName: string, rowData: Object) {
    const formControl = this.field(formGroupIndex, formControlName);
    formControl.markAsTouched();
    if (formControl.value === null) {
      formControl.setErrors({ 'required': true });
    }
  }

  /**
  * Handler for row re order
  *
  * @memberof DatatableComponent
  */
  onRowReorder(event) {
    this.rowReorder.emit(event)
  }
}




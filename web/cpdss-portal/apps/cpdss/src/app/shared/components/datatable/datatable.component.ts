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

  // public fields
  readonly fieldType = DATATABLE_FIELD_TYPE;
  readonly filterType = DATATABLE_FILTER_TYPE;
  readonly filterMatchMode = DATATABLE_FILTER_MATCHMODE;
  moreOptions: MenuItem[];
  selectedRowEvent: IDataTableEvent;

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
    this.editComplete.emit({ originalEvent: event, data: rowData, index: rowIndex, field: col.field });
    // this.onEditComplete({ originalEvent: event, data: rowData, index: rowIndex, field: col.field });
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
    this.editComplete.emit(this.selectedRowEvent);
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
            icon: null,
            command: () => {
              this.onRowSave();
            }
          }
          break;

        case DATATABLE_ACTION.DUPLICATE:
          _option = {
            id: DATATABLE_ACTION.DUPLICATE,
            label: label,
            icon: null,
            command: () => {
              this.onDuplicate();
            }
          }
          break;

        case DATATABLE_ACTION.DELETE:
          _option = {
            id: DATATABLE_ACTION.DELETE,
            label: label,
            icon: null,
            command: () => {
              this.onDelete();
            }
          }
          break;

        case DATATABLE_ACTION.VIEW:
          _option = {
            id: DATATABLE_ACTION.VIEW,
            label: label,
            icon: null,
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
  onDateSelect(value) {
    this.datatable.filter(this.formatDate(value), 'createdDate', 'equals');
  }

  /**
   * Format date(dd-mm-yyyy)
   */
  formatDate(date) {
    let month = date.getMonth() + 1;
    let day = date.getDate();

    if (month < 10) {
      month = '0' + month;
    }

    if (day < 10) {
      day = '0' + day;
    }

    return day + '-' + month + '-' + date.getFullYear();
  }

  /**
   * Method for filtering date on enter key press
   * @param value 
   */
  onDateFilter(value){
    this.datatable.filter(value, 'createdDate', 'equals');
  }
}




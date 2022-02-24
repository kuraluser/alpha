import { SortEvent } from 'primeng/api';
import { IValidationErrorMessages } from '../validation-error/validation-error.model';

/**
 * Interface for Datatable component
 *
 * @export
 * @interface IDataTableColumn
 */
export interface IDataTableColumn {
    field?: string;
    header: string;
    fieldType?: DATATABLE_FIELD_TYPE;
    sortable?: boolean;
    sortField?: string;
    editable?: boolean;
    filter?: boolean;
    filterType?: DATATABLE_FILTER_TYPE;
    filterField?: string;
    filterPlaceholder?: string;
    filterMatchMode?: string;
    listName?: string;
    filterListName?: string;
    listFilter?: boolean;
    arrayLabelField?: string;
    arrayFilterField?: string;
    fieldValueIcon?: string;
    fieldSuffix?: string;
    fieldSuffixField?: string;
    fieldPlaceholder?: string;
    fieldOptionLabel?: string;
    errorMessages?: IValidationErrorMessages;
    actions?: DATATABLE_ACTION[];
    fieldHeaderClass?: string;
    fieldClass?: string;
    filterFieldMaxvalue?: any;
    minDate?: Date;
    dateFormat?: string;
    allowEqualDates?: boolean;
    readonlyInput?: boolean;
    columns?: IDataTableColumn[];
    virtualScroll?: boolean;
    showTemplate?: boolean;
    rowspan?: number;
    colspan?: number;
    fieldColumnClass?: string;
    subColumns? : IDataTableColumn[];
    subHeader?: string;
    numberFormat?: string;
    showTotal?: boolean;
    filterByServer?: boolean;
    buttons?: IButtons[];
    fieldComponentClass?: string;
    showTooltip?: boolean;
    fieldValue?: string;
    fieldHeaderTooltipIcon?: string;
    fieldHeaderTooltipText?: string;
    unit?: string;
    numberType?: string;
    maskFormat?: string;
    badgeColorField?: string;
    filterClass?: string;
    totalFieldClass?: string;
    maxSelectedLabels?: number;
    multiSelectShowTooltip?: boolean;
    iconField?: string;
    showClear?: boolean;
}

/**
 * Interface for Button type
 *
 * @export
 * @interface IButtons
 */
interface IButtons {
    type: DATATABLE_BUTTON,
    field: string,
    icons?: string,
    class?: string,
    label?: string,
    tooltip?: string;
    tooltipPosition?: string;
}

/**
 * ENUM for Filter type
 *
 * @export
 * @enum {number}
 */

export enum DATATABLE_FIELD_TYPE {
  ICON = 'ICON',
  SLNO = 'SLNO',
  TEXT = 'TEXT',
  NUMBER = 'NUMBER',
  SELECT = 'SELECT',
  COLORPICKER = 'COLORPICKER',
  AUTOCOMPLETE = 'AUTOCOMPLETE',
  ARRAY = 'ARRAY',
  ACTION = 'ACTION',
  DATE = 'DATE',
  DATERANGE = 'DATERANGE',
  DATETIME = 'DATETIME',
  COLOR = 'COLOR',
  TIME = "TIME",
  BUTTON = 'BUTTON',
  MASK = 'MASK',
  BADGE = 'BADGE',
  CHECKBOX = 'CHECKBOX',
  MULTISELECT = 'MULTISELECT',
  FILEICONS = 'FILEICONS'
}

/**
 * ENUM for Filter type
 *
 * @export
 * @enum {number}
 */
export enum DATATABLE_FILTER_TYPE {
    TEXT = 'TEXT',
    NUMBER = 'NUMBER',
    ARRAY = 'ARRAY',
    DATE = 'DATE',
    DATETIME = 'DATETIME',
    SELECT = 'SELECT'
}

/**
 * ENUM for filter match code
 *
 * @export
 * @enum {number}
 */
export enum DATATABLE_FILTER_MATCHMODE {
    STARTSWITH = "startsWith",
    CONTAINS = "contains",
    ENDSWITH = "endsWith",
    EQUALS = "equals",
    NOTEQUALS = "notEquals",
    IN = "in",
    LESSTHAN = "lt",
    LESSTHAN_OR_EQUAL = "lte",
    GREATERTHAN = "gt",
    GREATERTHAN_OR_EQUAL = "gte"
}

/**
 *  ENUM for Selection mode
 *
 * @export
 * @enum {number}
 */
export enum DATATABLE_SELECTIONMODE {
    SINGLE = 'single',
    MULTIPLE = 'multiple'
}

/**
 * ENUM for edit mode
 *
 * @export
 * @enum {number}
 */
export enum DATATABLE_EDITMODE {
    CELL = 'cell',
    ROW = 'row'
}

/**
 * Interface for datatable events like call click, edit complete etc
 *
 * @export
 * @interface IDataTableEvent
 */
export interface IDataTableEvent {
    data: any;
    field: string;
    index: number;
    originalEvent: MouseEvent;
    previousValue?: any;
}

/**
 * Interface for datatable filter events
 *
 * @export
 * @interface IDataTableFilterEvent
 */
export interface IDataTableFilterEvent {
    filters: any;
    filteredValue: [];
}

/**
 * Interface for datatable sort event
 *
 * @export
 * @interface IDataTableSortEvent
 * @extends {SortEvent}
 */
export interface IDataTableSortEvent extends SortEvent {
    data?: any[];
    mode?: string;
    field?: string;
    order?: number;
}

/**
 * Enum for Datatable action
 *
 * @export
 * @enum {number}
 */
export enum DATATABLE_ACTION {
    SAVE = 'SAVE',
    DELETE = 'DELETE',
    DUPLICATE = 'DUPLICATE',
    VIEW = 'VIEW',
    EDIT = 'EDIT'
}

/**
 * Interface for datatable
 *
 * @export
 * @interface IDataTablePageChangeEvent
 */
export interface IDataTablePageChangeEvent {
    paginator: IPaginator;
    filter: any;
    action: string;
    sort: ISort
}

/**
 * Interface for Paginator
 *
 * @export
 * @interface IPaginator
 */
export interface IPaginator {
    currentPage: number,
    rows: number
}

/**
 * Interface for sort
 *
 * @export
 * @interface ISort
 */
export interface ISort {
    sortField: string,
    sortOrder: string
}

/**
 * Enum for Datatable button
 *
 * @export
 * @enum {number}
 */
export enum DATATABLE_BUTTON {
    RESETPASSWORD = 'RESET PASSWORD',
    START_VOYAGE = "Start",
    STOP_VOYAGE = "Stop",
    SAVE_BUTTON = 'Save',
    DELETE_BUTTON = 'Delete',
    VIEW_BUTTON = 'View',
    EDIT_BUTTON = 'Edit'
}



/**
 * Interface for state pagination , filter and sort
 *
 * @export
 * @interface IDataStateChange
 */
export interface IDataStateChange {
  filter?: Object;
  name: string;
  desc?: string;
  pageSize: number;
  page: number;
  sortBy: string;
  orderBy: string;
}

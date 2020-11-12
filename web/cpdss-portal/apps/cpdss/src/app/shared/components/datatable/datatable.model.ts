import { IValidationErrorMessages } from '../validation-error/validation-error.model';

/**
 * Interface for Datatable component
 *
 * @export
 * @interface IDataTableColumn
 */
export interface IDataTableColumn {
    field: string;
    header: string;
    fieldType?: DATATABLE_FIELD_TYPE;
    sortable?: boolean;
    editable?: boolean;
    filter?: boolean;
    filterType?: DATATABLE_FILTER_TYPE;
    filterField?: string;
    filterPlaceholder?: string;
    filterMatchMode?: string;
    listName?: string;
    listFilter?: boolean;
    arrayLabelField?: string;
    arrayFilterField?: string;
    fieldValueIcon?: string;
    fieldSuffix?: string;
    fieldPlaceholder?: string;
    fieldOptionLabel?: string;
    errorMessages?: IValidationErrorMessages;
    actions?: DATATABLE_ACTION[];
    fieldHeaderClass?: string;
    fieldClass?: string;
    filterFieldMaxvalue?: any;
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
    DATE = 'DATE'
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
    DATE = 'DATE'
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
    VIEW = 'VIEW'
}
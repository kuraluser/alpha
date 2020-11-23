import Dexie from 'dexie';

/**
 * Class for converting values to value object with additional status like modified, visible, edited etc
 *
 * @export
 * @class ValueObject
 * @template T
 */
export class ValueObject<T = any> {
    private _value: T;

    public get value(): T {
        return this._value;
    }
    public set value(v: T) {
        this._value = v;
        this._isModified = true;
    }

    public get isVisible(): boolean {
        return this._isVisible;
    }

    public set isVisible(v: boolean) {
        this._isVisible = v;
    }

    // Value Object is in edit mode or not
    public get isEditMode(): boolean {
        return this._isEditMode;
    }

    public set isEditMode(v: boolean) {
        this._isEditMode = v;
    }

    // Value Object is in editable or not
    public get isEditable(): boolean {
        return this._isEditable;
    }

    public set isEditable(v: boolean) {
        this._isEditable = v;
    }

    public get isModified(): boolean {
        return this._isModified;
    }

    public cloneData(): ValueObject<T> {
        return new ValueObject<T>(this.value);
    }

    constructor(prop: T, private _isVisible = true, private _isEditMode = true, private _isModified = false, private _isEditable = true) {
        this._value = prop;
    }

}

/**
 * Inteface for response status from api calls
 *
 * @export
 * @interface IResponseStatus
 */
export interface IResponseStatus {
    status?: string;
    message?: string,
    errorCode?: string,
    correlationId?: string;
}

/**
 * Generic Response interface
 *
 * @export
 * @interface IResponse
 */
export interface IResponse extends IResponseStatus {
    responseStatus?: IResponseStatus;
}

/**
 * Interface for generic dictionary type
 *
 * @export
 * @interface IDictionary
 * @template T
 */
export interface IDictionary<T> {
    [key: string]: T;
}

/**
 * Interface for permission context input params
 *
 * @export
 * @interface IPermissionContext
 */
export interface IPermissionContext {
    key: string;
    actions: PERMISSION_ACTION[];
    hideElementOnViewFalse?:boolean;
}

/**
 * Permission actions
 *
 * @export
 * @enum {number}
 */
export enum PERMISSION_ACTION {
    ADD = 'ADD',
    EDIT = 'EDIT',
    DELETE = 'DELETE',
    VIEW = 'VIEW'
}

/**
 * Common class for indexed db database
 *
 * @export
 * @class CPDSSDB
 * @extends {Dexie}
 */
export class CPDSSDB extends Dexie {
    cargoNominations!: Dexie.Table<any, number>;
    ports!: Dexie.Table<any, number>;
    constructor() {
        super('CPDSS');
    }
}
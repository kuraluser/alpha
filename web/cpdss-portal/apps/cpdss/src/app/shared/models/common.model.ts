import Dexie from 'dexie';
import { Observable } from 'rxjs';

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
    hideElementOnViewFalse?: boolean;
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
    ohq!: Dexie.Table<any, number>;
    obq!: Dexie.Table<any, number>;
    dischargePorts!: Dexie.Table<any, number>;
    dischargeOhq!: Dexie.Table<any , number>;
    loadingInformations: Dexie.Table<any, number>;
    properties!: Dexie.Table<any>;
    constructor() {
        super('CPDSS');
        this.version(1).stores({
            cargoNominations: "++,storeKey,timeStamp,vesselId,voyageId,loadableStudyId,status",
            ports: "++,storeKey,timeStamp,vesselId,voyageId,loadableStudyId,status",
            ohq: "++,storeKey,timeStamp,vesselId,voyageId,loadableStudyId,status",
            obq: "++,storeKey,timeStamp,vesselId,voyageId,loadableStudyId,status",
            dischargePorts: "++,storeKey,timeStamp,vesselId,voyageId,dischargeStudyId,status",
            dischargeOhq: "++,storeKey,timeStamp,vesselId,voyageId,dischargeStudyId,status",
            loadingInformations: "++,storeKey,timeStamp,vesselId,voyageId,loadablePlanId,status",
            properties: ""
        });
    }
}

/**
 * Class for Properties Dexie db
 *
 * @export
 * @class PropertiesDB
 * @extends {CPDSSDB}
 */
export class PropertiesDB extends CPDSSDB {
    properties!: Dexie.Table<any>;

    constructor() {
        super();
    }

}

/**
 * ENUM for units of quantity
 *
 * @export
 * @enum {number}
 */
export enum QUANTITY_UNIT {
    MT = "MT",
    KL = "KL",
    BBLS = "BBLS",
    OBSBBLS = "OBSBBLS",
    LT = "LT",
    OBSKL = "OBSKL"
}

/**
 * ENUM for units of rate
 *
 * @export
 * @enum {number}
 */
export enum RATE_UNIT {
  M3_PER_HR = "M3/HR",
  BBLS_PER_HR = "BBLS/HR",
}

/**
 * Interface for time zone API response
 *
 * @export
 * @interface ITimeZoneResponse
 */
export interface ITimeZoneResponse {
    responseStatus: IResponseStatus;
    timezones: ITimeZone[];
}

/**
 * Interface for countries API response
 *
 * @export
 * @interface ICountriesResponse
 */
export interface ICountriesResponse {
  responseStatus: IResponseStatus;
  countrys: ICountry[];
}

/**
 * Interface for list time zones
 *
 * @export
 * @interface ITimeZone
 */
export interface ITimeZone {
    id: number;
    timezone: string;
    offsetValue: string;
    abbreviation?: string;
}

/**
 * Interface for enable time format options
 *
 * @export
 * @interface IDateTimeFormatOptions
 */
 export interface IDateTimeFormatOptions {
    utcFormat?: boolean;
    portLocalFormat?: boolean;
    stringToDate?: boolean;
    portTimeZoneOffset?: string;
    portTimeZoneAbbr?: string;
    customFormat?: string;
}
/**
 * Interface for generate processing Id
 *
 * @export
 * @interface IValidateAndSaveResponse
 */
 export interface IValidateAndSaveResponse {
    responseStatus: IResponse;
    processId: string;
}

/**
* User authorizaion status
 *
 * @export
 * @enum {number}
 */
export enum AUTH_STATUS {
    APPROVED = 1,
    REJECTED = 2,
    REQUESTED = 3,
    CREATED = 4
}

/**
* calculate subtotal
 * @interface ISubTotal
 * @export
 */
export interface ISubTotal {
  dwt: string;
  sagCorrection: string;
  sgCorrection?: string;
  foOnboard: string;
  doOnboard: string;
  freshWaterOnboard: string;
  ballast: string;
  constant: string;
  others: string;
}

/**
 * Model for loadable quantity model
 */
 export class LoadableQuantityModel {
    public responseStatus: IResponseStatus;
    public loadableQuantity: LodadableQuantity;
    public isSummerZone?: boolean;
    public caseNo: number;
    public selectedZone: string;
}

/**
 * Model for loadable quantity
 */
 export class LodadableQuantity {
    public portRotationId?: number;
    public portId?: number;
    public draftRestriction: string;
    public displacmentDraftRestriction?: string;
    public vesselLightWeight?: string;
    public dwt: string;
    public tpc: string;
    public estSeaDensity?: string;
    public sgCorrection?: string;
    public estSagging: string;
    public saggingDeduction?: string;
    public boilerWaterOnBoard: string;
    public ballast: string;
    public constant: string;
    public estFOOnBoard: string;
    public estDOOnBoard: string;
    public estFreshWaterOnBoard?: string;
    public otherIfAny: string;
    public distanceFromLastPort?: string;
    public vesselAverageSpeed?: string;
    public runningHours?: string;
    public runningDays?: string;
    public foConsumptionPerDay?: string;
    public estTotalFOConsumption?: string;
    public updateDateAndTime?: string;
    public lastUpdatedTime?: string;
    public foConInSZ?: string;
    public subTotal?: string;
    public totalQuantity?: string;
    public loadableQuantityId?: number;
    public id?: number;
    private loadableStudyId?: number
}

/**
 * Interface for cargo conditions model
 */
 export class ICargoConditions {
    id: number;
    plannedWeight: number;
    actualWeight: number;
    abbreviation?: string;
    tankId?: number;
    colorCode?: string;
    cargoNominationId?: number;
    api?: number;
    temperature?: number;
}

/**
 * Interface for Mode
 *
 * @export
 * @interface IMode
 */
 export interface IMode {
    name: string;
    id: number;
}



/**
 * Interface for percentage
 *
 * @export
 * @interface IPercentage
 */
 export interface IPercentage {
    value: number;
    name: string;
}


/**
 * Interface for country
 *
 * @export
 * @interface ICountry
 */
export interface ICountry {
  id: number;
  name: string;
  code: string;
}

/**
 * Interface for list months.
 *
 * @export
 * @interface IMonth
 */
export interface IMonth {
  id: number;
  month: string;
}

/**
 * ENUM for units of LENGTH
 *
 * @export
 * @enum {number}
 */
 export enum LENGTH_UNIT {
    M = "M",
    FT = "FT",
    KM = "KM",
    NM = "NM"
}

/**
 *  Interface for component candeactivate function
 *
 */
export interface ComponentCanDeactivate {
  canDeactivate: () => boolean | Observable<boolean>;
}

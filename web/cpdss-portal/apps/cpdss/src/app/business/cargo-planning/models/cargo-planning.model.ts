import { SelectItem } from 'primeng/api';
import { IDataTableEvent } from '../../../shared/components/datatable/datatable.model';
import { CPDSSDB, IResponse, IResponseStatus, ValueObject } from '../../../shared/models/common.model';

/**
 * Interface for cargo nomination value object
 *
 * @export
 * @interface ICargoNominationValueObject
 */
export interface ICargoNominationValueObject {
    id: number;
    slNo: number;
    priority: ValueObject<number>;
    color: ValueObject<string>;
    cargo: ValueObject<ICargo>;
    abbreviation: ValueObject<string>;
    loadingPorts: ValueObject<Array<ILoadingPort>>;
    loadingPortsLabel: string;
    loadingPortsNameArray: string[];
    quantity: ValueObject<number>;
    api: ValueObject<number>;
    temperature: ValueObject<number>;
    minTolerance: ValueObject<number>;
    maxTolerance: ValueObject<number>;
    segregation: ValueObject<ISegregation>;
    isAdd: boolean;
    isDelete?: boolean;
    storeKey: number;
}

/**
 * Interface for cargo nomination
 *
 * @export
 * @interface ICargoNomination
 */
export interface ICargoNomination {
    id: number;
    priority?: number;
    color?: string;
    cargoId: number;
    abbreviation?: string;
    loadingPorts?: ILoadingPort[];
    quantity?: number;
    api?: number;
    temperature?: number;
    minTolerance?: number;
    maxTolerance?: number;
    segregationId?: number;
    loadableStudyId?: number;
    isDelete?: boolean;
    isAdd?: boolean;
    storeKey?: number;
    vesselId?: number;
    voyageId?: number;
}

/**
 * Interface for Cargonomination api response
 *
 * @export
 * @interface ICargoNominationDetailsResponse
 */
export interface ICargoNominationDetailsResponse {
    responseStatus: IResponseStatus;
    cargoNominations?: ICargoNomination[];
    cargos?: ICargo[];
    portList: IPort[];
    cargoNominationId?: number;
    segregations: ISegregation[];
}

/**
 * Interface for cargo ports api response
 *
 * @export
 * @interface ICargoPortsResponse
 */
export interface ICargoPortsResponse {
    responseStatus: IResponseStatus;
    ports: IPort[];
}

/**
 * Interface for cargo
 *
 * @export
 * @interface ICargo
 */
export interface ICargo {
    id: number;
    name: string;
    abbreviation: string;
    api: number;
    ports?: IPort[];
}

/**
 * Interface for loading port
 *
 * @export
 * @interface ILoadingPort
 */
export interface ILoadingPort {
    id: number;
    name: string;
    quantity: number;
}


/**
 * Interface for loading port value object
 *
 * @export
 * @interface ILoadingPortValueObject
 */
export interface ILoadingPortValueObject {
    id: number;
    name: ValueObject<string>;
    quantity: ValueObject<number>;
    isAdd: boolean;
    isDelete?: boolean;
}

/**
 * Interface for port
 *
 * @export
 * @interface IPort
 */
export interface IPort {
    id: number;
    name: string;
    code: string;
    maxAirDraft: number;
    maxDraft: number;
    waterDensity: number;
}

/**
 * Interface for cargo nomination list data
 *
 * @export
 * @interface ICargoNominationListData
 */
export interface ICargoNominationAllDropdownData {
    priorityList: SelectItem[];
    cargoList: ICargo[];
    segregationList: ISegregation[];
    ports: IPort[];
}

/**
 * Interface for port api response
 *
 * @export
 * @interface IPortsResponse
 */
export interface IPortsResponse {
    responseStatus: IResponseStatus;
    ports: IPort[];
}

/**
 * Interface for segregation 
 *
 * @export
 * @interface ISegregation
 */
export interface ISegregation {
    id: number;
    name: string;
}

/**
 * Interface for Loading Popup Data
 *
 * @export
 * @interface ILoadinPopupData
 */
export interface ILoadingPopupData {
    originalEvent: MouseEvent;
    rowData: ICargoNominationValueObject;
    rowIndex: number;
    ports: IPort[];
    isUpdate?: boolean;
}

/**
 * Interface for all cargo nomination grid events
 *
 * @export
 * @interface ICargoNominationEvent
 */
export interface ICargoNominationEvent extends IDataTableEvent {
    data: ICargoNominationValueObject;
    field: string;
    index: number;
    originalEvent: MouseEvent;

}

/**
 * Class for Cargonomination Dexie db
 *
 * @export
 * @class CargoNominationDB
 * @extends {CPDSSDB}
 */
export class CargoNominationDB extends CPDSSDB {
    cargoNominations!: Dexie.Table<ICargoNomination, number>;

    constructor() {
        super();
        this.version(1).stores({
            cargoNominations: '++,storeKey,timeStamp'
        });
    }

}


/**
 * Interface for ports list data
 *
 * @export
 * @interface IPortListData
 */
export interface IPortAllDropdownData {
    portList: IPort[];
    operationList: IOperations[];
    operationListComplete: IOperations[];
}

/**
 * Interface for Operations
 *
 * @export
 * @interface IOperations
 */
export interface IOperations {
    id: number;
    operationName: string;
}

/**
 * Interface for port
 *
 * @export
 * @interface IPortList
 */
export interface IPortList {
    id: number;
    portOrder: number;
    loadableStudyId: number;
    portId: number;
    operationId: number;
    seaWaterDensity: number;
    distanceBetweenPorts: number;
    timeOfStay: number;
    maxDraft: number;
    maxAirDraft: number;
    eta: string;
    etd: string;
    layCanFrom: string;
    layCanTo: string;
    isDelete?: boolean;
    isAdd?: boolean;
    storeKey?: number;
    vesselId?: number;
    voyageId?: number;
}

/**
 * Interface for Ports api response
 *
 * @export
 * @interface IPortsDetailsResponse
 */
export interface IPortsDetailsResponse {
    responseStatus: IResponseStatus;
    portList: IPortList[];
    operations: IOperations[];
    portId?: number;
}

/**
 * Interface for ports value object
 *
 * @export
 * @interface IPortsValueObject
 */
export interface IPortsValueObject {
    id: number;
    portOrder: number;
    slNo: number;
    port: ValueObject<IPort>;
    portcode: ValueObject<string>;
    operation: ValueObject<IOperations>;
    seaWaterDensity: ValueObject<number>;
    layCan: ValueObject<any>;
    layCanFrom: ValueObject<string>;
    layCanTo: ValueObject<string>;
    maxDraft: ValueObject<number>;
    maxAirDraft: ValueObject<number>;
    eta: ValueObject<any>;
    etd: ValueObject<any>;
    isAdd: boolean;
    isDelete?: boolean;
    storeKey: number;
    isLoadable: boolean;
}


/**
 * Interface for all port  grid events
 *
 * @export
 * @interface IPortsEvent
 */
export interface IPortsEvent {
    data: IPortsValueObject;
    field: string;
    index: number;
    originalEvent: MouseEvent;

}

/**
 * ENUM for loadable study details page
 *
 * @export
 * @enum {number}
 */
export enum LOADABLE_STUDY_DETAILS_TABS {
    CARGONOMINATION = "CARGONOMINATION",
    PORTS = "PORTS",
    OHQ = "OHQ",
    OBQ = "OBQ"
}

/**
 * Interface for ohq ports
 *
 * @export
 * @interface IOHQPort
 */
export interface IOHQPort {
    portId: number;
}

/**
 * Interface for ohq ports api
 *
 * @export
 * @interface IOHQPortRotationResponse
 */
export interface IOHQPortRotationResponse {
    responseStatus: IResponse;
    portList: IOHQPort[];
}

/**
 * Interface for port ohq details api
 *
 * @export
 * @interface IPortOHQResponse
 */
export interface IPortOHQResponse {
    responseStatus: IResponse;
    onHandQuantities: IPortOHQTankDetail[];
}

/**
 * Interface for details ohq details of specific port
 *
 * @export
 * @interface IPortOHQTankDetail
 */
export interface IPortOHQTankDetail {
    id: number;
    fuelTypeId: number;
    fuelTypeName: string;
    tankId: number;
    tankName: string;
    arrivalVolume: number;
    arrivalQuantity: number;
    departureVolume: number;
    departureQuantity: number;
    portId: number;
    storeKey: number;
    vesselId: number;
    voyageId: number;
    loadableStudyId: number;
}

/**
 * Interface for OHQ port details
 *
 * @export
 * @interface IPortOHQTankDetailValueObject
 */
export interface IPortOHQTankDetailValueObject {
    slNo: number;
    id: number;
    fuelTypeId: number;
    fuelTypeName: string;
    tankId: number;
    tankName: string;
    arrivalVolume: ValueObject<number>;
    arrivalQuantity: ValueObject<number>;
    departureVolume: ValueObject<number>;
    departureQuantity: ValueObject<number>;
    portId: number;
    storeKey: number;
}

/**
 * Interface for fuel type
 *
 * @export
 * @interface IFuelType
 */
export interface IFuelType {
    id: number;
    name: string;
}

/**
 * Interface for ohq grid list data
 *
 * @export
 * @interface IPortOHQListData
 */
export interface IPortOHQListData {
    fuelTypes: IFuelType[];
}
/**
 * Interface for OHQ grid events
 *
 * @export
 * @interface IPortOHQTankDetailEvent
 * @extends {IDataTableEvent}
 */
export interface IPortOHQTankDetailEvent extends IDataTableEvent {
    data: IPortOHQTankDetailValueObject;
    field: string;
    index: number;
    originalEvent: MouseEvent;
}

/**
 * Class for OHQ Dexie db
 *
 * @export
 * @class OHQDB
 * @extends {CPDSSDB}
 */
export class OHQDB extends CPDSSDB {
    ohq!: Dexie.Table<IPortOHQTankDetail, number>;

    constructor() {
        super();
        this.version(1).stores({
            ohq: '++,storeKey,timeStamp'
        });
    }

}

/**
 * Class for port Dexie db
 *
 * @export
 * @class PortsDB
 * @extends {CPDSSDB}
 */
export class PortsDB extends CPDSSDB {
    ports!: Dexie.Table<IPortList, number>;

    constructor() {
        super();
        this.version(1).stores({
            ports: '++,storeKey,timeStamp'
        });
    }

}


/**
 * Model for loadable study list 
 */
export class PatternHistory {
    public name: string;
    public statusId: number;
}

/**
 * ENUM for operations
 *
 * @export
 * @enum {number}
 */
export enum OPERATIONS {
    LOADING = 1,
    DISCHARGING = 2,
    BUNKERING = 3,
    TRANSIT = 4
}

/**
 * Interface for port obq details api
 *
 * @export
 * @interface IPortOBQResponse
 */
export interface IPortOBQResponse {
    responseStatus: IResponse;
    onBoardQuantities: IPortOBQTankDetail[];
}

/**
 * Interface for details ohq details of specific port
 *
 * @export
 * @interface IPortOBQTankDetail
 */
export interface IPortOBQTankDetail {
    id: number;
    cargoId: number;
    tankId: number;
    tankName: string;
    sounding: number;
    weight: number;
    volume: number;
    colorCode: string;
    portId: number;
}

/**
 * Interface for OBQ port details
 *
 * @export
 * @interface IPortOBQTankDetailValueObject
 */
export interface IPortOBQTankDetailValueObject {
    slNo: number;
    id: number;
    tankId: number;
    tankName: string;
    cargo: ValueObject<ICargo>;
    sounding: ValueObject<number>;
    weight: ValueObject<number>;
    volume: ValueObject<number>;
    colorCode: string;
    portId: number;
}

/**
 * Interface for obq grid list data
 *
 * @export
 * @interface IPortOBQListData
 */
export interface IPortOBQListData {
    cargoList: ICargo[];
}
import { SelectItem } from 'primeng/api';
import { IDataTableEvent } from '../../../shared/components/datatable/datatable.model';
import { CPDSSDB, IResponse, IResponseStatus, ValueObject } from '../../../shared/models/common.model';
import { ITank } from '../../core/models/common.model';
import {  IPort, IPortList } from '../../core/models/common.model';

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
    name?: string;
    abbreviation?: string;
    api?: number;
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
            cargoNominations: '++,storeKey,timeStamp,vesselId,voyageId,loadableStudyId,status'
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
    tanks: ITank[][];
    rearTanks: ITank[][];
}

/**
 * Enum for ohq voyage mode
 *
 * @export
 * @enum {number}
 */
export enum OHQ_MODE {
    ARRIVAL = "ARRIVAL",
    DEPARTURE = "DEPARTURE"
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
    density: number;
    arrivalQuantity: number;
    departureQuantity: number;
    portId: number;
    storeKey: number;
    vesselId: number;
    voyageId: number;
    loadableStudyId: number;
    colorCode: string;
    fuelTypeShortName: string;
    fullCapacityCubm: number;
    fullCapacity: number;
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
    density: ValueObject<number>;
    arrivalQuantity: ValueObject<number>;
    departureQuantity: ValueObject<number>;
    portId: number;
    storeKey: number;
    colorCode: string;
    quantity: number;
    volume: number;
    percentageFilled: string;
    fullCapacityCubm: number;
    fullCapacity: number;
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
    colorCode: string;
    shortName: string;
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
 * Interface for ohq tanks
 *
 * @export
 * @interface IOHQTank
 * @extends {ITank}
 */
export interface IBunkerTank extends ITank {
    id: number;
    categoryId: number;
    categoryName: string;
    name: string;
    frameNumberFrom: number;
    frameNumberTo: number;
    shortName: string;
    heightFrom?: number;
    heightTo?: number;
    fullCapacityCubm?: string;
    density: number;
    group: number;
    order: number;
    slopTank: boolean;
    commodity?: IPortOHQTankDetailValueObject;
    gridColumn?: string;
    percentageFilled?: string;
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
            ohq: '++,storeKey,timeStamp,vesselId,voyageId,loadableStudyId,status'
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
            ports: '++,storeKey,timeStamp,vesselId,voyageId,loadableStudyId,status'
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
 * Interface for commingle cargo details 
 *
 * @export
 * @interface ICommingleDetails
 */
export interface ICommingleDetails {
    id: number;
    tankShortName: string;
    cargo1Abbrivation: string;
    cargo2Abbrivation: string;
    grade: string;
    quantity: string;
    api: string;
    temperature: string;
    cargo1Quantity: string;
    cargo2Quantity: string;
    cargo1Percentage: string;
    cargo2Percentage: string;
}

/**
 * Interface for commingle cargo details value object
 *
 * @export
 * @interface ICommingleDetailValueObject
 */
export interface ICommingleDetailValueObject {
    id: number;
    tankShortName: ValueObject<string>;
    cargo1Abbrivation: ValueObject<string>;
    cargo2Abbrivation: ValueObject<string>;
    grade: ValueObject<string>;
    quantity: ValueObject<string>;
    api: ValueObject<string>;
    temperature: ValueObject<string>;
    cargo1Quantity: ValueObject<string>;
    cargo2Quantity: ValueObject<string>;
    cargo1Percentage: ValueObject<string>;
    cargo2Percentage: ValueObject<string>
    cargoQuantity: ValueObject<string>;
    cargoTotalQuantity: ValueObject<number>;
    cargoPercentage: ValueObject<string>;
}

/**
 * Interface for commingle cargo details api reponse
 *
 * @export
 * @interface ICommingleCargoDetailsResponse
 */
export interface ICommingleCargoDetailsResponse extends ICommingleDetails {
    responseStatus: IResponse;
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
    tanks: ITank[][];
}

/**
 * Interface for details ohq details of specific port
 *
 * @export
 * @interface IPortOBQTankDetail
 */
export interface IPortOBQTankDetail {
    storeKey?: number;
    id: number;
    cargoId: number;
    tankId: number;
    tankName: string;
    api: number;
    quantity: number;
    colorCode: string;
    portId: number;
    vesselId: number;
    voyageId: number;
    loadableStudyId: number;
    fullCapacityCubm: string;
    abbreviation: string;
    loadOnTop: boolean;
}

/**
 * Interface for OBQ port details
 *
 * @export
 * @interface IPortOBQTankDetailValueObject
 */
export interface IPortOBQTankDetailValueObject {
    storeKey: number;
    slNo: number;
    id: number;
    tankId: number;
    tankName: string;
    cargo: ValueObject<ICargo>;
    api: ValueObject<number>;
    quantity: ValueObject<number>;
    volume: number;
    colorCode: string;
    portId: number;
    percentageFilled: string;
    fullCapacityCubm: string;
    abbreviation: string;
    loadOnTop: boolean;
}

/**
 * Interface for load on top object for loadable study
 *
 * @export
 * @interface ILoadOnTop
 */
export interface ILoadOnTop {
    isLoadOnTop: boolean
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

/**
 * Class for OBQ Dexie db
 *
 * @export
 * @class OBQDB
 * @extends {CPDSSDB}
 */
export class OBQDB extends CPDSSDB {
    obq!: Dexie.Table<IPortOBQTankDetail, number>;

    constructor() {
        super();
        this.version(1).stores({
            obq: '++,storeKey,timeStamp,vesselId,voyageId,loadableStudyId,status'
        });
    }

}

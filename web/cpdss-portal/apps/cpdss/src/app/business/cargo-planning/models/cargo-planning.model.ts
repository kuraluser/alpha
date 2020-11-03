import { SelectItem } from 'primeng/api';
import { CPDSSDB, IResponseStatus, ValueObject } from '../../../shared/models/common.model';

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
    priority: number;
    color: string;
    cargoId: number;
    abbreviation: string;
    loadingPorts: ILoadingPort[];
    quantity: number;
    api: number;
    temperature: number;
    minTolerance: number;
    maxTolerance: number;
    segregationId: number;
    loadableStudyId: number;
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
export interface ICargoNominationEvent {
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
            cargoNominations: '++,storeKey'
        });
    }

}
import { IResponseStatus } from '../../../shared/models/common.model';

/**
 *  vessel-info model
 */
export interface IVesselInfo {
    responseStatus: {
        status: string
    },
    vessels: IVessels[]
}

/**
 *  model for new-loadable-study-list-names
 */
export interface INewLoadableStudyListNames {
    name: string;
    value: string;
}

/**
 *  model for loadline-list
 */
export interface ILoadLineList {
    id: number;
    name: string;
    draftMarks: IdraftMarks[]
}

/**
 *  model for draft-marks
 */
export interface IdraftMarks {
    id: number,
    name: string
}

/**
 *  model for vessel-info
 */
export interface IVessels {
    id: string,
    name: string,
    captainId: string,
    captainName: string,
    chiefOfficerId: string,
    chiefOfficerName: string,
    loadlines: ILoadLineList[]
}

/**
 * Model for voyage list 
 */
export class Voyage {
    public voyageNo: string;
    public id: number;
    public status?: VOYAGE_STATUS_LABEL;
    public confirmedLoadableStudyId?: number;
    public startDate?: string;
    public endDate?: string;
    public noOfDays?: number;
    statusId?: VOYAGE_STATUS;
}

/**
 * Interface for voyage response
 *
 * @export
 * @interface IVoyageResponse
 */
export interface IVoyageResponse {
    responseStatus: IResponseStatus;
    voyages: Voyage[];
}

/**
 * Interface for tank data
 *
 * @export
 * @interface ITank
 */
export interface ITank {
    id: number;
    categoryId: number;
    categoryName: string;
    name: string;
    frameNumberFrom: number;
    frameNumberTo: number;
    shortName: string;
    heightFrom?: number;
    heightTo?: number;
    fillCapcityCubm?: number;
    fullCapacityCubm?: string;
    density: number;
    group: number;
    order: number;
    slopTank: boolean;
    commodity?: any;
    gridColumn?: string;
    percentageFilled?: string;
}

/**
 * Interface for  cargo tank layout tank
 *
 * @export
 * @interface ICargoTank
 */
export interface ICargoTank extends ITank {
    id: number;
    categoryId: number;
    categoryName: string;
    name: string;
    frameNumberFrom: number;
    frameNumberTo: number;
    shortName: string;
    heightFrom?: number;
    heightTo?: number;
    fillCapcityCubm: number;
    fullCapacityCubm?: string;
    density: number;
    group: number;
    order: number;
    slopTank: boolean;
    commodity?: ILoadableCargo;
    gridColumn?: string;
    percentageFilled?: string;
}

/**
 * Interface for setting tank details options
 *
 * @export
 * @interface ITankOptions
 */
export interface ITankOptions {
    class?: string;
    isFullyFilled?: boolean;
    showFillingPercentage?: boolean;
    fillingPercentageField?: string;
    showVolume?: boolean;
    volumeField?: string;
    volumeUnit?: string;
    showWeight?: boolean;
    weightField?: string;
    weightUnit?: string;
    showUllage?: boolean;
    ullageField?: string;
    ullageUnit?: string;
    showCommodityName?: boolean;
    showTooltip?: boolean;
    densityField?: string;
    showDensity?: boolean;
    commodityNameField?: string;
    isSelectable?: boolean;
}

/**
 * ENUM for tank layout tabs
 *
 * @export
 * @enum {number}
 */
export enum TANKTYPE {
    CARGO = "CARGO",
    BALLAST = "BALLAST",
    BUNKER = "BUNKER",
}

/**
 * Interface for loadable cargo details
 *
 * @export
 * @interface ILoadableCargo
 */
export interface ILoadableCargo {
    cargoAbbreviation?: string;
    colorCode?: string;
    tankId?: number;
    quantity?: number;
    isCommingle?: boolean;
    volume?: number;
    ullage?: number;
	fillingRatio?: string;
}

/**
 * Interface for ballast stowage
 *
 * @export
 * @interface 
 */
export interface IBallastStowageDetails {
    id: number,
    tankName?: string,
    tankId: number,
    rdgLevel: string,
    correctionFactor: string,
    correctedLevel: string,
    metricTon: string,
    cubicMeter: string,
    percentage: string,
    sg: string,
    lcg: string,
    vcg: string,
    tcg: string,
    inertia: string;
    colorCode?: string;
}

/**
 * Interface for ballast Tank details
 * @export
 * @interface 
 */
export interface IBallastTank extends ITank {
    id: number,
    categoryId: number,
    categoryName: string,
    name: string,
    frameNumberFrom: number,
    frameNumberTo: number,
    shortName: string,
    fullCapacityCubm: string,
    density: number,
    group: number,
    order: number,
    slopTank: boolean,
    commodity: IBallastStowageDetails;
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
    code?: string;
    maxAirDraft?: number;
    maxDraft?: number;
    waterDensity?: number;
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
    layCanFrom: any;
    layCanTo: any;
    isDelete?: boolean;
    isAdd?: boolean;
    storeKey?: number;
    vesselId?: number;
    voyageId?: number;
    etaActual?: string;
    etdActual?: string;
}

/**
 * ENUM for voyage status
 *
 * @export
 * @enum {number}
 */
export enum VOYAGE_STATUS {
    OPEN = 1,
    CLOSE = 2,
    ACTIVE = 3
}

/**
 * ENUM for voyage status labels
 *
 * @export
 * @enum {number}
 */
export enum VOYAGE_STATUS_LABEL {
    OPEN = "Open",
    CLOSE = "Close",
    ACTIVE = "Active"
}

/**
 * ENUM for Loadable Study Status
 *
 * @export
 * @enum {number}
 */
export enum LOADABLE_STUDY_STATUS {
    PLAN_PENDING = 1,
    PLAN_CONFIRMED = 2,
    PLAN_GENERATED = 3,
    PLAN_ALGO_PROCESSING = 4,
    PLAN_ALGO_PROCESSING_COMPETED = 5,
    PLAN_NO_SOLUTION = 6,
    PLAN_ERROR = 11
}
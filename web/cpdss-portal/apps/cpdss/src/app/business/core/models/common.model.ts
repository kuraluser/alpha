import { ICountry, IResponse, IResponseStatus, ValueObject } from '../../../shared/models/common.model';

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
    actualStartDate?: string;
    actualEndDate?: string;
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
    fillingRatio?: string;
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
    fillCapcityCubm?: number;
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
    percentageFilled?: string;
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
    rdgLevelOrginal: string,
    correctionFactor: string,
    correctionFactorOrginal: string,
    correctedLevel: string,
    correctedLevelOrginal: string,
    metricTon: string,
    metricTonOrginal: string,
    cubicMeter: string,
    percentage: string,
    percentageOrginal: string,
    sg: string,
    lcg: string,
    vcg: string,
    tcg: string,
    inertia: string;
    colorCode?: string;
    fullCapacityCubm?: string;
    api?:number;
    temperature?: number;
    tankShortName: string;
    fillingRatio?: string;
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
    commodity?: IBallastStowageDetails;
}
/**
 * Interface for port
 *
 * @export
 * @interface IPort
 */
export interface IPort {
    id: number;
    portId?: number;
    name: string;
    code?: string;
    maxAirDraft?: number;
    maxDraft?: number;
    waterDensity?: number;
    timezone?: string;
    timezoneOffsetVal?: string;
    timezoneAbbreviation?: string;
    portOrder?: number;
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
    lastModifiedPortId?: number;
}

/**
 * Interface for Ports api response
 *
 * @export
 * @interface IDischargePortsDetailsResponse
 */
 export interface IDischargePortsDetailsResponse {
    responseStatus: IResponseStatus;
    portList: IDischargeStudyPortList[];
    operations: IOperations[];
    portId?: number;
    lastModifiedPortId?: number;
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
    portTimezoneId?: number;
    operationId: number;
    seaWaterDensity: any;
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
    isPortsComplete?: boolean;
}

/**
 * Interface for port
 *
 * @export
 * @interface IDischargeStudyPortList
 */
 export interface IDischargeStudyPortList {
    id: number;
    portOrder: number;
    dischargeStudyId: number;
    portId: number;
    portTimezoneId?: number;
    operationId: number;
    seaWaterDensity: any;
    distanceBetweenPorts: number;
    timeOfStay: number;
    maxDraft: number;
    maxAirDraft: number;
    eta: string;
    etd: string;
    isDelete?: boolean;
    isAdd?: boolean;
    storeKey?: number;
    vesselId?: number;
    voyageId?: number;
    etaActual?: string;
    etdActual?: string;
    isPortsComplete?: boolean;
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
    CLOSE = "Closed",
    ACTIVE = "Active"
}

/**
 * ENUM for Loadable Study Status ID
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
    PLAN_LOADICATOR_CHECKING = 7,
    PLAN_ERROR = 11
}

/**
 * ENUM for Discharge Study Status ID
 *
 * @export
 * @enum {number}
 */
 export enum DISCHARGE_STUDY_STATUS {
    PLAN_PENDING = 1,
    PLAN_CONFIRMED = 2,
    PLAN_GENERATED = 3,
    PLAN_ALGO_PROCESSING = 4,
    PLAN_ALGO_PROCESSING_COMPETED = 5,
    PLAN_NO_SOLUTION = 6,
    PLAN_ERROR = 11
}

/**
 * ENUM for Loadable Study Status
 *
 * @export
 * @enum {string}
 */
 export enum LOADABLE_STUDY_STATUS_TEXT {
    PLAN_PENDING = "Pending",
    PLAN_CONFIRMED = "Confirmed",
    PLAN_GENERATED = "Plan Generated",
    PLAN_ALGO_PROCESSING = "ALGO Processing Started",
    PLAN_ALGO_PROCESSING_COMPETED = "ALGO Processing Completed",
    PLAN_NO_SOLUTION = "No Plan Available",
    PLAN_LOADICATOR_CHECKING = "Loadicator Processing Started",
    PLAN_ERROR = "Error Occurred"
}

/**
 * Interface for Edit port rotation response
 */
 export interface IPortResponseModel {
    responseStatus: IResponseStatus;
}

/**
 *  Interface for edit port rotation model
 */
export interface IEditPortRotationModel {
    portList: IPortList[];
}

/**
 *  Interface for edit port rotation
 */
export interface IEditPortRotation{
    id: number;
    portOrder: number;
    portTimezoneId?: number;
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
    etaActual?: string;
    etdActual?: string;
    name: string;
    index?: number;
    isFutureDate?: boolean;
    type?: string;
    isDateEditable?: boolean;
    isTimeEditable?: boolean;
    isDistanceEditable?: boolean;
    currentPort?: boolean;
    isEditable?: boolean;
    isSelected?: boolean;
    isFocused?: boolean;
    portDate?: string;
    portTime?: string;
    operation?: string;
}

/**
 * Interface for Port Voyage Details model
 */
 export class IVoyagePortDetails {
    portOrder: number;
    operationType: string;
    portId?: number;
    portRotationId?: number;
    operationId?: number;
    name?: string;
}

/**
 * Model for new voyage
 */
 export class NewVoyageModel {
    public voyageNo!: string;
    public captainId!: number;
    public chiefOfficerId!: number;
    public startDate!: string;
    public endDate!: string;
    public startTimezoneId!: number;
    public endTimezoneId!: number;
}

/**
 * Model for new voyage api response
 */
export class NewVoyageResponseModel implements IResponse {
    responseStatus?: IResponseStatus;
    status: string;
    message?: string;
    errorCode?: string;
    correlationId?: string;
    voyageId: number;
}

 /**
 * Interface for algo response
 *
 * @export
 * @interface IAlgoResponse
 */
  export interface IAlgoResponse {
    responseStatus: IResponseStatus;
    algoErrors: IAlgoError[]
 }

 /**
 * Interface for algo error
 *
 * @export
 * @interface IAlgoError
 */
 export interface IAlgoError {
    errorHeading: string,
    errorDetails: string[]
 }


 /**
 * Interface for cargo tanks
 *
 * @export
 * @interface IShipCargoTank
 * @extends {ITank}
 */
export interface IShipCargoTank extends ITank {
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
    commodity?: ICargoQuantities;
    gridColumn?: string;
    percentageFilled?: string;
    fillingRatio?: string;
}

/**
 * Interface for cargo quantites model
 * @export
 * @interface ICargoQuantities
 */
export interface ICargoQuantities {
    fillingRatio?: any;
    tankId: number;
    tankName: string;
    actualWeight: number;
    plannedWeight: number;
    capacity: number;
    abbreviation: string;
    cargoId: number;
    colorCode: string;
    correctedUllage: number;
    difference?: number;
    isPositive?: boolean;
    quantity?: number;
    volume?: number;
    api?: number;
    percentageFilled?: string;
    isCommingle?: boolean;
    isCommingleCargo?: boolean;
    temperature?:any;
}

/**
 * Interface for loadable quality
 *
 * @export
 * @interface ILoadableQuantityCargo
 */
 export interface ILoadableQuantityCargo {
   id: number,
   grade: string,
   estimatedAPI: string,
   estimatedTemp: string,
   orderBblsdbs?: string,
   orderBbls60f?: string,
   maxLoadingRate?: string,
   orderedQuantity?: string,
   minTolerence?: string,
   maxTolerence?: string,
   loadableBblsdbs?: string,
   loadableBbls60f?: string,
   loadableLT?: string,
   loadableMT?: string,
   loadableKL?: string,
   differencePercentage?: string,
   differencePercentageValue?: number;
   differenceColor?: string
   cargoId?: number;
   minMaxTolerance?: string;
   slopQuantity?: number;
   timeRequiredForLoading?: string;
   loadingPorts?: string[];
   loadingPortsLabels?: string;
   cargoAbbreviation?: string;
   cargoNominationId?: number;
   colorCode?: string;
   maxDischargingRate?: string;
   blFigure?: string;
   shipFigure?: string;
   timeRequiredForDischarging?: string;
   protested?: ValueObject<IProtested>| any;
   isCommingled?: ValueObject<boolean> | any;
   isAdd?: boolean;
}

/**
 * Interface for protested object
 *
 * @export
 * @interface IProtested
 */
export interface IProtested {
  id: number;
  name: string;
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
 * Interface for cargo
 *
 * @export
 * @interface ICargo
 */
export interface ICargo {
  id: number;
  companyId?: number;
  actualWeight?: string;
  plannedWeight?: string;
  name?: string;
  abbreviation?: string;
  api?: number;
  ports?: IPort[];
  temp: string;
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
 * Interface for cargo response
 * @export
 * @interface
 */
export interface ICargoResponseModel {
  cargos: ICargo[],
  responseStatus: IResponseStatus;
}

/**
 * Interface for get confirm status
 *
 * @export
 * @interface IConfirmStatusResponse
 */
export interface IConfirmStatusResponse {
  responseStatus: IResponse;
  confirmed: boolean;
  loadablePatternStatusId: number;
  validated: boolean;
}

/**
 * Interface for instruction
 *
 * @export
 * @interface IInstruction
 */
 export interface IInstruction {
    instruction: string;
    id: number;
}

/**
 * Interface for instruction api response
 *
 * @export
 * @interface IInstructionResponse
 */
 export interface IInstructionResponse {
    responseStatus: IResponseStatus;
    instructions: IInstruction[];
}

/**
 * Interface for instruction
 *
 * @export
 * @interface ITankDetails
 */
export interface ITankDetails {
    displayOrder: number,
    group: number,
    id: number,
    name: string,
    order: number,
    shortName: string,
    slopTank: boolean,
}

/**
 * Interface for tank api response
 *
 * @export
 * @interface ITankResponse
 */
 export interface ITankResponse {
    responseStatus: IResponseStatus;
    cargoVesselTanks: ITankDetails[];
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
 * ENUM for operations
 *
 * @export
 * @enum {number}
 */
export enum OPERATIONS {
  LOADING = 1,
  DISCHARGING = 2,
  BUNKERING = 3,
  TRANSIT = 4,
  STSLOADING = 5,
  STSDISCHARGING = 6
}

/**
 * Enum for operation mode labels
 *
 * @export
 * @enum {number}
 */
export enum OPERATIONS_LABEL {
  LOADING = 'LOADING',
  DISCHARGING = 'DISCHARGING'
}

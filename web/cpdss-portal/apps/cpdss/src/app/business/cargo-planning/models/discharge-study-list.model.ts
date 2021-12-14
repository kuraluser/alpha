import { IResponse , ValueObject , IMode  , IPercentage } from '../../../shared/models/common.model';
import { DISCHARGE_STUDY_STATUS , ICargo, ITankDetails } from '../../core/models/common.model';
import { IPort, IPortList , IDischargeStudyPortList , IInstruction } from '../../core/models/common.model';


/**
 * Model for discharge study list
 */
export class IDischargeStudy {
    id?: number;
    slNo?: number;
    name?: string;
    status?: string;
    statusId?: DISCHARGE_STUDY_STATUS;
    detail?: string;
    createdDate?: string;
    lastEdited?:string;
    charterer?: string;
    subCharterer?: string;
    draftMark?: number;
    loadLineXId?: number;
    draftRestriction?: number;
    maxAirTemperature?: number;
    maxWaterTemperature?: number;
    dischargingPortIds?: number[];
    dischargingCargoId?: number;
    createdFromId?: number;
    loadOnTop?: boolean;
    isEditable?: boolean;
    isDeletable?: boolean;
    isDuplicate?: boolean;
    isActionsEnabled?: boolean;
    isCargoNominationComplete?: boolean;
    isPortsComplete?: boolean;
    isOhqComplete?: boolean;
    isObqComplete?: boolean;
    isDischargingPortComplete?: boolean;
    dischargeStudyStatusLastModifiedTime?: string;
    ohqPorts?: IDischargeOHQStatus[];
    lastLoadingPortETD?: string;
    isDischargeStudyComplete?: boolean;
}

/**
 * Interface for OHQ ports status
 *
 * @export
 * @interface IDischargeOHQStatus
 */
 export interface IDischargeOHQStatus {
    id: number;
    isPortRotationOhqComplete: boolean;
}

/**
 * Interface for discharge study attachments
 *
 * @export
 * @interface IDischargeStudyAttachment
 */
export interface IDischargeStudyAttachment {
    id: number;
    fileName: string;
}


/**
 * Interface for discharge commingle Cargo
 *
 * @export
 * @interface ILoadableQuantityCommingleCargo
 */
 export interface ILoadableQuantityCommingleCargo {
    id: number,
    grade: string,
    tankName: string,
    quantity: string,
    api: string,
    temp: string,
    cargo1Abbreviation: string,
    cargo2Abbreviation: string,
    cargo1Percentage: string,
    cargo2Percentage: string,
    cargo1Bblsdbs: string,
    cargo2Bblsdbs: string,
    cargo1Bbls60f: string,
    cargo2Bbls60f: string,
    cargo1LT: string,
    cargo2LT: string,
    cargo1MT: string,
    cargo2MT: string,
    cargo1KL: string,
    cargo2KL: string,
    orderedQuantity: string,
    priority: string,
    loadingOrder: string,
    cargo1NominationId: string,
    cargo2NominationId: string,
    tankId: string,
    fillingRatio: string,
    correctedUllage: string,
    rdgUllage: string,
    correctionFactor: string,
    slopQuantity: string,
    actualQuantity: string,
    toppingSequence: string,
    timeRequiredForLoading: string,
    tankShortName:string,
    toppingOffCargoId: string,
    colorCode?: string;
}

/**
 * Interface for discharge commingle Cargo
 *
 * @export
 * @interface ICommingleCargoDispaly
 */
 export interface ICommingleCargoDispaly {
    id: number,
    grade: string,
    tankName: string,
    quantity: string,
    quantityBLS: number;
    api: string,
    temp: string,
    cargoPercentage: string,
    cargoBblsdbs: string,
    cargoBbls60f: string,
    cargo1LT: number;
    cargo2LT: number;
    cargo1KL: number;
    cargo2KL: number;
    cargoLT: string
    cargoMT: string,
    cargoKL: string,
}


/**
 * Interface for cargo details us value object
 *
 * @export
 * @interface IBillingFigValueObject
 */
 export interface IBillingFigValueObject {
    port:string;
    quantityBbls: ValueObject<number>;
    lt: ValueObject<number>;
    quantityMt: ValueObject<number>;
    quantityKl: ValueObject<number>;
    api:ValueObject<number>;
    temperature: ValueObject<number>;
    cargoName: string;
    cargoAbbrevation: string;
    cargoColor: ValueObject<string>;
}

/**
 * Interface for cargo details billing Figure
 *
 * @export
 * @interface IBillingOfLaddings
 */
 export interface IBillingOfLaddings {
    id: number,
    loadingPort: number[],
    cargoNominationId: number,
    quantityBbls: number,
    quantityMt: number,
    quantityKl: number,
    api: number,
    temperature: number,
    cargoColor: string,
    cargoName: string,
    cargoAbbrevation: string,
}

/**
 * Interface for discharge study list data
 *
 * @export
 * @interface IDischargeAllDropdownData
 */
 export interface IDischargeAllDropdownData {
    cargoList: ICargo[];
}



/**
 * Interface for port details value object
 *
 * @export
 * @interface IPortDetailValueObject
 */
export interface IPortDetailValueObject {
    id: number;
    port: IPort;
    instruction: IInstruction;
    portTimezoneId: number;
    operationId: number;
    maxDraft: number;
    cargoDetail: IPortCargo[];
    enableBackToLoading: boolean;
    freshCrudeOil: boolean;
    cow: boolean;
    freshCrudeOilQuantity: number;
    freshCrudeOilTime: string;
    backLoadingDetails: IBackLoadingDetails[];
}

/**
 * Interface for cow details value object
 *
 * @export
 * @interface ICowDetailsValueObject
 */
export interface ICowDetailsValueObject {
    cow: IMode;
    percentage: IPercentage;
    tank: ITankDetails[];
}

export interface IBackLoadingDetails {
    cargo: ValueObject<ICargo>;
    mt: ValueObject<number>;
    id: ValueObject<number>;
    kl: ValueObject<number>;
    bbls: ValueObject<number>;
    color: ValueObject<string>;
    api: ValueObject<string>;
    temp: ValueObject<string>;
    isDelete: boolean;
    isAdd: boolean;
    isNew: boolean;
    abbreviation: ValueObject<string>;
    storedKey: ValueObject<string>;
}

/**
 * Interface for port cargo details value object
 *
 * @export
 * @interface IPortCargo
 */
export interface IPortCargo {
    id: ValueObject<string>;
    color: ValueObject<string>;
    cargo: ValueObject<ICargo>;
    bbls: ValueObject<string>;
    mt: ValueObject<string>;
    kl: ValueObject<string>;
    mode: ValueObject<IMode>;
    api: ValueObject<number>;
    temp: ValueObject<number>;
    maxKl: ValueObject<number>;
    abbreviation: ValueObject<string>;
    storedKey: ValueObject<string>;
    sequenceNo: ValueObject<string>;
    emptyMaxNoOfTanks: ValueObject<boolean>;
}



/**
 * Interface for discharge study list data
 *
 * @export
 * @interface IDischargeStudyDropdownData
 */
 export interface IDischargeStudyDropdownData {
    mode: IMode[];
    tank: ITankDetails[];
    instructions: IInstruction[];
    cargoList: ICargo[];
    portList: IPort[];
    percentageList: IPercentage[];
}

/**
 * Interface for Cargonomination api response
 *
 * @export
 * @interface ICargoNominationDetailsResponse
 */
 export interface ICargoNominationDetailsResponse {
    responseStatus: IResponse;
    billOfLaddings: IBillingOfLaddings[],
    loadableQuantityCommingleCargoDetails: ILoadableQuantityCommingleCargo[];
}

/**
 * Interface for discharge list api.
 *
 * @export
 * @interface IDischargeStudiesResponse
 */
export interface IDischargeStudiesResponse {
    responseStatus: IResponse;
    dischargeStudies: IDischargeStudy[];
}

/**
 * Interface for dicharge study api.
 *
 * @export
 * @interface IDischargeStudyResponse
 * @extends {IResponse}
 */
export interface IDischargeStudyResponse extends IResponse {
    dischargeStudyId: number;
}



/**
 * Interface for Discharge Study list api response.
 *
 * @export
 * @interface IDischargeStudiesResponse
 */
export interface IDischargeStudiesResponse {
    responseStatus: IResponse;
    loadableStudies: IDischargeStudy[]; //variable name is loadablestudies since it comes from backend in that name.
}

/**
 * Interface for Discharge Study api response
 *
 * @export
 * @interface IDischargeStudyDetailsResponse
 */
 export interface IDischargeStudyDetailsResponse {
    responseStatus: IResponse;
    dischargeStudyId: number;
    loadableQuantity: number;
    cowId: number;
    percentage: number;
    tanks: number[];
    portList: IDischargeStudyPortListDetails[];
}

/**
 * Interface for discharge study port list
 *
 * @export
 * @interface IDischargeStudyPortListDetails
 */
export interface IDischargeStudyPortListDetails {
    id: number;
    portId: number;
    maxDraft: number;
    portTimezoneId: number;
    instructionId: number[];
    isBackLoadingEnabled: boolean;
    backLoading: IDischargeStudyBackLoadingDetails[];
    operationId: number;
    freshCrudeOilQuantity?: number;
    freshCrudeOil?: boolean;
    freshCrudeOilTime?: number;
    cow: boolean;
    cargoNominationList: IDischargeStudyCargoNominationList[];
}

/**
 * Interface for discharge study backloading details
 *
 * @export
 * @interface IDischargeStudyBackLoadingDetails
 */
export interface IDischargeStudyBackLoadingDetails {
    id: number | string;
    color: string;
    cargoId: number;
    api: number;
    temperature: number;
    abbreviation: string;
    quantity: number;
}

/**
 * Interface for discharge study cargo nomination
 *
 * @export
 * @interface IDischargeStudyCargoNominationList
 */
export interface IDischargeStudyCargoNominationList {
    id: number | string;
    maxQuantity: number;
    priority: number;
    color: string;
    cargoId: number;
    abbreviation: string;
    quantity: number;
    api: number;
    temperature: number;
    mode: number;
    sequenceNo: string;
    emptyMaxNoOfTanks: boolean;
}

/**
 * Interface for port cargo response
 *
 * @export
 * @interface IPortCargoResponse
 */
 export interface IPortCargoResponse {
    responseStatus: IResponse;
    portWiseCorges: IPortCargoDetails[];
}

/**
 * Interface for port cargo details
 *
 * @export
 * @interface IPortCargoDetails
 */
 export interface IPortCargoDetails {
    portId: number,
    cargos: ICargo[]
}

/**
 * Interface for cargo history details
 *
 * @export
 * @interface ICargoHistoryDetails
 */
export interface ICargoHistoryDetails {
    api: string;
    cargoId: number;
    loadingPortId: number;
    temperature: string
    vesselId: number
}



import { IResponse , ValueObject , IMode  , IPercentage } from '../../../shared/models/common.model';
import { ICargo, IInstruction , ITankDetails , IPort } from '../../core/models/common.model';
import { IDischargeStudy } from './discharge-study-list.model';

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
    portList:IPort[];
    percentageList: IPercentage[];
}


/**
 * Interface for port details value object
 *
 * @export
 * @interface IPortDetailValueObject
 */
 export interface IPortDetailValueObject {
    portName: string;
    instruction: IInstruction;
    draftRestriction: number;
    cargoDetail: IPortCargo[];
    cow: IMode;
    dischargeRate: number,
    isBackLoadingEnabled: boolean;
    tank: string[];
    backLoadingDetails: IBackLoadingDetails[];
}
/**
 *
 * Interface for back loading details
 *
 * @export
 * @interface IBackLoadingDetails
 */
export interface IBackLoadingDetails {
    cargo: ValueObject<ICargo>;
    mt: ValueObject<number>;
    kl: ValueObject<number>;
    bbls: ValueObject<number>;
    color: ValueObject<string>;
    api: ValueObject<string>;
    temp: ValueObject<string>
    isDelete: boolean;
    isAdd: boolean;
    abbreviation: ValueObject<string>;
    cargoAbbreviation: string;
}

/**
 * Interface for port cargo details value object
 *
 * @export
 * @interface IPortCargo
 */
 export interface IPortCargo {
    color: ValueObject<string>;
    cargo: ValueObject<ICargo>;
    bbls: ValueObject<string>;
    mt: ValueObject<string>;
    kl: ValueObject<string>;
    dischargeTime: number;
    api: ValueObject<number>;
    temp: ValueObject<number>;
    maxKl: ValueObject<number>;
    sequenceNo: ValueObject<number>;
    abbreviation: ValueObject<string>;
    dischargeRate: ValueObject<number>;
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
 * Interface for Discharge Study api response
 *
 * @export
 * @interface IDischargeStudyDetailsResponse
 */
 export interface IDischargeStudyDetailsResponse {
    responseStatus: IResponse;
    dischargeStudyId: number;
    dischargePatternId: number;
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
    cowId: number;
    percentage: number;
    instructionId: number[];
    isBackLoadingEnabled: boolean;
    backLoading: IDischargeStudyBackLoadingDetails[];
    tanks: number[];
    dischargeRate: number;
    cargoNominationList: IDischargeStudyCargoNominationList[];
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
    color: string;
    cargoId: number;
    abbreviation: string;
    quantity: number;
    api: number;
    temperature: number;
    dischargeTime: number;
    sequenceNo: number;
    dischargeRate: number;
}

import { IResponse , ValueObject , IMode  , IPercentage } from '../../../shared/models/common.model';
import { ICargo, IInstruction , ITankDetails } from '../../core/models/common.model';


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
    draftRestriction: string;
    cargoDetail: IPortCargo[];
    cow: IMode;
    dischargeRate: string,
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
    time: number;
    api: ValueObject<number>;
    temp: ValueObject<number>;
    maxKl: ValueObject<number>;
    abbreviation: ValueObject<string>;
}

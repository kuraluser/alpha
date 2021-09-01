import { IResponseStatus } from './../../../shared/models/common.model';

/**
 * Interface for Discharging API response
 *
 * @export
 * @interface IDischargingInstructionResponse
 */
export interface IDischargingInstructionResponse {
    responseStatus: IResponseStatus;
    dischargingInstructionSubHeader: IDischargingInstructionSubHeaderData[];
    dischargingInstructionGroupList: IDischargingInstructionGroup[];
}

/**
 * interface for Discharging instruction sub-header
 *
 * @export
 * @interface IDischargingInstructionSubHeaderData
 */
export interface IDischargingInstructionSubHeaderData {
    instructionTypeId: number;
    instructionHeaderId: number;
    subHeaderId: number;
    subHeaderName: string;
    isChecked: boolean;
    isEditable: boolean;
    isSingleHeader: boolean;
    dischargingInstructionsList?: IDischargingInstructionList[];
}

/**
 * interface for Discharging instruction group
 *
 * @export
 * @interface IDischargingInstructionGroup
 */
export interface IDischargingInstructionGroup {
    groupId: number;
    groupName: string;
    selected?: boolean;
}

export interface IDischargingInstructionList {
    instructionTypeId: number;
    instructionHeaderId: number;
    instructionId: number;
    instruction: string;
    isChecked: boolean;
}
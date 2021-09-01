/**
 * Interface for instruction tab data
 *
 * @export
 * @interface IInstructionTabDetails
 */
export interface IInstructionTabDetails {
    loadingInstructionSubHeader: ILoadingInstructionSubHeaderData[];
    loadingInstructionGroupList: ILoadingInstructionGroup[];
}

/**
 * Interface for instruction side panel data
 *
 * @export
 * @interface ILoadingInstructionGroup
 */
export interface ILoadingInstructionGroup {
    groupId: number;
    groupName: string;
    selected?: boolean;
}

/**
 * Interface for instruction sub header data
 *
 * @export
 * @interface ILoadingInstructionSubHeaderData
 */
export interface ILoadingInstructionSubHeaderData {
    instructionTypeId: number;
    instructionHeaderId: number;
    subHeaderId: number;
    subHeaderName: string;
    isChecked: boolean;
    loadingInstructionsList: ILoadingInstructionList[];
}

/**
 * Interface for instruction list
 *
 * @export
 * @interface ILoadingInstructionList
 */
export interface ILoadingInstructionList {
    instructionTypeId: number;
    instructionHeaderId: number;
    instructionId: number;
    instruction: string;
    isChecked: boolean;
}
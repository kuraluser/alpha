/**
 * Interface for instruction add/edit
 *
 * @export
 * @interface IInstructionDetails
 */
export interface IInstructionDetails {
    instructionHeaderId?: number;
    isSingleHeader?: boolean;
    isSubHeader?: boolean;
    subHeaderId?: number;
    instruction?: string;
    isChecked?: boolean;
    instructionId?: number;
}

/**
 * Interface for instruction delete
 *
 * @export
 * @interface IDeleteData
 */
export interface IDeleteData {
    instructionId: number;
}

/**
 * Interface for instruction satus save data
 *
 * @export
 * @interface IUpdateInstructionStatusDetails
 */
export interface IUpdateInstructionStatusDetails {
    instructionId: number;
    isChecked: boolean;
}

/**
 * Interface for instruction satus save data
 *
 * @export
 * @interface ISaveStatusData
 */
export interface ISaveStatusData{
    instructionList: IUpdateInstructionStatusDetails[];
}
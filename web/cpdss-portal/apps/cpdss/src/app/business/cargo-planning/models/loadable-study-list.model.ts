import { IResponse } from '../../../shared/models/common.model';
/**
 * Model for loadable study list 
 */
export class LoadableStudy {
    public id: number;
    slNo: number;
    public name: string;
    public status: string;
    public statusId: LOADABLE_STUDY_STATUS;
    public detail: string;
    public createdDate: string;
    public charterer: string;
    public subCharterer: string;
    public draftMark: number;
    public loadLineXId: number;
    public draftRestriction: number;
    public maxAirTemperature: number;
    public maxWaterTemperature: number;
    public dischargingPortIds?: number[];
    public loadableStudyStatusLastModifiedTime ?: string;
    loadableStudyAttachment?: ILoadableStudyAttachment[];
    dischargingCargoId: number;
    createdFromId?: number;
    loadOnTop: boolean;
    isEditable?: boolean;
    isDeletable?: boolean;
    isDuplicate?: boolean;
    isActionsEnabled?: boolean;
}

/**
 * Interface for loadable study attachments
 *
 * @export
 * @interface ILoadableStudyAttachment
 */
export interface ILoadableStudyAttachment {
    id: number;
    fileName: string;
}

/**
 * Interface for dischrge ports ids
 *
 * @export
 * @interface IDischargingPortIds
 */
export interface IDischargingPortIds {
    portIds: number[];
    dischargingCargoId: number;
}

/**
 * Interface for loadable study save response
 *
 * @export
 * @interface ILoadableStudyResponse
 */
export interface ILoadableStudyResponse extends IResponse {
    loadableStudyId: number;
}

/**
 * Interface for loadable Studies response
 *
 * @export
 * @interface ILoadableStudiesResponse
 */
export interface ILoadableStudiesResponse {
    requestStatus: any;
    loadableStudies: LoadableStudy[];
}

/**
 * Interface for loadable pattern response
 *
 * @export
 * @interface LoadablePattern
 */
export interface LoadablePattern {
    loadablePatternId: number;
    caseNumber: number;
    loadableStudyStatusId: number;
}

/* Interface for loadable Patterns response
*
* @export
* @interface ILoadablePatternsResponse
*/
export interface ILoadablePatternsResponse {
   requestStatus: any;
   loadablePatterns: LoadablePattern[];
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
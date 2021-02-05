import { IResponse } from '../../../shared/models/common.model';

/**
 * Model for loadable study list 
 */
export class LoadableStudy {
    public id: number;
    slNo: number;
    public name: string;
    public status: string;
    public statusId: number;
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
    loadableStudyAttachment?: ILoadableStudyAttachment[];
    dischargingCargoId: number;
    createdFromId?: string;
    loadOnTop: boolean;
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
 * Model for Table Columns
 */
export class TableColumns {
    public field: string;
    public header: string;
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
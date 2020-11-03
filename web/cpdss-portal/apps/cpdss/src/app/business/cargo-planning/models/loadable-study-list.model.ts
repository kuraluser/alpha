import { ValueObject } from '../../../shared/models/common.model';

/**
 * Model for loadable study list 
 */
export class LoadableStudy {
    public id: number;
    public name: string;
    public status: string;
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
}

/**
 * Interface for dischrge ports ids
 *
 * @export
 * @interface IDischargingPortIds
 */
export interface IDischargingPortIds {
    portIds: number[];
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
export interface ILoadableStudyResponse {
    responseStatus: any;
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
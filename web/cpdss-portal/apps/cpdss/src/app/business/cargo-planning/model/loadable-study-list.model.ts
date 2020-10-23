/**
 * Model for loadable study list 
 */
export class LoadableStudies {
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
    public maxTempExpected: number;
}
/**
 * Model for voyage list 
 */
export class VoyageList {
    public name: string;
    public code: string;
}
/**
 * Model for Table Columns
 */
export class TableColumns {
    public field: string;
    public header: string;
}
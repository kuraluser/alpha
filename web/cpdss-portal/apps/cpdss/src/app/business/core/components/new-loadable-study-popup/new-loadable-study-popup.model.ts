import { IdraftMarks } from '../../models/common.models';

/**
 *  model for new-loadable-study
 */
export interface INewLoadableStudy {
    id: number;
    createdFromId: number;
    name: string;
    detail: string;
    charterer: string;
    subCharterer: string;
    draftMark: number;
    loadLineXId: number;
    draftRestriction: number;
    maxAirTempExpected: number;
    maxWaterTempExpected: number;
    attachMail: string[];
}


/**
 * Interface for IDropdown Event
 *
 * @export
 * @interface IDropdownEvent
 */
export interface IDropdownEvent {
    originalEvent: Event;
    value: any;
}
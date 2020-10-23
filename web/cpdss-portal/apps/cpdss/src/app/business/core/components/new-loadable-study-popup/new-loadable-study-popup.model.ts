import { IdraftMarks } from '../../models/common.models';

/**
 *  model for new-loadable-study
 */
export interface INewLoadableStudyPopupModel {
    loadableStudyId: string;
    duplicateExisting: string;
    newLoadableStudyName: string;
    enquiryDetails: string;
    attachMail: string[];
    charterer: string;
    subCharterer: string;
    loadLine: string;
    draftMark: string;
    draftRestriction: string;
    maxTempExpected: string;
}

export interface ILoadLineLists {
    id: number;
    name: string;
    draftMarks: string[];
}
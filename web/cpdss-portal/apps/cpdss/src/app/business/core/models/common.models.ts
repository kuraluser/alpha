import { IResponseStatus } from '../../../shared/models/common.model';

/**
 *  vessel-info model
 */
export interface IVesselInfo {
    responseStatus: {
        status: string
    },
    vessels: IVessels[]
}

/**
 *  model for new-loadable-study-list-names
 */
export interface INewLoadableStudyListNames {
    name: string;
    value: string;
}

/**
 *  model for loadline-list
 */
export interface ILoadLineList {
    id: number;
    name: string;
    draftMarks: IdraftMarks[]
}

/**
 *  model for draft-marks
 */
export interface IdraftMarks {
    id: number,
    name: string
}

/**
 *  model for vessel-info
 */
export interface IVessels {
    id: string,
    name: string,
    captainId: string,
    captainName: string,
    chiefOfficerId: string,
    chiefOfficerName: string,
    loadlines: ILoadLineList[]
}

/**
 * Model for voyage list 
 */
export class Voyage {
    public voyageNo: string;
    public id: number;
}

export interface IVoyageResponse {
    responseStatus: IResponseStatus;
    voyages: Voyage[];
}
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
    name: string;
    value: string;
    draftMarks: IdraftMarks[]
}

/**
 *  model for draft-marks
 */
export interface IdraftMarks {
    id: string,
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
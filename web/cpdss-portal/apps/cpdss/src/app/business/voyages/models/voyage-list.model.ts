import { IResponse, ICargo, IPort } from '../../../shared/models/common.model';

/**
 *  model for new-loadable-study-list-names
 */
export interface IVoyageListResponse {
    responseStatus: IResponse;
    voyages: IVoyageList[];
    totalElements: number;
}

/**
 *  model for new-loadable-study-list-names
 */
export interface IVoyageList {
    voyageNo: string;
    id: number;
    plannedStartDate?: string;
    plannedEndDate?: string;
    actualStartDate?: string;
    actualEndDate?: string;
    status?: string;
    confirmedLoadableStudyId?: number;
    loadingPorts?: ILoadingPort[];
    dischargingPorts?: IPort[];
    cargos?: ICargo[];
    charterer?: string;
    loading?: string;
    discharging?: string;
    cargo?: string;
    isStart?: boolean;
    isStop?: boolean;
}

/**
 *  model for loading port
 */
export interface ILoadingPort {
    id: number;
    name: string;
}

/**
 *  model for new-loadable-study-list-names
 */
export interface IVoyageStatusResponse {
    responseStatus: IResponse;
    voyageId: number;
}




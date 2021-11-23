import { IResponseStatus } from './../../../../shared/models/common.model';

/**
 * Enum for simulator request types
 *
 * @export
 * @enum {number}
 */
export enum SIMULATOR_REQUEST_TYPE {
    STOWAGE_PLAN = "StowagePlan",
    LOADING_SEQUENCE = "LoadingSequence",
    DISCHARGE_SEQUENCE = "DischargeSequence"
}

/**
 * Interface for params to load simulator
 *
 * @export
 * @interface ISimulatorLoadParams
 */
export interface ISimulatorLoadParams {
    shipName: string;
    stowageData: any;
    loadicatorData: any;
    path: string;
    userName: string;
    userRole: string;
    requestType: string;
    url: string;
}

/**
 * Interface for get stowage plan JSON
 *
 * @export
 * @interface ISimulatorStowageResponse
 */
export interface ISimulatorStowageResponse {
    responseStatus: IResponseStatus;
    departureCondition: any;
}

/**
 * Interface for loading sequence plan JSON
 *
 * @export
 * @interface ISimulatorLoadingSequenceResponse
 */
export interface ISimulatorLoadingSequenceResponse {
    responseStatus: IResponseStatus;
    loadicatorJson: any;
    loadingJson: any;
}

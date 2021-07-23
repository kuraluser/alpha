import { CPDSSDB, ICargoConditions, IResponse } from "../../../shared/models/common.model";
import { ICargoQuantities, ILoadableQuantityCargo, IShipCargoTank } from "../../core/models/common.model";

/**
 * Interface for Loadable information api response
 *
 * @export
 * @interface ILoadingInformationResponse
 */
export interface ILoadingInformationResponse {
    responseStatus: IResponse;
    loadingDetails: ILoadingDetails;
    loadingRates: ILoadingRates;
    berthDetails: IBerthDetails;
    machineryInUses: IMachineryInUses;
    loadingStages: ILoadingStages;
    loadingSequences: ILoadingSequences;
    toppingOffSequence: IToppingOffSequence[];
    cargoVesselTankDetails: ICargoVesselTankDetails;
    loadingInfoId: number;
    synopticTableId: number;
    isLoadingInfoComplete: boolean;
}




/**
* Interface for loading details
*
* @export
* @interface ILoadingDetails
*/
export interface ILoadingDetails {
    timeOfSunrise: string;
    timeOfSunset: string;
    startTime: string;
    trimAllowed: ITrimAllowed;
}

/**
 * Interface for trim details
 *
 * @export
 * @interface ITrimAllowed
 */
export interface ITrimAllowed {
    initialTrim: number;
    maximumTrim: number;
    finalTrim: number;
}

/**
 * Interface for berth details
 *
 * @export
 * @interface IBerthDetails
 */
export interface IBerthDetails {
    availableBerths: IBerth[];
    selectedBerths: IBerth[];
}


/**
 * Interface for machinary in use
 *
 * @export
 * @interface IMachineryInUses
 */
export interface IMachineryInUses {
    pumpTypes: IPumpTypes[];
    vesselPumps: IVesselPumps[];
    loadingMachinesInUses: ILoadingMachinesInUses[];
}

/**
 * Interface for pump types
 *
 * @export
 * @interface IPumpTypes
 */
export interface IPumpTypes {
    id: number;
    name: string;
}

/**
 * Interface for vessel pumps
 *
 * @export
 * @interface IVesselPumps
 */
export interface IVesselPumps {
    id: number;
    vesselId: number;
    pumpTypeId: number;
    pumpName: string;
    pumpCode: string;
    pumpCapacity: number;
    capacity?: number;
    isUsing?: boolean;
    machineId?: number;
}

/**
 * Interface for loading machine
 *
 * @export
 * @interface ILoadingMachinesInUses
 */
export interface ILoadingMachinesInUses {
    id: number;
    loadingInfoId: number;
    pumpId: number;
    capacity: number;
    isUsing?: boolean;
}


/**
 * Interface for loading rates
 *
 * @export
 * @interface ILoadingRates
 */
export interface ILoadingRates {
    id: number;
    initialLoadingRate?: number;
    minLoadingRate: number;
    maxLoadingRate: number;
    reducedLoadingRate?: number;
    minDeBallastingRate: number;
    maxDeBallastingRate: number;
    noticeTimeRateReduction: number;
    noticeTimeStopLoading: number;
    lineContentRemaining?: number;
    shoreLoadingRate: number;
}

/**
 * Interface for berth details
 *
 * @export
 * @interface IBerth
 */
export interface IBerth {
    berthId: number;
    portId: number;
    berthName: string;
    maxShipDepth: number;
    seaDraftLimitation: number;
    airDraftLimitation: number;
    maxManifoldHeight: number;
    regulationAndRestriction: string;
    itemsToBeAgreedWith?: string;
    loadingInfoId: number;
    maxShpChannel: number;
    loadingBerthId: number;
    hoseConnections: string;
    maxLoa: string;
    maxDraft: string;
    lineDisplacement: string;
}

/**
 * Interface for loading stages
 *
 * @export
 * @interface ILoadingStages
 */
export interface ILoadingStages {
    id: number;
    trackStartEndStage: boolean;
    trackGradeSwitch: boolean;
    stageOffset: number;
    stageDuration: number;
    stageOffsetList: IStageOffset[];
    stageDurationList: IStageDuration[];
}

/**
* Interface for stage offset list
*
* @export
* @interface IStageOffsetList
*/
export interface IStageOffset {
    id: number;
    stageOffsetVal: number;
}

/**
* Interface for stage duration list
*
* @export
* @interface IStageDurationList
*/
export interface IStageDuration {
    id: number;
    duration: number;
}


/**
* Interface for topping off sequence
*
* @export
* @interface IToppingOffSequence
*/
export interface IToppingOffSequence {
    id: number;
    loadingInfoId: number;
    orderNumber: number;
    tankId: number;
    cargoId: number;
    shortName: string;
    cargoName: string;
    cargoAbbreviation: string;
    colourCode: string;
    remark: string;
    ullage: number;
    quantity: number;
    fillingRatio: number;
    api: number;
displayOrder: number;
temperature: number;
}

/**
* Interface for loading sequence
*
* @export
* @interface ILoadingSequences
*/
export interface ILoadingSequences {
    reasonForDelays: IReasonForDelays[];
    loadingDelays: ILoadingDelays[];
}

/**
* Interface for reason for delays
*
* @export
* @interface IReasonForDelays
*/
export interface IReasonForDelays {
    id: number;
    reason: string;
}

/**
* Interface for loading delays
*
* @export
* @interface ILoadingDelays
*/
export interface ILoadingDelays {
    id: number;
    loadingInfoId: number;
    reasonForDelayId: number;
    duration: number;
    cargoId: number;
    quantity: number;
}

/**
* Interface for cargo vessel tank details
*
* @export
* @interface ICargoVesselTankDetails
*/
export interface ICargoVesselTankDetails {
    cargoConditions: ICargoConditions[];
    cargoTanks: IShipCargoTank[][];
    cargoQuantities: ICargoQuantities[];
    loadableQuantityCargoDetails: ILoadableQuantityCargo[];
}

/**
* Interface for loading information list
*
* @export
* @interface ILoadingInformation
*/
export interface ILoadingInformation {
    loadingInfoId: number;
    synopticalTableId: number;
    loadingDetails: ILoadingDetails;
    loadingRates: ILoadingRates;
    loadingBerths: IBerth[];
    loadingMachineries: ILoadingMachinesInUses[];
    loadingStages: ILoadingStagesDetails;
    loadingDelays: ILoadingDelays[];
    toppingOffSequence: IToppingOffSequence[];
    vesselId?: number;
    voyageId?: number;
}

/**
 * Interface for loading stages for save
 *
 * @export
 * @interface ILoadingStagesDetails
 */
 export interface ILoadingStagesDetails {
    trackStartEndStage: boolean;
    trackGradeSwitch: boolean;
    stageOffset: IStageOffset;
    stageDuration: IStageDuration;
}


/**
* Interface for loading information save response
*
* @export
* @interface ILoadingInformationSaveResponse
*/
export interface ILoadingInformationSaveResponse {
    loadingInfoId: number;
    loadingInformation: ILoadingInformationResponse;
    responseStatus: IResponse;
    synopticalTableId: number;
    vesseld: number;
    voyageId: number;
}

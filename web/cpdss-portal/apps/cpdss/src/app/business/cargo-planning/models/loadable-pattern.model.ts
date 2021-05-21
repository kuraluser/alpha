import { IResponseStatus } from '../../../shared/models/common.model';
import { ICargoTank, ILoadableCargo, IBallastStowageDetails,  IBallastTank} from '../../core/models/common.model';
import { ICommingleDetails, ILoadablePlanSynopticalRecord, ILoadableQuantityCargo } from './cargo-planning.model';


/**
 * Interface for  loadable pattern api response
 *
 * @export
 * @interface ILoadablePatternResponse
 */
export interface ILoadablePatternResponse {
    responseStatus: IResponseStatus;
    tankLists: ICargoTank[][];
    loadablePatterns: ILoadablePattern[];
    loadablePatternCreatedDate: string;
    loadableStudyName: string;
    frontBallastTanks: IBallastTank[][];
    centerBallastTanks: IBallastTank[][];
    rearBallastTanks: IBallastTank[][];
    confirmPlanEligibility: boolean;
}


/**
 * Interface for  loadable pattern
 *
 * @export
 * @interface ILoadablePattern
 */
export interface ILoadablePattern {
    loadablePatternId: number;
    constraints: string[];
    loadablePatternCargoDetails: ILoadablePatternCargoDetail[];
    loadablePlanStowageDetails: ILoadablePlanStowageDetails[];
    stabilityParameters: IStabilityParameter;
    loadableStudyStatusId: number;
    caseNumber: number;
    loadableQuantityCargoDetails: ILoadableQuantityCargo[];
    loadablePlanBallastDetails: IBallastStowageDetails[];
    loadablePlanSynopticRecord: ILoadablePlanSynopticalRecord;
    loadableQuantityCommingleCargoDetails: ICommingleDetails[];
}

/**
 * Interface for  loadable pattern cargo details
 *
 * @export
 * @interface ILoadablePatternCargoDetail
 */
export interface ILoadablePatternCargoDetail extends ILoadableCargo {
    priority: number;
    cargoAbbreviation: string;
    cargoColor: string;
    quantity: number;
    isCommingle: boolean;
    loadablePatternCommingleDetailsId: number;
    orderedQuantity: number;
    loadingOrder: number;
    api: number;
    difference?: number;
}

/**
 * Interface for cargo tank details
 *
 * @export
 * @interface ILoadablePlanStowageDetails
 */
export interface ILoadablePlanStowageDetails extends ILoadableCargo {
    id?: number;
    tankId: number;
    cargoAbbreviation: string;
    weight?: number;
    correctedUllage: number;
    fillingRatio: string;
    tankName: string;
    rdgUllage: number;
    correctionFactor: number;
    observedM3?: number;
    observedBarrels?: number;
    observedBarrelsAt60?: number;
    api: number;
    temperature: number;
    colorCode: string;
    quantityMT: string;
    isCommingle?: boolean;
}

/**
 * Interface for stability parameter
 *
 * @export
 * @interface IStabilityParameter
 */
export interface IStabilityParameter {
    forwardDraft: string;
    meanDraft: string;
    afterDraft: string;
    trim: string;
    heel: string;
    bendinMoment: string;
    shearForce: string;
}





import { IResponseStatus } from '../../../shared/models/common.model';
import { ICargoTank, ILoadableCargo } from '../../core/models/common.model';


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
    loadableStudyStatusId: number;
    caseNumber: number;
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
    quantity: string;
    isCommingle: boolean;
    loadablePatternCommingleDetailsId: number;
    orderedQuantity: string;
    loadingOrder: number;
}

/**
 * Interface for cargo tank details
 *
 * @export
 * @interface ILoadablePlanStowageDetails
 */
export interface ILoadablePlanStowageDetails extends ILoadableCargo {
    id: number;
    tankId: number;
    cargoAbbreviation: string;
    weight: number;
    correctedUllage: number;
    fillingRatio: number;
    tankName: string;
    rdgUllage: number;
    correctionFactor: number;
    observedM3: number;
    observedBarrels: number;
    observedBarrelsAt60: number;
    api: number;
    temperature: number;
    colorCode: string;
    quantityMT: string;
}



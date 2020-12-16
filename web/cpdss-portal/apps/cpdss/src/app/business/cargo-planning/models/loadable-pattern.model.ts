import { IResponseStatus } from '../../../shared/models/common.model';
import { ICargoTank } from '../../core/models/common.model';


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
    constraints: string;
    totalDifferenceColor: string;
    loadablePatternCargoDetails: ILoadablePatternCargoDetail[];
}

/**
 * Interface for  loadable pattern cargo details
 *
 * @export
 * @interface ILoadablePatternCargoDetail
 */
export interface ILoadablePatternCargoDetail {
    priority: number;
    cargoAbbreviation: string;
    cargoColor: string;
    tankId: number;
    quantity: string;
    difference: string;
    differenceColor: string;
    loadablePatternDetailsId: number;
    isCommingle: boolean;
    loadablePatternCommingleDetailsId: number;
}



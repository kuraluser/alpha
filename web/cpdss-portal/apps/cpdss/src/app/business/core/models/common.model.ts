import { IResponseStatus } from '../../../shared/models/common.model';
import { ILoadablePatternCargoDetail } from '../../cargo-planning/models/loadable-pattern.model';

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

/**
 * Interface for voyage response
 *
 * @export
 * @interface IVoyageResponse
 */
export interface IVoyageResponse {
    responseStatus: IResponseStatus;
    voyages: Voyage[];
}

/**
 * Interface for tank data
 *
 * @export
 * @interface ITank
 */
export interface ITank {
    id: number;
    categoryId: number;
    categoryName: string;
    name: string;
    frameNumberFrom: number;
    frameNumberTo: number;
    shortName: string;
    heightFrom?: number;
    heightTo?: number;
    fillCapcityCubm: number;
    fullCapacityCubm?: string;
    density: number;
    group: number;
    order: number;
    slopTank: boolean;
    commodity?: any;
    gridColumn?: string;
    percentageFilled?: string;
}

/**
 * Interface for  cargo tank layout tank
 *
 * @export
 * @interface ICargoTank
 */
export interface ICargoTank extends ITank {
    id: number;
    categoryId: number;
    categoryName: string;
    name: string;
    frameNumberFrom: number;
    frameNumberTo: number;
    shortName: string;
    heightFrom?: number;
    heightTo?: number;
    fillCapcityCubm: number;
    fullCapacityCubm?: string;
    density: number;
    group: number;
    order: number;
    slopTank: boolean;
    commodity?: ILoadablePatternCargoDetail;
    gridColumn?: string;
    percentageFilled?: string;
}
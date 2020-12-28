import { IdraftMarks } from './common.model';

/**
 * Vessel details Model
 */
export interface IVessel {
    id: number;
    name: string;
    captainId: number;
    captainName: string;
    chiefOfficerId: number;
    chiefOfficerName: string;
    loadlines: ILoadLine[];
    charterer: string;
    imoNumber: string;
    flagPath: string;

}

/**
 * Loadline Model
 */
export interface ILoadLine {
    id: number;
    name: string;
    draftMarks: IdraftMarks[];
}

/** Interface for vessel api response */
export interface IVesselsResponse {
    responseStatus: any;
    vessels: IVessel[];
}
import { IResponseStatus } from '../../../shared/models/common.model';
import { ICargo, ICargoNomination } from './cargo-planning.model';


/**
 * Interface for   cargo master api response
 *
 * @export
 * @interface ICargoResponseModel
 */
export interface ICargoResponseModel {
    responseStatus: IResponseStatus;
    cargos: ICargo[];
}


/**
 * Interface for  commingle cargo api response
 *
 * @export
 * @interface ICommingleResponseModel
 */
export interface ICommingleResponseModel {
    responseStatus: IResponseStatus;
    commingleCargo: ICommingleCargo;
    cargoNominations: ICargoNomination[];
    purposes: IPurpose[];
    vesselCargoTanks: IVesselCargoTank[];
}

/**
 * Interface for  commingle cargo
 *
 * @export
 * @interface ICommingleCargo
 */
export interface ICommingleCargo {
    purposeId: number;
    slopOnly?: boolean;
    preferredTanks?: IPreferredTank[];
    cargoGroups?: ICargoGroup[];
}

/**
 * Interface for preferred tank in commingle cargo
 *
 * @export
 * @interface IPreferredTank
 */
export interface IPreferredTank {
    id: number;
}


/**
 * Interface for purpose in commingle 
 *
 * @export
 * @interface IPurpose
 */
export interface IPurpose {
    id: number;
    name: string;
}


/**
 * Interface for vessel tanks in commingle cargo
 *
 * @export
 * @interface IVesselCargoTank
 */
export interface IVesselCargoTank {
    id: number;
    name: string;
    shortName: string;
    slopTank: boolean;
}



/**
 * Interface for cargo groups in commingle cargo
 *
 * @export
 * @interface ICargoGroup
 */
export interface ICargoGroup {
    id: number;
    cargo1Id?: number;
    cargo2Id?: number;
    quantity?: number;
    cargo1IdPct?: number;
    cargo2IdPct?: number;
}

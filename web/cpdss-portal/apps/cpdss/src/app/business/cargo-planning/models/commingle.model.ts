import { IDataTableEvent } from '../../../shared/components/datatable/datatable.model';
import { IResponseStatus, ValueObject } from '../../../shared/models/common.model';
import { ICargoNomination } from './cargo-planning.model';


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
    vesselTanks: IVesselCargoTank[];
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
    preferredTanks?: IVesselCargoTank[];
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
    name?: string;
    shortName?: string;
    slopTank?: boolean;
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
    cargo1pct?: number;
    cargo2pct?: number;
    cargoNomination1Id?: number;
    cargoNomination2Id?: number;
}
/**
 *Interface for listData 
 */
export interface ICommingleManual {
    cargoNominationsCargo1: ICargoNomination[];
    cargoNominationsCargo2: ICargoNomination[];
    percentage: IPercentage[];
}
/**
 * Interface for Percentage
 */
export interface IPercentage {
    id: number;
    name: string;
}
/**
 * Interface for commingle value object
 */
export interface ICommingleValueObject {
    id: number;
    cargo1: ValueObject<ICargoNomination>;
    cargo2: ValueObject<ICargoNomination>;
    cargo1Id: ValueObject<number>;
    cargo2Id: ValueObject<number>;
    cargo1Color: ValueObject<string>;
    cargo2Color:  ValueObject<string>;
    cargo1pct: ValueObject<number>;
    cargo2pct: ValueObject<number>;
    cargo1IdPct: ValueObject<IPercentage>;
    cargo2IdPct: ValueObject<IPercentage>;
    quantity: ValueObject<number>;
    isAdd: boolean;
    isDelete?: boolean;
    storeKey?: number;
}
/**
 * Interface for all commingle  grid events
 */
 export interface ICommingleManualEvent extends IDataTableEvent {
    data: ICommingleValueObject;
    field: string;
    index: number;
    originalEvent: MouseEvent;

}

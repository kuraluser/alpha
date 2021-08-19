import { ValueObject } from '../../../shared/models/common.model';
/**
 * Interface for cargo details us value object
 *
 * @export
 * @interface ICargoDetailValueObject
 */
 export interface ICargoDetailValueObject {
    blRefNo:ValueObject<string>;
    bbl: ValueObject<number>;
    lt: ValueObject<number>;
    mt: ValueObject<number>;
    kl: ValueObject<number>;
    api:ValueObject<number>;
    temp: ValueObject<number>;
    cargoName: string;
    isAdd: boolean;
    cargoNominationId: number;
    portId: number;
    cargoId: number;
}

/**
 * Interface for cargo details
 *
 * @export
 * @interface ICargoDetail
 */
 export interface ICargoDetail {
    blRefNo:string;
    quantityBbls: number;
    quantityLT: number;
    quantityMt: number;
    quantityKl: number;
    api: number;
    temperature: number;
    cargoName: string;
    portId: number;
}

/**
 * Interface for tank details us value object
 *
 * @export
 * @interface ITankDetailsValueObject
 */
 export interface ITankDetailsValueObject {
    tankName: ValueObject<string>;
    ullage: ValueObject<number>;
    temperature?: ValueObject<number>;
    api?: ValueObject<number>;
    quantity?: ValueObject<number>;
    density?: ValueObject<number>;
}
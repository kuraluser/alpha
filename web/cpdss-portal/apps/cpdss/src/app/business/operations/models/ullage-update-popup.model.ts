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
}

/**
 * Interface for cargo details
 *
 * @export
 * @interface ICargoDetail
 */
 export interface ICargoDetail {
    blRefNo:string;
    bbl: number;
    lt: number;
    mt: number;
    kl: number;
    api: number;
    temp: number;
    cargoName: string;
}
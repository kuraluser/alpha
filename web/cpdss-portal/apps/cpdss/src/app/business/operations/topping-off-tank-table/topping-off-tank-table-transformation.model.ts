import { ValueObject } from '../../../shared/models/common.model';

/**
 * Interface for topping sequence value object
 *
 * @export
 * @interface IToppingOffSequenceValueObject
 */
 export interface IToppingOffSequenceValueObject {
    id: number;
    loadingInfoId: number;
    orderNumber: number;
    tankId: number;
    cargoId: number;
    shortName: string;
    cargoName: string;
    cargoAbbreviation: string;
    colourCode: string;
    remark: ValueObject<string>;
    ullage: ValueObject<number>;
    quantity: number;
    fillingRatio: number;
    api: number;
    displayOrder: number;
    temperature: number;
 }

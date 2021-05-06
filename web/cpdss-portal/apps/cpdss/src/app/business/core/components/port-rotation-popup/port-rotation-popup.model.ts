import {  IOperations, ValueObject } from '../../../../shared/models/common.model';
import { IPort } from '../../../core/models/common.model'

/**
 * Interface for ports value object
 *
 * @export
 * @interface IPortRotationValueObject
 */
 export interface IPortRotationValueObject {
    id: number;
    portOrder: number;
    portTimezoneId?: number;
    slNo: number;
    port: ValueObject<IPort>;
    portcode: ValueObject<string>;
    operation: ValueObject<IOperations>;
    layCan: ValueObject<string>;
    layCanFrom: ValueObject<string>;
    layCanTo: ValueObject<string>;
    eta: ValueObject<any>;
    etd: ValueObject<any>;
}
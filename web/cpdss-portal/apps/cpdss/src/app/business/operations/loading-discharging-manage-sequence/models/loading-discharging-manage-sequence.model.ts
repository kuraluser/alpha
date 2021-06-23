import { ValueObject } from '../../../../shared/models/common.model';
import { ILoadableQuantityCargo } from '../../../core/models/common.model';
import { IReasonForDelays } from '../../models/loading-information.model';

/**
 * Interface for ports value object
 *
 * @export
 * @interface ILoadingSequenceValueObject
 */
 export interface ILoadingSequenceValueObject {
    id: number;
    reasonForDelay: ValueObject<IReasonForDelays>;
    duration: ValueObject<number>;
    cargo: ValueObject<ILoadableQuantityCargo>;
    quantity: ValueObject<number>;
 }

/**
* Interface for cargo vessel tank details
*
* @export
* @interface ILoadingSequenceListData
*/
export interface ILoadingSequenceListData {
    loadableQuantityCargo: ILoadableQuantityCargo[];
    reasonForDelays: IReasonForDelays[];
}

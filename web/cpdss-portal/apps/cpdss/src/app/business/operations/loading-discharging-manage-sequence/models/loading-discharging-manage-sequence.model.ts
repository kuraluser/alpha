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
    duration: ValueObject<string>;
    cargo: ValueObject<ILoadableQuantityCargo>;
    quantity: number;
    isAdd: boolean;
    colorCode: string;
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
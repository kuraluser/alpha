import { IDataTableEvent } from '../../../shared/components/datatable/datatable.model';
import { IResponseStatus, ValueObject } from '../../../shared/models/common.model';
import { ICargoTank, ILoadableCargo } from '../../core/models/common.model';

/**
 * Interface for loadable quality plan table 
 *
 * @export
 * @interface ITableHeaderModel
 */
export interface ITableHeaderModel {
    field?: string;
    header: string;
    rowspan?: number;
    colspan?: number,
    subColumns?: IColumHeader[];
}

/**
 * Interface for sub column table header
*/
interface IColumHeader {
    field: string;
    header: string;
    rowspan?: number;
}

/**
 * Interface for Loadable Quantity api response
 *
 * @export
 * @interface ILoadablePlanResponse
 */
export interface ILoadablePlanResponse {
    responseStatus: IResponseStatus;
    loadableQuantityCargoDetails: ILoadableQuantityCargo[];
    loadableQuantityCommingleCargoDetails: ILoadableQuantityCommingleCargo[];
    tankLists: ICargoTank[][];
    loadablePlanStowageDetails: ICargoTankDetail[];
}

/**
 * Interface for loadable quality 
 *
 * @export
 * @interface ILoadableQuantityCargo
 */
export interface ILoadableQuantityCargo {
    id: number,
    grade: string,
    estimatedAPI: string,
    estimatedTemp: string,
    orderBblsdbs: string,
    orderBbls60f: string,
    minTolerence: string,
    maxTolerence: string,
    loadableBblsdbs: string,
    loadableBbls60f: string,
    loadableLT: string,
    loadableMT: string,
    loadableKL: string,
    differencePercentage: string,
    differenceColor: string
}

/**
 * Interface for loadable commingle Cargo
 *
 * @export
 * @interface ILoadableQuantityCommingleCargo
 */
export interface ILoadableQuantityCommingleCargo {
    grade: string,
    estimatedAPI: string,
    estimatedTemp: string,
    orderBblsdbs: string,
    orderBbls60f: string,
}

/**
 * Interface for loadableQuantity total calculate
*/
export interface ITotalLoadableQuality {
    orderBblsdbs: number,
    orderBbls60f: number,
    loadableBblsdbs: number,
    loadableBbls60f: number,
    loadableLT: number,
    loadableMT: number,
    loadableKL: number,
    differencePercentage: number
}

/**
 * Interface for cargo tank details
 *
 * @export
 * @interface ICargoTankDetail
 */
export interface ICargoTankDetail extends ILoadableCargo {
    id: number;
    tankId: number;
    cargoAbbreviation: string;
    weight: number;
    correctedUllage: number;
    fillingRatio: number;
    tankName: string;
    rdgUllage: number;
    correctionFactor: number;
    observedM3: number;
    observedBarrels: number;
    observedBarrelsAt60: number;
    api: number;
    temperature: number;
}

/**
 * Interface for cargo tank details value object
 *
 * @export
 * @interface ICargoTankDetailValueObject
 */
export interface ICargoTankDetailValueObject {
    id: number;
    tankId: number;
    cargoAbbreviation: string;
    weight: ValueObject<number>;
    correctedUllage: ValueObject<number>;
    fillingRatio: ValueObject<number>;
    tankName: string;
    rdgUllage: ValueObject<number>;
    correctionFactor: ValueObject<number>;
    observedM3: ValueObject<number>;
    observedBarrels: ValueObject<number>;
    observedBarrelsAt60: ValueObject<number>;
    api: ValueObject<number>;
    temperature: ValueObject<number>;
    isAdd: boolean;
}

/**
 * Interface for cargo tank details grid events
 *
 * @export
 * @interface ICargoTankDetailEvent
 * @extends {IDataTableEvent}
 */
export interface ICargoTankDetailEvent extends IDataTableEvent {
    data: ICargoTankDetailValueObject;
    field: string;
    index: number;
    originalEvent: MouseEvent;
}
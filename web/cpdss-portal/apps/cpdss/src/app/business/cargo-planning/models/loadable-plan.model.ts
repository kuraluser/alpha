import { IResponseStatus } from '../../../shared/models/common.model';

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
    loadableQuantityCargoDetails: ILoadableQuantityCargo[],
    loadableQuantityCommingleCargoDetails: ILoadableQuantityCommingleCargo[]
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
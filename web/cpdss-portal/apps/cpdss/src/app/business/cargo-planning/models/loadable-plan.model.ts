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
    subColumns?:ColumHeader[];  
}

/**
 * Interface for table header
*/
interface ColumHeader {
    field: string;
    header: string;
    rowspan?: number;
}

/**
 * Interface for Loadable Quantity api response
 *
 * @export
 * @interface ILoadableQuantityResponse
 */
export interface ILoadablePlanResponse {
    responseStatus: IResponseStatus;
    loadableQuantityCargoDetails: LoadableQuantityCargo[],
    loadableQuantityCommingleCargoDetails: ILoadableQuantityCommingleCargo[]
}


/**
 * Interface for sub column table header
*/
interface ColumHeader {
    field: string;
    header: string;
}

/**
 * Interface for loadable quality 
 *
 * @export
 * @interface LoadableQuantityCargo
 */
export  interface LoadableQuantityCargo {
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
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
    className?: string,
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
    id: number,
    grade: string,
    tankName: string,
    quantity: string,
    api: string,
    temp: string,
    cargo1Abbreviation: string,
    cargo2Abbreviation: string,
    cargo1Percentage: string,
    cargo2Percentage: string,
    cargo1Bblsdbs: string,
    cargo2Bblsdbs: string,
    cargo1Bbls60f: string,
    cargo2Bbls60f: string,
    cargo1LT: string,
    cargo2LT: string,
    cargo1MT: string,
    cargo2MT: string,
    cargo1KL: string,
    cargo2KL: string
}

/**
 * Interface for loadable commingle Cargo
 *
 * @export
 * @interface ICommingleCargoDispaly
 */
export interface ICommingleCargoDispaly {
    id: number,
    grade: string,
    tankName: string,
    quantity: string,
    api: string,
    temp: string,
    cargoPercentage: string,
    cargoBblsdbs: string,
    cargoBbls60f: string,
    cargoLT: string
    cargoMT: string,
    cargoKL: string,
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
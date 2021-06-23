/**
 * Enum for loading and discharging module tab selection
 *
 * @export
 * @enum {number}
 */
export enum OPERATION_TAB {
    INFORMATION,
    INSTRUCTION,
    SEQUENCE,
    PLAN
}

/**
 * Enum for operation mode
 *
 * @export
 * @enum {number}
 */
export enum OPERATION {
  LOADING = 'LOADING',
  DISCHARGING = 'DISCHARGING'
}

/**
 * Interface for Loadable patten cargo details
 *
 * @export
 * @interface ILoadablePatternCargoDetails
 */
 export interface ILoadablePatternCargoDetails {
  priority:number,
  cargoAbbreviation: string,
  cargoColor: string,
  quantity: string,
  isCommingle: boolean,
  loadablePatternCommingleDetailsId:number,
  orderedQuantity: string,
  loadingOrder: number,
  api: string,
  tankName: string,
  tankId: number,
  fillingRatio: string,
  temperature: string
}

/**
* Interface for BL figure total
*
* @export
* @interface IBlFigureTotal
*/
export interface IBlFigureTotal {
  lt: number,
  mt: number,
  api: number,
  kl: number,
  bbl: number;
}

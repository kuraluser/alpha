import { IResponseStatus } from "../../../shared/models/common.model";

declare module 'highcharts' {

  /**
   * Overriding interface for Point
   *
   * @interface Point
   */
  interface Point {
    ullage?: number;
    abbreviation?: number;
    start?: number;
    end?: number;
    quantity?: number;
    rate?: number;
    tankName?: string;
    pumpName?: string;
    sounding?: string;
    highlight: (event: Highcharts.PointerEventObject) => void;
  }

  /**
   * Overriding interface for SVGRenderer
   *
   * @interface SVGRenderer
   */
  interface SVGRenderer {
    getStyle: () => Highcharts.CSSObject;
  }

  /**
   * Overriding interface for Series
   *
   * @interface Series
   */
  interface Series {
    searchPoint(e: PointerEventObject, compareX?: boolean): (Point | undefined);
  }

  /**
   * Overriding interface for Tick
   *
   * @interface Tick
   */
  interface Tick {
    getPosition(horiz: boolean, pos: number, tickmarkOffset: number)
  }

  /**
   * Overriding interface for Axis
   *
   * @interface Axis
   */
  interface Axis {
    tickmarkOffset: number | undefined
  }

  export let hoverChartIndex: number;
}

/**
 * Interface for Commodity data cargo/ ballast
 *
 * @export
 * @interface Commodity
 */
export interface Commodity {
  id: number,
  name: string,
  custom?: Object,
  color?: string,
  abbreviation?: string,
  duration?: string,
  label?: number,
  label1?: number,
  data?: Array<ITankData>,
  stageId?: number
}

/**
 * Interface for Pump data (cargo / ballast)
 *
 * @export
 * @interface Pump
 */
export interface Pump {
  id: number,
  name: string,
  custom?: Object,
  color?: string,
  duration?: string,
  data?: Array<IPumpData>,
  stageId?: number
}

/**
 * Interface for cargo and ballast tanks
 *
 * @export
 * @interface ITankData
 */
export interface ITankData {
  tankId?: number;
  start: number;
  end: number;
  ullage?: number;
  quantityMT: number;
  quantity: number;
  color: string;
  rate?: number;
  id?: string;
  className?: string;
  sounding?: number;
  abbreviation?: string;
  name?: string;
  cargoNominationId?: number;
  tankName?: string;
  api: number;
}

/**
 * Interface for cargo and ballast pumps
 *
 * @export
 * @interface IPumpData
 */
export interface IPumpData {
  pumpId: number;
  start: number;
  end: number;
  ullage?: number;
  quantityM3?: number;
  color?: string;
  rate?: number;
  rateM3PerHr?: number;
  id?: string;
  className?: string;
  pumpName?: string;
}

/**
 * Interface for tank
 *
 * @export
 * @interface ITank
 */
export interface ITank {
  id: number;
  tankName: string;
  quantity?: number;
  quantityMT?: number;
  ullage?: number;
  type?: string;
  time?: number;
  sounding?: number;
}

/**
 * Interface for pump
 *
 * @export
 * @interface IPump
 */
export interface IPump {
  id: number;
  pumpName: string;
  quantity?: number;
  pumpType?: string;
}

/**
 * Interface for loading or discharging sequence data
 *
 * @export
 * @interface ISequenceData
 */
export interface ISequenceData {
  cargos: ITankData[];
  ballasts: ITankData[];
  cargoTankCategories: ITank[];
  ballastTankCategories: ITank[];
  cargoPumpCategories?: IPump[];
  ballastPumpCategories: IPump[];
  minXAxisValue: number;
  maxXAxisValue: number;
  interval: number;
  stagePlotLines?: Highcharts.XAxisPlotLinesOptions[];
  cargoStages?: ICargoStage[];
  cargoStageTickPositions?: number[];
  stageTickPositions?: number[];
  tickPositions?: number[];
  cargoPumps?: IPumpData[];
  ballastPumps: IPumpData[];
  flowRates: Array<IFlowRate>;
  cargoLoadingRates: Array<ILoadingRate>;
  stabilityParams: IStabilityParam[];
  gravity?: IPumpData;
}

/**
 * Interface for cargo data
 *
 * @export
 * @interface ICargoStage
 */
export interface ICargoStage {
  start: number;
  end: number;
  cargos: ITankData[];
}

/**
 * Interface for stability params data
 *
 * @export
 * @interface IStabilityParam
 */
export interface IStabilityParam {
  name: string;
  data: number[][];
}

/**
 * Interface for loading rate data per stage
 *
 * @export
 * @interface ILoadingRate
 */
export interface ILoadingRate {
  startTime: number;
  endTime: number;
  loadingRates: number[];
  loadingRatesM3PerHr: number[];
}

/**
 * Interface for sequence data api response
 *
 * @export
 * @interface ISequenceDataResponse
 */
export interface ISequenceDataResponse extends ISequenceData {
  responseStatus: IResponseStatus;
}

/**
 * Interface for flow rate data
 *
 * @export
 * @interface IFlowRate
 */
export interface IFlowRate {
  tankName: string;
  data: Array<[number, number]>;
  dataM3PerHr: Array<[number, number]>;
}

/**
 * Interface for chart types use in loading
 *
 * @export
 * @enum {number}
 */
export enum SEQUENCE_CHARTS {
  CARGO_TANK = "CARGO_TANK",
  CARGO_PUMP = "CARGO_PUMP",
  BALLAST_TANK = "BALLAST_TANK",
  BALLAST_PUMP = "BALLAST_PUMP",
  FLOW_RATE = "FLOW_RATE",
  STABILITY_PARAMS = "STABILITY_PARAMS",
}

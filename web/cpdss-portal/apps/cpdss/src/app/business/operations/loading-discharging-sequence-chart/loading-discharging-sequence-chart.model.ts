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
  tankId: number;
  start: number;
  end: number;
  ullage?: number;
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
  cargoStages?: any[];
  cargoStageTickPositions?: number[];
  stageTickPositions?: number[];
  tickPositions?: number[];
  cargoPumps?: IPumpData[];
  ballastPumps: IPumpData[];
  flowRates: Array<any>;
  cargoLoadingRates: Array<any>;
  stabilityParams: IStabilityParam[];
  gravity?: IPumpData;
}

/**
 * Interface for stability params data
 *
 * @export
 * @interface IStabilityParam
 */
export interface IStabilityParam {
  name: string;
  data: Number[][];
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
  loadingRates: Number[];
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

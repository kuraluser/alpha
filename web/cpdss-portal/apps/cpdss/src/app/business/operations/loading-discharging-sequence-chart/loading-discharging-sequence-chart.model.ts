declare module 'highcharts' {
  interface Point {
    ullage?: number;
    abbreviation?: number;
    start?: number;
    end?: number;
    quantity?: number;
    rate?: number;
    tankNo?: string;
    pumpNo?: string;
    highlight: (event: Highcharts.PointerEventObject) => void;
  }

  interface SVGRenderer {
    getStyle: () => Highcharts.CSSObject;
  }

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
  data?: Array<any>,
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
  data?: Array<any>,
  stageId?: number
}

import { Component, Input, OnDestroy, OnInit } from '@angular/core';

import * as Highcharts from 'highcharts';
import Theme from 'highcharts/themes/grid-light';
import GanttChart from 'highcharts/modules/gantt';
import Annotations from 'highcharts/modules/annotations';
import { ISequenceData, SEQUENCE_CHARTS } from './loading-discharging-sequence-chart.model';
import { NgxSpinnerService } from 'ngx-spinner';
import { OPERATIONS } from '../../core/models/common.model';
import { LoadingDischargingSequenceApiService } from '../services/loading-discharging-sequence-api.service';
import { LoadingDischargingTransformationService } from '../services/loading-discharging-transformation.service';
import { TranslateService } from '@ngx-translate/core';
import { QUANTITY_UNIT, RATE_UNIT } from '../../../shared/models/common.model';
import { QuantityDecimalFormatPipe } from '../../../shared/pipes/quantity-decimal-format/quantity-decimal-format.pipe';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';

/**
 * Override the reset function, we don't need to hide the tooltips and
 * crosshairs.
 */
// May be needed in future
/* Highcharts.Pointer.prototype.reset = function () {
  return undefined;
}; */

Theme(Highcharts);
GanttChart(Highcharts);
Annotations(Highcharts);

Highcharts.setOptions({
  chart: {
    style: {
      fontFamily: 'Play'
    }
  },
  lang: {
    thousandsSep: ","
  }
});
/**
 * Component class for loading / discharging sequence gantt chart
 *
 * @export
 * @class LoadingDischargingSequenceChartComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-loading-discharging-sequence-chart',
  templateUrl: './loading-discharging-sequence-chart.component.html',
  styleUrls: ['./loading-discharging-sequence-chart.component.scss']
})
export class LoadingDischargingSequenceChartComponent implements OnInit, OnDestroy {

  // Static fields
  static _operation: OPERATIONS;
  static translationKeys: string[];
  static sequenceData: ISequenceData;
  static _currentQuantitySelectedUnit: QUANTITY_UNIT;
  static _currentRateSelectedUnit: RATE_UNIT;
  static _quantityDecimalFormatPipe;
  static _ngxSpinnerService;
  static charts: { [key: string]: Highcharts.Chart } = {};
  static tickPositions: number[];
  static minXAxisValue: number;

  // Input fields
  @Input() vesselId: number;
  @Input() voyageId: number;
  @Input() infoId: number;
  @Input()
  get operation(): OPERATIONS {
    return LoadingDischargingSequenceChartComponent._operation;
  }
  set operation(value: OPERATIONS) {
    LoadingDischargingSequenceChartComponent._operation = value;
  }

  @Input() get currentQuantitySelectedUnit(): QUANTITY_UNIT {
    return LoadingDischargingSequenceChartComponent._currentQuantitySelectedUnit;
  }

  set currentQuantitySelectedUnit(value: QUANTITY_UNIT) {
    LoadingDischargingSequenceChartComponent._currentQuantitySelectedUnit = value;
    this.initializeCharts();
  }

  @Input() get currentRateSelectedUnit(): RATE_UNIT {
    return LoadingDischargingSequenceChartComponent._currentRateSelectedUnit;
  }

  set currentRateSelectedUnit(value: RATE_UNIT) {
    LoadingDischargingSequenceChartComponent._currentRateSelectedUnit = value;
    this.initializeCharts();
  }

  // Public fileds
  readonly OPERATIONS = OPERATIONS;
  readonly SEQUENCE_CHARTS = SEQUENCE_CHARTS;
  Highcharts: typeof Highcharts = Highcharts;
  cargoSequenceGanttChart: Highcharts.Options;
  ballastSequenceGanttChart: Highcharts.Options;
  cargoPumpSequenceGanttChart: Highcharts.Options;
  ballastPumpSequenceGanttChart: Highcharts.Options;
  stabilityGanttChart: Highcharts.Options;
  flowRateAreaChart: Highcharts.Options;
  cargoSequenceChartSeries: Array<Highcharts.SeriesOptionsType>;
  ballastSequenceChartSeries: Array<Highcharts.SeriesOptionsType>;
  cargoPumpSequenceChartSeries: Array<Highcharts.SeriesOptionsType>;
  ballastPumpSequenceChartSeries: Array<Highcharts.SeriesOptionsType>;
  stabilityChartSeries: Array<Highcharts.SeriesOptionsType>;
  flowRateChartSeries: Array<Highcharts.SeriesOptionsType>;
  maxXAxisValue: number;
  maxXAxisScrollValue: number;
  stageInterval: number;
  stagePlotLines: Highcharts.XAxisPlotLinesOptions[];
  stageTickPositions: number[];
  cargoStageTickPositions: number[];
  updateFlowRateChart = false;
  updateCargoTankChart = false;
  updateCargoPumpChart = false;
  updateBallastTankChart = false;
  updateBallastPumpChart = false;
  updateStabilityParamsChart = false;
  oneToOne = true;

  // private fields


  /**
   * Draw a single line in the table
   *
   * @memberof LoadingDischargingSequenceChartComponent
   */
  static tableLine(renderer, x1, y1, x2, y2, group) {
    renderer.path(['M', x1, y1, 'L', x2, y2])
      .attr({
        stroke: '#bebebe',
        'stroke-width': 1
      })
      .addClass('table-border')
      .add();
  }

  /**
   *
   * Get position of ticks
   * @param {Highcharts.Tick} tick
   * @return {*}
   * @memberof LoadingDischargingSequenceChartComponent
   */
  static getPosition(tick: Highcharts.Tick) {
    const axis = tick.axis;
    return Highcharts.Tick.prototype?.getPosition.call(tick, axis.horiz, tick.pos, axis?.tickmarkOffset);
  };

  /**
   * Get the bm/sf text with frame no
   *
   * @static
   * @param {*} name
   * @param {*} index
   * @param {string} text
   * @return {*}  {string}
   * @memberof LoadingDischargingSequenceChartComponent
   */
  static getTextWithFrameNo(name, index, text: string): string {
    let frameNoData;

    switch (name) {
      case LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_SF']:
        frameNoData = LoadingDischargingSequenceChartComponent?.sequenceData?.stabilityParams.find(param => param.name === 'sfFrameNumber');
        break;

      case LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_BM']:
        frameNoData = LoadingDischargingSequenceChartComponent?.sequenceData?.stabilityParams.find(param => param.name === 'bmFrameNumber');
        break;
      case LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_TRIM']:
        const trimValue = LoadingDischargingSequenceChartComponent?.sequenceData?.stabilityParams.find(param => param.name === 'trim');
        if (trimValue) {
          text = trimValue?.data[index][1] >= 0 ? (trimValue?.data[index][1] + ' B/S') : (trimValue?.data[index][1] + ' B/H');
        }
        break;
      default:

        break;
    }
    if (frameNoData) {
      const frameNo = frameNoData?.data[index][1];
      text = frameNo + ' /<br/> ' + text + '%';
    }

    return text
  }

  constructor(private ngxSpinnerService: NgxSpinnerService,
    private loadingDischargingSequenceService: LoadingDischargingSequenceApiService,
    private loadingDischargingTransformationService: LoadingDischargingTransformationService,
    private translateService: TranslateService,
    private quantityDecimalFormatPipe: QuantityDecimalFormatPipe) { }


  async ngOnInit(): Promise<void> {
    this.ngxSpinnerService.show();
    LoadingDischargingSequenceChartComponent._quantityDecimalFormatPipe = this.quantityDecimalFormatPipe;
    LoadingDischargingSequenceChartComponent._ngxSpinnerService = this.ngxSpinnerService;
    const sequenceDataResponse = await this.loadingDischargingSequenceService.getSequenceData(this.vesselId, this.voyageId, this.infoId, this.operation).toPromise();
    if (sequenceDataResponse) {
      LoadingDischargingSequenceChartComponent.sequenceData = this.loadingDischargingTransformationService.transformSequenceData(sequenceDataResponse);
      LoadingDischargingSequenceChartComponent.minXAxisValue = LoadingDischargingSequenceChartComponent.sequenceData?.minXAxisValue;
      this.maxXAxisValue = LoadingDischargingSequenceChartComponent.sequenceData?.maxXAxisValue;
      this.stageInterval = LoadingDischargingSequenceChartComponent.sequenceData?.interval;
      this.stagePlotLines = LoadingDischargingSequenceChartComponent.sequenceData?.stagePlotLines;
      this.cargoStageTickPositions = LoadingDischargingSequenceChartComponent.sequenceData?.cargoStageTickPositions;
      this.stageTickPositions = LoadingDischargingSequenceChartComponent.sequenceData?.stageTickPositions;
      LoadingDischargingSequenceChartComponent.tickPositions = LoadingDischargingSequenceChartComponent.sequenceData?.stageTickPositions;
      this.maxXAxisScrollValue = this.setMaxXAxisScrollValue();
      this.initializeCharts();
    }
    this.ngxSpinnerService.hide();
  }

  ngOnDestroy() {
    delete LoadingDischargingSequenceChartComponent._operation;
    delete LoadingDischargingSequenceChartComponent.translationKeys;
    delete LoadingDischargingSequenceChartComponent.sequenceData;
    delete LoadingDischargingSequenceChartComponent._currentQuantitySelectedUnit;
    delete LoadingDischargingSequenceChartComponent._currentRateSelectedUnit;
    delete LoadingDischargingSequenceChartComponent._quantityDecimalFormatPipe;
    delete LoadingDischargingSequenceChartComponent._ngxSpinnerService;
    delete LoadingDischargingSequenceChartComponent.tickPositions;
    delete LoadingDischargingSequenceChartComponent.minXAxisValue;
  }

  /**
   * Set maximum x axis scroll value
   *
   * @return {*}  {number}
   * @memberof LoadingDischargingSequenceChartComponent
   */
  setMaxXAxisScrollValue(): number {
    const totalDuration = (this.maxXAxisValue - LoadingDischargingSequenceChartComponent.minXAxisValue) / (60 * 60 * 1000);
    const minStageDuration = this.stageTickPositions.reduce((duration, stage, i, stages) => {
      const _duration = (stages[i + 1] - stage) / (60 * 60 * 1000);
      duration = _duration < duration ? _duration : duration;
      return duration;
    }, totalDuration);

    const isStrippingShortDuration = LoadingDischargingSequenceChartComponent.sequenceData.ballasts?.some(tank => tank.id?.includes('stripping') && (tank.end - tank.start) / (60 * 60 * 1000) < 1);
    const isGravityShortDuration = LoadingDischargingSequenceChartComponent.sequenceData.ballastPumps?.some(tank => tank.id?.includes('gravity') && (tank.end - tank.start) / (60 * 60 * 1000) < 1);
    const isDense = this.stageTickPositions.length > totalDuration || (minStageDuration < 1 && totalDuration < 24) || isStrippingShortDuration || isGravityShortDuration;

    let maxXAxisValue = 0;

    switch (true) {
      case totalDuration < 24 && isDense:
        maxXAxisValue = Math.round(totalDuration / 2);
        break;

      case totalDuration < 24 && !isDense:
        maxXAxisValue = Math.round(totalDuration);
        break;

      case totalDuration >= 24 && isDense:
        maxXAxisValue = 12;
        break;

      case totalDuration >= 24 && !isDense:
        maxXAxisValue = 24;
        break;

      default:
        maxXAxisValue = 24;
        break;
    }

    const maxXAxisScrollValue = LoadingDischargingSequenceChartComponent.minXAxisValue + (maxXAxisValue * 60 * 60 * 1000);
    return maxXAxisScrollValue;
  }

  /**
   * Initialize charts
   *
   * @memberof LoadingDischargingSequenceChartComponent
   */
  async initializeCharts() {
    this.ngxSpinnerService.show();
    if (!LoadingDischargingSequenceChartComponent.translationKeys) {
      await this.getTranslationKeys();
    }
    if (LoadingDischargingSequenceChartComponent.sequenceData) {
      if (LoadingDischargingSequenceChartComponent?.charts[SEQUENCE_CHARTS.CARGO_TANK] && LoadingDischargingSequenceChartComponent?.charts[SEQUENCE_CHARTS.CARGO_TANK]?.xAxis) {
        LoadingDischargingSequenceChartComponent?.charts[SEQUENCE_CHARTS.CARGO_TANK]?.xAxis[0]?.setExtremes(LoadingDischargingSequenceChartComponent.minXAxisValue, this.maxXAxisScrollValue);
      }
      LoadingDischargingSequenceChartComponent.sequenceData = this.loadingDischargingTransformationService.transformSequenceDataToSelectedUnit(LoadingDischargingSequenceChartComponent.sequenceData, this.currentQuantitySelectedUnit, this.currentRateSelectedUnit);

      this.setCargoTankSequenceData();
      this.setCargoTankSequenceChartOptions();
      this.updateCargoTankChart = true;

      if (this.operation === OPERATIONS.DISCHARGING) {
        this.setCargoPumpSequenceData();
        this.setCargoPumpSequenceChartOptions();
        this.updateCargoPumpChart = true;
      }

      this.setBallastTankSequenceData();
      this.setBallastTankSequenceChartOptions();
      this.updateBallastTankChart = true;

      this.setBallastPumpSequenceData();
      this.setBallastPumpSequenceChartOptions();
      this.updateBallastPumpChart = true;

      this.setFlowRateData();
      this.setFlowRateChartOptions();
      this.updateFlowRateChart = true;

      this.setStabilityData();
      this.setStabilityChartOptions();
      this.updateStabilityParamsChart = true;

      setTimeout(() => {
        this.ngxSpinnerService.hide();
      }, 100);
    }
  }

  /**
   * Fetch all translation keys used in loading sequence tab
   *
   * @memberof LoadingDischargingSequenceChartComponent
   */
  async getTranslationKeys() {
    LoadingDischargingSequenceChartComponent.translationKeys = await this.translateService.get([
      "LOADING_SEQUENCE_CHART_LABEL",
      "DISCHARGING_SEQUENCE_CHART_LABEL",
      "STRIPPING_BY_EDUCTOR",
      "GRAVITY",
      "SEQUENCE_CHART_HRS",
      "SEQUENCE_CHART_TOTAL",
      "SEQUENCE_CHART_LOADING_RATE",
      "SEQUENCE_CHART_DEBALLASTING_RATE",
      "SEQUENCE_CHART_DRIVE_OIL_TANK",
      "SEQUENCE_CHART_DISCHARGING_RATE",
      "SEQUENCE_CHART_BALLASTING_RATE",
      "SEQUENCE_CHART_FLOW_RATE",
      "SEQUENCE_CHART_AGGEGATE",
      "SEQUENCE_CHART_DRAFT",
      "SEQUENCE_CHART_FORE_DRAFT",
      "SEQUENCE_CHART_AFT_DRAFT",
      "SEQUENCE_CHART_TRIM",
      "SEQUENCE_CHART_UKC",
      "SEQUENCE_CHART_GM",
      "SEQUENCE_CHART_SF",
      "SEQUENCE_CHART_BM",
      "SEQUENCE_CHART_FULL_WASH",
      "SEQUENCE_CHART_TOP_WASH",
      "SEQUENCE_CHART_BOTTOM_WASH",
      "SEQUENCE_CHART_TANK_NO",
      "SEQUENCE_CHART_CARGO",
      "SEQUENCE_CHART_ULLAGE",
      "SEQUENCE_CHART_QUANTITY",
      "SEQUENCE_CHART_CARGO_PUMP",
      "SEQUENCE_CHART_BALLAST",
      "SEQUENCE_CHART_BALLAST_PUMP",
      "SEQUENCE_CHART_COW_ANGLE",
      "SEQUENCE_CHART_TOOLTIP_HOURS",
      "SEQUENCE_CHART_TOOLTIP_STARTING_TIME",
      "SEQUENCE_CHART_TOOLTIP_ENDING_TIME",
      "SEQUENCE_CHART_TOOLTIP_QUANTITY",
      "SEQUENCE_CHART_TOOLTIP_SOUNDING",
      "SEQUENCE_CHART_TOOLTIP_ULLAGE"
    ]).toPromise();
  }

  /**
   * Set cargo tank chart data
   *
   * @memberof LoadingDischargingSequenceChartComponent
   */
  setCargoTankSequenceData() {
    const cargoSequenceSeriesData = [];
    LoadingDischargingSequenceChartComponent.sequenceData?.cargos?.forEach((dataObj: any) => {
      const tankIndex = LoadingDischargingSequenceChartComponent.sequenceData?.cargoTankCategories.findIndex(i => i?.id === dataObj.tankId);
      cargoSequenceSeriesData.push({
        tankId: dataObj?.tankId,
        start: dataObj?.start,
        end: dataObj?.end,
        className: dataObj?.className,
        tankName: LoadingDischargingSequenceChartComponent.sequenceData?.cargoTankCategories[tankIndex].tankName,
        ullage: dataObj?.ullage,
        quantity: dataObj?.quantity,
        id: dataObj?.id,
        color: dataObj?.id?.includes('stripping') ? '#f8f8f8' : dataObj.color,
        abbreviation: dataObj.abbreviation,
        y: tankIndex,
        pointWidth: dataObj?.id?.includes('stripping') ? 0 : 6,
      });
    });
    this.cargoSequenceChartSeries = [{
      type: 'gantt',
      name: `${LoadingDischargingSequenceChartComponent._operation === OPERATIONS.LOADING ? LoadingDischargingSequenceChartComponent.translationKeys['LOADING_SEQUENCE_CHART_LABEL'] : LoadingDischargingSequenceChartComponent.translationKeys['DISCHARGING_SEQUENCE_CHART_LABEL']}`,
      custom: LoadingDischargingSequenceChartComponent.sequenceData?.cargoStages,
      showInLegend: false,
      data: cargoSequenceSeriesData
    }];
  }

  /**
   * Set cargo tank chart options
   *
   * @memberof LoadingDischargingSequenceChartComponent
   */
  setCargoTankSequenceChartOptions() {
    this.cargoSequenceGanttChart = {
      credits: {
        enabled: false
      },
      chart: {
        marginLeft: 280, // Keep all charts left aligned
        spacing: [0, 0, 0, 0],
        events: {
          render: this.sequenceChartRender
        },
        zoomType: 'x',
        marginRight: 20
      },
      legend: {
        enabled: true
      },
      plotOptions: {
        gantt: {
          colorAxis: false,
          pointWidth: 6,
          borderWidth: 0,
          borderRadius: 0,
        },
        series: {
          dataLabels: [
            {
              enabled: true,
              align: 'right',
              verticalAlign: 'bottom',
              color: '#666666',
              formatter: function () {
                return !this.point?.options?.className && !this.point?.options?.id?.includes('stripping') ? this.point?.ullage : undefined;
              }
            },
            {
              enabled: true,
              formatter: function () {
                return this.point?.options?.className ? `<i class="pi ${this.point.options.className} sequence-icon"></i>` : undefined;
              },
              useHTML: true,
              align: 'left',
              padding: 0,
              className: 'cow-labels'
            }]
        }
      },
      scrollbar: {
        enabled: true,
        showFull: false
      },
      xAxis: [
        {
          crosshair: {
            color: 'red',
            snap: false
          },
          lineColor: '#bebebe',
          lineWidth: 1,
          events: {
            setExtremes: this.syncExtremes,
            afterSetExtremes: this.afterSyncExtremes
          },
          labels: {
            rotation: -45,
            align: 'center',
            formatter: function (y) {
              const hours = (1000 * 60 * 60),
                number = (Number(this.value) - LoadingDischargingSequenceChartComponent.minXAxisValue) / (hours);
              return number.toFixed(2);
            }
          },
          grid: {
            enabled: false
          },
          // tickInterval: 1000 * 60 * 60,
          tickPositions: LoadingDischargingSequenceChartComponent.tickPositions,
          minorTickInterval: 1000 * 60 * 60,
          minorGridLineColor: '#bebebe',
          tickLength: 0,
          gridLineWidth: 1,
          gridLineColor: '#bebebe',
          min: LoadingDischargingSequenceChartComponent.minXAxisValue,
          max: this.maxXAxisScrollValue,
          minRange: this.stageInterval * 1000 * 60 * 60,
          // plotLines: this.stagePlotLines // TODO: will be needed in future
        },
        {
          grid: {
            enabled: true,
          },
          lineWidth: 0,
          lineColor: 'transparent',
          gridZIndex: 2,
          gridLineColor: '#000d20',
          gridLineWidth: 1,
          tickPositions: this.cargoStageTickPositions,
          tickColor: '#000d20',
          tickLength: 50,
          margin: 10,
          labels: {
            useHTML: true,
            formatter: function () {
              if (!this.isLast) {
                let cargosLabel = '';
                let categoryLabel;

                const stage = LoadingDischargingSequenceChartComponent.sequenceData?.cargoStages.find((data: any) => data.start <= this.value && data.end > this.value);

                if (stage?.cargos?.length) {
                  stage?.cargos?.forEach(cargo => {
                    cargosLabel += `<p><span class="badge-custom mx-1" style="background-color: ${cargo?.color}">${cargo?.abbreviation}</span> - ${LoadingDischargingSequenceChartComponent._quantityDecimalFormatPipe.transform(cargo?.quantity, LoadingDischargingSequenceChartComponent._currentQuantitySelectedUnit)} ${LoadingDischargingSequenceChartComponent._currentQuantitySelectedUnit}</p>`;
                  });

                  const duration = (stage?.end - stage?.start) / (60 * 60 * 1000);
                  categoryLabel =
                    `<div class=" font-main  text-center pl-5 pr-5">
                    ${cargosLabel}
                      <div class="">(${duration.toFixed(2)} ${LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_HRS']})</div>
                  </div>`;
                }

                return categoryLabel;
              }
            },
          },
        },
        {
          opposite: false,
          grid: {
            enabled: false,
          },
          lineWidth: 0,
          lineColor: 'transparent',
          gridZIndex: 2,
          gridLineColor: '#000d20',
          gridLineWidth: 1,
          tickPositions: this.cargoStageTickPositions,
          tickColor: '#000d20',
          tickLength: 80,
          labels: {
            enabled: false
          },
        },
        {
          opposite: false,
          title: {
            text: `${LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_TOTAL']}: ${LoadingDischargingSequenceChartComponent._quantityDecimalFormatPipe.transform(LoadingDischargingSequenceChartComponent.sequenceData?.cargoTankCategories.reduce((a, b) => a + Number(b.quantity), 0), LoadingDischargingSequenceChartComponent._currentQuantitySelectedUnit)} ${LoadingDischargingSequenceChartComponent._currentQuantitySelectedUnit}`,
          },
          lineColor: '#bebebe',
          lineWidth: 1,
          grid: {
            enabled: true,
            // borderWidth: 0
          },
          tickPositions: this.stageTickPositions,
          tickColor: '#000d20',
          labels: {
            useHTML: true,
            formatter: function () {
              let quantity = 0;
              const stage = LoadingDischargingSequenceChartComponent.sequenceData?.cargoStages.find((data: any) => data.start <= this.value && data.end > this.value);
              let categoryLabel;
              if (stage?.cargos?.length) {
                const equalIndex = this.axis.tickPositions.findIndex(value => value === this.value);
                const nextTick = this.axis.tickPositions[equalIndex + 1];
                const quantityPerTank = [];
                LoadingDischargingSequenceChartComponent.sequenceData?.cargos.forEach((cargo: any) => {
                  if (cargo.start < nextTick && cargo.end <= nextTick) {
                    const index = quantityPerTank.findIndex(tank => tank.tankId === cargo.tankId);
                    if (index === -1 || index === undefined) {
                      quantityPerTank.push({ tankId: cargo.tankId, quantity: cargo.quantity });
                    } else {
                      quantityPerTank[index].quantity = cargo.quantity;
                    }
                  }
                });

                quantityPerTank.forEach(tank => {
                  quantity += Number(tank.quantity);
                });

                categoryLabel =
                  `<div class="font-main  text-center pl-5 pr-5">
                <div class="">${LoadingDischargingSequenceChartComponent._quantityDecimalFormatPipe.transform(quantity, LoadingDischargingSequenceChartComponent._currentQuantitySelectedUnit)} ${LoadingDischargingSequenceChartComponent._currentQuantitySelectedUnit}</div>
                </div>`;
              }

              return categoryLabel;
            },
          },
          offset: '0'
        },
        {
          visible: LoadingDischargingSequenceChartComponent._operation === OPERATIONS.LOADING,
          title: {
            text: `${LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_LOADING_RATE']} ${LoadingDischargingSequenceChartComponent._currentRateSelectedUnit}`,
          },
          grid: {
            enabled: true,
            // borderWidth: 0
          },
          opposite: false,
          lineWidth: 0,
          lineColor: 'transparent',
          tickPositions: this.stageTickPositions,
          tickColor: '#000d20',
          offset: '30',
          labels: {
            useHTML: true,
            formatter: function () {
              if (!this.isLast) {
                const equalIndex = this.axis.tickPositions.findIndex(value => value === this.value);
                const tempRateArray = [];
                const stage = LoadingDischargingSequenceChartComponent.sequenceData?.cargoStages.find((data: any) => data.start <= this.value && data.end > this.value);
                let categoryLabel;
                if (stage?.cargos?.length) {
                  LoadingDischargingSequenceChartComponent.sequenceData?.cargoLoadingRates[equalIndex]?.loadingRates?.map(item => {
                    tempRateArray.push(item?.toFixed());
                  });
                  const rate = tempRateArray.join('/');
                  if (rate) {
                    categoryLabel =
                      `<div class="font-main  text-center pl-5 pr-5">
                          <div class="">${rate} ${LoadingDischargingSequenceChartComponent._currentRateSelectedUnit}</div>
                      </div>`;
                  }
                }
                return categoryLabel;
              }
            },
          }
        },
        {
          visible: LoadingDischargingSequenceChartComponent._operation === OPERATIONS.DISCHARGING,
          title: {
            text: `${LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_DRIVE_OIL_TANK']}`,
          },
          grid: {
            enabled: true,
            // borderWidth: 0
          },
          opposite: false,
          lineWidth: 0,
          lineColor: 'transparent',
          tickPositions: this.stageTickPositions,
          tickColor: '#000d20',
          offset: '30',
          className: 'sequence-drive-oil-tanks',
          labels: {
            useHTML: true,
            align: 'right',
            formatter: function () {
              let categoryLabel;
              if (!this.isLast) {
                const equalIndex = this.axis.tickPositions.findIndex(value => value === this.value);
                const nextTick = this.axis.tickPositions[equalIndex + 1];
                const stage = LoadingDischargingSequenceChartComponent.sequenceData?.driveTanks?.find((data: any) => {
                  return this.value <= data.end && data.end <= nextTick;
                }
                );
                const lastStage = LoadingDischargingSequenceChartComponent.sequenceData?.driveTanks[LoadingDischargingSequenceChartComponent.sequenceData?.driveTanks.length - 1];
                if (stage) {
                  categoryLabel = stage?.tankName;
                } else if (equalIndex + 1 === this.axis.tickPositions?.length - 1 && lastStage.end >= nextTick) {
                  categoryLabel = lastStage?.tankName;
                }

                if (categoryLabel) {
                  categoryLabel =
                    `<div class="font-main text-center pl-5 pr-5">
                          <div class="">${categoryLabel}</div>
                    </div>`;
                }
              }
              return categoryLabel;
            },
          }
        },
      ],
      // TODO: needed in future
      /*  annotations: [{
         labels: [{
           point: 'stripping',
           text: 'Stripping by Eductor222',
           shape: 'rect',
           allowOverlap: true,
           distance: -15,
         }]
       }], */
      yAxis: {
        type: 'category',
        grid: {
          enabled: true,
          borderColor: '#bebebe',
          columns: [
            {
              title: {
                text: `<div class="sequence-chart-tank-column">${LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_TANK_NO']}</div>`,
                useHTML: true,
                y: -73.5,
              },
              categories: LoadingDischargingSequenceChartComponent.sequenceData?.cargoTankCategories.map(function (item) {
                return item.tankName;
              })
            },
            {
              title: {
                text: `<div class="sequence-chart-cargo-column">${LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_CARGO']}</div>`,
                useHTML: true,
                y: -73.5,
              },
              categories: LoadingDischargingSequenceChartComponent.sequenceData?.cargoTankCategories.map(function (item) {
                return item.id.toString();
              }),
              labels: {
                useHTML: true,
                formatter: function () {
                  let cargosLabel = '';
                  const cargos = [];

                  LoadingDischargingSequenceChartComponent.sequenceData?.cargos.forEach((cargo: any) => {
                    if (cargo?.tankId === Number(this.value) && cargo?.cargoNominationId && !cargos.some(item => item?.cargoNominationId === cargo?.cargoNominationId)) {
                      cargos.push({ cargoNominationId: cargo?.cargoNominationId, color: cargo?.color, abbreviation: cargo?.abbreviation });
                    }
                  });
                  cargos?.forEach(cargo => {
                    cargosLabel += `<span class="badge-custom m-1" style="background-color: ${cargo?.color}">${cargo?.abbreviation}</span>`;
                  });

                  const categoryLabel = `<div class="sequence-cargo-group">${cargosLabel}</div>`;

                  return categoryLabel;
                },
              }
            },
            {
              title: {
                text: `<div class="sequence-chart-ullage-column">${LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_ULLAGE']}</div>`,
                useHTML: true,
                y: -73.5
              },
              categories: LoadingDischargingSequenceChartComponent.sequenceData?.cargoTankCategories.map(function (item) {
                return item.ullage.toString();
              })
            },
            {
              width: 200,
              categories: LoadingDischargingSequenceChartComponent.sequenceData?.cargoTankCategories.map(function (item) {
                return LoadingDischargingSequenceChartComponent._quantityDecimalFormatPipe.transform(item.quantity, LoadingDischargingSequenceChartComponent._currentQuantitySelectedUnit);
              }),
              title: {
                text: `<div class="sequence-chart-quantity-column">${LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_QUANTITY']} ${LoadingDischargingSequenceChartComponent._currentQuantitySelectedUnit}</div>`,
                useHTML: true,
                y: -73.5
              }
            }
          ]
        },
        gridLineColor: '#bebebe'
      },
      tooltip: {
        borderColor: '#bebebe',
        borderWidth: 1,
        borderRadius: 20,
        followPointer: true,
        useHTML: true,
        className: 'sequence-chart-tooltip container-fluid',
        formatter: function () {
          let tooltipContent, cargoNames, duration, startingTime, endingTime, quantity, ullage, isCOW = false;
          const min = LoadingDischargingSequenceChartComponent.minXAxisValue;
          startingTime = (this?.point?.start - min) / (1000 * 60 * 60);
          endingTime = (this?.point?.end - min) / (1000 * 60 * 60);
          duration = (this?.point?.end - min) / (1000 * 60 * 60);

          if (this?.point?.options?.id?.includes('cow')) {
            isCOW = true;
            tooltipContent = `
              <table>
                <tr>
                  <th>${LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_COW_ANGLE']}</th>
                  <td>150&deg</td>
                </tr>
                <tr>
                  <th>${LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_TOOLTIP_STARTING_TIME']}</th>
                  <td>${startingTime.toFixed(2)}</td>
                </tr>
                <tr>
                  <th>${LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_TOOLTIP_ENDING_TIME']}</th>
                  <td>${endingTime.toFixed(2)}</td>
                </tr>
              </table>`;
          } else if (this?.point?.options?.id?.includes('stripping')) {
            return false;
          } else {
            cargoNames = this?.point?.abbreviation;
            quantity = this?.point?.quantity;
            ullage = this?.point?.ullage;

            tooltipContent = `
              <table>
                <tr>
                  <th>${cargoNames}</th>
                </tr>
                <tr>
                  <th>${LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_TOOLTIP_HOURS']}</th>
                  <td>${duration.toFixed(2)}</td>
                </tr>
                <tr>
                  <th>${LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_TOOLTIP_STARTING_TIME']}</th>
                  <td>${startingTime.toFixed(2)}</td>
                </tr>
                <tr>
                  <th>${LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_TOOLTIP_ENDING_TIME']}</th>
                  <td>${endingTime.toFixed(2)}</td>
                </tr>
                <tr>
                  <th>${LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_TOOLTIP_QUANTITY']}</th>
                  <td>${LoadingDischargingSequenceChartComponent._quantityDecimalFormatPipe.transform(quantity, LoadingDischargingSequenceChartComponent._currentQuantitySelectedUnit)} ${LoadingDischargingSequenceChartComponent._currentQuantitySelectedUnit}</td>
                </tr>
                <tr>
                  <th>${LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_TOOLTIP_ULLAGE']}</th>
                  <td>${ullage} ${AppConfigurationService.settings.ullageUnit}</td>
                </tr>
              </table>`;

          }
          return tooltipContent;
        },
      },
      series: this.cargoSequenceChartSeries
    };
  }

  /**
   * Set cargo pump chart data
   *
   * @memberof LoadingDischargingSequenceChartComponent
   */
  setCargoPumpSequenceData() {
    const cargoPumpSequenceSeriesData = [];
    LoadingDischargingSequenceChartComponent.sequenceData?.cargoPumps?.forEach((dataObj: any) => {
      const pumpIndex = LoadingDischargingSequenceChartComponent.sequenceData?.cargoPumpCategories?.findIndex(i => i?.id === dataObj.pumpId);
      cargoPumpSequenceSeriesData.push({
        pumpId: dataObj?.pumpId,
        start: dataObj?.start,
        end: dataObj?.end,
        className: dataObj?.className,
        pumpName: LoadingDischargingSequenceChartComponent.sequenceData?.cargoPumpCategories[pumpIndex].pumpName,
        rate: dataObj?.rate?.toFixed(),
        id: dataObj?.id,
        color: dataObj.color,
        y: pumpIndex,
        pointWidth: 6,
        borderColor: null,
        borderWidth: 0,
        borderRadius: 0,
      });
    });
    this.cargoPumpSequenceChartSeries = [{
      type: 'gantt',
      name: `${LoadingDischargingSequenceChartComponent._operation === OPERATIONS.LOADING ? LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_LOADING_RATE'] : LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_DISCHARGING_RATE']}`,
      custom: LoadingDischargingSequenceChartComponent.sequenceData?.cargoStages,
      showInLegend: false,
      data: cargoPumpSequenceSeriesData
    }];
  }
  /**
   * Set cargo pump chart options
   *
   * @memberof LoadingDischargingSequenceChartComponent
   */
  setCargoPumpSequenceChartOptions() {
    if (LoadingDischargingSequenceChartComponent.sequenceData?.cargoPumps) {
      this.cargoPumpSequenceGanttChart = {
        credits: {
          enabled: false
        },
        chart: {
          marginLeft: 280, // Keep all charts left aligned
          spacing: [0, 0, 0, 0],
          events: {
          },
          zoomType: 'x',
          marginRight: 20
        },
        plotOptions: {
          gantt: {
            colorAxis: false,
            pointWidth: 6,
            borderWidth: 0,
            borderRadius: 0,
          },
          series: {
            dataLabels: [
              {
                enabled: true,
                align: 'right',
                verticalAlign: 'bottom',
                formatter: function () {
                  return !this.point?.options?.className ? this.point?.ullage : undefined;
                }
              },
              {
                enabled: true,
                formatter: function () {
                  return this.point?.options?.className ? `<i class="pi ${this.point.options.className} sequence-icon"></i>` : undefined;
                },
                useHTML: true,
                align: 'center'
              }]
          }
        },
        scrollbar: {
          enabled: true,
          showFull: false
        },
        xAxis: [
          {
            lineColor: '#bebebe',
            lineWidth: 1,
            crosshair: {
              color: 'red',
              zIndex: 3,
              snap: false
            },
            events: {
              setExtremes: this.syncExtremes,
              afterSetExtremes: this.afterSyncExtremes
            },
            labels: {
              rotation: -45,
              align: 'center',
              formatter: function (y) {
                const hours = (1000 * 60 * 60),
                  number = (Number(this.value) - LoadingDischargingSequenceChartComponent.minXAxisValue) / (hours);
                return number.toFixed(2);
              }
            },
            grid: {
              enabled: false
            },
            // tickInterval: 1000 * 60 * 60,
            tickPositions: LoadingDischargingSequenceChartComponent.tickPositions,
            minorTickInterval: 1000 * 60 * 60,
            minorGridLineColor: '#bebebe',
            tickLength: 0,
            gridLineWidth: 1,
            gridLineColor: '#bebebe',
            min: LoadingDischargingSequenceChartComponent.minXAxisValue,
            max: this.maxXAxisScrollValue,
            minRange: this.stageInterval * 1000 * 60 * 60,
            // plotLines: this.stagePlotLines // TODO: will be needed in future
          },
          {
            title: {
              text: LoadingDischargingSequenceChartComponent._operation === OPERATIONS.DISCHARGING ? `${LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_DISCHARGING_RATE']} ${LoadingDischargingSequenceChartComponent._currentRateSelectedUnit}` : null,
            },
            grid: {
              enabled: true,
            },
            lineWidth: 0,
            lineColor: 'transparent',
            gridZIndex: 2,
            gridLineColor: '#000d20',
            tickPositions: this.stageTickPositions,
            tickColor: '#000d20',
            tickLength: 100,
            margin: 10,
            labels: {
              enabled: LoadingDischargingSequenceChartComponent._operation === OPERATIONS.DISCHARGING,
              useHTML: true,
              formatter: function () {
                if (!this.isLast) {
                  const equalIndex = this.axis.tickPositions.findIndex(value => value === this.value);
                  const tempRateArray = [];
                  const stage = LoadingDischargingSequenceChartComponent.sequenceData?.cargoStages.find((data: any) => data.start <= this.value && data.end > this.value);
                  let categoryLabel;
                  if (stage?.cargos?.length) {
                    LoadingDischargingSequenceChartComponent.sequenceData?.cargoDischargingRates[equalIndex]?.dischargingRates?.map(item => {
                      tempRateArray.push(item?.toFixed());
                    });
                    const rate = tempRateArray.join('/');
                    if (rate) {
                      categoryLabel =
                        `<div class="font-main  text-center pl-5 pr-5">
                            <div class="">${rate} ${LoadingDischargingSequenceChartComponent._currentRateSelectedUnit}</div>
                        </div>`;
                    }
                  }
                  return categoryLabel;
                }
              },
            }
          },
          {
            opposite: false,
            grid: {
              enabled: true,
            },
            lineWidth: 0,
            lineColor: 'transparent',
            gridZIndex: 2,
            gridLineColor: '#000d20',
            gridLineWidth: 1,
            tickPositions: this.cargoStageTickPositions,
            tickColor: '#000d20',
            margin: -45,
            labels: {
              enabled: false
            },
          },
        ],
        yAxis: {
          type: 'category',
          grid: {
            enabled: true,
            borderColor: '#bebebe',
            columns: [
              {
                title: {
                  text: `<div class="sequence-chart-cargo-pump-column">${LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_CARGO_PUMP']}</div>`,
                  useHTML: true,
                  y: -45,
                },
                categories: LoadingDischargingSequenceChartComponent.sequenceData?.cargoPumpCategories?.map(function (item) {
                  return item.pumpName;
                })
              }]
          },
          gridLineColor: '#bebebe'
        },
        tooltip: {
          // shared: true,
          borderColor: '#bebebe',
          borderWidth: 1,
          borderRadius: 20,
          followPointer: true,
          useHTML: true,
          className: 'sequence-chart-tooltip container-fluid',
          formatter: function () {
            const pumpName = this?.point?.pumpName,
              seriesName = this?.series?.name,
              rate = this?.point?.rate;

            return `
            <table>
              <tr>
                <th>${pumpName}</th>
              </tr>
              <tr>
                <th>${seriesName}</th>
                <td><span>${rate} ${LoadingDischargingSequenceChartComponent._currentRateSelectedUnit}</span></td>
              </tr>
            </table>`;

          },
        },
        series: this.cargoPumpSequenceChartSeries
      };
    }
  }

  /**
   * Set ballast tank chart data
   *
   * @memberof LoadingDischargingSequenceChartComponent
   */
  setBallastTankSequenceData() {
    const ballastSequenceSeriesData = [];
    LoadingDischargingSequenceChartComponent.sequenceData?.ballasts.forEach((dataObj: any) => {
      const tankIndex = LoadingDischargingSequenceChartComponent.sequenceData?.ballastTankCategories.findIndex(i => i?.id === dataObj.tankId);
      ballastSequenceSeriesData.push({
        tankId: dataObj?.tankId,
        start: dataObj?.start,
        end: dataObj?.end,
        className: dataObj?.className,
        tankName: LoadingDischargingSequenceChartComponent.sequenceData?.ballastTankCategories[tankIndex].tankName,
        rate: dataObj?.rate?.toFixed(),
        sounding: dataObj?.sounding,
        quantity: dataObj?.quantity,
        id: dataObj?.id,
        color: dataObj?.id?.includes('stripping') ? '#f8f8f8' : dataObj.color,
        abbreviation: 'Ballast',
        y: tankIndex,
        pointWidth: dataObj?.id?.includes('stripping') ? 0 : 6,
      });
    });
    this.ballastSequenceChartSeries = [{
      type: 'gantt',
      name: `${LoadingDischargingSequenceChartComponent._operation === OPERATIONS.LOADING ? LoadingDischargingSequenceChartComponent.translationKeys['LOADING_SEQUENCE_CHART_LABEL'] : LoadingDischargingSequenceChartComponent.translationKeys['DISCHARGING_SEQUENCE_CHART_LABEL']}`,
      custom: LoadingDischargingSequenceChartComponent.sequenceData?.cargoStages,
      showInLegend: false,
      data: ballastSequenceSeriesData
    }];
  }

  /**
   * Set ballast tank chart options
   *
   * @memberof LoadingDischargingSequenceChartComponent
   */
  setBallastTankSequenceChartOptions() {
    this.ballastSequenceGanttChart = {
      credits: {
        enabled: false
      },
      chart: {
        marginLeft: 280, // Keep all charts left aligned
        spacing: [0, 0, 0, 0],
        events: {
          render: this.ballastSequenceChartRender
        },
        zoomType: 'x',
        marginRight: 20
      },
      plotOptions: {
        gantt: {
          colorAxis: false,
          pointWidth: 6,
          borderWidth: 0,
          borderRadius: 0,
        },
        series: {
          dataLabels: [
            {
              enabled: true,
              align: 'right',
              verticalAlign: 'bottom',
              color: '#666666',
              formatter: function () {
                return !this.point?.options?.id?.includes('stripping') && !this.point?.options?.className ? this.point?.sounding : undefined;
              }
            },
            {
              enabled: true,
              formatter: function () {
                return this.point?.options?.className ? `<i class="pi ${this.point.options.className} sequence-icon"></i>` : undefined;
              },
              useHTML: true,
              align: 'center'
            }
          ]
        }
      },
      scrollbar: {
        enabled: true,
        showFull: false
      },
      xAxis: [
        {
          lineColor: '#bebebe',
          lineWidth: 1,
          crosshair: {
            color: 'red',
            zIndex: 3,
            snap: false
          },
          events: {
            setExtremes: this.syncExtremes,
            afterSetExtremes: this.afterSyncExtremes
          },
          labels: {
            rotation: -45,
            align: 'center',
            formatter: function (y) {
              const hours = (1000 * 60 * 60),
                number = (Number(this.value) - LoadingDischargingSequenceChartComponent.minXAxisValue) / (hours);
              return number.toFixed(2);
            }
          },
          grid: {
            enabled: false
          },
          // tickInterval: 1000 * 60 * 60,
          tickPositions: LoadingDischargingSequenceChartComponent.tickPositions,
          minorTickInterval: 1000 * 60 * 60,
          minorGridLineColor: '#bebebe',
          tickLength: 0,
          gridLineWidth: 1,
          gridLineColor: '#bebebe',
          min: LoadingDischargingSequenceChartComponent.minXAxisValue,
          max: this.maxXAxisScrollValue,
          minRange: this.stageInterval * 1000 * 60 * 60,
          // plotLines: this.stagePlotLines // TODO: will be needed in future
        },
        {
          lineColor: '#bebebe',
          lineWidth: 1,
          opposite: false,
          title: {
            text: `${LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_TOTAL']}`,
          },
          grid: {
            enabled: true,
            // borderWidth: 0
          },
          tickPositions: this.stageTickPositions,
          tickColor: '#000d20',
          labels: {
            useHTML: true,
            formatter: function () {
              const equalIndex = this.axis.tickPositions.findIndex(value => value === this.value);
              const nextTick = this.axis.tickPositions[equalIndex + 1];
              let quantity = 0;
              const quantityPerTank = [];
              const stage = LoadingDischargingSequenceChartComponent.sequenceData?.cargoStages.find((data: any) => data.start <= this.value && data.end > this.value);
              let categoryLabel;
              if (stage?.cargos?.length) {
                LoadingDischargingSequenceChartComponent.sequenceData?.ballasts.forEach((ballast: any) => {
                  if (ballast.start < nextTick && ballast.end <= nextTick) {
                    const index = quantityPerTank.findIndex(tank => tank.tankId === ballast.tankId);
                    if (index === -1 || index === undefined) {
                      quantityPerTank.push({ tankId: ballast.tankId, quantity: ballast.quantity });
                    } else {
                      quantityPerTank[index].quantity = ballast.quantity;
                    }
                  }
                });
                quantityPerTank.forEach(tank => {
                  quantity += Number(tank.quantity);
                });
                categoryLabel =
                  `<div class="">${LoadingDischargingSequenceChartComponent._quantityDecimalFormatPipe.transform(quantity, QUANTITY_UNIT.MT)} ${QUANTITY_UNIT.MT}</div>
                  `;
              }
              return categoryLabel;
            },
          },
        },
        {
          opposite: false,
          grid: {
            enabled: false,
          },
          lineWidth: 0,
          lineColor: 'transparent',
          gridZIndex: 2,
          gridLineColor: '#000d20',
          gridLineWidth: 1,
          tickPositions: this.cargoStageTickPositions,
          tickColor: '#000d20',
          tickLength: 45,
          margin: -45,
          labels: {
            enabled: false
          },
        },
        {
          grid: {
            enabled: true,
          },
          lineWidth: 0,
          lineColor: 'transparent',
          gridZIndex: 2,
          gridLineColor: '#000d20',
          tickPositions: this.cargoStageTickPositions,
          tickColor: '#000d20',
          tickLength: 50,
          margin: 10,
          labels: {
            enabled: false
          }
        },
      ],
      yAxis: {
        type: 'category',
        grid: {
          enabled: true,
          borderColor: '#bebebe',
          columns: [
            {
              title: {
                text: `<div class="sequence-chart-ballast-column">${LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_BALLAST']}</div>`,
                useHTML: true,
                y: -35,
              },
              categories: LoadingDischargingSequenceChartComponent.sequenceData?.ballastTankCategories.map(function (item) {
                return item.tankName;
              })
            }]
        },
        gridLineColor: '#bebebe'
      },
      tooltip: {
        // shared: true,
        borderColor: '#bebebe',
        borderWidth: 1,
        borderRadius: 20,
        followPointer: true,
        useHTML: true,
        className: 'sequence-chart-tooltip container-fluid',
        formatter: function () {
          const min = LoadingDischargingSequenceChartComponent.minXAxisValue, startingTime = (this?.point?.start - min) / (1000 * 60 * 60),
            endingTime = (this?.point?.end - min) / (1000 * 60 * 60),
            duration = (this?.point?.end - min) / (1000 * 60 * 60),
            quantity = this?.point?.quantity,
            sounding = this?.point?.sounding,
            tooltipContent = `
              <table>
                <tr>
                  <th>${LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_TOOLTIP_HOURS']}</th>
                  <td>${duration.toFixed(2)}</td>
                </tr>
                <tr>
                  <th>${LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_TOOLTIP_STARTING_TIME']}</th>
                  <td>${startingTime.toFixed(2)}</td>
                </tr>
                <tr>
                  <th>${LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_TOOLTIP_ENDING_TIME']}</th>
                  <td>${endingTime.toFixed(2)}</td>
                </tr>
                <tr>
                  <th>${LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_TOOLTIP_QUANTITY']}</th>
                  <td>${quantity}${QUANTITY_UNIT.MT}</td>
                </tr>
                <tr>
                  <th>${LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_TOOLTIP_SOUNDING']}</th>
                  <td>${sounding} ${AppConfigurationService?.settings?.ullageUnit}</td>
                </tr>
              </table>`;
          return tooltipContent;

        },
      },
      series: this.ballastSequenceChartSeries
    };
  }

  /**
   * Set ballast pump chart data
   *
   * @memberof LoadingDischargingSequenceChartComponent
   */
  setBallastPumpSequenceData() {
    const ballastPumpSequenceSeriesData = [];
    LoadingDischargingSequenceChartComponent.sequenceData?.ballastPumps?.forEach((dataObj: any) => {
      const pumpIndex = LoadingDischargingSequenceChartComponent.sequenceData?.ballastPumpCategories.findIndex(i => i?.id === dataObj.pumpId);
      ballastPumpSequenceSeriesData.push({
        pumpId: dataObj?.pumpId,
        start: dataObj?.start,
        end: dataObj?.end,
        className: dataObj?.className,
        pumpName: LoadingDischargingSequenceChartComponent.sequenceData?.ballastPumpCategories[pumpIndex]?.pumpName,
        rate: dataObj?.rate?.toFixed(),
        id: dataObj?.id,
        color: dataObj?.id?.includes('gravity') ? '#f8f8f8' : dataObj.color,
        y: pumpIndex,
        pointWidth: dataObj?.id?.includes('gravity') ? 40 : 6,
        borderColor: dataObj?.id?.includes('gravity') ? '#bebebe' : null,
        borderWidth: dataObj?.id?.includes('gravity') ? 1 : 0,
        borderRadius: dataObj?.id?.includes('gravity') ? 5 : 0,
      });
    });

    this.ballastPumpSequenceChartSeries = [{
      type: 'gantt',
      name: `${LoadingDischargingSequenceChartComponent._operation === OPERATIONS.LOADING ? LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_DEBALLASTING_RATE'] : LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_BALLASTING_RATE']}`,
      custom: LoadingDischargingSequenceChartComponent.sequenceData?.cargoStages,
      showInLegend: false,
      data: ballastPumpSequenceSeriesData
    }];
  }

  /**
   * Set ballast pump chart options
   *
   * @memberof LoadingDischargingSequenceChartComponent
   */
  setBallastPumpSequenceChartOptions() {
    if (LoadingDischargingSequenceChartComponent.sequenceData?.ballastPumps) {

      this.ballastPumpSequenceGanttChart = {
        credits: {
          enabled: false
        },
        chart: {
          marginLeft: 280, // Keep all charts left aligned
          spacing: [0, 0, 0, 0],
          events: {
            render: this.ballastPumpSequenceChartRender
          },
          zoomType: 'x',
          marginRight: 20
        },
        plotOptions: {
          gantt: {
            colorAxis: false,
            pointWidth: 6,
            borderWidth: 0,
            borderRadius: 0,
          },
          series: {
            dataLabels: [
              {
                enabled: true,
                align: 'right',
                verticalAlign: 'bottom',
                color: '#666666',
                formatter: function () {
                  return !this.point?.options?.className ? this.point?.sounding : undefined;
                }
              },
              {
                enabled: true,
                formatter: function () {
                  return this.point?.options?.className ? `<i class="pi ${this.point.options.className} sequence-icon"></i>` : undefined;
                },
                useHTML: true,
                align: 'center'
              }]
          }
        },
        scrollbar: {
          enabled: true,
          showFull: false
        },
        xAxis: [
          {
            lineColor: '#bebebe',
            lineWidth: 1,
            crosshair: {
              color: 'red',
              zIndex: 3,
              snap: false
            },
            events: {
              setExtremes: this.syncExtremes,
              afterSetExtremes: this.afterSyncExtremes
            },
            labels: {
              rotation: -45,
              align: 'center',
              formatter: function (y) {
                const hours = (1000 * 60 * 60),
                  number = (Number(this.value) - LoadingDischargingSequenceChartComponent.minXAxisValue) / (hours);
                return number.toFixed(2);
              }
            },
            grid: {
              enabled: false
            },
            // tickInterval: 1000 * 60 * 60,
            tickPositions: LoadingDischargingSequenceChartComponent.tickPositions,
            minorTickInterval: 1000 * 60 * 60,
            minorGridLineColor: '#bebebe',
            tickLength: 0,
            gridLineWidth: 1,
            gridLineColor: '#bebebe',
            min: LoadingDischargingSequenceChartComponent.minXAxisValue,
            max: this.maxXAxisScrollValue,
            minRange: this.stageInterval * 1000 * 60 * 60,
            // plotLines: this.stagePlotLines // TODO: Will be needed in future
          },
          {
            grid: {
              enabled: true,
            },
            lineWidth: 0,
            lineColor: 'transparent',
            gridZIndex: 2,
            gridLineColor: '#000d20',
            tickPositions: this.cargoStageTickPositions,
            tickColor: '#000d20',
            tickLength: 50,
            margin: 10,
            labels: {
              enabled: false
            }
          },
          {
            lineColor: '#bebebe',
            lineWidth: 1,
            opposite: false,
            grid: {
              enabled: true,
              // borderWidth: 0
            },
            tickPositions: this.stageTickPositions,
            tickColor: '#000d20',
            labels: {
              enabled: false
            }
          },
          {
            opposite: false,
            grid: {
              enabled: true,
            },
            lineWidth: 0,
            lineColor: 'transparent',
            gridZIndex: 2,
            gridLineColor: '#000d20',
            gridLineWidth: 1,
            tickPositions: this.cargoStageTickPositions,
            tickColor: '#000d20',
            margin: -45,
            labels: {
              enabled: false
            },
          }
        ],
        yAxis: {
          type: 'category',
          grid: {
            enabled: true,
            borderColor: '#bebebe',
            columns: [
              {
                title: {
                  text: `<div class="sequence-chart-ballast-pump-column">${LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_BALLAST_PUMP']}</div>`,
                  useHTML: true,
                  y: -45,
                },
                categories: LoadingDischargingSequenceChartComponent.sequenceData?.ballastPumpCategories.map(function (item) {
                  return item.pumpName;
                })
              }]
          },
          gridLineColor: '#bebebe'
        },
        tooltip: {
          // shared: true,
          borderColor: '#bebebe',
          borderWidth: 1,
          borderRadius: 20,
          followPointer: true,
          useHTML: true,
          className: 'sequence-chart-tooltip container-fluid',
          formatter: function () {
            const pumpName = this?.point?.pumpName,
              seriesName = this?.series?.name,
              rate = this?.point?.rate;

            return `
            <table>
              <tr>
                <th>${pumpName}</th>
              </tr>
              <tr>
                <th>${seriesName}</th>
                <td><span>${rate} ${LoadingDischargingSequenceChartComponent._currentRateSelectedUnit}</span></td>
              </tr>
            </table>`;

          },
        },
        series: this.ballastPumpSequenceChartSeries
      };
    }
  }

  /**
   * Set flow rate chart data
   *
   * @memberof LoadingDischargingSequenceChartComponent
   */
  setFlowRateData() {
    const xAxisTicks = [], aggregateData = [];
    let sum;

    LoadingDischargingSequenceChartComponent.sequenceData?.flowRates.forEach(function (tankDetails, z) {
      tankDetails.data.forEach(function (data, i) {
        if (!xAxisTicks.some(value => value === data[0])) {
          xAxisTicks.push(data[0]);
        }
      });
    });
    xAxisTicks.sort();
    for (let i = 0; i < xAxisTicks.length; i++) {
      sum = 0;
      LoadingDischargingSequenceChartComponent.sequenceData?.flowRates.forEach(function (tankDetails, k) {
        tankDetails.data.forEach(function (ob, j) {
          if (ob[0] === xAxisTicks[i]) {
            sum += ob[1];
          }
        });
      });
      aggregateData?.push([xAxisTicks[i], parseFloat((sum).toFixed(2))]);
    }
    this.flowRateChartSeries = [
      {
        name: LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_AGGEGATE'],
        type: 'areaspline',
        custom: LoadingDischargingSequenceChartComponent.sequenceData?.cargoStages,
        data: aggregateData,
        // step: 'left'
      },
      ...LoadingDischargingSequenceChartComponent.sequenceData?.flowRates?.map(item => {
        const series: Highcharts.SeriesOptionsType = {
          name: item.tankName,
          type: 'line',
          custom: LoadingDischargingSequenceChartComponent.sequenceData?.cargoStages,
          data: item.data,
          step: 'left'
        }
        return series;
      })];
  }

  /**
   * Set flow rate chart options
   *
   * @memberof LoadingDischargingSequenceChartComponent
   */
  setFlowRateChartOptions() {
    this.flowRateAreaChart = {
      credits: {
        enabled: false
      },
      chart: {
        marginLeft: 280, // Keep all charts left aligned
        spacing: [0, 0, 0, 0],
        events: {
        },
        zoomType: 'x',
        marginRight: 20
      },
      legend: {
        backgroundColor: 'transparent',
        itemMarginBottom: 20,
        x: 100
      },
      title: {
        text: null
      },
      plotOptions: {
        series: {
          events: {
            legendItemClick: function () {
              return false;
            }
          }
        },
        areaspline: {
          fillColor: {
            linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
            stops: [
              [0, Highcharts.getOptions().colors[0]],
              [1, Highcharts.color(Highcharts.getOptions()?.colors[0])?.setOpacity(0)?.get('rgba').toString()]
            ]
          },
          marker: {
            enabled: false
          }
        },
        line: {
          marker: {
            enabled: false
          }
        }
      },
      scrollbar: {
        enabled: true,
        showFull: false
      },
      xAxis: [
        {
          lineColor: '#bebebe',
          lineWidth: 1,
          // startOnTick: true,
          crosshair: {
            color: 'red',
            snap: false
          },
          opposite: true,
          type: 'datetime',
          events: {
            setExtremes: this.syncExtremes,
            afterSetExtremes: this.afterSyncExtremes
          },
          labels: {
            rotation: -45,
            align: 'center',
            formatter: function (y) {
              const hours = (1000 * 60 * 60),
                number = (Number(this.value) - LoadingDischargingSequenceChartComponent.minXAxisValue) / (hours);
              return number.toFixed(2);
            }
          },
          // tickInterval: 1000 * 60 * 60,
          tickPositions: LoadingDischargingSequenceChartComponent.tickPositions,
          minorTickInterval: 1000 * 60 * 60,
          minorGridLineColor: '#bebebe',
          tickLength: 0,
          // tickColor: '#bebebe',
          gridLineColor: '#bebebe',
          min: LoadingDischargingSequenceChartComponent.minXAxisValue,
          max: this.maxXAxisScrollValue,
          minRange: this.stageInterval * 1000 * 60 * 60,
          // plotLines: this.stagePlotLines // TODO: Will be needed in future
        },
        {
          lineWidth: 0,
          // startOnTick: true,
          opposite: true,
          tickPositions: this.cargoStageTickPositions,
          tickColor: '#000d20',
          tickLength: 20,
          margin: 10,
          gridLineColor: '#000d20',
          min: LoadingDischargingSequenceChartComponent.minXAxisValue,
          max: this.maxXAxisScrollValue,
          minRange: this.stageInterval * 1000 * 60 * 60,
          labels: {
            enabled: false
          }
        },
      ],
      yAxis: [{
        type: 'logarithmic',
        gridLineColor: '#bebebe',
        minorGridLineWidth: 0,
        // offset: 0,
        title: {
          align: 'high',
          text: `${LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_FLOW_RATE']} (${LoadingDischargingSequenceChartComponent._currentRateSelectedUnit})`,
          rotation: 0,
          y: -15
        }
      }],
      tooltip: {
        shared: true,
        borderColor: '#bebebe',
        borderWidth: 1,
        borderRadius: 20,
        valueSuffix: ` ${LoadingDischargingSequenceChartComponent._currentRateSelectedUnit}`
      },
      series: this.flowRateChartSeries
    };
  }

  /**
   * Set stabilityparameters chart data
   *
   * @memberof LoadingDischargingSequenceChartComponent
   */
  setStabilityData() {
    this.stabilityChartSeries = [...LoadingDischargingSequenceChartComponent.sequenceData?.stabilityParams.map(item => {
      let series;
      switch (item?.name) {
        case 'fore_draft':
          series = {
            yAxis: 0,
            type: 'areaspline',
            name: LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_FORE_DRAFT'],
            index: 0,
            data: item?.data,
          };
          break;

        case 'aft_draft':
          series = {
            yAxis: 0,
            type: 'areaspline',
            name: LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_AFT_DRAFT'],
            index: 1,
            data: item?.data,
          };
          break;

        case 'trim':
          series = {
            yAxis: 0,
            type: 'areaspline',
            name: LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_TRIM'],
            index: 2,
            data: item?.data,
          };
          break;

        case 'ukc':
          series = {
            yAxis: 0,
            type: 'areaspline',
            name: LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_UKC'],
            index: 3,
            data: item?.data,
          };
          break;

        case 'gm':
          series = {
            yAxis: 0,
            type: 'areaspline',
            name: LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_GM'],
            index: 4,
            data: item?.data,
          };
          break;

        case 'bm':
          series = {
            yAxis: 0,
            type: 'areaspline',
            name: LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_BM'],
            index: 5,
            data: item?.data,
          };
          break;

        case 'sf':
          series = {
            yAxis: 0,
            type: 'areaspline',
            name: LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_SF'],
            index: 6,
            data: item?.data,
          };
          break;

        default:
          break;
      }

      return series;
    }).filter(param => param).sort((a, b) => a.index - b.index)];
  }

  /**
   * Set stabilityparameters chart options
   *
   * @memberof LoadingDischargingSequenceChartComponent
   */
  setStabilityChartOptions() {
    this.stabilityGanttChart = {
      credits: {
        enabled: false
      },
      chart: {
        marginLeft: 280, // Keep all charts left aligned
        spacing: [0, 0, 0, 0],
        events: {
          render: this.drawTable
        },
        zoomType: 'x',
        marginRight: 20
      },
      title: {
        text: null,
      },
      legend: {
        enabled: false
      },
      plotOptions: {
        series: {
          color: 'transparent',
          marker: {
            enabled: false,
            states: {
              hover: {
                enabled: false
              }
            }
          }
        },
      },
      scrollbar: {
        enabled: true,
        showFull: false
      },
      xAxis: [
        {
          events: {
            setExtremes: this.syncExtremes,
            afterSetExtremes: this.afterSyncExtremes
          },
          title: {
            align: 'low',
            offset: 0,
            rotation: 0,
            x: -275,
            y: 15,
            text: LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_DRAFT'],
          },
          // startOnTick: true,
          opposite: true,
          type: 'datetime',
          lineWidth: 0,
          labels: {
            rotation: -45,
            align: 'center',
            y: 15,
            formatter: function (y) {
              const hours = (1000 * 60 * 60),
                number = (Number(this.value) - LoadingDischargingSequenceChartComponent.minXAxisValue) / (hours);
              return number.toFixed(2);
            }
          },
          tickPositions: this.stageTickPositions,
          tickLength: 20,
          tickColor: '#bebebe',
          gridLineWidth: 0,
          top: 24,
          min: LoadingDischargingSequenceChartComponent.minXAxisValue,
          max: this.maxXAxisScrollValue,
          minRange: this.stageInterval * 1000 * 60 * 60,
        },
        {
          lineWidth: 0,
          // startOnTick: true,
          opposite: true,
          top: 50,
          tickPositions: this.cargoStageTickPositions,
          tickColor: '#000d20',
          tickPosition: 'inside',
          tickLength: 20,
          gridLineWidth: 0,
          min: LoadingDischargingSequenceChartComponent.minXAxisValue,
          max: this.maxXAxisScrollValue,
          minRange: this.stageInterval * 1000 * 60 * 60,
          labels: {
            enabled: false
          }
        }
      ],
      yAxis: [{
        id: 'yaxis-0',
        gridLineWidth: 0,
        labels: {
          enabled: false
        },
        title: {
          text: null
        },
        minorGridLineWidth: 0
      }],
      tooltip: {
        shared: true,
        borderColor: '#bebebe',
        borderWidth: 1,
        borderRadius: 20,
        followPointer: true,
        enabled: true,
        xDateFormat: '%A, %b %e, %Y %H:%M',
        pointFormatter: function () {
          let text = this.y.toString();
          text = LoadingDischargingSequenceChartComponent.getTextWithFrameNo(this.series.name, this.index, text);

          return `${this.series.name}: <b>${text}</b><br/>`;
        }
      },
      series: this.stabilityChartSeries
    };
  }

  /**
   * Gantt chart call back method
   *
   * @param {Highcharts.Chart} chart
   * @memberof LoadingDischargingSequenceChartComponent
   */
  cargoSequenceGanttChartCallback = (chart: Highcharts.Chart) => {
  }

  /**
   * In order to synchronize tooltips and crosshairs, override the built-in events with handlers defined on the parent element.
   *
   * @param {*} e
   * @param {SEQUENCE_CHARTS} type
   * @memberof LoadingDischargingSequenceChartComponent
   */
  synchronizeTooltips(e, type: SEQUENCE_CHARTS) {
    const currentChart: Highcharts.Chart = LoadingDischargingSequenceChartComponent.charts[type];
    let chart: Highcharts.Chart,
      points: Array<Highcharts.Point>,
      i;
    for (i = 0; i < Highcharts.charts?.length; i++) {
      chart = Highcharts.charts[i];
      if (chart?.index !== currentChart?.index) {
        points = [];
        if (chart) {
          e = chart?.pointer?.normalize(e); // Find coordinates within the chart
          chart?.xAxis[0].drawCrosshair(e);
          for (let index = 0; index < chart?.series.length; index++) {
            const series = chart?.series[index];
            const point = this.searchPoint(e, chart, series);
            if (point) {
              points.push(point);
              if (![LoadingDischargingSequenceChartComponent.charts[SEQUENCE_CHARTS.FLOW_RATE]?.index, LoadingDischargingSequenceChartComponent.charts[SEQUENCE_CHARTS.STABILITY_PARAMS]?.index].includes(chart.index)) {
                break;
              }
            }
          }
        }

        if (points.length) {
          let number = 0;
          points.forEach((p, index: number) => {
            if (!p?.series?.visible && chart.index !== LoadingDischargingSequenceChartComponent.charts[SEQUENCE_CHARTS.STABILITY_PARAMS].index) {
              points.splice(index - number, 1);
              number++;
            }
          });
          chart?.tooltip?.refresh(points); // Show the tooltip
        } else {
          chart?.tooltip?.hide();
        }
      }
    }
  };

  /**
   * Method for searching points on mouseover
   *
   * @param {*} event
   * @param {Highcharts.Chart} chart
   * @param {*} series
   * @return {*}
   * @memberof LoadingDischargingSequenceChartComponent
   */
  searchPoint(event, chart: Highcharts.Chart, series: Highcharts.Series) {
    const points = series?.points.sort((pointA, pointB) => pointA.y - pointB.y),
      len = points?.length,
      x = chart.xAxis[0].toValue(event.chartX),
      range = 1000 * 60; // Show sychronized tooltip in the range +/- 1 minute
    let pointX, pointX2,
      i;

    for (i = 0; i < len; i++) {
      pointX = points[i].x;
      pointX2 = points[i]?.x2;
      if (x - range < pointX && pointX < x + range) {
        return points[i];
      } else if (x - range < pointX2 && pointX2 < x + range) {
        return points[i];
      } else if (x > pointX && x < pointX2) {
        return points[i];
      }
    }
  }

  /**
   * Method for drawing stability grid
   *
   * @memberof LoadingDischargingSequenceChartComponent
   */
  drawTable = function (event) {
    LoadingDischargingSequenceChartComponent._ngxSpinnerService.show();
    const chart: Highcharts.Chart = this,
      series = chart.series,
      renderer = chart.renderer,
      fontSize = Number(Highcharts.SVGRenderer.prototype.getStyle().fontSize.replace('px', '')),
      visibleTickPositions = chart.xAxis[0].tickPositions;

    const tableTop = 0,
      tableLeft = 0,
      rowHeight = 45,
      cellPadding = 2.5,
      tablePadding = 20;

    let cellWidth = 0;
    if (chart.group) {
      chart.group.destroy();
      chart.group = null;
    }
    chart.group = renderer.g('stability-params-group').add(chart.group);

    // Render series labels
    series.forEach((serie, serie_index) => {
      renderer.text(
        serie.name, tableLeft + cellPadding, tableTop + (serie_index + 2) * rowHeight - cellPadding).css({
          color: '#666666'
        })
        .addClass('table-header-text')
        .add(chart.group);
    });

    // Render data points
    LoadingDischargingSequenceChartComponent.tickPositions.forEach((category, category_index) => {
      const currTick = chart.xAxis[0].ticks[category];
      const dataLabelFixedWidth = 36;
      let distanceLeft, distanceRight;

      if (currTick && !currTick?.isLast) {
        const nextTick = chart.xAxis[0].ticks[LoadingDischargingSequenceChartComponent.tickPositions[category_index + 1]];
        const currTickPosition = LoadingDischargingSequenceChartComponent.getPosition.call(chart, currTick);
        const nextTickPosition = LoadingDischargingSequenceChartComponent.getPosition.call(chart, nextTick);
        distanceRight = nextTickPosition?.x - currTickPosition?.x;
      }

      if (currTick && !currTick?.isFirst) {
        const prevTick = chart.xAxis[0].ticks[LoadingDischargingSequenceChartComponent.tickPositions[category_index - 1]];
        const currTickPosition = LoadingDischargingSequenceChartComponent.getPosition.call(chart, currTick);
        const prevTickPosition = LoadingDischargingSequenceChartComponent.getPosition.call(chart, prevTick);
        distanceLeft = currTickPosition?.x - prevTickPosition?.x;
      }

      series.forEach((item, i) => {
        if (series[i].data[category_index]) {
          cellWidth = series[i].data[category_index].plotX;
          let text = series[i].data[category_index]?.y.toString();
          text = LoadingDischargingSequenceChartComponent.getTextWithFrameNo(series[i].name, category_index, text);

          const x = chart.plotLeft + series[i].data[category_index].plotX;
          const y = tableTop + (i + 2) * rowHeight - cellPadding;
          const dataLabel = `${text}`;
          const attr = {
            align: 'center',
          };
          switch (series[i].name) {
            case LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_SF']:

            case LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_BM']:
              attr['translateY'] = -7.5;
              break;
            default:

              break;
          }
          if (distanceLeft < 36 && distanceRight < 36) {
            attr['textLength'] = dataLabelFixedWidth / 2;
          } else if (distanceLeft < 36 && (!distanceRight || distanceRight > 36)) {
            attr['align'] = 'left';
          } else if ((!distanceLeft || distanceLeft > 36) && distanceRight < 36) {
            attr['align'] = 'right';
          } else if (distanceLeft > 36 && !distanceRight) {
            attr['align'] = 'right';
          }
          if (visibleTickPositions?.some(tickPosition => category === tickPosition)) {
            renderer.text(dataLabel, x, y, false).attr(attr).css({
              color: '#666666'
            })
              .addClass(`table-cell-text${dataLabelFixedWidth !== 36 ? ' table-cell-text-overflow' : ''}`)
              .add(chart.group);
          }

          // horizontal lines
          if (i === 0) {
            LoadingDischargingSequenceChartComponent.tableLine( // top
              renderer,
              tableLeft,
              tableTop + cellPadding,
              chart.plotLeft + chart.plotWidth,
              tableTop + cellPadding,
              chart.group
            );
            LoadingDischargingSequenceChartComponent.tableLine( // bottom
              renderer,
              tableLeft,
              tableTop + (series?.length + 2) * rowHeight - cellPadding - ((rowHeight + fontSize) / 2 - 2),
              chart.plotLeft + chart.plotWidth,
              tableTop + (series?.length + 2) * rowHeight - cellPadding - ((rowHeight + fontSize) / 2 - 2),
              chart.group
            );
          }
          // horizontal line
          LoadingDischargingSequenceChartComponent.tableLine(
            renderer,
            tableLeft,
            tableTop + (i + 2) * rowHeight - cellPadding - ((rowHeight + fontSize) / 2 - 2),
            chart.plotLeft + chart.plotWidth,
            tableTop + (i + 2) * rowHeight - cellPadding - ((rowHeight + fontSize) / 2 - 2),
            chart.group
          );
        }
      });

      // TODO:vertical lines
      /* if (category_index === 0) { // left table border
        LoadingDischargingSequenceChartComponent.tableLine(
          renderer,
          tableLeft,
          tableTop + cellPadding,
          tableLeft,
          tableTop + (LoadingDischargingSequenceChartComponent.tickPositions.length + 1) * rowHeight + cellPadding,
          chart.group
        );
        LoadingDischargingSequenceChartComponent.tableLine(
          renderer,
          chart.plotLeft - tablePadding,
          tableTop + cellPadding,
          chart.plotLeft - tablePadding,
          tableTop + (LoadingDischargingSequenceChartComponent.tickPositions.length + 1) * rowHeight + cellPadding,
          chart.group
        );
      }

      if (category_index === LoadingDischargingSequenceChartComponent.tickPositions.length - 1) { // right table border
        LoadingDischargingSequenceChartComponent.tableLine(
          renderer,
          chart.plotLeft + cellWidth + tablePadding,
          tableTop + cellPadding,
          chart.plotLeft + cellWidth + tablePadding,
          tableTop + (LoadingDischargingSequenceChartComponent.tickPositions.length + 1) * rowHeight + cellPadding,
          chart.group
        );
      } */
    });

    LoadingDischargingSequenceChartComponent._ngxSpinnerService.hide();
  }

  /**
   * Synchronize zooming through the setExtremes event handler
   *
   * @memberof LoadingDischargingSequenceChartComponent
   */
  syncExtremes = function (event) {
    LoadingDischargingSequenceChartComponent._ngxSpinnerService.show();
    const thisChart: Highcharts.Chart = this.chart;

    if (event.trigger !== 'syncExtremes') { // Prevent feedback loop
      Highcharts.charts?.forEach((chart) => {
        if (chart !== thisChart) {
          for (let index = 0; index < chart?.xAxis?.length; index++) {
            if (chart.xAxis[index].setExtremes) { // It is null while updating
              chart.xAxis[index].update({
                labels: {
                  style: {
                    'white-space': 'nowrap'
                  }
                }
              }, false);
              LoadingDischargingSequenceChartComponent._ngxSpinnerService.show();
              chart.xAxis[index].setExtremes(
                event.min,
                event.max,
                true,
                false,
                { trigger: 'syncExtremes' }
              );
            }
          }
        } else {
          for (let index = 1; index <= chart?.xAxis?.length - 1; index++) {
            if (chart.xAxis[index].setExtremes) { // It is null while updating
              chart.xAxis[index].update({
                labels: {
                  style: {
                    'white-space': 'nowrap'
                  }
                }
              }, false);
              LoadingDischargingSequenceChartComponent._ngxSpinnerService.show();
              chart.xAxis[index].setExtremes(
                event.min,
                event.max,
                true,
                false,
                { trigger: 'syncExtremes' }
              );
            }
          }
        }
      });
    } else {
      LoadingDischargingSequenceChartComponent._ngxSpinnerService.hide();
    }
  }

  /**
   * After setExtreme event handler
   *
   * @memberof LoadingDischargingSequenceChartComponent
   */
  afterSyncExtremes = function (event) {
    const chart: Highcharts.Chart = this.chart;
    if (event?.trigger === 'zoom') {
      chart.xAxis.forEach(xAxis => {
        xAxis.update({
          labels: {
            style: {
              'white-space': 'nowrap'
            }
          }
        }, false);
      });
      chart.redraw();
    }
  }

  /**
   * Sequence chart render handler
   *
   * @memberof LoadingDischargingSequenceChartComponent
   */
  sequenceChartRender = function () {
    const chart: Highcharts.Chart = this,
      renderer = chart.renderer;

    // Show COW legends only in discharging operation
    if (LoadingDischargingSequenceChartComponent._operation === OPERATIONS.DISCHARGING) {
      const cowLegend = `<ul class="list-group list-group-horizontal cow-legend">
                          <li class="list-group-item">
                            <i class=" cow-legend-icon full-wash"></i>
                            <span class="cow-legend-label">${LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_FULL_WASH']}</span>
                          </li>
                          <li class="list-group-item">
                            <i class="cow-legend-icon top-wash"></i>
                            <span class="cow-legend-label">${LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_TOP_WASH']}</span>
                          </li>
                          <li class="list-group-item">
                            <i class=" cow-legend-icon bottom-wash"></i>
                            <span class="cow-legend-label">${LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_BOTTOM_WASH']}</span> </li>
                        </ul>`;
      if (chart?.cargoCOWLegend) {
        chart?.cargoCOWLegend.destroy();
        chart.cargoCOWLegend = null;
      }
      chart.cargoCOWLegend = renderer.text(cowLegend, chart.plotLeft, chart.chartHeight - 20, true).attr({
        cursor: 'pointer',
        zIndex: 1
      }).add();
    }

    if (chart?.cargoStrippingGroup) {
      chart?.cargoStrippingGroup.destroy();
      chart.cargoStrippingGroup = null;
    }
    chart.cargoStrippingGroup = renderer.g('cargo-stripping-group')
      .attr({
        zIndex: 7
      })
      .add(chart.cargoStrippingGroup);

    chart.series[0].data.forEach((point) => {
      if (point?.options?.id?.includes('stripping')) {
        const rectX = point.plotX < 0 ? chart.plotLeft : point.plotX + chart.plotLeft;
        const rectWidth = point.plotX < 0 ? point?.shapeArgs?.width - 10 : point?.shapeArgs?.width;
        const rectHeight = 40;
        const rect = renderer.rect(rectX, point.plotY + chart.plotTop - (rectHeight / 2), rectWidth, rectHeight, 0)
          .attr({
            fill: '#f8f8f8',
            stroke: '#bebebe',
            'stroke-width': 1,
            zIndex: 7
          })
          .add(chart.cargoStrippingGroup);

        const titleRect = renderer.createElement('title');
        titleRect.element.append(LoadingDischargingSequenceChartComponent.translationKeys['STRIPPING_BY_EDUCTOR']);
        titleRect.add(rect);

        const box = rect.getBBox();

        const text = renderer.text(
          LoadingDischargingSequenceChartComponent.translationKeys['STRIPPING_BY_EDUCTOR'],
          box.x,
          box.y
        )
          .css({
            width: rectWidth,
            height: 40,
            textOverflow: 'ellipsis',
            color: '#666666'
          })
          .attr({
            zIndex: 7,
            translateY: 25,
            align: 'center',
            translateX: rectWidth / 2,
          })
          .addClass('sequence-stripping cargo')
          .add(chart.cargoStrippingGroup);

        if (rectWidth < 19) {
          text.element.append(LoadingDischargingSequenceChartComponent.translationKeys['STRIPPING_BY_EDUCTOR'].slice(0, 1));
          const titleText = renderer.createElement('title');
          titleText.element.append(LoadingDischargingSequenceChartComponent.translationKeys['STRIPPING_BY_EDUCTOR']);
          titleText.add(text);
        }
      }
    });
  }

  /**
   * Ballast tank Sequence chart render handler
   *
   * @memberof LoadingDischargingSequenceChartComponent
   */
  ballastSequenceChartRender = function () {
    const chart: Highcharts.Chart = this,
      renderer = chart.renderer;

    if (chart?.ballastStrippingGroup) {
      chart?.ballastStrippingGroup.destroy();
      chart.ballastStrippingGroup = null;
    }
    chart.ballastStrippingGroup = renderer.g('ballast-stripping-group')
      .attr({
        zIndex: 7
      })
      .add(chart.ballastStrippingGroup);

    chart.series[0].data.forEach((point) => {
      if (point?.options?.id?.includes('stripping')) {
        const rectX = point.plotX < 0 ? chart.plotLeft : point.plotX + chart.plotLeft;
        const rectWidth = point.plotX < 0 ? point?.shapeArgs?.width - 10 : point?.shapeArgs?.width;
        const rectHeight = 40;
        const rect = renderer.rect(rectX, point.plotY + chart.plotTop - (rectHeight / 2), rectWidth, rectHeight, 0)
          .attr({
            fill: '#f8f8f8',
            stroke: '#bebebe',
            'stroke-width': 1,
            zIndex: 7
          })
          .add(chart.ballastStrippingGroup);

        const titleRect = renderer.createElement('title');
        titleRect.element.append(LoadingDischargingSequenceChartComponent.translationKeys['STRIPPING_BY_EDUCTOR']);
        titleRect.add(rect);

        const box = rect.getBBox();

        const text = renderer.text(
          LoadingDischargingSequenceChartComponent.translationKeys['STRIPPING_BY_EDUCTOR'],
          box.x,
          box.y
        )
          .css({
            width: rectWidth,
            height: 40,
            textOverflow: 'ellipsis',
            color: '#666666'
          })
          .attr({
            zIndex: 7,
            translateY: 25,
            align: 'center',
            translateX: rectWidth / 2,
          })
          .addClass('sequence-stripping ballast')
          .add(chart.ballastStrippingGroup);

        if (rectWidth < 19) {
          text.element.append(LoadingDischargingSequenceChartComponent.translationKeys['STRIPPING_BY_EDUCTOR'].slice(0, 1));
          const titleText = renderer.createElement('title');
          titleText.element.append(LoadingDischargingSequenceChartComponent.translationKeys['STRIPPING_BY_EDUCTOR']);
          titleText.add(text);
        }
      }
    });
  }

  /**
   * Ballast pump Sequence chart render handler
   *
   * @memberof LoadingDischargingSequenceChartComponent
   */
  ballastPumpSequenceChartRender = function () {
    const chart: Highcharts.Chart = this,
      renderer = chart.renderer;

    if (chart?.ballastGravityGroup) {
      chart?.ballastGravityGroup.destroy();
      chart.ballastGravityGroup = null;
    }
    chart.ballastGravityGroup = renderer.g('ballast-gravity-group')
      .attr({
        zIndex: 7
      })
      .add(chart.ballastGravityGroup);

    chart.series[0].data.forEach((point) => {
      if (point?.options?.id?.includes('gravity')) {
        const rectX = point.plotX < 0 ? chart.plotLeft : point.plotX + chart.plotLeft;
        const rectWidth = point.plotX < 0 ? point?.shapeArgs?.width - 10 : point?.shapeArgs?.width;
        const rectHeight = 40;
        const rect = renderer.rect(rectX, point.plotY + chart.plotTop - (rectHeight / 2), rectWidth, rectHeight, 0)
          .attr({
            fill: '#f8f8f8',
            stroke: '#bebebe',
            'stroke-width': 1,
            zIndex: 7
          })
          .add(chart.ballastGravityGroup);

        const titleRect = renderer.createElement('title');
        titleRect.element.append(LoadingDischargingSequenceChartComponent.translationKeys['GRAVITY']);
        titleRect.add(rect);

        const box = rect.getBBox();

        const text = renderer.text(
          LoadingDischargingSequenceChartComponent.translationKeys['GRAVITY'],
          box.x,
          box.y
        )
          .css({
            width: rectWidth,
            textOverflow: 'ellipsis',
            color: '#666666'
          })
          .attr({
            zIndex: 7,
            translateY: 25,
            align: 'center',
            translateX: rectWidth / 2,
          })
          .addClass('sequence-gravity ballast')
          .add(chart.ballastGravityGroup);

        if (rectWidth < 19) {
          text.element.append(LoadingDischargingSequenceChartComponent.translationKeys['GRAVITY'].slice(0, 1));
          const titleText = renderer.createElement('title');
          titleText.element.append(LoadingDischargingSequenceChartComponent.translationKeys['GRAVITY']);
          titleText.add(text);
        }
      }
    });
  }

  /**
   * Method to make sum series
   *
   * @memberof LoadingDischargingSequenceChartComponent
   */
  makeSumSeries = function (event) {
    const chart = this, series = chart.series,
      x = [];
    let sum, flag;

    if (series) {
      series[0].update({
        data: []
      }, false);

      series.forEach(function (p, z) {
        if (p.visible === true) {
          p.data.forEach(function (serie, i) {
            flag = true;
            for (let b = 0; b < x.length; b++) {
              if (serie.x === x[b]) {
                flag = false;
              }
            }
            if (flag) {
              x.push(serie.x);
            }
          });
        }
      });
    }
    for (let i = 0; i < x.length; i++) {
      sum = 0;
      series.forEach(function (p, k) {
        if (p.name !== LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_AGGEGATE'] && p.visible === true) {
          p.data.forEach(function (ob, j) {
            if (ob.x === x[i]) {
              sum += ob.y;
            }
          });
        }
      });
      series[0].addPoint({
        y: parseFloat((sum).toFixed(2)),
        x: x[i]
      }, false);
    }
    chart.redraw();
  }

  /**
   * Get instance of charts
   *
   * @param {Highcharts.Chart} chart
   * @param {SEQUENCE_CHARTS} type
   * @memberof LoadingDischargingSequenceChartComponent
   */
  setChartInstance(chart: Highcharts.Chart, type: SEQUENCE_CHARTS) {
    LoadingDischargingSequenceChartComponent.charts[type] = chart;
  }

  /**
   * Set Cargo tank chart Instance
   *
   * @param {Highcharts.Chart} chart
   * @memberof LoadingDischargingSequenceChartComponent
   */
  setCargoTankChartInstance(chart: Highcharts.Chart) {
    this.setChartInstance(chart, SEQUENCE_CHARTS.CARGO_TANK);
  }

  /**
   * Set Cargo pump chart Instance
   *
   * @param {Highcharts.Chart} chart
   * @memberof LoadingDischargingSequenceChartComponent
   */
  setCargoPumpChartInstance(chart: Highcharts.Chart) {
    this.setChartInstance(chart, SEQUENCE_CHARTS.CARGO_PUMP);
  }

  /**
   * Set Ballast tank chart Instance
   *
   * @param {Highcharts.Chart} chart
   * @memberof LoadingDischargingSequenceChartComponent
   */
  setBallastTankChartInstance(chart: Highcharts.Chart) {
    this.setChartInstance(chart, SEQUENCE_CHARTS.BALLAST_TANK);
  }

  /**
   * Set Ballast Pumpo chart Instance
   *
   * @param {Highcharts.Chart} chart
   * @memberof LoadingDischargingSequenceChartComponent
   */
  setBallastPumpChartInstance(chart: Highcharts.Chart) {
    this.setChartInstance(chart, SEQUENCE_CHARTS.BALLAST_PUMP);
  }

  /**
   * Set Flow rate chart Instance
   *
   * @param {Highcharts.Chart} chart
   * @memberof LoadingDischargingSequenceChartComponent
   */
  setFlowRateChartInstance(chart: Highcharts.Chart) {
    this.setChartInstance(chart, SEQUENCE_CHARTS.FLOW_RATE);
  }

  /**
   * Set stability params chart Instance
   *
   * @param {Highcharts.Chart} chart
   * @memberof LoadingDischargingSequenceChartComponent
   */
  setStabilityParamsChartInstance(chart: Highcharts.Chart) {
    this.setChartInstance(chart, SEQUENCE_CHARTS.STABILITY_PARAMS);
  }

}

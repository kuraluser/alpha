import { Component, Input, OnInit } from '@angular/core';

import * as Highcharts from 'highcharts';
import Theme from 'highcharts/themes/grid-light';
import GanttChart from 'highcharts/modules/gantt';
import Annotations from 'highcharts/modules/annotations';
import { ISequenceData } from './loading-discharging-sequence-chart.model';
import { NgxSpinnerService } from 'ngx-spinner';
import { OPERATIONS } from '../../core/models/common.model';
import { LoadingDischargingSequenceApiService } from '../services/loading-discharging-sequence-api.service';
import { LoadingDischargingTransformationService } from '../services/loading-discharging-transformation.service';
import { TranslateService } from '@ngx-translate/core';
import { QUANTITY_UNIT, RATE_UNIT } from '../../../shared/models/common.model';
import { QuantityDecimalFormatPipe } from '../../../shared/pipes/quantity-decimal-format/quantity-decimal-format.pipe';

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
export class LoadingDischargingSequenceChartComponent implements OnInit {

  // Static fields
  static _operation: OPERATIONS;
  static translationKeys: string[];
  static sequenceData: ISequenceData;
  static _currentQuantitySelectedUnit: QUANTITY_UNIT;
  static _currentRateSelectedUnit: RATE_UNIT;
  static _quantityDecimalFormatPipe;

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
  OPERATIONS = OPERATIONS;
  Highcharts: typeof Highcharts = Highcharts;
  cargoSequenceGanttChart: Highcharts.Options;
  ballastSequenceGanttChart: Highcharts.Options;
  cargoPumpSequenceGanttChart: Highcharts.Options;
  ballastPumpSequenceGanttChart: Highcharts.Options;
  stabilityGanttChart: Highcharts.Options;
  flowRateAreaChart: Highcharts.Options;
  cargoSequenceChartSeries: Array<any>;
  ballastSequenceChartSeries: Array<any>;
  cargoPumpSequenceChartSeries: Array<any>;
  ballastPumpSequenceChartSeries: Array<any>;
  stabilityChartSeries: Array<any>;
  flowRateChartSeries: Array<any>;
  minXAxisValue: number;
  maxXAxisValue: number;
  stageInterval: number;
  stagePlotLines: Highcharts.XAxisPlotLinesOptions[];
  stageTickPositions: number[];
  cargoStageTickPositions: number[];
  tickPositions: number[];
  updateFlowRateChart = false;
  updateCargoTankChart = false;
  updateCargoPumpChart = false;
  updateBallastTankChart = false;
  updateBallastPumpChart = false;
  updateStabilityParamsChart = false;

  // private fields


  /**
   * Draw a single line in the table
   *
   * @memberof LoadingDischargingSequenceChartComponent
   */
  static tableLine(renderer, x1, y1, x2, y2) {
    renderer.path(['M', x1, y1, 'L', x2, y2])
      .attr({
        stroke: '#bebebe',
        'stroke-width': 1
      })
      .addClass('table-border')
      .add();
  }

  constructor(private ngxSpinnerService: NgxSpinnerService,
    private loadingDischargingSequenceService: LoadingDischargingSequenceApiService,
    private loadingDischargingTransformationService: LoadingDischargingTransformationService,
    private translateService: TranslateService,
    private quantityDecimalFormatPipe: QuantityDecimalFormatPipe) { }


  async ngOnInit(): Promise<void> {
    this.ngxSpinnerService.show();
    LoadingDischargingSequenceChartComponent._quantityDecimalFormatPipe = this.quantityDecimalFormatPipe;
    const sequenceDataResponse = await this.loadingDischargingSequenceService.getSequenceData(this.vesselId, this.voyageId, this.infoId, this.operation).toPromise();
    if (sequenceDataResponse) {
      LoadingDischargingSequenceChartComponent.sequenceData = this.loadingDischargingTransformationService.transformSequenceData(sequenceDataResponse);
      this.minXAxisValue = LoadingDischargingSequenceChartComponent.sequenceData?.minXAxisValue;
      this.maxXAxisValue = LoadingDischargingSequenceChartComponent.sequenceData?.maxXAxisValue;
      this.stageInterval = LoadingDischargingSequenceChartComponent.sequenceData?.interval;
      this.stagePlotLines = LoadingDischargingSequenceChartComponent.sequenceData?.stagePlotLines;
      this.cargoStageTickPositions = LoadingDischargingSequenceChartComponent.sequenceData?.cargoStageTickPositions;
      this.stageTickPositions = LoadingDischargingSequenceChartComponent.sequenceData?.stageTickPositions;
      this.tickPositions = LoadingDischargingSequenceChartComponent.sequenceData?.tickPositions;
      this.initializeCharts();
    }
    this.ngxSpinnerService.hide();
  }

  /**
   * Initialize charts
   *
   * @memberof LoadingDischargingSequenceChartComponent
   */
  async initializeCharts() {
    if (!LoadingDischargingSequenceChartComponent.translationKeys) {
      await this.getTranslationKeys();
    }
    if (LoadingDischargingSequenceChartComponent.sequenceData) {
      LoadingDischargingSequenceChartComponent.sequenceData = this.loadingDischargingTransformationService.transformSequenceDataToSelectedUnit(LoadingDischargingSequenceChartComponent.sequenceData, this.currentQuantitySelectedUnit, this.currentRateSelectedUnit);

      this.setCargoTankSequenceChartOptions();
      this.setCargoTankSequenceData();
      this.updateCargoTankChart = true;

      if (this.operation === OPERATIONS.DISCHARGING) {
        this.setCargoPumpSequenceChartOptions();
        this.setCargoPumpSequenceData();
        this.updateCargoPumpChart = true;
      }

      this.setBallastTankSequenceChartOptions();
      this.setBallastTankSequenceData();
      this.updateBallastTankChart = true;

      this.setBallastPumpSequenceChartOptions();
      this.setBallastPumpSequenceData();
      this.updateBallastPumpChart = true;

      this.setFlowRateChartOptions();
      this.setFlowRateData();
      this.updateFlowRateChart = true;

      this.setStabilityChartOptions();
      this.setStabilityData();
      this.updateStabilityParamsChart = true;
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
      "SEQUENCE_CHART_DISCHARGING_RATE",
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
        color: dataObj?.id === 'stripping' ? '#f8f8f8' : dataObj.color,
        abbreviation: dataObj.abbreviation,
        y: tankIndex,
        pointWidth: dataObj?.id === 'stripping' ? 40 : 6,
        borderColor: dataObj?.id === 'stripping' ? '#bebebe' : null,
        borderWidth: dataObj?.id === 'stripping' ? 1 : 0,
        borderRadius: dataObj?.id === 'stripping' ? 5 : 0,
      });
    });
    this.cargoSequenceChartSeries = [{
      name: `${LoadingDischargingSequenceChartComponent._operation === OPERATIONS.LOADING ? LoadingDischargingSequenceChartComponent.translationKeys['LOADING_SEQUENCE_CHART_LABEL'] : LoadingDischargingSequenceChartComponent.translationKeys['DISCHARGING_SEQUENCE_CHART_LABEL']}`,
      custom: LoadingDischargingSequenceChartComponent.sequenceData?.cargoStages,
      showInLegend: false,
      data: cargoSequenceSeriesData
    }];
    this.cargoSequenceGanttChart.series = this.cargoSequenceChartSeries;
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
          render: this.sequnceChartRender
        }
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
                return !this.point?.options?.className && this.point?.options?.id !== 'stripping' ? this.point?.ullage : undefined;
              }
            },
            {
              enabled: true,
              color: '#666666',
              formatter: function () {
                return this.point?.options?.id === 'stripping' ? LoadingDischargingSequenceChartComponent.translationKeys['STRIPPING_BY_EDUCTOR'] : undefined;
              },
              animation: {
                defer: 6000
              }
            },
            {
              enabled: true,
              format: '<i class="pi {point.className} sequence-icon"></i>',
              useHTML: true,
              align: 'center'
            }]
        }
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
            setExtremes: this.syncExtremes
          },
          labels: {
            align: 'center',
            formatter: function (y) {
              const hours = (1000 * 60 * 60),
                number = (Number(this.value) - this.axis.min) / (hours);
              return number.toString();
            }
          },
          grid: {
            enabled: false
          },
          // tickInterval: 1000 * 60 * 60,
          tickPositions: this.tickPositions,
          tickLength: 0,
          gridLineWidth: 1,
          gridLineColor: '#bebebe',
          min: this.minXAxisValue,
          max: this.maxXAxisValue,
          plotLines: this.stagePlotLines
        },
        {
          grid: {
            enabled: true,
          },
          lineWidth: 0,
          lineColor: 'transparent',
          gridZIndex: 3,
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

                const stage = LoadingDischargingSequenceChartComponent.sequenceData?.cargoStages.find((data: any) => data.start <= this.value && data.end > this.value);

                stage?.cargos?.forEach(cargo => {
                  cargosLabel += `<p><span class="badge-custom mx-1" style="background-color: ${cargo?.color}">${cargo?.abbreviation}</span> - ${cargo?.quantity} ${LoadingDischargingSequenceChartComponent._currentQuantitySelectedUnit}</p>`;
                });

                const duration = (stage?.end - stage?.start) / (60 * 60 * 1000);
                const categoryLabel =
                  `<div class=" font-main  text-center pl-5 pr-5">
                    ${cargosLabel}
                      <div class="content-ellipsis">(${duration.toFixed(2)} ${LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_HRS']})</div>
                  </div>`;

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
          gridZIndex: 3,
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
            text: `${LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_TOTAL']}: ${LoadingDischargingSequenceChartComponent._quantityDecimalFormatPipe.transform(LoadingDischargingSequenceChartComponent.sequenceData?.cargoTankCategories.reduce((a, b) => a + b.quantity, 0), LoadingDischargingSequenceChartComponent._currentQuantitySelectedUnit)} ${LoadingDischargingSequenceChartComponent._currentQuantitySelectedUnit}`,
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

              const categoryLabel =
                `<div class="font-main  text-center pl-5 pr-5">
                <div class="content-ellipsis">${LoadingDischargingSequenceChartComponent._quantityDecimalFormatPipe.transform(quantity, LoadingDischargingSequenceChartComponent._currentQuantitySelectedUnit)} ${LoadingDischargingSequenceChartComponent._currentQuantitySelectedUnit}</div>
                </div>`;

              return categoryLabel;
            },
          },
          offset: '0'
        },
        {
          visible: LoadingDischargingSequenceChartComponent._operation === OPERATIONS.LOADING,
          title: {
            text: `${LoadingDischargingSequenceChartComponent._operation === OPERATIONS.LOADING ? LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_LOADING_RATE'] : LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_DISCHARGING_RATE']} ${LoadingDischargingSequenceChartComponent._currentRateSelectedUnit}`,
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
                LoadingDischargingSequenceChartComponent.sequenceData?.cargoLoadingRates[equalIndex]?.loadingRates?.map(item=>{
                  tempRateArray.push(item?.toFixed());
                });
                const rate = tempRateArray.join('/');
                const categoryLabel =
                  `<div class="font-main  text-center pl-5 pr-5">
                      <div class="content-ellipsis">${rate} ${LoadingDischargingSequenceChartComponent._currentRateSelectedUnit}</div>
                  </div>`;

                return categoryLabel;
              }
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
                    if (cargo?.tankId === Number(this.value) && !cargos.some(item => item?.cargoNominationId === cargo?.cargoNominationId)) {
                      cargos.push({ cargoNominationId: cargo?.cargoNominationId, color: cargo?.color, abbreviation: cargo?.abbreviation });
                    }
                  });
                  cargos?.forEach(cargo => {
                    cargosLabel += `<span><span class="badge-custom mx-1" style="background-color: ${cargo?.color}">${cargo?.abbreviation}</span></span>`;
                  });

                  const categoryLabel = cargosLabel;

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
                return item.quantity.toString();
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
          if (this?.point.options.className === "pi-sort-up") {
            isCOW = true;
            tooltipContent = `
              <table>
                <tr>
                  <th>${LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_COW_ANGLE']}</th>
                  <td>150&deg</td>
                </tr>
              </table>`;
          } else {
            cargoNames = this?.point?.abbreviation;
            const min = this.series.xAxis.min;
            startingTime = (this?.point?.start - min) / (1000 * 60 * 60);
            endingTime = (this?.point?.end - min) / (1000 * 60 * 60);
            duration = (this?.point?.end - min) / (1000 * 60 * 60);
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
                  <td>${quantity}</td>
                </tr>
                <tr>
                  <th>${LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_TOOLTIP_ULLAGE']}</th>
                  <td>${ullage}</td>
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
        color: dataObj?.id === 'stripping' ? '#f8f8f8' : dataObj.color,
        y: pumpIndex,
        pointWidth: dataObj?.id === 'stripping' ? 40 : 6,
        borderColor: dataObj?.id === 'stripping' ? '#bebebe' : null,
        borderWidth: dataObj?.id === 'stripping' ? 1 : 0,
        borderRadius: dataObj?.id === 'stripping' ? 5 : 0,
      });
    });
    this.cargoPumpSequenceChartSeries = [{
      name: `${LoadingDischargingSequenceChartComponent._operation === OPERATIONS.LOADING ? LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_LOADING_RATE'] : LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_DISCHARGING_RATE']}`,
      custom: LoadingDischargingSequenceChartComponent.sequenceData?.cargoStages,
      showInLegend: false,
      data: cargoPumpSequenceSeriesData
    }];
    this.cargoPumpSequenceGanttChart.series = this.cargoPumpSequenceChartSeries;
  }
  /**
   * Set cargo pump chart options
   *
   * @memberof LoadingDischargingSequenceChartComponent
   */
  setCargoPumpSequenceChartOptions() {
    this.cargoPumpSequenceGanttChart = {
      credits: {
        enabled: false
      },
      chart: {
        marginLeft: 280, // Keep all charts left aligned
        spacing: [0, 0, 0, 0],
        events: {
        }
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
              format: '<i class="pi {point.className} sequence-icon"></i>',
              useHTML: true,
              align: 'center'
            }]
        }
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
            setExtremes: this.syncExtremes
          },
          labels: {
            align: 'center',
            formatter: function (y) {
              const hours = (1000 * 60 * 60),
                number = (Number(this.value) - this.axis.min) / (hours);
              return number.toString();
            }
          },
          grid: {
            enabled: false
          },
          // tickInterval: 1000 * 60 * 60,
          tickPositions: this.tickPositions,
          tickLength: 0,
          gridLineWidth: 1,
          gridLineColor: '#bebebe',
          min: this.minXAxisValue,
          max: this.maxXAxisValue,
          dateTimeLabelFormats: {
            hour: '%H'
          },
          plotLines: this.stagePlotLines
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
          gridZIndex: 3,
          gridLineColor: '#000d20',
          tickPositions: this.cargoStageTickPositions,
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
                LoadingDischargingSequenceChartComponent.sequenceData?.cargoLoadingRates[equalIndex]?.loadingRates?.map(item=>{
                  tempRateArray.push(item?.toFixed());
                });
                const rate = tempRateArray.join('/');

                const categoryLabel =
                  `<div class="row">
                    <div class="col-md-12 text-center">
                      <span>Requested Max</span>
                        <br/><span>${rate} ${LoadingDischargingSequenceChartComponent._currentRateSelectedUnit}</span>
                    </div>
                  </div>`;

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
          gridZIndex: 3,
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
                <td>${seriesName} <span>${rate?.toFixed()}</span></td>
              </tr>
            </table>`;

        },
      },
      series: this.cargoPumpSequenceChartSeries
    };
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
        color: dataObj?.id === 'stripping' ? '#f8f8f8' : dataObj.color,
        abbreviation: 'Ballast',
        y: tankIndex,
        pointWidth: dataObj?.id === 'stripping' ? 40 : 6,
        borderColor: dataObj?.id === 'stripping' ? '#bebebe' : null,
        borderWidth: dataObj?.id === 'stripping' ? 1 : 0,
        borderRadius: dataObj?.id === 'stripping' ? 5 : 0,
      });
    });
    this.ballastSequenceChartSeries = [{
      name: `${LoadingDischargingSequenceChartComponent._operation === OPERATIONS.LOADING ? LoadingDischargingSequenceChartComponent.translationKeys['LOADING_SEQUENCE_CHART_LABEL'] : LoadingDischargingSequenceChartComponent.translationKeys['DISCHARGING_SEQUENCE_CHART_LABEL']}`,
      custom: LoadingDischargingSequenceChartComponent.sequenceData?.cargoStages,
      showInLegend: false,
      data: ballastSequenceSeriesData
    }];
    this.ballastSequenceGanttChart.series = this.ballastSequenceChartSeries;
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
        }
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
                return this.point?.options?.id !== 'stripping' && !this.point?.options?.className ? this.point?.sounding : undefined;
              }
            },
            {
              enabled: true,
              color: '#666666',
              formatter: function () {
                return this.point?.options?.id === 'stripping' ? 'STRIPPING BY EDUCTOR' : undefined;
              },
              animation: {
                defer: 6000
              }
            },
            {
              enabled: true,
              format: '<i class="pi {point.className} sequence-icon"></i>',
              useHTML: true,
              align: 'center'
            }
          ]
        }
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
            setExtremes: this.syncExtremes
          },
          labels: {
            align: 'center',
            formatter: function (y) {
              const hours = (1000 * 60 * 60),
                number = (Number(this.value) - this.axis.min) / (hours);
              return number.toString();
            }
          },
          grid: {
            enabled: false
          },
          // tickInterval: 1000 * 60 * 60,
          tickPositions: this.tickPositions,
          tickLength: 0,
          gridLineWidth: 1,
          gridLineColor: '#bebebe',
          min: this.minXAxisValue,
          max: this.maxXAxisValue,
          plotLines: this.stagePlotLines
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
              const categoryLabel =
                `<div class="row">
                  <div class="col-md-12 text-center">
                    <span>${LoadingDischargingSequenceChartComponent._quantityDecimalFormatPipe.transform(quantity, LoadingDischargingSequenceChartComponent._currentQuantitySelectedUnit)} ${LoadingDischargingSequenceChartComponent._currentQuantitySelectedUnit}</span>
                  </div>
                </div>`;

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
          gridZIndex: 3,
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
          gridZIndex: 3,
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
          const min = this.series.xAxis.min, startingTime = (this?.point?.start - min) / (1000 * 60 * 60),
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
                  <td>${quantity}</td>
                </tr>
                <tr>
                  <th>${LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_TOOLTIP_SOUNDING']}</th>
                  <td>${sounding}</td>
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
        color: dataObj?.id?.includes('gravity') || dataObj?.id === 'stripping' ? '#f8f8f8' : dataObj.color,
        y: pumpIndex,
        pointWidth: dataObj?.id?.includes('gravity') || dataObj?.id === 'stripping' ? 40 : 6,
        borderColor: dataObj?.id?.includes('gravity') || dataObj?.id === 'stripping' ? '#bebebe' : null,
        borderWidth: dataObj?.id?.includes('gravity') || dataObj?.id === 'stripping' ? 1 : 0,
        borderRadius: dataObj?.id?.includes('gravity') || dataObj?.id === 'stripping' ? 5 : 0,
      });
    });

    this.ballastPumpSequenceChartSeries = [{
      name: `${LoadingDischargingSequenceChartComponent._operation === OPERATIONS.LOADING ? LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_DEBALLASTING_RATE'] : LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_DISCHARGING_RATE']}`,
      custom: LoadingDischargingSequenceChartComponent.sequenceData?.cargoStages,
      showInLegend: false,
      data: ballastPumpSequenceSeriesData
    }];
    this.ballastPumpSequenceGanttChart.series = this.ballastPumpSequenceChartSeries;
  }

  /**
   * Set ballast pump chart options
   *
   * @memberof LoadingDischargingSequenceChartComponent
   */
  setBallastPumpSequenceChartOptions() {
    this.ballastPumpSequenceGanttChart = {
      credits: {
        enabled: false
      },
      chart: {
        marginLeft: 280, // Keep all charts left aligned
        spacing: [0, 0, 0, 0],
        events: {
        }
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
                return this.point?.options?.id !== 'stripping' && !this.point?.options?.className ? this.point?.sounding : undefined;
              }
            },
            {
              enabled: true,
              color: '#666666',
              formatter: function () {
                return this.point?.options?.id?.includes('gravity') ? LoadingDischargingSequenceChartComponent.translationKeys['GRAVITY'] : undefined;
              },
              animation: {
                defer: 6000
              }
            },
            {
              enabled: true,
              format: '<i class="pi {point.className} sequence-class"></i>',
              useHTML: true,
              align: 'center'
            }]
        }
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
            setExtremes: this.syncExtremes
          },
          labels: {
            align: 'center',
            formatter: function (y) {
              const hours = (1000 * 60 * 60),
                number = (Number(this.value) - this.axis.min) / (hours);
              return number.toString();
            }
          },
          grid: {
            enabled: false
          },
          // tickInterval: 1000 * 60 * 60,
          tickPositions: this.tickPositions,
          tickLength: 0,
          gridLineWidth: 1,
          gridLineColor: '#bebebe',
          min: this.minXAxisValue,
          max: this.maxXAxisValue,
          dateTimeLabelFormats: {
            hour: '%H'
          },
          plotLines: this.stagePlotLines
        },
        {
          grid: {
            enabled: true,
          },
          lineWidth: 0,
          lineColor: 'transparent',
          gridZIndex: 3,
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
          gridZIndex: 3,
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
                <td>${seriesName} <span>${rate?.toFixed()}</span></td>
              </tr>
            </table>`;

        },
      },
      series: this.ballastPumpSequenceChartSeries
    };
  }

  /**
   * Set flow rate chart data
   *
   * @memberof LoadingDischargingSequenceChartComponent
   */
  setFlowRateData() {
    const xAxisTicks = [], aggregateData = [];
    let sum, count;

    LoadingDischargingSequenceChartComponent.sequenceData?.flowRates.forEach(function (tankDetails, z) {
      tankDetails.data.forEach(function (data, i) {
        if (!xAxisTicks.some(value => value === data[0])) {
          xAxisTicks.push(data[0]);
        }
      });
    });
    xAxisTicks.sort();
    for (let i = 0; i < xAxisTicks.length; i++) {
      sum = 0; count = 0;
      LoadingDischargingSequenceChartComponent.sequenceData?.flowRates.forEach(function (tankDetails, k) {
        tankDetails.data.forEach(function (ob, j) {
          if (ob[0] === xAxisTicks[i]) {
            count++;
            sum += ob[1];
          }
        });
      });
      aggregateData?.push([xAxisTicks[i], parseFloat((sum / count).toFixed())]);
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
        return {
          name: item.tankName,
          type: 'line',
          custom: LoadingDischargingSequenceChartComponent.sequenceData?.cargoStages,
          data: item.data,
          step: 'left'
        }
      })];
    this.flowRateAreaChart.series = this.flowRateChartSeries;
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
        }
      },
      legend: {
        backgroundColor: 'transparent',
        itemMarginBottom: 20
      },
      title: {
        text: null,
      },
      plotOptions: {
        series: {
          events: {
            hide: this.makeSumSeries,
            show: this.makeSumSeries,
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
            setExtremes: this.syncExtremes
          },
          labels: {
            align: 'center',
            formatter: function (y) {
              const hours = (1000 * 60 * 60),
                number = (Number(this.value) - this.axis.min) / (hours);
              return number.toString();
            }
          },
          // tickInterval: 1000 * 60 * 60,
          tickPositions: this.tickPositions,
          tickLength: 0,
          // tickColor: '#bebebe',
          gridLineColor: '#bebebe',
          min: this.minXAxisValue,
          max: this.maxXAxisValue,
          dateTimeLabelFormats: {
            hour: '%H'
          },
          plotLines: this.stagePlotLines
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
          min: this.minXAxisValue,
          max: this.maxXAxisValue,
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
        followPointer: true
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
    this.stabilityChartSeries = [
      {
        yAxis: 0,
        type: 'areaspline',
        name: LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_FORE_DRAFT'],
        custom: {
          showFinalValue: true
        },
        data: LoadingDischargingSequenceChartComponent.sequenceData?.stabilityParams.find(item => item.name === 'fore_draft')?.data,
      },
      {
        yAxis: 0,
        type: 'areaspline',
        name: LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_AFT_DRAFT'],
        custom: {
          showFinalValue: true
        },
        data: LoadingDischargingSequenceChartComponent.sequenceData?.stabilityParams.find(item => item.name === 'aft_draft')?.data,
      },
      {
        yAxis: 0,
        type: 'areaspline',
        name: LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_TRIM'],
        custom: {
          showFinalValue: true
        },
        data: LoadingDischargingSequenceChartComponent.sequenceData?.stabilityParams.find(item => item.name === 'trim')?.data,
      },
      {
        yAxis: 0,
        type: 'areaspline',
        name: LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_UKC'],
        custom: {
          showFinalValue: true
        },
        data: LoadingDischargingSequenceChartComponent.sequenceData?.stabilityParams.find(item => item.name === 'ukc')?.data,
      },
      {
        yAxis: 0,
        type: 'areaspline',
        name: LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_GM'],
        custom: {
          showFinalValue: true
        },
        data: LoadingDischargingSequenceChartComponent.sequenceData?.stabilityParams.find(item => item.name === 'gm')?.data,
      },
      {
        yAxis: 0,
        type: 'areaspline',
        name: LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_SF'],
        data: LoadingDischargingSequenceChartComponent.sequenceData?.stabilityParams.find(item => item.name === 'sf')?.data,
      },
      {
        yAxis: 0,
        type: 'areaspline',
        name: LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_BM'],
        data: LoadingDischargingSequenceChartComponent.sequenceData?.stabilityParams.find(item => item.name === 'bm')?.data,
      }];
    this.stabilityGanttChart.series = this.stabilityChartSeries;
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
        }
      },
      title: {
        text: null,
      },
      legend: {
        enabled: false
      },
      plotOptions: {
        series: {
          visible: false,
        },
      },
      xAxis: [
        {
          // startOnTick: true,
          events: {
            setExtremes: this.syncExtremes
          },
          type: 'datetime',
          lineWidth: 0,
          labels: {
            enabled: false,
          },
          // tickInterval: 1000 * 60 * 60,
          tickPositions: this.tickPositions,
          tickLength: 0,
          tickColor: '#bebebe',
          gridLineWidth: 0,
          top: 13,
          min: this.minXAxisValue,
          max: this.maxXAxisValue,
        },
        {
          title: {
            align: 'low',
            offset: 0,
            rotation: 0,
            x: -268,
            y: 15,
            text: LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_DRAFT'],
          },
          // startOnTick: true,
          opposite: true,
          type: 'datetime',
          lineWidth: 0,
          labels: {
            align: 'center',
            y: 15,
            formatter: function (y) {
              const hours = (1000 * 60 * 60),
                number = (Number(this.value) - this.axis.min) / (hours);
              return number.toFixed(1);
            }
          },
          tickPositions: this.stageTickPositions,
          tickLength: 20,
          tickColor: '#bebebe',
          gridLineWidth: 0,
          top: 24,
          min: this.minXAxisValue,
          max: this.maxXAxisValue,
        },
        {
          lineWidth: 0,
          // startOnTick: true,
          opposite: true,
          top: 39,
          tickPositions: this.cargoStageTickPositions,
          tickColor: '#000d20',
          tickPosition: 'inside',
          tickLength: 20,
          gridLineWidth: 0,
          min: this.minXAxisValue,
          max: this.maxXAxisValue,
          labels: {
            enabled: false
          }
        }
      ],
      yAxis: [{
        id: 'yaxis-3',
        gridLineWidth: 0,
        labels: {
          enabled: false
        },
        title: {
          text: null
        }
      }],
      tooltip: {
        shared: true,
        borderColor: '#bebebe',
        borderWidth: 1,
        borderRadius: 20,
        followPointer: true,
        enabled: false
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
   * @memberof LoadingDischargingSequenceChartComponent
   */
  synchronizeTooltips(e) {
    let chart: Highcharts.Chart,
      points: Array<Highcharts.Point>,
      i;
    for (i = 0; i < Highcharts.charts.length; i++) {
      chart = Highcharts.charts[i];
      e = chart?.pointer?.normalize(e); // Find coordinates within the chart
      points = [];
      chart?.xAxis[0].drawCrosshair(e);
      chart?.series.forEach((p) => {
        const point = this.searchPoint(e, chart, p);
        if (point) {
          points.push(point)
        }
      })

      if (points.length) {
        let number = 0;
        points.forEach((p, index: number) => {
          if (!p?.series?.visible) {
            points.splice(index - number, 1);
            number++;
          }
        })
        points?.forEach((point) => {
          // point.onMouseOver(); May be needed in future
          chart.tooltip.refresh(points); // Show the tooltip
          // chart.xAxis[0].drawCrosshair(e, points[0]); // Show the crosshair may be needed in future
        });
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
  searchPoint(event, chart: Highcharts.Chart, series) {
    const points = series?.points,
      len = points?.length,
      x = chart.xAxis[0].toValue(event.chartX),
      range = 1000 * 60 * 5; // Show sychronized tooltip in the range
    let pointX,
      i;

    for (i = 0; i < len; i++) {
      pointX = points[i].x;
      if (x - range < pointX && pointX < x + range) {
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
    const chart = this,
      series = chart.series,
      renderer = chart.renderer,
      fontSize = Number(Highcharts.SVGRenderer.prototype.getStyle().fontSize.replace('px', ''));

    const tableTop = 0,
      tableLeft = 0,
      rowHeight = 40,
      cellPadding = 2.5,
      tablePadding = 20;

    let cellWidth = 0;

    // Render series labels
    series.forEach((serie, serie_index) => {
      renderer.text(
        serie.name, tableLeft + cellPadding, tableTop + (serie_index + 2) * rowHeight - cellPadding).css({
          color: '#666666'
        })
        .addClass('table-header-text')
        .add();
      // if (serie.options?.custom?.showFinalValue) {
      //   renderer.text(
      //     serie.data[serie.data.length - 1].y, chart.plotLeft - 2 * tablePadding - cellPadding, tableTop + (serie_index + 2) * rowHeight - cellPadding).css({
      //       color: '#666666',
      //       align: 'right'
      //     }).add();
      // }
    });

    // Render data points
    chart.xAxis[0].tickPositions.forEach((category, category_index) => {

      series.forEach((item, i) => {
        if (series[i].data[category_index]) {
          cellWidth = series[i].data[category_index].plotX;
          // if (category_index !== 0) {
          renderer.text(series[i].data[category_index]?.y, chart.plotLeft + series[i].data[category_index].plotX, tableTop + (i + 2) * rowHeight - cellPadding).attr({
            align: 'center',
          }).css({
            color: '#666666'
          })
            .addClass('table-cell-text')
            .add();
          // }

          // horizontal lines
          if (i === 0) {
            LoadingDischargingSequenceChartComponent.tableLine( // top
              renderer,
              tableLeft,
              tableTop + cellPadding,
              chart.plotLeft + chart.plotWidth,
              tableTop + cellPadding
            );
            LoadingDischargingSequenceChartComponent.tableLine( // bottom
              renderer,
              tableLeft,
              tableTop + (series?.length + 2) * rowHeight - cellPadding - ((rowHeight + fontSize) / 2 - 2),
              chart.plotLeft + chart.plotWidth,
              tableTop + (series?.length + 2) * rowHeight - cellPadding - ((rowHeight + fontSize) / 2 - 2)
            );
          }
          // horizontal line
          LoadingDischargingSequenceChartComponent.tableLine(
            renderer,
            tableLeft,
            tableTop + (i + 2) * rowHeight - cellPadding - ((rowHeight + fontSize) / 2 - 2),
            chart.plotLeft + chart.plotWidth,
            tableTop + (i + 2) * rowHeight - cellPadding - ((rowHeight + fontSize) / 2 - 2)
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
          tableTop + (chart.xAxis[0].tickPositions.length + 1) * rowHeight + cellPadding
        );
        LoadingDischargingSequenceChartComponent.tableLine(
          renderer,
          chart.plotLeft - tablePadding,
          tableTop + cellPadding,
          chart.plotLeft - tablePadding,
          tableTop + (chart.xAxis[0].tickPositions.length + 1) * rowHeight + cellPadding
        );
      }

      if (category_index === chart.xAxis[0].tickPositions.length - 1) { // right table border
        LoadingDischargingSequenceChartComponent.tableLine(
          renderer,
          chart.plotLeft + cellWidth + tablePadding,
          tableTop + cellPadding,
          chart.plotLeft + cellWidth + tablePadding,
          tableTop + (chart.xAxis[0].tickPositions.length + 1) * rowHeight + cellPadding
        );
      } */
    });
  }

  /**
   * Synchronize zooming through the setExtremes event handler
   *
   * @memberof LoadingDischargingSequenceChartComponent
   */
  syncExtremes = function (event) {
    const thisChart = this.chart;

    if (event.trigger !== 'syncExtremes') { // Prevent feedback loop
      Highcharts.charts.forEach((chart) => {
        if (chart !== thisChart) {
          if (chart.xAxis[0].setExtremes) { // It is null while updating
            chart.xAxis[0].setExtremes(
              event.min,
              event.max,
              undefined,
              false,
              { trigger: 'syncExtremes' }
            );
          }
        }
      });
    }
  }

  /**
   * Sequence chart render handler
   *
   * @memberof LoadingDischargingSequenceChartComponent
   */
  sequnceChartRender = function () {
    const chart: Highcharts.Chart = this;

    // Show COW legends only in discharging operation
    if (LoadingDischargingSequenceChartComponent._operation === OPERATIONS.DISCHARGING) {
      const cowLegend = `<ul class="list-group list-group-horizontal cow-legend">
                          <li class="list-group-item">
                            <i class="pi pi-sort cow-legend-icon full-wash"></i>
                            <span class="cow-legend-label">${LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_FULL_WASH']}</span>
                          </li>
                          <li class="list-group-item">
                            <i class="pi pi-sort-down cow-legend-icon top-wash"></i>
                            <span class="cow-legend-label">${LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_TOP_WASH']}</span>
                          </li>
                          <li class="list-group-item">
                            <i class="pi pi-sort-up cow-legend-icon bottom-wash"></i>
                            <span class="cow-legend-label">${LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_BOTTOM_WASH']}</span> </li>
                        </ul>`;
      chart.renderer.text(cowLegend, chart.plotLeft, chart.chartHeight - 20, true).attr({
        cursor: 'pointer',
        zIndex: 1
      }).add();
    }
    /* TODO:Mabe added as annotation this.addAnnotation({
      labels: [{
        backgroundColor: 'transparent',
        shape: 'rect',
        point: {
          x: this.plotLeft,
          y: this.plotHeight + 300
        },
        useHTML: true,
        text: '<i class="pi pi-sort-up" style="color: #666666;font-size: 1.5em"></i>'
      }]
    }); */
  }

  /**
   * Method to make sum series
   *
   * @memberof LoadingDischargingSequenceChartComponent
   */
  makeSumSeries = function (event) {
    const chart = this, series = chart.series,
      x = [];
    let sum, flag, count;

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
    for (let i = 0; i < x.length; i++) {
      sum = 0; count = 0;
      series.forEach(function (p, k) {
        if (p.name !== LoadingDischargingSequenceChartComponent.translationKeys['SEQUENCE_CHART_AGGEGATE'] && p.visible === true) {
          p.data.forEach(function (ob, j) {
            if (ob.x === x[i]) {
              count++;
              sum += ob.y;
            }
          });
        }
      });
      series[0].addPoint({
        y: parseFloat((sum / count).toFixed(2)),
        x: x[i]
      }, false);
    }
    chart.redraw();
  }
}

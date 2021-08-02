import { Component, Input, OnInit } from '@angular/core';

import * as Highcharts from 'highcharts';
import Theme from 'highcharts/themes/grid-light';
import GanttChart from 'highcharts/modules/gantt';
import Annotations from 'highcharts/modules/annotations';
import { IPump, IPumpData, IStabilityParam, ITank, ITankData } from './loading-discharging-sequence-chart.model';
import { NgxSpinnerService } from 'ngx-spinner';
import { OPERATIONS } from '../../core/models/common.model';
import { LoadingDischargingSequenceApiService } from '../services/loading-discharging-sequence-api.service';
import { LoadingDischargingTransformationService } from '../services/loading-discharging-transformation.service';

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

  // Input fields
  @Input() static operation: OPERATIONS = OPERATIONS.LOADING;

  get operation() {
    return LoadingDischargingSequenceChartComponent.operation;
  }

  // Static fields
  static cargoStages: any[];
  static cargos: ITankData[];
  static ballasts: ITankData[];
  static cargoLoadingRates: number[][];

  // Public fileds
  OPERATIONS = OPERATIONS;
  Highcharts: typeof Highcharts = Highcharts;
  cargoSequenceGanttChart: Highcharts.Options;
  ballastSequenceGanttChart: Highcharts.Options;
  cargoPumpSequenceGanttChart: Highcharts.Options;
  ballastPumpSequenceGanttChart: Highcharts.Options;
  stabilityGanttChart: Highcharts.Options;
  flowRateAreaChart: Highcharts.Options;
  cargoPumps: IPumpData[];
  ballastPumps: IPumpData[];
  cargoSequenceChartSeries: Array<any>;
  ballastSequenceChartSeries: Array<any>;
  cargoPumpSequenceChartSeries: Array<any>;
  ballastPumpSequenceChartSeries: Array<any>;
  stabilityChartSeries: Array<any>;
  flowRateChartSeries: Array<any>;
  cargoTankCategories: ITank[] = [];
  ballastTankCategories: ITank[] = [];
  cargoPumpCategories: IPump[] = [];
  ballastPumpCategories: IPump[] = [];
  flowRateChartTankCategories: ITank[] = [];
  minXAxisValue: number;
  maxXAxisValue: number;
  stageInterval: number;
  stagePlotLines: Highcharts.XAxisPlotLinesOptions[];
  stageTickPositions: number[];
  cargoStageTickPositions: number[];
  tickPositions: number[];
  flowRates: Array<any>;
  stabilityParams: IStabilityParam[];


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
      .add();
  }

  constructor(private ngxSpinnerService: NgxSpinnerService,
    private loadingDischargingSequenceService: LoadingDischargingSequenceApiService,
    private loadingDischargingTransformationService: LoadingDischargingTransformationService) { }


  async ngOnInit(): Promise<void> {
    this.ngxSpinnerService.show();

    const sequenceDataResponse = await this.loadingDischargingSequenceService.getSequenceData(0, 0, 0).toPromise();
    const sequenceData =  this.loadingDischargingTransformationService.transformSequenceData(sequenceDataResponse);
    LoadingDischargingSequenceChartComponent.cargos = sequenceData.cargos;
    LoadingDischargingSequenceChartComponent.ballasts = sequenceData.ballasts;
    LoadingDischargingSequenceChartComponent.cargoStages = sequenceData.cargoStages;
    LoadingDischargingSequenceChartComponent.cargoLoadingRates = sequenceData.cargoLoadingRates;
    this.cargoTankCategories = sequenceData.cargoTankCategories;
    this.flowRateChartTankCategories = sequenceData.cargoTankCategories;
    this.ballastTankCategories = sequenceData.ballastTankCategories;
    this.minXAxisValue = sequenceData.minXAxisValue;
    this.maxXAxisValue = sequenceData.maxXAxisValue;
    this.stageInterval = sequenceData.interval;
    this.stagePlotLines = sequenceData.stagePlotLines;
    this.cargoStageTickPositions = sequenceData.cargoStageTickPositions;
    this.stageTickPositions = sequenceData.stageTickPositions;
    this.tickPositions = sequenceData.tickPositions;
    this.cargoPumps = sequenceData?.cargoPumps;
    this.ballastPumps = sequenceData.ballastPumps;
    this.cargoPumpCategories = sequenceData?.cargoPumpCategories;
    this.ballastPumpCategories = sequenceData.ballastPumpCategories;
    this.flowRates = sequenceData?.flowRates ?? [];
    this.stabilityParams = sequenceData.stabilityParams;

    this.setCargoTankSequenceData();
    this.setBallastTankSequenceData();
    this.setBallastPumpSequenceData();
    this.setFlowRateData();
    this.setStabilityData();

    if (this.operation === OPERATIONS.DISCHARGING) {
      this.setCargoPumpSequenceData();
    }
    this.ngxSpinnerService.hide();

  }

  /**
   * Set cargo tank chart data
   *
   * @memberof LoadingDischargingSequenceChartComponent
   */
  setCargoTankSequenceData() {
    const cargoSequenceSeriesData = [];
    LoadingDischargingSequenceChartComponent.cargos?.forEach((dataObj: any) => {
      const tankIndex = this.cargoTankCategories.findIndex(i => i?.id === dataObj.tankId);
      cargoSequenceSeriesData.push({
        tankId: dataObj?.tankId,
        start: dataObj?.start,
        end: dataObj?.end,
        className: dataObj?.className,
        tankName: this.cargoTankCategories[tankIndex].tankName,
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
      name: `${LoadingDischargingSequenceChartComponent.operation === OPERATIONS.LOADING ? 'LOADING' : 'DISCHARGING'} SEQUENCE`,
      custom: LoadingDischargingSequenceChartComponent.cargoStages,
      showInLegend: false,
      data: cargoSequenceSeriesData
    }];

    this.cargoSequenceGanttChart = {
      credits: {
        enabled: false
      },
      chart: {
        marginLeft: 240, // Keep all charts left aligned
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
                return this.point?.options?.id === 'stripping' ? 'STRIPPING BY EDUCTOR' : undefined;
              },
              animation: {
                defer: 6000
              }
            },
            {
              enabled: true,
              format: '<i class="pi {point.className}" style="color: #666666;font-size: 1.5em"></i>',
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
                const equalIndex = this.axis.tickPositions.findIndex(value => value === this.value);
                const nextTick = this.axis.tickPositions[equalIndex + 1];
                const cargos = [];
                let cargosLabel = '';

                LoadingDischargingSequenceChartComponent.cargos.forEach((cargo: any) => {
                  if (cargo.start >= this.value && cargo.end <= nextTick && !cargos.some(item => item?.cargoNominationId === cargo?.cargoNominationId)) {
                    cargos.push({ cargoNominationId: cargo?.cargoNominationId, color: cargo?.color, abbreviation: cargo?.abbreviation, name: cargo?.name });
                  }
                });

                cargos.forEach(cargo => {
                  cargosLabel += '<span><span class="badge-custom mx-1" style="background-color: ' + cargo?.color + '"> ' + cargo?.abbreviation + '</span>' + cargo?.name + '</span>';
                });

                const stage = LoadingDischargingSequenceChartComponent.cargoStages.find((data: any) => data.start <= this.value && data.end > this.value);

                const categoryLabel =
                  '<div class="row">' +
                  '<div class="col-md-12" style="text-align: center">' +
                  cargosLabel +
                  '<br/><span>' + stage.duration + '</span>' +
                  '</div>' +
                  '</div>';

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
            text: `TOTAL: ${this.cargoTankCategories.reduce((a, b) => a + b.quantity, 0)} BBLS`,
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
              const equalIndex = this.axis.tickPositions.findIndex(value => value === this.value);
              const nextTick = this.axis.tickPositions[equalIndex + 1];
              let quantity = 0;
              const quantityPerTank = [];
              LoadingDischargingSequenceChartComponent.cargos.forEach((cargo: any) => {
                if ((cargo.start >= this.value && cargo.end <= nextTick) || (cargo.start >= this.value && cargo.start < nextTick) || (cargo.end > this.value && cargo.end <= nextTick)) {
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
                '<div class="row">' +
                '<div class="col-md-12" style="text-align: center">' +
                '<span>' + quantity + ' BBLS</span>' +
                '</div>' +
                '</div>';

              return categoryLabel;
            },
          },
          offset: '0'
        },
        {
          visible: LoadingDischargingSequenceChartComponent.operation === OPERATIONS.LOADING,
          title: {
            text: `${LoadingDischargingSequenceChartComponent.operation === OPERATIONS.LOADING ? 'LOADING' : 'DISCHARGING'} RATE BBLS/HR`,
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
                const rate = LoadingDischargingSequenceChartComponent.cargoLoadingRates[equalIndex].join('/');

                const categoryLabel =
                  '<div class="row">' +
                  '<div class="col-md-12" style="text-align: center">' +
                  '<span>' + rate + ' BBLS/ HR</span>' +
                  '</div>' +
                  '</div>';

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
                text: `<div style="padding: 31px 16px; white-space: normal; text-align: center; border-right: 0; border-bottom: 0;">TANK NO.</div>`,
                useHTML: true,
                y: -73.5,
              },
              categories: this.cargoTankCategories.map(function (item) {
                return item.tankName;
              })
            },
            {
              title: {
                text: `<div style="padding: 31px 4.75px; white-space: normal; text-align: center; border-right: 0; border-bottom: 0;">CARGO GRADE</div>`,
                useHTML: true,
                y: -73.5,
              },
              categories: this.cargoTankCategories.map(function (item) {
                return item.id.toString();
              }),
              labels: {
                useHTML: true,
                formatter: function () {
                  let cargosLabel = '';
                  const cargos = [];

                  LoadingDischargingSequenceChartComponent.cargos.forEach((cargo: any) => {
                    if (cargo?.tankId === Number(this.value) && !cargos.some(item => item?.cargoNominationId === cargo?.cargoNominationId)) {
                      cargos.push({ cargoNominationId: cargo?.cargoNominationId, color: cargo?.color, abbreviation: cargo?.abbreviation });
                    }
                  });
                  cargos?.forEach(cargo => {
                    cargosLabel += '<span><span class="badge-custom mx-1" style="background-color: ' + cargo?.color + '"> ' + cargo?.abbreviation + '</span></span>';
                  });

                  const categoryLabel = cargosLabel;

                  return categoryLabel;
                },
              }
            },
            {
              title: {
                text: `<div style="padding: 38.25px 3px; white-space: normal; text-align: center; border-right: 0; border-bottom: 0;">ULLAGE</div>`,
                useHTML: true,
                y: -73.5
              },
              categories: this.cargoTankCategories.map(function (item) {
                return item.ullage.toString();
              })
            },
            {
              width: 200,
              categories: this.cargoTankCategories.map(function (item) {
                return item.quantity.toString();
              }),
              title: {
                text: `<div style="padding: 31px 0px; white-space: normal; text-align: center; border-right: 0; border-bottom: 0;">QTY BBLS</div>`,
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
        formatter: function () {
          let tooltipContent, cargoNames, duration, startingTime, endingTime, quantity, ullage, isCOW = false;
          if (this?.point.options.className === "pi-sort-up") {
            isCOW = true;
            tooltipContent = `COW ANGLE <br/> 150&deg`;
          } else {
            cargoNames = this?.point?.abbreviation;
            const min = this.series.xAxis.min;
            startingTime = (this?.point?.start - min) / (1000 * 60 * 60);
            endingTime = (this?.point?.end - min) / (1000 * 60 * 60);
            duration = (this?.point?.end - min) / (1000 * 60 * 60);
            quantity = this?.point?.quantity;
            ullage = this?.point?.ullage;
            tooltipContent = `<p>${cargoNames}</p><br/>
                  <p>HOURS <span>${duration}</span></p><br/>
                  <p>STARTING TIME <span>${startingTime}</span></p><br/>
                  <p>ENDING TIME <span>${endingTime}</span></p><br/>
                  <p>QUANTITY <span>${quantity}</span></p><br/>
                  <p>ULLAGE <span>${ullage}</span></p>`;
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
    this.cargoPumps.forEach((dataObj: any) => {
      const pumpIndex = this.cargoPumpCategories.findIndex(i => i?.id === dataObj.pumpId);
      cargoPumpSequenceSeriesData.push({
        pumpId: dataObj?.pumpId,
        start: dataObj?.start,
        end: dataObj?.end,
        className: dataObj?.className,
        pumpName: this.cargoPumpCategories[pumpIndex].pumpName,
        rate: dataObj?.rate,
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
      name: `${LoadingDischargingSequenceChartComponent.operation === OPERATIONS.LOADING ? 'LOADING' : 'DISCHARGING'} RATE`,
      custom: LoadingDischargingSequenceChartComponent.cargoStages,
      showInLegend: false,
      data: cargoPumpSequenceSeriesData
    }];

    this.cargoPumpSequenceGanttChart = {
      credits: {
        enabled: false
      },
      chart: {
        marginLeft: 240, // Keep all charts left aligned
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
              format: '<i class="pi {point.className}" style="color: #666666;font-size: 1.5em"></i>',
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
            text: LoadingDischargingSequenceChartComponent.operation === OPERATIONS.DISCHARGING ? `DISCHARGING RATE BBLS/HR` : null,
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
            enabled: LoadingDischargingSequenceChartComponent.operation === OPERATIONS.DISCHARGING,
            useHTML: true,
            formatter: function () {
              if (!this.isLast) {
                const stage = LoadingDischargingSequenceChartComponent.cargoStages.find((data: any) => data.start <= this.value && data.end >= this.value);
                const rate = stage?.rate;

                const categoryLabel =
                  '<div class="row">' +
                  '<div class="col-md-12" style="text-align: center">' +
                  '<span>Requested Max</span>' +
                  '<br/><span>' + rate + ' BBLS/ HR</span>' +
                  '</div>' +
                  '</div>';

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
                text: `<div style="padding: 31px 4.75px; white-space: normal; text-align: center; border-right: 0; border-bottom: 0;">CARGO PUMP</div>`,
                useHTML: true,
                y: -45,
              },
              categories: this.cargoPumpCategories.map(function (item) {
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
        formatter: function () {
          const pumpName = this?.point?.pumpName,
            seriesName = this?.series?.name,
            rate = this?.point?.rate;

          return `<p>${pumpName}</p><br/>
                  <p>${seriesName} <span>${rate}</span></p>`;

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
    LoadingDischargingSequenceChartComponent.ballasts.forEach((dataObj: any) => {
      const tankIndex = this.ballastTankCategories.findIndex(i => i?.id === dataObj.tankId);
      ballastSequenceSeriesData.push({
        tankId: dataObj?.tankId,
        start: dataObj?.start,
        end: dataObj?.end,
        className: dataObj?.className,
        tankName: this.ballastTankCategories[tankIndex].tankName,
        rate: dataObj?.rate,
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
      name: `${LoadingDischargingSequenceChartComponent.operation === OPERATIONS.LOADING ? 'LOADING' : 'DISCHARGING'} SEQUENCE`,
      custom: LoadingDischargingSequenceChartComponent.cargoStages,
      showInLegend: false,
      data: ballastSequenceSeriesData
    }];

    this.ballastSequenceGanttChart = {
      credits: {
        enabled: false
      },
      chart: {
        marginLeft: 240, // Keep all charts left aligned
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
              format: '<i class="pi {point.className}" style="color: #666666;font-size: 1.5em"></i>',
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
            text: `TOTAL`,
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
              LoadingDischargingSequenceChartComponent.ballasts.forEach((ballast: any) => {
                if ((ballast.start >= this.value && ballast.end <= nextTick) || (ballast.start >= this.value && ballast.start < nextTick) || (ballast.end > this.value && ballast.end <= nextTick)) {
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
                '<div class="row">' +
                '<div class="col-md-12" style="text-align: center;">' +
                '<span>' + quantity + ' BBLS</span>' +
                '</div>' +
                '</div>';

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
                text: `<div style="padding: 31px 4.75px; white-space: normal; text-align: center; border-right: 0; border-bottom: 0;">BALLAST</div>`,
                useHTML: true,
                y: -35,
              },
              categories: this.ballastTankCategories.map(function (item) {
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
        formatter: function () {
          const min = this.series.xAxis.min, startingTime = (this?.point?.start - min) / (1000 * 60 * 60),
            endingTime = (this?.point?.end - min) / (1000 * 60 * 60),
            duration = (this?.point?.end - min) / (1000 * 60 * 60),
            quantity = this?.point?.quantity,
            sounding = this?.point?.sounding,
            tooltipContent = `<p>HOURS <span>${duration}</span></p><br/>
                  <p>STARTING TIME <span>${startingTime}</span></p><br/>
                  <p>ENDING TIME <span>${endingTime}</span></p><br/>
                  <p>QUANTITY <span>${quantity}</span></p><br/>
                  <p>SOUNDING <span>${sounding}</span></p>`;

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

    this.ballastPumps?.forEach((dataObj: any) => {
      const pumpIndex = this.ballastPumpCategories.findIndex(i => i?.id === dataObj.pumpId);
      ballastPumpSequenceSeriesData.push({
        pumpId: dataObj?.pumpId,
        start: dataObj?.start,
        end: dataObj?.end,
        className: dataObj?.className,
        pumpName: this.ballastPumpCategories[pumpIndex]?.pumpName,
        rate: dataObj?.rate,
        id: dataObj?.id,
        color: dataObj?.id === 'gravity' || dataObj?.id === 'stripping' ? '#f8f8f8' : dataObj.color,
        y: pumpIndex,
        pointWidth: dataObj?.id === 'gravity' || dataObj?.id === 'stripping' ? 40 : 6,
        borderColor: dataObj?.id === 'gravity' || dataObj?.id === 'stripping' ? '#bebebe' : null,
        borderWidth: dataObj?.id === 'gravity' || dataObj?.id === 'stripping' ? 1 : 0,
        borderRadius: dataObj?.id === 'gravity' || dataObj?.id === 'stripping' ? 5 : 0,
      });
    });

    this.ballastPumpSequenceChartSeries = [{
      name: `${LoadingDischargingSequenceChartComponent.operation === OPERATIONS.LOADING ? 'LOADING' : 'DISCHARGING'} RATE`,
      custom: LoadingDischargingSequenceChartComponent.cargoStages,
      showInLegend: false,
      data: ballastPumpSequenceSeriesData
    }];

    this.ballastPumpSequenceGanttChart = {
      credits: {
        enabled: false
      },
      chart: {
        marginLeft: 240, // Keep all charts left aligned
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
                return this.point?.options?.id === 'gravity' ? 'GRAVITY' : undefined;
              },
              animation: {
                defer: 6000
              }
            },
            {
              enabled: true,
              format: '<i class="pi {point.className}" style="color: #666666;font-size: 1.5em"></i>',
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
                text: `<div style="padding: 31px 4.75px; white-space: normal; text-align: center; border-right: 0; border-bottom: 0;">BALLAST PUMP</div>`,
                useHTML: true,
                y: -45,
              },
              categories: this.ballastPumpCategories.map(function (item) {
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
        formatter: function () {
          const pumpName = this?.point?.pumpName,
            seriesName = this?.series?.name,
            rate = this?.point?.rate;

          return `<p>${pumpName}</p><br/>
                  <p>${seriesName} <span>${rate}</span></p>`;

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
    this.flowRateChartSeries = [
      {
        name: 'AGGREGATE',
        type: 'areaspline',
        custom: LoadingDischargingSequenceChartComponent.cargoStages,
        data: [],
        // step: 'left'
      },
      ...this.flowRates?.map(item => {
        return {
          name: item.tankName,
          type: 'line',
          custom: LoadingDischargingSequenceChartComponent.cargoStages,
          data: item.data,
          step: 'left'
        }
      })];


    this.flowRateAreaChart = {
      credits: {
        enabled: false
      },
      chart: {
        marginLeft: 240, // Keep all charts left aligned
        spacing: [0, 0, 0, 0],
        events: {
          load: this.makeSumSeries
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
          text: `FLOW RATE (BBLS/HR)`,
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
   * Set stability chart data
   *
   * @memberof LoadingDischargingSequenceChartComponent
   */
  setStabilityData() {
    this.stabilityChartSeries = [{
      yAxis: 0,
      type: 'areaspline',
      name: "FORE DRAFT",
      custom: {
        showFinalValue: true
      },
      data: this.stabilityParams.find(item => item.name === 'fore_draft')?.data,
    }, {
      yAxis: 0,
      type: 'areaspline',
      name: "AFT DRAFT",
      custom: {
        showFinalValue: true
      },
      data: this.stabilityParams.find(item => item.name === 'aft_draft')?.data,
    },
    {
      yAxis: 0,
      type: 'areaspline',
      name: "TRIM",
      custom: {
        showFinalValue: true
      },
      data: this.stabilityParams.find(item => item.name === 'trim')?.data,
    },
    {
      yAxis: 0,
      type: 'areaspline',
      name: "UKC",
      custom: {
        showFinalValue: true
      },
      data: this.stabilityParams.find(item => item.name === 'ukc')?.data,
    },
    {
      yAxis: 0,
      type: 'areaspline',
      name: "GM (M)",
      custom: {
        showFinalValue: true
      },
      data: this.stabilityParams.find(item => item.name === 'gm')?.data,
    },
    {
      yAxis: 0,
      type: 'areaspline',
      name: "MAX. SHEARING FORCE (FR.NO./%)",
      data: this.stabilityParams.find(item => item.name === 'sf')?.data,
    },
    {
      yAxis: 0,
      type: 'areaspline',
      name: "MAX. BENDING MOMENT (FR.NO./%)",
      data: this.stabilityParams.find(item => item.name === 'bm')?.data,
    }];
    this.stabilityGanttChart = {
      credits: {
        enabled: false
      },
      chart: {
        marginLeft: 240, // Keep all charts left aligned
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
            x: -237.5,
            y: 15,
            text: `DRAFT`,
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
              return number.toString();
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
      chart.xAxis[0].drawCrosshair(e);
      chart.series.forEach((p) => {
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
    const points = series.points,
      len = points.length,
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
        }).add();
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
          }).add();
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
    if (LoadingDischargingSequenceChartComponent.operation === OPERATIONS.DISCHARGING) {
      const cowLegend = `<ul class="list-group list-group-horizontal cow-legend">
                          <li class="list-group-item" style="background: none; border: none;">
                            <i class="pi pi-sort" style="color: #666666;font-size: 1.5em; padding-right: 5px; margin-top: -2px;"></i>
                            <span style="vertical-align: top;">FULL WASH</span>
                          </li>
                          <li class="list-group-item" style="background: none; border: none;">
                            <i class="pi pi-sort-down" style="color: #666666;font-size: 1.5em; padding-right: 5px;margin-top: -2px;"></i>
                            <span style="vertical-align: top;">TOP WASH</span>
                          </li>
                          <li class="list-group-item" style="background: none; border: none;">
                            <i class="pi pi-sort-up" style="color: #666666;font-size: 1.5em; padding-right: 5px; margin-top: -2px;"></i>
                            <span style="vertical-align: top;">BOTTOM
                            WASH</span> </li>
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
        if (p.name !== 'AGGREGATE' && p.visible === true) {
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

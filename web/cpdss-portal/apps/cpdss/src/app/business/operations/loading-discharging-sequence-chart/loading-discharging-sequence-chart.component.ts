import { Component, Input, OnInit } from '@angular/core';

import * as Highcharts from 'highcharts';
import Theme from 'highcharts/themes/grid-light';
import GanttChart from 'highcharts/modules/gantt';
import Annotations from 'highcharts/modules/annotations';
import { Commodity, Pump } from './loading-discharging-sequence-chart.model';
import { NgxSpinnerService } from 'ngx-spinner';
import { OPERATION } from '../models/operations.model';

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
  @Input() static operation: OPERATION = OPERATION.LOADING;

  // Static fields
  static stages = [
    { id: 1, name: 'Stage 1', start: Date.UTC(2014, 10, 17, 0), end: Date.UTC(2014, 10, 17, 12), rate: 25000, duration: '12 HRS' },
    { id: 2, name: 'Stage 2', start: Date.UTC(2014, 10, 17, 12), end: Date.UTC(2014, 10, 18, 4), rate: 25000, duration: '16 HRS' },
    { id: 3, name: 'Stage 3', start: Date.UTC(2014, 10, 18, 4), end: Date.UTC(2014, 10, 18, 12), rate: 25000, duration: '8 HRS' },
    { id: 4, name: 'Stage 4', start: Date.UTC(2014, 10, 18, 12), end: Date.UTC(2014, 10, 18, 20), rate: 25000, duration: '8 HRS' }
  ];
  static cargos: Commodity[] = [
    {
      id: 1,
      name: 'Cargo 1',
      color: '#236aff',
      abbreviation: 'KU',
      duration: '12 HRS',
      stageId: 1,
      data: [
        {
          tankId: 2,
          start: Date.UTC(2014, 10, 17, 0),
          end: Date.UTC(2014, 10, 17, 1),
          ullage: 2.3,
          quantity: 1856
        },
        {
          tankId: 2,
          start: Date.UTC(2014, 10, 17, 1),
          end: Date.UTC(2014, 10, 17, 4),
          ullage: 2.3,
          quantity: 1856
        },
        {
          tankId: 2,
          start: Date.UTC(2014, 10, 17, 4),
          end: Date.UTC(2014, 10, 17, 6),
          ullage: 2.3,
          quantity: 1856
        },
        {
          tankId: 2,
          start: Date.UTC(2014, 10, 17, 8),
          end: Date.UTC(2014, 10, 17, 12),
          ullage: 2.3,
          quantity: 1856,
        }]
    },
    {
      id: 2,
      name: 'Cargo 2',
      color: '#eaa15c',
      abbreviation: 'AEL',
      duration: '16 HRS',
      stageId: 2,
      data: [
        {
          tankId: 1,
          start: Date.UTC(2014, 10, 17, 13),
          end: Date.UTC(2014, 10, 17, 24),
          ullage: 2.3,
          quantity: 1856
        }, {
          tankId: 3,
          start: Date.UTC(2014, 10, 17, 15),
          end: Date.UTC(2014, 10, 17, 20),
          ullage: 2.3,
          quantity: 1856
        }, {
          tankId: 3,
          start: Date.UTC(2014, 10, 18, 0),
          end: Date.UTC(2014, 10, 18, 4),
          ullage: 2.3,
          quantity: 1856
        }]
    },
    {
      id: 3,
      name: 'Cargo 3',
      color: '#5d9ea0',
      abbreviation: 'OM',
      duration: '8 HRS',
      stageId: 3,
      data: [
        {
          tankId: 4,
          start: Date.UTC(2014, 10, 18, 4),
          end: Date.UTC(2014, 10, 18, 10),
          ullage: 2.3,
          quantity: 1856
        },
        {
          tankId: 4,
          start: Date.UTC(2014, 10, 18, 11),
          end: Date.UTC(2014, 10, 18, 11),
          className: 'pi-sort-up',
          ullage: 2.3,
          quantity: 1856
        }
      ]
    },
    {
      id: 4,
      name: 'Cargo 4',
      color: '#ff0095',
      abbreviation: 'AEH',
      duration: '8 HRS',
      stageId: 4,
      data: [
        {
          tankId: 5,
          start: Date.UTC(2014, 10, 18, 13),
          end: Date.UTC(2014, 10, 18, 18),
          ullage: 2.3,
          quantity: 1856
        }
      ]
    }
  ];
  static ballasts: Commodity[] = [
    {
      id: 6,
      name: 'Ballast',
      color: '#01717d',
      abbreviation: 'Ballast',
      stageId: 2,
      data: [
        {
          tankId: 2,
          start: Date.UTC(2014, 10, 17, 13),
          end: Date.UTC(2014, 10, 17, 16),
          quantity: 1856,
          ullage: 2.1,
          rate: 2000
        },
        {
          tankId: 2,
          start: Date.UTC(2014, 10, 17, 18),
          end: Date.UTC(2014, 10, 17, 22),
          quantity: 1856,
          ullage: 2.1,
          rate: 2000,
          id: 'stripping',
        }
      ]
    }, {
      id: 5,
      name: 'Ballast',
      stageId: 1,
      color: '#01717d',
      data: [
        {
          tankId: 1,
          start: Date.UTC(2014, 10, 17, 0),
          end: Date.UTC(2014, 10, 17, 6),
          quantity: 1856,
          ullage: 2.1,
          rate: 2000
        },
        {
          tankId: 3,
          start: Date.UTC(2014, 10, 17, 0),
          end: Date.UTC(2014, 10, 17, 12),
          quantity: 1856,
          ullage: 2.1,
          rate: 2000,
        }
      ]
    }
  ];

  // Public fileds
  Highcharts: typeof Highcharts = Highcharts;
  cargoSequenceGanttChart: Highcharts.Options;
  ballastSequenceGanttChart: Highcharts.Options;
  cargoPumpSequenceGanttChart: Highcharts.Options;
  ballastPumpSequenceGanttChart: Highcharts.Options;
  stabilityGanttChart: Highcharts.Options;
  flowRateAreaChart: Highcharts.Options;
  cargoPumps: Pump[] = [
    {
      id: 6,
      name: 'Cargo Pump',
      color: '#236aff',
      stageId: 1,
      data: [
        {
          pumpId: 1,
          start: Date.UTC(2014, 10, 17, 0),
          end: Date.UTC(2014, 10, 17, 8),
          rate: 1856,
        },
        {
          pumpId: 1,
          start: Date.UTC(2014, 10, 17, 0),
          end: Date.UTC(2014, 10, 17, 12),
          rate: 1856,
        }
      ]
    }, {
      id: 6,
      name: 'Cargo Pump',
      color: '#eaa15c',
      stageId: 2,
      data: [
        {
          pumpId: 2,
          start: Date.UTC(2014, 10, 17, 0),
          end: Date.UTC(2014, 10, 17, 16),
          rate: 1856,
        }
      ]
    }
  ];
  ballastPumps: Pump[] = [
    {
      id: 6,
      name: 'Ballast Pump',
      color: '#01717d',
      stageId: 1,
      data: [
        {
          pumpId: 1,
          start: Date.UTC(2014, 10, 17, 2),
          end: Date.UTC(2014, 10, 17, 16),
          rate: 1856,
        },
        {
          pumpId: 1,
          start: Date.UTC(2014, 10, 17, 0),
          end: Date.UTC(2014, 10, 17, 12),
          rate: 1856,
        }
      ]
    }, {
      id: 5,
      name: 'Ballast Pump',
      stageId: 1,
      color: '#01717d',
      data: [
        {
          pumpId: 3,
          start: Date.UTC(2014, 10, 17, 0),
          end: Date.UTC(2014, 10, 17, 6),
          rate: 1856,
        },
        {
          pumpId: 2,
          start: Date.UTC(2014, 10, 17, 0),
          end: Date.UTC(2014, 10, 17, 12),
          rate: 1856,
        }
      ]
    }
  ];
  cargoSequenceChartSeries: Array<any>;
  ballastSequenceChartSeries: Array<any>;
  cargoPumpSequenceChartSeries: Array<any>;
  ballastPumpSequenceChartSeries: Array<any>;
  stabilityChartSeries: Array<any>;
  flowRateChartSeries: Array<any>;
  cargoTankCategories: Array<any> = [];
  ballastTankCategories: Array<any> = [];
  cargoPumpCategories: Array<any> = [];
  ballastPumpCategories: Array<any> = [];
  flowRateChartTankCategories: Array<any> = [];

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

  constructor(private ngxSpinnerService: NgxSpinnerService) { }


  ngOnInit(): void {
    this.ngxSpinnerService.show();
    this.cargoTankCategories = [{ id: 1, tankNo: '1C' }, { id: 2, tankNo: '2C' }, { id: 3, tankNo: '3C' }, { id: 4, tankNo: '4C' }, { id: 5, tankNo: '5C' }, { id: 6, tankNo: '1P' }, { id: 7, tankNo: '1S' }];
    this.ballastTankCategories = [{ id: 1, tankNo: 'FPT', type: 'Ballast' }, { id: 2, tankNo: '1PS', type: 'Ballast' }, { id: 3, tankNo: '2PS', type: 'Ballast' }, { id: 4, tankNo: '3PS', type: 'Ballast' }];
    this.cargoPumpCategories = [{ id: 1, pumpNo: 'NO. 1COP', type: 'Cargo Pump' }, { id: 2, pumpNo: 'NO. 2COP', type: 'Cargo Pump' }, { id: 3, pumpNo: 'NO. 3COP', type: 'Cargo Pump' }, { id: 4, pumpNo: 'TCP', type: 'Cargo Pump' }];
    this.ballastPumpCategories = [{ id: 1, pumpNo: 'NO. 1WBP', type: 'Ballast Pump' }, { id: 2, pumpNo: 'NO. 2WBP', type: 'Ballast Pump' }, { id: 3, pumpNo: 'GS PUMP', type: 'Ballast Pump' }];
    this.flowRateChartTankCategories = [{ id: 1, tankNo: '1C', type: 'Cargo Pump' }, { id: 2, tankNo: '2C', type: 'Cargo Pump' }, { id: 3, tankNo: '3C', type: 'Ballast' }, { id: 4, tankNo: '4C', type: 'Ballast' }];

    const cargoSequenceSeriesData = [];
    const cargoPumpSequenceSeriesData = [];
    const ballastSequenceSeriesData = [];
    const ballastPumpSequenceSeriesData = [];

    LoadingDischargingSequenceChartComponent.stages.map(stage => {
      const cargos = LoadingDischargingSequenceChartComponent.cargos.filter(cargo => cargo.stageId === stage.id);
      cargos.forEach((cargoData) => {
        cargoData?.data?.forEach((dataObj: any) => {
          const tankIndex = this.cargoTankCategories.findIndex(i => i?.id === dataObj.tankId);
          cargoSequenceSeriesData.push({
            tankId: dataObj?.tankId,
            start: dataObj?.start,
            end: dataObj?.end,
            className: dataObj?.className,
            tankNo: this.cargoTankCategories[tankIndex].tankNo,
            ullage: dataObj?.ullage,
            quantity: dataObj?.quantity,
            id: dataObj?.id,
            color: dataObj?.id === 'stripping' ? '#d1d1d1' : cargoData.color,
            abbreviation: cargoData.abbreviation,
            y: tankIndex,
            pointWidth: dataObj?.id === 'stripping' ? 40 : 6,
            borderColor: dataObj?.id === 'stripping' ? 'grey' : null,
          });
        });
      });

      const cargoPumbs = this.cargoPumps.filter(item => item.stageId === stage.id);
      cargoPumbs.forEach((item) => {
        item?.data?.forEach((dataObj: any) => {
          const pumpIndex = this.cargoPumpCategories.findIndex(i => i?.id === dataObj.pumpId);
          cargoPumpSequenceSeriesData.push({
            pumpId: dataObj?.pumpId,
            start: dataObj?.start,
            end: dataObj?.end,
            className: dataObj?.className,
            pumpNo: this.cargoPumpCategories[pumpIndex].pumpNo,
            rate: dataObj?.rate,
            id: dataObj?.id,
            color: dataObj?.id === 'stripping' ? '#d1d1d1' : item.color,
            y: pumpIndex,
            pointWidth: dataObj?.id === 'stripping' ? 40 : 6,
            borderColor: dataObj?.id === 'stripping' ? 'grey' : null,
          });
        });
      });

      const ballasts = LoadingDischargingSequenceChartComponent.ballasts.filter(item => item.stageId === stage.id);
      ballasts.forEach((item) => {
        item?.data?.forEach((dataObj: any) => {
          const tankIndex = this.ballastTankCategories.findIndex(i => i?.id === dataObj.tankId);
          ballastSequenceSeriesData.push({
            tankId: dataObj?.tankId,
            start: dataObj?.start,
            end: dataObj?.end,
            className: dataObj?.className,
            tankNo: this.ballastTankCategories[tankIndex].tankNo,
            rate: dataObj?.rate,
            id: dataObj?.id,
            color: dataObj?.id === 'stripping' ? '#d1d1d1' : item.color,
            abbreviation: item.abbreviation,
            y: tankIndex,
            pointWidth: dataObj?.id === 'stripping' ? 40 : 6,
            borderColor: dataObj?.id === 'stripping' ? 'grey' : null,
          });
        });
      });

      const ballastPumbs = this.ballastPumps.filter(item => item.stageId === stage.id);
      ballastPumbs.forEach((item) => {
        item?.data?.forEach((dataObj: any) => {
          const pumpIndex = this.ballastPumpCategories.findIndex(i => i?.id === dataObj.pumpId);
          ballastPumpSequenceSeriesData.push({
            pumpId: dataObj?.pumpId,
            start: dataObj?.start,
            end: dataObj?.end,
            className: dataObj?.className,
            pumpNo: this.ballastPumpCategories[pumpIndex]?.pumpNo,
            rate: dataObj?.rate,
            id: dataObj?.id,
            color: dataObj?.id === 'stripping' ? '#d1d1d1' : item.color,
            y: pumpIndex,
            pointWidth: dataObj?.id === 'stripping' ? 40 : 6,
            borderColor: dataObj?.id === 'stripping' ? 'grey' : null,
          });
        });
      });
    });

    this.cargoSequenceChartSeries = [{
      name: `${LoadingDischargingSequenceChartComponent.operation === OPERATION.LOADING ? 'LOADING' : 'DISCHARGING'} SEQUENCE`,
      custom: LoadingDischargingSequenceChartComponent.stages,
      showInLegend: false,
      data: cargoSequenceSeriesData
    }];

    this.cargoPumpSequenceChartSeries = [{
      name: `${LoadingDischargingSequenceChartComponent.operation === OPERATION.LOADING ? 'LOADING' : 'DISCHARGING'} RATE`,
      custom: LoadingDischargingSequenceChartComponent.stages,
      showInLegend: false,
      data: cargoPumpSequenceSeriesData
    }];

    this.ballastSequenceChartSeries = [{
      name: `${LoadingDischargingSequenceChartComponent.operation === OPERATION.LOADING ? 'LOADING' : 'DISCHARGING'} SEQUENCE`,
      custom: LoadingDischargingSequenceChartComponent.stages,
      showInLegend: false,
      data: ballastSequenceSeriesData
    }];

    this.ballastPumpSequenceChartSeries = [{
      name: `${LoadingDischargingSequenceChartComponent.operation === OPERATION.LOADING ? 'LOADING' : 'DISCHARGING'} RATE`,
      custom: LoadingDischargingSequenceChartComponent.stages,
      showInLegend: false,
      data: ballastPumpSequenceSeriesData
    }];

    this.stabilityChartSeries = [{
      yAxis: 0,
      type: 'areaspline',
      name: "FORE DRAFT",
      custom: {
        showFinalValue: true
      },
      data: [Date.UTC(2014, 10, 17, 0), Date.UTC(2014, 10, 17, 6), Date.UTC(2014, 10, 17, 12), Date.UTC(2014, 10, 17, 20), Date.UTC(2014, 10, 18, 4), Date.UTC(2014, 10, 18, 8), Date.UTC(2014, 10, 18, 12), Date.UTC(2014, 10, 18, 16), Date.UTC(2014, 10, 18, 20)].map(v => [v, Math.floor(Math.random() * 11)]),
    }, {
      yAxis: 0,
      type: 'areaspline',
      name: "AFT DRAFT",
      custom: {
        showFinalValue: true
      },
      data: [Date.UTC(2014, 10, 17, 0), Date.UTC(2014, 10, 17, 6), Date.UTC(2014, 10, 17, 12), Date.UTC(2014, 10, 17, 20), Date.UTC(2014, 10, 18, 4), Date.UTC(2014, 10, 18, 8), Date.UTC(2014, 10, 18, 12), Date.UTC(2014, 10, 18, 16), Date.UTC(2014, 10, 18, 20)].map(v => [v, Math.floor(Math.random() * 11)]),
    },
    {
      yAxis: 0,
      type: 'areaspline',
      name: "TRIM",
      custom: {
        showFinalValue: true
      },
      data: [Date.UTC(2014, 10, 17, 0), Date.UTC(2014, 10, 17, 6), Date.UTC(2014, 10, 17, 12), Date.UTC(2014, 10, 17, 20), Date.UTC(2014, 10, 18, 4), Date.UTC(2014, 10, 18, 8), Date.UTC(2014, 10, 18, 12), Date.UTC(2014, 10, 18, 16), Date.UTC(2014, 10, 18, 20)].map(v => [v, Math.floor(Math.random() * 11)]),
    },
    {
      yAxis: 0,
      type: 'areaspline',
      name: "UKC",
      custom: {
        showFinalValue: true
      },
      data: [Date.UTC(2014, 10, 17, 0), Date.UTC(2014, 10, 17, 6), Date.UTC(2014, 10, 17, 12), Date.UTC(2014, 10, 17, 20), Date.UTC(2014, 10, 18, 4), Date.UTC(2014, 10, 18, 8), Date.UTC(2014, 10, 18, 12), Date.UTC(2014, 10, 18, 16), Date.UTC(2014, 10, 18, 20)].map(v => [v, Math.floor(Math.random() * 11)]),
    },
    {
      yAxis: 0,
      type: 'areaspline',
      name: "GM (M)",
      custom: {
        showFinalValue: true
      },
      data: [Date.UTC(2014, 10, 17, 0), Date.UTC(2014, 10, 17, 6), Date.UTC(2014, 10, 17, 12), Date.UTC(2014, 10, 17, 20), Date.UTC(2014, 10, 18, 4), Date.UTC(2014, 10, 18, 8), Date.UTC(2014, 10, 18, 12), Date.UTC(2014, 10, 18, 16), Date.UTC(2014, 10, 18, 20)].map(v => [v, Math.floor(Math.random() * 11)]),
    },
    {
      yAxis: 0,
      type: 'areaspline',
      name: "MAX. SHEARING FORCE (FR.NO./%)",
      data: [Date.UTC(2014, 10, 17, 0), Date.UTC(2014, 10, 17, 6), Date.UTC(2014, 10, 17, 12), Date.UTC(2014, 10, 17, 20), Date.UTC(2014, 10, 18, 4), Date.UTC(2014, 10, 18, 8), Date.UTC(2014, 10, 18, 12), Date.UTC(2014, 10, 18, 16), Date.UTC(2014, 10, 18, 20)].map(v => [v, Math.floor(Math.random() * 11)]),
    },
    {
      yAxis: 0,
      type: 'areaspline',
      name: "MAX. BENDING MOMENT (FR.NO./%)",
      data: [Date.UTC(2014, 10, 17, 0), Date.UTC(2014, 10, 17, 6), Date.UTC(2014, 10, 17, 12), Date.UTC(2014, 10, 17, 20), Date.UTC(2014, 10, 18, 4), Date.UTC(2014, 10, 18, 8), Date.UTC(2014, 10, 18, 12), Date.UTC(2014, 10, 18, 16), Date.UTC(2014, 10, 18, 20)].map(v => [v, Math.floor(Math.random() * 11)]),
    }];

    this.flowRateChartSeries = [{
      name: 'AGGREGATE',
      type: 'areaspline',
      custom: LoadingDischargingSequenceChartComponent.stages,
      data: []
    }, ...this.flowRateChartTankCategories.map(item => {
      return {
        name: item.tankNo,
        type: 'spline',
        custom: LoadingDischargingSequenceChartComponent.stages,
        data: [Date.UTC(2014, 10, 17, 0), Date.UTC(2014, 10, 17, 6), Date.UTC(2014, 10, 17, 12), Date.UTC(2014, 10, 17, 20), Date.UTC(2014, 10, 18, 4), Date.UTC(2014, 10, 18, 8), Date.UTC(2014, 10, 18, 12), Date.UTC(2014, 10, 18, 16), Date.UTC(2014, 10, 18, 20)].map(v => [v, Math.floor(Math.random() * 11)])
      }
    })];
    this.setChartData();
    this.ngxSpinnerService.hide();

  }

  /**
   * Set chart data
   *
   * @memberof LoadingDischargingSequenceChartComponent
   */
  setChartData() {
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
          tickInterval: 3600,
          tickLength: 0,
          gridLineWidth: 1,
          gridLineColor: '#bebebe',
          min: Date.UTC(2014, 10, 17, 0),
          max: Date.UTC(2014, 10, 18, 21),
          dateTimeLabelFormats: {
            hour: '%H'
          },
          plotLines: [{
            value: Date.UTC(2014, 10, 17, 6),
            color: '#000d20',
            width: 1,
            dashStyle: 'Dash',
            zIndex: 1
          },
          {
            value: Date.UTC(2014, 10, 17, 20),
            color: '#000d20',
            width: 1,
            dashStyle: 'Dash',
            zIndex: 1
          },
          {
            value: Date.UTC(2014, 10, 18, 8),
            color: '#000d20',
            width: 1,
            dashStyle: 'Dash',
            zIndex: 1
          },
          {
            value: Date.UTC(2014, 10, 18, 16),
            color: '#000d20',
            width: 1,
            dashStyle: 'Dash',
            zIndex: 1
          }
          ]
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
          tickPositions: [Date.UTC(2014, 10, 17, 0), Date.UTC(2014, 10, 17, 12), Date.UTC(2014, 10, 18, 4), Date.UTC(2014, 10, 18, 12), Date.UTC(2014, 10, 18, 20)],
          tickColor: '#000d20',
          tickLength: 50,
          margin: 10,
          labels: {
            useHTML: true,
            formatter: function () {
              if (!this.isLast) {
                const equalIndex = this.axis.tickPositions.findIndex(value => value === this.value);
                const nextTick = this.axis.tickPositions[equalIndex + 1];
                let cargosLabel = '';
                LoadingDischargingSequenceChartComponent.cargos.forEach((cargo: any) => {
                  if (cargo.data.some(item => item.start >= this.value && item.end <= nextTick)) {
                    cargosLabel += '<span><span class="badge-custom mx-1" style="background-color: ' + cargo?.color + '"> ' + cargo?.abbreviation + '</span>' + cargo?.name + '</span>';
                  }
                });

                const stage = LoadingDischargingSequenceChartComponent.stages.find((data: any) => data.start <= this.value && data.end > this.value);

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
          tickPositions: [Date.UTC(2014, 10, 17, 0), Date.UTC(2014, 10, 17, 12), Date.UTC(2014, 10, 18, 4), Date.UTC(2014, 10, 18, 12), Date.UTC(2014, 10, 18, 20)],
          tickColor: '#000d20',
          tickLength: 80,
          labels: {
            enabled: false
          },
        },
        {
          opposite: false,
          title: {
            text: `TOTAL:20,21,154 BBLS`,
          },
          lineColor: '#bebebe',
          lineWidth: 1,
          grid: {
            enabled: true,
            borderWidth: 0
          },
          tickPositions: [Date.UTC(2014, 10, 17, 0), Date.UTC(2014, 10, 17, 6), Date.UTC(2014, 10, 17, 12), Date.UTC(2014, 10, 17, 20), Date.UTC(2014, 10, 18, 4), Date.UTC(2014, 10, 18, 8), Date.UTC(2014, 10, 18, 12), Date.UTC(2014, 10, 18, 16), Date.UTC(2014, 10, 18, 20)],
          labels: {
            useHTML: true,
            formatter: function () {
              const equalIndex = this.axis.tickPositions.findIndex(value => value === this.value);
              const nextTick = this.axis.tickPositions[equalIndex + 1];
              let quantity = 0;
              LoadingDischargingSequenceChartComponent.cargos.forEach((cargo: any) => {
                cargo.data.forEach(item => {
                  if (item.start <= this.value && item.end <= nextTick) {
                    quantity += item.quantity;
                  }
                });
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
          visible: LoadingDischargingSequenceChartComponent.operation === OPERATION.LOADING,
          title: {
            text: `${LoadingDischargingSequenceChartComponent.operation === OPERATION.LOADING ? 'LOADING' : 'DISCHARGING'} RATE BBLS/HR`,
          },
          grid: {
            enabled: true,
            // borderWidth: 0
          },
          opposite: false,
          lineWidth: 0,
          lineColor: 'transparent',
          tickPositions: [Date.UTC(2014, 10, 17, 0), Date.UTC(2014, 10, 17, 12), Date.UTC(2014, 10, 18, 4), Date.UTC(2014, 10, 18, 12), Date.UTC(2014, 10, 18, 20)],
          tickColor: '#000d20',
          offset: '30',
          labels: {
            useHTML: true,
            formatter: function () {
              if (!this.isLast) {
                const stage = LoadingDischargingSequenceChartComponent.stages.find((data: any) => data.start <= this.value && data.end >= this.value);
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
      ],
      // TODO: needed in future
      /* annotations: [{
        labels: [{
          point: 'stripping',
          text: 'Stripping by Eductor',
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
                return item.tankNo;
              })
            },
            {
              title: {
                text: `<div style="padding: 31px 4.75px; white-space: normal; text-align: center; border-right: 0; border-bottom: 0;">CARGO GRADE</div>`,
                useHTML: true,
                y: -73.5,
              },
              categories: ['AEL', 'KU', 'AEL', 'OM', 'AEH'],
              labels: {
                useHTML: true,
                formatter: function () {
                  let cargosLabel = '';
                  LoadingDischargingSequenceChartComponent.cargos.forEach((cargo: any) => {
                    if (cargo.abbreviation === this.value) {
                      cargosLabel += '<span><span class="badge-custom mx-1" style="background-color: ' + cargo?.color + '"> ' + cargo?.abbreviation + '</span></span>';
                    }
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
              categories: ['2.3', '2.4', '2.6', '2.5', '2.1', '2.5', '2.2']
            }, {
              width: 200,
              categories: ['1.866', '1.857', '1.458', '1.058', '1.229', '1.928', '2.319'],
              title: {
                text: `<div style="padding: 31px 0px; white-space: normal; text-align: center; border-right: 0; border-bottom: 0;">QTY BBLS</div>`,
                useHTML: true,
                y: -73.5
              }
            }]
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
            duration = (this?.point?.end - this?.point?.start) / (1000 * 60 * 60);
            startingTime = Highcharts.dateFormat('%H', this?.point?.start);
            endingTime = Highcharts.dateFormat('%H', this?.point?.end);
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
          tickInterval: 3600,
          tickLength: 0,
          gridLineWidth: 1,
          gridLineColor: '#bebebe',
          min: Date.UTC(2014, 10, 17, 0),
          max: Date.UTC(2014, 10, 18, 21),
          dateTimeLabelFormats: {
            hour: '%H'
          },
          plotLines: [{
            value: Date.UTC(2014, 10, 17, 6),
            color: '#000d20',
            width: 1,
            dashStyle: 'Dash',
            zIndex: 1
          },
          {
            value: Date.UTC(2014, 10, 17, 20),
            color: '#000d20',
            width: 1,
            dashStyle: 'Dash',
            zIndex: 1
          },
          {
            value: Date.UTC(2014, 10, 18, 8),
            color: '#000d20',
            width: 1,
            dashStyle: 'Dash',
            zIndex: 1
          },
          {
            value: Date.UTC(2014, 10, 18, 16),
            color: '#000d20',
            width: 1,
            dashStyle: 'Dash',
            zIndex: 1
          }
          ]
        },
        {
          title: {
            text: LoadingDischargingSequenceChartComponent.operation === OPERATION.DISCHARGING ? `DISCHARGING RATE BBLS/HR` : null,
          },
          grid: {
            enabled: true,
          },
          lineWidth: 0,
          lineColor: 'transparent',
          gridZIndex: 3,
          gridLineColor: '#000d20',
          tickPositions: [Date.UTC(2014, 10, 17, 0), Date.UTC(2014, 10, 17, 12), Date.UTC(2014, 10, 18, 4), Date.UTC(2014, 10, 18, 12), Date.UTC(2014, 10, 18, 20)],
          tickColor: '#000d20',
          tickLength: 100,
          margin: 10,
          labels: {
            enabled: LoadingDischargingSequenceChartComponent.operation === OPERATION.DISCHARGING,
            useHTML: true,
            formatter: function () {
              if (!this.isLast) {
                const stage = LoadingDischargingSequenceChartComponent.stages.find((data: any) => data.start <= this.value && data.end >= this.value);
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
          tickPositions: [Date.UTC(2014, 10, 17, 0), Date.UTC(2014, 10, 17, 12), Date.UTC(2014, 10, 18, 4), Date.UTC(2014, 10, 18, 12), Date.UTC(2014, 10, 18, 20)],
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
                return item.pumpNo;
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
          const pumpNo = this?.point?.pumpNo,
            seriesName = this?.series?.name,
            rate = this?.point?.rate;

          return `<p>${pumpNo}</p><br/>
                  <p>${seriesName} <span>${rate}</span></p>`;

        },
      },
      series: this.cargoPumpSequenceChartSeries
    };

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
              formatter: function () {
                return this.point?.options?.id !== 'stripping' && !this.point?.options?.className ? this.point?.ullage : undefined;
              }
            },
            {
              enabled: true,
              formatter: function () {
                return this.point?.options?.id === 'stripping' ? 'Stripping by Eductor' : undefined;
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
          tickInterval: 3600,
          tickLength: 0,
          gridLineWidth: 1,
          gridLineColor: '#bebebe',
          min: Date.UTC(2014, 10, 17, 0),
          max: Date.UTC(2014, 10, 18, 21),
          dateTimeLabelFormats: {
            hour: '%H'
          },
          plotLines: [{
            value: Date.UTC(2014, 10, 17, 6),
            color: '#000d20',
            width: 1,
            dashStyle: 'Dash',
            zIndex: 1
          },
          {
            value: Date.UTC(2014, 10, 17, 20),
            color: '#000d20',
            width: 1,
            dashStyle: 'Dash',
            zIndex: 1
          },
          {
            value: Date.UTC(2014, 10, 18, 8),
            color: '#000d20',
            width: 1,
            dashStyle: 'Dash',
            zIndex: 1
          },
          {
            value: Date.UTC(2014, 10, 18, 16),
            color: '#000d20',
            width: 1,
            dashStyle: 'Dash',
            zIndex: 1
          }
          ]
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
            borderWidth: 0
          },
          tickPositions: [Date.UTC(2014, 10, 17, 0), Date.UTC(2014, 10, 17, 6), Date.UTC(2014, 10, 17, 12), Date.UTC(2014, 10, 17, 20), Date.UTC(2014, 10, 18, 4), Date.UTC(2014, 10, 18, 8), Date.UTC(2014, 10, 18, 12), Date.UTC(2014, 10, 18, 16), Date.UTC(2014, 10, 18, 20)],
          labels: {
            useHTML: true,
            formatter: function () {
              const equalIndex = this.axis.tickPositions.findIndex(value => value === this.value);
              const nextTick = this.axis.tickPositions[equalIndex + 1];
              let quantity = 0;
              LoadingDischargingSequenceChartComponent.ballasts.forEach((ballast: any) => {
                ballast.data.forEach(item => {
                  if (item.start <= this.value && item.end <= nextTick) {
                    quantity += item.quantity;
                  }
                });
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
          tickPositions: [Date.UTC(2014, 10, 17, 0), Date.UTC(2014, 10, 17, 12), Date.UTC(2014, 10, 18, 4), Date.UTC(2014, 10, 18, 12), Date.UTC(2014, 10, 18, 20)],
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
          tickPositions: [Date.UTC(2014, 10, 17, 0), Date.UTC(2014, 10, 17, 12), Date.UTC(2014, 10, 18, 4), Date.UTC(2014, 10, 18, 12), Date.UTC(2014, 10, 18, 20)],
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
                return item.tankNo;
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
          const tankNo = this?.point?.tankNo,
            seriesName = this?.series?.name,
            rate = this?.point?.rate;

          return `<p>${tankNo}</p><br/>
                  <p>${seriesName} <span>${rate}</span></p>`;

        },
      },
      series: this.ballastSequenceChartSeries
    };

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
              formatter: function () {
                return this.point?.options?.id !== 'stripping' && !this.point?.options?.className ? this.point?.ullage : undefined;
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
          tickInterval: 3600,
          tickLength: 0,
          gridLineWidth: 1,
          gridLineColor: '#bebebe',
          min: Date.UTC(2014, 10, 17, 0),
          max: Date.UTC(2014, 10, 18, 21),
          dateTimeLabelFormats: {
            hour: '%H'
          },
          plotLines: [{
            value: Date.UTC(2014, 10, 17, 6),
            color: '#000d20',
            width: 1,
            dashStyle: 'Dash',
            zIndex: 1
          },
          {
            value: Date.UTC(2014, 10, 17, 20),
            color: '#000d20',
            width: 1,
            dashStyle: 'Dash',
            zIndex: 1
          },
          {
            value: Date.UTC(2014, 10, 18, 8),
            color: '#000d20',
            width: 1,
            dashStyle: 'Dash',
            zIndex: 1
          },
          {
            value: Date.UTC(2014, 10, 18, 16),
            color: '#000d20',
            width: 1,
            dashStyle: 'Dash',
            zIndex: 1
          }
          ]
        },
        {
          grid: {
            enabled: true,
          },
          lineWidth: 0,
          lineColor: 'transparent',
          gridZIndex: 3,
          gridLineColor: '#000d20',
          tickPositions: [Date.UTC(2014, 10, 17, 0), Date.UTC(2014, 10, 17, 12), Date.UTC(2014, 10, 18, 4), Date.UTC(2014, 10, 18, 12), Date.UTC(2014, 10, 18, 20)],
          tickColor: '#000d20',
          tickLength: 50,
          margin: 10,
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
          tickPositions: [Date.UTC(2014, 10, 17, 0), Date.UTC(2014, 10, 17, 12), Date.UTC(2014, 10, 18, 4), Date.UTC(2014, 10, 18, 12), Date.UTC(2014, 10, 18, 20)],
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
                text: `<div style="padding: 31px 4.75px; white-space: normal; text-align: center; border-right: 0; border-bottom: 0;">BALLAST PUMP</div>`,
                useHTML: true,
                y: -45,
              },
              categories: this.ballastPumpCategories.map(function (item) {
                return item.pumpNo;
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
          const pumpNo = this?.point?.pumpNo,
            seriesName = this?.series?.name,
            rate = this?.point?.rate;

          return `<p>${pumpNo}</p><br/>
                  <p>${seriesName} <span>${rate}</span></p>`;

        },
      },
      series: this.ballastPumpSequenceChartSeries
    };

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
        spline: {
          marker: {
            enabled: false
          }
        }
      },
      xAxis: [
        {
          lineColor: '#bebebe',
          lineWidth: 1,
          startOnTick: true,
          crosshair: {
            color: 'red',
            snap: false
          },
          opposite: true,
          type: 'datetime',
          labels: {
            align: 'center',
            formatter: function (y) {
              const hours = (1000 * 60 * 60),
                number = (Number(this.value) - this.axis.min) / (hours);
              return number.toString();
            }
          },
          tickInterval: 3600,
          tickLength: 0,
          // tickColor: '#bebebe',
          gridLineColor: '#bebebe',
          min: Date.UTC(2014, 10, 17, 0),
          max: Date.UTC(2014, 10, 18, 21),
          dateTimeLabelFormats: {
            hour: '%H'
          },
          plotLines: [{
            value: Date.UTC(2014, 10, 17, 6),
            color: '#000d20',
            width: 1,
            dashStyle: 'Dash',
            zIndex: 1
          },
          {
            value: Date.UTC(2014, 10, 17, 20),
            color: '#000d20',
            width: 1,
            dashStyle: 'Dash',
            zIndex: 1
          },
          {
            value: Date.UTC(2014, 10, 18, 8),
            color: '#000d20',
            width: 1,
            dashStyle: 'Dash',
            zIndex: 1
          },
          {
            value: Date.UTC(2014, 10, 18, 16),
            color: '#000d20',
            width: 1,
            dashStyle: 'Dash',
            zIndex: 1
          }
          ]
        },
        {
          lineWidth: 0,
          startOnTick: true,
          opposite: true,
          tickPositions: [Date.UTC(2014, 10, 17, 0), Date.UTC(2014, 10, 17, 12), Date.UTC(2014, 10, 18, 4), Date.UTC(2014, 10, 18, 12), Date.UTC(2014, 10, 18, 20)],
          tickColor: '#000d20',
          tickLength: 20,
          margin: 10,
          gridLineColor: '#000d20',
          min: Date.UTC(2014, 10, 17, 0),
          max: Date.UTC(2014, 10, 18, 21),
          labels: {
            enabled: false
          }
        },
      ],
      yAxis: [{
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
          startOnTick: true,
          events: {
            setExtremes: this.syncExtremes
          },
          type: 'datetime',
          lineWidth: 0,
          labels: {
            enabled: false,
          },
          tickInterval: 3600,
          tickLength: 0,
          tickColor: '#bebebe',
          gridLineWidth: 0,
          top: 13,
          min: Date.UTC(2014, 10, 17, 0),
          max: Date.UTC(2014, 10, 18, 21),
          dateTimeLabelFormats: {
            hour: '%H'
          }
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
          startOnTick: true,
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
          tickPositions: [Date.UTC(2014, 10, 17, 0), Date.UTC(2014, 10, 17, 6), Date.UTC(2014, 10, 17, 12), Date.UTC(2014, 10, 17, 20), Date.UTC(2014, 10, 18, 4), Date.UTC(2014, 10, 18, 8), Date.UTC(2014, 10, 18, 12), Date.UTC(2014, 10, 18, 16), Date.UTC(2014, 10, 18, 20)],
          tickLength: 20,
          tickColor: '#bebebe',
          gridLineWidth: 0,
          top: 24,
          min: Date.UTC(2014, 10, 17, 0),
          max: Date.UTC(2014, 10, 18, 21),
          dateTimeLabelFormats: {
            hour: '%H'
          }
        },
        {
          lineWidth: 0,
          startOnTick: true,
          opposite: true,
          top: 39,
          tickPositions: [Date.UTC(2014, 10, 17, 0), Date.UTC(2014, 10, 17, 12), Date.UTC(2014, 10, 18, 4), Date.UTC(2014, 10, 18, 12), Date.UTC(2014, 10, 18, 20)],
          tickColor: '#000d20',
          tickPosition: 'inside',
          tickLength: 20,
          gridLineWidth: 0,
          min: Date.UTC(2014, 10, 17, 0),
          max: Date.UTC(2014, 10, 18, 21),
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
      e = chart.pointer.normalize(e); // Find coordinates within the chart
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
      range = 1000 * 60 * 60;
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
    if (LoadingDischargingSequenceChartComponent.operation === OPERATION.DISCHARGING) {
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
    let sum, flag;

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
      sum = 0;
      series.forEach(function (p, k) {
        if (p.name !== 'sum' && p.visible === true) {
          p.data.forEach(function (ob, j) {
            if (ob.x === x[i]) {
              sum += ob.y;
            }
          });
        }
      });
      series[0].addPoint({
        y: parseFloat(sum.toFixed(2)),
        x: x[i]
      }, false);
    }
    chart.redraw();
  }
}

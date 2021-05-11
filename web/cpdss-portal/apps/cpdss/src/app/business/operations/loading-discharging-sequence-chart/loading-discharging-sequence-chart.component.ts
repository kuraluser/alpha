import { Component, OnInit } from '@angular/core';

import * as Highcharts from 'highcharts';
import Theme from 'highcharts/themes/grid-light';
import GanttChart from 'highcharts/modules/gantt';
import Annotations from 'highcharts/modules/annotations';
import { Commodity } from './loading-discharging-sequence-chart.model';
import { NgxSpinnerService } from 'ngx-spinner';

/**
 * Override the reset function, we don't need to hide the tooltips and
 * crosshairs.
 */
Highcharts.Pointer.prototype.reset = function () {
  return undefined;
};

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
      color: '#00f',
      abbreviation: 'C1',
      duration: '12 HRS',
      stageId: 1,
      data: [
        {
          tankId: 2,
          start: Date.UTC(2014, 10, 17, 0),
          end: Date.UTC(2014, 10, 17, 1),
          tankNo: 'Tank 2',
          ullage: 2.3,
          quantity: 1856
        },
        {
          tankId: 2,
          start: Date.UTC(2014, 10, 17, 1),
          end: Date.UTC(2014, 10, 17, 4),
          tankNo: 'Tank 2',
          ullage: 2.3,
          quantity: 1856
        },
        {
          tankId: 2,
          start: Date.UTC(2014, 10, 17, 4),
          end: Date.UTC(2014, 10, 17, 6),
          tankNo: 'Tank 2',
          ullage: 2.3,
          quantity: 1856
        },
        {
          tankId: 2,
          start: Date.UTC(2014, 10, 17, 8),
          end: Date.UTC(2014, 10, 17, 12),
          id: 'stripping',
          tankNo: 'Tank 2',
          ullage: 2.3,
          quantity: 1856,
        }]
    },
    {
      id: 2,
      name: 'Cargo 2',
      color: '#ff0',
      abbreviation: 'C2',
      duration: '16 HRS',
      stageId: 2,
      data: [
        {
          tankId: 1,
          start: Date.UTC(2014, 10, 17, 13),
          end: Date.UTC(2014, 10, 17, 24),
          tankNo: 'Tank 1',
          ullage: 2.3,
          quantity: 1856
        }, {
          tankId: 3,
          start: Date.UTC(2014, 10, 17, 15),
          end: Date.UTC(2014, 10, 17, 20),
          tankNo: 'Tank 3',
          ullage: 2.3,
          quantity: 1856
        }, {
          tankId: 3,
          start: Date.UTC(2014, 10, 18, 0),
          end: Date.UTC(2014, 10, 18, 4),
          tankNo: 'Tank 3',
          ullage: 2.3,
          quantity: 1856
        }]
    },
    {
      id: 3,
      name: 'Cargo 3',
      color: '#f00',
      abbreviation: 'C3',
      duration: '8 HRS',
      stageId: 3,
      data: [
        {
          tankId: 4,
          start: Date.UTC(2014, 10, 18, 4),
          end: Date.UTC(2014, 10, 18, 10),
          tankNo: 'Tank 4',
          ullage: 2.3,
          quantity: 1856
        },
        {
          tankId: 4,
          start: Date.UTC(2014, 10, 18, 11),
          end: Date.UTC(2014, 10, 18, 11),
          className: 'pi-sort-up',
          tankNo: 'Tank 4',
          ullage: 2.3,
          quantity: 1856
        }
      ]
    },
    {
      id: 4,
      name: 'Cargo 4',
      color: '#00FF00',
      abbreviation: 'C4',
      duration: '8 HRS',
      stageId: 4,
      data: [
        {
          tankId: 5,
          start: Date.UTC(2014, 10, 18, 13),
          end: Date.UTC(2014, 10, 18, 18),
          tankNo: 'Tank 5',
          ullage: 2.3,
          quantity: 1856
        }
      ]
    },
    /* TODO: For ballast tanks {
      id: 5,
      name: 'Ballast',
      color: '#0ff',
      abbreviation: 'BALLAST',
      duration: '8 HRS',
      stageId: 1,
      data: [
        {
          tankId: 6,
          start: Date.UTC(2014, 10, 17, 0),
          end: Date.UTC(2014, 10, 17, 12),
          tankNo: 'Tank 6',
          ullage: 2.3,
          quantity: 1856
        },
        {
          tankId: 7,
          start: Date.UTC(2014, 10, 17, 12),
          end: Date.UTC(2014, 10, 17, 16),
          tankNo: 'Tank 7',
          ullage: 2.3,
          quantity: 1856
        }
      ]
    } */
  ];
  Highcharts: typeof Highcharts = Highcharts;
  sequenceGanttChart: Highcharts.Options;
  rateGanttChart: Highcharts.Options;
  stabilityGanttChart: Highcharts.Options;
  flowRateAreaChart: Highcharts.Options;
  loadingRates: Commodity[] = [
    {
      id: 6,
      name: 'Cargo Pump',
      color: '#0ff',
      abbreviation: 'Cargo Pump',
      stageId: 1,
      data: [
        {
          tankId: 1,
          start: Date.UTC(2014, 10, 17, 0),
          end: Date.UTC(2014, 10, 17, 8),
          tankNo: 'Tank 1',
          rate: 1856,
        },
        {
          tankId: 2,
          start: Date.UTC(2014, 10, 17, 0),
          end: Date.UTC(2014, 10, 17, 12),
          tankNo: 'Tank 2',
          rate: 1856,
        }
      ]
    }, {
      id: 6,
      name: 'Cargo Pump',
      color: '#0ff',
      abbreviation: 'Cargo Pump',
      stageId: 2,
      data: [
        {
          tankId: 1,
          start: Date.UTC(2014, 10, 17, 13),
          end: Date.UTC(2014, 10, 17, 16),
          tankNo: 'Tank 1',
          rate: 1856,
        }
      ]
    }, {
      id: 5,
      name: 'Ballast',
      stageId: 1,
      data: [
        {
          tankId: 3,
          start: Date.UTC(2014, 10, 17, 0),
          end: Date.UTC(2014, 10, 17, 6),
          tankNo: 'Tank 3',
          rate: 1856,
        },
        {
          tankId: 4,
          start: Date.UTC(2014, 10, 17, 0),
          end: Date.UTC(2014, 10, 17, 12),
          tankNo: 'Tank 4',
          rate: 1856,
        }
      ]
    }
  ];
  sequenceChartSeries: Array<any>;
  rateChartSeries: Array<any>;
  stabilityChartSeries: Array<any>;
  flowRateChartSeries: Array<any>;
  sequenceChartTankCategories: Array<any> = [];
  rateChartTankCategories: Array<any> = [];
  flowRateChartTankCategories: Array<any> = [];

  /**
   * Draw a single line in the table
   *
   * @memberof LoadingDischargingSequenceChartComponent
   */
  static tableLine(renderer, x1, y1, x2, y2) {
    renderer.path(['M', x1, y1, 'L', x2, y2])
      .attr({
        stroke: '#797f87',
        'stroke-width': 1
      })
      .add();
  }

  constructor(private ngxSpinnerService: NgxSpinnerService) { }


  ngOnInit(): void {
    this.ngxSpinnerService.show();
    this.sequenceChartTankCategories = [{ id: 1, tankNo: 'Tank 1' }, { id: 2, tankNo: 'Tank 2' }, { id: 3, tankNo: 'Tank 3' }, { id: 4, tankNo: 'Tank 4' }, { id: 5, tankNo: 'Tank 5' }, { id: 6, tankNo: 'Tank 6' }, { id: 7, tankNo: 'Tank 7' }];
    this.rateChartTankCategories = [{ id: 1, tankNo: 'Tank 1', type: 'Cargo Pump' }, { id: 2, tankNo: 'Tank 2', type: 'Cargo Pump' }, { id: 3, tankNo: 'Tank 3', type: 'Ballast' }, { id: 4, tankNo: 'Tank 4', type: 'Ballast' }];
    this.flowRateChartTankCategories = [{ id: 1, tankNo: 'Tank 1', type: 'Cargo Pump' }, { id: 2, tankNo: 'Tank 2', type: 'Cargo Pump' }, { id: 3, tankNo: 'Tank 3', type: 'Ballast' }, { id: 4, tankNo: 'Tank 4', type: 'Ballast' }];

    const sequenceSeriesData = [];
    const rateSeriesData = [];

    LoadingDischargingSequenceChartComponent.stages.map(stage => {
      const cargos = LoadingDischargingSequenceChartComponent.cargos.filter(cargo => cargo.stageId === stage.id);
      cargos.forEach((cargoData) => {
        cargoData?.data?.forEach((dataObj: any) => {
          sequenceSeriesData.push({
            tankId: dataObj?.tankId,
            start: dataObj?.start,
            end: dataObj?.end,
            className: dataObj?.className,
            tankNo: dataObj?.tankNo,
            ullage: dataObj?.ullage,
            quantity: dataObj?.quantity,
            id: dataObj?.id,
            color: dataObj?.id === 'stripping' ? '#666666' : cargoData.color,
            abbreviation: cargoData.abbreviation,
            y: this.sequenceChartTankCategories.findIndex(i => i?.id === dataObj.tankId),
            pointWidth: dataObj?.id === 'stripping' ? 40 : 6,
            borderColor: dataObj?.id === 'stripping' ? 'grey' : null,
          });
        });
      });
      const loadingRates = this.loadingRates.filter(item => item.stageId === stage.id);
      loadingRates.forEach((item) => {
        item?.data?.forEach((dataObj: any) => {
          rateSeriesData.push({
            tankId: dataObj?.tankId,
            start: dataObj?.start,
            end: dataObj?.end,
            className: dataObj?.className,
            tankNo: dataObj?.tankNo,
            rate: dataObj?.rate,
            id: dataObj?.id,
            color: dataObj?.id === 'stripping' ? '#666666' : item.color,
            abbreviation: item.abbreviation,
            y: this.sequenceChartTankCategories.findIndex(i => i?.id === dataObj.tankId),
            pointWidth: dataObj?.id === 'stripping' ? 40 : 6,
            borderColor: dataObj?.id === 'stripping' ? 'grey' : null,
          });
        });
      });
    });

    this.sequenceChartSeries = [{
      name: 'Loading Sequence',
      custom: LoadingDischargingSequenceChartComponent.stages,
      showInLegend: false,
      data: sequenceSeriesData
    }];


    this.rateChartSeries = [{
      name: 'Loading Rate',
      custom: LoadingDischargingSequenceChartComponent.stages,
      data: rateSeriesData
    }];

    this.stabilityChartSeries = this.loadingRates.map(item => {
      return {
        name: 'Loading Rate',
        custom: item,
        data: item.data?.map((dataObj: any) => {
          return {
            tankId: dataObj?.tankId,
            start: dataObj?.start,
            end: dataObj?.end,
            className: dataObj?.className,
            tankNo: dataObj?.tankNo,
            ullage: dataObj?.ullage,
            quantity: dataObj?.quantity,
            id: dataObj?.id,
            color: dataObj?.id === 'stripping' ? '#666666' : item.color,
            abbreviation: item.abbreviation,
            y: this.rateChartTankCategories.findIndex(i => i?.id === dataObj.tankId),
            pointWidth: dataObj?.id === 'stripping' ? 40 : 6,
            borderColor: dataObj?.id === 'stripping' ? 'grey' : null,
          };
        })
      }
    });

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
    this.sequenceGanttChart = {
      chart: {
        marginLeft: 240, // Keep all charts left aligned
        spacing: [20, 20, 20, 20],
        events: {
          render() {
            const chart = this;
            chart.renderer.text(`<ul class="list-group list-group-horizontal">
  <li class="list-group-item" style="background: none; border: none;"> <i class="pi pi-sort" style="color: #666666;font-size: 1.5em; padding-right: 5px; margin-top: -2px;"></i><span style="vertical-align: top;">FULL WASH</span>
  </li>
  <li class="list-group-item" style="background: none; border: none;"> <i class="pi pi-sort-down" style="color: #666666;font-size: 1.5em; padding-right: 5px;margin-top: -2px;"></i><span style="vertical-align: top;">TOP WASH</span>
  </li>
  <li class="list-group-item" style="background: none; border: none;"> <i class="pi pi-sort-up" style="color: #666666;font-size: 1.5em; padding-right: 5px; margin-top: -2px;"></i><span style="vertical-align: top;">BOTTOM
    WASH</span> </li>
</ul>`, chart.plotLeft, chart.chartHeight - 20, true).attr({
              cursor: 'pointer',
            }).add()
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
                return this.point?.options?.id !== 'stripping' && !this.point?.options?.className ? this.point?.ullage : undefined;
              }
            },
            {
              enabled: true,
              align: 'center',
              overflow: 'justify',
              verticalAlign: 'middle',
              useHTML: true,
              formatter: function () {
                return this.point?.options?.id === 'stripping' ? 'Stripping by Eductor' : undefined;
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
          lineColor: '#797f87',
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
          gridLineColor: '#797f87',
          min: Date.UTC(2014, 10, 17, 0),
          max: Date.UTC(2014, 10, 18, 21),
          dateTimeLabelFormats: {
            hour: '%H'
          },
          plotLines: [{
            value: Date.UTC(2014, 10, 17, 6),
            color: '#000d20',
            width: 1,
            dashStyle: 'ShortDash',
            zIndex: 1
          },
          {
            value: Date.UTC(2014, 10, 17, 20),
            color: '#000d20',
            width: 1,
            dashStyle: 'ShortDash',
            zIndex: 1
          },
          {
            value: Date.UTC(2014, 10, 18, 8),
            color: '#000d20',
            width: 1,
            dashStyle: 'ShortDash',
            zIndex: 1
          },
          {
            value: Date.UTC(2014, 10, 18, 16),
            color: '#000d20',
            width: 1,
            dashStyle: 'ShortDash',
            zIndex: 1
          }
          ]
        },
        {
          opposite: false,
          title: {
            align: 'middle',
            offset: 0,
            rotation: 0,
            x: 4.75,
            y: -18,
            text: `<div style="padding: 13px 52px;text-align: left;">Total:2,21,154 BBLS</div>`,
            useHTML: true,
          },
          lineColor: '#797f87',
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
        }
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
          borderColor: '#797f87',
          columns: [
            {
              title: {
                text: `<div style="padding: 31px 16px; white-space: normal; text-align: center; border-right: 0; border-bottom: 0;">Tank No.</div>`,
                useHTML: true,
                y: -73.5,
              },
              categories: this.sequenceChartTankCategories.map(function (item) {
                return item.tankNo;
              })
            },
            {
              title: {
                text: `<div style="padding: 31px 4.75px; white-space: normal; text-align: center; border-right: 0; border-bottom: 0;">Cargo Grade</div>`,
                useHTML: true,
                y: -73.5,
              },
              categories: ['C2', 'C1', 'C2', 'C3', 'C4', 'BALLAST', 'BALLAST']
            },
            {
              title: {
                text: `<div style="padding: 38.25px 3px; white-space: normal; text-align: center; border-right: 0; border-bottom: 0;">Ullage</div>`,
                useHTML: true,
                y: -73.5
              },
              categories: ['2.3', '2.4', '2.6', '2.5', '2.1', '2.5', '2.2']
            }, {
              width: 200,
              categories: ['1.866', '1.857', '1.458', '1.058', '1.229', '1.928', '2.319'],
              title: {
                text: `<div style="padding: 31px 0px; white-space: normal; text-align: center; border-right: 0; border-bottom: 0;">Quantity BBLS</div>`,
                useHTML: true,
                y: -73.5
              }
            }]
        },
        gridLineColor: '#797f87'
      },
      tooltip: {
        borderColor: '#797f87',
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
      series: this.sequenceChartSeries

    };

    this.rateGanttChart = {
      chart: {
        marginLeft: 240, // Keep all charts left aligned
        spacing: [20, 20, 20, 20],
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
          title: {
            align: 'low',
            offset: 0,
            rotation: 0,
            x: -140,
            y: -60,
            text: `Loading Rate BBLS/HR`,
          },
          lineColor: '#797f87',
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
          gridLineColor: '#797f87',
          min: Date.UTC(2014, 10, 17, 0),
          max: Date.UTC(2014, 10, 18, 21),
          dateTimeLabelFormats: {
            hour: '%H'
          },
          plotLines: [{
            value: Date.UTC(2014, 10, 17, 6),
            color: '#000d20',
            width: 1,
            dashStyle: 'ShortDash',
            zIndex: 1
          },
          {
            value: Date.UTC(2014, 10, 17, 20),
            color: '#000d20',
            width: 1,
            dashStyle: 'ShortDash',
            zIndex: 1
          },
          {
            value: Date.UTC(2014, 10, 18, 8),
            color: '#000d20',
            width: 1,
            dashStyle: 'ShortDash',
            zIndex: 1
          },
          {
            value: Date.UTC(2014, 10, 18, 16),
            color: '#000d20',
            width: 1,
            dashStyle: 'ShortDash',
            zIndex: 1
          }
          ]
        },
        {
          lineColor: '#797f87',
          lineWidth: 1,
          opposite: false,
          title: {
            align: 'middle',
            offset: 0,
            rotation: 0,
            x: 4.75,
            y: -18,
            text: `<div style="padding: 13px 52px;text-align: left;">Total</div>`,
            useHTML: true,
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
                '<span>' + quantity + '</span>' +
                '</div>' +
                '</div>';

              return categoryLabel;
            },
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
        }
      ],
      yAxis: {
        type: 'category',
        grid: {
          enabled: true,
          borderColor: '#797f87',
          columns: [
            {
              categories: this.rateChartTankCategories.map(function (item) {
                return item.type;
              })
            },
            {
              categories: this.rateChartTankCategories.map(function (item) {
                return item.tankNo;
              })
            }]
        },
        gridLineColor: '#797f87'
      },
      tooltip: {
        // shared: true,
        borderColor: '#797f87',
        borderWidth: 1,
        borderRadius: 20,
        followPointer: true,
        formatter: function () {
          console.log(this);
          const tankNo = this?.point?.tankNo,
            seriesName = this?.series?.name,
            rate = this?.point?.rate;

          return `<p>${tankNo}</p><br/>
                  <p>${seriesName} <span>${rate}</span></p>`;

        },
      },
      series: this.rateChartSeries
    };

    this.flowRateAreaChart = {
      chart: {
        marginLeft: 240, // Keep all charts left aligned
        spacing: [20, 20, 20, 20],
        events: {
          load: this.makeSumSeries
        }
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
          lineColor: '#797f87',
          lineWidth: 1,
          title: {
            align: 'low',
            offset: 0,
            rotation: 0,
            x: -237.5,
            text: `FLOW RATE (BBLS/HR)`,
          },
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
          tickLength: 10,
          tickColor: '#797f87',
          gridLineColor: '#797f87',
          min: Date.UTC(2014, 10, 17, 0),
          max: Date.UTC(2014, 10, 18, 21),
          dateTimeLabelFormats: {
            hour: '%H'
          }
        }
      ],
      yAxis: [{
        id: 'yaxis-3',
        gridLineColor: '#797f87',
        minorGridLineWidth: 0,
        // offset: 0,
        title: {
          text: null
        }
      }],
      tooltip: {
        shared: true,
        borderColor: '#797f87',
        borderWidth: 1,
        borderRadius: 20,
        followPointer: true
      },
      series: this.flowRateChartSeries
    };

    this.stabilityGanttChart = {
      chart: {
        marginLeft: 240, // Keep all charts left aligned
        spacing: [20, 20, 20, 20],
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
          tickColor: '#797f87',
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
          tickLength: 10,
          tickColor: '#797f87',
          gridLineWidth: 0,
          top: 13,
          min: Date.UTC(2014, 10, 17, 0),
          max: Date.UTC(2014, 10, 18, 21),
          dateTimeLabelFormats: {
            hour: '%H'
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
        borderColor: '#797f87',
        borderWidth: 1,
        borderRadius: 20,
        followPointer: true
      },
      series: [{
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
      }]
    };
  }

  /**
   * Gantt chart call back method
   *
   * @param {Highcharts.Chart} chart
   * @memberof LoadingDischargingSequenceChartComponent
   */
  sequenceGanttChartCallback = (chart: Highcharts.Chart) => {
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
        if (points.length) {
          points.forEach((point) => {
            point.onMouseOver();
            chart.tooltip.refresh(points); // Show the tooltip
            chart.xAxis[0].drawCrosshair(e, points[0]); // Show the crosshair
          });
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
      if (serie.options?.custom?.showFinalValue) {
        renderer.text(
          serie.data[serie.data.length - 1].y, chart.plotLeft - 2 * tablePadding - cellPadding, tableTop + (serie_index + 2) * rowHeight - cellPadding).css({
            color: '#666666',
            align: 'right'
          }).add();
      }
    });

    // Render data points
    chart.xAxis[0].tickPositions.forEach((category, category_index) => {

      series.forEach((item, i) => {
        if (series[i].data[category_index]) {
          cellWidth = series[i].data[category_index].plotX;
          if (category_index !== 0) {
            renderer.text(series[i].data[category_index]?.y, chart.plotLeft + series[i].data[category_index].plotX, tableTop + (i + 2) * rowHeight - cellPadding).attr({
              align: 'center',
            }).css({
              color: '#666666'
            }).add();
          }

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
              tableTop + (series[i].data.length + 1) * rowHeight + cellPadding,
              chart.plotLeft + chart.plotWidth,
              tableTop + (series[i].data.length + 1) * rowHeight + cellPadding
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

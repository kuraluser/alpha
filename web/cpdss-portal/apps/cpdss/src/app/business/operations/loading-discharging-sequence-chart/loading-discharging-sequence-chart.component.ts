import { Component, OnInit } from '@angular/core';

import * as Highcharts from 'highcharts';
import Theme from 'highcharts/themes/grid-light';
import GanttChart from 'highcharts/modules/gantt';
import Annotations from 'highcharts/modules/annotations';

//TODO: to be removed to model file
/**
 * Interface for Cago data
 *
 * @interface Cargo
 */
interface Cargo {
  id: number,
  name: string,
  custom?: Object,
  color?: string,
  abbreviation: string,
  duration: string,
  label: number,
  label1: number,
  data?: Array<any>
}

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
  Highcharts: typeof Highcharts = Highcharts;
  sequenceGanttChart: Highcharts.Options;
  rateGanttChart: Highcharts.Options;
  cargos: Cargo[] = [
    {
      id: 1,
      name: 'Cargo 1',
      color: '#00f',
      abbreviation: 'C1',
      duration: '12 HRS',
      label: 1.2,
      label1: 11.30,
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
      label: 1.2,
      label1: 12.60,
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
      label: 1.2,
      label1: 11.50,
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
          className: 'pi-arrow-circle-down',
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
      label: 1.2,
      label1: 11.60,
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
    {
      id: 5,
      name: 'Ballast',
      color: '#0ff',
      abbreviation: 'BALLAST',
      duration: '8 HRS',
      label: 1.2,
      label1: 11.60,
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
    }
  ];
  series: Array<any>;
  tankCategories: Array<any> = [];

  constructor() { }

  ngOnInit(): void {
    this.tankCategories = [{ id: 1, tankNo: 'Tank 1' }, { id: 2, tankNo: 'Tank 2' }, { id: 3, tankNo: 'Tank 3' }, { id: 4, tankNo: 'Tank 4' }, { id: 5, tankNo: 'Tank 5' }, { id: 6, tankNo: 'Tank 6' }, { id: 7, tankNo: 'Tank 7' }];
    this.series = this.cargos.map(item => {
      return {
        id: item.id,
        name: item.name,
        custom: item,
        color: item.color,
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
            color: dataObj?.id === 'stripping' ? 'black' : item.color,
            abbreviation: item.abbreviation,
            y: this.tankCategories.findIndex(i => i?.id === dataObj.tankId),
            pointWidth: dataObj?.id === 'stripping' ? 40 : 6,
            borderColor: dataObj?.id === 'stripping' ? 'grey' : null,
          };
        })
      }
    });

    this.sequenceGanttChart = {
      chart: {
        events: {
          render() {
          }
        }
      },
      plotOptions: {
        gantt: {
          colorAxis: false,
          colorByPoint: false,
          pointWidth: 6,
          borderWidth: 0,
          borderRadius: 0,
        },
        series: {
          dataLabels: [{
            enabled: true,
            align: 'right',
            verticalAlign: 'bottom',
            formatter: function () {
              return this.point?.options?.id !== 'stripping' && !this.point?.options?.className ? this.series?.options?.custom?.label : undefined;
            }
          },
          {
            enabled: true,
            align: 'center',
            verticalAlign: 'bottom',
            formatter: function () {
              return this.point?.options?.id !== 'stripping' && !this.point?.options?.className ? this.series?.options?.custom?.label1 : undefined;
            }
          },
            {
              enabled: true,
              align: 'center',
              overflow: 'justify',
              verticalAlign: 'middle',
              padding: 2,
              formatter: function () {
                return this.point?.options?.id === 'stripping' ? 'Stripping by Eductor' : undefined;
              }
            },
          {
            enabled: true,
            format: '<i class="pi {point.className}" style="color: #000;font-size: 1.5em"></i>',
            useHTML: true,
            align: 'center'
          }]
        }
      },
      xAxis: [
        {
          events: {
            setExtremes: function (e) {
              const thisChart = this.chart;

              if (e.trigger !== "syncExtremes") {
                // Prevent feedback loop
                Highcharts.each(Highcharts.charts, function (chart) {
                  if (
                    chart !== thisChart
                  ) {
                    if (chart.xAxis[0].setExtremes) {
                      // It is null while updating
                      chart.xAxis[0].setExtremes(e.min, e.max, undefined, false, {
                        trigger: "syncExtremes"
                      });
                    }
                  }
                });
              }
            }
          },
          labels: {
            align: 'center',
            formatter: function (y) {
              const hours = (1000 * 60 * 60),
                number = (this.value - this.axis.min) / (hours);
              return number.toString();
            }
          },
          grid: {
            enabled: false
          },
          tickInterval: 3600 * 4,
          tickLength: 0,
          gridLineWidth: 1,
          gridLineColor: '#ffffff',
          min: Date.UTC(2014, 10, 17, 0),
          max: Date.UTC(2014, 10, 18, 20),
          dateTimeLabelFormats: {
            hour: '%H'
          },
          plotLines: [{
            value: Date.UTC(2014, 10, 17, 6),
            color: '#b1b1b1',
            width: 2,
            dashStyle: 'ShortDash',
            zIndex: 1
          },
          {
            value: Date.UTC(2014, 10, 17, 20),
            color: '#b1b1b1',
            width: 2,
            dashStyle: 'ShortDash',
            zIndex: 1
          },
          {
            value: Date.UTC(2014, 10, 18, 8),
            color: '#b1b1b1',
            width: 2,
            dashStyle: 'ShortDash',
            zIndex: 1
          },
          {
            value: Date.UTC(2014, 10, 18, 16),
            color: '#b1b1b1',
            width: 2,
            dashStyle: 'ShortDash',
            zIndex: 1
          }
          ]
        },
        {
          grid: {
            enabled: true,
            borderColor: "#fff",
            borderWidth: 2
          },
          gridZIndex: 3,
          gridLineColor: '#fff',
          gridLineWidth: 2,
          tickPositions: [Date.UTC(2014, 10, 17, 0), Date.UTC(2014, 10, 17, 12), Date.UTC(2014, 10, 18, 4), Date.UTC(2014, 10, 18, 12), Date.UTC(2014, 10, 18, 20)],
          // tickColor: '#fff',
          // tickPosition: 'outside',
          // tickLength: 0,
          // tickWidth: 2,
          margin: 10,
          labels: {
            useHTML: true,
            formatter: function () {
              if (!this.isLast) {
                const index = this.axis.tickPositions.findIndex(value => value === this.value);
                const item = this.chart?.series[index];

                const categoryLabel =
                  '<div class="row">' +
                  '<div class="col-md-12" style="text-align: center">' +
                  '<span><span class="badge-custom mx-1" style="background-color: ' + item?.options.custom?.color + '"> ' + item?.options.custom?.abbreviation + '</span>' + item?.name + '</span>' +
                  '<br/><span>' + item?.options?.custom?.duration + '</span>' +
                  '</div>' +
                  '</div>';

                return categoryLabel;
              }
            },
          },
        },
        {
          opposite: false,
          title: {
            align: 'middle',
            offset: 0,
            rotation: 0,
            x: 4.75,
            y: -18,
            text: `<div style="border: 2.5px solid transparent; padding: 13px 52px;text-align: left;">Total:2,21,154 BBLS</div>`,
            useHTML: true,
          },
          grid: {
            enabled: true,
            borderColor: "#fff",
            borderWidth: 2
          },
          // gridZIndex: 3,
          // gridLineColor: '#fff',
          // gridLineWidth: 2,
          tickPositions: [Date.UTC(2014, 10, 17, 0), Date.UTC(2014, 10, 17, 6), Date.UTC(2014, 10, 17, 12), Date.UTC(2014, 10, 17, 20), Date.UTC(2014, 10, 18, 4), Date.UTC(2014, 10, 18, 8), Date.UTC(2014, 10, 18, 12), Date.UTC(2014, 10, 18, 16), Date.UTC(2014, 10, 18, 20)],
          // tickColor: '#fff',
          // tickPosition: 'outside',
          // tickLength: 0,
          // tickWidth: 2,
          margin: 10,
          labels: {
            useHTML: true,
            formatter: function () {
              if (!this.isLast) {
                const equalIndex = this.chart.xAxis[1].tickPositions.findIndex(value => value === this.value);
                const nextIndex = this.chart.xAxis[1].tickPositions.findIndex(value => value > this.value);
                const item = this.chart?.series[equalIndex !== -1 ? equalIndex : nextIndex - 1];
                const categoryLabel =
                  '<div class="row">' +
                  '<div class="col-md-12" style="text-align: center">' +
                  '<span>' + item?.options?.custom?.label1 + '</span>' +
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
          borderColor: '#fff',
          borderWidth: 2,
          columns: [
            {
              title: {
                text: `<div style="border: 2.5px solid transparent; padding: 31px 16px; white-space: normal; text-align: center; border-right: 0; border-bottom: 0;">Tank No.</div>`,
                useHTML: true,
                y: -73.5,
              },
              categories: ['Tank 1', 'Tank 2', 'Tank 3', 'Tank 4', 'Tank 5', 'Tank 6', 'Tank 7']
            },
            {
              title: {
                text: `<div style="border: 2.5px solid transparent; padding: 31px 4.75px; white-space: normal; text-align: center; border-right: 0; border-bottom: 0;">Cargo Grade</div>`,
                useHTML: true,
                // x: 0,
                y: -73.5,
              },
              categories: ['C2', 'C1', 'C2', 'C3', 'C4', 'BALLAST', 'BALLAST']
            },
            {
              title: {
                text: `<div style="border: 2.5px solid transparent; padding: 38.25px 3px; white-space: normal; text-align: center; border-right: 0; border-bottom: 0;">Ullage</div>`,
                useHTML: true,
                y: -73.5
              },
              categories: ['2.3', '2.4', '2.6', '2.5', '2.1', '2.5', '2.2']
            }, {
              width: '50',
              categories: ['1.86', '1.85', '1.45', '1.05', '1.22', '1.92', '2.31'],
              title: {
                text: `<div style="border: 2.5px solid transparent; padding: 31px 0px; white-space: normal; text-align: center; border-right: 0; border-bottom: 0;">Quantity BBLS</div>`,
                useHTML: true,
                y: -73.5
              }
            }]
        },
      },
      tooltip: {
        // outside: true
      },
      series: this.series
    };


    this.rateGanttChart = {
      chart: {
        events: {
        }
      },
      plotOptions: {
        gantt: {
          colorAxis: false,
          colorByPoint: false,
          pointWidth: 6,
          borderWidth: 0,
          borderRadius: 0,
        },
        series: {
          dataLabels: [{
            enabled: true,
            align: 'right',
            verticalAlign: 'bottom',
            formatter: function () {
              return this.point?.options?.id !== 'stripping' && !this.point?.options?.className ? this.series?.options?.custom?.label : undefined;
            }
          },
          {
            enabled: true,
            align: 'center',
            verticalAlign: 'bottom',
            formatter: function () {
              return this.point?.options?.id !== 'stripping' && !this.point?.options?.className ? this.series?.options?.custom?.label1 : undefined;
            }
          },
          {
            enabled: true,
            format: '<i class="pi {point.className}" style="color: #000;font-size: 1.5em"></i>',
            useHTML: true,
            align: 'center'
          }]
        }
      },
      xAxis: [
        {
          events: {
            setExtremes: function (e) {
              const thisChart = this.chart;

              if (e.trigger !== "syncExtremes") {
                // Prevent feedback loop
                Highcharts.each(Highcharts.charts, function (chart) {
                  if (
                    chart !== thisChart
                  ) {
                    if (chart.xAxis[0].setExtremes) {
                      // It is null while updating
                      chart.xAxis[0].setExtremes(e.min, e.max, undefined, false, {
                        trigger: "syncExtremes"
                      });
                    }
                  }
                });
              }
            }
          },
          labels: {
            align: 'center',
            formatter: function (y) {
              const hours = (1000 * 60 * 60),
                number = (this.value - this.axis.min) / (hours);
              return number.toString();
            }
          },
          grid: {
            enabled: false
          },
          tickInterval: 3600 * 4,
          tickLength: 0,
          gridLineWidth: 1,
          gridLineColor: '#ffffff',
          min: Date.UTC(2014, 10, 17, 0),
          max: Date.UTC(2014, 10, 18, 20),
          dateTimeLabelFormats: {
            hour: '%H'
          },
          plotLines: [{
            value: Date.UTC(2014, 10, 17, 6),
            color: '#b1b1b1',
            width: 2,
            dashStyle: 'ShortDash',
            zIndex: 1
          },
          {
            value: Date.UTC(2014, 10, 17, 20),
            color: '#b1b1b1',
            width: 2,
            dashStyle: 'ShortDash',
            zIndex: 1
          },
          {
            value: Date.UTC(2014, 10, 18, 8),
            color: '#b1b1b1',
            width: 2,
            dashStyle: 'ShortDash',
            zIndex: 1
          },
          {
            value: Date.UTC(2014, 10, 18, 16),
            color: '#b1b1b1',
            width: 2,
            dashStyle: 'ShortDash',
            zIndex: 1
          }
          ]
        },
      ],
      yAxis: {
        type: 'category',
        grid: {
          enabled: true,
          borderColor: '#fff',
          borderWidth: 2,
          columns: [
            {
              title: {
                text: `<div style="border: 2.5px solid transparent; padding: 31px 16px; white-space: normal; text-align: center; border-right: 0; border-bottom: 0;">Tank No.</div>`,
                useHTML: true,
                y: -73.5,
              },
              categories: ['Tank 1', 'Tank 2', 'Tank 3', 'Tank 4', 'Tank 5', 'Tank 6', 'Tank 7']
            },
            {
              title: {
                text: `<div style="border: 2.5px solid transparent; padding: 31px 4.75px; white-space: normal; text-align: center; border-right: 0; border-bottom: 0;">Cargo Grade</div>`,
                useHTML: true,
                // x: 0,
                y: -73.5,
              },
              categories: ['C2', 'C1', 'C2', 'C3', 'C4', 'BALLAST', 'BALLAST']
            },
            {
              title: {
                text: `<div style="border: 2.5px solid transparent; padding: 38.25px 3px; white-space: normal; text-align: center; border-right: 0; border-bottom: 0;">Ullage</div>`,
                useHTML: true,
                y: -73.5
              },
              categories: ['2.3', '2.4', '2.6', '2.5', '2.1', '2.5', '2.2']
            }, {
              width: '50',
              categories: ['1.86', '1.85', '1.45', '1.05', '1.22', '1.92', '2.31'],
              title: {
                text: `<div style="border: 2.5px solid transparent; padding: 31px 0px; white-space: normal; text-align: center; border-right: 0; border-bottom: 0;">Quantity BBLS</div>`,
                useHTML: true,
                y: -73.5
              }
            }]
        },
      },
      tooltip: {
        // outside: true
      },
      series: this.series
    };
  }

  /**
   * Gantt chart call back method
   *
   * @param {Highcharts.Chart} chart
   * @memberof LoadingDischargingSequenceChartComponent
   */
  sequenceGanttChartCallback = (chart: Highcharts.Chart) => {
    // if (chart)
    //   console.log(chart)
  }

}

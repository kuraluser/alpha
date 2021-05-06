import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoadingDischargingSequenceChartComponent } from './loading-discharging-sequence-chart.component';
import { HighchartsChartModule } from 'highcharts-angular';

/**
 * Module for loading/ discharging sequence gantt chart component
 *
 * @export
 * @class LoadingDischargingSequenceChartModule
 */
@NgModule({
  declarations: [LoadingDischargingSequenceChartComponent],
  imports: [
    CommonModule,
    HighchartsChartModule
  ],
  exports: [
    LoadingDischargingSequenceChartComponent
  ]
})
export class LoadingDischargingSequenceChartModule { }

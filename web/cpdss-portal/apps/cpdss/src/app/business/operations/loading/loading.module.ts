import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LoadingComponent } from './loading.component';
import { LoadingSequenceComponent } from './loading-sequence/loading-sequence.component';
import { LoadingRoutingModule } from './loading-routing.module';
import { LoadingInformationComponent } from './loading-information/loading-information.component'
import {LoadingDischargingBerthModule} from './../loading-discharging-berth/loading-discharging-berth.module'
import {LoadingDischargingCargoDetailsModule} from './../loading-discharging-cargo-details/loading-discharging-cargo-details.module'
import {LoadingDischargingCargoMachineryModule} from './../loading-discharging-cargo-machinery/loading-discharging-cargo-machinery.module'
import {LoadingDischargingDetailsModule} from './../loading-discharging-details/loading-discharging-details.module'
import { InstructionSidePanelModule } from '../instruction-side-panel/instruction-side-panel.module';
import { InstructionCheckListModule } from '../instruction-check-list/instruction-check-list.module';
import { LoadingDischargingSequenceChartModule } from '../loading-discharging-sequence-chart/loading-discharging-sequence-chart.module';

/**
 * Module for loading operation
 *
 * @export
 * @class LoadingModule
 */
@NgModule({
  declarations: [
    LoadingComponent,
    LoadingInformationComponent,
	LoadingSequenceComponent
  ],
  imports: [
    CommonModule,
    LoadingRoutingModule,
    LoadingDischargingBerthModule,
    LoadingDischargingCargoDetailsModule,
    LoadingDischargingCargoMachineryModule,
    LoadingDischargingDetailsModule,
    InstructionSidePanelModule,
    InstructionCheckListModule,
    LoadingDischargingSequenceChartModule
  ]
})
export class LoadingModule { }

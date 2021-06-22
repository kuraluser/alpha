import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LoadingComponent } from './loading.component';
import { LoadingSequenceComponent } from './loading-sequence/loading-sequence.component';
import { LoadingRoutingModule } from './loading-routing.module';
import { DatatableModule } from '../../../shared/components/datatable/datatable.module';
import { InstructionSidePanelModule } from '../instruction-side-panel/instruction-side-panel.module';
import { InstructionCheckListModule } from '../instruction-check-list/instruction-check-list.module';
import { PlanSidePanelModule } from '../plan-side-panel/plan-side-panel.module';
import { ArrivalConditionModule } from '../arrival-condition/arrival-condition.module';
import { DepartureConditionModule } from '../departure-condition/departure-condition.module';
import { LoadingInformationComponent } from './loading-information/loading-information.component'
import { LoadingDischargingBerthModule } from './../loading-discharging-berth/loading-discharging-berth.module'
import { LoadingDischargingCargoDetailsModule } from './../loading-discharging-cargo-details/loading-discharging-cargo-details.module'
import { LoadingDischargingCargoMachineryModule } from './../loading-discharging-cargo-machinery/loading-discharging-cargo-machinery.module'
import { LoadingDischargingDetailsModule } from './../loading-discharging-details/loading-discharging-details.module'
import { LoadingDischargingSequenceChartModule } from '../loading-discharging-sequence-chart/loading-discharging-sequence-chart.module';
import { LoadingPlanComponent } from './loading-plan/loading-plan.component';
import { LoadingRateModule } from './../loading-rate/loading-rate.module';
import { TableModule } from 'primeng/table';
import { TranslateModule } from '@ngx-translate/core';
import { DropdownModule } from 'primeng/dropdown';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LoadingInstructionComponent } from './loading-instruction/loading-instruction.component';
import { ToppingOffTankTableModule } from './../topping-off-tank-table/topping-off-tank-table.module';
import { CheckboxModule } from 'primeng/checkbox';
import { LoadingInformationApiService } from '../services/loading-information-api.service';
import { LoadingDischargingManageSequenceModule } from '../loading-discharging-manage-sequence/loading-discharging-manage-sequence.module';

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
    LoadingSequenceComponent,
    LoadingPlanComponent,
    LoadingInstructionComponent
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
    DatatableModule,
    PlanSidePanelModule,
    ArrivalConditionModule,
    DepartureConditionModule,
    LoadingDischargingSequenceChartModule,
    LoadingRateModule,
    TableModule,
    TranslateModule,
    DropdownModule,
    FormsModule,
    ReactiveFormsModule,
    ToppingOffTankTableModule,
    CheckboxModule,
    LoadingDischargingManageSequenceModule

  ],
  providers: [LoadingInformationApiService]
})
export class LoadingModule { }

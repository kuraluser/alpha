import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DischargingComponent } from './discharging.component';
import { DischargingRoutingModule } from './discharging-routing.module';
import { InstructionSidePanelModule } from '../instruction-side-panel/instruction-side-panel.module';
import { InstructionCheckListModule } from '../instruction-check-list/instruction-check-list.module';
import { TranslateModule } from '@ngx-translate/core';
import { DischargingInformationComponent } from './discharging-information/discharging-information.component';
import { LoadingDischargingInformationApiService } from '../services/loading-discharging-information-api.service'
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LoadingDischargingDetailsModule } from '../loading-discharging-details/loading-discharging-details.module';
import { DischargingRatesModule } from '../discharging-rates/discharging-rates.module';
import { LoadingDischargingBerthModule } from '../loading-discharging-berth/loading-discharging-berth.module';
import { LoadingDischargingCargoDetailsModule } from '../loading-discharging-cargo-details/loading-discharging-cargo-details.module';
import { CargoToBeLoadedDischargedModule } from '../cargo-to-be-loaded-discharged/cargo-to-be-loaded-discharged.module';
import { DropdownModule } from 'primeng/dropdown';
import { LoadingDischargingManageSequenceModule } from '../loading-discharging-manage-sequence/loading-discharging-manage-sequence.module';
import { LoadingDischargingCargoMachineryModule } from '../loading-discharging-cargo-machinery/loading-discharging-cargo-machinery.module';
import { CowPlanModule } from '../cow-plan/cow-plan.module';
import { PostDischargeStageModule } from '../post-discharge-stage/post-discharge-stage.module';
import { DischargingInstructionComponent } from './discharging-instruction/discharging-instruction/discharging-instruction.component';
import { DischargingInstructionApiService } from '../services/discharging-instruction-api.service';
import { DischargingSequenceComponent } from './discharging-sequence/discharging-sequence.component';
import { LoadingDischargingSequenceChartModule } from '../loading-discharging-sequence-chart/loading-discharging-sequence-chart.module';
import { DischargingPlanComponent } from './discharging-plan/discharging-plan.component';
import { DischargingPlanApiService } from '../services/discharging-plan-api.service';
import { PlanSidePanelModule } from '../plan-side-panel/plan-side-panel.module';
import { ArrivalConditionModule } from '../arrival-condition/arrival-condition.module';
import { DepartureConditionModule } from '../departure-condition/departure-condition.module';
import { ErrorLogPopupModule } from '../../core/components/error-log-popup/error-log-popup.module';

/**
 * Module for discharging operations
 *
 * @export
 * @class DischargingModule
 */
@NgModule({
  declarations: [DischargingComponent, DischargingInformationComponent, DischargingInstructionComponent, DischargingSequenceComponent, DischargingPlanComponent],
  imports: [
    CommonModule,
    DischargingRoutingModule,
    InstructionSidePanelModule,
    InstructionCheckListModule,
    TranslateModule,
    FormsModule,
    ReactiveFormsModule,
    DropdownModule,
    LoadingDischargingDetailsModule,
    DischargingRatesModule,
    LoadingDischargingBerthModule,
    LoadingDischargingCargoDetailsModule,
    CargoToBeLoadedDischargedModule,
    LoadingDischargingManageSequenceModule,
    LoadingDischargingCargoMachineryModule,
    CowPlanModule,
    PlanSidePanelModule,
    ArrivalConditionModule,
    DepartureConditionModule,
    PostDischargeStageModule,
    LoadingDischargingSequenceChartModule,
    ErrorLogPopupModule
  ],
  providers: [LoadingDischargingInformationApiService, DischargingInstructionApiService, DischargingPlanApiService]
})
export class DischargingModule { }

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MultiSelectModule } from 'primeng/multiselect';
import { DropdownModule } from 'primeng/dropdown';
import { TranslateModule } from '@ngx-translate/core';
import { TooltipModule } from 'primeng/tooltip';

import { DischargePlanComponent } from './discharge-plan.component';
import { ViewPlanComponent } from './view-plan/view-plan.component';
import { DischargePlanRoutingModule } from './discharge-plan-routing.module';
import { DischargePlanApiService } from '../services/discharge-plan-api.service';
import { DischargeStudyViewPlanTransformationService } from '../services/discharge-study-view-plan-transformation.service';
import { QuantityPipeModule } from '../../../shared/pipes/quantity/quantity-pipe.module';
import { QuantityPipe } from '../../../shared/pipes/quantity/quantity.pipe'; 

import { DatatableModule } from '../../../shared/components/datatable/datatable.module';

/**
 * Routing Module for Discharge Study view plan Screen
 *
 * @export
 * @class DischargeStudyViewPlanModule
 */

@NgModule({
  declarations: [DischargePlanComponent,ViewPlanComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MultiSelectModule,
    TooltipModule,
    DropdownModule,
    TranslateModule,
    QuantityPipeModule,
    DatatableModule,
    DischargePlanRoutingModule
  ],
  providers: [
    QuantityPipe,
    DischargeStudyViewPlanTransformationService, 
    DischargePlanApiService
  ]
})
export class DischargePlanModule { }
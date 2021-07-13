import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MultiSelectModule } from 'primeng/multiselect';
import { DropdownModule } from 'primeng/dropdown';
import { TranslateModule } from '@ngx-translate/core';

import { DischargeStudyComponent } from './discharge-study.component';
import { ViewPlanComponent } from './view-plan/view-plan.component';
import { DischargeStudyViewPlanRoutingModule } from './discharge-study-view-plan-routing.module';
import { DischargeStudyViewPlanApiService } from '../services/discharge-study-view-plan-api.service';
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
  declarations: [DischargeStudyComponent,ViewPlanComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MultiSelectModule,
    DropdownModule,
    TranslateModule,
    QuantityPipeModule,
    DatatableModule,
    DischargeStudyViewPlanRoutingModule
  ],
  providers: [
    QuantityPipe,
    DischargeStudyViewPlanTransformationService, 
    DischargeStudyViewPlanApiService]
})
export class DischargeStudyViewPlanModule { }

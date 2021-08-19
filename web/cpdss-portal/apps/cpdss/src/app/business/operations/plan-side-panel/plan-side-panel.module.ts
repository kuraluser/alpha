import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PlanSidePanelComponent } from './plan-side-panel.component';
import { DatatableModule } from '../../../shared/components/datatable/datatable.module';
import { PlanSidePanelTransformationService } from './plan-side-panel-transformation.service';
import { LoadingRateModule } from './../loading-rate/loading-rate.module';
import { DepartureConditionPanelModule } from './../departure-condition-panel/departure-condition-panel.module';
import { PanelModule } from 'primeng/panel';
import { ArrivalConditionPanelModule } from './../arrival-condition-panel/arrival-condition-panel.module';
import { LoadingDischargingBerthModule } from '../loading-discharging-berth/loading-discharging-berth.module';
import { TranslateModule } from '@ngx-translate/core';

/**
 * Module for plan tab side panel
 *
 * @export
 * @class PlanSidePanelModule
 */
@NgModule({
  declarations: [PlanSidePanelComponent],
  imports: [
    CommonModule,
    DatatableModule,
    PanelModule,
    LoadingRateModule,
    DepartureConditionPanelModule,
    ArrivalConditionPanelModule,
    LoadingDischargingBerthModule,
    TranslateModule
  ],
  exports: [PlanSidePanelComponent],
  providers: [PlanSidePanelTransformationService]
})
export class PlanSidePanelModule { }

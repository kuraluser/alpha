import { NgModule } from '@angular/core';
import { CommonModule ,  DecimalPipe } from '@angular/common';

import { LoadablePlanComponent } from './loadable-plan.component';
import { LoadableQuantityComponent } from './loadable-quantity/loadable-quantity.component';
import { StowageComponent } from './stowage/stowage.component';
import { PortEtaEtdConditionComponent } from './port-eta-etd-condition/port-eta-etd-condition.component';
import { CommentsComponent } from './comments/comments.component';
import { LoadablePlanRoutingModule } from './loadable-plan-routing.module'
import { VesselInfoModule } from '../../core/components/vessel-info/vessel-info.module';
import { TranslateModule } from '@ngx-translate/core';
import { CommingledCargoDetailsComponent } from './loadable-quantity/commingled-cargo-details/commingled-cargo-details.component';
import { TableModule } from 'primeng/table';
import { CargoTankLayoutModule } from '../../core/components/cargo-tank-layout/cargo-tank-layout.module';
import { LoadableQuantityApiService } from '../services/loadable-quantity-api.service';
import { LoadablePlanTransformationService } from '../services/loadable-plan-transformation.service';
import { LoadablePlanApiService } from '../services/loadable-plan-api.service';

@NgModule({
  declarations: [LoadablePlanComponent, LoadableQuantityComponent, StowageComponent, PortEtaEtdConditionComponent, CommentsComponent, CommingledCargoDetailsComponent],
  imports: [
    CommonModule,
    LoadablePlanRoutingModule,
    TranslateModule,
    VesselInfoModule,
    TableModule,
    CargoTankLayoutModule
  ],
  providers: [LoadableQuantityApiService , DecimalPipe , LoadablePlanTransformationService , LoadablePlanApiService],
  exports: [
    LoadableQuantityComponent, StowageComponent, PortEtaEtdConditionComponent, CommentsComponent, CommingledCargoDetailsComponent
  ]
})
export class LoadablePlanModule { }

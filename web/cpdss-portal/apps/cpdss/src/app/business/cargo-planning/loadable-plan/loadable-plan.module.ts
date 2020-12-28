import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

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
  exports: [
    LoadableQuantityComponent, StowageComponent, PortEtaEtdConditionComponent, CommentsComponent, CommingledCargoDetailsComponent
  ]
})
export class LoadablePlanModule { }

import { NgModule } from '@angular/core';
import { CommonModule ,  DecimalPipe , DatePipe } from '@angular/common';

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
import { DatatableModule } from '../../../shared/components/datatable/datatable.module';
import { ReactiveFormsModule } from '@angular/forms';
import { BallastStowageComponent } from './stowage/ballast-stowage/ballast-stowage.component';
import { BallastLayoutModule } from '../../core/components/ballast-layout/ballast-layout.module';

@NgModule({
  declarations: [LoadablePlanComponent, LoadableQuantityComponent, StowageComponent, PortEtaEtdConditionComponent, CommentsComponent, CommingledCargoDetailsComponent, BallastStowageComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    LoadablePlanRoutingModule,
    TranslateModule,
    VesselInfoModule,
    TableModule,
    CargoTankLayoutModule,
    DatatableModule,
    BallastLayoutModule
  ],
  providers: [LoadableQuantityApiService , DecimalPipe , LoadablePlanTransformationService , LoadablePlanApiService , DatePipe ],
  exports: [
    LoadableQuantityComponent, StowageComponent, PortEtaEtdConditionComponent, CommentsComponent, CommingledCargoDetailsComponent
  ]
})
export class LoadablePlanModule { }

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
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BallastStowageComponent } from './stowage/ballast-stowage/ballast-stowage.component';
import { BallastLayoutModule } from '../../core/components/ballast-layout/ballast-layout.module';
import { SaveStowagePopupComponent } from './stowage/save-stowage-popup/save-stowage-popup.component';
import { DialogModule } from 'primeng/dialog';
import { ValidationErrorModule } from '../../../shared/components/validation-error/validation-error.module';
import { LoadableStudyListApiService } from '../services/loadable-study-list-api.service';
import { ErrorLogPopupModule } from '../../core/components/error-log-popup/error-log-popup.module'
import { TooltipModule } from 'primeng/tooltip';
import { QuantityDecimalFormatPipeModule } from '../../../shared/pipes/quantity-decimal-format/quantity-decimal-format.module';
import { QuantityDecimalFormatPipe } from '../../../shared/pipes/quantity-decimal-format/quantity-decimal-format.pipe'; 
import { PortRotationPopupModule } from '../../core/components/port-rotation-popup/port-rotation-popup.module';
import { UserRoleSelectionPopupComponent } from './user-role-selection-popup/user-role-selection-popup.component';
/**
 * Module for loadable plan
 *
 * @export
 * @class LoadablePlanModule
 */
@NgModule({
  declarations: [LoadablePlanComponent, LoadableQuantityComponent, StowageComponent, PortEtaEtdConditionComponent, CommentsComponent, CommingledCargoDetailsComponent, BallastStowageComponent, SaveStowagePopupComponent, UserRoleSelectionPopupComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    LoadablePlanRoutingModule,
    TranslateModule,
    VesselInfoModule,
    TableModule,
    CargoTankLayoutModule,
    DatatableModule,
    BallastLayoutModule,
    DialogModule,
    ErrorLogPopupModule,
    ValidationErrorModule,
    TooltipModule,
    QuantityDecimalFormatPipeModule,
    PortRotationPopupModule,
    FormsModule
  ],
  providers: [QuantityDecimalFormatPipe , LoadableQuantityApiService , DecimalPipe , LoadablePlanTransformationService , LoadablePlanApiService , DatePipe, LoadableStudyListApiService ],
  exports: [
    LoadableQuantityComponent, StowageComponent, PortEtaEtdConditionComponent, CommentsComponent, CommingledCargoDetailsComponent, UserRoleSelectionPopupComponent
  ]
})
export class LoadablePlanModule { }

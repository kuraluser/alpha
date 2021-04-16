import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { VoyageStatusComponent } from './voyage-status.component';
import { VoyageStatusRoutingModule } from './voyage-status-routing.module';
import { NewVoyagePopupModule } from '../core/components/new-voyage-popup/new-voyage-popup.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TranslateModule } from '@ngx-translate/core';
import { VoyageApiService } from './services/voyage-api.service';
import { VesselInfoModule } from '../core/components/vessel-info/vessel-info.module';
import { EditPortRotationPopupModule } from '../core/components/edit-port-rotation-popup/edit-port-rotation-popup.module';
import { ValidationErrorModule } from '../../shared/components/validation-error/validation-error.module';
import { PortRotationRibbonModule } from '../core/components/port-rotation-ribbon/port-rotation-ribbon.module';
import { PortRotationService } from '../core/services/port-rotation.service';
import { ShipLandingTanksComponent } from './ship-landing-tanks/ship-landing-tanks.component';
import { CargoTankLayoutModule } from '../core/components/cargo-tank-layout/cargo-tank-layout.module';
import { BallastLayoutModule } from '../core/components/ballast-layout/ballast-layout.module';
import { DropdownModule } from 'primeng/dropdown';
import { VoyageStatusTransformationService } from '../voyage-status/services/voyage-status-transformation.service'
import { BunkeringLayoutModule } from '../core/components/bunkering-layout/bunkering-layout.module';
import {TableModule} from 'primeng/table';
import { ParameterListComponent } from './parameter-list/parameter-list.component';
import { CargoDetailsComponent } from './cargo-details/cargo-details.component';
import { QuantityPipe } from '../../shared/pipes/quantity/quantity.pipe';
import { DatatableModule } from '../../shared/components/datatable/datatable.module';
import {InputSwitchModule} from 'primeng/inputswitch';
import { UnitDropdownModule } from '../../shared/components/unit-dropdown/unit-dropdown.module';
import { QuantityPipeModule } from '../../shared/pipes/quantity/quantity-pipe.module';
import { DraftConditionComponent } from './draft-condition/draft-condition.component';
import { ListConditionComponent } from './list-condition/list-condition.component';
import { TrimConditionComponent } from './trim-condition/trim-condition.component';
import { TooltipModule } from 'primeng/tooltip';

/**
 * Module for new voyage-status
 */

@NgModule({
  declarations: [VoyageStatusComponent, ParameterListComponent, CargoDetailsComponent, ShipLandingTanksComponent, DraftConditionComponent, ListConditionComponent, TrimConditionComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    ValidationErrorModule,
    TranslateModule,
    VesselInfoModule,
    VoyageStatusRoutingModule,
    CargoTankLayoutModule,
    BallastLayoutModule,
    DropdownModule,
    BunkeringLayoutModule,
    TableModule,
    DatatableModule,
    InputSwitchModule,
    UnitDropdownModule,
    QuantityPipeModule,
    TooltipModule,
    PortRotationRibbonModule,
    NewVoyagePopupModule,
    EditPortRotationPopupModule
  ],
  providers: [
    VoyageApiService,
    VoyageStatusTransformationService,
    QuantityPipe,
    PortRotationService
  ]
})
export class VoyageStatusModule { }

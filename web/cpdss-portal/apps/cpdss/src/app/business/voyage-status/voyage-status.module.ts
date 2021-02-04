import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { VoyageStatusComponent } from './voyage-status.component';
import { VoyageStatusRoutingModule } from './voyage-status-routing.module';
import { DialogModule } from 'primeng/dialog';
import { NewVoyagePopupComponent } from './new-voyage-popup/new-voyage-popup.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TranslateModule } from '@ngx-translate/core';
import { VoyageApiService } from './services/voyage-api.service';
import { VesselInfoModule } from '../core/components/vessel-info/vessel-info.module';
import { EditPortRotationComponent } from './edit-port-rotation/edit-port-rotation.component';
import { CalendarModule } from 'primeng/calendar';
import { ValidationErrorModule } from '../../shared/components/validation-error/validation-error.module';
import { PortRotationRibbonComponent } from './port-rotation-ribbon/port-rotation-ribbon.component';
import { CarouselModule } from 'primeng/carousel';
import { OrderListModule } from 'primeng/orderlist';
import { EditPortRotationApiService } from './services/edit-port-rotation-api.service';
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

/**
 * Module for new voyage-status
 */

@NgModule({
  declarations: [VoyageStatusComponent, NewVoyagePopupComponent, EditPortRotationComponent,PortRotationRibbonComponent, ParameterListComponent, CargoDetailsComponent, ShipLandingTanksComponent, DraftConditionComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    ValidationErrorModule,
    DialogModule,
    TranslateModule,
    VesselInfoModule,
    VoyageStatusRoutingModule,
    CalendarModule,
    OrderListModule,
    CargoTankLayoutModule,
    BallastLayoutModule,
    CarouselModule,
    DropdownModule,
    BunkeringLayoutModule,
    TableModule,
    DatatableModule,
    InputSwitchModule,
    UnitDropdownModule,
    QuantityPipeModule
  ],
  providers: [
    VoyageApiService,
    EditPortRotationApiService,
    VoyageStatusTransformationService,
    QuantityPipe
  ]
})
export class VoyageStatusModule { }

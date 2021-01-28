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
import { DropdownModule } from 'primeng/dropdown';
import { VoyageStatusTransformationService } from '../voyage-status/services/voyage-status-transformation.service'

/**
 * Module for new voyage-status
 */

@NgModule({
  declarations: [VoyageStatusComponent, NewVoyagePopupComponent, EditPortRotationComponent, PortRotationRibbonComponent],
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
    CarouselModule,
    OrderListModule,
    DropdownModule
  ],
  providers: [
    VoyageApiService,
    EditPortRotationApiService,
    VoyageStatusTransformationService
  ]
})
export class VoyageStatusModule { }

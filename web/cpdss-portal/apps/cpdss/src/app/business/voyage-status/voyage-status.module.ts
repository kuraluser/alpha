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
import { CalendarModule } from 'primeng/calendar';
import { ValidationErrorModule } from '../../shared/components/validation-error/validation-error.module';

/**
 * Module for new voyage-status
 */

@NgModule({
  declarations: [VoyageStatusComponent, NewVoyagePopupComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    ValidationErrorModule,
    DialogModule,
    TranslateModule,
    VesselInfoModule,
    VoyageStatusRoutingModule,
    CalendarModule
  ],
  providers: [
    VoyageApiService
  ]
})
export class VoyageStatusModule { }

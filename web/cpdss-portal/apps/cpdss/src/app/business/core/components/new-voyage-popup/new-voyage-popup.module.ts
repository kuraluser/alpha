import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NewVoyagePopupComponent } from './new-voyage-popup.component';
import { TranslateModule } from '@ngx-translate/core';
import { CalendarModule } from 'primeng/calendar';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ValidationErrorModule } from '../../../../shared/components/validation-error/validation-error.module';
import { DialogModule } from 'primeng/dialog';
import { DropdownModule } from 'primeng/dropdown';
import { VoyageService } from '../../services/voyage.service';
import { FocusTrapModule } from 'primeng/focustrap';

/**
 *  Module for port ribbon component
 */
@NgModule({
  declarations: [NewVoyagePopupComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    ValidationErrorModule,
    CalendarModule,
    DialogModule,
    DropdownModule,
    TranslateModule,
    FocusTrapModule
  ],
  providers: [VoyageService],
  exports: [NewVoyagePopupComponent]
})
export class NewVoyagePopupModule { }

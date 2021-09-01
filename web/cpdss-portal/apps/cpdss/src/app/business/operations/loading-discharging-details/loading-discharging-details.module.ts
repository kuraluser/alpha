import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoadingDischargingDetailsComponent } from './loading-discharging-details.component';
import { DropdownModule } from 'primeng/dropdown';
import { FileUploadModule } from 'primeng/fileupload';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NumberDirectiveModule } from '../../../shared/directives/number-directive/number-directive.module';
import { TranslateModule } from '@ngx-translate/core';
import { CalendarModule } from 'primeng/calendar';
import { ValidationErrorModule } from '../../../shared/components/validation-error/validation-error.module';

/**
 * Module for loading discharging details module
 *
 * @export
 * @class LoadingDischargingDetailsModule
 */
@NgModule({
  declarations: [LoadingDischargingDetailsComponent],
  exports: [LoadingDischargingDetailsComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    DropdownModule,
    FileUploadModule,
    NumberDirectiveModule,
    TranslateModule,
    CalendarModule,
    ValidationErrorModule
  ]
})
export class LoadingDischargingDetailsModule { }

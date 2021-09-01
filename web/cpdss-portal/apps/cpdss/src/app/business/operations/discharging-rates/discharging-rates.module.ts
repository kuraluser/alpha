import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DischargingRatesComponent } from './discharging-rates.component';
import { TranslateModule } from '@ngx-translate/core';
import { ValidationErrorModule } from '../../../shared/components/validation-error/validation-error.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DropdownModule } from 'primeng/dropdown';
import { NumberDirectiveModule } from '../../../shared/directives/number-directive/number-directive.module';


/**
 * Module for discharge rates section in discharge information
 *
 * @export
 * @class DischargingRatesModule
 */
@NgModule({
  declarations: [DischargingRatesComponent],
  imports: [
    CommonModule,
    TranslateModule,
    ValidationErrorModule,
    FormsModule,
    ReactiveFormsModule,
    NumberDirectiveModule,
    DropdownModule
  ],
  exports: [DischargingRatesComponent]
})
export class DischargingRatesModule { }

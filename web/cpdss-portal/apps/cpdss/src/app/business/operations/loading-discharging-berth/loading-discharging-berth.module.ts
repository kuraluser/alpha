import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoadingDischargingBerthComponent } from './loading-discharging-berth.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NumberDirectiveModule } from '../../../shared/directives/number-directive/number-directive.module';
import { DropdownModule } from 'primeng/dropdown';
import { TranslateModule } from '@ngx-translate/core';
import { ValidationErrorModule } from '../../../shared/components/validation-error/validation-error.module';

/**
 * Module for loading discharging berth module
 *
 * @export
 * @class LoadingDischargingBerthModule
 */
@NgModule({
  declarations: [LoadingDischargingBerthComponent],
  exports: [LoadingDischargingBerthComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    NumberDirectiveModule,
    DropdownModule,
    TranslateModule,
    ValidationErrorModule
  ]
})
export class LoadingDischargingBerthModule { }

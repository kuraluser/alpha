import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoadingDischargingCargoMachineryComponent } from './loading-discharging-cargo-machinery.component';
import { TableModule } from 'primeng/table';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NumberDirectiveModule } from '../../../shared/directives/number-directive/number-directive.module';
import { DropdownModule } from 'primeng/dropdown';
import { TranslateModule } from '@ngx-translate/core';
import { ValidationErrorModule } from '../../../shared/components/validation-error/validation-error.module';

/**
 * Module for loading discharging cargo machinery module
 *
 * @export
 * @class LoadingDischargingCargoMachineryModule
 */
@NgModule({
  declarations: [LoadingDischargingCargoMachineryComponent],
  exports: [LoadingDischargingCargoMachineryComponent],
  imports: [
    CommonModule,
    TableModule,
    FormsModule,
    ReactiveFormsModule,
    NumberDirectiveModule,
    DropdownModule,
    TranslateModule,
    ValidationErrorModule
  ]
})
export class LoadingDischargingCargoMachineryModule { }

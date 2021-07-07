import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ValidationErrorModule } from '../../../shared/components/validation-error/validation-error.module';

import { TranslateModule } from '@ngx-translate/core';

import { LoadingRateComponent } from './loading-rate.component';

import { NumberDirectiveModule } from '../../../shared/directives/number-directive/number-directive.module';
import { DropdownModule } from 'primeng/dropdown';

/**
 * Module for loading rate module
 *
 * @export
 * @class LoadingRateModule
 */
@NgModule({
  declarations: [LoadingRateComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    TranslateModule,
    ValidationErrorModule,
    NumberDirectiveModule,
    DropdownModule
  ],
  exports: [LoadingRateComponent]
})
export class LoadingRateModule { }

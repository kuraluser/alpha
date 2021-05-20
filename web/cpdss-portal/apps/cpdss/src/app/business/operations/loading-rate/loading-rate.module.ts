import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoadingRateComponent } from './loading-rate.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NumberDirectiveModule } from '../../../shared/directives/number-directive/number-directive.module';

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
    NumberDirectiveModule
  ],
  exports: [LoadingRateComponent]
})
export class LoadingRateModule { }

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoadingDischargingDetailsComponent } from './loading-discharging-details.component';
import { DropdownModule } from 'primeng/dropdown';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NumberDirectiveModule } from '../../../shared/directives/number-directive/number-directive.module';


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
    NumberDirectiveModule
  ]
})
export class LoadingDischargingDetailsModule { }

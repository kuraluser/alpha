import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoadingDischargingCargoMachineryComponent } from './loading-discharging-cargo-machinery.component';
import { TableModule } from 'primeng/table';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

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
    ReactiveFormsModule
  ]
})
export class LoadingDischargingCargoMachineryModule { }

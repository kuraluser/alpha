import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoadingDischargingCargoDetailsComponent } from './loading-discharging-cargo-details.component';
import { CargoTankLayoutModule } from '../../../business/core/components/cargo-tank-layout/cargo-tank-layout.module';
import { DatatableModule } from '../../../shared/components/datatable/datatable.module'
import { LoadingDischargingCargoDetailsTransformationService } from './loading-discharging-cargo-details-transformation.service';
import { LoadingDischargingCargoDetailsTableModule } from '../loading-discharging-cargo-details-table/loading-discharging-cargo-details-table.module';

/**
 * Module for loading discharging cargo details module
 *
 * @export
 * @class LoadingDischargingCargoDetailsModule
 */
@NgModule({
  declarations: [LoadingDischargingCargoDetailsComponent],
  exports: [LoadingDischargingCargoDetailsComponent],
  imports: [
    CommonModule,
    CargoTankLayoutModule,
    DatatableModule,
    LoadingDischargingCargoDetailsTableModule
  ],
  providers: [LoadingDischargingCargoDetailsTransformationService]
})
export class LoadingDischargingCargoDetailsModule { }
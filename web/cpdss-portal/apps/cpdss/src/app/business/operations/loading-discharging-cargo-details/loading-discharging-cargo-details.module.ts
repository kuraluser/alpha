import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';
import { LoadingDischargingCargoDetailsComponent } from './loading-discharging-cargo-details.component';
import { CargoTankLayoutModule } from '../../../business/core/components/cargo-tank-layout/cargo-tank-layout.module';
import { DatatableModule } from '../../../shared/components/datatable/datatable.module'
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
    TranslateModule,
    LoadingDischargingCargoDetailsTableModule
  ]
})
export class LoadingDischargingCargoDetailsModule { }

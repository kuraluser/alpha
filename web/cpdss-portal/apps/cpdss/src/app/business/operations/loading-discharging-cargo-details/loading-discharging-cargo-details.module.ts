import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';
import { LoadingDischargingCargoDetailsComponent } from './loading-discharging-cargo-details.component';
import { CargoTankLayoutModule } from '../../../business/core/components/cargo-tank-layout/cargo-tank-layout.module';
import { LoadingDischargingCargoDetailsTableModule } from '../loading-discharging-cargo-details-table/loading-discharging-cargo-details-table.module';
import { LoadingDischargingCommingleDetailsModule } from './../loading-discharging-commingle-details/loading-discharging-commingle-details.module';
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
    TranslateModule,
    LoadingDischargingCargoDetailsTableModule,
    LoadingDischargingCommingleDetailsModule
  ]
})
export class LoadingDischargingCargoDetailsModule { }

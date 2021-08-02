import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoadingDischargingCargoDetailsTableComponent } from './loading-discharging-cargo-details-table.component';
import { TableModule } from 'primeng/table';
import { TranslateModule } from '@ngx-translate/core';
import { QuantityDecimalFormatPipe } from '../../../shared/pipes/quantity-decimal-format/quantity-decimal-format.pipe';
import { QuantityDecimalFormatPipeModule } from '../../../shared/pipes/quantity-decimal-format/quantity-decimal-format.module';
import { TooltipModule } from 'primeng/tooltip';

/**
 * Module for loading discharging cargo details table module
 *
 * @export
 * @class LoadingDischargingCargoDetailsTableModule
 */
@NgModule({
  declarations: [LoadingDischargingCargoDetailsTableComponent],
  imports: [
    CommonModule,
    TableModule,
    TranslateModule,
    QuantityDecimalFormatPipeModule,
    TooltipModule
  ],
  exports: [LoadingDischargingCargoDetailsTableComponent],
  providers: [QuantityDecimalFormatPipe]
})
export class LoadingDischargingCargoDetailsTableModule { }

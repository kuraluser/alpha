import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LoadingDischargingCommingleDetailsComponent } from './loading-discharging-commingle-details.component';
import { DatatableModule } from '../../../shared/components/datatable/datatable.module';
import { DialogModule } from 'primeng/dialog';
import { TranslateModule } from '@ngx-translate/core';

/**
 * Module for commingle details pop up
 *
 * @export
 * @class LoadingDischargingCommingleDetailsModule
 */
@NgModule({
  declarations: [LoadingDischargingCommingleDetailsComponent],
  imports: [
    CommonModule,
    DatatableModule,
    DialogModule,
    TranslateModule
  ],
  exports: [LoadingDischargingCommingleDetailsComponent]
})
export class LoadingDischargingCommingleDetailsModule { }

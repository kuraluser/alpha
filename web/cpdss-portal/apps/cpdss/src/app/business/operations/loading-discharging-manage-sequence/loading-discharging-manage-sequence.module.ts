import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoadingDischargingManageSequenceComponent } from './loading-discharging-manage-sequence.component';
import { DatatableModule } from '../../../shared/components/datatable/datatable.module';
import { CheckboxModule } from 'primeng/checkbox';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TranslateModule } from '@ngx-translate/core';

/**
 * Module for loading discharging manage sequence module
 *
 * @export
 * @class LoadingDischargingManageSequenceModule
 */
@NgModule({
  declarations: [LoadingDischargingManageSequenceComponent],
  imports: [
    CommonModule,
    DatatableModule,
    CheckboxModule,
    FormsModule,
    ReactiveFormsModule,
    TranslateModule
  ],
  exports: [LoadingDischargingManageSequenceComponent]
})
export class LoadingDischargingManageSequenceModule { }

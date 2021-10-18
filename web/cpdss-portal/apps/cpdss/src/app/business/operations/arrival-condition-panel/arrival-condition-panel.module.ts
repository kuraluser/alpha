import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';

import { ArrivalConditionPanelComponent } from './arrival-condition-panel.component';
import { QuantityDecimalFormatPipeModule } from '../../../shared/pipes/quantity-decimal-format/quantity-decimal-format.module';


/**
 * Module for arrival condition panel
 *
 * @export
 * @class ArrivalConditionPanelModule
 */
@NgModule({
  declarations: [ArrivalConditionPanelComponent],
  imports: [
    CommonModule,
    TranslateModule,
    QuantityDecimalFormatPipeModule
  ],
  exports: [ArrivalConditionPanelComponent]
})
export class ArrivalConditionPanelModule { }

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TranslateModule } from '@ngx-translate/core';

import { DepartureConditionPanelComponent } from './departure-condition-panel.component';
import { QuantityDecimalFormatPipeModule } from '../../../shared/pipes/quantity-decimal-format/quantity-decimal-format.module';

/**
 * Module for Departure condition panel
 *
 * @export
 * @class DepartureConditionPanelModule
 */
@NgModule({
  declarations: [DepartureConditionPanelComponent],
  imports: [
    CommonModule,
    TranslateModule,
    QuantityDecimalFormatPipeModule
  ],
  exports: [DepartureConditionPanelComponent]
})
export class DepartureConditionPanelModule { }

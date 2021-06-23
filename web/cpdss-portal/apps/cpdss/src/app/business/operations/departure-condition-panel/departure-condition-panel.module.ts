import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TranslateModule } from '@ngx-translate/core';

import { DepartureConditionPanelComponent } from './departure-condition-panel.component';

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
    TranslateModule
  ],
  exports: [DepartureConditionPanelComponent]
})
export class DepartureConditionPanelModule { }

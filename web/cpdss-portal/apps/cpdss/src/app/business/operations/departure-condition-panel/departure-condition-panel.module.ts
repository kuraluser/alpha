import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
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
    CommonModule
  ],
  exports: [DepartureConditionPanelComponent]
})
export class DepartureConditionPanelModule { }

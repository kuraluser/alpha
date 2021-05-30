import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ArrivalConditionPanelComponent } from './arrival-condition-panel.component';


/**
 * Module for arrival condition panel
 *
 * @export
 * @class ArrivalConditionPanelModule
 */
@NgModule({
  declarations: [ArrivalConditionPanelComponent],
  imports: [
    CommonModule
  ],
  exports: [ArrivalConditionPanelComponent]
})
export class ArrivalConditionPanelModule { }

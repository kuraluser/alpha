import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';

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
    CommonModule,
    TranslateModule
  ],
  exports: [ArrivalConditionPanelComponent]
})
export class ArrivalConditionPanelModule { }

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DischargingComponent } from './discharging.component';
import { InstructionSidePanelModule } from '../instruction-side-panel/instruction-side-panel.module';
import { InstructionCheckListModule } from '../instruction-check-list/instruction-check-list.module';

/**
 * Module for discharging operations
 *
 * @export
 * @class DischargingModule
 */
@NgModule({
  declarations: [DischargingComponent],
  imports: [
    CommonModule,
    InstructionSidePanelModule,
    InstructionCheckListModule
  ]
})
export class DischargingModule { }

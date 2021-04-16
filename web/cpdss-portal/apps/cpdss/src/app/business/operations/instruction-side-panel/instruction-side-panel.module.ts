import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { InstructionSidePanelComponent } from './instruction-side-panel.component';

/**
 * Module for instructions
 *
 * @export
 * @class InstructionSidePanelModule
 */
@NgModule({
  declarations: [InstructionSidePanelComponent],
  imports: [
    CommonModule
  ],
  exports: [InstructionSidePanelComponent]

})
export class InstructionSidePanelModule { }

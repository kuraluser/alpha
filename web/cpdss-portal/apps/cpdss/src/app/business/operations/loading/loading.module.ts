import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LoadingComponent } from './loading.component';
import { LoadingRoutingModule } from './loading-routing.module';
import { InstructionSidePanelModule } from '../instruction-side-panel/instruction-side-panel.module';
import { InstructionCheckListModule } from '../instruction-check-list/instruction-check-list.module';
/**
 * Module for loading operation
 *
 * @export
 * @class LoadingModule
 */
@NgModule({
  declarations: [LoadingComponent],
  imports: [
    CommonModule,
    LoadingRoutingModule,
    InstructionSidePanelModule,
    InstructionCheckListModule
  ]
})
export class LoadingModule { }

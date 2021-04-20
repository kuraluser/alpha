import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { InstructionCheckListComponent } from './instruction-check-list.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

/**
 * Module for instructions check list
 *
 * @export
 * @class InstructionCheckListModule
 */
@NgModule({
  declarations: [InstructionCheckListComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule
  ],
  exports: [InstructionCheckListComponent]
})
export class InstructionCheckListModule { }

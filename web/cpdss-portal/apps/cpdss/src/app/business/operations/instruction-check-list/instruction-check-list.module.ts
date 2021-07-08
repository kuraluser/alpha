import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { InstructionCheckListComponent } from './instruction-check-list.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TreeTableModule } from 'primeng/treetable';
import { TreeModule } from 'primeng/tree';
import { TranslateModule } from '@ngx-translate/core';

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
    ReactiveFormsModule,
    TreeTableModule,
    TreeModule,
    TranslateModule
  ],
  exports: [InstructionCheckListComponent]
})
export class InstructionCheckListModule { }

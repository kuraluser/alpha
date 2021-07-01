import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RulesTableComponent} from './rules-table.component'
import { TableModule } from 'primeng/table';
import { InputSwitchModule } from 'primeng/inputswitch';
import { DropdownModule } from 'primeng/dropdown';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TranslateModule } from '@ngx-translate/core';
import { ValidationErrorModule } from 'apps/cpdss/src/app/shared/components/validation-error/validation-error.module';
import { RadioButtonModule } from 'primeng/radiobutton';
import { NumberDirectiveModule } from 'apps/cpdss/src/app/shared/directives/number-directive/number-directive.module';
import { MultiSelectModule } from 'primeng/multiselect';

/**
 * Module class for rules table.
 *
 * @export
 * @class RulesTableModule
 */
@NgModule({
  declarations: [RulesTableComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    TranslateModule,
    DropdownModule,
    InputSwitchModule,
    ValidationErrorModule,
    RadioButtonModule,
    TableModule,
    NumberDirectiveModule,
    MultiSelectModule
  ],
  exports:[RulesTableComponent]
})
export class RulesTableModule { }

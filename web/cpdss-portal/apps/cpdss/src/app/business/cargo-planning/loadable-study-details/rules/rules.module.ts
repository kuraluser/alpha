import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RulesComponent} from './rules.component';
import { InputSwitchModule } from 'primeng/inputswitch';
import { RulesTableModule } from '../../../core/components/rules-table/rules-table.module';
import { TranslateModule } from '@ngx-translate/core';
import { DialogModule } from 'primeng/dialog';
import { DropdownModule } from 'primeng/dropdown';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RuleService } from '../../services/rule.service';




/**
 * Module class for rules.
 *
 * @export
 * @class RulesModule
 */
@NgModule({
  declarations: [RulesComponent],
  imports: [
    CommonModule,  
    InputSwitchModule,
    RulesTableModule,
    TranslateModule,
    DialogModule,
    DropdownModule,
    FormsModule,
    ReactiveFormsModule
  ],
  exports : [RulesComponent],
  providers:[RuleService]
})
export class RulesModule { }

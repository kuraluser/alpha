import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RulesComponent} from './rules.component';
import { DialogModule } from 'primeng/dialog';
import { RulesTableModule } from '../../core/components/rules-table/rules-table.module';
import { TranslateModule } from '@ngx-translate/core';


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
    DialogModule,
    RulesTableModule,
    TranslateModule,
    RulesTableModule 
  ],
  exports :[RulesComponent]
})
export class RulesModule { }

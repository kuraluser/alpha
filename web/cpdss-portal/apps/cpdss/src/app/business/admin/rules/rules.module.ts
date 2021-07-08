import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RulesComponent } from './rules.component';
import { RouterModule, Routes } from '@angular/router';
import { TranslateModule } from '@ngx-translate/core';
import { DropdownModule } from 'primeng/dropdown';
import { RulesService } from '../../core/services/rules.service'
import { RulesApiService } from './services/rules-api.service'
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RulesTableModule } from '../../core/components/rules-table/rules-table.module'
import { InputSwitchModule } from 'primeng/inputswitch';
import { ValidationErrorModule } from '../../../shared/components/validation-error/validation-error.module';
import {RadioButtonModule} from 'primeng/radiobutton';
import { TableModule } from 'primeng/table';



const routes: Routes = [
  {
      path: '',
      component: RulesComponent
  }
];

/**
 * Routing Module for Rules Module
 *
 * @export
 * @class RulesModule
 */
@NgModule({
  declarations: [RulesComponent],
  imports: [
    RouterModule.forChild(routes),
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    TranslateModule,
    DropdownModule,
    InputSwitchModule,
    ValidationErrorModule,
    RadioButtonModule,
    RulesTableModule,
    TableModule
  ],
  providers:[
    RulesService,
    RulesApiService
  ]
})
export class RulesModule { }

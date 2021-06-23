import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RulesComponent } from './rules.component';
import { RouterModule, Routes } from '@angular/router';
import { TranslateModule } from '@ngx-translate/core';
import { DropdownModule } from 'primeng/dropdown';
import { RulesService } from './services/rules.service'
import { RulesApiService } from './services/rules-api.service'
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RulesTableComponent } from './rules-table/rules-table.component';
import { TableModule } from 'primeng/table';
import { InputSwitchModule } from 'primeng/inputswitch';
import { ValidationErrorModule } from '../../../shared/components/validation-error/validation-error.module';

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
  declarations: [RulesComponent, RulesTableComponent],
  imports: [
    RouterModule.forChild(routes),
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    TranslateModule,
    DropdownModule,
    TableModule,
    InputSwitchModule,
    ValidationErrorModule
  ],
  providers:[
    RulesService,
    RulesApiService
  ]
})
export class RulesModule { }

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CowPlanComponent } from './cow-plan.component';
import { TranslateModule } from '@ngx-translate/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DropdownModule } from 'primeng/dropdown';
import { MultiSelectModule } from 'primeng/multiselect';
import { InputMaskModule } from 'primeng/inputmask';
import { InputSwitchModule } from 'primeng/inputswitch';
import { ValidationErrorModule } from '../../../shared/components/validation-error/validation-error.module';
import { NumberDirectiveModule } from '../../../shared/directives/number-directive/number-directive.module';

/**
 * Module for COW plan section component
 *
 * @export
 * @class CowPlanModule
 */
@NgModule({
  declarations: [CowPlanComponent],
  imports: [
    CommonModule,
    TranslateModule,
    FormsModule,
    ReactiveFormsModule,
    DropdownModule,
    MultiSelectModule,
    InputMaskModule,
    InputSwitchModule,
    ValidationErrorModule,
    NumberDirectiveModule
  ],
  exports: [CowPlanComponent]
})
export class CowPlanModule { }

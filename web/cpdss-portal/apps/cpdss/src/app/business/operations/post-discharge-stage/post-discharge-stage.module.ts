import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PostDischargeStageComponent } from './post-discharge-stage.component';
import { TranslateModule } from '@ngx-translate/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TableModule } from 'primeng/table';
import { InputMaskModule } from 'primeng/inputmask';
import { ValidationErrorModule } from '../../../shared/components/validation-error/validation-error.module';


/**
 * Module for post discharge component
 *
 * @export
 * @class PostDischargeStageModule
 */
@NgModule({
  declarations: [PostDischargeStageComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    TranslateModule,
    TableModule,
    InputMaskModule,
    ValidationErrorModule
  ],
  exports: [PostDischargeStageComponent],
})
export class PostDischargeStageModule { }

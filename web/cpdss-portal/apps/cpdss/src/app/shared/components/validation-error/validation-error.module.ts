import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ValidationErrorComponent } from './validation-error.component';

import { TooltipModule } from 'primeng/tooltip';
import { TranslateModule } from '@ngx-translate/core';


/**
 * Class for Validation Error module
 *
 * @export
 * @class ValidationErrorModule
 */
@NgModule({
  declarations: [ValidationErrorComponent],
  imports: [
    CommonModule,
    TooltipModule,
    TranslateModule
  ],
  exports: [ValidationErrorComponent]
})
export class ValidationErrorModule { }

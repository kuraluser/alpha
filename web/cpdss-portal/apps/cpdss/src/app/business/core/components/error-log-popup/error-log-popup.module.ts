import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DialogModule } from 'primeng/dialog';
import { TableModule } from 'primeng/table';
import { TranslateModule } from '@ngx-translate/core';

import { ErrorLogPopupComponent }  from '../error-log-popup/error-log-popup.component';

@NgModule({
  declarations: [ErrorLogPopupComponent],
  imports: [
    CommonModule,
    DialogModule,
    TableModule,
    TranslateModule
  ],
  exports: [ErrorLogPopupComponent]
})
export class ErrorLogPopupModule { }

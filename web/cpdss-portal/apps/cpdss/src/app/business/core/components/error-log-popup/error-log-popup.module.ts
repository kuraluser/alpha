import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DialogModule } from 'primeng/dialog';
import { TableModule } from 'primeng/table';

import { ErrorLogPopupComponent }  from '../error-log-popup/error-log-popup.component';

@NgModule({
  declarations: [ErrorLogPopupComponent],
  imports: [
    CommonModule,
    DialogModule,
    TableModule
  ],
  exports: [ErrorLogPopupComponent]
})
export class ErrorLogPopupModule { }

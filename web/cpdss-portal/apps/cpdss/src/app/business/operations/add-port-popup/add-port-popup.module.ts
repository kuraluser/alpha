import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';
import { AddPortPopupComponent } from './add-port-popup.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DialogModule } from 'primeng/dialog';
import { DropdownModule } from 'primeng/dropdown';

/**
 * Module for add port
 *
 * @export
 * @class AddPortPopupModule
 */
@NgModule({
  declarations: [AddPortPopupComponent],
  imports: [
    CommonModule,
    TranslateModule,
    FormsModule,
    ReactiveFormsModule,
    DialogModule,
    DropdownModule
  ],
  exports: [AddPortPopupComponent]
})
export class AddPortPopupModule { }

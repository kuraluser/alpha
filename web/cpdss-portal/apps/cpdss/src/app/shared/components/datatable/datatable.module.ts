import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { TableModule } from 'primeng/table';
import { DatatableComponent } from './datatable.component';
import { DropdownModule } from 'primeng/dropdown';
import { ColorPickerModule } from 'primeng/colorpicker';
import { ValidationErrorModule } from '../validation-error/validation-error.module';
import { TranslateModule } from '@ngx-translate/core';
import { SplitButtonModule } from 'primeng/splitbutton';
import { CalendarModule } from 'primeng/calendar';


/**
 * Module for DataTable
 *
 * @export
 * @class DatatableModule
 */
@NgModule({
  declarations: [DatatableComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    TableModule,
    DropdownModule,
    ColorPickerModule,
    ValidationErrorModule,
    TranslateModule,
    SplitButtonModule,
    CalendarModule
  ],
  exports: [DatatableComponent]
})
export class DatatableModule { }

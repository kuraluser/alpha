import { NgModule } from '@angular/core';
import { CommonModule, DecimalPipe } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { TableModule } from 'primeng/table';
import { DatatableComponent } from './datatable.component';
import { DropdownModule } from 'primeng/dropdown';
import { ColorPickerModule } from 'primeng/colorpicker';
import { ValidationErrorModule } from '../validation-error/validation-error.module';
import { TranslateModule } from '@ngx-translate/core';
import { SplitButtonModule } from 'primeng/splitbutton';
import { CalendarModule } from 'primeng/calendar';
import { NumberDirectiveModule } from '../../directives/number-directive/number-directive.module';
import { PaginatorModule } from 'primeng/paginator';
import { TooltipModule } from 'primeng/tooltip';
import { QuantityDecimalFormatPipeModule } from '../../pipes/quantity-decimal-format/quantity-decimal-format.module';
import {InputMaskModule} from 'primeng/inputmask';
import { MultiSelectModule } from 'primeng/multiselect';

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
    CalendarModule,
    PaginatorModule,
    NumberDirectiveModule,
    TooltipModule,
    QuantityDecimalFormatPipeModule,
    InputMaskModule,
    MultiSelectModule
  ],
  exports: [DatatableComponent],
  providers: [DecimalPipe]
})
export class DatatableModule { }

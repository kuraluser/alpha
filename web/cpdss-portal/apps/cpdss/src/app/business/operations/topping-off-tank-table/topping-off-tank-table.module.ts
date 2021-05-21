import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ToppingOffTankTableComponent } from './topping-off-tank-table.component';
import { DatatableModule } from '../../../shared/components/datatable/datatable.module';

/**
 * Module for topping off table
 *
 * @export
 * @class ToppingOffTankTableModule
 */
@NgModule({
  declarations: [ToppingOffTankTableComponent],
  imports: [
    CommonModule,
    DatatableModule
  ],
  exports: [ToppingOffTankTableComponent]
})
export class ToppingOffTankTableModule { }

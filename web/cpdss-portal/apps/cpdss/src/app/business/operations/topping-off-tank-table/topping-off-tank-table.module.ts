import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';

import { ToppingOffTankTableComponent } from './topping-off-tank-table.component';
import { DatatableModule } from '../../../shared/components/datatable/datatable.module';
import { ToppingOffTankTableTransformationService } from './topping-off-tank-table-transformation.service';
import { QuantityPipeModule } from '../../../shared/pipes/quantity/quantity-pipe.module';
import { QuantityPipe } from '../../../shared/pipes/quantity/quantity.pipe';

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
    TranslateModule,
    DatatableModule,
    QuantityPipeModule
  ],
  exports: [ToppingOffTankTableComponent],
  providers: [ ToppingOffTankTableTransformationService , QuantityPipe ]
})
export class ToppingOffTankTableModule { }

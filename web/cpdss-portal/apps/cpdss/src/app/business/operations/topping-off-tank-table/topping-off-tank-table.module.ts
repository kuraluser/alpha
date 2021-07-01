import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';

import { ToppingOffTankTableComponent } from './topping-off-tank-table.component';
import { DatatableModule } from '../../../shared/components/datatable/datatable.module';
import { ToppingOffTankTableTransformationService } from './topping-off-tank-table-transformation.service';
import { ToppingOffTankTableApiService } from './topping-off-tank-table-api.service';

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
    DatatableModule
  ],
  exports: [ToppingOffTankTableComponent],
  providers: [ ToppingOffTankTableTransformationService, ToppingOffTankTableApiService ]
})
export class ToppingOffTankTableModule { }

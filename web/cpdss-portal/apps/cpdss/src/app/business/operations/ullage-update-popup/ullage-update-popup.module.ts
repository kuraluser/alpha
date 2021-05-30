import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UllageUpdatePopupComponent } from './ullage-update-popup.component';
import { DialogModule } from 'primeng/dialog';
import { TableModule } from 'primeng/table';
import { DropdownModule } from 'primeng/dropdown';
import { TranslateModule } from '@ngx-translate/core';
import { CargoTankLayoutModule } from '../../../business/core/components/cargo-tank-layout/cargo-tank-layout.module';
import { DatatableModule } from '../../../shared/components/datatable/datatable.module';
import { BallastLayoutModule } from '../../../business/core/components/ballast-layout/ballast-layout.module';
import { BunkeringLayoutModule } from '../../../business/core/components/bunkering-layout/bunkering-layout.module';
import { UllageUpdatePopupTransformationService } from './ullage-update-popup-transformation.service';
import { TooltipModule } from 'primeng/tooltip';
import { QuantityPipeModule } from '../../../shared/pipes/quantity/quantity-pipe.module';
import { QuantityPipe } from '../../../shared/pipes/quantity/quantity.pipe';


/**
 * Module for ullage update
 *
 * @export
 * @class UllageUpdatePopupModule
 */
@NgModule({
  declarations: [UllageUpdatePopupComponent],
  imports: [
    CommonModule,
    DialogModule,
    CargoTankLayoutModule,
    TranslateModule,
    DatatableModule,
    TableModule,
    BallastLayoutModule,
    BunkeringLayoutModule,
    TooltipModule,
    QuantityPipeModule,
    DropdownModule
  ],
  exports: [UllageUpdatePopupComponent],
  providers: [UllageUpdatePopupTransformationService, QuantityPipe]
})
export class UllageUpdatePopupModule { }

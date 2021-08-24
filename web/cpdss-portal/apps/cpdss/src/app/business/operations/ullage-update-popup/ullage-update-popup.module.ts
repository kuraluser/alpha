import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
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
import { ValidationErrorModule } from '../../../shared/components/validation-error/validation-error.module';
import { NumberDirectiveModule } from '../../../shared/directives/number-directive/number-directive.module';
import { QuantityDecimalFormatPipeModule } from '../../../shared/pipes/quantity-decimal-format/quantity-decimal-format.module';
import { UllageUpdateApiService } from './ullage-update-api.service';

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
    FormsModule,
    ReactiveFormsModule,
    DialogModule,
    ValidationErrorModule,
    NumberDirectiveModule,
    CargoTankLayoutModule,
    TranslateModule,
    DatatableModule,
    TableModule,
    BallastLayoutModule,
    BunkeringLayoutModule,
    TooltipModule,
    QuantityDecimalFormatPipeModule,
    QuantityPipeModule,
    DropdownModule
  ],
  exports: [UllageUpdatePopupComponent],
  providers: [UllageUpdatePopupTransformationService, QuantityPipe, UllageUpdateApiService]
})
export class UllageUpdatePopupModule { }

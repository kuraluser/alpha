import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DepartureConditionComponent } from './departure-condition.component';
import { CargoTankLayoutModule } from '../../../business/core/components/cargo-tank-layout/cargo-tank-layout.module';
import { UllageUpdatePopupModule } from '../ullage-update-popup/ullage-update-popup.module';
import { DepartureConditionTransformationService } from './departure-condition-transformation.service';
import { DatatableModule } from '../../../shared/components/datatable/datatable.module'
import { LoadingDischargingCargoDetailsTableModule } from './../loading-discharging-cargo-details-table/loading-discharging-cargo-details-table.module';
import { BallastLayoutModule } from '../../../business/core/components/ballast-layout/ballast-layout.module';
import { TranslateModule } from '@ngx-translate/core';
import { TooltipModule } from 'primeng/tooltip';

/**
 * Module for Departure condition block
 *
 * @export
 * @class DepartureConditionModule
 */
@NgModule({
  declarations: [DepartureConditionComponent],
  imports: [
    CommonModule,
    CargoTankLayoutModule,
    UllageUpdatePopupModule,
    DatatableModule,
    LoadingDischargingCargoDetailsTableModule,
    BallastLayoutModule,
    TranslateModule,
    TooltipModule
  ],
  exports: [DepartureConditionComponent],
  providers: [DepartureConditionTransformationService]
})
export class DepartureConditionModule { }

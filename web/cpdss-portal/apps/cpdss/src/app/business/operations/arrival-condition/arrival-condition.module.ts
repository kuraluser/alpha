import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ArrivalConditionComponent } from './arrival-condition.component';
import { CargoTankLayoutModule } from '../../../business/core/components/cargo-tank-layout/cargo-tank-layout.module';
import { BallastLayoutModule } from '../../../business/core/components/ballast-layout/ballast-layout.module';
import { UllageUpdatePopupModule } from '../ullage-update-popup/ullage-update-popup.module';
import { LoadingDischargingCargoDetailsTableModule } from './../loading-discharging-cargo-details-table/loading-discharging-cargo-details-table.module';
import { ArrivalConditionTransformationService } from './arrival-condition-transformation.service';
import { TranslateModule } from '@ngx-translate/core';
import { TooltipModule } from 'primeng/tooltip';
import { LoadingDischargingCommingleDetailsModule } from './../loading-discharging-commingle-details/loading-discharging-commingle-details.module';

/**
 * Module for arrival condition block
 *
 * @export
 * @class ArrivalConditionModule
 */
@NgModule({
  declarations: [ArrivalConditionComponent],
  imports: [
    CommonModule,
    CargoTankLayoutModule,
    UllageUpdatePopupModule,
    LoadingDischargingCargoDetailsTableModule,
    BallastLayoutModule,
    TranslateModule,
    TooltipModule,
    LoadingDischargingCommingleDetailsModule
  ],
  exports: [ArrivalConditionComponent],
  providers: [ArrivalConditionTransformationService]
})
export class ArrivalConditionModule { }

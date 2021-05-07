import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { OperationsRoutingModule } from './operations-routing.module';
import { OperationsComponent } from './operations.component';
import { VesselsApiService } from '../core/services/vessels-api.service';
import { VesselInfoModule } from '../core/components/vessel-info/vessel-info.module';
import { UnitDropdownModule } from '../../shared/components/unit-dropdown/unit-dropdown.module';
import { DropdownModule } from 'primeng/dropdown';
import { TranslateModule } from '@ngx-translate/core';
import { PortRotationRibbonModule } from '../core/components/port-rotation-ribbon/port-rotation-ribbon.module';
import { NewVoyagePopupModule } from '../core/components/new-voyage-popup/new-voyage-popup.module';
import { EditPortRotationPopupModule } from '../core/components/edit-port-rotation-popup/edit-port-rotation-popup.module';


@NgModule({
  declarations: [
    OperationsComponent,
  ],
  imports: [
    CommonModule,
    DropdownModule,
    TranslateModule,
    OperationsRoutingModule,
    VesselInfoModule,
    UnitDropdownModule,
    PortRotationRibbonModule,
    NewVoyagePopupModule,
    EditPortRotationPopupModule
  ],
  providers: [VesselsApiService]
})
export class OperationsModule { }

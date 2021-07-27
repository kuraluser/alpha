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
import { AddPortPopupModule } from './add-port-popup/add-port-popup.module';
import { LoadingTransformationService } from './services/loading-transformation.service';
import {RulesModule} from './rules/rules.module';
import { RulesService } from './services/rules/rules.service';

@NgModule({
  declarations: [
    OperationsComponent
  ],
  imports: [
    CommonModule,
    DropdownModule,
    TranslateModule,
    OperationsRoutingModule,
    VesselInfoModule,
    UnitDropdownModule,
    PortRotationRibbonModule,
    AddPortPopupModule,
    RulesModule
  ],
  providers: [VesselsApiService, LoadingTransformationService,RulesService ]
})
export class OperationsModule { }

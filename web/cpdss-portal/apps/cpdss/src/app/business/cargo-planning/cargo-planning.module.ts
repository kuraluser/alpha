import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CargoPlanningRoutingModule } from './cargo-planning-routing.module';
import { CargoPlanningComponent } from './cargo-planning.component';
import { VesselInfoModule } from '../core/components/vessel-info/vessel-info.module';


@NgModule({
  declarations: [CargoPlanningComponent],
  imports: [
    CommonModule,
    CargoPlanningRoutingModule,
    VesselInfoModule
  ]
})
export class CargoPlanningModule { }

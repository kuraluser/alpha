import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CargoPlanningRoutingModule } from './cargo-planning-routing.module';
import { CargoPlanningComponent } from './cargo-planning.component';
import { VesselInfoModule } from '../core/components/vessel-info/vessel-info.module';
import { LoadableStudyListApiService } from './services/loadable-study-list-api.service';



@NgModule({
  declarations: [CargoPlanningComponent],
  imports: [
    CommonModule,
    CargoPlanningRoutingModule,
    VesselInfoModule
  ],
  providers: [LoadableStudyListApiService]
})
export class CargoPlanningModule { }

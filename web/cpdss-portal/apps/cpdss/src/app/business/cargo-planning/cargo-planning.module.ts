import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CargoPlanningRoutingModule } from './cargo-planning-routing.module';
import { CargoPlanningComponent } from './cargo-planning.component';
import { VesselInfoModule } from '../core/components/vessel-info/vessel-info.module';
import { LoadableStudyListApiService } from './services/loadable-study-list-api.service';
import { ReactiveFormsModule } from '@angular/forms';
import { NumberDirectiveModule } from '../../shared/directives/number-directive/number-directive.module';

/**
 * Module class Cargo Planning module 
 *
 * @export
 * @class CargoPlanningModule
 */

@NgModule({
  declarations: [CargoPlanningComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    CargoPlanningRoutingModule,
    VesselInfoModule,
    NumberDirectiveModule
  ],
  providers: [LoadableStudyListApiService]
})
export class CargoPlanningModule { }

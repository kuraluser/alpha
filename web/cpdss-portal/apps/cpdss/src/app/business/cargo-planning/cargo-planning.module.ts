import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CargoPlanningRoutingModule } from './cargo-planning-routing.module';
import { CargoPlanningComponent } from './cargo-planning.component';


@NgModule({
  declarations: [CargoPlanningComponent],
  imports: [
    CommonModule,
    CargoPlanningRoutingModule
  ]
})
export class CargoPlanningModule { }

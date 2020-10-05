import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { VoyageStatusComponent } from './voyage-status.component';
import { VoyageStatusRoutingModule } from './voyage-status-routing.module';



@NgModule({
  declarations: [VoyageStatusComponent],
  imports: [
    CommonModule,
    VoyageStatusRoutingModule
  ]
})
export class VoyageStatusModule { }

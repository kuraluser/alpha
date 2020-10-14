import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {VesselInfoComponent } from './vessel-info.component';


@NgModule({
  declarations: [VesselInfoComponent],
  imports: [
    CommonModule,
    
  ],
  exports: [VesselInfoComponent]
})
export class VesselInfoModule { }

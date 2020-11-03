import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {VesselInfoComponent } from './vessel-info.component';
import { TranslateModule } from '@ngx-translate/core';

/**
 * Module for shareble Vessel Icon widget
 *
 * @export
 * @class VesselInfoModule
 */
@NgModule({
  declarations: [VesselInfoComponent],
  imports: [
    CommonModule,
    TranslateModule
    
  ],
  exports: [VesselInfoComponent]
})
export class VesselInfoModule { }

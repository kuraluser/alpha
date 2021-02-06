import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CargoTankLayoutComponent } from './cargo-tank-layout.component';
import { TooltipModule } from 'primeng/tooltip';
import {OverlayPanelModule} from 'primeng/overlaypanel';
import { TranslateModule } from '@ngx-translate/core';

/**
 * Module for cargo tank layout component
 *
 * @export
 * @class CargoTankLayoutModule
 */
@NgModule({
  declarations: [CargoTankLayoutComponent],
  imports: [
    CommonModule,
    TooltipModule,
    OverlayPanelModule,
    TranslateModule
  ],
  exports: [CargoTankLayoutComponent]
})
export class CargoTankLayoutModule { }

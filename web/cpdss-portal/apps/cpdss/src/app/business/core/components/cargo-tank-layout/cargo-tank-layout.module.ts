import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CargoTankLayoutComponent } from './cargo-tank-layout.component';


/**
 * Module for cargo tank layout component
 *
 * @export
 * @class CargoTankLayoutModule
 */
@NgModule({
  declarations: [CargoTankLayoutComponent],
  imports: [
    CommonModule
  ],
  exports: [CargoTankLayoutComponent]
})
export class CargoTankLayoutModule { }

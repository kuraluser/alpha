import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BunkeringComponent } from './bunkering.component';
import { BunkeringRoutingModule } from './bunkering-routing.module';


/**
 * Module for bunkering operation
 *
 * @export
 * @class BunkeringModule
 */
@NgModule({
  declarations: [BunkeringComponent],
  imports: [
    CommonModule,
    BunkeringRoutingModule
  ]
})
export class BunkeringModule { }

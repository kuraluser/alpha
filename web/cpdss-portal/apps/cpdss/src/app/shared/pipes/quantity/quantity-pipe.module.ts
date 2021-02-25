import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { QuantityPipe } from './quantity.pipe';


/**
 * Module for quantity conversion pipe
 *
 * @export
 * @class QuantityPipeModule
 */
@NgModule({
  declarations: [QuantityPipe],
  imports: [
    CommonModule
  ],
  exports: [QuantityPipe]
})
export class QuantityPipeModule { }

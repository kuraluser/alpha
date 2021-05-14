import { NgModule } from '@angular/core';
import { CommonModule , DecimalPipe } from '@angular/common';
import { QuantityDecimalFormatPipe } from './quantity-decimal-format.pipe';



@NgModule({
  declarations: [QuantityDecimalFormatPipe],
  imports: [
    CommonModule
  ],
  providers:[DecimalPipe],
  exports: [QuantityDecimalFormatPipe]
})
export class QuantityDecimalFormatPipeModule { }

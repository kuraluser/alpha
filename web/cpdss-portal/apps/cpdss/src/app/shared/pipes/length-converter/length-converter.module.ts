import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LengthConverterPipe } from './length-converter.pipe';

/**
 * Module for length conversion pipe.
 */
@NgModule({
  declarations: [LengthConverterPipe],
  imports: [
    CommonModule
  ],
  exports: [LengthConverterPipe]
})
export class LengthConverterModule { }

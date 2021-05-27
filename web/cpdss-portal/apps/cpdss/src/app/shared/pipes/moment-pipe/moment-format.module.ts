import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MomentFormatPipe } from './moment-format.pipe';



@NgModule({
  declarations: [MomentFormatPipe],
  imports: [
    CommonModule
  ],
  exports: [MomentFormatPipe]
})
export class MomentFormatModule { }

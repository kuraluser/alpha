import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BallastLayoutComponent } from './ballast-layout.component';


/**
 * Module for ballast tank layount coponent
 *
 * @export
 * @class BallastLayoutModule
 */
@NgModule({
  declarations: [BallastLayoutComponent],
  imports: [
    CommonModule
  ],
  exports: [BallastLayoutComponent]
})
export class BallastLayoutModule { }

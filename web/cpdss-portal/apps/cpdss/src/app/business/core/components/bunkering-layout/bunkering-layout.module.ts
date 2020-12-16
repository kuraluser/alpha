import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BunkeringLayoutComponent } from './bunkering-layout.component';


/**
 * Module for bunkering layout component
 *
 * @export
 * @class BunkeringLayoutModule
 */
@NgModule({
  declarations: [BunkeringLayoutComponent],
  imports: [
    CommonModule
  ],
  exports: [BunkeringLayoutComponent]
})
export class BunkeringLayoutModule { }

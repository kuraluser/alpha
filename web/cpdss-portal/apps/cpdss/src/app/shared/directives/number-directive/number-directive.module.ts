import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NumberDecimalDirective } from './number.directive';


/**
 * Module for number directive
 *
 * @export
 * @class NumberDirectiveModule
 */
@NgModule({
  declarations: [NumberDecimalDirective],
  imports: [
    CommonModule
  ],
  exports: [NumberDecimalDirective]
})
export class NumberDirectiveModule { }

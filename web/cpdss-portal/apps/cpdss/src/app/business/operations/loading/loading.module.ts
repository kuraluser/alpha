import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LoadingComponent } from './loading.component';
import { LoadingRoutingModule } from './loading-routing.module';

/**
 * Module for loading operation
 *
 * @export
 * @class LoadingModule
 */
@NgModule({
  declarations: [LoadingComponent],
  imports: [
    CommonModule,
    LoadingRoutingModule
  ]
})
export class LoadingModule { }

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { VoyagesRoutingModule } from './voyages-routing.module';
import { VoyagesComponent } from './voyages.component';


@NgModule({
  declarations: [VoyagesComponent],
  imports: [
    CommonModule,
    VoyagesRoutingModule
  ]
})
export class VoyagesModule { }

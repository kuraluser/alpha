import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BusinessRoutingModule } from './business-routing.module';
import { BusinessComponent } from './business.component';
import { NavbarModule } from '../shared/components/navbar/navbar.module';
import { VesselsApiService } from './services/vessels-api.service';


@NgModule({
  declarations: [BusinessComponent],
  imports: [
    CommonModule,
    BusinessRoutingModule,
    NavbarModule
  ],
  providers :[
    VesselsApiService
  ]
})
export class BusinessModule { }

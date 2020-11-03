import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BusinessRoutingModule } from './business-routing.module';
import { BusinessComponent } from './business.component';
import { NavbarModule } from '../shared/components/navbar/navbar.module';
import { VesselsApiService } from './core/services/vessels-api.service';
import { VoyageService } from './core/services/voyage.service';

/**
 * CPDSS app main module. All business logic will be inside this module
 *
 * @export
 * @class BusinessModule
 */
@NgModule({
  declarations: [BusinessComponent],
  imports: [
    CommonModule,
    BusinessRoutingModule,
    NavbarModule
  ],
  providers: [
    VesselsApiService, VoyageService
  ]
})
export class BusinessModule { }

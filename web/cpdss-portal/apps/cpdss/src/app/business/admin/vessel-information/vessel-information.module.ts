import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TranslateModule } from '@ngx-translate/core';
import { TableModule } from 'primeng/table';
import { TooltipModule } from 'primeng/tooltip';

import { DatatableModule } from './../../../shared/components/datatable/datatable.module';
import { PermissionDirectiveModule } from './../../../shared/directives/permission/permission-directive.module';
import { LengthConverterModule } from './../../../shared/pipes/length-converter/length-converter.module';
import { VesselInformationRoutingModule } from './vessel-information-routing.module';
import { VesselInformationComponent } from './vessel-information.component';
import { VesselManagementComponent } from './Vessel-management/vessel-management.component';
import { CargoTankLayoutModule } from '../../core/components/cargo-tank-layout/cargo-tank-layout.module';
import { BallastLayoutModule } from '../../core/components/ballast-layout/ballast-layout.module';
import { BunkeringLayoutModule } from '../../core/components/bunkering-layout/bunkering-layout.module';

/**
 * Module for Vessel Information
 * @export
 * @class VesselInformationModule
 */
@NgModule({
  declarations: [VesselInformationComponent, VesselManagementComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    TranslateModule,
    DatatableModule,
    PermissionDirectiveModule,
    TableModule,
    TooltipModule,
    LengthConverterModule,
    CargoTankLayoutModule,
    BallastLayoutModule,
    BunkeringLayoutModule,
    VesselInformationRoutingModule
  ]
})
export class VesselInformationModule { }

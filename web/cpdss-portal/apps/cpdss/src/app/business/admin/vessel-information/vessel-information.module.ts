import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { TranslateModule } from '@ngx-translate/core';
import { TableModule } from 'primeng/table';

import { DatatableModule } from './../../../shared/components/datatable/datatable.module';
import { PermissionDirectiveModule } from './../../../shared/directives/permission/permission-directive.module';
import { VesselInformationRoutingModule } from './vessel-information-routing.module';
import { VesselInformationComponent } from './vessel-information.component';

/**
 * Module for Vessel Information
 * @export
 * @class VesselInformationModule
 */
@NgModule({
  declarations: [VesselInformationComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    TranslateModule,
    DatatableModule,
    PermissionDirectiveModule,
    TableModule,
    VesselInformationRoutingModule
  ]
})
export class VesselInformationModule { }

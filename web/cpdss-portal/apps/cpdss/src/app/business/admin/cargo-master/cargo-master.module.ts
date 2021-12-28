import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CargoMasterRoutingModule } from './cargo-master-routing.module';
import { CargoMasterComponent } from './cargo-master.component';
import { CargoDetailsComponent } from './cargo-details/cargo-details.component';
import { TranslateModule } from '@ngx-translate/core';
import { DatatableModule } from '../../../shared/components/datatable/datatable.module';
import { PermissionDirectiveModule } from '../../../shared/directives/permission/permission-directive.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ValidationErrorModule } from '../../../shared/components/validation-error/validation-error.module';
import { TableModule } from 'primeng/table';
import { DropdownModule } from 'primeng/dropdown';
import { ApiTemperatureHistoryPopupComponent } from './api-temperature-history-popup/api-temperature-history-popup.component';
import { DialogModule } from 'primeng/dialog';
import { CalendarModule } from 'primeng/calendar';

/**
 * Module for cargo master
 *
 * @export
 * @class CargoMasterModule
 */
@NgModule({
  declarations: [CargoMasterComponent, CargoDetailsComponent, ApiTemperatureHistoryPopupComponent],
  imports: [
    CommonModule,
    CargoMasterRoutingModule,
    TranslateModule,
    DatatableModule,
    PermissionDirectiveModule,
    FormsModule,
    ReactiveFormsModule,
    ValidationErrorModule,
    TableModule,
    DropdownModule,
    DialogModule,
    CalendarModule
  ]
})
export class CargoMasterModule { }

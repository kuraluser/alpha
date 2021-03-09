import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CargoHistoryRoutingModule } from './cargo-history-routing.module';
import { CargoHistoryComponent } from './cargo-history.component';
import { DatatableModule } from '../../../shared/components/datatable/datatable.module';
import { DropdownModule } from 'primeng/dropdown';
import { TranslateModule } from '@ngx-translate/core';
import { ValidationErrorModule } from '../../../shared/components/validation-error/validation-error.module';
import { TableModule } from 'primeng/table';
import { DialogModule } from 'primeng/dialog';
import { PermissionDirectiveModule } from '../../../shared/directives/permission/permission-directive.module';
import { VesselInfoModule } from '../../core/components/vessel-info/vessel-info.module';

/**
 * Module will import all the dependencies for Cargo-history
 *
 * @export
 * @class CargoHistoryModule
 */
@NgModule({
  declarations: [CargoHistoryComponent],
  imports: [
    CommonModule,
    DropdownModule,
    TranslateModule,
    TableModule,
    VesselInfoModule,
    ValidationErrorModule,
    DialogModule,
    DatatableModule,
    PermissionDirectiveModule,
    CargoHistoryRoutingModule
  ]
})
export class CargoHistoryModule { }

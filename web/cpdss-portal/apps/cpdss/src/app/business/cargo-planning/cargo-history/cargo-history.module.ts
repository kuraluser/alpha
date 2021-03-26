import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';

import { CargoHistoryRoutingModule } from './cargo-history-routing.module';
import { CargoHistoryComponent } from './cargo-history.component';
import { DatatableModule } from '../../../shared/components/datatable/datatable.module';
import { TranslateModule } from '@ngx-translate/core';
import { ValidationErrorModule } from '../../../shared/components/validation-error/validation-error.module';
import { DialogModule } from 'primeng/dialog';
import { PermissionDirectiveModule } from '../../../shared/directives/permission/permission-directive.module';
import { VesselInfoModule } from '../../core/components/vessel-info/vessel-info.module';
import { CalendarModule } from 'primeng/calendar';
import { FormsModule } from '@angular/forms';

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
    DatatableModule,
    TranslateModule,
    CalendarModule,
    VesselInfoModule,
    FormsModule,
    DialogModule,
    ValidationErrorModule,
    PermissionDirectiveModule,
    CargoHistoryRoutingModule
  ],
  providers: [DatePipe]
})
export class CargoHistoryModule { }

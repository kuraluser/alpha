import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { VoyagesRoutingModule } from './voyages-routing.module';
import { VoyagesComponent } from './voyages.component';
import { DatatableModule } from '../../shared/components/datatable/datatable.module';
import { VoyageListTransformationService } from './services/voyage-list-transformation.service';
import { VoyageListApiService } from './services/voyage-list-api.service';
import { TranslateModule } from '@ngx-translate/core';
import { CalendarModule } from 'primeng/calendar';
import { FormsModule } from '@angular/forms';
import { DatePopUpComponent } from './date-pop-up/date-pop-up.component';
import { DialogModule } from 'primeng/dialog';
import { TooltipModule } from 'primeng/tooltip';
import { VesselInfoModule } from '../core/components/vessel-info/vessel-info.module';
import { NewVoyagePopupModule } from '../core/components/new-voyage-popup/new-voyage-popup.module';
import { PermissionDirectiveModule } from '../../shared/directives/permission/permission-directive.module';
import { ValidationErrorModule } from '../../shared/components/validation-error/validation-error.module';

/**
 * CPDSS app main module. All voyages list logic will be inside this module
 *
 * @export
 * @class VoyagesModule
 */
@NgModule({
  declarations: [VoyagesComponent, DatePopUpComponent],
  imports: [
    CommonModule,
    VoyagesRoutingModule,
    DatatableModule,
    TranslateModule,
    FormsModule,
    CalendarModule,
    DialogModule,
    TooltipModule,
    NewVoyagePopupModule,
    PermissionDirectiveModule,
    VesselInfoModule,
    ValidationErrorModule
  ],
  exports: [DatePopUpComponent],
  providers: [ VoyageListTransformationService, VoyageListApiService ]
})
export class VoyagesModule { }

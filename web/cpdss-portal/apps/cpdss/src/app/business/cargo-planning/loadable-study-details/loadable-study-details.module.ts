import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LoadableStudyDetailsComponent } from './loadable-study-details.component';
import { LoadableStudyDetailsRoutingModule } from './loadable-study-details-routing.module';
import { DatatableModule } from '../../../shared/components/datatable/datatable.module';
import { CargoNominationComponent } from './cargo-nomination/cargo-nomination.component';
import { LoadingPortsPopupComponent } from './cargo-nomination/loading-ports-popup/loading-ports-popup.component';
import { DialogModule } from 'primeng/dialog';
import { ApiTemperatureHistoryPopupComponent } from './cargo-nomination/api-temperature-history-popup/api-temperature-history-popup.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MultiSelectModule } from 'primeng/multiselect';
import { DropdownModule } from 'primeng/dropdown';
import { TranslateModule } from '@ngx-translate/core';
import { VesselInfoModule } from '../../core/components/vessel-info/vessel-info.module';
import { SidePanelLoadableStudyListComponent } from './side-panel-loadable-study-list/side-panel-loadable-study-list.component';
import { NewLoadableStudyPopupModule } from '../../core/components/new-loadable-study-popup/new-loadable-study-popup.module';
import { PortsComponent } from './ports/ports.component';
import { PermissionDirectiveModule } from '../../../shared/directives/permission/permission-directive.module';
import { LoadableQuantityComponent } from './loadable-quantity/loadable-quantity.component';
import { LoadableQuantityApiService } from '../services/loadable-quantity-api.service';
/**
 * Routing Module for Loadable Study Details Screen
 *
 * @export
 * @class LoadableStudyDetailsModule
 */
@NgModule({
  declarations: [LoadableStudyDetailsComponent, CargoNominationComponent, LoadingPortsPopupComponent, ApiTemperatureHistoryPopupComponent, SidePanelLoadableStudyListComponent, PortsComponent, LoadableQuantityComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    LoadableStudyDetailsRoutingModule,
    DatatableModule,
    DialogModule,
    MultiSelectModule,
    DropdownModule,
    TranslateModule,
    VesselInfoModule,
    NewLoadableStudyPopupModule,
    PermissionDirectiveModule
  ],
  providers: [
    LoadableQuantityApiService
  ]
})
export class LoadableStudyDetailsModule { }

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
import { NewLoadableStudyPopupModule } from '../../core/components/new-loadable-study-popup/new-loadable-study-popup.module';
import { OnHandQuantityComponent } from './on-hand-quantity/on-hand-quantity.component';
import { PortsComponent } from './ports/ports.component';
import { PermissionDirectiveModule } from '../../../shared/directives/permission/permission-directive.module';
import { LoadableQuantityComponent } from './loadable-quantity/loadable-quantity.component';
import { LoadableQuantityApiService } from '../services/loadable-quantity-api.service';
import { SidePanelLoadableStudyListModule } from '../../core/components/side-panel-loadable-study-list/side-panel-loadable-study-list.module';
import { ValidationErrorModule } from '../../../shared/components/validation-error/validation-error.module';
import { CommingleComponent } from './commingle/commingle.component';
import { CheckboxModule } from 'primeng/checkbox';
import { ColorPickerModule } from 'primeng/colorpicker';
import { CommingleApiService } from '../services/commingle-api.service';
import { OnBoardQuantityComponent } from './on-board-quantity/on-board-quantity.component';
import { BunkeringLayoutModule } from '../../core/components/bunkering-layout/bunkering-layout.module';
import { CargoTankLayoutModule } from '../../core/components/cargo-tank-layout/cargo-tank-layout.module';

/**
 * Routing Module for Loadable Study Details Screen
 *
 * @export
 * @class LoadableStudyDetailsModule
 */
@NgModule({
  declarations: [LoadableStudyDetailsComponent, CargoNominationComponent, LoadingPortsPopupComponent, ApiTemperatureHistoryPopupComponent, PortsComponent, LoadableQuantityComponent, OnHandQuantityComponent, CommingleComponent, OnBoardQuantityComponent],
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
    SidePanelLoadableStudyListModule,
    PermissionDirectiveModule,
    ValidationErrorModule,
    CheckboxModule,
    ColorPickerModule,
    BunkeringLayoutModule ,
    CargoTankLayoutModule
  ],
  providers: [
    LoadableQuantityApiService, CommingleApiService
  ]
})
export class LoadableStudyDetailsModule { }

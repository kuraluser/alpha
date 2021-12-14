import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DischargeStudyDetailsComponent } from './discharge-study-details.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MultiSelectModule } from 'primeng/multiselect';
import { DropdownModule } from 'primeng/dropdown';
import { TranslateModule } from '@ngx-translate/core';
import { TableModule } from 'primeng/table';
import { InputMaskModule } from 'primeng/inputmask';
import { VesselInfoModule } from '../../core/components/vessel-info/vessel-info.module';
import { ErrorLogPopupModule } from '../../core/components/error-log-popup/error-log-popup.module';
import { PermissionDirectiveModule } from '../../../shared/directives/permission/permission-directive.module';
import { DischargeStudyDetailsRoutingModule } from './discharge-study-details-routing.module';
import { CargoNominationComponent } from './cargo-nomination/cargo-nomination.component';
import { DatatableModule } from '../../../shared/components/datatable/datatable.module';
import { PortsComponent } from './ports/ports.component';
import { OnHandQuantityComponent } from './on-hand-quantity/on-hand-quantity.component'
import { BunkeringLayoutModule } from '../../core/components/bunkering-layout/bunkering-layout.module';
import { DischargeStudyComponent } from './discharge-study/discharge-study.component';
import { QuantityPipeModule } from '../../../shared/pipes/quantity/quantity-pipe.module';
import { QuantityPipe } from '../../../shared/pipes/quantity/quantity.pipe'; 
import { ValidationErrorModule } from '../../../shared/components/validation-error/validation-error.module';
import { DischargeStudyDetailsApiService } from '../services/discharge-study-details-api.service';
import { DischargeStudyDetailsTransformationService } from '../services/discharge-study-details-transformation.service';
import { NumberDirectiveModule } from './../../../shared/directives/number-directive/number-directive.module';

/**
 * Routing Module for Discharge Study Details Screen
 *
 * @export
 * @class DischargeStudyDetailsModule
 */

@NgModule({
  declarations: [DischargeStudyDetailsComponent, CargoNominationComponent , PortsComponent , OnHandQuantityComponent, DischargeStudyComponent ],
  imports: [
    CommonModule,
    FormsModule, 
    ReactiveFormsModule,
    MultiSelectModule,
    DropdownModule,
    TranslateModule,
    VesselInfoModule,
    PermissionDirectiveModule,
    DatatableModule,
    TableModule,
    BunkeringLayoutModule,
    QuantityPipeModule,
    ValidationErrorModule,
    ErrorLogPopupModule,
    InputMaskModule,
    NumberDirectiveModule,
    DischargeStudyDetailsRoutingModule
  ],
  providers: [
    QuantityPipe , 
    DischargeStudyDetailsApiService,
    DischargeStudyDetailsTransformationService
  ]
})
export class DischargeStudyDetailsModule { }

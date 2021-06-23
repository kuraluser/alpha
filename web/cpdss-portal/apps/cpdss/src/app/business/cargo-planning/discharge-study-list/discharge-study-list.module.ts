import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DischargeStudyListComponent } from './discharge-study-list.component';
import { DischargeStudyListRoutingModule } from "./discharge-study-routing.module";
import { TranslateModule } from '@ngx-translate/core';
import { DropdownModule } from 'primeng/dropdown';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CalendarModule } from 'primeng/calendar';
import { InputTextModule } from 'primeng/inputtext';
import { TableModule } from 'primeng/table';
import { DatatableModule } from '../../../shared/components/datatable/datatable.module';
import { PermissionDirectiveModule } from '../../../shared/directives/permission/permission-directive.module';
import { VesselInfoModule } from '../../core/components/vessel-info/vessel-info.module';
import { ValidationErrorModule } from '../../../shared/components/validation-error/validation-error.module';
import { DialogModule } from 'primeng/dialog';
import { NewDischargeStudyPopUpComponent } from '../../core/components/new-discharge-study-pop-up/new-discharge-study-pop-up.component';



/**
 * Module class for DischargeStudyList.
 *
 * @export
 * @class DischargeStudyListModule
 */
@NgModule({
  declarations: [DischargeStudyListComponent, NewDischargeStudyPopUpComponent],
  imports: [
    CommonModule,
    DischargeStudyListRoutingModule,
    TranslateModule,
    DropdownModule  ,
    TableModule,
    FormsModule,
    InputTextModule,
    VesselInfoModule,
    DatatableModule,
    PermissionDirectiveModule,
    ReactiveFormsModule,
    ValidationErrorModule,
    CalendarModule,
    DialogModule
  ]
})
export class DischargeStudyListModule { }

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DatatableModule } from '../../../shared/components/datatable/datatable.module';
import { TranslateModule } from '@ngx-translate/core';
import { DropdownModule } from 'primeng/dropdown';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ValidationErrorModule } from '../../../shared/components/validation-error/validation-error.module';
import { NumberDirectiveModule } from '../../../shared/directives/number-directive/number-directive.module';
import { DialogModule } from 'primeng/dialog';
import { PermissionDirectiveModule } from '../../../shared/directives/permission/permission-directive.module';
import { TableModule } from 'primeng/table';
import { CrewDetailsRoutingModule } from './crew-details-routing.module';
import { CrewListingComponent } from './crew-listing/crew-listing.component';
import { AddCrewComponent } from './add-crew/add-crew.component';
import { MultiSelectModule } from 'primeng/multiselect';

/**
 * Module class for  CrewDetails
 * @export
 * @class CrewDetailsModule
 */
@NgModule({
  declarations: [CrewListingComponent, AddCrewComponent],
  imports: [
    CommonModule,
    DialogModule,
    ReactiveFormsModule,
    FormsModule,
    DatatableModule,
    ValidationErrorModule,
    PermissionDirectiveModule,
    TranslateModule,
    TableModule,
    MultiSelectModule,
    DropdownModule,
    NumberDirectiveModule,
    CrewDetailsRoutingModule
  ]
})
export class CrewDetailsModule { }

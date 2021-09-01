import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PortListingComponent } from './port-listing/port-listing.component';
import { PortMasterRoutingModule } from './port-master-routing.module';
import { DatatableModule } from '../../../shared/components/datatable/datatable.module';
import { TranslateModule } from '@ngx-translate/core';
import { DropdownModule } from 'primeng/dropdown';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AddPortComponent } from './add-port/add-port.component';
import { ValidationErrorModule } from '../../../shared/components/validation-error/validation-error.module';
import { NumberDirectiveModule } from '../../../shared/directives/number-directive/number-directive.module';
import { AddBerthComponent } from './add-berth/add-berth.component';
import { PortMasterMapComponent } from './port-master-map/port-master-map.component';
import { DialogModule } from 'primeng/dialog';



/**
 * Module class for  PortMaster Module
 * @export
 * @class PortMasterModule
 */
@NgModule({
  declarations: [PortListingComponent,AddPortComponent, AddBerthComponent, PortMasterMapComponent],
  imports: [
    CommonModule,
    DialogModule,
    ReactiveFormsModule,
    FormsModule,
    ValidationErrorModule,
    PortMasterRoutingModule,
    DatatableModule,
    TranslateModule,
    DropdownModule,
    NumberDirectiveModule,
    DatatableModule  
  ]
})
export class PortMasterModule { }

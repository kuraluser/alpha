import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ValidationErrorModule } from './../../shared/components/validation-error/validation-error.module';
import { DropdownModule } from 'primeng/dropdown';
import { PermissionDirectiveModule } from './../../shared/directives/permission/permission-directive.module';

import { FleetRoutingModule } from './fleet-routing.module';
import { FleetComponent } from './fleet.component';
import { TranslateModule } from '@ngx-translate/core';
import { FleetMapComponent } from './fleet-map/fleet-map.component';
import { FleetVesselCardComponent } from './fleet-vessel-card/fleet-vessel-card.component';

/**
 * Module will import all the dependencies for Fleet module
 *
 * @export
 * @class FleetModule
 */
@NgModule({
  declarations: [FleetComponent, FleetMapComponent, FleetVesselCardComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    ValidationErrorModule,
    TranslateModule,
    DropdownModule,
    FleetRoutingModule,
    PermissionDirectiveModule
  ]
})
export class FleetModule { }

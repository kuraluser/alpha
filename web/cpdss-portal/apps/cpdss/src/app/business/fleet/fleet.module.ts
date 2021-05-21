import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FleetRoutingModule } from './fleet-routing.module';
import { FleetComponent } from './fleet.component';
import { FleetMapComponent } from './fleet-map/fleet-map.component';
import { FleetVesselNotificationsComponent } from './fleet-vessel-notifications/fleet-vessel-notifications.component';
import { FleetVesselCardComponent } from './fleet-vessel-card/fleet-vessel-card.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TranslateModule } from '@ngx-translate/core';
import { ValidationErrorModule } from './../../shared/components/validation-error/validation-error.module';
import { DropdownModule } from 'primeng/dropdown';
import { CalendarModule } from 'primeng/calendar';
import { PermissionDirectiveModule } from './../../shared/directives/permission/permission-directive.module';
import { MomentFormatPipe } from '../../shared/pipes/moment-pipe/moment-format.pipe';
import { MomentFormatModule } from './../../shared/pipes/moment-pipe/moment-format.module';

/**
 * Module will import all the dependencies for Fleet module
 *
 * @export
 * @class FleetModule
 */
@NgModule({
  declarations: [FleetComponent, FleetMapComponent, FleetVesselCardComponent, FleetVesselNotificationsComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    TranslateModule,
    CalendarModule,
    ValidationErrorModule,
    DropdownModule,
    PermissionDirectiveModule,
    FleetRoutingModule,
    MomentFormatModule,
  ],
  providers: [MomentFormatPipe]
})
export class FleetModule { }

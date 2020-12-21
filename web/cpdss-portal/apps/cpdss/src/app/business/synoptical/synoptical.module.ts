import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SynopticalRoutingModule } from './synoptical-routing.module';
import { SynopticalComponent } from './synoptical.component';
import { VesselInfoModule } from '../core/components/vessel-info/vessel-info.module';
import { TranslateModule } from '@ngx-translate/core';
import { FormsModule } from '@angular/forms';
import { DropdownModule } from 'primeng/dropdown';
import { LoadableStudyListApiService } from '../cargo-planning/services/loadable-study-list-api.service';
import { SynopticalTableComponent } from './synoptical-table/synoptical-table.component';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';

/**
 * Module for Synoptical Table
 *
 * @export
 * @class SynopticalModule
 */

@NgModule({
  declarations: [SynopticalComponent, SynopticalTableComponent],
  imports: [
    CommonModule,
    SynopticalRoutingModule,
    FormsModule,
    TableModule,
    VesselInfoModule,
    TranslateModule,
    DropdownModule,
    ButtonModule
  ],
  providers: [LoadableStudyListApiService]
})
export class SynopticalModule { }

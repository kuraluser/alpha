import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { LoadableStudyListComponent } from './loadable-study-list.component';
import { LoadableStudyListRoutingModule } from './loadable-study-list-routing.module';
import { DropdownModule } from 'primeng/dropdown';
import { TableModule } from 'primeng/table';
import { InputTextModule } from 'primeng/inputtext';
import { CalendarModule } from 'primeng/calendar';
import { TranslateModule } from '@ngx-translate/core';
import { NewLoadableStudyPopupModule } from "../../core/components/new-loadable-study-popup/new-loadable-study-popup.module";
import { VesselInfoModule } from '../../core/components/vessel-info/vessel-info.module';
import { DatatableModule } from '../../../shared/components/datatable/datatable.module';


/**
 *  module will import all dependencies for loadable-study-list
 */
@NgModule({
  declarations: [LoadableStudyListComponent],
  imports: [
    CommonModule,
    DropdownModule,
    TableModule,
    FormsModule,
    InputTextModule,
    CalendarModule,
    TranslateModule,
    LoadableStudyListRoutingModule,
    NewLoadableStudyPopupModule,
    VesselInfoModule,
    DatatableModule
  ]
})
export class LoadableStudyListModule { }

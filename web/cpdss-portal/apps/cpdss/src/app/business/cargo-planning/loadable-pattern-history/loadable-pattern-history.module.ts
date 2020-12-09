import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LoadablePatternHistoryRoutingModule } from './loadable-pattern-history-routing.module';
import { VesselInfoModule } from '../../core/components/vessel-info/vessel-info.module';
import { FormsModule } from '@angular/forms';
import { LoadablePatternHistoryComponent } from './loadable-pattern-history.component';
import { SidePanelLoadableStudyListModule } from '../../core/components/side-panel-loadable-study-list/side-panel-loadable-study-list.module';
import { PatternCaseComponent } from './pattern-case/pattern-case.component';
import { GradeLoadingOrderComponent } from './pattern-case/grade-loading-order/grade-loading-order.component';
import { CargoPriorityGridComponent } from './pattern-case/cargo-priority-grid/cargo-priority-grid.component';
import { CargoTankLayoutComponent } from './cargo-tank-layout/cargo-tank-layout.component';
import { DropdownModule } from 'primeng/dropdown';
import { TranslateModule } from '@ngx-translate/core';

/**
 * Routing Module for Loadable Pattern History Screen
 *
 * @export
 * @class LoadablePatternHistoryModule
 */
@NgModule({
  declarations: [LoadablePatternHistoryComponent, PatternCaseComponent, GradeLoadingOrderComponent, CargoPriorityGridComponent, CargoTankLayoutComponent ],
  imports: [
    CommonModule,
    FormsModule,
    LoadablePatternHistoryRoutingModule,
    VesselInfoModule,
    SidePanelLoadableStudyListModule,
    DropdownModule,
    TranslateModule
  ]
})
export class LoadablePatternHistoryModule { }

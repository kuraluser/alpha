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
import { DropdownModule } from 'primeng/dropdown';
import { TranslateModule } from '@ngx-translate/core';
import { TableModule } from 'primeng/table';
import { DatatableModule } from '../../../shared/components/datatable/datatable.module';
import { DialogModule } from 'primeng/dialog';
import { ConstraintComponent } from './pattern-case/constraint/constraint.component';
import { CommingleCargoDetailsPopUpComponent } from './commingle-cargo-details-pop-up/commingle-cargo-details-pop-up.component';
import { CargoTankLayoutModule } from '../../core/components/cargo-tank-layout/cargo-tank-layout.module';
import { PermissionDirectiveModule } from '../../../shared/directives/permission/permission-directive.module';

/**
 * Routing Module for Loadable Pattern History Screen
 *
 * @export
 * @class LoadablePatternHistoryModule
 */
@NgModule({
  declarations: [LoadablePatternHistoryComponent, PatternCaseComponent, GradeLoadingOrderComponent, CargoPriorityGridComponent, ConstraintComponent, CommingleCargoDetailsPopUpComponent ],
  imports: [
    CommonModule,
    FormsModule,
    LoadablePatternHistoryRoutingModule,
    VesselInfoModule,
    SidePanelLoadableStudyListModule,
    DropdownModule,
    TranslateModule,
    TableModule,
    DatatableModule,
    DialogModule,
    CargoTankLayoutModule,
    PermissionDirectiveModule
  ],
  exports : [PatternCaseComponent, GradeLoadingOrderComponent, CargoPriorityGridComponent, ConstraintComponent, CommingleCargoDetailsPopUpComponent]
})
export class LoadablePatternHistoryModule { }

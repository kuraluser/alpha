import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SidePanelLoadableStudyListComponent } from './side-panel-loadable-study-list.component';
import { TranslateModule } from '@ngx-translate/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DatatableModule } from '../../../../shared/components/datatable/datatable.module';
import { NewLoadableStudyPopupModule } from '../new-loadable-study-popup/new-loadable-study-popup.module';
import { PermissionDirectiveModule } from '../../../../shared/directives/permission/permission-directive.module';

/**
 *  this module will load all the dependencies of new-loadable-study-popup component
 */
@NgModule({
  declarations: [SidePanelLoadableStudyListComponent],
  imports: [
    CommonModule,
    TranslateModule,
    ReactiveFormsModule,
    FormsModule,
    DatatableModule,
    NewLoadableStudyPopupModule,
    PermissionDirectiveModule
  ],
  exports: [SidePanelLoadableStudyListComponent]
})
export class SidePanelLoadableStudyListModule { }

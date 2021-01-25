import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NewLoadableStudyPopupComponent } from './new-loadable-study-popup.component';
import { ReactiveFormsModule } from '@angular/forms';
import { DropdownModule } from 'primeng/dropdown';
import { FileUploadModule } from 'primeng/fileupload';
import { TranslateModule } from '@ngx-translate/core';
import { DialogModule } from 'primeng/dialog';
import { TooltipModule } from 'primeng/tooltip';
import { NumberDirectiveModule } from '../../../../shared/directives/number-directive/number-directive.module';

/**
 *  this module will load all the dependencies of new-loadable-study-popup component
 */
@NgModule({
  declarations: [NewLoadableStudyPopupComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    DropdownModule,
    FileUploadModule,
    TranslateModule,
    DialogModule,
    TooltipModule,
    NumberDirectiveModule
  ],
  exports: [NewLoadableStudyPopupComponent]
})
export class NewLoadableStudyPopupModule { }

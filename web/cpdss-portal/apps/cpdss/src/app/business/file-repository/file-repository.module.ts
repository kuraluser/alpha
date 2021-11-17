import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FileRepositoryRoutingModule } from './file-repository-routing.module';
import { FileRepositoryComponent } from './file-repository.component';
import { VesselInfoModule } from '../core/components/vessel-info/vessel-info.module';
import { TranslateModule } from '@ngx-translate/core';
import { DatatableModule } from '../../shared/components/datatable/datatable.module';
import { FileRepositoryTransformationService } from './services/file-repository-transformation.service';
import { FileRepositoryApiService } from './services/file-repository-api.service';
import { AddEditFileComponent } from './add-edit-file/add-edit-file.component';
import { DialogModule } from 'primeng/dialog';
import { DropdownModule } from 'primeng/dropdown';
import { ValidationErrorModule } from '../../shared/components/validation-error/validation-error.module';
import { TooltipModule } from 'primeng/tooltip';

/**
 * Module for file repository
 *
 * @export
 * @class FileRepositoryModule
 */
@NgModule({
  declarations: [FileRepositoryComponent, AddEditFileComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    FileRepositoryRoutingModule,
    ValidationErrorModule,
    VesselInfoModule,
    TranslateModule,
    DatatableModule,
    DialogModule,
    DropdownModule,
    TooltipModule
  ],
  providers: [FileRepositoryTransformationService, FileRepositoryApiService]
})
export class FileRepositoryModule { }

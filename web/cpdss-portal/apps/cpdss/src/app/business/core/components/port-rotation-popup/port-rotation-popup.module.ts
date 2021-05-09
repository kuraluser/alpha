import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PortRotationPopupComponent } from './port-rotation-popup.component';
import { DialogModule } from 'primeng/dialog';
import { PortRotationPopupTransformationService } from './services/port-rotation-popup-transformation.service';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DatatableModule } from '../../../../shared/components/datatable/datatable.module';
import { TranslateModule } from '@ngx-translate/core';
import { PortRotationPopupApiService } from './services/port-rotation-popup-api.service';


/**
 * Routing Module for Port Rotation Popup Screen
 *
 * @export
 * @class PortRotationPopupModule
 */
@NgModule({
  declarations: [PortRotationPopupComponent],
  imports: [
    CommonModule,
    DialogModule,
    FormsModule,
    ReactiveFormsModule,
    DatatableModule,
    TranslateModule
  ],
  exports: [PortRotationPopupComponent],
  providers: [PortRotationPopupTransformationService, PortRotationPopupApiService]
})
export class PortRotationPopupModule { }

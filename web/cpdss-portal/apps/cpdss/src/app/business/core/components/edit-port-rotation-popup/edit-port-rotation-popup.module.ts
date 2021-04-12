import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EditPortRotationPopupComponent } from './edit-port-rotation-popup.component';
import { TranslateModule } from '@ngx-translate/core';
import { DialogModule } from 'primeng/dialog';
import { PortRotationService } from '../../services/port-rotation.service';
import { OrderListModule } from 'primeng/orderlist';

/**
 *  Module for port ribbon component
 */
@NgModule({
  declarations: [EditPortRotationPopupComponent],
  imports: [
    CommonModule,
    TranslateModule,
    DialogModule,
    OrderListModule,
  ],
  providers: [PortRotationService],
  exports: [EditPortRotationPopupComponent]
})
export class EditPortRotationPopupModule { }

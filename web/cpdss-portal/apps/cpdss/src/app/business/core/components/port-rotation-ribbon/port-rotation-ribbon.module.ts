import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PortRotationRibbonComponent } from './port-rotation-ribbon.component';
import { PortRotationService } from '../../services/port-rotation.service';
import { CarouselModule } from 'primeng/carousel';
import { TranslateModule } from '@ngx-translate/core';
import { CalendarModule } from 'primeng/calendar';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ValidationErrorModule } from '../../../../shared/components/validation-error/validation-error.module';

/**
 *  Module for port ribbon component
 */
@NgModule({
  declarations: [PortRotationRibbonComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    ValidationErrorModule,
    CalendarModule,
    CarouselModule,
    TranslateModule
  ],
  providers: [PortRotationService],
  exports: [PortRotationRibbonComponent]
})
export class PortRotationRibbonModule { }

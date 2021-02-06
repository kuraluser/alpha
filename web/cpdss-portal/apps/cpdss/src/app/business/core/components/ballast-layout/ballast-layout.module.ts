import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BallastLayoutComponent } from './ballast-layout.component';
import { OverlayPanelModule } from 'primeng/overlaypanel';
import { TranslateModule } from '@ngx-translate/core';


/**
 * Module for ballast tank layount coponent
 *
 * @export
 * @class BallastLayoutModule
 */
@NgModule({
  declarations: [BallastLayoutComponent],
  imports: [
    CommonModule,
    OverlayPanelModule,
    TranslateModule
  ],
  exports: [BallastLayoutComponent]
})
export class BallastLayoutModule { }

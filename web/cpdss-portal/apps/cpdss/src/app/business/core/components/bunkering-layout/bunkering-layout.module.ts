import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BunkeringLayoutComponent } from './bunkering-layout.component';
import { OverlayPanelModule } from 'primeng/overlaypanel';
import { TranslateModule } from '@ngx-translate/core';


/**
 * Module for bunkering layout component
 *
 * @export
 * @class BunkeringLayoutModule
 */
@NgModule({
  declarations: [BunkeringLayoutComponent],
  imports: [
    CommonModule,
    OverlayPanelModule,
    TranslateModule
  ],
  exports: [BunkeringLayoutComponent]
})
export class BunkeringLayoutModule { }

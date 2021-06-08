import { ModuleWithProviders, NgModule, Optional, SkipSelf } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ConfirmationAlertComponent } from './confirmation-alert.component';
import { ConfirmationAlertService } from './confirmation-alert.service';
import { ToastModule } from 'primeng/toast';
import { TranslateModule } from '@ngx-translate/core';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ConfirmationService } from 'primeng/api';


/**
 * Module for confirmation component
 *
 * @export
 * @class ConfirmationAlertModule
 */
@NgModule({
  declarations: [ConfirmationAlertComponent],
  imports: [
    CommonModule,
    ToastModule,
    TranslateModule,
    ConfirmDialogModule
  ],
  exports: [ConfirmationAlertComponent]
})
export class ConfirmationAlertModule {
  constructor(@Optional() @SkipSelf() parentModule?: ConfirmationAlertModule) {
    if (parentModule) {
      throw new Error(
        'ConfirmationAlertModule is already loaded. Import it in the AppModule only');
    }
  }

  static forRoot(): ModuleWithProviders<ConfirmationAlertModule> {
    return {
      ngModule: ConfirmationAlertModule,
      providers: [
        { provide: ConfirmationAlertService },
        { provide: ConfirmationService}
      ]
    };
  }
}

import { ModuleWithProviders, NgModule, Optional, SkipSelf } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ToastAlertComponent } from './toast-alert.component';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';


/**
 * Module for Toast Alert component
 *
 * @export
 * @class ToastAlertModule
 */
@NgModule({
  declarations: [ToastAlertComponent],
  imports: [
    CommonModule,
    ToastModule
  ],
  exports: [ToastAlertComponent]
})
export class ToastAlertModule {
  constructor(@Optional() @SkipSelf() parentModule?: ToastAlertModule) {
    if (parentModule) {
      throw new Error(
        'ToastAlertModule is already loaded. Import it in the AppModule only');
    }
  }

  static forRoot(): ModuleWithProviders<ToastAlertModule> {
    return {
      ngModule: ToastAlertModule,
      providers: [
        { provide: MessageService }
      ]
    };
  }
}

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';

import { AccessDeniedRoutingModule } from './access-denied-routing.module';
import { AccessDeniedComponent } from './access-denied.component';
import { AccessDeniedApiService } from './access-denied-api.service';

/**
 * Module for access denied
 *
 * @export
 * @class AccessDeniedModule
 */
@NgModule({
  declarations: [ AccessDeniedComponent ],
  imports: [
    CommonModule,
    AccessDeniedRoutingModule,
    TranslateModule
  ],
  providers: [ AccessDeniedApiService ]
})
export class AccessDeniedModule { }

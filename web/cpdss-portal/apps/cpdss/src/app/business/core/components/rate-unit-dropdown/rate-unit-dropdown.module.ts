import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RateUnitDropdownComponent } from './rate-unit-dropdown.component';
import { DropdownModule } from 'primeng/dropdown';
import { TranslateModule } from '@ngx-translate/core';
import { FormsModule } from '@angular/forms';


/**
 * Module for rate unit dropdown component
 *
 * @export
 * @class RateUnitDropdownModule
 */
@NgModule({
  declarations: [RateUnitDropdownComponent],
  imports: [
    CommonModule,
    FormsModule,
    DropdownModule,
    TranslateModule
  ],
  exports: [RateUnitDropdownComponent]
})
export class RateUnitDropdownModule { }

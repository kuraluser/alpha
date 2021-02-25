import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import  {UnitDropdownComponent } from './unit-dropdown.component'
import { FormsModule } from '@angular/forms';
import { DropdownModule } from 'primeng/dropdown';


/**
 * Module for Unit Dropdown
 *
 * @export
 * @class UnitDropdownModule
 */
@NgModule({
  declarations: [
    UnitDropdownComponent,
  ],
  exports: [
    UnitDropdownComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    DropdownModule
  ]
})
export class UnitDropdownModule { }

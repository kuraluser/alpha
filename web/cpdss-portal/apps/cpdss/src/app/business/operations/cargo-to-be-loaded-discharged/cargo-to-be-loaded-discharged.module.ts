import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CargoToBeLoadedDischargedComponent } from './cargo-to-be-loaded-discharged.component';
import { DatatableModule } from '../../../shared/components/datatable/datatable.module'
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

/**
 * Module for car go to be loaded / disharged component
 *
 * @export
 * @class CargoToBeLoadedDischargedModule
 */
@NgModule({
  declarations: [CargoToBeLoadedDischargedComponent],
  imports: [
    CommonModule,
    DatatableModule,
    FormsModule,
    ReactiveFormsModule
  ],
  exports: [CargoToBeLoadedDischargedComponent]
})
export class CargoToBeLoadedDischargedModule { }

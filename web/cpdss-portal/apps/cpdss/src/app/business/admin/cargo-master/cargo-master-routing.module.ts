import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CargoDetailsComponent } from './cargo-details/cargo-details.component';
import { CargoMasterComponent } from './cargo-master.component';

const routes: Routes = [
  {
    path: '',
    component: CargoMasterComponent
  },
  {
    path: ':cargoId',
    component: CargoDetailsComponent
  }

];

/**
 * Routing module class for cargo master
 *
 * @export
 * @class CargoMasterRoutingModule
 */
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CargoMasterRoutingModule { }

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CargoHistoryComponent } from './cargo-history.component';

const routes: Routes = [{path:'', component: CargoHistoryComponent}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CargoHistoryRoutingModule { }

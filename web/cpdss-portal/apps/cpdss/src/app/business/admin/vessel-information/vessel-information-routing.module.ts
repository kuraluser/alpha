import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { VesselInformationComponent } from './vessel-information.component';

/**
 * routing module for Vessel Information
 */
const routes: Routes = [
  {
    path: '',
    component: VesselInformationComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class VesselInformationRoutingModule { }

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { VesselInformationComponent } from './vessel-information.component';
import { VesselManagementComponent } from './Vessel-management/vessel-management.component';

/**
 * routing module for Vessel Information
 */
const routes: Routes = [
  {
    path: '',
    component: VesselInformationComponent
  },
  {
    path: 'vessel/:vesselId',
    component: VesselManagementComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class VesselInformationRoutingModule { }

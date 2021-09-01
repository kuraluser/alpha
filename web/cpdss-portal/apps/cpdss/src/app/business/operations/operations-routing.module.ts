import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { OperationsComponent } from './operations.component';

const routes: Routes = [
  {
    path: '', component: OperationsComponent,
    children: [
      { path: 'loading/:vesselId/:voyageId/:portRotationId', loadChildren: () => import('./loading/loading.module').then(m => m.LoadingModule) },
      { path: 'discharging/:vesselId/:voyageId/:portRotationId', loadChildren: () => import('./discharging/discharging.module').then(m => m.DischargingModule) },
      { path: 'bunkering', loadChildren: () => import('./bunkering/bunkering.module').then(m => m.BunkeringModule) }
    ]
  },
];

/**
 * Module for routing in operations module
 *
 * @export
 * @class OperationsRoutingModule
 */
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class OperationsRoutingModule { }

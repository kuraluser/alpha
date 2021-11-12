import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BunkeringComponent } from './bunkering.component';
import { UnsavedChangesGuard } from './../../../shared/services/guards/unsaved-data-guard';

const routes: Routes = [
  {
    path: '',
    component: BunkeringComponent,
    canDeactivate: [UnsavedChangesGuard]
  },
];

/**
 * Router module for bunkering module
 *
 * @export
 * @class BunkeringRoutingModule
 */
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BunkeringRoutingModule { }

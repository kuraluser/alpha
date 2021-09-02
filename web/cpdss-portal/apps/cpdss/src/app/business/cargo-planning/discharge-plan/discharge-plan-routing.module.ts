import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DischargePlanComponent } from './discharge-plan.component';

const routes: Routes = [
  {
    path: '',
    component: DischargePlanComponent
  }
];

/**
 *  this module will load routes for the discharge plan.
*/
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DischargePlanRoutingModule { }

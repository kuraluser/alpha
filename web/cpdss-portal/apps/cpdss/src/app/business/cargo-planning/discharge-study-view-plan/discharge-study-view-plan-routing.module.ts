import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DischargeStudyComponent } from './discharge-study.component';

const routes: Routes = [
  {
    path: '',
    component: DischargeStudyComponent
  }
];

/**
 *  this module will load routes for the discharge study plan.
*/
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DischargeStudyViewPlanRoutingModule { }

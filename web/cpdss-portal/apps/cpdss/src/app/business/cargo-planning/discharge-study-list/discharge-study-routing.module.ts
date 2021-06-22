import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {DischargeStudyListComponent} from './discharge-study-list.component';

const routes: Routes = [
  {
    path: '',
    component: DischargeStudyListComponent
  }
];

/**
 *  this module will load routes for the discharge study list.
 */
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DischargeStudyListRoutingModule { }

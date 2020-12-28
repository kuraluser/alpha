import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { LoadablePlanComponent } from './loadable-plan.component';

const routes: Routes = [{ path: '', component: LoadablePlanComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class LoadablePlanRoutingModule { }

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { VoyageStatusComponent } from './voyage-status.component';

const routes: Routes = [{ path: '', component: VoyageStatusComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class VoyageStatusRoutingModule { }

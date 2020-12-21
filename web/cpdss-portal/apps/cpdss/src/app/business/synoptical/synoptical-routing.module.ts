import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SynopticalComponent } from './synoptical.component';

const routes: Routes = [
  {
    path: '',
    component: SynopticalComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SynopticalRoutingModule { }

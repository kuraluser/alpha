import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SynopticalTableComponent } from './synoptical-table/synoptical-table.component';
import { SynopticalComponent } from './synoptical.component';

const routes: Routes = [
  {
    path: '',
    component: SynopticalComponent,
  },
  {
    path: ':vesselId/:voyageId',
    component: SynopticalComponent,
    children: [
      {
        path:':loadableStudyId',
        component: SynopticalTableComponent
      },
      {
        path:':loadableStudyId/:loadablePatternId',
        component: SynopticalTableComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SynopticalRoutingModule { }

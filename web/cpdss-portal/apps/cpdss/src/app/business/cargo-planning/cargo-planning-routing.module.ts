import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CargoPlanningComponent } from './cargo-planning.component';

const routes: Routes = [
  {
    path: '',
    component: CargoPlanningComponent,
    children: [
      {
        path: 'loadable-stusy-list',
        loadChildren: () => import('./loadable-study-list/loadable-study-list.module').then(m => m.LoadableStudyListModule)
      },
      {
        path: 'loadable-study-details',
        loadChildren: () => import('./loadable-study-details/loadable-study-details.module').then(m => m.LoadableStudyDetailsModule)
      },
      {
        path: 'loadable-pattern-history',
        loadChildren: () => import('./loadable-pattern-history/loadable-pattern-history.module')
          .then(m => m.LoadablePatternHistoryModule)
      },
      {
        path: 'loadable-plan',
        loadChildren: () => import('./loadable-plan/loadable-plan.module').then(m => m.LoadablePlanModule)
      }
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CargoPlanningRoutingModule { }

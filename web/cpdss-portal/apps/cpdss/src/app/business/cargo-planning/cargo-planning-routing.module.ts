import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CargoPlanningComponent } from './cargo-planning.component';

const routes: Routes = [
  {
    path: '',
    component: CargoPlanningComponent,
    children: [
   
      {
        path: 'loadable-study-list',
        loadChildren: () => import('./loadable-study-list/loadable-study-list.module').then(m => m.LoadableStudyListModule)
      },
      {
        path: 'loadable-study-details/:vesselId/:voyageId/:loadableStudyId',
        loadChildren: () => import('./loadable-study-details/loadable-study-details.module').then(m => m.LoadableStudyDetailsModule)
      },
      {
        path: 'loadable-pattern-history/:isViewPattern/:vesselId/:voyageId/:loadableStudyId',
        loadChildren: () => import('./loadable-pattern-history/loadable-pattern-history.module').then(m => m.LoadablePatternHistoryModule)
      },
      {
        path: 'loadable-plan/:vesselId/:voyageId/:loadableStudyId/:loadablePatternId',
        loadChildren: () => import('./loadable-plan/loadable-plan.module').then(m => m.LoadablePlanModule)
      },
      {
        path: 'cargo-history',
        loadChildren: () => import('./cargo-history/cargo-history.module').then(m => m.CargoHistoryModule)
      },
      {
        path: 'discharge-study-list',
        loadChildren: () => import('./discharge-study-list/discharge-study-list.module').then(m => m.DischargeStudyListModule)
      },
      {
        path: 'discharge-study-details/:vesselId/:voyageId/:dischargeStudyId',
        loadChildren: () => import('./discharge-study-details/discharge-study-details.module').then(m => m.DischargeStudyDetailsModule)
      },
      {
        path: 'discharge-plan/:vesselId/:voyageId/:dischargeStudyId',
        loadChildren: () => import('./discharge-plan/discharge-plan.module').then(m => m.DischargePlanModule)
      }
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CargoPlanningRoutingModule { }

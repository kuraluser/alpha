import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoadableStudyListComponent } from './loadable-study-list.component';

const routes: Routes = [{ path: '', component: LoadableStudyListComponent }];

/**
 *  this module will load routes for the loadable-study-list
 */
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class LoadableStudyListRoutingModule { }

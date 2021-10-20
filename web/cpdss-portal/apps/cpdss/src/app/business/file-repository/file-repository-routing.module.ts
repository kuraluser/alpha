import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { FileRepositoryComponent } from './file-repository.component';

const routes: Routes = [
  {
    path: '',
    component: FileRepositoryComponent
  }
];

/**
 * Module for routing in file repository module
 *
 * @export
 * @class FileRepositoryRoutingModule
 */
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FileRepositoryRoutingModule { }

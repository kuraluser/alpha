import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoadableStudyDetailsComponent } from './loadable-study-details.component';

const routes: Routes = [
    {
        path: '',
        component: LoadableStudyDetailsComponent
    },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class LoadableStudyDetailsRoutingModule { }

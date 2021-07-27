import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoadingComponent } from './loading.component';
import { UnsavedChangesGuard } from './../../../shared/services/guards/unsaved-data-guard';

const routes: Routes = [
    {
        path: '',
        component: LoadingComponent,
        canDeactivate: [UnsavedChangesGuard]
    },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class LoadingRoutingModule { }
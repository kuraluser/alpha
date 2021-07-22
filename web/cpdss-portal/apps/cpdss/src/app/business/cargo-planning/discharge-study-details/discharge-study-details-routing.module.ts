import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DischargeStudyDetailsComponent } from './discharge-study-details.component';
import { UnsavedChangesGuard } from '../../../shared/services/guards/unsaved-data-guard';

const routes: Routes = [
    {
        path: '',
        component: DischargeStudyDetailsComponent,
        canDeactivate: [UnsavedChangesGuard]
    },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class DischargeStudyDetailsRoutingModule { }

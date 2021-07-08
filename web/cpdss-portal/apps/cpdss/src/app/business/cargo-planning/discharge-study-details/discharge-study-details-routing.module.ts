import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DischargeStudyDetailsComponent } from './discharge-study-details.component';

const routes: Routes = [
    {
        path: '',
        component: DischargeStudyDetailsComponent
    },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class DischargeStudyDetailsRoutingModule { }

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoadableStudyDetailsComponent } from './loadable-study-details.component';

const routes: Routes = [
    {
        path: '',
        component: LoadableStudyDetailsComponent,
        /* children: [
            // { path: '', redirectTo: 'cargo-nomination', pathMatch: 'full' },
            // { path: 'cargo-nomination', component: CargoNominationComponent },
            // { path: 'ports', component: PortsComponent }
        ] */
    },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class LoadableStudyDetailsRoutingModule { }

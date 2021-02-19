import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoadablePatternHistoryComponent } from './loadable-pattern-history.component';


const routes: Routes = [
    {
        path: '',
        component: LoadablePatternHistoryComponent
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class LoadablePatternHistoryRoutingModule { }

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DischargingComponent } from './discharging.component';
import { UnsavedChangesGuard } from '../../../shared/services/guards/unsaved-data-guard';

const routes: Routes = [
    {
        path: '',
        component: DischargingComponent,
        canDeactivate: [UnsavedChangesGuard]
    },
];

/**
 * Module for discharging module routing
 *
 * @export
 * @class DischargingRoutingModule
 */
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class DischargingRoutingModule { }

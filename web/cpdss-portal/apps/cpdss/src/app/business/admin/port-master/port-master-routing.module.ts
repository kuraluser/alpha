import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PortListingComponent } from './port-listing/port-listing.component';
import {AddPortComponent} from './add-port/add-port.component';


const routes: Routes = [
    {
        path: '',
        component: PortListingComponent
    },
    {
        path: ':portId',
        component: AddPortComponent
    }
];

/**
 * Routing Module for Port Master Routing Module
 *
 * @export
 * @class PortMasterRoutingModule
 */

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class PortMasterRoutingModule { }

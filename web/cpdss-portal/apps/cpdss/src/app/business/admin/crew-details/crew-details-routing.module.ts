import { CrewListingComponent } from './crew-listing/crew-listing.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {AddPortComponent} from '../port-master/add-port/add-port.component';


const routes: Routes = [
    {
        path: '',
        component: CrewListingComponent
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
 * @class CrewDetailsRoutingModule
 */

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class CrewDetailsRoutingModule { }

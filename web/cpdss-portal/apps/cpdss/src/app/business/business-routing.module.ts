import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BusinessRoutingConfig } from './core/routing-configuration/business-routing.config.ship';

const routes: Routes = BusinessRoutingConfig.getRoutesForEnvironment();

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BusinessRoutingModule { }

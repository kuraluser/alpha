import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AppRoutingConfig } from './routing-configuration/app-routing.config.ship';

const routes: Routes = AppRoutingConfig.getRoutesForEnvironment();

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

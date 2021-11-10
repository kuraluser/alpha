import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MenuModule } from 'primeng/menu';
import { TranslateModule } from '@ngx-translate/core';
import { SimulatorComponent } from './simulator.component';
import { SimulatorApiService } from './services/simulator-api.service';

/**
 * Module for simulator
 *
 * @export
 * @class SimulatorModule
 */
@NgModule({
  declarations: [SimulatorComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    MenuModule,
    TranslateModule
  ],
  exports: [SimulatorComponent],
  providers: [SimulatorApiService]
})
export class SimulatorModule { }

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from './navbar.component';
import { RouterModule } from '@angular/router';
import { TranslateModule } from '@ngx-translate/core';
import { NavbarApiService } from './navbar-api.service';
import { TooltipModule } from 'primeng/tooltip';

@NgModule({
  declarations: [NavbarComponent],
  imports: [
    CommonModule,
    RouterModule,
    TranslateModule,
    TooltipModule
  ],
  exports: [
    NavbarComponent
  ],
  providers: [ NavbarApiService ]
})
export class NavbarModule { }

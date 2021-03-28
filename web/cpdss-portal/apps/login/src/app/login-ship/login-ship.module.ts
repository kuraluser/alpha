import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginShipComponent } from './login-ship.component';
import { LoginShipRoutingModule } from './login-ship-routing.module';
import { TranslateModule } from '@ngx-translate/core';
import { ReactiveFormsModule } from '@angular/forms';
import { CarouselModule } from 'ngx-owl-carousel-o';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';

// this module will load all the dependencies of ship-login

@NgModule({
  declarations: [LoginShipComponent],
  imports: [
    CommonModule,
    LoginShipRoutingModule,
    TranslateModule,
    ReactiveFormsModule,
    CarouselModule,
    ToastModule
  ],
  providers: [
    MessageService
  ]
})
export class LoginShipModule { }

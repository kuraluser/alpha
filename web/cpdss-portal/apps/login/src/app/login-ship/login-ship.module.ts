import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginShipComponent } from './login-ship.component';
import { LoginShipRoutingModule } from './login-ship-routing.module';
import { TranslateModule } from '@ngx-translate/core';



@NgModule({
  declarations: [LoginShipComponent],
  imports: [
    CommonModule,
    LoginShipRoutingModule,
    TranslateModule
  ]
})
export class LoginShipModule { }

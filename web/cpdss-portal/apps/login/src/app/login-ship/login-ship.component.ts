import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LoginShipService } from '../login-ship/services/login-ship.service';
import { IAppConfiguration } from '../shared/services/app-configuration/app-configuration.model';
import { AppConfigurationService } from '../shared/services/app-configuration/app-configuration.service';
import { ILoginShip } from './model/login-ship.model';
/**
 * Component for Ship login
 *
 * @export
 * @class LoginShipComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'app-login',
  templateUrl: './login-ship.component.html',
  styleUrls: ['./login-ship.component.scss']
})
export class LoginShipComponent implements OnInit {
  loginForm: FormGroup;
  settings: IAppConfiguration;

  constructor(private fb: FormBuilder,
    private loginShipService: LoginShipService
    ) { }

  /**
   * Method called on intialization of the component
   *
   * @memberof LoginShipComponent
   */
  ngOnInit(): void {
    this.settings = AppConfigurationService.settings;
    this.loginForm = this.fb.group({
      userName: ['', [Validators.required]],
      password: ['', [Validators.required, Validators.pattern('^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*[-+_!@#$%^&*.,?]).+$'), Validators.minLength(7)]]
    })
  }

  /**
   * Submit form
   */
  async onSubmit() {
    const loginData: ILoginShip = {
      'username': this.loginForm.controls.userName.value,
      'password': this.loginForm.controls.password.value,
    }
    if (this.loginForm.valid) {
      try {
        const result = await this.loginShipService.validateShipLogin(loginData).toPromise();
        if (result?.responseStatus?.status === '200') {
          window.location.href = window.location.protocol + '//' + window.location.hostname + ':' + this.settings.path;
        }
      }
      catch (error) {

      }
    }
    else {
      this.loginForm.markAllAsTouched();
    }
  }

  /**
   * Get form control of newVoyageForm 
   */
  get login() {
    return this.loginForm.controls;
  }

}

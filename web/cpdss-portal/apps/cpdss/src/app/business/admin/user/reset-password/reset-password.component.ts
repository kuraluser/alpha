import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { FormControl, FormBuilder, FormGroup, Validators, ValidationErrors } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { MessageService } from 'primeng/api';
import { NgxSpinnerService } from 'ngx-spinner';

import { IResetPasswordResponse , IResetPasswordModel , IUserDetails } from '../../models/user.model';

import { UserTransformationService } from '../../services/user-transformation.service';
import { UserApiService } from '../../services/user-api.service';

import { ConfirmPasswordValidator  } from '../../directives/confirm-password-validator.directive';
import { isAllowedCharacterPassword } from '../../directives/allowed-character-password-directive';

import { AppConfigurationService } from '../../../../shared/services/app-configuration/app-configuration.service';

/**
 * Component class of reset password
 *
 * @export
 * @class ResetPasswordComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.scss']
})
export class ResetPasswordComponent implements OnInit {

  @Output() displayPopUp = new EventEmitter<boolean>();
  @Output() passwordReset = new EventEmitter<IResetPasswordModel>();
  @Input() userDetails: IUserDetails;

  public visible: boolean;
  public passwordResetForm: FormGroup;
  public errorMessages: any;
  public passwordError: string;

  constructor(
    private fb: FormBuilder,
    private translateService: TranslateService,
    private messageService: MessageService,
    private ngxSpinnerService: NgxSpinnerService,
    private userApiService: UserApiService,
    private userTransformationService: UserTransformationService,
  ) { }
 
  /**
 * Component lifecycle ngOnit
 *
 * @returns {void}
 * @memberof ResetPasswordComponent
 */
  ngOnInit(): void {
    this.visible = true;
    this.errorMessages = this.userTransformationService.setValidationErrorMessageResetPassword();
    this.passwordResetForm = this.fb.group({
      'password': [null, [Validators.required , Validators.minLength(AppConfigurationService.settings.passwordMinLength) , isAllowedCharacterPassword , Validators.pattern(`^((?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@#$%&*])|(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])|(?=.*[a-z])(?=.*[0-9])(?=.*[@#$%&*])|(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%&*])|(?=.*[A-Z])(?=.*[a-z])(?=.*[@#$%&*])).{${AppConfigurationService.settings.passwordMinLength},16}$`)]],
      'confirmPassword': [null, [Validators.required]],
    },{
      validators: ConfirmPasswordValidator('password' , 'confirmPassword')
    });
  }

  /**
   * Get field errors
   * @param {string} formControlName
   * @returns {ValidationErrors}
   * @memberof ResetPasswordComponent
   */
  fieldError(formControlName: string): ValidationErrors {
    const formControl = this.field(formControlName);
    return formControl?.invalid && (formControl.dirty || formControl.touched) ? formControl.errors : null;
  }

  /**
  * Get form control of newVoyageForm 
  * @param {string} formControlName
  * @returns {FormControl}
  * @memberof ResetPasswordComponent
  */
  field(formControlName: string): FormControl {
    const formControl = <FormControl>this.passwordResetForm.get(formControlName);
    return formControl;
  }

  
  /**
   * Trim blank space 
   * @param {string} formControlName
   * @memberof ResetPasswordComponent
   */
  trimFormControl(formControlName: string) {
    this.passwordResetForm.controls[formControlName].setValue((this.passwordResetForm.get(formControlName).value).trim());
  }

  /**
  * Cancel popup
  * @memberof ResetPasswordComponent
  */
  cancel() {
    this.displayPopUp.emit(false);
  }

  /**
  * set new password
  * @memberof ResetPasswordComponent
  */
  async setPassword() {
    if(this.passwordResetForm.valid) {
      this.ngxSpinnerService.show();
      const translationKeys = await this.translateService.get(['RESET_PASSWORD_SUCCESS', 'RESET_PASSWORD_SUCCESSFULLY', 'RESET_PASSWORD_ERROR' , 'RESET_PASSWORD_ERROR_CONTAINS_FIRST_NAME' , 'RESET_PASSWORD_PASSWORD_INVALID']).toPromise();
      try {
        const resetPassword: IResetPasswordModel = {
          password: this.passwordResetForm.value.password,
          userId: this.userDetails.id
        }
        const res:IResetPasswordResponse = await this.userApiService.resetPassword(resetPassword).toPromise();
        if (res.responseStatus.status === "200") {
          this.messageService.add({ severity: 'success', summary: translationKeys['RESET_PASSWORD_SUCCESS'], detail: translationKeys['RESET_PASSWORD_SUCCESSFULLY'] });
          this.passwordReset.emit(resetPassword);
          this.displayPopUp.emit(false);
          this.ngxSpinnerService.hide();
        }
      }
      catch (error) {
        if (error.error.errorCode === 'ERR-RICO-120') {
          this.passwordError = translationKeys['RESET_PASSWORD_ERROR_CONTAINS_FIRST_NAME'];
          this.messageService.add({ severity: 'error', summary: translationKeys['RESET_PASSWORD_ERROR'], detail: translationKeys['RESET_PASSWORD_ERROR_CONTAINS_FIRST_NAME'] });
        } else if(error.error.errorCode === 'ERR-RICO-121') {
          this.passwordError = translationKeys['RESET_PASSWORD_PASSWORD_INVALID'];
          this.messageService.add({ severity: 'error', summary: translationKeys['RESET_PASSWORD_ERROR'], detail: translationKeys['RESET_PASSWORD_PASSWORD_INVALID'] });
        }
        this.ngxSpinnerService.hide();
      }
    } else {
      this.passwordResetForm.markAllAsTouched();
    }
  }

}

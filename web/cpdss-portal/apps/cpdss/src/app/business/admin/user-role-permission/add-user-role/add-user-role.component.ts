import { Component, OnInit , Output , EventEmitter } from '@angular/core';
import { FormControl , FormBuilder, FormGroup, Validators, ValidationErrors } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { MessageService } from 'primeng/api';
import { NgxSpinnerService } from 'ngx-spinner';

import { IUserRoleModel ,  ISaveUserRoleResponse } from '../../models/user-role-permission.model';

import { UserRolePermissionTransformationService } from '../../services/user-role-permission-transformation.service';
import { UserRolePermissionApiService } from '../../services/user-role-permission-api.service';

/**
 * Component class of user allocation
 *
 * @export
 * @class AddUserRoleComponent
 * @implements {OnInit}
 */

@Component({
  selector: 'cpdss-portal-add-user-role',
  templateUrl: './add-user-role.component.html',
  styleUrls: ['./add-user-role.component.scss']
})
export class AddUserRoleComponent implements OnInit {

  @Output() displayPopUp = new EventEmitter<boolean>();
  @Output() roleSaved = new EventEmitter();

  public visible: boolean;
  public addUserForm: FormGroup;
  public errorMessages: any;
  public isExisting: boolean;

  constructor(
    private fb: FormBuilder,
    private translateService: TranslateService,
    private messageService: MessageService,
    private ngxSpinnerService: NgxSpinnerService,
    private userRolePermissionApiService: UserRolePermissionApiService,
    private userRolePermissionTransformationService: UserRolePermissionTransformationService
  ) { }

  /**
 * Component lifecycle ngOnit
 *
 * @returns {void}
 * @memberof AddUserRoleComponent
 */
  ngOnInit(): void {
    this.errorMessages = this.userRolePermissionTransformationService.setValidationErrorMessage();
    this.visible = true;
    this.addUserForm = this.fb.group({
      'roleName': ['', [Validators.required , Validators.pattern('^[a-zA-Z0-9 ]+') , Validators.maxLength(10)]],
      'roleDescription': ['', [Validators.required]],
    });
  }

  /**
   * Get field errors
   * @param {string} formControlName
   * @returns {ValidationErrors}
   * @memberof AddUserRoleComponent
   */
  fieldError(formControlName: string): ValidationErrors {
    const formControl = this.field(formControlName);
    return formControl?.invalid && (formControl.dirty || formControl.touched) ? formControl.errors : null;
  }

  /**
  * Get form control of newVoyageForm 
  * @param {string} formControlName
  * @returns {FormControl}
  * @memberof AddUserRoleComponent
  */
  field(formControlName: string): FormControl {
    const formControl = <FormControl>this.addUserForm.get(formControlName);
    return formControl;
  }

  /**
   * Cancel popup
   * @memberof AddUserRoleComponent
  */
  cancel() {
    this.displayPopUp.emit(false);
  }

  /**
   * Save new Role popup
   * @memberof AddUserRoleComponent
  */
  async saveNewUserRole() {
    if(this.addUserForm.valid) {
      this.isExisting = false;
      const newUserRoleData:IUserRoleModel = {
        name: this.addUserForm.value.roleName,
        description: this.addUserForm.value.roleDescription
      }
      this.ngxSpinnerService.show();
      const translationKeys = await this.translateService.get(['NEW_ROLE_CREATE_SUCCESS', 'NEW_ROLE_CREATED_SUCCESSFULLY', 'NEW_ROLE_CREATE_ERROR', 'ROLE_ALREADY_EXIST']).toPromise();
      try {
        const res:ISaveUserRoleResponse = await this.userRolePermissionApiService.saveNewRoleData(newUserRoleData).toPromise();
        if (res.responseStatus.status === "200") {
          newUserRoleData.roleId = res.roleId;
          this.messageService.add({ severity: 'success', summary: translationKeys['NEW_ROLE_CREATE_SUCCESS'], detail: translationKeys['NEW_ROLE_CREATED_SUCCESSFULLY'] });
          this.roleSaved.emit(newUserRoleData);
          this.displayPopUp.emit(false);
          this.ngxSpinnerService.hide();
        }
      }
      catch (error) {
        if (error.error.errorCode === 'ERR-RICO-106') {
          this.isExisting = true;
          this.messageService.add({ severity: 'error', summary: translationKeys['NEW_ROLE_CREATE_ERROR'], detail: translationKeys['ROLE_ALREADY_EXIST'] });
        }
        this.ngxSpinnerService.hide();
      }
    } else {
      this.isExisting = false;
      this.addUserForm.markAllAsTouched();
    }
  }

  /**
   * Trim blank space 
   * @param {string} formControlName
   * @memberof AddUserRoleComponent
   */
  trimFormControl(formControlName: string) {
    this.addUserForm.controls[formControlName].setValue((this.addUserForm.get(formControlName).value).trim());
  }

}

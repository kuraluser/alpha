import { Component, OnInit , Output , EventEmitter, Input } from '@angular/core';
import { FormControl , FormBuilder, FormGroup, Validators, ValidationErrors } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { MessageService } from 'primeng/api';
import { NgxSpinnerService } from 'ngx-spinner';

import { UserTransformationService } from '../../services/user-transformation.service';
import { UserApiService } from '../../services/user-api.service';
import { IRoleResponse , IRoleDetails , USER_POPUP_SELECTIONMODE ,  IUserDetails , IUserModel , ISaveUserResponse } from '../../models/user.model';

/**
 * Component class of add user
 *
 * @export
 * @class AddUserComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.scss']
})
export class AddUserComponent implements OnInit {
  
  @Input() popupStatus: USER_POPUP_SELECTIONMODE;
  @Input() userDetails: IUserDetails;
  @Output() displayPopUp = new EventEmitter<boolean>();
  @Output() userSaved = new EventEmitter();

  public visible: boolean;
  public addUserForm: FormGroup;
  public errorMessages: any;
  public isExisting: boolean;
  public roleList: IRoleDetails[];
  public popUpHeader: string;

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
 * @memberof AddUserComponent
 */
  ngOnInit(): void {
    this.visible = true;
    this.errorMessages = this.userTransformationService.setValidationErrorMessage();
    this.addUserForm = this.fb.group({
      'userName': ['', [Validators.required , Validators.maxLength(50)]],
      'userDesignation': ['', [Validators.required , Validators.maxLength(10)]],
      'userRole': ['', [Validators.required]],
    });
    this.getRoles();
  }

  /**
   * get roles details
   * @memberof AddUserComponent
  */
  async getRoles() {
    this.ngxSpinnerService.show();
    const roleRes: IRoleResponse = await this.userApiService.getRoles().toPromise();
    this.ngxSpinnerService.hide();
    this.roleList = roleRes.roles;
    if(this.popupStatus !== 'ADD') {
      this.addUserForm.patchValue({
        userName: this.userDetails.username,
        userDesignation: this.userDetails.designation,
        userRole: this.roleList?.filter((role: IRoleDetails) => role.name === this.userDetails.role)
      })
    }
    const translationKeys = await this.translateService.get(['ADD_USER_DETAILS', 'EDIT_USER_DETAILS', 'USER_DETAILS']).toPromise();
    this.popUpHeader = this.popupStatus === 'ADD' ? translationKeys['ADD_USER_DETAILS'] : this.popupStatus === 'EDIT' ?  translationKeys['EDIT_USER_DETAILS'] :  translationKeys['USER_DETAILS']

  }

  /**
   * Get field errors
   * @param {string} formControlName
   * @returns {ValidationErrors}
   * @memberof AddUserComponent
  */
  fieldError(formControlName: string): ValidationErrors {
    const formControl = this.field(formControlName);
    return formControl?.invalid && (formControl.dirty || formControl.touched) ? formControl.errors : null;
  }

  /**
  * Get form control of newVoyageForm 
  * @param {string} formControlName
  * @returns {FormControl}
  * @memberof AddUserComponent
  */
  field(formControlName: string): FormControl {
    const formControl = <FormControl>this.addUserForm.get(formControlName);
    return formControl;
  }

    /**
  * Get form control of newVoyageForm 
  * @param {string} formControlName
  * @returns {FormControl}
  * @memberof AddUserComponent
  */
 formContolValue(formControlName: string): FormControl {
  const formControl = <FormControl>this.addUserForm.get(formControlName);
  return formControl.value;
}

  /**
   * Cancel popup
   * @memberof AddUserComponent
  */
  cancel() {
    this.displayPopUp.emit(false);
  }

  /**
   * Trim blank space 
   * @param {string} formControlName
   * @memberof AddUserComponent
   */
  trimFormControl(formControlName: string) {
    this.addUserForm.controls[formControlName].setValue((this.addUserForm.get(formControlName).value).trim());
  }

  /**
   * Save new Role popup
   * @memberof AddUserComponent
  */
 async saveNewUserRole() {
  if(this.addUserForm.valid) {
    this.isExisting = false;
    this.ngxSpinnerService.show();
    const newUserData:IUserModel = {
      name: this.addUserForm.value.userName,
      designation: this.addUserForm.value.userDesignation,
      role: this.addUserForm.value.userRole.id
    }
    let userId: number;
    this.popupStatus === 'ADD' ? userId = 0 : userId = this.userDetails.id;
    const translationKeys = await this.translateService.get(['NEW_USER_CREATE_SUCCESS', 'NEW_USER_CREATED_SUCCESSFULLY', 'USER_CREATE_ERROR', 'USER_ALREADY_EXIST' , 'MAXIMUM_USER_EXCEED']).toPromise();
    try {
      const res:ISaveUserResponse = await this.userApiService.saveUser(newUserData, userId).toPromise();
      if (res.responseStatus.status === "200") {
        this.messageService.add({ severity: 'success', summary: translationKeys['NEW_USER_CREATE_SUCCESS'], detail: translationKeys['NEW_USER_CREATED_SUCCESSFULLY'] });
        this.userSaved.emit('');
        this.displayPopUp.emit(false);
        this.ngxSpinnerService.hide();
      }
    }
    catch (error) {
      if (error.error.errorCode === 'ERR-RICO-103') {
        this.isExisting = true;
        this.messageService.add({ severity: 'error', summary: translationKeys['USER_CREATE_ERROR'], detail: translationKeys['USER_ALREADY_EXIST'] });
      } else if(error.error.errorCode === ' ERR-RICO-104') {
        this.displayPopUp.emit(false);
        this.messageService.add({ severity: 'error', summary: translationKeys['USER_CREATE_ERROR'], detail: translationKeys['MAXIMUM_USER_EXCEED'] });
      }
      this.ngxSpinnerService.hide();
    }
  } else {
    this.isExisting = false;
    this.addUserForm.markAllAsTouched();
  }
}

}

import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { ILoadingDischargingDetails } from '../models/loading-discharging.model';
import { loadingDetailsValidator } from '../directives/validator/loading-details-time-validator.directive';
import { numberValidator } from '../../core/directives/number-validator.directive';
import { LoadingDischargingTransformationService } from '../services/loading-discharging-transformation.service';
import { PermissionsService } from '../../../shared/services/permissions/permissions.service';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';
import { IPermission } from '../../../shared/models/user-profile.model';
import { OPERATIONS } from '../../core/models/common.model';

@Component({
  selector: 'cpdss-portal-loading-discharging-details',
  templateUrl: './loading-discharging-details.component.html',
  styleUrls: ['./loading-discharging-details.component.scss']
})

/**
 * Component class for loading discharging details component
 *
 * @export
 * @class LoadingDischargingDetailsComponent
 * @implements {OnInit}
 */
export class LoadingDischargingDetailsComponent implements OnInit {
  @Input() operation: OPERATIONS;

  @Input()
  get loadingDischargingDetails(): ILoadingDischargingDetails {
    return this._loadingDischargingDetails;
  }

  set loadingDischargingDetails(loadingDischargingDetails: ILoadingDischargingDetails) {
    this._loadingDischargingDetails = loadingDischargingDetails;
    this.getPagePermission();
    this.initLoadingDischargingDetailsForm();
  }

  @Output() updateLoadingDischargingDetails: EventEmitter<ILoadingDischargingDetails> = new EventEmitter();

  private _loadingDischargingDetails: ILoadingDischargingDetails;

  readonly OPERATIONS = OPERATIONS;
  loadingDischargingDetailsForm: FormGroup;
  errorMessages: any;
  loadingDischargingDetailsResponse: ILoadingDischargingDetails;
  timeOfSunrisePermission: IPermission;
  timeOfSunsetPermission: IPermission;
  selectedTime:any;
  constructor(private fb: FormBuilder,
    private permissionsService: PermissionsService,
    private loadingDischargingTransformationService: LoadingDischargingTransformationService) { }

  ngOnInit(): void {
    this.getPagePermission();
    this.errorMessages = this.loadingDischargingTransformationService.setValidationMessageForLoadingDetails();
  }

  /**
  * Method for initialise loading discharging details form
  *
  * @memberof LoadingDischargingDetailsComponent
  */
  initLoadingDischargingDetailsForm() {
    let timeOfSunriseValidation = [];
    let timeOfSunsetValidation = [];
    if(this.timeOfSunrisePermission.view || this.timeOfSunrisePermission.view === undefined) {
      timeOfSunriseValidation = [Validators.required];
    } 
    if(this.timeOfSunsetPermission.view || this.timeOfSunsetPermission.view === undefined) {
      timeOfSunsetValidation = [Validators.required];
    }
    if(this.timeOfSunrisePermission.view && this.timeOfSunsetPermission.view) {
      timeOfSunsetValidation = [...timeOfSunsetValidation , loadingDetailsValidator('timeOfSunrise', '>')];
      timeOfSunriseValidation = [...timeOfSunriseValidation , loadingDetailsValidator('timeOfSunset', '<')];
    }
    this.loadingDischargingDetailsResponse = this.loadingDischargingDetails;
    this.loadingDischargingDetailsForm = this.fb.group({
      timeOfSunrise: this.fb.control(this.getDateByDate(this.loadingDischargingDetails?.timeOfSunrise), timeOfSunriseValidation),
      timeOfSunset: this.fb.control(this.getDateByDate(this.loadingDischargingDetails?.timeOfSunset), timeOfSunsetValidation),
      startTime: this.fb.control(this.getDateByDate(this.loadingDischargingDetails?.startTime), [Validators.required]),
      initialTrim: this.fb.control(this.loadingDischargingDetails.trimAllowed?.initialTrim, [Validators.required, numberValidator(2, 1), Validators.min(0), Validators.max(4)]),
      maximumTrim: this.fb.control(this.loadingDischargingDetails.trimAllowed?.maximumTrim, [Validators.required, numberValidator(2, 1), Validators.min(1), Validators.max(3)]),
    });

    if(this.operation === OPERATIONS.DISCHARGING) {
      this.loadingDischargingDetailsForm.addControl('topOffTrim', this.fb.control(this.loadingDischargingDetails.trimAllowed?.topOffTrim, [Validators.required, numberValidator(2, 1), Validators.min(0), Validators.max(2)]));
    } else {
      this.loadingDischargingDetailsForm.addControl('finalTrim', this.fb.control(this.loadingDischargingDetails.trimAllowed?.finalTrim, [Validators.required, numberValidator(2, 1), Validators.min(0), Validators.max(2)]));
    }
   }

  /**
* Method for converting time string to date
*
* @memberof LoadingDischargingDetailsComponent
*/
  getDateByDate(field: string): Date {
    if (field) {
      const splittedField = field.split(":");
      const date = new Date();
      date.setHours(Number(splittedField[0]), Number(splittedField[1]))
      return date;
    } else {
      return null;
    }
  }

  /**
  * Method to check for field errors
  *
  * @param {string} formControlName
  * @return {ValidationErrors}
  * @memberof LoadingDischargingDetailsComponent
  */

  fieldError(formControlName: string): ValidationErrors {
    const formControl = this.field(formControlName);
    return formControl?.invalid && (formControl.dirty || formControl.touched) ? formControl.errors : null;
  }

  /**
*Method to get formcontrolName
* @param {string} formControlName
* @return {FormControl}
* @memberof LoadingDischargingDetailsComponent
*/
  field(formControlName: string): FormControl {
    const formControl = <FormControl>this.loadingDischargingDetailsForm?.get(formControlName);
    return formControl;
  }

  clearFilter(dd) {
  }

  onSubmit() { }

  /**
* Method for when time values change
*
* @memberof LoadingDischargingDetailsComponent
*/
  onTimeChange(fieldReferenceName, field) {
    fieldReferenceName.hideOverlay();
    this.selectedTime = null;
    if (this.loadingDischargingDetailsForm.value[field]) {
      this.selectedTime = new Date(this.loadingDischargingDetailsForm.value[field]);
     
    }
    else {
      this.selectedTime = new Date();
      this.selectedTime.setHours(fieldReferenceName.currentHour, fieldReferenceName.currentMinute);
      this.loadingDischargingDetailsForm.controls[field].setValue(this.selectedTime);
    }
    this.loadingDischargingDetailsResponse[field] = ((this.selectedTime.getHours() < 10 ? ('0' + this.selectedTime.getHours()) : this.selectedTime.getHours())) + ":" + ((this.selectedTime.getMinutes() < 10 ? ('0' + this.selectedTime.getMinutes()) : this.selectedTime.getMinutes()));
    
    if (!this.fieldError(field)) {
      this.updateLoadingDischargingDetails.emit(this.loadingDischargingDetailsResponse);
    }
    this.loadingDischargingDetailsForm.controls.timeOfSunrise.updateValueAndValidity();
    this.loadingDischargingDetailsForm.controls.timeOfSunset.updateValueAndValidity();
  }

  /**
   * Method to clear time input.
   *
   * @param {*} field
   * @memberof LoadingDischargingDetailsComponent
   */
  clearTimeInput(fieldReferenceName, field) {
    fieldReferenceName.hideOverlay();
    this.loadingDischargingDetailsForm.controls[field].setValue(null);
  }

/**
  * Get page permission
  *
  * @memberof SynopticalComponent
  */
 getPagePermission() {
  this.timeOfSunrisePermission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['LoadingInfoSunRise'], false);
  this.timeOfSunsetPermission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['LoadingInfoSunSet'], false);
}



  /**
* Method for when trim value update
*
* @memberof LoadingDischargingDetailsComponent
*/
  trimValueChange(field) {
    if (this.loadingDischargingDetailsForm.valid && this.loadingDischargingDetailsForm.value[field]) {
      this.loadingDischargingDetailsResponse.trimAllowed[field] = this.loadingDischargingDetailsForm.value[field];
      this.updateLoadingDischargingDetails.emit(this.loadingDischargingDetailsResponse);
    }
  }

}

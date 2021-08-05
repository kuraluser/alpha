import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { ILoadingDischargingDetails } from '../models/loading-discharging.model';
import { loadingDetailsValidator } from '../directives/validator/loading-details-time-validator.directive';
import { numberValidator } from '../../core/directives/number-validator.directive';
import { LoadingDischargingTransformationService } from '../services/loading-discharging-transformation.service';
import { PermissionsService } from '../../../shared/services/permissions/permissions.service';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';
import { IPermission } from '../../../shared/models/user-profile.model';

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
  @Input() loadingDischargingDetails: ILoadingDischargingDetails;

  @Output() updateLoadingDischargingDetails: EventEmitter<ILoadingDischargingDetails> = new EventEmitter();

  loadingDischargingDetailsForm: FormGroup;
  errorMessages: any;
  loadingDischargingDetailsResponse: ILoadingDischargingDetails;
  timeOfSunrisePermission: IPermission;
  timeOfSunsetPermission: IPermission;
  constructor(private fb: FormBuilder,
    private permissionsService: PermissionsService,
    private loadingDischargingTransformationService: LoadingDischargingTransformationService) { }

  ngOnInit(): void {
    this.getPagePermission();
    this.errorMessages = this.loadingDischargingTransformationService.setValidationMessageForLoadingDetails();
    this.loadingDischargingDetailsResponse = this.loadingDischargingDetails;
    this.loadingDischargingDetailsForm = this.fb.group({
      timeOfSunrise: this.fb.control(this.getDateByDate(this.loadingDischargingDetails?.timeOfSunrise), [Validators.required, loadingDetailsValidator('timeOfSunset','<')]),
      timeOfSunset: this.fb.control(this.getDateByDate(this.loadingDischargingDetails?.timeOfSunset), [Validators.required, loadingDetailsValidator('timeOfSunrise','>')]),
      startTime: this.fb.control(this.getDateByDate(this.loadingDischargingDetails?.startTime), [Validators.required]),
      initialTrim: this.fb.control(this.loadingDischargingDetails.trimAllowed?.initialTrim, [Validators.required, numberValidator(2, 1), Validators.min(0), Validators.max(4)]),
      maximumTrim: this.fb.control(this.loadingDischargingDetails.trimAllowed?.maximumTrim, [Validators.required, numberValidator(2, 1), Validators.min(1), Validators.max(3)]),
      finalTrim: this.fb.control(this.loadingDischargingDetails.trimAllowed?.finalTrim, [Validators.required, numberValidator(2, 1), Validators.min(0), Validators.max(2)])
    })
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
  onTimeChange(fieldReferenceName,field) {
    fieldReferenceName.hideOverlay();
    if (this.loadingDischargingDetailsForm.value[field]) {
      const selectedTime = new Date(this.loadingDischargingDetailsForm.value[field]);
      this.loadingDischargingDetailsResponse[field] = ((selectedTime.getHours() < 10 ? ('0' + selectedTime.getHours()) : selectedTime.getHours())) + ":" + ((selectedTime.getMinutes() < 10 ? ('0' + selectedTime.getMinutes()) : selectedTime.getMinutes()));
      if(!this.fieldError(field)){
        this.updateLoadingDischargingDetails.emit(this.loadingDischargingDetailsResponse);
      }

    }
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
    if (this.loadingDischargingDetailsForm.value[field]) {
      this.loadingDischargingDetailsResponse.trimAllowed[field] = this.loadingDischargingDetailsForm.value[field];
      this.updateLoadingDischargingDetails.emit(this.loadingDischargingDetailsResponse);
    }
  }

}

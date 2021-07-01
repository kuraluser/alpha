import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { ILoadingDetails } from '../models/loading-information.model';
import { LoadingDischargingDetailsTransformationService } from './loading-discharging-details-transformation.service';
import { loadingDetailsValidator } from '../directives/validator/loading-details-time-validator.directive';
import { numberValidator } from '../../core/directives/number-validator.directive';

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
  @Input() loadingDetails: ILoadingDetails;

  @Output() updateLoadingDetails: EventEmitter<ILoadingDetails> = new EventEmitter();

  loadingDetailsForm: FormGroup;
  errorMessages: any;
  loadingDetailsResponse: ILoadingDetails;
  constructor(private fb: FormBuilder, private loadingDischargingDetailsTransformationService: LoadingDischargingDetailsTransformationService) { }

  ngOnInit(): void {
    this.errorMessages = this.loadingDischargingDetailsTransformationService.setValidationMessageForLoadingDetails();
    this.loadingDetailsResponse = this.loadingDetails;
    this.loadingDetailsForm = this.fb.group({
      timeOfSunrise: this.fb.control(this.getDateByDate(this.loadingDetails?.timeOfSunrise), [Validators.required, loadingDetailsValidator('timeOfSunset','<')]),
      timeOfSunset: this.fb.control(this.getDateByDate(this.loadingDetails?.timeOfSunset), [Validators.required, loadingDetailsValidator('timeOfSunrise','>')]),
      startTime: this.fb.control(this.getDateByDate(this.loadingDetails?.startTime), [Validators.required]),
      initialTrim: this.fb.control(this.loadingDetails.trimAllowed?.initialTrim, [Validators.required, numberValidator(2, 1), Validators.min(0), Validators.max(4)]),
      maximumTrim: this.fb.control(this.loadingDetails.trimAllowed?.maximumTrim, [Validators.required, numberValidator(2, 1), Validators.min(1), Validators.max(3)]),
      finalTrim: this.fb.control(this.loadingDetails.trimAllowed?.finalTrim, [Validators.required, numberValidator(2, 1), Validators.min(0), Validators.max(2)])
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
    const formControl = <FormControl>this.loadingDetailsForm?.get(formControlName);
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
  onTimeChange(field) {
    if (this.loadingDetailsForm.value[field]) {
      const selectedTime = new Date(this.loadingDetailsForm.value[field]);
      this.loadingDetailsResponse[field] = ((selectedTime.getHours() < 10 ? ('0' + selectedTime.getHours()) : selectedTime.getHours())) + ":" + ((selectedTime.getMinutes() < 10 ? ('0' + selectedTime.getMinutes()) : selectedTime.getMinutes()));
      if(!this.fieldError(field)){
        this.updateLoadingDetails.emit(this.loadingDetailsResponse);
      }

    }
  }

  /**
* Method for when trim value update
*
* @memberof LoadingDischargingDetailsComponent
*/
  trimValueChange(field) {
    if (this.loadingDetailsForm.value[field]) {
      this.loadingDetailsResponse.trimAllowed[field] = this.loadingDetailsForm.value[field];
      this.updateLoadingDetails.emit(this.loadingDetailsResponse);
    }
  }

}

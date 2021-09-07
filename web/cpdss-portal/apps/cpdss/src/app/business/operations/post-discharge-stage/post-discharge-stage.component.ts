import { Component, Input, OnInit } from '@angular/core';
import { FormControl, FormGroup, ValidationErrors } from '@angular/forms';
import { IValidationErrorMessagesSet } from '../../../shared/components/validation-error/validation-error.model';
import { IPostDischargeStageTime } from '../models/loading-discharging.model';
import { LoadingDischargingTransformationService } from '../services/loading-discharging-transformation.service';

/**
 * Component class for post discharge details component
 *
 * @export
 * @class PostDischargeStageComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-post-discharge-stage',
  templateUrl: './post-discharge-stage.component.html',
  styleUrls: ['./post-discharge-stage.component.scss']
})
export class PostDischargeStageComponent implements OnInit {
  @Input() form: FormGroup;
  @Input() set postDischargeStageTime(postDischargeStageTime: IPostDischargeStageTime) {
    this.postDischargeStageTimeData.push(postDischargeStageTime);
  }
  postDischargeStageTimeData: IPostDischargeStageTime[] = [];

  get postDischargeStageTimeForm() {
    return <FormGroup>this.form.get('postDischargeStageTime');
  }

  errorMesages: IValidationErrorMessagesSet;

  constructor(private loadingDischargingTransformationService: LoadingDischargingTransformationService) { }

  ngOnInit(): void {
    this.errorMesages = this.loadingDischargingTransformationService.setPostDischargeValidationErrorMessage();
  }

  /**
   * Method to check for field errors
   *
   * @param {string} formControlName
   * @param {number} indexOfFormgroup
   * @return {ValidationErrors}
   * @memberof PostDischargeStageComponent
   */
  fieldError(formControlName: string): ValidationErrors {
    const formControl = this.field(formControlName);
    return formControl?.invalid && (formControl?.dirty || formControl?.touched) ? formControl?.errors : null;
  }

  /**
   * Method to get formControl
   * @param {string} formControlName
   * @return {FormControl}
   * @memberof PostDischargeStageComponent
  */
  field(formControlName: string): FormControl {
    const formControl = <FormControl>this.postDischargeStageTimeForm?.get(formControlName);
    return formControl;
  }
}

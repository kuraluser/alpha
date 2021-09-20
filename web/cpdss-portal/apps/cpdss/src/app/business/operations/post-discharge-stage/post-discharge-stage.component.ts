import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ValidationErrors } from '@angular/forms';
import { IValidationErrorMessagesSet } from '../../../shared/components/validation-error/validation-error.model';
import { durationValidator } from '../directives/validator/duration-validator.directive';
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
  @Input()
  get postDischargeStageTime(): IPostDischargeStageTime {
    return this._postDischargeStageTim;
  }
  set postDischargeStageTime(postDischargeStageTime: IPostDischargeStageTime) {
    this._postDischargeStageTim = postDischargeStageTime;
    this.postDischargeStageTimeData.push(postDischargeStageTime);
  }
  @Input()
  get editMode(): boolean {
    return this._editMode;
  }
  set editMode(editMode: boolean) {
    this._editMode = editMode;
  }

  get postDischargeStageTimeForm() {
    return <FormGroup>this.form.get('postDischargeStageTime');
  }

  postDischargeStageTimeData: IPostDischargeStageTime[] = [];
  errorMesages: IValidationErrorMessagesSet;
  private _postDischargeStageTim: IPostDischargeStageTime;
  private _editMode: boolean;

  constructor(
    private loadingDischargingTransformationService: LoadingDischargingTransformationService,
    private fb: FormBuilder
  ) { }

  ngOnInit(): void {
    this.initPostDischargeStageTimeForm();
  }

  /**
   * function to initialise PostDischargeStageTimeForm
   *
   * @param {IPostDischargeStageTime} postDischargeStageTime
   * @memberof PostDischargeStageComponent
   */
  initPostDischargeStageTimeForm(): void {
    this.errorMesages = this.loadingDischargingTransformationService.setPostDischargeValidationErrorMessage();
    this.form.setControl('postDischargeStageTime', this.fb.group({
      dryCheckTime: this.fb.control(this.postDischargeStageTime?.dryCheckTime, [durationValidator(3, 0)]),
      slopDischargingTime: this.fb.control(this.postDischargeStageTime?.dryCheckTime, [durationValidator(3, 0)]),
      finalStrippingTime: this.fb.control(this.postDischargeStageTime?.dryCheckTime, [durationValidator(3, 0)]),
      freshOilWashingTime: this.fb.control(this.postDischargeStageTime?.dryCheckTime, [durationValidator(3, 0)]),
    }));
  }

  /**
   * return the form control of PostDischargeStageTimeForm
   *
   * @readonly
   * @memberof PostDischargeStageComponent
   */
  get postDischargeStageTimeFormControl() {
    return this.postDischargeStageTimeForm.controls;
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

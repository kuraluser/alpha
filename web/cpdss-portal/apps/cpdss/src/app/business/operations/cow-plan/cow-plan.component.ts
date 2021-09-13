import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import * as moment from 'moment';
import { ICargo, ITank } from '../../core/models/common.model';
import { ICOWDetails, IDischargeOperationListData } from '../models/loading-discharging.model';
import { LoadingDischargingTransformationService } from '../services/loading-discharging-transformation.service';
import { tankPreferenceDuplicationValidator } from '../directives/validator/tank-preference-duplication-validator.directive';
import { IMode } from '../../../shared/models/common.model';
import { IValidationErrorMessagesSet } from '../../../shared/components/validation-error/validation-error.model';
import { durationValidator } from '../directives/validator/duration-validator.directive';
import { numberValidator } from '../../core/directives/number-validator.directive';

/**
 * Component class for COW plan setion
 *
 * @export
 * @class CowPlanComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-cow-plan',
  templateUrl: './cow-plan.component.html',
  styleUrls: ['./cow-plan.component.scss']
})
export class CowPlanComponent implements OnInit {
  @Input() listData: IDischargeOperationListData;
  @Input() cargoTanks: ITank[];
  @Input() loadedCargos: ICargo[];
  @Input() form: FormGroup;

  @Input()
  get cowDetails(): ICOWDetails {
    return this._cowDetails;
  }

  set cowDetails(value: ICOWDetails) {
    this._cowDetails = value;
    this.initFormGroup();
    this.enableDisableFieldsOnCowOption(value?.cowOption);
  }

  get cowDetailsForm() {
    return <FormGroup>this.form.get('cowDetails');
  }

  errorMesages: IValidationErrorMessagesSet;
  maxDuration: string[];

  private _cowDetails: ICOWDetails;

  constructor(
    private loadingDischargingTransformationService: LoadingDischargingTransformationService,
    private fb: FormBuilder) { }

  ngOnInit(): void {
    this.errorMesages = this.loadingDischargingTransformationService.setCOWValidationErrorMessage();
  }

  /**
   * Method to initialise cow form
   *
   * @memberof CowPlanComponent
   */
  initFormGroup() {
    const tanksWashingWithDifferentCargo = this.cowDetails?.tanksWashingWithDifferentCargo?.map(item => {
      return this.fb.group({
        cargo: this.fb.control(item?.cargo),
        washingCargo: this.fb.control(item?.washingCargo),
        tanks: this.fb.control(item?.tanks)
      })
    });
    this.maxDuration = this.cowDetails?.totalDuration.split(':');

    this.form.setControl('cowDetails', this.fb.group({
      washTanksWithDifferentCargo: this.fb.control(this.cowDetails?.washTanksWithDifferentCargo),
      cowOption: this.fb.control(this.cowDetails?.cowOption),
      cowPercentage: this.fb.control(this.cowDetails?.cowPercentage),
      topCOWTanks: this.fb.control(this.cowDetails?.topCOWTanks, [tankPreferenceDuplicationValidator('top')]),
      bottomCOWTanks: this.fb.control(this.cowDetails?.bottomCOWTanks),
      allCOWTanks: this.fb.control(this.cowDetails?.allCOWTanks),
      tanksWashingWithDifferentCargo: this.fb.array([...tanksWashingWithDifferentCargo]),
      cowStart: this.fb.control(this.cowDetails?.cowStart, [durationValidator(Number(this.maxDuration[0]), Number(this.maxDuration[1]))]),
      cowEnd: this.fb.control(this.cowDetails?.cowEnd, [durationValidator(Number(this.maxDuration[0]), Number(this.maxDuration[1]))]),
      cowDuration: this.fb.control(this.cowDetails?.cowDuration),
      cowTrimMin: this.fb.control(this.cowDetails?.cowTrimMin, [Validators.required, numberValidator(2, 1)]),
      cowTrimMax: this.fb.control(this.cowDetails?.cowTrimMax, [Validators.required, numberValidator(2, 1)]),
      needFreshCrudeStorage: this.fb.control(this.cowDetails?.needFreshCrudeStorage),
      needFlushingOil: this.fb.control(this.cowDetails?.needFlushingOil),
    }));
  }

  /**
   * Handler for cow option change
   *
   * @param {*} event
   * @memberof CowPlanComponent
   */
  onCowOptionChange(event) {
    this.enableDisableFieldsOnCowOption(event?.value);
  }

  /**
   * Enable Disable field based on cow option selected
   *
   * @param {number} mode
   * @memberof CowPlanComponent
   */
  enableDisableFieldsOnCowOption(mode: IMode) {
    if (mode?.id === 1) {
      this.cowDetailsForm.controls.cowPercentage.enable();
      this.cowDetailsForm.controls.allCOWTanks.disable();
      this.cowDetailsForm.controls.bottomCOWTanks.disable();
      this.cowDetailsForm.controls.topCOWTanks.disable();
      this.cowDetailsForm.controls.washTanksWithDifferentCargo.disable();
      this.cowDetailsForm.controls.tanksWashingWithDifferentCargo.disable();
    } else {
      this.cowDetailsForm.controls.cowPercentage.disable();
      this.cowDetailsForm.controls.allCOWTanks.enable();
      this.cowDetailsForm.controls.bottomCOWTanks.enable();
      this.cowDetailsForm.controls.topCOWTanks.enable();
      this.cowDetailsForm.controls.washTanksWithDifferentCargo.enable();
      this.enableDisableTanksWashWithDifferentCargoFields(this.cowDetailsForm.controls.washTanksWithDifferentCargo?.value);
    }
  }

  /**
   * Handler for changing wash with different cargo option
   *
   * @param {*} event
   * @memberof CowPlanComponent
   */
  onWashWithDifferentCargoChange(event) {
    this.enableDisableTanksWashWithDifferentCargoFields(event?.value);
  }

  /**
   * Enable disable tanks washing with different cargo
   *
   * @param {boolean} enable
   * @memberof CowPlanComponent
   */
  enableDisableTanksWashWithDifferentCargoFields(enable: boolean) {
    if (enable) {
      this.cowDetailsForm.controls.tanksWashingWithDifferentCargo.enable();
    } else {
      this.cowDetailsForm.controls.tanksWashingWithDifferentCargo.disable();
    }
  }

  /**
   * Handler for cow start and end time change
   *
   * @memberof CowPlanComponent
   */
  onCowStartEndChange() {
    const totalDurationInMinutes = this.loadingDischargingTransformationService.convertTimeStringToMinutes(this.cowDetails?.totalDuration);
    const startTimeInMinutes = this.loadingDischargingTransformationService.convertTimeStringToMinutes(this.cowDetailsForm.controls?.cowStart.value);
    const endTimeInMinutes = this.loadingDischargingTransformationService.convertTimeStringToMinutes(this.cowDetailsForm.controls?.cowEnd.value);
    const duration = totalDurationInMinutes - startTimeInMinutes - endTimeInMinutes;
    this.cowDetailsForm.controls.cowDuration.setValue(moment.utc(duration * 60 * 1000).format("HH:mm"));
    if (this.cowDetailsForm.controls?.cowStart.value) {
      this.cowDetailsForm.controls?.cowEnd.setValidators([Validators.required, durationValidator(Number(this.maxDuration[0]), Number(this.maxDuration[1]))]);
    } else{
      this.cowDetailsForm.controls?.cowEnd.setValidators([durationValidator(Number(this.maxDuration[0]), Number(this.maxDuration[1]))]);
    }
    if (this.cowDetailsForm.controls?.cowEnd.value) {
      this.cowDetailsForm.controls?.cowStart.setValidators([Validators.required, durationValidator(Number(this.maxDuration[0]), Number(this.maxDuration[1]))]);
    } else {
      this.cowDetailsForm.controls?.cowStart.setValidators([durationValidator(Number(this.maxDuration[0]), Number(this.maxDuration[1]))]);
    }
    this.cowDetailsForm.controls?.cowStart.updateValueAndValidity();
    this.cowDetailsForm.controls?.cowEnd.updateValueAndValidity();
  }

  /**
   * Method to check for field errors
   *
   * @param {string} formControlName
   * @param {number} indexOfFormgroup
   * @return {ValidationErrors}
   * @memberof CowPlanComponent
   */
  fieldError(formControlName: string): ValidationErrors {
    const formControl = this.field(formControlName);
    return formControl?.invalid && (formControl?.dirty || formControl?.touched) ? formControl?.errors : null;
  }

  /**
   * Method to get formControl
   * @param {string} formControlName
   * @return {FormControl}
   * @memberof CowPlanComponent
  */
  field(formControlName: string): FormControl {
    const formControl = <FormControl>this.cowDetailsForm?.get(formControlName);
    return formControl;
  }

}

import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import * as moment from 'moment';
import { ICargo, ITank } from '../../core/models/common.model';
import { ICOWDetails, IDischargeOperationListData } from '../models/loading-discharging.model';
import { LoadingDischargingTransformationService } from '../services/loading-discharging-transformation.service';
import { tankPreferenceDuplicationValidator } from '../directives/validator/tank-preference-duplication-validator.directive';
import { IMode } from '../../../shared/models/common.model';
import { IValidationErrorMessagesSet } from '../../../shared/components/validation-error/validation-error.model';

import { numberValidator } from '../../core/directives/number-validator.directive';
import { compareDischargeTimeValidation } from '../directives/validator/compare-discharge-time-validator.directive';

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
  }
  @Input()
  get editMode(): boolean {
    return this._editMode;
  }
  set editMode(editMode: boolean) {
    this._editMode = editMode;
  }

  @Output() updateCowPlan = new EventEmitter<ICOWDetails>();

  get cowDetailsForm() {
    return <FormGroup>this.form.get('cowDetails');
  }

  errorMesages: IValidationErrorMessagesSet;
  maxDuration: string[];

  private _cowDetails: ICOWDetails;
  private _editMode: boolean;

  constructor(
    private loadingDischargingTransformationService: LoadingDischargingTransformationService,
    private fb: FormBuilder) { }

  ngOnInit(): void {
    this.errorMesages = this.loadingDischargingTransformationService.setCOWValidationErrorMessage();
    this.initFormGroup();
    this.enableDisableFieldsOnCowOption(this.cowDetails?.cowOption);
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
    }) ?? [];

    this.maxDuration = this.cowDetails?.totalDuration?.split(':');

    this.form?.setControl('cowDetails', this.fb.group({
      washTanksWithDifferentCargo: this.fb.control(this.cowDetails?.washTanksWithDifferentCargo),
      cowOption: this.fb.control(this.cowDetails?.cowOption, [Validators.required]),
      cowPercentage: this.fb.control(this.cowDetails?.cowPercentage),
      topCOWTanks: this.fb.control(this.cowDetails?.topCOWTanks, [tankPreferenceDuplicationValidator('top')]),
      selectedTopCOWTanks: this.joinDropOptionsToLabel(this.cowDetails?.topCOWTanks),
      bottomCOWTanks: this.fb.control(this.cowDetails?.bottomCOWTanks),
      selectedBottomCOWTanks: this.joinDropOptionsToLabel(this.cowDetails?.bottomCOWTanks),
      allCOWTanks: this.fb.control(this.cowDetails?.allCOWTanks),
      selectedAllCOWTanks: this.joinDropOptionsToLabel(this.cowDetails?.allCOWTanks),
      tanksWashingWithDifferentCargo: this.fb.array([...tanksWashingWithDifferentCargo]),
      cowStart: this.fb.control(this.cowDetails?.cowStart, [compareDischargeTimeValidation(this.cowDetails?.totalDuration,'cowEnd')]),
      cowEnd: this.fb.control(this.cowDetails?.cowEnd, [compareDischargeTimeValidation(this.cowDetails?.totalDuration,'cowStart')]),
      cowDuration: this.fb.control(this.cowDetails?.cowDuration),
      cowTrimMin: this.fb.control(this.cowDetails?.cowTrimMin, [Validators.required, Validators.min(3.5), Validators.max(4.5), numberValidator(2, 1)]),
      cowTrimMax: this.fb.control(this.cowDetails?.cowTrimMax, [Validators.required, Validators.min(5.5), Validators.max(6.5), numberValidator(2, 1)]),
      needFreshCrudeStorage: this.fb.control(this.cowDetails?.needFreshCrudeStorage),
      needFlushingOil: this.fb.control(this.cowDetails?.needFlushingOil),
    }));
  }

  /**
   * return the form control of cowDetailsForm
   *
   * @readonly
   * @memberof CowPlanComponent
   */
  get cowDetailsFormControl() {
    return this.cowDetailsForm.controls;
  }

  /**
   * function to group the selcted tanks short-name as label
   *
   * @param {ITank[]} tanks
   * @return {*}  {string}
   * @memberof CowPlanComponent
   */
  joinDropOptionsToLabel(tanks: ITank[]): string {
    let tankShortNamesLabel: string;
    const tankShortNames: string[] = [];
    tanks?.forEach(selectedTanks => {
      tankShortNames.push(selectedTanks?.shortName);
    });
    tankShortNamesLabel = tankShortNames.join(', ');
    return tankShortNamesLabel;
  }

  /**
   * Handler for cow option change
   *
   * @param {*} event
   * @memberof CowPlanComponent
   */
  onCowOptionChange(event) {
    this.enableDisableFieldsOnCowOption(event?.value);
    this.updateCowDetails();
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
      this.cowDetailsForm.controls?.cowPercentage.setValidators([Validators.required]);

      this.cowDetailsForm.controls.cowStart.disable();
      this.cowDetailsForm.controls.cowEnd.disable();
      this.cowDetailsForm.controls.cowDuration.disable();
      this.cowDetailsForm.controls.cowTrimMin.disable();
      this.cowDetailsForm.controls.cowTrimMax.disable();

    } else {
      this.cowDetailsForm.controls.cowStart.enable();
      this.cowDetailsForm.controls.cowEnd.enable();
      this.cowDetailsForm.controls.cowDuration.enable();
      this.cowDetailsForm.controls.cowTrimMin.enable();
      this.cowDetailsForm.controls.cowTrimMax.enable();

      this.cowDetailsForm.controls.cowPercentage.disable();
      this.cowDetailsForm.controls.allCOWTanks.enable();
      this.cowDetailsForm.controls.bottomCOWTanks.enable();
      this.cowDetailsForm.controls.topCOWTanks.enable();
      this.cowDetailsForm.controls.washTanksWithDifferentCargo.enable();
      this.enableDisableTanksWashWithDifferentCargoFields(this.cowDetailsForm.controls.washTanksWithDifferentCargo?.value);
      this.cowDetailsForm.controls?.cowPercentage.clearValidators();
    }
  }

  /**
   * Handler for changing wash with different cargo option
   *
   * @param {*} event
   * @memberof CowPlanComponent
   */
  onWashWithDifferentCargoChange(event) {
    this.enableDisableTanksWashWithDifferentCargoFields(event?.checked);
    this.updateCowDetails();
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
    
    this.cowDetailsForm.controls.cowDuration.setValue(this.loadingDischargingTransformationService.convertMinutesToHHMM(duration));
    if (startTimeInMinutes) {
      this.cowDetailsForm.controls?.cowEnd.setValidators([Validators.required, compareDischargeTimeValidation(this.cowDetails?.totalDuration,'cowStart')]);
    } else{
      this.cowDetailsForm.controls?.cowEnd.setValidators([compareDischargeTimeValidation(this.cowDetails?.totalDuration,'cowStart')]);
    }
    if (endTimeInMinutes) {
      this.cowDetailsForm.controls?.cowStart.setValidators([Validators.required, compareDischargeTimeValidation(this.cowDetails?.totalDuration,'cowEnd')]);
    } else {
      this.cowDetailsForm.controls?.cowStart.setValidators([compareDischargeTimeValidation(this.cowDetails?.totalDuration,'cowEnd')]);
    }
    this.cowDetailsForm.controls?.cowStart.updateValueAndValidity();
    this.cowDetailsForm.controls?.cowEnd.updateValueAndValidity();
    this.updateCowDetails();
  }

  /**
   * Handler for input chnage event for various cow fields
   *
   * @param {*} event
   * @memberof CowPlanComponent
   */
  onChange(event) {
    this.cowDetailsForm.markAllAsTouched();
    this.updateCowDetails();
  }

  /**
   * Handler for input chnage event for washing cargo dropdown
   *
   * @param {*} event
   * @memberof CowPlanComponent
   */
  onWashingCargoChange(event, index) {
    this.cowDetails.tanksWashingWithDifferentCargo[index].washingCargo = this.cowDetailsForm?.value?.tanksWashingWithDifferentCargo[index]?.washingCargo;
    this.updateCowDetails();
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
    return formControl?.invalid ? formControl?.errors : null;
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

  /**
   * Emit cow details and update form validity
   *
   * @memberof CowPlanComponent
   */
  updateCowDetails() {
    this.cowDetailsForm.markAllAsTouched();
    this.findInvalidControlsRecursive(this.cowDetailsForm);
    this.updateCowPlan.emit(this.cowDetailsForm?.value);
  }

  /**
   * Method get all invalid fields in a row
   *
   * @private
   * @param {FormGroup} formToInvestigate
   * @returns {string[]}
   * @memberof CowPlanComponent
   */
  private findInvalidControlsRecursive(formToInvestigate: FormGroup): string[] {
    const invalidControls: string[] = [];
    const recursiveFunc = (form: FormGroup | FormArray) => {
      Object.keys(form.controls).forEach(field => {
        const control = form.get(field);
        control.updateValueAndValidity();
        if (control.invalid) invalidControls.push(field);
        if (control instanceof FormGroup) {
          recursiveFunc(control);
        } else if (control instanceof FormArray) {
          recursiveFunc(control);
        }
      });
    }
    recursiveFunc(formToInvestigate);
    return invalidControls;
  }

}

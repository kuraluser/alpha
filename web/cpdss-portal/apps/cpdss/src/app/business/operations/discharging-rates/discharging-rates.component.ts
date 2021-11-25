import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup, FormBuilder, Validators, ValidationErrors, FormControl, FormArray } from '@angular/forms';
import { numberValidator } from '../../core/directives/number-validator.directive';
import { compareNumberValidator } from '../directives/validator/compare-number-validator.directive';
import { IDischargingRates } from '../models/loading-discharging.model';
import { LoadingDischargingTransformationService } from '../services/loading-discharging-transformation.service';

/**
 * Component class for loading rates section
 *
 * @export
 * @class DischargingRatesComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-discharging-rates',
  templateUrl: './discharging-rates.component.html',
  styleUrls: ['./discharging-rates.component.scss']
})
export class DischargingRatesComponent implements OnInit {

  @Input()
  get dischargeRates(): IDischargingRates {
    return this._dischargeRates;
  }

  set dischargeRates(dischargeRates: IDischargingRates) {
    this._dischargeRates = dischargeRates;
    if (this.dischargingRatesFormGroup) {
      this.onConversionChange();
    }
  }

  @Input()
  get editMode(): boolean {
    return this._editMode;
  }

  set editMode(editMode: boolean) {
    this._editMode = editMode;
  }

  @Output() dischargingRateChange: EventEmitter<IDischargingRates> = new EventEmitter();


  dischargingRatesFormGroup: FormGroup;
  selectedConversion = {
    id: 2,
    value: "BBLS/Hr"
  }
  conversionDropdown = [
    {
      id: 1,
      value: "M3/Hr"
    },
    {
      id: 2,
      value: "BBLS/Hr"
    }
  ];
  errorMesages: any;

  private _editMode: boolean;
  private _dischargeRates: IDischargingRates;

  constructor(
    private fb: FormBuilder,
    private loadingDischargingTransformationService: LoadingDischargingTransformationService
  ) { }

  ngOnInit(): void {
    this.initDischargingRatesForm();
  }

  /**
   *
   * Method for initialise discharging rate array
   * @return {*}
   * @memberof DischargingRatesComponent
   */
  initDischargingRatesForm() {
    this.errorMesages = this.loadingDischargingTransformationService.setValidationMessageForDischargingRate(this.selectedConversion.id === 1 ? 'M3' : 'BBLS');
    this.dischargingRatesFormGroup = this.fb.group({
      id: this.dischargeRates?.id ? this.dischargeRates?.id : 0,
      maxDischargingRate: this.fb.control(this.dischargeRates?.maxDischargingRate, [Validators.required, compareNumberValidator('initialDischargingRate', '<'), Validators.min(5000), Validators.max(16500), numberValidator(0, 7)]),
      initialDischargingRate: this.fb.control(this.dischargeRates?.initialDischargingRate, [Validators.required, compareNumberValidator('maxDischargingRate', '>'), Validators.min(500), Validators.max(3000), numberValidator(0, 7)]),
      minBallastRate: this.fb.control(this.dischargeRates?.minBallastRate, [Validators.required, compareNumberValidator('maxBallastRate', '>'),  numberValidator(0, 7), Validators.min(500), Validators.max(10000)]),
      maxBallastRate: this.fb.control(this.dischargeRates?.maxBallastRate, [Validators.required, compareNumberValidator('minBallastRate', '<') ,numberValidator(0, 7), Validators.min(500), Validators.max(10000)]),
    });
    this.dischargingRatesFormGroup?.markAllAsTouched();
    this.onConversionChange();
  }

  /**
   * Return the form controlls of the discharging rate form
   *
   * @readonly
   * @memberof DischargingRatesComponent
   */
  get dischargingRatesFormGroupControl() {
    return this.dischargingRatesFormGroup.controls;
  }

  /**
   *Method to check for field errors
   *
   * @param {string} formControlName
   * @param {number} indexOfFormgroup
   * @return {ValidationErrors}
   * @memberof DischargingRateComponent
   */
  fieldError(formControlName: string): ValidationErrors {
    const formControl = this.field(formControlName);
    return formControl?.invalid ? formControl.errors : null;
  }


  /**
   * Method to get formcontrolName
   * @param {string} formControlName
   * @return {FormControl}
   * @memberof DischargingRateComponent
  */
  field(formControlName: string): FormControl {
    const formControl = <FormControl>this.dischargingRatesFormGroup.get(formControlName);
    return formControl;
  }


  /**
   *
   * Method for change fied value
   * @return {*}
   * @memberof DischargingRateComponent
   */
  onChange(field) {
    this.dischargingRatesFormGroup.markAllAsTouched();
    this.findInvalidControlsRecursive(this.dischargingRatesFormGroup);
    const dischargeRates: IDischargingRates = { ...this.dischargingRatesFormGroup?.value };
    if (this.selectedConversion?.id === 2) {
      const convertionFactor = 6.28981;
      dischargeRates[field] = Number(dischargeRates[field]) / convertionFactor;
      this.dischargeRates[field] = Number(dischargeRates[field]);
      this.dischargingRateChange.emit(dischargeRates)
    } else {
      this.dischargeRates[field] = Number(dischargeRates[field]);
      this.dischargingRateChange.emit(this.dischargingRatesFormGroup?.value)
    }
  }


  /**
   *
   * Method to set conversion of discharging rate
   * @return {*}
   * @memberof DischargingRateComponent
   * */
  onConversionChange() {
    const convertionFactor = 6.28981;
    this.dischargingRatesFormGroup?.controls['maxDischargingRate'].clearValidators();
    this.dischargingRatesFormGroup?.controls['initialDischargingRate'].clearValidators();
    this.dischargingRatesFormGroup?.controls['minBallastRate'].clearValidators();
    this.dischargingRatesFormGroup?.controls['maxBallastRate'].clearValidators();
    if (this.selectedConversion?.id === 2) {
      this.dischargingRatesFormGroup?.patchValue({
        id: this.dischargeRates?.id ? this.dischargeRates?.id : 0,
        maxDischargingRate: Math.round(Number(this.dischargeRates?.maxDischargingRate) * convertionFactor),
        initialDischargingRate: Math.round(Number(this.dischargeRates?.initialDischargingRate) * convertionFactor),
        minBallastRate: Math.round(Number(this.dischargeRates?.minBallastRate) * convertionFactor),
        maxBallastRate: Math.round(Number(this.dischargeRates?.maxBallastRate) * convertionFactor)
      })
      this.dischargingRatesFormGroup?.controls['maxDischargingRate'].setValidators([Validators.required, compareNumberValidator('initialDischargingRate', '<'), Validators.min(Math.round(5000 * convertionFactor)), Validators.max(Math.round(16500 * convertionFactor)), numberValidator(0, 7)]);
      this.dischargingRatesFormGroup?.controls['initialDischargingRate'].setValidators([Validators.required, compareNumberValidator('maxDischargingRate', '>'), Validators.min(Math.round(500 * convertionFactor)), Validators.max(Math.round(3000 * convertionFactor)), numberValidator(0, 7)]);
      this.dischargingRatesFormGroup?.controls['minBallastRate'].setValidators([Validators.required,compareNumberValidator('maxBallastRate', '>'), numberValidator(0, 7), Validators.min(Math.round(500 * convertionFactor)), Validators.max(Math.round(10000 * convertionFactor))]);
      this.dischargingRatesFormGroup?.controls['maxBallastRate'].setValidators([Validators.required, compareNumberValidator('minBallastRate', '<'), numberValidator(0, 7), Validators.min(Math.round(500 * convertionFactor)), Validators.max(Math.round(10000 * convertionFactor))]);
    } else {
      this.dischargingRatesFormGroup?.patchValue({
        id: this.dischargeRates?.id ? this.dischargeRates?.id : 0,
        maxDischargingRate: Math.round(Number(this.dischargeRates?.maxDischargingRate)),
        initialDischargingRate: Math.round(Number(this.dischargeRates?.initialDischargingRate)),
        minBallastRate: Math.round(Number(this.dischargeRates?.minBallastRate)),
        maxBallastRate: Math.round(Number(this.dischargeRates?.maxBallastRate))
      });
      this.dischargingRatesFormGroup?.controls['maxDischargingRate'].setValidators([Validators.required, compareNumberValidator('initialDischargingRate', '<'), Validators.min(5000), Validators.max(16500), numberValidator(0, 7)]);
      this.dischargingRatesFormGroup?.controls['initialDischargingRate'].setValidators([Validators.required, numberValidator(0, 7), compareNumberValidator('maxDischargingRate', '>'), Validators.min(500), Validators.max(3000), numberValidator(0, 7)]);
      this.dischargingRatesFormGroup?.controls['minBallastRate'].setValidators([Validators.required, compareNumberValidator('maxBallastRate', '>') , numberValidator(0, 7), Validators.min(500), Validators.max(10000)]);
      this.dischargingRatesFormGroup?.controls['maxBallastRate'].setValidators([Validators.required, compareNumberValidator('minBallastRate', '<') , numberValidator(0, 7), Validators.min(500), Validators.max(10000)]);
    }

    this.errorMesages = this.loadingDischargingTransformationService.setValidationMessageForDischargingRate(this.selectedConversion.id === 1 ? 'M3' : 'BBLS');

    const invalidFormControls = this.findInvalidControlsRecursive(this.dischargingRatesFormGroup);
    invalidFormControls.forEach((key) => {
      const formControl = this.field(key);
      formControl.markAsTouched();
    });
  }

  /**
   * Method get all invalid fields in a row
   *
   * @private
   * @param {FormGroup} formToInvestigate
   * @returns {string[]}
   * @memberof DischargingRateComponent
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

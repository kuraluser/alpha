import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup, FormBuilder, Validators, ValidationErrors, FormControl } from '@angular/forms';
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
    this.initDischargingRatesForm();
    if (this.selectedConversion.id === 2) {
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

  ngOnInit(): void {  }

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
      maxDischargingRate: this.fb.control(this.dischargeRates.maxDischargingRate, [Validators.required, compareNumberValidator('initialDischargingRate', '<'), Validators.min(5000), Validators.max(16500), numberValidator(0, 7)]),
      initialDischargingRate: this.fb.control(this.dischargeRates.initialDischargingRate, [Validators.required, compareNumberValidator('maxDischargingRate', '>'), Validators.min(500), Validators.max(3000), numberValidator(0, 7)]),
      minBallastingRate: this.fb.control(this.dischargeRates.minBallastingRate, [Validators.required, numberValidator(0, 4), Validators.min(500), Validators.max(10000)]),
      maxBallastingRate: this.fb.control(this.dischargeRates.maxBallastingRate, [numberValidator(0, 4), Validators.min(500), Validators.max(10000)]),
    });
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
    return formControl?.invalid && (formControl.dirty || formControl.touched) ? formControl.errors : null;
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
    if (!this.fieldError(field)) {
      if (this.selectedConversion?.id === 2) {
        const convertionFactor = 6.28981;
        const dischargeRates = this.dischargingRatesFormGroup?.value;
        dischargeRates.maxDischargingRate = Math.round(Number(dischargeRates?.maxDischargingRate) / convertionFactor);
        dischargeRates.initialDischargingRate = Math.round(Number(dischargeRates?.initialDischargingRate) / convertionFactor);
        dischargeRates.minBallastingRate = Math.round(Number(dischargeRates?.minBallastingRate) / convertionFactor);
        dischargeRates.maxBallastingRate = Math.round(Number(dischargeRates?.maxBallastingRate) / convertionFactor);
        this.dischargingRateChange.emit(dischargeRates)
      } else {
        this.dischargingRateChange.emit(this.dischargingRatesFormGroup?.value)
      }
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
    this.dischargingRatesFormGroup?.controls['minBallastingRate'].clearValidators();
    this.dischargingRatesFormGroup?.controls['maxBallastingRate'].clearValidators();
    if (this.selectedConversion?.id === 2) {
      this.dischargingRatesFormGroup?.patchValue({
        maxDischargingRate: Math.round(Number(this.dischargingRatesFormGroup?.value.maxDischargingRate) * convertionFactor),
        initialDischargingRate: Math.round(Number(this.dischargingRatesFormGroup?.value.initialDischargingRate) * convertionFactor),
        minBallastingRate: Math.round(Number(this.dischargingRatesFormGroup?.value.minBallastingRate) * convertionFactor),
        maxBallastingRate: Math.round(Number(this.dischargingRatesFormGroup?.value.maxBallastingRate) * convertionFactor)
      })
      this.dischargingRatesFormGroup?.controls['maxDischargingRate'].setValidators([Validators.required, compareNumberValidator('initialDischargingRate', '<'), Validators.min(5000 * convertionFactor), Validators.max(16500 * convertionFactor), numberValidator(0, 7)]);
      this.dischargingRatesFormGroup?.controls['initialDischargingRate'].setValidators([Validators.required, compareNumberValidator('maxLoadingrate', '>'), Validators.min(500 * convertionFactor), Validators.max(3000 * convertionFactor), numberValidator(0, 7)]);
      this.dischargingRatesFormGroup?.controls['minBallastingRate'].setValidators([numberValidator(0, 4), Validators.min(500 * convertionFactor), Validators.max(10000 * convertionFactor)]);
      this.dischargingRatesFormGroup?.controls['maxBallastingRate'].setValidators([numberValidator(0, 4), Validators.min(500 * convertionFactor), Validators.max(10000 * convertionFactor)]);
    } else {
      this.dischargingRatesFormGroup?.patchValue({
        maxDischargingRate: Math.round(Number(this.dischargingRatesFormGroup?.value.maxDischargingRate) / convertionFactor),
        initialDischargingRate: Math.round(Number(this.dischargingRatesFormGroup?.value.initialDischargingRate) / convertionFactor),
        minBallastingRate: Math.round(Number(this.dischargingRatesFormGroup?.value.minBallastingRate) / convertionFactor),
        maxBallastingRate: Math.round(Number(this.dischargingRatesFormGroup?.value.maxBallastingRate) / convertionFactor)
      });
      this.dischargingRatesFormGroup?.controls['maxDischargingRate'].setValidators([Validators.required, compareNumberValidator('initialDischargingRate', '<'), Validators.min(5000), Validators.max(16500), numberValidator(0, 7)]);
      this.dischargingRatesFormGroup?.controls['initialDischargingRate'].setValidators([Validators.required, numberValidator(0, 4), compareNumberValidator('maxLoadingrate', '>'), Validators.min(500), Validators.max(3000), numberValidator(0, 7)]);
      this.dischargingRatesFormGroup?.controls['minBallastingRate'].setValidators([numberValidator(0, 4), Validators.min(500), Validators.max(10000)]);
      this.dischargingRatesFormGroup?.controls['maxBallastingRate'].setValidators([numberValidator(0, 4), Validators.min(500), Validators.max(10000)]);
    }

    this.errorMesages = this.loadingDischargingTransformationService.setValidationMessageForDischargingRate(this.selectedConversion.id === 1 ? 'M3' : 'BBLS');
    this.dischargingRatesFormGroup.markAllAsTouched();
    this.dischargingRatesFormGroup.markAsTouched();
  }

}

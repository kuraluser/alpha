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
  get dischargingRates(): IDischargingRates {
    return this._dischargingRates;
  }

  set dischargingRates(dischargingRates: IDischargingRates) {
    this._dischargingRates = dischargingRates;
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
    id: 1,
    value: "M3/Hr"
  }
  conversionDropdown = [
    {
      id: 1,
      value: "M3/Hr"
    },
    {
      id: 2,
      value: "BBLs/Hr"
    }
  ];
  errorMesages: any;

  private _editMode: boolean;
  private _dischargingRates: IDischargingRates;

  constructor(
    private fb: FormBuilder,
    private loadingDischargingTransformationService: LoadingDischargingTransformationService
  ) { }

  ngOnInit(): void {
    this.errorMesages = this.loadingDischargingTransformationService.setValidationMessageForLoadingRate();
    this.dischargingRatesFormGroup = this.fb.group({
      id: this.dischargingRates?.id ? this.dischargingRates?.id : 0,
      maxDischargingRate: this.fb.control(this.dischargingRates.maxDischargingRate, [compareNumberValidator('minDischargingRate', '<'), Validators.min(4000), Validators.max(21000)]),
      minDischargingRate: this.fb.control(this.dischargingRates.minDischargingRate, [compareNumberValidator('maxLoadingrate', '>'), Validators.min(1000), Validators.max(3000)]),
      minDeBallastingRate: this.fb.control(this.dischargingRates.minDeBallastingRate, [numberValidator(0, 4), Validators.min(2500), Validators.max(4000)]),
      maxDeBallastingRate: this.fb.control(this.dischargingRates.maxDeBallastingRate, [numberValidator(0, 4), Validators.min(6000), Validators.max(7500)]),
    })

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
        const dischargingRates = this.dischargingRatesFormGroup?.value;
        dischargingRates.maxDischargingRate = Math.ceil(Number(dischargingRates?.maxDischargingRate) / convertionFactor);
        dischargingRates.minDischargingRate = Math.ceil(Number(dischargingRates?.minDischargingRate) / convertionFactor);
        dischargingRates.minDeBallastingRate = Math.ceil(Number(dischargingRates?.minDeBallastingRate) / convertionFactor);
        dischargingRates.maxDeBallastingRate = Math.ceil(Number(dischargingRates?.maxDeBallastingRate) / convertionFactor);
        this.dischargingRateChange.emit(dischargingRates)
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
    this.dischargingRatesFormGroup?.controls['minDischargingRate'].clearValidators();
    this.dischargingRatesFormGroup?.controls['minDeBallastingRate'].clearValidators();
    this.dischargingRatesFormGroup?.controls['maxDeBallastingRate'].clearValidators();
    if (this.selectedConversion?.id === 2) {
      this.dischargingRatesFormGroup?.patchValue({
        maxDischargingRate: Math.ceil(Number(this.dischargingRatesFormGroup?.value.maxDischargingRate) * convertionFactor),
        minDischargingRate: Math.ceil(Number(this.dischargingRatesFormGroup?.value.minDischargingRate) * convertionFactor),
        minDeBallastingRate: Math.ceil(Number(this.dischargingRatesFormGroup?.value.minDeBallastingRate) * convertionFactor),
        maxDeBallastingRate: Math.ceil(Number(this.dischargingRatesFormGroup?.value.maxDeBallastingRate) * convertionFactor)
      })
      this.dischargingRatesFormGroup?.controls['maxDischargingRate'].setValidators([compareNumberValidator('minDischargingRate', '<'), Validators.min(4000 * convertionFactor), Validators.max(21000 * convertionFactor)]);
      this.dischargingRatesFormGroup?.controls['minDischargingRate'].setValidators([compareNumberValidator('maxLoadingrate', '>'), Validators.min(1000 * convertionFactor), Validators.max(3000 * convertionFactor)]);
      this.dischargingRatesFormGroup?.controls['minDeBallastingRate'].setValidators([numberValidator(0, 4), Validators.min(2500 * convertionFactor), Validators.max(4000 * convertionFactor)]);
      this.dischargingRatesFormGroup?.controls['maxDeBallastingRate'].setValidators([numberValidator(0, 4), Validators.min(6000 * convertionFactor), Validators.max(7500 * convertionFactor)]);
    } else {
      this.dischargingRatesFormGroup?.patchValue({
        maxDischargingRate: Math.ceil(Number(this.dischargingRatesFormGroup?.value.maxDischargingRate) / convertionFactor),
        minDischargingRate: Math.ceil(Number(this.dischargingRatesFormGroup?.value.minDischargingRate) / convertionFactor),
        minDeBallastingRate: Math.ceil(Number(this.dischargingRatesFormGroup?.value.minDeBallastingRate) / convertionFactor),
        maxDeBallastingRate: Math.ceil(Number(this.dischargingRatesFormGroup?.value.maxDeBallastingRate) / convertionFactor)
      });
      this.dischargingRatesFormGroup?.controls['maxDischargingRate'].setValidators([numberValidator(0, 5), compareNumberValidator('minDischargingRate', '<'), Validators.min(4000), Validators.max(21000)]);
      this.dischargingRatesFormGroup?.controls['minDischargingRate'].setValidators([numberValidator(0, 4), compareNumberValidator('maxLoadingrate', '>'), Validators.min(1000), Validators.max(3000)]);
      this.dischargingRatesFormGroup?.controls['minDeBallastingRate'].setValidators([numberValidator(0, 4), Validators.min(2500), Validators.max(4000)]);
      this.dischargingRatesFormGroup?.controls['maxDeBallastingRate'].setValidators([numberValidator(0, 4), Validators.min(6000), Validators.max(7500)]);
    }
  }

}

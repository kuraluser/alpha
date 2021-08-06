import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';
import { FormBuilder, FormGroup, ValidationErrors, FormControl, Validators } from '@angular/forms';
import { ILoadingRates } from '../models/loading-discharging.model';
import { compareNumberValidator } from '../directives/validator/compare-number-validator.directive';
import { numberValidator } from '../../core/directives/number-validator.directive';
import { LoadingDischargingTransformationService } from '../services/loading-discharging-transformation.service';
@Component({
  selector: 'cpdss-portal-loading-rate',
  templateUrl: './loading-rate.component.html',
  styleUrls: ['./loading-rate.component.scss']
})

/**
 * Component class for loading / discharging rate component
 *
 * @export
 * @class LoadingRateComponent
 * @implements {OnInit}
 */
export class LoadingRateComponent implements OnInit {
  @Input()
  get loadingRates(): ILoadingRates {
    return this._loadingRates;
  }

  set loadingRates(loadingRates: ILoadingRates) {
    this._loadingRates = loadingRates;
    this.initLoadingRatesForm();
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

  @Output() loadingRateChange: EventEmitter<ILoadingRates> = new EventEmitter();


  private _editMode: boolean;
  private _loadingRates: ILoadingRates;
  public errorMesages: any;

  loadingRatesFormGroup: FormGroup;
  selectedConversion = {
    id: 1,
    value: "M3/Hr",
    unit: "M3"
  }
  conversionDropdown = [
    {
      id: 1,
      value: "M3/Hr",
      unit: "M3"
    },
    {
      id: 2,
      value: "BBLs/Hr",
      unit: "BBLS"
    }
  ]
  constructor(
    private fb: FormBuilder,
    private loadingDischargingTransformationService: LoadingDischargingTransformationService
  ) { }

  ngOnInit(): void {
  }

  /**
   *
   * Method for initialise loading rate array
   * @return {*} 
   * @memberof LoadingRateComponent
   */
  initLoadingRatesForm(){
    this.errorMesages = this.loadingDischargingTransformationService.setValidationMessageForLoadingRate(this.selectedConversion.unit);
    this.loadingRatesFormGroup = this.fb.group({
      id: this.loadingRates?.id ? this.loadingRates?.id : 0,
      maxLoadingRate: this.fb.control(this.loadingRates.maxLoadingRate, [Validators.required, compareNumberValidator('minLoadingRate', '<'), Validators.min(4000), Validators.max(21000)]),
      shoreLoadingRate: this.fb.control(this.loadingRates.shoreLoadingRate, [numberValidator(0, 5), Validators.min(1000), Validators.max(20000)]),
      minLoadingRate: this.fb.control(this.loadingRates.minLoadingRate, [Validators.required, compareNumberValidator('maxLoadingrate', '>'), Validators.min(1000), Validators.max(3000)]),
      minDeBallastingRate: this.fb.control(this.loadingRates.minDeBallastingRate, [Validators.required, numberValidator(0, 4), Validators.min(2500), Validators.max(4000)]),
      maxDeBallastingRate: this.fb.control(this.loadingRates.maxDeBallastingRate, [Validators.required, numberValidator(0, 4), Validators.min(6000), Validators.max(7500)]),
      noticeTimeRateReduction: this.fb.control(this.loadingRates.noticeTimeRateReduction, [Validators.required, numberValidator(0, 2), Validators.min(30), Validators.max(60)]),
      noticeTimeStopLoading: this.fb.control(this.loadingRates.noticeTimeStopLoading, [Validators.required, numberValidator(0, 2), Validators.min(30), Validators.max(60)])
    })
  }

  /**
  * Return the form controlls of the loading rate form
  */
  get loadingRatesFormGroupControl() {
    return this.loadingRatesFormGroup.controls;
  }

  /**
   *Method to check for field errors
   *
   * @param {string} formControlName
   * @param {number} indexOfFormgroup
   * @return {ValidationErrors}
   * @memberof LoadingRateComponent
   */
  fieldError(formControlName: string): ValidationErrors {
    const formControl = this.field(formControlName);
    return formControl?.invalid && (formControl.dirty || formControl.touched) ? formControl.errors : null;
  }


  /**
   *Method to get formcontrolName
   * @param {string} formControlName
   * @return {FormControl}
   * @memberof LoadingRateComponent
  */
  field(formControlName: string): FormControl {
    const formControl = <FormControl>this.loadingRatesFormGroup.get(formControlName);
    return formControl;
  }


  /**
   *
   * Method for change fied value
   * @return {*} 
   * @memberof LoadingRateComponent
   */
  onChange(field) {
    if (!this.fieldError(field)) {
      if (this.selectedConversion?.id === 2) {
        const convertionFactor = 6.28981;
        const loadingRates = this.loadingRatesFormGroup?.value;
        loadingRates.maxLoadingRate = Math.round(Number(loadingRates?.maxLoadingRate) / convertionFactor);
        loadingRates.shoreLoadingRate = Math.round(Number(loadingRates?.shoreLoadingRate) / convertionFactor);
        loadingRates.minLoadingRate = Math.round(Number(loadingRates?.minLoadingRate) / convertionFactor);
        loadingRates.minDeBallastingRate = Math.round(Number(loadingRates?.minDeBallastingRate) / convertionFactor);
        loadingRates.maxDeBallastingRate = Math.round(Number(loadingRates?.maxDeBallastingRate) / convertionFactor);
        this.loadingRateChange.emit(loadingRates)
      } else {
        this.loadingRateChange.emit(this.loadingRatesFormGroup?.value)
      }
    }
  }


  /**
 *
 * Method to set conversion of loading rate
 * @return {*} 
 * @memberof LoadingRateComponent
 */
  onConversionChange() {
    const convertionFactor = 6.28981;
    this.loadingRatesFormGroup?.controls['maxLoadingRate'].clearValidators();
    this.loadingRatesFormGroup?.controls['shoreLoadingRate'].clearValidators();
    this.loadingRatesFormGroup?.controls['minLoadingRate'].clearValidators();
    this.loadingRatesFormGroup?.controls['minDeBallastingRate'].clearValidators();
    this.loadingRatesFormGroup?.controls['maxDeBallastingRate'].clearValidators();
    if (this.selectedConversion?.id === 2) {
      this.loadingRatesFormGroup?.controls['maxLoadingRate'].setValidators([compareNumberValidator('minLoadingRate', '<'), Validators.min(4000 * convertionFactor), Validators.max(21000 * convertionFactor)]);
      this.loadingRatesFormGroup?.controls['shoreLoadingRate'].setValidators([numberValidator(0, 6), Validators.min(1000 * convertionFactor), Validators.max(20000 * convertionFactor)]);
      this.loadingRatesFormGroup?.controls['minLoadingRate'].setValidators([compareNumberValidator('maxLoadingrate', '>'), Validators.min(1000 * convertionFactor), Validators.max(3000 * convertionFactor)]);
      this.loadingRatesFormGroup?.controls['minDeBallastingRate'].setValidators([numberValidator(0, 4), Validators.min(2500 * convertionFactor), Validators.max(4000 * convertionFactor)]);
      this.loadingRatesFormGroup?.controls['maxDeBallastingRate'].setValidators([numberValidator(0, 4), Validators.min(6000 * convertionFactor), Validators.max(7500 * convertionFactor)]);
      this.loadingRatesFormGroup?.patchValue({
        maxLoadingRate: Math.round(Number(this.loadingRatesFormGroup?.value.maxLoadingRate) * convertionFactor),
        shoreLoadingRate: Math.round(Number(this.loadingRatesFormGroup?.value.shoreLoadingRate) * convertionFactor),
        minLoadingRate: Math.round(Number(this.loadingRatesFormGroup?.value.minLoadingRate) * convertionFactor),
        minDeBallastingRate: Math.round(Number(this.loadingRatesFormGroup?.value.minDeBallastingRate) * convertionFactor),
        maxDeBallastingRate: Math.round(Number(this.loadingRatesFormGroup?.value.maxDeBallastingRate) * convertionFactor)
      })
    } else {
      this.loadingRatesFormGroup?.controls['maxLoadingRate'].setValidators([numberValidator(0, 5), compareNumberValidator('minLoadingRate', '<'), Validators.min(4000), Validators.max(21000)]);
      this.loadingRatesFormGroup?.controls['shoreLoadingRate'].setValidators([numberValidator(0, 4), Validators.min(1000), Validators.max(20000)]);
      this.loadingRatesFormGroup?.controls['minLoadingRate'].setValidators([numberValidator(0, 4), compareNumberValidator('maxLoadingrate', '>'), Validators.min(1000), Validators.max(3000)]);
      this.loadingRatesFormGroup?.controls['minDeBallastingRate'].setValidators([numberValidator(0, 4), Validators.min(2500), Validators.max(4000)]);
      this.loadingRatesFormGroup?.controls['maxDeBallastingRate'].setValidators([numberValidator(0, 4), Validators.min(6000), Validators.max(7500)]);
      this.loadingRatesFormGroup?.patchValue({
        maxLoadingRate: Math.round(Number(this.loadingRatesFormGroup?.value.maxLoadingRate) / convertionFactor),
        shoreLoadingRate: Math.round(Number(this.loadingRatesFormGroup?.value.shoreLoadingRate) / convertionFactor),
        minLoadingRate: Math.round(Number(this.loadingRatesFormGroup?.value.minLoadingRate) / convertionFactor),
        minDeBallastingRate: Math.round(Number(this.loadingRatesFormGroup?.value.minDeBallastingRate) / convertionFactor),
        maxDeBallastingRate: Math.round(Number(this.loadingRatesFormGroup?.value.maxDeBallastingRate) / convertionFactor)
      });
    }
    this.errorMesages = this.loadingDischargingTransformationService.setValidationMessageForLoadingRate(this.selectedConversion.unit);
    this.loadingRatesFormGroup.markAllAsTouched();
    this.loadingRatesFormGroup.markAsTouched();
  }

}

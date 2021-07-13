import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';
import { FormBuilder, FormGroup, ValidationErrors, FormControl, Validators } from '@angular/forms';
import { DATATABLE_EDITMODE } from '../../../shared/components/datatable/datatable.model';
import { ILoadingRates } from '../models/loading-information.model';
import { compareNumberValidator } from '../directives/validator/compare-number-validator.directive';
import { numberValidator } from '../../core/directives/number-validator.directive';
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
    if (this.selectedConversion.id === 2) {
      this.onConversionChange();
    }
  }

  @Input()
  get editMode(): DATATABLE_EDITMODE {
    return this._editMode;
  }

  set editMode(editMode: DATATABLE_EDITMODE) {
    this._editMode = editMode;
  }

  @Output() loadingRateChange: EventEmitter<ILoadingRates> = new EventEmitter();


  private _editMode: DATATABLE_EDITMODE;
  private _loadingRates: ILoadingRates;
  public errorMesages: any;

  loadingRatesFormGroup: FormGroup;
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
  ]
  constructor(
    private fb: FormBuilder
  ) { }

  ngOnInit(): void {
    this.errorMesages = this.setValidationMessageForLoadingRate();
    this.loadingRatesFormGroup = this.fb.group({
      id: this.loadingRates?.id ? this.loadingRates?.id : 0,
      maxLoadingRate: this.fb.control(this.loadingRates.maxLoadingRate, [compareNumberValidator('minLoadingRate', '<'), Validators.min(4000), Validators.max(21000)]),
      shoreLoadingRate: this.fb.control(this.loadingRates.shoreLoadingRate, [numberValidator(0, 5), Validators.min(1000), Validators.max(20000)]),
      minLoadingRate: this.fb.control(this.loadingRates.minLoadingRate, [compareNumberValidator('maxLoadingrate', '>'), Validators.min(1000), Validators.max(3000)]),
      minDeBallastingRate: this.fb.control(this.loadingRates.minDeBallastingRate, [numberValidator(0, 4), Validators.min(2500), Validators.max(4000)]),
      maxDeBallastingRate: this.fb.control(this.loadingRates.maxDeBallastingRate, [numberValidator(0, 4), Validators.min(6000), Validators.max(7500)]),
      noticeTimeRateReduction: this.fb.control(this.loadingRates.noticeTimeRateReduction, [numberValidator(0, 2), Validators.min(30), Validators.max(60)]),
      noticeTimeStopLoading: this.fb.control(this.loadingRates.noticeTimeStopLoading, [numberValidator(0, 2), Validators.min(30), Validators.max(60)])
    })

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
 * Method to set validation message for loading rate
 * @return {*} 
 * @memberof LoadingRateComponent
 */
  setValidationMessageForLoadingRate() {
    return {
      maxLoadingRate: {
        'required': 'LOADING_RATE_REQUIRED',
        'failedCompare': 'MAX_LOADING_COMPARE',
        'min': 'MAX_LOADING_MIN',
        'max': 'MAX_LOADING_MAX'
      },
      shoreLoadingRate: {
        'required': 'LOADING_RATE_REQUIRED',
        'min': 'MAX_LOADING_MIN',
        'max': 'MAX_LOADING_MAX'
      },
      minLoadingRate: {
        'required': 'LOADING_RATE_REQUIRED',
        'failedCompare': 'MIN_LOADING_COMPARE',
        'min': 'MIN_LOADING_MIN',
        'max': 'MIN_LOADING_MAX'
      },
      minDeBallastingRate: {
        'required': 'LOADING_RATE_REQUIRED',
        'min': "MIN_DEBALLAST_MINIMUM",
        'max': "MIN_DEBALLAST_MAXIMUM"
      },
      maxDeBallastingRate: {
        'required': 'LOADING_RATE_REQUIRED',
        'min': "MAX_DEBALLAST_MINIMUM",
        'max': "MAX_DEBALLAST_MAXIMUM"
      },
      noticeTimeRateReduction: {
        'required': 'LOADING_RATE_REQUIRED'
      },
      noticeTimeStopLoading: {
        'required': 'LOADING_RATE_REQUIRED'
      }
    }
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
        loadingRates.maxLoadingRate = Math.ceil(Number(loadingRates?.maxLoadingRate) / convertionFactor);
        loadingRates.shoreLoadingRate = Math.ceil(Number(loadingRates?.shoreLoadingRate) / convertionFactor);
        loadingRates.minLoadingRate = Math.ceil(Number(loadingRates?.minLoadingRate) / convertionFactor);
        loadingRates.minDeBallastingRate = Math.ceil(Number(loadingRates?.minDeBallastingRate) / convertionFactor);
        loadingRates.maxDeBallastingRate = Math.ceil(Number(loadingRates?.maxDeBallastingRate) / convertionFactor);
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
      this.loadingRatesFormGroup?.patchValue({
        maxLoadingRate: Math.ceil(Number(this.loadingRatesFormGroup?.value.maxLoadingRate) * convertionFactor),
        shoreLoadingRate: Math.ceil(Number(this.loadingRatesFormGroup?.value.shoreLoadingRate) * convertionFactor),
        minLoadingRate: Math.ceil(Number(this.loadingRatesFormGroup?.value.minLoadingRate) * convertionFactor),
        minDeBallastingRate: Math.ceil(Number(this.loadingRatesFormGroup?.value.minDeBallastingRate) * convertionFactor),
        maxDeBallastingRate: Math.ceil(Number(this.loadingRatesFormGroup?.value.maxDeBallastingRate) * convertionFactor)
      })
      this.loadingRatesFormGroup?.controls['maxLoadingRate'].setValidators([compareNumberValidator('minLoadingRate', '<'), Validators.min(4000 * convertionFactor), Validators.max(21000 * convertionFactor)]);
      this.loadingRatesFormGroup?.controls['shoreLoadingRate'].setValidators([Validators.min(1500 * convertionFactor), Validators.max(5000 * convertionFactor)]);
      this.loadingRatesFormGroup?.controls['minLoadingRate'].setValidators([compareNumberValidator('maxLoadingrate', '>'), Validators.min(1000 * convertionFactor), Validators.max(3000 * convertionFactor)]);
      this.loadingRatesFormGroup?.controls['minDeBallastingRate'].setValidators([numberValidator(0, 4), Validators.min(2500 * convertionFactor), Validators.max(4000 * convertionFactor)]);
      this.loadingRatesFormGroup?.controls['maxDeBallastingRate'].setValidators([numberValidator(0, 4), Validators.min(6000 * convertionFactor), Validators.max(7500 * convertionFactor)]);
    } else {
      this.loadingRatesFormGroup?.patchValue({
        maxLoadingRate: Math.ceil(Number(this.loadingRatesFormGroup?.value.maxLoadingRate) / convertionFactor),
        shoreLoadingRate: Math.ceil(Number(this.loadingRatesFormGroup?.value.shoreLoadingRate) / convertionFactor),
        minLoadingRate: Math.ceil(Number(this.loadingRatesFormGroup?.value.minLoadingRate) / convertionFactor),
        minDeBallastingRate: Math.ceil(Number(this.loadingRatesFormGroup?.value.minDeBallastingRate) / convertionFactor),
        maxDeBallastingRate: Math.ceil(Number(this.loadingRatesFormGroup?.value.maxDeBallastingRate) / convertionFactor)
      });
      this.loadingRatesFormGroup?.controls['maxLoadingRate'].setValidators([numberValidator(0, 5), compareNumberValidator('minLoadingRate', '<'), Validators.min(4000), Validators.max(21000)]);
      this.loadingRatesFormGroup?.controls['shoreLoadingRate'].setValidators([numberValidator(0, 4), Validators.min(1500), Validators.max(5000)]);
      this.loadingRatesFormGroup?.controls['minLoadingRate'].setValidators([numberValidator(0, 4), compareNumberValidator('maxLoadingrate', '>'), Validators.min(1000), Validators.max(3000)]);
      this.loadingRatesFormGroup?.controls['minDeBallastingRate'].setValidators([numberValidator(0, 4), Validators.min(2500), Validators.max(4000)]);
      this.loadingRatesFormGroup?.controls['maxDeBallastingRate'].setValidators([numberValidator(0, 4), Validators.min(6000), Validators.max(7500)]);
    }
  }

}

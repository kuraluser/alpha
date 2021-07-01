import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';
import { FormBuilder, FormGroup , ValidationErrors , FormControl, Validators } from '@angular/forms';
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
@Input() loadingRates: ILoadingRates;
  @Input()
  get editMode(): DATATABLE_EDITMODE {
    return this._editMode;
  }

  set editMode(editMode: DATATABLE_EDITMODE) {
    this._editMode = editMode;
  }

  @Output() loadingRateChange: EventEmitter<ILoadingRates> = new EventEmitter();
  

  private _editMode: DATATABLE_EDITMODE;
  public errorMesages: any;

  loadingRatesFormGroup: FormGroup;
  constructor(
    private fb: FormBuilder
  ) { }

  ngOnInit(): void {
    this.errorMesages = this.setValidationMessageForLoadingRate();
    this.loadingRatesFormGroup = this.fb.group({
      id: this.loadingRates?.id ? this.loadingRates?.id : 0,
      maxLoadingRate: this.fb.control(this.loadingRates.maxLoadingRate, [compareNumberValidator('initialLoadingRate','<'), Validators.min(1), Validators.max(12)]),
      reducedLoadingRate: this.fb.control(this.loadingRates.reducedLoadingRate, [Validators.min(1), Validators.max(12)]),
      initialLoadingRate: this.fb.control(this.loadingRates.initialLoadingRate, [compareNumberValidator('maxLoadingrate','>'), Validators.min(1), Validators.max(2)]),
      minDeBallastingRate: this.fb.control(this.loadingRates.minDeBallastingRate,[numberValidator(0, 4), Validators.min(2500),  Validators.max(4000)]),
      maxDeBallastingRate: this.fb.control(this.loadingRates.maxDeBallastingRate, [numberValidator(0, 4),  Validators.min(6000),  Validators.max(7500)]),
      noticeTimeRateReduction: this.fb.control(this.loadingRates.noticeTimeRateReduction,  [numberValidator(0, 2),  Validators.min(30),  Validators.max(60)]),
      noticeTimeStopLoading: this.fb.control(this.loadingRates.noticeTimeStopLoading,  [numberValidator(0, 2),  Validators.min(30),  Validators.max(60)])
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
        'required': 'MAX_LOADRATE_REQUIRED',
        'failedCompare': 'MAX_LOADING_COMPARE',
        'min': 'MAX_LOADING_MIN',
        'max': 'MAX_LOADING_MAX'
      },
      reducedLoadingRate: {
        'required': 'REDUCED_RATE_REQUIRED',
        'min': 'MAX_LOADING_MIN',
        'max': 'MAX_LOADING_MAX'
      },
      initialLoadingRate: {
        'required': 'MIN_LOADING_REQUIRED',
        'failedCompare': 'MIN_LOADING_COMPARE',
        'min': 'MIN_LOADING_MIN',
        'max': 'MIN_LOADING_MAX'
      },
      minDeBallastingRate: {
        'required': 'MIN_DEBALLAST_REQUIRED',
        'min' : "MIN_DEBALLAST_MINIMUM",
        'max' : "MIN_DEBALLAST_MAXIMUM"
      },
      maxDeBallastingRate: {
        'required': 'MAX_DEBALLAST_REQUIRED',
        'min' : "MAX_DEBALLAST_MINIMUM",
        'max' : "MAX_DEBALLAST_MAXIMUM"
      },
      noticeTimeRateReduction: {
        'required': 'NOTICE_TIME_FOR_RATE_REDUCTION_REQUIRED'
      },
      noticeTimeStopLoading: {
        'required': 'NOTICE_TIME_FOR_STOPPING_LOADING_REQUIRED'
      }
    }
  }

  onChange(field){
    if(!this.fieldError(field)){
      this.loadingRateChange.emit(this.loadingRatesFormGroup?.value)
    }
  }

}

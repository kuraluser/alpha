import { Component, OnInit, Input } from '@angular/core';
import { FormBuilder, FormGroup , ValidationErrors , FormControl } from '@angular/forms';
import { DATATABLE_EDITMODE } from '../../../shared/components/datatable/datatable.model';
import { ILoadingRates } from '../models/loading-information.model';
import { compareNumberValidator } from '../directives/validator/compare-number-validator.directive';
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

  private _editMode: DATATABLE_EDITMODE;
  public errorMesages: any;

  loadingRatesFormGroup: FormGroup;
  constructor(
    private fb: FormBuilder
  ) { }

  ngOnInit(): void {
    this.errorMesages = this.setValidationMessageForLoadingRate();
    this.loadingRatesFormGroup = this.fb.group({
      maxLoadingrate: this.fb.control(this.loadingRates.maxLoadingRate),
      reducedLoadingRate: this.fb.control(this.loadingRates.reducedLoadingRate),
      initialLoadingRate: this.fb.control(this.loadingRates.initialLoadingRate, [compareNumberValidator('maxLoadingrate','>')]),
      minDeBallastingRate: this.fb.control(this.loadingRates.minDeBallastingRate),
      maxDeBallastingRate: this.fb.control(this.loadingRates.maxDeBallastingRate),
      noticeTimeRateReduction: this.fb.control(this.loadingRates.noticeTimeRateReduction),
      noticeTimeStopLoading: this.fb.control(this.loadingRates.noticeTimeStopLoading),
      lineContentRemaining: this.fb.control(this.loadingRates.lineContentRemaining)
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
      maxLoadingrate: {
        'required': 'MAX_LOADRATE_REQUIRED'
      },
      reducedLoadingRate: {
        'required': 'REDUCED_RATE_REQUIRED'
      },
      initialLoadingRate: {
        'required': 'MIN_LOADING_REQUIRED',
        'failedCompare': 'MIN_LOADING_COMPARE'
      },
      minDeBallastingRate: {
        'required': 'MIN_DEBALLAST_REQUIRED'
      },
      maxDeBallastingRate: {
        'required': 'MAX_DEBALLAST_REQUIRED'
      },
      noticeTimeRateReduction: {
        'required': 'NOTICE_TIME_FOR_RATE_REDUCTION_REQUIRED'
      },
      noticeTimeStopLoading: {
        'required': 'NOTICE_TIME_FOR_STOPPING_LOADING_REQUIRED'
      },
      lineContentRemaining: {
        'required': 'RATE_LINE_CONTENT_REMAIONG_REQUIRED'
      }
    }
  }

  change(){
  }

}

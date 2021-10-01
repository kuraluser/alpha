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
  actualValues : any = {
    maxLoadingRate :{
      defaultValue:'',
      lastEditedUnit:'M3',
      BblsValue :''
    },
    shoreLoadingRate :{
      defaultValue:'',
      lastEditedUnit:'M3',
      BblsValue :''
    }
    ,
    minLoadingRate :{
      defaultValue:'',
      lastEditedUnit:'M3',
      BblsValue :''
    }
    ,
    minDeBallastingRate :{
      defaultValue:'',
      lastEditedUnit:'M3',
      BblsValue :''
    }
    ,
    maxDeBallastingRate :{
      defaultValue:'',
      lastEditedUnit:'M3',
      BblsValue :''
    }
  };

  readonly  conversionFactor = 6.28981;
  selectedConversion = {
    id: 2,
    value: "BBLs/Hr",
    unit: "BBLS"
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
      maxLoadingRate: this.fb.control(this.loadingRates.maxLoadingRate, [numberValidator(0, 6),Validators.required, compareNumberValidator('minLoadingRate', '<'), Validators.min(4000), Validators.max(21000)]),
      shoreLoadingRate: this.fb.control(this.loadingRates.shoreLoadingRate, [numberValidator(0, 6), Validators.min(1000), Validators.max(20000)]),
      minLoadingRate: this.fb.control(this.loadingRates.minLoadingRate, [numberValidator(0, 6),Validators.required, compareNumberValidator('maxLoadingrate', '>'), Validators.min(1000), Validators.max(3000)]),
      minDeBallastingRate: this.fb.control(this.loadingRates.minDeBallastingRate, [numberValidator(0, 6),Validators.required, Validators.min(2500), Validators.max(4000)]),
      maxDeBallastingRate: this.fb.control(this.loadingRates.maxDeBallastingRate, [Validators.required, numberValidator(0, 5), Validators.min(6000), Validators.max(7500)]),
      noticeTimeRateReduction: this.fb.control(this.loadingRates.noticeTimeRateReduction, [Validators.required, numberValidator(0, 3), Validators.min(30), Validators.max(60)]),
      noticeTimeStopLoading: this.fb.control(this.loadingRates.noticeTimeStopLoading, [Validators.required, numberValidator(0, 3), Validators.min(30), Validators.max(60)])
    })
 

    for (let key in this.actualValues) {
      this.actualValues[key].defaultValue = this.loadingRates[key];
      if(this.loadingRates[key] !== "" && this.loadingRates[key] !== null)
      this.actualValues[key].BblsValue = this.loadingRates[key] * this.conversionFactor;
      this.actualValues[key].lastEditedUnit = 'M3';
      this.loadingRatesFormGroup.value[key] =   this.loadingRates[key];
    }   
   
  
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
   
    const loadingRates = {...this.loadingRatesFormGroup.value};
    if (this.selectedConversion.id == 2) {
      this.actualValues[field].defaultValue = this.loadingRatesFormGroup?.value[field];
      this.actualValues[field].lastEditedUnit = 'BBLS';
    }
    else {
      this.actualValues[field].defaultValue = this.loadingRatesFormGroup?.value[field];
      this.actualValues[field].lastEditedUnit = 'M3';
    }
    for (let key in this.actualValues) {
     
      if (this.actualValues[key].lastEditedUnit == 'BBLS') {
        if(this.actualValues[key]?.defaultValue !== "" && this.actualValues[key]?.defaultValue !== null)
        loadingRates[key] = this.actualValues[key]?.defaultValue / this.conversionFactor;
      }
      else {
        loadingRates[key] = this.actualValues[key]?.defaultValue;
      }
    }
    this.loadingRateChange.emit(loadingRates);
  }
  


  /**
 *
 * Method to set conversion of loading rate
 * @return {*} 
 * @memberof LoadingRateComponent
 */
  onConversionChange() {
   
    this.loadingRatesFormGroup?.controls['maxLoadingRate'].clearValidators();
    this.loadingRatesFormGroup?.controls['shoreLoadingRate'].clearValidators();
    this.loadingRatesFormGroup?.controls['minLoadingRate'].clearValidators();
    this.loadingRatesFormGroup?.controls['minDeBallastingRate'].clearValidators();
    this.loadingRatesFormGroup?.controls['maxDeBallastingRate'].clearValidators();
    if (this.selectedConversion?.id === 2) {
      for (let key in this.actualValues) {
        if (this.loadingRatesFormGroup?.value[key] !== "" && this.loadingRatesFormGroup?.value[key] != null) {
          this.loadingRatesFormGroup?.patchValue({
            [key]: (this.actualValues[key].lastEditedUnit == 'BBLS') ? (this.actualValues[key].defaultValue) : Math.round(Number(this.loadingRatesFormGroup?.value[key] * this.conversionFactor)),

          })
        }
      };
      this.loadingRatesFormGroup?.controls['maxLoadingRate'].setValidators([numberValidator(0, 6),compareNumberValidator('minLoadingRate', '<'), Validators.min(Math.round(4000 * this.conversionFactor)), Validators.max(Math.round(21000 * this.conversionFactor))]);
      this.loadingRatesFormGroup?.controls['maxLoadingRate'].updateValueAndValidity();
      this.loadingRatesFormGroup?.controls['shoreLoadingRate'].setValidators([numberValidator(0, 6), Validators.min(Math.round(1000 * this.conversionFactor)), Validators.max(Math.round(20000 *this.conversionFactor))]);
      this.loadingRatesFormGroup?.controls['shoreLoadingRate'].updateValueAndValidity();
      this.loadingRatesFormGroup?.controls['minLoadingRate'].setValidators([numberValidator(0, 5),compareNumberValidator('maxLoadingrate', '>'), Validators.min(Math.round(1000 * this.conversionFactor)), Validators.max(Math.round(3000 * this.conversionFactor))]);
      this.loadingRatesFormGroup?.controls['minLoadingRate'].updateValueAndValidity();
      this.loadingRatesFormGroup?.controls['minDeBallastingRate'].setValidators([numberValidator(0, 5), Validators.min(Math.round(2500 * this.conversionFactor)), Validators.max(Math.round(4000 * this.conversionFactor))]);
      this.loadingRatesFormGroup?.controls['minDeBallastingRate'].updateValueAndValidity();
      this.loadingRatesFormGroup?.controls['maxDeBallastingRate'].setValidators([numberValidator(0, 5), Validators.min(Math.round(6000 * this.conversionFactor)), Validators.max(Math.round(7500 * this.conversionFactor))]);
      this.loadingRatesFormGroup?.controls['maxDeBallastingRate'].updateValueAndValidity();
     
    } else {     
      for (let key in this.actualValues) {
        if (this.loadingRatesFormGroup?.value[key] !== "" && this.loadingRatesFormGroup?.value[key] != null) {
          this.loadingRatesFormGroup?.patchValue({
            [key]: (this.actualValues[key].lastEditedUnit == 'M3') ? (this.actualValues[key].defaultValue) : Math.round(Number(this.loadingRatesFormGroup?.value[key] / this.conversionFactor)),

          })
        }
      };
      this.loadingRatesFormGroup?.controls['maxLoadingRate'].setValidators([numberValidator(0, 6), compareNumberValidator('minLoadingRate', '<'), Validators.min(4000), Validators.max(21000)]);
      this.loadingRatesFormGroup?.controls['maxLoadingRate'].updateValueAndValidity();
      this.loadingRatesFormGroup?.controls['shoreLoadingRate'].setValidators([numberValidator(0, 5), Validators.min(1000), Validators.max(20000)]);
      this.loadingRatesFormGroup?.controls['shoreLoadingRate'].updateValueAndValidity();
      this.loadingRatesFormGroup?.controls['minLoadingRate'].setValidators([numberValidator(0, 4), compareNumberValidator('maxLoadingrate', '>'), Validators.min(1000), Validators.max(3000)]);
      this.loadingRatesFormGroup?.controls['minLoadingRate'].updateValueAndValidity();
      this.loadingRatesFormGroup?.controls['minDeBallastingRate'].setValidators([numberValidator(0, 4), Validators.min(2500), Validators.max(4000)]);
      this.loadingRatesFormGroup?.controls['minDeBallastingRate'].updateValueAndValidity();
      this.loadingRatesFormGroup?.controls['maxDeBallastingRate'].setValidators([numberValidator(0, 4), Validators.min(6000), Validators.max(7500)]);
      this.loadingRatesFormGroup?.controls['maxDeBallastingRate'].updateValueAndValidity();
    }
    this.errorMesages = this.loadingDischargingTransformationService.setValidationMessageForLoadingRate(this.selectedConversion.unit);
    this.loadingRatesFormGroup.markAllAsTouched();
    this.loadingRatesFormGroup.markAsTouched();
  
  }

}

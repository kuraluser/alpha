import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { numberValidator } from '../../core/directives/number-validator.directive';
import { IBerth, IBerthDetails } from '../models/loading-discharging.model';
import { LoadingDischargingTransformationService } from '../services/loading-discharging-transformation.service';
import { LoadingBerthDuplicateValidator } from '../directives/validator/loading-berth-duplicate-validator.directive';
import { alphaNumericSpecialCharacterValidator } from '../../core/directives/alpha-numeric-special-character-validator.directive.ts';
import { OPERATIONS } from '../../core/models/common.model';

/**
 * Component class for loading discharging berth component
 *
 * @export
 * @class LoadingDischargingBerthComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-loading-discharging-berth',
  templateUrl: './loading-discharging-berth.component.html',
  styleUrls: ['./loading-discharging-berth.component.scss']
})
export class LoadingDischargingBerthComponent implements OnInit {
  @Input() editMode = true;
  @Input() loadingInfoId: number;
  @Input() dischargeInfoId: number;
  @Input() operation: OPERATIONS;
  @Input() get berthDetails(): IBerthDetails {
    return this._berthDetails;
  }

  set berthDetails(berthDetails: IBerthDetails) {
    this._berthDetails = berthDetails;
    if (berthDetails) {
      this.availableBerths = berthDetails.availableBerths ?? [];
      this.selectedBerths = berthDetails.selectedBerths ?? [];
      this.initBerths();
    }
  }


  @Output() berthChange: EventEmitter<IBerth[]> = new EventEmitter();

  private _berthDetails: IBerthDetails;

  readonly OPERATIONS = OPERATIONS;
  berthDetailsForm: FormGroup;
  berthForm: FormGroup;
  selectedIndex: number;
  errorMesages: any;
  disableAddBtn = false;
  selectedBerths: IBerth[]
  availableBerths: IBerth[]
  constructor(
    private fb: FormBuilder,
    private loadingDischargingTransformationService: LoadingDischargingTransformationService,
    private confirmationService: ConfirmationService,
    private translateService: TranslateService,
    private messageService: MessageService
  ) { }

  ngOnInit(): void {
    this.errorMesages = this.loadingDischargingTransformationService.setBerthValidationErrorMessage();
    this.initBerths();
  }


  /**
  * initialise berth details
  */
  initBerths() {

    this.berthDetailsForm = this.fb.group({
      berthId: 0,
      berthName: '',
      maxShipDepth: this.fb.control('', [Validators.required, Validators.min(1), Validators.max(99.99), numberValidator(4, 2)]),
      hoseConnections: this.fb.control('', [Validators.maxLength(100),alphaNumericSpecialCharacterValidator]),
      seaDraftLimitation: this.fb.control(null, [Validators.required, Validators.min(1), Validators.max(99.99), numberValidator(4, 2)]),
      airDraftLimitation: this.fb.control('', [Validators.required, Validators.min(1), Validators.max(99.99), numberValidator(4, 2)]),
      maxManifoldHeight: this.fb.control('', [Validators.required, Validators.min(1), Validators.max(99.99), numberValidator(4, 2)]),
      regulationAndRestriction: this.fb.control('', [Validators.maxLength(500), Validators.pattern(/[a-zA-Z0-9]/)]),
      itemsToBeAgreedWith: this.fb.control('', [Validators.maxLength(500), Validators.pattern(/[a-zA-Z0-9]/)]),
      loadingInfoId: '',
      dischargeInfoId: '',
      maxShpChannel: '',
      loadingBerthId: 0,
      maxLoa: '',
      maxDraft: '',
      lineDisplacement: this.fb.control('', [numberValidator(0, 6), Validators.min(500), Validators.max(200000)])
    });

    if(this.operation === OPERATIONS.DISCHARGING) {
      this.berthDetailsForm.addControl('maxManifoldPressure', this.fb.control('', [Validators.required, Validators.min(0.1), Validators.max(99.99), numberValidator(4, 2)]))
      this.berthDetailsForm.addControl('cargoCirculation', this.fb.control(false));
      this.berthDetailsForm.addControl('airPurge', this.fb.control(false));
    }
    this.berthDetailsForm.disable();
    this.initFormArray();
  }

  /**
 * Return the form controlls of the berth form array
 */
  get berthFormArray(): FormArray {
    return this.berthForm?.get("berth") as FormArray
  }

  /**
   * Return the form controlls of the berth details form
   */
  get berthDetailsFormControl() {
    return this.berthDetailsForm.controls;
  }

  /**
* initialise berth details form
*/
  initFormArray() {
    if (this.selectedBerths?.length && this.availableBerths?.length) {
      this.selectedBerths = this.selectedBerths.map((berth) => {
        const foundedBerth = this.availableBerths.find((availBerth) => availBerth.berthId === berth.berthId);
        berth.berthName = foundedBerth?.berthName;
        return berth;
      });
      this.availableBerths = this.availableBerths.map(availBerth => this.selectedBerths.find(selectedBerth => selectedBerth.berthId === availBerth.berthId) || availBerth);
      const initialBerthArray = this.selectedBerths.map((selectedBerth, i) => this.createBerth(selectedBerth));
      this.berthForm = this.fb.group({
        berth: this.fb.array([...initialBerthArray])
      });
      this.setBerthDetails(this.selectedBerths[0], 0)
      this.selectedIndex = 0;
    } else {
      this.berthForm = this.fb.group({
        berth: this.fb.array([])
      });
    }

  }

  /**
  * Metgod for adding new berth form
  *
  * @memberof LoadingDischargingBerthComponent
  */
  createBerth(berth: IBerth): FormGroup {
    return this.fb.group({
      name: this.fb.control(berth, [LoadingBerthDuplicateValidator]),
      edit: berth ? false : true,
      formValid: true
    });
  }

  /**
  * Change value
  *
  * @memberof LoadingDischargingBerthComponent
  */
  change(field) {
    if (this.berthDetailsForm.value[field]) {
      this.selectedBerths.map((berth) => {
        if (berth.berthId === this.berthDetailsForm.value.berthId) {
          berth[field] = this.berthDetailsForm.value[field];
        }
        return berth;
      })
    } else {
      this.selectedBerths.map((berth) => {
        if(berth.berthId === this.berthDetailsForm.value.berthId) {
          if (!(field === 'lineDisplacement' ||  field === 'hoseConnections')) {
            berth[field] = 0;
          } else {
            berth[field] = '';
          }
        }

        return berth;
      })
    }
    this.checkFormValidity();
    this.berthChange.emit(this.selectedBerths);
  }
  clearFilter(data) {

  }

  /**
   * Add new berth
   *
   * @memberof LoadingDischargingBerthComponent
   */
  async addBerth(berth: IBerth, index: number) {
    if (this.availableBerths.length === 0) {
      const translationKeys = await this.translateService.get(['LOADING_DISCHARGING_BERTH', 'LOADING_DISCHARGING_BERTH_NO_BERTH_AVAILABLE']).toPromise();
      this.messageService.add({ severity: 'info', summary: translationKeys['LOADING_DISCHARGING_BERTH'], detail: translationKeys["LOADING_DISCHARGING_BERTH_NO_BERTH_AVAILABLE"] });
      return;
    }
    if (this.berthFormArray?.controls?.length >= 4) {
      this.disableAddBtn = false;
    }
    else {
      let required;
      this.berthFormArray.controls.forEach((berthDetails, i) => {
        if (!berthDetails?.value?.name) {
          required = true;
          berthDetails.updateValueAndValidity();
        } else {
          this.berthFormArray.at(i).patchValue({
            edit: false
          })
        }
      });
      if(!required) {
        this.berthFormArray.push(this.createBerth(berth));
      }
      this.updateFormValidity(this.berthFormArray.controls);
    }
  }

  /**
   * Berth change
   * @param {Event}
   * @memberof LoadingDischargingBerthComponent
   */
  onBerthChange(event, index) {
    this.updateFormValidity(this.berthFormArray.controls);
    this.checkFormValidity();
    const formControl = this.field(index, 'name');
      this.selectedBerths[index] = event.value;
      this.setBerthDetails(event.value, index , true);
      this.selectedBerths = this.selectedBerths.map((berth) => {
        if (!berth.loadingBerthId) {
          berth.loadingBerthId = 0;
        }
        return berth;
      })
      this.berthChange.emit(this.selectedBerths);
  }

    /**
     * Update validity of invalid rows if valid
     *
     * @memberof LoadingDischargingBerthComponent
    */
    updateFormValidity(formArray) {
      formArray.forEach(async (row: FormGroup, index) => {
        if (row.invalid && row.touched) {
          const invalidFormControls = this.findInvalidControlsRecursive(row);
          invalidFormControls.forEach((key) => {
            const formControl = this.field(index, key);
            formControl.updateValueAndValidity();
          });
        }
      })
    }

  /**
   * Method get all invalid fields in a row
   *
   * @private
   * @param {FormGroup} formToInvestigate
   * @returns {string[]}
   * @memberof LoadingDischargingBerthComponent
   */
  private findInvalidControlsRecursive(formToInvestigate: FormGroup): string[] {
    const invalidControls: string[] = [];
    const recursiveFunc = (form: FormGroup | FormArray) => {
      Object.keys(form.controls).forEach(field => {
        const control = form.get(field);
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

  /**
   * choose berth
   * @param berth
   * @memberof LoadingDischargingBerthComponent
   */
  selectBerth(berth, index, edit: boolean = false) {
    this.checkFormValidity();
    this.selectedIndex = index;
    this.setBerthDetails(berth.value.name, index, edit);
    this.berthDetailsForm.markAllAsTouched();
  }

  /**
   * Assign values for berth display
   * @param berthInfo
   * @memberof LoadingDischargingBerthComponent
   */
  setBerthDetails(berthInfo: IBerth, index: number, edit: boolean = false) {
    const lineDisplacement = Number(berthInfo.lineDisplacement);
    let berthDetailsForm = {
      berthId: berthInfo.berthId,
      portId: berthInfo.portId,
      berthName: berthInfo.berthName,
      maxShipDepth: berthInfo.maxShipDepth,
      hoseConnections: berthInfo.hoseConnections,
      seaDraftLimitation: berthInfo.seaDraftLimitation,
      airDraftLimitation: berthInfo.airDraftLimitation,
      maxManifoldHeight: berthInfo.maxManifoldHeight,
      regulationAndRestriction: berthInfo.regulationAndRestriction,
      itemsToBeAgreedWith: berthInfo.itemsToBeAgreedWith,
      loadingInfoId: berthInfo?.loadingInfoId,
      dischargeInfoId: berthInfo?.dischargeInfoId,
      maxShpChannel: berthInfo.maxShpChannel,
      loadingBerthId: berthInfo?.loadingBerthId ? berthInfo?.loadingBerthId : 0,
      dischargingBerthId: berthInfo?.dischargingBerthId ? berthInfo?.dischargingBerthId : 0,
      maxLoa: berthInfo.maxLoa,
      maxDraft: berthInfo.maxDraft,
      lineDisplacement: lineDisplacement ? Math.trunc(lineDisplacement) : ''
    };

    if (this.operation === OPERATIONS.DISCHARGING) {
      const additionalDetails = {
        maxManifoldPressure: Number(berthInfo?.maxManifoldPressure),
        cargoCirculation: berthInfo?.cargoCirculation,
        airPurge: berthInfo?.airPurge
      }
      berthDetailsForm = { ...berthDetailsForm, ...additionalDetails };
    }
    this.berthDetailsForm.patchValue(berthDetailsForm);
    this.berthDetailsForm.updateValueAndValidity();
    setTimeout(() => {
      if (!edit) {
        this.berthFormArray.at(index).patchValue({
          edit: false
        })
        this.berthDetailsForm.disable();
      } else {
        this.berthDetailsForm.enable();
        this.berthDetailsForm.markAllAsTouched();
        this.berthDetailsForm.markAsDirty();
        this.berthDetailsForm.updateValueAndValidity();
        this.berthFormArray.at(index).patchValue({
          edit: true,
          formValid: this.berthDetailsForm.valid
        })
      }
      this.berthFormArray.controls.forEach((berth, i) => {
        if (i !== index && berth?.value?.name) {
          this.berthFormArray.at(i).patchValue({
            edit: false
          })
        }
      });
    })
  }

  /**
   * Method to get error details
   * @memberof LoadingDischargingBerthComponent
   */
  getErrorDetails(index) {
    if(this.berthFormArray.at(index)['controls']?.name?.errors) {
      return this.berthFormArray.at(index)['controls']?.name?.errors;
    } else if(!this.berthFormArray.at(index)['controls']?.formValid?.value) {
      this.berthFormArray.at(index)['controls']?.formValid.setErrors({ 'invalidData': true });
      return this.berthFormArray.at(index)['controls']?.formValid?.errors;
    } else {
      this.berthFormArray.at(index)['controls']?.formValid.setErrors(null);
      return null;
    }
  }

  /**
   * Method to check form valid
   * @memberof LoadingDischargingBerthComponent
   */
  checkFormValidity() {
    this.berthDetailsForm.updateValueAndValidity();
    this.berthFormArray.value.map((formDetails,index) => {
      if(formDetails.edit) {
        this.berthFormArray.at(index).patchValue({
          formValid: this.berthDetailsForm.valid
        })
      }
    })
  }

  /**
   * edit berth info
   * @param berth
   * @memberof LoadingDischargingBerthComponent
   */
  editBerth(berth, index) {
    this.berthFormArray.at(index).patchValue({
      edit: true
    });
    this.berthDetailsForm.enable();
    this.selectBerth(berth, index, true);
    this.berthFormArray.controls.forEach((element, i) => {
      if (i !== index) {
        this.berthFormArray.at(i).patchValue({
          edit: false
        })
      }
    });
    this.berthDetailsForm.enable();
  }

  /**
   * Remove berth info
   * @param berth
   * @memberof LoadingDischargingBerthComponent
   */
  async deleteBerth(event, index: number) {
    const translationKeys = await this.translateService.get(['BERTH_DELETE_HEADER', 'BERTH_DELETE_SUMMARY', 'BERTH_DELETE_CONFIRM_LABEL', 'BERTH_DELETE_SUCCESS', 'BERTH_DELETE_SUCCESSFULLY', 'BERTH_DELETE_ERROR', 'BERTH_ERROR_SUMMARY']).toPromise();
    this.confirmationService.confirm({
      key: 'confirmation-alert',
      header: translationKeys['BERTH_DELETE_HEADER'],
      message: translationKeys['BERTH_DELETE_SUMMARY'],
      icon: 'pi pi-exclamation-triangle',
      acceptLabel: translationKeys['BERTH_DELETE_CONFIRM_LABEL'],
      acceptIcon: 'pi',
      acceptButtonStyleClass: 'btn btn-main mr-5',
      rejectVisible: true,
      rejectIcon: 'pi',
      rejectButtonStyleClass: 'btn btn-main',
      accept: async () => {
        if (this.selectedBerths?.length > 1 && event.value.name) {
          this.selectedBerths.splice(index,1);
          this.berthFormArray.removeAt(index);
          if (this.selectedBerths?.length > 0) {
            this.setBerthDetails(this.selectedBerths[0], 0)
          } else {
            this.berthDetailsForm.reset();
            this.berthDetailsForm.disable();
          }
          this.updateFormValidity(this.berthFormArray.controls);
          this.berthChange.emit(this.selectedBerths);
        } else if(!event.value.name) {
          this.berthFormArray.removeAt(index);
        } else {
          this.messageService.add({ severity: 'warn', summary: translationKeys['BERTH_DELETE_ERROR'], detail: translationKeys['BERTH_ERROR_SUMMARY'] });
        }
      }


    });

  }

  /**
  * Get form control of form
  *
  * @param {number} formGroupIndex
  * @param {string} formControlName
  * @returns {FormControl}
  * @memberof LoadingDischargingBerthComponent
  */
  field(formGroupIndex: number, formControlName: string): FormControl {
    const formControl = <FormControl>(<FormArray>this.berthForm.get('berth')).at(formGroupIndex).get(formControlName);
    return formControl;
  }

  /**
  *Method to check for field errors
  *
  * @param {string} formControlName
  * @param {number} indexOfFormgroup
  * @return {ValidationErrors}
  * @memberof LoadingDischargingBerthComponent
  */
  fieldError(formControlName: string): ValidationErrors {
    const formControl = <FormControl>this.berthDetailsForm.get(formControlName);
    return formControl?.invalid && (formControl.dirty || formControl.touched) ? formControl.errors : null;
  }


}

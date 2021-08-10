import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { numberValidator } from '../../core/directives/number-validator.directive';
import { IBerth, IBerthDetails } from '../models/loading-discharging.model';
import { LoadingDischargingTransformationService } from '../services/loading-discharging-transformation.service';
import { LoadingBerthDuplicateValidator } from '../validators/loading-berth-duplicate-validator.directive';
import { alphaNumericSpecialCharacterValidator } from '../../core/directives/alpha-numeric-special-character-validator.directive.ts';

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
  @Input() dischargingInfoId: number;
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
      maxShipDepth: this.fb.control('', [numberValidator(4, 2)]),
      hoseConnections: this.fb.control('', [Validators.maxLength(100),alphaNumericSpecialCharacterValidator]),
      seaDraftLimitation: this.fb.control(null, [numberValidator(4, 2)]),
      airDraftLimitation: this.fb.control('', [numberValidator(4, 2)]),
      maxManifoldHeight: this.fb.control('', [numberValidator(4, 2)]),
      regulationAndRestriction: this.fb.control('', [Validators.maxLength(500)]),
      itemsToBeAgreedWith: this.fb.control('', [Validators.maxLength(500)]),
      loadingInfoId: '',
      dischargingInfoId: '',
      maxShpChannel: '',
      loadingBerthId: 0,
      maxLoa: '',
      maxDraft: '',
      lineDisplacement: this.fb.control('', [Validators.required, numberValidator(0, 6), Validators.min(500), Validators.max(200000)])
    });
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
    if (this.selectedBerths.length && this.availableBerths.length) {
      this.selectedBerths = this.selectedBerths.map((berth) => {
        const foundedBerth = this.availableBerths.find((availBerth) => availBerth.berthId === berth.berthId);
        berth.berthName = foundedBerth.berthName;
        return berth;
      });
      this.availableBerths = this.availableBerths.map(availBerth => this.selectedBerths.find(selectedBerth => selectedBerth.berthId === availBerth.berthId) || availBerth);
      const initialBerthArray = this.selectedBerths.map((selectedBerth, i) => this.createBerth(selectedBerth, i));
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
  createBerth(berth: IBerth, index: number): FormGroup {
    return this.fb.group({
      name: this.fb.control(berth, [LoadingBerthDuplicateValidator(index)]),
      edit: berth ? false : true
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
        if (berth.berthId === this.berthDetailsForm.value.berthId) {
          berth[field] = 0;
        }
        return berth;
      })
    }
    if (this.berthDetailsForm.valid) {
      this.berthChange.emit(this.selectedBerths);
    }
  }
  clearFilter(data) {

  }

  /**
   * Add new berth
   *
   * @memberof LoadingDischargingBerthComponent
   */
  addBerth(berth: IBerth, index: number) {
    if (this.berthFormArray?.controls?.length >= 4) {
      this.disableAddBtn = false;
    }
    else {
      this.berthFormArray.push(this.createBerth(berth, index));
    }
  }

  /**
   * Berth change
   * @param {Event}
   * @memberof LoadingDischargingBerthComponent
   */
  onBerthChange(event, index) {
    const formControl = this.field(index, 'name');
    if (formControl.valid) {
      this.selectedBerths.push(event.value);
      this.setBerthDetails(event.value, index);
      this.selectedBerths = this.selectedBerths.map((berth) => {
        if (!berth.loadingBerthId) {
          berth.loadingBerthId = 0;
        }
        return berth;
      })
      this.berthChange.emit(this.selectedBerths);
    }
  }

  /**
   * choose berth
   * @param berth
   * @memberof LoadingDischargingBerthComponent
   */
  selectBerth(berth, index, edit: boolean = false) {
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
    this.berthDetailsForm.patchValue({
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
      dischargingInfoId: berthInfo?.dischargingInfoId,
      maxShpChannel: berthInfo.maxShpChannel,
      loadingBerthId: berthInfo?.loadingBerthId ? berthInfo?.loadingBerthId : 0,
      dischargingBerthId: berthInfo?.dischargingBerthId ? berthInfo?.dischargingBerthId : 0,
      maxLoa: berthInfo.maxLoa,
      maxDraft: berthInfo.maxDraft,
      lineDisplacement: berthInfo.lineDisplacement
    });
    setTimeout(() => {
      if (!edit) {
        if (this.berthDetailsForm.valid) {
          this.berthFormArray.at(index).patchValue({
            edit: false
          })
          this.berthDetailsForm.disable();
        } else {
          this.berthFormArray.at(index).patchValue({
            edit: true
          })
          this.berthDetailsForm.enable();
          this.berthDetailsForm.markAsTouched();
          this.berthDetailsForm.markAllAsTouched();
          this.berthDetailsForm.markAsDirty();
          this.berthDetailsForm.updateValueAndValidity();
        }
        this.berthFormArray.controls.forEach((berth, i) => {
          if (i !== index && berth?.value?.name) {
            this.berthFormArray.at(i).patchValue({
              edit: false
            })
          }
        });
      }
    }, 200);
  }

  /**
   * edit berth info
   * @param berth
   * @memberof LoadingDischargingBerthComponent
   */
  editBerth(berth, index) {
    this.selectBerth(berth, index, true);
    this.berthFormArray.at(index).patchValue({
      edit: true
    })
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
        if (this.selectedBerths?.length > 1) {
          this.selectedBerths = this.selectedBerths?.filter((berth) => berth.berthId !== event.value.name.berthId) ?? [];
          this.berthFormArray.removeAt(index);
          if (this.selectedBerths?.length > 0) {
            this.setBerthDetails(this.selectedBerths[0], 0)
          } else {
            this.berthDetailsForm.reset();
            this.berthDetailsForm.disable();
          }
          this.berthChange.emit(this.selectedBerths);
        }
        else {
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

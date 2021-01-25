import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { LoadableQuantityApiService } from '../../services/loadable-quantity-api.service';
import { LodadableQuantity } from '../../models/loadable-quantity.model';
import { LoadableStudyDetailsApiService } from '../../services/loadable-study-details-api.service';
import { LoadableStudy } from '../../models/loadable-study-list.model';
import { Voyage,IPort } from '../../../core/models/common.model';
import { numberValidator } from '../../directives/validator/number-validator.directive';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';
import { AppConfigurationService } from '../../../../shared/services/app-configuration/app-configuration.service';
import { IPermissionContext, PERMISSION_ACTION } from '../../../../shared/models/common.model';
import { IPermission } from '../../../../shared/models/user-profile.model';
import { PermissionsService } from '../../../../shared/services/permissions/permissions.service';
import { LoadableStudyDetailsTransformationService } from '../../services/loadable-study-details-transformation.service';

/**
 *  popup for loadable quantity
 */
@Component({
  selector: 'cpdss-portal-loadable-quantity',
  templateUrl: './loadable-quantity.component.html',
  styleUrls: ['./loadable-quantity.component.scss']
})
export class LoadableQuantityComponent implements OnInit {

  @Input() selectedLoadableStudy: LoadableStudy;
  @Input() vesselId: number;
  @Input() voyage: Voyage;
  @Output() displayPopUp = new EventEmitter<boolean>();
  @Output() newLoadableQuantity = new EventEmitter<string>();

  display = true;
  loadableQuantityForm: FormGroup;
  portData: any[];
  selectedPort: IPort;
  isSummerZone: boolean;
  loadableQuantity: LodadableQuantity;
  ports: IPort[];
  lastUpdatedDateAndTime: string;
  totalLoadableQuantity: number;
  isNegative = false;
  loadableQuantityBtnPermissionContext: IPermissionContext;
  errorMesages: any;
  permission: IPermission;
  isEditable = false;
  caseNo: number;
  selectedZone: string;
  loadableQuantityId: number;
  buttonLabel: string;

  private _loadableStudies: LoadableStudy[];

  constructor(private fb: FormBuilder,
    private loadableQuantityApiService: LoadableQuantityApiService,
    private loadableStudyDetailsApiService: LoadableStudyDetailsApiService,
    private ngxSpinnerService: NgxSpinnerService,
    private messageService: MessageService,
    private translateService: TranslateService,
    private permissionsService: PermissionsService,
    private loadableStudyDetailsTransformationService: LoadableStudyDetailsTransformationService
  ) { }

  /**
   * Component lifecycle ngOnit
   *
   * @returns {Promise<void>}
   * @memberof LoadableQuantityComponent
   */
  async ngOnInit(): Promise<void> {
    this.loadableQuantityBtnPermissionContext = { key: AppConfigurationService.settings.permissionMapping['LoadableQuantityComponent'], actions: [PERMISSION_ACTION.VIEW, PERMISSION_ACTION.ADD] };
    this.permission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['LoadableQuantityComponent'], false);
    this.isEditable = this.permission ? this.permission?.edit : false;
    this.errorMesages = this.loadableStudyDetailsTransformationService.setValidationErrorMessageForLoadableQuantity();
    this.ports = await this.getPorts();
    this.getLoadableQuantity();
  }

  /**
   * Get Loadable Quantity
   */
  async getLoadableQuantity() {
    this.ngxSpinnerService.show();
    const loadableQuantityResult = await this.loadableQuantityApiService.getLoadableQuantity(this.vesselId, this.voyage.id, this.selectedLoadableStudy.id).toPromise();
    if (loadableQuantityResult.responseStatus.status === '200') {
      this.caseNo = loadableQuantityResult.caseNo;
      this.selectedZone = loadableQuantityResult.selectedZone;
      this.loadableQuantity = loadableQuantityResult.loadableQuantity;
      this.selectedPort = this.ports.find(port => port.id === this.loadableQuantity.portId);
      this.lastUpdatedDateAndTime = this.loadableQuantity.updateDateAndTime;
      this.loadableQuantityId = this.loadableQuantity.loadableQuantityId;
      this.buttonLabel = this.loadableQuantityId ? 'LOADABLE_QUANTITY_UPDATE' : 'LOADABLE_QUANTITY_SAVE';

      this.loadableQuantityForm = this.fb.group({
        portName: [this.ports.find(port => port.id === this.loadableQuantity.portId), Validators.required],
        arrivalMaxDraft: ['', [Validators.required, numberValidator(2, 2)]],
        dwt: ['', [Validators.required]],
        tpc: ['', [Validators.required, numberValidator(1, 3)]],
        estimateSag: ['', [Validators.required, numberValidator(2, 2), , Validators.min(0)]],
        safCorrection: ['', [Validators.required, numberValidator(5, 7), Validators.min(0)]],
        foOnboard: [{ value: '', disabled: true} , [Validators.required, numberValidator(2, 7)]],
        doOnboard: [{ value: '', disabled: true}, [Validators.required, numberValidator(2, 7)]],
        freshWaterOnboard: [{ value: '', disabled: true}, [Validators.required, numberValidator(2, 7)]],

        boilerWaterOnboard: [{ value: '', disabled: true}, [Validators.required, numberValidator(0, 7), Validators.pattern(/^[0-9]\d{0,6}$/)]],

        ballast: ['', [Validators.required, numberValidator(2, 7), Validators.min(0)]],
        constant: ['', [Validators.required, numberValidator(2)]],
        others: ['', [Validators.required, numberValidator(2, 7), Validators.min(0)]],
        subTotal: ['', numberValidator(2, 7)],
        totalQuantity: ['', numberValidator(2, 7)]

      });

      if (this.caseNo === 1) {
        this.loadableQuantityForm.addControl('distanceInSummerzone', this.fb.control('', [Validators.required, numberValidator(2, 2), Validators.min(0)]));
        this.loadableQuantityForm.addControl('speedInSz', this.fb.control('', [Validators.required, numberValidator(2, 7), Validators.min(0.01)]));
        this.loadableQuantityForm.addControl('runningHours', this.fb.control('', [Validators.required, numberValidator(2, 7), Validators.min(0)]));
        this.loadableQuantityForm.addControl('runningDays', this.fb.control('', [Validators.required, numberValidator(2, 7), Validators.min(0)]));
        this.loadableQuantityForm.addControl('foConday', this.fb.control('', [Validators.required, numberValidator(2, 7), Validators.min(0)]));
        this.loadableQuantityForm.addControl('foConsInSz', this.fb.control('', [Validators.required, numberValidator(5, 7), Validators.min(0)]));
      }
      if (this.caseNo === 3) {
        this.loadableQuantityForm.addControl('displacement', this.fb.control('', [Validators.required]));
        this.loadableQuantityForm.addControl('lwt', this.fb.control('', [Validators.required]));
        this.loadableQuantityForm.addControl('estSeaDensity', this.fb.control('', [Validators.required, numberValidator(3, 1), Validators.min(0)]));
        this.loadableQuantityForm.addControl('sgCorrection', this.fb.control('', [Validators.required, numberValidator(5, 7)]));
      }

      this.getLoadableQuantityData();

    }
    this.ngxSpinnerService.hide();

  }

  /**
   * Populate loadable quantity data
   */
  getLoadableQuantityData() {
    this.loadableQuantityForm.controls.portName.setValue(this.ports.find(port => port.id === this.loadableQuantity.portId));
    this.loadableQuantityForm.controls.arrivalMaxDraft.setValue(this.loadableQuantity.draftRestriction);
    this.loadableQuantityForm.controls.dwt.setValue(this.loadableQuantity.dwt);
    this.loadableQuantityForm.controls.tpc.setValue(this.loadableQuantity.tpc);
    this.loadableQuantityForm.controls.estimateSag.setValue(this.loadableQuantity.estSagging);
    this.loadableQuantityForm.controls.safCorrection.setValue(this.loadableQuantity.saggingDeduction);
    this.loadableQuantityForm.controls.foOnboard.setValue(Number(this.loadableQuantity.estFOOnBoard));
    this.loadableQuantityForm.controls.doOnboard.setValue(Number(this.loadableQuantity.estDOOnBoard));

    this.loadableQuantityForm.controls.freshWaterOnboard.setValue(Number(this.loadableQuantity.estFreshWaterOnBoard));
    this.loadableQuantityForm.controls.boilerWaterOnboard.setValue(Number(this.loadableQuantity.boilerWaterOnBoard));
    this.loadableQuantityForm.controls.ballast.setValue(this.loadableQuantity.ballast);
    this.loadableQuantityForm.controls.constant.setValue(this.loadableQuantity.constant);
    this.loadableQuantityForm.controls.others.setValue(this.loadableQuantity.otherIfAny === '' ? 0 : this.loadableQuantity.otherIfAny);
    this.loadableQuantityForm.controls.subTotal.setValue(this.loadableQuantity.subTotal);
    this.loadableQuantityForm.controls.totalQuantity.setValue(this.loadableQuantity.totalQuantity);

    if (this.caseNo === 1) {
      this.loadableQuantityForm.controls.distanceInSummerzone.setValue(this.loadableQuantity.distanceFromLastPort);
      this.loadableQuantityForm.controls.speedInSz.setValue(this.loadableQuantity.vesselAverageSpeed);
      this.loadableQuantityForm.controls.runningHours.setValue(this.loadableQuantity.runningHours);
      this.loadableQuantityForm.controls.runningDays.setValue(this.loadableQuantity.runningDays);
      this.loadableQuantityForm.controls.foConday.setValue(this.loadableQuantity.foConsumptionPerDay?.toString());
      this.loadableQuantityForm.controls.foConsInSz.setValue(this.loadableQuantity.foConInSZ);

      this.getRunningHoursOnLoad();
      this.getRunningDaysOnLoad();
      this.getSubTotal();
    }
    else if (this.caseNo === 3) {
      this.loadableQuantityForm.controls.displacement.setValue(this.loadableQuantity.displacmentDraftRestriction);
      this.loadableQuantityForm.controls.lwt.setValue(this.loadableQuantity.vesselLightWeight);
      this.loadableQuantityForm.controls.estSeaDensity.setValue(this.loadableQuantity.estSeaDensity);
      this.loadableQuantityForm.controls.sgCorrection.setValue(this.loadableQuantity.sgCorrection);

      this.getDWT();
      this.getSubTotal();
    }
    else {
      this.getSubTotal();
    }

  }
  /**
   * Return the form controlls of the form
   */
  get loadableQuantityFormControl() {
    return this.loadableQuantityForm.controls;
  }

  /**
   * save loadable quantity
   */
  async onSubmit() {

    if (this.loadableQuantityForm.valid && !this.isNegative) {
      this.ngxSpinnerService.show();
      if (this.caseNo === 1) {
        this.loadableQuantity = {

          loadableQuantityId: this.loadableQuantityId,
          portId: this.loadableQuantityForm.controls.portName.value.id,
          draftRestriction: this.loadableQuantityForm.controls.arrivalMaxDraft.value,
          dwt: this.loadableQuantityForm.controls.dwt.value,
          tpc: this.loadableQuantityForm.controls.tpc.value,
          estSagging: this.loadableQuantityForm.controls.estimateSag.value,
          saggingDeduction: this.loadableQuantityForm.controls.safCorrection.value,
          estFOOnBoard: this.loadableQuantityForm.controls.foOnboard.value,
          estDOOnBoard: this.loadableQuantityForm.controls.doOnboard.value,
          estFreshWaterOnBoard: this.loadableQuantityForm.controls.freshWaterOnboard.value,
          ballast: this.loadableQuantityForm.controls.ballast.value,
          boilerWaterOnBoard: this.loadableQuantityForm.controls.boilerWaterOnboard.value,
          constant: this.loadableQuantityForm.controls.constant.value,
          otherIfAny: this.loadableQuantityForm.controls.others.value,
          totalQuantity: this.loadableQuantityForm.controls.totalQuantity.value,

          distanceFromLastPort: this.loadableQuantityForm.controls.distanceInSummerzone.value,
          vesselAverageSpeed: this.loadableQuantityForm.controls.speedInSz.value,
          runningHours: this.loadableQuantityForm.controls.runningHours.value,
          runningDays: this.loadableQuantityForm.controls.runningDays.value,
          foConsumptionPerDay: this.loadableQuantityForm.controls.foConday.value,
          foConInSZ: this.loadableQuantityForm.controls.foConsInSz.value,
        }

      }
      else if (this.caseNo === 2) {
        this.loadableQuantity = {
          loadableQuantityId: this.loadableQuantityId,
          portId: this.loadableQuantityForm.controls.portName.value.id,
          draftRestriction: this.loadableQuantityForm.controls.arrivalMaxDraft.value,
          dwt: this.loadableQuantityForm.controls.dwt.value,
          tpc: this.loadableQuantityForm.controls.tpc.value,
          estSagging: this.loadableQuantityForm.controls.estimateSag.value,
          saggingDeduction: this.loadableQuantityForm.controls.safCorrection.value,
          estFOOnBoard: this.loadableQuantityForm.controls.foOnboard.value,
          estDOOnBoard: this.loadableQuantityForm.controls.doOnboard.value,
          estFreshWaterOnBoard: this.loadableQuantityForm.controls.freshWaterOnboard.value,
          ballast: this.loadableQuantityForm.controls.ballast.value,
          boilerWaterOnBoard: this.loadableQuantityForm.controls.boilerWaterOnboard.value,
          constant: this.loadableQuantityForm.controls.constant.value,
          otherIfAny: this.loadableQuantityForm.controls.others.value,
          totalQuantity: this.loadableQuantityForm.controls.totalQuantity.value
        }
      }
      else {
        this.loadableQuantity = {
          loadableQuantityId: this.loadableQuantityId,
          portId: this.loadableQuantityForm.controls.portName.value.id,
          draftRestriction: this.loadableQuantityForm.controls.arrivalMaxDraft.value,
          displacmentDraftRestriction: this.loadableQuantityForm.controls.displacement.value,
          vesselLightWeight: this.loadableQuantityForm.controls.lwt.value,
          dwt: this.loadableQuantityForm.controls.dwt.value,
          tpc: this.loadableQuantityForm.controls.tpc.value,
          estSeaDensity: this.loadableQuantityForm.controls.estSeaDensity.value,
          sgCorrection: this.loadableQuantityForm.controls.sgCorrection.value,
          estSagging: this.loadableQuantityForm.controls.estimateSag.value,
          saggingDeduction: this.loadableQuantityForm.controls.safCorrection.value,
          estFOOnBoard: this.loadableQuantityForm.controls.foOnboard.value,
          estDOOnBoard: this.loadableQuantityForm.controls.doOnboard.value,
          estFreshWaterOnBoard: this.loadableQuantityForm.controls.freshWaterOnboard.value,
          ballast: this.loadableQuantityForm.controls.ballast.value,
          boilerWaterOnBoard: this.loadableQuantityForm.controls.boilerWaterOnboard.value,
          constant: this.loadableQuantityForm.controls.constant.value,
          otherIfAny: this.loadableQuantityForm.controls.others.value,
          totalQuantity: this.loadableQuantityForm.controls.totalQuantity.value,
        }
      }
      this.loadableQuantityId ? this.loadableQuantity['loadableStudyId'] = this.selectedLoadableStudy.id: null; 
      const translationKeys = await this.translateService.get(['LOADABLE_QUANTITY_SUCCESS', 'LOADABLE_QUANTITY_SAVED_SUCCESSFULLY','LOADABLE_QUANTITY_UPDATE_SUCCESS','LOADABLE_QUANTITY_UPDATE_SUCCESSFULLY']).toPromise();
      const result = await this.loadableQuantityApiService.saveLoadableQuantity(this.vesselId, this.voyage.id, this.selectedLoadableStudy.id, this.loadableQuantity).toPromise();
      this.newLoadableQuantity.emit(this.loadableQuantity.totalQuantity);
      if (result.responseStatus.status === "200") {
        if(this.loadableQuantityId) {
          this.messageService.add({ severity: 'success', summary: translationKeys['LOADABLE_QUANTITY_UPDATE_SUCCESS'], detail: translationKeys['LOADABLE_QUANTITY_UPDATE_SUCCESSFULLY'] });
        } else {
          this.messageService.add({ severity: 'success', summary: translationKeys['LOADABLE_QUANTITY_SUCCESS'], detail: translationKeys['LOADABLE_QUANTITY_SAVED_SUCCESSFULLY'] });
        }
        

      }
      this.ngxSpinnerService.hide();
      this.cancel();
    }
    else {
      this.loadableQuantityForm.markAllAsTouched();
    }
  }

  /**
   * Cancel popup
   */
  cancel() {
    this.displayPopUp.emit(false);
  }

  /**
   * get ports
   */
  private async getPorts(): Promise<IPort[]> {
    const result = await this.loadableStudyDetailsApiService.getPorts().toPromise();
    return result;
  }

  /**
   * get DWT
   */
  getDWT() {
    const dwt = (Number(this.loadableQuantity.displacmentDraftRestriction) - Number(this.loadableQuantity.vesselLightWeight));
    this.loadableQuantityForm.controls['dwt'].setValue(dwt);

  }

  /**
   * Calculation for sag correction
   */
  getSagCorrectionOnChange() {
    this.loadableQuantityForm.controls['safCorrection'].setValue(Number(this.loadableQuantityForm.get('estimateSag').value) * 1 / 4 * Number(this.loadableQuantityForm.get('tpc').value));
    this.getSubTotal();
  }
  /**
   * Calculation for sg correction
   */
  getSgCorrectionOnChange() {
    this.loadableQuantityForm.controls['sgCorrection'].setValue((Number(this.loadableQuantityForm.get('estSeaDensity').value) - 1.025) / 1.025 * Number(this.loadableQuantityForm.get('displacement').value));
    this.getSubTotal();
  }

  /**
   * Calculation for running hours
   */
  getRunningHours() {
    // Auto calculate (Distance/ Speed)
    this.loadableQuantityForm.get('speedInSz').valid ? 
      (this.loadableQuantityForm.controls['runningHours'].setValue(Number(this.loadableQuantityForm.get('distanceInSummerzone').value) / Number(this.loadableQuantityForm.get('speedInSz').value)),
      this.getRunningDays()) : '';
  }

  /**
   * Calculation for running hours
   * Auto calculate (Distance/ Speed)
   */
  getRunningHoursOnLoad() {
    let runningHours = Number(this.loadableQuantity.distanceFromLastPort) / Number(this.loadableQuantity.vesselAverageSpeed);
    this.loadableQuantityForm.controls['runningHours'].setValue(isNaN(runningHours) ? 0 : runningHours);
  }

  /**
   * Calculation for running days
   * Auto calculate (Time/24)
   */
  getRunningDays() {
    if (this.loadableQuantityForm?.get('runningHours')?.value) {
      this.loadableQuantityForm.controls['runningDays'].setValue(Number(this.loadableQuantityForm.get('runningHours').value) / 24);
      this.getFoConsumptionInSz();
    }
  }
  /**
   * Calculation for running days
   */
  getRunningDaysOnLoad() {
    this.loadableQuantityForm.controls['runningDays'].setValue(Number(this.loadableQuantity.runningHours) / 24);
    this.loadableQuantityForm.controls['foConsInSz'].setValue(Number(this.loadableQuantityForm.get('foConday').value) * Number(this.loadableQuantityForm.get('runningDays').value));
    this.getSubTotalOnLoad();
  }

  /**
   * Calculation for fo consumption in sz
   */
  getFoConsumptionInSz() {
    this.loadableQuantityForm.controls['foConsInSz'].setValue(Number(this.loadableQuantityForm.get('foConday').value) * (this.loadableQuantityForm.get('runningDays').value));
    this.getTotalLoadableQuantity();

  }

  /**
   * Calculation for subtotal
   */
  getSubTotal() {
    let subTotal = 0;
    if (this.caseNo === 1 || this.caseNo === 2) {
      subTotal = Number(this.loadableQuantityForm.get('dwt').value)
        + Number(this.loadableQuantityForm.get('safCorrection').value)
        - Number(this.loadableQuantityForm.get('foOnboard').value) - Number(this.loadableQuantityForm.get('doOnboard').value)
        - Number(this.loadableQuantityForm.get('freshWaterOnboard').value) - Number(this.loadableQuantityForm.get('boilerWaterOnboard').value)
        - Number(this.loadableQuantityForm.get('ballast').value) - Number(this.loadableQuantityForm.get('constant').value)
        - Number(this.loadableQuantityForm.get('others').value);
      this.loadableQuantityForm.controls['subTotal'].setValue(subTotal);
      this.getTotalLoadableQuantity();
    }
    else {
      subTotal = Number(this.loadableQuantityForm.get('dwt').value) + Number(this.loadableQuantityForm.get('safCorrection').value) + Number(this.loadableQuantityForm.get('sgCorrection').value)
        - Number(this.loadableQuantityForm.get('foOnboard').value) - Number(this.loadableQuantityForm.get('doOnboard').value) - Number(this.loadableQuantityForm.get('freshWaterOnboard').value) - Number(this.loadableQuantityForm.get('boilerWaterOnboard').value) - Number(this.loadableQuantityForm.get('ballast').value) - Number(this.loadableQuantityForm.get('constant').value) - Number(this.loadableQuantityForm.get('others').value);
      this.loadableQuantityForm.controls['subTotal'].setValue(subTotal);
      this.getTotalLoadableQuantity();
    }
  }

  /**
   * Calculation for subtotal
   */
  getSubTotalOnLoad() {
    if (this.caseNo === 1) {
      this.loadableQuantityForm.controls['subTotal'].setValue(Number(this.loadableQuantity.totalQuantity) + Number(this.loadableQuantityForm.get('foConsInSz').value));
    }
    else {
      this.loadableQuantityForm.controls['subTotal'].setValue(Number(this.loadableQuantity.totalQuantity));
    }
  }

  /**
   * Calculation for Loadable quantity
   */
  getTotalLoadableQuantity() {
    if (this.caseNo === 1) {
      const total = Number(this.loadableQuantityForm.get('subTotal').value) - Number(this.loadableQuantityForm.get('foConsInSz').value);
      if (total < 0) {
        this.isNegative = true;
        this.loadableQuantityForm.controls['totalQuantity'].setValue('');
      }
      else {
        this.isNegative = false;
        this.loadableQuantityForm.controls['totalQuantity'].setValue(total);
      }

    }
    else {
      Number(this.loadableQuantityForm.get('subTotal').value) < 0 ? this.isNegative = true : this.isNegative = false;
      if (this.isNegative) {
        this.isNegative = true;
        this.loadableQuantityForm.controls['totalQuantity'].setValue('');
      }
      else {
        this.isNegative = false;
        this.loadableQuantityForm.controls['totalQuantity'].setValue(Number(this.loadableQuantityForm.get('subTotal').value));
      }

    }

  }
  /**
   *
   * @param type 
   * Get form control value to label
   */
  getControlLabel(type: string) {
    return this.loadableQuantityForm.controls[type].value;
  }


  /**
   * Get field errors
   *
   *
   * @param {string} formControlName
   * @returns {ValidationErrors}
   * @memberof LoadableQuantityComponent
   */
  fieldError(formControlName: string): ValidationErrors {
    const formControl = this.field(formControlName);
    return formControl.invalid && (formControl.dirty || formControl.touched) ? formControl.errors : null;
  }

  /**
 * Get form control of loadableQuantityForm 
 *
 *
 * @param {string} formControlName
 * @returns {FormControl}
 * @memberof LoadableQuantityComponent
 */
  field(formControlName: string): FormControl {
    const formControl = <FormControl>this.loadableQuantityForm.get(formControlName);
    return formControl;
  }

}

import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { LoadableQuantityApiService } from '../../services/loadable-quantity-api.service';
import { LodadableQuantity } from '../../models/loadable-quantity.model';
import { LoadableStudyDetailsApiService } from '../../services/loadable-study-details-api.service';
import { LoadableStudy } from '../../models/loadable-study-list.model';
import { Voyage, IPort, LOADABLE_STUDY_STATUS, VOYAGE_STATUS } from '../../../core/models/common.model';
import { numberValidator } from '../../../core/directives/number-validator.directive';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';
import { AppConfigurationService } from '../../../../shared/services/app-configuration/app-configuration.service';
import { IPermissionContext, PERMISSION_ACTION, ISubTotal } from '../../../../shared/models/common.model';
import { IPermission } from '../../../../shared/models/user-profile.model';
import { PermissionsService } from '../../../../shared/services/permissions/permissions.service';
import { LoadableStudyDetailsTransformationService } from '../../services/loadable-study-details-transformation.service';
import { Dropdown } from 'primeng/dropdown';
import { TimeZoneTransformationService } from '../../../../shared/services/time-zone-conversion/time-zone-transformation.service';

/**
 *  popup for loadable quantity
 */
@Component({
  selector: 'cpdss-portal-loadable-quantity',
  templateUrl: './loadable-quantity.component.html',
  styleUrls: ['./loadable-quantity.component.scss']
})
export class LoadableQuantityComponent implements OnInit {

  @Input()
  get selectedLoadableStudy() {
    return this._selectedLoadableStudy;
  }
  set selectedLoadableStudy(value: LoadableStudy) {
    this._selectedLoadableStudy = value;
    this.isEditable = (this.permission?.edit === undefined || this.permission?.edit) && [LOADABLE_STUDY_STATUS.PLAN_PENDING, LOADABLE_STUDY_STATUS.PLAN_NO_SOLUTION, LOADABLE_STUDY_STATUS.PLAN_ERROR].includes(value?.statusId) && ![VOYAGE_STATUS.CLOSE].includes(this.voyage?.statusId) ? true : false;
  }
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
  isEditableSeaWaterDensity = false;
  caseNo: number;
  selectedZone: string;
  loadableQuantityId: number;
  buttonLabel: string;
  isNoTpc = false;
  isNoDwt = false;
  isNoArrivalMaxDraft = null;
  isNodisplacement = false;
  isNoLwt = false;
  isNoConstant = false;
  portRotationId = 0;

  private _selectedLoadableStudy: LoadableStudy;

  constructor(private fb: FormBuilder,
    private loadableQuantityApiService: LoadableQuantityApiService,
    private loadableStudyDetailsApiService: LoadableStudyDetailsApiService,
    private timeZoneTransformationService: TimeZoneTransformationService,
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
    this.loadableQuantityBtnPermissionContext = { key: AppConfigurationService.settings.permissionMapping['LoadableQuantityComponent'], actions: [PERMISSION_ACTION.VIEW, PERMISSION_ACTION.ADD, PERMISSION_ACTION.EDIT] };
    this.permission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['LoadableQuantityComponent'], false);
    this.isEditable = (this.permission?.edit === undefined || this.permission?.edit || this.permission?.add === undefined || this.permission?.add) && [LOADABLE_STUDY_STATUS.PLAN_PENDING, LOADABLE_STUDY_STATUS.PLAN_NO_SOLUTION, LOADABLE_STUDY_STATUS.PLAN_ERROR].includes(this.selectedLoadableStudy?.statusId) && ![VOYAGE_STATUS.CLOSE].includes(this.voyage?.statusId);
    this.errorMesages = this.loadableStudyDetailsTransformationService.setValidationErrorMessageForLoadableQuantity();
    const portsData = await this.loadableStudyDetailsApiService.getPortsDetails(this.vesselId, this.voyage.id, this.selectedLoadableStudy.id).toPromise();
    const portList = portsData?.portList;
    if (portList) {
      const ports = await this.getPorts();
      this.ports = portList?.map((portlist) => {
        return { ...ports?.find((port) => port.id === portlist.portId), id: portlist?.id, portId: portlist?.portId, portOrder: portlist?.portOrder };
      });
      this.ports.sort((a, b) => {
        return a.portOrder - b.portOrder;
      });
      const lastModifiedPortIdExist = this.ports.some(port => port.id === portsData?.lastModifiedPortId);
      if(lastModifiedPortIdExist){
        this.selectedPort = this.ports.find(port => port.id === portsData?.lastModifiedPortId);
        this.portRotationId = portsData?.lastModifiedPortId;
      }else{
        this.selectedPort = this.ports[0];
        this.portRotationId = this.ports[0].id;
      }
    }
    this.getLoadableQuantity();
  }

  /**
   * Get Loadable Quantity
   */
  async getLoadableQuantity() {
    this.ngxSpinnerService.show();
    const loadableQuantityResult = await this.loadableQuantityApiService.getLoadableQuantity(this.vesselId, this.voyage.id, this.selectedLoadableStudy.id, this.portRotationId).toPromise();
    if (loadableQuantityResult.responseStatus.status === '200') {
      this.caseNo = loadableQuantityResult.caseNo;
      this.selectedZone = loadableQuantityResult.selectedZone;
      this.loadableQuantity = loadableQuantityResult.loadableQuantity;
      this.lastUpdatedDateAndTime = this.timeZoneTransformationService.formatDateTime(this.loadableQuantity.lastUpdatedTime, { utcFormat: true });
      this.loadableQuantityId = this.loadableQuantity.loadableQuantityId;
      this.buttonLabel = this.loadableQuantityId ? 'LOADABLE_QUANTITY_UPDATE' : 'LOADABLE_QUANTITY_SAVE';

      this.loadableQuantityForm = this.fb.group({
        portName: [this.selectedPort, Validators.required],
        arrivalMaxDraft: ['', [numberValidator(2, 2)]],
        dwt: [''],
        tpc: ['', [numberValidator(1, 3)]],
        estimateSag: ['', [Validators.required, numberValidator(2, 2), , Validators.min(0)]],
        safCorrection: ['', [Validators.required, numberValidator(5, 7), Validators.min(0)]],
        foOnboard: [{ value: '', disabled: true }, [Validators.required, numberValidator(2, 7), Validators.min(0)]],
        doOnboard: [{ value: '', disabled: true }, [Validators.required, numberValidator(2, 7), Validators.min(0)]],
        freshWaterOnboard: [{ value: '', disabled: true }, [Validators.required, numberValidator(2, 7), Validators.min(0)]],
        ballast: ['', [Validators.required, numberValidator(2, 7), Validators.min(0)]],
        constant: ['', [Validators.required, numberValidator(2)]],
        others: ['', [Validators.required, numberValidator(2, 7), Validators.min(0)]],
        subTotal: ['', numberValidator(2, 7)],
        totalQuantity: ['', numberValidator(2, 7)],
        estSeaDensity: ['', [Validators.required, numberValidator(4, 1), Validators.min(0)]],
        sgCorrection: ['', [Validators.required, numberValidator(5, 7)]],
        displacement: [''],
        lwt: ['']
      });

      if (this.caseNo === 1) {
        this.loadableQuantityForm.addControl('distanceInSummerzone', this.fb.control('', [Validators.required, numberValidator(2, 7), Validators.min(0)]));
        this.loadableQuantityForm.addControl('speedInSz', this.fb.control('', [Validators.required, numberValidator(2, 7), Validators.min(0.01)]));
        this.loadableQuantityForm.addControl('runningHours', this.fb.control('', [Validators.required, numberValidator(2, 7), Validators.min(0)]));
        this.loadableQuantityForm.addControl('runningDays', this.fb.control('', [Validators.required, numberValidator(2, 7), Validators.min(0)]));
        this.loadableQuantityForm.addControl('foConday', this.fb.control('', [Validators.required, numberValidator(2, 7), Validators.min(0)]));
        this.loadableQuantityForm.addControl('foConsInSz', this.fb.control('', [Validators.required, numberValidator(5, 7), Validators.min(0)]));
      }

      this.getLoadableQuantityData();

    }
    this.ngxSpinnerService.hide();

  }

  /**
   * Populate loadable quantity data
   */
  getLoadableQuantityData() {

    if (this.loadableQuantity.tpc === '') {
      this.isNoTpc = true;
    } if (this.loadableQuantity.dwt === '') {
      this.isNoDwt = true;
    } if (this.loadableQuantity.draftRestriction === '') {
      this.isNoArrivalMaxDraft = { required: true };
    } else {
      this.isNoArrivalMaxDraft = null;
    }
    if (this.loadableQuantity.displacmentDraftRestriction === '') {
      this.isNodisplacement = true;
    }
    if (this.loadableQuantity.vesselLightWeight === '') {
      this.isNoLwt = true;
    }
    if (this.loadableQuantity.constant === '') {
      this.isNoConstant = true;
    }

    this.loadableQuantityForm.controls.portName.setValue(this.selectedPort);
    this.loadableQuantityForm.controls.arrivalMaxDraft.setValue(this.loadableQuantity.draftRestriction);
    this.loadableQuantityForm.controls.dwt.setValue(this.loadableQuantity.dwt);
    this.loadableQuantityForm.controls.tpc.setValue(this.loadableQuantity.tpc);
    this.loadableQuantityForm.controls.estimateSag.setValue(this.loadableQuantity.estSagging);
    this.loadableQuantityForm.controls.safCorrection.setValue(this.loadableQuantity.saggingDeduction);
    this.loadableQuantityForm.controls.foOnboard.setValue(Number(this.loadableQuantity.estFOOnBoard));
    this.loadableQuantityForm.controls.doOnboard.setValue(Number(this.loadableQuantity.estDOOnBoard));

    this.loadableQuantityForm.controls.freshWaterOnboard.setValue(Number(this.loadableQuantity.estFreshWaterOnBoard));
    this.loadableQuantityForm.controls.ballast.setValue(this.loadableQuantity.ballast);
    this.loadableQuantityForm.controls.constant.setValue(this.loadableQuantity.constant);
    this.loadableQuantityForm.controls.others.setValue(this.loadableQuantity.otherIfAny === '' ? 0 : this.loadableQuantity.otherIfAny);
    this.loadableQuantityForm.controls.subTotal.setValue(this.loadableQuantity.subTotal);
    this.loadableQuantityForm.controls.totalQuantity.setValue(this.loadableQuantity.totalQuantity);
    this.loadableQuantityForm.controls.estSeaDensity.setValue(this.loadableQuantity.estSeaDensity);
    this.loadableQuantityForm.controls.sgCorrection.setValue(this.loadableQuantity.sgCorrection);
    this.loadableQuantityForm.controls.displacement.setValue(this.loadableQuantity.displacmentDraftRestriction);
    this.loadableQuantityForm.controls.lwt.setValue(this.loadableQuantity.vesselLightWeight);
    this.getSgCorrectionOnChange();
    if (this.caseNo === 1) {
      this.loadableQuantityForm.controls.distanceInSummerzone.setValue(this.loadableQuantity.distanceFromLastPort);
      this.loadableQuantityForm.controls.speedInSz.setValue(this.loadableQuantity.vesselAverageSpeed);
      this.loadableQuantityForm.controls.runningHours.setValue(this.loadableQuantity.runningHours);
      this.loadableQuantityForm.controls.runningDays.setValue(this.loadableQuantity.runningDays);
      this.loadableQuantityForm.controls.foConday.setValue(this.loadableQuantity.foConsumptionPerDay?.toString());
      this.loadableQuantityForm.controls.foConsInSz.setValue(this.loadableQuantity.foConInSZ);

      this.getRunningHoursOnLoad();
      this.getRunningDaysOnLoad();
      this.getDWT();
      this.getSubTotal();
    }
    else {
       this.getDWT();
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
      if (this.caseNo === 1 && !this.isNoTpc && !this.isNoArrivalMaxDraft && !this.isNoDwt && !this.isNoConstant) {
        this.loadableQuantity = {
          portRotationId: this.portRotationId,
          loadableQuantityId: this.loadableQuantityId,
          portId: this.loadableQuantityForm.controls.portName.value.portId,
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
      else if (this.caseNo === 2 && !this.isNoTpc && !this.isNoArrivalMaxDraft && !this.isNoDwt && !this.isNoConstant) {
        this.loadableQuantity = {
          portRotationId: this.portRotationId,
          loadableQuantityId: this.loadableQuantityId,
          portId: this.loadableQuantityForm.controls.portName.value.portId,
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
          constant: this.loadableQuantityForm.controls.constant.value,
          otherIfAny: this.loadableQuantityForm.controls.others.value,
          totalQuantity: this.loadableQuantityForm.controls.totalQuantity.value
        }
      }
      else if (!this.isNoTpc && !this.isNoArrivalMaxDraft && !this.isNoDwt && !this.isNoLwt && !this.isNodisplacement && !this.isNoConstant) {
        this.loadableQuantity = {
          portRotationId: this.portRotationId,
          loadableQuantityId: this.loadableQuantityId,
          portId: this.loadableQuantityForm.controls.portName.value.portId,
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
          constant: this.loadableQuantityForm.controls.constant.value,
          otherIfAny: this.loadableQuantityForm.controls.others.value,
          totalQuantity: this.loadableQuantityForm.controls.totalQuantity.value,
        }
      }
      this.loadableQuantityId ? this.loadableQuantity['loadableStudyId'] = this.selectedLoadableStudy.id : null;
      const translationKeys = await this.translateService.get(['LOADABLE_QUANTITY_SUCCESS', 'LOADABLE_QUANTITY_SAVED_SUCCESSFULLY', 'LOADABLE_QUANTITY_UPDATE_SUCCESS', 'LOADABLE_QUANTITY_UPDATE_SUCCESSFULLY', 'LOADABLE_QUANTITY_SAVE_ERROR', 'LOADABLE_QUANTITY_SAVE_STATUS_ERROR']).toPromise();
      try {
        const result = await this.loadableQuantityApiService.saveLoadableQuantity(this.vesselId, this.voyage.id, this.selectedLoadableStudy.id, this.loadableQuantity).toPromise();
        this.newLoadableQuantity.emit(this.loadableQuantity.totalQuantity);
        if (result.responseStatus.status === "200") {
          if (this.loadableQuantityId) {
            this.messageService.add({ severity: 'success', summary: translationKeys['LOADABLE_QUANTITY_UPDATE_SUCCESS'], detail: translationKeys['LOADABLE_QUANTITY_UPDATE_SUCCESSFULLY'] });
          } else {
            this.messageService.add({ severity: 'success', summary: translationKeys['LOADABLE_QUANTITY_SUCCESS'], detail: translationKeys['LOADABLE_QUANTITY_SAVED_SUCCESSFULLY'] });
          }
        }
      } catch (errorResponse) {
        if (errorResponse?.error?.errorCode === 'ERR-RICO-110') {
          this.messageService.add({ severity: 'error', summary: translationKeys['LOADABLE_QUANTITY_SAVE_ERROR'], detail: translationKeys['LOADABLE_QUANTITY_SAVE_STATUS_ERROR'], life: 10000 });
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
    this.loadableStudyDetailsTransformationService.setLoadLineChange(false);
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
    this.loadableQuantityForm.get('speedInSz').valid && this.loadableQuantityForm.get('distanceInSummerzone').valid ?
      (this.loadableQuantityForm.controls['runningHours'].setValue(Number(this.loadableQuantityForm.get('distanceInSummerzone').value) / Number(this.loadableQuantityForm.get('speedInSz').value)),
        this.getRunningDays()) : (this.loadableQuantityForm.controls['runningHours'].setValue(0), this.loadableQuantityForm.controls['runningDays'].setValue(0));
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
      const data: ISubTotal = {
        dwt: this.loadableQuantityForm.get('dwt').value,
        sagCorrection: this.loadableQuantityForm.get('safCorrection').value,
        sgCorrection: this.loadableQuantityForm.get('sgCorrection').value,
        foOnboard: this.loadableQuantityForm.get('foOnboard').value,
        doOnboard: this.loadableQuantityForm.get('doOnboard').value,
        freshWaterOnboard: this.loadableQuantityForm.get('freshWaterOnboard').value,
        ballast: this.loadableQuantityForm.get('ballast').value,
        constant: this.loadableQuantityForm.get('constant').value,
        others: this.loadableQuantityForm.get('others').value
      }
      subTotal = Number(this.loadableStudyDetailsTransformationService.getSubTotal(data));
      this.loadableQuantityForm.controls['subTotal'].setValue(subTotal);
      this.getTotalLoadableQuantity();
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
      const total = Number(this.loadableQuantityForm.get('subTotal').value) + Number(this.loadableQuantityForm.get('foConsInSz').value);
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
    if (type === 'sgCorrection' && !this.loadableQuantityForm.controls[type].value) {
      const sgCorrectionValue = (Number(this.loadableQuantityForm.get('estSeaDensity').value) - 1.025) / 1.025 * Number(this.loadableQuantityForm.get('displacement').value);
      this.loadableQuantityForm.controls.sgCorrection.setValue(sgCorrectionValue);
    }
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
    return this.isEditable && formControl.invalid && (formControl.dirty || formControl.touched) ? formControl.errors : null;
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

  /**
   * Clear filter data
   */
  clearFilter(dropdown: Dropdown) {
    dropdown.resetFilter();
  }

  /**
   * method for update loadable quantity on port change
   */
  onPortChange(event) {
    this.selectedPort = event.value;
    this.portRotationId = this.selectedPort?.id;
    this.getLoadableQuantity();
  }
}

import { Component, EventEmitter, Input, OnDestroy, OnInit, Output, ViewChild } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { QUANTITY_UNIT } from '../../../../shared/models/common.model';
import { AppConfigurationService } from '../../../../shared/services/app-configuration/app-configuration.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { ICargo, OPERATIONS } from '../../../core/models/common.model';
import { IStageOffset, IStageDuration, IDischargingInformation, IDischargeOperationListData, IDischargingInformationSaveResponse, ILoadingDischargingDetails, ICOWDetails, IDischargingInformationPostData, IPostDischargeStageTime, ILoadedCargo, ILoadingDischargingDelays } from '../../models/loading-discharging.model';
import { LoadingDischargingInformationApiService } from '../../services/loading-discharging-information-api.service';
import { LoadingDischargingTransformationService } from '../../services/loading-discharging-transformation.service';
import { RulesService } from '../../services/rules/rules.service';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { LoadingDischargingManageSequenceComponent } from '../../loading-discharging-manage-sequence/loading-discharging-manage-sequence.component';
import { LoadingDischargingBerthComponent } from '../../loading-discharging-berth/loading-discharging-berth.component';
import { LoadingDischargingCargoMachineryComponent } from '../../loading-discharging-cargo-machinery/loading-discharging-cargo-machinery.component';
import { LoadingDischargingDetailsComponent } from '../../loading-discharging-details/loading-discharging-details.component';
import { DischargingRatesComponent } from '../../discharging-rates/discharging-rates.component';
import { IPermission } from '../../../../shared/models/user-profile.model';
import * as moment from 'moment';

/**
 * Component class for discharge information component
 *
 * @export
 * @class DischargingInformationComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-discharging-information',
  templateUrl: './discharging-information.component.html',
  styleUrls: ['./discharging-information.component.scss']
})
export class DischargingInformationComponent implements OnInit, OnDestroy {
  @ViewChild(LoadingDischargingManageSequenceComponent) manageSequenceComponent: LoadingDischargingManageSequenceComponent;
  @ViewChild(LoadingDischargingBerthComponent) dischargeBerthComponent: LoadingDischargingBerthComponent;
  @ViewChild(LoadingDischargingCargoMachineryComponent) machineryRefComponent: LoadingDischargingCargoMachineryComponent;
  @ViewChild(LoadingDischargingDetailsComponent) dischargeDetailsComponent: LoadingDischargingDetailsComponent;
  @ViewChild(DischargingRatesComponent) dischargeRatesComponent: DischargingRatesComponent;

  @Input() voyageId: number;
  @Input() vesselId: number;
  @Input() permission: IPermission;
  @Input() get cargos(): ICargo[] {
    return this._cargos;
  }

  set cargos(cargos: ICargo[]) {
    this._cargos = cargos;
  }

  @Input() get portRotationId(): number {
    return this._portRotationId;
  }
  set portRotationId(portRotationId: number) {
    this._portRotationId = portRotationId;
    this.dischargingInformationForm = null;
    this.getDischargingInformation()
  }

  @Output() dischargingInformationId: EventEmitter<any> = new EventEmitter();

  dischargingInformationData?: IDischargingInformation;
  dischargingInformationPostData = <IDischargingInformationPostData>{};
  dischargeInfoId: number;
  stageDuration: IStageDuration;
  stageOffset: IStageOffset;
  prevQuantitySelectedUnit: QUANTITY_UNIT;
  hasUnSavedData = false;
  currentQuantitySelectedUnit = <QUANTITY_UNIT>localStorage.getItem('unit');
  readonly OPERATIONS = OPERATIONS;
  dischargingInformationForm: FormGroup;
  listData: IDischargeOperationListData = {
    protestedOptions: [{ name: 'Yes', id: 1 }, { name: 'No', id: 2 }],
    cowOptions: [{ name: 'Auto', id: 1 }, { name: 'Manual', id: 2 }],
    cowPercentages: [
      { value: 25, name: '25%' },
      { value: 50, name: '50%' },
      { value: 75, name: '75%' },
      { value: 100, name: '100%' },
    ]
  };

  private _portRotationId: number;
  private _cargos: ICargo[];
  private ngUnsubscribe: Subject<any> = new Subject();

  constructor(private loadingDischargingInformationApiService: LoadingDischargingInformationApiService,
    private translateService: TranslateService,
    private messageService: MessageService,
    private loadingDischargingTransformationService: LoadingDischargingTransformationService,
    private rulesService: RulesService,
    private ngxSpinnerService: NgxSpinnerService,
    private fb: FormBuilder) { }


  ngOnInit() {
    this.initSubscriptions();
  }

  ngOnDestroy() {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  /**
  * Initialization for all subscriptions
  *
  * @private
  * @memberof DischargingInformationComponent
  */
  private async initSubscriptions() {
    this.loadingDischargingTransformationService.unitChange$.pipe(takeUntil(this.ngUnsubscribe)).subscribe((res) => {
      this.prevQuantitySelectedUnit = this.currentQuantitySelectedUnit ?? AppConfigurationService.settings.baseUnit
      this.currentQuantitySelectedUnit = <QUANTITY_UNIT>localStorage.getItem('unit');
    })
  }

  /**
  * Method to get discharging information
  *
  * @memberof DischargingInformationComponent
  */
  async getDischargingInformation() {
    this.ngxSpinnerService.show();
    const translationKeys = await this.translateService.get(['DISCHARGING_INFORMATION_NO_ACTIVE_VOYAGE', 'DISCHARGING_INFORMATION_NO_ACTIVE_VOYAGE_MESSAGE']).toPromise();
    try {
      const dischargingInformationResponse = await this.loadingDischargingInformationApiService.getDischargingInformation(this.vesselId, this.voyageId, this.portRotationId).toPromise();
      this.dischargingInformationData = this.loadingDischargingTransformationService.transformDischargingInformation(dischargingInformationResponse, this.listData);
      this.initFormArray(this.dischargingInformationData);
      // this.rulesService.dischargeInfoId.next(this.dischargingInformationData.dischargeInfoId);
      await this.updateGetData();
    }
    catch (error) {
      if (error.error.errorCode === 'ERR-RICO-522') {
        this.messageService.add({ severity: 'error', summary: translationKeys['DISCHARGING_INFORMATION_NO_ACTIVE_VOYAGE'], detail: translationKeys['DISCHARGING_INFORMATION_NO_ACTIVE_VOYAGE_MESSAGE'] });
      }
    }
    this.ngxSpinnerService.hide();
  }

  /**
   * Method for initialising form
   *
   * @param {IDischargingInformation} dischargingInformationData
   * @memberof DischargingInformationComponent
   */
  initFormArray(dischargingInformationData: IDischargingInformation) {
    this.dischargingInformationForm = this.fb.group({
      stageDetails: this.fb.group({
        trackStartEndStage: this.fb.control(dischargingInformationData?.dischargeStages?.trackStartEndStage),
        trackGradeSwitch: this.fb.control(dischargingInformationData?.dischargeStages?.trackGradeSwitch),
        stageOffset: this.fb.control(dischargingInformationData?.dischargeStages?.stageOffset),
        stageDuration: this.fb.control(dischargingInformationData?.dischargeStages?.stageDuration),
      }),
      dischargeSlopTanksFirst: this.fb.control(dischargingInformationData?.dischargeSlopTanksFirst),
      dischargeCommingledCargoSeparately: this.fb.control(dischargingInformationData?.dischargeCommingledCargoSeparately),
      machinery: this.fb.group({}),
      cowDetails: this.fb.group({}),
      postDischargeStageTime: this.fb.group({})
    });
  }

  /**
   * Method to update Get data
   *
   * @memberof DischargingInformationComponent
   */
  async updateGetData() {
    if (this.dischargingInformationData) {
      this.dischargingInformationPostData.dischargeInfoId = this.dischargingInformationData?.dischargeInfoId;
      this.dischargingInformationPostData.synopticTableId = this.dischargingInformationData?.synopticalTableId;
    }
    this.loadingDischargingTransformationService.setDischargingInformationValidity(this.dischargingInformationData?.isDischargeInfoComplete)
    this.dischargeInfoId = this.dischargingInformationData?.dischargeInfoId;
    this.dischargingInformationId.emit(this.dischargeInfoId);

  }

  /**
  * Method for event machinary data update
  *
  * @memberof DischargingInformationComponent
  */
  onUpdateMachineryInUses(event) {
    this.dischargingInformationPostData.dischargingMachineries = event;
    this.hasUnSavedData = true;
  }

  /**
  * Method for event berth data update
  *
  * @memberof DischargingInformationComponent
  */
  onBerthChange(event) {
    this.dischargingInformationPostData.dischargingBerths = event;
    this.hasUnSavedData = true;
  }

  /**
  * Method for event stage offset value change
  *
  * @memberof DischargingInformationComponent
  */
  onStageOffsetValChange(event) {
    this.dischargingInformationData.dischargeStages.stageOffset = event?.value;
    this.onUpdateDischargingStages();
  }

  /**
  * Method for event stage duration value change
  *
  * @memberof DischargingInformationComponent
  */
  onStageDurationValChange(event) {
    this.dischargingInformationData.dischargeStages.stageDuration = event?.value;
    this.onUpdateDischargingStages();
  }

  /**
  * Method for event discharging stages data update
  *
  * @memberof DischargingInformationComponent
  */
  onUpdateDischargingStages() {
    this.dischargingInformationPostData.dischargeStages = this.dischargingInformationData?.dischargeStages;
    this.hasUnSavedData = true;
  }

  /**
  * Method for event discharging delay data update
  *
  * @memberof DischargingInformationComponent
  */
  onUpdateDischargingDelays(event: ILoadingDischargingDelays[]) {
    this.dischargingInformationPostData.dischargingDelays = event;
    this.hasUnSavedData = true;
  }

  /**
   * Method for event discharging details data update
   *
   * @memberof DischargingInformationComponent
   */
  onUpdateDischargingDetails(event: ILoadingDischargingDetails) {
    this.dischargingInformationPostData.dischargeDetails = event;
    this.dischargingInformationPostData.dischargeDetails.trimAllowed.finalTrim = this.dischargingInformationPostData.dischargeDetails.trimAllowed.topOffTrim;
    this.hasUnSavedData = true;
  }

  /**
  * Method for event track value change
  *
  * @memberof DischargingInformationComponent
  */
  onTrackStageChange() {
    this.dischargingInformationData.dischargeStages.trackStartEndStage = this.dischargingInformationForm?.value?.stageDetails?.trackStartEndStage;
    this.dischargingInformationData.dischargeStages.trackGradeSwitch = this.dischargingInformationForm?.value?.stageDetails?.trackGradeSwitch;
    this.onUpdateDischargingStages();
  }

  /**
  * Method for event discharging rate update
  *
  * @memberof DischargingInformationComponent
  */
  onDischargingRateChange(event) {
    this.dischargingInformationPostData.dischargeRates = event;
    this.hasUnSavedData = true;
  }

  /**
   * Handler for on change of slop tank first checkbox
   *
   * @param {*} event
   * @memberof DischargingInformationComponent
   */
  onSlopTanksFirstCheckboxChange(event) {
    this.dischargingInformationPostData.cargoToBeDischarged = { ...this.dischargingInformationPostData?.cargoToBeDischarged, dischargeSlopTanksFirst: event.target.checked };
    this.hasUnSavedData = true;
  }

  /**
   * Handler for on change of dischrage commingled cargo seperately checkbox
   *
   * @param {*} event
   * @memberof DischargingInformationComponent
   */
  onCommingledSeperatelyCheckboxChange(event) {
    this.dischargingInformationPostData.cargoToBeDischarged = { ...this.dischargingInformationPostData?.cargoToBeDischarged, dischargeCommingledCargoSeparately: event.target.checked };
    this.hasUnSavedData = true;
  }

  /**
   * Handler for on change of cargo to be discharged grid
   *
   * @param {*} event
   * @memberof DischargingInformationComponent
   */
  onCargoToBeDischargedChange(event: ILoadedCargo[]) {
    this.dischargingInformationPostData.cargoToBeDischarged = { ...this.dischargingInformationPostData?.cargoToBeDischarged, dischargeQuantityCargoDetails: this.loadingDischargingTransformationService.getCargoToBeDischargedAsValue(event, this.listData, this.currentQuantitySelectedUnit) };
    this.hasUnSavedData = true;
  }

  /**
   * Handler for on change of cow plan details
   *
   * @param {*} event
   * @memberof DischargingInformationComponent
   */
  onCowPlanUpdate(event: ICOWDetails) {
    this.dischargingInformationPostData.cowPlan = {
      cowOption: event?.cowOption?.id,
      cowPercentage: event?.cowPercentage?.value,
      cowStart: moment.duration(event?.cowStart).asMinutes().toString(),
      cowEnd: moment.duration(event?.cowEnd).asMinutes().toString(),
      cowDuration: moment.duration(event?.cowDuration).asMinutes().toString(),
      needFreshCrudeStorage: event?.needFreshCrudeStorage,
      needFlushingOil: event?.needFlushingOil,
      washTanksWithDifferentCargo: true,
      topCow: event?.topCOWTanks?.map(tank => tank.id),
      bottomCow: event?.bottomCOWTanks?.map(tank => tank.id),
      allCow: event?.allCOWTanks?.map(tank => tank.id),
      cargoCow: event?.tanksWashingWithDifferentCargo?.filter(item => item?.washingCargo?.cargoNominationId && item?.tanks?.length).map(item => ({
        cargoId: item?.cargo?.id, cargoNominationId: item?.cargo?.cargoNominationId,
        tankIds: item?.tanks?.map(tank => tank.id),
        washingCargoId: item?.washingCargo?.id,
        washingCargoNominationId: item?.washingCargo?.cargoNominationId
      })),
      cowTrimMax: event?.cowTrimMax,
      cowTrimMin: event?.cowTrimMin
    };
    this.hasUnSavedData = true;
  }

  /**
     * Handler for on change of post discharge ste time details
     *
     * @param {*} event
     * @memberof DischargingInformationComponent
     */
  onPostDischargeTimeUpdate(event: IPostDischargeStageTime) {
    this.dischargingInformationPostData.postDischargeStageTime = {
      dryCheckTime: moment.duration(event?.dryCheckTime).asMinutes().toString(),
      slopDischargingTime: moment.duration(event?.slopDischargingTime).asMinutes().toString(),
      finalStrippingTime: moment.duration(event?.finalStrippingTime).asMinutes().toString(),
      freshOilWashingTime: moment.duration(event?.freshOilWashingTime).asMinutes().toString(),
    }
    this.hasUnSavedData = true;
  }

  /**
  * Method for event to save discharging information data
  *
  * @memberof DischargingInformationComponent
  */
  async saveDischargingInformationData() {
    this.dischargeDetailsComponent.loadingDischargingDetailsForm?.markAllAsTouched();
    this.dischargeDetailsComponent.loadingDischargingDetailsForm?.updateValueAndValidity();

    this.dischargeBerthComponent.berthDetailsForm?.markAllAsTouched();
    this.dischargeBerthComponent.berthDetailsForm?.updateValueAndValidity();

    this.dischargeRatesComponent.dischargingRatesFormGroup?.markAllAsTouched();
    this.dischargeRatesComponent.dischargingRatesFormGroup?.updateValueAndValidity();

    this.dischargingInformationForm.markAllAsTouched();
    this.dischargingInformationForm?.updateValueAndValidity();

    const isValid = await this.checkIfValid();
    this.dischargingInformationData.isDischargeInfoComplete = isValid && this.dischargingInformationForm.valid && this.dischargingInformationForm.valid && this.dischargeDetailsComponent.loadingDischargingDetailsForm?.valid && !this.dischargeBerthComponent.berthDetailsForm?.invalid && this.dischargeRatesComponent.dischargingRatesFormGroup?.valid;
    this.loadingDischargingTransformationService.setDischargingInformationValidity(this.dischargingInformationData?.isDischargeInfoComplete)

    const translationKeys = await this.translateService.get(['DISCHARGING_INFORMATION_SAVE_ERROR', 'DISCHARGING_INFORMATION_SAVE_NO_DATA_ERROR', 'DISCHARGING_INFORMATION_SAVE_SUCCESS', 'DISCHARGING_INFORMATION_SAVED_SUCCESSFULLY', 'DISCHARGING_INFORMATION_INVALID_DATA']).toPromise();

    if (!this.dischargingInformationData?.isDischargeInfoComplete) {
      this.messageService.add({ severity: 'error', summary: translationKeys['DISCHARGING_INFORMATION_SAVE_ERROR'], detail: translationKeys['DISCHARGING_INFORMATION_INVALID_DATA'] });
      if (document.querySelector('.error-icon')) {
        document.querySelector('.error-icon').scrollIntoView({ behavior: "smooth" });
      }
    }

    if (this.dischargingInformationData.isDischargeInfoComplete) {
      if (this.hasUnSavedData) {
        this.ngxSpinnerService.show();
        this.dischargingInformationPostData.isDischargeInfoComplete = true;
        const result: IDischargingInformationSaveResponse = await this.loadingDischargingInformationApiService.saveDischargingInformation(this.vesselId, this.voyageId, this.dischargingInformationPostData).toPromise();
        if (result?.responseStatus?.status === '200') {
          this.hasUnSavedData = false;
          this.messageService.add({ severity: 'success', summary: translationKeys['DISCHARGING_INFORMATION_SAVE_SUCCESS'], detail: translationKeys['DISCHARGING_INFORMATION_SAVED_SUCCESSFULLY'] });
        }
        this.ngxSpinnerService.hide();
      } else {
        this.messageService.add({ severity: 'error', summary: translationKeys['DISCHARGING_INFORMATION_SAVE_ERROR'], detail: translationKeys['DISCHARGING_INFORMATION_SAVE_NO_DATA_ERROR'] });
      }
    }
  }

  /**
   * Method to check if commingle discharge selection is valid
   *
   * @return {*}  {boolean}
   * @memberof DischargingInformationComponent
   */
  async checkIfValid(): Promise<boolean> {
    const translationKeys = this.translateService.instant(['DISCHARGING_INFO_ERROR', 'DISCHARGING_INFO_MIN_CARGO_SELECTED', 'DISCHARGING_COW_TANK_NOT_SELECTED', 'DISCHARGING_STAGE_DURATION_ERROR']);

    const isMachineryValid = await this.machineryRefComponent.isMachineryValid(true);
    if (!isMachineryValid) {
      return false;
    }

    const commingledCargos = this.dischargingInformationForm?.value?.cargoTobeLoadedDischarged?.dataTable?.filter(cargo => cargo?.isCommingledDischarge === true);
    if (commingledCargos?.length && commingledCargos?.length < 2) {
      this.messageService.add({ severity: 'error', summary: translationKeys['DISCHARGING_INFO_ERROR'], detail: translationKeys['DISCHARGING_INFO_MIN_CARGO_SELECTED'] });
      return false;
    }

    const validSequence = await this.manageSequenceComponent.checkCargoCount(true);
    if (!validSequence) {
      return false;
    }

    if (this.dischargingInformationData?.cowDetails?.totalDuration && this.dischargingInformationForm?.value?.stageDetails?.stageDuration?.duration * 60 > this.loadingDischargingTransformationService.convertTimeStringToMinutes(this.dischargingInformationData?.cowDetails?.totalDuration)) {
      this.messageService.add({ severity: 'error', summary: translationKeys['DISCHARGING_INFO_ERROR'], detail: translationKeys['DISCHARGING_STAGE_DURATION_ERROR'] });
      return false
    }

    if (this.dischargingInformationForm?.value?.cowDetails?.cowOption?.id === 2 && !this.dischargingInformationForm?.value?.cowDetails?.topCOWTanks?.length && !this.dischargingInformationForm?.value?.cowDetails?.bottomCOWTanks?.length && !this.dischargingInformationForm?.value?.cowDetails?.allCOWTanks?.length) {
      this.messageService.add({ severity: 'error', summary: translationKeys['DISCHARGING_INFO_ERROR'], detail: translationKeys['DISCHARGING_COW_TANK_NOT_SELECTED'] });
      return false
    }

    return true;
  }

}

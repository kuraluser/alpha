import { Component, EventEmitter, Input, OnDestroy, OnInit, Output, ViewChild } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { QUANTITY_UNIT, ValueObject } from '../../../../shared/models/common.model';
import { AppConfigurationService } from '../../../../shared/services/app-configuration/app-configuration.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { ICargo, OPERATIONS } from '../../../core/models/common.model';
import { IStageOffset, IStageDuration, IDischargingInformation, IDischargeOperationListData } from '../../models/loading-discharging.model';
import { LoadingDischargingInformationApiService } from '../../services/loading-discharging-information-api.service';
import { LoadingDischargingTransformationService } from '../../services/loading-discharging-transformation.service';
import { RulesService } from '../../services/rules/rules.service';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { LoadingDischargingManageSequenceComponent } from '../../loading-discharging-manage-sequence/loading-discharging-manage-sequence.component';
import { durationValidator } from '../../directives/validator/duration-validator.directive';

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

  @Input() voyageId: number;
  @Input() vesselId: number;
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
    this.getDischargingInformation()
  }

  @Output() dischargingInformationId: EventEmitter<any> = new EventEmitter();

  dischargingInformationData?: IDischargingInformation;
  dischargingInformationPostData = <IDischargingInformation>{};
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
    const translationKeys = await this.translateService.get(['LOADING_INFORMATION_NO_ACTIVE_VOYAGE', 'LOADING_INFORMATION_NO_ACTIVE_VOYAGE_MESSAGE']).toPromise();
    try {
      const dischargingInformationResponse = await this.loadingDischargingInformationApiService.getDischargingInformation(this.vesselId, this.voyageId, this.portRotationId).toPromise();
      this.dischargingInformationData = this.loadingDischargingTransformationService.transformDischargingInformation(dischargingInformationResponse, this.listData);
      this.initFormArray(this.dischargingInformationData);
      // this.rulesService.dischargeInfoId.next(this.dischargingInformationData.dischargeInfoId);
      await this.updateGetData();
    }
    catch (error) {
      if (error.error.errorCode === 'ERR-RICO-522') {
        this.messageService.add({ severity: 'error', summary: translationKeys['LOADING_INFORMATION_NO_ACTIVE_VOYAGE'], detail: translationKeys['LOADING_INFORMATION_NO_ACTIVE_VOYAGE_MESSAGE'] });
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
      dischargeCommingledCargoSeperately: this.fb.control(dischargingInformationData?.dischargeCommingledCargoSeperately),
      cargoTobeLoadedDischarged: this.fb.group({
        dataTable: this.fb.array([])
      }),
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
      this.dischargingInformationPostData.synopticalTableId = this.dischargingInformationData?.synopticalTableId;
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
    this.dischargingInformationPostData.dischargeStages.stageOffset = this.dischargingInformationData?.dischargeStages?.stageOffset;
    this.dischargingInformationPostData.dischargeStages.stageDuration = this.dischargingInformationData?.dischargeStages?.stageDuration;
    this.dischargingInformationPostData.dischargeStages.trackStartEndStage = this.dischargingInformationData?.dischargeStages?.trackStartEndStage;
    this.dischargingInformationPostData.dischargeStages.trackGradeSwitch = this.dischargingInformationData?.dischargeStages?.trackGradeSwitch;
    this.hasUnSavedData = true;
  }

  /**
  * Method for event discharging delay data update
  *
  * @memberof DischargingInformationComponent
  */
  onUpdateDischargingDelays(event) {
    this.dischargingInformationPostData.dischargingDelays = event;
    this.hasUnSavedData = true;
  }

  /**
   * Method for event discharging details data update
   *
   * @memberof DischargingInformationComponent
   */
  onUpdateLoadingDetails(event) {
    this.dischargingInformationPostData.dischargeDetails = event;
    this.hasUnSavedData = true;
  }

  /**
  * Method for event track value change
  *
  * @memberof DischargingInformationComponent
  */
  onTrackStageChange() {
    this.dischargingInformationPostData.dischargeStages.trackStartEndStage = this.dischargingInformationData?.dischargeStages?.trackStartEndStage;
    this.dischargingInformationPostData.dischargeStages.trackGradeSwitch = this.dischargingInformationData?.dischargeStages?.trackGradeSwitch;
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
    this.dischargingInformationPostData.dischargeSlopTanksFirst = event.target.checked;
    this.hasUnSavedData = true;
  }

  /**
   * Handler for on change of dischrage commingled cargo seperately checkbox
   *
   * @param {*} event
   * @memberof DischargingInformationComponent
   */
  onCommingledSeperatelyCheckboxChange(event) {
    this.dischargingInformationPostData.dischargeCommingledCargoSeperately = event.target.checked;
    this.hasUnSavedData = true;
  }

  /**
  * Method for event to save discharging information data
  *
  * @memberof DischargingInformationComponent
  */
  async saveDischargingInformationData() {
    if (this.checkIfValid()) {

    }
    const validSequence = await this.manageSequenceComponent.checkCargoCount();
    if (!validSequence) {
      return false;
    }
    //TODO:Need to integrate with discharge info save
    /* const translationKeys = await this.translateService.get(['LOADING_INFORMATION_SAVE_ERROR', 'LOADING_INFORMATION_SAVE_NO_DATA_ERROR', 'LOADING_INFORMATION_SAVE_SUCCESS', 'LOADING_INFORMATION_SAVED_SUCCESSFULLY']).toPromise();
    if (this.hasUnSavedData) {
      this.ngxSpinnerService.show();
      const result: IDischargingInformationSaveResponse = await this.loadingDischargingInformationApiService.saveLoadingInformation(this.vesselId, this.voyageId, this.dischargingInformationPostData).toPromise();
      if (result?.responseStatus?.status === '200') {
        this.dischargingInformationData = result?.dischargingInformation;
        await this.updateGetData();
        this.hasUnSavedData = false;
        this.messageService.add({ severity: 'success', summary: translationKeys['LOADING_INFORMATION_SAVE_SUCCESS'], detail: translationKeys['LOADING_INFORMATION_SAVED_SUCCESSFULLY'] });
      }
      this.ngxSpinnerService.hide();
    } else {
      this.messageService.add({ severity: 'error', summary: translationKeys['LOADING_INFORMATION_SAVE_ERROR'], detail: translationKeys['LOADING_INFORMATION_SAVE_NO_DATA_ERROR'] });
    } */
  }

  /**
   * Method to check if commingle discharge selection is valid
   *
   * @return {*}  {boolean}
   * @memberof DischargingInformationComponent
   */
  checkIfValid(): boolean {
    const translationKeys = this.translateService.instant(['DISCHARGING_INFO_MIN_CARGO_SELECTED_ERROR', 'DISCHARGING_INFO_MIN_CARGO_SELECTED', 'DISCHARGING_COW_TANK_NOT_SELECTED', 'DISCHARGING_STAGE_DURATION_ERROR']);
    const commingledCargos = this.dischargingInformationForm?.value?.cargoTobeLoadedDischarged?.dataTable?.filter(cargo => cargo?.isCommingledDischarge === true);
    if (commingledCargos?.length && commingledCargos?.length < 2) {
      this.messageService.add({ severity: 'error', summary: translationKeys['DISCHARGING_INFO_MIN_CARGO_SELECTED_ERROR'], detail: translationKeys['DISCHARGING_INFO_MIN_CARGO_SELECTED'] });
      return false;
    }

    if (this.dischargingInformationForm?.value?.cowDetails?.cowOption?.id === 2 && !this.dischargingInformationForm?.value?.cowDetails?.topCOWTanks?.length && !this.dischargingInformationForm?.value?.cowDetails?.bottomCOWTanks?.length && !this.dischargingInformationForm?.value?.cowDetails?.allCOWTanks?.length) {
      this.messageService.add({ severity: 'error', summary: translationKeys['DISCHARGING_INFO_MIN_CARGO_SELECTED_ERROR'], detail: translationKeys['DISCHARGING_COW_TANK_NOT_SELECTED'] });
      return false
    }

    if (this.dischargingInformationForm?.value?.stageDetails?.stageDuration?.duration * 60 > this.loadingDischargingTransformationService.convertTimeStringToMinutes(this.dischargingInformationData?.cowDetails?.totalDuration)) {
      this.messageService.add({ severity: 'error', summary: translationKeys['DISCHARGING_INFO_MIN_CARGO_SELECTED_ERROR'], detail: translationKeys['DISCHARGING_STAGE_DURATION_ERROR'] });
      return false
    }

    return true;
  }

}

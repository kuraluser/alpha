import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { QUANTITY_UNIT, ValueObject } from '../../../../shared/models/common.model';
import { AppConfigurationService } from '../../../../shared/services/app-configuration/app-configuration.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { ICargo, OPERATIONS } from '../../../core/models/common.model';
import { IStageOffset, IStageDuration, ICargoVesselTankDetails, IDischargingInformation, IDischargeOperationListData } from '../../models/loading-discharging.model';
import { LoadingDischargingInformationApiService } from '../../services/loading-discharging-information-api.service';
import { LoadingDischargingTransformationService } from '../../services/loading-discharging-transformation.service';
import { RulesService } from '../../services/rules/rules.service';
import { FormBuilder, FormGroup } from '@angular/forms';

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
export class DischargingInformationComponent implements OnInit {

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
  dischargingInfoId: number;
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

  constructor(private loadingDischargingInformationApiService: LoadingDischargingInformationApiService,
    private translateService: TranslateService,
    private messageService: MessageService,
    private loadingDischargingTransformationService: LoadingDischargingTransformationService,
    private rulesService: RulesService,
    private ngxSpinnerService: NgxSpinnerService,
    private fb: FormBuilder) { }


  async ngOnInit(): Promise<void> {
    this.initSubscriptions();
  }

  /**
  * Initialization for all subscriptions
  *
  * @private
  * @memberof DischargingInformationComponent
  */
  private async initSubscriptions() {
    this.loadingDischargingTransformationService.unitChange$.subscribe((res) => {
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
      // this.rulesService.dischargingInfoId.next(this.dischargingInformationData.dischargingInfoId);
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
    const cargoToBeDischarged = dischargingInformationData?.cargoVesselTankDetails?.loadableQuantityCargoDetails?.map(cargo => {
      return this.fb.group({
        protested: this.fb.control(cargo.protested.value),
        isCommingled: this.fb.control(cargo?.isCommingled.value),
        slopQuantity: this.fb.control((<ValueObject<number>>cargo?.slopQuantity)?.value),
      })
    });
    const tanksWashingWithDifferentCargo = dischargingInformationData?.cowDetails?.tanksWashingWithDifferentCargo?.map(item => {
      return this.fb.group({
        cargo: this.fb.control(item?.cargo),
        washingCargo: this.fb.control(item?.washingCargo),
        tanks: this.fb.control(item?.tanks)
      })
    });
    this.dischargingInformationForm = this.fb.group({
      stageDetails: this.fb.group({
        trackStartEndStage: this.fb.control(dischargingInformationData?.dischargingStages?.trackStartEndStage),
        trackGradeSwitch: this.fb.control(dischargingInformationData?.dischargingStages?.trackGradeSwitch),
        stageOffset: this.fb.control(dischargingInformationData?.dischargingStages?.stageOffset),
        stageDuration: this.fb.control(dischargingInformationData?.dischargingStages?.stageDuration),
      }),
      dischargeSlopTanksFirst: this.fb.control(dischargingInformationData?.dischargeSlopTanksFirst),
      dischargeCommingledCargoSeperately: this.fb.control(dischargingInformationData?.dischargeCommingledCargoSeperately),
      cargoTobeLoadedDischarged: this.fb.group({
        dataTable: this.fb.array([...cargoToBeDischarged])
      }),
      cowDetails: this.fb.group({
        washTanksWithDifferentCargo: this.fb.control(dischargingInformationData?.cowDetails?.washTanksWithDifferentCargo),
        cowOption: this.fb.control(dischargingInformationData?.cowDetails?.cowOption),
        cowPercentage: this.fb.control(dischargingInformationData?.cowDetails?.cowPercentage),
        topCOWTanks: this.fb.control(dischargingInformationData?.cowDetails?.topCOWTanks),
        bottomCOWTanks: this.fb.control(dischargingInformationData?.cowDetails?.bottomCOWTanks),
        allCOWTanks: this.fb.control(dischargingInformationData?.cowDetails?.allCOWTanks),
        tanksWashingWithDifferentCargo: this.fb.array([...tanksWashingWithDifferentCargo]),
        cowStart: this.fb.control(dischargingInformationData?.cowDetails?.cowStart),
        cowEnd: this.fb.control(dischargingInformationData?.cowDetails?.cowEnd),
        cowDuration: this.fb.control(dischargingInformationData?.cowDetails?.cowDuration),
        cowTrimMin: this.fb.control(dischargingInformationData?.cowDetails?.cowTrimMin),
        cowTrimMax: this.fb.control(dischargingInformationData?.cowDetails?.cowTrimMax),
        needFreshCrudeStorage: this.fb.control(dischargingInformationData?.cowDetails?.needFreshCrudeStorage),
        needFlushingOil: this.fb.control(dischargingInformationData?.cowDetails?.needFlushingOil),
      }),
      postDischargeStageTime: this.fb.group({
        dryCheckTime: this.fb.control(dischargingInformationData?.postDischargeStageTime?.dryCheckTime),
        slopDischargingTime: this.fb.control(dischargingInformationData?.postDischargeStageTime?.dryCheckTime),
        finalStrippingTime: this.fb.control(dischargingInformationData?.postDischargeStageTime?.dryCheckTime),
        freshOilWashingTime: this.fb.control(dischargingInformationData?.postDischargeStageTime?.dryCheckTime),
      })
    })
  }

  /**
   * Method to update Get data
   *
   * @memberof DischargingInformationComponent
   */
  async updateGetData() {
    if (this.dischargingInformationData) {
      this.dischargingInformationPostData.dischargingInfoId = this.dischargingInformationData?.dischargingInfoId;
      this.dischargingInformationPostData.synopticalTableId = this.dischargingInformationData?.synopticalTableId;
    }
    this.loadingDischargingTransformationService.setDischargingInformationValidity(this.dischargingInformationData?.isDischargingInfoComplete)
    this.dischargingInfoId = this.dischargingInformationData?.dischargingInfoId;
    this.dischargingInformationId.emit(this.dischargingInfoId);

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
    this.dischargingInformationData.dischargingStages.stageOffset = event?.value;
    this.onUpdateDischargingStages();
  }

  /**
  * Method for event stage duration value change
  *
  * @memberof DischargingInformationComponent
  */
  onStageDurationValChange(event) {
    this.dischargingInformationData.dischargingStages.stageDuration = event?.value;
    this.onUpdateDischargingStages();
  }

  /**
  * Method for event discharging stages data update
  *
  * @memberof DischargingInformationComponent
  */
  onUpdateDischargingStages() {
    this.dischargingInformationPostData.dischargingStages.stageOffset = this.dischargingInformationData?.dischargingStages?.stageOffset;
    this.dischargingInformationPostData.dischargingStages.stageDuration = this.dischargingInformationData?.dischargingStages?.stageDuration;
    this.dischargingInformationPostData.dischargingStages.trackStartEndStage = this.dischargingInformationData?.dischargingStages?.trackStartEndStage;
    this.dischargingInformationPostData.dischargingStages.trackGradeSwitch = this.dischargingInformationData?.dischargingStages?.trackGradeSwitch;
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
    this.dischargingInformationPostData.dischargingDetails = event;
    this.hasUnSavedData = true;
  }

  /**
  * Method for event track value change
  *
  * @memberof DischargingInformationComponent
  */
  onTrackStageChange() {
    this.dischargingInformationPostData.dischargingStages.trackStartEndStage = this.dischargingInformationData?.dischargingStages?.trackStartEndStage;
    this.dischargingInformationPostData.dischargingStages.trackGradeSwitch = this.dischargingInformationData?.dischargingStages?.trackGradeSwitch;
    this.onUpdateDischargingStages();
  }

  /**
  * Method for event discharging rate update
  *
  * @memberof DischargingInformationComponent
  */
  onDischargingRateChange(event) {
    this.dischargingInformationPostData.dischargingRates = event;
    this.hasUnSavedData = true;
  }

  /**
  * Method for event to save discharging information data
  *
  * @memberof DischargingInformationComponent
  */
  async saveDischargingInformationData() {
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

}

import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { QUANTITY_UNIT, ValueObject } from '../../../../shared/models/common.model';
import { AppConfigurationService } from '../../../../shared/services/app-configuration/app-configuration.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { ICargo, ILoadableQuantityCargo, IProtested, OPERATIONS } from '../../../core/models/common.model';
import { IStageOffset, IStageDuration, ICargoVesselTankDetails, IDischargingInformationResponse, IDischargingInformation, IDischargingInformationSaveResponse } from '../../models/loading-discharging.model';
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

  dischargingInformationData?: IDischargingInformationResponse;
  stageOffsetList: IStageOffset[];
  stageDurationList: IStageDuration[];
  cargoVesselTankDetails: ICargoVesselTankDetails;
  dischargingInformationPostData = <IDischargingInformation>{};
  dischargingInfoId: number;
  trackStartEndStage: boolean;
  trackGradeSwitch: boolean;
  stageDuration: IStageDuration;
  stageOffset: IStageOffset;
  prevQuantitySelectedUnit: QUANTITY_UNIT;
  hasUnSavedData = false;
  currentQuantitySelectedUnit = <QUANTITY_UNIT>localStorage.getItem('unit');
  readonly OPERATIONS = OPERATIONS;
  dischargingInformationForm: FormGroup;
  listData = { protestedOptions: [{ name: 'Yes', id: 1 }, { name: 'No', id: 2 }] };

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
      this.dischargingInformationData = await this.loadingDischargingInformationApiService.getDischargingInformation(this.vesselId, this.voyageId, this.portRotationId).toPromise();
      const loadableQuantityCargoDetails: ILoadableQuantityCargo[] = this.dischargingInformationData?.cargoVesselTankDetails?.loadableQuantityCargoDetails.map(cargo => {
        const _cargo = <ILoadableQuantityCargo>{};
        _cargo.isAdd = true;
        for (const key in cargo) {
          if (Object.prototype.hasOwnProperty.call(cargo, key)) {
            if (key === 'protested') {
              const _protested = cargo.protested ? this.listData?.protestedOptions[0] : this.listData?.protestedOptions[1];
              _cargo.protested = new ValueObject<IProtested>(_protested, true, true, false);
            } else if (key === 'isCommingled') {
              const _isCommingled = cargo.isCommingled ?? false;
              _cargo.isCommingled = new ValueObject<boolean>(_isCommingled, true, true, false);
            } else {
              _cargo[key] = cargo[key];
            }
          }
        }
        return _cargo;
      });
      this.dischargingInformationData.cargoVesselTankDetails.loadableQuantityCargoDetails = loadableQuantityCargoDetails;
      this.dischargingInformationData.dischargingSequences.loadingDischargingDelays = this.dischargingInformationData.dischargingSequences['dischargingDelays']

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
   * @param {IDischargingInformationResponse} dischargingInformationData
   * @memberof DischargingInformationComponent
   */
  initFormArray(dischargingInformationData: IDischargingInformationResponse) {
    const cargoToBeDischarged = dischargingInformationData?.cargoVesselTankDetails?.loadableQuantityCargoDetails?.map(cargo => {
      return this.fb.group({
        protested: this.fb.control(cargo.protested.value),
        isCommingled: this.fb.control(cargo?.isCommingled.value)
      })
    });
    this.dischargingInformationForm = this.fb.group({
      cargoTobeLoadedDischarged: this.fb.group({
        dataTable: this.fb.array([...cargoToBeDischarged])
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
      this.dischargingInformationPostData.synopticalTableId = this.dischargingInformationData?.synopticTableId;
    }
    this.loadingDischargingTransformationService.setDischargingInformationValidity(this.dischargingInformationData?.isDischargingInfoComplete)
    this.dischargingInfoId = this.dischargingInformationData?.dischargingInfoId;
    this.dischargingInformationId.emit(this.dischargingInfoId);
    this.trackStartEndStage = this.dischargingInformationData?.dischargingStages?.trackStartEndStage;
    this.trackGradeSwitch = this.dischargingInformationData?.dischargingStages?.trackGradeSwitch;
    this.cargoVesselTankDetails = this.dischargingInformationData?.cargoVesselTankDetails;
    this.stageOffsetList = this.dischargingInformationData?.dischargingStages.stageOffsetList;
    this.stageDurationList = this.dischargingInformationData?.dischargingStages.stageDurationList;
    this.stageDuration = this.stageDurationList?.find(duration => duration.id === this.dischargingInformationData?.dischargingStages?.stageDuration);
    this.stageOffset = this.stageOffsetList?.find(offset => offset.id === this.dischargingInformationData?.dischargingStages?.stageOffset);
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
    this.stageOffset = event?.value;
    this.onUpdateDischargingStages();
  }

  /**
  * Method for event stage duration value change
  *
  * @memberof DischargingInformationComponent
  */
  onStageDurationValChange(event) {
    this.stageDuration = event?.value;
    this.onUpdateDischargingStages();
  }

  /**
  * Method for event discharging stages data update
  *
  * @memberof DischargingInformationComponent
  */
  onUpdateDischargingStages() {
    this.dischargingInformationPostData.dischargingStages.stageOffset = this.stageOffset;
    this.dischargingInformationPostData.dischargingStages.stageDuration = this.stageDuration;
    this.dischargingInformationPostData.dischargingStages.trackStartEndStage = this.trackStartEndStage;
    this.dischargingInformationPostData.dischargingStages.trackGradeSwitch = this.trackGradeSwitch;
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
    this.dischargingInformationPostData.dischargingStages.trackStartEndStage = this.trackStartEndStage;
    this.dischargingInformationPostData.dischargingStages.trackGradeSwitch = this.trackGradeSwitch;
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
  * Method for event topping off sequence update
  *
  * @memberof DischargingInformationComponent
  */
  onUpdateToppingOff(event) {
    this.dischargingInformationPostData.toppingOffSequence = event;
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

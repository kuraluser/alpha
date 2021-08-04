import { Component, Input, OnInit, EventEmitter, Output } from '@angular/core';
import { QUANTITY_UNIT } from '../../../../shared/models/common.model';
import { ICargoVesselTankDetails, ILoadingDischargingStagesDetails, ILoadingInformation, ILoadingInformationResponse, ILoadingInformationSaveResponse, IStageDuration, IStageOffset } from '../../models/loading-discharging.model';
import { LoadingDischargingInformationApiService } from '../../services/loading-discharging-information-api.service';
import { MessageService } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';
import { AppConfigurationService } from '../../../../shared/services/app-configuration/app-configuration.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { ICargo, OPERATIONS } from '../../../core/models/common.model';
import {RulesService}from '../../services/rules/rules.service';
import { LoadingDischargingTransformationService } from '../../services/loading-discharging-transformation.service';

@Component({
  selector: 'cpdss-portal-loading-information',
  templateUrl: './loading-information.component.html',
  styleUrls: ['./loading-information.component.scss']
})

/**
 * Component class for loading information component
 *
 * @export
 * @class LoadingInformationComponent
 * @implements {OnInit}
 */
export class LoadingInformationComponent implements OnInit {
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
    this.getLoadingInformation()
  }

  @Output() loadingInformationId: EventEmitter<any> = new EventEmitter();
  private _portRotationId: number;
  private _cargos: ICargo[];

  loadingInformationData?: ILoadingInformationResponse;
  stageOffsetList: IStageOffset[];
  stageDurationList: IStageDuration[];
  cargoVesselTankDetails: ICargoVesselTankDetails;
  loadingInformationPostData = <ILoadingInformation>{};
  loadingInfoId: number;
  trackStartEndStage: boolean;
  trackGradeSwitch: boolean;
  stageDuration: IStageDuration;
  stageOffset: IStageOffset;
  prevQuantitySelectedUnit: QUANTITY_UNIT;
  hasUnSavedData = false;
  currentQuantitySelectedUnit = <QUANTITY_UNIT>localStorage.getItem('unit');
  readonly OPERATIONS = OPERATIONS;

  constructor(private loadingDischargingInformationApiService: LoadingDischargingInformationApiService,
    private translateService: TranslateService,
    private messageService: MessageService,
    private loadingDischargingTransformationService: LoadingDischargingTransformationService,
    private rulesService : RulesService,
    private ngxSpinnerService: NgxSpinnerService) {}


  async ngOnInit(): Promise<void> {
    this.initSubscriptions();
  }

  /**
  * Initialization for all subscriptions
  *
  * @private
  * @memberof LoadingInformationComponent
  */
  private async initSubscriptions() {
    this.loadingDischargingTransformationService.unitChange$.subscribe((res) => {
      this.prevQuantitySelectedUnit = this.currentQuantitySelectedUnit ?? AppConfigurationService.settings.baseUnit
      this.currentQuantitySelectedUnit = <QUANTITY_UNIT>localStorage.getItem('unit');
    })
  }

  /**
* Method to get loading information
*
* @memberof LoadingInformationComponent
*/
  async getLoadingInformation() {
    this.ngxSpinnerService.show();
    const translationKeys = await this.translateService.get(['LOADING_INFORMATION_NO_ACTIVE_VOYAGE', 'LOADING_INFORMATION_NO_ACTIVE_VOYAGE_MESSAGE']).toPromise();
    try {
      this.loadingInformationData = await this.loadingDischargingInformationApiService.getLoadingInformation(this.vesselId, this.voyageId, this.portRotationId).toPromise();
      this.loadingInformationData.loadingSequences.loadingDischargingDelays = this.loadingInformationData.loadingSequences['loadingDelays'];
      this.rulesService.loadingInfoId.next(this.loadingInformationData.loadingInfoId);
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
 * Method to update Get data
 *
 * @memberof LoadingInformationComponent
 */
  async updateGetData() {
    if (this.loadingInformationData) {
      await this.updatePostData();
    }
    this.loadingDischargingTransformationService.setLoadingInformationValidity(this.loadingInformationData?.isLoadingInfoComplete)
    this.loadingInfoId = this.loadingInformationData?.loadingInfoId;
    this.loadingInformationId.emit(this.loadingInfoId);
    this.loadingInformationData.loadingSequences.loadingDischargingDelays = this.loadingInformationData.loadingSequences['loadingDelays'];
    this.trackStartEndStage = this.loadingInformationData?.loadingStages?.trackStartEndStage;
    this.trackGradeSwitch = this.loadingInformationData?.loadingStages?.trackGradeSwitch;
    this.cargoVesselTankDetails = this.loadingInformationData?.cargoVesselTankDetails;
    this.stageOffsetList = this.loadingInformationData?.loadingStages.stageOffsetList;
    this.stageDurationList = this.loadingInformationData?.loadingStages.stageDurationList;
    this.stageDuration = this.stageDurationList?.find(duration => duration.id === this.loadingInformationData?.loadingStages?.stageDuration);
    this.stageOffset = this.stageOffsetList?.find(offset => offset.id === this.loadingInformationData?.loadingStages?.stageOffset);
  }



  /**
  * Method to update post data
  *
  * @memberof LoadingInformationComponent
  */
  async updatePostData() {
    this.loadingInformationPostData.loadingInfoId = this.loadingInformationData?.loadingInfoId;
    this.loadingInformationPostData.synopticalTableId = this.loadingInformationData?.synopticTableId;
  }




  /**
  * Method for event machinary data update
  *
  * @memberof LoadingInformationComponent
  */
  onUpdatemachineryInUses(event) {
    this.loadingInformationPostData.loadingMachineries = event;
    this.hasUnSavedData = true;
  }

  /**
  * Method for event berth data update
  *
  * @memberof LoadingInformationComponent
  */
  onBerthChange(event) {
    this.loadingInformationPostData.loadingBerths = event;
    this.hasUnSavedData = true;
  }

  /**
  * Method for event stage offset value change
  *
  * @memberof LoadingInformationComponent
  */
  onStageOffsetValChange(event) {
    this.stageOffset = event?.value;
    this.onUpdateLoadingStages();
  }

  /**
  * Method for event stage duration value change
  *
  * @memberof LoadingInformationComponent
  */
  onStageDurationValChange(event) {
    this.stageDuration = event?.value;
    this.onUpdateLoadingStages();
  }

  /**
* Method for event loading stages data update
*
* @memberof LoadingInformationComponent
*/
  onUpdateLoadingStages() {
    this.loadingInformationPostData.loadingStages = <ILoadingDischargingStagesDetails>{};
    this.loadingInformationPostData.loadingStages.stageOffset = this.stageOffset;
    this.loadingInformationPostData.loadingStages.stageDuration = this.stageDuration;
    this.loadingInformationPostData.loadingStages.trackStartEndStage = this.trackStartEndStage;
    this.loadingInformationPostData.loadingStages.trackGradeSwitch = this.trackGradeSwitch;
    this.hasUnSavedData = true;
  }

  /**
  * Method for event loading delay data update
  *
  * @memberof LoadingInformationComponent
  */
  onUpdateLoadingDelays(event) {
    this.loadingInformationPostData.loadingDelays = event;
    this.hasUnSavedData = true;
  }

  /**
 * Method for event loading details data update
 *
 * @memberof LoadingInformationComponent
 */
  onUpdateLoadingDetails(event) {
    this.loadingInformationPostData.loadingDetails = event;
    this.hasUnSavedData = true;
  }

  /**
* Method for event track value change
*
* @memberof LoadingInformationComponent
*/
  onTrackStageChange() {
    this.loadingInformationPostData.loadingStages.trackStartEndStage = this.trackStartEndStage;
    this.loadingInformationPostData.loadingStages.trackGradeSwitch = this.trackGradeSwitch;
    this.onUpdateLoadingStages();
  }

  /**
* Method for event loading rate update
*
* @memberof LoadingInformationComponent
*/
  onLoadingRateChange(event) {
    this.loadingInformationPostData.loadingRates = event;
    this.hasUnSavedData = true;
  }

  /**
* Method for event topping off sequence update
*
* @memberof LoadingInformationComponent
*/
  onUpdateToppingOff(event) {
    this.loadingInformationPostData.toppingOffSequence = event;
    this.hasUnSavedData = true;
  }

  /**
* Method for event to save loading information data
*
* @memberof LoadingInformationComponent
*/
  async saveLoadingInformationData() {
    const translationKeys = await this.translateService.get(['LOADING_INFORMATION_SAVE_ERROR', 'LOADING_INFORMATION_SAVE_NO_DATA_ERROR', 'LOADING_INFORMATION_SAVE_SUCCESS', 'LOADING_INFORMATION_SAVED_SUCCESSFULLY', 'LOADING_INFORMATION_NO_MACHINERY']).toPromise();
    if(this.hasUnSavedData){
      const isLoadingMachinery = this.loadingInformationPostData?.loadingMachineries?.some(machinery => machinery.isUsing) ?? true;
      if(!isLoadingMachinery){
        this.messageService.add({ severity: 'error', summary: translationKeys['LOADING_INFORMATION_SAVE_ERROR'], detail: translationKeys['LOADING_INFORMATION_NO_MACHINERY'] });
      }
      if(this.loadingInformationPostData?.loadingBerths && this.loadingInformationPostData?.loadingBerths.length === 0){
        this.messageService.add({ severity: 'error', summary: translationKeys['LOADING_INFORMATION_SAVE_ERROR'], detail: translationKeys['LOADING_INFORMATION_NO_BERTHS'] });
      }
      else{
        this.ngxSpinnerService.show();
        const result: ILoadingInformationSaveResponse = await this.loadingDischargingInformationApiService.saveLoadingInformation(this.vesselId, this.voyageId, this.loadingInformationPostData).toPromise();
        if (result?.responseStatus?.status === '200') {
          this.loadingInformationData = result?.loadingInformation;
          await this.updateGetData();
          this.hasUnSavedData = false;
          this.messageService.add({ severity: 'success', summary: translationKeys['LOADING_INFORMATION_SAVE_SUCCESS'], detail: translationKeys['LOADING_INFORMATION_SAVED_SUCCESSFULLY'] });
        }
        this.ngxSpinnerService.hide();
      }
      
    }else{
      this.messageService.add({ severity: 'error', summary: translationKeys['LOADING_INFORMATION_SAVE_ERROR'], detail: translationKeys['LOADING_INFORMATION_SAVE_NO_DATA_ERROR'] });
    }
  }


}

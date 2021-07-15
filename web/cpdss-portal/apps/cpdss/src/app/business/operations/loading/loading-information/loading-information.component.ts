import { Component, HostListener, Input, OnInit } from '@angular/core';
import { ICargo, QUANTITY_UNIT } from 'apps/cpdss/src/app/shared/models/common.model';
import { ICargoVesselTankDetails, ILoadingInformation, ILoadingInformationResponse, ILoadingInformationSaveResponse, IStageDuration, IStageOffset } from '../../models/loading-information.model';
import { LoadingInformationApiService } from '../../services/loading-information-api.service';
import { GlobalErrorHandler } from 'apps/cpdss/src/app/shared/services/error-handlers/global-error-handler';
import { MessageService } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';
import { LoadingTransformationService } from '../../services/loading-transformation.service';
import { AppConfigurationService } from 'apps/cpdss/src/app/shared/services/app-configuration/app-configuration.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { ComponentCanDeactivate } from 'apps/cpdss/src/app/shared/services/guards/component-can-deactivate';
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
export class LoadingInformationComponent extends ComponentCanDeactivate implements OnInit {
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
  constructor(private loadingInformationApiService: LoadingInformationApiService,
    private globalErrorHandler: GlobalErrorHandler,
    private translateService: TranslateService,
    private messageService: MessageService,
    private loadingTransformationService: LoadingTransformationService,
    private ngxSpinnerService: NgxSpinnerService) { 
      super();
    }

  @HostListener('window:beforeunload', ['$event'])
  public onPageUnload($event: BeforeUnloadEvent) {
    if (this.hasUnSavedData) {
      $event.returnValue = true;
    }
  }
  hasUnsavedData():boolean{
    return this.hasUnSavedData;
  }
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
    this.loadingTransformationService.unitChange$.subscribe((res) => {
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
      this.loadingInformationData = await this.loadingInformationApiService.getLoadingInformation(this.vesselId, this.voyageId, this.portRotationId).toPromise();
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
    this.loadingTransformationService.setLoadingInformationValidity(this.loadingInformationData?.isLoadingInfoComplete)
    this.loadingInfoId = this.loadingInformationData?.loadingInfoId;
    this.trackStartEndStage = this.loadingInformationData?.loadingStages?.trackStartEndStage;
    this.trackGradeSwitch = this.loadingInformationData?.loadingStages?.trackGradeSwitch;
    this.cargoVesselTankDetails = this.loadingInformationData?.cargoVesselTankDetails;
    this.stageOffsetList = this.loadingInformationData?.loadingStages.stageOffsetList;
    this.stageDurationList = this.loadingInformationData?.loadingStages.stageDurationList;
    this.stageDuration = this.stageDurationList?.find(duration => duration.id === this.loadingInformationData?.loadingStages?.stageDuration);
    this.stageOffset = this.stageOffsetList?.find(offset => offset.id === this.loadingInformationData?.loadingStages?.stageOffset);
    this.loadingInformationData?.machineryInUses?.vesselPumps?.map((pump) => {
      const machinaryUsed = this.loadingInformationData?.machineryInUses?.loadingMachinesInUses?.find((machine) => machine.pumpId === pump.id);
      if (machinaryUsed) {
        pump.capacity = machinaryUsed.capacity;
      } else {
        pump.capacity = pump.pumpCapacity;
      }
    })
    this.loadingInformationData?.machineryInUses?.loadingMachinesInUses?.map((machine) => {
      machine.isUsing = this.loadingInformationData?.machineryInUses?.vesselPumps?.some((pump) => pump.id === machine.pumpId)
    })
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
    this.ngxSpinnerService.show();
    const result: ILoadingInformationSaveResponse = await this.loadingInformationApiService.saveLoadingInformation(this.vesselId, this.voyageId, this.loadingInformationPostData).toPromise();
    if (result?.responseStatus?.status === '200') {
      this.loadingInformationData = result?.loadingInformation;
      await this.updateGetData();
      this.hasUnSavedData = false;
    }
    this.ngxSpinnerService.hide();
  }


}

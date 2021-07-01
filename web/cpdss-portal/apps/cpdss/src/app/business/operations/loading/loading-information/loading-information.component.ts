import { Component, Input, OnInit } from '@angular/core';
import { ICargo, ICargoResponseModel, QUANTITY_UNIT } from 'apps/cpdss/src/app/shared/models/common.model';
import { DATATABLE_EDITMODE } from '../../../../shared/components/datatable/datatable.model';
import { ICargoVesselTankDetails, ILoadingInformationList, ILoadingInformationResponse, IStageDurationList, IStageOffsetList } from '../../models/loading-information.model';
import { LoadingInformationApiService } from '../../services/loading-information-api.service';
import { v4 as uuid4 } from 'uuid';
import { GlobalErrorHandler } from 'apps/cpdss/src/app/shared/services/error-handlers/global-error-handler';
import { MessageService } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';
import { LoadingTransformationService } from '../../services/loading-transformation.service';
import { AppConfigurationService } from 'apps/cpdss/src/app/shared/services/app-configuration/app-configuration.service';
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
  @Input() get portRotationId(): number {
    return this._portRotationId;
  }
  set portRotationId(portRotationId: number) {
    this._portRotationId = portRotationId;
    this.getLoadingInformation()
  }

  private _portRotationId: number;

  loadingRateEditMode = DATATABLE_EDITMODE.CELL;
  loadingInformationData?: ILoadingInformationResponse;
  stageOffsetList: IStageOffsetList[];
  stageDurationList: IStageDurationList[];
  cargoVesselTankDetails: ICargoVesselTankDetails;
  cargos: ICargo[];
  loadingInformationPostData = <ILoadingInformationList>{};
  loadingInfoId: number;
  trackStartEndStage: boolean;
  trackGradeSwitch: boolean;
  stageDuration: IStageDurationList;
  stageOffset: IStageOffsetList;
  prevQuantitySelectedUnit: QUANTITY_UNIT;
  currentQuantitySelectedUnit = <QUANTITY_UNIT>localStorage.getItem('unit');
  constructor(private loadingInformationApiService: LoadingInformationApiService,
    private globalErrorHandler: GlobalErrorHandler,
    private translateService: TranslateService,
    private messageService: MessageService,
    private loadingTransformationService: LoadingTransformationService) { }

  async ngOnInit(): Promise<void> {
    this.initSubscriptions();
    await this.getCargos();
  }

  /**
   * Component lifecycle ngOnDestroy
   *
   * @returns {Promise<void>}
   * @memberof LoadingInformationComponent
   */
  ngOnDestroy() {
    navigator.serviceWorker.removeEventListener('message', this.swMessageHandler);
  }

  /**
  * Initialization for all subscriptions
  *
  * @private
  * @memberof LoadingInformationComponent
  */
  private async initSubscriptions() {
    navigator.serviceWorker.addEventListener('message', this.swMessageHandler);
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
    const translationKeys = await this.translateService.get(['LOADING_INFORMATION_NO_ACTIVE_VOYAGE', 'LOADING_INFORMATION_NO_ACTIVE_VOYAGE_MESSAGE']).toPromise();
    try {
    this.loadingInformationData = await this.loadingInformationApiService.getLoadingInformation(this.vesselId, this.voyageId, this.portRotationId).toPromise();
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
  }
  catch (error) {
    if (error.error.errorCode === 'ERR-RICO-522') {
      this.messageService.add({ severity: 'error', summary: translationKeys['LOADING_INFORMATION_NO_ACTIVE_VOYAGE'], detail: translationKeys['LOADING_INFORMATION_NO_ACTIVE_VOYAGE_MESSAGE'] });
    }
  }
  }

  /**
* Method to get cargos
*
* @memberof LoadingInformationComponent
*/
  async getCargos() {
    const cargos: ICargoResponseModel = await this.loadingInformationApiService.getCargos().toPromise();
    if (cargos.responseStatus.status === '200') {
      this.cargos = cargos.cargos;
    }
  }

  /**
  * Method to update post data
  *
  * @memberof LoadingInformationComponent
  */
  async updatePostData() {
    this.loadingInformationPostData.storeKey = this.loadingInformationPostData.storeKey ?? uuid4();
    this.loadingInformationPostData.loadingInfoId = this.loadingInformationData?.loadingInfoId;
    this.loadingInformationPostData.trackStartEndStage = this.trackStartEndStage;
    this.loadingInformationPostData.trackGradeSwitch = this.trackGradeSwitch;
    this.loadingInformationPostData.synopticalTableId = this.loadingInformationData?.synopticTableId;
    this.loadingInformationPostData.loadingDetails = this.loadingInformationData?.loadingDetails;
    this.loadingInformationPostData.loadingRates = this.loadingInformationData?.loadingRates;
    this.loadingInformationPostData.loadingBerths = this.loadingInformationData?.berthDetails?.selectedBerths ?? [];
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
    this.loadingInformationData.machineryInUses.loadingMachinesInUses = this.loadingInformationData?.machineryInUses?.loadingMachinesInUses ?? [];
    this.loadingInformationPostData.loadingMachineries = this.loadingInformationData?.machineryInUses?.loadingMachinesInUses ?? [];
    this.loadingInformationPostData.toppingOffSequence = this.loadingInformationData?.toppingOffSequence ?? [];
    this.loadingInformationPostData.loadingDelays = this.loadingInformationData?.loadingSequences?.loadingDelays ?? [];
  }

  /**
  * Handler for service worker message event
  *
  * @private
  * @memberof LoadingInformationComponent
  */
  private swMessageHandler = async (event) => {
    if (event?.data?.type === 'loading_information_sync_finished') {
      if (event?.data?.responseStatus?.status === '200') {
        this.getLoadingInformation();
      }
      if (event?.data?.status === '401' && event?.data?.errorCode === '210') {
        this.globalErrorHandler.sessionOutMessage();
      }
    }
  }


  /**
  * Method for event machinary data update
  *
  * @memberof LoadingInformationComponent
  */
  onUpdatemachineryInUses(event) {
    this.loadingInformationPostData.loadingMachineries = event;
    this.saveLoadingInformationData();
  }

  /**
  * Method for event berth data update
  *
  * @memberof LoadingInformationComponent
  */
  onBerthChange(event) {
    this.loadingInformationPostData.loadingBerths = event;
    this.saveLoadingInformationData();
  }

  /**
  * Method for event stage offset value change
  *
  * @memberof LoadingInformationComponent
  */
  onStageOffsetValChange(event) {
    this.loadingInformationPostData.stageOffset = event?.value;
    this.saveLoadingInformationData();
  }

  /**
  * Method for event stage duration value change
  *
  * @memberof LoadingInformationComponent
  */
  onStageDurationValChange(event) {
    this.loadingInformationPostData.stageDuration = event?.value;
    this.saveLoadingInformationData();
  }

  /**
  * Method for event loading delay data update
  *
  * @memberof LoadingInformationComponent
  */
  onUpdateLoadingDelays(event) {
    this.loadingInformationPostData.loadingDelays = event;
    this.saveLoadingInformationData();
  }

  /**
 * Method for event loading details data update
 *
 * @memberof LoadingInformationComponent
 */
  onUpdateLoadingDetails(event) {
    this.loadingInformationPostData.loadingDetails = event;
    this.saveLoadingInformationData();
  }

  /**
* Method for event track value change
*
* @memberof LoadingInformationComponent
*/
  onTrackStageChange() {
    this.loadingInformationPostData.trackStartEndStage = this.trackStartEndStage;
    this.loadingInformationPostData.trackGradeSwitch = this.trackGradeSwitch;
    this.saveLoadingInformationData();
  }

  /**
* Method for event loading rate update
*
* @memberof LoadingInformationComponent
*/
  onLoadingRateChange(event) {
    this.loadingInformationPostData.loadingRates = event;
    this.saveLoadingInformationData();
  }

  /**
* Method for event topping off sequence update
*
* @memberof LoadingInformationComponent
*/
  onUpdateToppingOff(event) {
    this.loadingInformationPostData.toppingOffSequence = event;
    this.saveLoadingInformationData();
  }

  /**
* Method for event to save loading information data
*
* @memberof LoadingInformationComponent
*/
  saveLoadingInformationData() {
    this.loadingInformationApiService.setLoadingInformation(this.loadingInformationPostData, this.vesselId, this.voyageId)
  }

 

}

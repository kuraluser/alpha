import { Component, Input, OnInit } from '@angular/core';
import { ICargo, ICargoResponseModel } from 'apps/cpdss/src/app/shared/models/common.model';
import { DATATABLE_EDITMODE } from '../../../../shared/components/datatable/datatable.model';
import { ICargoVesselTankDetails, ILoadingInformationList, ILoadingInformationResponse, IStageDurationList, IStageOffsetList } from '../../models/loading-information.model';
import { LoadingInformationApiService } from '../../services/loading-information-api.service';
import { v4 as uuid4 } from 'uuid';
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
  @Input() portRotationId: number;

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
  constructor(private loadingInformationApiService: LoadingInformationApiService) { }

  async ngOnInit(): Promise<void> {
    await this.getCargos();
    this.loadingInformationData = await this.loadingInformationApiService.getLoadingInformation(this.vesselId, this.voyageId, this.portRotationId).toPromise();
    if (this.loadingInformationData) {
      await this.updatePostData();
    }
    this.loadingInfoId = this.loadingInformationData?.loadingInfoId;
    this.trackStartEndStage = this.loadingInformationData?.loadingStages?.trackStartEndStage;
    this.trackGradeSwitch = this.loadingInformationData?.loadingStages?.trackGradeSwitch;
    this.cargoVesselTankDetails = this.loadingInformationData?.cargoVesselTankDetails;
    this.stageOffsetList = this.loadingInformationData?.loadingStages.stageOffsetList;
    this.stageDurationList = this.loadingInformationData?.loadingStages.stageDurationList;
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
    this.loadingInformationPostData.synopticalTableId = this.loadingInformationData?.synopticalTableId;
    this.loadingInformationPostData.loadingDetails = this.loadingInformationData?.loadingDetails;
    this.loadingInformationPostData.loadingRates = this.loadingInformationData?.loadingRates;
    this.loadingInformationPostData.loadingBerths = this.loadingInformationData?.berthDetails.selectedBerths;
    this.loadingInformationData?.machineryInUses?.vesselPumps?.map((pump) => {
      const machinaryUsed = this.loadingInformationData?.machineryInUses?.loadingMachinesInUses?.find((machine) => machine.pumpId === pump.pumpTypeId);
      if (machinaryUsed) {
        pump.capacity = machinaryUsed.capacity;
      } else {
        pump.capacity = pump.pumpCapacity;
      }
    })
    this.loadingInformationData?.machineryInUses?.loadingMachinesInUses?.map((machine) => {
      machine.isUsing = this.loadingInformationData?.machineryInUses?.vesselPumps?.some((pump) => pump.pumpTypeId === machine.pumpId)
    })
    this.loadingInformationPostData.loadingMachineries = this.loadingInformationData?.machineryInUses.loadingMachinesInUses;

  }

  /**
  * Method for event machinary data update
  *
  * @memberof LoadingInformationComponent
  */
  onUpdatemachineryInUses(event) {
    this.loadingInformationData.machineryInUses = event;
  }

  /**
  * Method for event berth data update
  *
  * @memberof LoadingInformationComponent
  */
  onBerthChange(event) {
    this.loadingInformationData.berthDetails = event;
  }

  /**
  * Method for event stage offset value change
  *
  * @memberof LoadingInformationComponent
  */
  onStageOffsetValChange(event) {
    this.loadingInformationPostData.stageOffset = event?.value;
  }

  /**
  * Method for event stage duration value change
  *
  * @memberof LoadingInformationComponent
  */
  onStageDurationValChange(event) {
    this.loadingInformationPostData.stageDuration = event?.value;
  }

  /**
  * Method for event loading delay data update
  *
  * @memberof LoadingInformationComponent
  */
  onUpdateLoadingDelays(event) {
    this.loadingInformationPostData.loadingDelays = event;
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

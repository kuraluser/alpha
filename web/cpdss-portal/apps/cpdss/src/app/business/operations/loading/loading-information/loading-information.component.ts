import { Component, Input, OnInit, EventEmitter, Output , ViewChild , OnDestroy} from '@angular/core';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { QUANTITY_UNIT, RATE_UNIT } from '../../../../shared/models/common.model';
import { ICargoVesselTankDetails, ILoadingDischargingStages, ILoadingInformation, ILoadingInformationResponse, ILoadingInformationSaveResponse, IStageDuration, IStageOffset, ULLAGE_STATUS_VALUE, ILoadableQuantityCargoSave } from '../../models/loading-discharging.model';
import { LoadingDischargingInformationApiService } from '../../services/loading-discharging-information-api.service';
import { MessageService } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';
import { AppConfigurationService } from '../../../../shared/services/app-configuration/app-configuration.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { ICargo, OPERATIONS , ILoadableQuantityCargo } from '../../../core/models/common.model';
import { RulesService }from '../../services/rules.service';
import { LoadingDischargingTransformationService } from '../../services/loading-discharging-transformation.service';
import { LoadingDischargingManageSequenceComponent } from '../../loading-discharging-manage-sequence/loading-discharging-manage-sequence.component';
import { LoadingDischargingCargoMachineryComponent } from '../../loading-discharging-cargo-machinery/loading-discharging-cargo-machinery.component';
import { IPermission } from '../../../../shared/models/user-profile.model';

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
export class LoadingInformationComponent implements OnInit , OnDestroy {
  @ViewChild('manageSequence') manageSequence: LoadingDischargingManageSequenceComponent;
  @ViewChild('dischargeBerth') dischargeBerth;
  @ViewChild('machineryRef') machineryRef: LoadingDischargingCargoMachineryComponent;
  @ViewChild('dischargeDetails') dischargeDetails;
  @ViewChild('loadingRate') loadingRate;
  @ViewChild('cargoMachinery')cargoMachineryRef;
  @ViewChild('cargoToBeloaded') cargoToBeloaded;

  @Input() voyageId: number;
  @Input() vesselId: number;
  disableSaveButton: boolean;
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
    this.getLoadingInformation()
  }

  @Output() loadingInformationId: EventEmitter<any> = new EventEmitter();
  private _portRotationId: number;
  private _cargos: ICargo[];

  loadingInformationData?: ILoadingInformationResponse;
  stageOffsetList: IStageOffset[];
  stageDurationList: IStageDuration[];
  cargoVesselTankDetails: ICargoVesselTankDetails;
  manageSequenceLoadableQuantityCargoDetails: ILoadableQuantityCargo[];
  loadingInformationPostData = <ILoadingInformation>{};
  loadingInfoId: number;
  trackStartEndStage: boolean;
  trackGradeSwitch: boolean;
  stageDuration: IStageDuration;
  stageOffset: IStageOffset;
  prevQuantitySelectedUnit: QUANTITY_UNIT;
  hasUnSavedData = false;
  currentQuantitySelectedUnit = <QUANTITY_UNIT>localStorage.getItem('unit');
  currentRateSelectedUnit = <RATE_UNIT>localStorage.getItem('rate_unit');
  isDischargeStarted: boolean;
  readonly OPERATIONS = OPERATIONS;
  private ngUnsubscribe: Subject<any> = new Subject();

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
   * unsubscribing loading info observable
   * @memberof UllageUpdatePopupComponent
   */
  ngOnDestroy() {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  /**
  * Initialization for all subscriptions
  *
  * @private
  * @memberof LoadingInformationComponent
  */
  private async initSubscriptions() {
    this.loadingDischargingTransformationService.unitChange$.pipe(takeUntil(this.ngUnsubscribe)).subscribe((res) => {
      this.prevQuantitySelectedUnit = this.currentQuantitySelectedUnit ?? AppConfigurationService.settings.baseUnit;
      this.currentQuantitySelectedUnit = <QUANTITY_UNIT>localStorage.getItem('unit');
    });
    this.loadingDischargingTransformationService.disableInfoInstructionRuleSave.pipe(takeUntil(this.ngUnsubscribe)).subscribe((status) => {
      this.disableSaveButton = status;
    });
    this.loadingDischargingTransformationService.isDischargeStarted$.pipe(takeUntil(this.ngUnsubscribe)).subscribe((value) => {
      this.isDischargeStarted = value;
    });
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
      this.hasUnSavedData = false;
      this.loadingInformationPostData = <ILoadingInformation>{};
      this.loadingInformationData = await this.loadingDischargingInformationApiService.getLoadingInformation(this.vesselId, this.voyageId, this.portRotationId).toPromise();
      this.loadingDischargingTransformationService._loadingInformationSource.next(this.loadingInformationData?.isLoadingInfoComplete)
      this.loadingDischargingTransformationService._loadingInstructionSource.next(this.loadingInformationData?.isLoadingInstructionsComplete)

      this.loadingDischargingTransformationService.isLoadingSequenceGenerated.next(this.loadingInformationData?.isLoadingSequenceGenerated)
      this.loadingDischargingTransformationService.isLoadingPlanGenerated.next(this.loadingInformationData?.isLoadingPlanGenerated);


      if (this.loadingInformationData.loadingInfoStatusId === 5 || this.loadingInformationData.loadingInfoStatusId === 6 || this.loadingInformationData.loadingInfoStatusId === 7 || this.loadingInformationData.loadingInfoStatusId === 2 || this.loadingInformationData.loadingInfoStatusId === 0 || this.loadingInformationData.loadingInfoStatusId === 1) {
        this.loadingDischargingTransformationService.disableInfoInstructionRuleSave.next(false);
        this.loadingDischargingTransformationService.inProcessing.next(false);
        this.loadingDischargingTransformationService.generateLoadingPlanButton.next(false)
        if(this.loadingInformationData.loadingInfoStatusId === 6 || this.loadingInformationData.loadingInfoStatusId === 7){
          this.loadingDischargingTransformationService.disableViewErrorButton.next(false);
        } else {
          this.loadingDischargingTransformationService.disableViewErrorButton.next(true);
        }
      }
      else {
        this.loadingDischargingTransformationService.inProcessing.next(true);
        this.loadingDischargingTransformationService.disableInfoInstructionRuleSave.next(true);
        this.loadingDischargingTransformationService.generateLoadingPlanButton.next(true)
        this.loadingDischargingTransformationService.disableViewErrorButton.next(true);
      }
      if(this.loadingInformationData.loadingPlanDepStatusId === ULLAGE_STATUS_VALUE.SUCCESS){
        this.loadingDischargingTransformationService.disableInfoInstructionRuleSave.next(true);
        this.loadingDischargingTransformationService.inProcessing.next(true);
        this.loadingDischargingTransformationService.generateLoadingPlanButton.next(true)
        this.loadingDischargingTransformationService.disableViewErrorButton.next(true);
      }
      this.rulesService.loadingDischargingInfo.next(this.loadingInformationData);
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
    this.loadingInformationData.loadingSequences["loadingDischargingDelays"] = this.loadingInformationData.loadingSequences.loadingDelays;
    this.loadingInformationData.machineryInUses['loadingDischargingMachinesInUses'] = this.loadingInformationData?.machineryInUses?.loadingMachinesInUses;
    // this.trackStartEndStage = this.loadingInformationData?.loadingStages?.trackStartEndStage; // <!-- TODO-Commented based on the user story  - DSS-4090 --> may require in future
    // this.trackGradeSwitch = this.loadingInformationData?.loadingStages?.trackGradeSwitch;
    this.cargoVesselTankDetails = this.loadingInformationData?.cargoVesselTankDetails;
    this.stageOffsetList = this.loadingInformationData?.loadingStages.stageOffsetList;
    this.stageDurationList = this.loadingInformationData?.loadingStages.stageDurationList;
    this.manageSequenceLoadableQuantityCargoDetails = JSON.parse(JSON.stringify(this.loadingInformationData?.cargoVesselTankDetails.loadableQuantityCargoDetails));
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
  * Method for event cargo to be loaded data update
  *
  * @memberof LoadingInformationComponent
  */
  cargoToBeLoadedChange(event){
    this.loadingInformationPostData.cargoToBeLoaded = <ILoadableQuantityCargoSave>{};
    this.loadingInformationPostData.cargoToBeLoaded.loadableQuantityCargoDetails = event;
    this.hasUnSavedData = true;
    this.checkLoadingRateErrors();
  }

  /**
  * Method for loading rate error check
  *
  * @memberof LoadingInformationComponent
  */
  checkLoadingRateErrors() {
    this.loadingInformationPostData?.cargoToBeLoaded?.loadableQuantityCargoDetails?.map((item, index) => {
      if (this.loadingRate.loadingRatesFormGroup.controls?.maxLoadingRate?.value < item.maxLoadingRate) {
        setTimeout(() => {
          this.cargoToBeloaded?.form?.controls?.cargoTobeLoadedDischarged.controls.dataTable.controls[index].controls.maxLoadingRateEdit.setErrors({ maxRate: true });
        });
      } else if (this.cargoToBeloaded?.form?.controls?.cargoTobeLoadedDischarged?.controls?.dataTable?.controls[index].controls?.maxLoadingRateEdit?.errors?.maxRate) {
        delete this.cargoToBeloaded?.form?.controls?.cargoTobeLoadedDischarged.controls.dataTable.controls[index].controls.maxLoadingRateEdit.errors?.maxRate;
        if (Object.keys(this.cargoToBeloaded?.form?.controls?.cargoTobeLoadedDischarged.controls.dataTable.controls[index].controls.maxLoadingRateEdit.errors).length === 0) {
          this.cargoToBeloaded?.form?.controls?.cargoTobeLoadedDischarged.controls.dataTable.controls[index].controls.maxLoadingRateEdit.setErrors(null);
        }
      }
      if (this.loadingRate.loadingRatesFormGroup.controls?.minLoadingRate?.value > item.maxLoadingRate) {
        setTimeout(() => {
          this.cargoToBeloaded?.form?.controls?.cargoTobeLoadedDischarged.controls.dataTable.controls[index].controls.maxLoadingRateEdit.setErrors({ minRate: true });
        });
      } else if (this.cargoToBeloaded?.form?.controls?.cargoTobeLoadedDischarged?.controls?.dataTable?.controls[index].controls?.maxLoadingRateEdit?.errors?.minRate) {
        delete this.cargoToBeloaded?.form?.controls?.cargoTobeLoadedDischarged.controls.dataTable.controls[index].controls.maxLoadingRateEdit.errors?.minRate;
        if (Object.keys(this.cargoToBeloaded?.form?.controls?.cargoTobeLoadedDischarged.controls.dataTable.controls[index].controls.maxLoadingRateEdit.errors).length === 0) {
          this.cargoToBeloaded?.form?.controls?.cargoTobeLoadedDischarged.controls.dataTable.controls[index].controls.maxLoadingRateEdit.setErrors(null);
        }
      }
    });
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
    this.loadingInformationPostData.loadingStages = <ILoadingDischargingStages>{};
    this.loadingInformationPostData.loadingStages.stageOffset = this.stageOffset;
    this.loadingInformationPostData.loadingStages.stageDuration = this.stageDuration;
    // this.loadingInformationPostData.loadingStages.trackStartEndStage = this.trackStartEndStage;  //may require in future
    // this.loadingInformationPostData.loadingStages.trackGradeSwitch = this.trackGradeSwitch;
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
* Method for event loading rate update
*
* @memberof LoadingInformationComponent
*/
  onLoadingRateChange(event) {
    this.loadingInformationPostData.loadingRates = event;
    this.hasUnSavedData = true;
  }

  /**
  * Method for saving loading information
  *
  * @memberof LoadingInformationComponent
  */
  saveDetails() {
    this.manageSequence.loadingDischargingSequenceForm.markAsDirty();
    this.manageSequence.loadingDischargingSequenceForm.markAllAsTouched();

    this.dischargeDetails.loadingDischargingDetailsForm.markAsDirty();
    this.dischargeDetails.loadingDischargingDetailsForm.markAllAsTouched();

    this.dischargeBerth.berthDetailsForm.markAsDirty();
    this.dischargeBerth.berthDetailsForm.updateValueAndValidity();

    this.cargoMachineryRef.machineryForm.markAsDirty();
    this.cargoMachineryRef.machineryForm.markAllAsTouched();


    setTimeout(() => {
      this.saveLoadingInformationData();
      this.loadingDischargingTransformationService.loadingInstructionValidity$.pipe(takeUntil(this.ngUnsubscribe)).subscribe((status)=>{
        if(status){
          this.loadingDischargingTransformationService.inProcessing.next(false);
        }
      })
    })
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

  isLoadingInfoValid() {
    if(this.loadingRate?.loadingRatesFormGroup?.invalid ||
      this.manageSequence?.loadingDischargingSequenceForm?.invalid ||
      this.dischargeBerth?.berthForm?.invalid ||
      this.dischargeBerth?.berthDetailsForm?.invalid ||
      this.dischargeDetails?.loadingDischargingDetailsForm?.invalid ||
      this.loadingRate?.loadingRatesFormGroup?.invalid) {
        return false;
      } else {
        return true;
      }
  }

  /**
  * Method for event to save loading information data
  *
  * @memberof LoadingInformationComponent
  */
  async saveLoadingInformationData() {
    const translationKeys = await this.translateService.get(['LOADING_INFORMATION_INVALID_DATA','LOADING_INFORMATION_SAVE_ERROR', 'LOADING_INFORMATION_SAVE_NO_DATA_ERROR', 'LOADING_INFORMATION_SAVE_SUCCESS', 'LOADING_INFORMATION_SAVED_SUCCESSFULLY', 'LOADING_INFORMATION_NO_MACHINERY', 'LOADING_INFORMATION_NO_BERTHS']).toPromise();
    this.checkLoadingRateErrors();
    if(this.loadingRate?.loadingRatesFormGroup?.invalid || this.manageSequence.loadingDischargingSequenceForm.invalid || this.dischargeBerth.berthForm.invalid || this.dischargeBerth.berthDetailsForm.invalid ||
      this.dischargeDetails.loadingDischargingDetailsForm.invalid || this.loadingRate.loadingRatesFormGroup.invalid || this.cargoMachineryRef.machineryForm.invalid || this.cargoToBeloaded?.form?.invalid) {


      this.messageService.add({ severity: 'error', summary: translationKeys['LOADING_INFORMATION_SAVE_ERROR'], detail: translationKeys['LOADING_INFORMATION_INVALID_DATA'] });
      if(document.querySelector('.error-icon') && !this.dischargeDetails.loadingDischargingDetailsForm.invalid) {
        document.querySelector('.error-icon').scrollIntoView({ behavior: "smooth"});
      }
      return;
    }
    const isMachineryValid = await this.machineryRef.isMachineryValid(true);
    if(!isMachineryValid) {
      return;
    }
    const iscargoAdded = await this.manageSequence.checkCargoCount(true);
    if(!iscargoAdded) {
      return;
    }
    if(this.hasUnSavedData){
      const isLoadingMachinery = this.loadingInformationPostData?.loadingMachineries?.some(machinery => machinery.isUsing) ?? true;
      if(!isLoadingMachinery){
        this.messageService.add({ severity: 'error', summary: translationKeys['LOADING_INFORMATION_SAVE_ERROR'], detail: translationKeys['LOADING_INFORMATION_NO_MACHINERY'] });
      }
      if((this.loadingInformationPostData?.loadingBerths && this.loadingInformationPostData?.loadingBerths.length === 0) || (!this.loadingInformationPostData?.loadingBerths && this.loadingInformationData.berthDetails.selectedBerths.length === 0)){
        this.messageService.add({ severity: 'error', summary: translationKeys['LOADING_INFORMATION_SAVE_ERROR'], detail: translationKeys['LOADING_INFORMATION_NO_BERTHS'] });
      } else{
        this.ngxSpinnerService.show();
        this.loadingInformationPostData.isLoadingInfoComplete = true
        const result: ILoadingInformationSaveResponse = await this.loadingDischargingInformationApiService.saveLoadingInformation(this.vesselId, this.voyageId, this.loadingInformationPostData).toPromise();
        if (result?.responseStatus?.status === '200') {
          this.hasUnSavedData = false;
          this.loadingDischargingTransformationService.setLoadingInformationValidity(true)
          this.messageService.add({ severity: 'success', summary: translationKeys['LOADING_INFORMATION_SAVE_SUCCESS'], detail: translationKeys['LOADING_INFORMATION_SAVED_SUCCESSFULLY'] });

        }
        this.ngxSpinnerService.hide();
      }

    }else{
      this.messageService.add({ severity: 'error', summary: translationKeys['LOADING_INFORMATION_SAVE_ERROR'], detail: translationKeys['LOADING_INFORMATION_SAVE_NO_DATA_ERROR'] });
    }
  }


}

import { Component, ComponentRef, HostListener, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subject, Observable } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { IAlgoError, IAlgoResponse, ICargo, ICargoResponseModel, OPERATIONS ,OPERATIONS_PLAN_STATUS} from '../../core/models/common.model';
import { OPERATION_TAB } from '../models/operations.model';
import { LoadingInformationComponent } from './loading-information/loading-information.component';
import { UnsavedChangesGuard, ComponentCanDeactivate } from './../../../shared/services/guards/unsaved-data-guard';
import { LoadingDischargingTransformationService } from '../services/loading-discharging-transformation.service';
import { OperationsApiService } from '../services/operations-api.service';
import { LoadingApiService } from '../services/loading-api.service';
import { TranslateService } from '@ngx-translate/core';
import { MessageService } from 'primeng/api';
import { GlobalErrorHandler } from '../../../shared/services/error-handlers/global-error-handler';
import { environment } from '../../../../environments/environment';
import { SecurityService } from '../../../shared/services/security/security.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { RATE_UNIT } from '../../../shared/models/common.model';

/**
 * Component class for loading component
 *
 * @export
 * @class LoadingComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-loading',
  templateUrl: './loading.component.html',
  styleUrls: ['./loading.component.scss']
})
export class LoadingComponent implements OnInit, OnDestroy, ComponentCanDeactivate {

  @ViewChild(LoadingInformationComponent) loadingInformationComponent: LoadingInformationComponent
  @ViewChild('loadingInstruction') loadingInstruction;
  currentTab: OPERATION_TAB = OPERATION_TAB.INFORMATION;
  OPERATION_TAB = OPERATION_TAB;
  vesselId: number;
  voyageId: number;
  portRotationId: number;
  loadingInfoId: number;
  cargoTanks = [];
  display = false;
  selectedPortName: string;
  loadingInformationComplete: boolean;
  cargos: ICargo[]
  loadingInstructionComplete: boolean;
  loadingSequenceComplete: boolean;
  loadingPlanComplete:boolean;
  OPERATIONS = OPERATIONS;
  errorMessage: IAlgoError[];
  disableGenerateLoadableButton: boolean = true;

  private ngUnsubscribe: Subject<any> = new Subject();
  errorPopUp: boolean = false;
  disableViewErrorButton: boolean = true;
  processing: boolean =true;

  @HostListener('window:beforeunload')
  canDeactivate(): Observable<boolean> | boolean {
    return !(this.loadingInstruction?.instructionCheckList?.hasUnsavedChanges || this.loadingInstruction?.instructionCheckList?.instructionForm?.dirty
      || this.loadingInformationComponent?.hasUnSavedData);
  }

  constructor(
    private activatedRoute: ActivatedRoute,
    private loadingDischargingTransformationService: LoadingDischargingTransformationService,
    private operationsApiService: OperationsApiService,
    private loadingApiService:LoadingApiService,
    private unsavedChangesGuard: UnsavedChangesGuard,
    private translateService: TranslateService,
    private messageService: MessageService,
    private globalErrorHandler:GlobalErrorHandler,
    private router:Router,
    private ngxSpinnerService: NgxSpinnerService
    ) {
  }



    ngOnInit(): void {
    this.initSubsciptions();
    this.getCargos();
    this.listenEvents();
    this.activatedRoute.paramMap
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(params => {
        this.vesselId = Number(params.get('vesselId'));
        this.voyageId = Number(params.get('voyageId'));
        this.portRotationId = Number(params.get('portRotationId'));
        localStorage.setItem("vesselId", this.vesselId.toString());
        localStorage.setItem("voyageId", this.voyageId.toString());
        this.selectedPortName = localStorage.getItem('selectedPortName');
        this.currentTab = OPERATION_TAB.INFORMATION;
        localStorage.setItem('rate_unit', RATE_UNIT.BBLS_PER_HR);
      });
  }

  ngOnDestroy() {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  /**
   * Initialise all subscription in this page
   *
   * @private
   * @memberof LoadingComponent
   */
  private async initSubsciptions() {
    this.loadingDischargingTransformationService.loadingInformationValidity$.subscribe((res) => {
      this.loadingInformationComplete = res;
      if (this.loadingInstructionComplete && this.loadingInstructionComplete) {
        this.disableGenerateLoadableButton = false;
      }
    });
    this.loadingDischargingTransformationService.loadingInstructionValidity$.subscribe((res) => {
      this.loadingInstructionComplete = res;
      if (this.loadingInstructionComplete && this.loadingInstructionComplete) {
        this.disableGenerateLoadableButton = false;
      }
    });
  }


  /**
* Method to get cargos
*
* @memberof LoadingComponent
*/
  async getCargos() {
    const cargos: ICargoResponseModel = await this.operationsApiService.getCargos().toPromise();
    if (cargos.responseStatus.status === '200') {
      this.cargos = cargos.cargos;
    }
  }


  /**
  * Method switch tab
  * @param tab
  * @memberof LoadingComponent
  */

  async switchTab(tab) {
    const value = await this.unsavedChangesGuard.canDeactivate(this);
    if (!value) { return };
    this.currentTab = tab;
    this.loadingDischargingTransformationService.setTabChange(tab);
  }

  /**
  * Method to set loading infoid
  * @param tab
  * @memberof LoadingComponent
  */

  setLoadingInfoId(data){
    this.loadingInfoId = data;
  }

  /**
   * Method to listen to events.
   *
   * @private
   * @memberof LoadingComponent
   */
  private listenEvents() {    
    navigator.serviceWorker.addEventListener('message', this.swMessageHandler);
  }


  /**
   * Method to set status messages based on api result.
   *
   * @private
   * @param {*} event
   * @memberof LoadingComponent
   */

  private swMessageHandler = async event => {
    this.ngxSpinnerService.hide();
    const translationKeys = await this.translateService.get(["GENERATE_LODABLE_PLAN_PENDING", "GENERATE_LODABLE_PLAN_NO_PLAN_AVAILABLE", "GENERATE_LODABLE_PLAN_CONFIRMED", "GENERATE_LODABLE_PLAN_ALGO_PROCESSING_STARTED", "GENERATE_LODABLE_PLAN_PLAN_GENERATED", "GENERATE_LODABLE_PLAN_ALGO_PROCESSING_COMPLETED", "GENERATE_LODABLE_PLAN_ERROR_OCCURED", "GENERATE_LODABLE_PLAN_VERIFICATION_WITH_LOADER", "GENERATE_LODABLE_PLAN_VERIFICATIOON_WITH_LOADER_COMPLETED",
      "GENERATE_LODABLE_PLAN_ALGO_VERIFICATION", "GENERATE_LODABLE_PLAN_ALGO_VERIFICATION_COMPLETED"]).toPromise();
    if (event?.data?.errorCode === '210') {
      this.globalErrorHandler.sessionOutMessage();
    }
    this.loadingSequenceComplete = this.loadingPlanComplete = (event?.data?.statusId === OPERATIONS_PLAN_STATUS.PLAN_GENERATED)
    if (event?.data.statusId === OPERATIONS_PLAN_STATUS.ERROR_OCCURED) {
      this.setButtonStatus();
      this.disableViewErrorButton = false;
      await this.getAlgoErrorMessage(true);
      this.messageService.add({ severity: 'error', summary: translationKeys['GENERATE_LOADABLE_PLAN_ERROR_OCCURED'], detail: translationKeys["GENERATE_LODABLE_PLAN_NO_PLAN_AVAILABLE"] });
    }
    else if (event?.data?.statusId === OPERATIONS_PLAN_STATUS.NO_PLAN_AVAILABLE) {
      this.setButtonStatus();
      this.messageService.clear();
      await this.getAlgoErrorMessage(true);
      this.messageService.add({ severity: 'error', summary: translationKeys['GENERATE_LOADABLE_PLAN_ERROR_OCCURED'], detail: translationKeys["GENERATE_LODABLE_PLAN_NO_PLAN_AVAILABLE"] });
    }
    else if (event?.data?.statusId === OPERATIONS_PLAN_STATUS.PLAN_GENERATED) {
      this.setButtonStatus();
      this.messageService.add({ severity: 'success', summary: translationKeys['GENERATE_LOADABLE_PLAN_COMPLETE_DONE'], detail: translationKeys["GENERATE_LODABLE_PLAN_PLAN_GENERATED"] });
    }

    if (this.router.url.includes('operations/loading')) {
     
      switch (event?.data?.statusId) {
        case OPERATIONS_PLAN_STATUS.PENDING:
          this.messageService.add({ severity: 'info', summary: translationKeys['GENERATE_LODABLE_PLAN_INFO'], detail: translationKeys["GENERATE_LODABLE_PLAN_PENDING"] });
          this.setButtonStatusInProcessing();
          break;
        case OPERATIONS_PLAN_STATUS.CONFIRMED:
          this.messageService.add({ severity: 'info', summary: translationKeys['GENERATE_LODABLE_PLAN_INFO'], detail: translationKeys["GENERATE_LODABLE_PLAN_CONFIRMED"] });
          this.setButtonStatusInProcessing();
          break;
        case OPERATIONS_PLAN_STATUS.PLAN_ALGO_PROCESSING_STARTED:
          this.messageService.add({ severity: 'info', summary: translationKeys['GENERATE_LODABLE_PLAN_INFO'], detail: translationKeys["GENERATE_LODABLE_PLAN_ALGO_PROCESSING_STARTED"] });
          this.setButtonStatusInProcessing();
          break;
        case OPERATIONS_PLAN_STATUS.VERIFICATION_WITH_LOADICATOR:
          this.messageService.add({ severity: 'info', summary: translationKeys['GENERATE_LODABLE_PLAN_INFO'], detail: translationKeys["GENERATE_LODABLE_PLAN_VERIFICATION_WITH_LOADER"] });
          this.setButtonStatusInProcessing();
          break;
        case OPERATIONS_PLAN_STATUS.VERFICATION_WITH_LOADICATOT_COMPLETED:
          this.messageService.add({ severity: 'info', summary: translationKeys['GENERATE_LODABLE_PLAN_INFO'], detail: translationKeys["GENERATE_LODABLE_PLAN_VERIFICATIOON_WITH_LOADER_COMPLETED"] });
          this.setButtonStatusInProcessing();
          break;
        case OPERATIONS_PLAN_STATUS.ALGO_PROCESSING_COMPLETED:
          this.messageService.add({ severity: 'info', summary: translationKeys['GENERATE_LODABLE_PLAN_INFO'], detail: translationKeys["GENERATE_LODABLE_PLAN_ALGO_PROCESSING_COMPLETED"] });
          this.setButtonStatusInProcessing();
          break;
        case  OPERATIONS_PLAN_STATUS.ERROR_OCCURED:
          this.messageService.add({ severity: 'error', summary: translationKeys['GENERATE_LODABLE_PLAN_INFO'], detail: translationKeys["GENERATE_LODABLE_PLAN_ERROR_OCCURED"] });
          this.setButtonStatusInProcessing();
          break;
        case  OPERATIONS_PLAN_STATUS.LOADICATOR_VERIFICATION_WITH_ALGO_COMPLETED:
          this.messageService.add({ severity: 'info', summary: translationKeys['GENERATE_LODABLE_PLAN_INFO'], detail: translationKeys["GENERATE_LODABLE_PLAN_ALGO_VERIFICATION_COMPLETED"] });
          this.setButtonStatusInProcessing();
          break;
      }

    }

  }
 
  /**
   *Method to set button status.
   *
   * @memberof LoadingComponent
   */
  setButtonStatus() {
    this.disableGenerateLoadableButton = false;
    this.processing = false;
    this.loadingDischargingTransformationService.disableSaveButton.next(false);
  }
  

  /**
   * Method to set button status in processing stage
   *
   * @memberof LoadingComponent
   */
  setButtonStatusInProcessing() {
    this.disableGenerateLoadableButton = true;
    this.processing = true;
  }
  /**
   * Method to call on generate loading plan
   *
   * @memberof LoadingComponent
   */
  async onGenerateLoadingPlan() {
    this.ngxSpinnerService.show();
    this.disableViewErrorButton = true;
    this.loadingDischargingTransformationService.disableSaveButton.next(true)
    let result = await this.loadingApiService.generateLoadingPlan(this.vesselId, this.voyageId, this.loadingInfoId).toPromise();
    const data = {
      processId: result.processId,
      vesselId: this.vesselId,
      voyageId: this.voyageId,
      loadingInfoId: this.loadingInfoId
    }
    if (result.responseStatus.status == "SUCCESS") {
      if (result.processId) {
        data.processId = result.processId;
        navigator.serviceWorker.controller.postMessage({ type: 'loading-plan-status', data });
      }
    }

  }

  
/**
 * Method to get algo error 
 *
 * @memberof LoadingComponent
 */
  async getAlgoErrorMessage(status) {
    const algoError: IAlgoResponse = await this.loadingApiService.getAlgoErrorDetails(this.vesselId, this.voyageId, this.loadingInfoId).toPromise();
    if (algoError.responseStatus.status === 'SUCCESS') {
      this.errorMessage = algoError.algoErrors;
      this.errorPopUp = status;
    }

  }

  /**
   * Method to display view error pop up.
   *
   * @memberof LoadingComponent
   */
  viewError(status) {
    this.errorPopUp = status;
  }

}

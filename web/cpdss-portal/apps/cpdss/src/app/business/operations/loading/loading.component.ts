import { Component, ComponentRef, HostListener, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subject, Observable } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { IAlgoError, IAlgoResponse, ICargo, ICargoResponseModel, OPERATIONS, OPERATIONS_PLAN_STATUS } from '../../core/models/common.model';
import { OPERATION_TAB } from '../models/operations.model';
import { LoadingInformationComponent } from './loading-information/loading-information.component';
import { UnsavedChangesGuard } from './../../../shared/services/guards/unsaved-data-guard';
import { LoadingDischargingTransformationService } from '../services/loading-discharging-transformation.service';
import { OperationsApiService } from '../services/operations-api.service';
import { TranslateService } from '@ngx-translate/core';
import { MessageService } from 'primeng/api';
import { GlobalErrorHandler } from '../../../shared/services/error-handlers/global-error-handler';
import { NgxSpinnerService } from 'ngx-spinner';
import { ComponentCanDeactivate, RATE_UNIT, PERMISSION_ACTION } from '../../../shared/models/common.model';
import { ULLAGE_STATUS_VALUE } from './../models/loading-discharging.model';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';
import { PermissionsService } from '../../../shared/services/permissions/permissions.service';
import { IPermission } from '../../../shared/models/user-profile.model';
import { SIMULATOR_REQUEST_TYPE } from '../../core/components/simulator/simulator.model';

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
  loadingPlanComplete: boolean;
  OPERATIONS = OPERATIONS;
  errorMessage: IAlgoError[];
  disableGenerateLoadableButton = true;
  loadinfoTemp: number;

  loadingInfoTabPermission: IPermission;
  loadingInstructionTabPermission: IPermission;
  loadingSequenceTabPermission: IPermission;
  loadingPlanTabPermission: IPermission;
  generatePlanPermission: IPermission;

  readonly SIMULATOR_REQUEST_TYPE = SIMULATOR_REQUEST_TYPE;

  private ngUnsubscribe: Subject<any> = new Subject();
  errorPopUp = false;
  disableViewErrorButton = true;
  processing = true;
  isDischargeStarted: boolean;

  @HostListener('window:beforeunload')
  canDeactivate(): Observable<boolean> | boolean {
    return !(this.loadingInstruction?.instructionCheckList?.hasUnsavedChanges || this.loadingInstruction?.instructionCheckList?.instructionForm?.dirty
      || this.loadingInformationComponent?.hasUnSavedData);
  }

  constructor(
    private activatedRoute: ActivatedRoute,
    private loadingDischargingTransformationService: LoadingDischargingTransformationService,
    private operationsApiService: OperationsApiService,
    private unsavedChangesGuard: UnsavedChangesGuard,
    private translateService: TranslateService,
    private messageService: MessageService,
    private globalErrorHandler: GlobalErrorHandler,
    private router: Router,
    private ngxSpinnerService: NgxSpinnerService,
    private permissionsService: PermissionsService
  ) { }

  ngOnInit(): void {
    this.ngxSpinnerService.show();
    this.initSubsciptions();
    this.setPagePermission();
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
        this.tabPermission();
        localStorage.setItem('rate_unit', RATE_UNIT.BBLS_PER_HR);
      });
  }

  /**
 * select tab permission
 * @memberof LoadingComponent
 */
  tabPermission() {
    if (this.loadingInfoTabPermission === undefined || this.loadingInfoTabPermission?.view) {
      this.currentTab = OPERATION_TAB.INFORMATION;
    } else if (this.loadingInstructionTabPermission?.view) {
      this.currentTab = OPERATION_TAB.INSTRUCTION;
    } else if (this.loadingSequenceTabPermission?.view) {
      this.currentTab = OPERATION_TAB.SEQUENCE;
    } else if (this.loadingPlanTabPermission?.view) {
      this.currentTab = OPERATION_TAB.PLAN;
    }
    this.loadingDischargingTransformationService.setTabChange(this.currentTab);
  }

  /**
  * Set page permission
  *
  * @memberof LoadingComponent
  */
  setPagePermission() {
    this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['LoadingComponent']);
    this.generatePlanPermission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['OperationLoadingGeneratePlan'], false);
    this.loadingInfoTabPermission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['OperationLoadingInformation'], false);
    this.loadingInstructionTabPermission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['OperationLoadingInstruction'], false);
    this.loadingSequenceTabPermission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['OperationLoadingSequence'], false);
    this.loadingPlanTabPermission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['OperationLoadingPlan'], false);
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

    this.ngxSpinnerService.show();
    this.loadingDischargingTransformationService.generateLoadingPlanButton.pipe(takeUntil(this.ngUnsubscribe)).subscribe((status) => {
      this.disableGenerateLoadableButton = status;
    })
    this.loadingDischargingTransformationService.isLoadingPlanGenerated.pipe(takeUntil(this.ngUnsubscribe)).subscribe((status) => {
      this.loadingPlanComplete = status;
      this.processing = !status;
      if (!this.processing)
        this.loadingDischargingTransformationService.disableInfoInstructionRuleSave.next(false)
    })
    this.loadingDischargingTransformationService.isLoadingSequenceGenerated.pipe(takeUntil(this.ngUnsubscribe)).subscribe((status) => {
      this.loadingSequenceComplete = status;
      this.processing = !status;
      if (!this.processing)
        this.loadingDischargingTransformationService.disableInfoInstructionRuleSave.next(false)
    })

    this.loadingDischargingTransformationService.validateUllageData$.pipe(takeUntil(this.ngUnsubscribe)).subscribe((res) => {
      if (res?.validate) {
        this.validateUllage(res);
      }
    });

    this.loadingDischargingTransformationService.loadingInformationValidity$.pipe(takeUntil(this.ngUnsubscribe)).subscribe((res) => {
      this.loadingInformationComplete = res;
      if (!this.processing) {
        this.loadingDischargingTransformationService.disableInfoInstructionRuleSave.next(false)
        if (this.loadingInstructionComplete && this.loadingInformationComplete) {
          this.loadingDischargingTransformationService.generateLoadingPlanButton.next(false);
        } else {
          this.loadingDischargingTransformationService.generateLoadingPlanButton.next(true);
        }
      }

    });
    this.loadingDischargingTransformationService.inProcessing.pipe(takeUntil(this.ngUnsubscribe)).subscribe((status) => {
      this.processing = status;
    })
    this.loadingDischargingTransformationService.loadingInstructionValidity$.pipe(takeUntil(this.ngUnsubscribe)).subscribe((res) => {
      this.loadingInstructionComplete = res;
      if (!this.processing) {
        this.loadingDischargingTransformationService.disableInfoInstructionRuleSave.next(false)
        if (this.loadingInstructionComplete && this.loadingInformationComplete) {
          this.loadingDischargingTransformationService.generateLoadingPlanButton.next(false);
        }
        else {
          this.loadingDischargingTransformationService.generateLoadingPlanButton.next(true);
        }
      }

    });
    this.loadingDischargingTransformationService.disableViewErrorButton.pipe(takeUntil(this.ngUnsubscribe)).subscribe((status) => {
      this.disableViewErrorButton = status;
    })
    this.loadingDischargingTransformationService.isDischargeStarted$.pipe(takeUntil(this.ngUnsubscribe)).subscribe((value) => {
      this.isDischargeStarted = value;
    });

    this.loadingDischargingTransformationService.setUllageDepartureBtnStatus$.pipe(takeUntil(this.ngUnsubscribe)).subscribe((value) => {
      if (value && value.status === ULLAGE_STATUS_VALUE.SUCCESS) {
        this.disableGenerateLoadableButton = true;
      }
    });
    this.ngxSpinnerService.hide();

  }

  /**
   * validate ullage values
   *
   * @memberof LoadingComponent
   */
  validateUllage(value) {
    const data = {
      processId: value.processId,
      vesselId: this.vesselId,
      voyageId: this.voyageId,
      loadingInfoId: this.loadingInfoId,
      type: 'ullage-update-status',
      status: value.status,
      portRotationId: this.portRotationId

    }
    navigator.serviceWorker.controller.postMessage({ type: 'ullage-update-status', data });

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

  setLoadingInfoId(data) {
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
    const translationKeys = await this.translateService.get(["GENERATE_LODABLE_PLAN_PENDING", "GENERATE_LODABLE_PLAN_NO_PLAN_AVAILABLE", "GENERATE_LODABLE_PLAN_CONFIRMED", "GENERATE_LODABLE_PLAN_ALGO_PROCESSING_STARTED", "GENERATE_LODABLE_PLAN_PLAN_GENERATED", "GENERATE_LODABLE_PLAN_ALGO_PROCESSING_COMPLETED", "GENERATE_LODABLE_PLAN_ERROR_OCCURED", "GENERATE_LODABLE_PLAN_VERIFICATION_WITH_LOADER", "GENERATE_LODABLE_PLAN_VERIFICATIOON_WITH_LOADER_COMPLETED",
      "GENERATE_LODABLE_PLAN_ALGO_VERIFICATION", "GENERATE_LODABLE_PLAN_ALGO_VERIFICATION_COMPLETED", "GENERATE_LODABLE_PLAN_INFO", "GENERATE_LOADABLE_PATTERN_NO_PLAN", "GENERATE_LOADABLE_PLAN_COMPLETE_DONE", "LOADING_PLAN_GENERATION_COMMUNICATED_TO_SHORE", "ULLAGE_UPDATE_PLAN_COMMUNICATED_TO_SHORE", "ULLAGE_UPDATE_VALIDATION_SUCCESS_LABEL", "Port"]).toPromise();
    if (event?.data?.errorCode === '210') {
      this.globalErrorHandler.sessionOutMessage();
    }
    if (event?.data?.pattern?.type === 'loading-plan-status') {
      this.loadinfoTemp = event?.data?.pattern?.loadingInfoId
      if (this.loadingInfoId === this.loadinfoTemp) {
        this.loadingDischargingTransformationService.isLoadingSequenceGenerated.next(event?.data?.statusId === OPERATIONS_PLAN_STATUS.PLAN_GENERATED);
        this.loadingDischargingTransformationService.isLoadingPlanGenerated.next(event?.data?.statusId === OPERATIONS_PLAN_STATUS.PLAN_GENERATED);
      }
      this.ngxSpinnerService.hide();

      if (event?.data.statusId === OPERATIONS_PLAN_STATUS.ERROR_OCCURED) {
        this.setButtonStatus(true);

        await this.getAlgoErrorMessage(true);
        this.messageService.add({ severity: 'error', summary: translationKeys['GENERATE_LOADABLE_PATTERN_NO_PLAN'], detail: translationKeys["GENERATE_LODABLE_PLAN_ERROR_OCCURED"] });
      }
      else if (event?.data?.statusId === OPERATIONS_PLAN_STATUS.NO_PLAN_AVAILABLE) {
        this.setButtonStatus(true);
        this.messageService.clear();
        await this.getAlgoErrorMessage(true);
        this.messageService.add({ severity: 'error', summary: translationKeys['GENERATE_LOADABLE_PATTERN_NO_PLAN'], detail: translationKeys["GENERATE_LODABLE_PLAN_NO_PLAN_AVAILABLE"] });
      }
      else if (event?.data?.statusId === OPERATIONS_PLAN_STATUS.PLAN_GENERATED) {
        this.setButtonStatus();
        this.messageService.add({ severity: 'success', summary: translationKeys['GENERATE_LOADABLE_PLAN_COMPLETE_DONE'], detail: event?.data?.pattern?.portName + ' ' + translationKeys["Port"]?.toLowerCase() + ' ' + translationKeys["GENERATE_LODABLE_PLAN_PLAN_GENERATED"], sticky: true, closable: true });

      }

      if (this.router.url.includes('operations/loading') && this.loadingDischargingTransformationService.portRotationId === event?.data?.pattern?.portRotationId) {

        switch (event?.data?.statusId) {
          case OPERATIONS_PLAN_STATUS.PENDING:
            this.messageService.add({ severity: 'info', summary: translationKeys['GENERATE_LODABLE_PLAN_INFO'], detail: translationKeys["GENERATE_LODABLE_PLAN_PENDING"], closable: false });
            this.setButtonStatusInProcessing();
            break;
          case OPERATIONS_PLAN_STATUS.CONFIRMED:
            this.messageService.add({ severity: 'info', summary: translationKeys['GENERATE_LODABLE_PLAN_INFO'], detail: translationKeys["GENERATE_LODABLE_PLAN_CONFIRMED"], closable: false });
            this.setButtonStatusInProcessing();
            break;
          case OPERATIONS_PLAN_STATUS.PLAN_ALGO_PROCESSING_STARTED:
            this.messageService.add({ severity: 'info', summary: translationKeys['GENERATE_LODABLE_PLAN_INFO'], detail: translationKeys["GENERATE_LODABLE_PLAN_ALGO_PROCESSING_STARTED"], closable: false });
            this.setButtonStatusInProcessing();
            break;
          case OPERATIONS_PLAN_STATUS.VERIFICATION_WITH_LOADICATOR:
            this.messageService.add({ severity: 'info', summary: translationKeys['GENERATE_LODABLE_PLAN_INFO'], detail: translationKeys["GENERATE_LODABLE_PLAN_VERIFICATION_WITH_LOADER"], closable: false });
            this.setButtonStatusInProcessing();
            break;
          case OPERATIONS_PLAN_STATUS.VERFICATION_WITH_LOADICATOT_COMPLETED:
            this.messageService.add({ severity: 'info', summary: translationKeys['GENERATE_LODABLE_PLAN_INFO'], detail: translationKeys["GENERATE_LODABLE_PLAN_VERIFICATIOON_WITH_LOADER_COMPLETED"], closable: false });
            this.setButtonStatusInProcessing();
            break;
          case OPERATIONS_PLAN_STATUS.ALGO_PROCESSING_COMPLETED:
            this.messageService.add({ severity: 'info', summary: translationKeys['GENERATE_LODABLE_PLAN_INFO'], detail: translationKeys["GENERATE_LODABLE_PLAN_ALGO_PROCESSING_COMPLETED"], closable: false });
            this.setButtonStatusInProcessing();
            break;

          case OPERATIONS_PLAN_STATUS.LOADICATOR_VERIFICATION_WITH_ALGO_COMPLETED:
            this.messageService.add({ severity: 'info', summary: translationKeys['GENERATE_LODABLE_PLAN_INFO'], detail: translationKeys["GENERATE_LODABLE_PLAN_ALGO_VERIFICATION_COMPLETED"], closable: false });
            this.setButtonStatusInProcessing();
            break;
          case OPERATIONS_PLAN_STATUS.COMMUNICATED_TO_SHORE:
            this.messageService.add({ severity: 'info', summary: translationKeys['GENERATE_LODABLE_PLAN_INFO'], detail: translationKeys["LOADING_PLAN_GENERATION_COMMUNICATED_TO_SHORE"], closable: false });
            this.setButtonStatusInProcessing();
            break;
        }

      }
    }
    if (event?.data?.pattern?.type === 'ullage-update-status') {
      if (event?.data.statusId === ULLAGE_STATUS_VALUE.ERROR) {
        const errorStatus = {value: true, status: event?.data?.pattern?.status};
        this.loadingDischargingTransformationService.showUllageError(errorStatus);
      } else if (event?.data?.statusId === ULLAGE_STATUS_VALUE.SUCCESS) {
        this.messageService.add({ severity: 'success', summary: translationKeys['GENERATE_LOADABLE_PLAN_COMPLETE_DONE'], detail: translationKeys["ULLAGE_UPDATE_VALIDATION_SUCCESS_LABEL"] });
      }

      if (this.router.url.includes('operations/loading') && this.loadingDischargingTransformationService.portRotationId === event?.data?.pattern?.portRotationId) {

        switch (event?.data?.statusId) {
          case ULLAGE_STATUS_VALUE.IN_PROGRESS:
            this.messageService.add({ severity: 'info', summary: translationKeys['GENERATE_LODABLE_PLAN_INFO'], detail: translationKeys["GENERATE_LODABLE_PLAN_ALGO_PROCESSING_STARTED"] });
            break;
          case ULLAGE_STATUS_VALUE.LOADICATOR_IN_PROGRESS:
            this.messageService.add({ severity: 'info', summary: translationKeys['GENERATE_LODABLE_PLAN_INFO'], detail: translationKeys["GENERATE_LODABLE_PLAN_VERIFICATION_WITH_LOADER"] });
            break;
          case ULLAGE_STATUS_VALUE.COMMUNICATED_TO_SHORE:
            this.messageService.add({ severity: 'info', summary: translationKeys['GENERATE_LODABLE_PLAN_INFO'], detail: translationKeys["ULLAGE_UPDATE_PLAN_COMMUNICATED_TO_SHORE"] });
            break;

        }

      }
      if (event?.data?.pattern?.status === 1) {
        this.loadingDischargingTransformationService.setUllageArrivalBtnStatus({ status: event?.data.statusId, portRotationId: event?.data?.pattern?.portRotationId });
      }
      if (event?.data?.pattern?.status === 2) {
        this.loadingDischargingTransformationService.setUllageDepartureBtnStatus({ status: event?.data.statusId, portRotationId: event?.data?.pattern?.portRotationId });
      }
    }

  }

  /**
   *Method to set button status.
   *
   * @memberof LoadingComponent
   */
  setButtonStatus(error?) {
    if (this.loadinfoTemp === this.loadingInfoId) {
      this.loadingDischargingTransformationService.generateLoadingPlanButton.next(false)
      this.processing = false;
      this.loadingDischargingTransformationService.disableInfoInstructionRuleSave.next(false);
      if (error) {
        this.loadingDischargingTransformationService.disableViewErrorButton.next(false)
      }
      else {
        this.loadingDischargingTransformationService.disableViewErrorButton.next(true)

      }
      this.ngxSpinnerService.hide();

    }


  }


  /**
   * Method to set button status in processing stage
   *
   * @memberof LoadingComponent
   */
  setButtonStatusInProcessing() {
    if (this.loadingInfoId === this.loadinfoTemp) {
      this.loadingDischargingTransformationService.generateLoadingPlanButton.next(true)
      this.processing = true;
    }
    this.ngxSpinnerService.hide();

  }

  /**
   * Method to call on generate loading plan
   *
   * @memberof LoadingComponent
   */
  async onGenerateLoadingPlan() {
    const value = await this.unsavedChangesGuard.canDeactivate(this);
    if (!value) { return };
    this.ngxSpinnerService.show();
    this.loadingDischargingTransformationService.inProcessing.next(true);
    this.loadingDischargingTransformationService.disableInfoInstructionRuleSave.next(true);
    this.loadingDischargingTransformationService.generateLoadingPlanButton.next(true);
    this.loadingDischargingTransformationService.disableViewErrorButton.next(true);
    const result = await this.operationsApiService.generateLoadingPlan(this.vesselId, this.voyageId, this.loadingInfoId).toPromise();
    const data = {
      processId: result.processId,
      vesselId: this.vesselId,
      voyageId: this.voyageId,
      loadingInfoId: this.loadingInfoId,
      portRotationId: this.portRotationId,
      portName: localStorage.getItem('selectedPortName'),
      type: 'loading-plan-status'
    }
    if (result.responseStatus.status === "SUCCESS") {
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
    this.ngxSpinnerService.show();
    if ((this.loadinfoTemp === this.loadingInfoId) || (!this.loadinfoTemp)) {
      const algoError: IAlgoResponse = await this.operationsApiService.getAlgoErrorDetails(this.vesselId, this.voyageId, this.loadingInfoId).toPromise();
      if (algoError.responseStatus.status === 'SUCCESS') {
        this.ngxSpinnerService.hide();
        this.errorMessage = algoError.algoErrors;
        this.errorPopUp = status;
      }
    }
  }

  /**
   * Method to display view error pop up.
   *
   * @memberof LoadingComponent
   */
  async viewError(status) {
    await this.getAlgoErrorMessage(true);
    this.errorPopUp = status;
  }

  /**
   * Method to check loading info is valid
   *
   * @memberof LoadingComponent
   */
  isLoadingInfoValid() {
    if (this.currentTab === OPERATION_TAB.INFORMATION && this.loadingInformationComponent?.loadingInformationData) {
      return (this.loadingInformationComponent.isLoadingInfoValid() &&
      this.loadingDischargingTransformationService.isMachineryValid && this.loadingDischargingTransformationService.isCargoAdded);
    } else {
      return this.loadingInformationComplete;
    }
  }

}

import { Component, OnInit } from '@angular/core';
import { DecimalPipe } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { switchMap } from 'rxjs/operators';

import { NgxSpinnerService } from 'ngx-spinner';

import { VesselsApiService } from '../../core/services/vessels-api.service';
import { IVessel } from '../../core/models/vessel-details.model';
import { IBallastTank, ICargoTank, Voyage, VOYAGE_STATUS, LOADABLE_STUDY_STATUS, IBallastStowageDetails } from '../../core/models/common.model';
import { LoadablePlanApiService } from '../services/loadable-plan-api.service';
import { ICargoTankDetailValueObject, ILoadablePlanResponse, ILoadableQuantityCommingleCargo, IAlgoError , IloadableQuantityCargoDetails , ILoadablePlanCommentsDetails , VALIDATION_AND_SAVE_STATUS , IAlgoResponse } from '../models/loadable-plan.model';
import { LoadablePlanTransformationService } from '../services/loadable-plan-transformation.service';
import { ICargoResponseModel, ICargo, ITimeZone , ISubTotal } from '../../../shared/models/common.model';
import { VoyageService } from '../../core/services/voyage.service';
import { LoadableStudy } from '../models/loadable-study-list.model';
import { LoadableStudyListApiService } from '../services/loadable-study-list-api.service';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';
import { PermissionsService } from '../../../shared/services/permissions/permissions.service';
import { ConfirmationService, MessageService } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';
import { ILoadablePlanSynopticalRecord, ILoadableQuantityCargo } from '../models/cargo-planning.model';
import { TimeZoneTransformationService } from '../../../shared/services/time-zone-conversion/time-zone-transformation.service';
import { IDateTimeFormatOptions } from './../../../shared/models/common.model';
import { saveAs } from 'file-saver';

/**
 * Component class of loadable plan
 *
 * @export
 * @class LoadablePlanComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-loadable-plan',
  templateUrl: './loadable-plan.component.html',
  styleUrls: ['./loadable-plan.component.scss']
})
export class LoadablePlanComponent implements OnInit  {

  get cargoTanks(): ICargoTank[][] {
    return this._cargoTanks;
  }
  set cargoTanks(tanks: ICargoTank[][]) {
    this._cargoTanks = tanks?.map(group => group.map(tank => this.loadablePlanTransformationService.formatCargoTanks(tank, this.cargoTankDetails)));
  }

  get cargoTankDetails(): ICargoTankDetailValueObject[] {
    return this._cargoTankDetails;
  }
  set cargoTankDetails(value: ICargoTankDetailValueObject[]) {
    this._cargoTankDetails = value;
  }

  get rearBallastTanks(): IBallastTank[][] {
    return this._rearBallastTanks;
  }
  set rearBallastTanks(tanks: IBallastTank[][]) {
    this._rearBallastTanks = tanks?.map(group => group.map(tank => this.loadablePlanTransformationService.formatBallastTanks(tank, this.loadablePlanBallastDetails)));
  }

  get centerBallastTanks(): IBallastTank[][] {
    return this._centerBallastTanks;
  }
  set centerBallastTanks(tanks: IBallastTank[][]) {
    this._centerBallastTanks = tanks?.map(group => group.map(tank => this.loadablePlanTransformationService.formatBallastTanks(tank, this.loadablePlanBallastDetails)));
  }

  get frontBallastTanks(): IBallastTank[][] {
    return this._frontBallastTanks;
  }
  set frontBallastTanks(tanks: IBallastTank[][]) {
    this._frontBallastTanks = tanks?.map(group => group.map(tank => this.loadablePlanTransformationService.formatBallastTanks(tank, this.loadablePlanBallastDetails)));
  }

  readonly validateAndSaveStatus = VALIDATION_AND_SAVE_STATUS;
  voyageId: number;
  loadableStudyId: number;
  loadableStudy: LoadableStudy;
  vesselId: number;
  loadablePatternId: number;
  vesselInfo: IVessel;
  loadableQuantityCargoDetails: ILoadableQuantityCargo[];
  loadableQuantityCommingleCargoDetails: ILoadableQuantityCommingleCargo[];
  loadablePlanBallastDetails: IBallastStowageDetails[];
  selectedVoyage: Voyage;
  confirmPlanEligibility: boolean;
  LOADABLE_STUDY_STATUS = LOADABLE_STUDY_STATUS;
  VOYAGE_STATUS = VOYAGE_STATUS;
  showPortRotationPopup = false;
  public loadablePlanSynopticalRecords: ILoadablePlanSynopticalRecord[];
  public loadablePlanComments: ILoadablePlanCommentsDetails[];
  public voyageNumber: string;
  public date: string;
  public caseNumber: string;
  public cargos: ICargo[];
  public confirmPlanPermission: boolean;
  public loadableStudyStatus: boolean;
  public loadablePatternValidationStatus: number;
  public confirmButtonStatus: boolean;
  public isVoyageClosed: boolean;
  public errorMessage: IAlgoError[];
  public errorPopup: boolean;
  public validationPending: boolean;
  timeZoneList: ITimeZone[];
  public loadableQuantity: number;
  loadableQuantityCargo: IloadableQuantityCargoDetails[];
  portRotationId: number;

  private _cargoTanks: ICargoTank[][];
  private _cargoTankDetails: ICargoTankDetailValueObject[] = [];
  private _rearBallastTanks: IBallastTank[][];
  private _centerBallastTanks: IBallastTank[][];
  private _frontBallastTanks: IBallastTank[][];

  constructor(private ngxSpinnerService: NgxSpinnerService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private vesselsApiService: VesselsApiService,
    private loadablePlanApiService: LoadablePlanApiService,
    private loadablePlanTransformationService: LoadablePlanTransformationService,
    private _decimalPipe: DecimalPipe,
    private confirmationService: ConfirmationService,
    private voyageService: VoyageService,
    private loadableStudyListApiService: LoadableStudyListApiService,
    private permissionsService: PermissionsService,
    private timeZoneTransformationService: TimeZoneTransformationService,
    private messageService: MessageService,
    private translateService: TranslateService
  ) { }

  /**
    * Component Lifecycle hook OnInit
    *
    * @memberof LoadablePlanComponent
  */
  ngOnInit(): void {
    this.getPagePermission();
    this.activatedRoute.paramMap.subscribe(params => {
      this.vesselId = Number(params.get('vesselId'));
      this.voyageId = Number(params.get('voyageId'));
      this.loadableStudyId = Number(params.get('loadableStudyId'));
      this.loadablePatternId = Number(params.get('loadablePatternId'))
      localStorage.setItem("vesselId",this.vesselId.toString())
      localStorage.setItem("voyageId",this.voyageId.toString())
      localStorage.setItem("loadableStudyId",this.loadableStudyId.toString())
      localStorage.setItem("loadablePatternId",this.loadablePatternId.toString())
      this.getCargos()
      this.getVesselInfo();
      this.initSubsciptions();
      this.getGlobalTimeZones();
      this.getVoyages(this.vesselId, this.voyageId);
      this.getLoadableStudies(this.vesselId, this.voyageId, this.loadableStudyId);
    });

  }

  /**
   * Listen events in this page
   *
   * @private
   * @memberof LoadablePlanComponent
   */
  private async listenEvents() {
    navigator.serviceWorker.addEventListener('message', this.swMessageHandler);
  }

    /**
   * Handler for service worker message event
   *
   * @private
   * @memberof LoadablePlanComponent
   */
  private swMessageHandler = async event => {
    if (event.data.type === 'loadable-pattern-validation-started' && this.router.url.includes('loadable-plan')) {
      if (event.data.pattern?.loadablePatternId === this.loadablePatternId) {
        this.processingMessage();
        this.loadablePatternValidationStatus = VALIDATION_AND_SAVE_STATUS.LOADABLE_PLAN_STARTED;
        this.validationPending = false;
        this.loadablePlanTransformationService.ballastEditStatus({validateAndSaveProcessing: false});
      } else {
        this.messageService.clear("process");
      }
    } else if (event.data.type === 'loadable-pattern-validation-failed') {
      if (event.data.pattern?.loadablePatternId === this.loadablePatternId) {
        navigator.serviceWorker.removeEventListener('message', this.swMessageHandler);
        this.validationFailed();
        this.loadablePatternValidationStatus = VALIDATION_AND_SAVE_STATUS.LOADABLE_PLAN_FAILED;
        this.loadablePlanTransformationService.ballastEditStatus({validateAndSaveProcessing: false});
        this.validationPending = false;
        this.getAlgoErrorMessage(true);
      }

    } else if (event.data.type === 'loadable-pattern-validation-success') {
      if (event.data.pattern?.loadablePatternId === this.loadablePatternId) {
        navigator.serviceWorker.removeEventListener('message', this.swMessageHandler);
        this.validationCompleted();
        this.getLoadablePlanDetails();
        this.validationPending = false;
        this.loadablePlanTransformationService.ballastEditStatus({validateAndSaveProcessing: false});
        this.loadablePatternValidationStatus = VALIDATION_AND_SAVE_STATUS.LOADABLE_PLAN_SUCCESS;
      }
    }
    this.setProcessingLoadableStudyActions();
  }

    /**
   * Enable/ Disable buttons of currently processing/processed loadable pattern
   * @memberof LoadablePlanComponent
   */
   setProcessingLoadableStudyActions() {
     if([VALIDATION_AND_SAVE_STATUS.LOADABLE_PLAN_STARTED,VALIDATION_AND_SAVE_STATUS.LOADABLE_PLAN_FAILED].includes(this.loadablePatternValidationStatus)) {
       this.confirmButtonStatus = true;
     } else {
      this.confirmButtonStatus = false;
     }
   }

  /**
   * Toast to show validating pattern started
   *
   * @memberof LoadablePlanComponent
  */
  async processingMessage() {
    this.messageService.clear("process");
    const translationKeys = await this.translateService.get(['LOADABLE_PATTERN_VALIDATION_INFO', 'LOADABLE_PATTERN_VALIDATION_STARTED']).toPromise();
    this.messageService.add({ severity: 'info', summary: translationKeys['LOADABLE_PATTERN_VALIDATION_INFO'], detail: translationKeys['LOADABLE_PATTERN_VALIDATION_STARTED'], life: 1000, key: "process", closable: false });
  }

  /**
 * Toast to show validating pattern completed
 *
 * @memberof LoadablePlanComponent
 */
  async validationCompleted() {
    const translationKeys = await this.translateService.get(['LOADABLE_PATTERN_VALIDATION_SUCCESS', 'LOADABLE_PATTERN_VALIDATION_SUCCESSFULLY']).toPromise();
    this.messageService.add({ severity: 'success', summary: translationKeys['LOADABLE_PATTERN_VALIDATION_SUCCESS'], detail: translationKeys['LOADABLE_PATTERN_VALIDATION_SUCCESSFULLY']});
  }

  /**
  * Toast to show validating pattern error
  *
  * @memberof LoadablePlanComponent
  */
  async validationFailed() {
    const translationKeys = await this.translateService.get(['LOADABLE_PATTERN_VALIDATION_ERROR', 'LOADABLE_PATTERN_VALIDATION_ERROR_DETAILS']).toPromise();
    this.messageService.add({ severity: 'error', summary: translationKeys['LOADABLE_PATTERN_VALIDATION_ERROR'], detail:  translationKeys['LOADABLE_PATTERN_VALIDATION_ERROR_DETAILS']});
  }


  /**
* Get page permission
*
* @memberof LoadablePlanComponent
*/
  getPagePermission() {
    this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['LoadablePlanComponent']);
    this.confirmPlanPermission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['ConfirmLoadablePlan'], false).view;
  }


  /**
    * Initialise all subscription in this page
    *
    * @memberof LoadablePlanComponent
  */
  async initSubsciptions() {
    this.loadablePlanTransformationService.savedComments$.pipe(switchMap(() => {
      return this.loadablePlanApiService.getLoadablePlanDetails(this.vesselId, this.voyageId, this.loadableStudyId, this.loadablePatternId).toPromise();;
    })).subscribe((loadablePlanRes) => {
      if (loadablePlanRes.responseStatus.status === '200') {
        this.loadablePlanComments = loadablePlanRes.loadablePlanComments;
      }
    })
  }

  /**
    * Method to get cargos
    *
    * @memberof LoadablePlanComponent
  */
  async getCargos() {
    this.ngxSpinnerService.show();
    const cargos: ICargoResponseModel = await this.loadablePlanApiService.getCargos().toPromise();
    this.ngxSpinnerService.hide();
    if (cargos.responseStatus.status === '200') {
      this.cargos = cargos.cargos;
      this.getLoadablePlanDetails();
    }
  }

  /**
    * Method to fetch all vessel info
    *
    * @memberof LoadablePlanComponent
    */
  async getVesselInfo() {
    this.ngxSpinnerService.show();
    const res = await this.vesselsApiService.getVesselsInfo().toPromise();
    this.vesselInfo = res[0] ?? <IVessel>{};
    this.ngxSpinnerService.hide();
  }

  /**
   * Method to fetch all voyages in the vessel
   *
   * @param {number} vesselId
   * @param {number} voyageId
   * @memberof LoadablePlanComponent
   */
  async getVoyages(vesselId: number, voyageId: number) {
    this.ngxSpinnerService.show();
    const result = await this.voyageService.getVoyagesByVesselId(vesselId).toPromise();
    const voyages = result ?? [];
    this.selectedVoyage = voyages.find(voyage => voyage.id === voyageId);
    this.ngxSpinnerService.hide();
  }

  /**
   * Method to fetch all loadable studies
   *
   * @param {number} vesselId
   * @param {number} voyageId
   * @param {number} loadableStudyId
   * @memberof LoadablePlanComponent
   */
  async getLoadableStudies(vesselId: number, voyageId: number, loadableStudyId: number) {
    this.ngxSpinnerService.show();
    const result = await this.loadableStudyListApiService.getLoadableStudies(vesselId, voyageId).toPromise();
    this.loadableStudy = result?.loadableStudies?.find(loadableStudy => loadableStudy.id === loadableStudyId);
    this.ngxSpinnerService.hide();
  }


  /**
  * Method to back to loadable study
  *
  * @memberof LoadablePlanComponent
  */
  async backToLoadableStudy() {
    this.router.navigate([`/business/cargo-planning/loadable-study-details/${this.vesselId}/${this.voyageId}/${this.loadableStudyId}`]);
  }

  /**
   * function list golbal time zones
   *
   * @memberof LoadablePlanComponent
   */
  async getGlobalTimeZones() {
    this.timeZoneList = await this.timeZoneTransformationService.getTimeZoneList().toPromise();
  }

  /**
  * Get details for loadable Plan
  * @returns {Promise<ILoadablePlanResponse>}
  * @memberof LoadablePlanComponent
  */
  private async getLoadablePlanDetails() {
    this.loadableQuantityCargo = [];
    this.ngxSpinnerService.show();
    const loadablePlanRes: ILoadablePlanResponse = await this.loadablePlanApiService.getLoadablePlanDetails(this.vesselId, this.voyageId, this.loadableStudyId, this.loadablePatternId).toPromise();
    this.confirmPlanEligibility = loadablePlanRes?.confirmPlanEligibility;
    this.loadableQuantityCargoDetails = loadablePlanRes.loadableQuantityCargoDetails;
    this.portRotationId = loadablePlanRes.lastModifiedPort;
    this.loadableQuantityCargoDetails.map((loadableQuantityCargoDetail) => {
      loadableQuantityCargoDetail['grade'] = this.fingCargo(loadableQuantityCargoDetail);
      const minTolerence = (Number(loadableQuantityCargoDetail.minTolerence)/100)* Number(loadableQuantityCargoDetail.orderedQuantity) + Number(loadableQuantityCargoDetail.orderedQuantity);
      const maxTolerence = (Number(loadableQuantityCargoDetail.maxTolerence)/100)* Number(loadableQuantityCargoDetail.orderedQuantity) + Number(loadableQuantityCargoDetail.orderedQuantity);
      this.loadableQuantityCargo.push({'cargoAbbreviation': loadableQuantityCargoDetail.cargoAbbreviation, total: 0 , minTolerence: minTolerence , maxTolerence: maxTolerence})
    })
    loadablePlanRes.loadableQuantity ? this.loadableQuantity = Number(loadablePlanRes.loadableQuantity) : this.getLoadableQuantity();
    this.loadableQuantityCommingleCargoDetails = loadablePlanRes.loadableQuantityCommingleCargoDetails;
    this.cargoTankDetails = loadablePlanRes?.loadablePlanStowageDetails ? loadablePlanRes?.loadablePlanStowageDetails?.map(cargo => {
      const tank = this.findCargoTank(cargo.tankId, loadablePlanRes?.tankLists)
      cargo.fullCapacityCubm = tank.fullCapacityCubm
      const formattedCargo = this.loadablePlanTransformationService.getFormatedCargoDetails(cargo)
      return this.loadablePlanTransformationService.getCargoTankDetailAsValueObject(formattedCargo)
    }) : [];
    this.cargoTanks = loadablePlanRes?.tankLists;
    this.loadablePlanBallastDetails = loadablePlanRes.loadablePlanBallastDetails?.map(ballast => {
      const tank = this.findBallastTank(ballast.tankId, [loadablePlanRes.frontBallastTanks, loadablePlanRes.rearBallastTanks, loadablePlanRes.centerBallastTanks])
      ballast.fullCapacityCubm = tank?.fullCapacityCubm
      const formattedCargo = this.loadablePlanTransformationService.getFormattedBallastDetails(this._decimalPipe, ballast)
      return formattedCargo
    }) ?? [];;
    this.frontBallastTanks = loadablePlanRes.frontBallastTanks;
    this.rearBallastTanks = loadablePlanRes.rearBallastTanks;
    this.centerBallastTanks = loadablePlanRes.centerBallastTanks;
    this.loadablePlanSynopticalRecords = this.convertLoadablePlanSynRecordsDateTime(loadablePlanRes.loadablePlanSynopticalRecords);
    this.loadablePlanComments = loadablePlanRes.loadablePlanComments;
    this.voyageNumber = loadablePlanRes.voyageNumber;
    this.date = loadablePlanRes.date;
    this.caseNumber = loadablePlanRes.caseNumber;
    this.isVoyageClosed = loadablePlanRes.voyageStatusId === 2 ? true : false;
    this.loadablePatternValidationStatus = loadablePlanRes.loadablePatternStatusId;
    if(this.loadablePatternValidationStatus === VALIDATION_AND_SAVE_STATUS.LOADABLE_PLAN_FAILED) {
      this.getAlgoErrorMessage(false);
    } else if(this.loadablePatternValidationStatus === VALIDATION_AND_SAVE_STATUS.LOADABLE_PLAN_STARTED) {
      this.listenEvents();
    }

    loadablePlanRes.loadableStudyStatusId === 2 ? this.loadableStudyStatus = true : this.loadableStudyStatus = false;
    this.setProcessingLoadableStudyActions();
    this.validationPending = !loadablePlanRes.validated;
    this.ngxSpinnerService.hide();
  }

  /**
  * Calculation for subtotal
  * @memberof LoadablePlanComponent
  */
  async getLoadableQuantity() {
    const loadableQuantityResult = await this.loadablePlanApiService.getLoadableQuantity(this.vesselId, this.voyageId, this.loadableStudyId, this.portRotationId ? this.portRotationId : 0).toPromise();
    if (loadableQuantityResult.responseStatus.status === "200") {
      loadableQuantityResult.loadableQuantity.totalQuantity === '' ? this.getSubTotal(loadableQuantityResult) : this.loadableQuantity = Number(loadableQuantityResult.loadableQuantity.totalQuantity);
    }
  }

  /**
  * Calculation for subtotal
  * @memberof LoadablePlanComponent
  */
   getSubTotal(loadableQuantityResult: any) {
    const loadableQuantity = loadableQuantityResult.loadableQuantity;
    let subTotal = 0;
    if (loadableQuantityResult.caseNo === 1 || loadableQuantityResult.caseNo === 2) {
      const data: ISubTotal = {
        dwt: loadableQuantity.dwt,
        sagCorrection: loadableQuantity.saggingDeduction,
        foOnboard: loadableQuantity.estFOOnBoard,
        doOnboard: loadableQuantity.estDOOnBoard,
        freshWaterOnboard: loadableQuantity.estFreshWaterOnBoard,
        boilerWaterOnboard: loadableQuantity.boilerWaterOnBoard,
        ballast: loadableQuantity.ballast,
        constant: loadableQuantity.constant,
        others: loadableQuantity.otherIfAny === '' ? 0 : loadableQuantity.otherIfAny
      }
      subTotal = Number(this.loadablePlanTransformationService.getSubTotal(data));
      this.getTotalLoadableQuantity(subTotal, loadableQuantityResult);
    }
    else {
      const dwt = (Number(loadableQuantity.displacmentDraftRestriction) - Number(loadableQuantity.vesselLightWeight))?.toString();
      const data: ISubTotal = {
        dwt: dwt,
        sagCorrection: loadableQuantity.saggingDeduction,
        sgCorrection: loadableQuantity.sgCorrection,
        foOnboard: loadableQuantity.estFOOnBoard,
        doOnboard: loadableQuantity.estDOOnBoard,
        freshWaterOnboard: loadableQuantity.estFreshWaterOnBoard,
        boilerWaterOnboard: loadableQuantity.boilerWaterOnBoard,
        ballast: loadableQuantity.ballast,
        constant: loadableQuantity.constant,
        others: loadableQuantity.otherIfAny === '' ? 0 : loadableQuantity.otherIfAny
      }
      subTotal = Number(this.loadablePlanTransformationService.getSubTotal(data));
      this.getTotalLoadableQuantity(subTotal, loadableQuantityResult);
    }
  }


  /**
   * Calculation for Loadable quantity
   * @memberof LoadablePlanComponent
  */
  getTotalLoadableQuantity(subTotal: number, loadableQuantityResult: any) {
    const loadableQuantity = loadableQuantityResult.loadableQuantity;
    if (loadableQuantityResult.caseNo === 1) {
      const total = Number(subTotal) + Number(loadableQuantity.foConInSZ);
      if (total < 0) {
        this.loadableQuantity = 0;
      }
      else {
        this.loadableQuantity = total;
      }
    }
    else {
      if (subTotal < 0) {
        this.loadableQuantity = 0;
      }
      else {
        this.loadableQuantity = subTotal;
      }
    }
  }

  /**
  * Get algo error response
  * @returns {Promise<IAlgoResponse>}
  * @memberof LoadablePlanComponent
  */
  async getAlgoErrorMessage(status: boolean) {
    const algoError: IAlgoResponse = await this.loadablePlanApiService.getAlgoErrorDetails(this.vesselId, this.voyageId, this.loadableStudyId, this.loadablePatternId).toPromise();
    if(algoError.responseStatus.status === '200') {
      this.errorMessage = algoError.algoErrors;
      this.errorPopup = status;
    }
  }

  /**
    * Method to get the specific cargo tank
    *
    * @memberof LoadablePlanComponent
  */
  findCargoTank(tankId, tankLists) {
    let tankDetails;
    tankLists.forEach(tankArr => {
      tankArr.forEach(tank => {
        if (tank.id === tankId) {
          tankDetails = tank;
          return;
        }
      })
    })
    return tankDetails;
  }

  /**
    * Method to get the specific ballast tank
    *
    * @memberof LoadablePlanComponent
  */
  findBallastTank(tankId, tankLists) {
    let tankDetails;
    tankLists.forEach(tankArr => {
      tankArr.forEach(arr => {
        arr.forEach(tank => {
          if (tank.id === tankId) {
            tankDetails = tank;
            return;
          }
        })
      })
    })
    return tankDetails;
  }

  /**
    * Method to find out cargo
    *
    * @memberof LoadablePlanComponent
  */
  fingCargo(loadableQuantityCargoDetails): string {
    let cargoDetail;
    this.cargos.map((cargo) => {
      if (cargo.id === loadableQuantityCargoDetails.cargoId) {
        cargoDetail = cargo;
      }
    })
    return cargoDetail.name;
  }

   /**
 * Handler for confirm plan button
 *
 * @memberof LoadablePlanComponent
 */
    onConfirmPlanClick() {
      if (this.confirmPlanEligibility) {
        this.confirmPlan();
      } else {
        this.showPortRotationPopup = true;
      }
    }

  /**
   * for confirm stowage plan
   *
   * @param {*} event
   * @memberof LoadablePlanComponent
  */
  async confirmPlan() {
    if (this.loadableStudyStatus) {
      return;
    }
    this.ngxSpinnerService.show();
    const translationKeys = await this.translateService.get(['LOADABLE_PATTERN_CONFIRM_ERROR', 'LOADABLE_PATTERN_CONFIRM_STATUS_ERROR', 'LOADABLE_PLAN_VALIDATION_SAVE_IN_PROGESS_DETAILS', 'LOADABLE_PLAN_VALIDATION_SAVE_IN_PROGESS', 'LOADABLE_PLAN_ULLAGE_UPDATED', 'LOADABLE_PLAN_ULLAGE_UPDATED_DETAILS', 'LOADABLE_PATTERN_CONFIRM_SUMMARY', 'LOADABLE_PATTERN_CONFIRM_DETAILS_NOT_CONFIRM', 'LOADABLE_PATTERN_CONFIRM_DETAILS_CONFIRM', 'LOADABLE_PATTERN_CONFIRM_CONFIRM_LABEL', 'LOADABLE_PATTERN_CONFIRM_REJECT_LABEL']).toPromise();

    try {
      const result = await this.loadablePlanApiService.getConfirmStatus(this.vesselId, this.voyageId, this.loadableStudyId, this.loadablePatternId).toPromise();
      this.ngxSpinnerService.hide();
      let detail;
      if (result.confirmed) {
        detail = "LOADABLE_PATTERN_CONFIRM_DETAILS_NOT_CONFIRM";
      } else {
        detail = "LOADABLE_PATTERN_CONFIRM_DETAILS_CONFIRM";
      }

      this.confirmationService.confirm({
        key: 'confirmation-alert',
        header: translationKeys['LOADABLE_PATTERN_CONFIRM_SUMMARY'],
        message: translationKeys[detail],
        icon: 'pi pi-exclamation-triangle',
        acceptLabel: translationKeys['LOADABLE_PATTERN_CONFIRM_CONFIRM_LABEL'],
        acceptIcon: 'pi',
        acceptButtonStyleClass: 'btn btn-main mr-5',
        rejectVisible: true,
        rejectLabel: translationKeys['LOADABLE_PATTERN_CONFIRM_REJECT_LABEL'],
        rejectIcon: 'pi',
        rejectButtonStyleClass: 'btn btn-main',
        accept: async () => {
          try {
            const confirmResult = await this.loadablePlanApiService.confirm(this.vesselId, this.voyageId, this.loadableStudyId, this.loadablePatternId).toPromise();
            if (confirmResult.responseStatus.status === '200') {
              this.loadableStudyStatus = true;
            }
          } catch (errorResponse) {
            if (errorResponse?.error?.errorCode === 'ERR-RICO-110') {
              this.messageService.add({ severity: 'error', summary: translationKeys['LOADABLE_PATTERN_CONFIRM_ERROR'], detail: translationKeys['LOADABLE_PATTERN_CONFIRM_STATUS_ERROR'], life: 10000 });
            }
          }
        }
      });
    } catch(error) {
      if (error.error.errorCode === 'ERR-RICO-115') {
        this.messageService.add({ severity: 'warn', summary: translationKeys['LOADABLE_PLAN_VALIDATION_SAVE_IN_PROGESS'], detail: translationKeys['LOADABLE_PLAN_VALIDATION_SAVE_IN_PROGESS_DETAILS'] });
        this.ngxSpinnerService.hide();
      }
    }

  }

  /**
   * function to map the eta-etd port-based date and time
   *
   * @param {ILoadablePlanSynopticalRecord[]} synopticalRecords
   * @return {*}  {ILoadablePlanSynopticalRecord[]}
   * @memberof LoadablePlanComponent
   */
  convertLoadablePlanSynRecordsDateTime(synopticalRecords: ILoadablePlanSynopticalRecord[]): ILoadablePlanSynopticalRecord[] {
    synopticalRecords.map(record => (record.etaEtdPlanned ? record.etaEtdPlanned = this.transformDateTimeToPortLocal(record.etaEtdPlanned, record.portTimezoneId) : null));
    return synopticalRecords;
  }

  /**
   * function to conevrt the eta-etd local to port timezone
   *
   * @param {string} etaEtdDateTime
   * @param {number} timeZonId
   * @return {*}  {string}
   * @memberof LoadablePlanComponent
   */
  transformDateTimeToPortLocal(etaEtdDateTime: string, timeZonId: number): string {
    const selectedPortTimeZone = this.timeZoneList.find(tz => (tz.id === timeZonId));
    const formatOptions: IDateTimeFormatOptions = {
      portLocalFormat: true,
      portTimeZoneOffset: selectedPortTimeZone?.offsetValue,
      portTimeZoneAbbr: selectedPortTimeZone?.abbreviation
    };
    return this.timeZoneTransformationService.formatDateTime(etaEtdDateTime, formatOptions);
  }

  /**
   * export data
   * @memberof LoadablePlanComponent
   */
  async export() {
    this.loadablePlanApiService.export(this.vesselId, this.voyageId, this.loadableStudyId, this.loadablePatternId).subscribe((data) => {
      const blob = new Blob([data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;' })
      const fileurl = window.URL.createObjectURL(blob)
      saveAs(fileurl, 'Report.xlsx')
    });
  }

   /**
  * Method to close port rotation pop up
  *
  * @memberof LoadablePlanComponent
  */
    closePortRotationPopup(e) {
      this.showPortRotationPopup = e.hide;
      if (e.success) {
        this.confirmPlanEligibility = false;
        this.confirmPlan();
      }
    }

}

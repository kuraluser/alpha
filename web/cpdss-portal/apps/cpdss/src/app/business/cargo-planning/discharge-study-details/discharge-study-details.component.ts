import { Component, OnInit, OnDestroy, HostListener, ViewChild, ElementRef } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subject, Observable } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { MessageService } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';
import * as moment from 'moment';

import { NgxSpinnerService } from 'ngx-spinner';

import { IVessel } from '../../core/models/vessel-details.model';
import { Voyage, IPort, VOYAGE_STATUS, DISCHARGE_STUDY_STATUS, ICargo, DISCHARGE_STUDY_STATUS_TEXT, IAlgoError, IAlgoResponse } from '../../core/models/common.model';

import { DischargeStudyComponent } from './discharge-study/discharge-study.component'
import { IPermissionContext, PERMISSION_ACTION, QUANTITY_UNIT } from '../../../shared/models/common.model';
import { IPermission } from '../../../shared/models/user-profile.model';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';
import { VoyageService } from '../../core/services/voyage.service';
import { VesselsApiService } from '../../core/services/vessels-api.service';
import { PermissionsService } from '../../../shared/services/permissions/permissions.service';
import { DISCHARGE_STUDY_DETAILS_TABS } from '../models/cargo-planning.model';
import { IDischargeStudy } from '../models/discharge-study-list.model';
import { DischargeStudyDetailsApiService } from '../services/discharge-study-details-api.service';
import { DischargeStudyListApiService } from '../services/discharge-study-list-api.service';
import { DischargeStudyDetailsTransformationService } from '../services/discharge-study-details-transformation.service';
import { UnsavedChangesGuard } from '../../../shared/services/guards/unsaved-data-guard';
import { GlobalErrorHandler } from '../../../shared/services/error-handlers/global-error-handler';
import { environment } from '../../../../environments/environment';
import { SecurityService } from '../../../shared/services/security/security.service';

/**
 *
 * Component class for DischargeStudyDetailsComponent
 * @export
 * @class DischargeStudyDetailsComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-discharge-study-details',
  templateUrl: './discharge-study-details.component.html',
  styleUrls: ['./discharge-study-details.component.scss']
})
export class DischargeStudyDetailsComponent implements OnInit, OnDestroy {
  @ViewChild('dischargeStudy') dischargeStudy: DischargeStudyComponent;

  get selectedDischargeStudy(): IDischargeStudy {
    return this._selectedDischargeStudy;
  }

  set selectedDischargeStudy(selectedDischargeStudy: IDischargeStudy) {
    this._selectedDischargeStudy = selectedDischargeStudy;
    this.isPlanGenerated = (this._selectedDischargeStudy?.statusId === DISCHARGE_STUDY_STATUS.PLAN_GENERATED || this._selectedDischargeStudy?.statusId === DISCHARGE_STUDY_STATUS.PLAN_CONFIRMED ) ? true : false;
    this.isPlanOpenOrNo = (this._selectedDischargeStudy?.statusId === 1 || this._selectedDischargeStudy?.statusId === 6) ? false : this.inProcessing();
    this.dischargeStudyId = selectedDischargeStudy ? selectedDischargeStudy?.id : this.dischargeStudies?.length ? this.dischargeStudies[0]?.id : 0;
  }

  readonly DISCHARGE_STUDY_DETAILS_TABS = DISCHARGE_STUDY_DETAILS_TABS;
  readonly DISCHARGE_STUDY_STATUS = DISCHARGE_STUDY_STATUS;
  readonly VOYAGE_STATUS = VOYAGE_STATUS;

  private ngUnsubscribe: Subject<any> = new Subject();
  private _selectedDischargeStudy: IDischargeStudy;
  voyageId: number;
  vesselId: number;
  dischargeStudyId: number;
  vesselInfo: IVessel;
  voyages: Voyage[];
  ports: IPort[];
  cargoNominationTabPermissionContext: IPermissionContext;
  portsTabPermissionContext: IPermissionContext;
  cargoNominationTabPermission: IPermission;
  portsTabPermission: IPermission;
  ohqTabPermissionContext: IPermissionContext;
  ohqTabPermission: IPermission;
  dischargeTabPermission: IPermission;
  selectedTab: string;
  selectedVoyage: Voyage;
  dischargeStudies: IDischargeStudy[];
  portsComplete: boolean;
  cargoNominationComplete: boolean;
  ohqComplete: boolean;
  dischargeStudyComplete: boolean;
  dischargeStudyTabPermissionContext: IPermissionContext;
  dischargeStudyTabPermission: IPermission;
  dischargeStudyViewPlanPermission: IPermission;
  dischargeStudyGeneratePermission: IPermission;
  cargos: ICargo[];
  disableGeneratePlan: boolean;
  isPlanGenerated: boolean;
  isPlanOpenOrNo: boolean;
  isGenerateClicked: boolean;
  errorPopup: boolean;
  errorMessage: IAlgoError[];
  isPlanConfirmed: IDischargeStudy;

  @HostListener('window:beforeunload')
  canDeactivate(): Observable<boolean> | boolean {
    return !(this.dischargeStudy?.dischargeStudyForm?.dirty);
  }

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private permissionsService: PermissionsService,
    private ngxSpinnerService: NgxSpinnerService,
    private vesselsApiService: VesselsApiService,
    private voyageService: VoyageService,
    private unsavedChangesGuard: UnsavedChangesGuard,
    private dischargeStudyDetailsApiService: DischargeStudyDetailsApiService,
    private dischargeStudyListApiService: DischargeStudyListApiService,
    private messageService: MessageService,
    private translateService: TranslateService,
    private globalErrorHandler: GlobalErrorHandler,
    private dischargeStudyDetailsTransformationService: DischargeStudyDetailsTransformationService
  ) { }

  /**
   * NgOnit init function for discharge study details component
   *
   * @memberof DischargeStudyComponent
   */
  ngOnInit(): void {
    this.initSubsciptions();
    this.listenEvents();
    this.setPagePermissionContext();
    this.activatedRoute.paramMap
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(params => {
        this.disableGeneratePlan = true;
        this.vesselId = Number(params.get('vesselId'));
        this.voyageId = Number(params.get('voyageId'));
        this.dischargeStudyId = Number(params.get('dischargeStudyId'));
        localStorage.setItem("vesselId", this.vesselId.toString());
        localStorage.setItem("voyageId", this.voyageId.toString());
        localStorage.setItem("dischargeStudyId", this.dischargeStudyId.toString());

        this.dischargeStudies = null;


        this.tabPermission();
        this.getDischargeStudies(this.vesselId, this.voyageId, this.dischargeStudyId);
      });
  }

  /**
 * Handler for service worker message event
 *
 * @private
 * @memberof DischargeStudyComponent
 */
  private swMessageHandler = async event => {
    if (event.data?.syncType !== 'discharge-study-plan-status') {
      return;
    }
    if (event?.data?.status === '401' && event?.data?.errorCode === '210') {
      this.globalErrorHandler.sessionOutMessage();
    } else if (environment.name !== 'shore' && (event?.data?.status === '200' || event?.data?.responseStatus?.status === '200')) {
      SecurityService.refreshToken(event?.data?.refreshedToken)
    }
    if (event.data.type === 'discharge-pattern-processing' && this.router.url.includes('discharge-study-details')) {
      if (event.data.pattern?.dischargeStudyId === this.dischargeStudyId) {
        this.isGenerateClicked = true;
        this.processingMessage();
      } else {
        this.messageService.clear();
      }
    }
    else if (event.data.type === 'discharge-pattern-loadicator-checking' && this.router.url.includes('discharge-study-details')) {
      if (event.data.pattern?.dischargeStudyId === this.dischargeStudyId) {
        this.isGenerateClicked = true;
        this.selectedDischargeStudy.statusId = DISCHARGE_STUDY_STATUS.PLAN_LOADICATOR_CHECKING;
        this.selectedDischargeStudy.status = DISCHARGE_STUDY_STATUS_TEXT.PLAN_LOADICATOR_CHECKING;
        this.messageService.clear();
        const translationKeys = await this.translateService.get(['GENERATE_DISCHARGE_STUDY_PATTERN_INFO', 'GENERATE_DISCHARGE_STUDY_PATTERN_LOADICATOR_CHECKING']).toPromise();
        this.messageService.add({ severity: 'info', summary: translationKeys['GENERATE_DISCHARGE_STUDY_PATTERN_INFO'], detail: translationKeys['GENERATE_DISCHARGE_STUDY_PATTERN_LOADICATOR_CHECKING'], life: 1000, closable: false });
      } else {
        this.messageService.clear();
      }
    }
    else if (event.data.type === 'discharge-pattern-completed') {
      if (event.data.pattern?.dischargeStudyId === this.dischargeStudyId) {
        this.isPlanGenerated = true;
        this.selectedDischargeStudy.statusId = DISCHARGE_STUDY_STATUS.PLAN_GENERATED;
        this.selectedDischargeStudy.status = DISCHARGE_STUDY_STATUS_TEXT.PLAN_GENERATED;
      }
      this.generatedMessage(event.data.pattern.selectedVoyageNo, event.data.pattern.selectedDischargeStudyName);
    }
    else if (event.data.type === 'discharge-pattern-no-solution') {
      if (event.data.pattern?.dischargeStudyId === this.dischargeStudyId) {
        this.isPlanOpenOrNo = false;
        this.isPlanGenerated = false;
        this.isGenerateClicked = false;
        this.getAlgoErrorMessage(true);
        this.selectedDischargeStudy.statusId = DISCHARGE_STUDY_STATUS.PLAN_NO_SOLUTION;
        this.selectedDischargeStudy.status = DISCHARGE_STUDY_STATUS_TEXT.PLAN_NO_SOLUTION;
      }
      this.noPlanMessage(event.data.pattern.selectedVoyageNo, event.data.pattern.selectedDischargeStudyName)
    }
    else if (event.data.type === 'discharge-pattern-error-occured') {
      if (event.data.pattern?.dischargeStudyId === this.dischargeStudyId) {
        this.isPlanOpenOrNo = false;
        this.isPlanGenerated = false;
        this.isGenerateClicked = false;
        this.getAlgoErrorMessage(true);
        this.selectedDischargeStudy.statusId = DISCHARGE_STUDY_STATUS.PLAN_ERROR;
        this.selectedDischargeStudy.status = DISCHARGE_STUDY_STATUS_TEXT.PLAN_ERROR;
      }
      this.errorOccurdMessage(event.data.pattern.selectedVoyageNo, event.data.pattern.selectedDischargeStudyName)
    }
    else if (event.data.type === 'discharge-pattern-no-response') {
      if (event.data.pattern?.dischargeStudyId === this.dischargeStudyId) {
        this.isPlanOpenOrNo = false;
        this.isPlanGenerated = false;
        this.isGenerateClicked = false;
      }
      this.noResponseMessage(event.data.pattern.selectedVoyageNo, event.data.pattern.selectedDischargeStudyName);
    }
    this.selectedDischargeStudy = {...this.selectedDischargeStudy};
  }

  /**
  * Toast to show pattern processing
  *
  * @memberof DischargeStudyComponent
  */
  async processingMessage() {
    this.messageService.clear();
    const translationKeys = await this.translateService.get(['GENERATE_DISCHARGE_STUDY_PATTERN_INFO', 'GENERATE_DISCHARGE_STUDY_PATTERN_PROCESSING']).toPromise();
    this.messageService.add({ severity: 'info', summary: translationKeys['GENERATE_DISCHARGE_STUDY_PATTERN_INFO'], detail: translationKeys['GENERATE_DISCHARGE_STUDY_PATTERN_PROCESSING'], life: 1000, closable: false });
  }

  /**
   * Toast to show pattern generate complete
   *
   * @param {string} selectedVoyageNo
   * @param {string} selectedDischargeStudyName
   * @memberof DischargeStudyComponent
   */
  async generatedMessage(selectedVoyageNo: string, selectedDischargeStudyName: string) {
    const translationKeys = await this.translateService.get(['GENERATE_DISCHARGE_STUDY_COMPLETE_DONE', 'GENERATE_DISCHARGE_STUDY_PATTERN_COMPLETED']).toPromise();
    this.messageService.add({ severity: 'success', summary: translationKeys['GENERATE_DISCHARGE_STUDY_COMPLETE_DONE'], detail: selectedVoyageNo + " " + selectedDischargeStudyName + " " + translationKeys['GENERATE_DISCHARGE_STUDY_PATTERN_COMPLETED'], sticky: true, closable: true });
  }

  /**
 * Toast to show for generating pattern have no solution
 *
 * @param {string} selectedVoyageNo
 * @param {string} selectedDischargeStudyName
 * @memberof DischargeStudyComponent
 */
  async noPlanMessage(selectedVoyageNo: string, selectedDischargeStudyName: string) {
    this.messageService.clear();
    const translationKeys = await this.translateService.get(['GENERATE_DISCHARGE_STUDY_PATTERN_NO_PLAN', 'GENERATE_DISCHARGE_STUDY_PATTERN_NO_PLAN_MESSAGE']).toPromise();
    this.messageService.add({ severity: 'error', summary: translationKeys['GENERATE_DISCHARGE_STUDY_PATTERN_NO_PLAN'], detail: selectedVoyageNo + " " + selectedDischargeStudyName + " " + translationKeys['GENERATE_DISCHARGE_STUDY_PATTERN_NO_PLAN_MESSAGE'] });
  }

  /**
   * Toast to show for generating pattern have algo error
   * @param {string} selectedVoyageNo
   * @param {string} selectedDischargeStudyName
   * @memberof DischargeStudyComponent
   */
  async errorOccurdMessage(selectedVoyageNo: string, selectedDischargeStudyName: string) {
    this.messageService.clear();
    const translationKeys = await this.translateService.get(['GENERATE_DISCHARGE_STUDY_PATTERN_ERROR_OCCURED', 'GENERATE_DISCHARGE_STUDY_PATTERN_ERROR_OCCURED_MESSAGE']).toPromise();
    this.messageService.add({ severity: 'error', summary: translationKeys['GENERATE_DISCHARGE_STUDY_PATTERN_ERROR_OCCURED'], detail: selectedVoyageNo + " " + selectedDischargeStudyName + " " + translationKeys['GENERATE_DISCHARGE_STUDY_PATTERN_ERROR_OCCURED_MESSAGE'] });
  }

  /**
* Toast to show pattern bo reposnse
*
* @param {string} selectedVoyageNo
* @param {string} selectedDischargeStudyName
* @memberof DischargeStudyComponent
*/
  async noResponseMessage(selectedVoyageNo: string, selectedDischargeStudyName: string) {
    const translationKeys = await this.translateService.get(['GENERATE_DISCHARGE_STUDY_NO_RESPONSE_ERROR', 'GENERATE_DISCHARGE_STUDY_PATTERN_RESPONSE']).toPromise();
    this.messageService.add({ severity: 'error', summary: translationKeys['GENERATE_DISCHARGE_STUDY_NO_RESPONSE_ERROR'], detail: selectedVoyageNo + " " + selectedDischargeStudyName + " " + translationKeys['GENERATE_DISCHARGE_STUDY_PATTERN_RESPONSE'], sticky: true, closable: true });
  }

    /**
  * Method for set button visibility on processing
  *
  * @returns {boolean}
  * @memberof DischargeStudyComponent
  */
     inProcessing() {
      if (this.selectedDischargeStudy?.statusId === 4) {
        let dateString = this.selectedDischargeStudy?.dischargeStudyStatusLastModifiedTime;
        const dateTimeParts = dateString?.split(' ');
        const dateParts = dateTimeParts[0]?.split('-');
  
        if (dateParts?.length) {
          dateString = Number(dateParts[1]) + "/" + Number(dateParts[0]) + "/" + Number(dateParts[2]) + " " + dateTimeParts[1];
          const modifiedDate = moment.utc(dateString).toDate();
          const addProcessingTimeout = new Date(modifiedDate.getTime() + 600);
          const now = new Date();
          if (addProcessingTimeout < now) {
            return false;
          } else {
            return true;
          }
        } else {
          return true;
        }
  
      }
    }


  /**
 * NgOnDestroy lifecycle hook
 *
 * @memberof DischargeStudyDetailsComponent
 */
  ngOnDestroy() {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
    navigator.serviceWorker.removeEventListener('message', this.swMessageHandler);
  }
  /**
   * Set page permission
   *
   * @memberof DischargeStudyDetailsComponent
   */
  setPagePermissionContext() {


    this.cargoNominationTabPermission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['DischargeStudyCargoNominationComponentTab'], false);
    this.cargoNominationTabPermissionContext = { key: AppConfigurationService.settings.permissionMapping['DischargeStudyCargoNominationComponentTab'], actions: [PERMISSION_ACTION.VIEW] };

    this.portsTabPermission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['DischargeStudyPortsComponentTab'], false);
    this.portsTabPermissionContext = { key: AppConfigurationService.settings.permissionMapping['DischargeStudyPortsComponentTab'], actions: [PERMISSION_ACTION.VIEW] };

    this.ohqTabPermission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['DischargeStudyOnHandQuantityComponentTab'], false);
    this.ohqTabPermissionContext = { key: AppConfigurationService.settings.permissionMapping['DischargeStudyOnHandQuantityComponentTab'], actions: [PERMISSION_ACTION.VIEW] };

    this.dischargeStudyTabPermission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['DischargeStudyComponentTab'], false);
    this.dischargeStudyTabPermissionContext = { key: AppConfigurationService.settings.permissionMapping['DischargeStudyComponentTab'], actions: [PERMISSION_ACTION.VIEW] };

    this.dischargeStudyViewPlanPermission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['DischargeStudyViewPlan'], false);
    this.dischargeStudyGeneratePermission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['DischargeStudyGeneratePlan'], false);
    this.tabPermission();
  }

  /**
   * select tab permission
   * @memberof DischargeStudyDetailsComponent
   */
  tabPermission() {
    if (this.cargoNominationTabPermission === undefined || this.cargoNominationTabPermission?.view) {
      this.selectedTab = DISCHARGE_STUDY_DETAILS_TABS.CARGONOMINATION;
    } else if (this.portsTabPermission?.view) {
      this.selectedTab = DISCHARGE_STUDY_DETAILS_TABS.PORTS;
    } else if (this.ohqTabPermission?.view) {
      this.selectedTab = DISCHARGE_STUDY_DETAILS_TABS.OHQ;
    } else if (this.dischargeTabPermission?.view) {
      this.selectedTab = DISCHARGE_STUDY_DETAILS_TABS.DISCHARGE_STUDY;
    } else {
      this.selectedTab = DISCHARGE_STUDY_DETAILS_TABS.CARGONOMINATION;
    }
  }

  /**
 * Method to fetch all discharge
 *
 * @param {number} vesselId
 * @param {number} voyageId
 * @param {number} dischargeStudyId
 * @memberof DischargeStudyDetailsComponent
 */
  async getDischargeStudies(vesselId: number, voyageId: number, dischargeStudyId: number) {
    this.ngxSpinnerService.show();
    const res = await this.vesselsApiService.getVesselsInfo().toPromise();
    this.vesselInfo = res[0] ?? <IVessel>{};
    this.dischargeStudyDetailsTransformationService.vesselInfo = this.vesselInfo;
    const voyages = await this.getVoyages(this.vesselId, this.voyageId);
    this.voyages = voyages.filter(voy => voy.statusId === VOYAGE_STATUS.ACTIVE || voy.statusId === VOYAGE_STATUS.CLOSE);
    const result = await this.dischargeStudyListApiService.getDischargeStudies(vesselId, voyageId).toPromise();
    const dischargeStudies = result?.dischargeStudies ?? [];
    this.cargoNominationComplete = true;
    if (dischargeStudies.length) {
      this.dischargeStudies = dischargeStudies;
      this.selectedDischargeStudy = dischargeStudyId ? this.dischargeStudies.find(dischargeStudy => dischargeStudy.id === dischargeStudyId) : this.dischargeStudies[0];
      this.dischargeStudyId = this.selectedDischargeStudy.id;
      if (this.selectedDischargeStudy.statusId === DISCHARGE_STUDY_STATUS.PLAN_NO_SOLUTION || this.selectedDischargeStudy.statusId === DISCHARGE_STUDY_STATUS.PLAN_ERROR) {
        this.getAlgoErrorMessage(false);
      } else if (this.selectedDischargeStudy.statusId === DISCHARGE_STUDY_STATUS.PLAN_ALGO_PROCESSING || this.selectedDischargeStudy.statusId === DISCHARGE_STUDY_STATUS.PLAN_ALGO_PROCESSING_COMPETED || this.selectedDischargeStudy.statusId === DISCHARGE_STUDY_STATUS.PLAN_LOADICATOR_CHECKING) {
        this.isGenerateClicked = true;
      }
      const isPortsComplete = this.selectedDischargeStudy?.ohqPorts?.length && this.selectedDischargeStudy.isPortsComplete ? true : false;
      this.dischargeStudyDetailsTransformationService.setPortValidity(isPortsComplete);
      this.dischargeStudyDetailsTransformationService.setOHQValidity(this.selectedDischargeStudy?.ohqPorts ?? []);
      this.dischargeStudyDetailsTransformationService.setDischargeStudyValidity(this.selectedDischargeStudy?.isDischargeStudyComplete);
    } else {
      this.cargoNominationComplete = false;
      this.selectedDischargeStudy = null;
      this.dischargeStudies = [];
    }
    this.isPlanConfirmed = this.dischargeStudies.find(dischargeStudy => DISCHARGE_STUDY_STATUS.PLAN_CONFIRMED === dischargeStudy.statusId);
    this.ports = await this.getPorts();
    const cargos = await this.dischargeStudyDetailsApiService.getCargoDetails().toPromise();
    this.cargos = cargos.cargos;
    this.disableGeneratePlan = false;
    this.ngxSpinnerService.hide();
  }

  /**
  * Get algo error response
  * @returns {Promise<IAlgoResponse>}
  * @memberof DischargeStudyDetailsComponent
  */
  async getAlgoErrorMessage(status: boolean) {
    const algoError: IAlgoResponse = await this.dischargeStudyDetailsApiService.getAlgoErrorDetails(this.vesselId, this.voyageId, this.dischargeStudyId).toPromise();
    if (algoError.responseStatus.status === '200') {
      this.errorMessage = algoError.algoErrors;
      this.errorPopup = status;
    }
  }

  /**
  * view algo error message
  * @param {boolean} status
  * @memberof DischargeStudyDetailsComponent
  */
  public viewError(status: boolean) {
    this.errorPopup = status;
  }

  /**
   * Method to fetch all voyages in the vessel
   *
   * @param {number} vesselId
   * @param {number} voyageId
   * @memberof DischargeStudyDetailsComponent
   */
  async getVoyages(vesselId: number, voyageId: number) {
    const result = await this.voyageService.getVoyagesByVesselId(vesselId).toPromise();
    const voyages = result ?? [];
    this.selectedVoyage = voyages.find(voyage => voyage.id === voyageId);
    return voyages;
  }

  /**
   * Method for fetching all ports from master
   *
   * @private
   * @memberof DischargeStudyDetailsComponent
   */
  private async getPorts(): Promise<IPort[]> {
    const result = await this.dischargeStudyDetailsApiService.getPorts().toPromise();
    return result;
  }

  /**
 * Handler for voyage dropdown change
 *
 * @param {*} event
 * @memberof DischargeStudyDetailsComponent
 */
  onVoyageChange(event: any) {
    this.voyageId = event?.value?.id;
    this.dischargeStudies = null;
    this.dischargeStudyId = 0;
    this.dischargeStudyDetailsTransformationService.setPortValidity(false);
    this.dischargeStudyDetailsTransformationService.setOHQValidity([]);
    this.tabPermission();
    this.selectedDischargeStudy = null;
    this.initSubsciptions();
    this.messageService.clear('isPortOrderValid');
    this.router.navigate([`business/cargo-planning/discharge-study-details/${this.vesselId}/${this.voyageId}/0`]);
  }

  /**
   * On Click of tab in discharge study details page
   *
   * @param {string} selectedTab
   * @memberof DischargeStudyDetailsComponent
  */
  async onTabClick(selectedTab: string) {
    const value = await this.unsavedChangesGuard.canDeactivate(this);
    if (!value) { return };
    this.selectedTab = selectedTab;
  }

  /**
   * Initialise all subscription in this page
   *
   * @private
   * @memberof DischargeStudyDetailsComponent
   */
  private async initSubsciptions() {
    this.dischargeStudyDetailsTransformationService.portValidity$.subscribe((res) => {
      this.portsComplete = res;
    })
    this.dischargeStudyDetailsTransformationService.ohqValidity$.subscribe((res) => {
      this.ohqComplete = res;
    })
    this.dischargeStudyDetailsTransformationService.dischargeStudyValiditySource$.subscribe((res) => {
      this.dischargeStudyComplete = res;
    })
  }

  /**
 * Listen events in this page
 *
 * @private
 * @memberof DischargeStudyDetailsComponent
 */
  private async listenEvents() {
    navigator.serviceWorker.addEventListener('message', this.swMessageHandler);
  }

  /**
* Triggering add port
*
* @memberof DischargeStudyDetailsComponent
*/
  addPort() {
    this.dischargeStudyDetailsTransformationService.addPort();
  }

  /**
  * Method to change discharge study
  *
  * @memberof DischargeStudyDetailsComponent
  */
  onDischargeStudyChange(event) {
    const dischargeStudyId = event.value.id;
    this.messageService.clear('isPortOrderValid');
    this.router.navigate([`business/cargo-planning/discharge-study-details/${this.vesselId}/${this.voyageId}/${dischargeStudyId}`]);
  }

  /**
  * Handler for Generate discharge pattern
  *
  * @param {number} vesselId
  * @param {number} voyageId
  * @param {number} dischargeStudyId
  * @memberof DischargeStudyDetailsComponent
  */
  async generatePlan() {
    this.ngxSpinnerService.show();
    const data = {
      vesselId: this.vesselId,
      voyageId: this.voyageId,
      dischargeStudyId: this.dischargeStudyId,
      selectedVoyageNo: this.selectedVoyage?.voyageNo,
      selectedDischargeStudyName: this.selectedDischargeStudy?.name,
      processId: null
    }

    try {
      const res = await this.dischargeStudyDetailsApiService.generateDischargePattern(this.vesselId, this.voyageId, this.dischargeStudyId).toPromise();
      if (res.responseStatus.status === '200') {
        if (environment.name === 'shore') {
          this.selectedDischargeStudy.statusId = DISCHARGE_STUDY_STATUS.PLAN_ALGO_PROCESSING;
        } else {
          this.selectedDischargeStudy.statusId = DISCHARGE_STUDY_STATUS.PLAN_COMMUNICATED_TO_SHORE;
        }
        data.processId = res.processId;
        if (res.processId) {
          navigator.serviceWorker.controller.postMessage({ type: 'discharge-study-pattern-status', data });
          this.selectedDischargeStudy = { ...this.selectedDischargeStudy };
        }
      }
    }
    catch (errorResponse) {
    }
    this.ngxSpinnerService.hide();
  }

  /**
    * Handler for discharge study view 
    * @memberof DischargeStudyDetailsComponent
  */
  async dischargeStudyView() {
    const value = await this.unsavedChangesGuard.canDeactivate(this);
    if (!value) { return };
    this.router.navigate([`business/cargo-planning/discharge-plan/${this.vesselId}/${this.voyageId}/${this.dischargeStudyId}`]);
  }

  /**
    * Check if Discharge study is valid or not
    * @memberof DischargeStudyDetailsComponent
  */
  isDischargeStudyValid() {
    if(this.dischargeStudy?.dischargeStudyForm && !this.dischargeStudy?.dischargeStudyForm.disabled) {
      return this.dischargeStudyComplete && !this.dischargeStudy?.dischargeStudyForm?.dirty && this.dischargeStudy?.dischargeStudyForm?.valid;
    } else {
      return this.dischargeStudyComplete;
    }
  }

}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ICargoTank, Voyage, VOYAGE_STATUS, LOADABLE_STUDY_STATUS, IBallastTank, ICargo, ICargoResponseModel } from '../../core/models/common.model';
import { VesselsApiService } from '../../core/services/vessels-api.service';
import { VoyageService } from '../../core/services/voyage.service';
import { IVessel } from '../../core/models/vessel-details.model';
import { LoadableStudy } from '../models/loadable-study-list.model';
import { LoadableStudyListApiService } from '../services/loadable-study-list-api.service';
import { LoadablePatternHistoryApiService } from '../services/loadable-pattern-history-api.service';
import { ILoadablePattern, ILoadablePatternCargoDetail, ILoadablePatternResponse, IStabilityParameter } from '../models/loadable-pattern.model';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';
import { PermissionsService } from '../../../shared/services/permissions/permissions.service';
import { IPermissionContext, PERMISSION_ACTION, QUANTITY_UNIT } from '../../../shared/models/common.model';
import { QuantityPipe } from '../../../shared/pipes/quantity/quantity.pipe';
import { ConfirmationService, MessageService } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';
import { VALIDATION_AND_SAVE_STATUS } from '../models/loadable-plan.model';

/**
 * Component class of pattern history screen
 *
 * @export
 * @class LoadablePatternHistoryComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-loadable-pattern-history',
  templateUrl: './loadable-pattern-history.component.html',
  styleUrls: ['./loadable-pattern-history.component.scss']
})
export class LoadablePatternHistoryComponent implements OnInit {

  get selectedLoadableStudy(): LoadableStudy {
    return this._selectedLoadableStudy;
  }
  set selectedLoadableStudy(selectedLoadableStudy: LoadableStudy) {
    this._selectedLoadableStudy = selectedLoadableStudy;
    this.loadableStudyId = selectedLoadableStudy?.id;
    if (this.loadableStudyId) {
      this.router.navigate([`/business/cargo-planning/loadable-pattern-history/0/${this.vesselId}/${this.voyageId}/${this.loadableStudyId}`]);
    }
  }

  private _selectedLoadableStudy: LoadableStudy;
  voyageId: number;
  loadableStudyId: number;
  vesselId: number;
  openSidePane = true;
  selectedVoyage: Voyage;
  vesselInfo: IVessel;
  voyages: Voyage[];
  loadableStudies: LoadableStudy[];
  loadablePatternResponse: ILoadablePatternResponse;
  loadablePatterns: ILoadablePattern[];
  tankLists: ICargoTank[][];
  rearBallastTanks: IBallastTank[][];
  centerBallastTanks: IBallastTank[][];
  frontBallastTanks: IBallastTank[][];
  loadablePatternCreatedDate: string;
  loadableStudyName: string;
  display = false;
  isViewPattern: boolean;
  selectedLoadablePatternCargoDetail: ILoadablePatternCargoDetail;
  loadablePatternPermissionContext: IPermissionContext;
  loadablePatternDetailsId: number;
  currentQuantitySelectedUnit = <QUANTITY_UNIT>localStorage.getItem('unit');
  prevQuantitySelectedUnit: QUANTITY_UNIT;
  showStability = false;
  stabilityParameters: IStabilityParameter[];
  LOADABLE_STUDY_STATUS = LOADABLE_STUDY_STATUS;
  VOYAGE_STATUS = VOYAGE_STATUS;
  viewMore = false;
  selectedLoadablePattern: ILoadablePattern;
  cargos: ICargo[];
  patternLoaded = false;
  baseUnit = AppConfigurationService.settings.baseUnit;
  loadablePlanPermissionContext: IPermissionContext;
  showPortRotationPopup = false;
  selectedLoadablePatternId: number;
  readonly VALIDATION_AND_SAVE_STATUS = VALIDATION_AND_SAVE_STATUS;

  constructor(private vesselsApiService: VesselsApiService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private voyageService: VoyageService,
    private ngxSpinnerService: NgxSpinnerService,
    private loadableStudyListApiService: LoadableStudyListApiService,
    private loadablePatternApiService: LoadablePatternHistoryApiService,
    private permissionsService: PermissionsService,
    private quantityPipe: QuantityPipe,
    private confirmationService: ConfirmationService,
    private messageService: MessageService,
    private translateService: TranslateService) { }

  /**
   * Component lifecycle ngOnit
   *
   * @returns {Promise<void>}
   * @memberof LoadablePatternHistoryComponent
   */
  async ngOnInit(): Promise<void> {
    const permission = await this.getPagePermission();
    this.activatedRoute.paramMap.subscribe(async params => {
      if (permission.view) {
        this.isViewPattern = Number(params.get('isViewPattern')) === 0 ? true : false;
        this.vesselId = Number(params.get('vesselId'));
        this.voyageId = Number(params.get('voyageId'));
        this.loadableStudyId = Number(params.get('loadableStudyId'));
        localStorage.setItem("vesselId", this.vesselId.toString())
        localStorage.setItem("voyageId", this.voyageId.toString())
        localStorage.setItem("loadableStudyId", this.loadableStudyId.toString())
        localStorage.removeItem("loadablePatternId")
        if (this.isViewPattern) {
          this.getLoadableStudies(this.vesselId, this.voyageId, this.loadableStudyId);
        }
        this.getCargos();
        this.getLoadablePatterns(this.vesselId, this.voyageId, this.loadableStudyId);
      }
    });
  }


  /**
* Get page permission
*
* @memberof LoadablePatternHistoryComponent
*/
  async getPagePermission() {
    const loadablePatternPermission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['LoadablePatternHistoryComponent'], true);
    this.loadablePatternPermissionContext = { key: AppConfigurationService.settings.permissionMapping['LoadablePatternHistoryComponent'], actions: [PERMISSION_ACTION.VIEW] };
    this.loadablePlanPermissionContext = { key: AppConfigurationService.settings.permissionMapping['LoadablePlanComponent'], actions: [PERMISSION_ACTION.VIEW] };
    return loadablePatternPermission;
  }


  /**
   * Method to fetch all voyages in the vessel
   *
   * @memberof LoadablePatternHistoryComponent
   */
  async backToLoadableStudy() {
    this.router.navigate([`/business/cargo-planning/loadable-study-details/${this.vesselId}/${this.voyageId}/${this.loadableStudyId}`]);
  }

  /**
     * Method to fetch all loadableStudies
     *
     * @param {number} vesselId
     * @param {number} voyageId
     * @param {number} loadableStudyId
     * @memberof LoadablePatternHistoryComponent
     */
  async getLoadableStudies(vesselId: number, voyageId: number, loadableStudyId: number) {
    this.ngxSpinnerService.show();
    const result = await this.loadableStudyListApiService.getLoadableStudies(vesselId, voyageId).toPromise();
    this.loadableStudies = result?.loadableStudies ?? [];
    if (this.loadableStudies.length) {
      this.setProcessingLoadableStudyActions(0, 0);
      this.selectedLoadableStudy = loadableStudyId ? this.loadableStudies.find(loadableStudy => loadableStudy.id === loadableStudyId) : this.loadableStudies[0];
    } else {
      this.selectedLoadableStudy = null;
    }
  }

  /**
  * Enable/ Disable actions of currently processing/processed loadable study
  *
  * @param {*} event
  * @memberof LoadablePatternHistoryComponent
  */
  setProcessingLoadableStudyActions(loadableStudyId: number, statusId: number) {
    const loadableStudies = this.loadableStudies.map(loadableStudy => {
      if (loadableStudyId === loadableStudy?.id) {
        if ([4, 5].includes(statusId) && this.router.url.includes('loadable-pattern-history')) {
          loadableStudy.isActionsEnabled = false;
        }
        else if ([2, 3].includes(statusId)) {
          loadableStudy.isEditable = false;
          loadableStudy.isDeletable = false;
          loadableStudy.isActionsEnabled = true;
        }
        else if ([6, 1].includes(statusId)) {
          loadableStudy.isActionsEnabled = true;
        }
      } else if (!loadableStudyId && !statusId) {
        loadableStudy.isActionsEnabled = [LOADABLE_STUDY_STATUS.PLAN_COMMUNICATED_TO_SHORE , LOADABLE_STUDY_STATUS.PLAN_ALGO_PROCESSING, LOADABLE_STUDY_STATUS.PLAN_ALGO_PROCESSING_COMPETED, LOADABLE_STUDY_STATUS.PLAN_LOADICATOR_CHECKING].includes(loadableStudy?.statusId) ? false : true;
        loadableStudy.isEditable = (loadableStudy?.statusId === 3 || loadableStudy?.statusId === 2) ? false : true;
        loadableStudy.isDeletable = (loadableStudy?.statusId === 3 || loadableStudy?.statusId === 2) ? false : true;
      }
      return loadableStudy;
    });
    this.loadableStudies = loadableStudies;
  }

  /**
   * Method to fetch all voyages in the vessel
   *
   * @param {number} vesselId
   * @param {number} voyageId
   * @memberof LoadablePatternHistoryComponent
   */
  async getVoyages(vesselId: number, voyageId: number) {
    const result = await this.voyageService.getVoyagesByVesselId(vesselId).toPromise();
    const voyages = result ?? [];
    this.selectedVoyage = voyages.find(voyage => voyage.id === voyageId);
    return voyages;
  }

  /**
 * Handler for on delete event from side panel
 *
 * @param {*} event
 * @memberof LoadablePatternHistoryComponent
 */
  onDeleteLoadableStudy(event) {
    //If deleted loadable study is equal to currently selected loadable study then we need reset the selection
    if (event?.data?.id === this.loadableStudyId) {
      const loadableStudies = this.loadableStudies?.filter(loadableStudy => event?.data?.id !== loadableStudy?.id);
      if (loadableStudies && loadableStudies.length) {
        this.selectedLoadableStudy = loadableStudies[0];
      } else {
        this.loadableStudyId = 0;
      }
    }
    this.loadableStudies.splice(event?.index, 1);
  }

  /**
   * Fetch loadable study details
   *
   * @param {number} vesselId
   * @param {number} voyageId
   * @param {number} loadableStudyId
   * @memberof LoadablePatternHistoryComponent
   */
  async getLoadablePatterns(vesselId: number, voyageId: number, loadableStudyId: number) {
    const res = await this.vesselsApiService.getVesselsInfo().toPromise();
    this.vesselInfo = res[0] ?? <IVessel>{};
    this.voyages = await this.getVoyages(this.vesselId, this.voyageId);
    this.loadablePatternResponse = await this.loadablePatternApiService.getLoadablePatterns(vesselId, voyageId, loadableStudyId).toPromise();
    if (this.loadablePatternResponse.responseStatus.status === '200') {
      this.loadablePatterns = this.loadablePatternResponse.loadablePatterns;
      this.prevQuantitySelectedUnit = AppConfigurationService.settings.baseUnit;
      this.convertQuantityToSelectedUnit();
      this.tankLists = this.loadablePatternResponse?.tankLists;
      this.rearBallastTanks = this.loadablePatternResponse?.rearBallastTanks;
      this.centerBallastTanks = this.loadablePatternResponse?.centerBallastTanks;
      this.frontBallastTanks = this.loadablePatternResponse?.frontBallastTanks;
      this.loadablePatternCreatedDate = this.loadablePatternResponse?.loadablePatternCreatedDate;
      this.loadableStudyName = this.loadablePatternResponse.loadableStudyName;
      this.patternLoaded = true;
    }
    this.ngxSpinnerService.hide();
  }

  /**
   * set visibility of popup (show/hide)
   *
   * @param {*} event
   * @memberof LoadablePatternHistoryComponent
   */
  setPopupVisibility(emittedValue) {
    this.selectedLoadablePatternCargoDetail = null;
    this.display = emittedValue;
  }

  /**
   * set visibility of popup (show/hide)
   *
   * @param {*} event
   * @memberof LoadablePatternHistoryComponent
   */
  displayCommingleDetailPopup(emittedValue) {
    this.loadablePatternDetailsId = emittedValue?.loadablePatternDetailsId;
    this.selectedLoadablePatternCargoDetail = emittedValue?.loadablePatternCargoDetail?.isCommingle ? emittedValue?.loadablePatternCargoDetail : null;
    this.display = emittedValue?.loadablePatternCargoDetail?.isCommingle;
  }

  /**
   * Handler for loadable study chnage change
   *
   * @param {*} event
   * @memberof LoadablePatternHistoryComponent
   */
  onLoadableStudyChange(event) {
    this.loadableStudyId = event;
    this.selectedLoadableStudy = this.loadableStudyId ? this.loadableStudies.find(loadableStudy => loadableStudy.id === this.loadableStudyId) : this.loadableStudies[0];
  }

  /**
   * for navigating stowage plan
   *
   * @param {*} event
   * @memberof LoadablePatternHistoryComponent
   */
  viewPlan(loadablePattern: ILoadablePattern) {
    this.router.navigate([`/business/cargo-planning/loadable-plan/${this.vesselId}/${this.voyageId}/${this.loadableStudyId}/${loadablePattern.loadablePatternId}`]);
  }

  /**
   * Handler for unit change event
   *
   * @param {*} event
   * @memberof LoadablePatternHistoryComponent
   */
  onUnitChange() {
    this.prevQuantitySelectedUnit = this.currentQuantitySelectedUnit??AppConfigurationService.settings.baseUnit
    this.currentQuantitySelectedUnit = <QUANTITY_UNIT>localStorage.getItem('unit');
    if (this.prevQuantitySelectedUnit) {
      this.convertQuantityToSelectedUnit();
    }
  }

  /**
   * Method to convert to selected unit
   *
   * @memberof LoadablePatternHistoryComponent
   */
  convertQuantityToSelectedUnit() {
    const loadablePatterns = this.loadablePatterns?.map(pattern => {
      const loadablePatternCargoDetails = pattern.loadablePatternCargoDetails.map(cargo => {
        const orderedQuantity = this.quantityPipe.transform(cargo.orderedQuantity, this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit, cargo?.api);
        cargo.orderedQuantity = orderedQuantity ? orderedQuantity : 0;
        const quantity = this.quantityPipe.transform(cargo.quantity, this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit, cargo?.api);
        cargo.quantity = quantity ? quantity : 0;
        const difference = cargo.orderedQuantity - cargo.quantity;
        cargo.difference = difference ? Number(difference.toFixed(2)) : 0;
        return cargo;
      });
      pattern.loadablePatternCargoDetails = loadablePatternCargoDetails;
      const loadablePlanStowageDetails = pattern.loadablePlanStowageDetails?.map(loadableStowage => {
        if (loadableStowage) {
          const quantity = this.quantityPipe.transform(loadableStowage?.weightOrginal, this.baseUnit , this.currentQuantitySelectedUnit, loadableStowage?.api);
          loadableStowage.quantity = Number(quantity);
        }
        return loadableStowage;
      })
      pattern.loadablePlanStowageDetails = loadablePlanStowageDetails;

      return pattern;
    });
    if (loadablePatterns) {
      this.loadablePatterns = JSON.parse(JSON.stringify(loadablePatterns));
    }
  }

  /**
   * for confirm stowage plan
   *
   * @param {*} event
   * @memberof LoadablePatternHistoryComponent
   */
  async confirmPlan(loadablePatternId: number) {
    this.ngxSpinnerService.show();
    const result = await this.loadablePatternApiService.getConfirmStatus(this.vesselId, this.voyageId, this.loadableStudyId, loadablePatternId).toPromise();
    this.ngxSpinnerService.hide();
    let detail;

    if (result.confirmed) {
      detail = "LOADABLE_PATTERN_CONFIRM_DETAILS_NOT_CONFIRM";
    } else {
      detail = "LOADABLE_PATTERN_CONFIRM_DETAILS_CONFIRM";
    }

    const translationKeys = await this.translateService.get(['LOADABLE_PATTERN_CONFIRM_SUMMARY', detail, 'LOADABLE_PATTERN_CONFIRM_CONFIRM_LABEL', 'LOADABLE_PATTERN_CONFIRM_REJECT_LABEL', 'LOADABLE_PATTERN_CONFIRM_ERROR', 'LOADABLE_PATTERN_CONFIRM_STATUS_ERROR',
    , 'VALIDATE_AND_SAVE_ERROR','VALIDATE_AND_SAVE_INPROGESS', 'VALIDATE_AND_SAVE_FAILED', 'VALIDATE_AND_SAVE_PENDING']).toPromise();
    if(!result.validated) {
      this.messageService.add({ severity: 'error', summary: translationKeys['VALIDATE_AND_SAVE_ERROR'], detail: translationKeys['VALIDATE_AND_SAVE_PENDING'] });
      return;
    }
    else if([VALIDATION_AND_SAVE_STATUS.LOADABLE_PLAN_STARTED,VALIDATION_AND_SAVE_STATUS.LOADABLE_PLAN_FAILED].includes(result.loadablePatternStatusId)) {
      const errorDetails = VALIDATION_AND_SAVE_STATUS.LOADABLE_PLAN_STARTED === result.loadablePatternStatusId ? translationKeys['VALIDATE_AND_SAVE_INPROGESS'] : translationKeys['VALIDATE_AND_SAVE_FAILED'];
      this.messageService.add({ severity: 'error', summary: translationKeys['VALIDATE_AND_SAVE_ERROR'], detail:  errorDetails });
      return;
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
          const confirmResult = await this.loadablePatternApiService.confirm(this.vesselId, this.voyageId, this.loadableStudyId, loadablePatternId).toPromise();
          if (confirmResult.responseStatus.status === '200') {
            this.patternLoaded = false;
            this.getLoadableStudies(this.vesselId, this.voyageId, this.loadableStudyId);
            this.getLoadablePatterns(this.vesselId, this.voyageId, this.loadableStudyId);
          }
        } catch (errorResponse) {
          if (errorResponse?.error?.errorCode === 'ERR-RICO-110') {
            this.messageService.add({ severity: 'error', summary: translationKeys['LOADABLE_PATTERN_CONFIRM_ERROR'], detail: translationKeys['LOADABLE_PATTERN_CONFIRM_STATUS_ERROR'], life: 10000 });
          }
        }
      }
    });
  }


  /**
 * Handler for confirm plan button
 *
 * @param {number} loadablePatternId
 * @memberof LoadablePatternHistoryComponent
 */
  onConfirmPlanClick(loadablePatternId: number) {
    if (this.loadablePatternResponse.confirmPlanEligibility) {
      this.confirmPlan(loadablePatternId);
    } else {
      this.selectedLoadablePatternId = loadablePatternId;
      this.showPortRotationPopup = true;
    }
  }

  /**
 * Handler for new loadable study added
 *
 * @param {*} event
 * @memberof LoadablePatternHistoryComponent
 */
  onNewLoadableStudyAdded(event) {
    this.router.navigate([`business/cargo-planning/loadable-study-details/${this.vesselId}/${this.voyageId}/${event}`]);
  }

  /**
* Handler for pattern history button
*
* @memberof LoadablePatternHistoryComponent
*/
  patternHistory() {
    if (!this.isViewPattern) {
      this.router.navigate([`/business/cargo-planning/loadable-pattern-history/0/${this.vesselId}/${this.voyageId}/${this.loadableStudyId}`]);
    } else {
      this.openSidePane = !this.openSidePane
    }
  }

  /**
   * for view stability pop up
   *
   * @param {IStabilityParameter} stabilityParameters
   * @memberof LoadablePatternHistoryComponent
   */
  viewStability(stabilityParameters: IStabilityParameter) {
    this.stabilityParameters = stabilityParameters ? [stabilityParameters] : [];
    this.showStability = true;
  }

  /**
  * set visibility of stability popup (show/hide)
  *
  * @param {*} event
  * @memberof LoadablePatternHistoryComponent
  */
  setStabilityPopupVisibility(emittedValue) {
    this.showStability = emittedValue;
  }

  /**
  * set visibility of pattern view more popup (show/hide)
  *
  * @param {*} event
  * @memberof LoadablePatternHistoryComponent
  */
  displayPatternViewMorePopup(emittedValue) {
    this.selectedLoadablePattern = emittedValue;
    this.viewMore = true;
  }

  /**
  * set visibility of stability pattern view more popup (show/hide)
  *
  * @param {*} event
  * @memberof LoadablePatternHistoryComponent
  */
  setPatternViewMorePopupVisibility(emittedValue) {
    this.viewMore = emittedValue;
    if(!emittedValue) {
      this.prevQuantitySelectedUnit = this.currentQuantitySelectedUnit;
      this.currentQuantitySelectedUnit = <QUANTITY_UNIT>localStorage.getItem('unit');
      this.convertQuantityToSelectedUnit();
    }
  }

  /**
  * Method to get cargos
  *
  * @memberof LoadablePatternHistoryComponent
*/
  async getCargos() {
    const cargos: ICargoResponseModel = await this.loadablePatternApiService.getCargos().toPromise();
    if (cargos.responseStatus.status === '200') {
      this.cargos = cargos.cargos;
    }
  }


  /**
  * Method to close port rotation pop up
  *
  * @memberof LoadablePatternHistoryComponent
  */
  closePortRotationPopup(e) {
    this.showPortRotationPopup = e.hide;
    if (e.success) {
      this.loadablePatternResponse.confirmPlanEligibility = false;
      this.confirmPlan(this.selectedLoadablePatternId);
    }
  }

}

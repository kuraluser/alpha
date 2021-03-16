import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ICargoTank, Voyage, VOYAGE_STATUS, LOADABLE_STUDY_STATUS } from '../../core/models/common.model';
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
import { ConfirmationAlertService } from '../../../shared/components/confirmation-alert/confirmation-alert.service';

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
  loadablePatternCreatedDate: string;
  loadableStudyName: string;
  display = false;
  isViewPattern: boolean;
  selectedLoadablePatterns: ILoadablePatternCargoDetail;
  loadablePatternPermissionContext: IPermissionContext;
  loadablePatternDetailsId: number;
  currentQuantitySelectedUnit = <QUANTITY_UNIT>localStorage.getItem('unit');
  prevQuantitySelectedUnit: QUANTITY_UNIT;
  showStability = false;
  stabilityParameters: IStabilityParameter[];
  LOADABLE_STUDY_STATUS = LOADABLE_STUDY_STATUS;
  VOYAGE_STATUS = VOYAGE_STATUS;

  constructor(private vesselsApiService: VesselsApiService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private voyageService: VoyageService,
    private ngxSpinnerService: NgxSpinnerService,
    private loadableStudyListApiService: LoadableStudyListApiService,
    private loadablePatternApiService: LoadablePatternHistoryApiService,
    private permissionsService: PermissionsService,
    private quantityPipe: QuantityPipe,
    private confirmationAlertService: ConfirmationAlertService) { }

  /**
   * Component lifecycle ngOnit
   *
   * @returns {Promise<void>}
   * @memberof LoadablePatternHistoryComponent
   */
  async ngOnInit(): Promise<void> {
    const loadablePatternPermission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['LoadablePatternHistoryComponent'], false);
    this.loadablePatternPermissionContext = { key: AppConfigurationService.settings.permissionMapping['LoadablePatternHistoryComponent'], actions: [PERMISSION_ACTION.VIEW] };
    this.activatedRoute.paramMap.subscribe(params => {
      this.isViewPattern = Number(params.get('isViewPattern')) === 0 ? true : false;
      this.vesselId = Number(params.get('vesselId'));
      this.voyageId = Number(params.get('voyageId'));
      this.loadableStudyId = Number(params.get('loadableStudyId'));
      if (this.isViewPattern) {
        this.getLoadableStudies(this.vesselId, this.voyageId, this.loadableStudyId);
      }
      this.getLoadablePatterns(this.vesselId, this.voyageId, this.loadableStudyId);
    });
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
    this.selectedLoadableStudy = loadableStudyId ? this.loadableStudies.find(loadableStudy => loadableStudy.id === loadableStudyId) : this.loadableStudies[0];
    this.ngxSpinnerService.hide();
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
    this.ngxSpinnerService.show();
    const res = await this.vesselsApiService.getVesselsInfo().toPromise();
    this.vesselInfo = res[0] ?? <IVessel>{};
    this.voyages = await this.getVoyages(this.vesselId, this.voyageId);
    this.loadablePatternResponse = await this.loadablePatternApiService.getLoadablePatterns(vesselId, voyageId, loadableStudyId).toPromise();
    if (this.loadablePatternResponse.responseStatus.status === '200') {
      this.loadablePatterns = this.loadablePatternResponse.loadablePatterns;
      this.prevQuantitySelectedUnit = AppConfigurationService.settings.baseUnit;
      this.convertQuantityToSelectedUnit();
      this.tankLists = this.loadablePatternResponse.tankLists;
      this.loadablePatternCreatedDate = this.loadablePatternResponse.loadablePatternCreatedDate;
      this.loadableStudyName = this.loadablePatternResponse.loadableStudyName;
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
    this.selectedLoadablePatterns = null;
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
    this.selectedLoadablePatterns = emittedValue?.loadablePatternCargoDetail?.isCommingle ? emittedValue?.loadablePatternCargoDetail : null;
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
    this.getLoadableStudies(this.vesselId, this.voyageId, this.loadableStudyId);
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
    this.prevQuantitySelectedUnit = this.currentQuantitySelectedUnit;
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
        cargo.orderedQuantity = orderedQuantity ? Number(orderedQuantity.toFixed(2)) : 0;
        const quantity = this.quantityPipe.transform(cargo.quantity, this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit, cargo?.api);
        cargo.quantity = quantity ? Number(quantity.toFixed(2)) : 0;
        const difference = cargo.orderedQuantity - cargo.quantity;
        cargo.difference = difference ? Number(difference.toFixed(2)) : 0;
        return cargo;
      });
      pattern.loadablePatternCargoDetails = loadablePatternCargoDetails;

      return pattern;
    });
    this.loadablePatterns = loadablePatterns;
  }

  /**
   * for confirm stowage plan
   *
   * @param {*} event
   * @memberof LoadablePatternHistoryComponent
   */
  async confirmPlan(loadablePattern: ILoadablePattern) {
    this.ngxSpinnerService.show();
    const result = await this.loadablePatternApiService.getConfirmStatus(this.vesselId, this.voyageId, this.loadableStudyId, loadablePattern?.loadablePatternId).toPromise();
    this.ngxSpinnerService.hide();
    let detail;
    if (result.confirmed) {
      detail = "LOADABLE_PATTERN_CONFIRM_DETAILS_NOT_CONFIRM";
    } else {
      detail = "LOADABLE_PATTERN_CONFIRM_DETAILS_CONFIRM";
    }
    this.confirmationAlertService.add({ key: 'confirmation-alert', sticky: true, severity: 'warn', summary: 'LOADABLE_PATTERN_CONFIRM_SUMMARY', detail: detail, data: { confirmLabel: 'LOADABLE_PATTERN_CONFIRM_CONFIRM_LABEL', rejectLabel: 'LOADABLE_PATTERN_CONFIRM_REJECT_LABEL' } });
    this.confirmationAlertService.confirmAlert$.pipe().subscribe(async (response) => {
      if (response) {
        const confirmResult = await this.loadablePatternApiService.confirm(this.vesselId, this.voyageId, this.loadableStudyId, loadablePattern?.loadablePatternId).toPromise();
        if (confirmResult.responseStatus.status === '200') {
          this.getLoadablePatterns(this.vesselId, this.voyageId, this.loadableStudyId);
        }
      }
    })
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
    if(!this.isViewPattern){
      this.router.navigate([`/business/cargo-planning/loadable-pattern-history/0/${this.vesselId}/${this.voyageId}/${this.loadableStudyId}`]);
    }else{
      this.openSidePane = !this.openSidePane
    }
  }

  /**
   * for view stability pop up
   *
   * @param {IStabilityParameter} stabilityParameters
   * @memberof LoadablePatternHistoryComponent
   */
  viewStability(stabilityParameters : IStabilityParameter){
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

}

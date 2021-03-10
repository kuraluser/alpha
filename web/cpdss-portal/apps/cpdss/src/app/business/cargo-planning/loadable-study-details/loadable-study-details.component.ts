import { Component, OnDestroy, OnInit } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { ICargo, LOADABLE_STUDY_DETAILS_TABS } from '../models/cargo-planning.model';
import { LoadableStudyDetailsTransformationService } from '../services/loadable-study-details-transformation.service';
import { LoadableStudyDetailsApiService } from '../services/loadable-study-details-api.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Voyage, IPort, LOADABLE_STUDY_STATUS, VOYAGE_STATUS } from '../../core/models/common.model';
import { VoyageService } from '../../core/services/voyage.service';
import { IDischargingPortIds, LoadableStudy } from '../models/loadable-study-list.model';
import { LoadableStudyListApiService } from '../services/loadable-study-list-api.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { IVessel } from '../../core/models/vessel-details.model';
import { VesselsApiService } from '../../core/services/vessels-api.service';
import { TranslateService } from '@ngx-translate/core';
import { MessageService } from 'primeng/api';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';
import { PermissionsService } from '../../../shared/services/permissions/permissions.service';
import { IPermissionContext, PERMISSION_ACTION, QUANTITY_UNIT } from '../../../shared/models/common.model';
import { LoadableQuantityModel } from '../models/loadable-quantity.model';
import { LoadableQuantityApiService } from '../services/loadable-quantity-api.service';
import { IPermission } from '../../../shared/models/user-profile.model';
import { takeUntil , switchMap } from 'rxjs/operators';


/**
 * Component class for loadable study details component
 *
 * @export
 * @class LoadableStudyDetailsComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-loadable-study-details',
  templateUrl: './loadable-study-details.component.html',
  styleUrls: ['./loadable-study-details.component.scss']
})
export class LoadableStudyDetailsComponent implements OnInit, OnDestroy {

  get selectedLoadableStudy(): LoadableStudy {
    return this._selectedLoadableStudy;
  }
  set selectedLoadableStudy(selectedLoadableStudy: LoadableStudy) {
    this._selectedLoadableStudy = selectedLoadableStudy;
    this.isPatternGenerated = (this._selectedLoadableStudy?.statusId === 3 || this._selectedLoadableStudy?.statusId === 2) ? true : false;
    this.isPatternOpenOrNoplan = (this._selectedLoadableStudy?.statusId === 1 || this._selectedLoadableStudy?.statusId === 6) ? false : true;
    if (this._selectedLoadableStudy?.statusId === 4) {
      const modifiedDate = new Date(selectedLoadableStudy?.loadableStudyStatusLastModifiedTime);
      const addFiveMinute = new Date(modifiedDate.getTime() + 300000);
      const now = new Date();
      if (addFiveMinute < now) {
        this.isPatternOpenOrNoplan = false;
      }
    }
    this.loadableStudyId = selectedLoadableStudy ? selectedLoadableStudy?.id : this.loadableStudies?.length ? this.loadableStudies[0]?.id : 0;
    this.getLoadableStudyDetails(this.vesselId, this.voyageId, selectedLoadableStudy?.id);
  }

  private _selectedLoadableStudy: LoadableStudy;
  private ngUnsubscribe: Subject<any> = new Subject();

  LOADABLE_STUDY_DETAILS_TABS = LOADABLE_STUDY_DETAILS_TABS;
  dischargingPorts: IPort[] = [];//TODO to be populated form loadable study details
  dischargingPortsNames: string;//TODO to be populated form loadable study details
  totalQuantity: number;
  ports: IPort[];
  cargoNominationComplete$: Observable<boolean>;
  ohqComplete$: Observable<boolean>;
  voyageId: number;
  loadableStudyId: number;
  vesselId: number;
  vesselInfo: IVessel;
  selectedVoyage: Voyage;
  voyages: Voyage[];
  loadableStudies: LoadableStudy[];
  openSidePane = true;
  portsComplete$: Observable<boolean>;
  selectedTab: string;
  cargoNominationTabPermissionContext: IPermissionContext;
  portsTabPermissionContext: IPermissionContext;
  addCargoBtnPermissionContext: IPermissionContext;
  addPortBtnPermissionContext: IPermissionContext;
  displayLoadableQuntity: boolean;
  loadableQuantityNew: string;
  loadableQuantityModel: LoadableQuantityModel;
  cargoNominationTabPermission: IPermission;
  portsTabPermission: IPermission;
  ohqTabPermissionContext: IPermissionContext;
  ohqTabPermission: IPermission;
  obqTabPermissionContext: IPermissionContext;
  loadableQuantityPermissionContext: IPermissionContext;
  displayCommingle: boolean;
  loadableQuantityPermission: IPermission;
  showCommingleButton = false;
  addCommingleBtnPermissionContext: IPermissionContext;
  obqComplete$: Observable<boolean>;
  errorMesages: any;
  isSelectedDischargePort = true;
  selectedDischargeCargo: ICargo;
  dischargeCargos: ICargo[] = [];
  currentUnit = <QUANTITY_UNIT>localStorage.getItem('unit');
  baseUnit = this.loadableStudyDetailsApiService.baseUnit;
  isPatternGenerated = false;
  isGenerateClicked = false;
  isPatternOpenOrNoplan = false;
  LOADABLE_STUDY_STATUS = LOADABLE_STUDY_STATUS;
  VOYAGE_STATUS = VOYAGE_STATUS;

  constructor(public loadableStudyDetailsApiService: LoadableStudyDetailsApiService,
    private loadableStudyDetailsTransformationService: LoadableStudyDetailsTransformationService,
    private loadableStudyListApiService: LoadableStudyListApiService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private voyageService: VoyageService,
    private vesselsApiService: VesselsApiService,
    private ngxSpinnerService: NgxSpinnerService,
    private translateService: TranslateService,
    private messageService: MessageService,
    private permissionsService: PermissionsService,
    private loadableQuantityApiService: LoadableQuantityApiService) {
  }

  ngOnInit(): void {
    this.initSubsciptions();
    this.listenEvents();
    this.activatedRoute.paramMap
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(params => {
        this.vesselId = Number(params.get('vesselId'));
        this.voyageId = Number(params.get('voyageId'));
        this.loadableStudyId = Number(params.get('loadableStudyId'));
        this.loadableStudies = null;
        this.loadableQuantityNew = '0';
        this.loadableStudyDetailsTransformationService.setCargoNominationValidity(false);
        this.loadableStudyDetailsTransformationService.setTotalQuantityCargoNomination(0);
        this.selectedTab = LOADABLE_STUDY_DETAILS_TABS.CARGONOMINATION;
        this.getLoadableStudies(this.vesselId, this.voyageId, this.loadableStudyId);
      });
    this.errorMesages = this.loadableStudyDetailsTransformationService.setValidationErrorMessage();
    this.setPagePermissionContext();
  }

  /**
   * NgOnDestroy lifecycle hook
   *
   * @memberof LoadableStudyDetailsComponent
   */
  ngOnDestroy() {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  /**
   * Set page permission
   *
   * @memberof LoadableStudyDetailsComponent
   */
  setPagePermissionContext() {
    this.cargoNominationTabPermission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['CargoNominationComponent'], false);
    this.cargoNominationTabPermissionContext = { key: AppConfigurationService.settings.permissionMapping['CargoNominationComponent'], actions: [PERMISSION_ACTION.VIEW] };

    this.portsTabPermission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['PortsComponent'], false);
    this.portsTabPermissionContext = { key: AppConfigurationService.settings.permissionMapping['PortsComponent'], actions: [PERMISSION_ACTION.VIEW] };

    this.ohqTabPermission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['OnHandQuantityComponent'], false);
    this.ohqTabPermissionContext = { key: AppConfigurationService.settings.permissionMapping['OnHandQuantityComponent'], actions: [PERMISSION_ACTION.VIEW] };

    this.loadableQuantityPermission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['LoadableQuantityComponent'], false);
    this.loadableQuantityPermissionContext = { key: AppConfigurationService.settings.permissionMapping['LoadableQuantityComponent'], actions: [PERMISSION_ACTION.VIEW] };

    const obqTabPermission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['OnBoardQuantityComponent'], false);
    this.obqTabPermissionContext = { key: AppConfigurationService.settings.permissionMapping['OnBoardQuantityComponent'], actions: [PERMISSION_ACTION.VIEW] };

    if (this.cargoNominationTabPermission === undefined || this.cargoNominationTabPermission?.view) {
      this.selectedTab = LOADABLE_STUDY_DETAILS_TABS.CARGONOMINATION;
    } else if (this.portsTabPermission?.view) {
      this.selectedTab = LOADABLE_STUDY_DETAILS_TABS.PORTS;
    } else if (this.ohqTabPermission?.view) {
      this.selectedTab = LOADABLE_STUDY_DETAILS_TABS.OHQ;
    } else if (obqTabPermission?.view) {
      this.selectedTab = LOADABLE_STUDY_DETAILS_TABS.OBQ;
    } else {
      this.selectedTab = LOADABLE_STUDY_DETAILS_TABS.CARGONOMINATION;
    }

    this.addCargoBtnPermissionContext = { key: AppConfigurationService.settings.permissionMapping['CargoNominationComponent'], actions: [PERMISSION_ACTION.VIEW, PERMISSION_ACTION.ADD] };
    this.addPortBtnPermissionContext = { key: AppConfigurationService.settings.permissionMapping['PortsComponent'], actions: [PERMISSION_ACTION.VIEW, PERMISSION_ACTION.ADD] };
    this.addCommingleBtnPermissionContext = { key: AppConfigurationService.settings.permissionMapping['CargoNominationComponent'], actions: [PERMISSION_ACTION.VIEW, PERMISSION_ACTION.EDIT] };
  }

  /**
   * Method to fetch all voyages in the vessel
   *
   * @param {number} vesselId
   * @param {number} voyageId
   * @memberof LoadableStudyDetailsComponent
   */
  async getVoyages(vesselId: number, voyageId: number) {
    const result = await this.voyageService.getVoyagesByVesselId(vesselId).toPromise();
    const voyages = result ?? [];
    this.selectedVoyage = voyages.find(voyage => voyage.id === voyageId);
    return voyages;
  }

  /**
   * Method to fetch all loadableStudies
   *
   * @param {number} vesselId
   * @param {number} voyageId
   * @param {number} loadableStudyId
   * @memberof LoadableStudyDetailsComponent
   */
  async getLoadableStudies(vesselId: number, voyageId: number, loadableStudyId: number) {
    this.ngxSpinnerService.show();
    const res = await this.vesselsApiService.getVesselsInfo().toPromise();
    this.vesselInfo = res[0] ?? <IVessel>{};
    this.voyages = await this.getVoyages(this.vesselId, this.voyageId);
    this.ports = await this.getPorts();
    const result = await this.loadableStudyListApiService.getLoadableStudies(vesselId, voyageId).toPromise();
    this.loadableStudies = result?.loadableStudies ?? [];
    if (this.loadableStudies.length) {
      this.setProcessingLoadableStudyActions(0, 0);
      this.selectedLoadableStudy = loadableStudyId ? this.loadableStudies.find(loadableStudy => loadableStudy.id === loadableStudyId) : this.loadableStudies[0];
      if (sessionStorage.getItem('loadableStudyInfo')) {
        this.displayLoadableQuntity = true;
        sessionStorage.removeItem('loadableStudyInfo');
      }
    } else {
      this.selectedLoadableStudy = null;
    }
    this.ngxSpinnerService.hide();
  }

  /**
   * Fetch loadable study details
   *
   * @param {number} vesselId
   * @param {number} voyageId
   * @param {number} loadableStudyId
   * @memberof LoadableStudyDetailsComponent
   */
  async getLoadableStudyDetails(vesselId: number, voyageId: number, loadableStudyId: number) {
    const translationKeys = await this.translateService.get(['TOTAL_QUANTITY_ERROR', 'TOTAL_QUANTITY_ERROR_DETAILS']).toPromise();
    this.ngxSpinnerService.show();
    if (this.selectedLoadableStudy?.dischargingCargoId) {
      this.selectedDischargeCargo = { id: this.selectedLoadableStudy?.dischargingCargoId }
    } else {
      this.selectedDischargeCargo = null;
    }
    if (this.selectedDischargeCargo && this.dischargeCargos?.length) {
      this.selectedDischargeCargo = this.dischargeCargos.find(cargo => cargo.id === this.selectedDischargeCargo.id)
    }
    this.dischargingPorts = this.selectedLoadableStudy?.dischargingPortIds?.map(portId => this.ports.find(port => port?.id === portId));
    if(!this.dischargingPorts){
      this.dischargingPorts = [];
    } 
    this.dischargingPortsNames = this.dischargingPorts?.map(port => port?.name).join(", ");
    // if no loadable study is selected set 1st loadable study as selected one and reload
    if (!loadableStudyId) {
      this.router.navigate([`business/cargo-planning/loadable-study-details/${vesselId}/${voyageId}/${this.loadableStudyId}`]);
    } else {
      const loadableQuantityResult = await this.loadableQuantityApiService.getLoadableQuantity(this.vesselId, this.voyageId, this.selectedLoadableStudy.id).toPromise();
      if (loadableQuantityResult.responseStatus.status === "200") {
        loadableQuantityResult.loadableQuantity.totalQuantity === '' ? this.getSubTotal(loadableQuantityResult) : this.loadableQuantityNew = loadableQuantityResult.loadableQuantity.totalQuantity;
        if (Number(this.totalQuantity) > Number(this.loadableQuantityNew)) {
          this.messageService.add({ severity: 'error', summary: translationKeys['TOTAL_QUANTITY_ERROR'], detail: translationKeys['TOTAL_QUANTITY_ERROR_DETAILS'] });
        }
        this.loadableQuantityModel = loadableQuantityResult;
      }
    }
    this.ngxSpinnerService.hide();
  }

  /**
   * Triggering add cargo nomination
   *
   * @memberof LoadableStudyDetailsComponent
   */
  addCargoNomination() {
    this.loadableStudyDetailsTransformationService.addCargoNomination();
  }

  /**
   * Initialise all subscription in this page
   *
   * @private
   * @memberof LoadableStudyDetailsComponent
   */
  private async initSubsciptions() {
    this.loadableStudyDetailsTransformationService.totalQuantityCargoNomination$.pipe(takeUntil(this.ngUnsubscribe)).subscribe(totalQuantity => {
      this.totalQuantity = totalQuantity;
    });
    this.cargoNominationComplete$ = this.loadableStudyDetailsTransformationService.cargoNominationValidity$;
    this.portsComplete$ = this.loadableStudyDetailsTransformationService.portValidity$;
    this.ohqComplete$ = this.loadableStudyDetailsTransformationService.ohqValidity$;
    this.obqComplete$ = this.loadableStudyDetailsTransformationService.obqValidity$;
    this.loadableStudyDetailsApiService.cargoNominationChange.asObservable()
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(() => {
        this.onCargoNominationChange();
      })
      this.loadableStudyDetailsTransformationService.obqUpdate$?.pipe(
        switchMap(() => {
          return this.loadableQuantityApiService.getLoadableQuantity(this.vesselId, this.voyageId, this.selectedLoadableStudy.id);
        })
      ).subscribe((loadableQuantityResult) => {
        if (loadableQuantityResult.responseStatus.status === "200") {
          loadableQuantityResult.loadableQuantity.totalQuantity === '' ? this.getSubTotal(loadableQuantityResult) : this.loadableQuantityNew = loadableQuantityResult.loadableQuantity.totalQuantity;
        }
      });
  }

  /**
   * Listen events in this page
   *
   * @private
   * @memberof LoadableStudyDetailsComponent
   */
  private async listenEvents() {
    navigator.serviceWorker.addEventListener('message', async event => {
      if (event.data.type === 'loadable-pattern-processing' && this.router.url.includes('loadable-study-details')) {
        if (event.data.pattern?.loadableStudyId === this.loadableStudyId) {
          this.processingMessage();
        } else {
          this.messageService.clear("process");
        }
      }
      else if (event.data.type === 'loadable-pattern-completed') {
        if (event.data.pattern?.loadableStudyId === this.loadableStudyId) {
          this.isPatternGenerated = true;
        }
        this.generatedMessage(event.data.pattern.selectedVoyageNo, event.data.pattern.selectedLoadableStudyName);
      }
      else if (event.data.type === 'loadable-pattern-no-solution') {
        if (event.data.pattern?.loadableStudyId === this.loadableStudyId) {
          this.isPatternOpenOrNoplan = false;
          this.isPatternGenerated = false;
          this.isGenerateClicked = false;
        }
        this.noPlanMessage(event.data.pattern.selectedVoyageNo, event.data.pattern.selectedLoadableStudyName)
      }
      else if (event.data.type === 'loadable-pattern-no-response') {
        if (event.data.pattern?.loadableStudyId === this.loadableStudyId) {
          this.isPatternOpenOrNoplan = false;
          this.isPatternGenerated = false;
          this.isGenerateClicked = false;
        }
        this.noResponseMessage(event.data.pattern.selectedVoyageNo, event.data.pattern.selectedLoadableStudyName);
      }
      this.setProcessingLoadableStudyActions(event.data?.pattern?.loadableStudyId, event.data.statusId);
    });
  }

  /**
   * Enable/ Disable actions of currently processing/processed loadable study
   *
   * @param {*} event
   * @memberof LoadableStudyDetailsComponent
   */
  setProcessingLoadableStudyActions(loadableStudyId: number, statusId: number) {
    this.loadableStudies = this.loadableStudies.map(loadableStudy => {
      if (loadableStudyId === loadableStudy?.id) {
        if ([4, 5].includes(statusId) && this.router.url.includes('loadable-study-details')) {
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
        loadableStudy.isEditable = (loadableStudy?.statusId === 3 || loadableStudy?.statusId === 2) ? false : true;
        loadableStudy.isDeletable = (loadableStudy?.statusId === 3 || loadableStudy?.statusId === 2) ? false : true;
      }
      return loadableStudy;
    });
  }

  /**
   * Method for fetching all ports from master
   *
   * @private
   * @memberof LoadableStudyDetailsComponent
   */
  private async getPorts(): Promise<IPort[]> {
    const result = await this.loadableStudyDetailsApiService.getPorts().toPromise();
    return result;
  }

  /**
   * Handler for discharge port dropdown change
   *
   * @param {Event} event
   * @memberof LoadableStudyDetailsComponent
   */
  async onDischargePortChange(event) {
    let isPortSelected;
    this.dischargingPorts.map((dischargingPort, index) => { if (dischargingPort.id === event.itemValue.id) { isPortSelected = index } });
    if (isPortSelected >= 0) {
      this.dischargingPorts.splice(isPortSelected, 1);
      this.updatingDischargingPort();
    } else if (this.dischargingPorts.length < 5) {
      this.dischargingPorts.push(event.itemValue);
      this.updatingDischargingPort();
    }
  }

  /**
   * updating discharging port
   * @memberof LoadableStudyDetailsComponent
   */
  updatingDischargingPort() {
    const sucessKeys = ['LOADABLE_STUDY_DISCHARGING_PORT_UPDATE_SUCCESS', 'LOADABLE_STUDY_DISCHARGING_PORT_UPDATE_SUCCESSFULLY']
    this.updateDischargeData(sucessKeys);
  }

  /**
   * Handler for voyage dropdown change
   *
   * @param {*} event
   * @memberof LoadableStudyDetailsComponent
   */
  onVoyageChange(event) {
    this.voyageId = event?.value?.id;
    this.loadableStudies = null;
    this.loadableStudyId = 0;
    this.loadableQuantityNew = '0';
    this.loadableStudyDetailsTransformationService.setTotalQuantityCargoNomination(0);
    this.loadableStudyDetailsTransformationService.setCargoNominationValidity(false);
    this.loadableStudyDetailsTransformationService.setPortValidity(false);
    this.loadableStudyDetailsTransformationService.setOHQValidity(false);
    this.loadableStudyDetailsTransformationService.setObqValidity(false);
    this.selectedTab = LOADABLE_STUDY_DETAILS_TABS.CARGONOMINATION;
    this.selectedLoadableStudy = null;
    this.initSubsciptions();
    this.router.navigate([`business/cargo-planning/loadable-study-details/${this.vesselId}/${this.voyageId}/0`]);
  }

  /**
  * Triggering add port
  *
  * @memberof LoadableStudyDetailsComponent
  */
  addPort() {
    this.loadableStudyDetailsTransformationService.addPort();
  }

  /**
   * Handler for on delete event from side panel
   *
   * @param {*} event
   * @memberof LoadableStudyDetailsComponent
   */
  onDeleteLoadableStudy(event) {
    this.loadableStudies.splice(event?.index, 1);
    //If deleted loadable study is equal to currently selected loadable study then we need reset the selection
    if (event?.data?.id === this.loadableStudyId) {
      if (this.loadableStudies && this.loadableStudies.length) {
        this.selectedLoadableStudy = this.loadableStudies[0];
      } else {
        this.loadableStudyId = 0;
        this.loadableQuantityNew = '0';
        this.loadableStudyDetailsTransformationService.setTotalQuantityCargoNomination(0);
        this.loadableStudyDetailsTransformationService.setCargoNominationValidity(false);
        this.selectedTab = LOADABLE_STUDY_DETAILS_TABS.CARGONOMINATION;
        this.selectedLoadableStudy = null;
        this.router.navigate([`business/cargo-planning/loadable-study-details/${this.vesselId}/${this.voyageId}/0`]);
      }
    }
  }

  /**
   * On Click of tab in lodable study details page
   *
   * @param {string} selectedTab
   * @memberof LoadableStudyDetailsComponent
   */
  async onTabClick(selectedTab: string) {
    if (!this?.loadableStudies?.length) {
      this.selectedTab = selectedTab;
      return;
    }
    const translationKeys = await this.translateService.get(['CARGONOMINATION_DISCHARGE_PORT_ERROR_SUMMARY', 'CARGONOMINATION_DISCHARGE_PORT_ERROR_DETAILS']).toPromise();
    if (selectedTab !== LOADABLE_STUDY_DETAILS_TABS.CARGONOMINATION) {
      if (this.dischargingPorts?.length > 0) {
        this.selectedTab = selectedTab;
        this.isSelectedDischargePort = true;
      }
      else {
        this.isSelectedDischargePort = false;
        this.messageService.add({ severity: 'error', summary: translationKeys['CARGONOMINATION_DISCHARGE_PORT_ERROR_SUMMARY'], detail: translationKeys['CARGONOMINATION_DISCHARGE_PORT_ERROR_DETAILS'] });
      }
    }
    else {
      this.selectedTab = selectedTab;
    }

  }

  /**
   * Show loadable quantity popup
   */
  showLoadableQuantityPopup() {
    this.displayLoadableQuntity = true;
  }

  /**
   * Value from Loadable Quantity Popup
   */
  displayPopUpTab(displayNew_: boolean) {
    this.displayLoadableQuntity = displayNew_;
  }


  /**
   * Loadable quantity  from loadable quantity popup
   */
  async loadableQuantity(newloadableQuantity: string) {
    this.loadableQuantityNew = newloadableQuantity;
    const translationKeys = await this.translateService.get(['TOTAL_QUANTITY_ERROR', 'TOTAL_QUANTITY_ERROR_DETAILS']).toPromise();
    if (Number(this.totalQuantity) > Number(this.loadableQuantityNew)) {
      this.messageService.add({ severity: 'error', summary: translationKeys['TOTAL_QUANTITY_ERROR'], detail: translationKeys['TOTAL_QUANTITY_ERROR_DETAILS'] });
    }
  }

  /**
   * Display commingle popup
   */
  showComminglePopup() {
    this.displayCommingle = true;
  }

  /**
   * Value from commingle popup
   */
  displayComminglePopUpTab(displayNew_: boolean) {
    this.displayCommingle = displayNew_;
  }

  /**
 * Cargo nomination update event haandler get value from commingle button
 */
  async cargoNominationUpdate(event) {
    this.showCommingleButton = event;
    // Show alert if total quantity exceeds loadable quantity
    const translationKeys = await this.translateService.get(['TOTAL_QUANTITY_ERROR', 'TOTAL_QUANTITY_ERROR_DETAILS']).toPromise();
    if (Number(this.totalQuantity) > Number(this.loadableQuantityNew)) {
      this.messageService.clear();
      this.messageService.add({ severity: 'error', summary: translationKeys['TOTAL_QUANTITY_ERROR'], detail: translationKeys['TOTAL_QUANTITY_ERROR_DETAILS'] });
    }

  }

  /**
 * Take the user to particular pattern history
 */
  navigateToPatternHistory(isViewPattern) {
    this.router.navigate([`/business/cargo-planning/loadable-pattern-history/${isViewPattern}/${this.vesselId}/${this.voyageId}/${this.loadableStudyId}`]);
  }

  /**
   * Handler for loadable study chnage change
   *
   * @param {*} event
   * @memberof LoadableStudyDetailsComponent
   */
  onLoadableStudyChange(event) {
    if (event) {
      this.loadableStudyId = event;
      this.loadableStudyDetailsTransformationService.setCargoNominationValidity(false);
      this.loadableStudyDetailsTransformationService.setPortValidity(false);
      this.loadableStudyDetailsTransformationService.setOHQValidity(false);
      this.loadableStudyDetailsTransformationService.setObqValidity(false);
      this.isGenerateClicked = false;
      this.initSubsciptions();
      this.selectedTab = LOADABLE_STUDY_DETAILS_TABS.CARGONOMINATION;
      this.getLoadableStudies(this.vesselId, this.voyageId, this.loadableStudyId);
    }
  }

  /**
   * Get error if no discharge port is selected
   */
  fieldError() {
    if (this.dischargingPorts?.length > 0) {
      return null;
    }
    else {
      return { required: true };
    }
  }

  /**
   * Actions to do when cargo nomination data changes
   */
  onCargoNominationChange() {
    this.dischargeCargos = []
    this.loadableStudyDetailsApiService.cargoNominations.forEach(cargoNomination => {
      if (!cargoNomination.isAdd && this.dischargeCargos.indexOf(cargoNomination.cargo.value) === -1)
        this.dischargeCargos.push(cargoNomination.cargo.value)
    });
    if (this.selectedDischargeCargo) {
      this.selectedDischargeCargo = this.dischargeCargos.find(cargo => cargo.id === this.selectedDischargeCargo.id)
    }
  }


  /**
 * Handler for discharge cargo dropdown change
 *
 * @param {Event} event
 * @memberof LoadableStudyDetailsComponent
 */
  async onDischargeCargoChange(event) {
    const sucessKeys = ['LOADABLE_STUDY_DISCHARGING_CARGO_UPDATE_SUCCESS', 'LOADABLE_STUDY_DISCHARGING_CARGO_UPDATE_SUCCESSFULLY']
    this.updateDischargeData(sucessKeys)
  }

  /**
   * Method to save discharge data for loadable study
   *
   * @param {string[]} sucessKeys
   * @memberof LoadableStudyDetailsComponent
   */
  async updateDischargeData(sucessKeys: string[]) {
    this.ngxSpinnerService.show();
    this.selectedLoadableStudy.dischargingPortIds = this.dischargingPorts?.map(port => port.id);
    const dischargingPortIds: IDischargingPortIds = {
      portIds: this.selectedLoadableStudy.dischargingPortIds,
      dischargingCargoId: this.selectedDischargeCargo?.id ?? null
    };
    const translationKeys = await this.translateService.get(sucessKeys).toPromise();
    const res = await this.loadableStudyDetailsApiService.setLoadableStudyDischargingPorts(this.vesselId, this.voyageId, this.loadableStudyId, dischargingPortIds).toPromise();
    if (res?.responseStatus?.status === "200") {
      this.messageService.add({ severity: 'success', summary: translationKeys[sucessKeys[0]], detail: translationKeys[sucessKeys[1]] });
    }
    this.dischargingPortsNames = this.dischargingPorts?.map(port => port?.name).join(", ");
    this.ngxSpinnerService.hide();
  }

  /**
  * Calculation for subtotal
  */
  getSubTotal(loadableQuantityResult: any) {
    const loadableQuantity = loadableQuantityResult.loadableQuantity;
    let subTotal = 0;
    if (loadableQuantityResult.caseNo === 1 || loadableQuantityResult.caseNo === 2) {
      subTotal = Number(loadableQuantity.dwt)
        + Number(loadableQuantity.saggingDeduction)
        - Number(loadableQuantity.estFOOnBoard) - Number(loadableQuantity.estDOOnBoard)
        - Number(loadableQuantity.estFreshWaterOnBoard) - Number(loadableQuantity.boilerWaterOnBoard)
        - Number(loadableQuantity.ballast) - Number(loadableQuantity.constant)
        - Number(loadableQuantity.otherIfAny === '' ? 0 : loadableQuantity.otherIfAny);
      this.getTotalLoadableQuantity(subTotal, loadableQuantityResult);
    }
    else {
      subTotal = Number(loadableQuantity.dwt) + Number(loadableQuantity.saggingDeduction) + Number(loadableQuantity.sgCorrection)
        - Number(loadableQuantity.estFOOnBoard) - Number(loadableQuantity.estDOOnBoard) - Number(loadableQuantity.estFreshWaterOnBoard) - Number(loadableQuantity.boilerWaterOnBoard) - Number(loadableQuantity.ballast) - Number(loadableQuantity.constant) - Number(loadableQuantity.otherIfAny === '' ? 0 : loadableQuantity.otherIfAny);
      this.getTotalLoadableQuantity(subTotal, loadableQuantityResult);
    }
  }


  /**
   * Calculation for Loadable quantity
  */
  getTotalLoadableQuantity(subTotal: number, loadableQuantityResult: any) {
    const loadableQuantity = loadableQuantityResult.loadableQuantity;
    if (loadableQuantityResult.caseNo === 1) {
      const total = Number(subTotal) - Number(loadableQuantity.foConInSZ);
      if (total < 0) {
        this.loadableQuantityNew = '0';
      }
      else {
        this.loadableQuantityNew = total?.toString();
      }

    }
    else {
      if (subTotal < 0) {
        this.loadableQuantityNew = '0';
      }
      else {
        this.loadableQuantityNew = subTotal?.toString();
      }

    }

  }

  /**
* Handler for Generate loadable pattern button
*
* @memberof LoadableStudyDetailsComponent
*/
  onGenerateLoadablePattern() {
    this.generateLoadablePattern(this.vesselId, this.voyageId, this.loadableStudyId, this.selectedVoyage.voyageNo, this.selectedLoadableStudy.name);
  }

  /**
  * Handler for Generate loadable pattern
  *
  * @param {number} vesselId
  * @param {number} voyageId
  * @param {number} loadableStudyId
  * @memberof LoadableStudyDetailsComponent
  */
  async generateLoadablePattern(vesselId: number, voyageId: number, loadableStudyId: number, selectedVoyageNo: string, selectedLoadableStudyName: string) {
    this.ngxSpinnerService.show();
    const data = {
      vesselId: vesselId,
      voyageId: voyageId,
      loadableStudyId: loadableStudyId,
      selectedVoyageNo: selectedVoyageNo,
      selectedLoadableStudyName: selectedLoadableStudyName,
      processId: null
    }
    this.isGenerateClicked = true;
    const res = await this.loadableStudyDetailsApiService.generateLoadablePattern(vesselId, voyageId, loadableStudyId).toPromise();
    if (res.responseStatus.status === '200') {
      data.processId = res.processId;
      if (res.processId) {
        navigator.serviceWorker.controller.postMessage({ type: 'loadable-pattern-status', data });
        this.selectedLoadableStudy.isActionsEnabled = false;
      } else {
        this.isGenerateClicked = false;
      }
    } else {
      this.isGenerateClicked = false;
    }
    this.ngxSpinnerService.hide();
  }

  /**
   * Toast to show pattern processing
   *
   * @memberof LoadableStudyDetailsComponent
   */
  async processingMessage() {
    this.messageService.clear("process");
    const translationKeys = await this.translateService.get(['GENERATE_LOADABLE_PATTERN_INFO', 'GENERATE_LOADABLE_PATTERN_PROCESSING']).toPromise();
    this.messageService.add({ severity: 'info', summary: translationKeys['GENERATE_LOADABLE_PATTERN_INFO'], detail: translationKeys['GENERATE_LOADABLE_PATTERN_PROCESSING'], life: 1000, key: "process", closable: false });
  }

  /**
 * Toast to show pattern generate complete
 *
 * @param {string} selectedVoyageNo
 * @param {string} selectedLoadableStudyName
 * @memberof LoadableStudyDetailsComponent
 */
  async generatedMessage(selectedVoyageNo: string, selectedLoadableStudyName: string) {
    const translationKeys = await this.translateService.get(['GENERATE_LOADABLE_PATTERN_COMPLETE_DONE', 'GENERATE_LOADABLE_PATTERN_COMPLETED']).toPromise();
    this.messageService.add({ severity: 'success', summary: translationKeys['GENERATE_LOADABLE_PATTERN_COMPLETE_DONE'], detail: selectedVoyageNo + " " + selectedLoadableStudyName + " " + translationKeys['GENERATE_LOADABLE_PATTERN_COMPLETED'], sticky: true, closable: true });
  }

  /**
 * Toast to show for generating pattern have no solution
 *
 * @param {string} selectedVoyageNo
 * @param {string} selectedLoadableStudyName
 * @memberof LoadableStudyDetailsComponent
 */
  async noPlanMessage(selectedVoyageNo: string, selectedLoadableStudyName: string) {
    this.messageService.clear("process");
    const translationKeys = await this.translateService.get(['GENERATE_LOADABLE_PATTERN_NO_PLAN', 'GENERATE_LOADABLE_PATTERN_NO_PLAN_MESSAGE']).toPromise();
    this.messageService.add({ severity: 'error', summary: translationKeys['GENERATE_LOADABLE_PATTERN_NO_PLAN'], detail: selectedVoyageNo + " " + selectedLoadableStudyName + " " + translationKeys['GENERATE_LOADABLE_PATTERN_NO_PLAN_MESSAGE'], key: "process" });
  }

  /**
* Toast to show pattern bo reposnse
*
* @param {string} selectedVoyageNo
* @param {string} selectedLoadableStudyName
* @memberof LoadableStudyDetailsComponent
*/
  async noResponseMessage(selectedVoyageNo: string, selectedLoadableStudyName: string) {
    const translationKeys = await this.translateService.get(['GENERATE_LOADABLE_PATTERN_NO_RESPONSE_ERROR', 'GENERATE_LOADABLE_PATTERN_RESPONSE']).toPromise();
    this.messageService.add({ severity: 'error', summary: translationKeys['GENERATE_LOADABLE_PATTERN_NO_RESPONSE_ERROR'], detail: selectedVoyageNo + " " + selectedLoadableStudyName + " " + translationKeys['GENERATE_LOADABLE_PATTERN_RESPONSE'], sticky: true, closable: true});
  }

  /* Handler for unit change event
 *
 * @param {*} event
 * @memberof LoadableStudyDetailsComponent
 */
  onUnitChange(event) {
    this.loadableStudyDetailsApiService.unitChange.next();
    this.currentUnit = <QUANTITY_UNIT>localStorage.getItem('unit');
  }

  /**
  * Handler for unit change blocked event
  *
  * @memberof LoadableStudyDetailsComponent
  */
  unitChangeBlocked() {
    this.loadableStudyDetailsApiService.unitChangeBlocked.next();
  }
}
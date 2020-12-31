import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { IPort, LOADABLE_STUDY_DETAILS_TABS } from '../models/cargo-planning.model';
import { LoadableStudyDetailsTransformationService } from '../services/loadable-study-details-transformation.service';
import { LoadableStudyDetailsApiService } from '../services/loadable-study-details-api.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Voyage } from '../../core/models/common.model';
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
import { IPermissionContext, PERMISSION_ACTION } from '../../../shared/models/common.model';
import { LoadableQuantityModel } from '../models/loadable-quantity.model';
import { LoadableQuantityApiService } from '../services/loadable-quantity-api.service';
import { IPermission } from '../../../shared/models/user-profile.model';

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
export class LoadableStudyDetailsComponent implements OnInit {

  get selectedLoadableStudy(): LoadableStudy {
    return this._selectedLoadableStudy;
  }
  set selectedLoadableStudy(selectedLoadableStudy: LoadableStudy) {
    this._selectedLoadableStudy = selectedLoadableStudy;
    this.loadableStudyId = selectedLoadableStudy?.id;
    if (selectedLoadableStudy) {
      this.getLoadableStudyDetails(this.vesselId, this.voyageId, this.loadableStudyId);
    }
  }

  private _selectedLoadableStudy: LoadableStudy;

  LOADABLE_STUDY_DETAILS_TABS = LOADABLE_STUDY_DETAILS_TABS;
  dischargingPorts: IPort[] = [];//TODO to be populated form loadable study details
  dischargingPortsNames: string;//TODO to be populated form loadable study details
  totalQuantity$: Observable<number>;
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

  constructor(private loadableStudyDetailsApiService: LoadableStudyDetailsApiService,
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
    this.activatedRoute.paramMap.subscribe(params => {
      this.vesselId = Number(params.get('vesselId'));
      this.voyageId = Number(params.get('voyageId'));
      this.loadableStudyId = Number(params.get('loadableStudyId'));
      this.loadableStudies = null;
      this.getLoadableStudies(this.vesselId, this.voyageId, this.loadableStudyId);
    });
    this.setPagePermissionContext();
  }

  /**
   * Set page permission
   *
   * @memberof LoadableStudyDetailsComponent
   */
  setPagePermissionContext() {
    const cargoNominationTabPermission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['CargoNominationComponent'], false);
    this.cargoNominationTabPermissionContext = { key: AppConfigurationService.settings.permissionMapping['CargoNominationComponent'], actions: [PERMISSION_ACTION.VIEW] };

    this.portsTabPermission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['PortsComponent'], false);
    this.portsTabPermissionContext = { key: AppConfigurationService.settings.permissionMapping['PortsComponent'], actions: [PERMISSION_ACTION.VIEW] };

    this.ohqTabPermission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['OnHandQuantityComponent'], false);
    this.ohqTabPermissionContext = { key: AppConfigurationService.settings.permissionMapping['OnHandQuantityComponent'], actions: [PERMISSION_ACTION.VIEW] };

    this.loadableQuantityPermission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['LoadableQuantityComponent'], false);
    this.loadableQuantityPermissionContext = { key: AppConfigurationService.settings.permissionMapping['LoadableQuantityComponent'], actions: [PERMISSION_ACTION.VIEW] };

    const obqTabPermission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['OnBoardQuantityComponent'], false);
    this.obqTabPermissionContext = { key: AppConfigurationService.settings.permissionMapping['OnBoardQuantityComponent'], actions: [PERMISSION_ACTION.VIEW] };

    if (cargoNominationTabPermission === undefined || cargoNominationTabPermission?.view) {
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
    this.selectedLoadableStudy = loadableStudyId ? this.loadableStudies.find(loadableStudy => loadableStudy.id === loadableStudyId) : this.loadableStudies[0];
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
    const translationKeys = await this.translateService.get(['TOTAL_QUANTITY_ERROR']).toPromise();
    this.ngxSpinnerService.show();
    this.dischargingPorts = this.selectedLoadableStudy?.dischargingPortIds?.map(portId => this.ports.find(port => port?.id === portId));
    this.dischargingPortsNames = this.dischargingPorts?.map(port => port?.name).join(", ");
    // if no loadable study is selected set 1st loadable study as selected one and reload
    if (!loadableStudyId) {
      this.router.navigate([`business/cargo-planning/loadable-study-details/${vesselId}/${voyageId}/${this.loadableStudyId}`]);
    } else {
      const loadableQuantityResult = await this.loadableQuantityApiService.getLoadableQuantity(this.vesselId, this.voyageId, this.selectedLoadableStudy.id).toPromise();
      if (loadableQuantityResult.responseStatus.status === "200") {
        loadableQuantityResult.loadableQuantity.totalQuantity === '' ? this.loadableQuantityNew = "0" : this.loadableQuantityNew = loadableQuantityResult.loadableQuantity.totalQuantity;
        if (Number(this.totalQuantity$) > Number(this.loadableQuantityNew)) {
          this.messageService.add({ severity: 'error', summary: translationKeys['TOTAL_QUANTITY_ERROR'], detail: translationKeys['TOTAL_QUANTITY_ERROR'] });
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
  private initSubsciptions() {
    this.totalQuantity$ = this.loadableStudyDetailsTransformationService.totalQuantityCargoNomination$;
    this.cargoNominationComplete$ = this.loadableStudyDetailsTransformationService.cargoNominationValidity$;
    this.portsComplete$ = this.loadableStudyDetailsTransformationService.portValidity$;
    this.ohqComplete$ = this.loadableStudyDetailsTransformationService.ohqValidity$;
    this.obqComplete$ = this.loadableStudyDetailsTransformationService.obqValidity$;
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
  async onDischargePortChange(event: Event) {
    this.ngxSpinnerService.show();
    this.selectedLoadableStudy.dischargingPortIds = this.dischargingPorts?.map(port => port.id);
    const dischargingPortIds: IDischargingPortIds = { portIds: this.selectedLoadableStudy.dischargingPortIds };
    const translationKeys = await this.translateService.get(['LOADABLE_STUDY_DISCHARGING_PORT_UPDATE_SUCCESS', 'LOADABLE_STUDY_DISCHARGING_PORT_UPDATE_SUCCESSFULLY']).toPromise();
    const res = await this.loadableStudyDetailsApiService.setLoadableStudyDischargingPorts(this.vesselId, this.voyageId, this.loadableStudyId, dischargingPortIds).toPromise();
    if (res?.responseStatus?.status === "200") {
      this.messageService.add({ severity: 'success', summary: translationKeys['LOADABLE_STUDY_DISCHARGING_PORT_UPDATE_SUCCESS'], detail: translationKeys['LOADABLE_STUDY_DISCHARGING_PORT_UPDATE_SUCCESSFULLY'] });
    }
    this.dischargingPortsNames = this.dischargingPorts?.map(port => port?.name).join(", ");
    this.ngxSpinnerService.hide();
  }

  /**
   * Handler for voyage dropdown change
   *
   * @param {*} event
   * @memberof LoadableStudyDetailsComponent
   */
  onVoyageChange(event) {
    this.voyageId = event?.value?.id;
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
   * On Click of tab in lodable study details page
   *
   * @param {string} selectedTab
   * @memberof LoadableStudyDetailsComponent
   */
  async onTabClick(selectedTab: string) {
    const translationKeys = await this.translateService.get(['CARGONOMINATION_DISCHARGE_PORT_ERROR_SUMMARY', 'CARGONOMINATION_DISCHARGE_PORT_ERROR_DETAILS']).toPromise();
    if (selectedTab !== LOADABLE_STUDY_DETAILS_TABS.CARGONOMINATION) {
      if (this.dischargingPorts?.length > 0) {
        this.selectedTab = selectedTab;
      }
      else {
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
    const translationKeys = await this.translateService.get(['TOTAL_QUANTITY_ERROR']).toPromise();
    if (Number(this.totalQuantity$) > Number(this.loadableQuantityNew)) {
      this.messageService.add({ severity: 'error', summary: translationKeys['TOTAL_QUANTITY_ERROR'], detail: translationKeys['TOTAL_QUANTITY_ERROR'] });
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
 * Value from commingle button
 */
  toggleCommingleButton(event) {
    this.showCommingleButton = event;
  }

  /**
 * Take the user to particular pattern history
 */
  navigateToPatternHistory() {
    this.router.navigate([`/business/cargo-planning/loadable-pattern-history/${this.vesselId}/${this.voyageId}/${this.loadableStudyId}`]);
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
      this.initSubsciptions();
      this.selectedTab = LOADABLE_STUDY_DETAILS_TABS.CARGONOMINATION;
      this.getLoadableStudies(this.vesselId, this.voyageId, this.loadableStudyId);
    }
  }
}
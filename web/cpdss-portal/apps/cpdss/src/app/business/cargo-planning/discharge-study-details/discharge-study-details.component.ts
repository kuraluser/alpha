import { Component, OnInit , OnDestroy , HostListener , ViewChild, ElementRef } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subject , Observable } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { MessageService } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';

import { NgxSpinnerService } from 'ngx-spinner';

import { IVessel } from '../../core/models/vessel-details.model';
import { Voyage, IPort, VOYAGE_STATUS  , DISCHARGE_STUDY_STATUS, ICargo } from '../../core/models/common.model';

import { IPermissionContext, PERMISSION_ACTION, QUANTITY_UNIT } from '../../../shared/models/common.model';
import { IPermission } from '../../../shared/models/user-profile.model';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';
import { VoyageService } from '../../core/services/voyage.service';
import { VesselsApiService } from '../../core/services/vessels-api.service';
import { PermissionsService } from '../../../shared/services/permissions/permissions.service';
import { DISCHARGE_STUDY_DETAILS_TABS } from '../models/cargo-planning.model';
import {  IDischargeStudy } from '../models/discharge-study-list.model';
import { DischargeStudyDetailsApiService } from '../services/discharge-study-details-api.service';
import { DischargeStudyListApiService } from '../services/discharge-study-list-api.service';
import { DischargeStudyDetailsTransformationService } from '../services/discharge-study-details-transformation.service';
import { UnsavedChangesGuard } from '../../../shared/services/guards/unsaved-data-guard';
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
export class DischargeStudyDetailsComponent implements OnInit , OnDestroy {
  @ViewChild('dischargeStudy') dischargeStudyRef;

  get selectedDischargeStudy(): IDischargeStudy {
    return this._selectedDischargeStudy;
  }

  set selectedDischargeStudy(selectedDischargeStudy: IDischargeStudy) {
    this._selectedDischargeStudy  = selectedDischargeStudy;
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
  dischargeStudyTabPermissionContext:IPermissionContext;
  dischargeStudyTabPermission: IPermission;
  dischargeStudyViewPlanPermission: IPermission;
  dischargeStudyGeneratePermission: IPermission;
  cargos: ICargo[];
  disableGeneratePlan: boolean;

  @HostListener('window:beforeunload')
  canDeactivate(): Observable<boolean> | boolean {
    return !(this.dischargeStudyRef?.dischargeStudyForm?.dirty);
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
    private dischargeStudyDetailsTransformationService: DischargeStudyDetailsTransformationService
  ) { }

  /**
   * NgOnit init function for discharge study details component
   *
   * @memberof DischargeStudyComponent
   */
  ngOnInit(): void {
    this.initSubsciptions();
    this.setPagePermissionContext();
    this.activatedRoute.paramMap
    .pipe(takeUntil(this.ngUnsubscribe))
    .subscribe(params => {
      this.disableGeneratePlan = true;
      this.vesselId = Number(params.get('vesselId'));
      this.voyageId = Number(params.get('voyageId'));
      this.dischargeStudyId = Number(params.get('dischargeStudyId'));
      localStorage.setItem("vesselId", this.vesselId.toString())
      localStorage.setItem("voyageId", this.voyageId.toString())
      localStorage.setItem("dischargeStudyId", this.dischargeStudyId.toString())

      this.dischargeStudies = null;


      this.tabPermission();
      this.getDischargeStudies(this.vesselId, this.voyageId, this.dischargeStudyId);
    });
  }

    /**
   * NgOnDestroy lifecycle hook
   *
   * @memberof DischargeStudyDetailsComponent
   */
    ngOnDestroy() {
      this.ngUnsubscribe.next();
      this.ngUnsubscribe.complete();
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
      const voyages = await this.getVoyages(this.vesselId, this.voyageId);
      this.voyages = voyages.filter(voy => voy.statusId === VOYAGE_STATUS.ACTIVE || voy.statusId === VOYAGE_STATUS.CLOSE);
      const result = await this.dischargeStudyListApiService.getDischargeStudies(vesselId, voyageId).toPromise();
      const dischargeStudies = result?.dischargeStudies ?? [];
      this.cargoNominationComplete = true;
      if (dischargeStudies.length) {
        this.dischargeStudies = dischargeStudies;
        this.selectedDischargeStudy = dischargeStudyId ? this.dischargeStudies.find(dischargeStudy => dischargeStudy.id === dischargeStudyId) : this.dischargeStudies[0];
        this.dischargeStudyId = this.selectedDischargeStudy.id;
        this.dischargeStudyDetailsTransformationService.setOHQValidity(this.selectedDischargeStudy?.ohqPorts ?? []);
      } else {
        this.cargoNominationComplete = false;
        this.selectedDischargeStudy = null;
        this.dischargeStudies = [];
      }
      this.ports = await this.getPorts();
      const cargos = await this.dischargeStudyDetailsApiService.getCargoDetails().toPromise();
      this.cargos = cargos.cargos;
      this.disableGeneratePlan =  false;
      this.ngxSpinnerService.hide();
    }

  /**
   * Enable/ Disable actions of currently processing/processed discharge study
   *
   * @param {*} event
   * @memberof DischargeStudyDetailsComponent
   */
  setProcessingLoadableStudyActions(dischargeStudyId: number, statusId: number, dischargeStudies: IDischargeStudy[] = null) {
    dischargeStudies = dischargeStudies ?? this.dischargeStudies;
    const _loadableStudies = dischargeStudies?.map(dischargeStudy => {
      if (dischargeStudyId === dischargeStudy?.id) {
        if ([4, 5].includes(statusId) && this.router.url.includes('discharge-study-details')) {
          dischargeStudy.isActionsEnabled = false;
        }
        else if ([2, 3].includes(statusId)) {
          dischargeStudy.isEditable = false;
          dischargeStudy.isDeletable = false;
          dischargeStudy.isActionsEnabled = true;
        }
        else if ([6, 1].includes(statusId)) {
          dischargeStudy.isActionsEnabled = true;
        }
      } else if (!dischargeStudyId && !statusId) {
        dischargeStudy.isActionsEnabled = [DISCHARGE_STUDY_STATUS.PLAN_ALGO_PROCESSING, DISCHARGE_STUDY_STATUS.PLAN_ALGO_PROCESSING_COMPETED].includes(dischargeStudy?.statusId) ? false : true;
        dischargeStudy.isEditable = (dischargeStudy?.statusId === 3 || dischargeStudy?.statusId === 2) ? false : true;
        dischargeStudy.isDeletable = (dischargeStudy?.statusId === 3 || dischargeStudy?.statusId === 2) ? false : true;
      }
      return dischargeStudy;
    });
    this.dischargeStudies = _loadableStudies;
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
    if(!value) { return };
    this.selectedTab = selectedTab;
  }

  /**
   * Initialise all subscription in this page
   *
   * @private
   * @memberof DischargeStudyDetailsComponent
   */
  private async initSubsciptions(){
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
    async generateLoadablePattern() {
      this.ngxSpinnerService.show();
      const data = {
        vesselId: this.vesselId,
        voyageId: this.voyageId,
        dischargeStudyId: this.dischargeStudyId,
        selectedVoyageNo: this.selectedVoyage?.voyageNo,
        selectedLoadableStudyName: this.selectedDischargeStudy?.name,
        processId: null
      }

      try {
        const res = await this.dischargeStudyDetailsApiService.generateDischargePattern(this.vesselId, this.voyageId, this.dischargeStudyId).toPromise();
        if (res.responseStatus.status === '200') {

          this.selectedDischargeStudy.statusId = 4;
          data.processId = res.processId;
          if (res.processId) {
            navigator.serviceWorker.controller.postMessage({ type: 'loadable-pattern-status', data });
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
      if(!value) { return };
      this.router.navigate([`business/cargo-planning/discharge-study-plan/${this.vesselId}/${this.voyageId}/${this.dischargeStudyId}`]);
    }

}

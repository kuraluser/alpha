import { Component, OnInit , OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil, switchMap } from 'rxjs/operators';

import { NgxSpinnerService } from 'ngx-spinner';

import { IVessel } from '../../core/models/vessel-details.model';
import { Voyage, IPort, VOYAGE_STATUS  , DISCHARGE_STUDY_STATUS } from '../../core/models/common.model';

import { IPermissionContext, PERMISSION_ACTION, QUANTITY_UNIT, ISubTotal } from '../../../shared/models/common.model';
import { IPermission } from '../../../shared/models/user-profile.model';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';
import { VoyageService } from '../../core/services/voyage.service';
import { VesselsApiService } from '../../core/services/vessels-api.service';
import { PermissionsService } from '../../../shared/services/permissions/permissions.service';
import { ICargo, DISCHARGE_STUDY_DETAILS_TABS } from '../models/cargo-planning.model';
import {  IDischargeStudy } from '../models/discharge-study-list.model';
import { DischargeStudyDetailsApiService } from '../services/discharge-study-details-api.service';
import { DischargeStudyListApiService } from '../services/discharge-study-list-api.service';
import { DischargeStudyDetailsTransformationService } from '../services/discharge-study-details-transformation.service';

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
  ohqComplete: boolean;
  dischargeStudyComplete: boolean;

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private permissionsService: PermissionsService,
    private ngxSpinnerService: NgxSpinnerService,
    private vesselsApiService: VesselsApiService,
    private voyageService: VoyageService,
    private dischargeStudyDetailsApiService: DischargeStudyDetailsApiService,
    private dischargeStudyListApiService: DischargeStudyListApiService,
    private dischargeStudyDetailsTransformationService: DischargeStudyDetailsTransformationService
  ) { }

  /**
   * NgOnit init function for discharge study details component
   *
   * @memberof DischargeStudyComponent
   */
  ngOnInit(): void {
    this.selectedTab = DISCHARGE_STUDY_DETAILS_TABS.CARGONOMINATION;
    this.initSubsciptions();
    this.activatedRoute.paramMap
    .pipe(takeUntil(this.ngUnsubscribe))
    .subscribe(params => {
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
    this.setPagePermissionContext();
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
  
  
      this.cargoNominationTabPermission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['CargoNominationComponent'], false);
      this.cargoNominationTabPermissionContext = { key: AppConfigurationService.settings.permissionMapping['CargoNominationComponent'], actions: [PERMISSION_ACTION.VIEW] };
  
      this.portsTabPermission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['PortsComponent'], false);
      this.portsTabPermissionContext = { key: AppConfigurationService.settings.permissionMapping['PortsComponent'], actions: [PERMISSION_ACTION.VIEW] };
  
      this.ohqTabPermission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['OnHandQuantityComponent'], false);
      this.ohqTabPermissionContext = { key: AppConfigurationService.settings.permissionMapping['OnHandQuantityComponent'], actions: [PERMISSION_ACTION.VIEW] };
  
  
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
      this.voyages = await this.getVoyages(this.vesselId, this.voyageId);
      this.ports = await this.getPorts();
      const result = await this.dischargeStudyListApiService.getDischargeStudies(vesselId, voyageId).toPromise();
      const dischargeStudies = result?.loadableStudies ?? [];
      if (dischargeStudies.length) {
        this.setProcessingLoadableStudyActions(0, 0, dischargeStudies);
        this.selectedDischargeStudy = dischargeStudyId ? this.dischargeStudies.find(loadableStudy => loadableStudy.id === dischargeStudyId) : this.dischargeStudies[0];
        this.dischargeStudyDetailsTransformationService.setOHQValidity(this.selectedDischargeStudy.ohqPorts ?? []);
      } else {
        this.dischargeStudies = [];
      }
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
    this.dischargeStudyDetailsTransformationService.setOHQValidity([]);
    this.router.navigate([`business/cargo-planning/discharge-study-details/${this.vesselId}/${this.voyageId}/0`]);
  }

  /**
   * On Click of tab in discharge study details page
   *
   * @param {string} selectedTab
   * @memberof DischargeStudyDetailsComponent
  */
  async onTabClick(selectedTab: string) {
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

}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';

import { DischargePlanApiService } from '../services/discharge-plan-api.service';

import { NgxSpinnerService } from 'ngx-spinner';

import { Voyage , ICargoResponseModel , ICargo } from '../../core/models/common.model';
import { IVessel } from '../../core/models/vessel-details.model';
import { IPort , VOYAGE_STATUS } from '../../core/models/common.model';
import { IDischargeStudy } from './../models/discharge-study-list.model';
import { IDischargeStudyPortListDetails } from './../models/discharge-study-view-plan.model';

import { VoyageService } from '../../core/services/voyage.service';
import { VesselsApiService } from '../../core/services/vessels-api.service';
import { PermissionsService } from '../../../shared/services/permissions/permissions.service';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';

/**
 * Component class of discharge plan
 *
 * @export
 * @class DischargePlanComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-discharge-plan',
  templateUrl: './discharge-plan.component.html',
  styleUrls: ['./discharge-plan.component.scss']
})
export class DischargePlanComponent implements OnInit {

  get selectedDischargeStudy(): IDischargeStudy {
    return this._selectedDischargeStudy;
  }

  set selectedDischargeStudy(selectedDischargeStudy: IDischargeStudy) {
    this._selectedDischargeStudy = selectedDischargeStudy;
    this.dischargeStudyId = selectedDischargeStudy ? selectedDischargeStudy?.id : this.dischargeStudies?.length ? this.dischargeStudies[0]?.id : 0;
  }

  ports: IPort[];
  voyageId: number;
  vesselId: number;
  dischargeStudyId: number;
  vesselInfo: IVessel;
  voyages: Voyage[];
  selectedVoyage: Voyage;
  cargos: ICargo[];
  dischargeStudies: IDischargeStudy[];
  dischargeStudyPlanDetails: IDischargeStudyPortListDetails[];

  private _selectedDischargeStudy: IDischargeStudy;

  private ngUnsubscribe: Subject<any> = new Subject();

  constructor(
    private dischargePlanApiService: DischargePlanApiService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private permissionsService: PermissionsService,
    private ngxSpinnerService: NgxSpinnerService,
    private vesselsApiService: VesselsApiService,
    private voyageService: VoyageService,
  ) { }

  /**
   * NgOnit init function for Discharge study plan component
   *
   * @memberof DischargePlanComponent
  */
  ngOnInit(): void {
    this.setPagePermissionContext();
    this.getPorts();
    this.getCargos();
    this.activatedRoute.paramMap
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(params => {
        this.vesselId = Number(params.get('vesselId'));
        this.voyageId = Number(params.get('voyageId'));
        this.dischargeStudyId = Number(params.get('dischargeStudyId'));
        localStorage.setItem("vesselId", this.vesselId.toString());
        localStorage.setItem("voyageId", this.voyageId.toString());
        localStorage.setItem("dischargeStudyId", this.dischargeStudyId.toString());
        this.getDischargeStudies(this.vesselId, this.voyageId, this.dischargeStudyId);
      });

  }

  /**
   * Set page permission
   *
   * @memberof DischargePlanComponent
   */
  setPagePermissionContext() {
    this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['DischargeStudyViewPlan'], true);
  }



  /**
  * Method for fetching all cargo from master
  *
  * @private
  * @memberof DischargePlanComponent
  */
  private async getCargos() {
    const cargos:ICargoResponseModel = await this.dischargePlanApiService.getCargoDetails().toPromise();
    this.cargos = cargos.cargos;
  }

  /**
  * Method for fetching all ports from master
  *
  * @private
  * @memberof DischargePlanComponent
  */
  private async getPorts() {
    const result = await this.dischargePlanApiService.getPorts().toPromise();
    this.ports = result;
  }

  /**
* Method to fetch all discharge 
*
* @param {number} vesselId
* @param {number} voyageId
* @param {number} dischargeStudyId
* @memberof DischargePlanComponent
*/
  async getDischargeStudies(vesselId: number, voyageId: number, dischargeStudyId: number) {
    this.ngxSpinnerService.show();
    const res = await this.vesselsApiService.getVesselsInfo().toPromise();
    this.vesselInfo = res[0] ?? <IVessel>{};
    const voyages = await this.getVoyages(this.vesselId, this.voyageId);
    this.voyages = voyages.filter(voy => voy.statusId === VOYAGE_STATUS.ACTIVE || voy.statusId === VOYAGE_STATUS.CLOSE);
    const result = await this.dischargePlanApiService.getDischargeStudies(vesselId, voyageId).toPromise();
    const dischargeStudies = result?.dischargeStudies ?? [];
    if (dischargeStudies.length) {
      this.dischargeStudies = dischargeStudies;
      this.selectedDischargeStudy = dischargeStudyId ? this.dischargeStudies.find(dischargeStudy => dischargeStudy.id === dischargeStudyId) : this.dischargeStudies[0];
    }
    this.ngxSpinnerService.hide();
  }

  /**
 * Method to fetch all voyages in the vessel
 *
 * @param {number} vesselId
 * @param {number} voyageId
 * @memberof DischargePlanComponent
 */
  async getVoyages(vesselId: number, voyageId: number) {
    const result = await this.voyageService.getVoyagesByVesselId(vesselId).toPromise();
    const voyages = result ?? [];
    this.selectedVoyage = voyages.find(voyage => voyage.id === voyageId);
    return voyages;
  }
  /**
   * Handler for voyage dropdown change
   *
   * @param {*} event
   * @memberof DischargePlanComponent
  */
  onVoyageChange(event: any) {
    this.voyageId = event?.value?.id;
    this.selectedDischargeStudy = null;
    this.dischargeStudies = [];
    this.router.navigate([`business/cargo-planning/discharge-plan/${this.vesselId}/${this.voyageId}/0`]);
  }

  /**
    * Method to change discharge study
    *
    * @memberof DischargePlanComponent
  */
  onDischargeStudyChange(event) {
    const dischargeStudyId = event.value.id;
    this.selectedDischargeStudy = this.dischargeStudies.find(dischargeStudy => dischargeStudy.id === dischargeStudyId);
  }

  /**
   * Method to route back to loadbale study
   *
   * @memberof DischargePlanComponent
  */
  backToDischargeStudy() {
    this.router.navigate([`business/cargo-planning/discharge-study-details/${this.vesselId}/${this.voyageId}/${this.dischargeStudyId}`]);
  }

  /**
   * Method to get plan details
   *
   * @memberof DischargePlanComponent
  */
  planDetails(planDetails: IDischargeStudyPortListDetails[]) {
    this.dischargeStudyPlanDetails = planDetails;
  }
  
}

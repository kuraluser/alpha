import { Component, OnInit , OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';


import { DischargePlanApiService } from '../services/discharge-plan-api.service';

import { NgxSpinnerService } from 'ngx-spinner';
import { TranslateService } from '@ngx-translate/core';
import { ConfirmationService, MessageService } from 'primeng/api';

import { Voyage, ICargoResponseModel, ICargo } from '../../core/models/common.model';
import { IVessel } from '../../core/models/vessel-details.model';
import { IPort, VOYAGE_STATUS, DISCHARGE_STUDY_STATUS } from '../../core/models/common.model';
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
export class DischargePlanComponent implements OnInit , OnDestroy {

  get selectedDischargeStudy(): IDischargeStudy {
    return this._selectedDischargeStudy;
  }

  set selectedDischargeStudy(selectedDischargeStudy: IDischargeStudy) {
    this._selectedDischargeStudy = selectedDischargeStudy;
    this.isPlanGenerated = (this._selectedDischargeStudy?.statusId === DISCHARGE_STUDY_STATUS.PLAN_GENERATED || this._selectedDischargeStudy?.statusId === DISCHARGE_STUDY_STATUS.PLAN_CONFIRMED) ? true : false;
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
  dischargePatternId: number;
  dischargeStudies: IDischargeStudy[];
  dischargeStudyPlanDetails: IDischargeStudyPortListDetails[];
  confirmPlanPermission: boolean;
  planConfirmed: boolean;
  isPlanGenerated: boolean;
  readonly DISCHARGE_STUDY_STATUS = DISCHARGE_STUDY_STATUS;
  readonly VOYAGE_STATUS = VOYAGE_STATUS;

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
    private translateService: TranslateService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
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
   * NgOnDestroy lifecycle hook
   *
   * @memberof DischargePlanComponent
 */
  ngOnDestroy() {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  /**
   * Set page permission
   *
   * @memberof DischargePlanComponent
   */
  setPagePermissionContext() {
    this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['DischargeStudyViewPlan'], true);
    this.confirmPlanPermission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['DischargeStudyConfirmPlan'], false)?.view;
  }



  /**
  * Method for fetching all cargo from master
  *
  * @private
  * @memberof DischargePlanComponent
  */
  private async getCargos() {
    const cargos: ICargoResponseModel = await this.dischargePlanApiService.getCargoDetails().toPromise();
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
    this.planConfirmed = false;
    if (dischargeStudies.length) {
      this.dischargeStudies = dischargeStudies;
      this.selectedDischargeStudy = dischargeStudyId ? this.dischargeStudies.find(dischargeStudy => dischargeStudy.id === dischargeStudyId) : this.dischargeStudies[0];
      const confirmed = this.dischargeStudies.findIndex(dischargeStudy => dischargeStudy.statusId === DISCHARGE_STUDY_STATUS.PLAN_CONFIRMED);
      if(confirmed !== -1) {
        this.planConfirmed = true;
      }
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
    this.router.navigate([`business/cargo-planning/discharge-plan/${this.vesselId}/${this.voyageId}/${dischargeStudyId}`]);
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

  /**
   * for confirm plan
   * @memberof DischargePlanComponent
  */
  async confirmPlan() {
    const translationKeys = await this.translateService.get(['DISCHARGE_PLAN_CONFIRM_STATUS_ERROR',
      'DISCHARGE_PLAN_CONFIRM_REJECT_LABEL', 'DISCHARGE_PLAN_CONFIRM_CONFIRM_LABEL', 'DISCHARGE_PLAN_CONFIRM_SUMMARY', 'DISCHARGE_PLAN_CONFIRM_ERROR', 'DISCHARGE_PLAN_CONFIRM_DETAILS_NOT_CONFIRM', 'DISCHARGE_PLAN_CONFIRM_DETAILS_CONFIRM']).toPromise();

    this.confirmationService.confirm({
      key: 'confirmation-alert',
      header: translationKeys['DISCHARGE_PLAN_CONFIRM_SUMMARY'],
      message: translationKeys['DISCHARGE_PLAN_CONFIRM_DETAILS_NOT_CONFIRM'],
      icon: 'pi pi-exclamation-triangle',
      acceptLabel: translationKeys['DISCHARGE_PLAN_CONFIRM_CONFIRM_LABEL'],
      acceptIcon: 'pi',
      acceptButtonStyleClass: 'btn btn-main mr-5',
      rejectVisible: true,
      rejectLabel: translationKeys['DISCHARGE_PLAN_CONFIRM_REJECT_LABEL'],
      rejectIcon: 'pi',
      rejectButtonStyleClass: 'btn btn-main',
      accept: async () => {
        try {
          this.ngxSpinnerService.show();
          const confirmResult = await this.dischargePlanApiService.confirm(this.vesselId, this.voyageId, this.dischargeStudyId, this.dischargePatternId).toPromise();
          if (confirmResult.responseStatus.status === '200') {
            this.selectedDischargeStudy.statusId = DISCHARGE_STUDY_STATUS.PLAN_CONFIRMED;
          }
          this.ngxSpinnerService.hide();
        } catch (errorResponse) {
          this.ngxSpinnerService.hide();
          if (errorResponse?.error?.errorCode === 'ERR-RICO-110') {
            this.messageService.add({ severity: 'error', summary: translationKeys['DISCHARGE_PLAN_CONFIRM_ERROR'], detail: translationKeys['DISCHARGE_PLAN_CONFIRM_STATUS_ERROR'], life: 10000 });
          }
        }
      }
    });

  }

}

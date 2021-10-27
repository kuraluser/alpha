import { Component, HostListener, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { Observable, Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { UnsavedChangesGuard } from '../../../shared/services/guards/unsaved-data-guard';
import { ICargo, ICargoResponseModel } from '../../core/models/common.model';
import { OPERATION_TAB } from '../models/operations.model';
import { OperationsApiService } from '../services/operations-api.service';
import { OPERATIONS } from '../../core/models/common.model';
import { LoadingDischargingTransformationService } from '../services/loading-discharging-transformation.service';
import { ComponentCanDeactivate } from '../../../shared/models/common.model';
import { DischargingInformationComponent } from './discharging-information/discharging-information.component';
import { PermissionsService } from '../../../shared/services/permissions/permissions.service';
import { IPermission } from '../../../shared/models/user-profile.model';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';

/**
 * Component for discharging module
 *
 * @export
 * @class DischargingComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-discharging',
  templateUrl: './discharging.component.html',
  styleUrls: ['./discharging.component.scss']
})
export class DischargingComponent implements OnInit, OnDestroy, ComponentCanDeactivate {

  @ViewChild(DischargingInformationComponent) dischargingInformationComponent: DischargingInformationComponent;
  @ViewChild('dischargingInstruction') dischargingInstruction;

  currentTab: OPERATION_TAB = OPERATION_TAB.INFORMATION;
  OPERATION_TAB = OPERATION_TAB;
  vesselId: number;
  voyageId: number;
  portRotationId: number;
  dischargeInfoId: number;
  cargoTanks = [];
  display = false;
  selectedPortName: string;
  dischargingInformationComplete: boolean;
  cargos: ICargo[]
  dischargingInstructionComplete: boolean;
  dischargingInfoTabPermission: IPermission;
  dischargingInstructionTabPermission: IPermission;
  dischargingSequenceTabPermission: IPermission;
  dischargingPlanTabPermission: IPermission;

  private ngUnsubscribe: Subject<any> = new Subject();
  readonly OPERATIONS = OPERATIONS;

  @HostListener('window:beforeunload')
  canDeactivate(): Observable<boolean> | boolean {
    return !(this.dischargingInstruction?.instructionCheckList?.hasUnsavedChanges || this.dischargingInstruction?.instructionCheckList?.instructionForm?.dirty || this.dischargingInformationComponent?.hasUnSavedData);
  }

  constructor(
    private activatedRoute: ActivatedRoute,
    private operationsApiService: OperationsApiService,
    private unsavedChangesGuard: UnsavedChangesGuard,
    private loadingDischargingTransformationService: LoadingDischargingTransformationService,
    private ngxSpinnerService: NgxSpinnerService,
    private permissionsService: PermissionsService
  ) { }

  ngOnInit(): void {
    this.initSubsciptions();
    this.setPagePermission();
    this.getCargos();
    this.activatedRoute.paramMap
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(params => {
        this.vesselId = Number(params.get('vesselId'));
        this.voyageId = Number(params.get('voyageId'));
        this.portRotationId = Number(params.get('portRotationId'));
        localStorage.setItem("vesselId", this.vesselId.toString());
        localStorage.setItem("voyageId", this.voyageId.toString());
        localStorage.removeItem("loadableStudyId")
        this.selectedPortName = localStorage.getItem('selectedPortName');
        this.tabPermission();
      });
  }

  ngOnDestroy() {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  /**
   * Initialise all subscription in this page
   *
   * @private
   * @memberof DischargingComponent
   */
  private async initSubsciptions() {
    this.loadingDischargingTransformationService.dischargingInformationValidity$.pipe(takeUntil(this.ngUnsubscribe)).subscribe((res) => {
      this.dischargingInformationComplete = res;
    });
  }


  /**
  * Method to get cargos
  *
  * @memberof DischargingComponent
  */
  async getCargos() {
    this.ngxSpinnerService.show();
    const cargos: ICargoResponseModel = await this.operationsApiService.getCargos().toPromise();
    if (cargos.responseStatus.status === '200') {
      this.cargos = cargos.cargos;
    }
    this.ngxSpinnerService.hide();
  }

  /**
  * Method to switch tabs
  * @param tab
  * @memberof DischargingComponent
  */
  async switchTab(tab) {
    const value = await this.unsavedChangesGuard.canDeactivate(this);
    if (!value) { return };
    this.currentTab = tab;
    this.loadingDischargingTransformationService.setTabChange(tab);
  }

  /**
  * Method to set discharging info id
  * @param tab
  * @memberof DischargingComponent
  */
  setDischargingInfoId(data) {
    this.dischargeInfoId = data;
  }

  /**
  * Set page permission
  *
  * @memberof DischargingComponent
  */
  setPagePermission() {
    this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['DischargingComponent']);
    this.dischargingInfoTabPermission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['DischargingInformationComponent'], false);
    this.dischargingInstructionTabPermission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['DischargingInstructionComponent'], false);
    this.dischargingSequenceTabPermission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['DischargingSequenceComponent'], false);
    this.dischargingPlanTabPermission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['DischargingPlanComponent'], false);
  }

  /**
  * Select tab permission
  * @memberof DischargingComponent
  */
  tabPermission() {
    if (this.dischargingInfoTabPermission === undefined || this.dischargingInfoTabPermission?.view) {
      this.currentTab = OPERATION_TAB.INFORMATION;
    } else if (this.dischargingInstructionTabPermission?.view) {
      this.currentTab = OPERATION_TAB.INSTRUCTION;
    } else if (this.dischargingSequenceTabPermission?.view) {
      this.currentTab = OPERATION_TAB.SEQUENCE;
    } else if (this.dischargingPlanTabPermission?.view) {
      this.currentTab = OPERATION_TAB.PLAN;
    }
    this.loadingDischargingTransformationService.setTabChange(this.currentTab);
  }

}

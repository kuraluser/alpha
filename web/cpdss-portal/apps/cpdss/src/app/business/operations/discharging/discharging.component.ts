import { Component, HostListener, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { Observable, Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { ComponentCanDeactivate, UnsavedChangesGuard } from '../../../shared/services/guards/unsaved-data-guard';
import { ICargo, ICargoResponseModel } from '../../core/models/common.model';
import { OPERATION_TAB } from '../models/operations.model';
import { OperationsApiService } from '../services/operations-api.service';
import { OPERATIONS } from '../../core/models/common.model';
import { LoadingDischargingTransformationService } from '../services/loading-discharging-transformation.service';

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

  private ngUnsubscribe: Subject<any> = new Subject();
  readonly OPERATIONS = OPERATIONS;

  @HostListener('window:beforeunload')
  canDeactivate(): Observable<boolean> | boolean {
    return !(this.dischargingInstruction?.instructionCheckList?.hasUnsavedChanges || this.dischargingInstruction?.instructionCheckList?.instructionForm?.dirty);
  }

  constructor(
    private activatedRoute: ActivatedRoute,
    private operationsApiService: OperationsApiService,
    private unsavedChangesGuard: UnsavedChangesGuard,
    private loadingDischargingTransformationService: LoadingDischargingTransformationService,
    private ngxSpinnerService: NgxSpinnerService
  ) {}

  ngOnInit(): void {
    this.initSubsciptions();
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
        this.currentTab = OPERATION_TAB.INFORMATION;
        this.loadingDischargingTransformationService.setTabChange(OPERATION_TAB.INFORMATION);
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

}

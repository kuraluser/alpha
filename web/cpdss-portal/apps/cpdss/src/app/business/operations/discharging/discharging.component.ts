import { Component, HostListener, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable, Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { ComponentCanDeactivate, UnsavedChangesGuard } from '../../../shared/services/guards/unsaved-data-guard';
import { ICargo, ICargoResponseModel } from '../../core/models/common.model';
import { OPERATION_TAB } from '../models/operations.model';
import { OperationsApiService } from '../services/operations-api.service';

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
  dischargingInfoId: number;
  cargoTanks = [];
  display = false;
  selectedPortName: string;
  dischargingInformationComplete: boolean;
  cargos: ICargo[]
  dischargingInstructionComplete: boolean;

  private ngUnsubscribe: Subject<any> = new Subject();

  @HostListener('window:beforeunload')
  canDeactivate(): Observable<boolean> | boolean {
    return !(this.dischargingInstruction?.instructionCheckList?.hasUnsavedChanges || this.dischargingInstruction?.instructionCheckList?.instructionForm?.dirty);
  }

  constructor(
    private activatedRoute: ActivatedRoute,
    private operationsApiService: OperationsApiService,
    private unsavedChangesGuard: UnsavedChangesGuard
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
        this.selectedPortName = localStorage.getItem('selectedPortName');
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
    const cargos: ICargoResponseModel = await this.operationsApiService.getCargos().toPromise();
    if (cargos.responseStatus.status === '200') {
      this.cargos = cargos.cargos;
    }
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
  }

  /**
  * Method to set discharging info id
  * @param tab
  * @memberof DischargingComponent
  */
  setDischargingInfoId(data) {
    this.dischargingInfoId = data;
  }

}

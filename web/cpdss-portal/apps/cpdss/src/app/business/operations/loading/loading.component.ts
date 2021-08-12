import { Component, ComponentRef, HostListener, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subject, Observable } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { ICargo, ICargoResponseModel, OPERATIONS } from '../../core/models/common.model';
import { OPERATION_TAB } from '../models/operations.model';
import { LoadingInformationComponent } from './loading-information/loading-information.component';
import { UnsavedChangesGuard, ComponentCanDeactivate } from './../../../shared/services/guards/unsaved-data-guard';
import { LoadingDischargingTransformationService } from '../services/loading-discharging-transformation.service';
import { OperationsApiService } from '../services/operations-api.service';

/**
 * Component class for loading component
 *
 * @export
 * @class LoadingComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-loading',
  templateUrl: './loading.component.html',
  styleUrls: ['./loading.component.scss']
})
export class LoadingComponent implements OnInit, OnDestroy, ComponentCanDeactivate {

  @ViewChild(LoadingInformationComponent) loadingInformationComponent: LoadingInformationComponent
  @ViewChild('loadingInstruction') loadingInstruction;
  currentTab: OPERATION_TAB = OPERATION_TAB.INFORMATION;
  OPERATION_TAB = OPERATION_TAB;
  vesselId: number;
  voyageId: number;
  portRotationId: number;
  loadingInfoId: number;
  cargoTanks = [];
  display = false;
  selectedPortName: string;
  loadingInformationComplete: boolean;
  cargos: ICargo[]
  loadingInstructionComplete: boolean;
  loadingSequenceComplete: boolean;
  OPERATIONS = OPERATIONS;

  private ngUnsubscribe: Subject<any> = new Subject();

  @HostListener('window:beforeunload')
  canDeactivate(): Observable<boolean> | boolean {
    return !(this.loadingInstruction?.instructionCheckList?.hasUnsavedChanges || this.loadingInstruction?.instructionCheckList?.instructionForm?.dirty
      || this.loadingInformationComponent?.hasUnSavedData);
  }

  constructor(
    private activatedRoute: ActivatedRoute,
    private loadingDischargingTransformationService: LoadingDischargingTransformationService,
    private operationsApiService: OperationsApiService,
    private unsavedChangesGuard: UnsavedChangesGuard
    ) {
  }



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
        this.currentTab = OPERATION_TAB.INFORMATION;
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
   * @memberof LoadingComponent
   */
  private async initSubsciptions() {
    this.loadingDischargingTransformationService.loadingInformationValidity$.subscribe((res) => {
      this.loadingInformationComplete = res;
    });
    this.loadingDischargingTransformationService.loadingInstructionValidity$.subscribe((res)=>{
      this.loadingInstructionComplete = res;
    });
  }


  /**
* Method to get cargos
*
* @memberof LoadingComponent
*/
  async getCargos() {
    const cargos: ICargoResponseModel = await this.operationsApiService.getCargos().toPromise();
    if (cargos.responseStatus.status === '200') {
      this.cargos = cargos.cargos;
    }
  }

  /**
  * Method switch tab
  * @param tab
  * @memberof LoadingComponent
  */

  async switchTab(tab) {
    const value = await this.unsavedChangesGuard.canDeactivate(this);
    if (!value) { return };
    this.currentTab = tab;
  }

  /**
  * Method to set loading infoid
  * @param tab
  * @memberof LoadingComponent
  */

  setLoadingInfoId(data){
    this.loadingInfoId = data;
  }

}

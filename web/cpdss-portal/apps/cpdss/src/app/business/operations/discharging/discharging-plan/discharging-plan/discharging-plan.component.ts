import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { TranslateService } from '@ngx-translate/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';

import { AppConfigurationService } from '../../../../../shared/services/app-configuration/app-configuration.service';
import { LoadingDischargingTransformationService } from '../../../services/loading-discharging-transformation.service';
import { DischargingPlanApiService } from './../../../services/discharging-plan-api.service';
import { OperationsApiService } from '../../../services/operations-api.service';

import { IAlgoError, IAlgoResponse, ICargo, ILoadableQuantityCargo, OPERATIONS } from '../../../../core/models/common.model';
import { QUANTITY_UNIT } from '../../../../../shared/models/common.model';
import { IDischargeOperationListData, IDischargingInformation, IDischargingPlanDetailsResponse, ULLAGE_STATUS_VALUE } from '../../../models/loading-discharging.model';

/**
 * Component for Discharge-plan
 */
@Component({
  selector: 'cpdss-portal-discharging-plan',
  templateUrl: './discharging-plan.component.html',
  styleUrls: ['./discharging-plan.component.scss']
})
export class DischargingPlanComponent implements OnInit, OnDestroy {

  @Input() vesselId: number;
  @Input() voyageId: number;
  @Input() dischargeInfoId: number;
  @Input() get cargos(): ICargo[] {
    return this._cargos;
  }
  set cargos(cargos: ICargo[]) {
    this._cargos = cargos;
  }
  @Input() get portRotationId(): number {
    return this._portRotationId;
  }
  set portRotationId(portRotationId: number) {
    this._portRotationId = portRotationId;
    this.getDischargingPlanDetails();
  }

  private _cargos: ICargo[];
  private _portRotationId: number;
  private ngUnsubscribe: Subject<any> = new Subject();

  dischargingPlanDetails: IDischargingPlanDetailsResponse;
  dischargingInformation: IDischargingInformation;
  currentQuantitySelectedUnit = <QUANTITY_UNIT>localStorage.getItem('unit');
  dischargeQuantityCargoDetails: ILoadableQuantityCargo[];
  loadedCargos: ICargo[];
  readonly OPERATIONS = OPERATIONS;
  prevQuantitySelectedUnit: QUANTITY_UNIT;
  dischargingPlanForm: FormGroup;
  errorMessage: IAlgoError[];
  errorPopUp = false;
  listData: IDischargeOperationListData = {
    protestedOptions: [{ name: 'Yes', id: 1 }, { name: 'No', id: 2 }],
    cowOptions: [{ name: 'Auto', id: 1 }, { name: 'Manual', id: 2 }],
    cowPercentages: [
      { value: 25, name: '25%' },
      { value: 50, name: '50%' },
      { value: 75, name: '75%' },
      { value: 100, name: '100%' },
    ]
  };

  constructor(
    private dischargingPlanApiService: DischargingPlanApiService,
    private operationsApiService: OperationsApiService,
    private loadingDischargingTransformationService: LoadingDischargingTransformationService,
    private ngxSpinnerService: NgxSpinnerService,
    private messageService: MessageService,
    private translateService: TranslateService,
    private fb: FormBuilder
  ) { }

  ngOnInit(): void {
    this.initSubscriptions();
  }

  ngOnDestroy() {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  /**
  * Initialization for all subscriptions
  *
  * @private
  * @memberof LoadingInformationComponent
  */
  private async initSubscriptions() {
    this.loadingDischargingTransformationService.unitChange$.pipe(takeUntil(this.ngUnsubscribe)).subscribe((res) => {
      this.prevQuantitySelectedUnit = this.currentQuantitySelectedUnit ?? AppConfigurationService.settings.baseUnit
      this.currentQuantitySelectedUnit = <QUANTITY_UNIT>localStorage.getItem('unit');
    });

    this.loadingDischargingTransformationService.showUllageErrorPopup$.pipe(takeUntil(this.ngUnsubscribe)).subscribe((res) => {
      this.getAlgoErrorMessage(res);
    });

    this.loadingDischargingTransformationService.setUllageArrivalBtnStatus$.pipe(takeUntil(this.ngUnsubscribe)).subscribe((value) => {
      if (value === ULLAGE_STATUS_VALUE.SUCCESS) {
        this.getDischargingPlanDetails();
      }
    });
    this.loadingDischargingTransformationService.setUllageDepartureBtnStatus$.pipe(takeUntil(this.ngUnsubscribe)).subscribe((value) => {
      if (value === ULLAGE_STATUS_VALUE.SUCCESS) {
        this.getDischargingPlanDetails();
      }
    });
  }

  /**
   * function to get discharging-plan data
   *
   * @memberof DischargingPlanComponent
   */
  async getDischargingPlanDetails() {
    this.ngxSpinnerService.show();
    try {
      const dischargePlanResponse: IDischargingPlanDetailsResponse = await this.dischargingPlanApiService.getDischargingPlanDetails(this.vesselId, this.voyageId, this.dischargeInfoId, this.portRotationId).toPromise();
      if (dischargePlanResponse.responseStatus.status === "200") {
        this.dischargingPlanDetails = dischargePlanResponse;
        this.dischargingPlanDetails.dischargingInformation.cargoVesselTankDetails.loadableQuantityCargoDetails = dischargePlanResponse.dischargingInformation.cargoVesselTankDetails.dischargeQuantityCargoDetails;
        this.dischargeQuantityCargoDetails = this.dischargingPlanDetails?.dischargingInformation?.cargoVesselTankDetails?.loadableQuantityCargoDetails;
        this.dischargingPlanDetails.dischargingInformation.loadedCargos = [...this.dischargeQuantityCargoDetails].map(cargo => {
          const loadedCargo = { 'id': cargo?.cargoNominationId, 'abbreviation': cargo?.cargoAbbreviation, 'colorCode': cargo?.colorCode };
          return loadedCargo;
        });
        this.dischargingInformation = this.loadingDischargingTransformationService.transformDischargingInformation(this.dischargingPlanDetails.dischargingInformation, this.listData);
        this.dischargingPlanForm = this.fb.group({
          cowDetails: this.fb.group({}),
          postDischargeStageTime: this.fb.group({})
        });
      }
      this.ngxSpinnerService.hide();
    } catch (error) {
      this.ngxSpinnerService.hide();
    }
  }

  /**
   * function to display ALGO error
   *
   * @param {*} status
   * @memberof DischargingPlanComponent
   */
  async getAlgoErrorMessage(status) {
    const translationKeys = await this.translateService.get(['DSICHARGING_PLAN_ALGO_ERROR', 'DSICHARGING_PLAN_ALGO_NO_PLAN']).toPromise();
    const algoError: IAlgoResponse = await this.operationsApiService.getDischargePlanAlgoError(this.vesselId, this.voyageId, this.dischargeInfoId, status.status).toPromise();
    if (algoError.responseStatus.status === '200') {
      this.errorMessage = algoError.algoErrors;
      this.errorPopUp = status.value;
    }
    this.messageService.add({ severity: 'error', summary: translationKeys['DSICHARGING_PLAN_ALGO_ERROR'], detail: translationKeys["DSICHARGING_PLAN_ALGO_NO_PLAN"] });
  }

  /**
   * function to display ALGO error popup
   *
   * @param {*} status
   * @memberof DischargingPlanComponent
   */
  viewError(status) {
    this.errorPopUp = status;
  }

}

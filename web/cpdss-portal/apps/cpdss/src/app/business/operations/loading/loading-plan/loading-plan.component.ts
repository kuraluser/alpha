import { Component, Input, OnInit } from '@angular/core';
import { ICargo, ILoadableQuantityCargo, IShipCargoTank } from '../../../core/models/common.model';
import { ILoadingDischargingSequences, IToppingOffSequence } from '../../models/loading-discharging.model';
import { LoadingPlanApiService } from './../../services/loading-plan-api.service';
import { QUANTITY_UNIT } from '../../../../shared/models/common.model';
import { LoadingDischargingTransformationService } from '../../services/loading-discharging-transformation.service';
import { AppConfigurationService } from '../../../../shared/services/app-configuration/app-configuration.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { ILoadingPlanDetails } from '../../models/loading-discharging.model';
/**
 * Component class for loading plan component
 *
 * @export
 * @class LoadingPlanComponent
 * @implements {OnInit}
 */

@Component({
  selector: 'cpdss-portal-loading-plan',
  templateUrl: './loading-plan.component.html',
  styleUrls: ['./loading-plan.component.scss']
})

export class LoadingPlanComponent implements OnInit {
  @Input() get cargos(): ICargo[] {
    return this._cargos;
  }

  set cargos(cargos: ICargo[]) {
    this._cargos = cargos;
  }

  @Input() vesselId: number;
  @Input() voyageId: number;
  @Input() portRotationId: number;
  @Input() loadingInfoId: number;

  private _cargos: ICargo[];
  toppingOffSequence: IToppingOffSequence[];
  cargoTanks: IShipCargoTank[][];
  loadableQuantityCargoDetails: ILoadableQuantityCargo[];
  loadingSequences: ILoadingDischargingSequences;
  loadingPlanDetails: ILoadingPlanDetails;
  prevQuantitySelectedUnit: QUANTITY_UNIT;
  currentQuantitySelectedUnit = <QUANTITY_UNIT>localStorage.getItem('unit');


  constructor(
    private loadingPlanApiService: LoadingPlanApiService,
    private loadingDischargingTransformationService: LoadingDischargingTransformationService,
    private ngxSpinnerService: NgxSpinnerService
  ) { }

  ngOnInit(): void {
    this.initSubscriptions();
    this.getLoadingPlanDetails();
  }

  /**
  * Get loading plan details
  *
  * @private
  * @memberof LoadingInformationComponent
  */
  async getLoadingPlanDetails() {
    try {
      this.ngxSpinnerService.show();
      this.loadingPlanDetails = await this.loadingPlanApiService.getLoadingPlanDetails(this.vesselId, this.voyageId, this.loadingInfoId, this.portRotationId).toPromise();
      this.toppingOffSequence = this.loadingPlanDetails?.loadingInformation?.toppingOffSequence;
      this.loadableQuantityCargoDetails = this.loadingPlanDetails?.loadingInformation?.cargoVesselTankDetails?.loadableQuantityCargoDetails;
      this.loadingSequences = this.loadingPlanDetails?.loadingInformation?.loadingSequences;
      this.loadingSequences.loadingDischargingDelays = this.loadingPlanDetails?.loadingInformation?.loadingSequences['loadingDelays'];
      this.cargoTanks = this.loadingPlanDetails?.cargoTanks;
      this.ngxSpinnerService.hide();
    } catch (e) {
      this.ngxSpinnerService.hide();
    }
  }

  /**
  * Initialization for all subscriptions
  *
  * @private
  * @memberof LoadingInformationComponent
  */
  private async initSubscriptions() {
    this.loadingDischargingTransformationService.unitChange$.subscribe((res) => {
      this.prevQuantitySelectedUnit = this.currentQuantitySelectedUnit ?? AppConfigurationService.settings.baseUnit
      this.currentQuantitySelectedUnit = <QUANTITY_UNIT>localStorage.getItem('unit');
    })
  }

}

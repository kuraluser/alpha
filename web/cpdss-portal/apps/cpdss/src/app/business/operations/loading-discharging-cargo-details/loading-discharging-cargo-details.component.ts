import { Component, Input, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { QUANTITY_UNIT } from '../../../shared/models/common.model';
import { QuantityPipe } from '../../../shared/pipes/quantity/quantity.pipe';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';
import { IShipCargoTank, ITankOptions, ICargoQuantities, ICargo, OPERATIONS } from '../../core/models/common.model';
import { ICargoVesselTankDetails } from '../models/loading-discharging.model';
import { LoadingDischargingTransformationService } from '../services/loading-discharging-transformation.service';

/**
 * Component class for loading discharging berth component
 *
 * @export
 * @class LoadingDischargingCargoDetailsComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-loading-discharging-cargo-details',
  templateUrl: './loading-discharging-cargo-details.component.html',
  styleUrls: ['./loading-discharging-cargo-details.component.scss']
})

export class LoadingDischargingCargoDetailsComponent implements OnInit {
  @Input() cargos: ICargo[];
  @Input() prevQuantitySelectedUnit: QUANTITY_UNIT;
  @Input() operation: OPERATIONS;
  @Input() loadableStudyName: string;
  @Input() dischargeStudyName: string;

  @Input() get currentQuantitySelectedUnit(): QUANTITY_UNIT {
    return this._currentQuantitySelectedUnit;
  }

  set currentQuantitySelectedUnit(value: QUANTITY_UNIT) {
    this.prevQuantitySelectedUnit = this.currentQuantitySelectedUnit ?? AppConfigurationService.settings.baseUnit;
    this._currentQuantitySelectedUnit = value;
    this.cargoTankOptions.weightUnit = value;
  }
  @Input() get cargoVesselTankDetails(): ICargoVesselTankDetails {
    return this._cargoVesselTankDetails;
  }

  set cargoVesselTankDetails(cargoVesselTankDetails: ICargoVesselTankDetails) {
    this._cargoVesselTankDetails = cargoVesselTankDetails;
    this.init();
  }

  readonly OPERATIONS = OPERATIONS;
  cargoTanks: IShipCargoTank[][];
  cargoConditions: any = [];
  cargoQuantities: ICargoQuantities[];
  cargoTankOptions: ITankOptions = { isFullyFilled: false, showTooltip: true, isSelectable: false, showFillingPercentage: true, weightField: 'plannedWeight', showWeight: true, weightUnit: 'MT', commodityNameField: 'abbreviation', ullageField: 'correctedUllage', ullageUnit: AppConfigurationService?.settings?.ullageUnit, densityField: 'api', fillingPercentageField: 'fillingRatio' }

  private _currentQuantitySelectedUnit: QUANTITY_UNIT;
  private _cargoVesselTankDetails: ICargoVesselTankDetails;

  constructor(
    private loadingDischargingTransformationService: LoadingDischargingTransformationService,
    private quantityPipe: QuantityPipe,
    private translateService: TranslateService,
  ) { }

  async ngOnInit(): Promise<void> {
  }

  /**
  * Method to initialise
  *
  * @memberof LoadingDischargingCargoDetailsComponent
  */
  init(){
    this.prevQuantitySelectedUnit = AppConfigurationService.settings.baseUnit;
    this.cargoConditions = this.cargoVesselTankDetails?.cargoConditions;
    this.cargoQuantities = this.cargoVesselTankDetails?.cargoQuantities;

    this.loadingDischargingTransformationService.unitChange$.subscribe((res) => {
      this.prevQuantitySelectedUnit = this.currentQuantitySelectedUnit ?? AppConfigurationService.settings.baseUnit
      this.currentQuantitySelectedUnit = <QUANTITY_UNIT>localStorage.getItem('unit');
      this.convertIntoSelectedUnit();
    })
    this.convertIntoSelectedUnit();
  }

  /**
   * Method to convert data into selected unit.
   *
   * @memberof LoadingDischargingCargoDetailsComponent
   */
  convertIntoSelectedUnit() {
    this.cargoTanks = this.loadingDischargingTransformationService.formatCargoTanks(this.cargoVesselTankDetails?.cargoTanks, this.cargoVesselTankDetails?.cargoQuantities, this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit);
  }
}

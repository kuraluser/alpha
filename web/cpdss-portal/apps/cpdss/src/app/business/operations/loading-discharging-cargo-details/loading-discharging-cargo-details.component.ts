import { Component, Input, OnInit } from '@angular/core';
import { QUANTITY_UNIT } from '../../../shared/models/common.model';
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
  @Input() cargoVesselTankDetails: ICargoVesselTankDetails;
  @Input() prevQuantitySelectedUnit: QUANTITY_UNIT;
  @Input() get currentQuantitySelectedUnit(): QUANTITY_UNIT {
    return this._currentQuantitySelectedUnit;
  }
  @Input() operation: OPERATIONS;

  set currentQuantitySelectedUnit(value: QUANTITY_UNIT) {
    this.prevQuantitySelectedUnit = this.currentQuantitySelectedUnit ?? AppConfigurationService.settings.baseUnit;
    this._currentQuantitySelectedUnit = value;
    this.cargoTankOptions.weightUnit = value;
  }

  cargoTanks: IShipCargoTank[][];
  cargoConditions: any = [];
  cargoQuantities: ICargoQuantities[];
  cargoTankOptions: ITankOptions = { isFullyFilled: false, showTooltip: true, isSelectable: false, showFillingPercentage: true, weightField: 'actualWeight', showWeight: true, weightUnit: 'MT', commodityNameField: 'cargoAbbreviation', ullageField: 'correctedUllage', ullageUnit: 'CM', densityField: 'api' }

  private _currentQuantitySelectedUnit: QUANTITY_UNIT;

  constructor(
    private loadingDischargingTransformationService: LoadingDischargingTransformationService
  ) { }

  async ngOnInit(): Promise<void> {
    this.prevQuantitySelectedUnit = AppConfigurationService.settings.baseUnit;
    this.cargoConditions = this.cargoVesselTankDetails?.cargoConditions;
    this.cargoQuantities = this.cargoVesselTankDetails?.cargoQuantities;
    this.cargoTanks = this.loadingDischargingTransformationService.formatCargoTanks(this.cargoVesselTankDetails?.cargoTanks, this.cargoVesselTankDetails?.cargoQuantities, this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit);
  }
}

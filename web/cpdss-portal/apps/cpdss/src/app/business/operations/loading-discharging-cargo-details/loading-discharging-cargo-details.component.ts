import { DecimalPipe } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { IDataTableColumn } from '../../../shared/components/datatable/datatable.model';
import { QUANTITY_UNIT } from '../../../shared/models/common.model';
import { QuantityPipe } from '../../../shared/pipes/quantity/quantity.pipe';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';
import { IShipCargoTank, ILoadableQuantityCargo, ITankOptions, ICargoQuantities, ICargo } from '../../core/models/common.model';
import { ICargoVesselTankDetails } from '../models/loading-information.model';
import { LoadingDischargingCargoDetailsTransformationService } from './loading-discharging-cargo-details-transformation.service';

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

  set currentQuantitySelectedUnit(value: QUANTITY_UNIT) {
    this.prevQuantitySelectedUnit = this.currentQuantitySelectedUnit ?? AppConfigurationService.settings.baseUnit;
    this._currentQuantitySelectedUnit = value;
    if( this.cargoVesselTankDetails?.loadableQuantityCargoDetails){
      this.updateCargoTobeLoadedData();
    }
  }
  private _currentQuantitySelectedUnit: QUANTITY_UNIT;

  cargoTanks: IShipCargoTank[][];
  cargoConditions: any = [];
  cargoQuantities: ICargoQuantities[];
  cargoTobeLoadedColumns: IDataTableColumn[];
  cargoTobeLoaded: ILoadableQuantityCargo[] = [];
  cargoTankOptions: ITankOptions = { isFullyFilled: false, showTooltip: true, isSelectable: false, showFillingPercentage: true, weightField: 'quantity', showWeight: true, weightUnit: 'MT', commodityNameField: 'cargoAbbreviation', ullageField: 'rdgUllage', ullageUnit: 'CM', densityField: 'api' }
  constructor(
    private _decimalPipe: DecimalPipe,
    private quantityPipe: QuantityPipe,
    private loadingDischargingCargoDetailsTransformationService: LoadingDischargingCargoDetailsTransformationService
  ) { }

  async ngOnInit(): Promise<void> {
    this.cargoTobeLoadedColumns = this.loadingDischargingCargoDetailsTransformationService.getCargotobeLoadedDatatableColumns(this.currentQuantitySelectedUnit);
    this.prevQuantitySelectedUnit = AppConfigurationService.settings.baseUnit;
    this.cargoConditions = this.cargoVesselTankDetails?.cargoConditions;
    this.cargoQuantities = this.cargoVesselTankDetails?.cargoQuantities;
    this.cargoTanks = this.loadingDischargingCargoDetailsTransformationService.formatCargoTanks(this.cargoVesselTankDetails?.cargoTanks, this.cargoVesselTankDetails?.cargoQuantities, this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit);
    this.updateCargoTobeLoadedData();
  }



  /**
  * Method to update cargo to be loaded data
  *
  * @memberof LoadingDischargingCargoDetailsComponent
  */
  updateCargoTobeLoadedData() {
    this.cargoTobeLoaded = this.cargoVesselTankDetails.loadableQuantityCargoDetails?.map(loadable => {
      if (loadable) {
        const minTolerence = this.loadingDischargingCargoDetailsTransformationService.decimalConvertion(this._decimalPipe, loadable.minTolerence, '0.2-2');
        const maxTolerence = this.loadingDischargingCargoDetailsTransformationService.decimalConvertion(this._decimalPipe, loadable.maxTolerence, '0.2-2');
        loadable.minMaxTolerance = maxTolerence + (minTolerence ? "/" + minTolerence : '');
        loadable.differencePercentage = loadable.differencePercentage ? (loadable.differencePercentage.includes('%') ? loadable.differencePercentage : loadable.differencePercentage + '%') : '';
        loadable.grade = this.findCargo(loadable);

        const orderedQuantity = this.quantityPipe.transform(this.loadingDischargingCargoDetailsTransformationService.convertToNumber(loadable?.orderQuantity), this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit, loadable?.estimatedAPI, loadable?.estimatedTemp, -1);
        loadable.orderedQuantity = orderedQuantity?.toString();

        const loadableMT = this.quantityPipe.transform(this.loadingDischargingCargoDetailsTransformationService.convertToNumber(loadable?.loadableMT), this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit, loadable?.estimatedAPI, loadable?.estimatedTemp, -1);
        loadable.loadableMT = loadableMT?.toString();

        const slopQuantity = loadable?.slopQuantity ? this.quantityPipe.transform(this.loadingDischargingCargoDetailsTransformationService.convertToNumber(loadable?.slopQuantity.toString()), this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit, loadable?.estimatedAPI, loadable?.estimatedTemp, -1) : 0;
        loadable.slopQuantity = slopQuantity;

        loadable.loadingPort = loadable?.loadingPorts?.join(',');
      }
      return loadable;
    })
  }

  /**
* Method to find out cargo
*
* @memberof LoadingDischargingCargoDetailsComponent
*/
  findCargo(loadableQuantityCargoDetails): string {
    let cargoDetail;
    this.cargos?.map((cargo) => {
      if (cargo?.id === loadableQuantityCargoDetails?.cargoId) {
        cargoDetail = cargo;
      }
    })
    return cargoDetail?.name;
  }

}

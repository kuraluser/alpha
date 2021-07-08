import { Component, OnInit, Input } from '@angular/core';
import { IDataTableColumn, DATATABLE_FIELD_TYPE } from '../../../shared/components/datatable/datatable.model';
import { ICargoConditions, QUANTITY_UNIT } from '../../../shared/models/common.model';
import { QuantityPipe } from '../../../shared/pipes/quantity/quantity.pipe';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';
import { ICargoQuantities } from '../../core/models/common.model';
import { LoadingDischargingCargoDetailsTableTransformationService } from './loading-discharging-cargo-details-table-transformation.service';

@Component({
  selector: 'cpdss-portal-loading-discharging-cargo-details-table',
  templateUrl: './loading-discharging-cargo-details-table.component.html',
  styleUrls: ['./loading-discharging-cargo-details-table.component.scss']
})

/**
 * Component class for loading discharging cargo details component
 *
 * @export
 * @class LoadingDischargingCargoDetailsTableComponent
 * @implements {OnInit}
 */
export class LoadingDischargingCargoDetailsTableComponent implements OnInit {

  @Input() cargoConditions: ICargoConditions[];

  @Input() get cargoQuantities(): ICargoQuantities[] {
    return this._cargoQuantities;
  }

  set cargoQuantities(cargoQuantities: ICargoQuantities[]) {
    this._cargoQuantities = cargoQuantities;
    if(cargoQuantities){
      this.setCargoDetails();
    }
  }

  @Input() get currentQuantitySelectedUnit(): QUANTITY_UNIT {
    return this._currentQuantitySelectedUnit;
  }

  set currentQuantitySelectedUnit(value: QUANTITY_UNIT) {
    this.prevQuantitySelectedUnit = this.currentQuantitySelectedUnit ?? AppConfigurationService.settings.baseUnit;
    this._currentQuantitySelectedUnit = value;
    this.convertQuantityToSelectedUnit();
  }

  columns: IDataTableColumn[];
  newCargoList: any[] = [];
  totalDifference = 0;
  totalPlanned = 0;
  totalActual = 0;
  isTotalPositive = true;
  prevQuantitySelectedUnit: QUANTITY_UNIT;
  readonly fieldType = DATATABLE_FIELD_TYPE;

  private _currentQuantitySelectedUnit: QUANTITY_UNIT;
  private _cargoQuantities: any[];
  constructor(
    private loadingDischargingCargoDetailsTableTransformationService: LoadingDischargingCargoDetailsTableTransformationService,
    private quantityPipe: QuantityPipe
  ) { }

  /**
   * Component lifecycle ngOnit
   *
   * @memberof LoadingDischargingCargoDetailsTableComponent
   */
  ngOnInit(): void {
    this.columns = this.loadingDischargingCargoDetailsTableTransformationService.getColumnFields();
  }

  /**
   * Set cargo details for grid
   *
   * @memberof LoadingDischargingCargoDetailsTableComponent
   */
  setCargoDetails() {
    this.newCargoList = this.cargoConditions?.map(itm => ({
      ...this.cargoQuantities?.find((item) => item.abbreviation === itm.abbreviation),
      ...itm
    }));
    this.prevQuantitySelectedUnit = AppConfigurationService.settings.baseUnit;
    if(this.currentQuantitySelectedUnit){
      this.convertQuantityToSelectedUnit();
    }

  }

  /**
   * Method to convert quantity to selected unit
   *
   * @memberof LoadingDischargingCargoDetailsTableComponent
   */
  convertQuantityToSelectedUnit() {
    this.totalPlanned = 0;
    this.totalActual = 0;
    this.totalDifference = 0;

    this.newCargoList?.map(cargoList => {
      const plannedWeight = this.quantityPipe.transform(cargoList.plannedWeight, this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit, cargoList?.api);
      cargoList.plannedWeight = plannedWeight ? Number(plannedWeight) : 0;
      const actualWeight = this.quantityPipe.transform(cargoList.actualWeight, this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit, cargoList?.api);
      cargoList.actualWeight = actualWeight ? Number(actualWeight) : 0;

      this.totalPlanned = cargoList.plannedWeight + this.totalPlanned;
      const difference = cargoList.actualWeight - cargoList.plannedWeight;
      this.totalActual = cargoList.actualWeight + this.totalActual;
      cargoList.difference = difference ? Number(difference) : 0;
      this.totalDifference = difference + this.totalDifference;
      difference > 0 ? cargoList.isPositive = true : cargoList.isPositive = false;
    });

    this.totalPlanned = this.totalPlanned ? Number(this.totalPlanned) : 0;
    this.totalActual = this.totalActual ? Number(this.totalActual) : 0;
    this.totalDifference = this.totalDifference ? Number(this.totalDifference) : 0;
    this.totalDifference > 0 ? this.isTotalPositive = true : this.isTotalPositive = false;
  }

}

import { DecimalPipe } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { DATATABLE_EDITMODE, IDataTableColumn } from '../../../shared/components/datatable/datatable.model';
import { QUANTITY_UNIT, ValueObject } from '../../../shared/models/common.model';
import { QuantityPipe } from '../../../shared/pipes/quantity/quantity.pipe';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';
import { ICargo, OPERATIONS } from '../../core/models/common.model';
import { ICargoVesselTankDetails, ILoadedCargo } from '../models/loading-discharging.model';
import { LoadingDischargingTransformationService } from '../services/loading-discharging-transformation.service';
import { QuantityDecimalFormatPipe } from '../../../shared/pipes/quantity-decimal-format/quantity-decimal-format.pipe'

/**
 * Componenet class for cargo to be loaded or discharge section
 *
 * @export
 * @class CargoToBeLoadedDischargeComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-cargo-to-be-loaded-discharged',
  templateUrl: './cargo-to-be-loaded-discharged.component.html',
  styleUrls: ['./cargo-to-be-loaded-discharged.component.scss']
})
export class CargoToBeLoadedDischargedComponent implements OnInit {

  @Input()
  get operation(): OPERATIONS {
    return this._operation;
  }

  set operation(value: OPERATIONS) {
    this._operation = value;
    this.editMode = value === OPERATIONS.DISCHARGING ? DATATABLE_EDITMODE.CELL : null;
  }
  
  @Input() cargos: ICargo[];
  @Input() prevQuantitySelectedUnit: QUANTITY_UNIT;
  @Input() get currentQuantitySelectedUnit(): QUANTITY_UNIT {
    return this._currentQuantitySelectedUnit;
  }
  set currentQuantitySelectedUnit(value: QUANTITY_UNIT) {
    this.prevQuantitySelectedUnit = this.currentQuantitySelectedUnit ?? AppConfigurationService.settings.baseUnit;
    this._currentQuantitySelectedUnit = value;
    if (this.cargoVesselTankDetails?.loadableQuantityCargoDetails) {
      this.updateCargoTobeLoadedDischargedData();
    }
  }
  @Input() get cargoVesselTankDetails(): ICargoVesselTankDetails {
    return this._cargoVesselTankDetails;
  }
  set cargoVesselTankDetails(cargoVesselTankDetails: ICargoVesselTankDetails) {
    this._cargoVesselTankDetails = cargoVesselTankDetails;
    if (cargoVesselTankDetails?.loadableQuantityCargoDetails) {
      this.updateCargoTobeLoadedDischargedData();
    }
  }

  @Input() form: FormGroup;
  @Input() listData;

  get cargoTobeLoadedDischargedForm() {
    return <FormGroup> this.form.get('cargoTobeLoadedDischarged');
  }

  cargoTobeLoadedDischargedColumns: IDataTableColumn[];
  cargoTobeLoadedDischarged: ILoadedCargo[] = [];
  editMode: DATATABLE_EDITMODE;

  private _currentQuantitySelectedUnit: QUANTITY_UNIT;
  private _operation: OPERATIONS;
  private _cargoVesselTankDetails: ICargoVesselTankDetails;

  constructor(
    private _decimalPipe: DecimalPipe,
    private quantityPipe: QuantityPipe,
    private loadingDischargingTransformationService: LoadingDischargingTransformationService,
    private fb: FormBuilder,
    private quantityDecimalFormatPipe: QuantityDecimalFormatPipe
  ) { }

  async ngOnInit(): Promise<void> {
    this.cargoTobeLoadedDischargedColumns = this.operation === OPERATIONS.LOADING ? this.loadingDischargingTransformationService.getCargoToBeLoadedDatatableColumns(this.currentQuantitySelectedUnit) : this.loadingDischargingTransformationService.getCargoToBeDischargedDatatableColumns(this.currentQuantitySelectedUnit);
    this.prevQuantitySelectedUnit = AppConfigurationService.settings.baseUnit;
    this.updateCargoTobeLoadedDischargedData();
  }

  /**
  * Method to update cargo to be loaded or discharged data
  *
  * @memberof LoadingDischargingCargoDetailsComponent
  */
  updateCargoTobeLoadedDischargedData() {
    if (this.operation === OPERATIONS.LOADING) {
      this.updateCargoTobeLoadedData();
    } else {
      this.updateCargoTobeDischargedData();
    }
  }

  /**
  * Method to update cargo to be loaded data
  *
  * @memberof LoadingDischargingCargoDetailsComponent
  */
  updateCargoTobeLoadedData() {
    this.cargoTobeLoadedDischarged = this.cargoVesselTankDetails?.loadableQuantityCargoDetails?.map(cargo => {
      if (cargo) {
        const minTolerence = this.loadingDischargingTransformationService.decimalConvertion(this._decimalPipe, cargo.minTolerence, '0.2-2');
        const maxTolerence = this.loadingDischargingTransformationService.decimalConvertion(this._decimalPipe, cargo.maxTolerence, '0.2-2');
        cargo.minMaxTolerance = maxTolerence + (minTolerence ? "/" + minTolerence : '');
        cargo.differencePercentage = cargo.differencePercentage ? (cargo.differencePercentage.includes('%') ? cargo.differencePercentage : cargo.differencePercentage + '%') : '';
        cargo.grade = this.findCargo(cargo);
        const orderedQuantity = this.quantityPipe.transform(this.loadingDischargingTransformationService.convertToNumber(cargo?.orderedQuantity), this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit, cargo?.estimatedAPI, cargo?.estimatedTemp, -1);   
        cargo.orderedQuantity = this.quantityDecimalFormatPipe.transform(orderedQuantity,this.currentQuantitySelectedUnit).toString().replace(/,/g,'');
        
        const actualQuantity = this.quantityPipe.transform(this.loadingDischargingTransformationService.convertToNumber(cargo?.loadableMT), QUANTITY_UNIT.MT , this.currentQuantitySelectedUnit, cargo?.estimatedAPI, cargo?.estimatedTemp, -1);
        cargo.actualQuantity = actualQuantity?.toString();
        
        const slopQuantity = cargo?.slopQuantity ? this.quantityPipe.transform(cargo?.slopQuantity.toString(), this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit, cargo?.estimatedAPI, cargo?.estimatedTemp, -1) : 0;
        cargo.slopQuantity = Number(this.quantityDecimalFormatPipe.transform(slopQuantity,this.currentQuantitySelectedUnit).toString().replace(/,/g,''));
        cargo.loadingPortsLabels = cargo?.loadingPorts?.join(',');
      }
      return cargo;
    });

    this.form = this.fb.group({
      cargoTobeLoadedDischarged: this.fb.group({
        dataTable: this.fb.array([...this.cargoTobeLoadedDischarged])
      })
    });
  }

  /**
  * Method to update cargo to be discharged data
  *
  * @memberof LoadingDischargingCargoDetailsComponent
  */
  updateCargoTobeDischargedData() {
    this.cargoTobeLoadedDischarged = this.cargoVesselTankDetails.loadableQuantityCargoDetails?.map(cargo => {
      if (cargo) {
        cargo.grade = this.findCargo(cargo);
        const blFigure = this.quantityPipe.transform(this.loadingDischargingTransformationService.convertToNumber(cargo?.blFigure), this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit, cargo?.estimatedAPI, cargo?.estimatedTemp, -1);
        cargo.blFigure = blFigure?.toString();

        const shipFigure = this.quantityPipe.transform(this.loadingDischargingTransformationService.convertToNumber(cargo?.shipFigure), this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit, cargo?.estimatedAPI, cargo?.estimatedTemp, -1);
        cargo.shipFigure = shipFigure?.toString();

        const slopQuantityObj = (<ValueObject<number>>cargo?.slopQuantity);
        const slopQuantity = cargo?.slopQuantity ? this.quantityPipe.transform(this.loadingDischargingTransformationService.convertToNumber(slopQuantityObj?.value?.toString()), this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit, cargo?.estimatedAPI, cargo?.estimatedTemp, -1) : 0;
        slopQuantityObj.value = slopQuantity;

        cargo.loadingPortsLabels = cargo?.loadingPorts?.join(',');
      }
      return cargo;
    });
  }

  /**
  * Method to find out cargo name
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

import { DecimalPipe } from '@angular/common';
import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { DATATABLE_EDITMODE, IDataTableColumn, IDataTableEvent } from '../../../shared/components/datatable/datatable.model';
import { QUANTITY_UNIT, ValueObject } from '../../../shared/models/common.model';
import { QuantityPipe } from '../../../shared/pipes/quantity/quantity.pipe';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';
import { ICargo, OPERATIONS } from '../../core/models/common.model';
import { ICargoVesselTankDetails, ILoadedCargo } from '../models/loading-discharging.model';
import { LoadingDischargingTransformationService } from '../services/loading-discharging-transformation.service';
import { QuantityDecimalFormatPipe } from '../../../shared/pipes/quantity-decimal-format/quantity-decimal-format.pipe'
import { takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';
import { QuantityDecimalService } from '../../../shared/services/quantity-decimal/quantity-decimal.service';
import { numberValidator } from '../../core/directives/number-validator.directive';

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
export class CargoToBeLoadedDischargedComponent implements OnInit, OnDestroy {

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
  @Input() isDischargePlanGenerated: boolean;
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

  @Output() updateCargoToBeLoaded = new EventEmitter<ILoadedCargo[]>();

  get cargoTobeLoadedDischargedForm() {
    return <FormGroup> this.form?.get('cargoTobeLoadedDischarged');
  }

  set cargoTobeLoadedDischargedForm(form: FormGroup) {
    this.form?.setControl('cargoTobeLoadedDischarged', form);
  }

  cargoTobeLoadedDischargedColumns: IDataTableColumn[];
  cargoTobeLoadedDischarged: ILoadedCargo[] = [];
  editMode: DATATABLE_EDITMODE;

  private _currentQuantitySelectedUnit: QUANTITY_UNIT;
  private _operation: OPERATIONS;
  private _cargoVesselTankDetails: ICargoVesselTankDetails;
  private ngUnsubscribe: Subject<any> = new Subject();

  constructor(
    private _decimalPipe: DecimalPipe,
    private quantityPipe: QuantityPipe,
    private loadingDischargingTransformationService: LoadingDischargingTransformationService,
    private fb: FormBuilder,
    private quantityDecimalFormatPipe: QuantityDecimalFormatPipe,
    private quantityDecimalService: QuantityDecimalService
  ) { }

  ngOnInit() {
    this.prevQuantitySelectedUnit = AppConfigurationService.settings.baseUnit;
    this.updateCargoTobeLoadedDischargedData();
  }

  ngOnDestroy() {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  /**
  * Method to update cargo to be loaded or discharged data
  *
  * @memberof LoadingDischargingCargoDetailsComponent
  */
  updateCargoTobeLoadedDischargedData() {
    this.cargoTobeLoadedDischargedColumns = this.operation === OPERATIONS.LOADING ? this.loadingDischargingTransformationService.getCargoToBeLoadedDatatableColumns(this.currentQuantitySelectedUnit) : this.loadingDischargingTransformationService.getCargoToBeDischargedDatatableColumns(this.currentQuantitySelectedUnit);
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

        const convertedOrderedQuantity = this.quantityPipe.transform(this.loadingDischargingTransformationService.convertToNumber(cargo?.orderedQuantity), QUANTITY_UNIT.MT , this.currentQuantitySelectedUnit, cargo?.estimatedAPI, cargo?.estimatedTemp, -1);
        cargo.convertedOrderedQuantity = convertedOrderedQuantity.toString();

        const shipFigure = this.quantityPipe.transform(this.loadingDischargingTransformationService.convertToNumber(cargo?.loadableMT), QUANTITY_UNIT.MT , this.currentQuantitySelectedUnit, cargo?.estimatedAPI, cargo?.estimatedTemp, -1);
        cargo.shipFigure = shipFigure?.toString();

        const convertedSlopQuantity = cargo?.slopQuantity ? this.quantityPipe.transform(cargo?.slopQuantity.toString(), QUANTITY_UNIT.MT, this.currentQuantitySelectedUnit, cargo?.estimatedAPI, cargo?.estimatedTemp, -1) : 0;
        cargo.convertedSlopQuantity = convertedSlopQuantity?.toString();
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
        const blFigure = Number(cargo?.blFigure) ? this.quantityPipe.transform(this.loadingDischargingTransformationService.convertToNumber(cargo?.blFigure), this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit, cargo?.estimatedAPI, cargo?.estimatedTemp, -1) : 0;
        cargo.blFigure = blFigure.toString();

        const shipFigure = Number(cargo?.loadableMT) ? this.quantityPipe.transform(this.loadingDischargingTransformationService.convertToNumber(cargo?.loadableMT), QUANTITY_UNIT.MT, this.currentQuantitySelectedUnit, cargo?.estimatedAPI, cargo?.estimatedTemp, -1) : 0;
        cargo.shipFigure = shipFigure.toString();

        const slopQuantityObj = (<ValueObject<number>>cargo?.slopQuantity);
        const slopQuantity = cargo?.slopQuantityMT ? this.quantityPipe.transform(this.loadingDischargingTransformationService.convertToNumber(cargo.slopQuantityMT), QUANTITY_UNIT.MT, this.currentQuantitySelectedUnit, cargo?.estimatedAPI, cargo?.estimatedTemp) : 0;
        slopQuantityObj.value = slopQuantity;

        if (this.isDischargePlanGenerated) {
          for (const key in cargo) {
            if (cargo[key]?.hasOwnProperty('_isEditMode') && cargo[key]?.hasOwnProperty('_isEditable')) {
              cargo[key].isEditMode = false;
              cargo[key].isEditable = false;
            }
          }
        }
      }
      return cargo;
    });

    const quantityDecimal = this.quantityDecimalService.quantityDecimal();
    const min = quantityDecimal ? (1 / Math.pow(10, quantityDecimal)) : 1;
    const cargoToBeDischarged = this.cargoTobeLoadedDischarged?.map(cargo => {
      return this.fb.group({
        isCommingledCargo: cargo?.isCommingledCargo ?? false,
        protested: this.fb.control(cargo.protested.value),
        isCommingledDischarge: this.fb.control(cargo?.isCommingledDischarge.value),
        slopQuantityMT: cargo?.slopQuantityMT,
        slopQuantity: this.fb.control((<ValueObject<number>>cargo?.slopQuantity)?.value, [Validators.required, Validators.min(min), numberValidator(quantityDecimal, 7, false)]),
      })
    });

    if (this.form) {
      this.cargoTobeLoadedDischargedForm = this.fb.group({
        dataTable: this.fb.array([...cargoToBeDischarged])
      });
    } else {
      this.form = this.fb.group({
        cargoTobeLoadedDischarged: this.fb.group({
          dataTable: this.fb.array([...cargoToBeDischarged])
        })
      });
    }

    this.form?.controls?.dischargeCommingledCargoSeparately?.valueChanges.pipe(takeUntil(this.ngUnsubscribe)).subscribe((value) => {
      const dataTableForm = <FormArray>this.cargoTobeLoadedDischargedForm?.get('dataTable');
      dataTableForm?.controls?.forEach((formGroup) => {
        if (formGroup.value?.isCommingledCargo) {
          const isCommingledDischargeFormControl = <FormControl>formGroup.get('isCommingledDischarge');
          isCommingledDischargeFormControl.setValue(false);
          if (value) {
            isCommingledDischargeFormControl.disable();
          } else {
            isCommingledDischargeFormControl.enable();
          }
        }
      });
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

  /**
   * Handler for cargo tobe load/discharged grid input change event
   *
   * @param {IDataTableEvent} event
   * @memberof CargoToBeLoadedDischargedComponent
   */
  onEditComplete(event: IDataTableEvent) {
    if(this.operation === OPERATIONS.DISCHARGING) {
      this.cargoTobeLoadedDischarged[event?.index][event?.field].value = event?.data[event?.field]?.value;
      if (event?.field === 'slopQuantity') {
        this.cargoTobeLoadedDischarged[event?.index].slopQuantityMT = this.quantityPipe.transform(event?.data[event?.field]?.value, this.currentQuantitySelectedUnit, QUANTITY_UNIT.MT, event?.data?.estimatedAPI, event?.data?.estimatedTemp, -1).toString();
      }
      this.updateCargoToBeLoaded.emit(this.cargoTobeLoadedDischarged);
    }
  }

}

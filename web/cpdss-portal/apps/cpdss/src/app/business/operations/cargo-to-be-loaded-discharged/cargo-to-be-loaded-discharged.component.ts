import { DecimalPipe } from '@angular/common';
import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
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
import { IValidationErrorMessagesSet } from '../../../shared/components/validation-error/validation-error.model';

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
  }

  @Input() editMode = DATATABLE_EDITMODE.CELL;

  @Input() cargos: ICargo[];
  @Input() slopQuantity = 0;
  @Input() prevQuantitySelectedUnit: QUANTITY_UNIT;
  @Input() isPlanGenerated: boolean;
  @Input() get currentQuantitySelectedUnit(): QUANTITY_UNIT {
    return this._currentQuantitySelectedUnit;
  }
  set currentQuantitySelectedUnit(value: QUANTITY_UNIT) {
    this.prevQuantitySelectedUnit = this.currentQuantitySelectedUnit ?? AppConfigurationService.settings.baseUnit;
    this._currentQuantitySelectedUnit = value;
  }
  @Input() get cargoVesselTankDetails(): ICargoVesselTankDetails {
    return this._cargoVesselTankDetails;
  }
  set cargoVesselTankDetails(cargoVesselTankDetails: ICargoVesselTankDetails) {
    this._cargoVesselTankDetails = cargoVesselTankDetails;
    this.slopTankFullCapacity = 0;
    cargoVesselTankDetails.cargoTanks.forEach(tanksArray => {
      tanksArray.forEach(tank => {
        if (tank.slopTank) {
          this.slopTankFullCapacity += Number(tank?.fullCapacityCubm);
        }
      })
    });
    if (cargoVesselTankDetails?.loadableQuantityCargoDetails) {
      this.updateCargoTobeLoadedDischargedData();
    }
  }

  @Input() form: FormGroup;
  @Input() listData;

  @Output() updateCargoToBeLoaded = new EventEmitter<ILoadedCargo[]>();
  @Output() updateSlopQuantity = new EventEmitter<number>();

  get cargoTobeLoadedDischargedForm() {
    return <FormGroup>this.form?.get('cargoTobeLoadedDischarged');
  }

  set cargoTobeLoadedDischargedForm(form: FormGroup) {
    this.form?.setControl('cargoTobeLoadedDischarged', form);
  }

  cargoTobeLoadedDischargedColumns: IDataTableColumn[];
  cargoTobeLoadedDischarged: ILoadedCargo[] = [];
  slopTankFullCapacity = 0;
  errorMesages: IValidationErrorMessagesSet = {
    slopQuantity: {
      'required': 'DISCHARGING_CARGO_TO_BE_DISCHARGED_REQUIRED',
      'min': 'DISCHARGING_CARGO_TO_BE_DISCHARGED_SLOP_QUANTITY_MIN_ERROR',
      'max': 'DISCHARGING_CARGO_TO_BE_DISCHARGED_SLOP_QUANTITY_MAX_ERROR',
      'invalidNumber': 'DISCHARGING_CARGO_TO_BE_DISCHARGED_INVALID'
    }
  };

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
    this.cargoTobeLoadedDischargedColumns = this.operation === OPERATIONS.LOADING ? this.loadingDischargingTransformationService.getCargoToBeLoadedDatatableColumns(QUANTITY_UNIT.BBLS) : this.loadingDischargingTransformationService.getCargoToBeDischargedDatatableColumns(QUANTITY_UNIT.BBLS);
    if (this.operation === OPERATIONS.LOADING) {
      this.updateCargoTobeLoadedData();
    } else if (this.operation === OPERATIONS.DISCHARGING) {
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
      if (cargo && ((this.operation === OPERATIONS.DISCHARGING && cargo.shipFigure) || (this.operation === OPERATIONS.LOADING))) {
        const minTolerence = this.loadingDischargingTransformationService.decimalConvertion(this._decimalPipe, cargo.minTolerence, '0.2-2');
        const maxTolerence = this.loadingDischargingTransformationService.decimalConvertion(this._decimalPipe, cargo.maxTolerence, '0.2-2');
        cargo.minMaxTolerance = maxTolerence + (minTolerence ? "/" + minTolerence : '');
        cargo.differencePercentage = cargo.differencePercentage ? (cargo.differencePercentage.includes('%') ? cargo.differencePercentage : cargo.differencePercentage + '%') : '';
        cargo.grade = this.findCargo(cargo);

        const convertedOrderedQuantity = this.quantityPipe.transform(this.loadingDischargingTransformationService.convertToNumber(cargo?.orderedQuantity), QUANTITY_UNIT.MT, QUANTITY_UNIT.BBLS, cargo?.estimatedAPI, cargo?.estimatedTemp, -1);
        cargo.convertedOrderedQuantity = convertedOrderedQuantity.toString();

        const shipFigure = this.quantityPipe.transform(this.loadingDischargingTransformationService.convertToNumber(cargo?.loadableMT), QUANTITY_UNIT.MT, QUANTITY_UNIT.BBLS, cargo?.estimatedAPI, cargo?.estimatedTemp, -1);
        cargo.shipFigure = shipFigure?.toString();

        cargo.loadingPortsLabels = cargo?.loadingPorts?.join(',');
        cargo['estimatedAPIEdit'] = new ValueObject<number | string>(cargo?.estimatedAPI, true, false, true, false);
        cargo['estimatedTempEdit'] = new ValueObject<number | string>(cargo?.estimatedTemp, true, false, true, false);
        cargo['maxLoadingRateEdit'] = new ValueObject<number | string>(cargo?.maxLoadingRate, true, false, true, false);

        if (this.isPlanGenerated) {
          for (const key in cargo) {
            if (cargo[key]?.hasOwnProperty('_isEditMode') && cargo[key]?.hasOwnProperty('_isEditable')) {
              cargo[key].isEditMode = false;
              cargo[key].isEditable = false;
            }
          }
        }
        cargo.isAdd = true;
      }
      return cargo;
    });

    const cargoToBeLoaded = this.cargoTobeLoadedDischarged?.map(cargo => {
      return this.fb.group({
        estimatedAPIEdit: this.fb.control(cargo.estimatedAPIEdit.value, [Validators.required, Validators.min(8), numberValidator(2, 2, false)]),
        estimatedTempEdit: this.fb.control(cargo.estimatedTempEdit.value, [Validators.required, Validators.min(40), Validators.max(160), numberValidator(2, 3, false)]),
        maxLoadingRateEdit: this.fb.control(cargo.maxLoadingRateEdit.value, [Validators.required, numberValidator(0, 7, false)]),
      })
    });
    const quantityDecimal = this.quantityDecimalService.quantityDecimal(QUANTITY_UNIT.OBSKL);
    this.cargoTobeLoadedDischargedForm = this.fb.group({
      dataTable: this.fb.array([...cargoToBeLoaded]),
      slopQuantity: this.fb.control(this.slopQuantity, [Validators.min(0), Validators.max(this?.slopTankFullCapacity), numberValidator(quantityDecimal, 7, false)]),
    });
    this.form = this.fb.group({
      cargoTobeLoadedDischarged: this.fb.group({
        dataTable: this.fb.array([...cargoToBeLoaded]),
        slopQuantity: this.fb.control(this.slopQuantity, [Validators.min(0), Validators.max(this?.slopTankFullCapacity), numberValidator(quantityDecimal, 7, false)]),
      })
    });
  }

  /**
  * Method to update cargo to be discharged data
  *
  * @memberof LoadingDischargingCargoDetailsComponent
  */
  async updateCargoTobeDischargedData() {
    this.cargoTobeLoadedDischarged = this.cargoVesselTankDetails.loadableQuantityCargoDetails?.map(cargo => {
      if (cargo) {
        cargo.grade = this.findCargo(cargo);

        cargo.blFigure = cargo?.blFigure ?? "0";

        const shipFigure = Number(cargo?.loadableMT) ? this.quantityPipe.transform(this.loadingDischargingTransformationService.convertToNumber(cargo?.loadableMT), QUANTITY_UNIT.MT, QUANTITY_UNIT.BBLS, cargo?.estimatedAPI, cargo?.estimatedTemp, -1) : 0;
        cargo.shipFigure = shipFigure.toString();

        cargo['estimatedAPIEdit'] = new ValueObject<number | string>(cargo?.estimatedAPI, true, false, true, false);
        cargo['estimatedTempEdit'] = new ValueObject<number | string>(cargo?.estimatedTemp, true, false, true, false);
        cargo['maxDischargingRateEdit'] = new ValueObject<number | string>(cargo?.maxDischargingRate, true, false, true, false);
        if (this.isPlanGenerated) {
          for (const key in cargo) {
            if (cargo[key]?.hasOwnProperty('_isEditMode') && cargo[key]?.hasOwnProperty('_isEditable')) {
              cargo[key].isEditMode = false;
              cargo[key].isEditable = false;
            }
          }
        }
        cargo.isAdd = true;
      }
      return cargo;
    });

    const quantityDecimal = this.quantityDecimalService.quantityDecimal(QUANTITY_UNIT.OBSKL);
    const cargoToBeDischarged = this.cargoTobeLoadedDischarged?.map(cargo => {
      return this.fb.group({
        isCommingledCargo: cargo?.isCommingledCargo ?? false,
        protested: this.fb.control(cargo.protested.value),
        isCommingledDischarge: this.fb.control(cargo?.isCommingledDischarge.value),
        estimatedAPIEdit: this.fb.control(cargo.estimatedAPIEdit.value, [Validators.required, Validators.min(8), numberValidator(2, 2, false)]),
        estimatedTempEdit: this.fb.control(cargo.estimatedTempEdit.value, [Validators.required, Validators.min(40), Validators.max(160), numberValidator(2, 3, false)]),
        maxDischargingRateEdit: this.fb.control(cargo.maxDischargingRateEdit.value, [Validators.required, numberValidator(0, 7, false)])
      })

    });

    if (this.form) {
      this.cargoTobeLoadedDischargedForm = this.fb.group({
        dataTable: this.fb.array([...cargoToBeDischarged]),
        slopQuantity: this.fb.control(this.slopQuantity, [Validators.min(0), Validators.max(this?.slopTankFullCapacity), numberValidator(quantityDecimal, 7, false)]),
      });
    } else {
      this.form = this.fb.group({
        cargoTobeLoadedDischarged: this.fb.group({
          dataTable: this.fb.array([...cargoToBeDischarged]),
          slopQuantity: this.fb.control(this.slopQuantity, [Validators.min(0), Validators.max(this?.slopTankFullCapacity), numberValidator(quantityDecimal, 7, false)]),
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
    if (this.operation === OPERATIONS.DISCHARGING) {
      this.cargoTobeLoadedDischarged[event?.index][event?.field].value = event?.data[event?.field]?.value;
    }
    switch (event?.field) {
      case 'estimatedAPIEdit':
        this.cargoTobeLoadedDischarged[event?.index].estimatedAPI = event?.data[event?.field]?.value;
        break;
      case 'estimatedTempEdit':
        this.cargoTobeLoadedDischarged[event?.index].estimatedTemp = event?.data[event?.field]?.value;
        break;
      case 'maxDischargingRateEdit':
        this.cargoTobeLoadedDischarged[event?.index].maxDischargingRate = event?.data[event?.field]?.value;
        break;
      case 'maxLoadingRateEdit':
        this.cargoTobeLoadedDischarged[event?.index].maxLoadingRate = event?.data[event?.field]?.value;
        break;
      default: {
        break;
      }
    }
    this.updateCargoToBeLoaded.emit(this.cargoTobeLoadedDischarged);
    this.cargoTobeLoadedDischargedForm.markAllAsTouched();
    this.cargoTobeLoadedDischargedForm.markAsDirty();
  }

  /**
   * Handler for cargo setting loading rate validation check
   *
   * @param {IDataTableEvent} event
   * @memberof CargoToBeLoadedDischargedComponent
   */
  updateRateValidation() {
    setTimeout(() => {
      this.updateCargoToBeLoaded.emit(this.cargoTobeLoadedDischarged);
    });
  }

  /**
   * Handler for change event of slop quantity field
   *
   * @memberof CargoToBeLoadedDischargedComponent
   */
  onChange() {
    this.updateSlopQuantity.emit(this.cargoTobeLoadedDischargedForm?.value?.slopQuantity);
  }

  /**
   * Method to check for field errors
   *
   * @param {string} formControlName
   * @return {ValidationErrors}
   * @memberof CargoToBeLoadedDischargedComponent
   */
  fieldError(formControlName: string): ValidationErrors {
    const formControl = this.field(formControlName);
    return formControl?.invalid ? formControl?.errors : null;
  }

  /**
   * Method to get formControl
   * @param {string} formControlName
   * @return {FormControl}
   * @memberof CargoToBeLoadedDischargedComponent
  */
  field(formControlName: string): FormControl {
    const formControl = <FormControl>this.cargoTobeLoadedDischargedForm?.get(formControlName);
    return formControl;
  }

}

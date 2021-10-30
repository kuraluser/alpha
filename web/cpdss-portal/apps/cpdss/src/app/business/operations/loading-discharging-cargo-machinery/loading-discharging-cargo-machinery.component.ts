import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MessageService } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';
import { ILoadingMachinesInUse, IMachineryInUses, IMachineTankTypes, MACHINE_TYPES, PUMP_TYPES, IDischargingMachinesInUse } from '../models/loading-discharging.model';
import { OPERATIONS } from '../../core/models/common.model';
import { IValidationErrorMessagesSet } from '../../../shared/components/validation-error/validation-error.model';
import { FormArray, FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { LoadingDischargingTransformationService } from '../services/loading-discharging-transformation.service';

@Component({
  selector: 'cpdss-portal-loading-discharging-cargo-machinery',
  templateUrl: './loading-discharging-cargo-machinery.component.html',
  styleUrls: ['./loading-discharging-cargo-machinery.component.scss']
})

/**
 * Component class for loading discharging cargo machinery component
 *
 * @export
 * @class LoadingDischargingCargoMachineryComponent
 * @implements {OnInit}
 */
export class LoadingDischargingCargoMachineryComponent implements OnInit {
  @Input() loadingInfoId: number;
  @Input() dichargingInfoId: number;
  @Input() operation: OPERATIONS;
  @Input() form: FormGroup;

  @Input() get machineryInUses(): IMachineryInUses {
    return this._machineryInUses;
  }

  set machineryInUses(machineryInUses: IMachineryInUses) {
    this._machineryInUses = machineryInUses;
    if (machineryInUses) {
      this.initMachinery();
    }
  }

  get machineryForm() {
    return <FormGroup>this.form?.get('machinery');
  }

  set machineryForm(form: FormGroup) {
    this.form?.setControl('machinery', form);
  }

  @Output() updatemachineryInUses: EventEmitter<Array<ILoadingMachinesInUse | IDischargingMachinesInUse>> = new EventEmitter();

  private _machineryInUses: IMachineryInUses;

  machineries: any = [];
  machineriesKey: string[] = [];
  cargoMachineryValues: any = [];
  pumpValues: any = [];
  machinery: any = [];
  selectedType: IMachineTankTypes;
  errorMessages: IValidationErrorMessagesSet;
  readonly MACHINE_TYPES = MACHINE_TYPES;
  readonly OPERATIONS = OPERATIONS;
  readonly PUMP_TYPES = PUMP_TYPES;

  constructor(
    private messageService: MessageService,
    private translateService: TranslateService,
    private fb: FormBuilder,
    private loadingDischargingTransformationService: LoadingDischargingTransformationService
  ) { }

  ngOnInit(): void {
    this.errorMessages = {
      capacity: {
        "required": "LOADING_INFORMATION_CARGO_MACHINERY_REQUIRED",
        "min": "LOADING_INFORMATION_CARGO_MACHINERY_MIN_ERROR",
        "max": "LOADING_INFORMATION_CARGO_MACHINERY_MAX_ERROR",
      }
    }
  }

  /**
  * Method for Initialise machinery
  *
  * @memberof LoadingDischargingCargoMachineryComponent
  */
  initMachinery() {
    this.machineryInUses.loadingDischargingMachinesInUses = this.machineryInUses?.loadingDischargingMachinesInUses ?? [];
    const usedManifold = this.machineryInUses?.loadingDischargingMachinesInUses?.find(machine => machine.machineTypeId === this.machineryInUses.machineTypes.MANIFOLD);
    const usedType = this.machineryInUses?.vesselManifold?.find(manifold => manifold.id === usedManifold?.machineId)
    this.selectedType = usedType ? this.machineryInUses?.tankTypes?.find(type => type.id === usedType.componentType) : this.machineryInUses?.tankTypes[0];
    this.updateMachinery();
  }

  /**
  * Method for Update machinery
  *
  * @memberof LoadingDischargingCargoMachineryComponent
  */
  updateMachinery() {
    this.machineries = [];
    this.machineryInUses?.vesselPumps?.map((vesselpump) => {
      vesselpump.isUsing = this.machineryInUses?.loadingDischargingMachinesInUses?.some(machine => machine.machineId === vesselpump.id && machine.machineTypeId === vesselpump.machineType);
      const machinaryUsed = this.machineryInUses?.loadingDischargingMachinesInUses?.find((machine) => machine.machineId === vesselpump.id && machine.machineTypeId === vesselpump.machineType);
      if (machinaryUsed) {
        vesselpump.capacity = machinaryUsed.capacity;
      } else {
        vesselpump.capacity = vesselpump.pumpCapacity;
      }
      this.machineryInUses?.loadingDischargingMachinesInUses?.map((machine) => {
        if (machine.machineId === vesselpump.id && machine.machineTypeId === vesselpump.machineType) {
          machine.pumpTypeId = vesselpump.pumpTypeId + '';
          machine.pumpCapacity = vesselpump?.pumpCapacity;
        };
      });
    })
    this.machinery = this.machineryInUses?.vesselPumps?.reduce((acc, obj) => {
      const key = obj['pumpTypeId'];
      if (!acc[key]) {
        acc[key] = [];
      }
      // Add object to list for given key's value
      acc[key].push(obj);
      return acc;
    }, {});
    let filteredManiFoldMachineByTankType = [];
    filteredManiFoldMachineByTankType = this.machineryInUses.vesselManifold.filter((manifold) => manifold.componentType === this.selectedType.id);
    filteredManiFoldMachineByTankType?.map((manifold) => {
      manifold.isUsing = this.machineryInUses?.loadingDischargingMachinesInUses?.some(machine => machine.machineId === manifold.id && machine.machineTypeId === manifold.machineTypeId);
    });
    const manifoldObject = {
      machine: 'Manifold',
      columns: [...filteredManiFoldMachineByTankType],
      field: 'componentCode'
    }
    this.machineryInUses.vesselBottomLine.map((bottoLine) => {
      bottoLine.isUsing = this.machineryInUses?.loadingDischargingMachinesInUses?.some(machine => machine.machineId === bottoLine.id && machine.machineTypeId === bottoLine.machineTypeId);
    });
    const bottomLineObject = {
      machine: 'BottomLine',
      columns: [...this.machineryInUses.vesselBottomLine],
      field: 'componentCode'
    }
    this.cargoMachineryValues = [];
    this.cargoMachineryValues.push(manifoldObject);
    this.cargoMachineryValues.push(bottomLineObject);
    const _cargoMachinery = <FormArray>this.fb.array([]);
    this.cargoMachineryValues.forEach((group) => {
      group?.columns?.forEach((machine, i) => {
        _cargoMachinery.push(this.fb.group({
          selectedType: i === 0 && machine.machineTypeId === 2 ? this.fb.control(this.selectedType) : null,
          isUsing: this.fb.control(machine?.isUsing)
        }));
      });
    });
    this.machineries['cargoMachineryValues'] = this.cargoMachineryValues;
    const vesselPumbArray = [];
    this.machineryInUses?.pumpTypes?.forEach((pump) => {
      if (this.machinery[pump.id]) {
        vesselPumbArray.push({
          machine: pump.name,
          columns: this.machinery[pump.id],
          field: 'pumpName',
          pumpTypeId: pump.id
        });
      }
    });
    this.pumpValues = [...vesselPumbArray];
    const _pumps = <FormArray>this.fb.array([]);
    this.pumpValues.forEach((group) => {
      group?.columns?.forEach(pump => {
        _pumps.push(this.fb.group({
          isUsing: this.fb.control(pump?.isUsing),
          capacity: this.fb.control({ value: pump?.capacity, disabled: !pump?.isUsing }, [Validators.required, Validators.min(1), Validators.max(pump?.pumpCapacity)])
        }));
      });
    });

    this.machineries['pumpValues'] = this.pumpValues;
    this.machineriesKey = Object.keys(this.machineries);

    const machineryFormGroup: FormGroup = this.fb.group({
      cargoMachinery: _cargoMachinery,
      pumps: _pumps
    });

    if (this.operation === OPERATIONS.DISCHARGING) {
      if (this.form) {
        this.machineryForm = machineryFormGroup;
      } else {
        this.form = this.fb.group({
          machinery: machineryFormGroup
        });
      }
    } else {
      this.form = this.fb.group({
        machinery: machineryFormGroup
      });
    }

    this.isMachineryValid(false);
  }

  /**
  * Method for changing capacity
  *
  * @memberof LoadingDischargingCargoMachineryComponent
  */
  onChange(event, column) {
    column.capacity = event.target.value;
    this.machineryInUses.loadingDischargingMachinesInUses = this.machineryInUses.loadingDischargingMachinesInUses.map((machine) => {
      if (machine.machineId === column.id) {
        machine.capacity = column.capacity;
      }
      return machine;
    })
    this.updatemachineryInUses.emit(this.machineryInUses.loadingDischargingMachinesInUses);
    this.isMachineryValid(false);
  }

  /**
  * Method for when using a machinary
  *
  * @memberof LoadingDischargingCargoMachineryComponent
  */
  onUse(event, column, key: string, typeIndex: number, index: number) {
    column.isUsing = event.target.checked;
    const formControl = this.field(this.getFormGroupName(key, typeIndex, index), 'capacity', key === 'cargoMachineryValues' ? 'cargoMachinery' : 'pumps');

    if (column?.isUsing) {
      const info = this.operation === OPERATIONS.LOADING ? { loadingInfoId: this.loadingInfoId } : { dischargeInfoId: this.dichargingInfoId };
      const machineInUse: ILoadingMachinesInUse | IDischargingMachinesInUse = {
        id: 0,
        machineId: column.id,
        capacity: column.capacity,
        isUsing: column.isUsing,
        machineTypeId: column?.machineType ?? column?.machineTypeId,
        pumpTypeId: column?.pumpTypeId !== undefined ? column?.pumpTypeId : '',
        ...info
      }
      this.machineryInUses.loadingDischargingMachinesInUses.push(machineInUse);
      formControl?.enable();
    } else {
      const machineTypeId = column?.machineType ?? column?.machineTypeId;
      this.machineryInUses?.loadingDischargingMachinesInUses?.map(machineUse => {
        if (machineUse.machineId === column.id && machineUse.machineTypeId === machineTypeId) {
          machineUse.isUsing = false;
        }
      });
      formControl?.disable();
    }
    this.updatemachineryInUses.emit(this.machineryInUses.loadingDischargingMachinesInUses);
    this.isMachineryValid(false);
  }

  /**
  * Method for machinary is valid
  *
  * @memberof LoadingDischargingCargoMachineryComponent
  */
  isMachineryValid(showToaster: boolean) {
    let bottomLine;
    let manifold;
    let vesselPump;

    const hasCargoPump = this.machineryInUses?.vesselPumps?.some(pump => Number(pump.pumpTypeId) === PUMP_TYPES.CARGO_PUMP);
    const hasBallastPump = this.machineryInUses?.vesselPumps?.some(pump => Number(pump.pumpTypeId) === PUMP_TYPES.BALLAST_PUMP);
    const hasStrippingPump = this.machineryInUses?.vesselPumps?.some(pump => Number(pump.pumpTypeId) === PUMP_TYPES.STRIPPING_PUMP);
    const hasGsPump = this.machineryInUses?.vesselPumps?.some(pump => Number(pump.pumpTypeId) === PUMP_TYPES.GS_PUMP);

    let { cargoPump, ballastPump, strippingPump, gsPump } = { cargoPump: !hasCargoPump, ballastPump: !hasBallastPump, strippingPump: !hasStrippingPump, gsPump: !hasGsPump };

    for (let index = 0; index < this.machineryInUses?.loadingDischargingMachinesInUses?.length; index++) {
      const machineUse = this.machineryInUses?.loadingDischargingMachinesInUses[index];
      if (machineUse.isUsing) {
        switch (machineUse.machineTypeId) {
          case MACHINE_TYPES.BOTTOM_LINE: {
            bottomLine = true;
            break;
          }
          case MACHINE_TYPES.MANIFOLD: {
            manifold = true;
            break;
          }
          case MACHINE_TYPES.VESSEL_PUMP: {
            if ([PUMP_TYPES.CARGO_PUMP, PUMP_TYPES.BALLAST_PUMP, PUMP_TYPES.STRIPPING_PUMP, PUMP_TYPES.GS_PUMP].includes(Number(machineUse.pumpTypeId))) {
              vesselPump = true;
            }
            if (this.operation === OPERATIONS.DISCHARGING) {
              switch (Number(machineUse.pumpTypeId)) {
                case PUMP_TYPES.CARGO_PUMP:
                  cargoPump = true;
                  break;

                case PUMP_TYPES.BALLAST_PUMP:
                  ballastPump = true;
                  break;

                case PUMP_TYPES.STRIPPING_PUMP:
                  strippingPump = true;
                  break;

                case PUMP_TYPES.GS_PUMP:
                  gsPump = true;
                  break;

                default:
                  break;
              }
            }
            break;
          }
        }
      }
    };

    if (this.operation === OPERATIONS.DISCHARGING && vesselPump && manifold && bottomLine && cargoPump && ballastPump && strippingPump && gsPump && this.machineryForm.valid) {
      this.loadingDischargingTransformationService.isMachineryValid = true;
      return true;
    } else if (this.operation === OPERATIONS.LOADING && vesselPump && manifold && bottomLine) {
      this.loadingDischargingTransformationService.isMachineryValid = true;
      return true;
    } else {
      this.loadingDischargingTransformationService.isMachineryValid = false;
      const translationKeys = this.translateService.instant(['LOADING_INFORMATION_SAVE_ERROR', 'LOADING_INFORMATION_CARGO_MACHINERY_MANIFOLD', 'LOADING_INFORMATION_CARGO_MACHINERY_BOTTOM_LINE', 'LOADING_INFORMATION_CARGO_MACHINERY_PUMP', 'LOADING_INFORMATION_CARGO_MACHINERY_BALLAST_PUMP', 'LOADING_INFORMATION_CARGO_MACHINERY_CARGO_PUMP', 'LOADING_INFORMATION_CARGO_MACHINERY_STRIPPING_PUMP', 'LOADING_INFORMATION_CARGO_MACHINERY_GS_PUMP', 'LOADING_INFORMATION_CARGO_MACHINERY_PUMP']);
      let detail;
      if (!manifold) {
        detail = translationKeys['LOADING_INFORMATION_CARGO_MACHINERY_MANIFOLD'];
      } else if (!bottomLine) {
        detail = translationKeys['LOADING_INFORMATION_CARGO_MACHINERY_BOTTOM_LINE'];
      } else if (!vesselPump) {
        detail = translationKeys['LOADING_INFORMATION_CARGO_MACHINERY_BALLAST_PUMP'];
      } else if (this.operation === OPERATIONS.DISCHARGING && !cargoPump) {
        detail = translationKeys['LOADING_INFORMATION_CARGO_MACHINERY_CARGO_PUMP'];
      } else if (this.operation === OPERATIONS.DISCHARGING && !ballastPump) {
        detail = translationKeys['LOADING_INFORMATION_CARGO_MACHINERY_BALLAST_PUMP'];
      } else if (this.operation === OPERATIONS.DISCHARGING && !strippingPump) {
        detail = translationKeys['LOADING_INFORMATION_CARGO_MACHINERY_STRIPPING_PUMP'];
      } else if (this.operation === OPERATIONS.DISCHARGING && !gsPump) {
        detail = translationKeys['LOADING_INFORMATION_CARGO_MACHINERY_GS_PUMP'];
      } else if (this.operation === OPERATIONS.DISCHARGING && this.machineryForm.invalid) {
        detail = translationKeys['LOADING_INFORMATION_CARGO_MACHINERY_PUMP'];
      }
      if (showToaster) {
        this.messageService.add({
          severity: 'error', summary: translationKeys['LOADING_INFORMATION_SAVE_ERROR'],
          detail: detail
        });
      }
      return false;
    };
  }

  /**
  * Method for changing type
  *
  * @memberof LoadingDischargingCargoMachineryComponent
  */
  onTypeChange(event) {
    this.selectedType = event?.value;
    this.updateMachinery();

    }

  /**
   * Method to check for field errors
   *
   * @param {string} formControlName
   * @param {number} indexOfFormgroup
   * @return {ValidationErrors}
   * @memberof LoadingDischargingCargoMachineryComponent
   */
  fieldError(formGroupIndex: number, formControlName: string, formArrayName: string): ValidationErrors {
    const formControl = this.field(formGroupIndex, formControlName, formArrayName);
    return formControl?.invalid ? formControl?.errors : null;
  }

  /**
   * Method to get formControl
   * @param {string} formControlName
   * @return {FormControl}
   * @memberof LoadingDischargingCargoMachineryComponent
  */
  field(formGroupIndex: number, formControlName: string, formArrayName: string): FormControl {
    const formControl = <FormControl>(<FormArray>this.machineryForm.get(formArrayName)).at(formGroupIndex).get(formControlName);
    return formControl;
  }

  /**
   * Method to get formgroup index
   * @param {string} formControlName
   * @return {FormControl}
   * @memberof LoadingDischargingCargoMachineryComponent
  */
  getFormGroupName(key: string, typeIndex: number, index: number) {
    const totalRowsAbove = this.machineries[key].reduce((sum, item, i) => sum += i < typeIndex ? item?.columns?.length : 0, 0);
    return index + totalRowsAbove;
  }

}

import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { DATATABLE_EDITMODE, IDataTableColumn } from '../../../shared/components/datatable/datatable.model';
import { ICargo, OPERATIONS } from '../../core/models/common.model';
import { ILoadingDischargingDelays, ILoadingSequenceDropdownData, ILoadingDischargingSequences, ILoadingDischargingSequenceValueObject, ILoadedCargo } from '../models/loading-discharging.model';
import { durationValidator } from '../directives/validator/duration-validator.directive';
import { cargoQuantityValidator } from '../directives/validator/cargo-quantity-validator.directive';
import { sequenceNumberValidator } from '../../core/directives/sequence-number-validator.directive';
import { ConfirmationService } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';
import { loadingCargoDuplicateValidator } from '../directives/validator/loading-cargo-duplicate-validator.directive';
import { LoadingDischargingTransformationService } from '../services/loading-discharging-transformation.service';
import { QUANTITY_UNIT } from '../../../shared/models/common.model';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';
import { QuantityDecimalFormatPipe } from '../../../shared/pipes/quantity-decimal-format/quantity-decimal-format.pipe';
import { numberValidator } from '../../core/directives/number-validator.directive';
import { QuantityPipe } from '../../../shared/pipes/quantity/quantity.pipe';
import { QuantityDecimalService } from '../../../shared/services/quantity-decimal/quantity-decimal.service';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

/**
 * Component class for loading discharging manage sequence component
 *
 * @export
 * @class LoadingDischargingManageSequenceComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-loading-discharging-manage-sequence',
  templateUrl: './loading-discharging-manage-sequence.component.html',
  styleUrls: ['./loading-discharging-manage-sequence.component.scss']
})
export class LoadingDischargingManageSequenceComponent implements OnInit, OnDestroy {
  @Input() cargos: ICargo[];
  @Input() loadableQuantityCargo: ILoadedCargo[];
  @Input() operation: OPERATIONS;
  @Input() loadingInfoId: number;
  @Input() dischargeInfoId: number;
  @Input() get currentQuantitySelectedUnit(): QUANTITY_UNIT {
    return this._currentQuantitySelectedUnit;
  }
  set currentQuantitySelectedUnit(value: QUANTITY_UNIT) {
    this._currentQuantitySelectedUnit = value;
    if (this.loadingDischargingSequences) {
      this.unitConversion();
    }
  }
  @Input() get loadingDischargingSequences(): ILoadingDischargingSequences {
    return this._loadingDischargingSequences;
  }
  set loadingDischargingSequences(loadingDischargingSequences: ILoadingDischargingSequences) {
    this._loadingDischargingSequences = loadingDischargingSequences;
    if (this.loadingDischargingSequences) {
      this.initiLoadingDischargingSequenceArray();
    }
  }
  @Input() get editable(): boolean {
    return this._editable;
  }
  set editable(editable: boolean) {
    this._editable = editable;
  }
  @Input() form: FormGroup;
  get loadingDischargingSequenceForm() {
    return <FormGroup>this.form?.get('loadingDischargingSequence');
  }
  set loadingDischargingSequenceForm(form: FormGroup) {
    this.form?.setControl('loadingDischargingSequence', form);
  }


  @Output() updateLoadingDischargingDelays: EventEmitter<ILoadingDischargingDelays[]> = new EventEmitter();

  columns: IDataTableColumn[];
  listData = <ILoadingSequenceDropdownData>{};
  loadingDischargingDelays: ILoadingDischargingSequenceValueObject[] = [];
  editMode: DATATABLE_EDITMODE = DATATABLE_EDITMODE.CELL;
  loadingDischargingDelayList: ILoadingDischargingDelays[];
  addInitialDelay = false;
  loadableQuantityCargoCount: number;
  totalDuration: number;
  totalMinutes: number;
  prevQuantitySelectedUnit: QUANTITY_UNIT = AppConfigurationService.settings.baseUnit;

  private _loadingDischargingSequences: ILoadingDischargingSequences;
  private _editable = true;
  private _currentQuantitySelectedUnit: QUANTITY_UNIT;
  private ngUnsubscribe: Subject<any> = new Subject();

  constructor(
    private confirmationService: ConfirmationService,
    private translateService: TranslateService,
    private fb: FormBuilder,
    private quantityPipe: QuantityPipe,
    private messageService: MessageService,
    private quantityDecimalFormatPipe: QuantityDecimalFormatPipe,
    private loadingDischargingTransformationService: LoadingDischargingTransformationService,
    private quantityDecimalService: QuantityDecimalService) { }

  async ngOnInit(): Promise<void> {
    await this.initSubsciptions();
    await this.initiLoadingDischargingSequenceArray();
  }

  ngOnDestroy() {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  /**
   * Method to initialise all subscriptions in the component
   *
   * @memberof LoadingDischargingManageSequenceComponent
   */
  private initSubsciptions(): void {
    this.loadingDischargingTransformationService.loadingDischargingRateValidity$.pipe(takeUntil(this.ngUnsubscribe)).subscribe(rateChanged => {
      this.resetLoadingDischargingRateValidation(rateChanged);
    });
  }

  /**
  * Method for init loading sequence array
  *
  * @memberof LoadingDischargingManageSequenceComponent
  */
  async initiLoadingDischargingSequenceArray() {
    this.totalDuration = 24;
    if (this.operation === OPERATIONS.DISCHARGING) {
      this.totalDuration = this.loadableQuantityCargo?.reduce((total, cargo) => total += Number(cargo?.timeRequiredForDischarging), 0);
    }

    this.columns = this.loadingDischargingTransformationService.getLoadingDischargingDelayDatatableColumns(this.operation, this.totalDuration, this.translateService);
    if (!this.editable) {
      this.columns.splice((this.columns.length - 1), 1);
    }
    this.listData = await this.getDropdownData();
    this.addInitialDelay = false;
    this.listData.reasonForDelays = this.loadingDischargingSequences.reasonForDelays;
    const initialDelay = this.loadingDischargingSequences.loadingDischargingDelays?.find(loadingDischargingDelay => !loadingDischargingDelay.cargoId && !loadingDischargingDelay.quantity);

    if (initialDelay) {
      initialDelay.isInitialDelay = true;
      this.loadingDischargingSequences.loadingDischargingDelays = this.loadingDischargingSequences.loadingDischargingDelays?.filter(loadingDischargingDelay => loadingDischargingDelay?.cargoId && loadingDischargingDelay?.quantity);
      this.loadingDischargingSequences.loadingDischargingDelays.unshift(initialDelay)
    }
    const _loadingDischargingDelays = this.loadingDischargingSequences.loadingDischargingDelays?.map((loadingDischargingDelay) => {
      const loadingSequenceData = this.loadingDischargingTransformationService.getLoadingDischargingDelayAsValueObject(loadingDischargingDelay, false, this.editable, this.listData, this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit, this.operation);

      if (!loadingDischargingDelay.cargoId && !loadingDischargingDelay.quantity) {
        this.addInitialDelay = true;
      }
      return loadingSequenceData;
    });
    const loadingDischargingDelayArray = _loadingDischargingDelays?.map((loadingDischargingDelay, index) => {
      if (loadingDischargingDelay?.cargo?.value?.cargoId && loadingDischargingDelay?.quantity) {
        loadingDischargingDelay.quantity = loadingDischargingDelay?.quantity;
        return this.initLoadingDischargingSequenceFormGroup(loadingDischargingDelay, index, false)
      } else {
        return this.initLoadingDischargingSequenceFormGroup(loadingDischargingDelay, index, true)
      }
    }
    );
    this.loadingDischargingDelays = _loadingDischargingDelays;

    if (this.operation === OPERATIONS.DISCHARGING) {
      if (this.form) {
        this.loadingDischargingSequenceForm = this.fb.group({
          dataTable: this.fb.array([...loadingDischargingDelayArray])
        });
      } else {
        this.form = this.fb.group({
          loadingDischargingSequence: this.fb.group({
            dataTable: this.fb.array([...loadingDischargingDelayArray])
          })
        });
      }
    } else {
      this.form = this.fb.group({
        loadingDischargingSequence: this.fb.group({
          dataTable: this.fb.array([...loadingDischargingDelayArray])
        })
      });
    }
    this.loadingDischargingSequenceForm.markAllAsTouched();
    this.checkCargoCount(false);
  }

  /**
  * Method call when manage sequence is saved
  *
  * @memberof LoadingDischargingManageSequenceComponent
  */
  manageSequenceSaved() {
    this.loadingDischargingDelays.map((loadingDischarging) => {
      loadingDischarging.isAdd = false;
    })
  }

  /**
  * Method for Unit conversion
  *
  * @memberof LoadingDischargingManageSequenceComponent
  */
  unitConversion() {
    this.loadingDischargingDelays?.map(item => {
      if (item?.cargo?.value) {
        this.listData.loadableQuantityCargo.map(el => {
          if (el.cargoNominationId === item?.cargo?.value?.cargoNominationId) {
            const loadableMT = this.loadingDischargingTransformationService.manageSequenceUnitConversion(Number(el.loadableMT), item, this.listData, this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit);
            item.quantity.value = Number(loadableMT);
          }
        });
      }
    });
    const loadingDischargingDelayArray = this.loadingDischargingDelays?.map((loadingDischargingDelay, index) => {
      if (loadingDischargingDelay?.cargo?.value?.cargoId && loadingDischargingDelay?.quantity) {
        loadingDischargingDelay.quantity = loadingDischargingDelay?.quantity;
        return this.initLoadingDischargingSequenceFormGroup(loadingDischargingDelay, index, false)
      } else {
        return this.initLoadingDischargingSequenceFormGroup(loadingDischargingDelay, index, true)
      }
    }
    );
    this.loadingDischargingSequenceForm = this.fb.group({
      dataTable: this.fb.array([...loadingDischargingDelayArray])
    });
  }

  /**
  * Get all lookups for loading sequence screen
  *
  * @returns {Promise<ILoadingSequenceDropdownData>}
  * @memberof LoadingDischargingManageSequenceComponent
  */
  async getDropdownData(): Promise<ILoadingSequenceDropdownData> {
    const cargoTobeLoaded = this.loadableQuantityCargo?.map(loadable => {
      if (loadable) {
        loadable.grade = this.findCargo(loadable);
      }
      return loadable;
    })
    this.listData = <ILoadingSequenceDropdownData>{};
    this.listData.loadableQuantityCargo = cargoTobeLoaded;

    return this.listData;
  }

  /**
   * Method to find out cargo
   *
   * @memberof LoadingDischargingManageSequenceComponent
   */
  findCargo(loadableQuantityCargoDetails): string {
    let cargoDetail;
    this.cargos?.map((cargo) => {
      if (cargo.id === loadableQuantityCargoDetails?.cargoId) {
        cargoDetail = cargo;
      }
    });
    return cargoDetail?.name;
  }

  /**
  * Method for initializing loading sequence row
  *
  * @private
  * @param {ILoadingDischargingSequenceValueObject} loadingDischargingDelay
  * @returns
  * @memberof LoadingDischargingManageSequenceComponent
  */
  initLoadingDischargingSequenceFormGroup(loadingDischargingDelay: ILoadingDischargingSequenceValueObject, index: number, initialDelay: boolean) {
    const [totalHours, totalMinutes] = this.totalDuration.toString()?.split('.').map((num, i) => i === 1 ? Number(num ?? 0) * 6 : Number(num));
    const formGroup = this.fb.group({
      id: loadingDischargingDelay.id,
      sequenceNo: this.fb.control(loadingDischargingDelay?.sequenceNo?.value, [Validators.required, Validators.min(1), numberValidator(0, 4, false), sequenceNumberValidator]),
      reasonForDelay: this.fb.control(loadingDischargingDelay.reasonForDelay.value, initialDelay ? [Validators.required] : []),
      duration: this.fb.control(loadingDischargingDelay.duration.value, [Validators.required, durationValidator(totalHours, totalMinutes)]),
      cargo: this.fb.control(loadingDischargingDelay.cargo.value, initialDelay ? [] : this.operation === OPERATIONS.DISCHARGING ? [Validators.required] : [Validators.required, loadingCargoDuplicateValidator()]),
      quantity: this.fb.control(loadingDischargingDelay.quantity?.value, initialDelay ? [] : this.getQuantityValidators()),
      colorCode: this.fb.control(loadingDischargingDelay.colorCode)
    });

    if (this.operation === OPERATIONS.DISCHARGING) {
      formGroup.addControl('dischargingRate', this.fb.control(loadingDischargingDelay?.dischargingRate?.value, initialDelay ? [] : [numberValidator(0, 6), Validators.required, Validators.min(loadingDischargingDelay?.rateMin), Validators.max(loadingDischargingDelay?.rateMax)]));
    } else if (this.operation === OPERATIONS.LOADING) {
      formGroup.addControl('loadingRate', this.fb.control(loadingDischargingDelay?.loadingRate?.value, initialDelay ? [] : [numberValidator(0, 6), Validators.required, Validators.min(loadingDischargingDelay?.rateMin), Validators.max(loadingDischargingDelay?.rateMax)]));
    }

    return formGroup;
  }

  /**
   * Get validators for quantity field
   *
   * @return {*}  {ValidatorFn[]}
   * @memberof LoadingDischargingManageSequenceComponent
   */
  getQuantityValidators(): ValidatorFn[] {
    const quantityDecimal = this.quantityDecimalService.quantityDecimal();
    const min = quantityDecimal ? (1 / Math.pow(10, quantityDecimal)) : 1;
    return this.operation === OPERATIONS.DISCHARGING ? [Validators.required, Validators.min(min), cargoQuantityValidator(), numberValidator(quantityDecimal, 7, false)] : [Validators.required]
  }


  /**
   * Method for adding new port
   *
   * @private
   * @memberof LoadingDischargingManageSequenceComponent
   */
  addLoadingDischargingSequence(loadingDischargingDelay: ILoadingDischargingDelays = null) {
    loadingDischargingDelay = loadingDischargingDelay ?? <ILoadingDischargingDelays>{ id: 0, loadingInfoId: null, dischargeInfoId: null, reasonForDelayIds: null, duration: null, cargoId: null, quantity: null };
    const _loadingDischargingDelays = this.loadingDischargingTransformationService.getLoadingDischargingDelayAsValueObject(loadingDischargingDelay, true, true, this.listData, this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit, this.operation);
    const dataTableControl = <FormArray>this.loadingDischargingSequenceForm.get('dataTable');
    const loadableQuantityCargoCount = this.listData.loadableQuantityCargo.length;
    this.loadableQuantityCargoCount = this.addInitialDelay ? loadableQuantityCargoCount + 1 : loadableQuantityCargoCount
    if (dataTableControl.controls.length !== this.loadableQuantityCargoCount || this.operation === OPERATIONS.DISCHARGING) {
      dataTableControl.push(this.initLoadingDischargingSequenceFormGroup(_loadingDischargingDelays, this.loadingDischargingDelays.length, false));
      this.loadingDischargingDelays = [...this.loadingDischargingDelays, _loadingDischargingDelays];
    }
  }

  /**
   * Event handler for edit complete event
   *
   * @memberof LoadingDischargingManageSequenceComponent
   */
  async onEditComplete(event) {
    const index = event.index;
    const form = this.row(index);

    const quantity = this.quantityPipe.transform(event.data.cargo.value?.loadableMT ?? 0, this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit, event.data.cargo.value?.estimatedAPI, event.data.cargo.value?.estimatedTemp);
    if (event.field === 'cargo') {
      this.loadingDischargingDelays[index].quantityMT = event.data.cargo.value.loadableMT;
      this.loadingDischargingDelays[index]['quantity'].value = Number(quantity);
      this.loadingDischargingDelays[index]['colorCode'] = event.data.cargo.value.colorCode;
      this.updateField(index, 'quantity', this.loadingDischargingDelays[index]['quantity'].value);
      this.updateField(index, 'colorCode', event.data.cargo.value.colorCode);
    } else if (event.field === 'quantity') {
      this.loadingDischargingDelays[index][event.field].value = event.data[event.field].value;
      this.loadingDischargingDelays[index].quantityMT = this.quantityPipe.transform(event.data[event.field].value, this.currentQuantitySelectedUnit, QUANTITY_UNIT.MT, event.data.cargo.value?.estimatedAPI, event.data.cargo.value?.estimatedTemp, -1);
      this.setCargoQuantity(index, quantity);
    } else {
      this.loadingDischargingDelays[index][event.field].value = event.data[event.field].value;
    }
    this.updateFormValidity();
    if (form.valid) {
      const loadingDischargingDelaysList = this.loadingDischargingTransformationService.getLoadingDischargingDelayAsValue(this.loadingDischargingDelays, this.operation === OPERATIONS.LOADING ? this.loadingInfoId : this.dischargeInfoId, this.operation, this.listData);
      this.updateLoadingDischargingDelays.emit(loadingDischargingDelaysList);
    }
    this.checkCargoCount(false);
  }

  /**
   * Set cargo quantity and validators
   *
   * @param {number} index
   * @param {number} quantity
   * @memberof LoadingDischargingManageSequenceComponent
   */
  setCargoQuantity(index: number, quantity: number) {
    const formControl = this.field(index, 'quantity');
    formControl.setValidators([...this.getQuantityValidators(), Validators.max(Number(quantity))]);
    formControl.updateValueAndValidity();
  }

  /**
 * Update validity of invalid rows if valid
 *
 * @memberof LoadingDischargingManageSequenceComponent
 */
  updateFormValidity() {
    const formArray = (<FormArray>this.loadingDischargingSequenceForm.get('dataTable')).controls;
    formArray.forEach(async (row: FormGroup, index) => {
      const invalidFormControls = this.findInvalidControlsRecursive(row);
      invalidFormControls.forEach((key) => {
        const formControl = this.field(index, key);
        formControl.updateValueAndValidity();
        formControl.markAllAsTouched();
        if (formControl.invalid && this.loadingDischargingDelays[index][key]['isEditable']) {
          this.loadingDischargingDelays[index][key]['isEditMode'] = true;
        } else {
          this.loadingDischargingDelays[index][key]['isEditMode'] = false;
        }
      });
      if (row.valid) {
        Object.keys(row?.controls).forEach(key => {
          if (this.loadingDischargingDelays[index][key]['isEditable']) {
            this.loadingDischargingDelays[index][key]['isEditMode'] = false;
          }
        });
      }
    })
  }


  /**
 * Method get all invalid fields in a row
 *
 * @private
 * @param {FormGroup} formToInvestigate
 * @returns {string[]}
 * @memberof LoadingDischargingManageSequenceComponent
 */
  private findInvalidControlsRecursive(formToInvestigate: FormGroup): string[] {
    const invalidControls: string[] = [];
    const recursiveFunc = (form: FormGroup | FormArray) => {
      Object.keys(form.controls).forEach(field => {
        const control = form.get(field);
        control.updateValueAndValidity();
        if (control.invalid) invalidControls.push(field);
        if (control instanceof FormGroup) {
          recursiveFunc(control);
        } else if (control instanceof FormArray) {
          recursiveFunc(control);
        }
      });
    }
    recursiveFunc(formToInvestigate);
    return invalidControls;
  }


  /**
  * Method for fetching form group
  *
  * @private
  * @param {number} formGroupIndex
  * @returns {FormGroup}
  * @memberof LoadingDischargingManageSequenceComponent
  */
  private row(formGroupIndex: number): FormGroup {
    const formGroup = <FormGroup>(<FormArray>this.loadingDischargingSequenceForm.get('dataTable')).at(formGroupIndex);
    return formGroup;
  }

  /**
  * Method for add initial delay
  *
  * @private
  * @memberof LoadingDischargingManageSequenceComponent
  */
  async onAddInitialDelay(loadingDischargingDelay: ILoadingDischargingDelays = null) {
    const dataTableControl = <FormArray>this.loadingDischargingSequenceForm.get('dataTable');
    if (this.addInitialDelay) {
      loadingDischargingDelay = loadingDischargingDelay ?? <ILoadingDischargingDelays>{ id: 0, loadingInfoId: null, dischargeInfoId: null, reasonForDelayIds: null, duration: null, cargoId: null, quantity: null };
      loadingDischargingDelay.isInitialDelay = true;
      const _loadingDischargingDelays = this.loadingDischargingTransformationService.getLoadingDischargingDelayAsValueObject(loadingDischargingDelay, true, !this.addInitialDelay, this.listData, this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit, this.operation);
      dataTableControl.insert(0, this.initLoadingDischargingSequenceFormGroup(_loadingDischargingDelays, this.loadingDischargingDelays.length, true));
      this.loadingDischargingDelays = [_loadingDischargingDelays, ...this.loadingDischargingDelays];
    } else {
      this.loadingDischargingDelays.splice(0, 1);
      dataTableControl.removeAt(0);
      dataTableControl.at(0).get('sequenceNo').setValue(1);
      this.loadingDischargingDelays[0].sequenceNo.value = 1;
    }
    const loadingDelaysList = this.loadingDischargingTransformationService.getLoadingDischargingDelayAsValue(this.loadingDischargingDelays, this.operation === OPERATIONS.LOADING ? this.loadingInfoId : this.dischargeInfoId, this.operation, this.listData);
    this.updateLoadingDischargingDelays.emit(loadingDelaysList);
    this.checkCargoCount(false);
  }

  /**
  * Method for delete row
  *
  * @private
  * @param {event} event
  * @memberof LoadingDischargingManageSequenceComponent
  */
  async onDelete(event) {
    const translationKeys = await this.translateService.get(['LOADING_MANAGE_SEQUENCE_DELETE_SUMMARY', 'LOADING_MANAGE_SEQUENCE_DELETE_CONFIRM_DETAILS', 'LOADING_MANAGE_SEQUENCE_DELETE_CONFIRM_LABEL', 'LOADING_MANAGE_SEQUENCE_DELETE_REJECT_LABEL']).toPromise();
    if (!event?.data?.isAdd) {
      this.confirmationService.confirm({
        key: 'confirmation-alert',
        header: translationKeys['LOADING_MANAGE_SEQUENCE_DELETE_SUMMARY'],
        message: translationKeys['LOADING_MANAGE_SEQUENCE_DELETE_CONFIRM_DETAILS'],
        icon: 'pi pi-exclamation-triangle',
        acceptLabel: translationKeys['LOADING_MANAGE_SEQUENCE_DELETE_CONFIRM_LABEL'],
        acceptIcon: 'pi',
        acceptButtonStyleClass: 'btn btn-main mr-5',
        rejectVisible: true,
        rejectLabel: translationKeys['LOADING_MANAGE_SEQUENCE_DELETE_REJECT_LABEL'],
        rejectIcon: 'pi',
        rejectButtonStyleClass: 'btn btn-main',
        accept: async () => {
          await this.removeFromLoadingDelay(event);
        },
      });
    }
    else {
      await this.removeFromLoadingDelay(event);
    }
  }

  /**
   * Method to remove loading delay from list
   *
   * @param {*} event
   * @memberof LoadingDischargingManageSequenceComponent
   */
  async removeFromLoadingDelay(event) {
    this.loadingDischargingDelays.splice(event.index, 1);
    this.loadingDischargingDelays = [...this.loadingDischargingDelays];
    const dataTableControl = <FormArray>this.loadingDischargingSequenceForm.get('dataTable');
    dataTableControl.removeAt(event?.index);
    this.loadingDischargingSequenceForm.updateValueAndValidity();
    if (event.index === 0) {
      this.addInitialDelay = false;
      if (this.loadingDischargingDelays?.length) {
        dataTableControl?.at(0)?.get('sequenceNo')?.setValue(1);
        this.loadingDischargingDelays[0].sequenceNo.value = 1;
      }
    }
    const loadingDelaysList = this.loadingDischargingTransformationService.getLoadingDischargingDelayAsValue(this.loadingDischargingDelays, this.operation === OPERATIONS.LOADING ? this.loadingInfoId : this.dischargeInfoId, this.operation, this.listData);
    this.updateLoadingDischargingDelays.emit(loadingDelaysList);
    this.updateFormValidity();
    this.checkCargoCount(false);
  }

  /**
  * Method for updating form field
  *
  * @private
  * @param {number} index
  * @param {string} field
  * @param {*} value
  * @memberof LoadingDischargingManageSequenceComponent
  */
  private updateField(index: number, field: string, value?: any) {
    const control = this.field(index, field);
    control.setValue(value);
    control.markAsDirty();
    control.markAsTouched();
  }

  /**
   * Method for fetching form fields
   *
   * @private
   * @param {number} formGroupIndex
   * @param {string} formControlName
   * @returns {FormControl}
   * @memberof LoadingDischargingManageSequenceComponent
   */
  private field(formGroupIndex: number, formControlName: string): FormControl {
    const formControl = <FormControl>(<FormArray>this.loadingDischargingSequenceForm.get('dataTable')).at(formGroupIndex).get(formControlName);
    return formControl;
  }

  /**
  * Method for update cargo
  *
  * @memberof LoadingDischargingManageSequenceComponent
  */
  checkCargoCount(showToaster: boolean) {
    const translationKeys = this.translateService.instant(['LOADING_MANAGE_SEQUENCE_PLANNED_CARGO_ERROR', 'LOADING_MANAGE_SEQUENCE_PLANNED_CARGO_SUMMERY', 'LOADING_MANAGE_SEQUENCE_PLANNED_CARGO_QUANTITY_SUMMERY', 'LOADING_MANAGE_SEQUENCE_PLANNED_CARGO_EXCEEDS_SUMMERY']);
    let cargoCount = this.listData.loadableQuantityCargo.length;
    cargoCount = this.addInitialDelay ? cargoCount + 1 : cargoCount;
    const dataTableControl = <FormArray>this.loadingDischargingSequenceForm.get('dataTable');
    if (this.loadingDischargingSequenceForm.valid && dataTableControl.length < cargoCount) {
      this.loadingDischargingTransformationService.isCargoAdded = false;
      if (showToaster) {
        this.messageService.add({ severity: 'error', summary: translationKeys['LOADING_MANAGE_SEQUENCE_PLANNED_CARGO_ERROR'], detail: translationKeys['LOADING_MANAGE_SEQUENCE_PLANNED_CARGO_SUMMERY'] });
      }
      return false;
    } else if (this.operation === OPERATIONS.DISCHARGING) {
      const totalQuantitySequence = this.loadingDischargingDelays?.reduce((total, sequence) => total + Number(sequence.quantityMT), 0);
      const totalLoadedQuantity = this.listData.loadableQuantityCargo?.reduce((total, cargo) => total + Number(cargo.loadableMT), 0);
      if (Math.round(totalLoadedQuantity) > Math.round(totalQuantitySequence) && showToaster) {
        this.messageService.add({ severity: 'error', summary: translationKeys['LOADING_MANAGE_SEQUENCE_PLANNED_CARGO_ERROR'], detail: translationKeys['LOADING_MANAGE_SEQUENCE_PLANNED_CARGO_QUANTITY_SUMMERY'] });
      } else if (Math.round(totalLoadedQuantity) < Math.round(totalQuantitySequence) && showToaster) {
        this.messageService.add({ severity: 'error', summary: translationKeys['LOADING_MANAGE_SEQUENCE_PLANNED_CARGO_ERROR'], detail: translationKeys['LOADING_MANAGE_SEQUENCE_PLANNED_CARGO_EXCEEDS_SUMMERY'] });
      } else {
        return true;
      }
    } else {
      this.loadingDischargingTransformationService.isCargoAdded = true;
      return true;
    }
  }

  /**
   * Method for dynamically change min & max validation of Loading / Discharging rate
   *
   * @param {any} rateChanged
   * @memberof LoadingDischargingManageSequenceComponent
   */
  resetLoadingDischargingRateValidation(rateChanged: any): void {
    for (const key in rateChanged) {
      if (Object.prototype.hasOwnProperty.call(rateChanged, key)) {
        rateChanged[key] = Number(Number(rateChanged[key]).toFixed());
      }
    }

    let rateMin, rateMax, field;
    if (this.operation === OPERATIONS.LOADING) {
      field = 'loadingRate';
      rateMin = rateChanged.minLoadingRate;
      rateMax = (rateChanged.maxLoadingRate > rateChanged.shoreLoadingRate) ? rateChanged.shoreLoadingRate : rateChanged.maxLoadingRate;
    } else if (this.operation === OPERATIONS.DISCHARGING) {
      field = 'dischargingRate';
      rateMin = rateChanged.initialDischargingRate;
      rateMax = rateChanged.maxDischargingRate;
    }

    this.loadingDischargingDelays.forEach((delay, index) => {
      const formControl = this.field(index, field);
      formControl.clearValidators();
      formControl.setValidators([numberValidator(0, 6), Validators.required, Validators.min(rateMin), Validators.max(rateMax)]);
      this.updateFormValidity();
    });
  }


}


import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { DATATABLE_EDITMODE, IDataTableColumn, IDataTableFilterEvent, IDataTableSortEvent } from '../../../../shared/components/datatable/datatable.model';
import { ICargoNominationValueObject, ICargoNominationAllDropdownData, ICargoNominationDetailsResponse, ICargoNominationEvent, ICargoNomination, ILoadingPopupData } from '../../models/cargo-planning.model';
import { LoadableStudyDetailsApiService } from '../../services/loadable-study-details-api.service';
import { LoadableStudyDetailsTransformationService } from '../../services/loadable-study-details-transformation.service';
import { cargoNominationColorValidator } from '../../directives/validator/cargo-nomination-color.directive'
import { cargoNominationLoadingPortValidator } from '../../directives/validator/cargo-nomination-loading-port.directive'
import { alphabetsOnlyValidator } from '../../directives/validator/cargo-nomination-alphabets-only.directive'
import { numberValidator } from '../../directives/validator/number-validator.directive'
import { NgxSpinnerService } from 'ngx-spinner';
import { ConfirmationAlertService } from '../../../../shared/components/confirmation-alert/confirmation-alert.service';
import { first } from 'rxjs/operators';

/**
 * Component class of cargonomination screen
 *
 * @export
 * @class CargoNominationComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-cargo-nomination',
  templateUrl: './cargo-nomination.component.html',
  styleUrls: ['./cargo-nomination.component.scss']
})
export class CargoNominationComponent implements OnInit {

  @Input() voyageId: number;

  @Input()
  get loadableStudyId() {
    return this._loadableStudyId;
  }
  set loadableStudyId(value: number) {
    this._loadableStudyId = value;
    this.cargoNominationForm = null;
    this.getCargoNominationDetails();
  }

  @Input() vesselId: number;

  @Output() cargoNominationUpdate = new EventEmitter<boolean>();

  // properties
  get cargoNominations(): ICargoNominationValueObject[] {
    return this._cargoNominations;
  }
  set cargoNominations(cargoNominations: ICargoNominationValueObject[]) {
    let totalQuantity = 0;
    this._cargoNominations = cargoNominations.map((cargoNomination, index) => {
      const _cargoNomination = this.loadableStudyDetailsTransformationService.formatCargoNomination(cargoNomination);
      totalQuantity += _cargoNomination?.isDelete ? 0 : _cargoNomination.quantity.value;
      _cargoNomination.slNo = index + 1;
      _cargoNomination.priority.value = _cargoNomination.priority.value > cargoNominations.length ? this._cargoNominations.length : _cargoNomination.priority.value;
      return _cargoNomination
    });
    this.loadableStudyDetailsTransformationService.setTotalQuantityCargoNomination(totalQuantity);
    this.updatePriorityDropdown(this._cargoNominations.length);
    this.loadableStudyDetailsTransformationService.setCargoNominationValidity(this.cargoNominationForm.valid && this.cargoNominations?.filter(item => !item?.isAdd).length > 0);
  }

  get loadingPopupData(): ILoadingPopupData {
    return this._loadingPopupData;
  }

  set loadingPopupData(loadingPopupData: ILoadingPopupData) {
    if (loadingPopupData.isUpdate) {
      this.updateLoadingPortData(loadingPopupData);
      loadingPopupData.isUpdate = false;
    }
    this._loadingPopupData = loadingPopupData;
  }

  get openLoadingPopup(): boolean {
    return this._openLoadingPopup;
  }
  set openLoadingPopup(openLoadingPopup: boolean) {
    this._openLoadingPopup = openLoadingPopup;
  }

  get openAPITemperatureHistoryPopup(): boolean {
    return this._openAPITemperatureHistoryPopup;
  }
  set openAPITemperatureHistoryPopup(openAPITemperatureHistoryPopup: boolean) {
    this._openAPITemperatureHistoryPopup = openAPITemperatureHistoryPopup;
  }

  // public fields
  readonly editMode = DATATABLE_EDITMODE.CELL;
  columns: IDataTableColumn[];
  listData = <ICargoNominationAllDropdownData>{};
  cargoNominationForm: FormGroup;
  cargoNominationDetails: ICargoNominationDetailsResponse;

  // private fields
  private _loadableStudyId: number;
  private _loadingPopupData: ILoadingPopupData;
  private _cargoNominations: ICargoNominationValueObject[];
  private _openLoadingPopup = false;
  private _openAPITemperatureHistoryPopup = false;


  // public methods
  constructor(private loadableStudyDetailsApiService: LoadableStudyDetailsApiService,
    private loadableStudyDetailsTransformationService: LoadableStudyDetailsTransformationService,
    private fb: FormBuilder,
    private ngxSpinnerService: NgxSpinnerService,
    private confirmationAlertService: ConfirmationAlertService) {
  }

  /**
   * Component lifecycle ngOnit
   *
   * @returns {Promise<void>}
   * @memberof CargoNominationComponent
   */
  ngOnInit() {
    this.columns = this.loadableStudyDetailsTransformationService.getCargoNominationDatatableColumns();
    this.initSubscriptions();
  }

  /**
   * Get all details for cargonomination screen
   *
   * @returns {Promise<ICargoNominationDetailsResponse>}
   * @memberof CargoNominationComponent
   */
  async getCargoNominationDetails() {
    this.ngxSpinnerService.show();
    this.listData = await this.getDropdownData();
    const cargoNominationFormData: ICargoNominationDetailsResponse = await this.loadableStudyDetailsApiService.getCargoNominationDetails(this.vesselId, this.voyageId, this.loadableStudyId).toPromise();
    if (cargoNominationFormData?.responseStatus?.status === '200') {
      cargoNominationFormData.cargoNominations = cargoNominationFormData.cargoNominations ?? [];
      this.listData.cargoList = cargoNominationFormData?.cargos;
      this.listData.segregationList = cargoNominationFormData?.segregations;
      this.cargoNominationDetails = cargoNominationFormData;
      this.initCargoNominationArray(this.cargoNominationDetails?.cargoNominations);
    }
    this.ngxSpinnerService.hide();
  }

  /**
   * Get all lookups for cargo nomination screen
   *
   * @returns {Promise<ICargoNominationAllDropdownData>}
   * @memberof CargoNominationComponent
   */
  async getDropdownData(): Promise<ICargoNominationAllDropdownData> {
    const result = await this.loadableStudyDetailsApiService.getPorts().toPromise();
    this.listData = <ICargoNominationAllDropdownData>{};
    this.listData.ports = result;

    return this.listData;
  }

  /**
   * Event handler for grid cell click
   *
   * @param {ICargoNominationEvent} event
   * @memberof CargoNominationComponent
   */
  async onCellValueClick(event: ICargoNominationEvent) {
    this.ngxSpinnerService.show();
    const valueIndex = this.cargoNominations.findIndex(cargoNomination => cargoNomination?.storeKey === event?.data?.storeKey);
    if (event.field === 'loadingPorts') {
      if (event.data?.cargo?.value) {
        const result = await this.loadableStudyDetailsApiService.getAllCargoPorts(event.data?.cargo?.value?.id).toPromise();
        event.data.cargo.value.ports = result?.ports;
        this.cargoNominations[valueIndex]['cargo'].value = event?.data?.cargo?.value;
        this.updateField(event.index, 'cargo', event?.data?.cargo?.value);
        this.cargoNominations = [...this.cargoNominations];
        this.loadingPopupData = <ILoadingPopupData>{
          originalEvent: event.originalEvent,
          rowData: event.data,
          rowIndex: event.index,
          ports: event.data?.cargo?.value?.ports
        }
        this.openLoadingPopup = true;
      }
    } else if (['api', 'temperature'].includes(event.field)) {
      this.openAPITemperatureHistoryPopup = true;
    }
    this.ngxSpinnerService.hide();
  }

  /**
   * Event handler for edit complete event
   *
   * @param {ICargoNominationEvent} event
   * @memberof CargoNominationComponent
   */
  async onEditComplete(event: ICargoNominationEvent) {
    const valueIndex = this.cargoNominations.findIndex(cargoNomination => cargoNomination?.storeKey === event?.data?.storeKey);
    if (event.field === 'cargo') {
      this.cargoNominations[valueIndex]['abbreviation'].value = event.data.cargo.value.abbreviation;
      this.cargoNominations[valueIndex]['api'].value = event.data.cargo.value.api;
      this.cargoNominations[valueIndex]['loadingPorts'].value = null;
      this.cargoNominations[valueIndex]['loadingPortsLabel'] = null;
      this.cargoNominations[valueIndex]['loadingPortsNameArray'] = null;
      this.cargoNominations[valueIndex]['quantity'].value = null;
      this.updateField(event.index, 'abbreviation', event.data.cargo.value.abbreviation);
      this.updateField(event.index, 'api', event.data.cargo.value.api);
      this.updateField(event.index, 'loadingPorts', null);
      this.updateField(event.index, 'quantity', null);
      const result = await this.loadableStudyDetailsApiService.getAllCargoPorts(event.data?.cargo?.value?.id).toPromise();
      event.data.cargo.value.ports = result?.ports;
      this.cargoNominations[valueIndex]['cargo'].value = event?.data?.cargo?.value;
      this.updateField(event.index, 'cargo', event?.data?.cargo?.value);
    }
    this.loadableStudyDetailsTransformationService.setCargoNominationValidity(this.cargoNominationForm.valid && this.cargoNominations?.filter(item => !item?.isAdd).length > 0);
    if (!event.data?.isAdd) {
      if (this.cargoNominationForm.valid) {
        this.ngxSpinnerService.show();
        const res = await this.loadableStudyDetailsApiService.setCargoNomination(this.loadableStudyDetailsTransformationService.getCargoNominationAsValue(this.cargoNominations[valueIndex]), this.vesselId, this.voyageId, this.loadableStudyId);
        if (res) {
          for (const key in this.cargoNominations[valueIndex]) {
            if (this.cargoNominations[valueIndex].hasOwnProperty(key) && this.cargoNominations[valueIndex][key].hasOwnProperty('_isEditMode')) {
              this.cargoNominations[valueIndex][key].isEditMode = false;
            }
          }
          this.cargoNominations = [...this.cargoNominations];
        }
        this.ngxSpinnerService.hide();
      } else {
        const fromGroup = this.row(event.index);
        const invalidFormControls = this.findInvalidControlsRecursive(fromGroup);
        invalidFormControls.forEach((key) => {
          this.cargoNominations[valueIndex][key].isEditMode = true;
        });
        fromGroup.markAllAsTouched();
        this.cargoNominationForm.updateValueAndValidity();
      }
    }
  }

  /**
   *
   *
   * @param {ICargoNominationEvent} event
   * @memberof CargoNominationComponent
   */
  async onDeleteRow(event: ICargoNominationEvent) {
    if (event?.data?.isDelete) {
      const valueIndex = this.cargoNominations.findIndex(cargoNomination => cargoNomination?.storeKey === event?.data?.storeKey);
      this.confirmationAlertService.add({ key: 'confirmation-alert', sticky: true, severity: 'warn', summary: 'CARGONOMINATION_DELETE_SUMMARY', detail: 'CARGONOMINATION_DELETE_DETAILS', data: { confirmLabel: 'CARGONOMINATION_DELETE_CONFIRM_LABEL', rejectLabel: 'CARGONOMINATION_DELETE_REJECT_LABEL' } });
      this.confirmationAlertService.confirmAlert$.pipe(first()).subscribe(async (response) => {
        if (response) {
          this.ngxSpinnerService.show();
          let res;
          if (!event?.data?.isAdd) {
            res = await this.loadableStudyDetailsApiService.setCargoNomination(this.loadableStudyDetailsTransformationService.getCargoNominationAsValue(this.cargoNominations[valueIndex]), this.vesselId, this.voyageId, this.loadableStudyId);
          } else {
            res = true;
          }
          if (res) {
            this.cargoNominations.splice(event.index, 1);
            this.cargoNominations = [...this.cargoNominations];
            this.updateCommingleButton();
          }
          this.ngxSpinnerService.hide();
        }
      });
    }
  }

  /**
   * Event handler for edit complete event
   *
   * @param {ICargoNominationEvent} event
   * @memberof CargoNominationComponent
   */
  async onRowSave(event: ICargoNominationEvent) {
    const valueIndex = this.cargoNominations.findIndex(cargoNomination => cargoNomination?.storeKey === event?.data?.storeKey);
    this.loadableStudyDetailsTransformationService.setCargoNominationValidity(this.cargoNominationForm.valid && this.cargoNominations?.filter(item => !item?.isAdd).length > 0);
    if (this.row(event.index).valid) {
      this.ngxSpinnerService.show();
      const res = await this.loadableStudyDetailsApiService.setCargoNomination(this.loadableStudyDetailsTransformationService.getCargoNominationAsValue(this.cargoNominations[valueIndex]), this.vesselId, this.voyageId, this.loadableStudyId);
      if (res) {
        this.cargoNominations[valueIndex].isAdd = false;
        for (const key in this.cargoNominations[valueIndex]) {
          if (this.cargoNominations[valueIndex].hasOwnProperty(key) && this.cargoNominations[valueIndex][key].hasOwnProperty('_isEditMode')) {
            this.cargoNominations[valueIndex][key].isEditMode = false;
          }
        }
        this.cargoNominations = [...this.cargoNominations];
        this.updateCommingleButton();
      }
      this.ngxSpinnerService.hide();

    } else {
      this.row(event.index).markAllAsTouched();
      this.cargoNominationForm.updateValueAndValidity();
    }
  }

  /**
   * Handler for duplicate row event
   *
   * @param {ICargoNominationEvent} event
   * @memberof CargoNominationComponent
   */
  onDuplicate(event: ICargoNominationEvent) {
    const rowData = this.loadableStudyDetailsTransformationService.getCargoNominationAsValue(event.data);
    rowData.storeKey = null;
    rowData.id = 0;
    this.addCargoNomination(rowData);
  }

  // private methods

  /**
   * Method for updating priority dropdown
   *
   * @private
   * @param {number} count
   * @memberof CargoNominationComponent
   */
  private updatePriorityDropdown(count: number) {
    this.listData.priorityList = [...Array(count).keys()].map(i => {
      return { label: (i + 1).toString(), value: (i + 1) }
    });
  }

  /**
   * Method for adding new cargo
   *
   * @private
   * @memberof CargoNominationComponent
   */
  private addCargoNomination(cargoNomination: ICargoNomination = null) {
    cargoNomination = cargoNomination ?? <ICargoNomination>{ id: 0, priority: null, color: null, cargoId: null, abbreviation: null, quantity: null, segregationId: 1, loadingPorts: null };
    const _cargoNomination = this.loadableStudyDetailsTransformationService.getCargoNominationAsValueObject(cargoNomination, true, this.listData);
    this.cargoNominations = [...this.cargoNominations, _cargoNomination];
    const dataTableControl = <FormArray>this.cargoNominationForm.get('dataTable');
    dataTableControl.push(this.initCargoNominationFormGroup(_cargoNomination));
  }

  /**
   * Method for updating cargo nomination form
   *
   * @private
   * @param {ICargoNomination[]} cargoNominations
   * @memberof CargoNominationComponent
   */
  private async initCargoNominationArray(cargoNominations: ICargoNomination[]) {
    this.ngxSpinnerService.show();
    const _cargoNominations = cargoNominations?.map((item) => {
      const cargoData = this.loadableStudyDetailsTransformationService.getCargoNominationAsValueObject(item, false, this.listData);
      return cargoData;
    });
    //TODO: need to remove this. Instead of calling api for each cargo for cargo specific ports must be coming from cargo nomination api
    // _cargoNominations = await this.getCargoPortMapping(_cargoNominations);
    const cargoNominationArray = _cargoNominations.map(cargoNomination => this.initCargoNominationFormGroup(cargoNomination));
    this.cargoNominationForm = this.fb.group({
      dataTable: this.fb.array([...cargoNominationArray])
    });
    this.cargoNominations = _cargoNominations;
    this.updateCommingleButton();
    this.loadableStudyDetailsTransformationService.setCargoNominationValidity(this.cargoNominationForm.valid && this.cargoNominations?.filter(item => !item?.isAdd).length > 0);
    this.ngxSpinnerService.hide();
  }


  /**
   * Method to mapp cargo and port
   *
   * @param {ICargoNominationValueObject[]} cargoNominations
   * @returns {Promise<ICargoNominationValueObject[]>}
   * @memberof CargoNominationComponent
   */
  async getCargoPortMapping(cargoNominations: ICargoNominationValueObject[]): Promise<ICargoNominationValueObject[]> {
    const _cargoNominations = await Promise.all(cargoNominations.map(async (item) => {
      const result = await this.loadableStudyDetailsApiService.getAllCargoPorts(item?.cargo?.value?.id).toPromise();
      item.cargo.value.ports = result?.ports;
      return item;
    }));
    return [..._cargoNominations];
  }

  /**
   * Method for initializing cargo nomination row
   *
   * @private
   * @param {ICargoNominationValueObject} cargoNomination
   * @returns
   * @memberof CargoNominationComponent
   */
  private initCargoNominationFormGroup(cargoNomination: ICargoNominationValueObject) {
    return this.fb.group({
      priority: this.fb.control(cargoNomination.priority.value, Validators.required),
      color: this.fb.control(cargoNomination.color.value, [Validators.required, cargoNominationColorValidator]),
      cargo: this.fb.control(cargoNomination.cargo.value, Validators.required),
      abbreviation: this.fb.control(cargoNomination.abbreviation.value, [Validators.required, alphabetsOnlyValidator, Validators.maxLength(4)]),
      loadingPorts: this.fb.control(cargoNomination.loadingPorts.value, [Validators.required, cargoNominationLoadingPortValidator]),
      quantity: this.fb.control({ value: cargoNomination.quantity.value, disabled: true }),
      api: this.fb.control(cargoNomination.api.value, [Validators.required, Validators.min(0), numberValidator(2, 3)]),
      temperature: this.fb.control(cargoNomination.temperature.value, [Validators.required, numberValidator(2, 3)]),
      minTolerance: this.fb.control(cargoNomination.minTolerance.value, [Validators.required, Validators.max(0), Validators.min(-25)]),
      maxTolerance: this.fb.control(cargoNomination.maxTolerance.value, [Validators.required, Validators.max(25), Validators.min(0)]),
      segregation: this.fb.control(cargoNomination.segregation.value, Validators.required),
    });
  }

  /**
   * Initialization for all subscriptions
   *
   * @private
   * @memberof CargoNominationComponent
   */
  private initSubscriptions() {
    this.loadableStudyDetailsTransformationService.addCargoNomination$.subscribe(() => {
      this.addCargoNomination();
    });

    navigator.serviceWorker.addEventListener('message', async event => {
      if (event.data.type === 'cargo_nomination_sync_finished') {
        const index = this.cargoNominations?.findIndex((item) => item.storeKey === event.data.storeKey);
        if (index !== -1) {
          this.cargoNominations[index].id = event.data.cargoNominationId;
          this.cargoNominations = [...this.cargoNominations];
        }
      }
    });
  }

  /**
   * Method for updating loading port data after loading popup save
   *
   * @private
   * @param {ILoadingPopupData} loadingPopupData
   * @memberof CargoNominationComponent
   */
  private updateLoadingPortData(loadingPopupData: ILoadingPopupData) {
    const valueIndex = this.cargoNominations.findIndex(cargoNomination => cargoNomination?.storeKey === loadingPopupData?.rowData?.storeKey);    
    this.cargoNominations[valueIndex].loadingPorts.value = loadingPopupData.rowData.loadingPorts.value;
    this.cargoNominations[valueIndex].loadingPorts.isEditMode = this.cargoNominations[valueIndex]?.isAdd ? true : false;
    this.cargoNominations = [...this.cargoNominations];
    this.updateField(loadingPopupData.rowIndex, 'loadingPorts', loadingPopupData.rowData.loadingPorts.value);
    this.updateField(loadingPopupData.rowIndex, 'quantity', loadingPopupData.rowData.quantity.value);
    this.onEditComplete({ originalEvent: loadingPopupData.originalEvent, data: loadingPopupData.rowData, field: 'loadingPorts', index: loadingPopupData.rowIndex });
  }

  /**
   * Method for fetching form fields
   *
   * @private
   * @param {number} formGroupIndex
   * @param {string} formControlName
   * @returns {FormControl}
   * @memberof CargoNominationComponent
   */
  private field(formGroupIndex: number, formControlName: string): FormControl {
    const formControl = <FormControl>(<FormArray>this.cargoNominationForm.get('dataTable')).at(formGroupIndex).get(formControlName);
    return formControl;
  }

  /**
   * Method for fetching form group
   *
   * @private
   * @param {number} formGroupIndex
   * @returns {FormGroup}
   * @memberof CargoNominationComponent
   */
  private row(formGroupIndex: number): FormGroup {
    const formGroup = <FormGroup>(<FormArray>this.cargoNominationForm.get('dataTable')).at(formGroupIndex);
    return formGroup;
  }


  /**
   * Method for updating form field
   *
   * @private
   * @param {number} index
   * @param {string} field
   * @param {*} value
   * @memberof CargoNominationComponent
   */
  private updateField(index: number, field: string, value: any) {
    const control = this.field(index, field);
    control.setValue(value);
    control.markAsDirty();
    control.markAsTouched();
  }

  /**
   * Method get all invalid fields in a row
   *
   * @private
   * @param {FormGroup} formToInvestigate
   * @returns {string[]}
   * @memberof CargoNominationComponent
   */
  private findInvalidControlsRecursive(formToInvestigate: FormGroup): string[] {
    const invalidControls: string[] = [];
    const recursiveFunc = (form: FormGroup | FormArray) => {
      Object.keys(form.controls).forEach(field => {
        const control = form.get(field);
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
   * Method for enable commingle button
   *
   * @memberof CargoNominationComponent
   */
  private updateCommingleButton() {
    const addedCargoNominations = this.cargoNominations.filter((cargoNomination) => !cargoNomination.isAdd);
    if (addedCargoNominations.length >= 2) {
      this.cargoNominationUpdate.emit(false)
    } else {
      this.cargoNominationUpdate.emit(true)
    }
  }

  /**
   * Handler for filter event
   *
   * @param {IDataTableFilterEvent} event
   * @memberof CargoNominationComponent
   */
  onFilter(event: IDataTableFilterEvent) {
    this.ngxSpinnerService.show();
    const cargoNominationArray = event?.filteredValue?.map(cargoNomination => this.initCargoNominationFormGroup(cargoNomination));
    this.cargoNominationForm.controls.dataTable = this.fb.array([...cargoNominationArray]);
    this.ngxSpinnerService.hide();
  }

  /**
   * Handler for datatable sort event
   *
   * @param {IDataTableSortEvent} event
   * @memberof CargoNominationComponent
   */
  onSort(event: IDataTableSortEvent) {
    this.ngxSpinnerService.show();
    const cargoNominationArray = event?.data?.map(cargoNomination => this.initCargoNominationFormGroup(cargoNomination));
    this.cargoNominationForm.controls.dataTable = this.fb.array([...cargoNominationArray]);
    this.ngxSpinnerService.hide();
  }

}

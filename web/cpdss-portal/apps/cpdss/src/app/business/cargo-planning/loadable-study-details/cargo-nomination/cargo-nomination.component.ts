import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { DATATABLE_EDITMODE, IDataTableColumn, IDataTableFilterEvent, IDataTableSortEvent } from '../../../../shared/components/datatable/datatable.model';
import { ICargoNominationValueObject, ICargoNominationAllDropdownData, ICargoNominationDetailsResponse, ICargoNominationEvent, ICargoNomination, ILoadingPopupData, IApiTempPopupData } from '../../models/cargo-planning.model';
import { LoadableStudyDetailsApiService } from '../../services/loadable-study-details-api.service';
import { LoadableStudyDetailsTransformationService } from '../../services/loadable-study-details-transformation.service';
import { cargoNominationColorValidator } from '../../directives/validator/cargo-nomination-color.directive'
import { cargoNominationLoadingPortValidator } from '../../directives/validator/cargo-nomination-loading-port.directive'
import { alphabetsOnlyValidator } from '../../directives/validator/cargo-nomination-alphabets-only.directive'
import { numberValidator } from '../../../core/directives/number-validator.directive';
import { NgxSpinnerService } from 'ngx-spinner';
import { takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';
import { QUANTITY_UNIT } from '../../../../shared/models/common.model';
import { ConfirmationService, MessageService } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';
import { LoadableStudy } from '../../models/loadable-study-list.model';
import { IPermission } from '../../../../shared/models/user-profile.model';
import { IPortsDetailsResponse, LOADABLE_STUDY_STATUS, Voyage, VOYAGE_STATUS } from '../../../core/models/common.model';
import { GlobalErrorHandler } from '../../../../shared/services/error-handlers/global-error-handler';
import { QuantityDecimalFormatPipe } from '../../../../shared/pipes/quantity-decimal-format/quantity-decimal-format.pipe';

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
export class CargoNominationComponent implements OnInit, OnDestroy {

  @Input() voyageId: number;

  @Input() voyage: Voyage;

  @Input()
  get loadableStudyId() {
    return this._loadableStudyId;
  }
  set loadableStudyId(value: number) {
    this._loadableStudyId = value;
    this.cargoNominationForm = null;
    this.getCargoNominationDetails();
  }

  @Input() permission: IPermission;

  @Input()
  get loadableStudy() {
    return this._loadableStudy;
  }
  set loadableStudy(value: LoadableStudy) {
    this._loadableStudy = value;
    this.editMode = (this.permission?.edit === undefined || this.permission?.edit) && [LOADABLE_STUDY_STATUS.PLAN_PENDING, LOADABLE_STUDY_STATUS.PLAN_NO_SOLUTION, LOADABLE_STUDY_STATUS.PLAN_ERROR].includes(this.loadableStudy?.statusId) && ![VOYAGE_STATUS.CLOSE].includes(this.voyage?.statusId) ? DATATABLE_EDITMODE.CELL : null;
    this.columns = this.loadableStudyDetailsTransformationService.getCargoNominationDatatableColumns(this.permission, this.loadableStudy?.statusId, this.voyage?.statusId);
  }

  @Input() vesselId: number;

  @Output() cargoNominationUpdate = new EventEmitter<any>();
  @Output() portOhqStatusUpdate = new EventEmitter<boolean>();

  // properties
  get cargoNominations(): ICargoNominationValueObject[] {
    return this.loadableStudyDetailsApiService.cargoNominations;
  }
  set cargoNominations(cargoNominations: ICargoNominationValueObject[]) {
    this.totalQuantity = 0;
    const unitFrom = <QUANTITY_UNIT>localStorage.getItem('unit');
    const unitTo = this.loadableStudyDetailsApiService.baseUnit
    this.loadableStudyDetailsApiService.cargoNominations = cargoNominations.map((cargoNomination, _index) => {
      const _cargoNomination = this.loadableStudyDetailsTransformationService.formatCargoNomination(cargoNomination);
      let value = _cargoNomination?.isDelete ? 0 : Number(_cargoNomination.quantity.value);
      value = this.loadableStudyDetailsApiService.updateQuantityByUnit(value, unitFrom, unitTo, _cargoNomination.api.value, _cargoNomination.temperature.value)
      this.totalQuantity += value;
      return _cargoNomination
    });
    this.loadableStudyDetailsTransformationService.setTotalQuantityCargoNomination(this.totalQuantity);
    this.updatePriorityDropdown();
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
  editMode: DATATABLE_EDITMODE;
  columns: IDataTableColumn[];
  listData = <ICargoNominationAllDropdownData>{};
  cargoNominationForm: FormGroup;
  cargoNominationDetails: ICargoNominationDetailsResponse;
  dataTableLoading: boolean;
  cargoNominationCheckUpdatesTimer;
  progress = true;
  apiTempPopupData: IApiTempPopupData;

  // private fields
  private _loadableStudyId: number;
  private _loadableStudy: LoadableStudy;
  private _loadingPopupData: ILoadingPopupData;
  private _openLoadingPopup = false;
  private _openAPITemperatureHistoryPopup = false;
  private totalQuantity = 0;
  private ngUnsubscribe: Subject<any> = new Subject();
  private savedCargoNomination: ICargoNominationValueObject[];


  // public methods
  constructor(
    private loadableStudyDetailsApiService: LoadableStudyDetailsApiService,
    private loadableStudyDetailsTransformationService: LoadableStudyDetailsTransformationService,
    private fb: FormBuilder,
    private ngxSpinnerService: NgxSpinnerService,
    private messageService: MessageService,
    private translateService: TranslateService,
    private confirmationService: ConfirmationService,
    private globalErrorHandler: GlobalErrorHandler,
    private quantityDecimalFormatPipe: QuantityDecimalFormatPipe) {
  }

  /**
   * Component lifecycle ngOnit
   *
   * @returns {Promise<void>}
   * @memberof CargoNominationComponent
   */
  ngOnInit() {
    this.initSubscriptions();
  }

  /**
   * Component lifecycle ngOnDestroy
   *
   * @returns {Promise<void>}
   * @memberof CargoNominationComponent
   */
  ngOnDestroy() {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
    this.loadableStudyDetailsApiService.disableUnitChange = false;
    navigator.serviceWorker.removeEventListener('message', this.swMessageHandler);
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
    await this.getCargoNominations();
    const hasPendingUpdates = await this.checkForPendingUpdates();
    if (hasPendingUpdates) {
      this.dataTableLoading = true;
      this.cargoNominationCheckUpdatesTimer = setInterval(async () => {
        const _hasPendingUpdates = await this.checkForPendingUpdates();
        if (!_hasPendingUpdates) {
          await this.getCargoNominations();
          this.dataTableLoading = false;
          clearInterval(this.cargoNominationCheckUpdatesTimer);
        }
      }, 500);
    }
    this.ngxSpinnerService.hide();
  }

  /**
   * Get cargonominations
   *
   * @memberof CargoNominationComponent
   */
  async getCargoNominations() {
    const cargoNominationFormData: ICargoNominationDetailsResponse = await this.loadableStudyDetailsApiService.getCargoNominationDetails(this.vesselId, this.voyageId, this.loadableStudyId).toPromise();
    if (cargoNominationFormData?.responseStatus?.status === '200') {
      cargoNominationFormData.cargoNominations = cargoNominationFormData.cargoNominations ?? [];
      this.listData.cargoList = cargoNominationFormData?.cargos;
      this.listData.segregationList = cargoNominationFormData?.segregations;
      this.cargoNominationDetails = cargoNominationFormData;
      this.onUnitChange(false);
      this.initCargoNominationArray(this.cargoNominationDetails?.cargoNominations);
    }
  }

  /**
   * Check if there are any pending updates in indexed db
   *
   * @returns {Promise<boolean>}
   * @memberof CargoNominationComponent
   */
  async checkForPendingUpdates(): Promise<boolean> {
    const count = await this.loadableStudyDetailsApiService.getCargoNominationPendingUpdatesCount(this.vesselId, this.voyageId, this.loadableStudyId);
    if (!count) {
      return false;
    } else {
      this.ngxSpinnerService.hide();
      this.dataTableLoading = true;
      return true;
    }
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
* Event handler for grid column click
*
* @param {ICargoNominationEvent} event
* @memberof CargoNominationComponent
*/
  async onColumnClick(event: ICargoNominationEvent) {
    this.ngxSpinnerService.show();
    const valueIndex = this.cargoNominations.findIndex(cargoNomination => cargoNomination?.storeKey === event?.data?.storeKey);
    if (event.field === 'loadingPorts' || event.field === 'quantity') {
      if (event.data?.cargo?.value) {
        const portsResponse: IPortsDetailsResponse = await this.loadableStudyDetailsApiService.getPortsDetails(this.vesselId, this.voyageId, this.loadableStudyId).toPromise();
        const loadableStudyPorts: number[] = portsResponse?.portList ? portsResponse.portList.map( port => (port.portId)) : [];
        const result = await this.loadableStudyDetailsApiService.getAllCargoPorts(event.data?.cargo?.value?.id).toPromise();
        event.data.cargo.value.ports = result?.ports;
        this.cargoNominations[valueIndex]['cargo'].value = event?.data?.cargo?.value;
        this.updateField(event.index, 'cargo', event?.data?.cargo?.value);
        this.cargoNominations = [...this.cargoNominations];
        this.loadingPopupData = <ILoadingPopupData>{
          originalEvent: event.originalEvent,
          rowData: event.data,
          rowIndex: event.index,
          ports: event.data?.cargo?.value?.ports,
          loadableStudyPorts: loadableStudyPorts
        }
        this.openLoadingPopup = true;
      }
    }
    this.ngxSpinnerService.hide();
  }

  /**
   * Event handler for grid cell click
   *
   * @param {ICargoNominationEvent} event
   * @memberof CargoNominationComponent
   */
  async onCellValueClick(event: ICargoNominationEvent) {
    this.ngxSpinnerService.show();
    if (['api', 'temperature'].includes(event.field)) {
      if (event.data?.cargo?.value) {
        this.apiTempPopupData = <IApiTempPopupData>{
          rowDataCargo: event.data?.loadingPorts?.value,
          vesselId: this.vesselId,
          voyageId: this.voyageId,
          loadableStudyId: this.loadableStudyId,
          cargoId: event.data?.cargo?.value.id,
          cargoName: event.data?.cargo?.value.name
        }
        this.openAPITemperatureHistoryPopup = true;
      }
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
    const savedCargoIndex = this.savedCargoNomination.findIndex(cargoNomination => cargoNomination?.storeKey === event?.data?.storeKey);
    if (event.field === 'cargo' && !event.data?.isAdd && !this.savedCargoNomination[savedCargoIndex]['isAdd']) {
      const translationKeys = await this.translateService.get(['CARGO_NOMINATION_CARGO_NAME_CHANGE_CONFIRM_SUMMARY', 'CARGO_NOMINATION_CARGO_NAME_CHANGE_CONFIRM_DETAILS', 'CARGO_NOMINATION_CARGO_NAME_CHANGE_CONFIRM_LABEL', 'CARGO_NOMINATION_CARGO_NAME_CHANGE_REJECT_LABEL']).toPromise();

      this.confirmationService.confirm({
        key: 'confirmation-alert',
        header: translationKeys['CARGO_NOMINATION_CARGO_NAME_CHANGE_CONFIRM_SUMMARY'],
        message: translationKeys['CARGO_NOMINATION_CARGO_NAME_CHANGE_CONFIRM_DETAILS'],
        icon: 'pi pi-exclamation-triangle',
        acceptLabel: translationKeys['CARGO_NOMINATION_CARGO_NAME_CHANGE_CONFIRM_LABEL'],
        acceptIcon: 'pi',
        acceptButtonStyleClass: 'btn btn-main mr-5',
        rejectVisible: true,
        rejectLabel: translationKeys['CARGO_NOMINATION_CARGO_NAME_CHANGE_REJECT_LABEL'],
        rejectIcon: 'pi',
        rejectButtonStyleClass: 'btn btn-main',
        accept: () => {
          this.savedCargoNomination[savedCargoIndex]['isAdd'] = true;
          this.updateCargoNominationsDetails(valueIndex, event);
        },
        reject: () => {
          this.cargoNominations[valueIndex]['cargo'].value = this.savedCargoNomination[valueIndex]['cargo']['_value'];
          this.updateField(event.index, 'cargo', this.savedCargoNomination[valueIndex]['cargo']['_value']);
        }
      });
    } else {
      this.updateCargoNominationsDetails(valueIndex, event);
    }
  }

  /**
   * update details
   *
   * @param {ICargoNominationEvent} event
   * @param {number} valueIndex
   * @memberof CargoNominationComponent
   */
  async updateCargoNominationsDetails(valueIndex: number, event: ICargoNominationEvent) {
    const fromGroup = this.row(event.index);
    if (event.field === 'cargo') {
      this.cargoNominations[valueIndex]['abbreviation'].value = event.data.cargo.value.abbreviation;
      this.cargoNominations[valueIndex]['api'].value = event.data.cargo.value.api;
      this.cargoNominations[valueIndex]['loadingPorts'].value = null;
      this.cargoNominations[valueIndex]['loadingPortsLabel'] = null;
      this.cargoNominations[valueIndex]['loadingPortsNameArray'] = null;
      this.cargoNominations[valueIndex]['quantity'].value = null;
      this.updateField(event.index, 'abbreviation', event.data.cargo.value.abbreviation);
      this.updateField(event.index, 'api', null);
      this.updateField(event.index, 'temperature', null);
      this.updateField(event.index, 'loadingPorts', null);
      this.updateField(event.index, 'quantity', null);
      const result = await this.loadableStudyDetailsApiService.getAllCargoPorts(event.data?.cargo?.value?.id).toPromise();
      event.data.cargo.value.ports = result?.ports;
      this.cargoNominations[valueIndex]['cargo'].value = event?.data?.cargo?.value;
      this.updateField(event.index, 'cargo', event?.data?.cargo?.value);
    } else if (event.field === 'loadingPorts') {
      const cargoId = event.data.cargo.value.id;
      const ports = event.data.loadingPorts.value;
      const portId = ports[ports.length - 1].id
      const result = await this.loadableStudyDetailsApiService.getApiFromCargoPorts(this.vesselId, portId, cargoId).toPromise();
      this.updateField(event.index, 'api', result.api);
      this.updateField(event.index, 'temperature', result.temperature);
      this.cargoNominations[valueIndex]['api'].value = result.api;
      this.cargoNominations[valueIndex]['temperature'].value = result.temperature;
    }
    if (!event.data?.isAdd) {
      if (fromGroup.valid) {
        this.ngxSpinnerService.show();
        event.data.processing = true;
        this.loadableStudyDetailsTransformationService.disableGenerateLoadablePatternBtn(true);
        this.updateCommingleButton(true);
        const row = this.cargoNominations[event.index];
        this.updateRowByUnit(row, this.loadableStudyDetailsApiService.currentUnit, this.loadableStudyDetailsApiService.baseUnit);
        const res = await this.loadableStudyDetailsApiService.setCargoNomination(this.loadableStudyDetailsTransformationService.getCargoNominationAsValue(this.cargoNominations[valueIndex]), this.vesselId, this.voyageId, this.loadableStudyId, true);
        this.updateRowByUnit(row, this.loadableStudyDetailsApiService.baseUnit, this.loadableStudyDetailsApiService.currentUnit);
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
        const invalidFormControls = this.findInvalidControlsRecursive(fromGroup);
        invalidFormControls.forEach((key) => {
          this.cargoNominations[valueIndex][key].isEditMode = true;
        });
        fromGroup.markAllAsTouched();
        this.cargoNominationForm.updateValueAndValidity();
      }
    }
    this.updateFormValidity();
    this.setDisableUnitChange();
    this.loadableStudyDetailsTransformationService.setCargoNominationValidity(this.cargoNominationForm.valid && this.cargoNominations?.filter(item => !item?.isAdd).length > 0);
  }

  /**
   * Update validity of invalid rows if valid
   *
   * @memberof CargoNominationComponent
   */
  updateFormValidity() {
    const formArray = (<FormArray>this.cargoNominationForm.get('dataTable')).controls;
    formArray.forEach(async (row: FormGroup, index) => {
      const _row = this.cargoNominations[index];
      if (row.invalid && row.touched && !_row.isAdd) {
        const invalidFormControls = this.findInvalidControlsRecursive(row);
        invalidFormControls.forEach((key) => {
          const formControl = this.field(index, key);
          formControl.updateValueAndValidity();
        });
        if (row.valid) {
          this.ngxSpinnerService.show();
          _row.processing = true;
          this.updateCommingleButton(true);
          this.updateRowByUnit(_row, this.loadableStudyDetailsApiService.currentUnit, this.loadableStudyDetailsApiService.baseUnit);
          const res = await this.loadableStudyDetailsApiService.setCargoNomination(this.loadableStudyDetailsTransformationService.getCargoNominationAsValue(_row), this.vesselId, this.voyageId, this.loadableStudyId, true);
          this.updateRowByUnit(_row, this.loadableStudyDetailsApiService.baseUnit, this.loadableStudyDetailsApiService.currentUnit);
          if (res) {
            for (const key in this.cargoNominations[index]) {
              if (this.cargoNominations[index].hasOwnProperty(key) && this.cargoNominations[index][key].hasOwnProperty('_isEditMode')) {
                this.cargoNominations[index][key].isEditMode = false;
              }
            }
            this.cargoNominations = [...this.cargoNominations];
          }
          this.ngxSpinnerService.hide();
        }
      } else if (row.invalid) {
        const invalidFormControls = this.findInvalidControlsRecursive(row);
        invalidFormControls.forEach((key) => {
          const formControl = this.field(index, key);
          formControl.updateValueAndValidity();
        });
      }
    });
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
      const translationKeys = await this.translateService.get(['CARGONOMINATION_DELETE_SUMMARY', 'PORT_CHANGE_CONFIRM_DETAILS', 'CARGONOMINATION_DELETE_CONFIRM_LABEL', 'CARGONOMINATION_DELETE_REJECT_LABEL', 'CARGO_NOMINATION_SAVED_CARGO_DETAILS', 'CARGONOMINATION_DELETE_DETAILS']).toPromise();

      this.confirmationService.confirm({
        key: 'confirmation-alert',
        header: translationKeys['CARGONOMINATION_DELETE_SUMMARY'],
        message: !event?.data?.isAdd ? translationKeys['CARGO_NOMINATION_SAVED_CARGO_DETAILS'] : translationKeys['CARGONOMINATION_DELETE_DETAILS'],
        icon: 'pi pi-exclamation-triangle',
        acceptLabel: translationKeys['CARGONOMINATION_DELETE_CONFIRM_LABEL'],
        acceptIcon: 'pi',
        acceptButtonStyleClass: 'btn btn-main mr-5',
        rejectVisible: true,
        rejectLabel: translationKeys['CARGONOMINATION_DELETE_REJECT_LABEL'],
        rejectIcon: 'pi',
        rejectButtonStyleClass: 'btn btn-main',
        accept: async () => {
          this.ngxSpinnerService.show();
          this.updateCommingleButton(true, false);
          let res;
          if (!event?.data?.isAdd) {
            res = await this.loadableStudyDetailsApiService.setCargoNomination(this.loadableStudyDetailsTransformationService.getCargoNominationAsValue(this.cargoNominations[valueIndex]), this.vesselId, this.voyageId, this.loadableStudyId, this.cargoNominationForm.valid && this.cargoNominations?.filter(item => !item?.isAdd).length > 0);
          } else {
            res = true;
          }
          if (res) {
            this.cargoNominations.splice(event.index, 1);
            this.cargoNominations = [...this.cargoNominations];
            const dataTableControl = <FormArray>this.cargoNominationForm.get('dataTable');
            dataTableControl.removeAt(event.index);
            this.loadableStudyDetailsTransformationService.setCargoNominationValidity(this.cargoNominationForm.valid && this.cargoNominations?.filter(item => !item?.isAdd).length > 0);
            this.updateFormValidity();
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
    console.log("cargonominationonRowSave", Date.now()); // TODO: Need to remove after testing.
    const valueIndex = this.cargoNominations.findIndex(cargoNomination => cargoNomination?.storeKey === event?.data?.storeKey);

    if (this.row(event.index).valid) {
      this.ngxSpinnerService.show();
      this.updateCommingleButton(true, false);
      this.updateRowByUnit(this.cargoNominations[valueIndex], this.loadableStudyDetailsApiService.currentUnit, this.loadableStudyDetailsApiService.baseUnit);
      const res = await this.loadableStudyDetailsApiService.setCargoNomination(this.loadableStudyDetailsTransformationService.getCargoNominationAsValue(this.cargoNominations[valueIndex]), this.vesselId, this.voyageId, this.loadableStudyId, this.cargoNominationForm.valid);
      this.updateRowByUnit(this.cargoNominations[valueIndex], this.loadableStudyDetailsApiService.baseUnit, this.loadableStudyDetailsApiService.currentUnit);
      this.loadableStudyDetailsTransformationService.setCargoNominationValidity(this.cargoNominationForm.valid && this.cargoNominations?.filter(item => !item?.isAdd).length > 0);
      this.loadableStudyDetailsTransformationService.setPortValidity(false);
      if (res) {
        this.loadableStudyDetailsTransformationService.portUpdated();
        this.cargoNominations[valueIndex].isAdd = false;
        for (const key in this.cargoNominations[valueIndex]) {
          if (this.cargoNominations[valueIndex].hasOwnProperty(key) && this.cargoNominations[valueIndex][key].hasOwnProperty('_isEditMode')) {
            this.cargoNominations[valueIndex][key].isEditMode = false;
          }
        }
        this.cargoNominations = [...this.cargoNominations];
      }
      this.ngxSpinnerService.hide();

    } else {
      this.row(event.index).markAllAsTouched();
      this.cargoNominationForm.updateValueAndValidity();
      const formControl = this.row(event.index);
      const controls = formControl['controls'];
      for (let col = 0; col < this.columns.length; col++) {
        if (controls[this.columns[col].field]?.invalid) {
          const element: HTMLElement = document.getElementById('cell' + this.columns[col].field + '_' + event.index);
          if (this.columns[col].fieldType === 'ARRAY') {
            element.focus();
          } else {
            element.getElementsByTagName('input')[0].focus();
          }
          break;
        }
      }
    }
    this.updateFormValidity();
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
  private updatePriorityDropdown(): void {
    this.listData.priorityList = [];
    for (let index = 1; index <= 7; index++) {
      this.listData.priorityList.push({ label: index.toString(), value: index })
    }
  }

  /**
   * Method for adding new cargo
   *
   * @private
   * @memberof CargoNominationComponent
   */
  private addCargoNomination(cargoNomination: ICargoNomination = null) {
    cargoNomination = cargoNomination ?? <ICargoNomination>{ id: 0, priority: 1, color: null, cargoId: null, abbreviation: null, quantity: null, segregationId: 1, loadingPorts: null };
    const _cargoNomination = this.loadableStudyDetailsTransformationService.getCargoNominationAsValueObject(cargoNomination, true, this.listData);
    this.cargoNominations = [...this.cargoNominations, _cargoNomination];
    const dataTableControl = <FormArray>this.cargoNominationForm.get('dataTable');
    _cargoNomination.quantity.value = Number(this.quantityDecimalFormatPipe.transform(_cargoNomination.quantity?.value).replace(/,/g, ''));
    dataTableControl.push(this.initCargoNominationFormGroup(_cargoNomination));
    this.cargoNominationForm.updateValueAndValidity();
    this.loadableStudyDetailsTransformationService.setCargoNominationValidity(this.cargoNominationForm.valid && this.cargoNominations?.filter(item => !item?.isAdd).length > 0);
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
      this.updateRowByUnit(cargoData, this.loadableStudyDetailsApiService.baseUnit, this.loadableStudyDetailsApiService.currentUnit)
      return cargoData;
    });
    //TODO: need to remove this. Instead of calling api for each cargo for cargo specific ports must be coming from cargo nomination api
    // _cargoNominations = await this.getCargoPortMapping(_cargoNominations);
    const cargoNominationArray = _cargoNominations.map(cargoNomination => this.initCargoNominationFormGroup(cargoNomination));
    this.cargoNominationForm = this.fb.group({
      dataTable: this.fb.array([...cargoNominationArray])
    });
    this.cargoNominations = _cargoNominations;
    this.savedCargoNomination = JSON.parse(JSON.stringify(this.cargoNominations));
    this.updateCommingleButton(false);
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
      abbreviation: this.fb.control(cargoNomination.abbreviation.value, [Validators.required, alphabetsOnlyValidator, Validators.maxLength(6)]),
      loadingPorts: this.fb.control(cargoNomination.loadingPorts.value, [Validators.required, cargoNominationLoadingPortValidator]),
      quantity: this.fb.control({ value: Number(cargoNomination.quantity.value), disabled: true }),
      api: this.fb.control(cargoNomination.api.value, [Validators.required, Validators.min(0), numberValidator(2, 3)]),
      temperature: this.fb.control(cargoNomination.temperature.value, [Validators.required, numberValidator(2, 3)]),
      minTolerance: this.fb.control(cargoNomination.minTolerance.value, [Validators.required, Validators.max(0), Validators.min(-25), numberValidator(2, 2)]),
      maxTolerance: this.fb.control(cargoNomination.maxTolerance.value, [Validators.required, Validators.max(25), Validators.min(0), numberValidator(2, 2)]),
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
    this.loadableStudyDetailsTransformationService.addCargoNomination$
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(() => {
        this.addCargoNomination();
      });
    this.loadableStudyDetailsApiService.unitChange.asObservable()
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(() => {
        this.onUnitChange();
      })

    this.loadableStudyDetailsApiService.unitChangeBlocked.asObservable()
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(() => {
        this.onUnitChangeBlocked();
      })

    navigator.serviceWorker.addEventListener('message', this.swMessageHandler);
  }

  /**
   * Handler for service worker message event
   *
   * @private
   * @memberof CargoNominationComponent
   */
  private swMessageHandler = async (event) => {
    const translationKeys = await this.translateService.get(['CARGONOMINATION_UPDATE_ERROR', 'CARGONOMINATION_UPDATE_STATUS_ERROR', 'CARGO_NOMINATION_PORT_SELECTION_ERROR_DETAIL']).toPromise();
    if (event?.data?.type === 'cargo_nomination_sync_finished') {
      this.loadableStudyDetailsTransformationService.disableGenerateLoadablePatternBtn(false);
      const index = this.cargoNominations?.findIndex((item) => item.storeKey === event.data.storeKey);
      if (index !== -1) {
        this.cargoNominations[index].processing = false;
        if (event?.data?.responseStatus?.status === '200') {
          this.cargoNominations[index].id = event.data.cargoNominationId;
          this.cargoNominations = [...this.cargoNominations];
          this.savedCargoNomination = JSON.parse(JSON.stringify(this.cargoNominations));
        }
      }
      this.updateCommingleButton(false, false); // enable comingle button
      if (event?.data?.status === '400' && event?.data?.errorCode === 'ERR-RICO-110') {
        this.messageService.add({ severity: 'error', summary: translationKeys['CARGONOMINATION_UPDATE_ERROR'], detail: translationKeys['CARGONOMINATION_UPDATE_STATUS_ERROR'], life: 10000, closable: false, sticky: false });
      }
      if (event?.data?.status === '400' && event?.data?.errorCode === 'ERR-RICO-107') {
        this.messageService.add({ severity: 'error', summary: translationKeys['CARGONOMINATION_UPDATE_ERROR'], detail: translationKeys['CARGO_NOMINATION_PORT_SELECTION_ERROR_DETAIL'], life: 10000, closable: true, sticky: false });
        this.getCargoNominations();
      }
      if (event?.data?.status === '401' && event?.data?.errorCode === '210') {
        this.globalErrorHandler.sessionOutMessage();
      }
    }
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
    this.updateField(loadingPopupData.rowIndex, 'quantity', Number(loadingPopupData.rowData.quantity.value));
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
  private async updateCommingleButton(disableCommingleButton, showQuantityError = true) {
    const addedCargoNominations = this.cargoNominations.filter((cargoNomination) => !cargoNomination.isAdd);
    if (addedCargoNominations.length >= 2) {
      if (this.dataTableLoading) {
        this.cargoNominationUpdate.emit({ value: true, error: showQuantityError });
      } else {
        this.cargoNominationUpdate.emit({ value: disableCommingleButton, error: showQuantityError })
      }
    } else {
      this.cargoNominationUpdate.emit({ value: true, error: showQuantityError })
    }
  }

  /**
   * Handler for filter event
   *
   * @param {IDataTableFilterEvent} event
   * @memberof CargoNominationComponent
   */
  onFilter(event: IDataTableFilterEvent) {
    this.dataTableLoading = true;
    const cargoNominationArray = event?.filteredValue?.map(cargoNomination => this.initCargoNominationFormGroup(cargoNomination));
    this.cargoNominationForm.setControl('dataTable', this.fb.array([...cargoNominationArray]));
    this.dataTableLoading = false;
  }

  /**
   * Handler for datatable sort event
   *
   * @param {IDataTableSortEvent} event
   * @memberof CargoNominationComponent
   */
  onSort(event: IDataTableSortEvent) {
    this.dataTableLoading = true;
    const cargoNominationArray = event?.data?.map(cargoNomination => this.initCargoNominationFormGroup(cargoNomination));
    this.cargoNominationForm.setControl('dataTable', this.fb.array([...cargoNominationArray]));
    this.dataTableLoading = false;
  }

  /**
   * Handler for unit change event
   *
   * @memberof CargoNominationComponent
   */
  onUnitChange(update = true) {
    const unitFrom = this.loadableStudyDetailsApiService.currentUnit
    const unitTo = <QUANTITY_UNIT>localStorage.getItem('unit');
    this.loadableStudyDetailsApiService.currentUnit = unitTo;
    if (update) {
      this.cargoNominations.forEach(row => {
        this.updateRowByUnit(row, unitFrom, unitTo, true);
      })
    }
  }

  /**
   * Method to update a specific row by unit
   *
   * @memberof CargoNominationComponent
   */
  updateRowByUnit(row, unitFrom, unitTo, excludeAdd = false) {
    if (!excludeAdd || !row.isAdd) {
      const api = row.api.value;
      const temp = row.temperature.value;
      row.quantity.value = this.loadableStudyDetailsApiService.updateQuantityByUnit(row.quantity.value, unitFrom, unitTo, api, temp)
      if (row?.loadingPorts?.value?.length) {
        row.loadingPorts.value.forEach(loadingPort => {
          loadingPort.quantity = this.loadableStudyDetailsApiService.updateQuantityByUnit(loadingPort.quantity, unitFrom, unitTo, api, temp)
        })
      }
    }
  }

  /**
   * Handler for unit change blocked event
   *
   * @memberof CargoNominationComponent
   */
  async onUnitChangeBlocked() {
    const translationKeys = await this.translateService.get(['UNIT_CHANGE_API_ERROR', 'UNIT_CHANGE_API_ERROR_DETAILS']).toPromise();
    this.messageService.add({ severity: 'error', summary: translationKeys['UNIT_CHANGE_API_ERROR'], detail: translationKeys['UNIT_CHANGE_API_ERROR_DETAILS'] });

  }

  /**
   * Method to desable/enable unit change
   *
   * @memberof CargoNominationComponent
   */
  setDisableUnitChange() {
    let disable = false;
    if (!this.cargoNominationForm.valid) {
      this.cargoNominations.forEach((row, index) => {
        if (!row.isAdd) {
          const api = this.field(index, 'api')
          const temp = this.field(index, 'temperature')
          if (api.invalid || temp.invalid) {
            disable = true;
            return;
          }
        }
      })
    }
    this.loadableStudyDetailsApiService.disableUnitChange = disable;
  }

  /**
   * function to emit incomplete status change
   * @param {*} event
   * @memberof CargoNominationComponent
   */
  async portOhqTabStatusUpdate(event) {
    this.portOhqStatusUpdate.emit(event);
  }
}

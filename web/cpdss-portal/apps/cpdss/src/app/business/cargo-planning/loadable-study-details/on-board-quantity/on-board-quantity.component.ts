import { Component, ElementRef, Input, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { DATATABLE_EDITMODE, DATATABLE_SELECTIONMODE, IDataTableColumn, IDataTableFilterEvent, IDataTableSortEvent } from '../../../../shared/components/datatable/datatable.model';
import { NgxSpinnerService } from 'ngx-spinner';
import { numberValidator } from '../../../core/directives/number-validator.directive';
import { IPortOBQListData, IPortOBQResponse, IPortOBQTankDetail, IPortOBQTankDetailValueObject } from '../../models/cargo-planning.model';
import { LoadableStudyDetailsApiService } from '../../services/loadable-study-details-api.service';
import { LoadableStudyDetailsTransformationService } from '../../services/loadable-study-details-transformation.service';
import { CommingleApiService } from '../../services/commingle-api.service';
import { ITank, IPort, ITankOptions, LOADABLE_STUDY_STATUS, Voyage, VOYAGE_STATUS } from '../../../core/models/common.model';
import { IPermission } from '../../../../shared/models/user-profile.model';
import { maximumVolumeValidator } from '../../directives/validator/maximum-volumn.directive';
import { LoadableStudy } from '../../models/loadable-study-list.model';
import { TranslateService } from '@ngx-translate/core';
import { MessageService } from 'primeng/api';
import { QUANTITY_UNIT } from '../../../../shared/models/common.model';
import { QuantityPipe } from '../../../../shared/pipes/quantity/quantity.pipe';
import { AppConfigurationService } from '../../../../shared/services/app-configuration/app-configuration.service';
import { GlobalErrorHandler } from '../../../../shared/services/error-handlers/global-error-handler';
import { QuantityDecimalService } from '../../../../shared/services/quantity-decimal/quantity-decimal.service'
import { QuantityDecimalFormatPipe } from '../../../../shared/pipes/quantity-decimal-format/quantity-decimal-format.pipe';
/**
 * Component for OBQ tab
 *
 * @export
 * @class OnBoardQuantityComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-on-board-quantity',
  templateUrl: './on-board-quantity.component.html',
  styleUrls: ['./on-board-quantity.component.scss']
})
export class OnBoardQuantityComponent implements OnInit, OnDestroy {

  @ViewChild('obqDatatable') obqDatatable: ElementRef;

  @Input() voyageId: number;

  @Input() voyage: Voyage;

  @Input()
  get loadableStudyId() {
    return this._loadableStudyId;
  }
  set loadableStudyId(value: number) {
    this._loadableStudyId = value;
    this.obqForm = null;
    this.getPortRotation();
  }

  @Input() vesselId: number;

  @Input() permission: IPermission;

  @Input()
  get loadableStudy() {
    return this._loadableStudy;
  }
  set loadableStudy(value: LoadableStudy) {
    this._loadableStudy = value;
    this.checkEditMode();
  }

  @Input()
  get quantitySelectedUnit(): QUANTITY_UNIT {
    return this._quantitySelectedUnit;

  }

  set quantitySelectedUnit(value: QUANTITY_UNIT) {
    this._prevQuantitySelectedUnit = this.quantitySelectedUnit;
    this._quantitySelectedUnit = value;
    this.cargoTankOptions.weightUnit = value;
    if (this._prevQuantitySelectedUnit) {
      this.convertSelectedPortOBQTankDetails();
      this.updateTankList();
      if(this.selectedTank && this.selectedIndex)
        this.onRowSelection({ data: this.selectedTank, index: this.selectedIndex }, true);
    }
    this.columns = this.loadableStudyDetailsTransformationService.getOBQDatatableColumns(this.quantitySelectedUnit);
  }

  get selectedPortOBQTankDetails() {
    return this._selectedPortOBQTankDetails;

  }

  set selectedPortOBQTankDetails(selectedPortOBQTankDetails: IPortOBQTankDetailValueObject[]) {
    this._selectedPortOBQTankDetails = selectedPortOBQTankDetails.map((obqTankDetail, index) => {
      const _obqTankDetail = this.loadableStudyDetailsTransformationService.formatOBQTankDetail(obqTankDetail);
      return _obqTankDetail;
    })
  }

  get selectedTank(): IPortOBQTankDetailValueObject {
    return this._selectedTank;
  }

  set selectedTank(selectedTank: IPortOBQTankDetailValueObject) {
    this._selectedTank = selectedTank;
  }

  get selectedTankId(): number {
    return this.selectedTank?.tankId;
  }

  set selectedTankId(id: number) {
    this.selectedIndex = this.selectedPortOBQTankDetails?.findIndex(obqDetails => obqDetails.tankId === id);
    this.selectedTank = this.selectedPortOBQTankDetails[this.selectedIndex];
    this.setFillingPercentage(this.selectedTankId);
    this.scrollToSelectedRow(this.selectedIndex);
    this.onRowSelection({ data: this.selectedTank, index: this.selectedIndex });
  }


  editMode: DATATABLE_EDITMODE;
  cargoList = [];
  selectedPort: IPort;
  obqForm: FormGroup;
  columns: IDataTableColumn[];
  listData = <IPortOBQListData>{};
  selectionMode = DATATABLE_SELECTIONMODE.SINGLE;
  selectedIndex = -1;
  tanks: ITank[][] = [];
  dataTableLoading: boolean;
  obqCheckUpdatesTimer;
  cargoTankOptions: ITankOptions = { showTooltip: true, showUllage: false, ullageField: 'correctedUllage', ullageUnit: AppConfigurationService.settings?.ullageUnit, densityField: 'api', weightField: 'quantity', commodityNameField: 'abbreviation' };
  progress = true;
  selectedPortOBQTankDetailsCopy: IPortOBQTankDetail[];

  private _selectedTank: IPortOBQTankDetailValueObject;
  private _loadableStudyId: number;
  private _selectedPortOBQTankDetails: IPortOBQTankDetailValueObject[];
  private _quantitySelectedUnit: QUANTITY_UNIT;
  private _prevQuantitySelectedUnit: QUANTITY_UNIT;
  private _loadableStudy: LoadableStudy;



  constructor(private loadableStudyDetailsApiService: LoadableStudyDetailsApiService,
    private ngxSpinnerService: NgxSpinnerService,
    private loadableStudyDetailsTransformationService: LoadableStudyDetailsTransformationService,
    private commingleApiService: CommingleApiService,
    private fb: FormBuilder,
    private translateService: TranslateService,
    private messageService: MessageService,
    private quantityPipe: QuantityPipe,
    private quantityDecimalService: QuantityDecimalService,
    private quantityDecimalFormatPipe: QuantityDecimalFormatPipe,
    private globalErrorHandler: GlobalErrorHandler) { }

  /**
   * Method called on component destroy
   *
   * @memberof OnBoardQuantityComponent
   */
  ngOnDestroy(): void {
    navigator.serviceWorker.removeEventListener('message', this.swMessageHandler);
  }

  /**
   * Method called on intialization of the component
   *
   * @memberof OnBoardQuantityComponent
   */
  ngOnInit(): void {
    this.initSubscriptions();
    this.checkEditMode();
  }

  /**
* Method for  enable/disable edit mode
*
* @memberof OnBoardQuantityComponent
*/
  checkEditMode() {
    this.editMode = (this.permission?.edit === undefined || this.permission?.edit) && [LOADABLE_STUDY_STATUS.PLAN_PENDING, LOADABLE_STUDY_STATUS.PLAN_NO_SOLUTION, LOADABLE_STUDY_STATUS.PLAN_ERROR].includes(this.loadableStudy?.statusId) && ![VOYAGE_STATUS.CLOSE].includes(this.voyage?.statusId) && !this.voyage?.isDischargeStarted ? DATATABLE_EDITMODE.CELL : null;
  }

  /**
 * Method for  fetching obq port
 *
 * @memberof OnBoardQuantityComponent
 */
  async getPortRotation() {
    this.ngxSpinnerService.show();
    const ports = await this.loadableStudyDetailsApiService.getPorts().toPromise();
    const result = await this.loadableStudyDetailsApiService.getOHQPortRotation(this.vesselId, this.voyageId, this.loadableStudyId).toPromise();

    if (result?.portList) {
      const obqPorts = result?.portList?.map((obqPort) => ports?.find((port) => port.id === obqPort.portId));
      this.selectedPort = obqPorts[0];
      await this.getPortOBQDetails(this.selectedPort?.id);
      this.loadableStudyDetailsTransformationService.setObqValidity(this.obqForm.controls.dataTable.valid);

      const hasPendingUpdates = await this.checkForPendingUpdates();
      if (hasPendingUpdates) {
        this.dataTableLoading = true;
        this.obqCheckUpdatesTimer = setInterval(async () => {
          const _hasPendingUpdates = await this.checkForPendingUpdates();
          if (!_hasPendingUpdates) {
            await this.getPortOBQDetails(this.selectedPort?.id);
            this.loadableStudyDetailsTransformationService.setObqValidity(this.obqForm.controls.dataTable.valid);
            this.dataTableLoading = false;
            clearInterval(this.obqCheckUpdatesTimer);
          }
        }, 500);
      }
    }
    this.ngxSpinnerService.hide();
  }

  /**
 * Method for fetching obq details of selected port
 *
 * @param {number} portId
 * @memberof OnBoardQuantityComponent
 */
  async getPortOBQDetails(portId: number) {
    this.listData = await this.getDropdownData();
    const result: IPortOBQResponse = await this.loadableStudyDetailsApiService.getPortOBQDetails(this.vesselId, this.voyageId, this.loadableStudyId, portId).toPromise();
    const selectedPortOBQTankDetails = result?.onBoardQuantities ?? [];
    this.selectedPortOBQTankDetailsCopy = [...selectedPortOBQTankDetails];
    this.tanks = result?.tanks;

    const _selectedPortOBQTankDetails = selectedPortOBQTankDetails?.map((obqTankDetail) => {
      obqTankDetail.portId = portId;
      [...result?.tanks].forEach(group => group.find(tank => {
        if (tank.id === obqTankDetail.tankId) {
          obqTankDetail.fullCapacityCubm = Number(tank?.fullCapacityCubm);
        }
      }));
      const _obqTankDetail = this.loadableStudyDetailsTransformationService.getOBQTankDetailsAsValueObject(obqTankDetail, false, this.listData, true, this.tanks);
      return _obqTankDetail;
    });
    const quantityDecimal = this.quantityDecimalService.quantityDecimal(this.quantitySelectedUnit);

    this.obqForm = this.fb.group({
      dataTable: this.fb.array([]),
      loadOnTop: this.fb.control(this.loadableStudy?.loadOnTop),
      cargo: new FormControl('', Validators.required),
      api: this.fb.control('', [Validators.required, Validators.min(0), numberValidator(2, 2)]),
      temperature: this.fb.control('', [Validators.required, Validators.min(0), numberValidator(2, 3)]),
      quantity: this.fb.control('', [Validators.required, Validators.min(0), numberValidator(2, 5)]),
      volume: this.fb.control('', [Validators.required, Validators.min(0), numberValidator(quantityDecimal, 7)]),
    });
    this.enableOrDisableControls(this.obqForm, ['api', 'temperature', 'quantity', 'volume'], false);
    this.selectedPortOBQTankDetails = [..._selectedPortOBQTankDetails];
    this.convertSelectedPortOBQTankDetails();
    this.loadableStudyDetailsTransformationService.setObqValidity(this.obqForm.controls.dataTable.valid);
    this.getGradeData();
    this.updateTankList();
  }

  /**
   * Check if there are any pending updates in indexed db
   *
   * @returns {Promise<boolean>}
   * @memberof OnBoardQuantityComponent
   */
  async checkForPendingUpdates(): Promise<boolean> {
    const count = await this.loadableStudyDetailsApiService.getOBQTankDetailsPendingUpdatesCount(this.vesselId, this.voyageId, this.loadableStudyId);
    if (!count) {
      return false;
    } else {
      this.ngxSpinnerService.hide();
      this.dataTableLoading = true;
      return true;
    }
  }

  /**
 * Method for initializing obq row
 *
 * @private
 * @param {IPortOBQTankDetailValueObject} obqTankDetail
 * @returns
 * @memberof OnBoardQuantityComponent
 */
  private initOBQFormGroup(obqTankDetail: IPortOBQTankDetailValueObject) {
    const quantityDecimal = this.quantityDecimalService.quantityDecimal(this.quantitySelectedUnit);
    const volume = this.loadableStudyDetailsTransformationService.convertToNumber(this.quantityDecimalFormatPipe.transform(obqTankDetail?.volume?.value, this.quantitySelectedUnit));

    return this.fb.group({
      cargo: this.fb.control(obqTankDetail?.cargo?.value),
      tankName: this.fb.control(obqTankDetail?.tankName, Validators.required),
      api: this.fb.control(obqTankDetail?.api?.value, [Validators.required, Validators.min(0), numberValidator(2, 2)]),
      temperature: this.fb.control(obqTankDetail?.temperature?.value, [Validators.required, Validators.min(0), numberValidator(2, 3)]),
      quantity: this.fb.control({ value: obqTankDetail?.quantity?.value, disabled: !obqTankDetail?.isSlopTank }, [Validators.required, Validators.min(0), numberValidator(2, 5)]),
      volume: this.fb.control(volume, [Validators.required, Validators.min(0), numberValidator(quantityDecimal, 7), maximumVolumeValidator('api', obqTankDetail, 98)]),
      isSlopTank: this.fb.control(obqTankDetail?.isSlopTank)
    });
  }

  /**
   * Get all lookups for cargo nomination screen
   *
   * @returns {Promise<IPortOBQListData>}
   * @memberof OnBoardQuantityComponent
   */
  async getDropdownData(): Promise<IPortOBQListData> {
    const result = await this.commingleApiService.getCargos().toPromise();
    this.listData = <IPortOBQListData>{};
    this.listData.cargoList = result.cargos;
    return this.listData;
  }

  /**
   * Method to handle edit complete event
   *
   * @param {IPortOBQTankDetailEvent} event
   * @memberof OnBoardQuantityComponent
   */
  async onEditComplete(event) {
    this.ngxSpinnerService.show();
    const formGroup = this.row(event.index);
    const formControl = formGroup.controls;
    const _prevFullcapacitySelectedUnit = QUANTITY_UNIT.OBSKL;
    const weightUnit = QUANTITY_UNIT.MT;
    const selectedRowObq = this.selectedPortOBQTankDetailsCopy[event.index];

    if (_prevFullcapacitySelectedUnit !== this.quantitySelectedUnit) {
      const fullCapacity = this.quantityPipe.transform(event?.data?.fullCapacityCubm, _prevFullcapacitySelectedUnit, this.quantitySelectedUnit, !(event?.data?.api?.value) ? 1 : event?.data?.api?.value, event?.data?.temperature?.value, -1);
      event.data.fullCapacity = fullCapacity ?? 0;
    } else {
      event.data.fullCapacity = event?.data?.fullCapacityCubm;
    }

    if (event?.field === 'cargo') {
      this.selectedPortOBQTankDetails[event.index].colorCode = event?.data?.cargo?.value?.color;
      this.selectedPortOBQTankDetails[event.index].abbreviation = event?.data?.cargo?.value?.abbreviation;
      this.selectedPortOBQTankDetails[event.index].cargo.value.id = selectedRowObq.cargoId;
      if (event?.data?.cargo?.value?.name === 'Slops') {
        this.cargoList = this.cargoList.findIndex(cargo => cargo?.id === -1) === -1 ? [...this.cargoList, { colorCode: event?.data?.cargo?.value?.color, ...event?.data?.cargo?.value }] : this.cargoList;
        formControl.quantity.enable();
        formControl.isSlopTank.setValue(true);
        formControl.api.setValue(0);
        formControl.temperature.setValue(0);
        formControl.quantity.setValue(0);
        formControl.volume.setValue(0);
        formControl.cargo.updateValueAndValidity();
        this.selectedPortOBQTankDetails[event.index].isSlopTank = true;
        this.selectedPortOBQTankDetails[event.index].slopCargoId = -1;
        this.selectedPortOBQTankDetails[event.index].api.value = 0;
        this.selectedPortOBQTankDetails[event.index].temperature.value = 0;
        this.selectedPortOBQTankDetails[event.index].quantity.value = 0;
        this.selectedPortOBQTankDetails[event.index].volume.value = 0;
      } else {
        formControl.quantity.disable();
        formControl.isSlopTank.setValue(false);
        formControl.api.setValue(selectedRowObq.api);
        formControl.temperature.setValue(selectedRowObq.temperature);
        formControl.quantity.setValue(selectedRowObq.quantity);
        formControl.volume.setValue(selectedRowObq.volume);
        formControl.cargo.updateValueAndValidity();
        this.selectedPortOBQTankDetails[event.index].isSlopTank = false;
        this.selectedPortOBQTankDetails[event.index].api.value = selectedRowObq.api;
        this.selectedPortOBQTankDetails[event.index].temperature.value = selectedRowObq.temperature;
        this.selectedPortOBQTankDetails[event.index].quantity.value = selectedRowObq.quantity;
        this.selectedPortOBQTankDetails[event.index].volume.value = selectedRowObq.volume;
      }
    }

    if (event?.field === 'api' || 'temperature' || 'volume') {
      if (event?.data?.isSlopTank) {
        this.selectedPortOBQTankDetails[event.index]['slopApi'] = formControl.api.value;
        this.selectedPortOBQTankDetails[event.index]['slopTemperature'] = formControl.temperature.value;
        this.selectedPortOBQTankDetails[event.index]['slopQuantity'] = formControl.quantity.value;
        this.selectedPortOBQTankDetails[event.index]['slopVolume'] = formControl.volume.value;
      } else {
        let calculatedWgt = this.changeUnit(formGroup, this.quantitySelectedUnit, weightUnit, true);
        formControl.quantity.setValue(calculatedWgt);
        this.selectedPortOBQTankDetails[event.index]['quantity'].value = calculatedWgt;
      }
      formControl.quantity.updateValueAndValidity();
    }

    if (formGroup.valid && (formControl.api.value >= 0 && formControl.temperature.value >= 0 && formControl.quantity.value >= 0 && formControl.volume.value >= 0)) {
      event.data.processing = true;
      this.loadableStudyDetailsTransformationService.disableGenerateLoadablePatternBtn(true);
      if (event?.data?.isSlopTank) {
        this.selectedPortOBQTankDetailsCopy[event.index]['slopApi'] = formControl.api.value;
        this.selectedPortOBQTankDetailsCopy[event.index]['slopTemperature'] = formControl.temperature.value;
        this.selectedPortOBQTankDetailsCopy[event.index]['slopQuantity'] = formControl.quantity.value;
        this.selectedPortOBQTankDetailsCopy[event.index]['slopVolume'] = formControl.volume.value;
      } else {
        this.selectedPortOBQTankDetailsCopy[event.index]['api'] = formControl.api.value;
        this.selectedPortOBQTankDetailsCopy[event.index]['temperature'] = formControl.temperature.value;
        this.selectedPortOBQTankDetailsCopy[event.index]['quantity'] = formControl.quantity.value;
        this.selectedPortOBQTankDetailsCopy[event.index]['volume'] = formControl.volume.value;
      }
      const _selectedPortOBQTankDetail = this.convertToStandardUnitForSave(this.selectedPortOBQTankDetails[event.index]);
      _selectedPortOBQTankDetail.loadOnTop = this.obqForm.controls?.loadOnTop?.value;
      const res = await this.loadableStudyDetailsApiService.setOBQTankDetails(_selectedPortOBQTankDetail, this.vesselId, this.voyageId, this.loadableStudyId, this.obqForm.controls.dataTable.valid);
      this.updateTankList();
      this.setFillingPercentage(this.selectedTankId);
    }
    Object.keys(formGroup.controls).forEach(key => {
      const control = formGroup.get(key);
      if ((this.selectedPortOBQTankDetails[event.index][key]).hasOwnProperty('_isEditMode')) {
        this.selectedPortOBQTankDetails[event.index][key].isEditMode = control.invalid;
        control.markAsTouched();
        this.obqForm.updateValueAndValidity();
      }
    });

    this.obqForm.controls.api.setValue(!isNaN(formControl.api.value) ? Number(formControl.api.value) : formControl.api.value);
    this.obqForm.controls.temperature.setValue(!isNaN(formControl.temperature.value) ? Number(formControl.temperature.value) : formControl.temperature.value);
    this.obqForm.controls.quantity.setValue(!isNaN(formControl.quantity.value) ? Number(formControl.quantity.value).toFixed(2) : formControl.quantity.value);
    this.obqForm.controls.volume.setValue(!isNaN(formControl.volume.value) ? Number(formControl.volume.value) : formControl.volume.value);
    this.obqForm.controls.cargo.setValue(formControl.cargo.value?.name);
    this.loadableStudyDetailsTransformationService.setObqValidity(this.obqForm.controls.dataTable.valid);
    this.ngxSpinnerService.hide();
  }

  /**
   * Method to handle row selection event
   *
   * @param {IPortOBQTankDetailEvent} event
   * @memberof OnBoardQuantityComponent
   */
  onRowSelection(event, isUnitChanged?: boolean) {
    const data = event.data;
    this.selectedTank = data;
    this.enableOrDisableControls(this.obqForm, ['api', 'temperature', 'quantity', 'volume']);
    if (!data?.isSlopTank) {
      this.enableOrDisableControls(this.obqForm, ['quantity'], false);
    }
    if (this.selectedIndex !== event.index || isUnitChanged) {
      const quantityDecimal = this.quantityDecimalService.quantityDecimal(this.quantitySelectedUnit);
      const volume = Number(data?.volume?.value).toFixed(quantityDecimal);
      const weight = Number(data?.quantity?.value).toFixed(2);
      this.selectedIndex = event.index;
      this.obqForm.controls.api.setValue(Number(data?.api.value));
      this.obqForm.controls.temperature.setValue(Number(data?.temperature.value));
      this.obqForm.controls.quantity.clearValidators();
      this.obqForm.controls.quantity.setValue(Number(weight));
      this.obqForm.controls.quantity.setValidators([Validators.required, Validators.min(0), numberValidator(2, 5)]);
      this.obqForm.controls.volume.clearValidators();
      this.obqForm.controls.volume.setValue(Number(volume));
      this.obqForm.controls.volume.setValidators([Validators.required, Validators.min(0), numberValidator(quantityDecimal, 7), maximumVolumeValidator('api', event.data, 98)]);
    }
    this.obqForm.controls.cargo.setValue(data?.cargo?.value?.name);
    this.setFillingPercentage(this.selectedTankId);
    this.loadableStudyDetailsTransformationService.setObqValidity(this.obqForm.controls.dataTable.valid);
  }

  /**
 * Method to disable or enable a list of form controls in a formGroup
 *
 * @memberof OnBoardQuantityComponent
 */
  enableOrDisableControls(form: FormGroup, controls: string[], enable: boolean = true) {
    controls.forEach(control => {
      if (form.controls[control]) {
        if (enable) form.controls[control].enable();
        else form.controls[control].disable();
        form.controls[control].markAsUntouched();
        form.controls[control].markAsPristine();
      }
    });
  }

  /**
   * Initialization for all subscriptions
   *
   * @private
   * @memberof OnBoardQuantityComponent
   */
  private initSubscriptions() {
    navigator.serviceWorker.addEventListener('message', this.swMessageHandler);
  }

  /**
   * Handler for service worker message event
   *
   * @private
   * @memberof OnBoardQuantityComponent
   */
  private swMessageHandler = async (event) => {
    const translationKeys = await this.translateService.get(['OBQ_UPDATE_ERROR', 'OBQ_UPDATE_STATUS_ERROR']).toPromise();
    if (event?.data?.type === 'obq_sync_finished') {
      this.loadableStudyDetailsTransformationService.disableGenerateLoadablePatternBtn(false);
      const index = this.selectedPortOBQTankDetails?.findIndex((item) => item.storeKey === event.data.storeKey);
      if (index !== -1) {
        this.selectedPortOBQTankDetails[index].processing = false;
        if (event?.data?.status === '200') {
          this.selectedPortOBQTankDetails[index].id = event.data.id;
          this.selectedPortOBQTankDetails = [...this.selectedPortOBQTankDetails];
        }
      }
      this.updateTankList();
      if (event?.data?.status === '400' && (event?.data?.errorCode === 'ERR-RICO-110' || event?.data?.errorCode === 'ERR-RICO-392')) {
        this.messageService.add({ severity: 'error', summary: translationKeys['OBQ_UPDATE_ERROR'], detail: translationKeys['OBQ_UPDATE_STATUS_ERROR'], life: 10000, closable: false, sticky: false });
      }
      if (event?.data?.status === '401' && event?.data?.errorCode === '210') {
        this.globalErrorHandler.sessionOutMessage();
      }
    }
  }

  /**
   * Method for fetching form group
   *
   * @private
   * @param {number} formGroupIndex
   * @returns {FormGroup}
   * @memberof OnBoardQuantityComponent
   */
  private row(formGroupIndex: number): FormGroup {
    const formGroup = <FormGroup>(<FormArray>this.obqForm.get('dataTable')).at(formGroupIndex);
    return formGroup;
  }

  /**
 * Get field errors
 *
 * @param {number} formGroupIndex
 * @param {string} formControlName
 * @returns {ValidationErrors}
 * @memberof OnBoardQuantityComponent
 */
  fieldError(formGroupIndex: number, formControlName: string): ValidationErrors {
    const formControl = this.field(formGroupIndex, formControlName);
    return formControl.invalid && (formControl.dirty || formControl.touched) ? formControl.errors : null;
  }

  /**
 * Get form control of form
 *
 * @param {number} formGroupIndex
 * @param {string} formControlName
 * @returns {FormControl}
 * @memberof OnBoardQuantityComponent
 */
  field(formGroupIndex: number, formControlName: string): FormControl {
    const formControl = <FormControl>this.obqForm.get(formControlName);
    return formControl;
  }

  /**
   *  Fetch error messages for fields
   *
   * @param {string} field
   * @memberof OnBoardQuantityComponent
   */
  getErrorMessages(field: string) {
    const column = this.columns.find(col => {
      return col.field === field
    });
    return column?.errorMessages;
  }

  /**
 * Method to update commodity in tank list
 *
 * @memberof OnBoardQuantityComponent
 */
  updateTankList() {
    this.tanks = this.tanks.map(group => {
      const newGroup = group.map((groupItem) => {
        const tank = Object.assign({}, groupItem);
        const selectedPortOBQTankDetail = this.selectedPortOBQTankDetails.find((item) => (item.tankId === groupItem.id) && item);
        const api = selectedPortOBQTankDetail?.isSlopTank ? selectedPortOBQTankDetail?.slopApi : selectedPortOBQTankDetail?.api.value;
        const temp = selectedPortOBQTankDetail?.isSlopTank ? selectedPortOBQTankDetail?.slopTemperature : selectedPortOBQTankDetail?.temperature.value;
        const weight = selectedPortOBQTankDetail?.isSlopTank ? selectedPortOBQTankDetail?.slopQuantity : selectedPortOBQTankDetail?.quantity.value;
        const weightKL = selectedPortOBQTankDetail?.isSlopTank ? selectedPortOBQTankDetail?.slopVolume : selectedPortOBQTankDetail?.volume.value;
        const volume = this.quantityPipe.transform(weight, QUANTITY_UNIT.MT, QUANTITY_UNIT.OBSKL, api, temp, -1);
        tank.commodity = {
          abbreviation: selectedPortOBQTankDetail?.abbreviation,
          colorCode: selectedPortOBQTankDetail?.colorCode,
          api: api,
          quantity: weightKL,
          volume: volume
        }
        return tank;
      });
      return newGroup;
    });
  }

  /**
  * Handler for change event of fields of tank layout form
  *
  * @param {*} event
  * @param {string} field
  * @memberof OnBoardQuantityComponent
  */
  onChange(event, field: string) {
    const controls = this.obqForm.controls;
    if (controls[field].valid) {
      this.selectedTank[field].value = event?.target.value;
      const formGroup = this.row(this.selectedIndex);
      formGroup.controls[field]?.setValue(event?.target.value);
    }
    if (this.obqForm.valid || (controls.api.valid && controls.temperature.valid && controls.quantity.valid && controls.volume.valid)) {
      this.onEditComplete({ originalEvent: event, data: this.selectedTank, field: field, index: this.selectedIndex });
    } else {
      controls.api.markAsTouched();
      controls.quantity.markAsTouched();
      this.obqForm.updateValueAndValidity();
    }
  }

  /**
  * Method to get grades to show on top of tank layout
  *
  * @memberof OnBoardQuantityComponent
  */
  getGradeData() {
    this.selectedPortOBQTankDetails.forEach(obqDetails => {
      if (this.cargoList.findIndex(cargo => cargo.id === obqDetails?.cargo?.value?.id) === -1) {
        if (obqDetails.cargo.value !== undefined) {
          obqDetails.cargo.value.abbreviation = obqDetails?.abbreviation;
        }
        this.cargoList.push({ colorCode: obqDetails?.colorCode, ...obqDetails?.cargo?.value })
      }
    })
  }

  /**
 * Set filling percentage label value
 *
 * @param {number} selectedTankId
 * @memberof OnBoardQuantityComponent
 */
  setFillingPercentage(selectedTankId: number) {
    const tanks = [...this.tanks];
    for (let index = 0; index < tanks.length; index++) {
      const selectedTank = tanks[index]?.find(tank => tank.id === selectedTankId);
      if (selectedTank) {
        this.selectedTank.percentageFilled = selectedTank?.percentageFilled ?? '';
        break;
      }

    }
  }

  /**
   * Method to scroll to seletced row in the obq grid
   *
   * @param {number} rowIndex
   * @memberof OnBoardQuantityComponent
   */
  scrollToSelectedRow(rowIndex: number) {
    const rows = this.obqDatatable?.nativeElement?.querySelectorAll('table tbody tr');
    if (rows) {
      rows[rowIndex]?.scrollIntoView({
        behavior: 'smooth',
        block: 'center'
      });
    }
  }

  /**
   * Method for toggling load on top checkbox
   *
   * @param {*} event
   * @memberof OnBoardQuantityComponent
   */
  async toggleLoadOnTop(event) {
    if (this.editMode) {
      this.loadableStudy.loadOnTop = event.target.checked;
      this.ngxSpinnerService.show();
      const translationKeys = await this.translateService.get(['LOADABLE_STUDY_LOAD_ON_TOP_SAVE_SUCCESS', 'LOADABLE_STUDY_LOAD_ON_TOP_SAVE_SUCCESS_DETAIL', 'LOADABLE_STUDY_LOAD_ON_TOP_SAVE_ERROR', 'LOADABLE_STUDY_LOAD_ON_TOP_SAVE_STATUS_ERROR']).toPromise();
      try {
        const res = await this.loadableStudyDetailsApiService.saveLoadableStudyLoadOnTop(this.vesselId, this.voyageId, this.loadableStudyId, { isLoadOnTop: event.target.checked }).toPromise();
        if (res?.responseStatus?.status === "200") {
          this.messageService.add({ severity: 'success', summary: translationKeys['LOADABLE_STUDY_LOAD_ON_TOP_SAVE_SUCCESS'], detail: translationKeys['LOADABLE_STUDY_LOAD_ON_TOP_SAVE_SUCCESS_DETAIL'] });
        }
      }
      catch (errorResponse) {
        if (errorResponse?.error?.errorCode === 'ERR-RICO-110' || errorResponse?.error?.errorCode === 'ERR-RICO-392') {
          this.messageService.add({ severity: 'error', summary: translationKeys['LOADABLE_STUDY_LOAD_ON_TOP_SAVE_ERROR'], detail: translationKeys['LOADABLE_STUDY_LOAD_ON_TOP_SAVE_STATUS_ERROR'], life: 10000 });
        }
      }
      this.ngxSpinnerService.hide();
    }
  }

  /**
   * Converting obq quantities in selected unit
   *
   * @memberof OnBoardQuantityComponent
   */
  convertSelectedPortOBQTankDetails() {
    const _prevQuantitySelectedUnit = this._prevQuantitySelectedUnit ?? QUANTITY_UNIT.OBSKL;

    const _selectedPortOBQTankDetails = this.selectedPortOBQTankDetails?.map(obqTankDetail => {
      if (obqTankDetail.api.value >= 0 && obqTankDetail.temperature.value >= 0) {
        const volume = this.changeUnit(obqTankDetail, _prevQuantitySelectedUnit, this.quantitySelectedUnit, false);
        obqTankDetail.volume.value = volume ?? 0;
        // setting converted values to the form below tank layout
        if (obqTankDetail.tankId === this.selectedTankId) {
          this.obqForm.controls.volume.setValue(obqTankDetail.volume.value);
        }
        const _prevFullcapacitySelectedUnit = QUANTITY_UNIT.OBSKL;
        const fullCapacity = this.quantityPipe.transform(obqTankDetail?.fullCapacityCubm, _prevFullcapacitySelectedUnit, this.quantitySelectedUnit, !(obqTankDetail?.api?.value) ? 1 : obqTankDetail?.api?.value, obqTankDetail?.temperature?.value, -1);
        obqTankDetail.fullCapacity = fullCapacity ?? 0;
      }
      return obqTankDetail;
    });
    this.selectedPortOBQTankDetails = [..._selectedPortOBQTankDetails];

    const _selectedPortOBQTankDetailsCopy = this.selectedPortOBQTankDetailsCopy?.map(obqTankData => {
      const volume = this.changeUnit(obqTankData, _prevQuantitySelectedUnit, this.quantitySelectedUnit, false);
      obqTankData.volume = volume;
      return obqTankData;
    });
    this.selectedPortOBQTankDetailsCopy = [..._selectedPortOBQTankDetailsCopy];

    const obqTankDetailsArray = _selectedPortOBQTankDetails?.map(obqTankDetails => this.initOBQFormGroup(obqTankDetails));
    this.obqForm.setControl('dataTable', this.fb.array([...obqTankDetailsArray]));
  }

  /**
   * Method to convert obq quantity to standard unit for saving
   *
   * @param {IPortOBQTankDetailValueObject} selectedPortOBQTankDetails
   * @returns {IPortOBQTankDetail}
   * @memberof OnBoardQuantityComponent
   */
  convertToStandardUnitForSave(selectedPortOBQTankDetails: IPortOBQTankDetailValueObject): IPortOBQTankDetail {
    const _selectedPortOBQTankDetail = this.loadableStudyDetailsTransformationService.getOBQTankDetailAsValue(selectedPortOBQTankDetails);
    _selectedPortOBQTankDetail.volume = _selectedPortOBQTankDetail?.volume ? this.changeUnit(_selectedPortOBQTankDetail, this.quantitySelectedUnit, QUANTITY_UNIT.OBSKL, false) : 0;
    return _selectedPortOBQTankDetail;
  }

  /**
   * Handler for filter event
   *
   * @param {IDataTableFilterEvent} event
   * @memberof OnBoardQuantityComponent
   */
  onFilter(event: IDataTableFilterEvent) {
    this.dataTableLoading = true;
    const obqTankDetailsArray = event?.filteredValue?.map((obqTankDetails) => this.initOBQFormGroup(obqTankDetails));
    this.obqForm.setControl('dataTable', this.fb.array([...obqTankDetailsArray]));
    this.dataTableLoading = false;
  }

  /**
   * Handler for datatable sort event
   *
   * @param {IDataTableSortEvent} event
   * @memberof OnBoardQuantityComponent
   */
  onSort(event: IDataTableSortEvent) {
    this.dataTableLoading = true;
    const obqTankDetailsArray = event?.data?.map((obqTankDetails) => this.initOBQFormGroup(obqTankDetails));
    this.obqForm.setControl('dataTable', this.fb.array([...obqTankDetailsArray]));
    this.dataTableLoading = false;
  }

  /**
   * Method to convert quantity to OBSBBLS, OBSKL, MT
   *
   * @param {FormGroup} formGroup
   * @param {QUANTITY_UNIT} unitFrom
   * @param {QUANTITY_UNIT} unitTo
   * @return {*}  {number}
   * @memberof OnBoardQuantityComponent
   */
  changeUnit(obqTankDetail: any, unitFrom: QUANTITY_UNIT, unitTo: QUANTITY_UNIT, isFormGroup: boolean): number {
    const value = isFormGroup ? obqTankDetail.controls.volume.value : obqTankDetail.volume.value ?? obqTankDetail.volume;
    const api = isFormGroup ? obqTankDetail.controls.api.value : obqTankDetail.api.value ?? obqTankDetail.api;
    const temp = isFormGroup ? obqTankDetail.controls.temperature.value : obqTankDetail.temperature.value ?? obqTankDetail.temperature;
    const qty = this.quantityPipe.transform(value?.toString(), unitFrom, unitTo, api?.toString(), temp?.toString(), -1);
    return qty;
  }

}

import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { DATATABLE_EDITMODE, DATATABLE_SELECTIONMODE, IDataTableColumn } from '../../../../shared/components/datatable/datatable.model';
import { NgxSpinnerService } from 'ngx-spinner';
import { numberValidator } from '../../directives/validator/number-validator.directive';
import { IPortOBQListData, IPortOBQTankDetail, IPortOBQTankDetailValueObject } from '../../models/cargo-planning.model';
import { LoadableStudyDetailsApiService } from '../../services/loadable-study-details-api.service';
import { LoadableStudyDetailsTransformationService } from '../../services/loadable-study-details-transformation.service';
import { CommingleApiService } from '../../services/commingle-api.service';
import { ITank, IPort, ITankOptions } from '../../../core/models/common.model';
import { IPermission } from '../../../../shared/models/user-profile.model';
import { maximumVolumeValidator } from '../../directives/validator/maximum-volumn.directive';
import { LoadableStudy } from '../../models/loadable-study-list.model';
import { TranslateService } from '@ngx-translate/core';
import { MessageService } from 'primeng/api';
import { QUANTITY_UNIT } from '../../../../shared/models/common.model';
import { QuantityPipe } from '../../../../shared/pipes/quantity/quantity.pipe';
import { AppConfigurationService } from '../../../../shared/services/app-configuration/app-configuration.service';

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
export class OnBoardQuantityComponent implements OnInit {

  @ViewChild('obqDatatable') obqDatatable: ElementRef;

  @Input() voyageId: number;

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
  @Input() loadableStudy: LoadableStudy;
  @Input() permission: IPermission;

  @Input()
  get quantitySelectedUnit(): QUANTITY_UNIT {
    return this._quantitySelectedUnit;

  }

  set quantitySelectedUnit(value: QUANTITY_UNIT) {
    this._prevQuantitySelectedUnit = this.quantitySelectedUnit;
    this._quantitySelectedUnit = value;
    if (this._prevQuantitySelectedUnit) {
      this.convertSelectedPortOBQTankDetails();
    }
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
  
  
  readonly editMode = DATATABLE_EDITMODE.CELL;
  cargoList = [];
  selectedPort: IPort;
  obqForm: FormGroup;
  columns: IDataTableColumn[];
  listData = <IPortOBQListData>{};
  selectionMode = DATATABLE_SELECTIONMODE.SINGLE;
  selectedIndex = -1;
  tanks: ITank[][] = [];
  isEditable = false;
  dataTableLoading: boolean;
  obqCheckUpdatesTimer;
  cargoTankOptions: ITankOptions = { showTooltip: true, ullageField: 'correctedUllage', ullageUnit: 'CM', densityField: 'api', weightField: 'quantity', commodityNameField: 'abbreviation' };

  private _selectedTank: IPortOBQTankDetailValueObject;
  private _loadableStudyId: number;
  private _selectedPortOBQTankDetails: IPortOBQTankDetailValueObject[];  
  private _quantitySelectedUnit: QUANTITY_UNIT;
  private _prevQuantitySelectedUnit: QUANTITY_UNIT;



  constructor(private loadableStudyDetailsApiService: LoadableStudyDetailsApiService,
    private ngxSpinnerService: NgxSpinnerService,
    private loadableStudyDetailsTransformationService: LoadableStudyDetailsTransformationService,
    private commingleApiService: CommingleApiService,
    private fb: FormBuilder,
    private translateService: TranslateService,
    private messageService: MessageService,
    private quantityPipe: QuantityPipe) { }

  /**
   * Method called on intialization of the component
   *
   * @memberof OnBoardQuantityComponent
   */
  ngOnInit(): void {
    this.isEditable = this.permission ? this.permission?.edit : false;
    this.columns = this.loadableStudyDetailsTransformationService.getOBQDatatableColumns();
    this.initSubscriptions();
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
    const result = await this.loadableStudyDetailsApiService.getPortOBQDetails(this.vesselId, this.voyageId, this.loadableStudyId, portId).toPromise();
    const selectedPortOBQTankDetails = result?.onBoardQuantities ?? [];
    this.tanks = result?.tanks;
    const _selectedPortOBQTankDetails = selectedPortOBQTankDetails?.map((obqTankDetail) => {
      obqTankDetail.portId = portId;
      [...result?.tanks].forEach(group => group.find(tank => {
        if (tank.id === obqTankDetail.tankId) {
          obqTankDetail.fullCapacityCubm = Number(tank?.fullCapacityCubm);
        }
      }));
      const _obqTankDetail = this.loadableStudyDetailsTransformationService.getOBQTankDetailsAsValueObject(obqTankDetail, false, this.listData);
      return _obqTankDetail;
    });

    this.obqForm = this.fb.group({
      dataTable: this.fb.array([]),
      loadOnTop: this.fb.control(this.loadableStudy?.loadOnTop),
      cargo: new FormControl('', Validators.required),
      api: this.fb.control('', [Validators.required, Validators.min(0), numberValidator(2, 2)]),
      quantity: this.fb.control('', [Validators.required, Validators.min(0), numberValidator(2, 7)]),
    });
    this.enableOrDisableControls(this.obqForm, ['api', 'quantity'], false);
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
    return this.fb.group({
      cargo: this.fb.control(obqTankDetail.cargo),
      tankName: this.fb.control(obqTankDetail.tankName, Validators.required),
      api: this.fb.control(obqTankDetail.api.value, [Validators.required, Validators.min(0), numberValidator(2, 2)]),
      quantity: this.fb.control(obqTankDetail.quantity.value, [Validators.required, Validators.min(0), numberValidator(2, 7), maximumVolumeValidator('api', obqTankDetail)]),
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
    const _prevFullcapacitySelectedUnit = AppConfigurationService.settings.volumeBaseUnit;
    if (_prevFullcapacitySelectedUnit !== this.quantitySelectedUnit) {
      const fullCapacity = this.quantityPipe.transform(event?.data?.fullCapacityCubm, _prevFullcapacitySelectedUnit, this.quantitySelectedUnit, event?.data?.api.value);
      event.data.fullCapacity = fullCapacity ?? 0;
    } else {
      event.data.fullCapacity = event?.data?.fullCapacityCubm;
    }
    const volume = this.quantityPipe.transform(event?.data?.quantity?.value, this.quantitySelectedUnit, AppConfigurationService.settings.volumeBaseUnit, event?.data?.api?.value);
    event.data.volume = volume ?? 0;

    if(event?.field === 'api'){
      formGroup.controls.quantity.updateValueAndValidity();   
    }

    if (formGroup.valid) {
      const _selectedPortOBQTankDetail = this.convertToStandardUnitForSave(event.data);     
      _selectedPortOBQTankDetail.loadOnTop = this.obqForm.controls?.loadOnTop?.value;
      const res = await this.loadableStudyDetailsApiService.setOBQTankDetails(_selectedPortOBQTankDetail, this.vesselId, this.voyageId, this.loadableStudyId);
      this.updateTankList();
      this.setFillingPercentage(this.selectedTankId);
    } 
    Object.keys(formGroup.controls).forEach(key => {
      const control = formGroup.get(key);
      if((this.selectedPortOBQTankDetails[event.index][key]).hasOwnProperty('_isEditMode')) {
        this.selectedPortOBQTankDetails[event.index][key].isEditMode = control.invalid;
        control.markAsTouched();
        this.obqForm.updateValueAndValidity();
      }
    });  

    this.obqForm.controls.api.setValue(Number(formGroup.controls.api.value));
    this.obqForm.controls.quantity.setValue(Number(formGroup.controls.quantity.value));
    this.loadableStudyDetailsTransformationService.setObqValidity(this.obqForm.controls.dataTable.valid);
    this.ngxSpinnerService.hide();
  }

  /**
   * Method to handle row selection event
   *
   * @param {IPortOBQTankDetailEvent} event
   * @memberof OnBoardQuantityComponent
   */
  onRowSelection(event) {
    if(this.selectedIndex !== event.index) {
      this.enableOrDisableControls(this.obqForm, ['api', 'quantity']);
      const data = event.data
      this.selectedTank = data;
      this.selectedIndex = event.index
      this.obqForm.controls.cargo.setValue(data?.cargo?.value?.name)
      this.obqForm.controls.api.setValue(data?.api.value);
      this.obqForm.controls.quantity.setValue(data?.quantity.value);
      this.obqForm.controls.quantity.setValidators([Validators.required, Validators.min(0), numberValidator(2, 7), Validators.max(Number(event.data?.fullCapacityCubm * event?.data?.api?.value))]);
      this.setFillingPercentage(this.selectedTankId);
      this.loadableStudyDetailsTransformationService.setObqValidity(this.obqForm.controls.dataTable.valid);
    }
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
    navigator.serviceWorker.addEventListener('message', async event => {
      if (event.data.type === 'obq_sync_finished') {
        const index = this.selectedPortOBQTankDetails?.findIndex((item) => item.storeKey === event.data.storeKey);
        if (index !== -1) {
          this.selectedPortOBQTankDetails[index].id = event.data.id;
          this.selectedPortOBQTankDetails = [...this.selectedPortOBQTankDetails];
        }
        this.updateTankList()
      }
    });
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
        tank.commodity = {
          quantity: selectedPortOBQTankDetail.quantity?.value,
          volume: selectedPortOBQTankDetail?.volume ?? 0,
          colorCode: selectedPortOBQTankDetail?.colorCode,
          api: selectedPortOBQTankDetail?.api?.value 
        }
        return tank;
      });
      return newGroup;
    })
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
    if (this.obqForm.valid || (controls.api.valid && controls.quantity.valid)) {
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
    this.loadableStudy.loadOnTop = event.target.checked;
    this.ngxSpinnerService.show();
    const translationKeys = await this.translateService.get(['LOADABLE_STUDY_LOAD_ON_TOP_SAVE_SUCCESS', 'LOADABLE_STUDY_LOAD_ON_TOP_SAVE_SUCCESS_DETAIL']).toPromise();
    const res = await this.loadableStudyDetailsApiService.saveLoadableStudyLoadOnTop(this.vesselId, this.voyageId, this.loadableStudyId, { isLoadOnTop: event.target.checked }).toPromise();
    if (res?.responseStatus?.status === "200") {
      this.messageService.add({ severity: 'success', summary: translationKeys['LOADABLE_STUDY_LOAD_ON_TOP_SAVE_SUCCESS'], detail: translationKeys['LOADABLE_STUDY_LOAD_ON_TOP_SAVE_SUCCESS_DETAIL'] });
    }
    this.ngxSpinnerService.hide();
  }

  /**
   * Converting obq quantities in selected unit
   *
   * @memberof OnBoardQuantityComponent
   */
  convertSelectedPortOBQTankDetails() {
    const _selectedPortOBQTankDetails = this.selectedPortOBQTankDetails?.map(obqTankDetail => {
      if (obqTankDetail.api.value) {
        const _prevQuantitySelectedUnit = this._prevQuantitySelectedUnit ?? AppConfigurationService.settings.baseUnit;
        if (_prevQuantitySelectedUnit !== this.quantitySelectedUnit) {
          obqTankDetail.quantity.value = this.quantityPipe.transform(obqTankDetail.quantity.value, _prevQuantitySelectedUnit, this.quantitySelectedUnit, obqTankDetail.api.value);
          obqTankDetail.quantity.value = obqTankDetail.quantity.value ? Number(obqTankDetail.quantity.value.toFixed(2)) : 0;
          const volume = this.quantityPipe.transform(obqTankDetail.quantity?.value, this.quantitySelectedUnit, AppConfigurationService.settings.volumeBaseUnit, obqTankDetail?.api?.value);
          obqTankDetail.volume = volume ?? 0;
        }
        const _prevFullcapacitySelectedUnit = this._prevQuantitySelectedUnit ?? AppConfigurationService.settings.volumeBaseUnit;
        if (_prevFullcapacitySelectedUnit !== this.quantitySelectedUnit) {
          const fullCapacity = this.quantityPipe.transform(obqTankDetail.fullCapacityCubm, _prevFullcapacitySelectedUnit, this.quantitySelectedUnit, obqTankDetail.api.value);
          obqTankDetail.fullCapacity = fullCapacity ?? 0;
        }
      }

      return obqTankDetail;
    });
    this.selectedPortOBQTankDetails = [..._selectedPortOBQTankDetails];
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
    _selectedPortOBQTankDetail.quantity = this.quantityPipe.transform(_selectedPortOBQTankDetail?.quantity, this.quantitySelectedUnit, QUANTITY_UNIT?.MT, _selectedPortOBQTankDetail?.api);
    return _selectedPortOBQTankDetail;
  }

}

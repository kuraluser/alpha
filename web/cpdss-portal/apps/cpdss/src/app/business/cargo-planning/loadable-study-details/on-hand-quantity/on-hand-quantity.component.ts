import { Component, Input, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { DATATABLE_EDITMODE, DATATABLE_SELECTIONMODE, IDataTableColumn, IDataTableFilterEvent, IDataTableSortEvent } from '../../../../shared/components/datatable/datatable.model';
import { NgxSpinnerService } from 'ngx-spinner';
import { IPortOHQTankDetailEvent, IPort, IPortOHQListData, IPortOHQTankDetailValueObject, OHQ_MODE, IBunkerTank } from '../../models/cargo-planning.model';
import { LoadableStudyDetailsApiService } from '../../services/loadable-study-details-api.service';
import { LoadableStudyDetailsTransformationService } from '../../services/loadable-study-details-transformation.service';
import { numberValidator } from '../../directives/validator/number-validator.directive';
import { groupTotalValidator } from '../../directives/validator/group-total.directive';
import { IPermission } from '../../../../shared/models/user-profile.model';

/**
 * Compoent for OHQ tab
 *
 * @export
 * @class OnHandQuantityComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-on-hand-quantity',
  templateUrl: './on-hand-quantity.component.html',
  styleUrls: ['./on-hand-quantity.component.scss']
})
export class OnHandQuantityComponent implements OnInit {

  @Input() voyageId: number;

  @Input()
  get loadableStudyId() {
    return this._loadableStudyId;
  }
  set loadableStudyId(value: number) {
    this._loadableStudyId = value;
    this.ohqForm = null;
    this.getPortRotation();
  }

  @Input() vesselId: number;

  @Input() permission: IPermission;

  get selectedPortOHQTankDetails() {
    return this._selectedPortOHQTankDetails;
  }

  set selectedPortOHQTankDetails(selectedPortOHQTankDetails: IPortOHQTankDetailValueObject[]) {
    this._selectedPortOHQTankDetails = selectedPortOHQTankDetails.map((ohqTankDetail, index) => {
      const _ohqTankDetail = this.loadableStudyDetailsTransformationService.formatOHQTankDetail(ohqTankDetail);
      return _ohqTankDetail;
    });
    this.loadableStudyDetailsTransformationService.setOHQValidity(this.ohqForm.valid && this.ohqGroupValidity(this._selectedPortOHQTankDetails));
  }

  get tanks(): IBunkerTank[][] {
    return this._tanks;
  }

  set tanks(tanks: IBunkerTank[][]) {
    this._tanks = this.getTankFuelMapping(tanks, this.selectedPortOHQTankDetails, this.ohqForm.get('mode').value);
  }

  get rearTanks(): IBunkerTank[][] {
    return this._rearTanks;
  }

  set rearTanks(rearTanks: IBunkerTank[][]) {
    this._rearTanks = this.getTankFuelMapping(rearTanks, this.selectedPortOHQTankDetails, this.ohqForm.get('mode').value);
  }

  get selectedTankId(): number {
    return this._selectedTankId;
  }

  set selectedTankId(tankId: number) {
    this._selectedTankId = tankId;
    this.selectedTank = this.selectedPortOHQTankDetails.find(ohqDetails => ohqDetails?.tankId === tankId);
    this.selectedTankFormGroupIndex = this.ohqForm?.controls?.dataTable?.value?.findIndex(row => row?.tankId === tankId);
    this.selectedTankFormGroup = this.row(this.selectedTankFormGroupIndex);
    if (this.selectedTank) {
      this.setFillingPercentage(this.selectedTankId);
    }
  }

  get selectedTank(): IPortOHQTankDetailValueObject {
    return this._selectedTank;
  }

  set selectedTank(selectedTank: IPortOHQTankDetailValueObject) {
    this._selectedTank = selectedTank;
  }

  get ohqForm(): FormGroup {
    return this._ohqForm;
  }

  set ohqForm(ohqForm: FormGroup) {
    this._ohqForm = ohqForm;
  }

  readonly OHQ_MODE = OHQ_MODE;
  readonly selectionMode = DATATABLE_SELECTIONMODE.SINGLE;
  editMode: DATATABLE_EDITMODE | boolean;
  ohqPorts: IPort[];
  selectedPort: IPort;
  ports: IPort[];
  columns: IDataTableColumn[];
  listData = <IPortOHQListData>{};
  selectedTankFormGroup: FormGroup;
  selectedTankFormGroupIndex: number;
  selectedTankStoreKey: number;

  private _selectedPortOHQTankDetails: IPortOHQTankDetailValueObject[];
  private _loadableStudyId: number;
  private _tanks: IBunkerTank[][];
  private _rearTanks: IBunkerTank[][];
  private _selectedTankId: number;
  private _selectedTank: IPortOHQTankDetailValueObject;
  private _ohqForm: FormGroup;


  constructor(private loadableStudyDetailsApiService: LoadableStudyDetailsApiService,
    private ngxSpinnerService: NgxSpinnerService,
    private loadableStudyDetailsTransformationService: LoadableStudyDetailsTransformationService,
    private fb: FormBuilder) { }

  /**
   * NgOnit init function for ohq component
   *
   * @memberof OnHandQuantityComponent
   */
  ngOnInit(): void {
    this.editMode = this.permission?.edit === undefined || this.permission?.edit ? DATATABLE_EDITMODE.CELL : false;
    this.columns = this.loadableStudyDetailsTransformationService.getOHQDatatableColumns();
    this.initSubscriptions();
  }

  /**
   * Method for  fetching ohq port rotation
   *
   * @memberof OnHandQuantityComponent
   */
  async getPortRotation() {
    this.ngxSpinnerService.show();
    this.ports = await this.loadableStudyDetailsApiService.getPorts().toPromise();
    const result = await this.loadableStudyDetailsApiService.getOHQPortRotation(this.vesselId, this.voyageId, this.loadableStudyId).toPromise();
    if (result?.portList) {
      this.ohqPorts = result?.portList?.map((ohqPort) => this.ports?.find((port) => port.id === ohqPort.portId));
      this.selectedPort = this.ohqPorts[0];
      await this.getPortOHQDetails(this.selectedPort?.id);
    }
    this.ngxSpinnerService.hide();
  }

  /**
   * Method for fetching ohq details of selected port
   *
   * @param {number} portId
   * @memberof OnHandQuantityComponent
   */
  async getPortOHQDetails(portId: number) {
    const result = await this.loadableStudyDetailsApiService.getPortOHQDetails(this.vesselId, this.voyageId, this.loadableStudyId, portId).toPromise();
    const selectedPortOHQTankDetails = result?.onHandQuantities ?? [];
    this.listData.fuelTypes = [...new Map(selectedPortOHQTankDetails.map(item => [item['fuelTypeId'], { id: item?.fuelTypeId, name: item?.fuelTypeName, colorCode: item?.colorCode, shortName: item?.fuelTypeShortName }])).values()];
    const _selectedPortOHQTankDetails = selectedPortOHQTankDetails?.map((ohqTankDetail) => {
      ohqTankDetail.portId = portId;
      [...result?.tanks, ...result?.rearTanks].forEach(group => group.find(tank => {
        if (tank.id === ohqTankDetail.tankId) {
          ohqTankDetail.fullCapacityCubm = tank.fullCapacityCubm;
        }
      }));
      const _ohqTankDetail = this.loadableStudyDetailsTransformationService.getOHQTankDetailsAsValueObject(ohqTankDetail, false);
      return _ohqTankDetail;
    });
    const ohqTankDetailsArray = _selectedPortOHQTankDetails?.map(ohqTankDetails => this.initOHQFormGroup(ohqTankDetails));
    this.ohqForm = this.fb.group({
      dataTable: this.fb.array([...ohqTankDetailsArray]),
      mode: this.fb.control(OHQ_MODE.ARRIVAL)
    });
    this.selectedPortOHQTankDetails = [..._selectedPortOHQTankDetails];
    this.tanks = result?.tanks ?? [];
    this.rearTanks = result?.rearTanks ?? [];
  }

  /**
   * Method to map tank and fuel details
   *
   * @param {IBunkerTank[][]} tanks
   * @param {IPortOHQTankDetailValueObject[]} selectedPortOHQTankDetails
   * @param {OHQ_MODE} mode
   * @returns {IBunkerTank[][]}
   * @memberof OnHandQuantityComponent
   */
  getTankFuelMapping(tanks: IBunkerTank[][], selectedPortOHQTankDetails: IPortOHQTankDetailValueObject[], mode: OHQ_MODE): IBunkerTank[][] {
    for (let groupIndex = 0; groupIndex < tanks.length; groupIndex++) {
      for (let tankIndex = 0; tankIndex < tanks[groupIndex].length; tankIndex++) {
        for (let index = 0; index < selectedPortOHQTankDetails.length; index++) {
          if (selectedPortOHQTankDetails[index]?.tankId === tanks[groupIndex][tankIndex]?.id) {
            tanks[groupIndex][tankIndex].commodity = selectedPortOHQTankDetails[index];
            tanks[groupIndex][tankIndex].commodity.quantity = mode === OHQ_MODE.ARRIVAL ? tanks[groupIndex][tankIndex]?.commodity?.arrivalQuantity?.value : tanks[groupIndex][tankIndex]?.commodity?.departureQuantity?.value;
            tanks[groupIndex][tankIndex].commodity.volume = mode === OHQ_MODE.ARRIVAL ? tanks[groupIndex][tankIndex]?.commodity?.arrivalVolume?.value : tanks[groupIndex][tankIndex]?.commodity?.departureVolume?.value;
            break;
          }
        }
      }
    }
    return tanks;
  }

  /**
   * Method for hadling the port selection
   *
   * @param {IPort} port
   * @memberof OnHandQuantityComponent
   */
  async onPortSelection(port: IPort) {
    this.ngxSpinnerService.show();
    this.selectedPort = port;
    await this.getPortOHQDetails(this.selectedPort?.id);
    this.ngxSpinnerService.hide();
  }

  /**
   * Method for initializing onq row
   *
   * @private
   * @param {IPortOHQTankDetailValueObject} ohqTankDetail
   * @returns
   * @memberof OnHandQuantityComponent
   */
  private initOHQFormGroup(ohqTankDetail: IPortOHQTankDetailValueObject) {
    return this.fb.group({
      fuelTypeId: this.fb.control(ohqTankDetail.fuelTypeId),
      fuelTypeName: this.fb.control(ohqTankDetail.fuelTypeName, Validators.required),
      tankName: this.fb.control(ohqTankDetail.tankName, Validators.required),
      tankId: this.fb.control(ohqTankDetail.tankId, Validators.required),
      arrivalVolume: this.fb.control(ohqTankDetail.arrivalVolume.value, [Validators.required, numberValidator(2, 7, false), groupTotalValidator('arrivalVolume', 'fuelTypeId'), Validators.max(Number(ohqTankDetail?.fullCapacityCubm))]),
      arrivalQuantity: this.fb.control(ohqTankDetail.arrivalQuantity.value, [Validators.required, numberValidator(2, 7, false), groupTotalValidator('arrivalQuantity', 'fuelTypeId')]),
      departureVolume: this.fb.control(ohqTankDetail.departureVolume.value, [Validators.required, numberValidator(2, 7, false), groupTotalValidator('departureVolume', 'fuelTypeId'), Validators.max(Number(ohqTankDetail?.fullCapacityCubm))]),
      departureQuantity: this.fb.control(ohqTankDetail.departureQuantity.value, [Validators.required, numberValidator(2, 7, false), groupTotalValidator('departureQuantity', 'fuelTypeId')]),
    });
  }

  /**
   * Method to handle edit complete event
   *
   * @param {IPortOHQTankDetailEvent} event
   * @memberof OnHandQuantityComponent
   */
  async onEditComplete(event: IPortOHQTankDetailEvent) {
    this.ngxSpinnerService.show();
    const fromGroup = this.row(event.index);
    //TODO: START need to be removed after latest validation

    let dependentKey;
    switch (event?.field) {
      case 'arrivalVolume':
        dependentKey = 'arrivalQuantity'
        break;
      case 'arrivalQuantity':
        dependentKey = 'arrivalVolume'
        break;
      case 'departureQuantity':
        dependentKey = 'departureVolume'
        break;
      case 'departureVolume':
        dependentKey = 'departureQuantity'
        break;

      default:
        break;
    }
    if (dependentKey) {
      if (event?.data[event?.field].value) {
        const formControl = this.field(event?.index, dependentKey);
        if (!formControl.value) {
          formControl.setValue(null);
        }
      } else {
        const formControl = this.field(event?.index, event?.field);
        if (!event?.data[dependentKey].value) {
          formControl.setValue(null);
        }
      }
    }
    //TODO: END need to be removed after latest validation

    this.loadableStudyDetailsTransformationService.setOHQValidity(this.ohqForm.valid);
    const valueIndex = this.selectedPortOHQTankDetails.findIndex(ohqDetails => ohqDetails?.storeKey === event?.data?.storeKey);
    if (fromGroup.valid) {
      const res = await this.loadableStudyDetailsApiService.setOHQTankDetails(this.loadableStudyDetailsTransformationService.getOHQTankDetailAsValue(this.selectedPortOHQTankDetails[valueIndex]), this.vesselId, this.voyageId, this.loadableStudyId);
      if (res) {
        for (const key in this.selectedPortOHQTankDetails[valueIndex]) {
          if (this.selectedPortOHQTankDetails[valueIndex]?.hasOwnProperty(key) && this.selectedPortOHQTankDetails[valueIndex][key]?.hasOwnProperty('_isEditMode')) {
            this.selectedPortOHQTankDetails[valueIndex][key].isEditMode = false;
          }
        }
        this.selectedPortOHQTankDetails = [...this.selectedPortOHQTankDetails];
      }

    } else {
      for (const key in this.selectedPortOHQTankDetails[valueIndex]) {
        if (this.selectedPortOHQTankDetails[valueIndex].hasOwnProperty(key) && this.selectedPortOHQTankDetails[valueIndex][key]?.hasOwnProperty('_isEditMode')) {
          const formControl = this.field(event.index, key);
          formControl.updateValueAndValidity();
          this.selectedPortOHQTankDetails[valueIndex][key].isEditMode = formControl.invalid;
        }
      }
      fromGroup.markAllAsTouched();
      this.ohqForm.updateValueAndValidity();
    }

    if (this.selectedTankId) {
      this.setFillingPercentage(this.selectedTankId);
      this.selectedTankFormGroup.get(event?.field).setValue((event?.data[event?.field]).value);
    }
    const formArray = (<FormArray>this.ohqForm.get('dataTable')).controls;
    formArray.forEach(async (row: FormGroup, index) => {
      if (row.invalid && row.touched) {
        const invalidFormControls = this.findInvalidControlsRecursive(row);
        invalidFormControls.forEach((key) => {
          const formControl = this.field(index, key);
          formControl.updateValueAndValidity();
        });
        if (row.valid) {
          const res = await this.loadableStudyDetailsApiService.setOHQTankDetails(this.loadableStudyDetailsTransformationService.getOHQTankDetailAsValue(this.selectedPortOHQTankDetails[valueIndex]), this.vesselId, this.voyageId, this.loadableStudyId);
          if (res) {
            for (const key in this.selectedPortOHQTankDetails[valueIndex]) {
              if (this.selectedPortOHQTankDetails[valueIndex].hasOwnProperty(key) && this.selectedPortOHQTankDetails[valueIndex][key]?.hasOwnProperty('_isEditMode')) {
                const formControl = this.field(index, key);
                this.selectedPortOHQTankDetails[valueIndex][key].isEditMode = formControl.invalid;
              }
            }
            this.selectedPortOHQTankDetails = [...this.selectedPortOHQTankDetails];
          }
        }
      }
    });
    this.tanks = [...this.tanks];
    this.rearTanks = [...this.rearTanks];
    this.ngxSpinnerService.hide();
  }

  /**
   * Initialization for all subscriptions
   *
   * @private
   * @memberof OnHandQuantityComponent
   */
  private initSubscriptions() {
    navigator.serviceWorker.addEventListener('message', async event => {
      if (event.data.type === 'ohq_sync_finished') {
        const index = this.selectedPortOHQTankDetails?.findIndex((item) => item.storeKey === event.data.storeKey);
        if (index !== -1) {
          this.selectedPortOHQTankDetails[index].id = event.data.id;
          this.selectedPortOHQTankDetails = [...this.selectedPortOHQTankDetails];
        }
      }
    });
  }

  /**
   * Method for fetching form group
   *
   * @private
   * @param {number} formGroupIndex
   * @returns {FormGroup}
   * @memberof OnHandQuantityComponent
   */
  private row(formGroupIndex: number): FormGroup {
    const formGroup = <FormGroup>(<FormArray>this.ohqForm.get('dataTable')).at(formGroupIndex);
    return formGroup;
  }

  /**
   * Get form control of form 
   *
   * @param {number} formGroupIndex
   * @param {string} formControlName
   * @returns {FormControl}
   * @memberof OnHandQuantityComponent
   */
  field(formGroupIndex: number, formControlName: string): FormControl {
    const formControl = <FormControl>(<FormArray>this.ohqForm.get('dataTable'))?.at(formGroupIndex)?.get(formControlName);
    return formControl;
  }

  /**
   * Method get all invalid fields in a row
   *
   * @private
   * @param {FormGroup} formToInvestigate
   * @returns {string[]}
   * @memberof OnHandQuantityComponent
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
   *  Handler for change of mode
   *
   * @param {*} event
   * @memberof OnHandQuantityComponent
   */
  onModeChange(mode: OHQ_MODE) {
    this.tanks = [...this.tanks];
    this.rearTanks = [...this.rearTanks];
  }

  /**
   * Handler for on row selection of ohq grid
   *
   * @param {IPortOHQTankDetailEvent} event
   * @memberof OnHandQuantityComponent
   */
  onRowSelection(event: IPortOHQTankDetailEvent) {
    if (event?.data?.fuelTypeId !== 19) {
      this.selectedTankStoreKey = event?.data?.storeKey;
      this.selectedTank = event?.data;
      this.selectedTankId = this.selectedTank?.tankId;
    } else {
      this.selectedTankStoreKey = null;
      this.selectedTank = null;
      this.selectedTankId = null;
    }
  }

  /**
   * Set filling percentage label value
   *
   * @param {number} selectedTankId
   * @memberof OnHandQuantityComponent
   */
  setFillingPercentage(selectedTankId: number) {
    const tanks = [...this.tanks, ...this.rearTanks];
    for (let index = 0; index < tanks.length; index++) {
      const selectedTank = tanks[index]?.find(tank => tank.id === selectedTankId);
      if (selectedTank) {
        this.selectedTank.percentageFilled = selectedTank?.percentageFilled ?? '';
        break;
      }
    }
  }


  /**
   * Handler for change event of fields of tank layout form
   *
   * @param {*} event
   * @param {number} selectedTankFormGroupIndex
   * @param {string} field
   * @memberof OnHandQuantityComponent
   */
  onChange(event, selectedTankFormGroupIndex: number, field: string) {
    this.selectedTank[field].value = event?.target.value;
    if (this.selectedTankFormGroup.valid) {
      this.onEditComplete({ originalEvent: event, data: this.selectedTank, field: field, index: selectedTankFormGroupIndex });
    } else {
      this.selectedTankFormGroup.markAllAsTouched();
      this.ohqForm.updateValueAndValidity();
    }
  }

  /**
   * Get field errors
   *
   * @param {number} formGroupIndex
   * @param {string} formControlName
   * @returns {ValidationErrors}
   * @memberof OnHandQuantityComponent
   */
  fieldError(formGroupIndex: number, formControlName: string): ValidationErrors {
    const formControl = this.field(formGroupIndex, formControlName);
    return (this.permission?.edit === undefined || this.permission?.edit) && formControl?.invalid && (formControl?.dirty || formControl?.touched) ? formControl?.errors : null;
  }

  /**
   *  Fetch error messages for fields
   *
   * @param {string} field
   * @memberof OnHandQuantityComponent
   */
  getErrorMessages(field: string) {
    return this.columns?.find(column => column?.columns?.find(subColumn => subColumn?.field === field))?.columns?.find(subColumn => subColumn?.field === field)?.errorMessages;
  }

  /**
   * Handler for filter event
   *
   * @param {IDataTableFilterEvent} event
   * @memberof OnHandQuantityComponent
   */
  onFilter(event: IDataTableFilterEvent) {
    this.ngxSpinnerService.show();
    const ohqTankDetailsArray = event?.filteredValue?.map(ohqTankDetails => this.initOHQFormGroup(ohqTankDetails));
    this.ohqForm.controls.dataTable = this.fb.array([...ohqTankDetailsArray]);
    this.ngxSpinnerService.hide();
  }

  /**
   * Handler for datatable sort event
   *
   * @param {IDataTableSortEvent} event
   * @memberof OnHandQuantityComponent
   */
  onSort(event: IDataTableSortEvent) {
    this.ngxSpinnerService.show();
    const ohqTankDetailsArray = event?.data?.map(ohqTankDetails => this.initOHQFormGroup(ohqTankDetails));
    this.ohqForm.controls.dataTable = this.fb.array([...ohqTankDetailsArray]);
    this.ngxSpinnerService.hide();
  }

  /**
   * Method returns true if at least one value in the ohq grid of each group has a value
   *
   * @param {IPortOHQTankDetailValueObject[]} selectedPortOHQTankDetails
   * @returns {boolean}
   * @memberof OnHandQuantityComponent
   */
  ohqGroupValidity(selectedPortOHQTankDetails: IPortOHQTankDetailValueObject[]): boolean {
    const key = ['arrivalVolume', 'arrivalQuantity', 'departureVolume', 'departureQuantity'];
    for (let index = 0; index < this.listData.fuelTypes.length; index++) {
      for (let i = 0; i < key.length; i++) {
        const groupId = this.listData.fuelTypes[index].id;
        const total = selectedPortOHQTankDetails?.reduce((a, b) => a + (b.fuelTypeId === groupId ? b[key[i]]?.value || 0 : 0), 0);
        if (!total) {
          return false;
        }
      }
    }
    return true;
  }

}

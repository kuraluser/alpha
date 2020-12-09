import { Component, Input, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { DATATABLE_EDITMODE, IDataTableColumn } from '../../../../shared/components/datatable/datatable.model';
import { NgxSpinnerService } from 'ngx-spinner';
import { IPortOHQTankDetailEvent, IPort, IPortOHQListData, IPortOHQTankDetailValueObject } from '../../models/cargo-planning.model';
import { LoadableStudyDetailsApiService } from '../../services/loadable-study-details-api.service';
import { LoadableStudyDetailsTransformationService } from '../../services/loadable-study-details-transformation.service';
import { numberValidator } from '../../directives/validator/number-validator.directive';
import { groupTotalValidator } from '../../directives/validator/group-total.directive';

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

  get selectedPortOHQTankDetails() {
    return this._selectedPortOHQTankDetails;
  }

  set selectedPortOHQTankDetails(selectedPortOHQTankDetails: IPortOHQTankDetailValueObject[]) {
    this._selectedPortOHQTankDetails = selectedPortOHQTankDetails.map((ohqTankDetail, index) => {
      const _ohqTankDetail = this.loadableStudyDetailsTransformationService.formatOHQTankDetail(ohqTankDetail);
      _ohqTankDetail.slNo = index + 1;
      return _ohqTankDetail;
    })
  }

  readonly editMode = DATATABLE_EDITMODE.CELL;
  ohqPorts: IPort[];
  selectedPort: IPort;
  ports: IPort[];
  ohqForm: FormGroup;
  columns: IDataTableColumn[];
  listData = <IPortOHQListData>{};

  private _selectedPortOHQTankDetails: IPortOHQTankDetailValueObject[];
  private _loadableStudyId: number;


  constructor(private loadableStudyDetailsApiService: LoadableStudyDetailsApiService,
    private ngxSpinnerService: NgxSpinnerService,
    private loadableStudyDetailsTransformationService: LoadableStudyDetailsTransformationService,
    private fb: FormBuilder,) { }

  /**
   * NgOnit init function for ohq component
   *
   * @memberof OnHandQuantityComponent
   */
  ngOnInit(): void {
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
      this.selectedPortOHQTankDetails = await this.getPortOHQDetails(this.selectedPort?.id);
    }
    this.ngxSpinnerService.hide();
  }

  /**
   * Method for fetching ohq details of selected port
   *
   * @param {number} portId
   * @memberof OnHandQuantityComponent
   */
  async getPortOHQDetails(portId: number): Promise<IPortOHQTankDetailValueObject[]> {
    const result = await this.loadableStudyDetailsApiService.getPortOHQDetails(this.vesselId, this.voyageId, this.loadableStudyId, portId).toPromise();
    const selectedPortOHQTankDetails = result?.onHandQuantities ?? [];
    this.listData.fuelTypes = [...new Map(selectedPortOHQTankDetails.map(item =>
      [item['fuelTypeId'], { id: item.fuelTypeId, name: item.fuelTypeName }])).values()];
    const _selectedPortOHQTankDetails = selectedPortOHQTankDetails?.map((ohqTankDetail) => {
      ohqTankDetail.portId = portId;
      const _ohqTankDetail = this.loadableStudyDetailsTransformationService.getOHQTankDetailsAsValueObject(ohqTankDetail, false);
      return _ohqTankDetail;
    });
    const ohqTankDetailsArray = _selectedPortOHQTankDetails?.map(ohqTankDetails => this.initOHQFormGroup(ohqTankDetails));
    this.ohqForm = this.fb.group({
      dataTable: this.fb.array([...ohqTankDetailsArray])
    });
    return [..._selectedPortOHQTankDetails];
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
    this.selectedPortOHQTankDetails = await this.getPortOHQDetails(this.selectedPort?.id);
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
      arrivalVolume: this.fb.control(ohqTankDetail.arrivalVolume.value, [Validators.required, Validators.min(0), numberValidator(2, 7), groupTotalValidator('arrivalVolume', 'fuelTypeId')]),
      arrivalQuantity: this.fb.control(ohqTankDetail.arrivalQuantity.value, [Validators.required, Validators.min(0), numberValidator(2, 7), groupTotalValidator('arrivalQuantity', 'fuelTypeId')]),
      departureVolume: this.fb.control(ohqTankDetail.departureVolume.value, [Validators.required, Validators.min(0), numberValidator(2, 7), groupTotalValidator('departureVolume', 'fuelTypeId')]),
      departureQuantity: this.fb.control(ohqTankDetail.departureQuantity.value, [Validators.required, Validators.min(0), numberValidator(2, 7), groupTotalValidator('departureQuantity', 'fuelTypeId')]),
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
    if (fromGroup.valid) {
      const res = await this.loadableStudyDetailsApiService.setOHQTankDetails(this.loadableStudyDetailsTransformationService.getOHQTankDetailAsValue(this.selectedPortOHQTankDetails[event.index]), this.vesselId, this.voyageId, this.loadableStudyId);
      if (res) {
        for (const key in this.selectedPortOHQTankDetails[event.index]) {
          if (this.selectedPortOHQTankDetails[event.index].hasOwnProperty(key) && this.selectedPortOHQTankDetails[event.index][key].hasOwnProperty('_isEditMode')) {
            this.selectedPortOHQTankDetails[event.index][key].isEditMode = false;
          }
        }
        this.selectedPortOHQTankDetails = [...this.selectedPortOHQTankDetails];
      }

    } else {
      for (const key in this.selectedPortOHQTankDetails[event.index]) {
        if (this.selectedPortOHQTankDetails[event.index].hasOwnProperty(key) && this.selectedPortOHQTankDetails[event.index][key].hasOwnProperty('_isEditMode')) {
          const formControl = this.field(event.index, key);
          formControl.updateValueAndValidity();
          this.selectedPortOHQTankDetails[event.index][key].isEditMode = formControl.invalid;
        }
      }
      fromGroup.markAllAsTouched();
      this.ohqForm.updateValueAndValidity();
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
          const res = await this.loadableStudyDetailsApiService.setOHQTankDetails(this.loadableStudyDetailsTransformationService.getOHQTankDetailAsValue(this.selectedPortOHQTankDetails[index]), this.vesselId, this.voyageId, this.loadableStudyId);
          if (res) {
            for (const key in this.selectedPortOHQTankDetails[index]) {
              if (this.selectedPortOHQTankDetails[index].hasOwnProperty(key) && this.selectedPortOHQTankDetails[index][key].hasOwnProperty('_isEditMode')) {
                const formControl = this.field(index, key);
                this.selectedPortOHQTankDetails[index][key].isEditMode = formControl.invalid;
              }
            }
          }
        }
      }
    });
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
    const formControl = <FormControl>(<FormArray>this.ohqForm.get('dataTable')).at(formGroupIndex).get(formControlName);
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

}

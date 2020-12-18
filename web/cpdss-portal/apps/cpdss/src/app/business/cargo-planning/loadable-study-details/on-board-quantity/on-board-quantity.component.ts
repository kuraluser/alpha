import { Component, Input, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { DATATABLE_EDITMODE, DATATABLE_SELECTIONMODE, IDataTableColumn } from '../../../../shared/components/datatable/datatable.model';
import { NgxSpinnerService } from 'ngx-spinner';
import { groupTotalValidator } from '../../directives/validator/group-total.directive';
import { numberValidator } from '../../directives/validator/number-validator.directive';
import { IPort, IPortOBQListData, IPortOBQTankDetailValueObject } from '../../models/cargo-planning.model';
import { LoadableStudyDetailsApiService } from '../../services/loadable-study-details-api.service';
import { LoadableStudyDetailsTransformationService } from '../../services/loadable-study-details-transformation.service';
import { CommingleApiService } from '../../services/commingle-api.service';

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

  get selectedPortOBQTankDetails() {
    return this._selectedPortOBQTankDetails;
  }

  set selectedPortOBQTankDetails(selectedPortOBQTankDetails: IPortOBQTankDetailValueObject[]) {
    this._selectedPortOBQTankDetails = selectedPortOBQTankDetails.map((obqTankDetail, index) => {
      const _obqTankDetail = this.loadableStudyDetailsTransformationService.formatOBQTankDetail(obqTankDetail);
      _obqTankDetail.slNo = index + 1;
      return _obqTankDetail;
    })
  }

  selectedPort: IPort;
  obqForm: FormGroup;
  columns: IDataTableColumn[];
  listData = <IPortOBQListData>{};
  selectionMode = DATATABLE_SELECTIONMODE.SINGLE;
  selectedIndex: number = -1;

  private _loadableStudyId: number;
  private _selectedPortOBQTankDetails: IPortOBQTankDetailValueObject[];
  readonly editMode = DATATABLE_EDITMODE.CELL;




  constructor(private loadableStudyDetailsApiService: LoadableStudyDetailsApiService,
    private ngxSpinnerService: NgxSpinnerService,
    private loadableStudyDetailsTransformationService: LoadableStudyDetailsTransformationService,
    private commingleApiService: CommingleApiService,
    private fb: FormBuilder,) { }

  ngOnInit(): void {
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
      this.selectedPortOBQTankDetails = await this.getPortOBQDetails(this.selectedPort?.id);
    }
    this.ngxSpinnerService.hide();
  }

  /**
 * Method for fetching obq details of selected port
 *
 * @param {number} portId
 * @memberof OnBoardQuantityComponent
 */
  async getPortOBQDetails(portId: number): Promise<IPortOBQTankDetailValueObject[]> {
    this.listData = await this.getDropdownData();
    const result = await this.loadableStudyDetailsApiService.getPortOBQDetails(this.vesselId, this.voyageId, this.loadableStudyId, portId).toPromise();
    const selectedPortOBQTankDetails = result?.onBoardQuantities ?? [];
    const _selectedPortOBQTankDetails = selectedPortOBQTankDetails?.map((obqTankDetail) => {
      obqTankDetail.portId = portId;
      const _obqTankDetail = this.loadableStudyDetailsTransformationService.getOBQTankDetailsAsValueObject(obqTankDetail, false, this.listData);
      return _obqTankDetail;
    });
    const obqTankDetailsArray = _selectedPortOBQTankDetails?.map(obqTankDetails => this.initOBQFormGroup(obqTankDetails));
    this.obqForm = this.fb.group({
      dataTable: this.fb.array([...obqTankDetailsArray]),
      cargo: new FormControl('', Validators.required),
      sounding: this.fb.control('', [Validators.required, Validators.min(0), numberValidator(2, 4)]),
      volume: this.fb.control('', [Validators.required, Validators.min(0), numberValidator(2, 7)]),
      weight: this.fb.control('', [Validators.required, Validators.min(0), numberValidator(2, 7)]),
    });
    this.enableOrDisableControls(this.obqForm, ['sounding', 'weight', 'volume'], false);
    return [..._selectedPortOBQTankDetails];
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
      sounding: this.fb.control(obqTankDetail.sounding.value, [Validators.required, Validators.min(0), numberValidator(2, 4)]),
      volume: this.fb.control(obqTankDetail.volume.value, [Validators.required, Validators.min(0), numberValidator(2, 7)]),
      weight: this.fb.control(obqTankDetail.weight.value, [Validators.required, Validators.min(0), numberValidator(2, 7)]),
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
    if (formGroup.valid) {
      const tankDetails = this.loadableStudyDetailsTransformationService.getOBQTankDetailAsValue(this.selectedPortOBQTankDetails[event.index]);
      const res = await this.loadableStudyDetailsApiService.setOBQTankDetails(tankDetails, this.vesselId, this.voyageId, this.loadableStudyId);
    } else{
      Object.keys(formGroup.controls).forEach(key => {
        const control = formGroup.get(key);
        if(control.invalid){
          this.selectedPortOBQTankDetails[event.index][key].isEditMode = true;
          control.markAsTouched()
        }
      });
    }
    this.ngxSpinnerService.hide();
  }

  /**
   * Method to handle row selection event
   *
   * @param {IPortOBQTankDetailEvent} event
   * @memberof OnBoardQuantityComponent
   */
  onRowSelection(event) {
    this.enableOrDisableControls(this.obqForm, ['sounding', 'weight', 'volume']);
    const data = event.data
    this.selectedIndex = event.index
    this.obqForm.controls.cargo.setValue(data.cargo.value?.name)
    this.obqForm.controls.sounding.setValue(data.sounding.value)
    this.obqForm.controls.weight.setValue(data.weight.value)
    this.obqForm.controls.volume.setValue(data.volume.value)
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
      }
    });
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
        const index = this.selectedPortOBQTankDetails?.findIndex((item) => item.storeKey === event.data.storeKey);
        if (index !== -1) {
          this.selectedPortOBQTankDetails[index].id = event.data.id;
          this.selectedPortOBQTankDetails = [...this.selectedPortOBQTankDetails];
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
    const formGroup = <FormGroup>(<FormArray>this.obqForm.get('dataTable')).at(formGroupIndex);
    return formGroup;
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
    return formControl.invalid && (formControl.dirty || formControl.touched) ? formControl.errors : null;
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
    const formControl = <FormControl>this.obqForm.get(formControlName);
    return formControl;
  }

  /**
   *  Fetch error messages for fields
   *
   * @param {string} field
   * @memberof OnHandQuantityComponent
   */
  getErrorMessages(field: string) {
    const column = this.columns.find(column => {
     return column.field === field
    });
    return column?.errorMessages;
  }
  
}

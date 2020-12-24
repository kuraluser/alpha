import { Component, Input, OnInit } from '@angular/core';
import { LoadableStudyDetailsApiService } from '../../services/loadable-study-details-api.service';
import { LoadableStudyDetailsTransformationService } from '../../services/loadable-study-details-transformation.service';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { IPortAllDropdownData, IPortList, IPortsDetailsResponse, IPortsValueObject, IPortsEvent } from '../../models/cargo-planning.model';
import { DATATABLE_EDITMODE, IDataTableColumn, IDataTableFilterEvent, IDataTableSortEvent } from '../../../../shared/components/datatable/datatable.model';
import { numberValidator } from '../../directives/validator/number-validator.directive';
import { NgxSpinnerService } from 'ngx-spinner';
import { ConfirmationAlertService } from '../../../../shared/components/confirmation-alert/confirmation-alert.service';
import { OPERATIONS } from '../../models/cargo-planning.model';
import { IPermission } from '../../../../shared/models/user-profile.model';
import { portDateRangeValidator } from '../../directives/validator/port-daterange-validator.directive';
import { portDateCompareValidator } from '../../directives/validator/port-date-compare-validator.directive';
import { portDuplicationValidator } from '../../directives/validator/port-duplication-validator.directive';

/**
 * Component class of ports screen
 *
 * @export
 * @class PortsComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-ports',
  templateUrl: './ports.component.html',
  styleUrls: ['./ports.component.scss']
})
export class PortsComponent implements OnInit {

  @Input() voyageId: number;
  @Input() loadableStudyId: number;
  @Input() vesselId: number;
  @Input() permission: IPermission;
  // properties
  get portsLists(): IPortsValueObject[] {
    return this._portsLists;
  }

  set portsLists(portsLists: IPortsValueObject[]) {
    this._portsLists = portsLists;
    this._portsLists = portsLists.map((ports, index) => {
      const _ports = this.loadableStudyDetailsTransformationService.formatPorts(ports);
      _ports.slNo = index + 1;
      return _ports
    });
    this.loadableStudyDetailsTransformationService.setPortValidity(this.portsForm.valid && this.portsLists?.filter(item => !item?.isAdd).length > 0);
    this.updatePortOrder();
  }

  // public fields
  readonly editMode = DATATABLE_EDITMODE.CELL;
  OPERATIONS: OPERATIONS;
  portsForm: FormGroup;
  columns: IDataTableColumn[];
  listData = <IPortAllDropdownData>{};
  portsDetails: IPortsDetailsResponse;
  portOrder = 0;

  // private fields
  private _portsLists: IPortsValueObject[];


  constructor(private loadableStudyDetailsApiService: LoadableStudyDetailsApiService,
    private loadableStudyDetailsTransformationService: LoadableStudyDetailsTransformationService,
    private fb: FormBuilder,
    private ngxSpinnerService: NgxSpinnerService,
    private confirmationAlertService: ConfirmationAlertService) { }

  ngOnInit(): void {
    this.columns = this.loadableStudyDetailsTransformationService.getPortDatatableColumns(this.permission);
    this.initSubscriptions();
    this.getPortDetails();
  }

  /**
 * Get all lookups for ports screen
 *
 * @returns {Promise<IPortAllDropdownData>}
 * @memberof PortsComponent
 */
  async getDropdownData(): Promise<IPortAllDropdownData> {
    const result = await this.loadableStudyDetailsApiService.getPorts().toPromise();
    this.listData = <IPortAllDropdownData>{};
    this.listData.portList = result;
    return this.listData;
  }

  /**
 * Get all details for ports screen
 *
 * @returns {Promise<IPortsDetailsResponse>}
 * @memberof PortsComponent
 */
  async getPortDetails() {
    this.ngxSpinnerService.show();
    this.listData = await this.getDropdownData();
    const portsFormData: IPortsDetailsResponse = await this.loadableStudyDetailsApiService.getPortsDetails(this.vesselId, this.voyageId, this.loadableStudyId).toPromise();
    portsFormData.portList = portsFormData.portList ?? [];
    this.listData.operationListComplete = portsFormData.operations;
    this.listData.operationList = portsFormData.operations.filter((item) => item.id !== OPERATIONS.LOADING);
    this.portsDetails = portsFormData;
    this.initPortsArray(this.portsDetails?.portList);
    this.ngxSpinnerService.hide();
  }

  /**
 * Method for updating ports form
 *
 * @private
 * @param {IPortList[]} portsLists
 * @memberof PortsComponent
 */
  private async initPortsArray(portsLists: IPortList[]) {
    this.ngxSpinnerService.show();
    const isEditable = this.permission ? this.permission?.edit : true;
    const _portsLists = portsLists?.map((item) => {
      const portData = this.loadableStudyDetailsTransformationService.getPortAsValueObject(item, false, isEditable, this.listData);
      return portData;
    });
    const portListArray = _portsLists.map(ports => this.initPortsFormGroup(ports));
    this.portsForm = this.fb.group({
      dataTable: this.fb.array([...portListArray])
    });
    this.portsLists = _portsLists;
    this.loadableStudyDetailsTransformationService.setPortValidity(this.portsForm.valid && this.portsLists?.filter(item => !item?.isAdd).length > 0);
    this.updatePortOrder();
    this.ngxSpinnerService.hide();
  }


  /**
  * Method for initializing ports row
  *
  * @private
  * @param {IPortsValueObject} ports
  * @returns
  * @memberof PortsComponent
  */
  private initPortsFormGroup(ports: IPortsValueObject) {
    return this.fb.group({
      port: this.fb.control(ports.port.value, [Validators.required, portDuplicationValidator()]),
      portOrder: this.fb.control(ports.portOrder),
      portcode: this.fb.control(ports.portcode.value, [Validators.required]),
      operation: this.fb.control(ports.operation.value, [Validators.required, portDuplicationValidator()]),
      seaWaterDensity: this.fb.control(ports.seaWaterDensity.value, [Validators.required, Validators.min(0), numberValidator(4, 2)]),
      layCan: this.fb.control(ports.layCan.value, [Validators.required]),
      layCanFrom: this.fb.control(ports.layCan.value?.split('to')[0]?.trim(), Validators.required),
      layCanTo: this.fb.control(ports.layCan.value?.split('to')[1]?.trim(), Validators.required),
      maxDraft: this.fb.control(ports.maxDraft.value, [Validators.required, Validators.min(0), numberValidator(2, 2)]),
      maxAirDraft: this.fb.control(ports.maxAirDraft.value, [Validators.required, Validators.min(0), numberValidator(2, 2)]),
      eta: this.fb.control(ports.eta.value, [Validators.required, portDateRangeValidator, portDateCompareValidator('etd', '<')]),
      etd: this.fb.control(ports.etd.value, [Validators.required, portDateCompareValidator('eta', '>')])

    });
  }

  /**
 * Initialization for all subscriptions
 *
 * @private
 * @memberof PortsComponent
 */
  private initSubscriptions() {
    this.loadableStudyDetailsTransformationService.addPort$.subscribe(() => {
      this.addPort();
    });

    navigator.serviceWorker.addEventListener('message', async event => {
      if (event.data.type === 'ports_sync_finished') {
        const index = this.portsLists.findIndex((item) => item.storeKey === event.data.storeKey);
        if (index !== -1) {
          this.portsLists[index].id = event.data.id;
          this.portsLists = [...this.portsLists];
        }
      }
    });
  }

  /**
   * Method for adding new port
   *
   * @private
   * @memberof PortsComponent
   */
  private addPort(ports: IPortList = null) {
    ports = ports ?? <IPortList>{ id: 0, loadableStudyId: null, portOrder: 0, portId: null, operationId: null, seaWaterDensity: null, distanceBetweenPorts: null, timeOfStay: null, maxDraft: null, maxAirDraft: null, eta: null, etd: null, layCanFrom: null, layCanTo: null };
    const _ports = this.loadableStudyDetailsTransformationService.getPortAsValueObject(ports, true, true, this.listData);
    this.portsLists = [_ports, ...this.portsLists];
    const dataTableControl = <FormArray>this.portsForm.get('dataTable');
    dataTableControl.insert(0, this.initPortsFormGroup(_ports));
  }

  /**
     * Method for fetching form fields
     *
     * @private
     * @param {number} formGroupIndex
     * @param {string} formControlName
     * @returns {FormControl}
     * @memberof PortsComponent
     */
  private field(formGroupIndex: number, formControlName: string): FormControl {
    const formControl = <FormControl>(<FormArray>this.portsForm.get('dataTable')).at(formGroupIndex).get(formControlName);
    return formControl;
  }

  /**
 * Method for updating form field
 *
 * @private
 * @param {number} index
 * @param {string} field
 * @param {*} value
 * @memberof PortsComponent
 */
  private updateField(index: number, field: string, value: any) {
    const control = this.field(index, field);
    control.setValue(value);
    control.markAsDirty();
    control.markAsTouched();
  }


  /**
   * Event handler for edit complete event
   *
   * @param {IPortsEvent} event
   * @memberof PortsComponent
   */
  async onEditComplete(event: IPortsEvent) {
    const form = this.row(event.index);
    const valueIndex = this.portsLists.findIndex(port => port?.storeKey === event?.data?.storeKey);
    if (event.field === 'port') {
      this.portsLists[valueIndex]['portcode'].value = event.data.port.value.code;
      this.portsLists[valueIndex]['maxDraft'].value = event.data.port.value.maxDraft;
      this.portsLists[valueIndex]['maxAirDraft'].value = event.data.port.value.maxAirDraft;
      this.portsLists[valueIndex]['seaWaterDensity'].value = event.data.port.value.waterDensity;
      this.portsLists[valueIndex]['portOrder'] = this.portOrder;
      this.updateField(event.index, 'portcode', event.data.port.value.code);
      this.updateField(event.index, 'maxDraft', event.data.port.value.maxDraft);
      this.updateField(event.index, 'maxAirDraft', event.data.port.value.maxAirDraft);
      this.updateField(event.index, 'seaWaterDensity', event.data.port.value.waterDensity);
      this.updateField(event.index, 'portOrder', this.portOrder);
      form.controls.operation.updateValueAndValidity();
    }
    if (event.field === 'operation') {
      form.controls.port.updateValueAndValidity();
    }
    if (event.field === 'layCan') {
        this.portsLists[valueIndex]['layCanFrom'].value = event.data.layCan.value.split('to')[0].trim();
        this.portsLists[valueIndex]['layCanTo'].value = event.data.layCan.value.split('to')[1].trim();
        this.updateField(event.index, 'layCanFrom', event.data.layCan.value.split('to')[0].trim());
        this.updateField(event.index, 'layCanTo', event.data.layCan.value.split('to')[1].trim());
        form.controls.eta.updateValueAndValidity();
        form.controls.etd.updateValueAndValidity();
    }
    if (event.field === 'eta' || event.field === 'etd') {
      if (form.controls.eta.errors) {
        form.controls.eta.updateValueAndValidity();
      }
      if (form.controls.etd.errors) {
        form.controls.etd.updateValueAndValidity();
      }
    }
    this.loadableStudyDetailsTransformationService.setPortValidity(this.portsForm.valid && this.portsLists?.filter(item => !item?.isAdd).length > 0);
    if (!event.data?.isAdd) {
      if (form.valid) {
        const res = await this.loadableStudyDetailsApiService.setPort(this.loadableStudyDetailsTransformationService.getPortAsValue(this.portsLists[valueIndex]), this.vesselId, this.voyageId, this.loadableStudyId);
        if (res) {
          for (const key in this.portsLists[valueIndex]) {
            if (this.portsLists[valueIndex].hasOwnProperty(key) && this.portsLists[valueIndex][key].hasOwnProperty('_isEditMode')) {
              this.portsLists[valueIndex][key].isEditMode = false;
            }
          }
          this.portsLists = [...this.portsLists];
        }

      } else {
        const fromGroup = this.row(event.index);
        const invalidFormControls = this.findInvalidControlsRecursive(fromGroup);
        invalidFormControls.forEach((key) => {
          this.portsLists[valueIndex][key].isEditMode = true;
        });
        fromGroup.markAllAsTouched();
        this.portsForm.updateValueAndValidity();
      }
    }
  }

  /**
 * Method for fetching form group
 *
 * @private
 * @param {number} formGroupIndex
 * @returns {FormGroup}
 * @memberof PortsComponent
 */
  private row(formGroupIndex: number): FormGroup {
    const formGroup = <FormGroup>(<FormArray>this.portsForm.get('dataTable')).at(formGroupIndex);
    return formGroup;
  }

  /**
 * Event handler for edit complete event
 *
 * @param {IPortsEvent} event
 * @memberof PortsComponent
 */
  async onRowSave(event: IPortsEvent) {
    const form = this.row(event.index);
    const valueIndex = this.portsLists.findIndex(port => port?.storeKey === event?.data?.storeKey);
    if (form.valid) {
      const res = await this.loadableStudyDetailsApiService.setPort(this.loadableStudyDetailsTransformationService.getPortAsValue(this.portsLists[valueIndex]), this.vesselId, this.voyageId, this.loadableStudyId);
      if (res) {
        this.portsLists[valueIndex].isAdd = false;

        for (const key in this.portsLists[valueIndex]) {
          if (this.portsLists[valueIndex].hasOwnProperty(key) && this.portsLists[valueIndex][key].hasOwnProperty('_isEditMode')) {
            this.portsLists[valueIndex][key].isEditMode = false;
          }
        }
        this.portsLists = [...this.portsLists];
        this.portsForm.updateValueAndValidity();
        this.loadableStudyDetailsTransformationService.setPortValidity(this.portsForm.valid && this.portsLists?.filter(item => !item?.isAdd).length > 0);
      }

    } else {
      this.row(event.index).markAllAsTouched();
      this.portsForm.updateValueAndValidity();
    }
  }

  /**
   * Method get all invalid fields in a row
   *
   * @private
   * @param {FormGroup} formToInvestigate
   * @returns {string[]}
   * @memberof PortsComponent
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
  *
  *
  * @param {IPortsEvent} event
  * @memberof PortsComponent
  */
  async onDeleteRow(event: IPortsEvent) {
    if (event?.data?.isDelete) {
      const valueIndex = this.portsLists.findIndex(port => port?.storeKey === event?.data?.storeKey);
      this.confirmationAlertService.add({ key: 'confirmation-alert', sticky: true, severity: 'warn', summary: 'PORTS_DELETE_SUMMARY', detail: 'PORTS_DELETE_DETAILS', data: { confirmLabel: 'PORTS_DELETE_CONFIRM_LABEL', rejectLabel: 'PORTS_DELETE_REJECT_LABEL' } });
      this.confirmationAlertService.confirmAlert$.subscribe(async (response) => {
        if (response) {
          const res = await this.loadableStudyDetailsApiService.setPort(this.loadableStudyDetailsTransformationService.getPortAsValue(this.portsLists[valueIndex]), this.vesselId, this.voyageId, this.loadableStudyId);
          if (res) {
            this.portsLists.splice(event.index, 1);
            this.portsLists = [...this.portsLists];
          }
        }
      });
    }
  }

  /**
 * Update latest Port Order
 * @memberof PortsComponent
 */
  updatePortOrder() {
    if (this.portsLists.length > 0) {
      this.portOrder = Math.max(...this.portsLists.map(d => d.portOrder ? d.portOrder : 0).map(Number)) + 1;
    } else {
      this.portOrder = 1;
    }
  }

  /**
 * Event handler for row re order complete event
 *
 * @memberof PortsComponent
 */
  async onRowReorder(event) {
    const dragform = this.row(event.dragIndex)
    const dropform = this.row(event.dropIndex)
    if (dragform.valid && dropform.valid) {
      this.ngxSpinnerService.show();
      const temp = this.portsLists[event.dragIndex].portOrder;
      this.portsLists[event.dragIndex].portOrder = this.portsLists[event.dropIndex].portOrder;
      this.portsLists[event.dropIndex].portOrder = temp;
      const slNoTemp = this.portsLists[event.dragIndex].slNo;
      this.portsLists[event.dragIndex].slNo = this.portsLists[event.dropIndex].slNo;
      this.portsLists[event.dropIndex].slNo = slNoTemp;
      const dragRes = await this.loadableStudyDetailsApiService.setPort(this.loadableStudyDetailsTransformationService.getPortAsValue(this.portsLists[event.dragIndex]), this.vesselId, this.voyageId, this.loadableStudyId);
      const dropRes = await this.loadableStudyDetailsApiService.setPort(this.loadableStudyDetailsTransformationService.getPortAsValue(this.portsLists[event.dropIndex]), this.vesselId, this.voyageId, this.loadableStudyId);
      if (dragRes && dropRes) {
        for (const key in this.portsLists[event.dragIndex]) {
          if (this.portsLists[event.dragIndex].hasOwnProperty(key) && this.portsLists[event.dragIndex][key].hasOwnProperty('_isEditMode')) {
            this.portsLists[event.dragIndex][key].isEditMode = false;
          }
        }
        for (const key in this.portsLists[event.dropIndex]) {
          if (this.portsLists[event.dropIndex].hasOwnProperty(key) && this.portsLists[event.dropIndex][key].hasOwnProperty('_isEditMode')) {
            this.portsLists[event.dropIndex][key].isEditMode = false;
          }
        }
        this.portsLists = [...this.portsLists];
      }
      this.ngxSpinnerService.hide();
    }
  }

  /**
   * Handler for filter event
   *
   * @param {IDataTableFilterEvent} event
   * @memberof PortsComponent
   */
  onFilter(event: IDataTableFilterEvent) {
    this.ngxSpinnerService.show();
    const portListArray = event?.filteredValue?.map(ports => this.initPortsFormGroup(ports));
    this.portsForm.controls.dataTable = this.fb.array([...portListArray]);
    this.ngxSpinnerService.hide();
  }

  /**
   * Handler for datatable sort event
   *
   * @param {IDataTableSortEvent} event
   * @memberof PortsComponent
   */
  onSort(event: IDataTableSortEvent) {
    this.ngxSpinnerService.show();
    const portListArray = event?.data?.map(ports => this.initPortsFormGroup(ports));
    this.portsForm.controls.dataTable = this.fb.array([...portListArray]);
    this.ngxSpinnerService.hide();
  }
}


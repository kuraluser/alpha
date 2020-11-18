import { Component, OnInit } from '@angular/core';
import { LoadableStudyDetailsApiService } from '../../services/loadable-study-details-api.service';
import { LoadableStudyDetailsTransformationService } from '../../services/loadable-study-details-transformation.service';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { IPortAllDropdownData, IPortList, IPortsDetailsResponse, IPortsValueObject, IPortsEvent } from '../../models/cargo-planning.model';
import { ActivatedRoute } from '@angular/router';
import { DATATABLE_EDITMODE, IDataTableColumn } from '../../../../shared/components/datatable/datatable.model';
import {  numberValidator } from '../../directives/validator/number-validator.directive';

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
  }

  // public fields
  readonly editMode = DATATABLE_EDITMODE.CELL;
  portsForm: FormGroup;
  columns: IDataTableColumn[];
  listData = <IPortAllDropdownData>{};
  voyageId: number;
  loadableStudyId: number;
  vesselId: number;
  portsDetails: IPortsDetailsResponse;

  // private fields
  private _portsLists: IPortsValueObject[];


  constructor(private loadableStudyDetailsApiService: LoadableStudyDetailsApiService,
    private loadableStudyDetailsTransformationService: LoadableStudyDetailsTransformationService,
    private fb: FormBuilder,
    private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.columns = this.loadableStudyDetailsTransformationService.getPortDatatableColumns();
    this.initSubscriptions();
    this.activatedRoute.parent.paramMap.subscribe(params => {
      this.vesselId = Number(params.get('vesselId'));
      this.voyageId = Number(params.get('voyageId'));
      this.loadableStudyId = Number(params.get('loadableStudyId'));
      this.getPortDetails();
    })
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
    this.listData = await this.getDropdownData();
    const portsFormData: IPortsDetailsResponse = await this.loadableStudyDetailsApiService.getPortsDetails(this.vesselId, this.voyageId, this.loadableStudyId).toPromise();
    portsFormData.portList = portsFormData.portList ?? [];
    this.listData.operationListComplete = portsFormData.operations;
    this.listData.operationList = portsFormData.operations.filter((item) => item.operationName.toLowerCase() !== 'loading');
    this.portsDetails = portsFormData;
    this.initPortsArray(this.portsDetails?.portList);
  }

  /**
 * Method for updating ports form
 *
 * @private
 * @param {IPortList[]} portsLists
 * @memberof PortsComponent
 */
  private async initPortsArray(portsLists: IPortList[]) {
    const _portsLists = portsLists?.map((item) => {
      const portData = this.loadableStudyDetailsTransformationService.getPortAsValueObject(item, false, this.listData);
      return portData;
    });
    const portListArray = _portsLists.map(ports => this.initPortsFormGroup(ports));
    this.portsForm = this.fb.group({
      dataTable: this.fb.array([...portListArray])
    });
    this.portsLists = _portsLists;
    this.loadableStudyDetailsTransformationService.setPortValidity(this.portsForm.valid && this.portsLists?.filter(item => !item?.isAdd).length > 0);
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
      port: this.fb.control(ports.port.value, Validators.required),
      portcode: this.fb.control(ports.portcode.value, [Validators.required]),
      operation: this.fb.control(ports.operation.value, Validators.required),
      seaWaterDensity: this.fb.control(ports.seaWaterDensity.value, [Validators.required, numberValidator(4, 2)]),
      layCan: this.fb.control(ports.layCan.value,[ Validators.required]),
      layCanFrom: this.fb.control(ports.layCan.value?.split('to')[0]?.trim(), Validators.required),
      layCanTo: this.fb.control(ports.layCan.value?.split('to')[1]?.trim(), Validators.required),
      maxDraft: this.fb.control(ports.maxDraft.value, [Validators.required, numberValidator(2, 2)]),
      maxAirDraft: this.fb.control(ports.maxAirDraft.value, [Validators.required, numberValidator(2, 2)]),
      eta: this.fb.control(ports.eta.value, Validators.required),
      etd: this.fb.control(ports.eta.value, Validators.required)

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
          this.portsLists[index].id = event.data.portId;
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

    ports = ports ?? <IPortList>{ id: 0, loadableStudyId: null, portId: null, operationId: null, seaWaterDensity: null, distanceBetweenPorts: null, timeOfStay: null, maxDraft: null, maxAirDraft: null, eta: null, etd: null, layCanFrom: null, layCanTo: null };
    const _ports = this.loadableStudyDetailsTransformationService.getPortAsValueObject(ports, true, this.listData);
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
    if (event.field === 'port') {
      this.portsLists[event.index]['portcode'].value = event.data.port.value.code;
      this.portsLists[event.index]['maxDraft'].value = event.data.port.value.maxDraft;
      this.portsLists[event.index]['maxAirDraft'].value = event.data.port.value.maxAirDraft;
      this.portsLists[event.index]['seaWaterDensity'].value = event.data.port.value.waterDensity;
      this.updateField(event.index, 'portcode', event.data.port.value.code);
      this.updateField(event.index, 'maxDraft', event.data.port.value.maxDraft);
      this.updateField(event.index, 'maxAirDraft', event.data.port.value.maxAirDraft);
      this.updateField(event.index, 'seaWaterDensity', event.data.port.value.waterDensity);
    }
    if(event.field === 'layCan'){
      this.portsLists[event.index]['layCanFrom'].value = event.data.layCan.value.split('to')[0].trim();
      this.portsLists[event.index]['layCanTo'].value = event.data.layCan.value.split('to')[1].trim();
      this.updateField(event.index, 'layCanFrom', event.data.layCan.value.split('to')[0].trim());
      this.updateField(event.index, 'layCanTo', event.data.layCan.value.split('to')[1].trim());
    }
    this.loadableStudyDetailsTransformationService.setPortValidity(this.portsForm.valid && this.portsLists?.filter(item => !item?.isAdd).length > 0);
    if (!event.data?.isAdd) {
      const form = this.row(event.index)
      if (form.valid) {
        const res = await this.loadableStudyDetailsApiService.setPort(this.loadableStudyDetailsTransformationService.getPortAsValue(this.portsLists[event.index]), this.vesselId, this.voyageId, this.loadableStudyId);
        if (res) {
          for (const key in this.portsLists[event.index]) {
            if (this.portsLists[event.index].hasOwnProperty(key) && this.portsLists[event.index][key].hasOwnProperty('_isEditMode')) {
              this.portsLists[event.index][key].isEditMode = false;
            }
          }
          this.portsLists = [...this.portsLists];
        }

      } else {
        const fromGroup = this.row(event.index);
        const invalidFormControls = this.findInvalidControlsRecursive(fromGroup);
        invalidFormControls.forEach((key) => {
          this.portsLists[event.index][key].isEditMode = true;
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
    this.loadableStudyDetailsTransformationService.setPortValidity(this.portsForm.valid && this.portsLists?.filter(item => !item?.isAdd).length > 0);

    if (this.portsForm.valid) {
      const res = await this.loadableStudyDetailsApiService.setPort(this.loadableStudyDetailsTransformationService.getPortAsValue(this.portsLists[event.index]), this.vesselId, this.voyageId, this.loadableStudyId);
      if (res) {
        this.portsLists[event.index].isAdd = false;
        for (const key in this.portsLists[event.index]) {
          if (this.portsLists[event.index].hasOwnProperty(key) && this.portsLists[event.index][key].hasOwnProperty('_isEditMode')) {
            this.portsLists[event.index][key].isEditMode = false;
          }
        }
        this.portsLists = [...this.portsLists];
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
      const res = await this.loadableStudyDetailsApiService.setPort(this.loadableStudyDetailsTransformationService.getPortAsValue(this.portsLists[event.index]), this.vesselId, this.voyageId, this.loadableStudyId);
      if (res) {
        this.portsLists.splice(event.index, 1);
        this.portsLists = [...this.portsLists];
      }
    }
  }
}


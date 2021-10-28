import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { IDataTableColumn, IDataTableSortEvent } from '../../../../shared/components/datatable/datatable.model';
import { IDateTimeFormatOptions, ITimeZone } from '../../../../shared/models/common.model';
import { IPermission } from '../../../../shared/models/user-profile.model';
import { AppConfigurationService } from '../../../../shared/services/app-configuration/app-configuration.service';
import { PermissionsService } from '../../../../shared/services/permissions/permissions.service';
import { TimeZoneTransformationService } from '../../../../shared/services/time-zone-conversion/time-zone-transformation.service';
import * as moment from 'moment';
import { portDateCompareValidator } from '../../../cargo-planning/directives/validator/port-date-compare-validator.directive';
import { portDateRangeValidator } from '../../../cargo-planning/directives/validator/port-daterange-validator.directive';
import { portDuplicationValidator } from '../../../cargo-planning/directives/validator/port-duplication-validator.directive';
import { portEtaEtdValidator } from '../../../cargo-planning/directives/validator/port-eta-etd-validator.directive';
import { IPortAllDropdownData, IPortsEvent } from '../../../cargo-planning/models/cargo-planning.model';
import { LoadableStudyDetailsApiService } from '../../../cargo-planning/services/loadable-study-details-api.service';
import { IEditPortRotationModel, OPERATIONS } from '../../models/common.model';
import { PortRotationPopupTransformationService } from './services/port-rotation-popup-transformation.service';
import { IPortRotationValueObject } from './port-rotation-popup.model';
import { IPortList, IPortsDetailsResponse } from '../../../core/models/common.model';
import { PortRotationPopupApiService } from './services/port-rotation-popup-api.service';
import { NgxSpinnerService } from 'ngx-spinner';

/**
 * Component class of port rotation popup screen
 *
 * @export
 * @class PortRotationPopupComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-port-rotation-popup',
  templateUrl: './port-rotation-popup.component.html',
  styleUrls: ['./port-rotation-popup.component.scss']
})
export class PortRotationPopupComponent implements OnInit {
  @Output() displayPopup = new EventEmitter();

  @Input() voyageId: number;
  @Input() loadableStudyId: number;
  @Input() vesselId: number;
  @Input()
  get visible(): boolean {
    return this._visible;
  }
  set visible(visible: boolean) {
    this._visible = visible;
  }

  columns: IDataTableColumn[];
  portsForm: FormGroup;
  portsDetails: IPortsDetailsResponse;
  ports: IPortRotationValueObject[] = [];
  timeZoneList: ITimeZone[];
  portEtaEtdPermission: IPermission;
  listData = <IPortAllDropdownData>{};
  portLoaded = false;
  enableSaveButton: boolean = false;

  private _visible: boolean;

  constructor(private portRotationPopupTransformationService: PortRotationPopupTransformationService,
    private fb: FormBuilder,
    private timeZoneTransformationService: TimeZoneTransformationService,
    private loadableStudyDetailsApiService: LoadableStudyDetailsApiService,
    private permissionsService: PermissionsService,
    private portRotationPopupApiService: PortRotationPopupApiService,
    private ngxSpinnerService: NgxSpinnerService) { }

  async ngOnInit(): Promise<void> {
    this.ngxSpinnerService.show();
    this.portEtaEtdPermission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['PortTabEtaEtd'], false);
    this.columns = await this.portRotationPopupTransformationService.getPortDatatableColumns();
    this.listData = await this.getDropdownData();
    this.getTimeZoneList();
    this.getPorts()
  }

  /**
  * Get all lookups for ports screen
  *
  * @returns {Promise<IPortAllDropdownData>}
  * @memberof PortRotationPopupComponent
  */
  async getDropdownData(): Promise<IPortAllDropdownData> {
    const result = await this.loadableStudyDetailsApiService.getPorts().toPromise();
    this.listData = <IPortAllDropdownData>{};
    this.listData.portList = result;
    return this.listData;
  }


  /**
  * function to list timezones
  *
  * @memberof PortRotationPopupComponent
  */
  async getTimeZoneList() {
    this.timeZoneList = await this.timeZoneTransformationService.getTimeZoneList().toPromise();
  }


  /**
   * Get ports
   *
   * @memberof PortRotationPopupComponent
   */
  async getPorts() {
    const portsFormData: IPortsDetailsResponse = await this.loadableStudyDetailsApiService.getPortsDetails(this.vesselId, this.voyageId, this.loadableStudyId).toPromise();
    portsFormData.portList = portsFormData.portList ?? [];
    this.listData.operationListComplete = portsFormData.operations;
    this.listData.operationList = portsFormData.operations.filter((item) => item.id !== OPERATIONS.LOADING && item.id !== OPERATIONS.DISCHARGING);
    this.portsDetails = portsFormData;
    this.initPortsArray(this.portsDetails?.portList);
  }

  /**
   * init ports array
   * @private
   * @param {IPortList[]} portsLists
   * @memberof PortRotationPopupComponent
   */
  private async initPortsArray(portsLists: IPortList[]) {
    const formatOpt: IDateTimeFormatOptions = { customFormat: AppConfigurationService.settings?.dateFormat };
    const laycanFormat: IDateTimeFormatOptions = { customFormat: AppConfigurationService.settings?.dateFormat.split(' ')[0] };
    const _ports = portsLists?.map((item) => {
      if (item.layCanFrom && item.layCanTo) {
        const layCanFrom = moment(item.layCanFrom, 'DD-MM-YYYY').startOf('d').toDate();
        item.layCanFrom = item.portTimezoneId ? this.convertDateTimeWithZone(layCanFrom, item.portTimezoneId, true) : this.timeZoneTransformationService.formatDateTime(item.layCanFrom, laycanFormat);
        const layCanTo = moment(item.layCanTo, 'DD-MM-YYYY').endOf('d').toDate()
        item.layCanTo = item.portTimezoneId ? this.convertDateTimeWithZone(layCanTo, item.portTimezoneId, true) : this.timeZoneTransformationService.formatDateTime(item.layCanTo, laycanFormat);
      }
      if (item.eta) {
        item.eta = item.portTimezoneId ? this.convertDateTimeWithZone(item.eta, item.portTimezoneId) : this.timeZoneTransformationService.formatDateTime(item.eta, formatOpt);
      }
      if (item.etd) {
        item.etd = item.portTimezoneId ? this.convertDateTimeWithZone(item.etd, item.portTimezoneId) : this.timeZoneTransformationService.formatDateTime(item.etd, formatOpt);
      }
      const portData = this.portRotationPopupTransformationService.getPortAsValueObject(item, true, true, this.listData, this.portEtaEtdPermission);
      return portData;
    });
    const portListArray = _ports.map((ports, index) =>
      this.initPortsFormGroup(ports, index)
    );
    this.ports = _ports;
    this.portsForm = this.fb.group({
      dataTable: this.fb.array([...portListArray])
    });
    this.portLoaded = true;
    this.enableSaveButton = this.portsForm.valid ? true : false;
    this.ngxSpinnerService.hide()
  }

  /**
* Method for closing popup
*
* @memberof PortRotationPopupComponent
*/
  closePopup(success?: boolean) {
    let successData = {
      success: success,
      hide: false
    }
    this.displayPopup.emit(successData);
  }


  /**
* Method for initializing ports row
*
* @private
* @param {IPortRotationValueObject} ports
* @returns
* @memberof PortRotationPopupComponent
*/
  private initPortsFormGroup(ports: IPortRotationValueObject, index: number) {
    const required = this.portRotationPopupTransformationService.isEtaEtdViewable(this.portEtaEtdPermission);
    const layCanData = (ports.operation.value && [OPERATIONS.BUNKERING, OPERATIONS.DISCHARGING, OPERATIONS.TRANSIT].includes(ports.operation.value.id));
    const layCanArray = [];
    const layCanFrom = this.dateStringToDate(ports.layCan.value?.split('to')[0]?.trim(), true);
    const layCanTo = this.dateStringToDate(ports.layCan.value?.split('to')[1]?.trim(), true);
    if (layCanFrom && layCanTo) {
      layCanArray.push(layCanFrom)
      layCanArray.push(layCanTo)
    }
    return this.fb.group({
      port: this.fb.control(ports.port.value),
      portcode: this.fb.control(ports.portcode.value, [Validators.required]),
      operation: this.fb.control(ports.operation.value, [Validators.required, portDuplicationValidator('operation')]),
      layCan: this.fb.control({ value: layCanFrom && layCanTo ? layCanArray : null, disabled: layCanData }, { validators: layCanData ? [] : Validators.required }),
      layCanFrom: this.fb.control({ value: this.dateStringToDate(ports.layCan.value?.split('to')[0]?.trim(), true), disabled: layCanData }, layCanData ? [] : { validators: layCanData ? [] : Validators.required }),
      layCanTo: this.fb.control({ value: this.dateStringToDate(ports.layCan.value?.split('to')[1]?.trim(), true), disabled: layCanData }, layCanData ? [] : { validators: layCanData ? [] : Validators.required }),
      eta: this.fb.control(this.dateStringToDate(ports.eta.value), this.getValidators('eta', index, required)),
      etd: this.fb.control(this.dateStringToDate(ports.etd.value), this.getValidators('etd', index, required))
    });

  }

  /**
 * function to convert string to Date object
 *
 * @param {string} dateTime
 * @return {*}  {Date}
 * @memberof PortRotationPopupComponent
 */
  dateStringToDate(dateTime: string, layCan?: boolean): Date {
    if (dateTime) {
      const _dateTime = layCan ? moment(dateTime, AppConfigurationService.settings?.dateFormat.split(' ')[0]).format('DD-MM-YYYY HH:mm') : moment(dateTime.slice(0, 17)).format('DD-MM-YYYY HH:mm');
      const formatOptions: IDateTimeFormatOptions = { stringToDate: true };
      return this.timeZoneTransformationService.formatDateTime(_dateTime, formatOptions);
    }
  }

  /**
 * Method to get validators of a key
 *
 * @memberof PortRotationPopupComponent
 */
  getValidators(key, index: number, isRequired: boolean) {
    const required = isRequired ? [Validators.required] : []
    switch (key) {
      case 'eta':
        return [...required, portDateCompareValidator('etd', '<'), portEtaEtdValidator('eta', index)];
      case 'etd':
        return [...required, portDateCompareValidator('eta', '>'), portEtaEtdValidator('etd', index)];
      default:
        return [];
    }
  }

  /**
   * function to convert date-time to port local
   *
   * @param {Date} dateTime
   * @param {number} portTimezoneId
   * @return {*}  {string}
   * @memberof PortRotationPopupComponent
   */
  convertDateTimeWithZone(dateTime: Date | string, timeZoneId: number, isLayCan?: boolean): string {
    const selectedTimeZone: ITimeZone = this.timeZoneList?.find(tz => (tz.id === timeZoneId));
    const formatOptions: IDateTimeFormatOptions = {
      portLocalFormat: true,
      portTimeZoneOffset: selectedTimeZone?.offsetValue,
      portTimeZoneAbbr: selectedTimeZone?.abbreviation
    };
    if (isLayCan) {
      return this.timeZoneTransformationService.formatDateTime(dateTime, formatOptions)?.slice(0, 11);
    } else {
      return this.timeZoneTransformationService.formatDateTime(dateTime, formatOptions);
    }
  }


  /**
 * Event handler for edit complete event
 *
 * @param {IPortsEvent} event
 * @memberof PortRotationPopupComponent
 */
  async onEditComplete(event: IPortsEvent) {
    const index = event.index;
    const form = this.row(index);
    if (event.field === 'layCan') {
      const layCanFrom = event.data.layCan.value.split('to')[0].trim()
      const layCanTo = event.data.layCan.value.split('to')[1].trim()
      this.ports[index]['layCanFrom'].value = layCanFrom;
      this.ports[index]['layCanTo'].value = layCanTo;
      this.updateField(event.index, 'layCanFrom', this.dateStringToDate(layCanFrom, true));
      this.updateField(event.index, 'layCanTo', this.dateStringToDate(layCanTo, true));
      this.updateValidityAndEditMode(index, 'eta');
      this.updateValidityAndEditMode(index, 'etd');
    }
    if (event.field === 'etd') {
      for (let i = 0; i < this.ports.length; i++) {
        this.updateValidityAndEditMode(i, 'eta');
      }
    }
    if (event.field === 'eta') {
      for (let i = 0; i < this.ports.length; i++) {
        this.updateValidityAndEditMode(i, 'etd')
      }
    }
    if (event.field === 'eta' || event.field === 'etd') {
      this.updateValuesIfBunkering(event.data, form, index);
      const control = this.field(index, 'layCan');
      control.markAsDirty();
      control.markAsTouched();
    }
    this.enableSaveButton = this.portsForm.valid ? true : false;
  }

  /**
 * Method for fetching form fields
 *
 * @private
 * @param {number} formGroupIndex
 * @param {string} formControlName
 * @returns {FormControl}
 * @memberof PortRotationPopupComponent
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
 * @memberof PortRotationPopupComponent
 */
  private updateField(index: number, field: string, value?: any) {
    const control = this.field(index, field);
    control.setValue(value);
    control.markAsDirty();
    control.markAsTouched();
  }

  /**
   * Method to update validity and edit mode
   *
   * @memberof PortRotationPopupComponent
   */
  updateValidityAndEditMode(index: number, key: string) {
    if ((key === 'eta' || key === 'etd') && !this.portRotationPopupTransformationService.isEtaEtdViewable(this.portEtaEtdPermission)) {
      return;
    }
    const field = this.field(index, key)
    if (field) {
      field.updateValueAndValidity();
      field.markAsTouched();
      this.ports[index][key].isEditMode = field.invalid && field.enabled;
    }
  }


  /**
 * Method to save port data
 *
 * @memberof PortRotationPopupComponent
 */
  async savePort() {
    if(!this.portsForm.valid) { return; }
    this.ngxSpinnerService.show();
    const portSave: IEditPortRotationModel = { portList: [] };
    const saveportList = this.portRotationPopupTransformationService.getPortAsValue(this.portsDetails?.portList, this.ports);
    portSave.portList = JSON.parse(JSON.stringify(saveportList));
    const res = await this.portRotationPopupApiService.saveEditPortRotation(this.vesselId, this.voyageId, this.loadableStudyId, portSave).toPromise();
    if (res.responseStatus.status === '200') {
      this.closePopup(true)
    }
    this.ngxSpinnerService.hide();
  }

  /**
 * Handler for datatable sort event
 *
 * @param {IDataTableSortEvent} event
 * @memberof PortRotationPopupComponent
 */
  onSort(event: IDataTableSortEvent) {
    this.ngxSpinnerService.show();
    const portListArray = event?.data?.map((ports, index) => this.initPortsFormGroup(ports, index));
    this.portsForm.controls.dataTable = this.fb.array([...portListArray]);
    this.ngxSpinnerService.hide();
  }

  /**
* Method for fetching form group
*
* @private
* @param {number} formGroupIndex
* @returns {FormGroup}
* @memberof PortRotationPopupComponent
*/
  private row(formGroupIndex: number): FormGroup {
    const formGroup = <FormGroup>(<FormArray>this.portsForm.get('dataTable')).at(formGroupIndex);
    return formGroup;
  }


  /**
* Method to update eta and etd if port is bunkering
*
* @memberof PortRotationPopupComponent
*/
  updateValuesIfBunkering(data, form, index) {
    if (!this.portRotationPopupTransformationService.isEtaEtdViewable(this.portEtaEtdPermission)) {
      return;
    }
    if (data && data.operation?.value?.id === OPERATIONS.BUNKERING && data.port.value) {
      const portId = Number(data.port.value.id);
      if (index > 0) {
        const row = this.ports[index - 1]
        if (row.port?.value?.id === portId && [OPERATIONS.LOADING, OPERATIONS.DISCHARGING].includes(row.operation?.value?.id)) {
          const loadingPortData = row;
          const loadingPortForm = this.row(Number(loadingPortData.slNo - 1));
          form.controls.eta.setValue(loadingPortForm.value.eta ?? null);
          form.controls.etd.setValue(loadingPortForm.value.etd ?? null);
          form.controls.eta.disable();
          form.controls.etd.disable();
          this.ports[index].eta.value = loadingPortData.eta.value ? loadingPortData.eta.value : '';
          this.ports[index].etd.value = loadingPortData.etd.value ? loadingPortData.etd.value : '';
          this.ports[index].eta.isEditable = false;
          this.ports[index].etd.isEditable = false;
          this.updateValidityAndEditMode(index, 'eta');
          this.updateValidityAndEditMode(index, 'etd');
          return;
        }
      }
    }
    if (form.controls.eta.disabled && form.controls.etd.disabled) {
      form.controls.eta.enable();
      form.controls.etd.enable();
      form.controls.eta.markAsTouched();
      form.controls.etd.markAsTouched();
      this.ports[index].eta.isEditable = true;
      this.ports[index].etd.isEditable = true;
      this.updateValidityAndEditMode(index, 'eta');
      this.updateValidityAndEditMode(index, 'etd');
    }
  }

}

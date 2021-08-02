import { AppConfigurationService } from './../../../../shared/services/app-configuration/app-configuration.service';
import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { DischargeStudyDetailsApiService } from '../../services/discharge-study-details-api.service';
import { DischargeStudyDetailsTransformationService } from '../../services/discharge-study-details-transformation.service';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { IPortAllDropdownData, IDischargeStudyPortsValueObject , IPortsEvent } from '../../models/cargo-planning.model';
import { DATATABLE_EDITMODE, IDataTableColumn, IDataTableFilterEvent, IDataTableSortEvent } from '../../../../shared/components/datatable/datatable.model';
import { numberValidator } from '../../../core/directives/number-validator.directive';
import { NgxSpinnerService } from 'ngx-spinner';
import { OPERATIONS } from '../../models/cargo-planning.model';
import { IPermission } from '../../../../shared/models/user-profile.model';
import { portDateRangeValidator } from '../../directives/validator/port-daterange-validator.directive';
import { portDateCompareValidator } from '../../directives/validator/port-date-compare-validator.directive';
import { portDuplicationValidator } from '../../directives/validator/port-duplication-validator.directive';
import { IDischargeStudyPortList , IDischargePortsDetailsResponse , Voyage, VOYAGE_STATUS } from '../../../core/models/common.model';
import { portEtaEtdValidator } from '../../directives/validator/port-eta-etd-validator.directive'
import { ConfirmationService, MessageService } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';
import { IDischargeStudy } from '../../models/discharge-study-list.model';
import { GlobalErrorHandler } from '../../../../shared/services/error-handlers/global-error-handler';
import { IDateTimeFormatOptions, ITimeZone, ValueObject } from './../../../../shared/models/common.model';
import { TimeZoneTransformationService } from './../../../../shared/services/time-zone-conversion/time-zone-transformation.service';
import { PermissionsService } from '../../../../shared/services/permissions/permissions.service';
import * as moment from 'moment';
import { seaWaterDensityRangeValidator } from '../../../core/directives/seawater-density-range-validator.directive';
import { environment } from '../../../../../environments/environment';
import { SecurityService } from '../../../../shared/services/security/security.service';

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
export class PortsComponent implements OnInit, OnDestroy {

  @Input() voyageId: number;
  @Input() voyage: Voyage;
  @Input() dischargeStudyId: number;
  @Input() vesselId: number;
  @Input() permission: IPermission;
  // properties
  get portsLists(): IDischargeStudyPortsValueObject[] {
    return this._portsLists;
  }

  set portsLists(portsLists: IDischargeStudyPortsValueObject[]) {
    this._portsLists = portsLists;
    this._portsLists = portsLists.map((ports, index) => {
      const _ports = this.dischargeStudyDetailsTransformationService.formatPorts(ports);
      _ports.slNo = index + 1;
      return _ports
    });
    this.dischargeStudyDetailsTransformationService.setPortValidity(this.portsForm.valid && this.portsLists?.filter(item => !item?.isAdd).length > 0 && !this.portOrderValidation());
    this.updatePortOrder();
  }

  @Input()
  get dischargeStudy() {
    return this._dischargeStudy;
  }
  set dischargeStudy(value: IDischargeStudy) {
    this._dischargeStudy = value;
    this.editMode = (this.permission?.edit === undefined || this.permission?.edit || this.permission?.add === undefined || this.permission?.add) && [VOYAGE_STATUS.ACTIVE].includes(this.voyage?.statusId) ? DATATABLE_EDITMODE.CELL : null;
  }

  @Output() portUpdate = new EventEmitter<boolean>();

  // public fields
  editMode: DATATABLE_EDITMODE;
  OPERATIONS: OPERATIONS;
  portsForm: FormGroup;
  columns: IDataTableColumn[];
  listData = <IPortAllDropdownData>{};
  portsDetails: IDischargePortsDetailsResponse;
  portOrder = 0;
  dataTableLoading: boolean;
  portCheckUpdatesTimer;
  progress = true;
  timeZoneList: ITimeZone[];
  portEtaEtdPermission: IPermission;

  // private fields
  private _portsLists: IDischargeStudyPortsValueObject[];
  private _dischargeStudy: IDischargeStudy;
  private portsListSaved: IDischargeStudyPortsValueObject[];


  constructor(private dischargeStudyDetailsApiService: DischargeStudyDetailsApiService,
    private dischargeStudyDetailsTransformationService: DischargeStudyDetailsTransformationService,
    private timeZoneTransformationService: TimeZoneTransformationService,
    private fb: FormBuilder,
    private ngxSpinnerService: NgxSpinnerService,
    private confirmationService: ConfirmationService,
    private messageService: MessageService,
    private translateService: TranslateService,
    private globalErrorHandler: GlobalErrorHandler,
    private permissionsService: PermissionsService) { }

  async ngOnInit(): Promise<void> {
    await this.getTimeZoneList();
    this.portEtaEtdPermission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['DischargeStudyPortETAETD'], false);
    this.columns = this.dischargeStudyDetailsTransformationService.getPortDatatableColumns(this.permission, this.portEtaEtdPermission, this.dischargeStudy?.statusId, this.voyage?.statusId);
    this.initSubscriptions();
    this.getPortDetails();
  }

  ngOnDestroy() {
    navigator.serviceWorker.removeEventListener('message', this.swMessageHandler);
  }

  /**
   * function to list timezones
   *
   * @memberof PortsComponent
   */
  async getTimeZoneList() {
    this.timeZoneList = await this.timeZoneTransformationService.getTimeZoneList().toPromise();
  }

  /**
 * Get all lookups for ports screen
 *
 * @returns {Promise<IPortAllDropdownData>}
 * @memberof PortsComponent
 */
  async getDropdownData(): Promise<IPortAllDropdownData> {
    const result = await this.dischargeStudyDetailsApiService.getPorts().toPromise();
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
    await this.getPorts();
    const hasPendingUpdates = await this.checkForPendingUpdates();
    if (hasPendingUpdates) {
      this.dataTableLoading = true;
      this.portCheckUpdatesTimer = setInterval(async () => {
        const _hasPendingUpdates = await this.checkForPendingUpdates();
        if (!_hasPendingUpdates) {
          await this.getPorts();
          this.dataTableLoading = false;
          clearInterval(this.portCheckUpdatesTimer);
        }
      }, 500);
    }
    this.portsListSaved = [];
    this.portsLists?.forEach(row => {
      this.portsListSaved.push(JSON.parse(JSON.stringify(row)))
    })
    this.ngxSpinnerService.hide();
  }

  /**
   * Get ports
   *
   * @memberof PortsComponent
   */
  async getPorts() {
    const portsFormData: IDischargePortsDetailsResponse = await this.dischargeStudyDetailsApiService.getPortsDetails(this.vesselId, this.voyageId, this.dischargeStudyId).toPromise();
    portsFormData.portList = portsFormData.portList ?? [];
    this.listData.operationListComplete = portsFormData.operations;
    this.listData.operationList = portsFormData.operations.filter((item) => item.id === OPERATIONS.DISCHARGING || item.id === OPERATIONS.BUNKERING || item.id === OPERATIONS.TRANSIT);
    this.portsDetails = portsFormData;
    this.initPortsArray(this.portsDetails?.portList);
  }

  /**
   * Check if there are any pending updates in indexed db
   *
   * @returns {Promise<boolean>}
   * @memberof PortsComponent
   */
  async checkForPendingUpdates(): Promise<boolean> {
    const count = await this.dischargeStudyDetailsApiService.getPortPendingUpdatesCount(this.vesselId, this.voyageId, this.dischargeStudyId);
    if (!count) {
      return false;
    } else {
      this.ngxSpinnerService.hide();
      this.dataTableLoading = true;
      return true;
    }
  }

  /**
 * Method for updating ports form
 *
 * @private
 * @param {IDischargeStudyPortList[]} portsLists
 * @memberof PortsComponent
 */
  private async initPortsArray(portsLists: IDischargeStudyPortList[]) {
    this.ngxSpinnerService.show();
    const isEditable = this.permission ? this.permission?.edit : true;
    const formatOpt: IDateTimeFormatOptions = { customFormat: AppConfigurationService.settings?.dateFormat };
    const _portsLists = portsLists?.map((item) => {
      item.seaWaterDensity = item?.seaWaterDensity?.toFixed(Number(AppConfigurationService.settings.sgNumberFormat.slice(-1)));
      if (item.eta) {
        item.eta = item.portTimezoneId ? this.convertDateTimeWithZone(item.eta, item.portTimezoneId) : this.timeZoneTransformationService.formatDateTime(item.eta, formatOpt);
      }
      if (item.etd) {
        item.etd = item.portTimezoneId ? this.convertDateTimeWithZone(item.etd, item.portTimezoneId) : this.timeZoneTransformationService.formatDateTime(item.etd, formatOpt);
      }
      const portData = this.dischargeStudyDetailsTransformationService.getPortAsValueObject(item, false, isEditable, this.listData, this.portEtaEtdPermission);
      return portData;
    });
    const portListArray = _portsLists.map((ports, index) =>
      this.initPortsFormGroup(ports, index)
    );
    this.portsForm = this.fb.group({
      dataTable: this.fb.array([...portListArray])
    });
    this.portsLists = _portsLists;
    setTimeout(async () => {
      this.dischargeStudyDetailsTransformationService.setPortValidity(this.portsForm.valid && this.portsLists?.filter(item => !item?.isAdd).length > 0 && !this.portOrderValidation());
      this.updatePortOrder();
      this.ngxSpinnerService.hide();
      this.updateFormValidity(portListArray);
      if (this.portOrderValidation()) {
        const translationKeys = await this.translateService.get(['PORT_ROTATION_ERROR_DETAILS_REORDER', 'PORT_ROTATION_WARN']).toPromise();
        this.messageService.add({ key: 'isPortOrderValid' , severity: 'warn', summary: translationKeys['PORT_ROTATION_WARN'], detail: translationKeys['PORT_ROTATION_ERROR_DETAILS_REORDER'], sticky: true, closable: true });
      }
    }, 500);

  }


  /**
  * Method for initializing ports row
  *
  * @private
  * @param {IDischargeStudyPortsValueObject} ports
  * @returns
  * @memberof PortsComponent
  */
  private initPortsFormGroup(ports: IDischargeStudyPortsValueObject, index: number) {
    const required = this.dischargeStudyDetailsTransformationService.isEtaEtdViewable(this.portEtaEtdPermission, ports.isAdd);
    
    return this.fb.group({
      port: this.fb.control(ports.port.value, [Validators.required, portDuplicationValidator('port')]),
      portOrder: this.fb.control(ports.portOrder),
      portcode: this.fb.control(ports.portcode.value, [Validators.required]),
      operation: this.fb.control(ports.operation.value, [Validators.required, portDuplicationValidator('operation')]),
      seaWaterDensity: this.fb.control(ports.seaWaterDensity.value, [Validators.required, Validators.min(0), numberValidator(4, 1), seaWaterDensityRangeValidator()]),
      maxDraft: this.fb.control(ports.maxDraft.value, [Validators.required, Validators.min(0), numberValidator(2, 2)]),
      maxAirDraft: this.fb.control(ports.maxAirDraft.value, [Validators.required, Validators.min(0), numberValidator(2, 2)]),
      eta: this.fb.control({ value: this.dateStringToDate(ports.eta.value), disabled: !required }, this.getValidators('eta', index, required)),
      etd: this.fb.control({ value: this.dateStringToDate(ports.etd.value), disabled: !required }, this.getValidators('etd', index, required))
    });

  }


  /**
 * Initialization for all subscriptions
 *
 * @private
 * @memberof PortsComponent
 */
  private initSubscriptions() {
    this.dischargeStudyDetailsTransformationService.addPort$.subscribe(() => {
      this.addPort();
    });

    navigator.serviceWorker.addEventListener('message', this.swMessageHandler);
  }

  /**
   * Handler for service worker message event
   *
   * @private
   * @memberof PortsComponent
   */
  private swMessageHandler = async (event) => {
    if (event?.data?.status === '401' && event?.data?.errorCode === '210') {
      this.globalErrorHandler.sessionOutMessage();
    } else if (environment.name !== 'shore' && (event?.data?.status === '200' || event?.data?.responseStatus?.status === '200')) {
      SecurityService.refreshToken(event?.data?.refreshedToken)
    }
    const translationKeys = await this.translateService.get(['DISCHARGE_STUDY_PORT_UPDATE_ERROR', 'DISCHARGE_STUDY_PORT_UPDATE_STATUS_ERROR']).toPromise();
    if (event?.data?.type === 'discharge_ports_sync_finished') {
      this.dischargeStudyDetailsTransformationService.disableGenerateLoadablePatternBtn(false);
      this.portUpdate.emit(false);
      const index = this.portsLists?.findIndex((item) => item.storeKey === event.data.storeKey);
      if (index !== -1) {
        this.portsLists[index].processing = false;
        if (event?.data?.responseStatus?.status === '200') {
          this.portsLists[index].id = event.data.id;
          this.portsLists = [...this.portsLists];
          this.portsListSaved = JSON.parse(JSON.stringify(this.portsLists));
        }
      }
      if (event?.data?.status === '400' && event?.data?.errorCode === 'ERR-RICO-110') {
        this.messageService.add({ severity: 'error', summary: translationKeys['DISCHARGE_STUDY_PORT_UPDATE_ERROR'], detail: translationKeys['DISCHARGE_STUDY_PORT_UPDATE_STATUS_ERROR'], life: 10000, closable: false, sticky: false });
      }
      if (event?.data?.status === '401' && event?.data?.errorCode === '210') {
        this.globalErrorHandler.sessionOutMessage();
      }
    }
  }

  /**
   * Method for adding new port
   *
   * @private
   * @memberof PortsComponent
   */
  private async addPort(ports: IDischargeStudyPortList = null) {
    const translationKeys = await this.translateService.get(['DISCHARGE_STUDY_MAX_ERROR', 'DISCHARGE_STUDY_MAX_ERROR_SUMMERY']).toPromise();
    if(10 > this.portsLists?.length) {
      ports = ports ?? <IDischargeStudyPortList>{ id: 0, dischargeStudyId: null, portOrder: 0, portId: null, operationId: null, seaWaterDensity: null, distanceBetweenPorts: null, timeOfStay: null, maxDraft: null, maxAirDraft: null, eta: null, etd: null };
      const _ports = this.dischargeStudyDetailsTransformationService.getPortAsValueObject(ports, true, true, this.listData, this.portEtaEtdPermission);
      const dataTableControl = <FormArray>this.portsForm.get('dataTable');
      dataTableControl.push(this.initPortsFormGroup(_ports, this.portsLists.length));
      this.portsLists = [...this.portsLists, _ports];
    } else {
      this.messageService.add({ severity: 'error', summary: translationKeys['DISCHARGE_STUDY_MAX_ERROR'], detail: translationKeys['DISCHARGE_STUDY_MAX_ERROR_SUMMERY'] });
    }
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
  private updateField(index: number, field: string, value?: any) {
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
    const index = event.index;
    const valueIndex = this.portsLists.findIndex(port => port?.storeKey === event?.data?.storeKey);
    if ((event.field === 'port' || event.field === 'operation') && !event.data?.isAdd && !this.portsListSaved[valueIndex]['isAdd']) {
      const translationKeys = await this.translateService.get(['PORT_CHANGE_CONFIRM_SUMMARY', 'PORT_CHANGE_CONFIRM_DETAILS', 'PORT_CHANGE_CONFIRM_LABEL', 'PORT_CHANGE_REJECT_LABEL']).toPromise();

      this.confirmationService.confirm({
        key: 'confirmation-alert',
        header: translationKeys['PORT_CHANGE_CONFIRM_SUMMARY'],
        message: translationKeys['PORT_CHANGE_CONFIRM_DETAILS'],
        icon: 'pi pi-exclamation-triangle',
        acceptLabel: translationKeys['PORT_CHANGE_CONFIRM_LABEL'],
        acceptIcon: 'pi',
        acceptButtonStyleClass: 'btn btn-main mr-5',
        rejectVisible: true,
        rejectLabel: translationKeys['PORT_CHANGE_REJECT_LABEL'],
        rejectIcon: 'pi',
        rejectButtonStyleClass: 'btn btn-main',
        accept: async () => {
          this.portsListSaved[index] = JSON.parse(JSON.stringify(this.portsLists[index]))
          this.portsListSaved[index]['isAdd'] = true;
          this.updatePortsDetails(event)
        },
        reject: () => {
          this.portsLists[valueIndex][event.field].value = this.portsListSaved[valueIndex][event.field]['_value'];
          this.updateField(event.index, event.field, this.portsListSaved[valueIndex][event.field]['_value']);
        }
      });
    } else {
      this.updatePortsDetails(event)
    }
  }

  /**
   * Method to handle ports data on update
   *
   * @param {IPortsEvent} event
   * @memberof PortsComponent
   */
  async updatePortsDetails(event: IPortsEvent) {
    const index = event.index;
    const form = this.row(index);
    const valueIndex = this.portsLists.findIndex(port => port?.storeKey === event?.data?.storeKey);
    if (event.field === 'port') {
      this.portsLists[valueIndex]['portcode'].value = event.data.port.value.code;
      this.portsLists[valueIndex]['portOrder'] = event.data.portOrder ? event.data.portOrder : this.portOrder;
      this.updateField(event.index, 'portcode', event.data.port.value.code);
      this.portsLists[valueIndex]['maxDraft'].value = event.data.port.value.maxDraft;
      this.updateField(event.index, 'maxDraft', event.data.port.value.maxDraft);
      this.updateValidityAndEditMode(event.index, 'maxDraft')
      this.portsLists[valueIndex]['maxAirDraft'].value = event.data.port.value.maxAirDraft;
      this.updateField(event.index, 'maxAirDraft', event.data.port.value.maxAirDraft);
      this.updateValidityAndEditMode(event.index, 'maxAirDraft')
      this.portsLists[valueIndex]['seaWaterDensity'].value = event.data.port.value.waterDensity;
      this.updateField(event.index, 'seaWaterDensity', event.data.port.value.waterDensity);
      this.updateField(event.index, 'portOrder', event.data.portOrder ? event.data.portOrder : this.portOrder);
      this.portsLists[valueIndex]['eta'].value = null;
      this.portsLists[valueIndex]['etd'].value = null;
      this.updateField(event.index, 'eta', null);
      this.updateField(event.index, 'etd', null);
      form.controls.operation.updateValueAndValidity();
      this.updateValuesIfBunkering(event.data, form, index);
    }
    if (event.field === 'operation') {
      const operationId = event.data.operation.value.id;

      form.controls.port.updateValueAndValidity();
      
      this.updateValidityAndEditMode(index, 'eta');
      this.updateValidityAndEditMode(index, 'etd');

      this.updateValuesIfBunkering(event.data, form, index);
    }
    
    if (event.field === 'etd') {
      for (let i = 0; i < this.portsLists.length; i++) {
        this.updateValidityAndEditMode(i, 'eta')
      }
      const previousRow = this.portsLists[valueIndex - 1] ? this.portsLists[valueIndex - 1] : null;
      if (previousRow && previousRow.port.value.id === this.portsLists[valueIndex].port.value.id && this.portsLists[valueIndex].operation.value.id === OPERATIONS.DISCHARGING && previousRow.operation.value.id === OPERATIONS.BUNKERING) {
        this.portsLists[valueIndex - 1]['etd'].value = event.data.etd.value;
        this.updateValidityAndEditMode(valueIndex - 1, 'etd');
      }
      const nextRow = this.portsLists[valueIndex + 1] ? this.portsLists[valueIndex + 1] : null;
      if (nextRow && nextRow.port.value.id === this.portsLists[valueIndex].port.value.id && this.portsLists[valueIndex].operation.value.id === OPERATIONS.DISCHARGING && nextRow.operation.value.id === OPERATIONS.BUNKERING) {
        this.portsLists[valueIndex + 1]['etd'].value = event.data.etd.value;
        this.updateValidityAndEditMode(valueIndex - 1, 'etd');
      }
    }
    if (event.field === 'eta') {
      for (let i = 0; i < this.portsLists.length; i++) {
        this.updateValidityAndEditMode(i, 'etd')
      }
      const previousRow = this.portsLists[valueIndex - 1] ? this.portsLists[valueIndex - 1] : null;
      if (previousRow && previousRow.port.value.id === this.portsLists[valueIndex].port.value.id && this.portsLists[valueIndex].operation.value.id === OPERATIONS.DISCHARGING && previousRow.operation.value.id === OPERATIONS.BUNKERING) {
        this.portsLists[valueIndex - 1]['eta'].value = event.data.eta.value;
        this.updateValidityAndEditMode(valueIndex - 1, 'eta');
      }
      const nextRow = this.portsLists[valueIndex + 1] ? this.portsLists[valueIndex + 1] : null;
      if (nextRow && nextRow.port.value.id === this.portsLists[valueIndex].port.value.id && this.portsLists[valueIndex].operation.value.id === OPERATIONS.DISCHARGING && nextRow.operation.value.id === OPERATIONS.BUNKERING) {
        this.portsLists[valueIndex + 1]['eta'].value = event.data.eta.value;
        this.updateValidityAndEditMode(valueIndex - 1, 'eta');
      }
    }
    form.markAsTouched();
    const formArray = (<FormArray>this.portsForm.get('dataTable')).controls;
    formArray.forEach(async (row: FormGroup, rowIndex) => {
      if ((event.field === 'port' || event.field === 'operation')) {
        if (row.controls.port.hasError('duplicate') || row.controls.port.hasError('transitDuplicate')) {
          this.updateValidityAndEditMode(rowIndex, 'port');
        }
        if (row.controls.operation.hasError('duplicate') || row.controls.port.hasError('transitDuplicate')) {
          this.updateValidityAndEditMode(rowIndex, 'operation');
        }
      }
      if (row.valid && !event.data?.isAdd && row.touched) {
        event.data.processing = true;
        this.dischargeStudyDetailsTransformationService.disableGenerateLoadablePatternBtn(true);
        if (this.portsLists[rowIndex]?.id !== 0) {
          this.portUpdate.emit(true);
          const res = await this.dischargeStudyDetailsApiService.setPort(this.dischargeStudyDetailsTransformationService.getPortAsValue(this.portsLists[rowIndex]), this.vesselId, this.voyageId, this.dischargeStudyId, (this.portsForm.valid && !this.portOrderValidation()));
          if (res) {
            row.markAsUntouched();
            for (const key in this.portsLists[rowIndex]) {
              if (this.portsLists[rowIndex]?.hasOwnProperty(key) && this.portsLists[rowIndex][key]?.hasOwnProperty('_isEditMode')) {
                this.portsLists[rowIndex][key].isEditMode = false;
              }
            }
          }
          
        }
      }
    });
    this.portsLists = [...this.portsLists];
    if (this.portsForm.valid && this.portOrderValidation()) {
      const translationKeys = await this.translateService.get(['PORT_ROTATION_ERROR_DETAILS_REORDER', 'PORT_ROTATION_WARN']).toPromise();
      this.messageService.add({ key: 'isPortOrderValid' , severity: 'warn', summary: translationKeys['PORT_ROTATION_WARN'], detail: translationKeys['PORT_ROTATION_ERROR_DETAILS_REORDER'], sticky: true, closable: true });
    } else if(!this.portOrderValidation()){
      this.messageService.clear('isPortOrderValid');
    }
    this.dischargeStudyDetailsTransformationService.setPortValidity(this.portsForm.valid && this.portsLists?.filter(item => item?.isAdd).length <= 0 && !this.portOrderValidation());
  }

  /**
 * Method for Port order validation
 *
 * @memberof PortsComponent
 */
  portOrderValidation() {
    let dischargeFound = false, orderError = false;
    for (let i = 0; i < this.portsLists.length; i++) {
      if (this.portsLists[i].operation?.value?.id === OPERATIONS.DISCHARGING) {
        dischargeFound = true;
      }
      if (dischargeFound && this.portsLists[i].operation?.value?.id === OPERATIONS.LOADING) {
        orderError = true;
        break;
      }
    }
    if (this.portsLists[this.portsLists.length - 1].operation.value?.id !== OPERATIONS.DISCHARGING) {
      orderError = true;
    }
    return orderError;
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
    if (event.index > 0) {
      if (this.portsLists[event.index - 1].isAdd) {
        const translationKeys = await this.translateService.get(['PORT_ROW_ADD_ERROR', 'PORT_ROW_ADD_ERROR_DETAILS']).toPromise();
        this.messageService.add({ severity: 'warn', summary: translationKeys['PORT_ROW_ADD_ERROR'], detail: translationKeys['PORT_ROW_ADD_ERROR_DETAILS'] });
        return;
      }
    }
    const form = this.row(event.index);
    const valueIndex = this.portsLists.findIndex(port => port?.storeKey === event?.data?.storeKey);
    if (form.valid) {
      this.portUpdate.emit(true);
      const res = await this.dischargeStudyDetailsApiService.setPort(this.dischargeStudyDetailsTransformationService.getPortAsValue(this.portsLists[valueIndex]), this.vesselId, this.voyageId, this.dischargeStudyId, (this.portsForm.valid && !this.portOrderValidation()));
      if (res) {
        this.portsListSaved = JSON.parse(JSON.stringify(this.portsLists));
        this.dischargeStudyDetailsTransformationService.portUpdated();
        this.portsLists[valueIndex].isAdd = false;

        for (const key in this.portsLists[valueIndex]) {
          if (this.portsLists[valueIndex]?.hasOwnProperty(key) && this.portsLists[valueIndex][key]?.hasOwnProperty('_isEditMode')) {
            this.portsLists[valueIndex][key].isEditMode = false;
          }
        }
        this.portsLists = [...this.portsLists];
        this.portsForm.updateValueAndValidity();
        if (this.portOrderValidation()) {
          const translationKeys = await this.translateService.get(['PORT_ROTATION_ERROR_DETAILS_REORDER', 'PORT_ROTATION_WARN']).toPromise();
          this.messageService.add({ key: 'isPortOrderValid' , severity: 'warn', summary: translationKeys['PORT_ROTATION_WARN'], detail: translationKeys['PORT_ROTATION_ERROR_DETAILS_REORDER'], sticky: true, closable: true });
        } else {
          this.messageService.clear('isPortOrderValid');
        }
        this.dischargeStudyDetailsTransformationService.setPortValidity(this.portsForm.valid && this.portsLists?.filter(item => !item?.isAdd).length > 0 && !this.portOrderValidation());
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
    if (!event?.data?.isAdd && event?.data?.id === 0) {
      this.getPorts();
    }
    
    if (event?.data?.isDelete) {
      if (!event?.data?.isAdd) {
        const translationKeys = await this.translateService.get(['DISCHARGE_STUDY_PORTS_DELETE_SUMMARY', 'DISCHARGE_STUDY_PORT_CHANGE_CONFIRM_DETAILS', 'DISCHARGE_STUDY_PORT_CHANGE_CONFIRM_LABEL', 'DISCHARGE_STUDY_PORT_CHANGE_REJECT_LABEL']).toPromise();

        this.confirmationService.confirm({
          key: 'confirmation-alert',
          header: translationKeys['DISCHARGE_STUDY_PORTS_DELETE_SUMMARY'],
          message: translationKeys['DISCHARGE_STUDY_PORT_CHANGE_CONFIRM_DETAILS'],
          icon: 'pi pi-exclamation-triangle',
          acceptLabel: translationKeys['DISCHARGE_STUDY_PORT_CHANGE_CONFIRM_LABEL'],
          acceptIcon: 'pi',
          acceptButtonStyleClass: 'btn btn-main mr-5',
          rejectVisible: true,
          rejectLabel: translationKeys['DISCHARGE_STUDY_PORT_CHANGE_REJECT_LABEL'],
          rejectIcon: 'pi',
          rejectButtonStyleClass: 'btn btn-main',
          accept: async () => {
            await this.removePortFromList(event);
          },
        });
      }
      else {
        await this.removePortFromList(event);
      }
    }
  }


  /**
   * Method to remove port from list
   *
   * @param {*} event
   * @memberof PortsComponent
   */
  async removePortFromList(event) {
    if (event?.data?.isAdd) {
      this.portsLists.splice(event.index, 1);
      this.portsLists = [...this.portsLists];
    } else {
      this.portUpdate.emit(true);
      const valueIndex = this.portsLists.findIndex(port => port?.storeKey === event?.data?.storeKey);
      const res = await this.dischargeStudyDetailsApiService.setPort(this.dischargeStudyDetailsTransformationService.getPortAsValue(this.portsLists[valueIndex]), this.vesselId, this.voyageId, this.dischargeStudyId, (this.portsForm.valid && !this.portOrderValidation()));
      if (res) {
        this.portsLists.splice(event.index, 1);
        this.portsLists = [...this.portsLists];
        for (let i = 0; i < this.portsLists.length; i++) {
          this.portsLists[i].portOrder = i + 1;
          this.portsLists[i].slNo = i + 1;
          if (this.portsLists[i].id !== 0) {
            await this.dischargeStudyDetailsApiService.setPort(this.dischargeStudyDetailsTransformationService.getPortAsValue(this.portsLists[i]), this.vesselId, this.voyageId, this.dischargeStudyId, (this.portsForm.valid && !this.portOrderValidation()));
          }
        }
      }
    }
    const formArray = <FormArray>this.portsForm.get('dataTable');
    formArray.removeAt(event.index)
    setTimeout(() => {
      this.updateFormValidity(formArray)
      if (!this.portOrderValidation()) {
        this.messageService.clear('isPortOrderValid');
      }
    }, 500);
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
   * @param {*} event
   * @memberof PortsComponent
 */
  async onRowReorder(event: any) {
    const translationKeys = await this.translateService.get(['DISCHARGE_STUDY_PORTS_REORDER_SUMMARY', 'DISCHARGE_STUDY_PORT_CHANGE_CONFIRM_DETAILS', 'DISCHARGE_STUDY_PORT_CHANGE_CONFIRM_LABEL', 'DISCHARGE_STUDY_PORT_CHANGE_REJECT_LABEL']).toPromise();

        this.confirmationService.confirm({
          key: 'confirmation-alert',
          header: translationKeys['DISCHARGE_STUDY_PORTS_REORDER_SUMMARY'],
          message: translationKeys['DISCHARGE_STUDY_PORT_CHANGE_CONFIRM_DETAILS'],
          icon: 'pi pi-exclamation-triangle',
          acceptLabel: translationKeys['DISCHARGE_STUDY_PORT_CHANGE_CONFIRM_LABEL'],
          acceptIcon: 'pi',
          acceptButtonStyleClass: 'btn btn-main mr-5',
          rejectVisible: true,
          rejectLabel: translationKeys['DISCHARGE_STUDY_PORT_CHANGE_REJECT_LABEL'],
          rejectIcon: 'pi',
          rejectButtonStyleClass: 'btn btn-main',
          accept: async () => {
              await this.portReorder(event);
          },
          reject: () => {
            const dropData = this.portsLists[event.dropIndex];
            this.portsLists.splice(event.dropIndex, 1);
            this.portsLists.splice(event.dragIndex, 0, dropData);
          }
        });
  }

  /**
 * Event handler for row re order complete event
 *
 * @memberof PortsComponent
 */
  async portReorder(event) {
    const isPortOrderCorrect = this.isPortOrderCorrect(event.dropIndex, event.dragIndex);


    if (!isPortOrderCorrect) {
      const dropData = this.portsLists[event.dropIndex];
      this.portsLists.splice(event.dropIndex, 1);
      this.portsLists.splice(event.dragIndex, 0, dropData);
      const translationKeys = await this.translateService.get(['PORT_ROTATION_ERROR_DETAILS_REORDER', 'PORT_ROTATION_WARN']).toPromise();
      this.messageService.add({ key: 'isPortOrderValid' , severity: 'warn', summary: translationKeys['PORT_ROTATION_WARN'], detail: translationKeys['PORT_ROTATION_ERROR_DETAILS_REORDER'], sticky: true, closable: true });
      return;
    } else {
      this.messageService.clear('isPortOrderValid');
    }
    this.ngxSpinnerService.show();
    if (this.portsLists[event.dragIndex]?.id !== 0 && this.portsLists[event.dropIndex]?.id !== 0) {
      for (let i = 0; i < this.portsLists.length; i++) {
        const form = this.row(i);
        this.portsLists[i].portOrder = i + 1;
        this.portsLists[i].slNo = i + 1;
      }
      const portListArray = this.portsLists.map((ports, index) =>
        this.initPortsFormGroup(ports, index)
      );
      this.portsForm = this.fb.group({
        dataTable: this.fb.array([...portListArray])
      });
      setTimeout(async () => {
        this.updateFormValidity(portListArray);
        this.bunkeringDIsableEnable(event);
        for (let i = 0; i < this.portsLists.length; i++) {
          if (this.portsLists[i].id !== 0) {
            await this.dischargeStudyDetailsApiService.setPort(this.dischargeStudyDetailsTransformationService.getPortAsValue(this.portsLists[i]), this.vesselId, this.voyageId, this.dischargeStudyId, (this.portsForm.valid && !this.portOrderValidation()));
          }
        }
      }, 500);
    }
    this.ngxSpinnerService.hide();
  }

  /**
   * Event handler for bunkering eta/etd enable and disable
   *
   * @memberof PortsComponent
   * @param {Event}
   */
  bunkeringDIsableEnable(event, recursive?, index?) {
    if (recursive) {
      event.dropIndex = index;
      event.dragIndex = null;
    }
    let previousCheck = false, nextCheck = false;
    const previousRow = this.portsLists[event.dropIndex - 1] ? this.portsLists[event.dropIndex - 1] : null;
    if (previousRow && previousRow.port.value.id === this.portsLists[event.dropIndex].port.value.id && [OPERATIONS.DISCHARGING, OPERATIONS.BUNKERING].includes(this.portsLists[event.dropIndex].operation.value.id) && [OPERATIONS.BUNKERING, OPERATIONS.DISCHARGING].includes(previousRow.operation.value.id)) {
      let rowIndex = null;
      if (this.portsLists[event.dropIndex].operation.value.id === OPERATIONS.BUNKERING) {
        rowIndex = event.dropIndex;
        this.portsLists[rowIndex].eta.value = this.portsLists[event.dropIndex - 1].eta.value;
        this.portsLists[rowIndex].etd.value = this.portsLists[event.dropIndex - 1].etd.value;
      }
      if (this.portsLists[event.dropIndex - 1].operation.value.id === OPERATIONS.BUNKERING) {
        rowIndex = event.dropIndex - 1;
        this.portsLists[rowIndex].eta.value = this.portsLists[event.dropIndex].eta.value;
        this.portsLists[rowIndex].etd.value = this.portsLists[event.dropIndex].etd.value;
      }

      this.enableDisableBunkeringControl(false, rowIndex);
    } else {
      previousCheck = true;
    }
    const nextRow = this.portsLists[event.dropIndex + 1] ? this.portsLists[event.dropIndex + 1] : null;
    if (nextRow && nextRow.port.value.id === this.portsLists[event.dropIndex].port.value.id && [OPERATIONS.DISCHARGING, OPERATIONS.BUNKERING].includes(this.portsLists[event.dropIndex].operation.value.id) && [OPERATIONS.BUNKERING, OPERATIONS.DISCHARGING].includes(nextRow.operation.value.id)) {
      let rowIndex = null;
      if (this.portsLists[event.dropIndex].operation.value.id === OPERATIONS.BUNKERING) {
        rowIndex = event.dropIndex;
        this.portsLists[rowIndex].eta.value = this.portsLists[event.dropIndex + 1].eta.value;
        this.portsLists[rowIndex].etd.value = this.portsLists[event.dropIndex + 1].etd.value;
      }
      if (this.portsLists[event.dropIndex + 1].operation.value.id === OPERATIONS.BUNKERING) {
        rowIndex = event.dropIndex + 1;
        this.portsLists[rowIndex].eta.value = this.portsLists[event.dropIndex].eta.value;
        this.portsLists[rowIndex].etd.value = this.portsLists[event.dropIndex].etd.value;
      }
      this.enableDisableBunkeringControl(false, rowIndex);
    } else {
      nextCheck = true;
    }

    if (this.portsLists[event.dropIndex].operation.value.id !== OPERATIONS.BUNKERING && previousRow && nextRow) {
      if (previousRow.port.value.id === nextRow.port.value.id && [OPERATIONS.BUNKERING, OPERATIONS.DISCHARGING].includes(previousRow.operation.value.id) && [OPERATIONS.BUNKERING, OPERATIONS.DISCHARGING].includes(nextRow.operation.value.id)) {
        let rowIndex = null;
        if (previousRow.operation.value.id === OPERATIONS.BUNKERING) {
          rowIndex = event.dropIndex - 1;
          this.portsLists[rowIndex].eta.value = this.portsLists[event.dropIndex + 1].eta.value;
          this.portsLists[rowIndex].etd.value = this.portsLists[event.dropIndex + 1].etd.value;
        }
        if (nextRow.operation.value.id === OPERATIONS.BUNKERING) {
          rowIndex = event.dropIndex + 1;
          this.portsLists[rowIndex].eta.value = this.portsLists[event.dropIndex - 1].eta.value;
          this.portsLists[rowIndex].etd.value = this.portsLists[event.dropIndex - 1].etd.value;
        }
        this.enableDisableBunkeringControl(true, rowIndex);
      }
    }

    if (event.dragIndex && [OPERATIONS.BUNKERING, OPERATIONS.DISCHARGING].includes(this.portsLists[event.dragIndex].operation.value.id)) {
      this.bunkeringDIsableEnable({}, true, event.dragIndex);
    }

    if (this.portsLists[event.dropIndex].operation.value.id === OPERATIONS.BUNKERING && previousCheck && nextCheck) {
      this.enableDisableBunkeringControl(true, event.dropIndex);
    }
  }

  /**
   * Event handler for enable/disable bunkering control
   *
   * @param editable
   * @param index
   * @memberof PortsComponent
   */
  enableDisableBunkeringControl(editable, index) {
    this.portsLists[index].eta.isEditable = editable;
    this.portsLists[index].etd.isEditable = editable;
    this.updateValidityAndEditMode(index, 'eta');
    this.updateValidityAndEditMode(index, 'etd');
    const form = this.row(index);
    if (editable) {
      form.controls.eta.enable();
      form.controls.etd.enable();
    } else {
      form.controls.eta.disable();
      form.controls.etd.disable();
    }
  }

  /**
 * Event handler for row re order complete event
 *
 * @memberof PortsComponent
 */
  isPortOrderCorrect(dropIndex, dragIndex) {
    let isPortOrderCorrect = true;
    if (this.portsLists[dropIndex]?.operation?.value?.operationName === 'Loading') {
      for (let i = dropIndex; 0 <= i; i--) {
        if (this.portsLists[i]?.operation?.value?.operationName === 'Discharging') {
          isPortOrderCorrect = false;
        }
      }
    } else if (this.portsLists[dropIndex]?.operation?.value?.operationName === 'Discharging') {
      for (let i = dropIndex; i < this.portsLists?.length; i++) {
        if (this.portsLists[i]?.operation?.value?.operationName === 'Loading') {
          isPortOrderCorrect = false;
        }
      }
    }
    if (dropIndex === (this.portsLists.length - 1) && this.portsLists[dropIndex]?.operation?.value?.operationName !== 'Discharging') {
      isPortOrderCorrect = false;
    }
    if (dragIndex === (this.portsLists.length - 1) && this.portsLists[this.portsLists.length - 1]?.operation?.value?.operationName !== 'Discharging') {
      isPortOrderCorrect = false;
    }
    return isPortOrderCorrect;
  }

  /**
   * Handler for filter event
   *
   * @param {IDataTableFilterEvent} event
   * @memberof PortsComponent
   */
  onFilter(event: IDataTableFilterEvent) {
    this.dataTableLoading = true;
    const portListArray = event?.filteredValue?.map((ports, index) => this.initPortsFormGroup(ports, index));
    this.portsForm.setControl('dataTable', this.fb.array([...portListArray]));
    this.dataTableLoading = false;
  }

  /**
   * Handler for datatable sort event
   *
   * @param {IDataTableSortEvent} event
   * @memberof PortsComponent
   */
  onSort(event: IDataTableSortEvent) {
    this.dataTableLoading = true;
    const portListArray = event?.data?.map((ports, index) => this.initPortsFormGroup(ports, index));
    this.portsForm.setControl('dataTable', this.fb.array([...portListArray]));
    this.dataTableLoading = false;
  }

  /**
 * Handler for update ports form validity
 *
 * @memberof PortsComponent
 */
  updateFormValidity(portListArray) {
    for (let i = 0; i < portListArray.length; i++) {
      const form = this.row(i);
      const invalidFormControls = this.findInvalidControlsRecursive(form);
      if (invalidFormControls.length) {
        if (form.value.eta === undefined) {
          this.portsLists[i]['eta'].isEditMode = true;
        }
        if (form.value.etd === undefined) {
          this.portsLists[i]['etd'].isEditMode = true;
        }
      }
      invalidFormControls.forEach((key) => {
        this.portsLists[i][key].isEditMode = true;
        form.get(key).markAsTouched();
      });
      this.updateValuesIfBunkering(this.portsLists[i], form, i);
      form.updateValueAndValidity();
      this.portsForm.updateValueAndValidity();
    }
    this.dischargeStudyDetailsTransformationService.setPortValidity(this.portsForm.valid && this.portsLists?.filter(item => !item?.isAdd).length > 0 && !this.portOrderValidation());
  }

  /**
   * Method to update validity and edit mode
   *
   * @memberof PortsComponent
   */
  updateValidityAndEditMode(index: number, key: string) {
    if ((key === 'eta' || key === 'etd') && !this.dischargeStudyDetailsTransformationService.isEtaEtdViewable(this.portEtaEtdPermission, this.portsLists[index].isAdd)) {
      return;
    }
    const field = this.field(index, key)
    if (field) {
      field.updateValueAndValidity();
      field.markAsTouched();
      const row = this.portsLists[index];
      row[key].isEditMode = field.invalid && field.enabled || (row.isAdd && key !== 'eta' && key !== 'etd')
    }
  }

  /**
   * Method to update eta and etd if port is bunkering
   *
   * @memberof PortsComponent
   */
  updateValuesIfBunkering(data, form, index) {
    if (!this.dischargeStudyDetailsTransformationService.isEtaEtdViewable(this.portEtaEtdPermission, this.portsLists[index].isAdd)) {
      return;
    }
    if (data && data.operation?.value?.id === OPERATIONS.BUNKERING && data.port.value) {
      const portId = Number(data.port.value.id);
      if (index > 0) {
        const row = this.portsLists[index - 1]
        if (row.port?.value?.id === portId && [OPERATIONS.LOADING, OPERATIONS.DISCHARGING].includes(row.operation?.value?.id)) {
          const loadingPortData = row;
          const loadingPortForm = this.row(Number(loadingPortData.slNo - 1));
          form.controls.eta.setValue(loadingPortForm.value.eta ?? null);
          form.controls.etd.setValue(loadingPortForm.value.etd ?? null);
          form.controls.eta.disable();
          form.controls.etd.disable();
          this.portsLists[index].eta.value = loadingPortData.eta.value ? loadingPortData.eta.value : '';
          this.portsLists[index].etd.value = loadingPortData.etd.value ? loadingPortData.etd.value : '';
          this.portsLists[index].eta.isEditable = false;
          this.portsLists[index].etd.isEditable = false;
          this.updateValidityAndEditMode(index, 'eta');
          this.updateValidityAndEditMode(index, 'etd');
          return;
        } else if (this.portsLists[index + 1] && portId === this.portsLists[index + 1].port?.value?.id && [OPERATIONS.LOADING, OPERATIONS.DISCHARGING].includes(this.portsLists[index + 1].operation?.value?.id)) {
          form.controls.eta.disable();
          form.controls.etd.disable();
          this.portsLists[index].eta.isEditable = false;
          this.portsLists[index].etd.isEditable = false;
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
      this.portsLists[index].eta.isEditable = true;
      this.portsLists[index].etd.isEditable = true;
      this.updateValidityAndEditMode(index, 'eta');
      this.updateValidityAndEditMode(index, 'etd');
    }
  }

  /**
   * Method to get validators of a key
   *
   * @memberof PortsComponent
   */
  getValidators(key, index: number, isRequired: boolean) {
    const required = isRequired ? [Validators.required] : []
    switch (key) {
      case 'eta':
        return [...required, portDateRangeValidator, portDateCompareValidator('etd', '<'), portEtaEtdValidator('eta', index)];
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
   * @memberof PortRotationRibbonComponent
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
   * function to convert string to Date object
   *
   * @param {string} dateTime
   * @return {*}  {Date}
   * @memberof PortsComponent
   */
  dateStringToDate(dateTime: string, layCan?: boolean): Date {
    if (dateTime) {
      const _dateTime = layCan ? moment(dateTime, AppConfigurationService.settings?.dateFormat.split(' ')[0]).format('DD-MM-YYYY HH:mm') : moment(dateTime.slice(0, 17)).format('DD-MM-YYYY HH:mm');
      const formatOptions: IDateTimeFormatOptions = { stringToDate: true };
      return this.timeZoneTransformationService.formatDateTime(_dateTime, formatOptions);
    }
  }

}


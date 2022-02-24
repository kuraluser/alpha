import { AppConfigurationService } from './../../../../shared/services/app-configuration/app-configuration.service';
import { Component, ElementRef, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { IVessel } from '../../models/vessel-details.model';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { PortRotationService } from '../../services/port-rotation.service';
import { TranslateService } from '@ngx-translate/core';
import { MessageService } from 'primeng/api';
import { IEditPortRotation, IOperations, IPortsDetailsResponse, IVoyagePortDetails, VOYAGE_STATUS } from '../../../core/models/common.model';
import { Voyage } from '../../../core/models/common.model';
import { Subscription, fromEvent, Observable } from 'rxjs';
import { OnDestroy } from '@angular/core';
import { portEtaEtdValidator } from '../../directives/port-eta-etd-validator.directive';
import { portTimeValidator } from '../../directives/port-time-validator.directive';
import { IDateTimeFormatOptions, ITimeZone } from './../../../../shared/models/common.model';
import { TimeZoneTransformationService } from './../../../../shared/services/time-zone-conversion/time-zone-transformation.service';
import * as moment from 'moment';
import { IPermission } from '../../../../shared/models/user-profile.model';

/**
 * Component class of PortRotationRibbonComponent
 *
 * @export
 * @class PortRotationRibbonComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-port-rotation-ribbon',
  templateUrl: './port-rotation-ribbon.component.html',
  styleUrls: ['./port-rotation-ribbon.component.scss']
})
export class PortRotationRibbonComponent implements OnInit, OnDestroy {

  @Input() vesselDetails: IVessel;
  selectedPortIndex = -1;
  @Input()

  get voyageDetails() {
    return this._voyageDetails;
  }
  set voyageDetails(value: Voyage) {
    this._voyageDetails = value;
    this.voyageId = value.id;
    this.loadableStudyId = value.confirmedLoadableStudyId;
    this.dischargeStudyId = value.confirmedDischargeStudyId;
    this.getPortRotationRibbonData();
  }
  @Input() isDeletable = false;

  @Input() groupSelection = false;

  @Input() showOperationType = false;
  @Input() etaEtdPermision: IPermission;

  @Output() portSelection = new EventEmitter<IVoyagePortDetails>();

  voyageId: number;
  loadableStudyId: number;
  dischargeStudyId: number;
  portList: IEditPortRotation[] = [];
  portRotationForm: FormGroup;
  maxDate = new Date();
  isCurrentPortSelected = false;
  errorMesages: any;
  carouselDateFormat: string;
  portOrderSubscription$: Subscription;
  responsiveOptions: { breakpoint: string; numVisible: number; numScroll: number; }[];
  VOYAGE_STATUS = VOYAGE_STATUS;
  resizeObservable$: Observable<Event>;
  resizeSubscription$: Subscription;
  numVisible: number;
  portCarousel: IEditPortRotation[] = [];
  timeZoneList: ITimeZone[];
  operations: IOperations[];
  selectedPort: IEditPortRotation;

  // private fields
  private _voyageDetails: Voyage;
  private _currentPort: number;



  constructor(private ngxSpinnerService: NgxSpinnerService,
    private fb: FormBuilder,
    private editPortRotationApiService: PortRotationService,
    private timeZoneTransformationService: TimeZoneTransformationService,
    private translateService: TranslateService,
    private messageService: MessageService,
    private elementRef: ElementRef) { }

  /**
   * Component lifecycle ngOnit
   *
   * @returns {Promise<void>}
   * @memberof PortRotationRibbonComponent
   */
  async ngOnInit(): Promise<void> {
    this._currentPort = -1;
    this.resizeObservable$ = fromEvent(window, 'resize')
    this.carouselDateFormat = this.timeZoneTransformationService.getMappedConfigurationDateFormat(AppConfigurationService.settings?.dateFormat)
    this.resizeSubscription$ = this.resizeObservable$.subscribe((evt) => {
      this.setCarouselNumVisble(evt.target['innerWidth']);
    })
    this.portOrderSubscription$ = this.editPortRotationApiService.portOrderChange.subscribe((data) => {
      if (data) {
        this.getPortRotationRibbonData();
      }
    })
    this.responsiveOptions = [
      {
        breakpoint: '1921px',
        numVisible: 8,
        numScroll: 1
      },
      {
        breakpoint: '1600px',
        numVisible: 7,
        numScroll: 1
      },
      {
        breakpoint: '1400px',
        numVisible: 6,
        numScroll: 1
      },
      {
        breakpoint: '1200px',
        numVisible: 5,
        numScroll: 1
      },
      {
        breakpoint: '1024px',
        numVisible: 4,
        numScroll: 1
      }
    ];
    this.timeZoneList = await this.timeZoneTransformationService.getTimeZoneList().toPromise();
  }

  /**
  * set carousel num visible
  * @param {number} innerWidth
  */
  setCarouselNumVisble(innerWidth: number) {
    if (innerWidth > 1921) {
      this.numVisible = 8;
    } else if (innerWidth < 1600 && innerWidth > 1400) {
      this.numVisible = 6;
    } else if (innerWidth < 1400 && innerWidth > 1200) {
      this.numVisible = 4;
    } else if (innerWidth < 1200 && innerWidth > 1024) {
      this.numVisible = 4;
    } else {
      this.numVisible = 4;
    }
  }

  /**
   * Method to select port
   * @param port
   */
  onClickPort(port: IEditPortRotation) {
    this.selectedPort = port;
    const portDetails: IVoyagePortDetails = {
      "portOrder": port.portOrder,
      "operationType": port.type === 'Arrival' ? "ARR" : "DEP",
      "portId": port.portId,
      "portRotationId": port.id,
      "operationId": port?.operationId,
      'name': port?.name,
      'portType': port.portType,
      'version': port.version,
      'sequenceNumber': port?.sequenceNumber
    }
    const index = this.portList.indexOf(port);
    this.resetPreviousPort(index);
    if (!port.isFutureDate) {
      this.portSelection.emit(portDetails);
    }
    this.portList.map(ports => {
      ports.isFutureDate = false;
      ports.isSelected = false;
      port.isFocused = false;
    });
    port.isSelected = true;
    port.isFocused = true;
    if ((port.type === 'Arrival' && port.etaActual) || (port.type === 'Departure' && port.etdActual)) {
      port.isFutureDate = false;
    } else {
      port.isFutureDate = true;
    }
    this.setPortSelection(port);
  }

  /**
   * Enable editing
   * @param port
   * @param field
   */
  editPort(event, port: IEditPortRotation, field: string) {
    if (![VOYAGE_STATUS.CLOSE].includes(this.voyageDetails?.statusId) && this.isDischargeStarted(port)) {
      const form = this.row(this.portList.indexOf(port));
      this.setInvalid(port, form)
      let isDateEditable;
      isDateEditable = this.etaEtdPermision?.edit !== undefined && !this.etaEtdPermision?.edit ?  false : true;
      if (port.isFutureDate === true) {
        if (field === 'date' && isDateEditable) {
          port.isDateEditable = true;
        } else if (field === 'time' && isDateEditable) {
          port.isTimeEditable = true;
        } else if (field === 'distance') {
          port.isDistanceEditable = true;
        }
      }
      form.markAllAsTouched();
    }
  }

  /**
  * Method to get current position of the ship
  */
  currentPosition() {
    let currentPort = -1;
    for (let index = 0; index < this.portList.length; index++) {
      const port = this.portList[index]
      if ((port.type === 'Arrival' && port.etaActual) || (port.type === 'Departure' && port.etdActual)) {
        currentPort = index;
      }
    }
    if(currentPort !== -1) {
      this._currentPort = currentPort;
      this.portList[currentPort].currentPort = true;
    }
  }
  /**
   * Method to delete port
   * @param port
   */
  async deleteRow(port) {
    const translationKeys = await this.translateService.get(['PORT_ROTATION_RIBBON_SUCCESS', 'PORT_ROTATION_RIBBON_DELETED_SUCCESSFULLY', 'PORT_ROTATION_DELETE_ERROR', 'PORT_ROTATION_DELETE_STATUS_ERROR']).toPromise();
    this.portList.splice(this.portList.indexOf(port), 1);
    for (let i = 0; i < this.portList.length; i++) {
      if (this.portList[i].id === port.id) {
        this.portList.splice(i, 1);
      }
    }
    try {
      const result = await this.editPortRotationApiService.deletePort(port, this.vesselDetails.id, this.voyageId, this.loadableStudyId).toPromise();
      if (result.responseStatus === "200") {
        this.messageService.add({ severity: 'success', summary: translationKeys['PORT_ROTATION_RIBBON_SUCCESS'], detail: translationKeys['PORT_ROTATION_RIBBON_DELETED_SUCCESSFULLY'] });
      }
    }
    catch (errorResponse) {
      if (errorResponse?.error?.errorCode === 'ERR-RICO-110') {
        this.messageService.add({ severity: 'error', summary: translationKeys['PORT_ROTATION_DELETE_ERROR'], detail: translationKeys['PORT_ROTATION_DELETE_STATUS_ERROR'], life: 10000 });
      }
    }
  }

  /**
   * Initialize port rotation form
   *
   * @private
   * @memberof PortRotationRibbonComponent
   */
  private initPortRotationArray() {
    const portRibbonDetailsArray = this.portList?.map((port, index) => this.initPortFormGroup(port, index));
    this.portRotationForm = this.fb.group({
      portsData: this.fb.array([...portRibbonDetailsArray])
    });
  }

  /**
   * Initialize port rotation form group
   *
   * @private
   * @memberof PortRotationRibbonComponent
   */
  initPortFormGroup(newPortList: IEditPortRotation, index: number): FormGroup {
    let date = this.convertToDate(newPortList.type === "Arrival" ? newPortList?.eta : newPortList?.etd);
    date = this.convertToPortBasedTimeZone(date, newPortList?.portTimezoneId, 'utcToPort');
    let dateActual = this.convertToDate(newPortList.type === "Arrival" ? newPortList?.etaActual : newPortList?.etdActual);
    dateActual = this.convertToPortBasedTimeZone(dateActual, newPortList?.portTimezoneId, 'utcToPort');
    const portTimezoneData = this.getTimezoneData(newPortList?.portTimezoneId);
    return this.fb.group({
      port: this.fb.control(newPortList?.name, [Validators.required]),
      portTimezoneOffset: this.fb.control(portTimezoneData?.offsetValue, []),
      date: this.fb.control(dateActual ? dateActual : date, [Validators.required, portEtaEtdValidator(index)]),
      time: this.fb.control(dateActual ? dateActual : date, [Validators.required, portTimeValidator(index)]),
      distance: this.fb.control(newPortList?.distanceBetweenPorts ? newPortList?.distanceBetweenPorts : 0, [Validators.required])
    });
  }

  /**
   * Method to check discharge study started
   *
   * @memberof PortRotationRibbonComponent
   */
  isDischargeStarted(port: IEditPortRotation) {
    const status = this.voyageDetails.isDischargeStarted && port.portType === 'LOADING' ? false : true;
    return status;
  }

  /**
   * Save port details
   * @param port
   */

  async save(port: IEditPortRotation) {
    // this.updateForm(port);
    const index = this.portList.indexOf(port);
    const form = <FormGroup>(<FormArray>this.portRotationForm.get('portsData')).at(index);
    form.markAllAsTouched()
    form.updateValueAndValidity();
    const translationKeys = await this.translateService.get(['PORT_ROTATION_RIBBON_WARNING','PORT_ROTATION_RIBBON_INVALID_DATE','PORT_ROTATION_RIBBON_SUCCESS', 'PORT_ROTATION_RIBBON_SAVED_SUCCESSFULLY', 'PORT_ROTATION_RIBBON_INVALID', 'PORT_ROTATION_RIBBON_INVALID_DATA', 'PORT_ROTATION_UPDATE_ERROR', 'PORT_ROTATION_UPDATE_STATUS_ERROR']).toPromise();
    if (form.valid) {
      if(moment(form.controls['date'].value).isAfter(this.maxDate))  {
        this.messageService.add({ severity: 'warn', summary: translationKeys['PORT_ROTATION_RIBBON_WARNING'], detail: translationKeys['PORT_ROTATION_RIBBON_INVALID_DATE'] });
        return;
      }
      if (port.type === 'Arrival') {
        const newEtaActual = this.timeZoneTransformationService.formatDateTime(form.controls['date'].value) + ' ' + this.timeZoneTransformationService.formatDateTime(form.controls['time'].value, { customFormat: 'HH:mm' });
        port.etaActual = this.convertToPortBasedTimeZone(newEtaActual, port?.portTimezoneId, 'portToUTC');
      }
      else {
        const newEtdActual = this.timeZoneTransformationService.formatDateTime(form.controls['date'].value) + ' ' + this.timeZoneTransformationService.formatDateTime(form.controls['time'].value, { customFormat: 'HH:mm' });
        port.etdActual = this.convertToPortBasedTimeZone(newEtdActual, port?.portTimezoneId, 'portToUTC');
        port.distanceBetweenPorts = form.controls['distance'].value;
      }
      try {
        const result = await this.editPortRotationApiService.savePortRotationRibbon(this.vesselDetails.id, this.voyageId, this.loadableStudyId, port).toPromise();
        if (result.responseStatus.status === "200") {
          this.messageService.add({ severity: 'success', summary: translationKeys['PORT_ROTATION_RIBBON_SUCCESS'], detail: translationKeys['PORT_ROTATION_RIBBON_SAVED_SUCCESSFULLY'] });
          let voyageDistance = 0;
          for (let i = 0; i < this.portList?.length; i++) {
            if(this.portList[i]?.type === 'Departure'){
              voyageDistance += this.portList[i].distanceBetweenPorts;
            }
          }
          this.editPortRotationApiService.updateVoyageDistance(voyageDistance);
          if(this._currentPort !== -1) {
            this.portList[this._currentPort].currentPort = false;
          }
          this.currentPosition();
        }
      }
      catch (errorResponse) {
        if (errorResponse?.error?.errorCode === 'ERR-RICO-110') {
          this.messageService.add({ severity: 'error', summary: translationKeys['PORT_ROTATION_UPDATE_ERROR'], detail: translationKeys['PORT_ROTATION_UPDATE_STATUS_ERROR'], life: 10000 });
        }
      }
    }
    else {
      this.setInvalid(port, form)
      this.messageService.add({ severity: 'warn', summary: translationKeys['PORT_ROTATION_RIBBON_INVALID'], detail: translationKeys['PORT_ROTATION_RIBBON_INVALID_DATA'] });
    }
  }

  /**
  * Method to get formcontrol error
  *
  * @param {string} formControlName
  * @returns {ValidationErrors}
  * @memberof PortRotationRibbonComponent
  */
  fieldError(index: number, formControlName: string) {
    const formControl = this.field(formControlName, index);
    return formControl.invalid && (formControl.dirty || formControl.touched) ? formControl.errors : null;
  }

  /**
   * Get form control of portRotationForm
   *
   *
   * @param {string} formControlName
   * @returns {FormControl}
   * @memberof PortRotationRibbonComponent
   */
  field(formControlName: string, formGroupIndex: number): FormControl {
    const formControl = <FormControl>(<FormArray>this.portRotationForm.get('portsData')).at(formGroupIndex).get(formControlName);
    return formControl;
  }

  /**
   * Method for updating form field
   */
  updateForm(port: IEditPortRotation) {
    const form = this.row(this.portList.indexOf(port));
    if (port?.type === 'Arrival') {
      form.controls['date']?.clearValidators();
      form.controls['time']?.clearValidators();
      form.controls['distance']?.clearValidators();
      form.controls['distance']?.updateValueAndValidity();
    }
    else {
      form.controls['date']?.clearValidators();
      form.controls['time']?.clearValidators();
    }
  }

  /**
  * Method for fetching form group
  *
  * @private
  * @param {number} formGroupIndex
  * @returns {FormGroup}
  * @memberof PortRotationRibbonComponent
  */
  private row(formGroupIndex: number): FormGroup {
    const formGroup = <FormGroup>(<FormArray>this.portRotationForm.get('portsData')).at(formGroupIndex);
    return formGroup;
  }
  /**
   * Component lifecycle ngOnDestroy
   *
   * @returns {Promise<void>}
   * @memberof PortRotationRibbonComponent
   */
  ngOnDestroy() {
    this.portOrderSubscription$.unsubscribe();
    this.resizeSubscription$.unsubscribe();
  }
  /**
   * Get port rotation ribbon data
   */
  async getPortRotationRibbonData() {
    this.ngxSpinnerService.show();
    const ports = await this.editPortRotationApiService.getPorts().toPromise();
    const id = this.dischargeStudyId ? this.dischargeStudyId : this.loadableStudyId;
    const portsFormData: IPortsDetailsResponse = await this.editPortRotationApiService.getPortsDetails(this.vesselDetails.id, this.voyageId, id).toPromise();
    this.portCarousel = [];
    let voyageDistance = 0;
    for (let i = 0; i < portsFormData?.portList?.length; i++) {
      voyageDistance += portsFormData?.portList[i].distanceBetweenPorts;
    }
    this.editPortRotationApiService.updateVoyageDistance(voyageDistance);
    const portData: IEditPortRotation[] = portsFormData?.portList?.map(itm => ({
      name : ports.find((item) => (item.id === itm.portId) && item)?.name,
      ...itm
    }));
    this.operations = portsFormData?.operations;
    this.portList = [];
    for (let i = 0; i < portData.length; i++) {
      const operation = this.getOperationLabel(portData[i]?.operationId);

      const portArrival = { ...portData[i] };
      portArrival.isFutureDate = false;
      portArrival.type = 'Arrival';
      portArrival.isTimeEditable = false;
      portArrival.isDateEditable = false;
      portArrival.currentPort = false;
      portArrival.isSelected = false;
      portArrival.isFocused = false;
      portArrival.operation = operation;

      const portDeparture = { ...portData[i] };
      portDeparture.isFutureDate = false;
      portDeparture.type = 'Departure';
      portDeparture.isTimeEditable = false;
      portDeparture.isDateEditable = false;
      portDeparture.isDistanceEditable = false;
      portDeparture.currentPort = false;
      portArrival.isSelected = false;
      portArrival.isFocused = false;
      portDeparture.operation = operation;

      this.portList.push(portArrival);
      this.portList.push(portDeparture);

    }
    this.setPOrtData();
    this.initPortRotationArray();

    this.currentPosition();
    this.errorMesages = this.setValidationErrorMessageForPortRotationRibbon();
    this.setCarouselNumVisble(window.innerWidth);
    setTimeout(() => {
      this.portCarousel = [...this.transformCarouselData(this.portList)];
      this.setPortSelection(this.portCarousel[0]);
    }, 50);
    if (ports && portsFormData) {
      this.ngxSpinnerService.hide();
    }
  }

  /**
   * Get operation type label
   *
   * @param {number} operationId
   * @return {*}
   * @memberof PortRotationRibbonComponent
   */
  getOperationLabel(operationId: number) {
    let operation = '';
    switch (operationId) {
      case 1:
        operation = 'L';
        break;

      case 2:
        operation = 'D';
        break;

      case 3:
        operation = 'B';
        break;

      case 4:
        operation = 'T';
        break;

      case 5:
        operation = 'STSL';
        break;

      case 6:
        operation = 'STSD';
        break;

      default:
        break;
    }
    return operation;
  }

  /**
   * Method to set port data on page load
   */
  setPOrtData() {
    const portDetails: IVoyagePortDetails = {
      "portOrder": this.portList[0].portOrder,
      "operationType": this.portList[0].type === 'Arrival' ? "ARR" : "DEP",
      "portId": this.portList[0].portId,
      "portRotationId": this.portList[0].id,
      "operationId": this.portList[0].operationId,
      'name': this.portList[0].name,
      'portType': this.portList[0].portType,
      'version': this.portList[0].version,
      'sequenceNumber': this.portList[0]?.sequenceNumber
    }
    this.portList[0].isFocused = true;
    this.portList[0].isSelected = true;
    this.selectedPort = this.portList[0];
    this.portSelection.emit(portDetails);
  }

  /**
   * Method called when focusing out of the input
   * @param event
   * @param port
   * @param type
   */
  onBlur(event, port, type) {
    const form = this.row(this.portList.indexOf(port));
    if (form.get(type).valid) {
      switch (type) {
        case 'date':
          form.controls.time.markAsTouched();
          form.controls.time.updateValueAndValidity();
          break;
        case 'time':
          break;
        case 'distance':
          break;
        default:
          break;
      }
    }
    this.setInvalid(port, form)
    setTimeout(() => {
      this.portCarousel = [...this.transformCarouselData(this.portList)];
    }, 50)
  }

  /**
   * function to set current time input
   *
   * @param {*} event
   * @param {*} port
   * @param {*} type
   * @param {*} ref
   * @memberof PortRotationRibbonComponent
   */
  setTimeInput(event, port, type, ref: any): void {
    if (type === 'time' && !ref.value) {
      const time = new Date();
      time.setHours(ref.currentHour, ref.currentMinute);
      const form = this.row(this.portList.indexOf(port));
      form.controls.time.setValue(time);
    }
    ref.hideOverlay();
  }

  /**
  * Convert to date time(dd-mm-yyyy hh:mm)
  *
  */
  convertToDate(value) {
    if (value) {
      const arr = value.toString().split(' ')
      const dateArr = arr[0]?.split('-');
      if (arr[1]) {
        const timeArr = arr[1].split(':')
        if (dateArr.length > 2 && timeArr.length > 1) {
          return new Date(Number(dateArr[2]), Number(dateArr[1]) - 1, Number(dateArr[0]), Number(timeArr[0]), Number(timeArr[1]));
        }
      } else {
        return new Date(Number(dateArr[2]), Number(dateArr[1]) - 1, Number(dateArr[0]))
      }
    }
    return null
  }

  /**
  * Reset previously selected port
  *
  */
  resetPreviousPort(index: number) {
    if (this.selectedPortIndex >= 0 && this.selectedPortIndex !== index) {
      const port = this.portList[this.selectedPortIndex];
      if (port !== undefined) {
        const form = this.row(this.selectedPortIndex)
        let date = this.convertToDate(port.type === "Arrival" ? port?.eta : port?.etd);
        date = this.convertToPortBasedTimeZone(date, port?.portTimezoneId, 'utcToPort');
        let dateActual = this.convertToDate(port.type === "Arrival" ? port?.etaActual : port?.etdActual);
        dateActual = this.convertToPortBasedTimeZone(dateActual, port?.portTimezoneId, 'utcToPort');
        form.controls.date.setValue(dateActual ? dateActual : date)
        form.controls.time.setValue(dateActual ? dateActual : date)
        form.controls.distance.setValue(port?.distanceBetweenPorts ? port?.distanceBetweenPorts : 0)
        port.isDateEditable = false;
        port.isDistanceEditable = false;
        port.isTimeEditable = false;
        setTimeout(() => {
          this.portCarousel = [...this.transformCarouselData(this.portList)];
        }, 50)
      }
    }
    this.selectedPortIndex = index;
  }

  /**
   * Set invalid state
   *
   * @param {*} port
   * @param {*} form
   * @memberof PortRotationRibbonComponent
   */
  setInvalid(port, form) {
    if (port.isFutureDate) {
      let control = form.controls.distance
      port.isDistanceEditable = control.invalid && control.touched;
      control = form.controls.date
      port.isDateEditable = control.invalid && control.touched;
      control = form.controls.time
      port.isTimeEditable = control.invalid && control.touched;
    }
  }

  /**
   * Set validation Error to form control
   */
  setValidationErrorMessageForPortRotationRibbon() {
    return {
      eta: {
        'required': 'PORT_ROTATION_RIBBON_ETA_REQUIRED',
        'maxError': 'PORT_ROTATION_RIBBON_ETA_FAILED_COMPARE_MAX',
        'minError': 'PORT_ROTATION_RIBBON_ETA_FAILED_COMPARE_MIN',
        'compareDateWithPrevious': 'PORT_ROTATION_RIBBON_ETA_FAILED_COMPARE_ETD_DATE'
      },
      etd: {
        'required': 'PORT_ROTATION_RIBBON_ETD_REQUIRED',
        'maxError': 'PORT_ROTATION_RIBBON_ETD_FAILED_COMPARE_MAX',
        'minError': 'PORT_ROTATION_RIBBON_ETD_FAILED_COMPARE_MIN',
        'compareDateWithPrevious': 'PORT_ROTATION_RIBBON_ETD_FAILED_COMPARE_ETA_DATE'
      },
      etaTime: {
        'required': 'PORT_ROTATION_RIBBON_TIME_REQUIRED',
        'maxError': 'PORT_ROTATION_RIBBON_ETA_FAILED_COMPARE_MAX',
        'minError': 'PORT_ROTATION_RIBBON_ETA_FAILED_COMPARE_MIN'
      },
      etdTime: {
        'required': 'PORT_ROTATION_RIBBON_TIME_REQUIRED',
        'maxError': 'PORT_ROTATION_RIBBON_ETD_FAILED_COMPARE_MAX',
        'minError': 'PORT_ROTATION_RIBBON_ETD_FAILED_COMPARE_MIN'
      },
      distance: {
        'required': 'PORT_ROTATION_RIBBON_DISTANCE_REQUIRED',
      }
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
  convertToPortBasedTimeZone(dateTime: Date | string, portTimezoneId: number, convertType?: string): any {
    if (dateTime && portTimezoneId) {
      const selectedTimeZone: ITimeZone = this.getTimezoneData(portTimezoneId);
      const formatOptions: IDateTimeFormatOptions = {
        portLocalFormat: true,
        portTimeZoneOffset: selectedTimeZone?.offsetValue,
        portTimeZoneAbbr: selectedTimeZone?.abbreviation
      };
      if (convertType === 'utcToPort') {
        return this.timeZoneTransformationService.convertToZoneBasedTime(dateTime, false, selectedTimeZone.offsetValue);
      } else if (convertType === 'portToUTC') {
        return this.timeZoneTransformationService.revertZoneTimetoUTC(dateTime, selectedTimeZone.offsetValue);
      } else {
        return this.timeZoneTransformationService.formatDateTime(dateTime, formatOptions);
      }
    }
  }

  /**
   * function to transform carousal date, time, port name & distance
   *
   * @param {IEditPortRotation[]} portCarousal
   * @return {*}  {IEditPortRotation[]}
   * @memberof PortRotationRibbonComponent
   */
  transformCarouselData(portCarousal: IEditPortRotation[]): IEditPortRotation[] {
    return portCarousal.map(port => {
      const formGroup = <FormGroup>(<FormArray>this.portRotationForm.get('portsData')).at(this.portList.indexOf(port)).value;
      const portLocalAbbr = this.convertToPortBasedTimeZone(formGroup['date'], port?.portTimezoneId)?.slice(17);
      port.portDate = portLocalAbbr && moment(formGroup['date']).format(AppConfigurationService.settings?.dateFormat.split(' ')[0]);
      port.portTime = portLocalAbbr && moment(formGroup['time']).format(AppConfigurationService.settings?.dateFormat.split(' ')[1]) + portLocalAbbr;
      return port;
    });
  }

  /**
   * function to return port timezone details.
   *
   * @param {number} timezoneId
   * @return {*}  {ITimeZone}
   * @memberof PortRotationRibbonComponent
   */
  getTimezoneData(timezoneId: number): ITimeZone {
    return this.timeZoneList.find(tz => (tz.id === timezoneId));
  }

  /**
   * Set port selection on port card click
   *
   * @param {IEditPortRotation} port
   * @memberof PortRotationRibbonComponent
   */
  setPortSelection(port: IEditPortRotation) {
    if (this.groupSelection) {
      this.portCarousel = this.portCarousel.map((_port, index) => {
        if (_port?.id === port?.id) {
          _port.isSelected = true;
        }
        return _port;
      });
    }
  }

}

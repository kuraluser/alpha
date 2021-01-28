import { Component, Input, OnInit } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { IVessel } from '../../core/models/vessel-details.model';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { EditPortRotationApiService } from '../services/edit-port-rotation-api.service';
import { TranslateService } from '@ngx-translate/core';
import { MessageService } from 'primeng/api';
import { IEditPortRotation } from '../models/edit-port-rotation.model';
import { IPortsDetailsResponse } from '../../core/models/common.model';
import { Voyage } from '../../core/models/common.model';
import { Subscription } from 'rxjs';
import { OnDestroy } from '@angular/core';
import { VoyageStatusTransformationService } from '../services/voyage-status-transformation.service';
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
  @Input() voyageDetails: Voyage;

  voyageName: string;
  voyageId: number;
  loadableStudyId: number;
  portList: IEditPortRotation[] = [];
  portRotationForm: FormGroup;
  minDate = new Date();
  isCurrentPortSelected = false;
  errorMesages: any;
  isSelected = false;
  portOrderSubscription$: Subscription;
  responsiveOptions: { breakpoint: string; numVisible: number; numScroll: number; }[];



  constructor(private ngxSpinnerService: NgxSpinnerService,
    private fb: FormBuilder,
    private editPortRotationApiService: EditPortRotationApiService,
    private translateService: TranslateService,
    private messageService: MessageService,
    private voyageStatusTransformationService: VoyageStatusTransformationService) {}

  /**
   * Component lifecycle ngOnit
   *
   * @returns {Promise<void>}
   * @memberof PortRotationRibbonComponent
   */
  async ngOnInit(): Promise<void> {
    this.portOrderSubscription$ = this.voyageStatusTransformationService.portOrderChange.subscribe(data => {
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
        breakpoint: '1200',
        numVisible: 5,
        numScroll: 1
      },
      {
        breakpoint: '1024px',
        numVisible: 4,
        numScroll: 1
      }
    ];

    this.getPortRotationRibbonData();
  }
  /**
   * Method to select port
   * @param port 
   */
  onClickArrival(port: IEditPortRotation) {
    this.portList.map(ports => {
      ports.isFutureDate = false;
      ports.isSelected = false;
    });

    port.isSelected = true;
    if (port?.eta) {
      const current = new Date();
      const dateAndTime = (port?.eta)?.split(" ");
      const date = dateAndTime[0];
      const time = dateAndTime[1];
      const newdate = date.split("-").reverse().join("-");
      const date2 = new Date(newdate + ' ' + time);
      const d1 = current.getTime();
      const d2 = date2.getTime();
      if (d1 > d2 || port?.etaActual) {
        port.isFutureDate = false;
      }
      else {
        port.isFutureDate = true;
      }
      this.updateForm(port);
    }
    else {
      port.isFutureDate = true;
    }
  }
  /**
   * Enable editing
   * @param port 
   * @param field 
   */
  editPort(port: IEditPortRotation, field: string) {
    if (port.isFutureDate === true) {
      if (field === 'eta') {
        port.isEtaEditable = true;
      } else if (field === 'etaTime') {
        port.isTimeEditable = true;
      }
      else if (field === 'etd') {
        port.isEtdEditable = true;
      } else if (field === 'etdTime') {
        port.isTimeEditable = true;
      }
      else if (field === 'distance') {
        port.isDistanceEditable = true;
      }
    }
  }

  /**
  * Method to get current position of the ship
  */
  currentPosition() {
    const currentPort: IEditPortRotation[] = [];
    this.portList?.map(port => {
      if (!this.isCurrentPortSelected) {
        if(!port.etaActual){
          currentPort.push(port);
        }
      }
    })
    if(currentPort){
      this.portList[this.portList.indexOf(currentPort[0])-1].currentPort = true;
    }
  }
  /**
   * Method to delete port
   * @param port 
   */
  async deleteRow(port) {
    const translationKeys = await this.translateService.get(['PORT_ROTATION_RIBBON_SUCCESS', 'PORT_ROTATION_RIBBON_DELETED_SUCCESSFULLY']).toPromise();
    this.portList.splice(port.index, 1);
    this.portList = [...this.portList];
    const result = await this.editPortRotationApiService.deletePort(port, this.vesselDetails.id, this.voyageId, this.loadableStudyId).toPromise();
    if (result.responseStatus === "200") {
      this.messageService.add({ severity: 'success', summary: translationKeys['PORT_ROTATION_RIBBON_SUCCESS'], detail: translationKeys['PORT_ROTATION_RIBBON_DELETED_SUCCESSFULLY'] });
    }
  }

  /** 
   * Initialize port rotation form
   *
   * @private
   * @memberof PortRotationRibbonComponent
   */
  private initPortRotationArray() {
    const portRibbonDetailsArray = this.portList?.map(port => this.initPortFormGroup(port));
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
  initPortFormGroup(newPortList: IEditPortRotation): FormGroup {
    return this.fb.group({
      port: this.fb.control(newPortList?.name, [Validators.required]),
      eta: this.fb.control(newPortList?.eta?.split(" ")[0], [Validators.required]),
      etaTime: this.fb.control(newPortList?.eta?.split(" ")[1], [Validators.required]),
      etaActual: this.fb.control(newPortList?.etaActual?.split(" ")[0], [Validators.required]),
      etaActualTime: this.fb.control(newPortList?.etaActual?.split(" ")[1], [Validators.required]),
      etd: this.fb.control(newPortList?.etd?.split(" ")[0], [Validators.required]),
      etdTime: this.fb.control(newPortList?.etd?.split(" ")[1], [Validators.required]),
      etdActual: this.fb.control(newPortList?.etdActual?.split(" ")[0], [Validators.required]),
      etdActualTime: this.fb.control(newPortList?.etdActual?.split(" ")[1], [Validators.required]),
      distance: this.fb.control(newPortList?.distanceBetweenPorts ? newPortList?.distanceBetweenPorts : 0, [Validators.required])

    });
  }

  /**
   *
   * @param type 
   * Get form control value to label
   */
  getControlLabel(port, type) {
    const formGroup = <FormGroup>(<FormArray>this.portRotationForm.get('portsData')).at(this.portList.indexOf(port)).value;
    return formGroup[type];
  }


  /**
   * Save port details
   * @param port 
   */

  async save(port: IEditPortRotation) {
    this.updateForm(port);
    const form = <FormGroup>(<FormArray>this.portRotationForm.get('portsData')).at(this.portList.indexOf(port));
    const translationKeys = await this.translateService.get(['PORT_ROTATION_RIBBON_SUCCESS', 'PORT_ROTATION_RIBBON_SAVED_SUCCESSFULLY']).toPromise();
    if ((<FormGroup>(<FormArray>this.portRotationForm.get('portsData')).at(this.portList.indexOf(port))).valid) {
      if (port.type === 'Arrival') {
        port.eta = this.formatDateTime(form.controls['eta'].value, form.controls['etaTime'].value);
      }
      else {
        port.etd = this.formatDateTime(form.controls['etd'].value, form.controls['etd'].value);
        port.distanceBetweenPorts = form.controls['distance'].value;
      }
      const result = await this.editPortRotationApiService.savePortRotationRibbon(this.vesselDetails.id, this.voyageId, this.loadableStudyId, port).toPromise();
      if (result.responseStatus.status === "200") {
        this.messageService.add({ severity: 'success', summary: translationKeys['PORT_ROTATION_RIBBON_SUCCESS'], detail: translationKeys['PORT_ROTATION_RIBBON_SAVED_SUCCESSFULLY'] });
      }
    }
    else {
      (<FormGroup>(<FormArray>this.portRotationForm.get('portsData')).at(this.portList.indexOf(port))).markAllAsTouched();
    }

  }

  /**
  * Format date time(dd-mm-yyyy hh:mm)
  */
  formatDateTime(date, time) {
    let month = date.getMonth() + 1;
    let day = date.getDate();
    let hour = time.getHours();
    let minute = time.getMinutes();

    if (month < 10) {
      month = '0' + month;
    }

    if (day < 10) {
      day = '0' + day;
    }

    if (hour < 10) {
      hour = '0' + hour;
    }

    if (minute < 10) {
      minute = '0' + minute;
    }
    return day + '-' + month + '-' + date.getFullYear() + ' ' + hour + ':' + minute;
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
      form.controls['etd']?.clearValidators();
      form.controls['etdTime']?.clearValidators();
      form.controls['distance']?.clearValidators();
      form.controls['etd']?.updateValueAndValidity();
      form.controls['etdTime']?.updateValueAndValidity();
      form.controls['distance']?.updateValueAndValidity();

    }
    else {
      form.controls['eta']?.clearValidators();
      form.controls['etaTime']?.clearValidators();
      form.controls['eta']?.updateValueAndValidity();
      form.controls['etaTime']?.updateValueAndValidity();
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
  ngOnDestroy(){
    this.portOrderSubscription$.unsubscribe();
  }
/**
 * Get port rotation ribbon data
 */
  async getPortRotationRibbonData(){
    this.ngxSpinnerService.show();
    this.voyageName = this.voyageDetails?.voyageNo;
    this.voyageId = this.voyageDetails?.id;
    this.loadableStudyId = this.voyageDetails?.confirmedLoadableStudyId;
    const result = await this.editPortRotationApiService.getPorts().toPromise();
    const portsFormData: IPortsDetailsResponse = await this.editPortRotationApiService.getPortsDetails(this.vesselDetails.id, this.voyageId, this.loadableStudyId).toPromise();
    const portData: IEditPortRotation[] = portsFormData?.portList?.map(itm => ({
      ...result.find((item) => (item.id === itm.portId) && item),
      ...itm
    }));
    this.portList = [];
    for (let i = 0; i < portData.length; i++) {

      const portArrival = { ...portData[i] };
      portArrival.isFutureDate = false;
      portArrival.type = 'Arrival';
      portArrival.isEtaEditable = false;
      portArrival.isTimeEditable = false;
      portArrival.isEtdEditable = false;
      portArrival.currentPort = false;
      portArrival.isSelected = false;

      const portDeparture = { ...portData[i] };
      portDeparture.isFutureDate = false;
      portDeparture.type = 'Departure';
      portDeparture.isEtaEditable = false;
      portDeparture.isTimeEditable = false;
      portDeparture.isEtdEditable = false;
      portDeparture.isDistanceEditable = false;
      portDeparture.currentPort = false;
      portArrival.isSelected = false;
     
      this.portList.push(portArrival);
      this.portList.push(portDeparture);

    }
    this.initPortRotationArray();
    this.currentPosition();
    this.errorMesages = this.voyageStatusTransformationService.setValidationErrorMessageForPortRotationRibbon();
    this.ngxSpinnerService.hide();

  }
}

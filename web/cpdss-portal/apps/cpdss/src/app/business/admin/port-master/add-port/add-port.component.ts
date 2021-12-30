import { Component, EventEmitter, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { TranslateService } from '@ngx-translate/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';

import { TimeZoneTransformationService } from '../../../../shared/services/time-zone-conversion/time-zone-transformation.service';
import { PortMasterTransformationService } from '../../services/port-master-transformation.service';
import { PortMasterApiService } from '../../services/port-master-api.service';

import { numberValidator } from '../../../core/directives/number-validator.directive';

import { IValidationErrorMessagesSet } from '../../../../shared/components/validation-error/validation-error.model';
import { ICountry, ITimeZone } from '../../../../shared/models/common.model';
import { IBerthInfo, IPortDetails, IPortsDetailsResponse } from '../../models/port.model';


/**
 * Component Class for Add port
 *
 * @export
 * @class AddPortComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-add-port',
  templateUrl: './add-port.component.html',
  styleUrls: ['./add-port.component.scss']
})
export class AddPortComponent implements OnInit {

  public addPortDetailsForm: FormGroup;
  public errorMessages: IValidationErrorMessagesSet;
  public portId: number;
  public timeZoneList: ITimeZone[];
  public countryList: ICountry[];
  public portDetails: IPortDetails;
  public berthInfo: IBerthInfo[];
  public currentLocation: number[];
  public isVisible = false;

  private ngUnsubscribe: Subject<any> = new Subject();

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    private portMasterTransformationService: PortMasterTransformationService,
    private timeZoneTransformationService: TimeZoneTransformationService,
    private portMasterApiService: PortMasterApiService,
    private messageService: MessageService,
    private translateService: TranslateService,
    private ngxSpinnerService: NgxSpinnerService
  ) { }

  /**
   * Component lifecyle ngoninit
   * @return {Promise<void>}
   * @memberof AddPortComponent
   */
  async ngOnInit() {
    this.ngxSpinnerService.show();
    this.activatedRoute.paramMap.pipe(takeUntil(this.ngUnsubscribe)).subscribe(routeParams => {
      this.portId = Number(routeParams.get('portId'));
    });
    this.timeZoneList = await this.timeZoneTransformationService.getTimeZoneList().toPromise();
    this.countryList = await this.timeZoneTransformationService.getCountries().toPromise();
    this.errorMessages = this.portMasterTransformationService.setValidationErrorMessage();
    if (this.portId) {
      const portDetailsResponse: IPortsDetailsResponse = await this.portMasterApiService.getPortDetailsById(this.portId).toPromise();
      if (portDetailsResponse.responseStatus.status === '200') {
        this.portDetails = this.modifyTimeZoneAndCountryAsObj(portDetailsResponse.portDetails);
        this.berthInfo = portDetailsResponse.portDetails.berthInfo;
      }
    } else {
      this.portDetails = <IPortDetails>{};
      this.berthInfo =[];
    }
    this.initForm(this.portDetails);
    this.ngxSpinnerService.hide();
  }

  /**
   * Method to add selected timezone & country obj's to port details
   *
   * @param {IPortDetails} portObject
   * @return {*}  {*}
   * @memberof AddPortComponent
   */
  modifyTimeZoneAndCountryAsObj(portObject: IPortDetails): any {
    for (const key in portObject) {
      if (Object.prototype.hasOwnProperty.call(portObject, key)) {
        const portObjValue = portObject[key];
        switch (key) {
          case 'timezoneId': portObject['timezoneObj'] = this.timeZoneList.find(tz => (tz.id === portObjValue));
            break;
          case 'countryId': portObject['country'] = this.countryList.find(country => (country.id === portObjValue));
            break;
        }
      }
    }
    portObject.latitude = Number(portObject?.latitude?.split(' ')[0]);
    portObject.longitude = Number(portObject?.longitude?.split(' ')[0]);
    portObject['position'] = (portObject?.latitude && portObject?.longitude) ? [portObject?.latitude, portObject?.longitude] : null;
    this.currentLocation = portObject['position'];
    return portObject;
  }

  /**
   * Method to initialize port master form
   *
   * @param {IPortDetails} portData
   * @memberof AddPortComponent
   */
  initForm(portData: IPortDetails) {
    this.addPortDetailsForm = this.fb.group({
      portName: this.fb.control(portData?.portName, [Validators.required, Validators.maxLength(100)]),
      portCode: this.fb.control(portData?.portCode, [Validators.required, Validators.maxLength(10)]),
      timeZone: this.fb.control(portData?.timezoneObj, [Validators.required]),
      country: this.fb.control(portData?.country, [Validators.required]),
      tideHeightHigh: this.fb.control(portData?.tideHeightHigh, [Validators.required, numberValidator(2, 4, false)]),
      tideHeightLow: this.fb.control(portData?.tideHeightLow, [Validators.required, numberValidator(2, 4, false)]),
      maxPermissibleDraft: this.fb.control(portData?.maxPermissibleDraft, [Validators.required, numberValidator(2, 4, false)]),
      densityOfWater: this.fb.control(portData?.densityOfWater, [Validators.required, numberValidator(4, 1, false)]),
      ambientTemperature: this.fb.control(portData?.ambientTemperature, [Validators.required, numberValidator(2, 3, false)]),
      position: this.fb.control({ value: portData?.position, disabled: true })
    });
  }

  /**
   *Method to get formcontrolName
   * @param {string} formControlName
   * @return {FormControl}
   * @memberof AddPortComponent
   */
  field(formControlName: string): FormControl {
    const formControl = <FormControl>this.addPortDetailsForm.get(formControlName);
    return formControl;
  }

  /**
   * Method to check for field errors
   *
   * @param {string} formControlName
   * @return {ValidationErrors}
   * @memberof AddPortComponent
   */
  fieldError(formControlName: string): ValidationErrors {
    const formControl = this.field(formControlName);
    return formControl?.invalid && (formControl.dirty || formControl.touched) ? formControl.errors : null;
  }


  /**
   * Method to save port details
   *
   * @memberof AddPortComponent
   */
  async savePortDetails() {
    const berthDetailsForm = this.portMasterTransformationService.getBerthFormDetails();
    const translationKeys = await this.translateService.get(['PORT_ADDED_SUCCESSFULLY', 'PORT_MASTER_SUCCESS', 'PORT_MASTER_FAILURE', 'PORT_MASTER_ERROR']).toPromise();
    // TODO - has implement save api call later.
    if (this.addPortDetailsForm.valid && berthDetailsForm.valid) {
      this.messageService.add({ severity: 'success', summary: translationKeys['PORT_MASTER_SUCCESS'], detail: translationKeys['PORT_ADDED_SUCCESSFULLY'] });
    } else {
      this.addPortDetailsForm.markAllAsTouched();
      this.messageService.add({ severity: 'error', summary: translationKeys['PORT_MASTER_ERROR'], detail: translationKeys['PORT_MASTER_FAILURE'] });
    }
  }

  /**
   * Method to cancel the added port details and navigate to portlisting page
   *
   * @memberof AddPortComponent
   */
  cancel() {
    this.router.navigate(['/business/admin/port-listing']);
  }

  /**
   * Method to set coordinates to input box
   *
   * @param {*} coordinates
   * @memberof AddPortComponent
   */
  setCoordinates(coordinates: any) {
    const latAndLong = (coordinates?.lat && coordinates?.long) ? [coordinates.lat, coordinates.long] : null;
    const formControl = this.field('position');
    formControl.setValue(latAndLong);
    formControl.updateValueAndValidity();
    this.currentLocation = latAndLong;
    this.closeModal(true);
  }

  /**
   * Method to open map popup
   *
   * @memberof AddPortComponent
   */
  showMapPopup(): void {
    this.isVisible = true;
  }

  /**
   *Method to close the modal
   *
   * @param {*} event
   * @memberof AddPortComponent
   */
  closeModal(event) {
    this.isVisible = event;
  }
}
